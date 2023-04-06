<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose">
      <el-form
        ref="dataForm"
        :model="dataForm"
        size="medium"
        label-width="100px"
        label-position="right"
      >
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="流程Key" prop="key">
              <el-input
                v-model.trim="dataForm.key"
                :maxlength="20"
                placeholder="请输入流程Key"
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
import { deploy } from '~/system/processDeployment'

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
          }
        }
      }
    }
  },
  data() {
    return {
      dataForm: {
        key: ''
      }
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
          deploy(this.dataForm.key).then(() => {
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
      })
    },
    open() {
      this.showTemp = false
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
