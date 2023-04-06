<template>
  <div>
    <el-row :gutter="15">
      <el-form ref="dataForm" :model="dataForm" :rules="rules" size="medium" label-width="100px">
        <el-col :span="9">
          <el-form-item label="代码生成" prop="tableName">
            <el-select
              v-model.trim="dataForm.tableName"
              placeholder="请择选表名"
              clearable
              :style="{width: '100%'}"
            >
              <el-option
                v-for="(item, index) in tableNameOptions"
                :key="index"
                :label="item"
                :value="item"
                :disabled="item.disabled"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="保存路径" prop="parentPackage">
            <el-input
              v-model.trim="dataForm.parentPackage"
              clearable
              placeholder="保存路径 例如D:\project\baseproject_vue\api"
            />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item size="large">
            <el-button type="primary" @click="submitForm(dataForm)">提交</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
  </div>
</template>
<script>
import { generate, getTableNames } from '~/system/generate'

export default {
  components: {},
  props: [],
  data() {
    return {
      dataForm: {
        tableName: '',
        parentPackage: ''
      },
      rules: {
        tableName: [{
          required: true,
          message: '请择选表名',
          trigger: 'change'
        }],
        parentPackage: [{
          required: true,
          message: '请输入保存路径',
          trigger: 'change'
        }]
      },
      tableNameOptions: []
    }
  },
  computed: {},
  watch: {},
  created() {
    this.getTableNameList()
  },
  mounted() {},
  methods: {
    submitForm(data) {
      this.$refs['dataForm'].validate(valid => {
        if (!valid) return
        // TODO 提交表单
        alert(data)
        generate(data).then(() => {
          this.$message({
            type: 'success',
            message: '生成成功!'
          })
        }).catch(() => {
        })
      })
    },
    getTableNameList() {
      getTableNames().then(res => {
        this.tableNameOptions = res.data
        console.log(this.tableNameOptions)
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
