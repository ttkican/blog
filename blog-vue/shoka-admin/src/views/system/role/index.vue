<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <el-form ref="queryFormRef" :model="queryParams" :inline="true" v-show="showSearch">
      <el-form-item label="角色名称">
        <el-input v-model="queryParams.keyword" style="width: 200px" placeholder="请输入角色名称" clearable
          @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="queryParams.isDisable" placeholder="角色状态" clearable style="width: 200px">
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
        <el-button type="primary" plain icon="Plus" @click="openModel(undefined)">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain :disabled="roleIdList.length === 0" icon="Delete"
          @click="handleDelete(undefined)">批量删除</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 表格展示 -->
    <el-table border :data="roleList" @selection-change="handleSelectionChange" v-loading="loading">
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <!-- 角色名 -->
      <el-table-column prop="roleName" width="280" label="角色名" align="center"></el-table-column>
      <!-- 角色描述 -->
      <el-table-column prop="roleDesc" width="300" label="角色描述" align="center"></el-table-column>
      <!-- 状态 -->
      <el-table-column prop="isDisable" label="状态" align="center">
        <template #default="scope">
          <el-switch v-model="scope.row.isDisable" active-color="#13ce66" inactive-color="#ff4949" :active-value="0"
            :inactive-value="1" @change="handleChangeStatus(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <!-- 创建时间 -->
      <el-table-column prop="createTime" width="270" label="创建时间" align="center">
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
      <el-table-column width="270" label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" icon="Edit" link @click="openModel(scope.row)">
            编辑
          </el-button>
          <el-button v-if="scope.row.id !== '1'" type="danger" icon="Delete" link @click="handleDelete(scope.row.id)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <pagination v-if="count > 0" :total="count" v-model:page="queryParams.current" v-model:limit="queryParams.size"
      @pagination="getList" />
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" v-model="addOrUpdate" width="500px" append-to-body>
      <el-form ref="roleFormRef" :model="roleForm" :rules="rules" label-width="100px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input placeholder="请输入角色名称" v-model="roleForm.roleName" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="roleForm.isDisable">
            <el-radio v-for="dict in status" :key="dict.value" :label="dict.value">
              {{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单权限">
          <el-checkbox v-model="menuExpand" @change="handleCheckedTreeExpand">展开/折叠</el-checkbox>
          <el-checkbox v-model="menuNodeAll" @change="handleCheckedTreeNodeAll">全选/全不选</el-checkbox>
          <el-checkbox v-model="menuCheckStrictly" @change="handleCheckedTreeConnect">父子联动</el-checkbox>
          <el-tree class="tree-border" :data="treeData" show-checkbox ref="treeRef" node-key="id"
            :default-expand-all="false" :check-strictly="!menuCheckStrictly" empty-text="加载中，请稍候"
            :props="defaultProps"></el-tree>
        </el-form-item>
        <el-form-item label="角色描述">
          <el-input v-model="roleForm.roleDesc" :autosize="{ minRows: 2, maxRows: 4 }" resize="none" type="textarea"
            placeholder="请输入内容"></el-input>
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
import { getMenuTree } from "@/api/menu";
import { MenuTree } from "@/api/menu/types";
import { addRole, deleteRole, getRoleList, getRoleMenuTree, updateRole, updateRoleStatus } from "@/api/role";
import { Role, RoleForm, RoleQuery } from "@/api/role/types";
import { formatDateTime } from "@/utils/date";
import { messageConfirm, notifySuccess } from "@/utils/modal";
import { ElTree, FormInstance, FormRules } from 'element-plus';
import type Node from 'element-plus/es/components/tree/src/model/node';
import { nextTick, onMounted, reactive, ref, toRefs } from "vue";
const treeRef = ref<InstanceType<typeof ElTree>>()
const roleFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
  roleName: [{ required: true, message: "请输入角色名称", trigger: "blur" }],
});
const defaultProps = {
  children: 'children',
  label: 'label',
};
const data = reactive({
  menuExpand: false,
  menuNodeAll: false,
  menuCheckStrictly: false,
  count: 0,
  showSearch: true,
  loading: false,
  title: "",
  addOrUpdate: false,
  queryParams: {
    current: 1,
    size: 10,
  } as RoleQuery,
  status: [
    {
      value: 0,
      label: "正常",
    },
    {
      value: 1,
      label: "禁用",
    },
  ],
  treeData: [] as MenuTree[],
  roleForm: {} as RoleForm,
  roleIdList: [] as string[],
  roleList: [] as Role[],
});
const {
  menuExpand,
  menuNodeAll,
  menuCheckStrictly,
  count,
  showSearch,
  loading,
  title,
  addOrUpdate,
  queryParams,
  status,
  treeData,
  roleForm,
  roleIdList,
  roleList,
} = toRefs(data);
const handleCheckedTreeExpand = () => {
  let treeList = treeData.value;
  for (let i = 0; i < treeList.length; i++) {
    treeRef.value!.store.nodesMap[treeList[i].id].expanded = menuExpand.value;
  }
};
const handleCheckedTreeNodeAll = () => {
  treeRef.value!.setCheckedNodes(menuNodeAll.value ? treeData.value as Node[] : []);
};
const handleCheckedTreeConnect = () => {
  menuCheckStrictly.value = menuCheckStrictly.value ? true : false;
};
const handleSelectionChange = (selection: Role[]) => {
  roleIdList.value = selection.map((item) => item.id);
};
const reset = () => {
  if (treeRef.value !== undefined) {
    treeRef.value.setCheckedKeys([]);
  }
  menuExpand.value = false;
  menuNodeAll.value = false;
  menuCheckStrictly.value = false;
  roleFormRef.value?.clearValidate();
};
const openModel = async (role?: Role) => {
  reset();
  await getMenuTree().then(({ data }) => {
    treeData.value = data.data;
  });
  if (role !== undefined) {
    title.value = "修改角色";
    roleForm.value.id = role.id;
    roleForm.value.roleName = role.roleName;
    roleForm.value.isDisable = role.isDisable;
    roleForm.value.roleDesc = role.roleDesc;
    getRoleMenuTree(role.id).then(({ data }) => {
      data.data.forEach(v => {
        nextTick(() => {
          treeRef.value!.setChecked(v, true, false);
        });
      })
    })
  } else {
    title.value = "添加角色";
    roleForm.value = {
      id: undefined,
      roleName: "",
      roleDesc: "",
      isDisable: 0,
      menuIdList: [],
    };
  }
  addOrUpdate.value = true;
};
const submitForm = () => {
  roleFormRef.value?.validate((valid) => {
    if (valid) {
      if (roleForm.value.id !== undefined) {
        roleForm.value.menuIdList = getMenuAllCheckedKeys();
        updateRole(roleForm.value).then(({ data }) => {
          if (data.flag) {
            notifySuccess(data.msg);
            getList();
          }
          addOrUpdate.value = false;
        })
      } else {
        roleForm.value.menuIdList = getMenuAllCheckedKeys();
        addRole(roleForm.value).then(({ data }) => {
          if (data.flag) {
            notifySuccess(data.msg);
            getList();
          }
          addOrUpdate.value = false;
        })
      }
    }
  })
};
const getMenuAllCheckedKeys = () => {
  // 目前被选中的菜单节点
  let checkedKeys = treeRef.value!.getCheckedKeys();
  // 半选中的菜单节点
  let halfCheckedKeys = treeRef.value!.getHalfCheckedKeys();
  checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
  return checkedKeys;
}
const handleDelete = (id?: string) => {
  let ids: string[] = [];
  if (id == undefined) {
    ids = roleIdList.value;
  } else {
    ids = [id];
  }
  messageConfirm("确认删除已选中的数据项?").then(() => {
    deleteRole(ids).then(({ data }) => {
      if (data.flag) {
        notifySuccess(data.msg);
        getList();
      }
    });
  }).catch(() => { });
};
const getList = () => {
  loading.value = true;
  getRoleList(queryParams.value).then(({ data }) => {
    roleList.value = data.data.recordList;
    count.value = data.data.count;
    loading.value = false;
  });
};
const handleChangeStatus = (role: Role) => {
  let text = role.isDisable === 0 ? "启用" : "禁用";
  messageConfirm("确定要" + text + role.roleName + "角色吗?").then(() => {
    updateRoleStatus({ id: role.id, isDisable: role.isDisable }).then(({ data }) => {
      if (data.flag) {
        notifySuccess(data.msg);
      } else {
        role.isDisable = role.isDisable === 0 ? 1 : 0;
      }
    });
  }).catch(() => { role.isDisable = role.isDisable === 0 ? 1 : 0; });
};

const handleQuery = () => {
  queryParams.value.current = 1;
  getList();
};
onMounted(() => {
  getList();
});
</script>

<style scoped></style>