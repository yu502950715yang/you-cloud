export default {
  // 你需要动态引入的页面组件
  systemDashboard: () => import('@/views/dashboard/index'),
  user: () => import('@/views/user/index'),
  menu: () => import('@/views/menu/index'),
  role: () => import('@/views/role/index'),
  globalParam: () => import('@/views/globalParam/index'),
  log: () => import('@/views/log/index'),
  dict: () => import('@/views/dict/index'),
  organization: () => import('@/views/organization/index'),
  upload: () => import('@/views/upload/index'),
  formGenerator: () => import('@/views/formGenerator/index'),
  druid: () => import('@/views/monitor/druid/index'),
  generate: () => import('@/views/generate/index'),
  notice: () => import('@/views/notice/index'),
  server: () => import('@/views/server/index'),

  // 数据模拟
  dataCreate: () => import('@/views/dataCreate/index'),

  // 工作流
  todoList: () => import('@/views/workflow/todoList/index'),
  myApplication: () => import('@/views/workflow/myApplication/index'),
  processDeployment: () => import('@/views/workflow/processDeployment/index'),
  leaveProcess: () => import('@/views/workflow/leaveProcess/index'),
  workflowAalready: () => import('@/views/workflow/already/index'),
  lexicon: () => import('@/views/lexicon/index')
}
