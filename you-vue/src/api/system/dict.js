import request from '@/utils/request'

export function list(data) {
  return request({
    url: '/core/dictValue/listPage',
    method: 'post',
    data
  })
}

export function add(data) {
  return request({
    url: '/core/dictValue/add',
    method: 'post',
    data
  })
}
export function edit(data) {
  return request({
    url: '/core/dictValue/update',
    method: 'post',
    data
  })
}

export function remove(data) {
  return request({
    url: '/core/dictValue/delete',
    method: 'post',
    data
  })
}

export function listInfo(dictName, data) {
  return request({
    url: '/core/dictValue/infoList/' + dictName,
    method: 'post',
    data
  })
}

export function addDictValue(data) {
  return request({
    url: '/core/dictValue/addDictValue',
    method: 'post',
    data
  })
}

export function updateDictValue(data) {
  return request({
    url: '/core/dictValue/updateDictValue',
    method: 'post',
    data
  })
}

export function removeDictVale(dictId) {
  return request({
    url: '/core/dictValue/removeDictValue/' + dictId,
    method: 'get'
  })
}

