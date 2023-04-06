<template>
  <div style="width: 100%;height: 100%;">
    <div class="title">{{ rowData.title }}</div>
    <div class="echartsPie">
      <div id="networkChart" style="width:100%;height:100%;" />
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
export default {
  name: 'EchartsPie',
  inheritAttrs: false,
  props: {
    rowData: {
      type: Object,
      required: false
    }
  },
  data() {
    return {}
  },
  mounted() {
    this.drawChart()
  },
  methods: {
    async drawChart() {
      // 基于准备好的dom，初始化echarts实例
      const myPieChart = echarts.init(
        document.getElementById('networkChart')
      )
      // 自定义颜色
      const color = ['#ffe000', '#006ced', '#00ffff', '#00cfff', '#ffa800', '#ff5b00', '#ff3000']
      const color2 = ['#ffe00079', '#006ced79', '#00ffff79', '#00cfff79', '#ffa80079', '#ff5b0079', '#ff300079']
      // 图表数据
      const chartData = [{
        name: '农用地',
        value: 1000.25
      }, {
        name: '建设用地',
        value: 98.37
      }, {
        name: '建设用地',
        value: 300.26
      }]
      const data = []
      chartData.forEach((item, index) => {
        data.push(
          {
            value: item.value,
            name: item.name,
            itemStyle: {
              normal: {
                borderWidth: 1.5,
                shadowBlur: 20,
                borderColor: color[index],
                shadowColor: color[index], // 图表shadow颜色
                color: color2[index] // 图表填充颜色
              }
            }
          }
        )
      })
      // 指定图表的配置项和数据
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{b} : {c} ({d}%)'
        },
        polar: {},
        angleAxis: {
          interval: 1,
          type: 'category',
          data: [],
          z: 10,
          axisLine: {
            show: false,
            lineStyle: {
              color: '#0B4A6B',
              width: 1,
              type: 'solid'
            }
          },
          axisLabel: {
            interval: 0,
            show: true,
            color: '#0B4A6B',
            margin: 8,
            fontSize: 16
          }
        },
        radiusAxis: {
          min: 40,
          max: 120,
          interval: 20,
          axisLine: {
            show: false,
            lineStyle: {
              color: '#0B3E5E',
              width: 1,
              type: 'solid'
            }
          },
          axisLabel: {
            formatter: '{value} %',
            show: false,
            padding: [0, 0, 20, 0],
            color: '#0B3E5E',
            fontSize: 16
          },
          splitLine: {
            lineStyle: {
              color: '#0B3E5E',
              width: 2,
              type: 'solid'
            }
          }
        },
        calculable: true,
        series: [
          {
            // stack: 'a',
            type: 'pie',
            radius: ['20%', '80%'],
            roseType: 'area',
            zlevel: 10,
            label: {
              normal: { // 线指向文字展示
                show: true,
                formatter: function(params) {
                  return '{line|}{space|}{n|' + params.name + '}\n{hr|}\n{d|' + params.data.value + '}'
                },
                rich: {
                  line: {
                    backgroundColor: '#01e798',
                    borderRadius: 1,
                    width: 2,
                    height: 10
                  },
                  space: {
                    width: 5
                  },
                  n: {
                    color: '#ffffff',
                    fontSize: 16,
                    fontFamily: 'pmzd_title',
                    height: 20
                  },
                  hr: {
                    borderColor: '#ffffff50',
                    width: '100%',
                    borderWidth: 1,
                    height: 0
                  },
                  d: {
                    color: '#bcc8cc',
                    fontSize: 15,
                    fontFamily: 'DINPro-Regular',
                    // lineHeight: 10,
                    padding: 4
                  }
                },
                textStyle: {
                  fontSize: 12
                },
                position: 'outside'
              },
              emphasis: {
                show: true
              }
            },
            labelLine: {
              normal: {
                show: true
                // lineStyle: {
                //   color: "#fff",
                //   type: "dashed"
                // }
                // length: 20, // 第一段线长
                // length2: 45 // 第二段线长
              },
              emphasis: {
                show: false
              }
            },
            data: data
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
<style lang="scss">
.title {
  height: 50px;
  line-height: 50px;
  padding-left: 20px;
}
.echartsPie {
  width: 90%;
  height: calc(100% - 65px);
  margin: 0 auto;
}
</style>
