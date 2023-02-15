<template>
    <div class="app-container">
        <!-- 搜索栏 -->
        <el-form @submit.native.prevent :model="queryParams" :inline="true" v-show="showSearch">
            <el-form-item label="访问页面">
                <el-input @keyup.enter="handleQuery" v-model="queryParams.keyword" style="width: 200px"
                    placeholder="请输入访问页面" clearable />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            </el-form-item>
        </el-form>
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb15">
            <el-col :span="1.5">
                <el-button type="danger" plain :disabled="logIdList.length === 0" icon="Delete"
                    @click="handleDelete(undefined)">批量删除</el-button>
            </el-col>
            <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <!-- 表格展示 -->
        <el-table border :data="logList" @selection-change="handleSelectionChange" v-loading="loading">
            <!-- 表格列 -->
            <el-table-column type="selection" align="center" width="55"></el-table-column>
            <!-- 访问页面 -->
            <el-table-column prop="page" label="访问页面" align="center" width="130"></el-table-column>
            <!-- 访问ip -->
            <el-table-column prop="ipAddress" label="访问ip" align="center" width="150"></el-table-column>
            <!-- ip来源 -->
            <el-table-column prop="ipSource" label="ip来源" align="center" width="200"></el-table-column>
            <!-- 操作系统 -->
            <el-table-column prop="os" label="操作系统" align="center" width="180"></el-table-column>
            <!-- 浏览器 -->
            <el-table-column prop="browser" label="浏览器" align="center"></el-table-column>
            <!-- 操作日期 -->
            <el-table-column prop="createTime" label="访问时间" align="center" width="200">
                <template #default="scope">
                    <div class="create-time">
                        <el-icon>
                            <clock />
                        </el-icon>
                        <span style="margin-left: 10px">{{ formatDateTime(scope.row.createTime) }}</span>
                    </div>
                </template>
            </el-table-column>
            <!-- 操作 -->
            <el-table-column label="操作" align="center" width="150">
                <template #default="scope">
                    <el-button type="danger" icon="Delete" link @click="handleDelete(scope.row.id)">
                        删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <pagination v-if="count > 0" :total="count" v-model:page="queryParams.current" v-model:limit="queryParams.size"
            @pagination="getList" />
    </div>
</template>

<script setup lang="ts">import { deleteVisit, getVisitLogList } from '@/api/log';
import { LogQuery, VisitLog } from '@/api/log/types';
import { formatDateTime } from '@/utils/date';
import { messageConfirm, notifySuccess } from '@/utils/modal';
import { onMounted, reactive, toRefs } from 'vue';

const data = reactive({
    count: 0,
    showSearch: true,
    loading: false,
    queryParams: {
        current: 1,
        size: 10,
    } as LogQuery,
    logIdList: [] as number[],
    logList: [] as VisitLog[],
});
const {
    count,
    showSearch,
    loading,
    queryParams,
    logIdList,
    logList,
} = toRefs(data);
const handleSelectionChange = (selection: VisitLog[]) => {
    logIdList.value = selection.map((item) => item.id);
};
const handleDelete = (id?: number) => {
    let ids: number[] = [];
    if (id == undefined) {
        ids = logIdList.value;
    } else {
        ids = [id];
    }
    messageConfirm("确认删除已选中的数据项?").then(() => {
        deleteVisit(ids).then(({ data }) => {
            if (data.flag) {
                notifySuccess(data.msg);
                getList();
            }
        });
    }).catch(() => { });
};
const getList = () => {
    loading.value = true;
    getVisitLogList(queryParams.value).then(({ data }) => {
        logList.value = data.data.recordList;
        count.value = data.data.count;
        loading.value = false;
    });
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