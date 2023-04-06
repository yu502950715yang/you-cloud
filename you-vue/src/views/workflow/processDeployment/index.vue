<template>
  <div class="app-container">
    <z-card style="height: auto">
      <el-form ref="searchForm" class="searchForm" :model="searchForm" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="6" class="form-item-height">
            <el-form-item label="流程名称">
              <el-select
                v-model.trim="searchForm.procKey"
                placeholder="请选择流程"
                clearable
                :style="{width: '100%'}"
              ><el-option
                v-for="item in procListSelect"
                :key="item.procKey"
                :label="item.procName"
                :value="item.procKey"
              />
              </el-select>
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
        <el-button size="mini" class="filter-item" type="primary" plain icon="el-icon-plus" @click="deployment">
          部署
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
    <deployment
      v-if="modal.dialogDeploymentVisible"
      :title="modal.dialogOperate"
      :visible.sync="modal.dialogDeploymentVisible"
      @form-visible="formVisible"
      @search="search"
    />
    <approver
      v-if="modal.dialogApproverVisible"
      :title="modal.dialogOperate"
      :visible.sync="modal.dialogApproverVisible"
      :row-data="modal.data"
      @form-visible="formVisible"
      @search="search"
    />
  </div>
</template>

<script>
import ZCard from '@/components/Cardarea'
import WrPagination from '@/components/Pagination'
import WrTable from '@/components/Table'
import { getProcListSelect, list, deployPersonDetail } from '~/system/processDeployment'
import deployment from './modal/deployment' // 授权弹窗
import approver from './modal/index' // 授权弹窗

export default {
  name: 'Role',
  components: {
    ZCard,
    WrPagination,
    WrTable,
    deployment,
    approver
  },
  props: {},
  data() {
    return {
      multipleSelection: [], // 存放当前列表中选中的项
      pageForm: { // 分页数据集合
        total: 0,
        showPage: false
      },
      tableForm: {
        defaultSort: {
        },
        tableData: [],
        showSelection: false,
        operation: true,
        columnList: [
          {
            text: true,
            prop: 'id',
            label: '流程实例ID'
          },
          {
            text: true,
            prop: 'procName',
            label: '流程名称'
          },
          {
            text: true,
            prop: 'procKey',
            label: '流程实例Key'
          },
          {
            text: true,
            prop: 'procVersion',
            label: '流程版本'
          },
          {
            label: '操作',
            isOperation: true,
            operation: [
              {
                btnFont: '选择审批人',
                btnIcon: 'el-icon-user',
                btnObject: 'handleAuthorizationUpdate'
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
          ]
        },
        procKey: ''
      },
      modal: { // 表单数据集合
        dialogOperate: '', // 控制弹窗的title
        dialogDeploymentVisible: false, // 控制部署弹窗显示隐藏
        dialogApproverVisible: false, // 控制部署弹窗显示隐藏
        data: {}
      },
      procListSelect: []
    }
  },
  created() {
    getProcListSelect().then(res => {
      this.procListSelect = res.data
    }).catch(() => {
    })
    this.search()
  },
  mounted() {
  },
  methods: {
    deployment() {
      this.modal.dialogOperate = '部署'
      this.modal.dialogDeploymentVisible = true
    },
    handleAuthorizationUpdate(data) {
      deployPersonDetail(data.procKey).then((res) => {
        this.modal.data.deployPersonList = Object.assign([], this.modal.data, res.data) // copy obj
        this.modal.dialogOperate = '选择审批人'
        this.modal.dialogApproverVisible = true
      })
    },
    formVisible() {
      this.modal.dialogFormVisible = false
      this.modal.data = {}
      this.modal.rowData = {}
    },
    pageChange(current) {
      this.searchForm.page.current = current
      this.getRoles()
    },
    handleSizeChange(size) {
      this.searchForm.page.size = size
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
    search() {
      this.searchForm.page.current = 1
      this.getRoles()
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
          orders: []
        },
        roleName: ''
      }
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
