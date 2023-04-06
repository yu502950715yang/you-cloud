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
          <el-col :span="24">
            <el-form-item label="标题" prop="title">
              <el-input v-model.trim="dataForm.title" placeholder="请输入标题" :maxlength="20" clearable :style="{width: '100%'}" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="内容" prop="words">
              <el-input v-model.trim="dataForm.words" :rows="4" type="textarea" placeholder="请输入内容" :maxlength="500" clearable :style="{width: '100%'}" />
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
import { add, edit } from '~/system/lexicon'
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
            id: '',
            title: '',
            words: ''
          }
        }
      }
    }
  },
  data() {
    return {
      handleType: this.modalType,
      dataForm: {
        id: this.rowData.id,
        title: this.rowData.title,
        words: this.rowData.words
      },
      rules: {
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        words: [{ required: true, message: '请输入内容', trigger: 'blur' }]
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
