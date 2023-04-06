import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import '@/styles/index.scss' // global css
import '../theme/index.css'
// import './assets/css/lodingCss.css'

import App from './App'
import store from './store'
import plugins from './plugins' // plugins
import router from './router'
import ElTreeSelect from 'el-tree-select'

import '@/icons' // icon
import '@/permission' // permission control
import permission from '@/directive/permission' // 权限判断指令
import waves from '@/directive/waves' // 水波纹指令
import lodash from '@/utils/lodash' // lodash
import VueCookies from 'vue-cookies' // cookies
import global from './global' // 全局js
import dataV from '@jiaminghi/data-view'
/* 右边工具栏样式*/
// import 'bpmn-js-properties-panel/dist/assets/bpmn-js-properties-panel.css'

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */
// import { mockXHR } from '../mock'
// if (process.env.NODE_ENV === 'development') {
//   mockXHR()
// }

// set ElementUI lang to EN
Vue.use(ElementUI, { locale })
Vue.use(permission)
Vue.use(waves)
Vue.use(plugins)
Vue.use(lodash)
Vue.use(ElTreeSelect)
Vue.use(VueCookies)
Vue.use(dataV)

Vue.config.productionTip = false
Vue.prototype.$global = global

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
