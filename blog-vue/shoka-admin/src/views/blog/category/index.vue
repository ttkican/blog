<template>
    <div class="app-container">
        <!-- 搜索栏 -->
        <el-form @submit.native.prevent :model="queryParams" :inline="true" v-show="showSearch">
            <el-form-item label="分类名称">
                <el-input @keyup.enter="handleQuery" v-model="queryParams.keyword" style="width: 200px"
                    placeholder="请输入分类名称" clearable />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            </el-form-item>
        </el-form>
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb15">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="Plus" @click="openModel(undefined)">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain :disabled="categoryIdList.length === 0" icon="Delete"
                    @click="handleDelete(undefined)">批量删除</el-button>
            </el-col>
            <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <!-- 表格展示 -->
        <el-table border :data="categoryList" @selection-change="handleSelectionChange" v-loading="loading">
            <!-- 表格列 -->
            <el-table-column type="selection" width="55" align="center"></el-table-column>
            <!-- 分类名 -->
            <el-table-column prop="categoryName" width="300" label="分类名" align="center"></el-table-column>
            <!-- 文章量 -->
            <el-table-column prop="articleCount" label="文章量" align="center"></el-table-column>
            <!-- 创建时间 -->
            <el-table-column prop="createTime" width="300" label="创建时间" align="center">
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
            <el-table-column width="300" label="操作" align="center">
                <template #default="scope">
                    <el-button type="primary" icon="Edit" link @click="openModel(scope.row)">
                        编辑
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
        <!-- 添加或修改对话框 -->
        <el-dialog :title="title" v-model="addOrUpdate" width="500px" append-to-body>
            <el-form ref="categoryFormRef" label-width="100px" :model="categoryForm" :rules="rules"
                @submit.native.prevent>
                <el-form-item label="分类名称" prop="categoryName">
                    <el-input placeholder="请输入分类名称" v-model="categoryForm.categoryName" style="width: 250px;" />
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="primary" @click="submitForm">确 定</el-button>
                    <el-button @click="addOrUpdate = false">取 消</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { addCategory, deleteCategory, getCategoryList, updateCategory } from "@/api/category";
import { Category, CategoryForm, CategoryQuery } from "@/api/category/types";
import { formatDate } from "@/utils/date";
import { messageConfirm, notifySuccess } from "@/utils/modal";
import { FormInstance, FormRules } from 'element-plus';
import { onMounted, reactive, ref, toRefs } from "vue";
const categoryFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
    categoryName: [{ required: true, message: "请输入分类名称", trigger: "blur" }],
});
const data = reactive({
    count: 0,
    showSearch: true,
    loading: false,
    title: "",
    addOrUpdate: false,
    queryParams: {
        current: 1,
        size: 10,
    } as CategoryQuery,
    categoryForm: {
        id: undefined,
        categoryName: "",
    } as CategoryForm,
    categoryIdList: [] as number[],
    categoryList: [] as Category[],
});
const {
    count,
    showSearch,
    loading,
    title,
    addOrUpdate,
    queryParams,
    categoryForm,
    categoryIdList,
    categoryList,
} = toRefs(data);
const handleSelectionChange = (selection: Category[]) => {
    categoryIdList.value = selection.map((item) => item.id);
};
const openModel = (category?: Category) => {
    categoryFormRef.value?.clearValidate();
    if (category !== undefined) {
        title.value = "修改分类";
        categoryForm.value = {
            id: category.id,
            categoryName: category.categoryName,
        }
    } else {
        title.value = "添加分类";
        categoryForm.value = {
            id: undefined,
            categoryName: "",
        }
    }
    addOrUpdate.value = true;
};
const submitForm = () => {
    categoryFormRef.value?.validate((valid) => {
        if (valid) {
            if (categoryForm.value.id !== undefined) {
                updateCategory(categoryForm.value).then(({ data }) => {
                    if (data.flag) {
                        notifySuccess(data.msg);
                        getList();
                    }
                    addOrUpdate.value = false;
                })
            } else {
                addCategory(categoryForm.value).then(({ data }) => {
                    if (data.flag) {
                        notifySuccess(data.msg);
                        getList();
                    }
                    addOrUpdate.value = false;
                });
            }
        }
    })
};
const handleDelete = (id?: number) => {
    let ids: number[] = [];
    if (id == undefined) {
        ids = categoryIdList.value;
    } else {
        ids = [id];
    }
    messageConfirm("确认删除已选中的数据项?").then(() => {
        deleteCategory(ids).then(({ data }) => {
            if (data.flag) {
                notifySuccess(data.msg);
                getList();
            }
        });
    }).catch(() => { });
};
const getList = () => {
    loading.value = true;
    getCategoryList(queryParams.value).then(({ data }) => {
        categoryList.value = data.data.recordList;
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