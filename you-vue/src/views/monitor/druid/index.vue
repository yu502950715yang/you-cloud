<template>
  <iframe id="main" class="frame" :src="url" width="100%" :height="height" />
</template>
<script>
export default {
  name: 'Druid',
  data() {
    return {
      url: '/api/druid/login.html'
    }
  },
  computed: {
    height() {
      const deviceHeight = document.documentElement.clientHeight
      return (Number(deviceHeight) - 120)
    }
  }
}
</script>
