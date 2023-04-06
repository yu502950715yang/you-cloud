import Mock from 'mockjs'
import _ from 'lodash'

const menus = {}
const menuMap = {}

export default [
  {
    url: '/system/menu/list',
    type: 'get',
    response: config => {
      return {
        code: 200,
        data: {
          menus: menus
        }
      }
    }
  },
  {
    url: '/system/menu/add',
    type: 'post',
    response: config => {
      const menu = Mock.mock({
        id: '@id',
        label: config.body.menuName,
        parentId: config.body.parentId,
        systemId: config.body.systemId,
        children: []
      })
      if (!menuMap[config.body.systemId]) {
        menuMap[config.body.systemId] = {}
      }
      menuMap[config.body.systemId][menu.id] = menu
      if (config.body.parentId <= 0) {
        if (!menus[config.body.systemId]) {
          menus[config.body.systemId] = []
        }
        menus[config.body.systemId].push(menu)
      } else {
        menuMap[config.body.systemId][config.body.parentId].children.push(menu)
      }
      return {
        code: 200,
        message: '新增成功'
      }
    }
  },
  {
    url: '/system/menu/remove',
    type: 'post',
    response: config => {
      const id = config.body.id
      _.forOwn(menuMap, (value) => {
        _.forOwn(value, (item) => {
          const children = item.children
          const index = children.findIndex(d => d.id === id)
          if (index !== -1) {
            children.splice(index, 1)
          }
        })
      })
      return {
        code: 200,
        message: '删除成功'
      }
    }
  },
  {
    url: '/system/menu/getRoutes',
    type: 'get',
    response: config => {
      return {
        code: 200,
        data: [
          {
            'title': '系统管理',
            'name': '系统管理',
            'key': 'system',
            'children': [
              {
                'title': '',
                'name': '',
                'key': 'user',
                'permissions': 'system:user:open'
              },
              {
                'title': '用户授权',
                'name': '用户授权',
                'key': 'user-authorization',
                'permissions': 'system:userAuthorization:open'
              },
              {
                'title': '菜单管理',
                'name': '菜单管理',
                'key': 'menu',
                'permissions': 'system:menu:open'
              },
              {
                'title': '部门管理',
                'name': '部门管理',
                'key': 'department',
                'permissions': 'system:department:open'
              },
              {
                'title': '角色管理',
                'name': '角色管理',
                'key': 'role',
                'permissions': 'system:role:open'
              },
              {
                'title': '角色授权',
                'name': '角色授权',
                'key': 'role-authorization',
                'permissions': 'system:roleAuthorization:open'
              },
              {
                'title': '操作日志',
                'name': '操作日志',
                'key': 'log',
                'permissions': 'system:log:open'
              },
              {
                'key': 'baidu',
                'title': '测试打开百度',
                'name': '测试打开百度',
                'path': 'http://www.baidu.com',
                'permissions': 'system:role:open'
              }
            ]
          }
        ]
      }
    }
  }
]
