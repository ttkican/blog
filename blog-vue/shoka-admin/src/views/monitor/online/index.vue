<template>
    <div class="app-container">
        <!-- 搜索栏 -->
        <el-form @submit.native.prevent :model="queryParams" :inline="true">
            <el-form-item label="用户昵称">
                <el-input @keyup.enter="handleQuery" v-model="queryParams.keyword" style="width: 200px"
                    placeholder="请输入用户昵称" clearable />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            </el-form-item>
        </el-form>
        <!-- 表格展示 -->
        <el-table border :data="onlineList" v-loading="loading">
            <!-- 用户头像 -->
            <el-table-column prop="avatar" label="头像" align="center" width="100">
                <template #default="scope">
                    <img :src="scope.row.avatar" width="40" height="40" />
                </template>
            </el-table-column>
            <!-- 昵称 -->
            <el-table-column prop="nickname" label="昵称" align="center" width="200"></el-table-column>
            <!-- 登录ip -->
            <el-table-column prop="ipAddress" label="登录ip" align="center" width="150"></el-table-column>
            <!-- 登录地址 -->
            <el-table-column prop="ipSource" label="登录地址" align="center" width="190"></el-table-column>
            <!-- 操作系统 -->
            <el-table-column prop="os" label="操作系统" align="center" width="150"></el-table-column>
            <!-- 浏览器 -->
            <el-table-column prop="browser" label="浏览器" align="center" width="150"></el-table-column>
            <!-- 登录时间 -->
            <el-table-column prop="loginTime" label="登录时间" align="center" width="200">
                <template #default="scope">
                    <div class="create-time">
                        <el-icon>
                            <clock />
                        </el-icon>
                        <span style="margin-left: 10px">{{ formatDateTime(scope.row.loginTime) }}</span>
                    </div>
                </template>
            </el-table-column>
            <!-- 操作 -->
            <el-table-column label="操作" align="center">
                <template #default="scope">
                    <el-button type="primary" icon="Edit" link @click="kick(scope.row.token)">
                        下线
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <pagination v-if="count > 0" :total="count" v-model:page="queryParams.current" v-model:limit="queryParams.size"
            @pagination="getList" />
    </div>
</template>

<script setup lang="ts">
import { getOnlineList, kickOutUser } from "@/api/user";
import { Online, OnlineQuery } from "@/api/user/types";
import { formatDateTime } from "@/utils/date";
import { messageConfirm, notifySuccess } from "@/utils/modal";
import { onMounted, reactive, toRefs } from "vue";
const data = reactive({
    count: 0,
    loading: false,
    queryParams: {
        current: 1,
        size: 10,
    } as OnlineQuery,
    onlineList: [] as Online[],
});
const {
    count,
    loading,
    queryParams,
    onlineList,
} = toRefs(data);
const kick = (token: string) => {
    messageConfirm("确定要下线该用户吗?").then(() => {
        kickOutUser(token).then(({ data }) => {
            if (data.flag) {
                notifySuccess(data.msg);
                getList();
            }
        })
    }).catch(() => { });
};
const getList = () => {
    loading.value = true;
    getOnlineList(queryParams.value).then(({ data }) => {
        onlineList.value = data.data.recordList;
        count.value = data.data.count;
        loading.value = false;
    })
};
const handleQuery = () => {
    queryParams.value.current = 1;
    getList();
};
onMounted(() => {
    getList();
});
</script>

<style scoped>

</style>