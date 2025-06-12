import request from '@/utils/request'

export function listJavaCategory(query) {
  return request({
    url: '/system/category/list',
    method: 'get',
    params: query
  })
}

// 获取分类明细
export function getCategoryDetail(categoryId) {
  return request({
    url: '/system/category/' + categoryId,
    method: 'get'
  })
}

export function addCategory(data) {
  return request({
    url: '/system/category',
    method: 'post',
    data: data
  })
}

// 修改部门
export function updateCategory(data) {
  return request({
    url: '/system/category',
    method: 'put',
    data: data
  })
}

// 删除部门
export function delCategory(categoryId) {
  return request({
    url: '/system/category/' + categoryId,
    method: 'delete'
  })
}