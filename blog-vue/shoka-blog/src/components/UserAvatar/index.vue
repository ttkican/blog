<template>
  <div>
    <img class="user-avatar" :src="user.avatar" @click="dialogVisible = true" />
    <n-modal class="bg" v-model:show="dialogVisible" preset="dialog" :show-icon="false" transform-origin="center"
      :block-scroll="false" :closable="false" @after-leave="handleClose">
      <div style="width: 100%; height: 300px">
        <vue-cropper ref="cropperRef" :img="options.img" :info="true" :autoCrop="options.autoCrop"
          :autoCropWidth="options.autoCropWidth" :autoCropHeight="options.autoCropHeight" :fixedBox="options.fixedBox"
          :outputType="options.outputType"></vue-cropper>
      </div>
      <n-upload class="mt-4" action="#" accept="image/*" :show-file-list="false" :custom-request="customUpload">
        <n-button>点击上传</n-button>
      </n-upload>
      <template #action>
        <n-button color="#3e999f" @click="dialogVisible = false">
          取消
        </n-button>
        <n-button color="#e9546b" @click="hanleUpload">
          提交
        </n-button>
      </template>
    </n-modal>
  </div>
</template>

<script setup lang="ts">
import { updateUserAvatar } from "@/api/user";
import { useUserStore } from "@/store";
import { UploadCustomRequestOptions } from "naive-ui";
import { VueCropper } from 'vue-cropper';
import 'vue-cropper/dist/index.css';
const user = useUserStore();
const dialogVisible = ref(false);
const cropperRef = ref();
const options = reactive({
  img: user.avatar, // 裁剪图片的地址
  autoCrop: true, // 是否默认生成截图框
  autoCropWidth: 200, // 默认生成截图框宽度
  autoCropHeight: 200, // 默认生成截图框高度
  fixedBox: true, // 固定截图框大小 不允许改变
  outputType: "png", // 默认生成截图为PNG格式
});
const customUpload = ({ file }: UploadCustomRequestOptions) => {
  const reader = new FileReader();
  reader.readAsDataURL(file.file as File);
  reader.onload = () => {
    options.img = reader.result as string;
  };
};
const handleClose = () => {
  options.img = user.avatar;
};
const hanleUpload = () => {
  cropperRef.value.getCropBlob((data: string | Blob) => {
    let formData = new FormData();
    formData.append("file", data);
    updateUserAvatar(formData).then(({ data }) => {
      if (data.flag) {
        options.img = data.data;
        user.avatar = options.img;
        dialogVisible.value = false;
      }
    });
  });
};
</script>

<style scoped>
.user-avatar {
  width: 140px;
  height: 140px;
  border-radius: 50%;
}
</style>
