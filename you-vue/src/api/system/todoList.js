import request from '@/utils/request'

export function list(data) {
  return request({
    url: '/workflow/todoList',
    method: 'post',
    data

  })
}

export function todoViewAjax(data) {
  return request({
    url: '/workflow/png?processKey=' + data.procKey + '&version=' + data.procVersion,
    method: 'get'
  })
}

export function getApproveDetail(data) {
  return request({
    url: '/workflow/approveDetail',
    method: 'post',
    data
  })
}

export function complete(data) {
  return request({
    url: '/workflow/leaveFrom/complete',
    method: 'post',
    data
  })
}
