<template>
  <div class="centerBox">
    <dv-border-box-13 :color="['#3f96a5', '#000c31']">
      <ScreenModal :row-data="screenData[0]" />
    </dv-border-box-13>
  </div>
</template>

<script>
import ScreenModal from './index'
export default {
  name: 'FootGroup3',
  components: {
    ScreenModal
  },
  inheritAttrs: false,
  data() {
    return {
      screenData: [
        {
          title: '折线图展示',
          type: 'line'
        }
      ]
    }
  }
}
</script>

<style scoped>
.centerBox {
  width: 98%;
  height: 90%;
  display:flex;
  align-items: center;
  justify-content: space-between;
  color: white;
  margin: 0 auto;
}
</style>
