// axios
import request from '@/assets/common/request'
import qs from 'qs';


// 获取oss直传签名
export function getSignature() {
  return request({
    url: '/aliyun/oss/getSignature',
    method: 'get'
  })
}

// 培训班作业上传文件
export function uploadHomeworkfile(data) {
  return request({
    url: '/train/trainStageLesson/homeworkUploadFile',
    method: 'post',
    data: data
  })
}
