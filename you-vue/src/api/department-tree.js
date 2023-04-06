import request from '@/utils/request'

export function getDepartments(data) {
  return request({
    url: '/system/department/getAll',
    method: 'get',
    data,
    headers: { 'showLoading': false }
  }).then(res => createTree('0', res.data))
}

function createTree(pid, departments) {
  const treeDepartment = []
  departments.forEach(item => {
    if (pid === item.parentId) {
      const department = {
        id: item.id,
        name: item.name,
        children: undefined
      }
      treeDepartment.push(department)
      department.children = createTree(item.id, departments)
    }
  })
  if (treeDepartment.length === 0) {
    return undefined
  }
  return treeDepartment
}
