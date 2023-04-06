import request from '@/utils/request'

export function list(data) {
  return request({
    url: '/workflow/procList',
    method: 'post',
    data

  })
}

export function saveDeployPerson(data) {
  return request({
    url: '/workflow/saveDeployPerson',
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

export function deploy(data) {
  return request({
    url: '/workflow/deploy/' + data,
    method: 'get'
  })
}

export function getApproverList() {
  return request({
    url: '/core/user/getUserOrgTree',
    method: 'get'
  })
}

export function deployPersonDetail(data) {
  return request({
    url: '/workflow/deployPersonDetail/' + data,
    method: 'get'
  })
}
