export default [
  {
    url: '/system/log/listPage',
    type: 'post',
    response: config => {
      return {
        code: 200,
        data: {
          current: 1,
          total: 1,
          records: [{
            'username': 'admin',
            'systemName': '系统管理',
            'result': null,
            'remark': '将【123(123)】的IC卡号由【null】改为【null】',
            'name': null,
            'ip': '127.0.0.1',
            'id': null,
            'module': '用户管理',
            'handle': '编辑',
            'createTime': '2019-11-26T14:30:04.498'
          }, {
            'username': 'admin',
            'systemName': '系统管理',
            'result': null,
            'remark': '新建【123(123)】',
            'name': null,
            'ip': '127.0.0.1',
            'id': null,
            'module': '用户管理',
            'handle': '新增',
            'createTime': '2019-11-26T14:27:16.928'
          }, {
            'username': 'admin',
            'systemName': '系统管理',
            'result': null,
            'remark': '将【切问而近思的(qwjeq)】的用户姓名由【切问而近思的】改为【切问而近思的1】，IC卡号由【null】改为【null】',
            'name': null,
            'ip': '127.0.0.1',
            'id': null,
            'module': '用户管理',
            'handle': '编辑',
            'createTime': '2019-11-26T14:27:08.321'
          }
          ] }
      }
    }
  }
]
