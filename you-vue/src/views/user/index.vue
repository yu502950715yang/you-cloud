<template>
  <div class="app-container">
    <z-card style="height: auto">
      <el-form ref="searchForm" class="searchForm" :model="searchForm" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="6" class="form-item-height">
            <el-form-item label="用户姓名">
              <el-input v-model.trim="searchForm.realName" clearable size="mini" placeholder="请输入用户姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="6" class="form-item-height">
            <el-form-item label="登录名称">
              <el-input v-model.trim="searchForm.username" clearable size="mini" placeholder="请输入登录名称" />
            </el-form-item>
          </el-col>
          <el-col :span="6" class="form-item-height">
            <el-form-item label="账号状态">
              <el-select v-model="searchForm.status" clearable size="mini" style="width: 100%;" placeholder="账号状态">
                <el-option
                  v-for="item in enums.userEnableEnum"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <div class="form-item-btnGroup">
              <el-button class="filter-item" type="primary" icon="el-icon-search" size="mini" @click="search">
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
            <el-dialog
              title="导入数据"
              :visible.sync="modal.dialogVisible"
              :destroy-on-close="true"
              width="60%"
              :before-close="dialogClose"
              center
            >
              <el-table
                :data="accountList"
                border
                style="width: 100%;"
              >
                <el-table-column
                  prop="realName"
                  header-align="center"
                  align="center"
                  label="用户姓名"
                />
                <el-table-column
                  prop="username"
                  header-align="center"
                  align="center"
                  label="登录名称"
                />
                <el-table-column
                  prop="organizationId"
                  header-align="center"
                  align="center"
                  label="部门ID"
                />
                <el-table-column
                  prop="password"
                  header-align="center"
                  align="center"
                  label="密码"
                />
                <el-table-column
                  prop="emailAddress"
                  header-align="center"
                  align="center"
                  label="电子邮箱"
                />
                <el-table-column
                  prop="phone"
                  header-align="center"
                  align="center"
                  label="电话号码"
                />
              </el-table>
              <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="importExcel()">确 定</el-button>
                <el-button @click="dialogClose">取 消</el-button>
              </span>
            </el-dialog>
          </el-col>
        </el-row>
      </el-form>
    </z-card>
    <z-card style="overflow: hidden">
      <div style="width: 15%;float:left">
        <el-tree
          :data="tableData"
          row-key="organizationId"
          :default-expand-all="isExpandAll"
          :props="defaultProps"
          :expand-on-click-node="false"
          highlight-current
          @node-click="getUserList"
        />
      </div>
      <div style="width: 84%;float:right;margin-left: 1%;">
        <div class="table-item-btnGroup">
          <el-button
            v-permission="['b:user:add']"
            size="mini"
            class="filter-item"
            type="primary"
            plain
            icon="el-icon-plus"
            @click="showAdd"
          >新增
          </el-button>
          <el-button
            v-permission="['b:user:edit']"
            size="mini"
            class="filter-item"
            type="success"
            icon="el-icon-edit"
            plain
            :disabled="!(multipleSelection.length === 1)"
            @click="handleUpdate(multipleSelection[0])"
          >编辑
          </el-button>
          <el-button
            v-permission="['b:user:authorization']"
            size="mini"
            class="filter-item"
            type="warning"
            icon="el-icon-user"
            plain
            :disabled="!(multipleSelection.length === 1)"
            @click="handleAuthorizationUpdate(multipleSelection[0])"
          >用户授权
          </el-button>
          <el-button
            v-permission="['b:user:download']"
            class="filter-item"
            type="success"
            icon="el-icon-download"
            plain
            size="mini"
            @click="exportExcel"
          >导出
          </el-button>
          <el-upload
            class="filter-item"
            style="margin-left: 10px;"
            action=""
            :on-change="handleChange"
            :show-file-list="false"
            :on-remove="handleRemove"
            :file-list="fileListUpload"
            :limit="limitUpload"
            accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
            :auto-upload="false"
          >
            <el-button
              v-permission="['b:user:importExcel']"
              class="filter-item"
              type="info"
              icon="el-icon-upload2"
              plain
              size="mini"
            >导入
            </el-button>
          </el-upload>
          <el-button
            v-permission="['b:user:importExcel']"
            style="margin-left: 10px"
            class="filter-item"
            type="success"
            icon="el-icon-upload"
            plain
            size="mini"
            @click="downLoadTemplate"
          >模板下载
          </el-button>
        </div>
        <div class="block">
          <WrTable
            :page-form="searchForm.page"
            :table-form="tableForm"
            @slotChange="slotChange"
            @handleSelectionChange="handleSelectionChange"
            @rowOperation="rowOperation"
            @switchChange="switchChange"
          />
          <WrPagination
            :page-form="searchForm.page"
            :total="pageForm.total"
            @pageChange="pageChange"
            @handleSizeChange="handleSizeChange"
          />
        </div>
      </div>
    </z-card>
    <authorize
      v-if="modal.dialogAuthorizationVisible"
      :title="modal.dialogOperate"
      :visible.sync="modal.dialogAuthorizationVisible"
      :row-data="modal.rowData"
      @form-visible="formVisible"
      @search="search"
    />
    <add-or-edit
      v-if="modal.dialogFormVisible"
      :visible.sync="modal.dialogFormVisible"
      :title="modal.dialogOperate"
      :modal-type="modal.handleType"
      :row-data="modal.data"
      @form-visible="formVisible"
      @search="search"
    />
  </div>
</template>

<script>
import { changeEnable, exportDaIlyTable, getUserById, importExcel, list, resetPassword, userList } from '~/system/user'
import ZCard from '@/components/Cardarea'
import WrPagination from '@/components/Pagination'
import WrTable from '@/components/Table'
import addOrEdit from './modal/index' // 新增编辑
import authorize from './modal/authorize' // 授权弹窗
import waves from '@/directive/waves/index.js'
import enums from '@/enum/enums'
import axios from 'axios'
import { getInfoForSave } from '../../api/system/user'
import { handleTree } from '@/utils/generator/ruoyi'

export default {
  name: 'User',
  components: {
    ZCard,
    addOrEdit,
    authorize,
    WrPagination,
    WrTable
  },
  directives: {
    waves
  },
  data() {
    return {
      multipleSelection: [], // 存放当前列表中选中的项
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
            prop: 'realName',
            label: '用户名称'
          },
          {
            text: true,
            prop: 'username',
            label: '登录名称'
          },
          {
            text: true,
            prop: 'organizationName',
            label: '部门'
          },
          {
            text: true,
            prop: 'emailAddress',
            label: '电子邮箱'
          },
          {
            text: true,
            prop: 'phone',
            label: '电话号码'
          },
          {
            switch: true,
            prop: 'status',
            label: '账号状态',
            openText: '启用',
            closeText: '禁用',
            columnWidth: '100'
          },
          {
            label: '操作',
            isOperation: true,
            columnWidth: '300',
            operation: [
              {
                btnFont: '编辑',
                btnIcon: 'el-icon-edit',
                btnObject: 'handleUpdate',
                permission: 'b:user:edit'
              },
              {
                btnFont: '用户授权',
                btnIcon: 'el-icon-user',
                btnObject: 'handleAuthorizationUpdate',
                permission: 'b:user:authorization'
              },
              {
                btnFont: '重置密码',
                btnIcon: 'el-icon-refresh-right',
                btnObject: 'handleResetPassword',
                permission: 'b:user:resetPassword'
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
        realName: '',
        status: ''
      },
      modal: { // 表单数据集合
        dialogOperate: '', // 控制弹窗的title
        dialogVisible: false, // 控制导入数据窗口显示隐藏
        dialogFormVisible: false, // 控制新增编辑弹窗显示隐藏
        handleType: Symbol(), // 控制新增编辑弹窗的类型：add为新增，update为编辑
        dialogAuthorizationVisible: false, // 控制用户授权弹窗显示隐藏
        data: {},
        rowData: {}
      },
      // 部门
      department: '',
      // tbale是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      defaultProps: {
        children: 'children',
        label: 'organizationName'
      },
      // 部门树
      tableData: [],
      // 导入
      fileListUpload: [],
      // 导入
      limitUpload: 3,
      // 编辑
      disabled: false,
      // Excel
      accountList: [],
      // 枚举
      enums: enums,
      // 模板下载
      pageData: []
    }
  },
  created() {
    this.search()
    this.getOrganizationId()
  },
  methods: {
    // 展示新增弹窗
    showAdd(row) {
      getInfoForSave().then(res => {
        const organizationList = res.data.organizationList
        const organization = {
          parentId: row.organizationId
        }
        this.modal.data = Object.assign({}, this.modal.data, { organizationList: organizationList }, organization)
        this.modal.handleType = this.$global.HandleType.ADD
        this.modal.dialogOperate = '新增'
        this.modal.dialogFormVisible = true
      }).catch(() => {
      })
    },
    // 展示编辑弹窗
    handleUpdate(row) {
      getInfoForSave(row.organizationId).then(res => {
        const organizationList = res.data.organizationList
        const organization = {
          parentId: row.organizationId
        }
        this.modal.data = Object.assign({}, this.modal.data, { organizationList: organizationList }, organization)
      }).catch(() => {
      })
      getUserById(row.userId).then(res => {
        this.modal.data = Object.assign({}, this.modal.data, res.data.user) // copy obj
        this.modal.handleType = this.$global.HandleType.UPDATE
        this.modal.dialogOperate = '编辑'
        this.modal.dialogFormVisible = true
      })
    },
    // 展示用户授权页面
    handleAuthorizationUpdate(row) {
      getUserById(row.userId).then(res => {
        const dataPermission = res.data
        this.modal.rowData = Object.assign({}, this.modal.rowData, dataPermission)
        this.modal.dialogOperate = '用户授权'
        this.modal.dialogAuthorizationVisible = true
      })
    },
    getOrganizationId() {
      list(this.searchForm).then(res => {
        this.tableData = handleTree(res.data, 'organizationId')
      }).catch(() => {
      })
    },
    importExcel() {
      importExcel(this.accountList).then(() => {
        this.$message({
          type: 'success',
          message: '提交成功!'
        })
        this.dialogClose()
        this.$emit('refresh-table')
        this.search()
      })
    },

    exportExcel() {
      exportDaIlyTable(this.searchForm).then(res => {
        const blob = new Blob([res.data])
        const filename = res.headers['content-disposition']
        const downloadElement = document.createElement('a')
        const href = window.URL.createObjectURL(blob)
        downloadElement.href = href
        downloadElement.download = decodeURIComponent(filename.split('filename=')[1])
        document.body.appendChild(downloadElement)
        downloadElement.click()
        document.body.removeChild(downloadElement)
        window.URL.revokeObjectURL(href)
      })
    },
    handleSizeChange(size) {
      this.searchForm.page.size = size
      this.getUserList()
    },
    formVisible() {
      this.modal.data = {}
      this.dialogFormVisible = false
    },
    changeEnable(row) {
      changeEnable(row.userId).then(() => {
        this.$message({
          type: row.status ? 'success' : 'info',
          message: row.status ? '账号已启用' : '账号已禁用'
        })
        this.getUserList()
      }).catch(() => {
      })
    },
    handleResetPassword(row) {
      this.$confirm('确定将此用户密码重置为"123456"?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        resetPassword(row.userId).then(() => {
          this.$message({
            type: 'success',
            message: '重置成功!'
          })
        }).catch(() => {
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消重置'
        })
      })
    },
    search() {
      this.searchForm.page.current = 1
      this.getUserList()
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
        username: '',
        realName: '',
        status: ''
      }
      this.search()
    },
    pageChange(current) {
      this.searchForm.page.current = current
      this.getUserList()
    },
    getUserList(node) {
      if (node != null && node !== '') {
        this.searchForm.organizationId = node.organizationId
        this.modal.data.organizationId = node.organizationId
      }
      userList(this.searchForm).then(res => {
        const { records, total } = res.data
        for (let i = 0; i < records.length; i++) {
          records[i].status = records[i].status === 1
        }
        this.tableForm.tableData = records
        this.pageForm.total = total
      }).catch(() => {
      })
    },
    // excel表上传
    handleChange(file, fileList) {
      this.fileTemp = file.raw
      const fileName = file.raw.name
      const fileType = fileName.substring(fileName.lastIndexOf('.') + 1)
      // 判断上传文件格式
      if (this.fileTemp) {
        if ((fileType === 'xlsx') || (fileType === 'xls')) {
          this.importf(this.fileTemp)
        } else {
          this.$message({
            type: 'warning',
            message: '附件格式错误，请删除后重新上传！'
          })
        }
      } else {
        this.$message({
          type: 'warning',
          message: '请上传附件！'
        })
      }
    },
    // 移除excel表
    handleRemove(file, fileList) {
      this.fileTemp = null
    },
    downLoadTemplate() {
      axios.get('file/userImport.xlsx', {
        responseType: 'blob'
      }).then(response => {
        const url = window.URL.createObjectURL(new Blob([response.data]))
        const link = document.createElement('a')
        const fname = '用户导入模板.xlsx'
        link.href = url
        link.setAttribute('download', fname)
        document.body.appendChild(link)
        link.click()
        document.body.removeChild(link)
      })
    },
    // 导入的方法
    importf(obj) {
      this.modal.dialogVisible = true
      const _this = this
      this.file = event.currentTarget.files[0]
      var rABS = false // 是否将文件读取为二进制字符串
      var f = this.file
      var reader = new FileReader()
      FileReader.prototype.readAsBinaryString = function(f) {
        var binary = ''
        var rABS = false // 是否将文件读取为二进制字符串
        var wb // 读取完成的数据
        var outdata
        var reader = new FileReader()
        reader.onload = function(e) {
          var bytes = new Uint8Array(reader.result)
          var length = bytes.byteLength
          for (var i = 0; i < length; i++) {
            binary += String.fromCharCode(bytes[i])
          }
          var XLSX = require('xlsx')
          if (rABS) {
            // eslint-disable-next-line no-undef
            wb = XLSX.read(btoa(fixdata(binary)), { // 手动转化
              type: 'base64'
            })
          } else {
            wb = XLSX.read(binary, {
              type: 'binary'
            })
          }
          // outdata就是你想要的东西 excel导入的数据
          outdata = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]])
          // excel 数据再处理
          const arr = []
          outdata.map(v => {
            const jsonString = JSON.stringify(v).replace(/\//g, '').replace(/\s/ig, '')
            v = JSON.parse(jsonString)
            const obj = {}
            obj.realName = v.用户姓名
            obj.username = v.登录名称
            obj.organizationId = v.部门ID
            obj.emailAddress = v.电子邮箱
            obj.phone = v.电话号码
            obj.password = v.密码
            arr.push(obj)
          })
          _this.accountList = [...arr]
        }
        reader.readAsArrayBuffer(f)
      }
      if (rABS) {
        reader.readAsArrayBuffer(f)
      } else {
        reader.readAsBinaryString(f)
      }
    },
    dialogClose() {
      this.modal.dialogVisible = false
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    rowOperation(row, now) {
      this[now](row)
    },
    switchChange(row, prop) {
      this.changeEnable(row)
    }
  }
}
</script>
<style scoped>
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
