import request from '@/utils/request'

export function list(data) {
  return request({
    url: '/core/log/listPage',
    method: 'post',
     data
  })

}

export function download(data) {
  return request({
    url: '/core/log/download',
    method: 'post',
    responseType: 'blob',
    data
  })
}
