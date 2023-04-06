import request from '@/utils/request'

export function getTableNames() {
  return request({
    url: '/core/generate/getTableNames',
    method: 'get'
  })
}

export function generate(data) {
  return request({
    url: '/core/generate/generate',
    method: 'post',
    data
  })
}

