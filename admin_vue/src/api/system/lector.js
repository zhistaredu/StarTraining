import request from '@/utils/request'

export function listLector(query) {
  return request({
    url: '/lector/lector/list',
    method: 'get',
    params: query
  })
}

export function getLector(query) {
  return request({
    url: '/lector/lector/lectorList',
    method: 'get',
    params: query
  })
}

export function lectorDetail(id) {
  return request({
    url: "/lector/lector/" + id,
    method: 'get'
  })
}

export function addLector(data) {
  return request({
    url: '/lector/lector',
    method: 'post',
    data: data
  })
}

export function updateLector(data) {
  return request({
    url: '/lector/lector',
    method: 'put',
    data: data
  })
}

export function publishLector(id) {
  return request({
    url: '/lector/lector/publish/' + id,
    method: 'get'
  })
}

export function closeLector(id) {
  return request({
    url: '/lector/lector/close/' + id,
    method: 'get'
  })
}

export function delLector(id) {
  return request({
    url: '/lector/lector/' + id,
    method: 'delete'
  })
}