import request from '@/utils/request'

export function getBanner(query) {
  return request({
    url: '/system/banner/info',
    method: 'get',
    params: query
  })
}

export function addBanner(data) {
  return request({
    url: '/system/banner',
    method: 'post',
    data: data
  })
}
