import Vue from 'vue'

import Cookies from 'js-cookie'

import Element from 'element-ui'

import './assets/styles/element-variables.scss'

import '@/assets/styles/index.scss' // global css
import '@/assets/styles/ruoyi.scss' // ruoyi css
import App from './App'
import store from './store'
import router from './router'
import directive from './directive' // directive
import plugins from './plugins' // plugins
import { download } from '@/utils/request'
import '@/utils/mixin.js'

import '@static/js/ueditor/ueditor.config.js'
import '@static/js/ueditor/ueditor.all.min.js'
import '@static/js/ueditor/lang/zh-cn/zh-cn.js'
import '@static/js/ueditor/ueditor.parse.min.js'
// 编辑器样式
import "@static/js/ueditor/themes/default/css/ueditor.css";

import './assets/icons' // icon
import './permission' // permission control
import { getConfigKey } from "@/api/system/config";
import { parseTime, resetForm, addDateRange, selectDictLabel, selectDictLabels, handleTree } from "@/utils/ruoyi";
// 分页组件
import Pagination from "@/components/Pagination";
// 自定义表格工具组件
import RightToolbar from "@/components/RightToolbar"
// 文件上传组件
import FileUpload from "@/components/FileUpload"
// 图片上传组件
import ImageUpload from "@/components/ImageUpload"
// 图片预览组件
import ImagePreview from "@/components/ImagePreview"
// 头部标签组件
import VueMeta from 'vue-meta'
// 字典数据组件
import WDialog from '@/components/Common/WDialog'
import Ellipsis from '@/components/Common/Ellipsis'
// 懒加载
import VueLazyload from 'vue-lazyload'
//引入jquery
import $ from 'jquery'
//多语言
import VueI18n from 'vue-i18n';

import DWAPI from '@/assets/js/platform-jsapi/index';
/** 将封装好的jsapi，赋值到全局属性 */
const dw = new DWAPI();

/** 动态引入js文件 */
dw.createJsApiScript();
Vue.prototype.dw = dw;


// 导入全局组件
import wwOpenData from '@/page/components/openData/wwOpenData.vue';

// 注册全局组件
Vue.component('OpenDataCom', wwOpenData);

// 全局方法挂载
Vue.prototype.getConfigKey = getConfigKey
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.selectDictLabels = selectDictLabels
Vue.prototype.download = download
Vue.prototype.handleTree = handleTree

Vue.prototype.$isNotEmpty = function(obj){
  return (obj !== undefined && obj !== null && obj !== '' && obj !== 'null')
}

Vue.prototype.$getDefalut = function(obj, key, df){
  return (obj === undefined || key === undefined || !this.$isNotEmpty(obj[key])) ? df : obj[key];
}

Vue.prototype.$deepCopy = function (obj){return JSON.parse(JSON.stringify(obj))}

// 全局组件挂载
Vue.component('Pagination', Pagination)
Vue.component('RightToolbar', RightToolbar)
Vue.component('FileUpload', FileUpload)
Vue.component('ImageUpload', ImageUpload)
Vue.component('ImagePreview', ImagePreview)

Vue.use(VueLazyload, {
  preLoad: 1.3,
  error: require('./assets/images/no.png'),
  loading: require('./assets/images/moren.jpg'),
  attempt: 1,
  listenEvents: ['scroll', 'wheel', 'mousewheel', 'resize', 'animationend', 'transitionend', 'touchmove']
})

Vue.use(directive)
Vue.use(plugins)
Vue.use(VueMeta)
Vue.use(VueI18n);
Vue.use(WDialog);
Vue.use(Ellipsis);

// 调试功能，上线要记得关闭
// import Vconsole from 'vconsole'
// if (process.env.ENV !== "production") {
//   new Vconsole()
// }

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
