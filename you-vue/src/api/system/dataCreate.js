import request from '@/utils/request'

export function getInitData() {
  return request({
    url: '/core/dataCreate/getInitData',
    method: 'get'
  })
}

export function getTableColumn(tableName) {
  return request({
    url: '/core/dataCreate/getTableColumn?tableName=' + tableName,
    method: 'get'
  })
}

export function dataCreate(data) {
  return request({
    url: '/core/dataCreate/create',
    method: 'post',
    data
  })
}
