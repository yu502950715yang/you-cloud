export default [
  {
    url: '/department/getAll',
    type: 'get',
    response: config => {
      return {
        code: 200,
        data: [
          {
            id: '1',
            name: '安全保密司',
            parentId: '-1'
          },
          {
            id: '5',
            name: '领导办',
            parentId: '1'
          },
          {
            id: '6',
            name: '所办',
            parentId: '1'
          },
          {
            id: '2',
            name: '党办',
            parentId: '-1'
          },
          {
            id: '3',
            name: '投资办',
            parentId: '-1'
          },
          {
            id: '4',
            name: '项目管理部',
            parentId: '3'
          },
          {
            id: '14',
            name: '安全保密司1',
            parentId: '0'
          },
          {
            id: '24',
            name: '党办1',
            parentId: '0'
          },
          {
            id: '123',
            name: '投资办1',
            parentId: '0'
          }
        ]
      }
    }
  }
]

