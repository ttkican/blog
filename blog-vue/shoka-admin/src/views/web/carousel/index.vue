<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <el-form @submit.native.prevent :model="queryParams" :inline="true" v-show="showSearch">
      <el-form-item label="状态">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 130px;">
          <el-option v-for="item in status" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb15">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Upload" @click="openModel(undefined)">上传</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain :disabled="carouselIdList.length === 0" icon="Delete"
          @click="handleDelete(undefined)">批量删除</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 轮播图列表 -->
    <el-table border :data="carouselList" @selection-change="handleSelectionChange" v-loading="loading">
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column prop="imgUrl" label="图片" align="center">
        <template #default="scope">
          <el-image style="width: 200px; height: 100%" :src="scope.row.imgUrl" fit="cover"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="80" align="center">
        <template #default="scope">
          <el-switch v-model="scope.row.status" style="--el-switch-on-color: #13ce66;" :disabled="scope.row.status == 0"
            :active-value="1" :inactive-value="0" @change="handleStatus(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="remark" width="200" label="备注" align="center">
      </el-table-column>
      <el-table-column prop="createTime" width="220" label="创建时间" align="center">
        <template #default="scope">
          <div class="create-time">
            <el-icon>
              <clock />
            </el-icon>
            <span style="margin-left: 10px">{{ formatDateTime(scope.row.createTime) }}</span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="160">
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
      @pagination="getList"></pagination>
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" v-model="addOrUpdate" width="600px" append-to-body>
      <el-form ref="carouselFormRef" label-width="80px" :model="carouselForm" :rules="rules">
        <el-form-item label="轮播图" prop="imgUrl">
          <el-upload drag :show-file-list="false" :headers="authorization" action="/api/admin/carousel/upload"
            accept="image/*" :before-upload="beforeUpload" :on-success="handleSuccess">
            <el-icon class="el-icon--upload" v-if="carouselForm.imgUrl === ''"><upload-filled /></el-icon>
            <div class="el-upload__text" v-if="carouselForm.imgUrl === ''">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <img v-else :src="carouselForm.imgUrl" width="360" />
          </el-upload>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch v-model="carouselForm.status" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="carouselForm.remark" :autosize="{ minRows: 2, maxRows: 4 }" resize="none" type="textarea"
            placeholder="请输入内容" maxlength="50" show-word-limit style="width: 300px;"></el-input>
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
import { addCarousel, deleteCarousel, getCarouselList, updateCarousel, updateCarouselStatus } from "@/api/carousel";
import { Carousel, CarouselForm, CarouselQuery } from '@/api/carousel/types';
import { formatDateTime } from "@/utils/date";
import { messageConfirm, notifySuccess } from "@/utils/modal";
import { getToken, token_prefix } from "@/utils/token";
import { AxiosResponse } from "axios";
import { FormInstance, FormRules, UploadRawFile } from "element-plus";
import * as imageConversion from 'image-conversion';
import { computed, onMounted, reactive, ref } from 'vue';

const carouselList = ref<Carousel[]>();
const loading = ref(false);
const addOrUpdate = ref(false);
const showSearch = ref(true);
const count = ref(0);
const title = ref("");
const carouselIdList = ref<number[]>([]);
const carouselFormRef = ref<FormInstance>();
const status = ref([
  {
    value: 0,
    label: "隐藏",
  },
  {
    value: 1,
    label: "显示",
  },
]);

const queryParams = reactive<CarouselQuery>({
  current: 1,
  size: 10,
});

const carouselForm = ref<CarouselForm>({
  id: undefined,
  imgUrl: "",
  status: 0,
  remark: "",
});

const rules = reactive<FormRules>({
  imgUrl: [{ required: true, message: "轮播图不能为空", trigger: "blur" }],
});

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

const authorization = computed(() => {
  return {
    Authorization: token_prefix + getToken(),
  }
});

const handleSuccess = (response: AxiosResponse) => {
  carouselForm.value.imgUrl = response.data;
};

const handleSelectionChange = (selection: Carousel[]) => {
  carouselIdList.value = selection.map((item) => item.id);
};

const handleStatus = (carousel: Carousel) => {
  let text = carousel.status === 0 ? "隐藏" : "显示";
  messageConfirm("确定要" + text + "该图片吗?").then(() => {
    updateCarouselStatus({ id: carousel.id, status: carousel.status }).then(({ data }) => {
      if (data.flag) {
        notifySuccess(data.msg);
      } else {
        carousel.status = carousel.status === 0 ? 1 : 0;
      }
    });
  }).catch(() => { carousel.status = carousel.status === 0 ? 1 : 0; });
};

const handleDelete = (id?: number) => {
  let ids: number[] = [];
  if (id === undefined) {
    ids = carouselIdList.value;
  } else {
    ids = [id];
  }
  messageConfirm("确认删除已选中的数据项?").then(() => {
    deleteCarousel(ids).then(({ data }) => {
      if (data.flag) {
        notifySuccess(data.msg);
        getList();
      }
    });
  }).catch(() => { });
};

const submitForm = () => {
  carouselFormRef.value?.validate((valid) => {
    if (valid) {
      if (carouselForm.value.id !== undefined) {
        updateCarousel(carouselForm.value).then(({ data }) => {
          if (data.flag) {
            notifySuccess(data.msg);
            getList();
          }
          addOrUpdate.value = false;
        })
      } else {
        addCarousel(carouselForm.value).then(({ data }) => {
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

const openModel = (carousel?: Carousel) => {
  carouselFormRef.value?.clearValidate();
  if (carousel !== undefined) {
    carouselForm.value = JSON.parse(JSON.stringify(carousel));
    title.value = "修改轮播图";
  } else {
    title.value = "上传轮播图";
    carouselForm.value = {
      id: undefined,
      imgUrl: "",
      status: 0,
      remark: "",
    }
  }
  addOrUpdate.value = true;
};
const getList = () => {
  loading.value = true;
  getCarouselList(queryParams).then(({ data }) => {
    carouselList.value = data.data.recordList;
    count.value = data.data.count;
    loading.value = false;
  });
};

const handleQuery = () => {
  queryParams.current = 1;
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