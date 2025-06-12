import Vue from 'vue'
import axios from 'axios'

// 定义全局公用ajax
Object.defineProperty(Vue.prototype, '$axios', { value: axios });

// 是否携带cookie信息
axios.defaults.withCredentials = true;

axios.interceptors.request.use(req => { // 添加请求拦截器
  return req;
}, err => {
  return Promise.reject(err);
})

axios.interceptors.response.use(res => { // 添加响应拦截器
  if (res.data && res.data.code >= 400) {
    Vue.prototype.$message.error(res.data.msg);
  }

  return res.data;
}, err => {
  Promise.reject(err);
})

// axios.defaults.transformRequest = [function (data) { // 用于请求之前对请求数据进行操作
//   var ret = []
//   for (var it in data) {
//     ret.push(encodeURIComponent(it) + '=' + encodeURIComponent(data[it]))
//   }
//   return ret.join('&')
// }]
