import request from '@/utils/request'

// 登录方法
export function login(data) {
  return request({
    url: '/auth/login',
    headers: {
      isToken: false
    },
    method: 'post',
    data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/system/user/getInfo',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/auth/logout',
    method: 'get'
  })
}

export const codeImgUrl = import.meta.env.VITE_APP_BASE_API + "/auth/verifyCode"