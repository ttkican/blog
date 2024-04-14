<template>
  <div class="social-container">
    <template v-for="(item, index) in showSocialList">
      <a class="social-item" :key="index" v-if="isShowSocial(item.type)" target="_blank" :href="item.href">
        <svg-icon :icon-class="item.type" size="1.4rem" :color="item.color"></svg-icon>
      </a>
    </template>
  </div>
</template>

<script setup lang="ts">
import { useBlogStore } from "@/store";
const blog = useBlogStore();
const socialList = blog.blogInfo.siteConfig.socialList;
const isShowSocial = computed(() => (social: string) => {
	if (socialList) {
		return socialList.includes(social);
	}
});
const showSocialList = [
  {
    type: "github",
    href: blog.blogInfo.siteConfig.github,
  },
  {
    type: "gitee",
    href: blog.blogInfo.siteConfig.gitee,
  },
  {
    type: "bilibili",
    href: blog.blogInfo.siteConfig.bilibili,
  },
  {
    type: "qq",
    href: 'http://wpa.qq.com/msgrd?v=3&uin=' + blog.blogInfo.siteConfig.qq + '&site=qq&menu=yes',
    color: "#00a1d6"
  }
];
</script>

<style scoped></style>
