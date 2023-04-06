export default [
  {
    url: '/system/roleAuthorization/listPage',
    type: 'get',
    response: config => {
      return {
        code: 200,
        data: {
          currentPage: 1,
          total: 1,
          pageData: [{
            id: '1',
            roleName: '超级管理员',
            systemList: [{ id: '1', name: '系统管理' }],
            menu: [{
              id: 1,
              label: '一级 1',
              children: [{
                id: 4,
                label: '二级 1-1',
                children: [{
                  id: 9,
                  label: '三级 1-1-1'
                }, {
                  id: 10,
                  label: '三级 1-1-2'
                }]
              }]
            }, {
              id: 2,
              label: '一级 2',
              children: [{
                id: 5,
                label: '二级 2-1'
              }, {
                id: 6,
                label: '二级 2-2'
              }]
            }, {
              id: 3,
              label: '一级 3',
              children: [{
                id: 7,
                label: '二级 3-1'
              }, {
                id: 8,
                label: '二级 3-2'
              }]
            }],
            users: [{
              value: '1',
              label: '张三'
            }, {
              value: '2',
              label: '李四'
            }, {
              value: '3',
              label: '王五'
            }, {
              value: '4',
              label: '赵六'
            }, {
              value: '5',
              label: '史七'
            }]
          }],
          chooseData: []
        }
      }
    }
  },
  {
    url: '/roleAuthorization/updateAuthorization',
    type: 'post',
    response: config => {
      return {
        code: 200,
        message: '提交成功'
      }
    }
  }
]
