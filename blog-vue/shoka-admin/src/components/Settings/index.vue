<template>
    <el-drawer v-model="showSettings" :withHeader="false" direction="rtl" size="300px">
        <div class="drawer-container">
            <el-divider>主题</el-divider>
            <el-switch class="theme-style" v-model="isDark" inline-prompt @change="toggleTheme" :active-icon="Sunny"
                :inactive-icon="Moon"></el-switch>
            <el-divider>系统布局配置</el-divider>
            <div class="drawer-item">
                <span>开启 Tag-View</span>
                <span class="comp-style">
                    <el-switch v-model="tagView" class="drawer-switch" />
                </span>
            </div>
            <div class="drawer-item">
                <span>固定 Header</span>
                <span class="comp-style">
                    <el-switch v-model="fixedHeader" class="drawer-switch" />
                </span>
            </div>
            <div class="drawer-item">
                <span>显示 Logo</span>
                <span class="comp-style">
                    <el-switch v-model="sidebarLogo" class="drawer-switch" />
                </span>
            </div>
        </div>
    </el-drawer>
</template>

<script setup lang="ts">
import useStore from '@/store';
import { Moon, Sunny } from '@element-plus/icons-vue';
import { useDark, useToggle } from '@vueuse/core';
import { reactive, ref, toRefs, watch } from "vue";

const { setting } = useStore();
const data = reactive({
    fixedHeader: setting.fixedHeader,
    tagView: setting.tagView,
    sidebarLogo: setting.sidebarLogo
});
const { fixedHeader, tagView, sidebarLogo } = toRefs(data);
const showSettings = ref(false);
const isDark = useDark();
const toggleTheme = () => {
    const isDark = useDark();
    useToggle(isDark);
};
const openSetting = () => {
    showSettings.value = true;
};
watch(
    () => fixedHeader.value,
    value => {
        setting.changeSetting({ key: 'fixedHeader', value: value });
    }
);
watch(
    () => tagView.value,
    value => {
        setting.changeSetting({ key: 'tagView', value: value });
    }
);
watch(
    () => sidebarLogo.value,
    value => {
        setting.changeSetting({ key: 'sidebarLogo', value: value });
    }
);
defineExpose({
    openSetting,
})
</script>

<style lang="scss" scoped>
.drawer-container {
    font-size: 14px;
    line-height: 1.5;
    word-wrap: break-word;

    .drawer-item {
        font-size: 14px;
        padding: 12px 0;

        .comp-style {
            float: right;
            margin: -3px 8px 0 0;
        }
    }

}

.theme-style {
    display: flex;
    justify-content: center;
}
</style>