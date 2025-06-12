// 官方文档：https://developer.work.weixin.qq.com/document/path/94313
// demo：https://open.work.weixin.qq.com/api/jsapidemo
export const WX = {
  // 获取jsapi地址
  getJsApiUrl() {
    return [
      'https://res.wx.qq.com/open/js/jweixin-1.2.0.js',
      'https://open.work.weixin.qq.com/wwopen/js/jwxwork-1.0.0.js'
    ];
  },
  ready(callback) {
    window.wx.ready(callback);
  },
  /**
   * @description: 是否是企微环境
   * @return:Boolean
   */
  isWx() {
    return (
      navigator.userAgent.toLowerCase().match(/wxwork/i) == 'wxwork' ||
      navigator.userAgent.toLowerCase().match(/wechatdevtools/i) ==
      'wechatdevtools'
    );
  },
  /**
   * @description: 获取微信需要鉴权的jsapi
   * @return: Array
   */
  getJsApiList() {
    return [
      'getNetworkType',
      'shareAppMessage',
      'previewImage',
      'hideMenuItems',
      'startRecord',
      'stopRecord',
      'onVoiceRecordEnd',
      'playVoice',
      'pauseVoice',
      'stopVoice',
      'uploadVoice',
      'onVoicePlayEnd',
      'downloadVoice',
      'chooseImage',
      'getLocalImgData',
      'uploadImage',
      'selectExternalContact',
      'startLiving',
      'replayLiving',
      'invoke'
    ];
  },
  /**
   * @description:微信鉴权
   */
  config({
    corpId,
    timeStamp,
    nonceStr,
    signature,
    onSuccess
  }) {
    window.wx.config({
      beta: true, // 必须这么写，否则wx.invoke调用形式的jsapi会有问题
      debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
      appId: corpId, // 必填，企业微信的corpID
      timestamp: timeStamp, // 必填，生成签名的时间戳
      nonceStr, // 必填，生成签名的随机串
      signature, // 必填，签名，见 附录-JS-SDK使用权限签名算法
      jsApiList: this.getJsApiList(), // 必填，需要使用的JS接口列表，凡是要调用的接口都需要传进来
      success: res => {
        console.log('wxconfig成功');
        onSuccess && onSuccess(res);
      },
      fail: err => {
        console.log('wxconfig失败', err);
      },
      complete: com => {
        console.log('wxconfig complete', com);
      },
      cancel: () => {
        console.log('wxconfig cancel');
      },
      trigger: () => {
        console.log('wxconfig trigger');
      }
    });
    window.wx.error(err => {
      console.log('corpId：' + corpId + 'timeStamp: ' + timeStamp + 'nonceStr: ' + nonceStr + 'signature :' + signature);
      // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
      console.log('wxconfig失败', err);
    });
  },
  /**
   * @description: 微信agentConfig 客户端内置浏览器需在wx.config完成后调用，第三方浏览器不需要
   * @return:
   */
  WxAgentConfig({
    corpid,
    agentid,
    timestamp,
    nonceStr,
    signature,
    jsApiList,
    onSuccess
  }) {
    window.wx.error(function (res) {
      console.log(res, 'error');
    });
    window.wx.agentConfig({
      corpid, // 必填，企业微信的corpid，必须与当前登录的企业一致
      agentid, // 必填，企业微信的应用id （e.g. 1000247）
      timestamp, // 必填，生成签名的时间戳
      nonceStr, // 必填，生成签名的随机串
      signature, // 必填，签名，见附录-JS-SDK使用权限签名算法
      jsApiList: jsApiList || this.getJsApiList(), // 必填，传入需要使用的接口名称
      success: result => {
        console.log('企微鉴权成功');
        onSuccess && onSuccess(result);
      },
      fail: res => {
        if (res.errMsg.indexOf('function not exist') > -1) {
          console.log(res, '版本过低请升级');
        } else {
          console.log(res, 'WxAgentConfig失败');
        }
      }
    });
  },
  /**
   * @description:企微环境预览图片
   * @param:{showStatusBar, clockwise, onSuccess, onFail}
   */
  previewImage({
    img,
    onSuccess,
    onFail
  }) {
    window.wx.previewImage({
      current: img, // 当前显示图片的http链接
      urls: [img], // 需要预览的图片http链接列表
      success: function (result) {
        onSuccess(result);
      },
      fail: function (err) {
        onFail(err);
      }
    });
  },
  /**
   * @description:错误监听
   */
  error(callback) {
    window.wx.error(callback);
  },
  /**
   * @description:企微环境关闭当前页面
   * @param:{onSuccess, onFail, title}
   */
  navigationClose() {
    window.wx.closeWindow();
  },
  /**
   * @description: 微信扫码
   * @param: onSuccess, onFail
   */
  scan(onSuccess, onFail) {
    window.wx.scanQRCode({
      desc: 'scanQRCode desc',
      needResult: 0, // 默认为0，扫描结果由企业微信处理，1则直接返回扫描结果，
      scanType: ['qrCode'], // 可以指定扫二维码还是条形码（一维码），默认二者都有
      success: function (res) {
        onSuccess(res);
      },
      error: function (res) {
        if (res.errMsg.indexOf('function_not_exist') > 0) {
          onFail();
          console.log('版本过低请升级');
        }
      }
    });
  },
  // 上传图片
  uploadImage({
    localId,
    onSuccess
  }) {
    window.wx.uploadImage({
      localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
      isShowProgressTips: 1, // 默认为1，显示进度提示
      success: function (res) {
        // var serverId = res.serverId; // 返回图片的服务器端ID
        onSuccess && onSuccess(res);
      }
    });
  },

  /**
   * @description: 企微环境获取网络类型
   * @param: {onSuccess,onFail}
   */
  getNetworkType({
    onSuccess,
    onFail
  }) {
    window.wx.getNetworkType({
      success: function (res) {
        onSuccess(res.networkType); // 返回网络类型2g，3g，4g，wifi
      },
      fail: function (err) {
        onFail(err); // 返回网络类型2g，3g，4g，wifi
      }
    });
  },
  // 分享
  share({
    url,
    title,
    content,
    image,
    onSuccess,
    onFail
  }) {
    window.wx.invoke(
      'shareAppMessage', {
        title: title, // 分享标题
        desc: content, // 分享描述
        link: url, // 分享链接
        imgUrl: image // 分享封面
      },
      function (res) {
        if (res.err_msg == 'shareAppMessage:ok') {
          onSuccess && onSuccess();
        } else {
          onFail && onFail()
        }
      }
    );
  },

};
