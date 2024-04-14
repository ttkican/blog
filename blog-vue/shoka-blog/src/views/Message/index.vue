<template>
  <!-- 弹幕输入框 -->
  <div class="message-container">
    <h1 class="message-title">留言板</h1>
    <div class="message-input">
      <input class="input" v-model="messageContent" @click="show = true" @keyup.enter="send" placeholder="说点什么吧" />
      <button class="send" @click="send" v-show="show">发送</button>
    </div>
  </div>
  <!-- 弹幕列表 -->
  <div class="danmaku-container">
    <vue-danmaku ref="danmaku" class="danmaku" use-slot v-model:danmus="messageList" :is-suspend="true">
      <template v-slot:dm="{ danmu }">
        <span class="danmaku-item">
          <img :src="danmu.avatar" width="30" height="30" style="border-radius: 50%" />
          <span class="ml">{{ danmu.nickname }} :</span>
          <span class="ml">{{ danmu.messageContent }}</span>
        </span>
      </template>
    </vue-danmaku>
  </div>
</template>

<script setup lang="ts">
import { addMessage, getMessageList } from "@/api/message";
import { Message } from "@/api/message/types";
import { useBlogStore, useUserStore } from "@/store";
import vueDanmaku from "vue3-danmaku";
const user = useUserStore();
const blog = useBlogStore();
const messageContent = ref("");
const show = ref(false);
const danmaku = ref();
const messageList = ref<Message[]>([]);
onMounted(async () => {
  await getMessageList().then(({ data }) => {
    messageList.value = data.data;
  });
});
const send = () => {
  if (messageContent.value.trim() == "") {
    window.$message?.warning("留言内容不能为空");
    return false;
  }
  const userAvatar = user.avatar ? user.avatar : blog.blogInfo.siteConfig.touristAvatar;
  const userNickname = user.nickname ? user.nickname : "游客";
  let message = {
    avatar: userAvatar,
    nickname: userNickname,
    messageContent: messageContent.value,
  };
  addMessage(message).then(({ data }) => {
    if (data.flag) {
      if (blog.blogInfo.siteConfig.messageCheck) {
        window.$message?.warning("留言成功，正在审核中");
      } else {
        danmaku.value.push(message);
        window.$message?.success("留言成功");
      }
      messageContent.value = "";
    }
  });
};
</script>

<style lang="scss" scoped>
.message-container {
  position: fixed;
  top: 35%;
  left: 0;
  right: 0;
  width: 22.5rem;
  margin: 0 auto;
  text-align: center;
  color: #fff;
  z-index: 5;
}

.message-title {
  animation: titleScale 1s;
}

.message-input {
  display: flex;
  justify-content: center;
  height: 2.5rem;
  margin-top: 2rem;
}

.message-input .input {
  width: 70%;
  height: 100%;
  border-radius: 1.25rem;
  padding: 0 1.25rem;
  outline: none;
  color: #eee;
  border: #fff 1px solid;
}

.message-input .input::-webkit-input-placeholder {
  color: #eeee;
}

.message-input .send {
  height: 100%;
  padding: 0 1.25rem;
  color: #eee;
  border: #fff 1px solid;
  border-radius: 20px;
  outline: none;
  animation: slideUpIn 0.3s;
}

.danmaku-container {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  width: 100%;
  background-color: var(--color-blue);
  background: url("https://static.ttkwsd.top/config/e3408389cb0d4ea1b5f651873dab2a19.jpg") center no-repeat;
  animation: slideDownIn 1s;
}

.danmaku {
  position: fixed;
  top: 3.125rem;
  width: 100%;
  height: 100%;

  .danmaku-item {
    display: flex;
    align-items: center;
    padding: 0 0.625rem 0 0.3125rem;
    border-radius: 6.25rem;
    background-color: rgba(0, 0, 0, 0.3);
    color: #fff;
  }

  .ml {
    margin-left: 0.5rem;
  }
}
</style>
