import request from '@/utils/request'

export function list(data) {
  return request({
    url: '/core/notice/listPage',
    method: 'post',
    data

  })
}

export function add(data) {
  return request({
    url: '/core/notice/add',
    method: 'post',
    data
  })
}
export function edit(data) {
  return request({
    url: '/core/notice/edit',
    method: 'post',
    data
  })
}

export function remove(id) {
  return request({
    url: '/core/notice/delete/' + id,
    method: 'post'
  })
}

export function getNoticeById(id) {
  return request({
    url: '/core/notice/getNotice/' + id,
    method: 'get'
  })
}
