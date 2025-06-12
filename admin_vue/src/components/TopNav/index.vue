<template>
  <el-menu class="el-menu-demo" :default-active="activeMenu" mode="horizontal" text-color="#333"
    active-text-color="#5881db" @select="handleSelect">
    <template v-for="(item, index) in topMenus">
      <el-menu-item :index="item.path" :key="index" v-if="index < visibleNumber">
        <!-- <svg-icon :icon-class="item.meta.icon" /> -->
        {{ item.meta.title }}
      </el-menu-item>
    </template>

    <!-- 顶部菜单超出数量折叠 -->
    <el-submenu index="more" v-if="topMenus.length > visibleNumber">
      <template slot="title">更多菜单</template>
      <template v-for="(item, index) in topMenus">
        <el-menu-item :index="item.path" :key="index" v-if="index >= visibleNumber">
          <!-- <svg-icon :icon-class="item.meta.icon" /> -->
          {{ item.meta.title }}
        </el-menu-item>
      </template>
    </el-submenu>
  </el-menu>
</template>

<script>
import { constantRoutes } from "@/router";

export default {
  data() {
    return {
      // 顶部栏初始数
      visibleNumber: 6,
      // 是否为首次加载
      isFrist: false,
      // 当前激活菜单的 index
      currentIndex: undefined
    };
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme;
    },
    // 顶部显示菜单
    topMenus() {
      let topMenus = []
      // let topMenus = [{alwaysShow: true, hidden: false, meta: {title: '工作台'}, name: 'index', path: '/index'}];
      this.routers.map((menu) => {
        // console.log(menu)
        if (menu.hidden !== true) {
          // 兼容顶部栏一级菜单内部跳转
          if (menu.path === "/") {
            topMenus.push(menu.children[0]);
          } else {
            topMenus.push(menu);
          }
        }
      });
      return topMenus;
    },
    // 所有的路由信息
    routers() {
      return this.$store.state.permission.topbarRouters;
    },
    // 设置子路由
    childrenMenus() {
      var childrenMenus = [];
      this.routers.map((router) => {
        for (var item in router.children) {
          if (router.children[item].parentPath === undefined) {
            if (router.path === "/") {
              router.children[item].path = "/redirect/" + router.children[item].path;
            } else {
              if (!this.ishttp(router.children[item].path)) {
                router.children[item].path = router.path + "/" + router.children[item].path;
              }
            }
            router.children[item].parentPath = router.path;
          }
          childrenMenus.push(router.children[item]);
        }
      });
      return constantRoutes.concat(childrenMenus);
    },
    // 默认激活的菜单
    activeMenu() {
      const path = this.$route.path;
      let activePath = this.defaultRouter();
      if (path.lastIndexOf("/") > 0) {
        const tmpPath = path.substring(1, path.length);
        activePath = "/" + tmpPath.substring(0, tmpPath.indexOf("/"));
      } else if ("/index" == path || "" == path) {
        if (!this.isFrist) {
          this.isFrist = true;
        } else {
          activePath = "index";
        }
      }
      var routes = this.activeRoutes(activePath);
      if (routes.length === 0) {
        activePath = this.currentIndex || this.defaultRouter()
        this.activeRoutes(activePath);
      }
      return activePath;
    },
  },
  beforeMount() {
    window.addEventListener('resize', this.setVisibleNumber)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.setVisibleNumber)
  },
  mounted() {
    this.setVisibleNumber();
  },
  methods: {
    // 根据宽度计算设置显示栏数
    setVisibleNumber() {
      const width = document.body.getBoundingClientRect().width / 2;
      this.visibleNumber = parseInt(width / 100);
    },
    // 默认激活的路由
    defaultRouter() {
      let router;
      Object.keys(this.routers).some((key) => {
        if (!this.routers[key].hidden) {
          router = this.routers[key].path;
          return true;
        }
      });
      return router;
    },
    // 菜单选择事件
    handleSelect(key, keyPath) {
      // console.log(key)
      this.currentIndex = key;

      if (this.ishttp(key)) {
        // http(s):// 路径新窗口打开
        window.open(key, "_blank");
      } else if (key.indexOf("/redirect") !== -1) {
        // /redirect 路径内部打开
        this.$router.push({ path: key.replace("/redirect", "") });
      } else {
        // 显示左侧联动菜单
        this.activeRoutes(key);
        // 打开第一个
        for (var i in this.topMenus) {
          if (key == this.topMenus[i].path) {
            // console.log(this.topMenus[i].children)
            let arr = []
            if (this.topMenus[i].children && this.topMenus[i].children.length) {
              arr = this.topMenus[i].children
              for (var j in arr) {
                if (arr[j].hidden == false) {
                  let arr2 = []
                  if (arr[j].children && arr[j].children.length) {
                    arr2 = arr[j].children
                    for (var k in arr2) {
                      if (arr2[k].hidden == false) {

                        let newpath = arr[j].path + '/' + arr2[k].path
                        this.$router.push({ path: newpath });

                        return
                      }
                    }
                  } else {
                    // console.log(arr[j].path)
                    this.$router.push({ path: arr[j].path });
                  }
                  return
                }
              }
            } else {
              this.$router.push({ path: key });
            }
          }
        }
        // this.$router.push({ path: key });
      }
    },
    // 当前激活的路由
    activeRoutes(key) {
      var routes = [];
      if (this.childrenMenus && this.childrenMenus.length > 0) {
        this.childrenMenus.map((item) => {
          if (key == item.parentPath || (key == "index" && "" == item.path)) {
            routes.push(item);
          }
        });
      }
      if (routes.length > 0) {
        this.$store.commit("SET_SIDEBAR_ROUTERS", routes);
      }
      return routes;
    },
    ishttp(url) {
      return url.indexOf('http://') !== -1 || url.indexOf('https://') !== -1
    }
  },
};
</script>

<style lang="scss" scoped>
.el-menu-demo{
  display: flex;
  align-items: center;
}
.topmenu-container.el-menu--horizontal>.el-menu-item {
  float: left;
  height: 60px !important;
  line-height: 60px !important;
  color: #333 !important;
  padding: 0 5px !important;
  margin: 0 15px !important;
  font-size: 18px;
}

.topmenu-container.el-menu--horizontal>.el-menu-item.is-active,
.el-menu--horizontal>.el-submenu.is-active .el-submenu__title {
  border-bottom: 2px solid transparent !important;
  color: #5881db !important;
  font-weight: bold;
}

/* submenu item */
.topmenu-container.el-menu--horizontal>.el-submenu .el-submenu__title {
  float: left;
  height: 50px !important;
  line-height: 50px !important;
  color: #333 !important;
  padding: 0 5px !important;
  margin: 0 15px !important;
  font-size: 18px;
}
</style>
