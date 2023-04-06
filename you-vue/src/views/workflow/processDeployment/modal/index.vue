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
        <div v-for="(item,index) in deployPersonList" :key="index">
          <el-row :gutter="30">
            <el-col :span="12">
              <el-form-item label="流程节点">
                <span>{{ item.taskName }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="30">
            <el-col :span="24">
              <el-form-item label="审批人" prop="personList">
                <el-cascader
                  v-model="dataForm.boList[index].personList"
                  :options="approverList"
                  :props="props"
                  :show-all-levels="false"
                  :style="{width: '100%'}"
                  clearable
                />
              </el-form-item>
            </el-col>
          </el-row>
          <div style="width: 100%;height: 1px;background: #aaa" />
        </div>
      </el-form>
      <div slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getApproverList, saveDeployPerson } from '~/system/processDeployment'

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
            deployPersonList: []
          }
        }
      }
    }
  },
  data() {
    const getUserIds = _ => {
      const tempDeployPersonList = this.rowData.deployPersonList
      const newDeployPersonList = []
      if (tempDeployPersonList && tempDeployPersonList.length > 0) {
        tempDeployPersonList.forEach((tempDeployPerson) => {
          const wfTaskPersonList = tempDeployPerson.wfTaskPersonList
          const personList = []
          for (let i = 0; i < wfTaskPersonList.length; i++) {
            personList.push(wfTaskPersonList[i].person)
          }
          const userTemp = {
            taskDefKey: tempDeployPerson.taskDefKey,
            personList: personList
          }
          newDeployPersonList.push(userTemp)
        })
      }
      return newDeployPersonList
    }
    return {
      dataForm: {
        boList: getUserIds()
      },
      props: {
        label: 'name',
        value: 'id',
        multiple: true,
        leaf: 'leaf',
        emitPath: false
      },
      deployPersonList: this.rowData.deployPersonList,
      approverList: []
    }
  },
  computed: {},
  watch: {},
  created() {
    getApproverList().then((res) => {
      this.approverList = res.data
      this.setDeployPersonList(this.approverList)
    })
  },
  mounted() {},
  methods: {
    setDeployPersonList(data) {
      for (let i = 0; i < data.length; i++) {
        if (data[i].children && data[i].children.length > 0) {
          this.setDeployPersonList(data[i].children)
        } else {
          var tempData = data[i]
          data[i] = {
            id: tempData.id,
            name: tempData.name
          }
        }
      }
    },
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
          saveDeployPerson(this.dataForm.boList).then(() => {
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
