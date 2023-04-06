<template>
  <div class="app-container">
    <z-card style="height: auto">
      <el-form ref="searchForm" class="searchForm" :model="searchForm" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="6" class="form-item-height">
            <el-form-item label="标题">
              <el-input v-model.trim="searchForm.title" size="mini" clearable placeholder="请输入标题" />
            </el-form-item>
          </el-col>
          <el-col :span="8" class="form-item-height">
            <el-form-item label="时间区间">
              <el-date-picker
                v-model="createTime"
                size="mini"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="datetimerange"
                format="yyyy-MM-dd HH:mm"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <div class="form-item-btnGroup" style="margin-left: 100px">
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
        <el-button v-permission="['b:notice:add']" size="mini" class="filter-item" type="primary" plain icon="el-icon-plus" @click="add">
          新增
        </el-button>
        <el-button
          v-permission="['b:notice:edit']"
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
          v-permission="['b:notice:delete']"
          size="mini"
          class="filter-item"
          type="danger"
          icon="el-icon-delete"
          plain
          :disabled="!(multipleSelection.length > 0)"
          @click="remove(multipleSelection)"
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
      :title="modal.dialogOperate"
      :row-data="modal.data"
      :modal-type="modal.handleType"
      @form-visible="formVisible"
      @search="search"
    />
    <info
      v-if="modal.dialogFormVisibleView"
      icon="el-icon-info"
      :visible.sync="modal.dialogFormVisibleView"
      :title="modal.dialogOperate"
      :row-data="modal.data"
      @info-visible="infoVisible"
    />
  </div>
</template>

<script>
import ZCard from '@/components/Cardarea'
import WrPagination from '@/components/Pagination'
import WrTable from '@/components/Table'
import addOrEdit from './modal/index' // 新增编辑页面
import info from './modal/info' // 新增编辑页面
import { getNoticeById, list, remove } from '~/system/notice'

export default {
  name: 'Notice',
  components: {
    addOrEdit,
    info,
    ZCard,
    WrPagination,
    WrTable
  },
  props: {},
  data() {
    return {
      multipleSelection: [], // 存放当前列表中选中的项
      pageForm: { // 分页数据集合
        total: 0,
        showPage: false
      },
      createTime: [],
      tableForm: {
        defaultSort: {
          prop: 'createTime', order: 'descending'
        },
        tableData: [],
        showSelection: true,
        operation: true,
        columnList: [
          {
            text: true,
            prop: 'title',
            label: '标题',
            columnWidth: '200'
          },
          {
            text: true,
            prop: 'content',
            label: '内容'
          },
          {
            text: true,
            prop: 'createTime',
            sortable: true,
            label: '创建时间'
          },
          {
            label: '操作',
            isOperation: true,
            operation: [
              {
                btnFont: '查看',
                btnIcon: 'el-icon-info',
                btnObject: 'infoVisible'
              },
              {
                btnFont: '编辑',
                btnIcon: 'el-icon-edit',
                btnObject: 'edit',
                permission: 'b:notice:edit'
              },
              {
                btnFont: '删除',
                btnIcon: 'el-icon-delete',
                btnObject: 'remove',
                permission: 'b:notice:delete',
                isShow: function(row) {
                  return row.type !== 1
                }
              }],
            align: 'center'
          }
        ]
      },
      searchForm: { // 搜索框数据集合
        page: {
          current: 1,
          size: 10,
          orders: [
            { column: 'create_time', asc: false }
          ]
        },
        title: '',
        fromTime: '',
        toTime: ''
      },
      modal: { // 表单数据集合
        dialogOperate: '', // 控制弹窗的title
        dialogFormVisible: false, // 控制新增编辑弹窗显示隐藏
        handleType: Symbol(), // 控制新增编辑弹窗的类型：add为新增，update为编辑
        dialogFormVisibleView: false, // 控制用户授权弹窗显示隐藏
        data: {},
        rowData: {}
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
      this.modal.data = Object.assign({}, this.modal.data)
      this.modal.handleType = this.$global.HandleType.ADD
      this.modal.dialogOperate = '新增'
      this.modal.dialogFormVisible = true
    },
    edit(row) {
      getNoticeById(row.id).then(res => {
        this.modal.data = Object.assign({}, this.modal.data, row, res.data) // copy obj
        this.modal.handleType = this.$global.HandleType.UPDATE
        this.modal.dialogOperate = '编辑'
        this.modal.dialogFormVisible = true
      }).catch(() => {
      })
    },
    remove(row) {
      this.$confirm('确定删除公告名称为"' + row.title + '"的数据项?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        remove(row.id).then(() => {
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
    formVisible() {
      this.modal.dialogFormVisible = false
      this.modal.data = {}
      this.modal.rowData = {}
    },
    pageChange(current) {
      this.searchForm.page.current = current
      this.getNotice()
    },
    handleSizeChange(size) {
      this.searchForm.page.size = size
      this.getNotice()
    },
    infoVisible(row) {
      getNoticeById(row.id).then(res => {
        this.modal.data = Object.assign({}, this.modal.data, row, res.data) // copy obj
        this.modal.dialogOperate = '查看'
        this.modal.dialogFormVisibleView = true
      }).catch(() => {
      })
    },
    getNotice() {
      if (this.createTime != null) {
        this.searchForm.fromTime = this.createTime[0]
        this.searchForm.toTime = this.createTime[1]
      } else {
        this.searchForm.fromTime = ''
        this.searchForm.toTime = ''
      }
      list(this.searchForm).then(res => {
        const { records, total } = res.data
        this.tableForm.tableData = records
        this.pageForm.total = total
        this.pageForm.showPage = true
      }).catch(() => {
      })
    },
    search() {
      this.searchForm.page.current = 1
      this.getNotice()
    },
    slotChange(data) {
      this.searchForm.page.orders = []
      if (data) {
        this.searchForm.page.orders.push(data)
      }
      this.search()
    },
    refresh() {
      this.searchForm = {
        page: {
          current: 1,
          size: 10,
          orders: [{ column: 'create_time', asc: false }]
        }
      }
      this.createTime = []
      this.search()
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
