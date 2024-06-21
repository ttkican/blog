<template>
    <div class="app-container">
        <!-- 文章标题 -->
        <div class="operation-container">
            <el-input v-model="articleForm.articleTitle" placeholder="请输入文章标题"></el-input>
            <el-button type="danger" style="margin-left: 10px" @click="openModel">发布文章</el-button>
        </div>
        <!-- 文章内容 -->
        <md-editor ref="editorRef" v-model="articleForm.articleContent" :theme="isDark ? 'dark' : 'light'"
            class="md-container" :toolbars="toolbars" @on-upload-img="uploadImg" placeholder="请输入文章内容...">
            <template #defToolbars>
                <emoji-extension :on-insert="insert" />
            </template>
        </md-editor>
        <!-- 发布或修改对话框 -->
        <el-dialog title="发布文章" v-model="addOrUpdate" width="600px" top="0.5vh" append-to-body>
            <el-form ref="articleFormRef" label-width="80px" :model="articleForm" :rules="rules">
                <!-- 文章分类 -->
                <el-form-item label="文章分类" prop="categoryName">
                    <el-tag type="success" v-show="articleForm.categoryName" :disable-transitions="true"
                        :closable="true" @close="removeCategory">
                        {{ articleForm.categoryName }}
                    </el-tag>
                    <!-- 分类选项 -->
                    <el-popover v-if="!articleForm.categoryName" placement="bottom-start" width="460" trigger="click">
                        <template #reference>
                            <el-button type="success" plain>添加分类</el-button>
                        </template>
                        <div class="popover-title">分类</div>
                        <!-- 搜索框 -->
                        <el-autocomplete style="width: 100%" v-model="categoryName" :fetch-suggestions="searchCategory"
                            placeholder="请输入分类名搜索,enter可添加自定义分类" :trigger-on-focus="false" @keyup.enter="saveCategory"
                            @select="handleSelectCategory">
                            <template #default="{ item }">
                                <div>{{ item.categoryName }}</div>
                            </template>
                        </el-autocomplete>
                        <!-- 分类 -->
                        <div class="popover-container">
                            <div v-for="item of categoryList" :key="item.id" class="category-item"
                                @click="addCategory(item.categoryName)">
                                {{ item.categoryName }}
                            </div>
                        </div>
                    </el-popover>
                </el-form-item>
                <!-- 文章标签 -->
                <el-form-item label="文章标签" prop="tagNameList">
                    <el-tag v-for="(item, index) of articleForm.tagNameList" :key="index" :disable-transitions="true"
                        :closable="true" @close="removeTag(item)" style="margin-right: 1rem;">
                        {{ item }}
                    </el-tag>
                    <!-- 标签选项 -->
                    <el-popover placement="bottom-start" width="460" trigger="click"
                        v-if="articleForm.tagNameList.length < 3">
                        <template #reference>
                            <el-button type="success" plain>添加标签</el-button>
                        </template>
                        <div class="popover-title">标签</div>
                        <!-- 搜索框 -->
                        <el-autocomplete style="width: 100%" v-model="tagName" :fetch-suggestions="searchTag"
                            placeholder="请输入标签名搜索,enter可添加自定义标签" :trigger-on-focus="false" @keyup.enter="saveTag"
                            @select="handleSelectTag">
                            <template #default="{ item }">
                                <div>{{ item.tagName }}</div>
                            </template>
                        </el-autocomplete>
                        <!-- 标签 -->
                        <div class="popover-container">
                            <div style="margin-bottom: 1rem">添加标签</div>
                            <el-tag v-for="(item, index) of tagList" :key="index" :class="tagClass(item.tagName)"
                                @click="addTag(item.tagName)" style="margin-right: 1rem;">
                                {{ item.tagName }}
                            </el-tag>
                        </div>
                    </el-popover>
                </el-form-item>
                <!-- 文章类型 -->
                <el-form-item label="文章类型" prop="articleType">
                    <el-select v-model="articleForm.articleType" placeholder="请选择类型">
                        <el-option v-for="item in typeList" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <!-- 缩略图 -->
                <el-form-item label="缩略图" prop="articleCover">
                    <el-upload drag :show-file-list="false" :headers="authorization" action="/api/admin/article/upload"
                        accept="image/*" :before-upload="beforeUpload" :on-success="handleSuccess">
                        <el-icon class="el-icon--upload"
                            v-if="articleForm.articleCover === ''"><upload-filled /></el-icon>
                        <div class="el-upload__text" v-if="articleForm.articleCover === ''">
                            将文件拖到此处，或<em>点击上传</em>
                        </div>
                        <img v-else :src="articleForm.articleCover" width="360" />
                    </el-upload>
                </el-form-item>
                <!-- 置顶 -->
                <el-form-item label="置顶" prop="isTop">
                    <el-switch v-model="articleForm.isTop" :active-value="1" :inactive-value="0"></el-switch>
                </el-form-item>
                <!-- 推荐 -->
                <el-form-item label="推荐" prop="isRecommend">
                    <el-switch v-model="articleForm.isRecommend" :active-value="1" :inactive-value="0"></el-switch>
                </el-form-item>
                <!-- 发布形式 -->
                <el-form-item label="发布形式" prop="status">
                    <el-radio-group v-model="articleForm.status">
                        <el-radio :label="1">公开</el-radio>
                        <el-radio :label="2">私密</el-radio>
                        <el-radio :label="3">草稿</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="文章摘要" prop="articleDesc">
                    <el-input v-model="articleForm.articleDesc" resize="none" maxlength="100"
                        :autosize="{ minRows: 5, maxRows: 5 }" style="width: 300px" show-word-limit type="textarea" />
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button v-if="articleForm.status != 3" type="danger" @click="submitForm">发布文章</el-button>
                    <el-button v-else type="danger" @click="submitForm">保存草稿</el-button>
                    <el-button @click="addOrUpdate = false">取 消</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { addArticle, editArticle, getCategoryOption, getTagOption, updateArticle, uploadArticleCover } from "@/api/article";
import { ArticleForm, CategoryVO, TagVO } from "@/api/article/types";
import EmojiExtension from '@/components/EmojiExtension/index.vue';
import { toolbars } from '@/components/EmojiExtension/staticConfig';
import router from "@/router";
import useStore from "@/store";
import { notifySuccess } from "@/utils/modal";
import { getToken, token_prefix } from '@/utils/token';
import { useDark, useDateFormat } from '@vueuse/core';
import { AxiosError, AxiosResponse } from 'axios';
import { ElMessage, FormInstance, FormRules, UploadRawFile } from 'element-plus';
import * as imageConversion from 'image-conversion';
import type { ExposeParam, InsertContentGenerator } from 'md-editor-v3';
import MdEditor from "md-editor-v3";
import "md-editor-v3/lib/style.css";
import { computed, onMounted, reactive, ref, toRefs } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const articleId = route.params.articleId;
const editorRef = ref<ExposeParam>();
const articleFormRef = ref<FormInstance>();
const articleTitle = ref(useDateFormat(new Date(), "YYYY-MM-DD"));
const { tag } = useStore();
const rules = reactive<FormRules>({
    categoryName: [{ required: true, message: "文章分类不能为空", trigger: "blur" }],
    tagNameList: [{ required: true, message: "文章标签不能为空", trigger: "blur" }],
    articleDesc: [{ required: true, message: "文章概要不能为空", trigger: "blur" }],
});
const authorization = computed(() => {
    return {
        Authorization: token_prefix + getToken(),
    }
});
const isDark = useDark();
const tagClass = computed(() => {
    return function (item: string) {
        const index = articleForm.value.tagNameList.indexOf(item);
        return index !== -1 ? "tag-item-select" : "tag-item";
    };
});

const initArticle = {
    id: undefined,
    articleCover: "",
    articleTitle: articleTitle.value,
    articleContent: "",
    articleDesc: "",
    categoryName: "",
    tagNameList: [],
    articleType: 1,
    isTop: 0,
    isRecommend: 0,
    status: 1,
} as ArticleForm;
const data = reactive({
    addOrUpdate: false,
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
    articleForm: initArticle,
    categoryList: [] as CategoryVO[],
    tagList: [] as TagVO[],
    categoryName: "",
    tagName: "",
});
const {
    addOrUpdate,
    typeList,
    articleForm,
    categoryList,
    tagList,
    categoryName,
    tagName,
} = toRefs(data);
const uploadImg = async (files: Array<File>, callback: (urls: string[]) => void) => {
    const res = await Promise.all(
        files.map((file) => {
            return new Promise((rev, rej) => {
                const form = new FormData();
                form.append('file', file);
                uploadArticleCover(form).then(({ data }) => {
                    if (data.flag) {
                        rev(data);
                    }
                }).catch((error: AxiosError) => rej(error));
            });
        })
    );
    callback(res.map((item: any) => item.data));
};
const openModel = () => {
    if (articleForm.value.articleTitle.trim() == "") {
        ElMessage.error("文章标题不能为空");
        return false;
    }
    if (articleForm.value.articleContent.trim() == "") {
        ElMessage.error("文章内容不能为空");
        return false;
    }
    articleFormRef.value?.clearValidate();
    getCategoryOption().then(({ data }) => {
        categoryList.value = data.data;
    });
    getTagOption().then(({ data }) => {
        tagList.value = data.data;
    });
    if (articleForm.value.articleDesc === "") {
        articleForm.value.articleDesc = removeSpecialChars(articleForm.value.articleContent);
    }
    addOrUpdate.value = true;
};
const removeSpecialChars = (str: string) => {
    // 移除所有HTML标签
    var cleanedStr = str.replace(/<[^>]*>?/g, "");
    // 移除所有空格、星号、井号、[]、换行、制表符
    cleanedStr = cleanedStr.replace(/[\s\*#\[\]]/g, "");
    // 移除所有()和其中内容
    cleanedStr = cleanedStr.replace(/\([^\)]*\)/g, "");
    // 移除所有换行、制表符
    cleanedStr = cleanedStr.replace(/[\n\t]+/g, "");
    return cleanedStr.substring(0, 100);
}

const removeTag = (item: string) => {
    const index = articleForm.value.tagNameList.indexOf(item);
    articleForm.value.tagNameList.splice(index, 1);
};
const handleSelectTag = (item: TagVO) => {
    addTag(item.tagName);
};
const saveTag = () => {
    if (tagName.value.trim() != "") {
        addTag(tagName.value);
        tagName.value = "";
    }
};
const addTag = (item: string) => {
    if (articleForm.value.tagNameList.indexOf(item) == -1) {
        articleForm.value.tagNameList.push(item);
    }
};
const searchTag = (keyword: string, cb: (arg: TagVO[]) => void) => {
    const results = keyword
        ? tagList.value.filter(createTagFilter(keyword))
        : tagList.value
    cb(results);
};
const createTagFilter = (queryString: string) => {
    return (restaurant: TagVO) => restaurant.tagName.indexOf(queryString) !== -1;
};
const removeCategory = () => {
    articleForm.value.categoryName = "";
};
const handleSelectCategory = (item: CategoryVO) => {
    addCategory(item.categoryName);
};
const saveCategory = () => {
    // 分类不为空
    if (categoryName.value.trim() != "") {
        addCategory(categoryName.value);
        categoryName.value = "";
    }
};
const addCategory = (item: string) => {
    articleForm.value.categoryName = item;
};
const searchCategory = (keyword: string, cb: (arg: CategoryVO[]) => void) => {
    const results = keyword
        ? categoryList.value.filter(createCategoryFilter(keyword))
        : categoryList.value
    cb(results);
};
const createCategoryFilter = (queryString: string) => {
    return (restaurant: CategoryVO) => {
        return (
            restaurant.categoryName.indexOf(queryString) !== -1
        )
    }
};
const insert = (generator: InsertContentGenerator) => {
    editorRef.value?.insert(generator);
};
const handleSuccess = (response: AxiosResponse) => {
    articleForm.value.articleCover = response.data;
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
const submitForm = () => {
    articleFormRef.value?.validate((valid) => {
        if (!valid) {
            return;
        }
        if (articleForm.value.id !== undefined) {
            updateArticle(articleForm.value).then(({ data }) => {
                if (data.flag) {
                    notifySuccess(data.msg);
                    tag.delView({ path: `/article/write/${articleForm.value.id}` });
                    router.push({ path: "/article/list" });
                    articleForm.value = initArticle;
                }
                addOrUpdate.value = false;
            });
        } else {
            addArticle(articleForm.value).then(({ data }) => {
                if (data.flag) {
                    notifySuccess(data.msg);
                    tag.delView({ path: "/article/write" });
                    router.push({ path: "/article/list" });
                    articleForm.value = initArticle;
                }
                addOrUpdate.value = false;
            });
        }
    })
};
onMounted(() => {
    if (articleId) {
        console.log("sadfasdfas", articleId);

        editArticle(Number(articleId)).then(({ data }) => {
            if (data.flag) {
                articleForm.value = data.data;
            } else {
                tag.delView({ path: `/article/write/${articleId}` });
                router.push({ path: "/article/list" });
            }
        });
    }
});
</script>

<style scoped>
.operation-container {
    display: flex;
    align-items: center;
    margin-bottom: 1.25rem;
}

.md-container {
    min-height: 300px;
    height: calc(100vh - 200px);
}

.popover-title {
    margin-bottom: 1rem;
    text-align: center;
}

.popover-container {
    margin-top: 1rem;
    height: 260px;
    overflow-y: auto;
}

.category-item {
    cursor: pointer;
    padding: 0.6rem 0.5rem;
}

.category-item:hover {
    background-color: #f0f9eb;
    color: #67c23a;
}

.tag-item {
    margin-right: 1rem;
    margin-bottom: 1rem;
    cursor: pointer;
}

.tag-item-select {
    margin-right: 1rem;
    margin-bottom: 1rem;
    cursor: not-allowed;
    color: #ccccd8 !important;
}
</style>