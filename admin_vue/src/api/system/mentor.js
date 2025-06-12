import request from '@/utils/request'

export function listMentor(query) {
  return request({
    url: '/system/mentor/list',
    method: 'get',
    params: query
  })
}

export function mentorDetail(id) {
  return request({
    url: "/system/mentor/" + id,
    method: 'get'
  })
}

export function addMentor(data) {
  return request({
    url: '/system/mentor',
    method: 'post',
    data: data
  })
}

export function updateMentor(data) {
  return request({
    url: '/system/mentor',
    method: 'put',
    data: data
  })
}

export function delMentor(id) {
  return request({
    url: '/system/mentor/' + id,
    method: 'delete'
  })
}