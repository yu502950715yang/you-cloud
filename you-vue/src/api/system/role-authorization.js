import request from '@/utils/request'

export function list() {
  return request({
    url: '/system/roleAuthorization/listPage',
    method: 'get'
  })
}

export function getAllSystem() {
  return request({
    url: '/common/system/getAll',
    method: 'get'
  })
}

export function getInfo(id) {
  return request({
    url: '/system/roleAuthorization/info/' + id,
    method: 'get'
  })
}

export function updateAuthorization(data) {
  return request({
    url: '/system/roleAuthorization/authorization',
    method: 'post',
    data
  })
}
