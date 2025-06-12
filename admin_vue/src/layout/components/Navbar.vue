<template>
  <div class="navbar">
    <!-- logo -->
    <div class="logo-box">
      <logo v-if="showLogo" />
    </div>
    <!-- c侧边栏开关 -->
    <hamburger
      id="hamburger-container"
      :is-active="sidebar.opened"
      class="hamburger-container"
      @toggleClick="toggleSideBar"
    />

    <breadcrumb
      id="breadcrumb-container"
      class="breadcrumb-container"
      v-if="!topNav"
    />
    <top-nav id="topmenu-container" class="topmenu-container" v-if="topNav" />

    <div class="right-menu">
      <!-- <template v-if="device!=='mobile'">
        <search id="header-search" class="right-menu-item" />

        <el-tooltip content="源码地址" effect="dark" placement="bottom">
          <ruo-yi-git id="ruoyi-git" class="right-menu-item hover-effect" />
        </el-tooltip>

        <el-tooltip content="文档地址" effect="dark" placement="bottom">
          <ruo-yi-doc id="ruoyi-doc" class="right-menu-item hover-effect" />
        </el-tooltip>

        <screenfull id="screenfull" class="right-menu-item hover-effect" />

        <el-tooltip content="布局大小" effect="dark" placement="bottom">
          <size-select id="size-select" class="right-menu-item hover-effect" />
        </el-tooltip>

      </template> -->

      <div class="avatar-wrapper">
        <img :src="userData.avatar" alt="" class="user-avatar" />

        <OpenDataCom
          type="userName"
          v-if="userData.userName"
          :openid="userData.userName"
          :defaultname="userData.userName"
        ></OpenDataCom>
      </div>

      <div class="student" @click="goStudent">前往学员端</div>

      <!-- <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <img :src="userData.avatar" class="user-avatar">

          <OpenDataCom type="userName" v-if="userData.userName" :openid="userData.userName"
            :defaultname="userData.userName"></OpenDataCom>
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/user/profile">
            <el-dropdown-item>个人中心</el-dropdown-item>
          </router-link>

          <el-dropdown-item divided @click.native="logout">
            <span>退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown> -->
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Logo from "./Sidebar/Logo";
import Breadcrumb from "@/components/Breadcrumb";
import TopNav from "@/components/TopNav";
import Hamburger from "@/components/Hamburger";
import Screenfull from "@/components/Screenfull";
import SizeSelect from "@/components/SizeSelect";
import Search from "@/components/HeaderSearch";
import RuoYiGit from "@/components/RuoYi/Git";
import RuoYiDoc from "@/components/RuoYi/Doc";
export default {
  components: {
    Logo,
    Breadcrumb,
    TopNav,
    Hamburger,
    Screenfull,
    SizeSelect,
    Search,
    RuoYiGit,
    RuoYiDoc,
  },
  computed: {
    ...mapGetters(["sidebar", "avatar", "device"]),
    setting: {
      get() {
        return this.$store.state.settings.showSettings;
      },
      set(val) {
        this.$store.dispatch("settings/changeSetting", {
          key: "showSettings",
          value: val,
        });
      },
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav;
      },
    },
    showLogo() {
      return this.$store.state.settings.sidebarLogo;
    },
    isCollapse() {
      return !this.sidebar.opened;
    },
  },
  data() {
    return {
      userData: {},
    };
  },
  mounted() {
    setTimeout(() => {
      if (window.localStorage.getItem("userInfo")) {
        this.userData = JSON.parse(window.localStorage.getItem("userInfo"));
      }
    }, 100);
  },
  methods: {
    // 前往学员端
    goStudent() {
      let time = new Date().getTime()
      window.open(process.env.VUE_APP_BASE_URL + '/pcuser/?v=' + time, '_self')
    },
    toggleSideBar() {
      this.$store.dispatch("app/toggleSideBar");
    },
    async logout() {
      this.$confirm("确定注销并退出系统吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then((action) => {
        if (action == "confirm") {
          this.$store.dispatch("LogOut").then(() => {
            location.href = "/index";
          });
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.navbar {
  width: 100%;
  z-index: 1899;
  height: 80px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  display: flex;
  align-items: center;
  justify-content: space-between;

  .logo-box {
    width: 180px;
    height: 60px;
    flex-shrink: 0;
  }

  .hamburger-container {
    line-height: 80px;
    height: 100%;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .breadcrumb-container {
    flex-shrink: 0;
  }

  .topmenu-container {
    flex: 1;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    height: 100%;
    display: flex;
    align-items: center;

    &:focus {
      outline: none;
    }

    .student {
      padding: 0 15px;
      height: 36px;
      line-height: 36px;
      border-radius: 5px;
      font-size: 14px;
      color: #5881db;
      background: #fff;
      border: 1px solid #5881db;
      margin-right: 20px;
      margin-left: 20px;
      cursor: pointer;
      white-space: nowrap;

      &:hover {
        background: #5881db;
        color: #fff;
      }
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.025);
        }
      }
    }

    .avatar-wrapper {
      // max-width: 150px;
      position: relative;
      display: flex;
      align-items: center;

      .user-avatar {
        cursor: pointer;
        width: 50px;
        height: 50px;
        border-radius: 50%;
        margin-right: 10px;
      }

      .el-icon-caret-bottom {
        cursor: pointer;
        position: absolute;
        right: -20px;
        top: 25px;
        font-size: 12px;
      }
    }

    .avatar-container {
      margin-right: 30px;
    }
  }
}
</style>
