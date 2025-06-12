// axios
import request from '@/assets/common/request'
import qs from 'qs';

// 获取课程列表
export function getCourseList(data) {
  return request({
    url: '/knowledge/knowledge/home/list',
    method: 'get',
    params: data
  })
}

// 获取我的课程列表
export function getMyCourseList(data) {
  return request({
    url: '/knowledge/knowledge/home/list',
    method: 'get',
    params: data
  })
}

// 获取课程分类列表
export function getCourseCategoryList(data) {
  return request({
    url: '/system/category/list',
    method: 'get',
    params: data
  })
}
// 获取课程详情
export function getCourseDetails(id) {
  return request({
    url: '/knowledge/knowledge/home/info/' + id,
    method: 'get'
  })
}

// 加载课件
export function getCourseLessons(data) {
  return request({
    url: '/knowledge/Courseware/home/list',
    method: 'get',
    params: data
  })
}
// 获取单个课件详情
export function getCourseLessonsDetail(id, data) {
  return request({
    url: '/knowledge/Courseware/home/info/' + id,
    method: 'get',
    params: data
  })
}
// 完成课程
export function finishCourseLearing(data) {
  return request({
    url: '/knowledge/Courseware/finish',
    method: 'get',
    params: data
  })
}
// 加入选修
export function joinXuanLearn(data) {
  return request({
    url: '/knowledge/courseUser/joinElective',
    method: 'post',
    data: data
  })
}
// 课程开始学习
export function startCourseLearing(data) {
  return request({
    url: '/knowledge/Courseware/start',
    method: 'get',
    params: data
  })
}
// 更新课程学习时长
export function updateCourseLearning(data) {
  return request({
    url: '/knowledge/Courseware/updateLearnProgress',
    method: 'post',
    data: data
  })
}
// g更新视频和音频总时长
export function updateLessonLength(data) {
  return request({
    url: '/knowledge/Courseware/verifyLength',
    method: 'post',
    data: data
  })
}

// 获取文档预览的AccessToken
export function getDocumentToken(data) {
  return request({
    url: '/knowledge/Courseware/docPlayUrl',
    method: 'get',
    params: data
  })
}

