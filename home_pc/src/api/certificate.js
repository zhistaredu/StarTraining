// axios
import request from '@/assets/common/request'
import qs from 'qs';

// 获取证书列表
export function getCertificateList(data) {
  return request({
    url: '/system/certificateUser/home/list',
    method: 'get',
    params: data
  })
}
// 获取证书详情
export function getCertificateDetails(id) {
  return request({
    url: '/system/certificateUser/' + id,
    method: 'get'
  })
}
