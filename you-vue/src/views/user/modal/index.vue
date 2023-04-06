<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose">
      <el-form
        ref="dataForm"
        :model="dataForm"
        :rules="rules"
        size="medium"
        label-width="100px"
        label-position="right"
      >
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="用户姓名" prop="realName">
              <el-input v-model.trim="dataForm.realName" placeholder="请输入用户姓名" :maxlength="20" clearable :style="{width: '100%'}" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="登录名称" prop="username">
              <el-input v-model.trim="dataForm.username" placeholder="请输入登录名称" clearable :style="{width: '100%'}" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="modalType === $global.HandleType.ADD" :gutter="15" :span="12">
          <el-col :span="12">
            <el-form-item label="密码" prop="password">
              <el-input v-model.trim="dataForm.password" type="password" placeholder="请输入密码" :maxlength="16" clearable :style="{width: '100%'}" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="确认密码" prop="passwordAgain">
              <el-input
                v-model.trim="dataForm.passwordAgain"
                type="password"
                placeholder="请输入确认密码"
                :maxlength="16"
                clearable
                :style="{width: '100%'}"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="电子邮箱" prop="emailAddress">
              <el-input v-model.trim="dataForm.emailAddress" placeholder="请输入电子邮箱" :maxlength="100" clearable :style="{width: '100%'}" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话号码" prop="phone">
              <el-input v-model.trim="dataForm.phone" placeholder="请输入电话号码" :maxlength="11" clearable :style="{width: '100%'}" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="部门" prop="organizationId">
              <select-tree v-model="dataForm.organizationId" :default-select="defaultSelect" :department-list="rowData.organizationList" :default-props="defaultProps" clearable placeholder="选择部门" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { isUsername } from '../../../utils/validate'
import rule from '@/utils/rules' // 校验
import SelectTree from '@/components/SelectTree'
import md5 from '_md5@2.3.0@md5'
import { add, edit } from '~/system/user'
export default {
  components: {
    SelectTree
  },
  inheritAttrs: false,
  props: {
    modalType: {
      type: Symbol,
      required: false
    },
    rowData: {
      type: Object,
      required: true,
      default: () => {
        return {
          dataForm: {
            realName: '',
            username: '',
            password: '',
            passwordAgain: '',
            emailAddress: '',
            phone: '',
            organizationName: '',
            organizationId: '',
            userId: '',
            organizationList: []
          }
        }
      }
    }
  },
  data() {
    const validatePass = (rule, value, callback) => {
      if (this.dataForm.passwordAgain !== '') {
        this.$refs.dataForm.validateField('passwordAgain')
      }
      callback()
    }
    const validatePassAgain = (rule, value, callback) => {
      if (value !== this.dataForm.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    const validateUsername = (rule, value, callback) => {
      if (!isUsername(value)) {
        callback(new Error('登录名称最多20位，只能输入数字和字母'))
      }
      callback()
    }
    return {
      // 下拉树默认选中
      defaultSelect: this.rowData.organizationId ? this.rowData.organizationId.toString() : '',
      handleType: this.modalType,
      dataForm: {
        realName: this.rowData.realName,
        username: this.rowData.username,
        password: this.rowData.password,
        passwordAgain: this.rowData.passwordAgain,
        emailAddress: this.rowData.emailAddress,
        organizationName: this.rowData.organizationName,
        organizationId: this.rowData.organizationId,
        phone: this.rowData.phone,
        userId: this.rowData.userId
      },
      defaultProps: {
        label: 'organizationName',
        value: 'organizationId'
      },
      rules: {
        realName: [{ required: true, message: '请输入用户姓名', trigger: 'blur' }],
        username: [{ required: true, message: '请输入登录名称', trigger: 'blur' },
          { validator: validateUsername, trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 16, message: '密码长度6至16位', trigger: 'blur' },
          { validator: validatePass, trigger: 'blur' }],
        passwordAgain: [{ required: true, message: '请输入确认密码', trigger: 'blur' },
          { validator: validatePassAgain, trigger: ['blur', 'blur'] }],
        phone: [{ required: false, message: '请输入电话号码', trigger: 'blur' },
          { pattern: /^[0-9]+\.{0,1}[0-9]*$/, message: '请输入正确的电话号码', trigger: 'blur' }
        ],
        emailAddress: [{ required: false, message: '请输入电子邮箱', trigger: 'blur' },
          { pattern: /^(\w+([-.][A-Za-z0-9]+)*){3,18}@\w+([-.][A-Za-z0-9]+)*\.\w+([-.][A-Za-z0-9]+)*$/, message: '请输入正确的电子邮箱', trigger: 'blur' }
        ],
        organizationId: [
          { required: true, message: '请输入部门', trigger: 'blur' },
          { validator: rule.FormValidate.Form().validateSpecialKey, trigger: 'blur' }
        ]
      }
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    onOpen() {
    },
    onClose() {
      this.$emit('form-visible')
    },
    close() {
      this.$emit('update:visible', false)
      this.onClose()
    },
    handleConfirm() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          if (this.handleType === this.$global.HandleType.ADD) {
            this.dataForm.password = md5(this.dataForm.password)
            add(this.dataForm).then(res => {
              this.$message({
                type: 'success',
                message: '提交成功!'
              })
              this.$emit('search')
              this.close()
            }).catch(() => {
            })
          }
          if (this.handleType === this.$global.HandleType.UPDATE) {
            edit(this.dataForm).then(res => {
              this.$message({
                type: 'success',
                message: '提交成功!'
              })
              this.$emit('search')
              this.close()
            })
          }
        }
      })
    }
  }
}

</script>
<style>
</style>
