<template>
  <div>
    <!-- 标题 -->
    <div class="notice-title">
      <svg-icon class="trumpet" icon-class="trumpet" size="1.1875rem" color="red"></svg-icon>
      公告
    </div>
    <!-- 公告内容 -->
    <div class="notice-content" v-html="blog.blogInfo.siteConfig.siteNotice"></div>
  </div>
</template>

<script setup lang="ts">
import { useBlogStore } from "@/store";
const blog = useBlogStore();
</script>

<style lang="scss" scoped>
.notice-title {
  font-size: 1.2em;
}

.notice-content {
  font-size: 0.9rem;
  line-height: 1.75rem;
}

.trumpet {
  animation: trumpet-shake 1s linear infinite;
}

@keyframes trumpet-shake {
  0% {
    transform: rotate(-15deg);
  }

  4% {
    transform: rotate(15deg);
  }

  16% {
    transform: rotate(-22deg);
  }

  20% {
    transform: rotate(22deg);
  }

  8%,
  24% {
    transform: rotate(-18deg);
  }

  12%,
  28% {
    transform: rotate(18deg);
  }

  32% {
    transform: rotate(-12deg);
  }

  36% {
    transform: rotate(12deg);
  }

  40%,
  100% {
    transform: rotate(0deg);
  }
}
</style>
