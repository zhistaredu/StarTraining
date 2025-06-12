import axios from 'axios';
export default {

  // 企业微信通讯录组件鉴权
  wxAgentConfig({
    commit,
    state
  }, vThis) {
    const _companyId = window.localStorage.getItem('companyId');
    const _href = window.location.href;
    console.log('调用鉴权')
    return new Promise((resolve, reject) => {
      // 当不存在企业id时
      if (!_companyId) {
        reject({
          errMsg: '企业ID不存在'
        });
        return;
      }



    });

  },

}
