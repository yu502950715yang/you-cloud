<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose">
      <el-form ref="dataForm" :model="data" label-position="right" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="标题">
              <span>{{ data.title }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="内容">
              <span>{{ data.content }}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="close">
          取消
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

export default {
  title: 'Handle',
  props: {
    rowData: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      data: {
        id: this.rowData.id,
        title: this.rowData.title,
        content: this.rowData.content
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
    }
  }
}
</script>

<style scoped>
.authorize >>> .el-input__inner {
  border-color: #DCDFE6 !important;
}
</style>
