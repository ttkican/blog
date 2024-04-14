<template>
  <div class="page-header">
    <h1 class="page-title">友情链接</h1>
    <img class="page-cover" src="https://static.ttkwsd.top/config/c8049b9b880411ebb6edd017c2d2eca2.jpg" alt="" />
    <!-- 波浪 -->
    <Waves></Waves>
  </div>
  <div class="bg">
    <div class="page-container">
      <h2>
        <svg-icon class="flower" icon-class="flower" size="1.25rem" color="pink"></svg-icon>
        本站信息
      </h2>
      <blockquote class="block">
        <p>名称：{{ blog.blogInfo.siteConfig.siteName }}</p>
        <p>简介：{{ blog.blogInfo.siteConfig.siteIntro }}</p>
        <p>头像：{{ blog.blogInfo.siteConfig.authorAvatar }}</p>
      </blockquote>
      <h2>
        <svg-icon class="flower" icon-class="flower" size="1.25rem" color="pink"></svg-icon>
        申请方法
      </h2>
      <div class="welcome">需要交换友链的可在本页留言 (｡･∀･)ﾉﾞ</div>
      <blockquote class="block">
        <p>出于信息需要,大佬你的信息格式要包含：网站名称、网站链接、头像链接、网站介绍、名称颜色</p>
      </blockquote>
      <h2>
        <svg-icon class="flower" icon-class="flower" size="1.25rem" color="pink"></svg-icon>
        小伙伴们
      </h2>
      <div class="friends">
        <div class="friend-item" v-animate="['slideUpBigIn']" v-for="friend in friendList" :key="friend.id">
          <a target="_blank" :href="friend.url">
            <img class="image" v-lazy="friend.avatar" />
          </a>
          <div class="info">
            <a class="name" target="_blank" :href="friend.url" :style="{ color: friend.color }">{{
              friend.name
            }}</a>
            <p class="desc">{{ friend.introduction }}</p>
          </div>
        </div>
      </div>
      <CommentList :comment-type="commentType"></CommentList>
    </div>
  </div>
</template>

<script setup lang="ts">
import { getFriendList } from "@/api/friend";
import { Friend } from "@/api/friend/types";
import { useBlogStore } from "@/store";
const blog = useBlogStore();
const commentType = ref(2);
const friendList = ref<Friend[]>([]);
onMounted(() => {
  getFriendList().then(({ data }) => {
    friendList.value = data.data;
  });
});
</script>

<style lang="scss" scoped>
.block {
  line-height: 2;
  margin: 0 1.5rem;
  font-size: 15px;
  border-left: 0.2rem solid var(--color-purple);
  padding: 0.625rem 1rem;
  color: var(--grey-5);
  background: var(--color-pink-light);
  border-radius: 4px;
  word-wrap: break-word;
}

.welcome {
  position: relative;
  margin: 0 2.5rem 0.5rem;

  &::before {
    content: "";
    position: absolute;
    width: 0.4em;
    height: 0.4em;
    background: var(--primary-color);
    border-radius: 50%;
    top: 0.85em;
    left: -1em;
  }
}

.friends {
  display: flex;
  flex-wrap: wrap;
  font-size: 0.9rem;
}

.friend-item {
  display: inline-flex;
  align-items: center;
  width: calc(50% - 2rem);
  margin: 1rem;
  padding: 0.5rem 1rem;
  line-height: 1.5;
  border-radius: 0.5rem;
  border: 0.0625rem solid var(--grey-2);
  box-shadow: 0 0.625rem 1.875rem -0.9375rem var(--box-bg-shadow);
  transition: all 0.2s ease-in-out 0s;
  animation-duration: 0.5s;
  visibility: hidden;

  &:hover {
    box-shadow: 0 0 2rem var(--box-bg-shadow);
  }

  .image {
    display: block;
    width: 4rem;
    height: 4rem;
    border-radius: 0.9375rem;
  }

  .info {
    padding-left: 1rem;
  }

  .name {
    font-weight: 700;
  }

  .desc {
    font-size: 0.75em;
    margin: 0.5rem 0;
  }
}

.flower {
  animation: rotate 6s linear infinite;
}

@keyframes rotate {
  0% {
    transform: rotate(0);
  }

  100% {
    transform: rotate(360deg);
  }
}

@media (max-width: 767px) {
  .friend-item {
    width: calc(100% - 2rem);
  }
}
</style>
