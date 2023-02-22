<template>
    <div class="app-container">
        <!-- 文章状态 -->
        <el-row :gutter="24" style="color: #999;" class="mb15">
            <el-col :span="1.5">
                状态
            </el-col>
            <el-col :span="1.5" :class="isActive('all')" @click="changeStatus('all')">
                全部
            </el-col>
            <el-col :span="1.5" :class="isActive('public')" @click="changeStatus('public')">
                公开
            </el-col>
            <el-col :span="1.5" :class="isActive('secret')" @click="changeStatus('secret')">
                私密
            </el-col>
            <el-col :span="1.5" :class="isActive('draft')" @click="changeStatus('draft')">
                草稿
            </el-col>
            <el-col :span="1.5" :class="isActive('delete')" @click="changeStatus('delete')">
                回收站
            </el-col>
        </el-row>
        <!-- 搜索栏 -->
        <el-form :model="queryParams" :inline="true" v-show="showSearch">
            <el-form-item label="名称">
                <el-input @keyup.enter="handleQuery" v-model="queryParams.keyword" style="width: 150px"
                    placeholder="请输入文章名称" clearable />
            </el-form-item>
            <el-form-item label="类型">
                <el-select v-model="queryParams.articleType" placeholder="请选择类型" clearable style="width: 130px;">
                    <el-option v-for="item in typeList" :key="item.value" :label="item.label" :value="item.value" />
                </el-select>
            </el-form-item>
            <el-form-item label="标签">
                <el-select v-model="queryParams.tagId" placeholder="请选择标签" clearable filterable @visible-change="getTag"
                    style="width: 130px">
                    <el-option v-for="item in tagList" :key="item.id" :label="item.tagName" :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="分类">
                <el-select v-model="queryParams.categoryId" placeholder="请选择分类" clearable filterable
                    @visible-change="getCategory" style="width: 130px">
                    <el-option v-for="item in categoryList" :key="item.id" :label="item.categoryName" :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            </el-form-item>
        </el-form>
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb15">
            <el-col :span="1.5">
                <el-button type="danger" plain icon="Delete" :disabled="articleIdList.length === 0"
                    @click="handleDelete(undefined)">批量删除</el-button>
            </el-col>
            <el-col :span="1.5" v-if="queryParams.isDelete == 0">
                <el-button type="danger" plain icon="Delete" :disabled="articleIdList.length === 0"
                    @click="handleRecycle(undefined)">批量回收</el-button>
            </el-col>
            <el-col :span="1.5" v-if="queryParams.isDelete == 1">
                <el-button type="success" plain icon="Finished" :disabled="articleIdList.length === 0"
                    @click="handleRecycle(undefined)">批量恢复</el-button>
            </el-col>
            <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <!-- 表格展示 -->
        <el-table border :data="articleList" @selection-change="handleSelectionChange" v-loading="loading">
            <!-- 表格列 -->
            <el-table-column type="selection" width="55" align="center"></el-table-column>
            <!--缩略图-->
            <el-table-column prop="articleCover" label="缩略图" width="180" align="center">
                <template #default="scope">
                    <el-image class="article-cover" :src="scope.row.articleCover"></el-image>
                    <el-icon v-if="scope.row.status == 1" class="article-status-icon">
                        <View />
                    </el-icon>
                    <el-icon v-if="scope.row.status == 2" class="article-status-icon">
                        <Hide />
                    </el-icon>
                    <el-icon v-if="scope.row.status == 3" class="article-status-icon">
                        <EditPen />
                    </el-icon>
                </template>
            </el-table-column>
            <!-- 文章标题 -->
            <el-table-column prop="articleTitle" label="标题" align="center"></el-table-column>
            <!-- 文章分类 -->
            <el-table-column prop="categoryName" label="分类" width="90" align="center"></el-table-column>
            <!-- 文章标签 -->
            <el-table-column prop="tagVOList" label="标签" width="160" align="center">
                <template #default="scope">
                    <el-tag v-for="item of scope.row.tagVOList" :key="item.tagId"
                        style="margin-right: 0.2rem; margin-top: 0.2rem">
                        {{ item.tagName }}
                    </el-tag>
                </template>
            </el-table-column>
            <!-- 文章浏览量 -->
            <el-table-column prop="viewCount" label="浏览量" width="70" align="center">
            </el-table-column>
            <!-- 文章点赞量 -->
            <el-table-column prop="likeCount" label="点赞量" width="70" align="center">
            </el-table-column>
            <!-- 文章类型 -->
            <el-table-column prop="articleType" label="类型" width="70" align="center">
                <template #default="scope">
                    <el-tag v-if="scope.row.articleType == 1" type="success">原创</el-tag>
                    <el-tag v-if="scope.row.articleType == 2" type="danger">转载</el-tag>
                    <el-tag v-if="scope.row.articleType == 3" type="primary">翻译</el-tag>
                </template>
            </el-table-column>
            <!-- 文章置顶 -->
            <el-table-column prop="isTop" label="置顶" width="70" align="center">
                <template #default="scope">
                    <el-switch v-model="scope.row.isTop" style="--el-switch-on-color: #13ce66;"
                        :disabled="scope.row.isDelete == 1" :active-value="1" :inactive-value="0"
                        @change="handleTop(scope.row)"></el-switch>
                </template>
            </el-table-column>
            <!-- 文章推荐 -->
            <el-table-column prop="isRecommend" label="推荐" width="70" align="center">
                <template #default="scope">
                    <el-switch v-model="scope.row.isRecommend" style="--el-switch-on-color: #13ce66;"
                        :disabled="scope.row.isDelete == 1" :active-value="1" :inactive-value="0"
                        @change="handleRecommend(scope.row)"></el-switch>
                </template>
            </el-table-column>
            <!-- 创建时间 -->
            <el-table-column prop="createTime" width="130" label="创建时间" align="center">
                <template #default="scope">
                    <div class="create-time">
                        <el-icon>
                            <clock />
                        </el-icon>
                        <span style="margin-left: 10px">{{ formatDate(scope.row.createTime) }}</span>
                    </div>
                </template>
            </el-table-column>
            <!-- 操作 -->
            <el-table-column width="220" label="操作" align="center">
                <template #default="scope">
                    <el-button type="primary" icon="Edit" link @click="handleEdit(scope.row.id)"
                        v-if="scope.row.isDelete == 0">
                        编辑
                    </el-button>
                    <el-button type="danger" icon="Delete" link @click="handleDelete(scope.row.id)">
                        删除
                    </el-button>
                    <el-button type="danger" icon="Delete" link @click="handleRecycle(scope.row.id)"
                        v-if="queryParams.isDelete == 0">
                        回收
                    </el-button>
                    <el-button type="success" icon="Finished" link @click="handleRecycle(scope.row.id)"
                        v-if="queryParams.isDelete == 1">
                        恢复
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
import { deleteArticle, getArticleList, getCategoryOption, getTagOption, recycleArticle, updateArticleRecommend, updateArticleTop } from '@/api/article';
import { Article, ArticleQuery, CategoryVO, DeleteDTO, TagVO } from '@/api/article/types';
import router from "@/router";
import { formatDate } from "@/utils/date";
import { messageConfirm, notifySuccess } from '@/utils/modal';
import { computed, onMounted, reactive, toRefs } from 'vue';

const isActive = computed(() => {
    return function (value: string | undefined) {
        return activeStatus.value == value ? "active-status" : "status";
    }
});
const data = reactive({
    count: 0,
    showSearch: true,
    loading: false,
    title: "",
    queryParams: {
        current: 1,
        size: 10,
        isDelete: 0,
    } as ArticleQuery,
    typeList: [
        {
            value: 1,
            label: "原创",
        },
        {
            value: 2,
            label: "转载",
        },
        {
            value: 3,
            label: "翻译",
        },
    ],
    activeStatus: "all",
    categoryList: [] as CategoryVO[],
    tagList: [] as TagVO[],
    articleIdList: [] as number[],
    articleList: [] as Article[],
});
const {
    count,
    showSearch,
    loading,
    queryParams,
    typeList,
    activeStatus,
    categoryList,
    tagList,
    articleIdList,
    articleList,
} = toRefs(data);
const handleSelectionChange = (selection: Article[]) => {
    articleIdList.value = selection.map((item) => item.id);
};
const changeStatus = (status: string) => {
    switch (status) {
        case "all":
            queryParams.value.isDelete = 0;
            queryParams.value.status = undefined;
            break;
        case "public":
            queryParams.value.isDelete = 0;
            queryParams.value.status = 1;
            break;
        case "secret":
            queryParams.value.isDelete = 0;
            queryParams.value.status = 2;
            break;
        case "draft":
            queryParams.value.isDelete = 0;
            queryParams.value.status = 3;
            break;
        case "delete":
            queryParams.value.isDelete = 1;
            queryParams.value.status = undefined;
            break;
    }
    activeStatus.value = status;
    handleQuery();
};
const handleRecycle = (id?: number) => {
    let params: DeleteDTO = {
        idList: [],
        isDelete: 0,
    };
    if (id != undefined) {
        params.idList = [id];
    } else {
        params.idList = articleIdList.value;
    }
    params.isDelete = queryParams.value.isDelete === 0 ? 1 : 0;
    let text = queryParams.value.isDelete === 0 ? "回收" : "恢复";
    messageConfirm("确认" + text + "已选中的数据项?").then(() => {
        recycleArticle(params).then(({ data }) => {
            if (data.flag) {
                notifySuccess(data.msg);
                getList();
            }
        });
    }).catch(() => { });
};
const handleEdit = (id: number) => {
    router.push({ path: `/article/write/${id}` });
};
const handleDelete = (id?: number) => {
    let ids: number[] = [];
    if (id == undefined) {
        ids = articleIdList.value;
    } else {
        ids = [id];
    }
    messageConfirm("确认删除已选中的数据项?").then(() => {
        deleteArticle(ids).then(({ data }) => {
            if (data.flag) {
                notifySuccess(data.msg);
                getList();
            }
        });
    }).catch(() => { });
};
const handleTop = (article: Article) => {
    let text = article.isTop === 0 ? "取消置顶" : "置顶";
    messageConfirm("确定要" + text + "该文章吗?").then(() => {
        updateArticleTop({ id: article.id, isTop: article.isTop }).then(({ data }) => {
            if (data.flag) {
                notifySuccess(data.msg);
            } else {
                article.isTop = article.isTop === 0 ? 1 : 0;
            }
        });
    }).catch(() => { article.isTop = article.isTop === 0 ? 1 : 0; });
};
const handleRecommend = (article: Article) => {
    let text = article.isRecommend === 0 ? "取消推荐" : "推荐";
    messageConfirm("确定要" + text + "该文章吗?").then(() => {
        updateArticleRecommend({ id: article.id, isRecommend: article.isRecommend }).then(({ data }) => {
            if (data.flag) {
                notifySuccess(data.msg);
            } else {
                article.isRecommend = article.isRecommend === 0 ? 1 : 0;
            }
        });
    }).catch(() => { article.isRecommend = article.isRecommend === 0 ? 1 : 0; });
};
const getCategory = (val: boolean) => {
    if (val) {
        getCategoryOption().then(({ data }) => {
            categoryList.value = data.data;
        });
    }
};
const getTag = (val: boolean) => {
    if (val) {
        getTagOption().then(({ data }) => {
            tagList.value = data.data;
        });
    }
};
const getList = () => {
    loading.value = true;
    getArticleList(queryParams.value).then(({ data }) => {
        articleList.value = data.data.recordList;
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
.status {
    cursor: pointer;
}

.active-status {
    cursor: pointer;
    color: #333;
    font-weight: bold;
}

.article-cover {
    position: relative;
    width: 100%;
    height: 90px;
    border-radius: 4px;
}

.article-cover::after {
    content: "";
    background: rgba(0, 0, 0, 0.3);
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
}

.article-status-icon {
    color: #fff;
    font-size: 1.5rem;
    position: absolute;
    right: 1rem;
    bottom: 1.4rem;
}
</style>