<template>
  <div class="app-container">
    <z-card style="height: auto">
      <el-form ref="searchForm" class="searchForm" :model="searchForm" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="6" class="form-item-height">
            <el-form-item label="部门名称">
              <el-input v-model.trim="searchForm.organizationName" clearable size="mini" placeholder="请输入部门名称" />
            </el-form-item>
          </el-col>
          <el-col :span="6" class="form-item-height">
            <el-form-item label="状态">
              <el-select v-model="searchForm.status" clearable size="mini" style="width: 100%;" placeholder="部门状态">
                <el-option
                  v-for="item in enums.organizationStatusEnum"
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
          </el-col>
        </el-row>
      </el-form>
    </z-card>
    <z-card style="height: auto">
      <div class="table-item-btnGroup">
        <el-button
          v-permission="['b:organization:add']"
          size="mini"
          class="filter-item"
          type="primary"
          plain
          icon="el-icon-plus"
          @click="add"
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
        :data="tableData"
        row-key="organizationId"
        :default-expand-all="isExpandAll"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      >
        <el-table-column prop="organizationName" label="部门名称" />
        <el-table-column prop="sortId" label="排序" />
        <el-table-column prop="status" label="部门状态" :formatter="statusFormatter" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="380">
          <template slot-scope="{row}">
            <el-button v-if="row.parentId !== -1" v-permission="['b:organization:update']" type="text" size="mini" icon="el-icon-edit" @click="edit(row)">
              编辑
            </el-button>
            <el-button v-if="row.parentId !== -1" v-permission="['b:organization:add']" size="mini" type="text" icon="el-icon-plus" @click="add(row)">
              新增
            </el-button>
            <el-button v-if="row.parentId !== -1" v-permission="['b:organization:delete']" size="mini" type="text" icon="el-icon-delete" @click="remove(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </z-card>
    <addOrEdit
      v-if="modal.dialogFormVisible"
      :visible.sync="modal.dialogFormVisible"
      :title="modal.dialogOperate"
      :row-data="modal.rowData"
      @form-visible="formVisible"
      @save="save"
    />
  </div>
</template>

<script>
import ZCard from '@/components/Cardarea'
import addOrEdit from './modal/index'
import { addOrganization, getInfoForSave, list, remove, updateOrganization } from '~/system/organization'
import enums from '@/enum/enums'
import { handleTree } from '@/utils/generator/ruoyi'

const HandleType = Object.freeze({
  ADD: Symbol('add'),
  UPDATE: Symbol('update')
})
export default {
  components: {
    ZCard,
    addOrEdit
  },
  data() {
    return {
      enums: enums,
      tableData: [],
      defaultProps: {
        children: 'children',
        label: 'organizationName'
      },
      searchForm: {
        organizationName: '',
        status: null
      },
      // tbale是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      modal: {
        dialogFormVisible: false,
        handleType: Symbol(),
        dialogOperate: '',
        data: {},
        rowData: {}
      }
    }
  },
  created() {
    this.search()
  },
  methods: {
    statusFormatter(row) {
      return this.enums.organizationStatusEnum.getLabelByValue(row.status)
    },
    search() {
      list(this.searchForm).then(res => {
        this.tableData = handleTree(res.data, 'organizationId')
      }).catch(() => {
      })
    },
    refresh() {
      this.searchForm = {
        organizationName: '',
        status: null
      }
      this.search()
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false
      this.isExpandAll = !this.isExpandAll
      this.$nextTick(() => {
        this.refreshTable = true
      })
    },
    add(row) {
      getInfoForSave().then(res => {
        const organizationList = res.data.organizationList
        const organization = {
          parentId: row.organizationId,
          status: this.enums.organizationStatusEnum.ENABLE.value
        }
        this.modal.rowData = Object.assign({}, this.rowData, { organizationList: organizationList }, organization)
        this.modal.handleType = HandleType.ADD
        this.modal.dialogOperate = '新增'
        this.modal.dialogFormVisible = true
      }).catch(() => {
      })
    },
    edit(row) {
      getInfoForSave(row.organizationId).then(res => {
        const organizationList = res.data.organizationList
        const organization = res.data.organization
        this.modal.rowData = Object.assign({}, this.rowData, { organizationList: organizationList }, organization)
        this.modal.handleType = HandleType.UPDATE
        this.modal.dialogOperate = '编辑'
        this.modal.dialogFormVisible = true
      }).catch(() => {
      })
    },
    save(data) {
      if (this.modal.handleType === HandleType.ADD) {
        addOrganization(data).then(() => {
          this.$message({
            type: 'success',
            message: '提交成功!'
          })
          this.formVisible()
          this.search()
        }).catch(() => {
          return false
        })
      }
      if (this.modal.handleType === HandleType.UPDATE) {
        updateOrganization(data).then(() => {
          this.$message({
            type: 'success',
            message: '提交成功!'
          })
          this.formVisible()
          this.search()
        }).catch(() => {
          return false
        })
      }
    },
    remove(row) {
      this.$confirm('确定删除此条记录?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        remove(row.organizationId).then(() => {
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
      this.modal.rowData = {}
      this.modal.dialogFormVisible = false
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
</style>
