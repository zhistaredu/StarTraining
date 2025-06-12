import Vue from 'vue'

// 日期过滤器
// Vue.filter('date', (s) => {
//   if (!s) return ''
//   return formatDate(new Date(s), 'yyyy-MM-dd H:m:s')
// })
Vue.mixin({
  filters: {
    timeConvertNew(value) {
      // eslint-disable-next-line no-undefined
      if (value) {
        let val = value.replace(/-/g, '/');
        // ios兼容
        let convertDate = new Date(val);
        // let convertDate = new Date(value);//ios兼容
        let convertLocale = convertDate.getTime();
        let nowOffset = new Date().getTimezoneOffset() * 60 * 1000;
        let nowUtc = convertLocale - nowOffset;
        let _sValue = new Date(nowUtc);
        let o = {
          'Y+': _sValue.getFullYear(),
          'M+': _sValue.getMonth() + 1,
          'd+': _sValue.getDate(),
          'h+': _sValue.getHours(),
          'm+': _sValue.getMinutes(),
          's+': _sValue.getSeconds()
        };
        for (let k in o) {
          if (o.hasOwnProperty(k)) {
            val = o[k].toString();
            val = val.length < 2 ? ('00' + val).substr(val.length) : val;
            o[k] = val;
          }
        }
        return (
          o['Y+'] +
          '-' +
          o['M+'] +
          '-' +
          o['d+'] +
          ' ' +
          o['h+'] +
          ':' +
          o['m+']
        );
      }
    },
    timeConvert(value) {
      // eslint-disable-next-line no-undefined
      if (value) {
        let val = value.replace(/-/g, '/');
        // ios兼容
        let convertDate = new Date(val);
        // let convertDate = new Date(value);//ios兼容
        let convertLocale = convertDate.getTime();
        let nowOffset = new Date().getTimezoneOffset() * 60 * 1000;
        let nowUtc = convertLocale - nowOffset;
        let _sValue = new Date(nowUtc);
        let o = {
          'Y+': _sValue.getFullYear(),
          'M+': _sValue.getMonth() + 1,
          'd+': _sValue.getDate(),
          'h+': _sValue.getHours(),
          'm+': _sValue.getMinutes(),
          's+': _sValue.getSeconds()
        };
        for (let k in o) {
          if (o.hasOwnProperty(k)) {
            val = o[k].toString();
            val = val.length < 2 ? ('00' + val).substr(val.length) : val;
            o[k] = val;
          }
        }
        return (
          o['Y+'] +
          '-' +
          o['M+'] +
          '-' +
          o['d+'] +
          ' ' +
          o['h+'] +
          ':' +
          o['m+'] +
          ':' +
          o['s+']
        );
      }
    },
    // 课程时长
    timeLong(time) {
      let second = parseInt(time); // 秒
      let minute = 0; // 分
      let hour = 0; // 时
      if (second >= 60) {
        minute = parseInt(second / 60);
        second = parseInt(second % 60);
        if (minute > 60) {
          hour = parseInt(minute / 60);
          minute = parseInt(minute % 60);
        }
      }
      let result = '' + parseInt(second);
      if (second < 10) {
        result = '0' + parseInt(second);
      }
      if (minute > 0) {
        if (minute < 10) {
          result = '0' + parseInt(minute) + '：' + result;
        } else {
          result = '' + parseInt(minute) + '：' + result;
        }
      } else {
        result = '00' + '：' + result;
      }
      if (hour > 0) {
        if (hour < 10) {
          result = '0' + parseInt(hour) + '：' + result;
        } else {
          result = '' + parseInt(hour) + '：' + result;
        }
      } else {
        result = '00' + '：' + result;
      }
      return result;
    },
    // 考试时长
    timeFilter(value) {
      let data
      if (value < 60) {
        data = value + '秒'
      } else if (value < 3600) {
        let m = parseInt(value / 60 % 60)
        m = m < 10 ? '0' + m : m
        let s = parseInt(value % 60)
        s = s < 10 ? '0' + s : s
        data = m + '分' + s + '秒'
      } else {
        let h = parseInt(value / 60 / 60 % 24)
        h = h < 10 ? '0' + h : h
        let m = parseInt(value / 60 % 60)
        m = m < 10 ? '0' + m : m
        let s = parseInt(value % 60)
        s = s < 10 ? '0' + s : s
        data = h + '时' + m + '分' + s + '秒'
      }
      return data
    }
  },
  methods: {
    // 课程学习倒计时
    $_courseCountDown(time, showType) {
      var second = parseInt(time); // 秒
      var minute = 0; // 分
      var hour = 0; // 时
      if (second >= 60) {
        minute = parseInt(second / 60);
        second = parseInt(second % 60);
        if (minute > 60) {
          hour = parseInt(minute / 60);
          minute = parseInt(minute % 60);
        }
      }
      // 计时显示
      if (showType == 'count') {
        var result = '' + parseInt(second);
        if (second < 10) {
          result = '0' + parseInt(second);
        }
        if (minute > 0) {
          if (minute < 10) {
            result = '0' + parseInt(minute) + '：' + result;
          } else {
            result = '' + parseInt(minute) + '：' + result;
          }
        } else {
          result = '00' + '：' + result;
        }
        if (hour > 0) {
          if (hour < 10) {
            result = '0' + parseInt(hour) + '：' + result;
          } else {
            result = '' + parseInt(hour) + '：' + result;
          }
        } else {
          result = '00' + '：' + result;
        }
      } else {
        // 最短学习时间展示
        var result = '';
        if (second > 0) {
          result =
            '' +
            parseInt(second) +
            ' ' +
            '秒';
        }
        if (minute > 0) {
          result =
            '' +
            parseInt(minute) +
            ' ' +
            '分' +
            result;
        }
        if (hour > 0) {
          result =
            '' +
            parseInt(hour) +
            ' ' +
            '时' +
            result;
        }
      }
      return result;
    },
    // 处理考试时间
    $_handleTime(value) {
      let theTime = parseInt(value); // 秒
      let theTime1 = 0; // 分
      let theTime2 = 0; // 小时
      if (theTime > 60) {
        theTime1 = parseInt(theTime / 60);
        theTime = parseInt(theTime % 60);
        if (theTime1 > 60) {
          theTime2 = parseInt(theTime1 / 60);
          theTime1 = parseInt(theTime1 % 60);
        }
      }
      let result = '' + parseInt(theTime);
      if (theTime1 > 0) {
        if (result > 9) {
          result = '' + parseInt(theTime1) + ':' + result;
        } else {
          result = '' + parseInt(theTime1) + ':0' + result;
        }

      }
      if (theTime2 > 0) {
        if (parseInt(theTime1) > 9) {
          result = '' + parseInt(theTime2) + ':' + result;
        } else {
          result = '' + parseInt(theTime2) + ':0' + result;
        }

      }
      return result;
    },
    // 监听浏览器关闭刷新
    $_listenBrowser({
      onBefore,
      onComplete
    }) {
      // console.log('页面刷新')
      let _beforeUnloadTime = 0;

      // 当用户离开页面时发生的事件
      window.onunload = () => {
        const _gapTime = new Date().getTime() - _beforeUnloadTime;

        onBefore && onBefore();

        // 关闭
        if (_gapTime <= 5) {
          onComplete && onComplete('close');
        } else {
          // 刷新
          onComplete && onComplete('refresh');
        }
      };
      window.onbeforeunload = () => {
        _beforeUnloadTime = new Date().getTime();
      };
    },
    $_script(url, isJsUrl) {
      const callback = scriptUrl => {
        return new Promise(resolve => {
          let _saveScripts = window.saveScripts;
          _saveScripts = _saveScripts ? _saveScripts : {};
          if (_saveScripts[scriptUrl]) {
            resolve();
          } else {
            let _script = document.createElement('script');
            _script.src = isJsUrl ? url : '/pcHome/dist/' + scriptUrl;
            _script.onload = () => {
              _saveScripts[scriptUrl] = true;
              window.saveScripts = _saveScripts;
              resolve();
            };
            document.body.appendChild(_script);
          }
        });
      };
      if (typeof url === 'string') {
        return callback(url);
      } else if (typeof url === 'object') {
        let _promises = [];
        url.forEach(item => {
          _promises.push(callback(item));
        });
        return Promise.all(_promises);
      }
    },
  }
});
