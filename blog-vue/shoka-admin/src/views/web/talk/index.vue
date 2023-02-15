<template>
    <div class="app-container">
        <!-- 操作 -->
        <el-row :gutter="10" class="mb15">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="Promotion" @click="openModel">发布说说</el-button>
            </el-col>
        </el-row>
        <!-- 说说状态 -->
        <el-row :gutter="24" style="color: #999;">
            <el-col :span="1.5">
                状态
            </el-col>
            <el-col :span="1.5" :class="isActive(undefined)" @click="changeStatus(undefined)">
                全部
            </el-col>
            <el-col :span="1.5" :class="isActive(1)" @click="changeStatus(1)">
                公开
            </el-col>
            <el-col :span="1.5" :class="isActive(2)" @click="changeStatus(2)">
                私密
            </el-col>
        </el-row>
        <el-empty v-if="talkList === null" description="暂无说说"></el-empty>
        <!-- 说说列表 -->
        <div class="talk-item" v-for="talk of talkList" :key="talk.id">
            <!-- 用户头像 -->
            <img class="user-avatar" :src="talk.avatar">
            <div class="talk-content-container">
                <!-- 用户信息 -->
                <div class="user-info">
                    <!-- 用户昵称 -->
                    <div class="user-name">{{ talk.nickname }}</div>
                    <!-- 操作 -->
                    <el-dropdown trigger="click" @command="handleOperation">
                        <el-icon style="color:#333">
                            <MoreFilled />
                        </el-icon>
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item :command="'update' + talk.id">编辑</el-dropdown-item>
                                <el-dropdown-item :command="'delete' + talk.id">删除</el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </div>
                <!-- 说说信息 -->
                <div class="talk-info">
                    <!-- 发布时间 -->
                    <span class="talk-time">{{ formatDateTime(talk.createTime) }}</span>
                    <span class="top" v-if="talk.isTop === 1"><svg-icon icon-class="top"
                            size="0.85rem"></svg-icon>置顶</span>
                    <span class="secret" v-if="talk.status === 2">
                        <el-icon>
                            <Hide />
                        </el-icon>
                        私密</span>
                </div>
                <!-- 说说内容 -->
                <div class="talk-content" v-html="talk.talkContent"></div>
                <!-- 说说图片 -->
                <el-row :gutter="4" style="margin-top: 0.5rem;">
                    <el-col :md="8" :cols="6" v-for="(img, index) of talk.imgList" :key="index">
                        <el-image class="talk-image" :src="img" :preview-src-list="previewList"></el-image>
                    </el-col>
                </el-row>
            </div>
        </div>
        <!-- 分页 -->
        <el-pagination v-if="count > 0" class="pagination-container" v-model:current-page="queryParams.current"
            v-model:page-size="queryParams.size" layout="prev, pager, next" :total="count"
            @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>
        <!-- 添加或修改对话框 -->
        <el-dialog :title="title" v-model="addOrUpdate" @close="close" width="750px" append-to-body>
            <el-form :model="talkForm">
                <el-form-item prop="talkContent">
                    <Editor ref="editorRef" v-model:text="talkForm.talkContent" placeholder="说点什么吧"></Editor>
                </el-form-item>
            </el-form>
            <el-row :gutter="10" align="middle" style="margin-top: 16px;">
                <el-col :span="1.5">
                    <!-- 表情 -->
                    <el-popover placement="bottom-start" :width="460" trigger="click">
                        <template #reference>
                            <span><svg-icon icon-class="emoji" size="1.4rem" color="#838383"></svg-icon></span>
                        </template>
                        <span class="emoji-item" v-for="(value, key, index) of emojiList" :key="index"
                            @click="addEmoji(key, value)">
                            <img :src="value" :title="key" class="emoji" width="24" height="24" />
                        </span>
                    </el-popover>
                </el-col>
                <el-col :span="1.5">
                    <!-- 图片上传 -->
                    <el-upload accept="image/*" multiple action="/api/admin/talk/upload" :headers="authorization"
                        :before-upload="beforeUpload" :on-success="handleSuccess" :show-file-list="false">
                        <svg-icon icon-class="album" size="1.5rem" color="#838383"
                            style="padding-top:0.1rem;"></svg-icon>
                    </el-upload>
                </el-col>
                <el-col :span="1.5" :offset="14">
                    <el-switch style="margin-right:7px" v-model="talkForm.isTop" inactive-text="置顶" :active-value="1"
                        :inactive-value="0" />
                </el-col>
                <el-col :span="1.5">
                    <el-dropdown trigger="click" @command="handleCommand">
                        <span class="el-dropdown-link">
                            {{ statusTitle }}
                            <el-icon class="el-icon--right">
                                <arrow-down />
                            </el-icon>
                        </span>
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item v-for="(item, index) of statusList" :key="index"
                                    :command="item.value">
                                    {{ item.label }}
                                </el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </el-col>
                <el-col :span="1.5">
                    <el-button type="primary" :disabled="talkForm.talkContent === ''" @click="submitForm">发布</el-button>
                </el-col>
            </el-row>
            <!-- 图片上传 -->
            <el-upload accept="image/*" v-show="uploadList.length > 0" action="/api/admin/talk/upload"
                :headers="authorization" list-type="picture-card" :file-list="uploadList" multiple
                :before-upload="beforeUpload" :on-success="handleSuccess" :on-remove="handleRemove"
                :on-preview="handlePictureCardPreview" style="margin-top:1rem;">
                <el-icon>
                    <Plus />
                </el-icon>
            </el-upload>
        </el-dialog>
        <!-- 图片预览 -->
        <el-dialog v-model="dialogVisible" append-to-body>
            <img :src="dialogImageUrl" style="max-width:100%" />
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { addTalk, deleteTalk, editTalk, getTalkList, updateTalk } from "@/api/talk";
import { Talk, TalkForm, TalkQuery } from "@/api/talk/types";
import Editor from "@/components/Editor/index.vue";
import { Picture } from "@/model";
import { formatDateTime } from "@/utils/date";
import emojiList from "@/utils/emoji";
import { messageConfirm, notifySuccess } from "@/utils/modal";
import { getToken, token_prefix } from "@/utils/token";
import { AxiosResponse } from "axios";
import { UploadFile, UploadRawFile } from 'element-plus';
import * as imageConversion from 'image-conversion';
import { computed, onMounted, reactive, ref, toRefs } from 'vue';
const editorRef = ref();
const dialogImageUrl = ref('');
const dialogVisible = ref(false);
const isActive = computed(() => {
    return function (value: number | undefined) {
        return queryParams.value.status == value ? "active-status" : "status";
    }
});
const authorization = computed(() => {
    return {
        Authorization: token_prefix + getToken(),
    }
});
const statusTitle = computed(() => {
    let label = "公开";
    statusList.value.forEach(item => {
        if (item.value == talkForm.value.status) {
            label = item.label;
        }
    })
    return label;
})
const data = reactive({
    count: 0,
    title: "",
    addOrUpdate: false,
    queryParams: {
        current: 1,
        size: 5,
        status: undefined,
    } as TalkQuery,
    statusList: [
        { value: 1, label: "公开" },
        { value: 2, label: "私密" }
    ],
    previewList: [] as string[],
    talkForm: {
        id: undefined,
        talkContent: "",
        images: "",
        isTop: 0,
        status: 1,
    } as TalkForm,
    talkList: [] as Talk[],
    uploadList: [] as Picture[],
});
const {
    count,
    title,
    addOrUpdate,
    queryParams,
    statusList,
    previewList,
    talkForm,
    talkList,
    uploadList,
} = toRefs(data);
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
const handleOperation = (command: string) => {
    const type = command.substring(0, 6);
    talkForm.value.id = Number(command.substring(6));
    if (type === "delete") {
        messageConfirm("确认删除已选中的数据项?").then(() => {
            deleteTalk(talkForm.value.id).then(({ data }) => {
                if (data.flag) {
                    notifySuccess(data.msg);
                    getList();
                }
            });
        }).catch(() => { });
    } else {
        editTalk(talkForm.value.id).then(({ data }) => {
            if (data.flag) {
                talkForm.value = data.data;
                if (data.data.imgList) {
                    data.data.imgList.forEach((item) => {
                        uploadList.value.push({ url: item });
                    });
                }
                title.value = "修改说说";
                addOrUpdate.value = true;
            }
        })
    }
};
const handleCommand = (command: number) => {
    talkForm.value.status = command;
};
const addEmoji = (key: string, value: string) => {
    editorRef.value.addText("<img src= '" +
        value +
        "' width='24'height='24' alt=" +
        key +
        " style='margin: 0 1px;vertical-align: text-bottom'/>");
};
const changeStatus = (value: number | undefined) => {
    queryParams.value.current = 1;
    previewList.value = [];
    queryParams.value.status = value;
    getList();
};
const handleSizeChange = (size: number) => {
    previewList.value = [];
    queryParams.value.size = size;
    getList();
};
const handleCurrentChange = (current: number) => {
    previewList.value = [];
    queryParams.value.current = current;
    getList();
};
const submitForm = () => {
    // 转换图片
    if (uploadList.value.length > 0) {
        let imgList: string[] = [];
        uploadList.value.forEach((item) => {
            imgList.push(item.url);
        });
        talkForm.value.images = JSON.stringify(imgList);
    } else {
        talkForm.value.images = "";
    }
    if (talkForm.value.id !== undefined) {
        updateTalk(talkForm.value).then(({ data }) => {
            if (data.flag) {
                uploadList.value = [];
                notifySuccess(data.msg);
                getList();
            }
            addOrUpdate.value = false;
        })
    } else {
        addTalk(talkForm.value).then(({ data }) => {
            if (data.flag) {
                notifySuccess(data.msg);
                getList();
            }
            addOrUpdate.value = false;
        });
    }
};
const reset = () => {
    talkForm.value = {
        id: undefined,
        talkContent: "",
        images: "",
        isTop: 0,
        status: 1,
    };
    uploadList.value = [];
    editorRef.value.clear();
};
const close = () => {
    reset();
    addOrUpdate.value = false;
};
const openModel = () => {
    talkForm.value = {
        id: undefined,
        talkContent: "",
        images: "",
        isTop: 0,
        status: 1,
    };
    title.value = "发布说说";
    addOrUpdate.value = true;
};
const getList = () => {
    getTalkList(queryParams.value).then(({ data }) => {
        talkList.value = data.data.recordList;
        if (talkList.value) {
            talkList.value!.forEach((item) => {
                if (item.imgList) {
                    previewList.value.push(...item.imgList);
                }
            });
        }
        count.value = data.data.count;
    });
};
onMounted(() => {
    getList();
});
</script>

<style lang="scss" scoped>
.el-dropdown-link {
    display: flex;
    align-items: center;
    margin-top: 0.1rem;
    cursor: pointer;
}

.status {
    cursor: pointer;
}

.active-status {
    cursor: pointer;
    color: #333;
    font-weight: bold;
}

.talk-item {
    display: flex;
    padding: 1rem 1.25rem;
    border-radius: 0.5rem;
    box-shadow: 0 0.625rem 1.875rem -0.9375rem rgba(0, 0, 0, 0.1);
    transition: all 0.2s ease-in-out 0s;

    &:hover {
        box-shadow: 0 0 2rem rgba(0, 0, 0, 0.1);
    }

    &:not(:first-child) {
        margin-top: 1.25rem;
    }
}

.user-avatar {
    width: 2.25rem;
    height: 2.25rem;
    border-radius: 50%;
    transition: all 0.5s;

    &:hover {
        transform: rotate(360deg)
    }
}

.talk-content-container {
    flex: auto;
    margin-left: 0.825rem;
}

.user-info {
    display: flex;
    align-items: center;
    justify-content: space-between;

    .user-name {
        font-size: 0.875rem;
        font-weight: 600;
    }
}

.talk-info {
    display: flex;
    align-items: center;
    margin-top: 0.2rem;
    line-height: 1.5rem;

    .talk-time {
        font-size: 0.8125rem;
        margin-right: 1.25rem;
        color: #9499a0;
    }

    .top {
        font-size: 0.8125rem;
        color: #ff7242;
    }

    .secret {
        color: #999;
        margin-left: 10px;
    }
}

.talk-content {
    margin-top: 0.5rem;
    font-size: 0.875rem;
    line-height: 1.5rem;
    word-wrap: break-word;
    word-break: break-all;
    white-space: pre-line;
}

.talk-image {
    width: 100%;
    height: 200px;
    padding: 0.125rem;
    border-radius: 10px;
    cursor: pointer;
    object-fit: cover;
}

.emoji-item {
    cursor: pointer;
    display: inline-block;

    .emoji {
        user-select: none;
        margin: 0.25rem;
        display: inline-block;
        vertical-align: middle;
    }

    &:hover {
        transition: all 0.2s;
        border-radius: 0.25rem;
        background: #dddddd;
    }
}
</style>