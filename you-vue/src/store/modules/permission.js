import { constantRoutes } from '@/router'
import { generator, notFoundRouter, homeRouter } from '@/router/generator'
import { getRoutes } from '~/system/menu'

/**
 * Use meta.permission to determine if the current user has permission
 * @param permissions
 * @param route
 */
function hasPermission(permissions, route) {
  if (route.meta && route.meta.permissions) {
    if (permissions) {
      return permissions.some(permission => route.meta.permissions.includes(permission))
    } else {
      return null
    }
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param permissions
 */
export function filterAsyncRoutes(routes, permissions) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (tmp.children) {
      tmp.children = filterAsyncRoutes(tmp.children, permissions)
    }
    if (hasPermission(permissions, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, permissions)
      }
      res.push(tmp)
    }
  })

  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, data) {
    const { system, permissions } = data
    return new Promise(resolve => {
      getRoutes({ systemId: system }).then(({ code, data }) => {
        const asyncRoutes = generator(data)
        let accessedRoutes = filterAsyncRoutes(asyncRoutes, permissions)
        accessedRoutes = homeRouter(system, accessedRoutes)
        accessedRoutes.push(notFoundRouter)
        commit('SET_ROUTES', accessedRoutes[0].children)
        resolve(accessedRoutes)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
