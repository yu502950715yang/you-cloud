<template>
  <dv-full-screen-container>
    <div class="bg">
      <div class="entrance" @click="login">进入系统</div>
      <div class="titleGroup">
        <dv-decoration-8 style="width:25%;height:60px;" />
        <dv-decoration-5 style="width:40%;height:60px;margin-top: 30px;" />
        <dv-decoration-8 :reverse="true" style="width:25%;height:60px;" />
        <div class="titleFont">{{ titleData }}</div>
      </div>
      <div class="bodyGroup">
        <body-group-5 v-if="bodyType === 5" />
      </div>
      <div class="footGroup">
        <foot-group-3 v-if="footType === 3" />
      </div>
    </div>
  </dv-full-screen-container>
</template>

<script>
import BodyGroup5 from './modal/bodyGroup5'
import FootGroup3 from './modal/footGroup3'
export default {
  name: 'Index',
  components: {
    BodyGroup5,
    FootGroup3
  },
  data() {
    return {
      redirect: '/system/dashboard',
      map: null,
      titleData: '可视化大屏数据展示',
      bodyType: 5,
      footType: 3
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  mounted() {
  },
  methods: {
    login() {
      if (localStorage.getItem('showScreen')) {
        localStorage.getItem('showScreen') === 'true' ? localStorage.setItem('showScreen', 'false') : localStorage.setItem('showScreen', 'true')
      } else {
        this.$store.state.settings.showScreen === 'true' ? localStorage.setItem('showScreen', 'false') : localStorage.setItem('showScreen', 'true')
      }
      this.$store.state.settings.showScreen = localStorage.getItem('showScreen')
    }
  }
}
</script>

<style scoped>
.entrance {
  position: absolute;
  right: 0;
  top: 0;
  cursor: pointer;
  z-index: 9999;
  color: white;
}
.bg {
  width: 100%;
  height: 100%;
  background-size: 100% 100%;
  background-image: url(../../../img/screenBg.png);
}
.titleGroup {
  display: flex;
  width: 100%;
  align-items: center;
  justify-content: space-between;
}
.titleFont {
  position: absolute;
  font-size: 30px;
  font-weight: 700;
  left: 50%;
  top: 15px;
  -webkit-transform: translateX(-50%);
  transform: translateX(-50%);
  color: white;
}
.bodyGroup {
  width: 100%;
  height: calc(60% - 55px);
  margin-top: 10px;
}
.footGroup {
  width: 100%;
  height: calc(40% - 65px);
  margin-top: 20px;
}
</style>
