<template>
    <div>
        <div class="web-title">
            <svg-icon icon-class="web" size="1.1875rem"></svg-icon>
            网站资讯
        </div>
        <div class="web-item">
            <div class="web-name">文章数目</div>
            <div class="webcount">{{ blog.articleCount }}</div>
        </div>
        <div class="web-item">
            <div class="web-name">运行时长</div>
            <div class="web-count">{{ runTime }}</div>
        </div>
        <div class="web-item">
            <div class="web-name">总访问量</div>
            <div class="web-count">{{ blog.viewCount }}</div>
        </div>
    </div>
</template>

<script setup lang="ts">
import useStore from "@/store";
import dayjs from "dayjs";
const { blog } = useStore();
const runTime = ref("");
setInterval(() => {
    const days = dayjs().diff(blog.siteConfig.createSiteTime, "days");
    const day = new Date();
    let str = "";
    str += days + "天";
    str += day.getHours() + "时";
    str += day.getMinutes() + "分";
    str += day.getSeconds() + "秒";
    runTime.value = str;
}, 1000);
</script>

<style lang="scss" scoped>
.web-title {
    font-size: 1.2em;
}

.web-item {
    display: flex;
    justify-content: space-between;
}
</style>