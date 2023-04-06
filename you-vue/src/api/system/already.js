import request from '@/utils/request'

export function list(data) {
  return request({
    url: '/workflow/already',
    method: 'post',
    data

  })
}

export function getProcListSelect() {
  return request({
    url: '/workflow/procListSelect',
    method: 'get'
  })
}

export function getProcessDetail(data) {
  return request({
    url: '/workflow/processFinished',
    method: 'post',
    data
  })
}
