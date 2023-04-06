<template>
  <div>
    <el-pagination
      background
      :total="pageData.total"
      :current-page="pageData.current"
      style="margin-top: 15px"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageData.size"
      layout="total, sizes, prev, pager, next, jumper"
      @current-change="pageChange"
      @size-change="handleSizeChange"
    />
  </div>
</template>

<script>
export default {
  props: {
    // 显示共有多少条数据
    total: {
      type: Number,
      required: true
    },
    pageForm: {
      type: Object,
      required: false,
      default: () => {
        return {
          current: 0, // 第几页
          size: 0 // 每页多少条数据
        }
      }
    }
  },
  data() {
    return {
      pageData: {
        total: this.total,
        current: this.pageForm.current,
        size: this.pageForm.size
      }
    }
  },
  watch: {
    total(val) {
      this.pageData.total = val
    }
  },
  methods: {
    handleSizeChange(size) {
      this.$emit('handleSizeChange', size)
    },
    pageChange(current) {
      this.$emit('pageChange', current)
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
