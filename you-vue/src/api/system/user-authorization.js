import request from '@/utils/request'

export function changeEnable(params) {
  return request({
    url: '/system/userAuthorization/changeEnable',
    method: 'post',
    params
  })
}

export function userList(data) {
  return request({
    url: '/system/userAuthorization/listPage',
    method: 'post',
    data
  })
}

export function getDataPermission(id) {
  return request({
    url: '/system/userAuthorization/' + id,
    method: 'get'
  })
}

export function updateAuthorization(data) {
  return request({
    url: '/core/user/authorization',
    method: 'post',
    data
  })
}
