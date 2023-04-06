<template>
  <div>
    <el-dialog class="info" v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose">
      <el-form
        ref="dataForm"
        :model="dataForm"
        label-position="left"
        :rules="rules"
        label-width="80px"
        style="overflow: hidden"
      >
        <el-table
          :key="mainTableKey"
          :data="pageData"
          style="width: 100%"
          :default-sort="{prop: 'date', order: 'descending'}"
          border
          fit
          highlight-current-row
        >
          <el-table-column
            prop="dictCode"
            label="字典代码"
          >
            <template slot-scope="scope">
              <el-form-item
                v-if="scope.row.isEdit"
                prop="dictCode"
                :rules="rules.dictCode"
              >
                <el-input v-model.trim="scope.row.dictCode" placeholder="请输入字典代码" @change="changeData(scope.row)" />
              </el-form-item>
              <span v-else>{{ scope.row.dictCode }}</span>
            </template>
          </el-table-column>
          >
          <el-table-column
            prop="dictValue"
            label="字典值"
          >
            <template slot-scope="scope">
              <el-form-item
                v-if="scope.row.isEdit"
                prop="dictValue"
              >
                <el-input v-model.trim="scope.row.dictValue" placeholder="请输入字典值" @change="changeData(scope.row)" />
              </el-form-item>
              <span v-else>{{ scope.row.dictValue }}</span>
            </template>
          </el-table-column>

          <el-table-column
            prop="status"
            label="状态"
          >
            <template slot-scope="scope">
              <el-form-item
                v-if="scope.row.isEdit"
                prop="status"
              >
                <el-select
                  v-model.trim="scope.row.statusName"
                  placeholder="请输入状态"
                  clearable
                  :style="{width: '100%'}"
                >
                  <el-option
                    v-for="item in enums.EnableStatusEnum"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
              <span v-else>{{ scope.row.statusName }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="sortId"
            label="排序号"
          >
            <template slot-scope="scope">

              <el-form-item
                v-if="scope.row.isEdit"
                prop="sortId"
              >
                <el-input v-model.trim="scope.row.sortId" placeholder="请输入排序号" @change="changeData(scope.row)" />
              </el-form-item>
              <span v-else>{{ scope.row.sortId }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="300">
            <template slot-scope="scope">
              <el-button v-if="!scope.row.isEdit" v-permission="['b:dict:infoEdit']" v-waves type="text" size="mini" icon="el-icon-edit" @click="edit(scope.row)">
                编辑
              </el-button>
              <el-button v-if="!scope.row.isEdit" v-permission="['b:dict:infoDelete']" v-waves type="text" size="mini" icon="el-icon-delete" @click="remove(scope.row)">
                删除
              </el-button>
              <el-button v-if="scope.row.isEdit" v-permission="['b:dict:infoAdd','b:dict:infoEdit']" type="success" size="small" @click="saveDictValue(scope.row)">
                保存
              </el-button>
              <el-button v-if="scope.row.isEdit" type="info" size="small" @click="cancel(scope.row,scope.$index)">
                取消
              </el-button>

            </template>
          </el-table-column>
        </el-table>
        <div class="block">
          <WrPagination
            v-if="showPage"
            :total="total"
            :search-form="searchForm"
            @pageChange="pageChange"
            @handleSizeChange="handleSizeChange"
            @slotChange="slotChange"
          />
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">

        <el-button v-waves type="primary" @click="add()">
          新增
        </el-button>
        <el-button v-waves plain @click="dictTableReload()">
          刷新
        </el-button>
        <el-button v-waves @click="close">
          关闭
        </el-button>

      </div>
    </el-dialog>
  </div>
</template>

<script>
import rule from '@/utils/rules' // 校验
import WrPagination from '@/components/Pagination'
import enums from '@/enum/enums'
import { addDictValue, listInfo, removeDictVale, updateDictValue } from '~/system/dict'

export default {
  name: 'Dict',
  components: {
    WrPagination
  },
  props: {
    rowData: {
      type: Object,
      required: false,
      default: () => {
        return {
          dataForm: {
            dictId: '',
            dictName: '',
            dictCode: '',
            dictValue: '',
            status: '',
            statusName: '',
            sortId: ''
          }
        }
      }
    }
  },

  data() {
    return {
      enums: enums,
      mainTableKey: 1,
      showPage: true,
      pageData: [],
      total: 0,
      searchForm: {
        current: 1,
        size: 10,
        orders: []
      },
      dataForm: {
        dictId: this.rowData.dictId,
        dictName: this.rowData.dictName,
        dictCode: this.rowData.dictCode,
        dictValue: this.rowData.dictValue,
        status: this.rowData.status,
        statusName: this.rowData.statusName,
        sortId: this.rowData.sortId
      },
      rules: {
        dictCode: [
          { required: true, message: '请输入字典代码', trigger: 'blur' },
          { max: 20, message: '字典代码最多20位', trigger: 'blur' },
          { validator: rule.FormValidate.Form().validateSpecialKey, trigger: 'blur' }
        ],
        dictValue: [
          { required: true, message: '请输入字典值', trigger: 'blur' },
          { max: 20, message: '字典值最多20位', trigger: 'blur' },
          { validator: rule.FormValidate.Form().validateSpecialKey, trigger: 'blur' }
        ],
        status: [
          { required: true, message: '请输入状态', trigger: 'blur' },
          { validator: rule.FormValidate.Form().validateNumber, trigger: 'blur' }
        ],
        sortId: [
          { required: true, message: '请输入排序', trigger: 'blur' }
        ]
      }
    }
  },

  computed: {},
  created() {
    this.getDict()
  },

  methods: {
    onOpen() {
    },
    onClose() {
      this.$emit('info-visible')
    },
    close() {
      this.$emit('update:visible', false)
      this.onClose()
    },
    add() {
      this.pageData.forEach(date => {
        if (date.isEdit || date.isAdd) {
          this.$message({
            type: 'error',
            message: '请完成当前编辑'
          })
          throw SyntaxError()
        }
      })
      this.pageData.unshift({
        dictCode: '',
        dictValue: '',
        status: '',
        sortId: '',
        isEdit: true,
        isAdd: true

      })
    },
    slotChange(data) {
      this.searchForm.orders = []
      if (data) {
        this.searchForm.orders.push(data)
      }
      this.getDict()
    },
    saveDictValue(row) {
      this.dataForm = Object.assign({}, this.dataForm, row)
      this.$refs.dataForm.validate((valid) => {
        if (!valid) {
          return
        } else {
          if (row.isAdd === false) {
            updateDictValue(this.dataForm).then(() => {
              this.$emit('updateDictValue')
              this.pageData.forEach(date => {
                if (date.dictId === row.dictId) {
                  date.isEdit = false
                  date.isAdd = false
                  this.mainTableKey = Math.random()
                }
              })
              this.getDict()
            })
          } else {
            addDictValue(this.dataForm).then(() => {
              this.$message({
                type: 'success',
                message: '保存成功!'
              })
              this.getDict()
              // this.$emit('saveDictValue')
              this.pageData.forEach(date => {
                if (date.dictCode === row.dictCode) {
                  date.isEdit = false
                  date.isAdd = false
                }
              })
            })
          }
        }
      })
    },
    remove(row) {
      this.$confirm('确定删除此条记录?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        removeDictVale(row.dictId).then(() => {
          this.getDict()
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },

    handleSizeChange(size) {
      this.searchForm.size = size
      this.getDict()
    },
    pageChange(current) {
      this.searchForm.current = current
      this.getDict()
    },
    dialogClose() {
      this.dialogVisible = false
    },
    changeData(row) {
      this.dataForm = Object.assign({}, this.dataForm, row)
    },
    getDict() {
      listInfo(this.rowData.dictName, this.searchForm).then(res => {
        const { records, total } = res.data
        this.pageData = records
        this.total = total
        this.pageData.forEach(row => {
          // 是否标记
          row['isEdit'] = false
          // 是否新增
          row['isAdd'] = false
        })
      }).catch(err => {
        this.$alert(err)
      })
    },
    edit(row) {
      this.pageData.forEach(date => {
        if (date.isEdit || date.isAdd) {
          this.$message({
            type: 'error',
            message: '请完成当前编辑'
          })
          throw SyntaxError()
        }
      })
      row.status = String(row.status)
      this.dataForm = Object.assign({}, this.dataForm, row)
      row['oldRow'] = JSON.parse(JSON.stringify(row))
      row.isEdit = true
      this.mainTableKey = Math.random()
    },

    dictTableReload() {
      this.getDict()
    },

    cancel(row, index) {
      // 如果是新增的数据
      if (row.isAdd) {
        this.pageData.splice(index, 1)
      } else {
        // 不是新增的数据  还原数据
        for (const i in row.oldRow) {
          row[i] = row.oldRow[i]
        }
      }
      this.mainTableKey = Math.random()
    }
  }
}
</script>

<style lang="scss" scoped>
.info >>>.el-form-item__content {
  margin-left: 0!important;
}
</style>
