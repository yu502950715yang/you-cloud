import request from '@/utils/request'

export function getDepartmentAndUser(params) {
  return request({
    url: '/system/userDepartment/getAll',
    method: 'get',
    params
  })
}
