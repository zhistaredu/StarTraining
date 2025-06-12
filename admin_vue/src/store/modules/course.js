const state = {
  courseInfo: {}
}

const mutations = {
  ADD_COURSE_FROM: (state, view) => {
    state.courseInfo = view;
  },

  DEL_COURSE_FROM: (state, view) => {
    state.courseInfo = {};
  },

  UPDATE_COURSE_FROM: (state, view) => {
    state.courseInfo = view;
  }
}

const actions = {
  addView({ dispatch }, view) {
    dispatch('addCourseInfo', view)
  },

  addCourseInfo({ commit }, view) {
    commit('ADD_COURSE_FROM', view)
  },

  delView({ dispatch, state }, view) {
    return new Promise(resolve => {
      dispatch('delCourseInfo', view)
      resolve({
        courseInfo: [...state.courseInfo],
      })
    })
  },

  delCourseInfo({ commit, state }, view) {
    return new Promise(resolve => {
      commit('DEL_COURSE_FROM', view)
      resolve([...state.courseInfo])
    })
  },

  updateCourseInfo({ commit }, view) {
    commit('UPDATE_COURSE_FROM', view)
  },
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
