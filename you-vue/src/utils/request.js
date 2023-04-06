import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '@/store'
import { getToken, setToken } from '@/utils/auth'
import { showFullScreenLoading, tryHideFullScreenLoading } from './loading'

// create an axios instance
const service = axios.create({
  baseURL: '/api', // 使每次请求都会带一个 /api 前缀
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 50000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['token'] = getToken()
    }
    if (config.headers['showLoading'] !== false) {
      showFullScreenLoading()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data
    if (response.headers['token']) {
      setToken(response.headers['token'])
    }

    if (response.config.headers['showLoading'] !== false) {
      tryHideFullScreenLoading()
    }
    // if the custom code is not 20000, it is judged as an error.
    if (response.headers['downloadfile'] === 'true') {
      // 文件下载
      if (response.status === 200) {
        return response
      } else {
        Message({
          message: '文件下载失败',
          type: 'error',
          duration: 5 * 1000
        })
        return Promise.reject(new Error('文件下载失败'))
      }
    } else {
      if (res.code !== 200) {
        // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
        if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
          // to re-login
          MessageBox.confirm('登录超时，请重新登录', '提示', {
            confirmButtonText: '重新登录',
            type: 'warning'
          }).then(() => {
            store.dispatch('user/resetToken').then(() => {
              location.reload()
            })
          })
        } else {
          Message({
            message: res.msg || 'Error',
            type: 'error',
            duration: 5 * 1000
          })
        }
        return Promise.reject(new Error(res.msg || 'Error'))
      } else {
        return res
      }
    }
  },
  error => {
    console.log('err' + error) // for debug
    tryHideFullScreenLoading()
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
