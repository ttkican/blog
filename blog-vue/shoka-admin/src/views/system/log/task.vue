<template>
    <div class="app-container">
        <!-- 搜索栏 -->
        <el-form ref="queryFormRef" :model="queryParams" :inline="true" v-show="showSearch">
            <el-form-item label="任务名称" prop="jobName">
                <el-input v-model="queryParams.keyword" placeholder="请输入任务名称" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="任务组名" prop="jobGroup">
                <el-input v-model="queryParams.taskGroup" placeholder="请输入任务组名" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="任务状态" prop="status">
                <el-select v-model="queryParams.status" placeholder="任务状态" clearable style="width: 200px">
                    <el-option v-for="item in status" :key="item.value" :label="item.label" :value="item.value" />
                </el-select>
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
            <el-col :span="1.5">
                <el-button type="danger" plain icon="Delete" @click="handleClear">清空日志</el-button>
            </el-col>
            <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <!-- 表格展示 -->
        <el-table border :data="logList" @selection-change="handleSelectionChange" v-loading="loading">
            <!-- 表格列 -->
            <el-table-column type="selection" width="55" align="center"></el-table-column>
            <!--任务名称 -->
            <el-table-column label="任务名称" width="160" align="center" prop="taskName"
                :show-overflow-tooltip="true"></el-table-column>
            <!--任务组名 -->
            <el-table-column label="任务组名" width="140" align="center" prop="taskGroup"></el-table-column>
            <!-- 调用目标 -->
            <el-table-column label="调用目标" width="150" align="center" prop="invokeTarget"
                :show-overflow-tooltip="true"></el-table-column>
            <!-- 日志信息 -->
            <el-table-column label="日志信息" align="center" prop="taskMessage"
                :show-overflow-tooltip="true"></el-table-column>
            <!-- 状态 -->
            <el-table-column label="状态" align="center" width="100">
                <template #default="scope">
                    <el-tag v-if="scope.row.status == 1" type="success">成功</el-tag>
                    <el-tag v-if="scope.row.status == 0" type="danger">失败</el-tag>
                </template>
            </el-table-column>
            <!-- 执行时间 -->
            <el-table-column label="执行时间" align="center" width="180">
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
            <el-table-column label="操作" align="center" width="120">
                <template #default="scope">
                    <el-button type="primary" icon="View" link @click="handleView(scope.row)">详细</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <pagination v-if="count > 0" :total="count" v-model:page="queryParams.current" v-model:limit="queryParams.size"
            @pagination="getList" />
        <!-- 操作日志详细 -->
        <el-dialog title="任务日志详细" v-model="open" width="700px" append-to-body>
            <el-form :model="taskForm" label-width="100px">
                <el-form-item label="任务名称：">
                    {{ taskForm.taskName }}
                </el-form-item>
                <el-form-item label="任务分组：">
                    {{ taskForm.taskGroup }}
                </el-form-item>
                <el-form-item label="调用方法：">
                    {{ taskForm.invokeTarget }}
                </el-form-item>
                <el-form-item label="日志信息：">
                    {{ taskForm.taskMessage }}
                </el-form-item>
                <el-form-item label="执行时间：">
                    {{ formatDateTime(taskForm.createTime) }}
                </el-form-item>
                <el-form-item label="执行状态：">
                    <div v-if="taskForm.status == 1">成功</div>
                    <div v-else-if="taskForm.status == 0">失败</div>
                </el-form-item>
                <el-form-item label="错误信息：" v-if="taskForm.status == 0">{{
                    taskForm.errorInfo
                }}</el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="open = false">关 闭</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { clearTaskLog, deleteTaskLog, getTaskLogList } from '@/api/log';
import { TaskLog } from '@/api/log/types';
import { TaskQuery } from '@/api/task/types';
import { formatDateTime } from "@/utils/date";
import { messageConfirm, notifySuccess } from '@/utils/modal';
import { onMounted, reactive, toRefs } from 'vue';
const data = reactive({
    count: 0,
    open: false,
    showSearch: true,
    loading: false,
    queryParams: {
        current: 1,
        size: 10,
    } as TaskQuery,
    status: [
        {
            value: 0,
            label: "失败",
        },
        {
            value: 1,
            label: "成功",
        },
    ],
    logIdList: [] as number[],
    logList: [] as TaskLog[],
    taskForm: {} as TaskLog,
});
const {
    count,
    open,
    showSearch,
    loading,
    queryParams,
    status,
    logIdList,
    logList,
    taskForm
} = toRefs(data);
const handleView = (log: TaskLog) => {
    taskForm.value = log;
    open.value = true;
};
const handleSelectionChange = (selection: TaskLog[]) => {
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
        deleteTaskLog(ids).then(({ data }) => {
            if (data.flag) {
                notifySuccess(data.msg);
                getList();
            }
        });
    }).catch(() => { });
};
const handleClear = () => {
    clearTaskLog().then(({ data }) => {
        if (data.flag) {
            notifySuccess(data.msg);
            getList();
        }
    })
};
const getList = () => {
    loading.value = true;
    getTaskLogList(queryParams.value).then(({ data }) => {
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