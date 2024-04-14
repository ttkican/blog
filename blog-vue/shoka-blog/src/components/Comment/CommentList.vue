<template>
  <div class="reply-warp" id="reply-wrap">
    <div class="reply-title">
      <svg-icon icon-class="comment" size="1.4rem" style="margin-right: 5px;"></svg-icon>
      评论
    </div>
    <ReplyBox @reload="reloadComments" :comment-type="commentType" :type-id="typeId"></ReplyBox>
    <div v-if="count > 0 && reFresh">
      <div class="reply-item" v-for="(comment, index) of commentList" :key="comment.id">
        <div class="reply-box-avatar">
          <img class="shoka-avatar" :src="comment.avatar">
        </div>
        <div class="content-warp">
          <div class="user-info">
            <div class="user-name">{{ comment.fromNickname }}</div>
            <svg-icon v-if="comment.fromUid == 1" icon-class="badge"></svg-icon>
          </div>
          <div class="reply-content" v-html="comment.commentContent"></div>
          <div class="reply-info">
            <span class="reply-time">{{ formatDateTime(comment.createTime) }}</span>
            <span class="reply-like" @click="like(comment)">
              <svg-icon class="like" icon-class="like" size="0.8rem" :class="isLike(comment.id)"
                style="margin-right: 5px"></svg-icon>
              <span v-show="comment.likeCount">{{ comment.likeCount }}</span>
            </span>
            <span class="reply-btn" @click="handleReply(index, comment)">回复</span>
          </div>
          <div class="sub-reply-item" v-for="reply of comment.replyVOList" :key="reply.id">
            <div class="sub-user-info">
              <img class="sub-reply-avatar" :src="reply.avatar" />
              <div class="sub-user-name">{{ reply.fromNickname }}</div>
              <svg-icon v-if="reply.fromUid == 1" icon-class="badge" style="margin-left: 5px;"></svg-icon>
            </div>
            <span class="reply-content">
              <template v-if="reply.fromUid !== reply.toUid">回复 <span style="color: #008ac5">@{{
                reply.toNickname
              }}</span> :</template>
              <span v-html="reply.commentContent"></span>
            </span>
            <div class="reply-info">
              <span class="reply-time">{{ formatDateTime(reply.createTime) }}</span>
              <span class="reply-like" @click="like(reply)">
                <svg-icon class="like" icon-class="like" size="0.8rem" :class="isLike(reply.id)"
                  style="margin-right: 5px"></svg-icon>
                <span v-show="reply.likeCount > 0">{{ reply.likeCount }}</span>
              </span>
              <span class="reply-btn" @click="handleReply(index, reply)">回复</span>
            </div>
          </div>
          <div ref="readMoreRef" class="view-more" v-show="comment.replyCount > 3">
            <span>共{{ comment.replyCount }}条回复, </span>
            <span class="view-more-btn" @click="readMoreComment(index, comment)">点击查看</span>
          </div>
          <Paging ref="pageRef" :total="comment.replyCount" :index="index" :commentId="comment.id"
            @get-current-page="getCurrentPage"></Paging>
          <ReplyBox ref="replyRef" class="mt-4" :show="false" :comment-type="commentType" :type-id="typeId"
            @reload="reloadReplies(index)">
          </ReplyBox>
          <div class="bottom-line"></div>
        </div>
      </div>
      <div class="loading-warp" v-if="count > commentList.length">
        <n-button class="btn" color="#e9546b" @click="getList">
          加载更多...
        </n-button>
      </div>
    </div>
    <div v-else style="padding: 1.25rem; text-align: center">来发评论吧~</div>
  </div>
</template>

<script setup lang="ts">
import { getCommentList, getReplyList, likeComment } from '@/api/comment';
import { Comment, CommentQuery, Reply } from '@/api/comment/types';
import { useAppStore, useUserStore } from "@/store";
import { formatDateTime } from '@/utils/date';
const user = useUserStore();
const app = useAppStore();
const replyRef = ref<any>([]);
const pageRef = ref<any>([]);
const readMoreRef = ref<any>([]);
const props = defineProps({
  commentType: {
    type: Number,
  }
});
const emit = defineEmits(["getCommentCount"]);
const typeId = computed(() => Number(useRoute().params.id) ? Number(useRoute().params.id) : undefined);
const isLike = computed(() => (id: number) => user.commentLikeSet.indexOf(id) != -1 ? "like-flag" : "");
const data = reactive({
  count: 0,
  reFresh: true,
  queryParams: {
    current: 1,
    typeId: typeId.value,
    commentType: props.commentType,
  } as CommentQuery,
  commentList: [] as Comment[],
});
const { count, reFresh, queryParams, commentList } = toRefs(data);
const like = (comment: Comment | Reply) => {
  if (!user.id) {
    app.setLoginFlag(true);
    return;
  }
  let id = comment.id;
  likeComment(id).then(({ data }) => {
    if (data.flag) {
      //判断是否点赞
      if (user.commentLikeSet.indexOf(id) != -1) {
        comment.likeCount -= 1;
      } else {
        comment.likeCount += 1;
      }
      user.commentLike(id);
    }
  });
};
// 刷新评论列表
watch(
  commentList,
  () => {
    reFresh.value = false;
    nextTick(() => {
      reFresh.value = true
    })
  },
  { deep: false },
);
// 查看更多评论
const readMoreComment = (index: number, comment: Comment) => {
  getReplyList(comment.id, { current: 1, size: 5 }).then(({ data }) => {
    comment.replyVOList = data.data;
    // 回复大于5条展示分页
    if (comment.replyCount > 5) {
      pageRef.value[index].setPaging(true);
    }
    // 隐藏查看更多
    readMoreRef.value[index].style.display = "none";
  });
};
// 查看当前页的回复评论
const getCurrentPage = (current: number, index: number, commentId: number) => {
  getReplyList(commentId, { current: current, size: 5 }).then(({ data }) => {
    commentList.value[index].replyVOList = data.data;
  });
};
const handleReply = (index: number, target: Comment | Reply) => {
  replyRef.value.forEach((element: any) => {
    element.setReply(false);
  });
  const currentReply = replyRef.value[index];
  currentReply.nickname = target.fromNickname;
  currentReply.commentForm.replyId = target.id;
  currentReply.commentForm.toUid = target.fromUid;
  currentReply.commentForm.parentId = commentList.value[index].id;
  currentReply.setReply(true);
};
const getList = () => {
  getCommentList(queryParams.value).then(({ data }) => {
    if (queryParams.value.current == 1) {
      commentList.value = data.data.recordList;
    } else {
      commentList.value.push(...data.data.recordList);
    }
    queryParams.value.current++;
    count.value = data.data.count;
    emit("getCommentCount", count.value);
  });
};
// 重新加载评论列表
const reloadComments = () => {
  queryParams.value.current = 1;
  getList();
};
// 重新加载回复评论
const reloadReplies = (index: number) => {
  getReplyList(commentList.value[index].id, {
    current: pageRef.value[index].current,
    size: 5,
  }).then(({ data }) => {
    commentList.value[index].replyVOList = data.data;
    commentList.value[index].replyCount++;
    // 隐藏回复框
    replyRef.value[index].setReply(false);
    // 回复大于5条展示分页
    if (commentList.value[index].replyCount > 5) {
      pageRef.value[index].setPaging(true);
    }
    // 隐藏查看更多
    readMoreRef.value[index].style.display = "none";
  });
};
onMounted(() => {
  getList();
});
</script>

<style lang="scss" scoped>
@import "@/assets/styles/mixin.scss";

.reply-title {
  display: flex;
  align-items: center;
  margin: 22px 0;
  padding-left: 10px;
  font-size: 20px;
  font-weight: 600;
  line-height: 40px;
}

.sub-reply-avatar {
  position: absolute;
  left: 0;
  width: 1.5rem;
  height: 1.5rem;
  border-radius: 50%;
}

.reply-item {
  display: flex;
  padding-top: 1rem;

  .content-warp {
    flex: auto;
    margin-left: 0.6rem;
  }

  .bottom-line {
    border-bottom: 1px solid var(--grey-3);
    margin-top: 0.5rem;
  }
}

.user-info {
  display: flex;
  align-items: center;
  margin-bottom: 4px;

  .user-name {
    font-size: 0.875rem;
    font-weight: 500;
    margin-right: 0.3125rem;
  }
}

.sub-reply-item {
  position: relative;
  padding: 8px 0 8px 33px;
  font-size: 15px;
  line-height: 24px;

  .sub-user-info {
    display: inline-flex;
    align-items: center;
    margin-right: 9px;
    line-height: 24px;
  }

  .sub-user-name {
    font-size: 13px;
    line-height: 24px;
  }
}

.reply-info {
  display: flex;
  align-items: center;
  margin-top: 0.125rem;
  font-size: 0.8125rem;
  color: #9499a0;

  .reply-time {
    margin-right: 15px;
    padding-top: 2px;
  }

  .reply-like {
    display: flex;
    align-items: center;
    margin-right: 17px;
    cursor: pointer;

    &:hover .like {
      color: var(--color-pink);
    }
  }

  .reply-btn {
    cursor: pointer;

    &:hover {
      color: var(--color-pink);
    }
  }
}

.reply-content {
  overflow: hidden;
  word-wrap: break-word;
  word-break: break-word;
  white-space: pre-wrap;
  font-size: 0.9375rem;
  line-height: 1.5;
  vertical-align: baseline;
}

.view-more {
  font-size: 13px;
  color: #9499a0;

  .view-more-btn {
    cursor: pointer;

    &:hover {
      color: var(--color-pink);
    }
  }
}
</style>
