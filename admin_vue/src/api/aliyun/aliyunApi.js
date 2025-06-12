import request from '@/utils/request'


// 获取oss直传签名
export function getSignature() {
  return request({
    url: '/aliyun/oss/getSignature',
    method: 'get'
  })
}

// 获取oss直传签名
export function uploadfile(data) {
  return request({
    url: '/learn/lesson',
    method: 'post',
    data: data
  })
}