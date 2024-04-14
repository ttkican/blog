<template>
  <div class="reply-box" v-if="show">
    <div class="box-normal">
      <div class="reply-box-avatar">
        <img class="shoka-avatar" v-if="user.avatar" :src="user.avatar" alt="" />
        <img class="shoka-avatar" v-else :src="blog.blogInfo.siteConfig.touristAvatar" alt="" />
      </div>
      <div class="reply-box-warp">
        <textarea class="reply-box-textarea" v-model="commentContent" :style="sendActive ? lineStyle : ''"
          @input.prevent="inputActive" :placeholder="placeholderText"></textarea>
      </div>
      <div class="reply-box-send" :class="sendActive ? 'send-active' : ''" @click="handleAdd">
        评论
      </div>
    </div>
    <div class="box-expand">
      <Emoji @add-emoji="handleEmoji" @choose-type="handleType"></Emoji>
    </div>
  </div>
</template>

<script setup lang="ts">
import { addComment } from "@/api/comment";
import { CommentForm } from "@/api/comment/types";
import { useAppStore, useBlogStore, useUserStore } from "@/store";
import { emojiList } from "@/utils/emoji";
import { tvList } from "@/utils/tv";
const user = useUserStore();
const blog = useBlogStore();
const app = useAppStore();
const lineStyle = {
  lineHeight: "normal",
  borderColor: "#ed6ea0",
  backgroundColor: "var(--grey-0)",
};
const emit = defineEmits(["reload"]);
const props = defineProps({
  commentType: {
    type: Number,
  },
  show: {
    type: Boolean,
    default: true,
  },
  typeId: {
    type: Number,
  },
});
const data = reactive({
  nickname: "",
  sendActive: false,
  show: props.show,
  commentContent: "",
  emojiType: 0,
  commentForm: {
    typeId: props.typeId,
    commentType: props.commentType,
    parentId: undefined,
    replyId: undefined,
    toUid: undefined,
    commentContent: "",
  } as CommentForm,
});
const { nickname, sendActive, show, commentContent, emojiType, commentForm } = toRefs(data);
const placeholderText = computed(() =>
  nickname.value ? `回复 @${nickname.value}：` : "发一条友善的评论"
);
const inputActive = () => {
  if (commentContent.value.length == 0) {
    sendActive.value = false;
  } else {
    sendActive.value = true;
  }
};
const handleEmoji = (key: string) => {
  commentContent.value += key;
  sendActive.value = true;
};
const handleType = (key: number) => {
  emojiType.value = key;
};
const handleAdd = () => {
  if (!user.id) {
    app.setLoginFlag(true);
    return;
  }
  if (commentContent.value.trim() == "") {
    window.$message?.error("评论不能为空");
    return;
  }
  // 解析表情
  commentForm.value.commentContent = commentContent.value.replace(/\[.+?\]/g, (str) => {
    if (emojiType.value === 0) {
      if (emojiList[str] === undefined) {
        return str;
      }
      return (
        "<img src= '" +
        emojiList[str] +
        "' width='21' height='21' style='margin: 0 1px;vertical-align: text-bottom'/>"
      );
    }
    if (emojiType.value === 1) {
      if (tvList[str] === undefined) {
        return str;
      }
      return (
        "<img src= '" +
        tvList[str] +
        "' width='21' height='21' style='margin: 0 1px;vertical-align: text-bottom'/>"
      );
    }
    return str;
  });
  addComment(commentForm.value).then(({ data }) => {
    if (data.flag) {
      sendActive.value = false;
      commentContent.value = "";
      if (blog.blogInfo.siteConfig.commentCheck) {
        window.$message?.warning("评论成功，正在审核中");
      } else {
        window.$message?.success("评论成功");
      }
      // 重新加载评论列表
      emit("reload");
    }
  });
};
const setReply = (flag: boolean) => {
  show.value = flag;
};
defineExpose({ commentForm, nickname, setReply });
</script>

<style scoped></style>
