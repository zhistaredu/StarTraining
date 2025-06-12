import request from '@/utils/request'

// 查询角色列表
export function listUserGroup(query) {
  return request({
    url: '/system/userGroup/list',
    method: 'get',
    params: query
  })
}

// 查询角色详细
export function getUserGroup(groupId) {
  return request({
    url: '/system/userGroup/' + groupId,
    method: 'get'
  })
}

// 新增角色
export function addUserGroup(data) {
  return request({
    url: '/system/userGroup',
    method: 'post',
    data: data
  })
}

// 修改角色
export function updateUserGroup(data) {
  return request({
    url: '/system/userGroup',
    method: 'put',
    data: data
  })
}

// 删除角色
export function delUserGroup(groupId) {
  return request({
    url: '/system/userGroup/' + groupId,
    method: 'delete'
  })
}

export function getUserGroupItemList(query) {
  return request({
    url: '/system/userGroupItem/list',
    method: 'get',
    params: query
  })
}

// 查询角色未授权用户列表
export function unallocatedUserList(query) {
  return request({
    url: '/system/role/authUser/unallocatedList',
    method: 'get',
    params: query
  })
}

// 取消用户授权角色
export function authUserCancel(data) {
  // console.log(data);
  return request({
    url: '/system/role/authUser/cancel',
    method: 'put',
    data: data
  })
}

// 批量取消用户授权角色
export function authUserCancelAll(data) {
  return request({
    url: '/system/role/authUser/cancelAll',
    method: 'put',
    params: data
  })
}

// 授权用户选择
export function authUserGroupSelectAll(data) {
  return request({
    url: '/system/userGroupItem',
    method: 'post',
    data: data
  })
}
