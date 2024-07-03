<template>
    <div class="app-container">
        <!-- 相册信息 -->
        <el-row :gutter="12" class="mb15">
            <el-col :span="1.5">
                <el-image fit="cover" class="album-cover" :src="albumInfo.albumCover">
                </el-image>
            </el-col>
            <el-col :span="12">
                <el-row align="bottom">
                    <span class="album-name">{{ albumInfo.albumName }}</span>
                    <span class="photo-count">{{ albumInfo.photoCount }}张</span>
                </el-row>
                <el-row class="album-desc">{{ albumInfo.albumDesc }}</el-row>
                <el-row class="select-count">已选择{{ selectPhotoIdList.length }}张</el-row>
            </el-col>
        </el-row>
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb20">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="Upload" @click="upload = true">上传</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="success" plain icon="Promotion"
                    :disabled="selectPhotoIdList.length == 0">移动</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="Delete" :disabled="selectPhotoIdList.length == 0"
                    @click="handleDelete">批量删除</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-checkbox v-model="checkAll" :indeterminate="isIndeterminate" @change="handleCheckAllChange">
                    全选
                </el-checkbox>
            </el-col>
        </el-row>
        <!-- 照片列表 -->
        <el-checkbox-group v-model="selectPhotoIdList" @change="handleCheckedPhotoChange">
            <el-row class="picture-list" :gutter="10">
                <el-col :xs="12" :sm="6" :lg="4" v-for="photo of photoList" :key="photo.id" style="margin-bottom:1rem;">
                    <el-checkbox :label="photo.id">
                        <template #default>
                            <div class="photo-item">
                                <div class="photo-operation">
                                    <el-dropdown @command="handleCommand">
                                        <el-icon style="color:#fff">
                                            <MoreFilled />
                                        </el-icon>
                                        <template #dropdown>
                                            <el-dropdown-menu>
                                                <el-dropdown-item :command="photo">编辑</el-dropdown-item>
                                            </el-dropdown-menu>
                                        </template>
                                    </el-dropdown>
                                </div>
                                <el-image class="photo-cover" fit="cover" :src="photo.photoUrl"
                                    :preview-src-list="[photo.photoUrl]">
                                </el-image>
                                <div class="photo-name">{{ photo.photoName }}</div>
                            </div>
                        </template>
                    </el-checkbox>
                </el-col>
            </el-row>
        </el-checkbox-group>
        <!-- 分页 -->
        <el-pagination class="pagination-container" v-model:current-page="queryParams.current"
            v-model:page-size="queryParams.size" :hide-on-single-page="true" layout=" prev, pager, next" :total="count"
            @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>
        <!-- 修改对话框 -->
        <el-dialog title="修改照片" v-model="update" width="550px" append-to-body>
            <el-form ref="photoFormRef" label-width="100px" :model="photoForm" :rules="rules">
                <el-form-item label="照片名称" prop="photoName">
                    <el-input placeholder="请输入照片名称" v-model="photoForm.photoName" style="width: 250px;" />
                </el-form-item>
                <el-form-item label="照片描述" prop="photoDesc">
                    <el-input placeholder="请输入照片描述" v-model="photoForm.photoDesc" style="width: 250px;" />
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="primary" @click="submitForm">确 定</el-button>
                    <el-button @click="update = false">取 消</el-button>
                </div>
            </template>
        </el-dialog>
        <!-- 上传对话框 -->
        <el-dialog title="上传照片" v-model="upload" width="850px" append-to-body>
            <div class="upload-container">
                <el-upload v-show="uploadList.length > 0" :headers="authorization" class="avatar-uploader" multiple
                    action="/api/admin/photo/upload" :before-upload="beforeUpload" :on-success="handleSuccess"
                    :on-remove="handleRemove" :on-preview="handlePictureCardPreview" list-type="picture-card"
                    :file-list="uploadList" accept="image/*">
                    <img class="avatar" />
                    <el-icon class="avatar-uploader-icon">
                        <Plus />
                    </el-icon>
                </el-upload>
                <div class="upload">
                    <el-upload v-show="uploadList.length === 0" :headers="authorization" drag multiple
                        action="/api/admin/photo/upload" :before-upload="beforeUpload" :show-file-list="false"
                        accept="image/*" :on-success="handleSuccess" style="width:360px;">
                        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
                        <div class="el-upload__text">
                            将文件拖到此处，或<em>点击上传</em>
                        </div>
                        <img width="360" />
                    </el-upload>
                </div>
            </div>
            <template #footer>
                <div class="dialog-footer">
                    <div>
                        共上传{{ uploadList.length }}张照片
                    </div>
                    <div>
                        <el-button type="primary" :disabled="uploadList.length == 0" @click="handleAdd">确 定</el-button>
                        <el-button @click="upload = false">取 消</el-button>
                    </div>
                </div>
            </template>
        </el-dialog>
        <!-- 图片预览 -->
        <el-dialog v-model="dialogVisible" append-to-body>
            <img :src="dialogImageUrl" style="max-width:100%" />
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { addPhoto, deletePhoto, getAlbumInfo, getPhotoList, updatePhoto } from '@/api/photo';
import { AlbumInfo, Photo, PhotoForm, PhotoQuery } from '@/api/photo/types';
import { Picture } from '@/model';
import { messageConfirm, notifySuccess } from '@/utils/modal';
import { getToken, token_prefix } from '@/utils/token';
import { AxiosResponse } from 'axios';
import { FormInstance, FormRules, UploadFile, UploadRawFile } from 'element-plus';
import * as imageConversion from 'image-conversion';
import { computed, onMounted, reactive, ref, toRefs, watch } from 'vue';
import { useRoute } from "vue-router";
const photoFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
    photoName: [{ required: true, message: "请输入照片名称", trigger: "blur" }],
});
const authorization = computed(() => {
    return {
        Authorization: token_prefix + getToken(),
    }
});
const route = useRoute();
const data = reactive({
    count: 0,
    loading: false,
    upload: false,
    update: false,
    checkAll: false,
    isIndeterminate: false,
    dialogImageUrl: "",
    dialogVisible: false,
    queryParams: {
        current: 1,
        size: 18,
        albumId: Number(route.params.albumId),
    } as PhotoQuery,
    photoForm: {} as PhotoForm,
    photoIdList: [] as number[],
    selectPhotoIdList: [] as number[],
    photoList: [] as Photo[],
    albumInfo: {} as AlbumInfo,
    uploadList: [] as Picture[],
});
const {
    count,
    loading,
    upload,
    update,
    checkAll,
    isIndeterminate,
    dialogImageUrl,
    dialogVisible,
    queryParams,
    photoForm,
    photoIdList,
    selectPhotoIdList,
    photoList,
    albumInfo,
    uploadList,
} = toRefs(data);
watch(
    () => photoList.value,
    (newValue) => {
        photoIdList.value = [];
        if (newValue && newValue.length > 0) {
            newValue.forEach(item => {
                photoIdList.value.push(item.id);
            });
        }
    });
const handleSizeChange = (size: number) => {
    queryParams.value.size = size;
    getList();
};
const handleCurrentChange = (current: number) => {
    queryParams.value.current = current;
    getList();
};
const handleCheckAllChange = (val: boolean) => {
    selectPhotoIdList.value = val ? photoIdList.value : [];
    isIndeterminate.value = false;
};
const handleCheckedPhotoChange = (value: number[]) => {
    const checkedCount = value.length;
    checkAll.value = checkedCount === photoIdList.value.length;
    isIndeterminate.value = checkedCount > 0 && checkedCount < photoIdList.value.length;
};
const handleCommand = (photo: Photo) => {
    photoFormRef.value?.resetFields();
    photoForm.value = photo;
    update.value = true;
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
    uploadList.value.push({ url: response.data });
};
const handleRemove = (file: UploadFile) => {
    uploadList.value.forEach((item, index) => {
        if (item.url == file.url) {
            uploadList.value.splice(index, 1);
        }
    });
};
const handlePictureCardPreview = (file: UploadFile) => {
    dialogImageUrl.value = file.url!;
    dialogVisible.value = true;
};
const handleMove = () => {

};
const handleDelete = () => {
    messageConfirm("确认删除已选中的数据项?").then(() => {
        deletePhoto(selectPhotoIdList.value).then(({ data }) => {
            if (data.flag) {
                notifySuccess(data.msg);
                getList();
                selectPhotoIdList.value = [];
                isIndeterminate.value = false;
            }
        });
    }).catch(() => { });
};
const handleAdd = () => {
    let photoUrlList: string[] = [];
    if (uploadList.value.length > 0) {
        uploadList.value.forEach(item => {
            photoUrlList.push(item.url);
        });
    }
    addPhoto({ albumId: Number(route.params.albumId), photoUrlList: photoUrlList }).then(({ data }) => {
        if (data.flag) {
            notifySuccess(data.msg);
            uploadList.value = [];
            getList();
        }
        upload.value = false;
    })
};
const submitForm = () => {
    photoFormRef.value?.validate((valid) => {
        if (valid) {
            updatePhoto(photoForm.value).then(({ data }) => {
                if (data.flag) {
                    notifySuccess(data.msg);
                    getList();
                }
                update.value = false;
            })
        }
    });
};
const getList = () => {
    loading.value = true;
    getPhotoList(queryParams.value).then(({ data }) => {
        photoList.value = data.data.recordList;
        count.value = data.data.count;
        loading.value = false;
    });
};
onMounted(() => {
    getList();
    getAlbumInfo(Number(route.params.albumId)).then(({ data }) => {
        albumInfo.value = data.data;
    });
});
</script>

<style lang="scss" scoped>
.album-cover {
    border-radius: 4px;
    width: 5rem;
    height: 5rem;
}

.album-name {
    font-size: 1.25rem;
}

.photo-count {
    font-size: 13px;
    margin: 0 0 0.1rem 0.5rem;
}

.album-desc {
    font-size: 15px;
    margin-top: 0.4rem;
}

.select-count {
    font-size: 13px;
    margin-top: 0.4rem;
}

.photo-item {
    position: relative;
    width: 100%;
    cursor: pointer;

    margin-bottom: 1rem;

    .photo-operation {
        position: absolute;
        top: 0.3rem;
        right: 0.5rem;
        z-index: 9;
    }

    .photo-cover {
        width: 100%;
        height: 7rem;
        border-radius: 4px;
    }

    .photo-name {
        font-size: 14px;
        margin-top: 0.3rem;
        text-align: center;
    }
}

.upload-container {
    min-height: 400px;
    max-height: 600px;
    overflow: auto;

    .upload {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100%;
    }
}

.dialog-footer {
    display: flex;
    align-items: center;
    justify-content: space-between;
}
</style>
