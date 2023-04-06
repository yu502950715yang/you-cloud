const state = {
  systems: {
    'system': 1
  },
  currentSystemId: -1,
  currentSystemCode: ''
}
const mutations = {
  SET_CURRENT_SYSTEM: (state, currentSystem) => {
    state.currentSystemId = state.systems[currentSystem]
    state.currentSystemCode = currentSystem
  }
}
const actions = {}
export default {
  namespaced: true,
  state,
  mutations,
  actions
}
