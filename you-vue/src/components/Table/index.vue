<template>
  <div>
    <el-table
      :data="tableForm.tableData"
      :default-sort="tableForm.defaultSort"
      style="width: 100%"
      border
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
      @sort-change="slotChange"
    >
      <el-table-column
        v-if="tableForm.showSelection"
        type="selection"
        width="55"
      />
      <el-table-column
        v-for="(item,columIndex) in tableForm.columnList"
        :key="columIndex"
        :prop="item.prop"
        :label="item.label"
        :width="item.columnWidth"
        :align="item.align || 'left'"
        :sortable="item.sortable ? 'custom': false"
        show-overflow-tooltip
      >
        <template slot-scope="{row}">
          <!-- 默认展示 -->
          <span v-if="item.text">{{ row[item.prop] }}</span>
          <!-- switch开关 -->
          <el-switch
            v-if="item.switch"
            v-model="row[item.prop]"
            :inactive-text="row[item.prop] ? item.openText:item.closeText"
            @change="switchChange(row,item.prop)"
          />
          <!-- 操作按钮 -->
          <span v-for="(operations,index) in item.operation" :key="index">
            <el-button
              v-if="showBtn(operations.isShow,row)"
              v-permission="showPermission(operations.permission)"
              :icon="operations.btnIcon"
              size="small"
              style="margin-right: 10px;"
              type="text"
              @click="rowOperation(row,operations.btnObject)"
            >{{ operations.btnFont }}</el-button>
          </span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  props: {
    tableForm: {
      type: Object,
      required: false,
      default: () => {
        return {
          operation: true,
          tableData: [], // table表格的数据
          showSelection: '', // 是否启用多选
          columnList: [], // 行元素信息
          btnList: [] // 按钮信息
        }
      }
    }
  },
  data() {
    return {}
  },
  methods: {
    // 行操作
    rowOperation(row, now) {
      this.$emit('rowOperation', row, now)
    },
    // switchChange调用
    switchChange(row, prop) {
      this.$emit('switchChange', row, prop)
    },
    showBtn(isShow, row) {
      if (isShow === undefined) {
        return true
      } else {
        return isShow(row)
      }
    },
    // 多个权限用,号分割 例如 b:role:edit,b:role:add
    showPermission(permissions) {
      if (permissions) {
        const permArr = permissions.split(',')
        return permArr
      } else {
        return []
      }
    },
    slotChange(data) {
      if (data.order !== null) {
        const changeData = {
          column: this.getKebabCase(data.prop),
          asc: this.orderType(data.order)
        }
        this.$emit('slotChange', changeData)
      } else {
        this.$emit('slotChange', '')
      }
    },
    orderType(data) {
      return data === 'ascending'
    },
    getKebabCase(str) {
      return str.replace(/[A-Z]/g, function(i) {
        return '_' + i.toLowerCase()
      })
    },
    handleSelectionChange(val) {
      this.$emit('handleSelectionChange', val)
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
