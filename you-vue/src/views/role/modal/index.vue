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
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="角色名称" prop="roleName">
              <el-input
                v-model.trim="dataForm.roleName"
                :maxlength="20"
                placeholder="请输入角色名称"
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
                :maxlength="50"
                :style="{width: '100%'}"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="24">
            <el-form-item
              label="权限"
              prop="permTokenList"
            >
              <el-tree
                ref="tree"
                class="tree-border"
                :data="data.data"
                show-checkbox
                node-key="permissions"
                :default-checked-keys="dataForm.permTokenList"
                :check-strictly="true"
                :props="defaultProps"
                @check-change="handleCheckChange()"
                @check="getCurrentNode"
              />
            </el-form-item>
            <div v-if="showTemp" style="height: 10000px;">用以解决火狐浏览器上出现的问题，这行不会显示在页面上</div>
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
import { add, edit } from '~/system/role'

export default {
  components: {},
  inheritAttrs: false,
  props: {
    modalType: {
      type: Symbol,
      required: true
    },
    rowData: {
      type: Object,
      required: false,
      default: () => {
        return {
          dataForm: {
            roleName: '',
            description: '',
            menuList: '',
            permTokenList: []
          }
        }
      }
    }
  },
  data() {
    return {
      data: {
        data: this.rowData.menuList
      },
      // eslint-disable-next-line vue/no-dupe-keys
      handleType: this.modalType,
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      dataForm: {
        roleId: this.rowData.roleId,
        roleName: this.rowData.roleName,
        description: this.rowData.description,
        permTokenList: this.rowData.permTokenList
      },
      rules: {
        roleName: [{ required: true, message: '请输入角色名称', trigger: 'blur' },
          { pattern: /^[\u4e00-\u9fa5a-zA-Z-z0-9]+$/, message: '不能输入特殊字符', trigger: 'change' }],
        permTokenList: [
          { required: true, message: '请至少选择一个权限' }
        ]
      },
      showTemp: true
    }
  },
  computed: {},
  watch: {},
  created() {
    const _this = this
    setTimeout(function() {
      _this.open()
    }, 0)
  },
  mounted() {},
  methods: {
    onOpen() {},
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
            add(this.dataForm).then(() => {
              this.$message({
                type: 'success',
                message: '提交成功!'
              })
              this.$emit('search')
              this.close()
            }).catch(() => {
              return false
            })
          }
          if (this.handleType === this.$global.HandleType.UPDATE) {
            // 同新增
            edit(this.dataForm).then(() => {
              this.$message({
                type: 'success',
                message: '提交成功!'
              })
              this.$emit('search')
              this.close()
            }).catch(() => {
              return false
            })
          }
        }
      })
    },
    open() {
      this.showTemp = false
    },
    getCurrentNode(data) {
      const node = this.$refs.tree.getNode(data)
      this.childNodes(node)
      this.parentNodes(node)
    },
    childNodes(node) {
      const len = node.childNodes.length
      for (let i = 0; i < len; i++) {
        node.childNodes[i].checked = node.checked
        this.childNodes(node.childNodes[i])
      }
    },
    parentNodes(node) {
      if (node.parent) {
        for (const key in node) {
          if (key === 'parent') {
            node[key].checked = true
            this.parentNodes(node[key])
          }
        }
      }
    },
    handleCheckChange() {
      const res = this.$refs.tree.getCheckedKeys()
      const arrDeptId = []
      res.forEach((item) => {
        arrDeptId.push(item)
      })
      this.dataForm.permTokenList = arrDeptId
    }
  }
}

</script>
<style>

/* tree border */
.tree-border {
    margin-top: 5px;
    border: 1px solid #e5e6e7;
    background: #FFFFFF none;
    border-radius:4px;
}
</style>
