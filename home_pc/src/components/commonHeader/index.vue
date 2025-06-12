<template>
  <div class="header-index" v-if="isShow">
    <div class="header">
      <div class="lf">
        <div class="logo">
          <img src="@assets/img/logo2.png" alt="">
          <span>{{ userData.companyName }}</span>
        </div>

        <div class="menu">
          <ul>
            <li @click="handleSelect('home')" :class="{ 'active': activeIndex == 'home' }">
              <span>首页</span>
            </li>
            <li @click="handleSelect('knowledge')" :class="{ 'active': activeIndex == 'knowledge' }">
              <span>知识中心</span>
            </li>
            <li @click="handleSelect('lecturer')" :class="{ 'active': activeIndex == 'lecturer' }">
              <span>讲师中心</span>
            </li>
            <li @click="handleSelect('mine')" :class="{ 'active': activeIndex == 'mine' }">
              <span>个人中心</span>
            </li>

          </ul>

        </div>
      </div>
      <div class="user-data">
        <div class="user-avatar" v-if="userData.userId">
          <img :src="userData.avatar" alt="" class="user-avatar-img"  />
          <div class="name">
            {{userData.userName}}
          </div>

        </div>

      </div>
    </div>
  </div>
</template>

<script>
import { isAdmin } from '@/api/user'
import Vconsole from 'vconsole'
export default {
  name: 'myheader',
  data() {
    return {
      activeIndex: 'home',
      userData: {},
      isShow: true,
      isAdminToken: false, // 是否有管理员权限
      timer: null, // 设置定时器
      clickCountNow: 0, // 置初始点击计数
      clickTatgatNum: 10, // 连续点击N次触发xxx
    }
  },
  components: {},
  watch: {
    '$route.path'(newV) {
      console.log(newV)
      this.activeIndex = newV.split('/')[1]
      if (window.localStorage.getItem('userInfo')) {
        this.userData = JSON.parse(window.localStorage.getItem('userInfo'))
      }
    },
  },
  created() {
    this.getisAdmin()
    // console.log(this.$route.path)
    let routerPath = this.$route.path

    // console.log(routerPath.split('/'))
    this.activeIndex = routerPath.split('/')[1]
    if (window.localStorage.getItem('userInfo')) {
      this.userData = JSON.parse(window.localStorage.getItem('userInfo'))
    }

  },
  methods: {

    getisAdmin() {
      isAdmin()
        .then((res) => {
          if (res.status === 'ok') {
            this.isAdminToken = res.data.is_master
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    handleSelect(name) {

      this.activeIndex = name
      this.$router.push({
        path: '/' + name,
      })
    },
    // 去登录
    gotoLogin() {
      this.$router.push({
        name: 'login',
      })
    },
    // 退出登录
    logout() {
      window.localStorage.removeItem('AuthorizationToken')
      window.localStorage.removeItem('companyId')
      window.localStorage.removeItem('userInfo')
      this.$router.push({
        name: 'login',
      })
    },
    // 前往管理员端
    toadmin() {
      let time = new Date().getTime()
      window.open(process.env.VUE_APP_BASE_API + '/dist/?fromLogin=1&v=' + time, '_self')


    },
  },
}
</script>

<style lang="less">
.header-index {
  width: 100%;
  min-width: 1000px;
  height: 80px;
  background: #fff;
  padding: 0 30px;
  box-sizing: border-box;
  border-bottom: 1px solid #eee;
  box-sizing: border-box;

  .el-menu-item {
    font-size: 16px;
  }

  .el-menu--horizontal>.el-menu-item.is-active {
    font-size: 16px;
  }

  .header {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .menu {
      flex-shrink: 0;
      padding-left: 20px;
      box-sizing: border-box;
      height: 100%;

      ul {
        display: flex;
        align-items: center;

        li {
          cursor: pointer;
          margin-left: 40px;
          font-size: 18px;
          color: #333;
          display: flex;
          align-items: center;

          &:hover {
            color: #5881db;
          }

          &.active {
            color: #5881db;
            font-weight: bold;
          }

          &:last-child {
            margin-left: 50px;

          }

          i.iconfont {
            margin-right: 5px;
            font-size: 18px;
          }
        }
      }
    }

    .lf {
      display: flex;
      align-items: center;

      .logo {
        display: flex;
        align-items: center;

        img {
          width: 60px;
          height: 60px;
          object-fit: contain;
          margin-right: 10px;
        }

        span {
          font-size: 18px;
          font-weight: bold;
        }
      }
    }

    .user-data {
      .login {
        font-size: 16px;
        color: #333;
        cursor: pointer;

        &:hover {
          color: #5881db;
        }
      }

      .user-avatar {
        font-size: 16px;
        color: #333;
        display: flex;
        align-items: center;

        img {
          width: 50px;
          height: 50px;
          border-radius: 50%;
          margin-right: 10px;
        }

        .name {
          max-width: 150px;
          overflow: hidden;
          white-space: nowrap;
        }

        span {
          font-size: 14px;
          color: #333;
          margin-left: 5px;
        }

        .admin {
          padding: 0 15px;
          height: 30px;
          line-height: 30px;
          border-radius: 5px;
          font-size: 14px;
          color: #5881db;
          background: #fff;
          border: 1px solid #5881db;
          margin-left: 20px;
          cursor: pointer;
          white-space: nowrap;

          &:hover {
            background: #5881db;
            color: #fff;
          }
        }

        .btn {
          padding: 0 15px;
          height: 30px;
          line-height: 30px;
          border-radius: 5px;
          font-size: 14px;
          color: #fff;
          background: #5881db;
          margin-left: 20px;
          cursor: pointer;
          white-space: nowrap;

          &:hover {
            background: #3972ee;
          }
        }
      }
    }
  }
}
</style>
