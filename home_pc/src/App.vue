<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {}
  },
  methods: {},
  mounted() { },
}
</script>


<style lang="less">
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

* {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
  white-space: normal;
  word-break: break-all;
}

.el-tabs__header {
  margin: 0;
}

body {
  -webkit-user-select: none;
  /* Chrome, Safari, Opera */
  -moz-user-select: none;
  /* Firefox */
  -ms-user-select: none;
  /* Internet Explorer/Edge */
  user-select: none;
  /* Non-prefixed version, currently not supported by any browser */
}

div::-webkit-scrollbar {
  width: 4px;
}

div::-webkit-scrollbar-thumb {
  border-radius: 10px;
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  opacity: 0.2;
  background: #dad7d7;
}

div::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  border-radius: 0;
  background: #f5f5f5;
}

.el-tabs__nav-wrap::after {
  background-color: transparent;
}

.el-tabs__item.is-active {
  color: #5881db !important;
  font-weight: bold;
  font-size: 15px;
}

.el-tabs__active-bar {
  background-color: #5881db !important;
}

.el-tabs__item:hover {
  color: #5881db !important;
}

.el-menu--horizontal>.el-menu-item.is-active {
  border-bottom: 2px solid #5881db !important;
  color: #5881db !important;
  font-weight: bold;
  font-size: 15px;
}

.el-radio__input.is-checked+.el-radio__label {
  color: #5881db !important;
}

.el-radio__input.is-checked .el-radio__inner {
  background: #5881db !important;
  border-color: #5881db !important;
}

.el-pager li.active {
  color: #5881db !important;
  font-weight: bold;
}

.el-pager li:hover {
  color: #5881db !important;
}

.el-tree--highlight-current .el-tree-node.is-current>.el-tree-node__content {
  background-color: #5881db !important;
  color: #fff;
}

.el-tree-node__content:hover,
.el-upload-list__item:hover {
  background-color: #b0c9fd !important;
  color: #fff;
}

input,
button {
  background: none;
  border: 0;
  margin: 0;
  padding: 0;
}

html {
  height: 100%;
}

.el-pagination {
  margin: 10px auto;
  text-align: right;
}

body {
  position: relative;
  margin: 0 auto;
  height: 100%;
  white-space: normal;
  word-break: break-all;
  font-size: 18px;
  font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB,
    Microsoft YaHei, SimSun, sans-serif;
  background: #f5f5f5;
}

a {
  text-decoration: none;
}

ul,
li {
  list-style: none;
}

i {
  font-style: normal;
}

a {
  -webkit-tap-highlight-color: rgba(255, 255, 255, 0);
  -webkit-user-select: none;
  -moz-user-focus: none;
  -moz-user-select: none;
}

input {
  outline: none;
  outline-color: invert;
  outline-style: none;
  outline-width: 0px;
  border: none;
  border-style: none;
  text-shadow: none;
  -webkit-user-select: text;
  outline-color: transparent;
  box-shadow: none;
}

.el-message-box__wrapper {
  overflow-y: auto;
}

.el-message-box {
  width: auto !important;
  min-width: 420px !important;
  max-width: 75% !important;
}

.app-alertBg {
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
}

.app-alertBg .app-confirm {
  min-width: 400px;
  background: #fff;
  position: absolute;
  top: 50%;
  left: 50%;
  margin-top: -75px;
  margin-left: -200px;
}

.app-alertBg .app-confirm .confirm-title {
  height: 40px;
  line-height: 40px;
  text-align: center;
}

.app-alertBg .app-confirm .confirm-msg {
  line-height: 40px;
  text-align: center;
}

.app-alertBg .app-confirm .confirm-btn {
  height: 60px;
  line-height: 60px;
  text-align: center;
}

.app-alertBg .app-confirm .confirm-btn span {
  display: inline-block;
  width: 49%;
}

.nav-drop {
  .el-dropdown-menu__item {

    &:hover {
      background-color: #ffffff;
      color: #000000;
    }
  }
}

.tab-text {
  .el-dropdown {
    color: #000000 !important;
  }
}

#app {
  width: 100%;
  height: 100%;
  overflow: hidden;

  &.blur {
    filter: blur(7px);
  }

  &.top-50 {
    >div {
      >.router {
        margin-top: 50px;
      }
    }
  }
}
</style>
