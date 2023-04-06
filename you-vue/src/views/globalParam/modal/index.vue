<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose">
      <el-form ref="dataForm" :model="dataForm" :rules="rules" size="medium" label-width="100px">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="参数名" prop="paramName">
              <div>{{ dataForm.paramName }}</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="参数值" prop="paramValue">
              <el-input v-model.trim="dataForm.paramValue" placeholder="请输入参数值" clearable
                        :style="{width: '100%'}"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="描述" prop="paramDescription">
              <el-input v-model.trim="dataForm.paramDescription" placeholder="请输入描述" clearable
                        :style="{width: '100%'}"></el-input>
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
    inheritAttrs: false,
    components: {},
    props: {
      rowData: {
        type: Object,
        required: false,
        default: () => {
          return {
            dataForm: {
              paramName: '',
              paramValue: '',
              paramDescription: '',
            }
          }
        }
      }
    },
    data() {
      return {
        dataForm: {
          paramName: this.rowData.paramName,
          paramValue: this.rowData.paramValue,
          paramDescription: this.rowData.paramDescription,
        },
        rules: {
          paramName: [{
            required: true,
            message: '请输入参数名',
            trigger: 'blur'
          }],
          paramValue: [{
            required: true,
            message: '请输入参数值',
            trigger: 'blur'
          }],
          paramDescription: [{
            required: true,
            message: '请输入描述',
            trigger: 'blur'
          }],
        },
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
      },
      handleConfirm() {
        this.$refs['dataForm'].validate(valid => {
          if (!valid) {
            return
          } else {
            this.$emit('save', this.dataForm)
          }
          this.close()
        })
      },
    }
  }

</script>
<style>
</style>
