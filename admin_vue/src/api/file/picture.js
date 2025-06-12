import request from '@/utils/request'


export function listPicture(query) {
  return request({
    url: '/system/picture/list',
    method: 'get',
    params: query
  })
}

export function insertPicture(data) {
  return request({
    url: '/system/picture',
    method: 'post',
    data: data
  })
}

export function deletePicture(uuids) {
  return request({
    url: '/system/picture/' + uuids,
    method: 'delete',
  })
}
