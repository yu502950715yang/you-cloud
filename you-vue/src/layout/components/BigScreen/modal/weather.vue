<template>
  <div style="width: 100%;height: 100%;">
    <div class="title">{{ rowData.title }}</div>
    <div class="weatherBody">
      <div class="weatherTimeBox">
        <div class="weatherDate">{{ date }}</div>
        <div class="weatherTime">{{ time }}</div>
      </div>
      <dv-decoration-2 :reverse="true" style="width:5px;height:100%;" />
      <div class="weatherDataBox">
        <div class="weatherTitle">
          <img style="width: 3.2rem;height: 3.2rem;" :src="weatherData.icon" alt="">
          <div class="weather">
            <div>{{ weatherData.temp }}</div>
            <div>{{ weatherData.text }}</div>
          </div>
        </div>
        <div class="weatherData">
          风向：{{ weatherData.windDir }}
        </div>
        <div class="weatherData">
          风力：{{ weatherData.windScale }}
        </div>
        <div class="weatherData">
          风速：{{ weatherData.windSpeed }}
        </div>
        <div class="weatherData">
          湿度：{{ weatherData.humidity }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Weather',
  inheritAttrs: false,
  props: {
    rowData: {
      type: Object,
      required: false
    }
  },
  data() {
    return {
      timer: '',
      date: '',
      time: '',
      weatherData: {
        icon: '',
        temp: '',
        text: '',
        windDir: '',
        windScale: '',
        windSpeed: '',
        humidity: ''
      }
    }
  },
  created() {
    var vm = this
    vm.timer = setInterval(() => {
      var y = new Date().getFullYear()
      var m = vm.appendZero(new Date().getMonth() + 1)
      var d = vm.appendZero(new Date().getDate())
      var ho = vm.appendZero(new Date().getHours())
      var mi = vm.appendZero(new Date().getMinutes())
      var se = vm.appendZero(new Date().getSeconds())
      // 修改数据date
      vm.date = y + '-' + m + '-' + d
      vm.time = ho + ':' + mi + ':' + se
    }, 1000)
    axios.get('https://devapi.qweather.com/v7/weather/now?location=101060703&key=eb5ca97a6529496897839e5783ddd549', { id: 'value' })
      .then(res => {
        const now = res.data.now
        this.weatherData.icon = require('@/assets/weather-icon-S2/256/' + now.icon + '.png')
        this.weatherData.temp = now.temp + '℃'
        this.weatherData.text = now.text
        this.weatherData.windDir = now.windDir
        this.weatherData.windScale = now.windScale + '级'
        this.weatherData.windSpeed = now.windSpeed + 'm/s'
        this.weatherData.humidity = now.humidity + '%'
      })
  },
  mounted() {
  },
  beforeDestroy() {
    if (this.timer) {
      clearInterval(this.timer) // 在Vue实例销毁前，清除我们的定时器
    }
  },
  methods: {
    appendZero(obj) {
      if (obj < 10) {
        return '0' + obj
      } else {
        return obj
      }
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
.weatherBody {
  display: flex;
  justify-content: space-around;
  height: calc(90% - 65px);
}
.weatherTimeBox {
  width: 40%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  font-size: 1.4rem;
}
.weatherDataBox {
  width: 40%;
  height: 100%;
}

.weatherTitle {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-around;
}

.weather {
  font-size: 1rem;
}

.weatherData {
  margin-left: 20%;
  font-size: 1rem;
  margin-top: 0.5rem;
  display: flex;
}
</style>
