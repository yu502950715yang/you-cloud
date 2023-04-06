export default [
  {
    url: '/common/system/getAll',
    type: 'get',
    response: config => {
      return {
        code: 200,
        data: [
          {
            id: 0,
            name: '系统管理'
          },
          {
            id: 1,
            name: '文件定密'
          },
          {
            id: 2,
            name: '信息设备'
          },
          {
            id: 3,
            name: '风险评估'
          },
          {
            id: 4,
            name: '保密责任制'
          },
          {
            id: 5,
            name: '涉密人员'
          },
          {
            id: 6,
            name: '密品'
          },
          {
            id: 7,
            name: '综合事务'
          },
          {
            id: 8,
            name: '打印刻录'
          },
          {
            id: 9,
            name: '考试系统'
          }
        ]
      }
    }
  },

  {
    url: '/common/getWithoutSystem',
    type: 'get',
    response: config => {
      return {
        code: 200,
        data: {
          systems: [
            {
              id: 0,
              name: '系统管理'
            },
            {
              id: 1,
              name: '文件定密'
            },
            {
              id: 2,
              name: '信息设备'
            },
            {
              id: 3,
              name: '风险评估'
            },
            {
              id: 4,
              name: '保密责任制'
            },
            {
              id: 5,
              name: '涉密人员'
            },
            {
              id: 6,
              name: '密品'
            },
            {
              id: 7,
              name: '综合事务'
            },
            {
              id: 8,
              name: '打印刻录'
            },
            {
              id: 9,
              name: '考试系统'
            }
          ]
        }
      }
    }
  }
]
