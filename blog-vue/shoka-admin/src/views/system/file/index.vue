<template>
    <div class="app-container">
        <!-- 操作按钮 -->
        <el-row :gutter="10" class="mb15">
            <el-col :span="1.5">
                <el-upload action="" multiple :show-file-list="false" :http-request="handleUpload"
                    :before-upload="beforeUpload" accept="image/*">
                    <el-button type="primary" plain icon="Upload">上传</el-button>
                </el-upload>
            </el-col>
            <el-col :span="1.5">
                <el-button type="primary" plain icon="Plus" @click="handleNew">新建文件夹</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="Delete" :disabled="fileIdList.length === 0"
                    @click="handleDelete(undefined)">批量删除</el-button>
            </el-col>
            <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <!-- 面包屑 -->
        <div class="breadcrumb-container">
            <div class="position">当前位置：</div>
            <el-breadcrumb class="breadcrumb" separator-icon="ArrowRight">
                <el-breadcrumb-item v-for="(item, index) in breadcrumbList" :key="index"
                    :to="{ query: { filePath: item.path } }">{{
                        item.name }}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <!-- 表格展示 -->
        <el-table :data="fileList" @selection-change="handleSelectionChange" v-loading="loading">
            <!-- 表格列 -->
            <el-table-column type="selection" align="center" width="55"></el-table-column>
            <el-table-column prop="isDir" width="56">
                <template #default="scope">
                    <img v-if="scope.row.isDir === 1" src="@/assets/dir.png" @click="handleOpen(scope.row)"
                        style="width: 30px; cursor: pointer" />
                    <el-image v-else v-viewer class="article-cover" :src="scope.row.fileUrl"></el-image>
                </template>
            </el-table-column>
            <!-- 文件名 -->
            <el-table-column prop="fileName" sortable label="文件名">
                <template #default="scope">
                    <span style="cursor: pointer" @click="handleOpen(scope.row)">
                        {{ scope.row.fileName }}
                    </span>
                </template>
            </el-table-column>
            <!-- 类型 -->
            <el-table-column prop="extendName" sortable label="类型" align="center" width="160">
                <template #default="scope">
                    {{ getFileType(scope.row) }}
                </template>
            </el-table-column>
            <!-- 大小 -->
            <el-table-column prop="fileSize" sortable label="大小" align="center" width="150">
                <template #default="scope">
                    {{ scope.row.isDir === 0 ? calculateFileSize(scope.row.fileSize) : '' }}
                </template>
            </el-table-column>
            <!-- 创建日期 -->
            <el-table-column prop="createTime" sortable label="创建日期" align="center" width="180">
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
                    <el-button type="primary" icon="View" link>
                        <template #default>
                            <a :href="`/api/file/download/${scope.row.id}`">下载</a>
                        </template>
                    </el-button>
                    <el-button type="danger" icon="Delete" link @click="handleDelete(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <pagination v-if="count > 0" :total="count" v-model:page="queryParams.current" v-model:limit="queryParams.size"
            @pagination="getList" />
        <!-- 新建文件夹对话框 -->
        <el-dialog title="新建文件夹" v-model="add" width="500px" append-to-body>
            <el-form ref="folderFormRef" label-width="100px" :model="folderForm" :rules="rules" @submit.native.prevent>
                <el-form-item label="文件夹名称" prop="fileName">
                    <el-input placeholder="请输入文件夹名称" v-model="folderForm.fileName" style="width: 250px;" />
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="primary" @click="submitForm">确 定</el-button>
                    <el-button @click="add = false">取 消</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { createFolder, deleteFile, getFileList, uploadFile } from '@/api/file';
import { BlogFile, FileQuery, FolderForm } from '@/api/file/types';
import router from '@/router';
import { formatDateTime } from '@/utils/date';
import { messageConfirm, notifySuccess } from '@/utils/modal';
import { FormInstance, FormRules, UploadRawFile, UploadRequestOptions } from 'element-plus';
import * as imageConversion from 'image-conversion';
import { computed, onMounted, reactive, ref, toRefs, watch } from 'vue';
import { useRoute } from "vue-router";

const folderFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
    fileName: [{ required: true, message: "请输入文件夹名称", trigger: "blur" }],
});
const route = useRoute();
const getFileType = computed(() => (file: BlogFile) => {
    return file.isDir === 1 ? '文件夹' : file.extendName ? file.extendName : '文件';
});
const filePath = computed(() => {
    let filePath = route.query.filePath as string;
    return filePath ? filePath : "/";
});
const breadcrumbList = computed(() => {
    let filePath = route.query.filePath as string;
    let filePathList = filePath ? filePath.split("/") : [];
    let res = [];
    let _path = [];
    for (let i = 0; i < filePathList.length; i++) {
        if (filePathList[i]) {
            _path.push(filePathList[i])
            res.push({
                path: _path.join("/"),
                name: filePathList[i],
            })
        } else if (i === 0) {
            filePathList[i] = "";
            _path.push(filePathList[i]);
            res.push({
                path: "/",
                name: "全部文件"
            })
        }
    }
    return res;
});
const data = reactive({
    count: 0,
    loading: false,
    showSearch: true,
    add: false,
    fileIdList: [] as number[],
    folderForm: {} as FolderForm,
    queryParams: {
        current: 1,
        size: 10,
        filePath: filePath.value,
    } as FileQuery,
    fileList: [] as BlogFile[],
});
const {
    count,
    loading,
    showSearch,
    add,
    fileIdList,
    folderForm,
    queryParams,
    fileList,
} = toRefs(data);
watch(() => queryParams.value.filePath, () => {
    getList();
});
const handleNew = () => {
    add.value = true;
    folderFormRef.value?.clearValidate();
    folderForm.value = {
        fileName: "",
        filePath: "",
    };
};
const handleDownLoad = (fileId: number) => {

};
const submitForm = () => {
    folderFormRef.value?.validate((valid) => {
        if (valid) {
            folderForm.value.filePath = filePath.value;
            createFolder(folderForm.value).then(({ data }) => {
                if (data.flag) {
                    notifySuccess(data.msg);
                    getList();
                }
                add.value = false;
            })
        }
    })
};
const handleSelectionChange = (selection: BlogFile[]) => {
    fileIdList.value = selection.map((item) => item.id);
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
const handleUpload = (options: UploadRequestOptions) => {
    let formData = new FormData();
    formData.append("file", options.file);
    formData.append("path", filePath.value);
    uploadFile(formData).then(({ data }) => {
        if (data.flag) {
            notifySuccess(data.msg);
            getList();
        }
    })
};
const handleDelete = (id?: number) => {
    let ids: number[] = [];
    if (id == undefined) {
        ids = fileIdList.value;
    } else {
        ids = [id];
    }
    messageConfirm("确认删除已选中的数据项?").then(() => {
        deleteFile(ids).then(({ data }) => {
            if (data.flag) {
                notifySuccess(data.msg);
                getList();
            }
        });
    }).catch(() => { });
};
const calculateFileSize = (size: number, isInteger = false) => {
    const B = 1024
    const KB = Math.pow(1024, 2)
    const MB = Math.pow(1024, 3)
    const GB = Math.pow(1024, 4)
    if (isInteger) {
        // 截取为整数
        if (size < B) {
            return `${size}B`
        } else if (size < KB) {
            return `${(size / B).toFixed(0)}KB`
        } else if (size < MB) {
            return `${(size / KB).toFixed(0)}MB`
        } else if (size < GB) {
            return `${(size / MB).toFixed(0)}GB`
        } else {
            return `${(size / GB).toFixed(0)}TB`
        }
    } else {
        // 保留小数位
        if (size < B) {
            return `${size}B`
        } else if (size < KB) {
            return `${(size / B).toFixed(0)}KB`
        } else if (size < MB) {
            return `${(size / KB).toFixed(1)}MB`
        } else if (size < GB) {
            return `${(size / MB).toFixed(2)}GB`
        } else {
            return `${(size / GB).toFixed(3)}TB`
        }
    }
};
const handleOpen = (row: BlogFile) => {
    router.push({
        query: {
            filePath: `${row.filePath === '/' ? '' : row.filePath}/${row.fileName}`,
        }
    })
};
const getList = () => {
    loading.value = true;
    getFileList(queryParams.value).then(({ data }) => {
        fileList.value = data.data.recordList;
        count.value = data.data.count;
        loading.value = false;
    });
};
onMounted(() => {
    getList();
});
</script>

<style scoped>
.article-cover {
    position: relative;
    width: 100%;
    border-radius: 4px;
}

.position {
    height: 30px;
    line-height: 30px;
}

.breadcrumb-container {
    display: flex;
    height: 30px;
    line-height: 30px;
}

.breadcrumb {
    display: flex;
    flex: auto;
    padding-left: 8px;
}
</style>