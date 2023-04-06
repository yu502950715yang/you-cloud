export default [
  {
    url: '/userDepartment/getAll',
    type: 'get',
    response: config => {
      return {
        code: 200,
        data: [
          {
            userId: '1',
            userName: 'admin',
            departmentId: '1'
          },
          {
            userId: '2',
            userName: '测试账号',
            departmentId: '1'
          },
          {
            userId: '3',
            userName: 'admin1',
            departmentId: '2'
          },
          {
            userId: '4',
            userName: '测试账号2',
            departmentId: '2'
          },
          {
            userId: '5',
            userName: '测试账号4',
            departmentId: '1'
          },
          {
            userId: '6',
            userName: '测试账号5',
            departmentId: '2'
          }
        ]
      }
    }
  }
]

