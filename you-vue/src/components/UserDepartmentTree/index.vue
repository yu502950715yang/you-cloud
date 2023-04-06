<template>
  <el-cascader
    v-if="flag!=='人员'"
    ref="userDepartmentTree"
    v-model="userDepartmentData"
    :options="data"
    :props="{
      label: 'name',
      value: 'id',
      children: 'children',
      checkStrictly: true,
      emitPath: false,
      multiple:multiple
    }"
    :filterable="true"
    clearable
    style="width: 100%"
    @change="handleChange"
  />
  <el-select v-else v-model="userDepartmentData" placeholder="请选择" style="width: 100%" :multiple="multiple" @change="handleChange">
    <el-option v-for="item in data" :key="item.userId" :label="item.userName" :value="item.userId" />
  </el-select>

</template>

<script>
import { getTreeData } from '~/userDepartmentTree'
import { list } from '~/system/department'
import { getDepartmentAndUser } from '~/system/user-department'
export default {
  name: 'UserDepartmentTree',
  props: {
    flag: {
      type: String,
      required: true
    },
    result: {
      type: String,
      required: true
    },
    multiple: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      data: [],
      userDepartmentData: this.result
    }
  },
  mounted() {
    if (this.flag === '人员部门') {
      Promise.all([getDepartmentAndUser(), list()]).then(([userList, departmentList]) => {
        this.data = getTreeData(userList, departmentList)
      })
    }
    if (this.flag === '部门') {
      list().then(res => {
        this.data = getTreeData(undefined, res)
      })
    }
    if (this.flag === '人员') {
      getDepartmentAndUser().then(res => {
        this.data = res.data
      })
    }
  },
  methods: {
    handleChange(value) {
      if (this.flag !== '人员') {
        this.$emit('change', this.$refs.userDepartmentTree.getCheckedNodes()[0].data.trueId)
      } else {
        this.$emit('change', value)
      }
    }
  }
}
</script>

<style scoped>
</style>
