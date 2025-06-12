// axios
import request from '@/assets/common/request'
import qs from 'qs';
// 企业微信登录
export function wxlogin(data) {
  return request({
    url: '/wxLogin',
    method: 'get',
    params: data
  })
}
// 钉钉登录
export function ddlogin(data) {
  return request({
    url: '/dingLogin',
    method: 'get',
    params: data
  })
}
// 钉钉登录
export function getDingJsSignAgent(data) {
  return request({
    url: '/getDingJsSignAgent',
    method: 'post',
    data: data
  })
}
// 判断是否有管理员权限
export function isAdmin() {
  return request({
    url: '/getInfo',
    method: 'get'
  })
}
// 获取用户详情
export function getUserInfo(data) {
  return request({
    url: '/system/user/mineData',
    method: 'get',
    params: data
  })
}

// 上传头像图片
export function insertPicture(data) {
  return request({
    url: '/system/user/editAvatar',
    method: 'get',
    params: data
  })
}


// 学分记录
export function getCreditList(data) {
  return request({
    url: '/system/creditLog/home/list',
    method: 'get',
    params: data
  })
}
// 学分排行
export function getCreditRank(data) {
  return request({
    url: '/system/creditLog/home/creditList',
    method: 'get',
    params: data
  })
}

// 学分
export function getCreditInfo(data) {
  return request({
    url: '/system/creditLog/home/info',
    method: 'get',
    params: data
  })
}



// 网页登录
export function login(data) {
  return request({
    url: '/login',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}
