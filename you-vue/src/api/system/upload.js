import request from '@/utils/request'

export function ftpUpload(data) {
  return request({
    url: '/core/upload/ftpUpload',
    method: 'post',
    data
  })

}


