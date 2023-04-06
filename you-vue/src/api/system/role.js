import request from '@/utils/request'

export function list(data) {
  return request({
    url: '/core/role/listPage',
    method: 'post',
    data

  })
}

export function add(data) {
  return request({
    url: '/core/role/add',
    method: 'post',
    data
  })
}
export function edit(data) {
  return request({
    url: '/core/role/edit',
    method: 'post',
    data
  })
}

export function remove(data) {
  return request({
    url: '/core/role/delete',
    method: 'post',
    data
  })
}

export function getAll(roleId) {
  return request({
    url: '/core/role/getAll/' + roleId,
    method: 'get'
  })
}

export function addAuthorize(data) {
  return request({
    url: '/core/role/authorize',
    method: 'post',
    data
  })
}

export function getMenuList() {
  return request({
    url: '/core/role/getMenuList',
    method: 'get'
  })
}

export function getPermTokenList(roleId) {
  return request({
    url: '/core/role/getPermTokenList/' + roleId,
    method: 'get'
  })
}
