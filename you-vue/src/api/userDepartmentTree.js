
export function getTreeData(user, department) {
  const treeData = []
  let id = -1
  if (department) {
    department.data.forEach(item => {
      const data = {
        id: id--,
        name: item.name,
        parentId: item.parentId,
        trueId: item.id,
        children: []
      }
      treeData.push(data)
    })
  }
  if (user) {
    user.data.forEach(item => {
      const data = {
        id: id--,
        name: item.userName,
        parentId: item.departmentId,
        trueId: item.userId,
        children: undefined
      }
      treeData.push(data)
    })
  }
  return createUserTree('0', treeData)
}
function createUserTree(pid, treeData) {
  const result = []
  treeData.forEach(item => {
    if (pid === item.parentId) {
      result.push(item)
      if (item.children !== undefined) {
        item.children = createUserTree(item.trueId, treeData)
      }
    }
  })
  if (result.length === 0) {
    return undefined
  }
  return result
}
