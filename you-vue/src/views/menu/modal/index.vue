<template>
  <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose">
    <el-form
      ref="dataForm"
      :model="dataForm"
      label-position="right"
      :rules="rules"
      label-width="80px"
      style="overflow: hidden"
    >
      <el-row :gutter="30">
        <el-col :span="24">
          <el-form-item
            label="上级菜单"
            prop="parentId "
          >
            <select-tree
              v-model="dataForm.parentId"
              :default-select="dataForm.parentId"
              :department-list="rowData.menuList"
              :default-props="defaultProps"
              clearable
              placeholder="选择上级菜单"
              @selectChange="selectChange"
            />

          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="菜单类型" prop="menuType">
            <el-radio-group v-model="dataForm.menuType">
              <el-radio label="1">菜单</el-radio>
              <el-radio label="2">按钮</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item
            label="菜单名称"
            prop="menuName"
          >
            <el-input v-model.trim="dataForm.menuName" placeholder="请输入菜单名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="菜单代码"
            prop="menuId"
          >
            <el-input v-if="handleType===this.$global.HandleType.ADD" v-model.trim="dataForm.menuId" placeholder="请输入菜单代码" />
            <el-input v-else v-model.trim="dataForm.menuId" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="权限识别"
            prop="permToken"
          >
            <el-input v-model.trim="dataForm.permToken" placeholder="请输入权限识别" />
          </el-form-item>
        </el-col>
        <el-col v-if="dataForm.menuType == 1" :span="12">
          <el-form-item
            label="path路由"
            prop="path"
          >
            <el-input v-model.trim="dataForm.path" placeholder="请输入path路由" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="排序"
            prop="orderNum"
          >
            <el-input v-model.trim="dataForm.orderNum" placeholder="请输入排序" />
          </el-form-item>
        </el-col>
        <el-col v-if="dataForm.menuType == 1" :span="12">
          <el-form-item
            label="图标"
            prop="icon"
          >
            <el-input v-model.trim="dataForm.icon" placeholder="请输入图标" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否启用" prop="status">
            <el-radio-group v-model="dataForm.status">
              <el-radio label="1">是</el-radio>
              <el-radio label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button v-waves @click="close">
        取消
      </el-button>
      <el-button v-waves type="primary" @click="handleConfirm">
        提交
      </el-button>
    </div>
  </el-dialog>

</template>

<script>

import rule from '@/utils/rules' // 校验
import SelectTree from '@/components/SelectTree'
import { add, edit } from '~/system/menu'

export default {
  components: {
    SelectTree
  },
  inheritAttrs: false,
  props: {
    modalType: {
      type: Symbol,
      required: true
    },
    rowData: {
      type: Object,
      required: false,
      default: () => {
        return {
          dataForm: {
            menuType: '1',
            menuName: '',
            orderNum: '',
            icon: '',
            status: '1',
            menuId: '',
            parentId: '',
            permToken: '',
            menuList: []
          }
        }
      }
    }
  },
  data() {
    const validateSortNumber = (rule, value, callback) => {
      if (value !== '' && value !== undefined && value !== null) {
        if (!/^\+?[1-9][0-9]*$/.test(value)) {
          callback(new Error('只能填写正整数'))
        } else {
          callback()
        }
      } else {
        callback()
      }
    }
    return {
      handleType: this.modalType,
      dataForm: {
        menuType: this.rowData.menuType || '1',
        menuName: this.rowData.menuName,
        orderNum: this.rowData.orderNum,
        icon: this.rowData.icon,
        status: this.rowData.status || '1',
        menuId: this.rowData.menuId || this.rowData.parentId,
        parentId: this.rowData.parentId,
        permToken: this.rowData.permToken,
        path: this.rowData.path
      },
      treeData: [],
      defaultProps: {
        label: 'menuName',
        value: 'menuId'
      },
      rules: {
        menuName: [
          { required: true, message: '请输入菜单名称', trigger: 'blur' },
          { max: 20, message: '菜单名称最多20位', trigger: 'blur' },
          { validator: rule.FormValidate.Form().validateSpecialKey, trigger: 'blur' }
        ],
        menuId: [
          { required: true, message: '请输入菜单代码', trigger: 'blur' },
          { max: 20, message: '菜单代码最多20位', trigger: 'blur' },
          { validator: rule.FormValidate.Form().validateChinese, trigger: 'blur' },
          { validator: rule.FormValidate.Form().validateSpecialKey, trigger: 'blur' },
          { validator: rule.FormValidate.Form().validateNumber, trigger: 'blur' }
        ],
        sortId: [
          { validator: validateSortNumber, trigger: 'blur' }
        ],
        permToken: [
          { required: true, message: '请输入权限字符', trigger: 'blur' },
          { validator: rule.FormValidate.Form().validateChinese, trigger: 'blur' }
        ],
        path: [
          { validator: rule.FormValidate.Form().validateChinese, trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    dialogVisible: {
      get() {
        return this.dialogFormVisible
      },
      set(val) {
        this.$emit('form-visible', val)
      }
    }
  },
  methods: {
    onOpen() {
    },
    onClose() {
      this.$emit('form-visible')
    },
    close() {
      this.$emit('update:visible', false)
    },
    handleConfirm() {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          const param = this.dataForm.menuType === '1' ? this.dataForm : {
            parentId: this.dataForm.parentId,
            menuType: this.dataForm.menuType,
            menuName: this.dataForm.menuName,
            menuId: this.dataForm.menuId,
            permToken: this.dataForm.permToken,
            orderNum: this.dataForm.orderNum,
            status: this.dataForm.status
          }
          if (this.handleType === this.$global.HandleType.ADD) {
            add(param).then(() => {
              this.$message({
                type: 'success',
                message: '提交成功!'
              })
              this.$emit('search')
              this.close()
            })
          } else {
            edit(param).then(() => {
              this.$message({
                type: 'success',
                message: '修改成功!'
              })
              this.$emit('search')
              this.close()
            }).catch(() => {
            })
          }
        } else {
          return false
        }
      })
    },
    // 上级菜单选择后回显菜单代码
    selectChange(val) {
      this.dataForm.menuId = val
    }
  }
}
</script>

<style scoped>

</style>
