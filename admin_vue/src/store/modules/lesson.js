const state = {
  lessonInfo: {}
}

const mutations = {
  ADD_LESSON_FROM: (state, view) => {
    state.lessonInfo = view;
  },

  DEL_LESSON_FROM: (state, view) => {
    state.lessonInfo = {};
  },

  UPDATE_LESSON_FROM: (state, view) => {
    state.lessonInfo = view;
  }
}

const actions = {
  addView({ dispatch }, view) {
    dispatch('addLessonInfo', view)
  },

  addLessonInfo({ commit }, view) {
    commit('ADD_LESSON_FROM', view)
  },

  delView({ dispatch, state }, view) {
    return new Promise(resolve => {
      dispatch('delLessonInfo', view)
      resolve({
        lessonInfo: [...state.lessonInfo],
      })
    })
  },

  delLessonInfo({ commit, state }, view) {
    return new Promise(resolve => {
      commit('DEL_LESSON_FROM', view)
      resolve([...state.lessonInfo])
    })
  },

  updateLessonInfo({ commit }, view) {
    commit('UPDATE_LESSON_FROM', view)
  },
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
