<template>
  <div>
    <el-form ref="dataForm" :model="dataForm" :rules="rules" size="medium" label-width="100px">
      <el-form-item label="请假天数" prop="days">
        <el-input v-model.trim="dataForm.days" placeholder="请输入请假天数" clearable :style="{width: '100%'}" />
      </el-form-item>
      <el-form-item label="请假原因" prop="reason">
        <el-input
          v-model.trim="dataForm.reason"
          type="textarea"
          placeholder="请输入请假原因"
          :autosize="{minRows: 4, maxRows: 4}"
          :style="{width: '100%'}"
        />
      </el-form-item>
      <el-form-item label="审批人" prop="approver">
        <el-select
          v-model.trim="dataForm.approver"
          placeholder="请选择审批人"
          multiple
          clearable
          :style="{width: '100%'}"
        ><el-option
          v-for="item in userList"
          :key="item.username"
          :label="item.realName"
          :value="item.username"
        />
        </el-select>
      </el-form-item>
      <el-form-item size="large">
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { add, getTaskApprover } from '~/system/leaveProcess'
export default {
  components: {},
  props: {
    rowData: {
      type: Object,
      required: false,
      default: () => {
        return {
          dataForm: {
            days: '',
            reason: '',
            approver: ''
          }
        }
      }
    }
  },
  data() {
    return {
      dataForm: {
        days: this.rowData.days,
        reason: this.rowData.reason,
        approver: this.rowData.approver
      },
      rules: {
        days: [{
          required: true,
          message: '请输入请假天数',
          trigger: 'blur'
        }],
        reason: [{
          required: true,
          message: '请输入请假原因',
          trigger: 'blur'
        }]
      },
      userList: []
    }
  },
  computed: {},
  watch: {},
  created() {
    getTaskApprover('leaveProcess', '').then((res) => {
      this.userList = res.data
    })
  },
  mounted() {},
  methods: {
    submitForm() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          add(this.dataForm).then(() => {
            this.$message({
              type: 'success',
              message: '提交成功!'
            })
            this.$emit('search')
            this.close()
          }).catch(() => {
            return false
          })
        }
      })
    },
    resetForm() {
      this.$refs['dataForm'].resetFields()
    }
  }
}

</script>
<style>
</style>
