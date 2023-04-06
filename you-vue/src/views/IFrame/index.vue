<template>
  <div class="app-container">
    <z-card style="height: auto">
      <iframe id="main" class="frame" :src="src" width="100%" :height="height" />
    </z-card>
  </div>
</template>

<script>
import ZCard from '@/components/Cardarea'
export default {
  name: 'IFrame',
  components: {
    ZCard
  },
  data() {
    return {
      src: this.$route.query.url && decodeURIComponent(this.$route.query.url)
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

<style lang="scss" scoped>
  .frame{
    border: 0;
  }
</style>
