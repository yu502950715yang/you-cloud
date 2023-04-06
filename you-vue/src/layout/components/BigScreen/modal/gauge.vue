<template>

  <div style="width: 100%;height: 100%;">
    <div class="title">{{ rowData.title }}</div>
    <div class="echartsGauge">
      <div
        id="gaugeChart"
        ref="main"
        class="main-echarts-contianer"
      />
    </div>
  </div>
</template>
<script>
import * as echarts from 'echarts'
export default {
  name: 'Gauge',
  inheritAttrs: false,
  props: {
    rowData: {
      type: Object,
      required: false
    }
  },
  mounted() {
    this.drawClockChart()
  },
  methods: {
    drawClockChart() {
      // 指定图表的配置项和数据
      const myGaugeChart = echarts.init(
        document.getElementById('gaugeChart')
      )
      const option = {
        'series': [
          {
            'name': '个人指标',
            'type': 'gauge',
            'radius': '90%',
            'startAngle': '240',
            'endAngle': '-60',
            // 图表的刻度分隔段数
            'splitNumber': 5,
            // 图表的轴线相关
            'axisLine': {
              'show': true,
              'lineStyle': {
                'color': [
                  [
                    0.9,
                    new echarts.graphic.LinearGradient(0, 0, 1, 0, [{
                      offset: 0,
                      color: '#FFD900'
                    },
                    {
                      offset: 1,
                      color: '#FF8000'
                    }
                    ])
                  ],
                  [1, '#56606E']
                ],
                'width': 15
              }
            },
            // 图表的刻度及样式
            'axisTick': {
              'distance': -15,
              'lineStyle': {
                'color': '#000',
                'width': 3
              },
              'length': 15,
              'splitNumber': 1
            },
            // axisTick: {
            //   length: 15,
            //   lineStyle: {
            //     color: 'auto',
            //     width: 2
            //   }
            // },
            // 图表的刻度标签(20、40、60等等)
            'axisLabel': {
              'distance': 0,
              'textStyle': {
                'color': '#9E9E9E'
              }
            },
            // 图表的分割线
            'splitLine': {
              'show': false
            },
            // 图表的指针
            'pointer': {
              'show': false
            },
            // 图表的数据详情
            'detail': {
              'formatter': function(params) {
                return '{title|' + '总体得分}' + '\n' + '{score|' + params + '}'
              },
              'offsetCenter': [0, 0],
              'rich': {
                'title': {
                  'fontSize': 12,
                  'color': '#9E9E9E',
                  'lineHeight': 30
                },
                'score': {
                  'fontSize': 27,
                  'color': '#fff'
                }
              }
            },
            // 图表的标题
            'title': {
              'offsetCenter': [0, '90%'],
              'color': '#fff',
              'fontSize': 14
            },
            'data': [{
              'name': this.rowData.gaugeTitle,
              'value': 31
            }]
          },
          {
            'name': '外层线',
            'type': 'gauge',
            'radius': '100%',
            'startAngle': '240',
            'endAngle': '-60',
            'center': ['50%', '50%'],
            'axisLine': {
              'lineStyle': {
                'width': 1,
                'color': [[1, '#56606E']]
              }
            },
            'splitLine': {
              'length': -6,
              'lineStyle': {
                'opacity': 0
              }
            },
            'axisLabel': {
              'show': false
            },
            'axisTick': {
              'splitNumber': 1,
              'lineStyle': {
                'opacity': 0
              }
            },
            'detail': {
              'show': false
            },
            'pointer': {
              'show': false
            }
          }
        ]
      }
      let tempVal = 0
      clearInterval(this.clockChartTimer)
      this.clockChartTimer = setInterval(() => {
        if (tempVal > this.rowData.data) {
          clearInterval(this.clockChartTimer)
          // 最后转到最终数据的地方
          option.series[0].data[0].value = this.rowData.data
          option.series[0].axisLine.lineStyle.color[0][0] = this.rowData.data / 100
          // 使用刚指定的配置项和数据显示图表
          myGaugeChart.setOption(option)
          // 初始化渲染完成
          this.renderCompleted = true
          return
        }
        option.series[0].data[0].value = tempVal
        option.series[0].axisLine.lineStyle.color[0][0] = tempVal / 100
        // 使用刚指定的配置项和数据显示图表。
        myGaugeChart.setOption(option)
        tempVal++
      }, 20)
      // 此处监听浏览器的resize，重新渲染图表
      const that = this
      window.addEventListener('resize', function() {
        clearTimeout(that.resizeTimer)
        that.resizeTimer = setTimeout(() => {
          myGaugeChart.resize()
        }, 500)
      })
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
.echartsGauge {
  width: 90%;
  height: calc(100% - 65px);
  margin: 0 auto;
}
.main-echarts-contianer {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
