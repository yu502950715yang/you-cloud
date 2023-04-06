import IFrame from '@/views/IFrame'
import systemConstantRouterComponents from './system'

// 前端路由表
export default {
  // 基础页面 layout 必须引入
  IFrame: IFrame,
  ...systemConstantRouterComponents
}
