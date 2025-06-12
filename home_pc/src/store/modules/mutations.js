export default {

  // 更改企微鉴权成功标识
  SET_WX_AGENT_CONFIG_SUCCESS(state, e) {
    console.log('改变wxAgentConfigSuccess状态')
    state.wxAgentConfigSuccess = e;
  },

}
