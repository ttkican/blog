<template>
  <div class="page-header">
    <h1 class="page-title">说说</h1>
    <img class="page-cover" src="https://ik.imagekit.io/nicexl/Wallpaper/ba41a32b219e4b40ad055bbb52935896_Y0819msuI.jpg"
      alt="">
    <Waves></Waves>
  </div>
  <div class="bg">
    <div class="page-container">
      <router-link :to="`/talk/${talk.id}`" class="talk-item" v-animate="['slideUpBigIn']" v-for="talk in talkList"
        :key="talk.id">
        <div class="talk-meta">
          <!-- 用户头像 -->
          <img class="user-avatar" :src="talk.avatar">
          <div class="talk-info">
            <span class="talk-user-name">{{ talk.nickname }}<svg-icon icon-class="badge"
                style="margin-left: 0.4rem;"></svg-icon></span>
            <span class="talk-time">{{ formatDateTime(talk.createTime) }}</span>
          </div>
        </div>
        <!-- 说说内容 -->
        <div class="talk-content" v-html="talk.talkContent">
        </div>
        <!-- 说说图片 -->
        <div class="talk-image" v-viewer>
          <img @click.prevent class="image" v-for="(img, index) in talk.imgList" :key="index" v-lazy="img" />
        </div>
        <!-- 说说信息 -->
        <div class="info" style="margin-top: 0.5rem;">
          <!-- 点赞量 -->
          <span class="talk-like info">
            <svg-icon icon-class="like" size="0.8rem" style="margin-right: 5px;"></svg-icon>{{
              talk.likeCount
            }}
          </span>
          <!-- 评论量 -->
          <span class="talk-comment info">
            <svg-icon icon-class="comment" size="0.9rem" style="margin-right: 5px;"></svg-icon>{{
              talk.commentCount
            }}
          </span>
        </div>
      </router-link>
      <div class="loading-warp" v-if="talkList && count > talkList.length">
        <n-button class="btn" color="#e9546b" @click="getList">
          加载更多...
        </n-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { getTalkList } from "@/api/talk";
import { Talk } from "@/api/talk/types";
import { PageQuery } from "@/model";
import { formatDateTime } from "@/utils/date";
const data = reactive({
  count: 0,
  queryParams: {
    current: 1,
    size: 5
  } as PageQuery,
  talkList: [] as Talk[],
});
const {
  count,
  queryParams,
  talkList,
} = toRefs(data);
const getList = () => {
  getTalkList(queryParams.value).then(({ data }) => {
    if (queryParams.value.current == 1) {
      talkList.value = data.data.recordList;
    } else {
      talkList.value.push(...data.data.recordList);
    }
    queryParams.value.current++;
    count.value = data.data.count;
  });
};
onMounted(() => {
  getList();
})
</script>

<style lang="scss" scoped>
.talk-item {
  display: flex;
  flex-direction: column;
  padding: 1rem 1.25rem;
  border-radius: 0.5rem;
  box-shadow: 0 0.625rem 1.875rem -0.9375rem var(--box-bg-shadow);
  animation-duration: 0.5s;
  transition: all 0.2s ease-in-out 0s;
  visibility: hidden;

  &:hover {
    box-shadow: 0 0 2rem var(--box-bg-shadow);
  }

  &:not(:first-child) {
    margin-top: 1.25rem;
  }
}

.talk-meta {
  display: flex;
  align-items: center;
  width: 100%;
}

.talk-info {
  display: flex;
  flex-direction: column;
  margin-left: 0.5rem;
}

.user-avatar {
  width: 2.8rem;
  height: 2.8rem;
  border-radius: 10px;
}

.info {
  display: flex;
  align-items: center;
}
</style>