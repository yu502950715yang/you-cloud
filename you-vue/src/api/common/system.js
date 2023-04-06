import request from '@/utils/request'

export function getSystems() {
  return request({
    url: '/common/system/getAll',
    method: 'get'
  })
}

export function getWithoutSystem() {
  return request({
    url: '/common/system/getWithoutSystem',
    method: 'get'
  })
}
