<template>
  <div class="blog-container">
    <!-- 文章 -->
    <div class="blog-item" v-for="(item, index) in blogInfo" :key="index">
      <router-link :to="item.path">
        <div class="count">{{ item.count }}</div>
        <div class="name">{{ item.name }}</div>
      </router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useBlogStore } from "@/store";
const blog = useBlogStore();
const blogInfo = [
  { path: "/archive", count: blog.blogInfo.articleCount, name: "文章" },
  { path: "/category", count: blog.blogInfo.categoryCount, name: "分类" },
  { path: "/tag", count: blog.blogInfo.tagCount, name: "标签" },
];
</script>

<style scoped></style>
