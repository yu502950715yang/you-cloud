<template>
  <el-tree-select
    ref="departmentTree"
    v-model="selectDepartment"
    :select-params="{
      multiple:false,
      placeholder: placeholder,
      clearable:clearable
    }"
    :tree-params="{
      checkStrictly: checkStrictly,
      data:treeData,
      clickParent: true,
      'expand-on-click-node': false,
      props: defaultProps
    }"
    :styles="styles"
    @input="selectChange"
  />
</template>

<script>

import { handleTree } from '@/utils/generator/ruoyi'

export default {
  name: 'DepartmentTree',
  props: {
    department: {
      type: String,
      required: false,
      default: undefined
    },
    placeholder: {
      type: String,
      required: false,
      default: '请选择'
    },
    // 多选时，严格遵循父子不互相关联
    checkStrictly: {
      type: Boolean,
      required: false,
      default: false
    },
    clearable: {
      type: Boolean,
      required: false,
      default: false
    },
    departments: {
      type: Array,
      required: false,
      default: () => undefined
    },
    departmentList: {
      type: Array,
      required: true
    },
    defaultSelect: {
      type: String,
      required: false,
      default: ''
    },
    defaultProps: {
      type: Object,
      required: false,
      default: () => {
        return {
          label: 'name',
          value: 'id'
        }
      }
    }
  },
  data() {
    return {
      treeData: handleTree(this.departmentList, this.defaultProps.value),
      styles: {
        width: '100%'
      },
      selectDepartment: this.defaultSelect
    }
  },
  computed: {},
  watch: {
    treeData() {
      this.$refs.departmentTree.treeDataUpdateFun(this.treeData)
    }
  },
  created() {},
  mounted() {},
  methods: {
    selectChange(val) {
      this.$emit('selectChange', val)
      this.$emit('input', val)
    }
  }
}
</script>
