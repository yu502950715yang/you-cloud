<template>
  <div>
    <el-form
      ref="dataForm"
      :model="dataForm"
      size="medium"
      label-width="100px"
      label-position="right"
    >
      <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item label="申请人">
            <span>{{ rowData.formData.applicantName }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="请假天数">
            <span>{{ rowData.formData.days }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="15">
        <el-col :span="24">
          <el-form-item label="请假原因">
            <span>{{ rowData.formData.reason }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item label="当前节点">
            <span>{{ rowData.taskVo.taskName }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="userList.length > 0" :gutter="15">
        <el-col :span="24">
          <el-form-item label="下级审批人" prop="approvalOption">
            <el-select
              v-model.trim="dataForm.approval.approver"
              placeholder="请选择审批人"
              multiple
              clearable
              :style="{width: '100%'}"
            >
              <el-option
                v-for="item in userList"
                :key="item.username"
                :label="item.realName"
                :value="item.username"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="15">
        <el-col :span="24">
          <el-form-item label="审批意见" prop="reason">
            <el-input
              v-model.trim="dataForm.approval.approvalOption"
              type="textarea"
              placeholder="请输入请假原因"
              :autosize="{minRows: 4, maxRows: 4}"
              :style="{width: '100%'}"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item label="是否同意" prop="reason">
            <el-radio v-model="dataForm.approval.result" label="true">是</el-radio>
            <el-radio v-model="dataForm.approval.result" label="false">否</el-radio>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>
<script>
import { getTaskApprover } from '~/system/leaveProcess'

export default {
  components: {},
  inheritAttrs: false,
  props: {
    rowData: {
      type: Object,
      required: true,
      default: () => {
        return {
          dataForm: {
          }
        }
      }
    }
  },
  data() {
    return {
      dataForm: {
        approval: {
          result: '',
          approver: null,
          approvalOption: ''
        },
        taskId: this.rowData.taskId
      },
      userList: []
    }
  },
  computed: {},
  watch: {},
  created() {
    getTaskApprover('leaveProcess', this.dataForm.taskId).then((res) => {
      this.userList = res.data
    })
  },
  mounted() {
  },
  methods: {
    onOpen() {
      this.dataForm = this.rowData
    },
    onClose() {
      this.$emit('form-visible')
    },
    close() {
      this.$emit('update:visible', false)
    },
    handleValidate() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          console.log(this.dataForm)
          this.$emit('submit', this.dataForm)
        }
      })
    }
  }
}

</script>
<style>
</style>
