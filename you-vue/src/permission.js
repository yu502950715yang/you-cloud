import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist

const getSystemId = (router) => {
  return router.substring(1, router.substr(1).indexOf('/') + 1)
}

const setRouter = async(to, permissions) => {
  // 根路径时无需设置菜单
  if (to.path === '/') {
    return
  }
  const system = getSystemId(to.path)
  store.commit('system/SET_CURRENT_SYSTEM', system)
  const accessRoutes = await store.dispatch('permission/generateRoutes',
    {
      system: system,
      permissions
    })
  router.addRoutes(accessRoutes)
}

router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)
  // determine whether the user has logged in
  const hasToken = getToken()
  if (hasToken) {
    if (to.path === '/login') {
      // if is logged in, redirect to the home page
      next({ path: '/' })
      NProgress.done()
    } else if (to.path === '/') {
      const hasPermissions = store.getters.permissions && store.getters.permissions.length > 0
      if (!hasPermissions) {
        await store.dispatch('user/getInfo')
      }
      next()
      NProgress.done()
    } else {
      const hasPermissions = store.getters.permissions && store.getters.permissions.length > 0
      if (hasPermissions) {
        if (!store.getters.permission_routes || store.getters.permission_routes.length === 0) {
          await setRouter(to, store.getters.permissions)
          next({ ...to })
          return
        }
        next()
      } else {
        try {
          // get user info
          const { permissions } = await store.dispatch('user/getInfo')
          await setRouter(to, permissions)
          next({ ...to, replace: true })
        } catch (error) {
          // remove token and go to login page to re-login
          await store.dispatch('user/resetToken')
          Message.error(error.message || 'Has Error')
          next('/login')
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/

    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
