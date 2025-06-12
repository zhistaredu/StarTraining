// 对应的jsapi，需要在各个平台下新增
import scriptSrc from 'scriptjs';
import {
  DingTalk
} from './dingtalk/index'; // 钉钉

import {
  WX
} from './wx/index'; // 企微

import {
  Browser
} from './browser/index'; // 浏览器

// 环境匹配
const platform = DingTalk.isDingtalk() ?
  DingTalk :
  WX.isWx() ?
  WX :
  Browser;

export default class SK {
  constructor() {
    this.platform = platform;
    // 企微isv用户需要使用通讯录组件
    this.weiXinIsv = false;
  }

  /**
   * @description:是否是钉钉环境
   */
  isDingtalk() {
    return DingTalk.isDingtalk();
  }

  /**
   * @description:是否是企微
   */
  isWx() {
    return WX.isWx();
  }

  // 动态引入对应jsapi的文件
  createJsApiScript(callback) {
    if (!platform.getJsApiUrl) {
      return;
    }

    const jsapiUrl = platform.getJsApiUrl(); // 当前都是数组,每个平台下面都需要一个getJsApiUrl方法
    scriptSrc(jsapiUrl, () => {
      callback && callback();
    });
  }

  /**
   * @description:平台鉴权
   */
  configStart({
    corpId,
    timeStamp,
    nonceStr,
    signature,
    onSuccess
  }) {
    console.log('corpId：' + corpId + 'timeStamp: ' + timeStamp + 'nonceStr: ' + nonceStr + 'signature :' + signature);
    platform.config &&
      platform.config({
        corpId,
        timeStamp,
        nonceStr,
        signature,
        onSuccess
      });
  }

  /**
   * @description:专门针对微信-agentConfig 客户端内置浏览器需在wx.config完成后调用，第三方浏览器不需要
   */
  WxAgentConfigStart({
    corpid,
    agentid,
    timestamp,
    nonceStr,
    signature,
    jsApiList,
    onSuccess
  }) {
    console.log('微信鉴权')
    console.log('corpId：' + corpid + 'timeStamp: ' + timestamp + 'nonceStr: ' + nonceStr + 'signature :' + signature);
    platform.WxAgentConfig &&
      platform.WxAgentConfig({
        corpid,
        agentid,
        timestamp,
        nonceStr,
        signature,
        jsApiList,
        onSuccess
      });

  }

  /**
   * @description: 错误监听
   */
  error(callback) {
    platform.error && platform.error(callback);
  }

  /**
   * @description: ready
   */
  ready(callback) {
    if (platform.ready) {
      platform.ready(callback)
    } else {
      callback()
    }
  }

  /**
   * @description: 免登获取code
   * @param:{companyId,onSuccess,onFail}
   * @return:
   */
  getAuthCode({
    companyId,
    onSuccess,
    onFail
  }) {
    platform.getAuthCode && platform.getAuthCode({
      companyId,
      onSuccess,
      onFail
    });
  }

  /**
   * @description: 获取网络类型
   * @param: {onSuccess,onFail}
   */
  getNetworkType({
    onSuccess,
    onFail
  }) {
    platform.getNetworkType && platform.getNetworkType({
      onSuccess,
      onFail
    });
  }


  /**
   * @description:打开新页面
   */
  openLink({
    url,
    onSuccess,
    onFail
  }) {
    platform.openLink && platform.openLink({
      url,
      onSuccess,
      onFail
    });
  }

  /**
   * @description:打开会话
   * @param:{onSuccess, onFail}
   */
  toConversation({
    companyId,
    chatId,
    onSuccess,
    onFail
  }) {
    platform.toConversation &&
      platform.toConversation({
        companyId,
        chatId,
        onSuccess,
        onFail
      });
  }

  /**
   * @description:扫码
   * @param:onSuccess, onFail
   */
  scan(onSuccess, onFail) {
    platform.scan && platform.scan(onSuccess, onFail);
  }

  /**
   * @description:设置导航栏标题
   * @param:{onSuccess, onFail}
   */
  setTitle({
    title,
    onSuccess,
    onFail
  }) {
    platform.setTitle && platform.setTitle({
      title,
      onSuccess,
      onFail
    });
  }

  /**
   * @description:隐藏头部导航
   */
  setHideTitleBar() {
    platform.setHideTitleBar && platform.setHideTitleBar();
  }

  /**
   * @description:显示头部导航
   */
  setShowTitleBar() {
    platform.setShowTitleBar && platform.setShowTitleBar();
  }

  /**
   * @description:设置左侧导航按钮文本
   * @param:{onSuccess, onFail}
   */
  setLeft({
    control,
    text,
    onSuccess,
    onFail
  }) {
    platform.setLeft && platform.setLeft({
      control,
      text,
      onSuccess,
      onFail
    });
  }

  /**
   * @description:关闭当前页面
   * @param:{onSuccess, onFail}
   */
  navigationClose({
    onSuccess,
    onFail
  }) {
    platform.navigationClose && platform.navigationClose({
      onSuccess,
      onFail
    });
  }

  /**
   * @description:返回上一页
   * @param:{onSuccess, onFail}
   */
  goBack({
    onSuccess,
    onFail
  }) {
    platform.goBack && platform.goBack({
      onSuccess,
      onFail
    });
  }

  /**
   * @description:设置标题栏右侧按钮
   * @param:{show, control, text, onSuccess, onFail}
   */
  setRight({
    show,
    control,
    text,
    onSuccess,
    onFail
  }) {
    platform.setRight &&
      platform.setRight({
        show,
        control,
        text,
        onSuccess,
        onFail
      });
  }

  /**
   * @description:alert
   * @param:{message, title, buttonName, onSuccess, onFail}
   */
  alert({
    message,
    title,
    buttonName,
    onSuccess,
    onFail
  }) {
    platform.alert &&
      platform.alert({
        message,
        title,
        buttonName,
        onSuccess,
        onFail
      });
  }

  /**
   * @description:分享
   * @param:{message, title, buttonName, onSuccess, onFail}
   */
  share({
    type,
    url,
    title,
    content,
    image,
    onSuccess,
    onFail
  }) {
    platform.share &&
      platform.share({
        type,
        url,
        title,
        content,
        image,
        onSuccess,
        onFail
      });
  }

  /**
   * @description:图片预览
   * @param:{img, onSuccess, onFail}
   */
  previewImage({
    img,
    onSuccess,
    onFail
  }) {
    platform.previewImage && platform.setRight({
      img,
      onSuccess,
      onFail
    });
  }


}
