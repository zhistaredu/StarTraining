import request from '@/utils/request'

export function listLectorGrade(query) {
  return request({
    url: '/lector/lectorGrade/list',
    method: 'get',
    params: query
  })
}

export function lectorGradeDetail(id) {
  return request({
    url: "/lector/lectorGrade/" + id,
    method: 'get'
  })
}

export function addLectorGrade(data) {
  return request({
    url: '/lector/lectorGrade',
    method: 'post',
    data: data
  })
}

export function updateLectorGrade(data) {
  return request({
    url: '/lector/lectorGrade',
    method: 'put',
    data: data
  })
}

export function delLectorGrade(id) {
  return request({
    url: '/lector/lectorGrade/' + id,
    method: 'delete'
  })
}