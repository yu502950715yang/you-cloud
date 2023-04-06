import request from '@/utils/request'

export function add(data) {
  return request({
    url: '/core/menu/add',
    method: 'post',
    data
  })
}

export function edit(data) {
  return request({
    url: '/core/menu/edit',
    method: 'post',
    data
  })
}

export function list(data) {
  return request({
    url: '/core/menu/list',
    method: 'post',
    data
  })
}

export function menuList() {
  return request({
    url: '/core/menu/menuList',
    method: 'post'
  })
}

export function remove(did) {
  return request({
    url: '/core/menu/delete?did=' + did,
    method: 'get'
  })
}

export function getRoutes() {
  return request({
    url: '/core/menu/getRoutes',
    method: 'get'
  })
}

export function getMenuById(id) {
  return request({
    url: '/core/menu/' + id,
    method: 'get'
  })
}
