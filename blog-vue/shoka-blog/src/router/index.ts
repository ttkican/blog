import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";

const routes: RouteRecordRaw[] = [
  {
    path: "/",
    component: () => import("@/views/Home/index.vue"),
    meta: {
      title: "首页",
    },
  },
  {
    path: "/message",
    component: () => import("@/views/Message/index.vue"),
    meta: {
      title: "留言",
    },
  },
  {
    path: "/about",
    component: () => import("@/views/About/index.vue"),
    meta: {
      title: "关于",
    },
  },
  {
    path: "/category/:categoryId",
    component: () => import("@/views/Category/ArticleList.vue"),
  },
  {
    path: "/tag/:tagId",
    component: () => import("@/views/Tag/ArticleList.vue"),
  },
  {
    name: "friend",
    path: "/friend",
    component: () => import("@/views/Friend/index.vue"),
    meta: {
      title: "友链",
    },
  },
  {
    path: "/archive",
    component: () => import("@/views/Archive/index.vue"),
    meta: {
      title: "归档",
    },
  },
  {
    path: "/user",
    component: () => import("@/views/User/index.vue"),
    meta: {
      title: "个人中心",
    },
  },
  {
    path: "/category",
    component: () => import("@/views/Category/index.vue"),
    meta: {
      title: "分类",
    },
  },
  {
    path: "/tag",
    component: () => import("@/views/Tag/index.vue"),
    meta: {
      title: "标签",
    },
  },
  {
    path: "/picture",
    component: () => import("@/views/Picture/index.vue"),
    meta: {
      title: "图床",
    },
  },
  {
    path: "/album",
    component: () => import("@/views/Album/Album.vue"),
    meta: {
      title: "相册",
    },
  },
  {
    path: "/album/:albumId",
    component: () => import("@/views/Album/Photo.vue"),
  },
  {
    name: "talk",
    path: "/talk",
    component: () => import("@/views/Talk/TalkList.vue"),
    meta: {
      title: "说说",
    },
  },
  {
    name: "talkInfo",
    path: "/talk/:id",
    component: () => import("@/views/Talk/Talk.vue"),
    meta: {
      title: "说说",
    },
  },
  {
    name: "article",
    path: "/article/:id",
    component: () => import("@/views/Article/Article.vue"),
    meta: {
      title: "文章",
    },
  },
  {
    path: "/404",
    component: () => import("@/views/404/index.vue"),
    meta: {
      title: "404",
    },
  },
  {
    path: "/oauth/login/gitee",
    component: () => import("@/components/Oauth/index.vue"),
  },
  {
    path: "/oauth/login/github",
    component: () => import("@/components/Oauth/index.vue"),
  },
  {
    path: "/oauth/login/qq",
    component: () => import("@/components/Oauth/index.vue"),
  },
  { path: "/:catchAll(.*)", redirect: "/404" },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  //滚动行为
  scrollBehavior(to, from, savedPosition) {
    // to：即将进入的路由对象
    // from：当前导航正要离开的路由对象
    // savedPosition：上次记录的滚动位置
    // 默认行为，如果有记录的滚动位置，则恢复到该位置
    if (savedPosition) {
      return savedPosition;
    }
    // 没有记录的滚动位置，则滚动到页面顶部
    return { top: 0 };
  },
});

export default router;
