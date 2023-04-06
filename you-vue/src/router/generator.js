import constantRouterComponents from '@/router/modules'
import Layout from '@/layout'
import BlankLayout from '@/layout/BlankLayout'
import iconComponents from '@/icons/modules'
import { isExternal } from '@/utils/validate'

// 前端未找到页面路由（固定不用改）
export const notFoundRouter = {
  path: '*', redirect: '/404', hidden: true
}

export const homeRouter = (systemCode, routes) => {
  return [{
    path: `/${systemCode}`,
    component: Layout,
    hidden: true,
    children: [
      {
        path: `dashboard`,
        name: '首页',
        component: constantRouterComponents['systemDashboard'],
        meta: { title: '首页', icon: 'house', isElIcon: false }
      },
      ...routes
    ]
  }]
}

/**
 * 格式化 后端 结构信息并递归生成层级路由表
 *
 * @param routerMap
 * @param parent
 * @returns {*}
 */
export const generator = (routerMap, parent) => {
  return routerMap.map(item => {
    const path = item.path
    let component
    let props
    let icon
    let isElIcon
    // 如果配置地址为url则跳转固定展示iframe的组件
    if (isExternal(path)) {
      component = constantRouterComponents['IFrame']
      props = () => ({ url: encodeURIComponent(item.path) })
    } else {
      component = constantRouterComponents[item.key]
    }
    if (item.icon) {
      icon = item.icon
      isElIcon = true
    } else {
      icon = iconComponents[item.key] || undefined
      isElIcon = false
    }
    const currentRouter = {
      // 路由地址 动态拼接生成如 /dashboard/workplace
      path: `${item.key}`,
      // 路由名称，建议唯一
      name: item.name || item.key || '',
      // 该路由对应页面的 组件
      component: component || BlankLayout,
      // meta: 页面标题, 菜单图标, 页面权限(供指令权限用，可去掉)
      meta: { title: item.title, icon: icon, permissions: (item.permissions && item.permissions.split(',')) || null, systemId: item.systemId, isElIcon: isElIcon },
      props: props || null
    }
    // 重定向
    item.redirect && (currentRouter.redirect = item.redirect)
    // 是否有子菜单，并递归处理
    if (item.children && item.children.length > 0) {
      // Recursion
      currentRouter.children = generator(item.children, currentRouter)
    }
    return currentRouter
  })
}
