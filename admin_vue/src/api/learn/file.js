import request from '@/utils/request'

// 查询素材库列表
export function listFile(query) {
  return request({
    url: '/knowledge/file/list',
    method: 'get',
    params: query
  })
}

// 查询素材库详细
export function getFile(id) {
  return request({
    url: '/knowledge/file/' + id,
    method: 'get'
  })
}

// 新增素材库
export function addFile(data) {
  return request({
    url: '/knowledge/file',
    method: 'post',
    data: data
  })
}

// 修改素材库
export function updateFile(data) {
  return request({
    url: '/knowledge/file',
    method: 'put',
    data: data
  })
}

// 删除素材库
export function delFile(id) {
  return request({
    url: '/knowledge/file/' + id,
    method: 'delete'
  })
}
