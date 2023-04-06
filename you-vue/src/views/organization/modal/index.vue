<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose">
      <el-form ref="dataForm" :model="dataForm" :rules="rules" size="medium" label-width="100px">
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="上级部门" prop="parentId">
              <select-tree v-model="dataForm.parentId" :default-select="defaultSelect" :department-list="rowData.organizationList" :default-props="defaultProps" clearable placeholder="选择上级部门" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="部门名称" prop="organizationName">
              <el-input
                v-model.trim="dataForm.organizationName"
                placeholder="请输入部门名称"
                clearable
                :style="{width: '100%'}"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序" prop="sortId">
              <el-input v-model.trim="dataForm.sortId" placeholder="请输入排序" clearable :style="{width: '100%'}" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="部门状态" prop="status">
              <el-radio-group v-model="dataForm.status" size="medium">
                <el-radio
                  v-for="(item, index) in enums.organizationStatusEnum"
                  :key="index"
                  :label="item.value"
                  :disabled="item.disabled"
                >{{ item.label }}</el-radio>
              </el-radio-group>
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
import rule from '@/utils/rules' // 校验
import SelectTree from '@/components/SelectTree'
import enums from '@/enum/enums'

export default {
  components: {
    SelectTree
  },
  inheritAttrs: false,
  props: {
    rowData: {
      type: Object,
      required: false,
      default: () => {
        return {
          dataForm: {
            organizationId: '',
            parentId: '',
            organizationName: '',
            sortId: '',
            status: ''
          }
        }
      }
    }
  },
  data() {
    return {
      enums: enums,
      // 下拉树默认选中
      defaultSelect: this.rowData.parentId ? this.rowData.parentId.toString() : '',
      dataForm: {
        organizationId: this.rowData.organizationId,
        parentId: this.rowData.parentId,
        organizationName: this.rowData.organizationName,
        sortId: this.rowData.sortId,
        status: this.rowData.status
      },
      defaultProps: {
        label: 'organizationName',
        value: 'organizationId'
      },
      rules: {
        parentId: [{ required: true, message: '请输入上级部门', trigger: 'blur' }],
        organizationName: [
          { required: true, message: '请输入部门名称', trigger: 'blur' },
          { validator: rule.FormValidate.Form().validateSpecialKey, trigger: 'blur' }
        ],
        sortId: [
          { required: true, message: '请输入排序', trigger: 'blur' },
          { validator: rule.FormValidate.Form().validateNumber, trigger: 'blur' }
        ]
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
