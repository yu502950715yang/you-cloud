export default [
  {
    url: '/system/role/list',
    type: 'post',
    response: config => {
      return {
        code: 200,
        msg: '',
        data: {
          records: [{
            name: '1111',
            isDel: null,
            id: '7'
          },
          {
            name: '账户中',
            isDel: null,
            id: '6'
          },
          {
            name: '张',
            isDel: null,
            id: '5'
          }],
          total: 7,
          size: 10,
          current: 1,
          orders: [],
          searchCount: true,
          pages: 1 }}
    }
  },
  {
    url: '/system/role/add',
    type: 'post',
    response: config => {
      return {
        code: 200,
        message: '提交成功'
      }
    }
  },
  {
    url: '/system/role/edit',
    type: 'post',
    response: config => {
      return {
        code: 200,
        message: '提交成功'
      }
    }
  },
  {
    url: '/system/role/delete\.*',
    type: 'get',
    response: config => {
      return {
        code: 200,
        message: '删除成功'
      }
    }
  }
]
