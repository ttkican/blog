<template>
  <div class="page-header">
    <h1 class="page-title">图床</h1>
    <img class="page-cover" src="https://ik.imagekit.io/nicexl/Wallpaper/ba41a32b219e4b40ad055bbb52935896_Y0819msuI.jpg"
      alt="">
    <!-- 波浪 -->
    <Waves></Waves>
  </div>
  <div class="bg">
    <div class="page-container">
      <n-form ref="formInstRef" label-placement="left" :label-width="90" :model="form" :rules="rules">
        <n-form-item label="SESSDATA:" label-style="color: var(--text-color);" path="data">
          <n-input placeholder="输入SESSDATA" v-model:value="form.data" style="width: 400px;" />
        </n-form-item>
        <n-form-item label="bili_jct:" label-style="color: var(--text-color);" path="csrf">
          <n-input placeholder="请输入bili_jct" v-model:value="form.csrf" style="width: 400px;"/>
        </n-form-item>
      </n-form>
      <div class="btn-list">
        <n-popover trigger="click" style="width: 250px;" placement="bottom">
          <template #trigger>
            <n-button color="#18A058">使用方法</n-button>
          </template>
          <span style="word-break: break-all;">使用方法: 登录哔哩哔哩→F12打开控制台→Application→Cookies→bili_jct、SESSDATA</span>
        </n-popover>
        <n-button class="ml" color="#3e999f" @click="handleSave">保存配置</n-button>
        <n-upload class="ml" accept="image/*" multiple :show-file-list="false" @change="changeFile">
          <n-button color="#49b1f5" :loading="loading">上传文件</n-button>
        </n-upload>
      </div>
      <div v-if="imgList.length > 0" v-masonry fit-width="true" transition-duration="0.3s" item-selector=".card"
        style="margin-top: 15px;">
        <div v-masonry-tile class="card" v-for="(img, index) in imgList" :key="index">
          <img class="img" :src="img">
          <div class="mask">
            <svg-icon icon-class="copy" size="1.5rem" color="#fff" style="margin-right:0.15rem;"
              @click="handleCopy(img)"></svg-icon>
            <svg-icon icon-class="delete" size="1.6rem" color="#fff" @click="imgList.splice(index, 1)"></svg-icon>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { biliUpload } from "@/api/user";
import { useClipboard } from "@vueuse/core";
import { FormInst, UploadFileInfo } from "naive-ui";
const form = reactive({
  csrf: "",
  data: "",
});
const loading = ref(false);
const formInstRef = ref<FormInst | null>(null);
const rules = {
  data: {
    required: true,
    message: "SESSDATA不能为空",
  },
  csrf: {
    required: true,
    message: "bili_jct不能为空",
  },
};
const imgList = reactive<string[]>([]);
const { copy } = useClipboard();
const handleCopy = (text: string) => {
  copy(text);
  window.$message?.success("复制成功");
};
const handleSave = () => {
  formInstRef.value?.validate((errors) => {
    if (!errors) {
      localStorage.setItem("SESSDATA", form.data);
      localStorage.setItem("csrf", form.csrf);
      window.$message?.success("保存成功");
    }
  })
};
const changeFile = (options: { file: UploadFileInfo }) => {
  formInstRef.value?.validate((errors) => {
    if (!errors) {
      let formData = new FormData();
      formData.append("file_up", options.file.file as File);
      formData.append("csrf", form.csrf);
      formData.append("data", form.data);
      loading.value = true;
      biliUpload(formData).then(({ data }) => {
        if (data.flag) {
          imgList.push(data.data);
          loading.value = false;
        }
      })
    }
  })
}
onMounted(() => {
  form.data = localStorage.getItem("SESSDATA") as string;
  form.csrf = localStorage.getItem("csrf") as string;
})
</script>

<style lang="scss" scoped>
.card {
  width: 100%;
  max-width: 200px;
  margin: 0.5rem;
  border-radius: 5px;
  box-shadow: 0px 2px 10px 1px rgba(0, 0, 0, 0.1);
  transition: all 0.3s;

  .img {
    width: 100%;
    height: 100%;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
  }

  &:hover {
    box-shadow: none;
  }
}

.ml {
  margin-left: 15px;
}

.btn-list {
  display: flex;
}

.mask {
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 0;
  right: 0;
  width: 100%;
  height: 100%;
  border-radius: 5px;
  opacity: 0;
  background: rgba(101, 101, 101, 0.4);
  transition: all 0.3s;
}

.card:hover .mask {
  opacity: 1;
}
</style>