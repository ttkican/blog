<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <el-form :model="queryParams" :inline="true" v-show="showSearch">
      <el-form-item label="菜单名称">
        <el-input @keyup.enter="handleQuery" v-model="queryParams.keyword" style="width: 200px" placeholder="请输入菜单名称"
          clearable />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="queryParams.isDisable" placeholder="菜单状态" clearable style="width: 200px">
          <el-option v-for="item in isDisable" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb15">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd(undefined)">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="Sort" @click="toggleExpandAll">展开/折叠</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 表格展示 -->
    <el-table v-if="refreshTable" v-loading="loading" :data="menuList" row-key="id" :default-expand-all="isExpandAll"
      :tree-props="defaultProps" highlight-current-row>
      <!-- 菜单名称 -->
      <el-table-column prop="menuName" label="菜单名称" :show-overflow-tooltip="true" width="200"></el-table-column>
      <!-- 图标 -->
      <el-table-column prop="icon" label="图标" align="center" width="90">
        <template #default="scope">
          <svg-icon :icon-class="scope.row.icon" />
        </template>
      </el-table-column>
      <!-- 排序 -->
      <el-table-column prop="orderNum" label="排序" align="center" width="60"></el-table-column>
      <!-- 类型 -->
      <el-table-column prop="menuType" label="类型" align="center" width="80">
        <template #default="scope">
          <el-tag v-if="scope.row.menuType === 'M'" type="warning">目录</el-tag>
          <el-tag v-if="scope.row.menuType === 'C'" type="success">菜单</el-tag>
          <el-tag v-if="scope.row.menuType === 'B'" type="danger">按钮</el-tag>
        </template>
      </el-table-column>
      <!-- 状态 -->
      <el-table-column prop="status" label="状态" align="center" width="80">
        <template #default="scope">
          <el-tag v-if="scope.row.isDisable === 0">正常</el-tag>
          <el-tag v-if="scope.row.isDisable === 1" type="danger">禁用</el-tag>
        </template>
      </el-table-column>
      <!-- 权限标识 -->
      <el-table-column prop="perms" label="权限标识" :show-overflow-tooltip="true"></el-table-column>
      <!-- 组件路径 -->
      <el-table-column prop="component" label="组件路径" :show-overflow-tooltip="true"></el-table-column>
      <!-- 是否隐藏 -->
      <el-table-column prop="status" label="是否隐藏" align="center" width="90">
        <template #default="scope">
          <el-tag v-if="scope.row.isHidden === 0">否</el-tag>
          <el-tag v-if="scope.row.isHidden === 1" type="danger">是</el-tag>
        </template>
      </el-table-column>
      <!-- 创建时间 -->
      <el-table-column label="创建时间" align="center" prop="createTime" width="120">
        <template #default="scope">
          <span>{{ formatDate(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <!-- 操作 -->
      <el-table-column label="操作" align="center" width="210">
        <template #default="scope">
          <el-button v-if="scope.row.menuType === 'M' || scope.row.menuType === 'C'" type="success" link icon="Plus"
            @click="handleAdd(scope.row)">新增</el-button>
          <el-button type="primary" link icon="Edit" @click="handleEdit(scope.row.id)">编辑</el-button>
          <el-button type="danger" link icon="Delete" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" v-model="addOrUpdate" width="680px" @close="cancel" append-to-body>
      <el-form ref="menuFormRef" :model="menuForm" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="上级菜单">
              <el-tree-select v-model="menuForm.parentId" :data="menuOptions" placeholder="选择上级菜单" filterable
                check-strictly :render-after-expand="false"></el-tree-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="菜单类型" prop="menuType">
              <el-radio-group v-model="menuForm.menuType">
                <el-radio label="M">目录</el-radio>
                <el-radio label="C">菜单</el-radio>
                <el-radio label="B">按钮</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="menuForm.menuType != 'B'">
            <el-form-item label="菜单图标" prop="icon">
              <el-popover placement="bottom-start" :width="540" trigger="click">
                <template #reference>
                  <el-input v-model="menuForm.icon" placeholder="点击选择图标" readonly @click="showChooseIcon = true">
                    <template #prefix>
                      <svg-icon v-if="menuForm.icon" :icon-class="menuForm.icon" />
                      <el-icon v-else style="height: 32px;width: 16px;">
                        <search />
                      </el-icon>
                    </template>
                  </el-input>
                </template>
                <icon-select @selected="selected" />
              </el-popover>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜单名称" prop="menuName">
              <el-input v-model="menuForm.menuName" placeholder="请输入菜单名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示排序" prop="orderNum">
              <el-input-number v-model="menuForm.orderNum" controls-position="right" :min="1" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="menuForm.menuType != 'B'">
            <el-form-item prop="path">
              <template #label>
                <span>
                  <el-tooltip content="访问的路由地址，如：user" placement="top">
                    <el-icon><question-filled /></el-icon>
                  </el-tooltip>
                  路由地址
                </span>
              </template>
              <el-input v-model="menuForm.path" placeholder="请输入路由地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="menuForm.menuType == 'C'">
            <el-form-item prop="component">
              <template #label>
                <span>
                  <el-tooltip content="访问的组件路径，如：/user/index，默认在views目录下" placement="top">
                    <el-icon><question-filled /></el-icon>
                  </el-tooltip>
                  组件路径
                </span>
              </template>
              <el-input v-model="menuForm.component" placeholder="请输入组件路径" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="menuForm.menuType != 'M'">
            <el-form-item>
              <template #label>
                <span>
                  <el-tooltip content="控制器中定义的权限字符，如：system:user:list" placement="top">
                    <el-icon><question-filled /></el-icon>
                  </el-tooltip>
                  权限字符
                </span>
              </template>
              <el-input v-model="menuForm.perms" placeholder="请输入权限标识" maxlength="100" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="menuForm.menuType != 'B'">
            <el-form-item>
              <template #label>
                <span>
                  <el-tooltip content="选择隐藏则路由将不会出现在侧边栏，但仍然可以访问" placement="top">
                    <el-icon><question-filled /></el-icon>
                  </el-tooltip>
                  显示状态
                </span>
              </template>
              <el-radio-group v-model="menuForm.isHidden">
                <el-radio v-for="dict in isHidden" :key="dict.value" :label="dict.value">
                  {{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="menuForm.menuType != 'B'">
            <el-form-item>
              <template #label>
                <span>
                  <el-tooltip content="选择停用则路由将不会出现在侧边栏，也不能被访问" placement="top">
                    <el-icon><question-filled /></el-icon>
                  </el-tooltip>
                  菜单状态
                </span>
              </template>
              <el-radio-group v-model="menuForm.isDisable">
                <el-radio v-for="dict in isDisable" :key="dict.value" :label="dict.value">
                  {{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { addMenu, deleteMenu, editMenu, getMenuList, getMenuOptions, updateMenu } from "@/api/menu";
import { Menu, MenuForm, MenuOption, MenuQuery } from "@/api/menu/types";
import IconSelect from '@/components/IconSelect/index.vue';
import { formatDate } from "@/utils/date";
import { messageConfirm, notifySuccess } from "@/utils/modal";
import { FormInstance, FormRules } from 'element-plus';
import { nextTick, onMounted, reactive, ref, toRefs } from "vue";
const menuFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
  menuName: [{ required: true, message: "菜单名称不能为空", trigger: "blur" }],
  component: [{ required: true, message: "组件不能为空", trigger: "blur" }],
  orderNum: [{ required: true, message: "顺序不能为空", trigger: "blur" }],
  path: [{ required: true, message: "路由地址不能为空", trigger: "blur" }]
});
const defaultProps = { value: 'id', label: 'label', children: 'children' };
const data = reactive({
  showSearch: true,
  loading: false,
  title: "",
  addOrUpdate: false,
  queryParams: {} as MenuQuery,
  isDisable: [
    {
      value: 0,
      label: "正常",
    },
    {
      value: 1,
      label: "禁用",
    },
  ],
  isHidden: [
    {
      value: 0,
      label: "显示",
    },
    {
      value: 1,
      label: "隐藏",
    },
  ],
  showChooseIcon: false,
  refreshTable: true,
  isExpandAll: false,
  menuOptions: [] as MenuOption[],
  menuForm: {} as MenuForm,
  menuList: [] as Menu[],
});
const {
  showSearch,
  loading,
  title,
  addOrUpdate,
  queryParams,
  isDisable,
  isHidden,
  showChooseIcon,
  refreshTable,
  isExpandAll,
  menuOptions,
  menuForm,
  menuList
} = toRefs(data);
/** 选择图标 */
const selected = (name: string) => {
  menuForm.value.icon = name;
  showChooseIcon.value = false;
};
const toggleExpandAll = () => {
  refreshTable.value = false;
  isExpandAll.value = !isExpandAll.value;
  nextTick(() => {
    refreshTable.value = true;
  });
};
// 取消
const cancel = () => {
  addOrUpdate.value = false;
  menuFormRef.value?.clearValidate();
  reset();
};
// 重置表单
const reset = () => {
  menuForm.value = {
    id: undefined,
    parentId: 0,
    menuName: "",
    menuType: "C",
    path: undefined,
    icon: undefined,
    component: undefined,
    orderNum: 1,
    isHidden: 0,
    isDisable: 0,
    perms: undefined,
  };
};
// 添加
const handleAdd = async (menu?: Menu) => {
  reset();
  await getMenuTree();
  if (menu != undefined && menu.id) {
    menuForm.value.parentId = menu.id;
  } else {
    menuForm.value.parentId = 0;
  }
  title.value = "添加菜单";
  addOrUpdate.value = true;
};
// 编辑
const handleEdit = async (menuId: number) => {
  await getMenuTree();
  editMenu(menuId).then(({ data }) => {
    if (data.flag) {
      menuForm.value = data.data;
      title.value = "修改菜单";
      addOrUpdate.value = true;
    }
  })
};
// 删除
const handleDelete = (id: number) => {
  messageConfirm("确认删除已选中的数据项?").then(() => {
    deleteMenu(id).then(({ data }) => {
      if (data.flag) {
        notifySuccess(data.msg);
        getList();
      }
    });
  }).catch(() => { });
};
// 提交
const submitForm = () => {
  menuFormRef.value?.validate((valid) => {
    if (valid) {
      if (menuForm.value.id !== undefined) {
        updateMenu(menuForm.value).then(({ data }) => {
          if (data.flag) {
            notifySuccess(data.msg);
            getList();
          }
          cancel();
        });
      } else {
        addMenu(menuForm.value).then(({ data }) => {
          if (data.flag) {
            notifySuccess(data.msg);
            getList();
          }
          cancel();
        });
      }
    }
  })
};
// 获取菜单树
const getMenuTree = async () => {
  const menuTree: MenuOption[] = [];
  await getMenuOptions().then(({ data }) => {
    const menuOption: MenuOption = { value: 0, label: "顶级菜单", children: data.data };
    menuTree.push(menuOption);
    menuOptions.value = menuTree;
  })
};
// 加载表格内容
const getList = () => {
  loading.value = true;
  getMenuList(queryParams.value).then(({ data }) => {
    menuList.value = data.data;
    loading.value = false;
  });
};
const handleQuery = () => {
  getList();
};
onMounted(() => {
  getList();
});
</script>
