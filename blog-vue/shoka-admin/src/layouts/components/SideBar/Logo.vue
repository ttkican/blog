<template>
    <div class="sidebar-logo-container" :class="{ collapse: isCollapse }">
        <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
            <img v-if="logo" :src="logo" class="sidebar-logo" />
            <h1 v-else class="sidebar-title">博客后台管理系统</h1>
        </router-link>
        <router-link v-else key="expand" class="sidebar-logo-link" to="/">
            <img v-if="logo" :src="logo" class="sidebar-logo" />
            <h1 class="sidebar-title">博客后台管理系统</h1>
        </router-link>
    </div>
</template>

<script setup lang="ts">
import { reactive, toRefs } from "vue";

const props = defineProps({
    collapse: {
        type: Boolean,
        required: true,
    },
});

const state = reactive({
    isCollapse: props.collapse,
    logo: new URL(`../../../assets/logo.png`, import.meta.url).href,
});

const { logo, isCollapse } = toRefs(state);
</script>

<style lang="scss" scoped>
.sidebar-logo-container {
    position: relative;
    width: 100%;
    height: 50px;
    line-height: 50px;
    text-align: center;
    overflow: hidden;

    & .sidebar-logo-link {
        height: 100%;
        width: 100%;

        & .sidebar-logo {
            width: 20px;
            height: 20px;
            vertical-align: middle;
        }

        & .sidebar-title {
            display: inline-block;
            margin: 0;
            color: #fff;
            font-weight: 600;
            line-height: 50px;
            font-size: 14px;
            font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
            vertical-align: middle;
            margin-left: 12px;
        }
    }

    &.collapse {
        .sidebar-logo {
            margin-right: 0px;
        }
    }
}
</style>
