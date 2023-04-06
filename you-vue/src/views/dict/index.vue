<template>
  <div class="app-container">
    <z-card style="height: auto">
      <el-form ref="searchForm" class="searchForm" :model="searchForm" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="6" class="form-item-height">
            <el-form-item label="字典名称">
              <el-input v-model.trim="searchForm.dictName" size="mini" clearable placeholder="请输入字典名称" />
            </el-form-item>
          </el-col>
          <el-col :span="6" class="form-item-height">
            <el-form-item label="描述">
              <el-input v-model.trim="searchForm.description" size="mini" clearable placeholder="请输入描述" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <div class="form-item-btnGroup">
              <el-button size="mini" class="filter-item" type="primary" icon="el-icon-search" @click="search">
                搜索
              </el-button>
              <el-button
                size="mini"
                class="filter-item"
                style="margin-left: 10px;"
                icon="el-icon-refresh"
                @click="refresh"
              >
                重置
              </el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>
    </z-card>
    <z-card>
      <div class="table-item-btnGroup">
        <el-button v-permission="['b:dict:add']" size="mini" class="filter-item" type="primary" plain icon="el-icon-plus" @click="add">
          新增
        </el-button>
        <el-button
          v-permission="['b:dict:edit']"
          size="mini"
          class="filter-item"
          type="success"
          icon="el-icon-edit"
          plain
          :disabled="!(multipleSelection.length === 1)"
          @click="edit(multipleSelection[0])"
        >编辑
        </el-button>
        <el-button
          v-permission="['b:dict:delete']"
          size="mini"
          class="filter-item"
          type="danger"
          icon="el-icon-delete"
          plain
          :disabled="!(multipleSelection.length > 0)"
          @click="batchRemove(multipleSelection)"
        >删除
        </el-button>
      </div>

      <div class="block">
        <WrTable
          :page-form="searchForm.page"
          :table-form="tableForm"
          @slotChange="slotChange"
          @handleSelectionChange="handleSelectionChange"
          @rowOperation="rowOperation"
        />
        <WrPagination
          :page-form="searchForm.page"
          :total="pageForm.total"
          @pageChange="pageChange"
          @handleSizeChange="handleSizeChange"
        />
      </div>
    </z-card>
    <add-or-edit
      v-if="modal.dialogFormVisible"
      :visible.sync="modal.dialogFormVisible"
      :row-data="modal.data"
      :title="modal.dialogOperate"
      @form-visible="formVisible"
      @save="save"
    />
    <info
      v-if="modal.dialogFormVisibleView"
      :visible.sync="modal.dialogFormVisibleView"
      :title="modal.dialogOperate"
      :row-data="modal.data"
      @info-visible="infoVisible"
      @saveDictValue="saveDictValue"
      @updateDictValue="updateDictValue"
      @removeDictValue="removeDictValue"
    />
  </div>
</template>

<script>
import ZCard from '@/components/Cardarea'
import addOrEdit from './modal/index'
import info from './modal/info'
import WrPagination from '@/components/Pagination'
import WrTable from '@/components/Table'
import { add, edit, list, remove } from '~/system/dict'

const HandleType = Object.freeze({
  ADD: Symbol('add'),
  UPDATE: Symbol('update')
})
export default {
  name: 'Dict',
  components: {
    addOrEdit,
    ZCard,
    info,
    WrTable,
    WrPagination
  },
  props: {},
  data() {
    return {
      multipleSelection: [], // 存放当前列表中选中的项
      searchForm: { // 搜索框数据集合
        page: {
          current: 1,
          size: 10,
          orders: []
        },
        dictName: '',
        description: ''
      },
      pageForm: { // 分页数据集合
        total: 0,
        showPage: false
      },
      tableForm: {
        tableData: [],
        showSelection: true,
        operation: true,
        columnList: [
          {
            text: true,
            prop: 'dictName',
            label: '字典名称'
          },
          {
            text: true,
            prop: 'description',
            label: '描述'
          },
          {
            label: '操作',
            isOperation: true,
            operation: [
              {
                btnFont: '详情',
                btnIcon: 'el-icon-info',
                btnObject: 'info',
                permission: 'b:dict:info'
              }, {
                btnFont: '编辑',
                btnIcon: 'el-icon-edit',
                btnObject: 'edit',
                permission: 'b:dict:edit'
              },
              {
                btnFont: '删除',
                btnIcon: 'el-icon-delete',
                btnObject: 'remove',
                permission: 'b:dict:delete'
              }],
            align: 'center'
          }
        ]

      },
      modal: {
        dialogFormVisible: false, // 控制新增编辑弹窗显示隐藏
        handleType: Symbol(), // 控制新增编辑弹窗的类型：add为新增，update为编辑
        dialogOperate: '', // 控制弹窗的title
        dialogFormVisibleView: false, // 控制详情弹窗显示隐藏
        data: {}
      }
    }
  },
  created() {
    this.search()
  },
  mounted() {
  },
  methods: {
    add() {
      this.modal.dialogFormVisible = true
      this.modal.handleType = HandleType.ADD
      this.modal.dialogOperate = '新增'
    },
    edit(row) {
      this.modal.data = Object.assign({}, this.modal.data, row) // copy obj
      this.modal.handleType = HandleType.UPDATE
      this.modal.dialogOperate = '编辑'
      this.modal.dialogFormVisible = true
    },
    remove(row) {
      this.$confirm('确定删除字典名称为"' + row.dictName + '"的数据项?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var data = { ids: row.id }
        remove(data).then(() => {
          this.search()
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    slotChange(data) {
      this.searchForm.page.orders = []
      if (data) {
        this.searchForm.page.orders.push(data)
      }
      this.search()
    },
    info(row) {
      this.modal.data = Object.assign({}, this.modal.data, row) // copy obj
      this.modal.handleType = HandleType.UPDATE
      this.modal.dialogOperate = '详情'
      this.modal.dialogFormVisibleView = true
    },
    batchRemove(row) {
      let dictName = ''
      let id = ''
      for (let i = 0; i < row.length; i++) {
        if (i === row.length - 1) {
          dictName += row[i].dictName
          id += row[i].id
        } else {
          dictName += row[i].dictName + ','
          id += row[i].id + ','
        }
      }
      var data = { ids: id }
      this.$confirm('确定删除字典名称名称为"' + dictName + '"的数据项?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        remove(data).then(() => {
          this.search()
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch(() => {
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },

    save(data) {
      if (this.modal.handleType === HandleType.ADD) {
        add(data).then(() => {
          this.$message({
            type: 'success',
            message: '提交成功!'
          })
          this.formVisible()
          this.search()
        })
      }
      if (this.modal.handleType === HandleType.UPDATE) {
        // 同新增
        edit(data).then(() => {
          this.$message({
            type: 'success',
            message: '提交成功!'
          })
          this.formVisible()
          this.search()
        })
      }
    },

    getDict() {
      list(this.searchForm).then(res => {
        const { records, total } = res.data
        this.tableForm.tableData = records
        this.pageForm.total = total
        this.pageForm.showPage = true
      }).catch(err => {
      })
    },

    formVisible() {
      this.modal.data = {}
      this.modal.dialogFormVisible = false
    },
    infoVisible() {
      this.modal.data = {}
    },
    handleSizeChange(size) {
      this.searchForm.page.size = size
      this.getRoles()
    },
    pageChange(current) {
      this.searchForm.page.current = current
      this.getDict()
    },
    search() {
      this.searchForm.page.current = 1
      this.getDict()
    },
    refresh() {
      this.searchForm = {
        page: {
          current: 1,
          size: 10,
          orders: []
        },
        dictName: '',
        description: ''
      }
      this.search()
    },

    saveDictValue() {
      this.$message({
        type: 'success',
        message: '提交成功'
      })
      this.infoVisible()
    },
    updateDictValue() {
      this.$message({
        type: 'success',
        message: '提交成功'
      })
      this.infoVisible()
    },
    removeDictValue(dictId) {
      this.$confirm('确定删除此条记录?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        remove(dictId).then(() => {
          this.search()
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    rowOperation(row, now) {
      this[now](row)
    }
  }
}
</script>

<style lang="scss" scoped>
.table-item-btnGroup {
  margin-bottom: 10px;
}

.form-item-btnGroup {
  margin-left: 10px;
  margin-top: 5px
}

.filter-item {
  display: inline-block;
}
</style>
