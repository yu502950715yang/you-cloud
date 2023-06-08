<template>
  <div class="app-container">
    <z-card style="height: auto">
      <el-form ref="searchForm" class="searchForm" :model="searchForm" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="6" class="form-item-height">
            <el-form-item label="登录名称">
              <el-input v-model.trim="searchForm.username" clearable size="mini" />
            </el-form-item>
          </el-col>
          <el-col :span="8" class="form-item-height">
            <el-form-item label="操作时间">
              <el-date-picker
                v-model="searchTime"
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
    <todoView
      v-if="modal.dialogTodoViewVisible"
      :title="modal.dialogOperate"
      :visible.sync="modal.dialogTodoViewVisible"
      :row-data="modal.rowData"
      @form-visible="formVisible"
      @search="search"
    />
    <approveDetail
      v-if="modal.dialogApproveVisible"
      :title="modal.dialogOperate"
      :visible.sync="modal.dialogApproveVisible"
      :row-data="modal.data"
      @form-visible="formVisible"
      @search="search"
    />
  </div>
</template>

<script>
import ZCard from '@/components/Cardarea'
import { list, getApproveDetail } from '~/system/todoList'
import WrPagination from '@/components/Pagination'
import WrTable from '@/components/Table'
import todoView from './modal/todoView'
import approveDetail from './modal/index'

export default {
  name: 'Log',
  components: {
    ZCard,
    WrTable,
    WrPagination,
    todoView,
    approveDetail
  },
  props: {},
  data() {
    return {
      multipleSelection: [], // 存放当前列表中选中的项
      pageForm: { // 分页数据集合
        total: 0,
        showPage: false
      },
      searchTime: [],
      tableForm: {
        tableData: [],
        operation: false,
        showSelection: false,
        columnList: [
          {
            text: true,
            prop: 'procName',
            label: '流程类型'
          },
          {
            text: true,
            prop: 'approve',
            label: '申请人'
          },
          {
            text: true,
            prop: 'approveTime',
            label: '申请时间'
          },
          {
            text: true,
            prop: 'taskName',
            label: '当前节点'
          },
          {
            label: '操作',
            isOperation: true,
            columnWidth: '300',
            operation: [
              {
                btnFont: '审批',
                btnIcon: 'el-icon-edit',
                btnObject: 'handleApprove'
              },
              {
                btnFont: '流程图',
                btnIcon: 'el-icon-user',
                btnObject: 'handleTodoView'
              }],
            align: 'center',
            width: '200'
          }
        ]
      },
      searchForm: { // 搜索框数据集合
        page: {
          current: 1,
          size: 10,
          orders: []
        },
        username: '',
        fromTime: '',
        toTime: ''
      },
      modal: { // 表单数据集合
        dialogOperate: '', // 控制弹窗的title
        dialogApproveVisible: false, // 控制审批弹窗显示隐藏
        dialogTodoViewVisible: false, // 控制流程图弹窗显示隐藏
        data: {},
        rowData: {}
      }
    }
  },
  created() {
    this.search()
  },
  mounted() {},
  methods: {
    handleApprove(row) {
      getApproveDetail(row).then(res => {
        this.modal.data = Object.assign({}, this.modal.data, res.data, { procKey: row.procKey, taskId: row.taskId })
        this.modal.dialogApproveVisible = true
        this.modal.dialogOperate = '审批'
      }).catch(() => {
      })
    },
    handleTodoView(row) {
      this.modal.rowData = row
      this.modal.dialogTodoViewVisible = true
      this.modal.dialogOperate = '流程图'
    },
    slotChange(data) {
      this.searchForm.page.orders = []
      if (data) {
        this.searchForm.page.orders.push(data)
      }
      this.search()
    },

    pageChange(current) {
      this.searchForm.page.current = current
      this.getLog()
    },
    handleSizeChange(size) {
      this.searchForm.page.size = size
      this.getLog()
    },
    search() {
      this.searchForm.page.current = 1
      this.getLog()
    },
    formVisible() {
      this.temp = {}
      this.dialogFormVisible = false
    },
    refresh() {
      this.searchForm = {
        page: {
          current: 1,
          size: 10,
          orders: []
        },
        username: ''
      }
      this.searchTime = []
      this.search()
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    rowOperation(row, now) {
      this[now](row)
    },
    getLog() {
      list(this.searchForm).then(res => {
        const { records, total } = res.data
        this.tableForm.tableData = records
        this.pageForm.total = total
        this.pageForm.showPage = true
      }).catch(() => {
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container >>> .cell {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.app-container >>> .el-tooltip {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  width: 100%;
}
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
