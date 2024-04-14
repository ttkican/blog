<template>
    <n-popover trigger="click" placement="bottom-start" style="max-height: 200px;" content-style="padding: 0 8px;"
        :width="250" header-style="padding: 8px 15px 0 12px;font-size: 12px;" footer-style="padding: 0;" :to="false">
        <template #trigger>
            <span><svg-icon icon-class="emoji"></svg-icon></span>
        </template>
        <template #header>
            <div class="emoji-title">{{ emojiType === 0 ? "小黄脸" : emojiType === 1 ? "tv_小电视" : "颜文字" }}</div>
        </template>
        <div class="emoji-content" v-if="emojiType === 0">
            <span class="emoji-item" v-for="(value, key, index) of emojiList" :key="index" @click="addEmoji(key)">
                <img :src="value" :title="key" class="emoji" width="24" height="24" />
            </span>
        </div>
        <div class="emoji-content" v-if="emojiType === 1">
            <span class="emoji-item" v-for="(value, key, index) of tvList" :key="index" @click="addEmoji(key)">
                <img :src="value" :title="key" class="emoji" width="24" height="24" />
            </span>
        </div>
        <div class="emoji-content" v-if="emojiType === 2">
            <span class="text-emoji" v-for="(value, index) in textList" :key="index" @click="addEmoji(value)">
                {{ value }}
            </span>
        </div>
        <template #footer>
            <div class="emoji-tabs">
                <div class="emoji-tab" :class="{ 'on': emojiType === 0 }" @click="chooseType(0)">
                    <img src="https://static.ttkwsd.top/emoji/re.png" width="22" height="22" />
                </div>
                <div class="emoji-tab" :class="{ 'on': emojiType === 1 }" @click="chooseType(1)">
                    <img src="https://static.ttkwsd.top/emoji/tvby.png" width="22" height="22" />
                </div>
                <div class="emoji-tab" :class="{ 'on': emojiType === 2 }" @click="chooseType(2)">
                    <img src="https://static.ttkwsd.top/emoji/yan.png" width="22" height="22">
                </div>
            </div>
        </template>
    </n-popover>
</template>

<script setup lang="ts">
import { emojiList } from "@/utils/emoji";
import { textList } from "@/utils/text";
import { tvList } from "@/utils/tv";
const emojiType = ref(0);
const emit = defineEmits(["addEmoji", "chooseType"]);
const addEmoji = (key: string) => {
    emit("addEmoji", key);
};
const chooseType = (key: number) => {
    emojiType.value = key;
    emit("chooseType", key);
};
</script>

<style lang="scss" scoped>
.emoji-content {
    height: 120px;
    overflow: auto;
}

.emoji-item {
    cursor: pointer;
    display: inline-block;

    .emoji {
        user-select: none;
        margin: 0.25rem;
        display: inline-block;
        vertical-align: middle;
    }
}

.text-emoji {
    display: inline-block;
    cursor: pointer;
    padding: 5px 8px;
    font-size: 12px;
    line-height: 22px;
}

.emoji-item:hover,
.text-emoji:hover {
    transition: all 0.2s;
    border-radius: 0.25rem;
    background: #dddddd;
}

.emoji-tabs {
    display: flex;
    background-color: #f1f2f3;

    .emoji-tab {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 58px;
        height: 36px;
        padding: 7px 18px;
        cursor: pointer;

        &:hover {
            background-color: #ddd;
        }
    }

    .on {
        background-color: #fff;
    }
}
</style>
