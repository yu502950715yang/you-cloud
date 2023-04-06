export const secretLevel = [{ code: 10, name: '公开' }, { code: 20, name: '公开' }, { code: 29, name: '普通商密' },
  { code: 30, name: '秘密' }, { code: 39, name: '核心商密' }, { code: 40, name: '机密' }, { code: 50, name: '绝密' }]

export default [
  {
    url: '/common/secretLevel',
    type: 'get',
    response: config => {
      return {
        code: 200,
        data: {
          secretLevel: secretLevel
        }
      }
    }
  }
]
