import Vue from 'vue';
import Router from 'vue-router';
const whiteList = ['/login']
Vue.use(Router)

const router = new Router({
  base: '/pcuser', // 配置单页应用的基路径
  mode: 'history', // hash
  routes: [{
      path: '/',
      name: 'index',
      redirect: 'home',
      component: () => import( /* webpackChunkName: 'index' */ '@pages/index/index.vue'),
      meta: {
        auth: false,
        title: ''
      },
      children: [{
          path: 'home',
          name: 'home',
          component: () => import('@pages/home/home.vue'),
          meta: {
            auth: false,
            title: ''
          },
          children: [{
              path: '',
              name: 'homeList',
              component: () => import('@pages/home/index/index.vue'),
            }
          ]
        },

        {
          path: 'knowledge',
          name: 'knowledge',
          component: () => import('@pages/knowledge/center.vue'),
          meta: {
            auth: false,
            title: ''
          },
          children: [{
              path: '',
              name: 'knowledgeList',
              component: () => import('@pages/knowledge/centerList/index.vue'),
            },
            {
              path: 'detail',
              name: 'knowledgeDetail',
              component: () => import('@pages/knowledge/detail/index'),
            },
            {
              path: 'learn',
              name: 'learnKnowledge',
              component: () => import('@pages/knowledge/learn/index'),
            },
          ]
        },
        {
          path: 'lecturer',
          name: 'lecturer',
          component: () => import('@pages/lecturer/center.vue'),
          meta: {
            auth: false,
            title: ''
          },
          children: [{
              path: '',
              name: 'lecturerList',
              component: () => import('@pages/lecturer/centerList/index.vue'),
            },
            {
              path: 'detail',
              name: 'lecturerDetail',
              component: () => import('@pages/lecturer/detail/index'),
            },
          ]
        },
        {
          path: 'mine',
          name: 'mine',
          component: () => import('@pages/mine/mine.vue'),
          meta: {
            auth: false,
            title: ''
          },
          children: [{
              path: '',
              name: 'mineIndex',
              component: () => import('@pages/mine/index/index.vue'),
            }
          ]
        },
      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('@pages/login/login.vue'),
    },
    {
      path: '*',
      name: '404',
      component: () => import('@pages/error/index.vue'),
      meta: {
        auth: false,
        title: '404页面'
      }
    }
  ]
});

// 路由跳转前拦截
router.beforeEach((to, from, next) => {
  let isLogin = localStorage.getItem('AuthorizationToken')
  // console.log(localStorage.getItem('AuthorizationToken'), to)
  if (isLogin) {
    next()
  } else {

    // 账号密码登录
    if (whiteList.includes(to.path)) {
      next()
    } else {
      next('/login')
    }

  }
})

export default router;
