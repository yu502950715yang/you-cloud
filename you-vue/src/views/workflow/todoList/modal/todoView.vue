<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @closed="onClose">
      <img style="width: 100%;" :src="dataForm.url" alt="">
      <div slot="footer">
        <el-button @click="close">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  components: {
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
            processKey: '',
            version: ''
          }
        }
      }
    }
  },
  data() {
    return {
      dataForm: {
        url: '/api/workflow/png?processKey=' + this.rowData.procKey + '&version=' + this.rowData.procVersion
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
    }
  }
}

</script>
<style>
</style>
