import lodash from 'lodash'

export default {
  install: function(Vue, name = '$lodash') {
    Object.defineProperty(Vue.prototype, name, { value: lodash })
  }
}
