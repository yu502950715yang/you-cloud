import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/core/user/info',
    method: 'get'
  })
}

export function userList(data) {
  return request({
    url: '/core/user/listPage',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

export function add(data) {
  return request({
    url: '/core/user/add',
    method: 'post',
    data
  })
}

export function getUserById(id) {
  return request({
    url: '/core/user/getUser/' + id,
    method: 'get'
  })
}

export function edit(data) {
  return request({
    url: '/core/user/edit',
    method: 'post',
    data
  })
}

export function resetPassword(id) {
  return request({
    url: '/core/user/resetPassword/' + id,
    method: 'post'
  })
}

export function changeEnable(id) {
  return request({
    url: '/core/user/changeEnable/' + id,
    method: 'post'
  })
}

export function getDataPermission(id) {
  return request({
    url: '/system/user/' + id,
    method: 'get'
  })
}

export function updateAuthorization(data) {
  return request({
    url: '/system/user/authorization',
    method: 'post',
    data
  })
}

export function changePassword(data) {
  return request({
    url: '/core/user/updatePassword',
    method: 'post',
    data
  })
}

export function exportDaIlyTable(data) {
  return request({
    url: '/core/user/download',
    method: 'post',
    responseType: 'blob',
    data
  })
}

export function downloadExcelTemplate() {
  return request({
    url: '/core/user/excelTemplate',
    method: 'get',
    responseType: 'blob'
  })
}

export function importExcel(data) {
  return request({
    url: '/core/user/importExcel',
    method: 'post',
    data
  })
}

export function list(data) {
  return request({
    url: '/core/organ/getOrganTreeList',
    method: 'post',
    data
  })
}

export function getInfoForSave(id) {
  const url = id ? '?organizationId=' + id : ''
  return request({
    url: '/core/organ/getInfoForSave' + url,
    method: 'get'
  })
}

