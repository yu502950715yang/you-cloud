<template>
  <div class="app-container">
    <z-card style="height: auto">
      <el-form ref="searchForm" class="searchForm" :model="this.searchForm" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="6" class="form-item-height">
            <el-form-item label="登录名称">
              <el-input v-model.trim="searchForm.username" clearable size="mini" />
            </el-form-item>
          </el-col>
          <el-col :span="8" class="form-item-height">
            <el-form-item label="操作时间">
              <el-date-picker
                v-model="searchTime"
                size="mini"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="datetimerange"
                format="yyyy-MM-dd HH:mm"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <div class="form-item-btnGroup" style="margin-left: 100px">
              <el-button size="mini" class="filter-item" type="primary" icon="el-icon-search" @click="search">
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
    <z-card>
      <div class="table-item-btnGroup">
        <el-button
          v-permission="['b:log:excelOut']"
          class="filter-item"
          type="success"
          size="mini"
          plain
          icon="el-icon-download"
          @click="exportExcel"
        >
          导出
        </el-button>
      </div>

      <div class="block">
        <WrTable
          :page-form="searchForm.page"
          :table-form="tableForm"
          @slotChange="slotChange"
          @handleSelectionChange="handleSelectionChange"
        />
        <WrPagination
          :page-form="searchForm.page"
          :total="pageForm.total"
          @pageChange="pageChange"
          @handleSizeChange="handleSizeChange"
        />
      </div>
    </z-card>
  </div>
</template>

<script>
import ZCard from '@/components/Cardarea'
import { download, list } from '~/system/log'
import WrPagination from '@/components/Pagination'
import WrTable from '@/components/Table'

export default {
  name: 'Log',
  components: {
    ZCard,
    WrTable,
    WrPagination
  },
  props: {},
  data() {
    return {
      multipleSelection: [], // 存放当前列表中选中的项
      pageForm: { // 分页数据集合
        total: 0,
        showPage: false
      },
      searchTime: [],
      tableForm: {
        tableData: [],
        operation: false,
        showSelection: false,
        columnList: [
          {
            text: true,
            prop: 'username',
            label: '登录名称'
          },
          {
            text: true,
            prop: 'createDate',
            label: '操作时间'
          },
          {
            text: true,
            prop: 'requestUri',
            label: '请求地址'
          },
          {
            text: true,
            prop: 'module',
            label: '模块名称'
          },
          {
            text: true,
            prop: 'handle',
            label: '操作'
          },
          {
            text: true,
            prop: 'requestMethod',
            label: '请求方法'
          },
          {
            text: true,
            prop: 'requestParams',
            label: '请求参数'
          },
          {
            text: true,
            prop: 'responseResult',
            label: '响应结果'
          },
          {
            text: true,
            prop: 'responseTime',
            label: '响应时间'
          }
        ]
      },
      searchForm: { // 搜索框数据集合
        page: {
          current: 1,
          size: 10,
          orders: []
        },
        username: '',
        fromTime: '',
        toTime: ''
      }
    }
  },
  created() {
    this.search()
  },
  mounted() {},
  methods: {

    slotChange(data) {
      this.searchForm.page.orders = []
      if (data) {
        this.searchForm.page.orders.push(data)
      }
      this.search()
    },

    pageChange(current) {
      this.searchForm.page.current = current
      this.getLog()
    },
    handleSizeChange(size) {
      this.searchForm.page.size = size
      this.getLog()
    },
    search() {
      this.searchForm.page.current = 1
      this.getLog()
    },
    refresh() {
      this.searchForm = {
        page: {
          current: 1,
          size: 10,
          orders: []
        },
        username: ''
      }
      this.searchTime = []
      this.search()
    },
    exportExcel() {
      if (this.searchTime != null) {
        this.searchForm.fromTime = this.searchTime[0]
        this.searchForm.toTime = this.searchTime[1]
      } else {
        this.searchForm.fromTime = ''
        this.searchForm.toTime = ''
      }
      download(this.table.searchForm).then(res => {
        const blob = new Blob([res.data])
        const filename = res.headers['content-disposition']
        const downloadElement = document.createElement('a')
        const href = window.URL.createObjectURL(blob)
        downloadElement.href = href
        downloadElement.download = decodeURIComponent(filename.split('filename=')[1])
        document.body.appendChild(downloadElement)
        downloadElement.click()
        document.body.removeChild(downloadElement)
        window.URL.revokeObjectURL(href)
      }).catch(function(error) {
        console.log(error)
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    getLog() {
      if (this.searchTime != null) {
        this.searchForm.fromTime = this.searchTime[0]
        this.searchForm.toTime = this.searchTime[1]
      } else {
        this.searchForm.fromTime = ''
        this.searchForm.toTime = ''
      }
      list(this.searchForm).then(res => {
        const { records, total } = res.data
        this.tableForm.tableData = records
        this.pageForm.total = total
        this.pageForm.showPage = true
      }).catch(() => {
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container >>> .cell {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.app-container >>> .el-tooltip {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  width: 100%;
}
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
</style>
