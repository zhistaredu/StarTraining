import axios from 'axios'
import {
  Notification,
  MessageBox,
  Message,
  Loading
} from 'element-ui'
import errorCode from '../utils/errorCode'
import {
  tansParams,
  blobValidate
} from "./set";
import cache from '@/plugins/cache'
import qs from 'qs';
// 是否显示重新登录
let isReloginShow;

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: process.env.VUE_APP_BASE_API,
  // 超时
  timeout: 10000
})

// request拦截器
service.interceptors.request.use(config => {
  // 是否需要设置 token
  const isToken = (config.headers || {}).isToken === false
  // 是否需要防止数据重复提交
  const isRepeatSubmit = (config.headers || {}).repeatSubmit === false
  let authorization = window.localStorage.getItem('AuthorizationToken');
  if (authorization && !isToken) {
    config.headers['Authorization'] = authorization // 让每个请求携带自定义token 请根据实际情况自行修改
  }
  // get请求映射params参数
  if (config.method === 'get' && config.params) {
    let url = config.url + '?' + tansParams(config.params);
    url = url.slice(0, -1);
    config.params = {};
    config.url = url;
  }

  if (!isRepeatSubmit && (config.method === 'post' || config.method === 'put')) {
    const requestObj = {
      url: config.url,
      data: typeof config.data === 'object' ? qs.stringify(config.data) : config.data,
      time: new Date().getTime()
    }
    const sessionObj = cache.session.getJSON('sessionObj')
    if (sessionObj === undefined || sessionObj === null || sessionObj === '') {
      cache.session.setJSON('sessionObj', requestObj)
    } else {
      const s_url = sessionObj.url; // 请求地址
      const s_data = sessionObj.data; // 请求数据
      const s_time = sessionObj.time; // 请求时间
      const interval = 1000; // 间隔时间(ms)，小于此时间视为重复提交
      if (s_data === requestObj.data && requestObj.time - s_time < interval && s_url === requestObj.url) {
        const message = '数据正在处理，请勿重复提交';
        console.warn(`[${s_url}]: ` + message)
        return Promise.reject(new Error(message))
      } else {
        cache.session.setJSON('sessionObj', requestObj)
      }
    }
  }
  return config
}, error => {
  console.log(error)
  Promise.reject(error)
})

// 响应拦截器
service.interceptors.response.use(res => {
    // 未设置状态码则默认成功状态
    const resdata = res.data
    const code = res.data.code || 200;
    // 获取错误信息
    const msg = errorCode[code] || res.data.msg || errorCode['default']
    // 二进制数据则直接返回
    if (res.request.responseType === 'blob' || res.request.responseType === 'arraybuffer') {
      return res.data
    }
    if (resdata.temp == 'j') {
      if (code === 401) {
        if (!isReloginShow) {
          isReloginShow = true;
          MessageBox.confirm('报错了', '系统提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            isReloginShow = false;

          }).catch(() => {
            isReloginShow = false;

          });
        }
        return Promise.reject('无效的会话，或者会话已过期，请重新登录。')
      } else if (code === 500) {
        Message({
          message: msg,
          type: 'error'
        })
        return Promise.reject(new Error(msg))
      } else if (code == 200 || code == 201) {
        return resdata

      } else {
        Message({
          message: msg,
          type: 'error'
        })
        return Promise.reject(new Error(msg))

      }
    }else{
      if (resdata.status && resdata.status !== 'ok') {
        if (resdata.status == '401') {
          return Promise.resolve(resdata)
        } else {
          return Promise.reject(resdata || 'error')
        }

      } else {
        return Promise.resolve(resdata)
      }
    }
  },
  error => {
    console.log('err' + error)
    let {
      message
    } = error;
    if (message == "Network Error") {
      message = "后端接口连接异常";
    } else if (message.includes("timeout")) {
      message = "系统接口请求超时";
    } else if (message.includes("Request failed with status code")) {
      message = "系统接口" + message.substr(message.length - 3) + "异常";
    }
    Message({
      message: message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)



export default service
