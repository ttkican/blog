<template>
    <div class="app-container">
        <!-- 搜索栏 -->
        <el-form @submit.native.prevent :model="queryParams" :inline="true" v-show="showSearch">
            <el-form-item label="用户昵称">
                <el-input @keyup.enter="handleQuery" v-model="queryParams.keyword" style="width: 200px"
                    placeholder="请输入用户昵称" clearable />
            </el-form-item>
            <el-form-item label="状态">
                <el-select v-model="queryParams.isCheck" placeholder="评论状态" clearable style="width: 200px">
                    <el-option v-for="item in status" :key="item.value" :label="item.label" :value="item.value" />
                </el-select>
            </el-form-item>
            <el-form-item label="来源">
                <el-select v-model="queryParams.commentType" placeholder="评论来源" clearable style="width: 200px">
                    <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            </el-form-item>
        </el-form>
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb15">
            <el-col :span="1.5">
                <el-button type="danger" plain :disabled="commentIdList.length === 0" icon="Delete"
                    @click="handleDelete(undefined)">批量删除</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="success" plain :disabled="commentIdList.length === 0" icon="Check"
                    @click="handleCheck(undefined)">批量通过</el-button>
            </el-col>
            <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <!-- 表格展示 -->
        <el-table border :data="commentList" @selection-change="handleSelectionChange" v-loading="loading">
            <!-- 表格列 -->
            <el-table-column type="selection" align="center" width="55" />
            <!-- 头像 -->
            <el-table-column prop="avatar" label="头像" align="center" width="100">
                <template #default="scope">
                    <img :src="scope.row.avatar" width="40" height="40" />
                </template>
            </el-table-column>
            <!-- 评论人昵称 -->
            <el-table-column prop="fromNickname" label="评论人" align="center" width="120" />
            <!-- 回复人昵称 -->
            <el-table-column prop="toNickname" label="回复人" align="center" width="120">
                <template #default="scope">
                    <span v-if="scope.row.toNickname">
                        {{ scope.row.toNickname }}
                    </span>
                    <span v-else>无</span>
                </template>
            </el-table-column>
            <!-- 评论文章标题 -->
            <el-table-column prop="articleTitle" label="文章标题" align="center">
                <template #default="scope">
                    <span v-if="scope.row.articleTitle">
                        {{ scope.row.articleTitle }}
                    </span>
                    <span v-else>无</span>
                </template>
            </el-table-column>
            <!-- 评论内容 -->
            <el-table-column prop="commentContent" label="评论内容" align="center">
                <template #default="scope">
                    <span v-html="scope.row.commentContent" class="comment-content" />
                </template>
            </el-table-column>
            <!-- 评论时间 -->
            <el-table-column prop="createTime" label="评论时间" width="130" align="center">
                <template #default="scope">
                    <div class="create-time">
                        <el-icon>
                            <clock />
                        </el-icon>
                        <span style="margin-left: 10px">{{ formatDate(scope.row.createTime) }}</span>
                    </div>
                </template>
            </el-table-column>
            <!-- 状态 -->
            <el-table-column prop="isCheck" label="状态" width="90" align="center">
                <template #default="scope">
                    <el-tag v-if="scope.row.isCheck == 0" type="warning">审核中</el-tag>
                    <el-tag v-if="scope.row.isCheck == 1" type="success">通过</el-tag>
                </template>
            </el-table-column>
            <!-- 来源 -->
            <el-table-column prop="commentType" label="来源" width="80" align="center">
                <template #default="scope">
                    <el-tag v-if="scope.row.commentType == 1">文章</el-tag>
                    <el-tag v-if="scope.row.commentType == 2" type="warning">友链</el-tag>
                    <el-tag v-if="scope.row.commentType == 3" type="danger">说说</el-tag>
                </template>
            </el-table-column>
            <!-- 列操作 -->
            <el-table-column label="操作" width="160" align="center">
                <template #default="scope">
                    <el-button v-if="scope.row.isCheck == 0" type="success" icon="Finished" link
                        @click="handleCheck(scope.row.id)">
                        通过
                    </el-button>
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

<script setup lang="ts">
import { deleteComment, getCommentList, updateCommentCheck } from '@/api/comment';
import { Comment, CommentQuery } from '@/api/comment/types';
import { CheckDTO } from '@/model';
import { formatDate } from '@/utils/date';
import { messageConfirm, notifySuccess } from '@/utils/modal';
import { onMounted, reactive, toRefs } from 'vue';

const data = reactive({
    count: 0,
    showSearch: true,
    loading: false,
    queryParams: {
        current: 1,
        size: 10,
    } as CommentQuery,
    status: [
        {
            value: 0,
            label: "审核中",
        },
        {
            value: 1,
            label: "通过",
        },
    ],
    options: [
        {
            value: 1,
            label: "文章"
        },
        {
            value: 2,
            label: "友链"
        },
        {
            value: 3,
            label: "说说"
        }
    ],
    commentIdList: [] as number[],
    commentList: [] as Comment[],
});
const {
    count,
    showSearch,
    loading,
    queryParams,
    status,
    options,
    commentIdList,
    commentList,
} = toRefs(data);
const getList = () => {
    loading.value = true;
    getCommentList(queryParams.value).then(({ data }) => {
        commentList.value = data.data.recordList;
        count.value = data.data.count;
        loading.value = false;
    });
};
const handleCheck = (id?: number) => {
    let ids: number[] = [];
    if (id == undefined) {
        ids = commentIdList.value;
    } else {
        ids = [id];
    }
    let checkDTO: CheckDTO = {
        idList: ids,
        isCheck: 1,
    }
    messageConfirm("确认通过已选中的数据项?").then(() => {
        updateCommentCheck(checkDTO).then(({ data }) => {
            if (data.flag) {
                notifySuccess(data.msg);
                getList();
            }
        });
    }).catch(() => { });
};
const handleDelete = (id?: number) => {
    let ids: number[] = [];
    if (id == undefined) {
        ids = commentIdList.value;
    } else {
        ids = [id];
    }
    messageConfirm("确认删除已选中的数据项?").then(() => {
        deleteComment(ids).then(({ data }) => {
            if (data.flag) {
                notifySuccess(data.msg);
                getList();
            }
        });
    }).catch(() => { });
};
const handleSelectionChange = (selection: Comment[]) => {
    commentIdList.value = selection.map((item) => item.id);
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