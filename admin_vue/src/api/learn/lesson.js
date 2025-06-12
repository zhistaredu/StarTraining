import request from '@/utils/request'

// 查询素材库列表
export function listLesson(query) {
  return request({
    url: '/learn/lesson/list',
    method: 'get',
    params: query
  })
}

// 查询素材库详细
export function getLesson(id) {
  return request({
    url: '/learn/lesson/' + id,
    method: 'get'
  })
}

// 新增素材库
export function addLesson(data) {
  return request({
    url: '/learn/lesson',
    method: 'post',
    data: data
  })
}

// 修改素材库
export function updateLesson(data) {
  return request({
    url: '/learn/lesson',
    method: 'put',
    data: data
  })
}

// 删除素材库
export function delLesson(id) {
  return request({
    url: '/learn/lesson/' + id,
    method: 'delete'
  })
}

// 批量移动课件分类
export function addMove(data) {
  return request({
    url: '/learn/lesson/addMove',
    method: 'post',
    data: data
  })
}