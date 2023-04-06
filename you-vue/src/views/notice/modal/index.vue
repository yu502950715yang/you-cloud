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
          <el-col :span="24">
            <el-form-item label="标题" prop="title">
              <el-input
                v-model.trim="dataForm.title"
                :maxlength="20"
                placeholder="请输入标题"
                clearable
                :style="{width: '100%'}"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="24">
            <el-form-item label="内容" prop="content">
              <el-input
                v-model.trim="dataForm.content"
                type="textarea"
                placeholder="请输入内容"
                clearable
                :rows="4"
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
import { add, edit } from '~/system/notice'

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
            title: '',
            content: ''
          }
        }
      }
    }
  },
  data() {
    return {
      data: {
        data: this.rowData
      },
      // eslint-disable-next-line vue/no-dupe-keys
      handleType: this.modalType,
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      dataForm: {
        id: this.rowData.id,
        title: this.rowData.title,
        content: this.rowData.content
      },
      rules: {
        title: [{ required: true, message: '请输入标题名称', trigger: 'blur' },
          { max: 50, message: '不能输入特殊字符', trigger: 'change' }],
        content: [
          { required: true, max: 500, message: '请输入内容' }
        ]
      },
      showTemp: true
    }
  },
  computed: {},
  watch: {},
  created() {
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
