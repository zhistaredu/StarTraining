import request from '@/utils/request'

// 查询1212列表
export function listAdmin(query) {
  return request({
    url: '/system/admin/list',
    method: 'get',
    params: query
  })
}

// 查询1212详细
export function getAdmin(id) {
  return request({
    url: '/system/admin/' + id,
    method: 'get'
  })
}

export function getCompanyData() {
  return request({
    url: '/system/user/getCompanyData',
    method: 'get'
  })
}

// 新增1212
export function addAdmin(data) {
  return request({
    url: '/system/admin',
    method: 'post',
    data: data
  })
}

// 修改1212
export function updateAdmin(data) {
  return request({
    url: '/system/admin',
    method: 'put',
    data: data
  })
}

// 删除1212
export function delAdmin(id) {
  return request({
    url: '/system/admin/' + id,
    method: 'delete'
  })
}

export function getWorkbench() {
  return request({
    url: '/statistics/dataCenter',
    method: 'get'
  })
}

export function learningProject() {
  return request({
    url: '/statistics/dataCenter/learningProject',
    method: 'get'
  })
}
