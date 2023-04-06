<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose">
      <el-form ref="dataForm" :model="dataForm" :rules="rules" size="medium" label-width="100px">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="字典名称" prop="dictName">
              <el-input
                v-model.trim="dataForm.dictName"
                placeholder="请输入字典名称"
                clearable
                :style="{width: '100%'}"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="描述" prop="description">
              <el-input
                v-model.trim="dataForm.description"
                placeholder="请输入描述"
                clearable
                :style="{width: '100%'}"
              />
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
export default {
  components: {},
  inheritAttrs: false,
  props: {
    rowData: {
      type: Object,
      required: false,
      default: () => {
        return {
          dataForm: {
            oldDictName: '',
            dictName: '',
            description: ''
          }
        }
      }
    }
  },
  data() {
    return {
      dataForm: {
        dictName: this.rowData.dictName,
        description: this.rowData.description,
        oldDictName: this.rowData.dictName
      },
      rules: {
        dictName: [{
          required: true,
          message: '请输入字典名称',
          trigger: 'blur'
        }],
        description: [{
          required: true,
          message: '请输入描述',
          trigger: 'blur'
        }]
      }
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
        if (!valid) {
          return
        } else {
          this.$emit('save', this.dataForm)
        }
      })
    }
  }
}

</script>
<style>
</style>
