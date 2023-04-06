const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  name: state => state.user.name,
  permissions: state => state.user.permissions,
  fullRoutes: state => state.permission.routes,
  permission_routes: state => state.permission.addRoutes,
  currentSystemId: state => state.system.currentSystemId,
  currentSystemCode: state => state.system.currentSystemCode
}
export default getters
