import Mock from 'mockjs'
const { Random } = Mock

const tokens = {
  admin: {
    token: 'admin-token'
  },
  editor: {
    token: 'editor-token'
  }
}

const users = {
  'admin-token': {
    permissions: ['system:user:open', 'system:user2:open', 'system:userAuthorization:open', 'system:menu:open', 'system:department:open', 'system:role:open', 'system:roleAuthorization:open', 'system:log:open'],
    introduction: 'I am a super administrator',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Super Admin'
  },
  'editor-token': {
    permissions: ['editor'],
    introduction: 'I am an editor',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Normal Editor'
  }
}

const page = {}
let userList = []

export default [
  {
    url: '/system/user/changeEnable',
    type: 'post',
    response: config => {
      console.log(config)
      console.log(config.body)
      const { id, enable } = config.query
      const user = userList.find(item => item.id === id)
      if (enable === '1') {
        user.enable = '0'
        return {
          code: 200,
          message: '账号已禁用'
        }
      } else {
        user.enable = '1'
        return {
          code: 200,
          message: '账号已启用'
        }
      }
    }
  },
  // user login
  {
    url: '/login',
    type: 'post',
    response: config => {
      const { username } = config.body
      const token = tokens[username]

      // mock error
      if (!token) {
        return {
          code: 60204,
          message: 'Account and password are incorrect.'
        }
      }

      return {
        code: 200,
        data: token
      }
    }
  },

  // get user info
  {
    url: '/user/info\.*',
    type: 'get',
    response: config => {
      const { token } = config.query
      const info = users[token]

      // mock error
      if (!info) {
        return {
          code: 50008,
          message: 'Login failed, unable to get user details.'
        }
      }

      return {
        code: 200,
        data: info
      }
    }
  },

  {
    url: '/user/listPage',
    type: 'post',
    response: config => {
      if (page[config.query.currentPage]) {
        userList = page[config.query.currentPage]
      } else {
        userList = page[config.query.currentPage] = Mock.mock({
          'items|10': [{
            sort: null,
            username: () => Random.string(),
            updateTime: null,
            token: null,
            secretLevelCode: '10',
            password: null,
            name: () => Random.cname(),
            isDel: null,
            isAdmin: null,
            idNum: '2223232',
            id: '@id',
            enable: '1',
            createTime: null,
            cardNum: null,
            departmentList: [{
              parentId: null,
              parentsId: null,
              name: '保密部',
              id: '1',
              sort: null,
              lv: null
            }],
            secretLevel: {
              name: '公开',
              id: '1',
              code: '10'
            }
          }]
        }).items
      }

      return {
        code: 200,
        data: {
          records: userList,
          total: 1,
          size: 10,
          current: 1,
          orders: [],
          searchCount: true,
          pages: 1
        }
      }
    }
  },

  // user logout
  {
    url: '/user/logout',
    type: 'post',
    response: _ => {
      return {
        code: 20000,
        data: 'success'
      }
    }
  },

  {
    url: '/user/add',
    type: 'post',
    response: _ => {
      return {
        code: 200,
        data: {}
      }
    }
  },

  {
    url: '/user/getUser/\.*',
    type: 'get',
    response: _ => {
      return {
        code: 200,
        data: {
          'sort': null,
          'username': 'md5',
          'updateTime': null,
          'token': null,
          'secretLevelCode': null,
          'password': null,
          'name': '测试密码',
          'isDel': null,
          'isAdmin': null,
          'idNum': '222323232323232',
          'id': '27',
          'enable': null,
          'createTime': null,
          'cardNum': '123213123',
          'departmentList': [{
            'parentId': null,
            'parentsId': null,
            'name': '保密部',
            'id': '1',
            'sort': null,
            'lv': null
          }],
          'roleList': [],
          'permissions': null
        }
      }
    }
  },

  {
    url: '/user/edit',
    type: 'post',
    response: _ => {
      return {
        code: 200,
        data: {}
      }
    }
  },

  {
    url: '/user/resetPassword\.*',
    type: 'get',
    response: _ => {
      return {
        code: 200,
        data: {}
      }
    }
  }
]
