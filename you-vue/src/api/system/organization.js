import request from '@/utils/request'

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

export function addOrganization(data) {
  return request({
    url: '/core/organ/add',
    method: 'post',
    data
  })
}

export function updateOrganization(data) {
  return request({
    url: '/core/organ/update',
    method: 'post',
    data
  })
}

export function remove(organizationId) {
  return request({
    url: '/core/organ/' + organizationId,
    method: 'get'
  })
}

