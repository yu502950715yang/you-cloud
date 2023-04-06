<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @closed="onClose">
      <leaveProcess v-if="dataForm.procKey == 'leaveProcess'" ref="child" :row-data="rowData" @submit="handleSubmit" />
      <div slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import leaveProcess from './leaveProcess'
import { complete } from '~/system/todoList'
export default {
  components: {
    leaveProcess
  },
  inheritAttrs: false,
  props: {
    rowData: {
      type: Object,
      required: true,
      default: () => {
        return {
          dataForm: {
            procKey: ''
          }
        }
      }
    }
  },
  data() {
    return {
      dataForm: {
        procKey: this.rowData.procKey
      }
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
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
    handleConfirm() {
      this.$refs.child.handleValidate()
    },
    handleSubmit(data) {
      complete(data).then(res => {
        this.$message({
          type: 'success',
          message: '提交成功!'
        })
        this.$emit('search')
        this.close()
      }).catch(() => {
      })
    }
  }
}

</script>
<style>
</style>
