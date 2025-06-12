// axios
import request from '@/assets/common/request'

// 首页 推荐课程
export function getRemendCourse(data) {
  return request({
    url: '/knowledge/knowledge/home/list',
    method: 'get',
    params: data
  })
}

// 首页 轮播
export function getBannerList(data) {
  return request({
    url: '/system/banner/home/list',
    method: 'get',
    params: data
  })
}
