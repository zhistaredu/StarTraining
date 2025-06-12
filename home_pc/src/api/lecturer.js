// axios
import request from '@/assets/common/request'
import qs from 'qs';

// 获取讲师等级列表
export function getLecturerGrade(data) {
  return request({
    url: '/lector/lectorGrade/home/list',
    method: 'get',
    params: data
  })
}

// 获取讲师列表
export function getLecturerList(data) {
  return request({
    url: '/lector/lector/home/list',
    method: 'get',
    params: data
  })
}

// 获取讲师详情
export function getLecturerDetails(id) {
  return request({
    url: '/lector/lector/home/info/' + id,
    method: 'get'
  })
}
