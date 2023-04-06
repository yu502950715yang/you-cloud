<template>
  <div style="width: 100%;height: 100%;">
    <Map v-if="rowData.type === 'map'" />
    <Weather v-if="rowData.type === 'weather'" :row-data="rowData" />
    <Pie v-if="rowData.type === 'pie'" :row-data="rowData" />
    <Gauge v-if="rowData.type === 'gauge'" :row-data="rowData" />
    <ScrollBoard v-if="rowData.type === 'scrollBoard'" :row-data="rowData" />
    <LineChart v-if="rowData.type === 'line'" :row-data="rowData" />
  </div>
</template>

<script>
import Map from './map'
import Weather from './weather'
import Pie from './pie'
import Gauge from './gauge'
import ScrollBoard from './scrollBoard'
import LineChart from './lineChart'
export default {
  name: 'Index',
  components: {
    Map,
    Weather,
    Pie,
    Gauge,
    ScrollBoard,
    LineChart
  },
  inheritAttrs: false,
  props: {
    rowData: {
      type: Object,
      required: false
    }
  },
  data() {
    return {
    }
  },
  mounted() {
  },
  methods: {
  }
}
</script>

<style scoped>
</style>
