<template>
  <div class="app-container">
    <z-card style="height: auto">
      <el-form ref="searchForm" class="searchForm" :model="searchForm" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="6" class="form-item-height">
            <el-form-item label="角色名称">
              <el-input v-model.trim="searchForm.roleName" size="mini" clearable placeholder="请输入角色名称" />
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
        <el-button v-permission="['b:role:add']" size="mini" class="filter-item" type="primary" plain icon="el-icon-plus" @click="add">
          新增
        </el-button>
        <el-button
          v-permission="['b:role:edit']"
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
          v-permission="['b:role:authorize']"
          size="mini"
          class="filter-item"
          type="warning"
          icon="el-icon-user"
          plain
          :disabled="!(multipleSelection.length === 1)"
          @click="handleAuthorizationUpdate(multipleSelection[0])"
        >角色授权
        </el-button>
        <el-button
          v-permission="['b:role:delete']"
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
      :title="modal.dialogOperate"
      :row-data="modal.data"
      :modal-type="modal.handleType"
      @form-visible="formVisible"
      @search="search"
    />
    <authorize
      v-if="modal.dialogAuthorizationVisible"
      :title="modal.dialogOperate"
      :visible.sync="modal.dialogAuthorizationVisible"
      :row-data="modal.rowData"
      @form-visible="formVisible"
      @search="search"
    />
  </div>
</template>

<script>
import ZCard from '@/components/Cardarea'
import WrPagination from '@/components/Pagination'
import WrTable from '@/components/Table'
import addOrEdit from './modal/index' // 新增编辑页面
import authorize from './modal/authorize' // 授权弹窗
import { getAll, getMenuList, getPermTokenList, list, remove } from '~/system/role'

export default {
  name: 'Role',
  components: {
    addOrEdit,
    ZCard,
    authorize,
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
      tableForm: {
        defaultSort: {
          prop: 'roleName', order: 'descending'
        },
        tableData: [],
        showSelection: true,
        operation: true,
        columnList: [
          {
            text: true,
            prop: 'roleName',
            label: '角色名称',
            sortable: true,
            columnWidth: '200'
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
                btnFont: '编辑',
                btnIcon: 'el-icon-edit',
                btnObject: 'edit',
                permission: 'b:role:edit'
              },
              {
                btnFont: '角色授权',
                btnIcon: 'el-icon-user',
                btnObject: 'handleAuthorizationUpdate',
                permission: 'b:role:authorize'
              },
              {
                btnFont: '删除',
                btnIcon: 'el-icon-delete',
                btnObject: 'remove',
                permission: 'b:role:delete',
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
            { column: 'role_name', asc: false }
          ]
        },
        roleName: ''
      },
      modal: { // 表单数据集合
        dialogOperate: '', // 控制弹窗的title
        dialogFormVisible: false, // 控制新增编辑弹窗显示隐藏
        handleType: Symbol(), // 控制新增编辑弹窗的类型：add为新增，update为编辑
        dialogAuthorizationVisible: false, // 控制用户授权弹窗显示隐藏
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
      getMenuList(this.searchForm.role).then(res => {
        const menuList = res.data
        this.modal.data = Object.assign({}, this.modal.data, menuList)
        this.modal.handleType = this.$global.HandleType.ADD
        this.modal.dialogOperate = '新增'
        this.modal.dialogFormVisible = true
      }).catch(() => {
      })
    },
    edit(row) {
      getPermTokenList(row.roleId).then(res => {
        this.modal.data = Object.assign({}, this.modal.data, row, res.data) // copy obj
        this.modal.handleType = this.$global.HandleType.UPDATE
        this.modal.dialogOperate = '编辑'
        this.modal.dialogFormVisible = true
      }).catch(() => {
      })
    },
    handleAuthorizationUpdate(row) {
      getAll(row.roleId).then(res => {
        const dataPermission = res.data
        this.modal.rowData = Object.assign({}, this.rowData, dataPermission)
        this.modal.dialogOperate = '角色授权'
        this.modal.dialogAuthorizationVisible = true
      }).catch(() => {
      })
    },
    remove(row) {
      this.$confirm('确定删除角色名称为"' + row.roleName + '"的数据项?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var data = { roleIds: row.roleId }
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
    batchRemove(row) {
      let roleName = ''
      let roleId = ''
      for (let i = 0; i < row.length; i++) {
        if (i === row.length - 1) {
          roleName += row[i].roleName
          roleId = roleId + row[i].roleId
        } else {
          roleName = roleName + row[i].roleName + ','
          roleId = roleId + row[i].roleId + ','
        }
      }
      var data = { roleIds: roleId }
      this.$confirm('确定删除角色名称为"' + roleName + '"的数据项?', '提示', {
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
    getAll() {
      getAll(this.searchForm).then(res => {
        const { records, total } = res.data
        this.tableForm.tableData = records
        this.pageForm.total = total
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
