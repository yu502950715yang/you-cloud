<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose">
      <el-form ref="dataForm" :model="data" :rules="rules" label-position="right" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户姓名">
              <span>{{ data.realName }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="登录名称">
              <span>{{ data.username }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="电子邮箱">
              <span>{{ data.emailAddress }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话号码">
              <span>{{ data.phone }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部门">
              <span>{{ data.organizationName }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="角色" prop="roleIds">
              <template>
                <el-transfer
                  v-model="data.roleIds"
                  filterable
                  :titles="['待选择','已选择']"
                  :data="data.roleList"
                />
              </template>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="close">
          取消
        </el-button>
        <el-button type="primary" @click="handleConfirm">
          提交
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import enums from '@/enum/enums'
import { updateAuthorization } from '~/system/user-authorization'

export default {
  realName: 'Handle',
  props: {
    rowData: {
      type: Object,
      required: true
    }
  },
  data() {
    const getRoleIds = _ => {
      const roleList = this.rowData.roleList
      const roleIds = []
      roleList.forEach((role) => {
        roleIds.push(role.roleId)
      })
      return roleIds
    }
    const generateData = _ => {
      const roleAllList = this.rowData.roleAllList
      const roleList = []
      roleAllList.forEach((role) => {
        roleList.push({
          label: role.roleName,
          key: role.roleId
        })
      })
      return roleList
    }
    return {
      userEnableEnum: enums.userEnableEnum,
      dataPermissionEnum: enums.dataPermissionEnum,
      systems: this.rowData.systems,
      data: {
        userId: this.rowData.user.userId,
        username: this.rowData.user.username,
        realName: this.rowData.user.realName,
        emailAddress: this.rowData.user.emailAddress,
        phone: this.rowData.user.phone,
        organizationName: this.rowData.user.organizationName,
        status: this.rowData.user.status,
        roleIds: getRoleIds(),
        secretLevelCode: this.rowData.secretLevelCode,
        dataPermissions: [],
        roleList: generateData()
      },
      rules: {
        permission: [{ required: true, message: '请选择数据权限', trigger: ['blur', 'change'] }],
        department: [{ type: 'array', required: true, message: '请选择指定部门', trigger: ['blur', 'change'] }]
      },
      value: []
    }
  },
  computed: {},
  methods: {
    onOpen() {
      this.dataForm = Object.assign(this.dataForm, this.rowData.role) // copy obj
    },
    onClose() {
      this.$emit('form-visible')
    },
    close() {
      this.$emit('update:visible', false)
    },
    handleConfirm() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          updateAuthorization(this.data).then(() => {
            this.$message({
              type: 'success',
              message: '提交成功!'
            })
            this.$emit('search')
            this.close()
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.authorize >>> .el-input__inner {
  border-color: #DCDFE6 !important;
}
</style>
