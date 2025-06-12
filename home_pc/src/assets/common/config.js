// Browser environment
const inBrowser = typeof window !== 'undefined'
const UA = inBrowser && window.navigator.userAgent.toLowerCase()

let env = '' // local dev prod 本地开发环境、测试环境

let hostname = location.hostname
if (hostname.includes('admin.pkucare')) { // 正式环境
  env = 'prod'
} else if (hostname.includes('admintest.pkucare')) { // 测试环境
  env = 'dev'
} else { // 本地开发环境
  env = 'local'
}

window.$config = {
  env,
  code: process.env.VUE_APP_CODE,
  system: {
    isIE: UA && /msie|trident/.test(UA),
    isIE9: UA && UA.indexOf('msie 9.0') > 0, // ie9
    isEdge: UA && UA.indexOf('edge/') > 0 // ie10 以上
  }
}
