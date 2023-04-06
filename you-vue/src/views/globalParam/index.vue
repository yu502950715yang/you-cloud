<template>
  <div class="app-container">
    <z-card style="height: auto">
      <el-form ref="searchForm" class="searchForm" :model="searchForm" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="6" class="form-item-height">
            <el-form-item label="参数名">
              <el-input v-model.trim="searchForm.paramName" size="mini" clearable placeholder="请输入参数名" />
            </el-form-item>
          </el-col>
          <el-col :span="6" class="form-item-height">
            <el-form-item label="参数值">
              <el-input v-model.trim="searchForm.paramValue" size="mini" clearable placeholder="请输入参数值" />
            </el-form-item>
          </el-col>
          <el-col :span="6" class="form-item-height">
            <el-form-item label="描述">
              <el-input v-model.trim="searchForm.paramDescription" size="mini" clearable placeholder="请输入描述" />
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
        <el-button
          v-permission="['b:globalParam:edit']"
          size="mini"
          class="filter-item"
          type="success"
          icon="el-icon-edit"
          plain
          :disabled="!(multipleSelection.length === 1)"
          @click="edit(multipleSelection[0])"
        >编辑
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
      @form-visible="formVisible"
      @save="save"
    />
  </div>
</template>

<script>
import ZCard from '@/components/Cardarea'
import addOrEdit from './modal/index'
import WrPagination from '@/components/Pagination'
import WrTable from '@/components/Table'
import { edit, list } from '~/system/globalParam'

const HandleType = Object.freeze({
  ADD: Symbol('add'),
  UPDATE: Symbol('update')
})

export default {
  components: {
    addOrEdit,
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
      searchForm: {
        page: {
          current: 1,
          size: 10,
          orders: []
        }, // 搜索框数据集合
        paramName: '',
        paramValue: '',
        paramDescription: ''
      },
      tableForm: {
        tableData: [],
        showSelection: true,
        operation: true,
        columnList: [
          {
            text: true,
            prop: 'paramName',
            label: '参数名'
          },
          {
            text: true,
            prop: 'paramValue',
            label: '参数值'
          },
          {
            text: true,
            prop: 'paramDescription',
            label: '描述'
          },
          {
            label: '操作',
            isOperation: true,
            operation: [
              {
                btnFont: '编辑',
                btnIcon: 'el-icon-edit',
                btnObject: 'edit',
                permission: 'b:globalParam:edit'
              }],
            align: 'center'
          }
        ]
      },
      modal: {
        dialogFormVisible: false, // 控制编辑弹窗显示隐藏
        handleType: Symbol(), // 控制新增编辑弹窗的类型：add为新增，update为编辑
        dialogOperate: '', // 控制弹窗的title
        data: {}
      }
    }
  },
  created() {
    this.search()
  },
  mounted() {},
  methods: {
    refresh() {
      this.searchForm = {
        page: {
          current: 1,
          size: 10,
          orders: []
        },
        paramName: '',
        paramValue: '',
        paramDescription: ''
      }
      this.search()
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    handleSizeChange(size) {
      this.searchForm.size = size
      this.getRoles()
    },
    formVisible() {
      this.modal.data = {}
    },

    pageChange(current) {
      this.searchForm.page.current = current
      this.getRoles()
    },
    getRoles() {
      list(this.searchForm).then(res => {
        const { records, total } = res.data
        this.tableForm.tableData = records
        this.pageForm.total = total
        this.pageForm.showPage = true
      }).catch(() => {
      })
    },
    edit(row) {
      this.modal.data = Object.assign({}, this.modal.data, row) // copy obj
      this.modal.handleType = HandleType.UPDATE
      this.modal.dialogOperate = '编辑'
      this.modal.dialogFormVisible = true
    },
    slotChange(data) {
      this.searchForm.page.orders = []
      if (data) {
        this.searchForm.page.orders.push(data)
      }
      this.search()
    },
    search() {
      this.searchForm.page.current = 1
      this.getRoles()
    },
    save(data) {
      if (this.modal.handleType === HandleType.UPDATE) {
        // 同新增
        edit(data).then(() => {
          this.$message({
            type: 'success',
            message: '提交成功!'
          })
          this.formVisible()
          this.search()
        }).catch(() => {
        })
      }
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
