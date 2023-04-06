<template>
  <div class="app-container">
    <z-card style="height: auto">
      <el-form class="searchForm" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="6" class="form-item-height">
            <el-form-item label="选择表">
              <el-select v-model="tableName" filterable clearable size="mini" style="width: 100%" placeholder="请选择">
                <el-option
                  v-for="item in tableNameList"
                  :key="item"
                  :label="item"
                  :value="item"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6" class="form-item-height">
            <div class="form-item-btnGroup">
              <el-button class="filter-item" type="primary" icon="el-icon-search" size="mini" @click="getTableColumn">
                读取表信息
              </el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>
    </z-card>
    <z-card style="height: auto">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card class="box-card" style="width:100%">
            <div slot="header" class="clearfix">
              <el-row>
                <el-col :span="10">
                  <span v-if="tableName != ''">表名:{{ tableName }}</span>
                  <span v-if="tableName == ''" style="color:red">请先选择表</span>
                </el-col>
                <el-col :span="14">
                  <el-button style="float: right; padding: 3px 0" type="text" @click="dataCreate">一键生成</el-button>
                </el-col>
              </el-row>
            </div>
            <el-form
              size="medium"
              label-width="100px"
              label-position="right"
            >
              <el-row :gutter="20">
                <el-col :span="8">
                  <el-form-item label="生成条数:">
                    <el-input v-model.trim="createNum" placeholder="请输入生成条数" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-collapse>
                <el-collapse-item v-for="(tableColunm, colunmIndex) in tableColumnList" :key="colunmIndex">
                  <template slot="title">
                    字段名: {{ tableColunm.columnName }}
                  </template>
                  <div>
                    <el-row :gutter="20">
                      <el-col :span="7">
                        <el-form-item label="字段类型:">
                          {{ tableColunm.columnType }}
                        </el-form-item>
                      </el-col>
                      <el-col :span="5">
                        <el-form-item label="默认值:">
                          {{ tableColunm.columnDefault }}
                        </el-form-item>
                      </el-col>
                      <el-col :span="12">
                        <el-form-item label="注释:">
                          {{ tableColunm.columnComment }}
                        </el-form-item>
                      </el-col>
                    </el-row>
                    <el-row :gutter="20">
                      <el-col :span="3">
                        <el-form-item label="非空:">
                          <el-checkbox v-if="tableColunm.isNullable == 'NO'" checked />
                          <el-checkbox v-else-if="tableColunm.isNullable == 'YES'" />
                        </el-form-item>
                      </el-col>
                      <el-col :span="3">
                        <el-form-item label="主键:">
                          <el-checkbox v-if="tableColunm.columnKey == 'PRI'" checked />
                          <el-checkbox v-else />
                        </el-form-item>
                      </el-col>
                      <el-col :span="3">
                        <el-form-item label="自增:">
                          <el-checkbox v-if="tableColunm.extra == 'auto_increment'" checked />
                          <el-checkbox v-else />
                        </el-form-item>
                      </el-col>
                    </el-row>
                    <el-row :gutter="20">
                      <el-col :span="7">
                        <el-form-item label="模拟类型:">
                          <el-select v-model="tableColumnList[colunmIndex].simulationDataType">
                            <el-option
                              v-for="item in simulationTypeList"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value"
                            />
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col v-if="tableColunm.simulationDataType === simulationDataTypeEnum.FIXED.value" :span="8">
                        <el-form-item label="固定值:">
                          <el-input v-model.trim="tableColumnList[colunmIndex].simulationDataValue" placeholder="请输入固定值" />
                        </el-form-item>
                      </el-col>
                      <el-col v-else-if="tableColunm.simulationDataType === simulationDataTypeEnum.RANDOM.value" :span="8">
                        <el-form-item label="随机规则:">
                          <el-select v-model="tableColumnList[colunmIndex].randomRule">
                            <el-option
                              v-for="item in randomRuleList"
                              :key="item.dictCode"
                              :label="item.dictValue"
                              :value="item.dictCode"
                            />
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col v-else-if="tableColunm.simulationDataType === simulationDataTypeEnum.INCREASE.value" :span="6">
                        <el-form-item label="起始值:">
                          <el-input-number v-model="tableColumnList[colunmIndex].startNum" controls-position="right" :min="0" size="small" />
                        </el-form-item>
                      </el-col>
                      <el-col v-else-if="tableColunm.simulationDataType === simulationDataTypeEnum.RULE.value" :span="9">
                        <el-form-item label="规则:">
                          <el-input v-model.trim="tableColumnList[colunmIndex].regEx" placeholder="请输入正则表达式" />
                        </el-form-item>
                      </el-col>
                    </el-row>
                  </div>
                </el-collapse-item>
              </el-collapse>
            </el-form>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="box-card" style="width:100%">
            <div slot="header" class="clearfix">
              生成结果
              <el-button id="copy" style="float: right; padding: 3px 0" type="text" :data-clipboard-text="data.insertSql" @click="copy()">复制</el-button>
            </div>
            <div>
              <div id="container" style="height: 600px;">
                <el-empty v-if="data.insertSql.size == 0" description="描述文字" />
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </z-card>
  </div>
</template>

<script>
import ZCard from '@/components/Cardarea'
import enums from '@/enum/enums'
import * as monaco from 'monaco-editor/esm/vs/editor/editor.api'
import 'monaco-editor/esm/vs/basic-languages/javascript/javascript.contribution'
// import * as monaco from 'monaco-editor/esm/vs/editor/editor.main'
// 具体需要引入具体语言
import 'monaco-editor/esm/vs/basic-languages/sql/sql.contribution'

import { getInitData, getTableColumn, dataCreate } from '~/system/dataCreate'
import Clipboard from '_clipboard@2.0.11@clipboard'

export default {
  name: 'DataCreate',
  components: {
    ZCard
  },
  data() {
    return {
      simulationDataTypeEnum: enums.simulationDataType,
      tableName: '',
      tableNameList: [],
      tableColumnList: [],
      // 模拟类型
      simulationTypeList: [],
      // 随机规则
      randomRuleList: [],
      // 生成条数
      createNum: 20,
      data: {
        insertSql: []
      },
      color: ''
    }
  },
  computed: {
  },
  created() {
    this.getInitData()
  },
  methods: {
    getInitData() {
      getInitData().then(res => {
        const { tableNameList, simulationTypeList, randomRuleList } = res.data
        this.tableNameList = tableNameList
        this.simulationTypeList = simulationTypeList
        this.randomRuleList = randomRuleList
      }).catch(() => {
      })
    },

    getTableColumn() {
      if (this.tableName === '' || this.tableName === undefined) {
        this.$message.error('请先选择表')
        return
      }
      this.tableColumnList = []
      getTableColumn(this.tableName).then(res => {
        this.tableColumnList = res.data
      })
    },
    dataCreate() {
      if (this.tableName === '' || this.tableColumnList.size === 0) {
        this.$message.error('请先填写生成信息')
        return
      }
      var sqlCreate = {
        tableColumnList: this.tableColumnList,
        tableName: this.tableName,
        num: this.createNum
      }
      dataCreate(sqlCreate).then(res => {
        const self = this
        self.data = res.data
        self.data.insertSql = res.data.insertSql.join('\n')
        if (!self.monacoEditor) {
          const domEditor = document.getElementById('container')
          self.monacoEditor = monaco.editor.create(domEditor, {
            value: self.data.insertSql, // 编辑器内容
            theme: 'vs-dark',
            readOnly: false,
            automaticLayout: true,
            language: 'sql',
            ...this.opts
          })
        } else {
          self.monacoEditor.setValue(self.data.insertSql)
        }
      }).catch(() => {
      })
    },
    copy() {
      const _this = this
      const clipboard = new Clipboard('#copy')
      // 复制成功
      clipboard.on('success', function() {
        _this.$message.success('复制成功！')
        clipboard.destroy()
      })
      // 复制失败
      clipboard.on('error', function() {
        _this.$message.error('复制失败！')
        clipboard.destroy()
      })
    }
  }
}
</script>

<style lang="scss" scoped>.table-item-btnGroup {
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

