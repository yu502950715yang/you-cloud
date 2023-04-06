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
        <el-col :span="24">
          <WrTable
            :table-form="tableForm"
          />
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>
<script>
import WrTable from '@/components/Table'

export default {
  components: { WrTable },
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
    const getTableData = _ => {
      const tableData = this.rowData.taskDetailList
      const tableDataTemp = []
      if (tableData && tableData.length > 0) {
        tableData.forEach((table) => {
          const tableTemp = {
            approveName: table.approveName,
            processName: table.processName,
            approveTime: table.approveTime,
            approvalOption: table.approval.approvalOption
          }
          if (table.approval.result === true) {
            tableTemp.result = '是'
          } else if (table.approval.result === false) {
            tableTemp.result = '否'
          } else {
            tableTemp.result = ''
          }
          tableDataTemp.push(tableTemp)
        })
      }
      return tableDataTemp
    }
    return {
      tableForm: {
        defaultSort: {
        },
        tableData: getTableData(),
        showSelection: false,
        operation: true,
        columnList: [
          {
            text: true,
            prop: 'processName',
            label: '审批环节'
          },
          {
            text: true,
            prop: 'approveName',
            label: '审批人'
          },
          {
            text: true,
            label: '是否同意',
            prop: 'result'
          },
          {
            text: true,
            prop: 'approvalOption',
            label: '审批意见'
          },
          {
            text: true,
            prop: 'approveTime',
            label: '审批时间'
          }
        ]
      },
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
