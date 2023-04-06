import Mock from 'mockjs'
import _ from 'lodash'

const departments = []
const departmentMap = {}

export default [
  {
    url: '/system/department/getAll',
    type: 'get',
    response: config => {
      return {
        code: 200,
        data: {
          departments: departments
        }
      }
    }
  },
  {
    url: '/system/department/add',
    type: 'post',
    response: config => {
      const department = Mock.mock({
        id: '@id',
        label: config.body.departmentName,
        children: []
      })
      departmentMap[department.id] = department
      if (config.body.parentId === 0) {
        departments.push(department)
      } else {
        departmentMap[config.body.parentId].children.push(department)
      }
      return {
        code: 200,
        message: '新增成功'
      }
    }
  },
  {
    url: '/system/department/remove',
    type: 'post',
    response: config => {
      const id = config.body.id
      _.forOwn(departmentMap, (value) => {
        const children = value.children
        const index = children.findIndex(d => d.id === id)
        if (index !== -1) {
          children.splice(index, 1)
        }
      })
      return {
        code: 200,
        message: '删除成功'
      }
    }
  }
]
