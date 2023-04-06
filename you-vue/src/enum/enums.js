import { Enum } from './Enum.js'

/**
 * 全局公共枚举类
 */
export default {
  // 账号状态
  userEnableEnum: new Enum()
    .add('ENABLE', '启用', 1)
    .add('DISABLE', '禁用', 0),
  // 账号状态
  EnableStatusEnum: new Enum()
    .add('ENABLE', '启用', 1)
    .add('DISABLE', '禁用', 0),
  // 部门状态
  organizationStatusEnum: new Enum()
    .add('ENABLE', '启用', 1)
    .add('DISABLE', '禁用', 0),

  // 模拟类型
  simulationDataType: new Enum()
    .add('FIXED', '固定', 'fixed')
    .add('RANDOM', '随机', 'random')
    .add('INCREASE', '递增', 'increase')
    .add('RULE', '规则', 'rule')
    .add('LEXICON', '词库', 'lexicon')
    .add('NO_SIMULATION', '不模拟', 'noSimulation')
}

