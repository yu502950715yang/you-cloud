<template>
  <div id="map" />
</template>

<script>
export default {
  name: 'Index',
  inheritAttrs: false,
  props: {
  },
  data() {
    return {
      map: {}
    }
  },
  mounted() {
    this.load()
  },
  methods: {
    load() {
      const init = new Promise((resolve, reject) => {
        if (window.T) {
          console.log('地图初始化成功')
          resolve(window.T)
          reject('error')
        }
      })
      init.then(T => {
        this.map = new T.Map('map')
        this.map.centerAndZoom(new T.LngLat(116.40769, 39.89945), 12)
        // 创建对象
        var ctrl = new T.Control.MapType()
        // 添加控件
        this.map.addControl(ctrl)
        var points = []
        points.push(new T.LngLat(116.41136, 39.97569))
        points.push(new T.LngLat(116.411794, 39.9068))
        points.push(new T.LngLat(116.32969, 39.92940))
        points.push(new T.LngLat(116.385438, 39.90610))
        // 创建面对象 color为线边颜色 fillColor为填充色
        var polygon = new T.Polygon(points, {
          color: 'blue', weight: 3, opacity: 0.5, fillColor: 'blue', fillOpacity: 0.5
        })
        // 向地图上添加面
        this.map.addOverLay(polygon)
      })
    }
  }
}
</script>

<style scoped>

#map {
  width: 100%;
  height: 100%;
  z-index: 99999999999999999999;
}
</style>
