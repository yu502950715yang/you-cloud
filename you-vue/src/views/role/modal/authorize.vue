<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose">
      <el-form
        ref="dataForm"
        :model="dataForm"
        size="medium"
        label-width="100px"
        label-position="right"
      >
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="角色名称">
              <span>{{ dataForm.roleName }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="描述">
              <span>{{ dataForm.description }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="用户" prop="userIdList">
              <template>
                <el-transfer
                  v-model="dataForm.userIdList"
                  filterable
                  :titles="['待选择', '已选择 ']"
                  :data="dataForm.userList"
                >

                  <span slot-scope="{ option }">{{ option.label }}
                    <span class="forbidden">{{ option.comment }}</span>
                  </span>

                </el-transfer>
              </template>
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
import { addAuthorize } from '~/system/role'
export default {
  components: {},
  inheritAttrs: false,
  props: {
    rowData: {
      type: Object,
      required: true
    }
  },
  data() {
    const getUserIds = _ => {
      const userIdList = this.rowData.userIdList
      const userIds = []
      if (userIdList && userIdList.length > 0) {
        userIdList.forEach((user) => {
          userIds.push(user.userId)
        })
      }
      return userIds
    }
    const getUserList = _ => {
      const userAllList = this.rowData.userList
      const userList = []
      if (userAllList && userAllList.length > 0) {
        userAllList.forEach((user) => {
          if (user.status === 0) {
            userList.push({
              label: user.username,
              key: user.userId,
              comment: '禁用'
            })
          } else {
            userList.push({
              label: user.username,
              key: user.userId
            })
          }
        })
      }
      return userList
    }
    return {
      dataForm: {
        roleId: this.rowData.role.roleId,
        roleName: this.rowData.role.roleName,
        description: this.rowData.role.description,
        userIdList: getUserIds(),
        userList: getUserList()
      }
    }
  },
  computed: {},
  watch: {},
  created() {
  },
  mounted() {},
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
      this.$refs['dataForm'].validate(valid => {
        if (!valid) return
        else {
          addAuthorize(this.dataForm).then(() => {
            this.$message({
              type: 'success',
              message: '提交成功!'
            })
            this.$emit('search')
            this.close()
          }).catch(() => {
          })
        }
      })
    }
  }
}

</script>
<style>
.forbidden {
  color: #C31E18;
  font-size: 13px;
  background-color: rgba(255, 204, 204, 1)
}
</style>
