import request from '@/utils/request'

export function listMentorGrade(query) {
  return request({
    url: '/system/mentorGrade/list',
    method: 'get',
    params: query
  })
}

export function mentorGradeDetail(id) {
  return request({
    url: "/system/mentorGrade/" + id,
    method: 'get'
  })
}

export function addMentorGrade(data) {
  return request({
    url: '/system/mentorGrade',
    method: 'post',
    data: data
  })
}

export function updateMentorGrade(data) {
  return request({
    url: '/system/mentorGrade',
    method: 'put',
    data: data
  })
}

export function delMentorGrade(id) {
  return request({
    url: '/system/mentorGrade/' + id,
    method: 'delete'
  })
}