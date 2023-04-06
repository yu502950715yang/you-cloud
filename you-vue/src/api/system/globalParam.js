import request from '@/utils/request'

export function list(data) {
  return request({
    url: '/core/globalParam/getGlobalParamListPage',
    method: 'post',
     data
  })
}
export function edit(data) {
  return request({
    url: '/core/globalParam/updateGlobalParam',
    method: 'post',
    data
  })
}
