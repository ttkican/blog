<template>
  <div v-if="blog.blogInfo.siteConfig.isChat">
    <div class="chat-container" v-show="show">
      <div class="chat-header">
        <img width="32" height="32" src="https://static.ttkwsd.top/config/room.png" />
        <div style="margin-left:12px">
          <div>聊天室</div>
          <div style="font-size:12px">当前{{ onlineCount }}人在线</div>
        </div>
        <svg-icon class="close" icon-class="close" size="1.2rem" @click="show = false"></svg-icon>
      </div>
      <div class="chat-content" id="chat-content">
        <div class="chat-item" v-for="(chat, index) of recordList  " :class="isMy(chat) ? 'my-chat' : ''">
          <img class="user-avatar" :src="chat.avatar" alt="">
          <div :class="isMy(chat) ? 'right-info' : 'left-info'">
            <div class="user-info" :class="isMy(chat) ? 'my-chat' : ''">
              <span style="color: var(--color-red);">{{ chat.nickname }}</span>
              <span style="color :var(--color-blue);" :class="isMy(chat) ? 'right-info' : 'left-info'">
                {{ formatDateTime(chat.createTime) }}
              </span>
            </div>
            <div class="user-content" :class="isMy(chat) ? 'my-content' : ''"
              @contextmenu.prevent.stop="showBack(chat, index, $event)">
              <div v-html="chat.content"></div>
              <div class="back-menu" ref="backBtn" @click="back(chat, index)">
                撤回
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="chat-footer">
        <textarea class="chat-input" v-model="chatContent" placeholder="开始聊天吧"
          @keydown="handleKeyCode($event)"></textarea>
        <Emoji @add-emoji="handleEmoji" @choose-type="handleType"></Emoji>
        <svg-icon class="send-btn" icon-class="plane" size="1.5rem" @click="handleSend"></svg-icon>
      </div>
    </div>
    <div class="chat-btn" @click="handleOpen">
      <span class="unread" v-if="unreadCount > 0">{{ unreadCount }}</span>
      <img src="https://static.ttkwsd.top/config/chat.png" alt="">
    </div>
  </div>
</template>

<script setup lang="ts">
import { Record } from "@/model";
import { useBlogStore, useUserStore } from "@/store";
import { formatDateTime } from '@/utils/date';
import { emojiList } from "@/utils/emoji";
import { tvList } from "@/utils/tv";
const user = useUserStore();
const blog = useBlogStore();
const data = reactive({
  show: false,
  ipAddress: "",
  ipSource: "",
  recordList: [] as Record[],
  chatContent: "",
  emojiType: 0,
  unreadCount: 0,
  webSocketState: false,
  onlineCount: 0,
});
enum Type {
  ONLINE_COUNT = 1,
  HISTORY_RECORD = 2,
  SEND_MESSAGE = 3,
  RECALL_MESSAGE = 4,
  HEART_BEAT = 5,
}
const { show, recordList, ipAddress, ipSource, chatContent, emojiType, unreadCount, webSocketState, onlineCount } = toRefs(data);
const websocketMessage = reactive<{
  type: number,
  data: any,
}>({
  type: 0,
  data: {}
});
const backBtn = ref<any>([]);
const websocket = ref<WebSocket>();
const timeout = ref<NodeJS.Timeout>();
const serverTimeout = ref<NodeJS.Timeout>();
const isMy = computed(() => (chat: Record) => chat.ipAddress == ipAddress.value || (chat.userId !== undefined && chat.userId === user.id));
const userNickname = computed(() => user.nickname ? user.nickname : ipAddress.value);
const userAvatar = computed(() => user.avatar ? user.avatar : blog.blogInfo.siteConfig.touristAvatar);
const handleOpen = () => {
  if (websocket.value === undefined) {
    websocket.value = new WebSocket(blog.blogInfo.siteConfig.websocketUrl);
    websocket.value.onopen = () => {
      webSocketState.value = true;
      startHeart();
    }
    websocket.value.onmessage = (event: MessageEvent) => {
      const data = JSON.parse(event.data);
      switch (data.type) {
        case Type.ONLINE_COUNT:
          // 在线人数
          onlineCount.value = data.data;
          break;
        case Type.HISTORY_RECORD:
          recordList.value = data.data.chatRecordList;
          ipAddress.value = data.data.ipAddress;
          ipSource.value = data.data.ipSource;
          break;
        case Type.SEND_MESSAGE:
          recordList.value.push(data.data);
          if (!show.value) {
            unreadCount.value++;
          }
          break;
        case Type.RECALL_MESSAGE:
          for (let i = 0; i < recordList.value.length; i++) {
            if (recordList.value[i].id === data.data) {
              recordList.value.splice(i, 1);
              i--;
            }
          }
          break;
        case Type.HEART_BEAT:
          webSocketState.value = true;
          break;
      }
    }
    websocket.value.onclose = () => {
      alert("关闭连接");
      webSocketState.value = false;
      clear();
    }
  }
  unreadCount.value = 0;
  show.value = !show.value;
};
// 展示菜单
const showBack = (chat: Record, index: number, e: any) => {
  backBtn.value.forEach((item: any) => {
    item.style.display = "none";
  });
  if (
    chat.ipAddress === ipAddress.value ||
    (chat.userId != null && chat.userId == user.id)
  ) {
    backBtn.value[index].style.left = e.offsetX + "px";
    backBtn.value[index].style.bottom = e.offsetY + "px";
    backBtn.value[index].style.display = "block";
  }
};
// 撤回消息
const back = (item: Record, index: number) => {
  websocketMessage.type = Type.RECALL_MESSAGE;
  websocketMessage.data = item.id;
  websocket.value?.send(JSON.stringify(websocketMessage));
  backBtn.value[index].style.display = "none";
};
const handleKeyCode = (e: any) => {
  if (e.ctrlKey && e.keyCode === 13) {
    chatContent.value = chatContent.value + '\n';
  } else if (e.keyCode === 13) {
    e.preventDefault();
    handleSend();
  }
}
const handleSend = () => {
  if (chatContent.value.trim() == "") {
    window.$message?.error("内容不能为空");
    return;
  }
  // 解析表情
  chatContent.value = chatContent.value.replace(/\[.+?\]/g, (str) => {
    if (emojiType.value === 0) {
      if (emojiList[str] === undefined) {
        return str;
      }
      return (
        "<img src='" +
        emojiList[str] +
        "' width='21' height='21' style='margin: 0 1px;vertical-align: text-bottom'/>"
      );
    }
    if (emojiType.value === 1) {
      if (tvList[str] === undefined) {
        return str;
      }
      return (
        "<img src='" +
        tvList[str] +
        "' width='21' height='21' style='margin: 0 1px;vertical-align: text-bottom'/>"
      );
    }
    return str;
  });
  let chat = {
    nickname: userNickname.value,
    avatar: userAvatar.value,
    content: chatContent.value,
    userId: user.id,
    ipAddress: ipAddress.value,
    ipSource: ipSource.value,
  };
  websocketMessage.type = Type.SEND_MESSAGE;
  websocketMessage.data = chat;
  websocket.value?.send(JSON.stringify(websocketMessage));
  chatContent.value = "";
};
const startHeart = () => {
  timeout.value = setTimeout(() => {
    const beatMessage = {
      type: Type.HEART_BEAT,
      data: "ping",
    };
    websocket.value?.send(JSON.stringify(beatMessage));
    waitServer();
  }, 30 * 1000);
};
const waitServer = () => {
  webSocketState.value = false;
  serverTimeout.value = setTimeout(() => {
    if (webSocketState.value) {
      startHeart();
      return;
    }
    websocket.value?.close();
  }, 20 * 1000);
};
const clear = () => {
  timeout.value && clearTimeout(timeout.value);
  serverTimeout.value && clearTimeout(serverTimeout.value);
}
const handleEmoji = (key: string) => {
  chatContent.value += key;
};
const handleType = (key: number) => {
  emojiType.value = key;
};
onUpdated(() => {
  const element = document.getElementById("chat-content");
  if (element) {
    element.scrollTop = element.scrollHeight;
  }
});
</script>

<style lang="scss" scoped>
.chat-container {
  box-shadow: 0 5px 40px rgba(0, 0, 0, 0.16);
  font-size: 14px;
  background: var(--grey-1);
  animation: bounceInUp 1s;
  animation-fill-mode: both;
  z-index: 1200;
}

@media (min-width: 760px) {
  .chat-container {
    position: fixed;
    bottom: 100px;
    right: 20px;
    width: 400px;
    height: calc(100% - 110px);
    max-height: 590px;
    min-height: 250px;
    border-radius: 1rem;
  }

  .close {
    display: none;
  }
}

@media (max-width: 760px) {
  .chat-container {
    position: fixed;
    top: 0;
    bottom: 0;
    right: 0;
    left: 0;
  }

  .close {
    display: block;
    margin-left: auto;
  }
}

.chat-header {
  display: flex;
  align-items: center;
  padding: 20px 24px;
  border-radius: 1rem 1rem 0 0;
  background-color: var(--grey-0);
}

.unread {
  position: absolute;
  width: 18px;
  height: 18px;
  text-align: center;
  border-radius: 50%;
  line-height: 20px;
  font-size: 12px;
  background: var(--color-red);
  color: var(--grey-0);
}

.chat-content {
  position: absolute;
  top: 80px;
  bottom: 46px;
  width: 100%;
  padding: 20px 16px 0 16px;
  background-color: var(--chat-bg);
  overflow-y: auto;
  overflow-x: hidden;
}

.my-chat {
  flex-direction: row-reverse;
}


.chat-item {
  display: flex;
  margin-bottom: 0.5rem;

  .user-avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
  }

  .left-info {
    margin-left: 0.5rem;
  }

  .right-info {
    margin-right: 0.5rem;
  }

  .user-info {
    display: flex;
    align-items: center;
    font-size: 12px;
  }

  .user-content {
    position: relative;
    padding: 10px;
    border-radius: 5px 20px 20px 20px;
    background: var(--grey-0);
    width: fit-content;
    white-space: pre-line;
    word-wrap: break-word;
    word-break: break-all;
  }

  .my-content {
    float: right;
    border-radius: 20px 5px 20px 20px;
    background: var(--color-blue);
    color: var(--grey-0);
  }
}

.back-menu {
  position: absolute;
  width: 80px;
  height: 35px;
  line-height: 35px;
  font-size: 13px;
  border-radius: 2px;
  background: rgba(255, 255, 255, 0.9);
  color: #000;
  text-align: center;
  display: none;
}

.chat-btn {
  position: fixed;
  bottom: 15px;
  right: 5px;
  width: 60px;
  height: 60px;
  border-radius: 100px;
  cursor: pointer;
  z-index: 1000;
}

.chat-footer {
  position: absolute;
  bottom: 0;
  display: flex;
  align-items: center;
  width: 100%;
  padding: 8px 16px;
  background: var(--grey-2);
  border-radius: 0 0 1rem 1rem;

  .chat-input {
    width: 100%;
    height: 30px;
    padding: 0.5rem 0 0 0.6rem;
    margin-right: 0.5rem;
    font-size: 13px;
    color: var(--text-color);
    background-color: var(--grey-1);
    outline: none;
    resize: none;
  }

  .send-btn {
    color: var(--color-blue);
    margin-left: 0.5rem;
  }
}
</style>
