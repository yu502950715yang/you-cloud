import request from '@/utils/request'

export function dataList(data) {
  return request({
    url: '/core/lexicon/listPage',
    method: 'post',
    data
  })
}

export function add(data) {
  return request({
    url: '/core/lexicon/add',
    method: 'post',
    data
  })
}

export function getLexiconById(id) {
  return request({
    url: '/core/lexicon/getLexicon/' + id,
    method: 'get'
  })
}

export function edit(data) {
  return request({
    url: '/core/lexicon/update',
    method: 'post',
    data
  })
}

export function remove(data) {
  return request({
    url: '/core/lexicon/' + data,
    method: 'get'
  })
}
