const state = {
  uploadData: {
  },
}

const mutations = {
  ADD_EXAM_FROM: (state, view) => {
    state.uploadData = view;
  },

  DEL_EXAM_FROM: (state, view) => {
    state.uploadData = {};
  },

  UPDATE_EXAM_FROM: (state, view) => {
    state.uploadData = view;
  },

}

const actions = {
  addView({ dispatch }, view) {
    dispatch('addUploadData', view)
  },

  addUploadData({ commit }, view) {
    // console.log('触发')
    commit('ADD_EXAM_FROM', view)
  },

  delView({ dispatch, state }, view) {
    return new Promise(resolve => {
      dispatch('delUploadData', view)
      resolve({
        uploadData: [...state.uploadData],
      })
    })
  },

  delUploadData({ commit, state }, view) {
    return new Promise(resolve => {
      commit('DEL_EXAM_FROM', view)
      resolve([...state.uploadData])
    })
  },

  updateUploadData({ commit }, view) {
    commit('UPDATE_EXAM_FROM', view)
  },

}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
