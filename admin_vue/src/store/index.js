import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import user from './modules/user'
import course from './modules/course'
import upload from './modules/upload'
import tagsView from './modules/tagsView'
import permission from './modules/permission'
import settings from './modules/settings'
import lesson from './modules/lesson'
import getters from './getters'
import state from './state';
import mutations from './mutations';
import actions from './actions';

Vue.use(Vuex)

const store = new Vuex.Store({
  state,
  mutations,
  actions,
  modules: {
    app,
    user,
    tagsView,
    permission,
    settings,
    course,
    lesson
  },
  getters
})

export default store
