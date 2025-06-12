<template>
  <div class="app-index">

    <myHeader></myHeader>
    <div class="content" :class="{ content2: userData.edition == 'free' && !isClose }">
      <!-- <div class="router-cont"> -->
      <router-view class="router-cont"></router-view>
      <!-- </div> -->
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import myHeader from '@/components/commonHeader/index'
export default {
  name: 'index',
  data() {
    return {
      activeIndex: 'home',
      userData: {},
      isClose: false,
      companyId: window.localStorage.getItem('companyId'),
    }
  },
  components: { myHeader },

  mounted() {
    if (window.localStorage.getItem('userInfo')) {
      this.userData = JSON.parse(window.localStorage.getItem('userInfo'))
    }
  },
  computed: {
    wxAgentConfigSuccess() {
      return this.$store?.state?.wxAgentConfigSuccess
    },
  },
  methods: {
    handleSelect(key, keyPath) {
      // console.log(key, keyPath)
    },

  },
}
</script>

<style lang="less">
.app-index {
  width: 100%;
  height: 100%;

  .top-tip-free {
    width: 100%;
    height: 30px;
    background: rgba(88, 129, 219, 0.2);
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    color: #5881db;
    padding: 0 20px;
    box-sizing: border-box;

    // position: fixed;
    // top: 0;
    // left: 0;
    .iconfont {
      margin-right: 5px;
    }

    .close {
      cursor: pointer;
    }
  }

  .content {
    width: 100%;
    display: flex;
    justify-content: center;
    height: calc(100% - 85px);
    // padding-top: 10px;
    box-sizing: border-box;
    overflow-y: auto;
    overflow-x: auto;

    &.content2 {
      height: calc(100% - 115px) !important;
      box-sizing: border-box;
      overflow-y: auto;
      overflow-x: auto;
    }

    .router-cont {
      min-width: 1000px;
      flex-shrink: 0;
      background: #f5f5f5;
    }
  }

  @media screen and (min-width: 1301px) {
    .cont_all_width {
      width: 1200px;
      margin: 0 auto;
    }
  }

  @media screen and (max-width: 1300px) {
    .cont_all_width {
      width: 1000px;
      margin: 0 auto;
    }
  }
}
</style>
