import Vue from 'vue'

Vue.mixin({
  filters: {
    // 时区转换过滤器
    timeConvert(value) {
      if (value == undefined || value == '' || !value || value == '0000-00-00 00:00:00') {
        return '-';
      }
      let utc = new Date(value);
      let utcTime = utc.getTime();
      if (!utcTime || utcTime == 0) {
        return '-';
      }
      let nowDate = new Date();
      let offset = nowDate.getTimezoneOffset() * 60 * 1000;
      let nowTime = utcTime - offset;
      let showData = new Date(nowTime);

      let y = showData.getFullYear();
      let Mon = showData.getMonth() + 1;
      let d = showData.getDate();
      let h = showData.getHours();
      let mm = showData.getMinutes();
      let s = showData.getSeconds();
      if (Mon < 10) {
        Mon = '0' + Mon;
      }
      if (d < 10) {
        d = '0' + d;
      }
      if (h < 10) {
        h = '0' + h;
      }
      if (mm < 10) {
        mm = '0' + mm;
      }
      if (s < 10) {
        s = '0' + s;
      }

      return y + '-' + Mon + '-' + d + ' ' + h + ':' + mm + ':' + s;
    },
    timeFormat(time) { 
      let second = parseInt(time); // 秒 
      let minute = 0; // 分 
      let hour = 0; // 时 
     
      minute = parseInt(second / 60); 
      second = parseInt(second % 60); 
      if (minute > 60) { 
        hour = parseInt(minute / 60); 
        minute = parseInt(minute % 60); 
      } 
      let result = ('00' + second.toString()).slice(-2); 
      if (minute > 0) { 
        result = ('00' + minute.toString()).slice(-2) + ':' + result; 
      } else { 
        result = '00:' + result; 
      } 
      if (hour > 0) { 
        result = ('00' + hour.toString()).slice(-2) + ':' + result; 
      } else { 
        result = '00:' + result; 
      } 
      return result; 
    } 
  },
  methods: {
    // 添加选项
    $_makeOperator(length) {
      let operator = ''
      if (length == 0) {
        operator = 'A'
      } else if (length == 1) {
        operator = 'B'
      } else if (length == 2) {
        operator = 'C'
      } else if (length == 3) {
        operator = 'D'
      } else if (length == 4) {
        operator = 'E'
      } else if (length == 5) {
        operator = 'F'
      } else if (length == 6) {
        operator = 'G'
      } else if (length == 7) {
        operator = 'H'
      } else if (length == 8) {
        operator = 'I'
      } else if (length == 9) {
        operator = 'J'
      }

      return operator
    },
    $_sortOperator(arr) {
      let arr2;
      arr2 = arr.map((item, index) => {
        if(index == 0) {
          item.operator = 'A'
          item.key = 'A'
        }else if(index==1) {
          item.operator = 'B'
          item.key = 'B'
        }else if(index==2) {
          item.operator = 'C'
          item.key = 'C'
        }else if(index==3) {
          item.operator = 'D'
          item.key = 'D'
        }else if(index==4) {
          item.operator = 'E'
          item.key = 'E'
        }else if(index==5) {
          item.operator = 'F'
          item.key = 'F'
        }else if(index==6) {
          item.operator = 'G'
          item.key = 'G'
        }else if(index==7) {
          item.operator = 'H'
          item.key = 'H'
        }else if(index==8) {
          item.operator = 'I'
          item.key = 'I'
        }else if(index==9) {
          item.operator = 'J'
          item.key = 'J'
        }
        return item
      })
      return arr2
    },

    /**
     * utc时间转换为客户端当前时间，一般用于页面时间显示
     * @date    2020-08-13T14:33:24+0800
     * @author pgj
     * @version v1
     * @param   {[type]}                 value [description]
     * @return  {[type]}                       [description]
     */
    $_utcToLocalTime(value) {
      if (value == '' || value == '0000-00-00 00:00:00' || value == null) {
        return value;
      }
      let utc = new Date(value);
      let utcTime = utc.getTime();

      let nowDate = new Date();
      let offset = nowDate.getTimezoneOffset() * 60 * 1000;
      // console.log('offset:'+offset);
      let nowTime = utcTime - offset;
      let showData = new Date(nowTime);

      let y = showData.getFullYear();
      let Mon = this.completionTime(showData.getMonth() + 1);
      let d = this.completionTime(showData.getDate());
      let h = this.completionTime(showData.getHours());
      let mm = this.completionTime(showData.getMinutes());
      let s = this.completionTime(showData.getSeconds());
      return y + '-' + Mon + '-' + d + ' ' + h + ':' + mm + ':' + s;
    },

    completionTime(t) {
      if (t < 10) {
        t = '0' + t;
      }
      return t;
    },
  }
})
