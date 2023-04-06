<template>
  <div style="width: 100%;height: 100%;">
    <div class="title">{{ rowData.title }}</div>
    <div class="echartsLine">
      <div id="lineChart" style="width:100%;height:100%;" />
    </div>
  </div>
</template>
<script>
import * as echarts from 'echarts'
export default {
  name: 'LineChart',
  inheritAttrs: false,
  props: {
    rowData: {
      type: Object,
      required: false
    }
  },
  mounted() {
    this.drawChart()
  },
  methods: {
    async drawChart() {
      // 基于准备好的dom，初始化echarts实例
      const myPieChart = echarts.init(
        document.getElementById('lineChart')
      )
      // 指定图表的配置项和数据
      const option = {
        color: ['#00e49b', '#0276d9', '#ff0349'],
        title: {
          text: 'Gradient Stacked Area Chart'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
          data: ['Line 1', 'Line 2', 'Line 3', 'Line 4', 'Line 5']
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: 'Line 1',
            type: 'line',
            smooth: true,
            lineStyle: {
              width: 2
            },
            showSymbol: true,
            areaStyle: {
              opacity: 0.8,
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: 'rgba(0,228,155,1)'
                },
                {
                  offset: 0.34,
                  color: 'rgba(0,228,155,0.5)'
                },
                {
                  offset: 1,
                  color: 'rgba(0,228,155,0)'
                }
              ])
            },
            emphasis: {
              focus: 'series'
            },
            data: [140, 232, 255, 100, 400, 340, 250]
          },
          {
            name: 'Line 2',
            type: 'line',
            stack: 'Total',
            smooth: true,
            lineStyle: {
              width: 2
            },
            showSymbol: true,
            areaStyle: {
              opacity: 0.8,
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: 'rgba(2,118,217,1)'
                },
                {
                  offset: 0.34,
                  color: 'rgba(2,118,217,0.5)'
                },
                {
                  offset: 1,
                  color: 'rgba(2,118,217,0)'
                }
              ])
            },
            emphasis: {
              focus: 'series'
            },
            data: [120, 282, 340, 234, 20, 111, 220]
          },
          {
            name: 'Line 3',
            type: 'line',
            stack: 'Total',
            smooth: true,
            lineStyle: {
              width: 2
            },
            showSymbol: true,
            areaStyle: {
              opacity: 0.8,
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: 'rgba(255,3,73,1)'
                },
                {
                  offset: 0.5,
                  color: 'rgba(255,3,73,0.5)'
                },
                {
                  offset: 1,
                  color: 'rgba(255,3,73,0)'
                }
              ])
            },
            emphasis: {
              focus: 'series'
            },
            data: [320, 334, 201, 34, 190, 130, 220]
          }
        ]
      }
      // 使用刚指定的配置项和数据显示图表。
      myPieChart.setOption(option)
      window.onresize = myPieChart.resize
    }
  }
}
</script>

<style scoped>

.title {
  height: 50px;
  line-height: 50px;
  padding-left: 20px;
}
.echartsLine {
  width: 90%;
  height: calc(100% - 65px);
  margin: 0 auto;
}
</style>
