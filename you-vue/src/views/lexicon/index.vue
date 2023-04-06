<template>
  <div class="app-container">
    <z-card style="height: auto">
      <el-form ref="searchForm" class="searchForm" :model="searchForm" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="6" class="form-item-height">
            <el-form-item label="词库标题">
              <el-input v-model.trim="searchForm.title" clearable size="mini" placeholder="词库标题" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <div class="form-item-btnGroup">
              <el-button class="filter-item" type="primary" icon="el-icon-search" size="mini" @click="search">
                搜索
              </el-button>
              <el-button
                size="mini"
                class="filter-item"
                style="margin-left: 10px;"
                icon="el-icon-refresh"
                @click="refresh"
              >
                重置
              </el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>
    </z-card>
    <z-card style="overflow: hidden">
      <div>
        <div class="table-item-btnGroup">
          <el-button
            v-permission="['b:user:add']"
            size="mini"
            class="filter-item"
            type="primary"
            plain
            icon="el-icon-plus"
            @click="showAdd"
          >新增
          </el-button>
        </div>
        <div class="block">
          <el-table
            :data="tableForm.tableData"
            style="width: 100%"
            :default-sort="{prop: 'date', order: 'descending'}"
            border
            fit
            highlight-current-row
            :stripe="true"
          >
            <el-table-column
              label="公开词库"
            >
              <template slot-scope="scope">
                <div style="margin-left: 10px;font-size: 16px;color: #000000d9">{{ scope.row.title }}</div>
                <div
                  :ref="`community_${scope.$index}`"
                  class="community-words"
                  :class="{'more-line':scope.row.domHeight > 100 && !scope.row.open}"
                  style="margin-left: 10px;color: #00000073"
                >{{ scope.row.words }}</div>
                <div
                  v-show="scope.row.domHeight > 100"
                  class="toggle-fold"
                  @click="toggleFoldFn(scope.row)"
                >
                  {{ scope.row.open ? '收起' : '展开' }}
                </div>
                <div>
                  <span style="margin-left: 10px;color: #00000073">{{ scope.row.createTime }}</span>
                  <el-button
                    icon="el-icon-edit"
                    size="small"
                    style="margin-left: 10px;"
                    type="text"
                    @click="handleUpdate(scope.row)"
                  >编辑</el-button>
                  <el-button
                    icon="el-icon-delete"
                    size="small"
                    style="margin-left: 10px;"
                    type="text"
                    @click="remove(scope.row)"
                  >删除</el-button>
                </div>
              </template>
            </el-table-column>
          </el-table>
          <WrPagination
            :page-form="searchForm.page"
            :total="pageForm.total"
            @pageChange="pageChange"
            @handleSizeChange="handleSizeChange"
          />
        </div>
      </div>
    </z-card>
    <add-or-edit
      v-if="modal.dialogFormVisible"
      :visible.sync="modal.dialogFormVisible"
      :title="modal.dialogOperate"
      :modal-type="modal.handleType"
      :row-data="modal.data"
      @form-visible="formVisible"
      @search="search"
    />
  </div>
</template>

<script>
import { dataList, getLexiconById } from '~/system/lexicon'
import ZCard from '@/components/Cardarea'
import WrPagination from '@/components/Pagination'
import addOrEdit from './modal/index'
import { remove } from '~/system/lexicon' // 新增编辑

export default {
  name: 'User',
  components: {
    ZCard,
    addOrEdit,
    WrPagination
  },
  directives: {
  },
  data() {
    return {
      pageForm: { // 分页数据集合
        total: 0,
        showPage: false
      },
      tableForm: {
        tableData: []
      },
      searchForm: { // 搜索框数据集合
        page: {
          current: 1,
          size: 10,
          orders: []
        },
        title: ''
      },
      modal: { // 表单数据集合
        dialogOperate: '', // 控制弹窗的title
        dialogFormVisible: false, // 控制新增编辑弹窗显示隐藏
        handleType: Symbol(), // 控制新增编辑弹窗的类型：add为新增，update为编辑
        data: {}
      }
    }
  },
  created() {
    this.search()
  },
  methods: {
    // 展示新增弹窗
    showAdd(row) {
      this.modal.handleType = this.$global.HandleType.ADD
      this.modal.dialogOperate = '新增'
      this.modal.dialogFormVisible = true
    },
    // 展示编辑弹窗
    handleUpdate(row) {
      getLexiconById(row.id).then(res => {
        this.modal.data = Object.assign({}, res.data.lexicon) // copy obj
        this.modal.handleType = this.$global.HandleType.UPDATE
        this.modal.dialogOperate = '编辑'
        this.modal.dialogFormVisible = true
      })
    },
    remove(row) {
      this.$confirm('确定删除标题为"' + row.title + '"的数据项?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        remove(row.id).then(() => {
          this.search()
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch(() => {
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    handleSizeChange(size) {
      this.searchForm.page.size = size
      this.getDataList()
    },
    formVisible() {
      this.modal.data = {}
      this.dialogFormVisible = false
    },
    search() {
      this.searchForm.page.current = 1
      this.getDataList()
    },
    slotChange(data) {
      this.searchForm.page.orders = []
      if (data) {
        this.searchForm.page.orders.push(data)
      }
      this.search()
    },
    refresh() {
      this.searchForm = {
        page: {
          current: 1,
          size: 10,
          orders: []
        },
        username: '',
        realName: '',
        status: ''
      }
      this.search()
    },
    pageChange(current) {
      this.searchForm.page.current = current
      this.getDataList()
    },
    getDataList() {
      dataList(this.searchForm).then(res => {
        const { records, total } = res.data
        records.forEach((record) => {
          record.createTime = this.getDataTime(record.createTime)
        })
        this.tableForm.tableData = records
        this.pageForm.total = total
        this.$nextTick(() => {
          for (let k = 0; k < this.tableForm.tableData.length; k++) {
            const domHeight = this.$refs[`community_${k}`].offsetHeight
            const open = domHeight < 100
            this.$set(this.tableForm.tableData[k], 'domHeight', domHeight)
            this.$set(this.tableForm.tableData[k], 'open', open)
            console.log(this.tableForm.tableData[k])
          }
        })
      }).catch(() => {
      })
    },
    getDataTime(data) {
      var y = new Date(data).getFullYear()
      var m = this.appendZero(new Date(data).getMonth() + 1)
      var d = this.appendZero(new Date(data).getDate())
      var ho = this.appendZero(new Date(data).getHours())
      var mi = this.appendZero(new Date(data).getMinutes())
      var se = this.appendZero(new Date(data).getSeconds())
      return y + '-' + m + '-' + d + '   ' + ho + ':' + mi + ':' + se
    },
    toggleFoldFn(item) {
      item.open = !item.open
    },
    appendZero(obj) {
      if (obj < 10) {
        return '0' + obj
      } else {
        return obj
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    rowOperation(row, now) {
      this[now](row)
    }
  }
}
</script>
<style scoped>
.table-item-btnGroup {
  margin-bottom: 10px;
}

.form-item-btnGroup {
  margin-left: 10px;
  margin-top: 5px
}

.filter-item {
  display: inline-block;
}
.community-words {
   font-size: 14px;
   font-weight: 400;
   line-height: 20px;
   color: rgba(6, 15, 38, 1);
   word-wrap:break-word;
   word-break:break-all;
 }
.toggle-fold {
  color: #1890ff;
  cursor: pointer;
  margin: 0 10px;
  float: right;
}
.more-line {
  word-break: break-all;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 5;
  overflow: hidden;
}
</style>
