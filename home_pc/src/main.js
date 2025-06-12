// 全局配置、公共文件
import './assets/common/index.js';
import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';


// 调试工具 上线要隐藏
import Vconsole from 'vconsole'
if (process.env.NODE_ENV !== "production") {
  // new Vconsole()
}


import VideoPlayer from 'vue-video-player'

//引入样式

import 'vue-video-player/src/custom-theme.css'

import 'video.js/dist/video-js.css'

import 'videojs-contrib-hls'

//使用组件

Vue.use(VideoPlayer)

// 引入 ElementUI 组件库
import ElementUI from 'element-ui';
// import './assets/element-variables.scss'
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);
import Base64 from 'Base64'
Vue.prototype.$Base64 = Base64;

// 关闭提示
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
