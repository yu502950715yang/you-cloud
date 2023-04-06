<template>
  <div class="app-container">
    <z-card style="height: auto">
      <el-form ref="searchForm" class="searchForm" :model="searchForm" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="6" class="form-item-height">
            <el-form-item label="菜单名称">
              <el-input v-model.trim="searchForm.menuName" clearable size="mini" placeholder="请输入部门名称" />
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
          </el-col>
        </el-row>
      </el-form>
    </z-card>
    <z-card style="height: auto">
      <div class="table-item-btnGroup">
        <el-button
          v-permission="['b:menu:add']"
          size="mini"
          class="filter-item"
          type="primary"
          plain
          icon="el-icon-plus"
          @click="showAdd"
        >新增
        </el-button>
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >展开/折叠
        </el-button>
      </div>
      <el-table
        v-if="refreshTable"
        :data="menuData"
        row-key="menuId"
        :default-expand-all="isExpandAll"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      >
        <el-table-column prop="menuName" label="菜单名称" />
        <el-table-column prop="icon" label="图标">
          <template slot-scope="scope">
            <i :class="scope.row.icon" />
          </template>
        </el-table-column>
        <el-table-column prop="orderNum" label="排序" />
        <el-table-column prop="permToken" label="权限识别" />
        <el-table-column prop="path" label="path路由" />
        <el-table-column prop="status" label="菜单状态" :formatter="statusFormatter" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="380">
          <template slot-scope="{row}">
            <el-button v-permission="['b:menu:edit']" type="text" size="mini" icon="el-icon-edit" @click="showEdit(row)">
              编辑
            </el-button>
            <el-button v-permission="['b:menu:add']" size="mini" type="text" icon="el-icon-plus" @click="showAdd(row)">
              新增
            </el-button>
            <el-button size="mini" type="text" icon="el-icon-delete" @click="remove(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </z-card>
    <add-or-edit
      v-if="modal.dialogFormVisible"
      :visible.sync="modal.dialogFormVisible"
      :modal-type="modal.handleType"
      :title="modal.dialogOperate"
      :row-data="modal.rowData"
      @form-visible="formVisible"
      @search="search"
    />
  </div>
</template>

<script>

import addOrEdit from './modal/index' // 新增编辑
import enums from '@/enum/enums'
import ZCard from '@/components/Cardarea'
import { handleTree } from '@/utils/generator/ruoyi'
import { getMenuById, list, menuList, remove } from '~/system/menu'

export default {
  components: {
    addOrEdit,
    ZCard
  },
  data() {
    return {
      enums: enums,
      // tbale是否展开，默认不展开
      isExpandAll: false,
      // 重新渲染表格状态
      refreshTable: true,
      modal: { // 表单数据集合
        dialogOperate: '', // 控制弹窗的title
        dialogFormVisible: false, // 控制新增编辑弹窗显示隐藏
        handleType: Symbol(), // 控制新增编辑弹窗的类型：add为新增，update为编辑
        dialogAuthorizationVisible: false, // 控制用户授权弹窗显示隐藏
        rowData: {}
      },
      menuData: [],
      searchForm: {
        menuName: ''
      }
    }
  },
  computed: {},
  created() {
    this.$nextTick(() => {
      this.search()
    })
  },
  methods: {
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false
      this.isExpandAll = !this.isExpandAll
      this.$nextTick(() => {
        this.refreshTable = true
      })
    },
    search() {
      list(this.searchForm).then(res => {
        this.menuData = handleTree(res.data, 'menuId')
      }).catch(() => {
      })
    },
    showAdd(data) {
      const menu = {
        parentId: data.menuId
      }
      menuList().then(res => {
        this.modal.rowData = Object.assign({}, this.rowData, { menuList: res.data }, menu)
        this.modal.handleType = this.$global.HandleType.ADD
        this.modal.dialogOperate = '新增'
        this.modal.dialogFormVisible = true
      }).catch(() => {
      })
    },
    formVisible() {
      this.modal.rowData = {}
    },
    showEdit(row) {
      getMenuById(row.menuId).then(res => {
        const menu = res.data.menu
        menu.menuType = menu.menuType.toString()
        menu.status = menu.status.toString()
        this.modal.rowData = Object.assign({}, menu, { menuList: res.data.menuList }) // copy obj
        this.modal.handleType = this.$global.HandleType.UPDATE
        this.modal.dialogOperate = '编辑'
        this.modal.dialogFormVisible = true
      }).catch(() => {
      })
    },
    statusFormatter(row) {
      return this.enums.organizationStatusEnum.getLabelByValue(row.status)
    },
    remove(row) {
      this.$confirm('确定将此菜单删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        remove(row.menuId).then(() => {
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
    refresh() {
      this.searchForm = {
        menuName: ''
      }
      this.search()
    }
  }
}
</script>

<style>
.table-item-btnGroup {
  margin-bottom: 10px;
}
.form-item-btnGroup {
  margin-left: 10px;
  margin-top: 5px
}
</style>
