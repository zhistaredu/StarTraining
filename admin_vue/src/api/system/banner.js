import request from '@/utils/request'

// 查询部门列表
export function listBanner(query) {
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
