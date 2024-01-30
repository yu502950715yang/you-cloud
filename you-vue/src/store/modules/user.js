import {getInfo, login, logout} from '@/api/login'
import {getToken, removeToken, setTokenName} from '@/utils/auth'


const useUserStore = defineStore(
  'user',
  {
    state: () => ({
      token: getToken(),
      name: '',
      avatar: '',
      permissions: [],
      // 是否获取过权限
      isGetPermission: false
    }),
    actions: {
      // 登录
      login(loginData) {
        return new Promise((resolve, reject) => {
          login(loginData).then(res => {
            let data = res.data
            setTokenName(data.tokenInfo.tokenName)
            this.token = data.tokenInfo.tokenValue
            resolve()
          }).catch(error => {
            reject(error)
          })
        })
      },
      // 获取用户信息
      getInfo() {
        return new Promise((resolve, reject) => {
          getInfo().then(res => {
            const user = res.data
            this.permissions = user.permissions
            this.name = user.nickname
            this.isGetPermission = true
            resolve(res)
          }).catch(error => {
            reject(error)
          })
        })
      },
      // 退出系统
      logOut() {
        return new Promise((resolve, reject) => {
          logout(this.token).then(() => {
            this.token = ''
            this.permissions = []
            this.isGetPermission = false
            removeToken()
            resolve()
          }).catch(error => {
            reject(error)
          })
        })
      }
    }
  })

export default useUserStore
