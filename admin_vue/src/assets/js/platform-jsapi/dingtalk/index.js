// 单独封装维护，需要调用的钉钉相关jsapi
export const DingTalk = {
  ready(callback) {
    // 解决pc端的windows电脑打开没响应问题
    if(window.dd.pc) {
      setTimeout(() => {
        callback && callback()
      }, 400)
    }

    !window.dd.pc && window.dd.ready(() => {
      callback && callback()
    });
  },
  /**
   * @description: 免登获取code
   * @param:{companyId,onSuccess,onFail}
   * @return:
   */
  getAuthCode({ companyId, onSuccess, onFail }) {
    window.dd.runtime.permission.requestAuthCode({
      companyId,
      onSuccess,
      onFail
    });
  },
  /**
   * @description: 是否在钉钉应用
   * @return: Boolean
   */
  isDingtalk() {
    return /dingtalk/.test(navigator.userAgent.toLowerCase());
  },
  /**
   * @description: 配置钉钉环境需要鉴权
   * @return: Array
   */
  getJsApiList() {
    return [
      'device.notification.confirm',
      'device.notification.alert',
      'device.notification.prompt',
      'runtime.permission.requestAuthCode',
      'device.launcher.launchApp',
      'biz.util.openLink',
      'biz.util.open',
      'biz.util.uploadImageFromCamera',
      'biz.ding.create',
      'biz.util.uploadImage',
      'biz.contact.departmentsPicker',
      'biz.alipay.pay',
      'biz.util.previewImage',
      'device.audio.startRecord',
      'device.audio.onRecordEnd',
      'device.audio.stopRecord',
      'device.audio.download',
      'device.audio.play',
      'device.audio.pause',
      'device.audio.onPlayEnd',
      'device.audio.stop',
      'device.audio.pause',
      'device.audio.resume',
      'device.screen.resetView',
      'biz.chat.openSingleChat',
      'biz.chat.toConversation'
    ];
  },
  /**
   * @description: 钉钉鉴权
   * @param:
   */
  config(
    { agentId, companyId, timeStamp, nonceStr, signature },
    onSuccess,
    onError
  ) {
    window.dd.config({
      agentId,
      companyId,
      timeStamp,
      nonceStr,
      signature,
      jsApiList: this.getJsApiList()
    });

    setTimeout(() => {
      onSuccess && onSuccess();
    }, 0)

    window.dd.error(error => {
      // eslint-disable-next-line no-unused-expressions
      onError && typeof onError === 'function' && onError(error);
    });
  },
  /**
   * @description:错误监听
   */
  error(callback) {
    window.dd.error(callback);
  },
  /**
   * @description:钉钉环境禁用iOS Webview弹性效果
   */
  webViewBounce() {
    window.dd.ui.webViewBounce.disable();
  },
  /**
   * @description:钉钉环境重置旋转屏幕
   * @param:{onSuccess, onFail}
   */
  resetView({ onSuccess, onFail }) {
    window.dd.device.screen.resetView({
      onSuccess: function(result) {
        onSuccess(result);
      },
      onFail: function(err) {
        onFail(err);
      }
    });
  },
  /**
   * @description:钉钉环境旋转屏幕
   * @param:{showStatusBar, clockwise, onSuccess, onFail}
   */
  rotateView({ showStatusBar, clockwise, onSuccess, onFail }) {
    window.dd.device.screen.rotateView({
      showStatusBar: showStatusBar, // 否显示statusbar
      clockwise: clockwise, // 是否顺时针方向
      onSuccess: function(result) {
        onSuccess(result);
      },
      onFail: function(err) {
        onFail(err);
      }
    });
  },
  /**
   * @description:钉钉环境发钉
   * @param:{users, companyId, type, alertType, text, bizType, onSuccess, onFail}
   */
  dingCreate({
    users,
    companyId,
    type,
    alertType,
    text,
    bizType,
    onSuccess,
    onFail
  }) {
    window.dd.biz.ding.create({
      users, // 用户列表，工号
      companyId, // 企业id
      type, // 附件类型 1：image  2：link
      alertType, // 钉发送方式 0:电话, 1:短信, 2:应用内
      text, // 正文  '管理员提醒你批阅试卷《'+title+'》'
      bizType, // 业务类型 0：通知DING；1：任务；2：会议；
      confInfo: {
        bizSubType: 0, // 子业务类型如会议：0：预约会议；1：预约电话会议；2：预约视频会议；（注：目前只有会议才有子业务类型）
        startTime: { format: 'yyyy-MM-dd HH:mm', value: '2015-05-09 08:00' }, // 会议开始时间
        endTime: { format: 'yyyy-MM-dd HH:mm', value: '2015-05-09 08:00' }, // 会议结束时间
        remindMinutes: 30, // 会前提醒。单位分钟-1：不提醒；0：事件发生时提醒；5：提前5分钟；15：提前15分钟；30：提前30分钟；60：提前1个小时；1440：提前一天；
        remindType: 2 // 会议提前提醒方式。0:电话, 1:短信, 2:应用内
      },
      onSuccess: function(res) {
        onSuccess(res);
      },
      onFail: function(err) {
        onFail(err);
      }
    });
  },
  /**
   * @description:钉钉环境分享
   * @param:{onSuccess, onFail, type, url, title, content, image}
   */
  share({ type, url, title, content, image, onSuccess, onFail }) {
    window.dd.biz.util
      .share({
        type, // 分享类型，0:全部组件 默认；1:只能分享到钉钉；2:不能分享，只有刷新按钮
        url,
        title,
        content,
        image,
        onSuccess: function() {
          onSuccess();
        },
        onFail: function(err) {
          onFail(err);
        }
      })
      .catch(err => {
        console.log(err);
      });
  },
  // 钉钉调取摄像头拍照
  photograph({ onSuccess, onFail }) {
    window.dd.biz.util.uploadImageFromCamera({
      compression: true, // (是否压缩，默认为true压缩)
      quality: 50, // 图片压缩质量,
      resize: 50, // 图片缩放率
      cameraDevice: 1, // 前置摄像头
      stickers: {},
      onSuccess: result => {
        onSuccess && onSuccess(result);
      },
      onFail: err => {
        onFail && onFail(err);
        console.log(err, '拍照');
      }
    });
  },
  /**
   * @description:钉钉环境预览图片
   * @param:{onSuccess, onFail, type, url, title, content, image}
   */
  previewImage({ img, onSuccess, onFail }) {
    window.dd.biz.util.previewImage({
      urls: [img], // 图片地址列表
      current: String, // 当前显示的图片链接
      onSuccess: function(result) {
        onSuccess(result);
      },
      onFail: function(err) {
        onFail(err);
      }
    });
  },
  /**
   * @description:钉钉环境alert
   * @param:{onSuccess, onFail, type, url, title, content, image}
   */
  alert({ message, title, buttonName, onSuccess, onFail }) {
    window.dd.device.notification.alert({
      message,
      title, // 可传空
      buttonName,
      onSuccess: function(res) {
        // onSuccess将在点击button之后回调
        onSuccess(res);
      },
      onFail: function(err) {
        onFail(err);
      }
    });
  },
  /**
   * @description: 钉钉环境打开新页面
   */
  openLink({ url, onSuccess, onFail }) {
    window.dd.biz.util.openLink({
      url, // 要打开链接的地址
      onSuccess: function(result) {
        onSuccess && onSuccess(result);
      },
      onFail: function(err) {
        onFail && onFail(err);
      }
    });
  },
  /**
   * @description:钉钉环境设置右侧标题栏
   * @param:{show, control, text, onSuccess, onFail}
   */
  setRight({ show, control, text, onSuccess, onFail }) {
    window.dd.biz.navigation
      .setRight({
        show, // 控制按钮显示， true 显示， false 隐藏， 默认true
        control, // 是否控制点击事件，true 控制，false 不控制， 默认false
        text, // 控制显示文本，空字符串表示显示默认文本
        onSuccess: function(result) {
          // 如果control为true，则onSuccess将在发生按钮点击事件被回调
          onSuccess(result);
        },
        onFail: function(err) {
          onFail(err);
        }
      })
      .catch(err => {
        console.log(err);
      });
  },
  /**
   * @description:钉钉环境返回上一页
   * @param:{onSuccess, onFail}
   */
  goBack({ onSuccess, onFail }) {
    window.dd.biz.navigation
      .goBack({
        onSuccess: function(result) {
          onSuccess(result);
        },
        onFail: function(err) {
          onFail(err);
        }
      })
      .catch(err => {
        console.log(err);
      });
  },
  /**
   * @description:钉钉环境关闭当前页面
   * @param:{onSuccess, onFail, control, text}
   */
  navigationClose({ onSuccess, onFail }) {
    window.dd.biz.navigation
      .close({
        onSuccess: function(result) {
          onSuccess(result);
        },
        onFail: function(err) {
          onFail(err);
        }
      })
      .catch(err => {
        console.log(err);
      });
  },
  /**
   * @description:钉钉环境设置左侧导航按钮文本
   * @param:{control, text, onSuccess, onFail}
   */
  setLeft({ control, text, onSuccess, onFail }) {
    window.dd.biz.navigation
      .setLeft({
        control, // 是否控制点击事件，true 控制，false 不控制， 默认false
        text, // 控制显示文本，空字符串表示显示默认文本
        onSuccess: function(result) {
          // 如果control为true，则onSuccess将在发生按钮点击事件被回调
          onSuccess(result);
        },
        onFail: function(err) {
          onFail(err);
        }
      })
      .catch(err => {
        console.log(err);
      });
  },
  /**
   * @description:钉钉环境设置导航栏标题
   * @param:{onSuccess, onFail, title}
   */
  setTitle({ onSuccess, onFail, title }) {
    window.dd.biz.navigation
      .setTitle({
        title, // 控制标题文本，空字符串表示显示默认文本
        onSuccess: function(result) {
          onSuccess(result);
        },
        onFail: function(err) {
          onFail(err);
        }
      })
      .catch(err => {
        console.log(err);
      });
  },
  /**
   * @description: 钉钉扫码
   * @param: onSuccess, onFail
   */
  scan(onSuccess, onFail) {
    window.dd.biz.util.scan({
      type: 'qrCode', // type 为 all、qrCode、barCode，默认是all。
      onSuccess: function(data) {
        // { 'text': String}
        onSuccess(data);
      },
      onFail: function(err) {
        onFail(err);
      }
    });
  },
  /**
   * @description: 钉钉环境获取网络类型
   * @param: {onSuccess,onFail}
   */
  getNetworkType({ onSuccess, onFail }) {
    window.dd.ready(() => {
      window.dd.device.connection.getNetworkType({
        onSuccess: function(data) {
          onSuccess(data.result);
        },
        onFail: function(err) {
          onFail(err);
        }
      });
    });
  },
  /**
   * @description:钉钉环境打开会话
   * @param:{companyId, chatId, onSuccess, onFail}
   */
  toConversation({ companyId, chatId, onSuccess, onFail }) {
    window.dd.biz.chat.toConversation({
      companyId, // 企业id,必须是用户所属的企业的corpid
      chatId, // 会话Id
      onSuccess: function(res) {
        onSuccess(res);
      },
      onFail: function(err) {
        onFail(err);
      }
    });
  },
  /**
   * @description:钉钉环境开始录音
   * @param:{onSuccess, onFail}
   */
  startRecord({ maxDuration, onSuccess, onFail }) {
    window.dd.device.audio.startRecord({
      maxDuration,
      onSuccess: function(res) {
        // 支持最长为300秒（包括）的音频录制，默认60秒(包括)。
        onSuccess(res);
      },
      onFail: function(err) {
        onFail(err);
      }
    });
  },
  /**
   * @description:钉钉环境停止录音
   * @param:{onSuccess, onFail}
   */
  stopRecord({ onSuccess, onFail }) {
    window.dd.device.audio.stopRecord({
      onSuccess: function(res) {
        // 支持最长为300秒（包括）的音频录制，默认60秒(包括)。
        // res.mediaId; 返回音频的MediaID，可用于本地播放和音频下载
        // res.duration; 返回音频的时长，单位：秒
        onSuccess(res);
      },
      onFail: function(err) {
        onFail(err);
      }
    });
  },
  /**
   * @description:钉钉环境播放录音
   * @param:{onSuccess, onFail}
   */
  playAudio({ localAudioId, onSuccess, onFail }) {
    window.dd.device.audio.play({
      localAudioId,
      onSuccess: function(res) {
        onSuccess(res);
      },
      onFail: function(err) {
        onFail(err);
      }
    });
  },
  /**
   * @description:钉钉环境监听录音自动停止
   * @param:{onSuccess, onFail}
   */
  onRecordEnd({ onSuccess, onFail }) {
    window.dd.device.audio.onRecordEnd({
      onSuccess: function(res) {
        console.log(res, 'res');
        // res.mediaId;  停止播放音频MediaID
        // res.duration; 返回音频的时长，单位：秒
        onSuccess(res);
      },
      onFail: function(err) {
        console.log(err, 'err');
        onFail(err);
      }
    });
  },
  /**
   * @description:钉钉环境下载音频
   * @param:{onSuccess, onFail, mediaId}
   */
  downloadAudio({ onSuccess, onFail, mediaId }) {
    window.dd.device.audio.download({
      mediaId,
      onSuccess: function(res) {
        // res.localAudioId;  音频在本地的MediaId
        onSuccess(res);
      },
      onFail: function(err) {
        onFail(err);
      }
    });
  },
  /**
   * @description:钉钉环境暂停音频
   * @param:{onSuccess, onFail, localAudioId}
   */
  pauseAudio({ onSuccess, onFail, localAudioId }) {
    window.dd.device.audio.pause({
      localAudioId,
      onSuccess: function(res) {
        onSuccess(res);
      },
      onFail: function(err) {
        onFail(err);
      }
    });
  },
  /**
   * @description:钉钉环境恢复暂停播放的语音
   * @param:{onSuccess, onFail, localAudioId}
   */
  resumeAudio({ localAudioId, onSuccess, onFail }) {
    window.dd.device.audio.pause({
      localAudioId,
      onSuccess: function(res) {
        onSuccess(res);
      },
      onFail: function(err) {
        onFail(err);
      }
    });
  },
  /**
   * @description:钉钉环境停止音频
   * @param:{onSuccess, onFail, localAudioId}
   */
  stopAudio({ onSuccess, onFail, localAudioId }) {
    window.dd.device.audio.stop({
      localAudioId,
      onSuccess: function(res) {
        onSuccess(res);
      },
      onFail: function(err) {
        onFail(err);
      }
    });
  },
  /**
   * @description:钉钉环境监听播放自动停止
   * @param:{onSuccess, onFail}
   */
  onPlayEnd({ onSuccess, onFail }) {
    window.dd.device.audio.onPlayEnd({
      onSuccess: function(res) {
        // res.localAudioId 停止的音频id
        onSuccess(res);
      },
      onFail: function(err) {
        onFail(err);
      }
    });
  }
};
