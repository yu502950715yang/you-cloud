<template>
  <div class="centerBox">
    <div class="bilateralBox">
      <div>
        <dv-border-box-9 :color="['#3f96a5', '#000c31']">
          <ScreenModal :row-data="screenData[0]" />
        </dv-border-box-9>
      </div>
      <div>
        <dv-border-box-9 :color="['#3f96a5', '#000c31']">
          <ScreenModal :row-data="screenData[1]" /></dv-border-box-9>
      </div>
    </div>
    <div class="center">
      <dv-border-box-9 :color="['#3f96a5', '#000c31']">
        <ScreenModal :row-data="screenData[2]" />
      </dv-border-box-9>
    </div>
    <div class="bilateralBox">
      <div>
        <dv-border-box-9 :color="['#3f96a5', '#000c31']">
          <ScreenModal :row-data="screenData[3]" /></dv-border-box-9>
      </div>
      <div>
        <dv-border-box-9 :color="['#3f96a5', '#000c31']">
          <ScreenModal :row-data="screenData[4]" />
        </dv-border-box-9></div>
    </div>
  </div>
</template>

<script>
import ScreenModal from './index'
export default {
  name: 'BodyGroup5',
  components: {
    ScreenModal
  },
  inheritAttrs: false,
  data() {
    return {
      screenData: [
        {
          title: '仪表盘展示',
          type: 'gauge',
          data: 88,
          gaugeTitle: '2020年'
        },
        {
          title: '饼状图展示',
          type: 'pie'
        },
        {
          title: 3,
          type: 'map'
        },
        {
          title: '今日天气（长春市）',
          type: 'weather'
        },
        {
          title: '轮播展示',
          type: 'scrollBoard'
        }
      ]
    }
  }
}
</script>

<style scoped>
.centerBox {
  width: 98%;
  height: 100%;
  display:flex;

  align-items: center;
  justify-content: space-between;
  color: white;
  margin: 0 auto;
}
.centerBox > div {
  height: 100%;
}
.centerBox > .bilateralBox > div {
  height: 49%;
}
.centerBox > .bilateralBox > div:first-child {
  margin-bottom: 3%;
}
.centerBox > .bilateralBox {
  width: 24%;
}
.centerBox > .center {
  width: 50%;
}
</style>
