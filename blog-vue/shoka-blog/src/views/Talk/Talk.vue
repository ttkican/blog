<template>
  <div class="page-header">
    <h1 class="page-title">说说</h1>
    <img class="page-cover" src="https://ik.imagekit.io/nicexl/Wallpaper/ba41a32b219e4b40ad055bbb52935896_Y0819msuI.jpg"
      alt="">
    <Waves></Waves>
  </div>
  <div class="bg">
    <div class="page-container" v-if="talk">
      <div class="talk-item">
        <div class="talk-meta">
          <img class="user-avatar" :src="talk.avatar">
        </div>
        <div class="talk-content-wrap">
          <div class="talk-info">
            <span class="talk-user-name">{{ talk.nickname }}<svg-icon icon-class="badge"
                style="margin-left: 0.4rem;"></svg-icon></span>
            <span class="talk-time">{{ formatDateTime(talk.createTime) }}</span>
          </div>
          <div class="talk-content" v-html="talk.talkContent"></div>
          <div class="talk-image" v-viewer v-if="talk.imgList">
            <img @click.prevent class="image" v-for="(img, index) in talk.imgList" :key="index" v-lazy="img" />
          </div>
          <div class="info" style="margin-top: 0.5rem;">
            <span class="talk-like info" @click="like">
              <svg-icon icon-class="like" size="0.8rem" :class="isLike(talk.id)" style="margin-right: 5px"></svg-icon>
              {{ talk.likeCount }}
            </span>
            <span class="talk-comment info">
              <svg-icon icon-class="comment" size="0.9rem" style="margin-right: 5px;"></svg-icon>
              {{ commentCount == null ? 0 : commentCount }}
            </span>
          </div>
        </div>
      </div>
      <CommentList :comment-type="commentType" @get-comment-count="getCommentCount"></CommentList>
    </div>
  </div>
</template>

<script setup lang="ts">
import { getTalk, likeTalk } from "@/api/talk";
import { Talk } from "@/api/talk/types";
import { useAppStore, useUserStore } from "@/store";
import { formatDateTime } from "@/utils/date";
const user = useUserStore();
const app = useAppStore();
const route = useRoute();
const isLike = computed(() => (id: number) => user.talkLikeSet.indexOf(id) != -1 ? "like-flag" : "");
const data = reactive({
  commentCount: 0,
  commentType: 3,
  talk: {
    id: 0,
    nickname: "",
    avatar: "",
    talkContent: "",
    imgList: [],
    isTop: 0,
    likeCount: 0,
    commentCount: 0,
    createTime: "",
  } as Talk,
});
const { commentCount, commentType, talk } = toRefs(data);
const getCommentCount = (count: number) => {
  commentCount.value = count;
};
const like = () => {
  if (!user.id) {
    app.setLoginFlag(true);
    return;
  }
  let id = talk.value.id;
  likeTalk(id).then(({ data }) => {
    if (data.flag) {
      //判断是否点赞
      if (user.talkLikeSet.indexOf(id) != -1) {
        talk.value.likeCount -= 1;
      } else {
        talk.value.likeCount += 1;
      }
      user.talkLike(id);
    }
  });
};
onMounted(() => {
  getTalk(Number(route.params.id)).then(({ data }) => {
    talk.value = data.data;
  })
})
</script>

<style lang="scss" scoped>
@import "@/assets/styles/mixin.scss";

.talk-item {
  display: flex;
}

.talk-meta {
  @include flex;
  width: 3rem;
  height: 3.125rem;
}

.talk-content-wrap {
  flex: auto;
  margin-left: 0.5rem;
}

.talk-info {
  display: flex;
  flex-direction: column;
}

.user-avatar {
  width: 100%;
  height: 3rem;
  border-radius: 50%;
}

.info {
  display: flex;
  align-items: center;
}
</style>
