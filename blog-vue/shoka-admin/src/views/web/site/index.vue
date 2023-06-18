<template>
    <div class="app-container">
        <el-tabs type="border-card" class="demo-tabs">
            <!-- 网站信息 -->
            <el-tab-pane>
                <template #label>
                    <span class="custom-tabs-label">
                        <el-icon>
                            <Platform />
                        </el-icon>
                        <span>网站信息</span>
                    </span>
                </template>
                <el-form label-width="80px" :model="siteConfig" label-position="left">
                    <el-row>
                        <el-col :md="6">
                            <el-form-item label="用户头像">
                                <el-upload class="avatar-uploader" :headers="authorization" action="/api/admin/site/upload"
                                    :show-file-list="false" accept="image/*" :before-upload="beforeUpload"
                                    :on-success="handleUserAvatarSuccess">
                                    <img v-if="siteConfig.userAvatar" :src="siteConfig.userAvatar" class="avatar" />
                                    <el-icon v-else class="avatar-uploader-icon">
                                        <Plus />
                                    </el-icon>
                                </el-upload>
                            </el-form-item>
                        </el-col>
                        <el-col :md="6">
                            <el-form-item label="游客头像">
                                <el-upload class="avatar-uploader" :headers="authorization" action="/api/admin/site/upload"
                                    :show-file-list="false" accept="image/*" :before-upload="beforeUpload"
                                    :on-success="handleTouristAvatarSuccess">
                                    <img v-if="siteConfig.touristAvatar" :src="siteConfig.touristAvatar" class="avatar" />
                                    <el-icon v-else class="avatar-uploader-icon">
                                        <Plus />
                                    </el-icon>
                                </el-upload>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-form-item label="网站名称">
                        <el-input v-model="siteConfig.siteName" style="width: 400px;"></el-input>
                    </el-form-item>
                    <el-form-item label="网站地址">
                        <el-input v-model="siteConfig.siteAddress" style="width: 400px;"></el-input>
                    </el-form-item>
                    <el-form-item label="网站简介">
                        <el-input v-model="siteConfig.siteIntro" style="width: 400px;"></el-input>
                    </el-form-item>
                    <el-form-item label="网站公告">
                        <el-input style="width: 400px;" v-model="siteConfig.siteNotice"
                            :autosize="{ minRows: 4, maxRows: 5 }" resize="none" type="textarea"></el-input>
                    </el-form-item>
                    <el-form-item label="建站日期">
                        <el-date-picker v-model="siteConfig.createSiteTime" value-format="YYYY-MM-DD" type="date"
                            placeholder="选择日期"></el-date-picker>
                    </el-form-item>
                    <el-form-item label="备案号">
                        <el-input v-model="siteConfig.recordNumber" style="width: 400px;"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="handleUpdate">保 存</el-button>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
            <!-- 作者信息 -->
            <el-tab-pane label="author">
                <template #label>
                    <span class="custom-tabs-label">
                        <el-icon>
                            <Flag />
                        </el-icon>
                        <span>作者信息</span>
                    </span>
                </template>
                <el-form label-width="80px" :model="siteConfig" label-position="left">
                    <el-form-item label="作者头像">
                        <el-upload class="avatar-uploader" :headers="authorization" action="/api/admin/site/upload"
                            :show-file-list="false" accept="image/*" :before-upload="beforeUpload"
                            :on-success="handleAuthorAvatarSuccess">
                            <img v-if="siteConfig.authorAvatar" :src="siteConfig.authorAvatar" class="avatar" />
                            <el-icon v-else class="avatar-uploader-icon">
                                <Plus />
                            </el-icon>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="网站作者">
                        <el-input v-model="siteConfig.siteAuthor" style="width: 400px;"></el-input>
                    </el-form-item>
                    <el-form-item label="关于我">
                        <v-md-editor v-model="siteConfig.aboutMe" :disabled-menus="[]" :left-toolbar="toolList"
                            @upload-image="handleUploadImage" height="400px" />
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="handleUpdate">保 存</el-button>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
            <!-- 社交信息 -->
            <el-tab-pane label="social">
                <template #label>
                    <span class="custom-tabs-label">
                        <el-icon>
                            <Opportunity />
                        </el-icon>
                        <span>社交信息</span>
                    </span>
                </template>
                <el-form label-width="70px" :model="siteConfig" label-position="left">
                    <el-checkbox-group v-model="socialList">
                        <el-form-item label="Github">
                            <el-input v-model="siteConfig.github" style="width: 400px; margin-right: 1rem"></el-input>
                            <el-checkbox label="github">是否展示</el-checkbox>
                        </el-form-item>
                        <el-form-item label="Gitee">
                            <el-input v-model="siteConfig.gitee" style="width: 400px; margin-right: 1rem"></el-input>
                            <el-checkbox label="gitee">是否展示</el-checkbox>
                        </el-form-item>
                        <el-form-item label="BiliBili">
                            <el-input v-model="siteConfig.bilibili" style="width: 400px; margin-right: 1rem"></el-input>
                            <el-checkbox label="bilibili">是否展示</el-checkbox>
                        </el-form-item>
                        <el-form-item label="QQ">
                            <el-input v-model="siteConfig.qq" style="width: 400px; margin-right: 1rem"></el-input>
                            <el-checkbox label="qq">是否展示</el-checkbox>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="handleUpdate">保 存</el-button>
                        </el-form-item>
                    </el-checkbox-group>
                </el-form>
            </el-tab-pane>
            <!-- 审核&打赏 -->
            <el-tab-pane label="check">
                <template #label>
                    <span class="custom-tabs-label">
                        <el-icon>
                            <Stamp />
                        </el-icon>
                        <span>审核&打赏</span>
                    </span>
                </template>
                <el-form label-width="100px" :model="siteConfig" label-position="left">
                    <el-form-item label="评论审核">
                        <el-radio-group v-model="siteConfig.commentCheck">
                            <el-radio :label="0">关闭</el-radio>
                            <el-radio :label="1">开启</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="留言审核">
                        <el-radio-group v-model="siteConfig.messageCheck">
                            <el-radio :label="0">关闭</el-radio>
                            <el-radio :label="1">开启</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="打赏状态">
                        <el-radio-group v-model="siteConfig.isReward">
                            <el-radio :label="0">关闭</el-radio>
                            <el-radio :label="1">开启</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-row style="width: 600px" v-if="siteConfig.isReward == 1">
                        <el-col :md="12">
                            <el-form-item label="微信收款码">
                                <el-upload class="avatar-uploader" :headers="authorization" action="/api/admin/site/upload"
                                    :show-file-list="false" accept="image/*" :before-upload="beforeUpload"
                                    :on-success="handleWeiXinSuccess">
                                    <img v-if="siteConfig.weiXinCode" :src="siteConfig.weiXinCode" class="avatar" />
                                    <el-icon v-else class="avatar-uploader-icon">
                                        <Plus />
                                    </el-icon>
                                </el-upload>
                            </el-form-item>
                        </el-col>
                        <el-col :md="12">
                            <el-form-item label="支付宝收款码">
                                <el-upload class="avatar-uploader" :headers="authorization" action="/api/admin/site/upload"
                                    :show-file-list="false" accept="image/*" :before-upload="beforeUpload"
                                    :on-success="handleAliSuccess">
                                    <img v-if="siteConfig.aliCode" :src="siteConfig.aliCode" class="avatar" />
                                    <el-icon v-else class="avatar-uploader-icon">
                                        <Plus />
                                    </el-icon>
                                </el-upload>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-form-item>
                        <el-button type="primary" @click="handleUpdate">保 存</el-button>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
            <!-- 其他设置 -->
            <el-tab-pane label="other">
                <template #label>
                    <span class="custom-tabs-label">
                        <el-icon>
                            <Briefcase />
                        </el-icon>
                        <span>其他设置</span>
                    </span>
                </template>
                <el-form label-width="120px" :model="siteConfig" label-position="left">
                    <el-form-item label="文章默认封面">
                        <el-upload class="avatar-uploader" :headers="authorization" action="/api/admin/site/upload"
                            :show-file-list="false" accept="image/*" :before-upload="beforeUpload"
                            :on-success="handleArticleSuccess">
                            <img v-if="siteConfig.articleCover" :src="siteConfig.articleCover" class="article-cover" />
                            <el-icon v-else class="avatar-uploader-icon">
                                <Plus />
                            </el-icon>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="邮箱通知">
                        <el-radio-group v-model="siteConfig.emailNotice">
                            <el-radio :label="0">关闭</el-radio>
                            <el-radio :label="1">开启</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="第三方登录">
                        <el-checkbox-group v-model="loginList">
                            <el-checkbox label="qq">QQ</el-checkbox>
                            <el-checkbox label="gitee">Gitee</el-checkbox>
                            <el-checkbox label="github">Github</el-checkbox>
                        </el-checkbox-group>
                    </el-form-item>
                    <el-form-item label="音乐播放器">
                        <el-radio-group v-model="siteConfig.isMusic">
                            <el-radio :label="0">关闭</el-radio>
                            <el-radio :label="1">开启</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="网易云歌单Id" v-if="siteConfig.isMusic == 1">
                        <el-input v-model="siteConfig.musicId" style="width: 400px;"></el-input>
                    </el-form-item>
                    <el-form-item label="聊天室">
                        <el-radio-group v-model="siteConfig.isChat">
                            <el-radio :label="0">关闭</el-radio>
                            <el-radio :label="1">开启</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="websocket链接" v-if="siteConfig.isChat == 1">
                        <el-input v-model="siteConfig.websocketUrl" style="width: 400px;"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="handleUpdate">保 存</el-button>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script setup lang="ts">
import { getSiteConfig, updateSiteConfig, uploadSiteImg } from '@/api/site';
import { SiteConfig } from '@/api/site/types';
import { notifySuccess } from '@/utils/modal';
import { getToken, token_prefix } from '@/utils/token';
import { AxiosResponse } from 'axios';
import { UploadRawFile } from 'element-plus';
import * as imageConversion from 'image-conversion';
import { computed, onMounted, reactive, toRefs } from 'vue';

const toolList = "undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code | emoji tip todo-list";
const authorization = computed(() => {
    return {
        Authorization: token_prefix + getToken(),
    }
});
const data = reactive({
    siteConfig: {} as SiteConfig,
    socialList: [] as string[],
    loginList: [] as string[],
});
const {
    siteConfig,
    socialList,
    loginList,
} = toRefs(data);
const handleUploadImage = (event: any, insertImage: any, files: File[]) => {
    files.forEach(file => {
        let formData = new FormData();
        formData.append("file", file);
        uploadSiteImg(formData).then(({ data }) => {
            if (data.flag) {
                insertImage({
                    url: data.data,
                });
            }
        })
    });
};
const handleUserAvatarSuccess = (response: AxiosResponse) => {
    siteConfig.value.userAvatar = response.data;
};
const handleTouristAvatarSuccess = (response: AxiosResponse) => {
    siteConfig.value.touristAvatar = response.data;
};
const handleAuthorAvatarSuccess = (response: AxiosResponse) => {
    siteConfig.value.authorAvatar = response.data;
};
const handleWeiXinSuccess = (response: AxiosResponse) => {
    siteConfig.value.weiXinCode = response.data;
};
const handleAliSuccess = (response: AxiosResponse) => {
    siteConfig.value.aliCode = response.data;
};
const handleArticleSuccess = (response: AxiosResponse) => {
    siteConfig.value.articleCover = response.data;
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
const handleUpdate = () => {
    if (loginList.value.length > 0) {
        siteConfig.value.loginList = loginList.value.toString();
    } else {
        siteConfig.value.loginList = "";
    }
    if (socialList.value.length > 0) {
        siteConfig.value.socialList = socialList.value.toString();
    } else {
        siteConfig.value.socialList = "";
    }
    updateSiteConfig(siteConfig.value).then(({ data }) => {
        if (data.flag) {
            notifySuccess(data.msg);
            getList();
        }
    })
};
const getList = () => {
    getSiteConfig().then(({ data }) => {
        siteConfig.value = data.data;
        socialList.value = data.data.socialList.split(",");
        loginList.value = data.data.loginList.split(",");
    })
};
onMounted(() => {
    getList();
});
</script>

<style scoped>
.demo-tabs>.el-tabs__content {
    padding: 32px;
    color: #6b778c;
    font-size: 32px;
    font-weight: 600;

}

.demo-tabs .custom-tabs-label .el-icon {
    vertical-align: middle;
}

.demo-tabs .custom-tabs-label span {
    vertical-align: middle;
    margin-left: 4px;
}

.avatar-uploader .avatar {
    width: 120px;
    height: 120px;
    object-fit: contain;
}

.article-cover {
    width: 300px;
}
</style>
