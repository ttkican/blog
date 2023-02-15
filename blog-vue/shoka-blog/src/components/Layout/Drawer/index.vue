<template>
    <n-drawer class="side-bg" v-model:show="drawerVisible" :width="240" :block-scroll="false" placement="right">
        <n-drawer-content style="padding-top: 0.5rem;">
            <!-- 作者信息 -->
            <div class="author-container">
                <!-- 作者头像 -->
                <img class="author-avatar" v-lazy="blog.siteConfig.authorAvatar" alt="">
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
            <!-- 菜单 -->
            <ul class="side-menu">
                <li class="item" :class="{ active: route.path === '/' }">
                    <router-link to="/"><svg-icon icon-class="home"></svg-icon> 首页 </router-link>
                </li>
                <li class="item dropdown" :class="{ expand: articleExpand(route.meta.title as string) }">
                    <a><svg-icon icon-class="article"></svg-icon> 文章</a>
                    <ul class="submenu">
                        <li class="item" :class="{ active: route.path === '/archive' }">
                            <router-link to="/archive">
                                <svg-icon icon-class="archives"></svg-icon> 归档
                            </router-link>
                        </li>
                        <li class="item" :class="{ active: route.path === '/category' }">
                            <router-link to="/category">
                                <svg-icon icon-class="category"></svg-icon> 分类
                            </router-link>
                        </li>
                        <li class="item" :class="{ active: route.path === '/tag' }">
                            <router-link to="/tag"><svg-icon icon-class="tag"></svg-icon> 标签</router-link>
                        </li>
                    </ul>
                </li>
                <li class="item dropdown" :class="{ expand: funExpand(route.meta.title as string) }">
                    <a><svg-icon icon-class="fun"></svg-icon> 娱乐</a>
                    <ul class="submenu">
                        <li class="item" :class="{ active: route.meta.title === '说说' }">
                            <router-link to="/talk">
                                <svg-icon icon-class="talk"></svg-icon> 说说
                            </router-link>
                        </li>
                        <li class="item" :class="{ active: route.path === '/album' }">
                            <router-link to="/album">
                                <svg-icon icon-class="album"></svg-icon> 相册
                            </router-link>
                        </li>
                    </ul>
                </li>
                <li class="item" :class="{ active: route.path === '/friend' }">
                    <router-link to="/friend">
                        <svg-icon icon-class="friend"></svg-icon> 友链
                    </router-link>
                </li>
                <li class="item" :class="{ active: route.path === '/message' }">
                    <router-link to="/message">
                        <svg-icon icon-class="message"></svg-icon> 留言板
                    </router-link>
                </li>
                <li class="item" :class="{ active: route.path === '/about' }">
                    <router-link to="/about">
                        <svg-icon icon-class="plane"></svg-icon> 关于
                    </router-link>
                </li>
                <li class="item" v-if="!user.id">
                    <a @click="app.loginFlag = true">
                        <svg-icon icon-class="user"></svg-icon> 登录
                    </a>
                </li>
                <template v-else>
                    <li class="item" :class="{ active: route.path === '/user' }">
                        <router-link to="/user">
                            <svg-icon icon-class="author"></svg-icon> 个人中心
                        </router-link>
                    </li>
                    <li class="item">
                        <a @click="logout">
                            <svg-icon icon-class="logout"></svg-icon> 退出
                        </a>
                    </li>
                </template>
            </ul>
        </n-drawer-content>
    </n-drawer>
</template>

<script setup lang="ts">
import useStore from "@/store";
import { useWindowSize } from "@vueuse/core";
const route = useRoute();
const router = useRouter();
const { app, blog, user } = useStore();
const { width } = useWindowSize();
const isShowSocial = computed(() => (social: string) => blog.siteConfig.socialList.split(",").includes(social));
const article = ["归档", "分类", "标签"];
const fun = ["说说", "相册"];
const articleExpand = computed(() => (value: string) => article.includes(value));
const funExpand = computed(() => (value: string) => fun.includes(value));
const drawerVisible = computed({
    get: () => app.isCollapse,
    set: (value) => app.isCollapse = value,
});
watchEffect(() => {
    // 监听屏幕宽度，侧边栏收缩
    if (width.value > 991) {
        app.setCollapse(false);
    }
});
const logout = () => {
    if (route.path == "/user") {
        router.go(-1);
    }
    user.LogOut();
    window.$message?.success("退出成功");
};
</script>

<style lang="scss" scoped>
.side-menu {
    text-align: center;
    line-height: 3;
    padding: 1rem 1rem 1.25rem;
    background-color: transparent;
    animation: sidebarItem 0.8s;

    .item {
        color: var(--grey-5);
        border-radius: 0.9375rem;
        margin-bottom: 0.625rem;
        transition: all 0.2s ease-in-out 0s;

        &:hover {
            background-color: rgba(0, 0, 0, 0.1);
            color: inherit;

            .submenu {
                display: block;
            }
        }

        .submenu {
            display: none;
        }
    }
}

.item.expand {
    background-color: rgba(0, 0, 0, 0.05);
}

.item.expand .submenu {
    display: block;
}

.item.active {
    color: var(--grey-0);
    background-image: linear-gradient(to right, var(--color-pink) 0, var(--color-orange) 100%);
    box-shadow: 0 0 0.75rem var(--color-pink-a3);

    &:hover {
        color: var(--grey-0);
        box-shadow: 0 0 0.75rem var(--color-pink);
    }
}
</style>