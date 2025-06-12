<template>
  <div class="login">
    <div class="login-box">
      <div id="ww_login"></div>
      <div class="login-content">
        <div class="tab">
          <div class="active">职星学院企业培训系统</div>
        </div>
        <div class="cont">
          <div class="login-word">
            <div class="line">
              <label for="tel"> 用户名 </label>
              <input type="text" id="tel" v-model="username" />
            </div>

            <div class="line">
              <label for="password"> 密码 </label>
              <input type="password" id="password" v-model="password" />
            </div>
            <div class="line btn-cont">
              <div class="btn" @click="loginWord">登录</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getUserInfo, login } from '@/api/user'
export default {
  name: 'Login',
  mixins: [],
  data() {
    return {
      type: 'word',
      username: 'admin', // 用户名
      password: '123456', // 密码
      isChoice: false,
      isDevelp: '', // 判断是否是测试环境
    }
  },
  mounted() {
    // console.log(process.env.NODE_ENV)
    // 判断是否是测试环境
    this.isDevelp = process.env.NODE_ENV == 'development' ? true : false
  },
  methods: {
    changeType(type) {
      this.type = type
    },
    // 密码登录
    loginWord() {
      login({
        username: this.username,
        password: this.password,
      })
        .then((res) => {
          if (res.status === 'ok') {
            window.localStorage.setItem('AuthorizationToken', res.data.token)
            window.localStorage.setItem('companyId', res.data.companyId)
            window.localStorage.setItem('userInfo', JSON.stringify(res.data))
            setTimeout(() => {
              this.$router.push({
                path: '/home',
              })
            }, 500)
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },

  },
  beforeRouteLeave(to, from, next) {
    next()
  },
}
</script>

<style lang="less">
.login {
  width: 100%;
  height: 100%;
  background: #fff;

  .choice-box {
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    .choice-company {
      width: 400px;
      height: 300px;
      background: #fff;
      border-radius: 10px;
      padding: 0 10px;
      box-sizing: border-box;

      .choice-name {
        font-size: 16px;
        color: #333;
        font-weight: bold;
        line-height: 80px;
        text-align: center;
      }

      .choice-list {
        .choice-item {
          width: 100%;
          height: 50px;
          border-bottom: 1px solid #eee;
          padding: 0 15px;
          box-sizing: border-box;
          display: flex;
          align-items: center;
          justify-content: space-between;
          cursor: pointer;

          .name {
            font-size: 14px;
            color: #333;
          }

          .iconfont {
            font-size: 12px;
            color: #333;
          }
        }
      }
    }
  }
  .login-nosee-cont {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    background: url(~@/assets/img/login-bg.png) no-repeat;
    background-size: 100% 100%;
    .tips {
      font-size: 24px;
      color: #fff;
    }
  }

  .login-box {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    background: url(~@/assets/img/login-bg.png) no-repeat;
    background-size: 100% 100%;
  }

  .img {
    position: absolute;
    right: -100px;
    top: -200px;
    height: 200px;
  }

  .login-content {
    position: relative;
    background-color: #fff;
    // background: url(~@/assets/img/head-bg.png) no-repeat;
    // background-size: 100% auto;
    width: 500px;
    // min-height: 330px;
    border-radius: 10px;
    // background: #fff;
    box-shadow: 0 0 12px rgba(0, 0, 0, 0.3);

    .tab {
      width: 100%;
      height: 60px;
      display: flex;
      align-items: center;
      justify-content: space-around;
      border-bottom: 1px solid #eee;
      font-size: 16px;
      font-weight: bold;

      div {
        cursor: pointer;
      }

      div.active {
        color: #5881db;
        font-size: 18px;
      }
    }

    .cont {
      width: 100%;
      padding: 10px 0;
      box-sizing: border-box;

      .login-word,
      .login-code {
        width: 100%;
        height: 280px;
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        align-items: center;

        .line {
          width: 100%;
          height: 50px;
          display: flex;
          align-items: center;
          justify-content: center;
          position: relative;

          label {
            flex-shrink: 0;
            width: 70px;
            text-align: right;
            padding-right: 10px;
            box-sizing: border-box;
            font-size: 16px;
            color: #333;
          }

          input {
            width: 300px;
            height: 50px;
            border: 1px solid #eee;
            box-sizing: border-box;
            border-radius: 4px;
            color: #333;
            font-size: 16px;
            padding: 0 10px;
            box-sizing: border-box;
          }

          #code {
            width: 200px;
          }

          .get {
            width: 90px;
            height: 50px;
            text-align: center;
            line-height: 50px;
            background: #5881db;
            color: #fff;
            cursor: pointer;
            border-radius: 10px;
            font-size: 14px;
            margin-left: 10px;
          }

          .forget {
            font-size: 14px;
            color: #666;
            cursor: pointer;
            position: absolute;
            bottom: -20px;
            line-height: 20px;
            right: 70px;
            text-decoration: underline;

            &:hover {
              color: #5881db;
            }
          }

          .tip {
            font-size: 14px;
            color: #666;
            cursor: pointer;
            position: absolute;
            bottom: 0;
            line-height: 30px;
            left: 0;
            text-align: center;
            width: 100%;

            &:hover {
              color: #5881db;
            }
          }

          .btn {
            width: 200px;
            height: 36px;
            border: 1px solid #5881db;
            color: #5881db;
            box-sizing: border-box;
            text-align: center;
            line-height: 34px;
            font-size: 16px;
            border-radius: 18px;
            cursor: pointer;

            &:hover {
              background: #5881db;
              color: #fff;
            }
          }
        }

        .btn-cont {
          height: 80px;
          // padding-bottom: 30px;
          box-sizing: border-box;
        }
      }
    }
  }
}
</style>
