<template>
    <div class="menu">
        <div class="menu-item title">
            <router-link to="/" class="menu-btn">
                {{ blog.siteConfig.siteName }}
            </router-link>
        </div>
        <div class="menu-item" :class="{ active: route.meta.title === '首页' }">
            <router-link to="/" class="menu-btn">
                <svg-icon icon-class="home"></svg-icon> 首页
            </router-link>
        </div>
        <div class="menu-item dropdown">
            <a class="menu-btn drop">
                <svg-icon icon-class="article"></svg-icon> 文章
            </a>
            <ul class="submenu">
                <li class="subitem" :class="{ active: route.meta.title === '归档' }">
                    <router-link class="link" to="/archive">
                        <svg-icon icon-class="archives"></svg-icon> 归档
                    </router-link>
                </li>
                <li class="subitem" :class="{ active: route.meta.title === '分类' }">
                    <router-link class="link" to="/category">
                        <svg-icon icon-class="category"></svg-icon> 分类
                    </router-link>
                </li>
                <li class="subitem" :class="{ active: route.meta.title === '标签' }">
                    <router-link class="link" to="/tag">
                        <svg-icon icon-class="tag"></svg-icon> 标签
                    </router-link>
                </li>
            </ul>
        </div>
        <div class="menu-item dropdown">
            <a class="menu-btn drop">
                <svg-icon icon-class="fun"></svg-icon> 娱乐
            </a>
            <ul class="submenu">
                <li class="subitem" :class="{ active: route.meta.title === '说说' }">
                    <router-link class="link" to="/talk">
                        <svg-icon icon-class="talk"></svg-icon> 说说
                    </router-link>
                </li>
                <li class="subitem" :class="{ active: route.meta.title === '相册' }">
                    <router-link class="link" to="/album">
                        <svg-icon icon-class="album"></svg-icon> 相册
                    </router-link>
                </li>
            </ul>
        </div>
        <div class="menu-item" :class="{ active: route.meta.title === '友链' }">
            <router-link to="/friend" class="menu-btn">
                <svg-icon icon-class="friend"></svg-icon> 友链
            </router-link>
        </div>
        <div class="menu-item" :class="{ active: route.meta.title === '留言' }">
            <router-link to="/message" class="menu-btn">
                <svg-icon icon-class="message"></svg-icon> 留言板
            </router-link>
        </div>
        <div class="menu-item" :class="{ active: route.meta.title === '关于' }">
            <router-link to="/about" class="menu-btn">
                <svg-icon icon-class="plane"></svg-icon> 关于
            </router-link>
        </div>
        <div class="menu-item">
            <a v-if="!user.id" @click="app.loginFlag = true" class="menu-btn">
                <svg-icon icon-class="user"></svg-icon> 登录
            </a>
            <template v-else>
                <img class="user-avatar drop" :src="user.avatar" />
                <ul class="submenu">
                    <li class="subitem" :class="{ active: route.meta.title === '个人中心' }">
                        <router-link to="/user" class="link">
                            <svg-icon icon-class="author"></svg-icon> 个人中心
                        </router-link>
                    </li>
                    <li class="subitem">
                        <a class="link" @click="logout"><svg-icon icon-class="logout"></svg-icon> 退出 </a>
                    </li>
                </ul>
            </template>
        </div>
    </div>
</template>

<script setup lang="ts">
import useStore from "@/store";
const { user, app, blog } = useStore();
const router = useRouter();
const route = useRoute();
const logout = () => {
    if (route.path == "/user") {
        router.go(-1);
    }
    user.LogOut();
    window.$message?.success("退出成功");
};
</script>

<style lang="scss" scoped>
.user-avatar {
    display: inline-block;
    position: relative;
    top: 0.3rem;
    width: 24px;
    height: 24px;
    border-radius: 50%;
    cursor: pointer;
}

.menu {
    display: flex;
    align-items: center;
    height: 100%;
}

.menu-item {
    position: relative;
    display: inline-block;
    padding: 0 0.625rem;
    letter-spacing: 0.0625rem;
    font-size: 17px;
    text-align: center;

    &:not(.title) .menu-btn::before {
        content: "";
        position: absolute;
        width: 0;
        height: 0.1875rem;
        bottom: 0;
        border-radius: 0.125rem;
        left: 50%;
        transform: translateX(-50%);
        background-color: currentColor;
        transition: all 0.4s ease-in-out 0s;
    }

    &:hover .submenu {
        display: block;
    }
}

.menu-item.active:not(.dropdown) .menu-btn::before,
.menu-item:not(.dropdown):hover .menu-btn::before {
    width: 70%;
}

.submenu {
    display: none;
    position: absolute;
    left: 7px;
    width: max-content;
    margin-top: 0.5rem;
    padding: 0;
    background: var(--grey-9-a5);
    box-shadow: 0 0.3125rem 1.25rem -0.25rem var(--grey-9-a1);
    border-radius: 0.625rem 0;
    animation: slideUpIn 0.3s;

    &::before {
        position: absolute;
        top: -1.25rem;
        left: 0;
        width: 100%;
        height: 2.5rem;
        content: "";
    }
}

.subitem {
    display: block;
    font-size: 1rem;

    &:first-child {
        border-radius: 0.625rem 0 0 0;
    }

    &:last-child {
        border-radius: 0 0 0.625rem 0;
    }

    .link {
        display: inline-block;
        padding: 0.3rem 0.7rem;
        width: 100%;
        text-shadow: none;
    }

    &:hover .link {
        transform: translateX(0.3rem);
    }
}

.submenu .subitem.active,
.submenu .subitem:hover {
    color: var(--grey-0);
    background-image: linear-gradient(to right, var(--color-pink) 0, var(--color-orange) 100%);
    box-shadow: 0 0 0.75rem var(--color-pink-a3);
}

.sub.menu .submenu {
    background-color: var(--grey-1);
}

.drop::after {
    content: "";
    display: inline-block;
    vertical-align: middle;
    border: 0.3rem solid transparent;
    border-top-color: currentColor;
    border-bottom: 0;
}

@media (max-width: 865px) {
    .menu {
        justify-content: center;
    }

    .menu .menu-item {
        display: none;
    }

    .menu .title {
        display: block;
    }
}
</style>