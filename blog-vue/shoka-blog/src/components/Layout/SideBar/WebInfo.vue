<template>
  <div>
    <div class="web-title">
      <svg-icon icon-class="web" size="1.1875rem"></svg-icon>
      网站资讯
    </div>
    <div class="web-item" v-for="(item, index) in webInfo" :key="index">
      <div class="web-name">{{ item.name }}</div>
      <div class="web-count">{{ item.count }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useBlogStore } from "@/store";
import dayjs from "dayjs";
const blog = useBlogStore();
const runTime = ref("");
setInterval(() => {
  const days = dayjs().diff(blog.blogInfo.siteConfig.createSiteTime, "days");
  const day = new Date();
  let str = "";
  str += days + "天";
  str += day.getHours() + "时";
  str += day.getMinutes() + "分";
  str += day.getSeconds() + "秒";
  runTime.value = str;
}, 1000);
const webInfo = ref([
  { name: "文章数目", count: blog.blogInfo.articleCount, },
  { name: "运行时长", count: runTime, },
  { name: "总访问量", count: blog.blogInfo.viewCount, }
])
</script>

<style lang="scss" scoped>
.web-title {
  font-size: 1.2em;
}

.web-item {
  display: flex;
  justify-content: space-between;
}
</style>
