<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <el-form :model="queryParams" :inline="true">
      <el-form-item label="用户昵称">
        <el-input @keyup.enter="handleQuery" v-model="queryParams.keyword" style="width: 200px" placeholder="请输入用户昵称"
          clearable />
      </el-form-item>
      <el-form-item label="登录方式">
        <el-select v-model="queryParams.loginType" placeholder="请选择登录方式" clearable style="width: 200px">
          <el-option v-for="item in typeList" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格展示 -->
    <el-table border :data="userList" v-loading="loading">
      <!-- 用户头像 -->
      <el-table-column prop="avatar" label="头像" align="center" width="100">
        <template #default="scope">
          <img :src="scope.row.avatar" width="40" height="40" />
        </template>
      </el-table-column>
      <!-- 昵称 -->
      <el-table-column prop="nickname" label="昵称" align="center"></el-table-column>
      <!-- 登录方式 -->
      <el-table-column prop="loginType" label="登录方式" align="center" width="100">
        <template #default="scope">
          <el-tag type="success" v-if="scope.row.loginType == 1">邮箱</el-tag>
          <el-tag v-if="scope.row.loginType == 2">QQ</el-tag>
          <el-tag type="danger" v-if="scope.row.loginType == 3">Gitee</el-tag>
          <el-tag type="info" v-if="scope.row.loginType == 4">Github</el-tag>
        </template>
      </el-table-column>
      <!-- 用户角色 -->
      <el-table-column prop="roleList" label="用户角色" align="center" width="150">
        <template #default="scope">
          <el-tag v-for="item in scope.row.roleList" :key="item.id" style="margin-right:4px;margin-top:4px">
            {{ item.roleName }}
          </el-tag>
        </template>
      </el-table-column>
      <!-- 状态 -->
      <el-table-column prop="status" label="状态" align="center" width="100">
        <template #default="scope">
          <el-switch v-model="scope.row.isDisable" active-color="#13ce66" inactive-color="#ff4949" :active-value="0"
            :inactive-value="1" @change="handleChangeStatus(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <!-- 登录ip -->
      <el-table-column prop="ipAddress" label="登录ip" align="center"></el-table-column>
      <!-- 登录地址 -->
      <el-table-column prop="ipSource" label="登录地址" align="center"></el-table-column>
      <!-- 创建时间 -->
      <el-table-column prop="createTime" label="创建时间" align="center" width="220">
        <template #default="scope">
          <div class="create-time">
            <el-icon>
              <clock />
            </el-icon>
            <span style="margin-left: 10px">{{ formatDateTime(scope.row.createTime) }}</span>
          </div>
        </template>
      </el-table-column>
      <!-- 登录时间 -->
      <el-table-column prop="loginTime" label="登录时间" align="center" width="220">
        <template #default="scope">
          <div class="create-time">
            <el-icon>
              <clock />
            </el-icon>
            <span style="margin-left: 10px">{{ formatDateTime(scope.row.loginTime) }}</span>
          </div>
        </template>
      </el-table-column>
      <!-- 操作 -->
      <el-table-column label="操作" align="center" width="130">
        <template #default="scope">
          <el-button type="primary" icon="Edit" link @click="openModel(scope.row)">
            编辑
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <pagination v-if="count > 0" :total="count" v-model:page="queryParams.current" v-model:limit="queryParams.size"
      @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <el-dialog title="修改用户" v-model="update" width="500px" append-to-body>
      <el-form ref="userFormRef" label-width="100px" :model="userForm" :rules="rules">
        <el-form-item label="昵称" prop="nickname">
          <el-input placeholder="请输入昵称" v-model="userForm.nickname" style="width: 250px;" />
        </el-form-item>
        <el-form-item label="角色" prop="roleIdList">
          <el-checkbox-group v-model="roleIdList">
            <el-checkbox v-for="item in userRoleList" :key="item.id" :label="item.id">
              {{ item.roleName }}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="update = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { getUserList, getUserRoleList, updateUser, updateUserStatus } from '@/api/user';
import { User, UserForm, UserQuery, UserRole } from '@/api/user/types';
import { formatDateTime } from "@/utils/date";
import { messageConfirm, notifySuccess } from '@/utils/modal';
import { FormInstance, FormRules } from 'element-plus';
import { onMounted, reactive, ref, toRefs } from 'vue';
const userFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
  nickname: [{ required: true, message: "请输入昵称", trigger: "blur" }],
  roleIdList: [{ required: true, message: "角色不能为空", trigger: "click" }]
});
const data = reactive({
  count: 0,
  update: false,
  loading: false,
  queryParams: {
    current: 1,
    size: 10,
  } as UserQuery,
  typeList: [
    {
      value: 1,
      label: "邮箱",
    },
    {
      value: 2,
      label: "QQ",
    },
    {
      value: 3,
      label: "Gitee",
    },
    {
      value: 4,
      label: "Github",
    },
  ],
  userList: [] as User[],
  userForm: {} as UserForm,
  userRoleList: [] as UserRole[],
  roleIdList: [] as string[],
});
const {
  count,
  update,
  loading,
  queryParams,
  typeList,
  userList,
  userForm,
  userRoleList,
  roleIdList,
} = toRefs(data);
const openModel = (user: User) => {
  roleIdList.value = [];
  userForm.value.id = user.id;
  userForm.value.nickname = user.nickname;
  user.roleList.forEach(item => {
    roleIdList.value.push(item.id);
  });
  userFormRef.value?.clearValidate();
  update.value = true;
};
const handleChangeStatus = (user: User) => {
  let text = user.isDisable === 0 ? "解封" : "封禁";
  messageConfirm("确定要" + text + user.nickname + "吗?").then(() => {
    updateUserStatus({ id: user.id, isDisable: user.isDisable }).then(({ data }) => {
      if (data.flag) {
        notifySuccess(data.msg);
      } else {
        user.isDisable = user.isDisable === 0 ? 1 : 0;
      }
    })
  }).catch(() => { user.isDisable = user.isDisable === 0 ? 1 : 0; });
};
const submitForm = () => {
  userForm.value.roleIdList = roleIdList.value;
  userFormRef.value?.validate((valid) => {
    if (valid) {
      updateUser(userForm.value).then(({ data }) => {
        if (data.flag) {
          notifySuccess(data.msg);
          getList();
        }
        update.value = false;
      })
    }
  })
};
const getList = () => {
  loading.value = true;
  getUserList(queryParams.value).then(({ data }) => {
    userList.value = data.data.recordList;
    count.value = data.data.count;
    loading.value = false;
  })
};
const handleQuery = () => {
  queryParams.value.current = 1;
  getList();
};
onMounted(() => {
  getList();
  getUserRoleList().then(({ data }) => {
    userRoleList.value = data.data;
  })
});
</script>

<style scoped></style>
