<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      companyId: window.localStorage.getItem('companyId'),
    }
  },
  metaInfo() {
    return {
      title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
      titleTemplate: title => {
        return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
      }
    }
  },
  mounted() {
    window.localStorage.removeItem('IS-WEI-XIN-CONFIG')
    if (this.dw.isDingtalk()) {
      // console.log('钉钉环境')
      let isDTOpen = window.DTOpenData.init(this.companyId)
      if (!isDTOpen) {
        let url = encodeURIComponent(window.location.href)
        url = 'http://auth.dingtalk.com/login?redirectUri=' + url
        let encodedUrl = encodeURIComponent(url);
        window.location.href = 'https://login.dingtalk.com/oauth2/auth?response_type=code&client_id=dingwa4tibze6jwz7mgv&scope=openid&state=dddd&redirect_uri=' + encodedUrl;
      }
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
.choice-category {
  .el-cascader-panel {
    .el-cascader-menu {
      max-width: 240px;
    }
  }
}

.el-upload-list__item:hover {
  background-color: #5881db !important;

  a {
    color: #fff !important;
  }

  i {
    color: #fff !important;
  }

  .el-icon-close {
    position: absolute;
    top: 5px;
    right: 5px;
  }
}

li {
  list-style: none;
  /*text-align: left;*/
}

.el-menu.el-menu--horizontal {
  border-bottom: solid 0px #e6e6e6 !important;
}

.el-tabs__nav-wrap::after {
  content: "";
  position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 1px;
  background-color: #dfe4ed;
  z-index: 1;
}

.el-dialog {
  z-index: 9999 !important;
}

.el-tree--highlight-current .el-tree-node.is-current>.el-tree-node__content {
  background: #5881db !important;
  color: #fff !important;
  border-radius: 4px !important;
}

.el-tree-node__content:hover,
.el-upload-list__item:hover {
  background-color: #e8eef8 !important;
  border-radius: 4px !important;
  font-weight: bold;
  color: black;
}


/* 针对Chrome/Safari */
::-webkit-scrollbar {
    width: 6px; /* 滚动条的宽度 */
}

::-webkit-scrollbar-track {
    background: #ffffff; /* 滚动条的轨道背景色 */
}

::-webkit-scrollbar-thumb {
    background: #e8eef8; /* 滚动条的滑块颜色 */
    border-radius: 6px; /* 滚动条的滑块圆角 */
}

::-webkit-scrollbar-thumb:hover {
    background: #e8eef8; /* 当鼠标悬停在滚动条滑块上时的颜色 */
}
</style>
