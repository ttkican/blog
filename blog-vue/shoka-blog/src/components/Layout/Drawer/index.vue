<template>
  <n-drawer class="side-bg" v-model:show="drawerVisible" :width="240" :block-scroll="false" placement="right">
    <n-drawer-content style="padding-top: 0.5rem">
      <div class="author-container">
        <img class="author-avatar" :src="blog.blogInfo.siteConfig.authorAvatar" />
        <p class="author-name">{{ blog.blogInfo.siteConfig.siteAuthor }}</p>
        <div class="site-desc">{{ blog.blogInfo.siteConfig.siteIntro }}</div>
      </div>
      <blog-info></blog-info>
      <social-list></social-list>
      <ul class="side-menu">
        <template v-for="menu of menuList" :key="menu.name">
          <li v-if="!menu.children" class="item" :class="{ active: route.path === menu.path }">
            <router-link :to="menu.path"><svg-icon :icon-class="menu.icon"></svg-icon> {{ menu.name }} </router-link>
          </li>
          <li v-else class="item dropdown" :class="{ expand: expand(menu.children) }">
            <a><svg-icon :icon-class="menu.icon"></svg-icon> {{ menu.name }} </a>
            <ul class="submenu">
              <li class="item" v-for="submenu of menu.children" :key="submenu.name"
                :class="{ active: route.path === submenu.path }">
                <router-link :to="submenu.path"> <svg-icon :icon-class="submenu.icon"></svg-icon> {{ submenu.name }}
                </router-link>
              </li>
            </ul>
          </li>
        </template>
        <li class="item" v-if="!user.id">
          <a @click="app.loginFlag = true"> <svg-icon icon-class="user"></svg-icon> 登录 </a>
        </li>
        <template v-else>
          <li class="item" :class="{ active: route.path === '/user' }">
            <router-link to="/user">
              <svg-icon icon-class="author"></svg-icon> 个人中心
            </router-link>
          </li>
          <li class="item">
            <a @click="logout"> <svg-icon icon-class="logout"></svg-icon> 退出 </a>
          </li>
        </template>
      </ul>
    </n-drawer-content>
  </n-drawer>
</template>

<script setup lang="ts">
import { useAppStore, useBlogStore, useUserStore } from "@/store";
import { useWindowSize } from "@vueuse/core";
const route = useRoute();
const router = useRouter();
const app = useAppStore();
const blog = useBlogStore();
const user = useUserStore();
const { width } = useWindowSize();
const menuList = [
  {
    name: "首页",
    icon: "home",
    path: "/"
  },
  {
    name: "文章",
    icon: "article",
    children: [
      {
        name: "归档",
        icon: "archives",
        path: "/archive"
      },
      {
        name: "分类",
        icon: "category",
        path: "/category"
      },
      {
        name: "标签",
        icon: "tag",
        path: "/tag"
      },
    ]
  },
  {
    name: "娱乐",
    icon: "fun",
    children: [
      {
        name: "说说",
        icon: "talk",
        path: "/talk"
      },
      {
        name: "相册",
        icon: "album",
        path: "/album"
      },
      {
        name: "图床",
        icon: "upload",
        path: "/picture"
      },
    ]
  },
  {
    name: "友链",
    icon: "friend",
    path: "/friend"
  },
  {
    name: "留言板",
    icon: "message",
    path: "/message"
  },
  {
    name: "关于",
    icon: "plane",
    path: "/about"
  },
];
const expand = computed(() => (value: any) => {
  let res: any[] = value.map((item: any) => item.name);
  return res.includes(route.meta.title);
});
const drawerVisible = computed({
  get: () => app.isCollapse,
  set: (value) => (app.isCollapse = value),
});
watchEffect(() => {
  // 监听屏幕宽度，侧边栏收缩
  if (width.value > 991) {
    app.setCollapse(false);
  }
});
const logout = () => {
  if (route.path == "/user") {
    router.go(-1);
  }
  user.LogOut();
  window.$message?.success("退出成功");
};
</script>

<style lang="scss" scoped>
.side-menu {
  text-align: center;
  line-height: 3;
  padding: 1rem 1rem 1.25rem;
  background-color: transparent;
  animation: sidebarItem 0.8s;

  .item {
    color: var(--grey-5);
    border-radius: 0.9375rem;
    margin-bottom: 0.625rem;
    transition: all 0.2s ease-in-out 0s;
    cursor: pointer;

    &:hover {
      background-color: rgba(0, 0, 0, 0.1);
      color: inherit;

      .submenu {
        display: block;
      }
    }

    .submenu {
      display: none;
    }
  }
}

.item.expand {
  background-color: rgba(0, 0, 0, 0.05);
}

.item.expand .submenu {
  display: block;
}

.item.active {
  color: var(--grey-0);
  background-image: linear-gradient(to right, var(--color-pink) 0, var(--color-orange) 100%);
  box-shadow: 0 0 0.75rem var(--color-pink-a3);

  &:hover {
    color: var(--grey-0);
    box-shadow: 0 0 0.75rem var(--color-pink);
  }
}
</style>
