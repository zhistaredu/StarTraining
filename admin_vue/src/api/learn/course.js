import request from '@/utils/request'

// 查询课程管理列表
export function listCourse(query) {
  return request({
    url: '/knowledge/knowledge/list',
    method: 'get',
    params: query
  })
}

// 查询课程管理详细
export function courseDetail(id) {
  return request({
    url: "/knowledge/knowledge/" + id,
    method: 'get'
  })
}

// 新增课程管理
export function addCourse(data) {
  return request({
    url: '/knowledge/knowledge',
    method: 'post',
    data: data
  })
}

// 修改课程管理
export function updateCourse(data) {
  return request({
    url: '/knowledge/knowledge',
    method: 'put',
    data: data
  })
}

// 删除课程管理
export function delCourse(id) {
  return request({
    url: '/knowledge/knowledge/' + id,
    method: 'delete'
  })
}

// 关闭课程管理 修改
export function publishCourse(id) {
  return request({
    url: '/knowledge/knowledge/publish/' + id,
    method: 'get'
  })
}

// 关闭课程管理 修改
export function closeCourse(id) {
  return request({
    url: '/knowledge/knowledge/close/' + id,
    method: 'get'
  })
}

// 查询课程管理学员指派
export function courseAssignUser(data) {
  return request({
    url: "/knowledge/knowledge/controlLimit",
    method: 'get',
    params: data
  })
}

// 更新课程管理学员指派
export function updateCourseAssignUser(data) {
  return request({
    url: "/knowledge/knowledge/controlLimit",
    method: 'post',
    data: data
  })
}

// 获取课程目录
export function courseLessonList(data) {
  return request({
    url: "/knowledge/Courseware/list",
    method: 'get',
    params: data
  })
}

// 课件排序
export function courseLessonListSort(data) {
  return request({
    url: "/knowledge/Courseware/listSort",
    method: 'post',
    data: data
  })
}

export function saveCourseLessonList(data) {
  return request({
    url: "/knowledge/Courseware",
    method: 'post',
    data: data
  })
}

export function saveBatchLesson(data) {
  return request({
    url: "/knowledge/Courseware/batchAddLesson",
    method: 'post',
    data: data
  })
}

// 删除课件
export function deleteCourseLessons(id) {
  return request({
    url: "/knowledge/Courseware/" + id,
    method: 'delete',
  })
}

// 获取课件内容
export function getCourseLessonDetail(data) {
  return request({
    url: "/knowledge/Courseware/info",
    method: 'get',
    params: data
  })
}

// 更新课件内容
export function updateCourseLessonDetail(data) {
  return request({
    url: "/knowledge/Courseware",
    method: 'put',
    data: data
  })
}

// 提交转码
export function gotoConvert(id) {
  return request({
    url: "/learn/lesson/submitTranscoding/" + id ,
    method: 'get'
  })
}

export function getCourseUser(data) {
  return request({
    url: "/knowledge/knowledgeUser/list" ,
    method: 'get',
    params: data
  })
}

export function getCourseUserDept(data) {
  return request({
    url: "/knowledge/knowledgeUser/deptList" ,
    method: 'get',
    params: data
  })
}

export function userLearnList(data) {
  return request({
    url: "/knowledge/knowledgeUser/userLearnList" ,
    method: 'get',
    params: data
  })
}

export function deptLearnList(data) {
  return request({
    url: "/knowledge/knowledgeUser/deptLearnList" ,
    method: 'get',
    params: data
  })
}
