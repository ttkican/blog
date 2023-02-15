<template>
    <div>
        <!-- 作者信息 -->
        <div class="author-container">
            <!-- 作者头像 -->
            <img class="author-avatar" :src="blog.siteConfig.authorAvatar" alt="">
            <!-- 作者名称 -->
            <p class="author-name">{{ blog.siteConfig.siteAuthor }}</p>
            <!-- 网站简介 -->
            <div class="site-desc">{{ blog.siteConfig.siteIntro }}</div>
        </div>
        <!-- 博客信息 -->
        <div class="blog-container">
            <!-- 文章 -->
            <div class="blog-item">
                <a href="">
                    <div class="count">{{ blog.articleCount }}</div>
                    <div class="name">文章</div>
                </a>
            </div>
            <!-- 分类 -->
            <div class="blog-item">
                <a href="">
                    <div class="count">{{ blog.categoryCount }}</div>
                    <div class="name">分类</div>
                </a>
            </div>
            <!-- 标签 -->
            <div class="blog-item">
                <a href="">
                    <div class="count">{{ blog.tagCount }}</div>
                    <div class="name">标签</div>
                </a>
            </div>
        </div>
        <!-- 社交 -->
        <div class="social-container">
            <a v-if="isShowSocial('github')" target="_blank" :href="blog.siteConfig.github" class="social-item">
                <svg-icon icon-class="github" size="1.4rem"></svg-icon>
            </a>
            <a v-if="isShowSocial('gitee')" target="_blank" :href="blog.siteConfig.gitee" class="social-item">
                <svg-icon icon-class="gitee" size="1.4rem"></svg-icon>
            </a>
            <a v-if="isShowSocial('bilibili')" target="_blank" :href="blog.siteConfig.bilibili" class="social-item">
                <svg-icon icon-class="bilibili" size="1.4rem"></svg-icon>
            </a>
            <a v-if="isShowSocial('qq')" target="_blank"
                :href="'http://wpa.qq.com/msgrd?v=3&uin=' + blog.siteConfig.qq + '&site=qq&menu=yes'"
                class="social-item">
                <svg-icon icon-class="qq" size="1.4rem" color="#00a1d6"></svg-icon>
            </a>
        </div>
    </div>
</template>

<script setup lang="ts">
import useStore from "@/store";
const { blog } = useStore();
const isShowSocial = computed(() => (social: string) => blog.siteConfig.socialList.includes(social));
</script>

<style scoped>

</style>