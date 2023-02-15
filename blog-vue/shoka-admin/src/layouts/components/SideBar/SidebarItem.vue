<template>
    <div v-if="!item.meta || !item.meta.hidden">
        <template v-if="
            hasOneShowingChild(item.children, item) &&
            (!onlyOneChild.children || onlyOneChild.noShowingChildren) &&
            (!item.alwaysShow)
        ">
            <app-link v-if="onlyOneChild.meta" :to="resolvePath(onlyOneChild.path)">
                <el-menu-item :index="resolvePath(onlyOneChild.path)" :class="{ 'submenu-title-noDropdown': !isNest }">
                    <el-icon>
                        <svg-icon v-if="onlyOneChild.meta && onlyOneChild.meta.icon"
                            :icon-class="onlyOneChild.meta.icon" />
                    </el-icon>
                    <template #title>
                        {{ onlyOneChild.meta.title }}
                    </template>
                </el-menu-item>
            </app-link>
        </template>
        <el-sub-menu v-else :index="resolvePath(item.path)">
            <template #title>
                <el-icon>
                    <svg-icon v-if="item.meta && item.meta.icon" :icon-class="item.meta.icon" />
                </el-icon>
                <span class="menu-title">{{ item.meta.title }}</span>
            </template>
            <sidebar-item v-for="child in item.children" :key="child.path" :item="child" :is-nest="true"
                :base-path="resolvePath(child.path)" class="nest-menu" />
        </el-sub-menu>
    </div>
</template>

<script setup lang="ts">
import SvgIcon from '@/components/SvgIcon/index.vue';
import { ref } from "vue";
import { RouteRecordRaw } from 'vue-router';
import AppLink from './Link.vue';
const onlyOneChild = ref();
const props = defineProps({
    item: {
        type: Object,
        required: true
    },
    isNest: {
        type: Boolean,
        required: false
    },
    basePath: {
        type: String,
        required: true
    }
});
const hasOneShowingChild = (children: RouteRecordRaw[], parent: any) => {
    if (!children) {
        children = [];
    }
    const showingChildren = children.filter((item) => {
        if (item.meta && item.meta.hidden) {
            return false;
        } else {
            onlyOneChild.value = item;
            return true;
        }
    });
    if (showingChildren.length === 1) {
        return true;
    }
    if (showingChildren.length === 0) {
        onlyOneChild.value = { ...parent, path: '', noShowingChildren: true };
        return true;
    }
    return false;
}
const resolvePath = (routePath: string) => {
    return getNormalPath(props.basePath + '/' + routePath)
}
const getNormalPath = (p: string) => {
    if (p.length === 0 || !p || p == 'undefined') {
        return p
    };
    let res = p.replace('//', '/')
    if (res[res.length - 1] === '/') {
        return res.slice(0, res.length - 1)
    }
    return res;
}
</script>

<style scoped>

</style>