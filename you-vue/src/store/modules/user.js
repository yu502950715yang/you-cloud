import {getInfo, login, logout} from '@/api/login'
import {getToken, removeToken, setTokenName} from '@/utils/auth'


const useUserStore = defineStore(
  'user',
  {
    state: () => ({
      token: getToken(),
      name: '',
      avatar: '',
      permissions: []
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
            this.name = user.nickName
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
