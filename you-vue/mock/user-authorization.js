import Mock from 'mockjs'
const { Random } = Mock
import { secretLevel } from './secretLevel'
import _ from 'lodash'

Random.extend({
  departments: [{ id: '1', name: '安全保密司' }, { id: '5', name: '领导办' },
    { id: '6', name: '所办' }, { id: '2', name: '党办' }, { id: '3', name: '投资办' }, { id: '4', name: '项目管理部' }],
  department: function(type, id) {
    if (type === 'id') {
      return this.pick(this.departments).id
    } else {
      return this.departments.find(i => i.id === id).name
    }
  },
  secretLevels: secretLevel,
  secretLevel: function(type, code) {
    if (type === 'code') {
      return this.pick(this.secretLevels).code
    } else {
      return this.secretLevels.find(i => i.code === code).name
    }
  }
})

const page = {}
let userList = []

export default [
  {
    url: '/system/userAuthorization/changeEnable',
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
  {
    url: '/system/userAuthorization/listPage',
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
  {
    url: '/userAuthorization/getDataPermission',
    type: 'post',
    response: config => {
      const data = userList.find(item => item.id === config.body.id)
      const dataPermission = Object.assign({}, data, {
        systems: Mock.mock([{
          id: '@id',
          name: '文件定密',
          'permission|0-3': 0,
          'departments': () => _.sampleSize(['1', '2', '3', '4', '5', '6'], Random.natural(0, 5))
        }, {
          id: '@id',
          name: '信息设备',
          'permission|0-3': 0,
          'departments': () => _.sampleSize(['1', '2', '3', '4', '5', '6'], Random.natural(0, 5))
        }, {
          id: '@id',
          name: '风险评估',
          'permission|0-3': 0,
          'departments': () => _.sampleSize(['1', '2', '3', '4', '5', '6'], Random.natural(0, 5))
        }, {
          id: '@id',
          name: '保密责任制',
          'permission|0-3': 0,
          'departments': () => _.sampleSize(['1', '2', '3', '4', '5', '6'], Random.natural(0, 5))
        }, {
          id: '@id',
          name: '涉密人员',
          'permission|0-3': 0,
          'departments': () => _.sampleSize(['1', '2', '3', '4', '5', '6'], Random.natural(0, 5))
        }, {
          id: '@id',
          name: '密品',
          'permission|0-3': 0,
          'departments': () => _.sampleSize(['1', '2', '3', '4', '5', '6'], Random.natural(0, 5))
        }, {
          id: '@id',
          name: '综合事务',
          'permission|0-3': 0,
          'departments': () => _.sampleSize(['1', '2', '3', '4', '5', '6'], Random.natural(0, 5))
        }, {
          id: '@id',
          name: '打印刻录',
          'permission|0-3': 0,
          'departments': () => _.sampleSize(['1', '2', '3', '4', '5', '6'], Random.natural(0, 5))
        }, {
          id: '@id',
          name: '考试系统',
          'permission|0-3': 0,
          'departments': () => _.sampleSize(['1', '2', '3', '4', '5', '6'], Random.natural(0, 5))
        }])
      })
      return {
        code: 200,
        data: dataPermission
      }
    }
  },
  {
    url: '/userAuthorization/updateAuthorization',
    type: 'post',
    response: config => {
      return {
        code: 200,
        message: '提交成功'
      }
    }
  }
]
