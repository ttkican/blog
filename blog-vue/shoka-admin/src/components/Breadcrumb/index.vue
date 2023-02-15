<template>
  <el-breadcrumb class="app-breadcrumb" separator="/">
    <transition-group name="breadcrumb">
      <el-breadcrumb-item v-for="(item, index) in breadcrumbList" :key="item.path">
        <span v-if="item.redirect === 'noRedirect' || index == breadcrumbList.length - 1" class="no-redirect">{{
            item.meta.title
        }}</span>
        <a v-else @click.prevent="handleLink(item)">{{ item.meta.title }}</a>
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script setup lang="ts">
import router from '@/router';
import { onBeforeMount, ref, watch } from "vue";
import { RouteLocationMatched, useRoute } from "vue-router";

const breadcrumbList = ref([] as Array<RouteLocationMatched>);
const currentRoute = useRoute();

const getBreadCrumb = () => {
  let matched = currentRoute.matched.filter((item) => item.name);
  const first = matched[0];
  if (!isDashboard(first)) {
    // 添加首页进去
    matched = [{ path: "/index", meta: { title: "首页" } } as any].concat(matched);
  }
  breadcrumbList.value = matched.filter(item => item.meta && item.meta.title);
}

const isDashboard = (route: RouteLocationMatched) => {
  const name = route && route.name;
  if (!name) {
    return false;
  }
  return name.toString().trim() === 'Index';
};
const handleLink = (item: any) => {
  const { redirect, path } = item;
  if (redirect) {
    router.push(redirect);
    return;
  }
  router.push(path);
};
watch(
  () => currentRoute.path,
  path => {
    if (path.startsWith('/redirect/')) {
      return;
    }
    getBreadCrumb();
  }
);
onBeforeMount(() => {
  getBreadCrumb();
});
</script>

<style lang="scss" scoped>
.el-breadcrumb__inner,
.el-breadcrumb__inner a {
  font-weight: 400 !important;
}

.app-breadcrumb.el-breadcrumb {
  display: inline-block;
  font-size: 14px;
  line-height: 50px;
  margin-left: 8px;

  .no-redirect {
    color: #97a8be;
    cursor: text;
  }
}
</style>
