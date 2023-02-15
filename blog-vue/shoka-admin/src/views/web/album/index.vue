<template>
    <div class="app-container">
        <!-- 搜索栏 -->
        <el-form @submit.native.prevent :inline="true" v-show="showSearch">
            <el-form-item label="相册名称">
                <el-input v-model="queryParams.keyword" style="width: 200px" placeholder="请输入相册名称" clearable
                    @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            </el-form-item>
        </el-form>
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb15">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="Plus" @click="handleAdd">新建</el-button>
            </el-col>
            <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <!-- 相册列表 -->
        <el-row :gutter="12" v-loading="loading">
            <el-empty v-if="albumList === null" description="暂无相册" />
            <el-col v-for="album of albumList" :key="album.id" :xs="12" :sm="12" :lg="6">
                <div class="album-item" @click="checkPhoto(album.id)">
                    <div class="album-operation">
                        <el-dropdown @command="handleCommand">
                            <el-icon style="color:#fff">
                                <MoreFilled />
                            </el-icon>
                            <template #dropdown>
                                <el-dropdown-menu>
                                    <el-dropdown-item :command="'update' + album.id">编辑</el-dropdown-item>
                                    <el-dropdown-item :command="'delete' + album.id">删除</el-dropdown-item>
                                </el-dropdown-menu>
                            </template>
                        </el-dropdown>
                    </div>
                    <el-image class="album-cover" fit="cover" :src="album.albumCover">
                    </el-image>
                    <div class="photo-count">
                        <div>{{ album.photoCount }}</div>
                        <el-icon v-if="album.status === 2">
                            <Hide />
                        </el-icon>
                    </div>
                    <div class="album-name">{{ album.albumName }}</div>
                </div>
            </el-col>
        </el-row>
        <!-- 分页 -->
        <el-pagination class="pagination-container" v-model:current-page="queryParams.current"
            v-model:page-size="queryParams.size" :hide-on-single-page="true" layout=" prev, pager, next" :total="count"
            @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>
        <!-- 添加或修改对话框 -->
        <el-dialog :title="title" v-model="addOrUpdate" width="550px" append-to-body>
            <el-form ref="albumFormRef" label-width="100px" :model="albumForm" :rules="rules">
                <el-form-item label="相册名称" prop="albumName">
                    <el-input placeholder="请输入相册名称" v-model="albumForm.albumName" style="width: 250px;" />
                </el-form-item>
                <el-form-item label="相册描述" prop="albumDesc">
                    <el-input placeholder="请输入相册描述" v-model="albumForm.albumDesc" style="width: 250px;" />
                </el-form-item>
                <el-form-item label="相册封面" prop="albumCover">
                    <el-upload drag :show-file-list="false" :headers="authorization" action="/api/admin/album/upload"
                        accept="image/*" :before-upload="beforeUpload" :on-success="handleSuccess">
                        <el-icon class="el-icon--upload" v-if="albumForm.albumCover === ''"><upload-filled /></el-icon>
                        <div class="el-upload__text" v-if="albumForm.albumCover === ''">
                            将文件拖到此处，或<em>点击上传</em>
                        </div>
                        <img v-else :src="albumForm.albumCover" width="360" />
                    </el-upload>
                </el-form-item>
                <el-form-item label="发布形式">
                    <el-radio-group v-model="albumForm.status">
                        <el-radio :label="1">公开</el-radio>
                        <el-radio :label="2">私密</el-radio>
                    </el-radio-group>
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
import { addAlbum, deleteAlbum, editAlbum, getAlbumList, updateAlbum } from '@/api/album';
import { Album, AlbumForm, AlbumQuery } from '@/api/album/types';
import router from "@/router";
import { messageConfirm, notifySuccess } from '@/utils/modal';
import { getToken, token_prefix } from '@/utils/token';
import { AxiosResponse } from 'axios';
import { FormInstance, FormRules, UploadRawFile } from 'element-plus';
import * as imageConversion from 'image-conversion';
import { computed, onMounted, reactive, ref, toRefs } from 'vue';
const albumFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
    albumName: [{ required: true, message: "请输入相册名称", trigger: "blur" }],
    albumCover: [{ required: true, message: "请上传相册封面", trigger: "blur" }],
});
const authorization = computed(() => {
    return {
        Authorization: token_prefix + getToken(),
    }
});
const data = reactive({
    count: 0,
    showSearch: true,
    loading: false,
    title: "",
    addOrUpdate: false,
    queryParams: {
        current: 1,
        size: 18,
    } as AlbumQuery,
    albumForm: {} as AlbumForm,
    albumList: [] as Album[],
});
const {
    count,
    showSearch,
    loading,
    title,
    addOrUpdate,
    queryParams,
    albumForm,
    albumList,
} = toRefs(data);
const checkPhoto = (albumId: number) => {
    router.push({ path: `/web/photo/${albumId}` });
};
const beforeUpload = (rawFile: UploadRawFile) => {
    return new Promise(resolve => {
        if (rawFile.size / 1024 < 200) {
            resolve(rawFile);
        }
        // 压缩到200KB,这里的200就是要压缩的大小,可自定义
        imageConversion
            .compressAccurately(rawFile, 200)
            .then(res => {
                resolve(res);
            });
    });
};
const handleSuccess = (response: AxiosResponse) => {
    albumForm.value.albumCover = response.data;
};
const handleSizeChange = (size: number) => {
    queryParams.value.size = size;
    getList();
};
const handleCurrentChange = (current: number) => {
    queryParams.value.current = current;
    getList();
};
const handleAdd = () => {
    albumFormRef.value?.clearValidate();
    title.value = "新建相册";
    albumForm.value = {
        id: undefined,
        albumName: "",
        albumDesc: "",
        albumCover: "",
        status: 1,
    }
    addOrUpdate.value = true;
};
const handleCommand = (command: string) => {
    const type = command.substring(0, 6);
    albumForm.value.id = Number(command.substring(6));
    if (type === "delete") {
        messageConfirm("确认删除已选中的数据项?").then(() => {
            deleteAlbum(albumForm.value.id).then(({ data }) => {
                if (data.flag) {
                    notifySuccess(data.msg);
                    getList();
                }
            });
        }).catch(() => { });
    } else {
        albumFormRef.value?.resetFields();
        editAlbum(albumForm.value.id).then(({ data }) => {
            if (data.flag) {
                albumForm.value = data.data;
                title.value = "修改相册";
                addOrUpdate.value = true;
            }
        })
    }
};
const submitForm = () => {
    albumFormRef.value?.validate((valid) => {
        if (valid) {
            if (albumForm.value.id !== undefined) {
                updateAlbum(albumForm.value).then(({ data }) => {
                    if (data.flag) {
                        notifySuccess(data.msg);
                        getList();
                    }
                    addOrUpdate.value = false;
                })
            } else {
                addAlbum(albumForm.value).then(({ data }) => {
                    if (data.flag) {
                        notifySuccess(data.msg);
                        getList();
                    }
                    addOrUpdate.value = false;
                });
            }
        }
    });
};
const getList = () => {
    loading.value = true;
    getAlbumList(queryParams.value).then(({ data }) => {
        albumList.value = data.data.recordList;
        count.value = data.data.count;
        loading.value = false;
    });
};
const handleQuery = () => {
    getList();
};
onMounted(() => {
    getList();
});
</script>

<style lang="scss" scoped>
.album-item {
    position: relative;
    cursor: pointer;
    margin-bottom: 1rem;

    .album-operation {
        position: absolute;
        top: 0.5rem;
        right: 0.8rem;
        z-index: 9;
    }

    .album-cover {
        position: relative;
        border-radius: 4px;
        width: 100%;
        height: 170px;

        &:before {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(0, 0, 0, 0.5);
        }
    }

    .photo-count {
        position: absolute;
        left: 0;
        right: 0;
        display: flex;
        align-items: center;
        justify-content: space-between;
        font-size: 1.5rem;
        padding: 0 0.5rem;
        bottom: 2.6rem;
        color: #fff;
    }

    .album-name {
        text-align: center;
        margin-top: 0.5rem;
    }
}
</style>