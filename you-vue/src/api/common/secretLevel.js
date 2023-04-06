import request from '@/utils/request'

export function getSecretLevel() {
  return request({
    url: '/system/secretLevel/getAll',
    method: 'get'
  })
}
