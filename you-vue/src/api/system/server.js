import request from '@/utils/request'

export function getInfo() {
  return request({
    url: '/core/monitor/server',
    method: 'get'
  })
}

