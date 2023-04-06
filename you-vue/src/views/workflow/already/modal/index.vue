<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose">
      <leaveProcess v-if="dataForm.procKey == 'leaveProcess'" ref="child" :row-data="rowData" />
      <div slot="footer">
        <el-button @click="close">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { addAuthorize } from '~/system/role'
import leaveProcess from './leaveProcess'
export default {
  components: { leaveProcess },
  inheritAttrs: false,
  props: {
    rowData: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      dataForm: {
        applicantName: this.rowData.formData.applicantName,
        days: this.rowData.formData.days,
        reason: this.rowData.formData.reason,
        procKey: this.rowData.procKey
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
