/**
 * @description: 格式化时间 formatDate(new Date(), 'yyyy-MM-dd H:m:s')
 * @param {Date} time
 * @param {String} format
 * @return {String}
 */
function formatDate(time, format) {
  const o = {
    'M+': time.getMonth() + 1, // 月份
    'd+': time.getDate(), // 日
    'H+': time.getHours(), // 小时
    'm+': time.getMinutes(), // 分
    's+': time.getSeconds(), // 秒
    'q+': Math.floor((time.getMonth() + 3) / 3), // 季度
    'f+': time.getMilliseconds() // 毫秒
  }
  if (/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, (time.getFullYear() + '').substr(4 - RegExp.$1.length))
  }
  for (let k in o) {
    if (new RegExp('(' + k + ')').test(format)) {
      format = format.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
    }
  }
  return format
}

// 数字加前缀 0
function fullNumber(n) {
  return n > 9 ? n : '0' + n
}

// 去除头尾空白字符
function trim(s) {
  return s.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
}

// 有效值
function truthy(val) {
  if (val && val != 'undefined' && val != 'null' && val != 'NaN') {
    return true
  }
  return false
}

export {
	formatDate,
  fullNumber,
  trim,
  truthy
}
