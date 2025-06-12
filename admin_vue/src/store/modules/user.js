import {
  login,
  logout,
  getInfo
} from '@/api/login'
import axios from 'axios';
import {
  getToken,
  setToken,
  removeToken
} from '@/utils/auth'

const user = {
  state: {
    token: getToken(),
    name: '',
    avatar: '',
    roles: [],
    permissions: [],
    // 企微鉴权成功标识
    wxAgentConfigSuccess: false,
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions
    },
    // 更改企微鉴权成功标识
    SET_WX_AGENT_CONFIG_SUCCESS: (state, e) => {
      console.log('改变wxAgentConfigSuccess状态')
      state.wxAgentConfigSuccess = e;
    },
  },

  actions: {
    // 登录
    Login({
      commit
    }, userInfo) {
      const username = userInfo.username.trim()
      const password = userInfo.password
      const code = userInfo.code
      const uuid = userInfo.uuid
      return new Promise((resolve, reject) => {
        login(username, password, code, uuid).then(res => {
          setToken(res.data.token)
          commit('SET_TOKEN', res.data.token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({
      commit,
      state
    }) {
      return new Promise((resolve, reject) => {
        getInfo().then(res => {
          const user = res.user
          const avatar = user.avatar == "" ? require("@/assets/images/profile.jpg") : process.env.VUE_APP_BASE_API + user.avatar;
          if (res.roles && res.roles.length > 0) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', res.roles)
            commit('SET_PERMISSIONS', res.permissions)
          } else {
            commit('SET_ROLES', ['ROLE_DEFAULT'])
          }
          commit('SET_NAME', user.userName)
          commit('SET_AVATAR', avatar)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 退出系统
    LogOut({
      commit,
      state
    }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          commit('SET_PERMISSIONS', [])
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({
      commit
    }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    },

    // 企业微信通讯录组件鉴权
    wxAgentConfig({
      commit,
      state
    }, vThis) {
      const _companyId = window.localStorage.getItem('companyId');
      const _href = window.location.href;
      // const _href = 'https://app135926.eapps.dingtalkcloud.com/dist/index'
      console.log('调用鉴权')
      return new Promise((resolve, reject) => {
        // 当不存在企业id时
        if (!_companyId) {
          reject({
            errMsg: '企业ID不存在'
          });
          return;
        }
        // 当存在vuex时
        if (window.WWOpenData && state.wxAgentConfigSuccess) {
          resolve();
          return;
        }
        // 新增标识，防止一个页面有多个组件使用
        const _requestKey = 'IS-WEI-XIN-CONFIG';
        const _isWeiXinIsvRequest = localStorage.getItem(_requestKey);
        if (_isWeiXinIsvRequest) {
          localStorage.removeItem(_requestKey);
          resolve();
          return;
        }
        localStorage.setItem(_requestKey, true);

        if (!window.WWOpenData) {
          axios({
            method: "post",
            url: "https://web.seeklove.space/prod-api/getJsSignAgent",
            params: {
              corp_id: _companyId,
              url: _href,
            },
            headers: {
              isToken: false
            },
          }).then((res) => {
            console.log(res);
            let resData = res.data.data;
            let dwSet = setTimeout(() => {

              vThis.dw.WxAgentConfigStart({
                corpid: resData.corpId,
                agentid: resData.agentId,
                timestamp: resData.timestamp,
                nonceStr: resData.nonceStr,
                signature: resData.signature,
                jsApiList: [
                  'selectExternalContact',
                  'startLiving',
                  'replayLiving'
                ],
                onSuccess: () => {
                  localStorage.removeItem(_requestKey);
                  commit('SET_WX_AGENT_CONFIG_SUCCESS', true);
                  resolve();
                },
                onFail: error => {
                  localStorage.removeItem(_requestKey);
                  reject(error);
                }
              });
              clearTimeout(dwSet)
            }, 500);
          }).catch(err => {
            localStorage.removeItem(_requestKey);
          })


        }
      });
    },
  }
}

export default user
