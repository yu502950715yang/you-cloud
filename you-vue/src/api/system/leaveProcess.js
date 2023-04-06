import request from '@/utils/request'

export function list(data) {
  return request({
    url: '/core/role/listPage',
    method: 'post',
    data

  })
}

export function getTaskApprover(procKey, taskId) {
  var param = '?procKey=' + procKey + (taskId !== '' ? '&taskId=' + taskId : '')
  return request({
    url: '/workflow/getTaskApprover' + param,
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: '/workflow/leaveFrom/start',
    method: 'post',
    data
  })
}
