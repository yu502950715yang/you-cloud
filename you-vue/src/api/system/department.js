import request from '@/utils/request'

export function add(data) {
  return request({
    url: '/system/department/add',
    method: 'post',
    data
  })
}

export function list(params) {
  return request({
    url: '/system/department/getAll',
    method: 'get',
    params
  })
}

export function remove(params) {
  return request({
    url: '/system/department/delete',
    method: 'get',
    params
  })
}

export function getDepartmentById(params) {
  return request({
    url: '/system/department/getDepartment',
    method: 'get',
    params
  })
}

export function edit(data) {
  return request({
    url: '/system/department/edit',
    method: 'post',
    data
  })
}
