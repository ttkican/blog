<template>
    <div class="app-container">
        <!-- 搜索栏 -->
        <el-form ref="queryFormRef" :model="queryParams" :inline="true" v-show="showSearch">
            <el-form-item label="任务名称" prop="jobName">
                <el-input v-model="queryParams.keyword" placeholder="请输入任务名称" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="任务组名" prop="jobGroup">
                <el-input v-model="queryParams.taskGroup" placeholder="请输入任务组名" clearable @keyup.enter="handleQuery" />
            </el-form-item>
            <el-form-item label="任务状态" prop="status">
                <el-select v-model="queryParams.status" placeholder="任务状态" clearable style="width: 200px">
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
                <el-button type="danger" plain :disabled="taskIdList.length === 0" icon="Delete"
                    @click="handleDelete(undefined)">批量删除</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="info" plain icon="operation" @click="handleTaskLog">日志</el-button>
            </el-col>
            <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <!-- 表格展示 -->
        <el-table border :data="taskList" @selection-change="handleSelectionChange" v-loading="loading">
            <!-- 表格列 -->
            <el-table-column type="selection" width="55" align="center"></el-table-column>
            <!--任务名称 -->
            <el-table-column label="任务名称" width="160" align="center" prop="taskName"
                :show-overflow-tooltip="true"></el-table-column>
            <!--任务组名 -->
            <el-table-column label="任务组名" width="140" align="center" prop="taskGroup"></el-table-column>
            <!-- 调用目标 -->
            <el-table-column label="调用目标" align="center" prop="invokeTarget"
                :show-overflow-tooltip="true"></el-table-column>
            <!-- cron执行表达式 -->
            <el-table-column label="cron执行表达式" align="center" prop="cronExpression"
                :show-overflow-tooltip="true"></el-table-column>
            <!-- 状态 -->
            <el-table-column label="状态" align="center" width="100">
                <template #default="scope">
                    <el-switch v-model="scope.row.status" active-color="#13ce66" inactive-color="#ff4949" :active-value="0"
                        :inactive-value="1" @change="handleChangeStatus(scope.row)"></el-switch>
                </template>
            </el-table-column>
            <!-- 备注 -->
            <el-table-column label="备注" align="center" width="160">
                <template #default="scope">
                    {{ scope.row.remark }}
                </template>
            </el-table-column>
            <!-- 创建时间 -->
            <el-table-column label="创建时间" align="center" width="130">
                <template #default="scope">
                    <div class="create-time">
                        <el-icon>
                            <clock />
                        </el-icon>
                        <span style="margin-left: 10px">{{ formatDate(scope.row.createTime) }}</span>
                    </div>
                </template>
            </el-table-column>
            <!-- 操作 -->
            <el-table-column label="操作" align="center" width="180">
                <template #default="scope">
                    <el-button type="primary" icon="Edit" link @click="openModel(scope.row)">
                        编辑
                    </el-button>
                    <el-button type="danger" icon="Delete" link @click="handleDelete(scope.row.id)">
                        删除
                    </el-button>
                    <el-dropdown @command="(command: string) => handleCommand(command, scope.row)">
                        <el-button type="info" icon="DArrowRight" link>更多</el-button>
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item command="handleRun" icon="CaretRight">执行一次</el-dropdown-item>
                                <el-dropdown-item command="handleView" icon="View">任务详细</el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <pagination v-if="count > 0" :total="count" v-model:page="queryParams.current" v-model:limit="queryParams.size"
            @pagination="getList" />
        <!-- 添加或修改定时任务对话框 -->
        <el-dialog :title="title" v-model="addOrUpdate" width="800px" append-to-body>
            <el-form ref="taskFormRef" :model="taskForm" :rules="rules" label-width="120px">
                <el-row>
                    <!-- 任务名称 -->
                    <el-col :span="12">
                        <el-form-item label="任务名称" prop="taskName">
                            <el-input v-model="taskForm.taskName" placeholder="请输入任务名称"></el-input>
                        </el-form-item>
                    </el-col>
                    <!-- 任务分组 -->
                    <el-col :span="12">
                        <el-form-item label="任务分组" prop="taskGroup">
                            <template #label v-if="title == '修改任务'">
                                <span>
                                    任务分组
                                    <el-tooltip placement="top">
                                        <template #content>
                                            <div>不能手动修改数据库ID和任务组名，否则会导致脏数据</div>
                                        </template>
                                        <el-icon><question-filled /></el-icon>
                                    </el-tooltip>
                                </span>
                            </template>
                            <el-input v-model="taskForm.taskGroup" :disabled="title == '修改任务'"
                                placeholder="请输入任务分组"></el-input>
                        </el-form-item>
                    </el-col>
                    <!-- 调用方法 -->
                    <el-col :span="24">
                        <el-form-item prop="invokeTarget">
                            <template #label>
                                <span>
                                    调用方法
                                    <el-tooltip placement="top">
                                        <template #content>
                                            <div>
                                                Bean调用示例：timedTask.ryNoParams
                                                <br />Class类调用示例：com.ican.quartz.task.RyTask.ryParams
                                                <br />参数说明：支持字符串，布尔类型，长整型，浮点型，整型
                                            </div>
                                        </template>
                                        <el-icon><question-filled /></el-icon>
                                    </el-tooltip>
                                </span>
                            </template>
                            <el-input v-model="taskForm.invokeTarget" placeholder="请输入调用目标字符串"></el-input>
                        </el-form-item>
                    </el-col>
                    <!-- cron表达式 -->
                    <el-col :span="24">
                        <el-form-item label="cron表达式" prop="cronExpression">
                            <el-input v-model="taskForm.cronExpression" placeholder="请输入cron执行表达式">
                                <template #append>
                                    <el-button type="primary" @click="handleShowCron">
                                        生成表达式
                                        <i class="el-icon-time el-icon--right"></i>
                                    </el-button>
                                </template>
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <!-- 错误执行策略 -->
                    <el-col :span="24">
                        <el-form-item label="错误执行策略" prop="misfirePolicy">
                            <el-radio-group v-model="taskForm.misfirePolicy">
                                <el-radio-button label="1">立即执行</el-radio-button>
                                <el-radio-button label="2">执行一次</el-radio-button>
                                <el-radio-button label="3">放弃执行</el-radio-button>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                    <!-- 是否并发 -->
                    <el-col :span="12">
                        <el-form-item label="是否并发" prop="concurrent">
                            <el-radio-group v-model="taskForm.concurrent">
                                <el-radio-button label="1">允许</el-radio-button>
                                <el-radio-button label="0">禁止</el-radio-button>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                    <!-- 状态 -->
                    <el-col :span="12">
                        <el-form-item label="状态">
                            <el-radio-group v-model="taskForm.status">
                                <el-radio :label="1">暂停</el-radio>
                                <el-radio :label="0">运行</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                    <!-- 备注 -->
                    <el-col :span="12">
                        <el-form-item label="备注" prop="remark">
                            <el-input v-model="taskForm.remark" placeholder="备注信息" :autosize="{ minRows: 2, maxRows: 4 }"
                                resize="none" type="textarea"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="primary" @click="submitForm">确 定</el-button>
                    <el-button @click="addOrUpdate = false">取 消</el-button>
                </div>
            </template>
        </el-dialog>
        <!-- Cron表达式生成器 -->
        <el-dialog title="Cron表达式生成器" v-model="openCron" append-to-body destroy-on-close>
            <crontab ref="crontabRef" @hide="openCron = false" @fill="crontabFill" :expression="expression"></crontab>
        </el-dialog>
        <!-- 任务日志详细 -->
        <el-dialog title="任务详细" v-model="taskView" width="700px" append-to-body>
            <el-form ref="form" :model="taskForm" label-width="120px">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="任务编号：">{{ taskForm.id }}</el-form-item>
                        <el-form-item label="任务名称：">{{ taskForm.taskName }}</el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="任务分组：">{{ taskForm.taskGroup }}</el-form-item>
                        <el-form-item label="创建时间：">{{ formatDateTime(taskForm.createTime) }}</el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="cron表达式：">{{ taskForm.cronExpression }}</el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="下次执行时间：">{{ formatDateTime(taskForm.nextValidTime) }}</el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="调用目标方法：">{{ taskForm.invokeTarget }}</el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="任务状态：">
                            <div v-if="taskForm.status == 0">正常</div>
                            <div v-else-if="taskForm.status == 1">失败</div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="是否并发：">
                            <div v-if="taskForm.concurrent == 1">允许</div>
                            <div v-else-if="taskForm.concurrent == 0">禁止</div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="错误执行策略：">
                            <div v-if="taskForm.misfirePolicy == 0">默认策略</div>
                            <div v-else-if="taskForm.misfirePolicy == 1">立即执行</div>
                            <div v-else-if="taskForm.misfirePolicy == 2">执行一次</div>
                            <div v-else-if="taskForm.misfirePolicy == 3">放弃执行</div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="备注：">{{ taskForm.remark }}</el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="taskView = false">关 闭</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { addTask, deleteTask, getTaskList, runTask, updateTask, updateTaskStatus } from "@/api/task";
import { Task, TaskForm, TaskQuery } from "@/api/task/types";
import Crontab from '@/components/Crontab/index.vue';
import { formatDate, formatDateTime } from "@/utils/date";
import { messageConfirm, notifySuccess } from "@/utils/modal";
import { FormInstance, FormRules } from "element-plus";
import { onMounted, reactive, ref, toRefs } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();
const taskFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
    taskName: [{ required: true, message: "任务名称不能为空", trigger: "blur" }],
    invokeTarget: [{ required: true, message: "调用目标字符串不能为空", trigger: "blur" }],
    cronExpression: [{ required: true, message: "cron执行表达式不能为空", trigger: "blur" }]
});
const data = reactive({
    count: 0,
    taskView: false,
    showSearch: true,
    loading: false,
    title: "",
    addOrUpdate: false,
    queryParams: {
        current: 1,
        size: 10,
    } as TaskQuery,
    status: [
        {
            value: 0,
            label: "运行",
        },
        {
            value: 1,
            label: "暂停",
        },
    ],
    taskIdList: [] as number[],
    taskForm: {} as TaskForm,
    taskList: [] as Task[],
    openCron: false,
    expression: "",
});
const {
    count,
    taskView,
    showSearch,
    loading,
    title,
    addOrUpdate,
    queryParams,
    status,
    taskIdList,
    taskForm,
    taskList,
    openCron,
    expression,
} = toRefs(data);
const handleChangeStatus = (task: Task) => {
    let text = task.status === 0 ? "开启" : "停止";
    messageConfirm("确定要" + text + "该任务吗?").then(() => {
        updateTaskStatus({ id: task.id, status: task.status }).then(({ data }) => {
            if (data.flag) {
                notifySuccess(data.msg);
            } else {
                task.status = task.status === 0 ? 1 : 0;
            }
        })
    }).catch(() => { task.status = task.status === 0 ? 1 : 0; });
};
const handleTaskLog = () => {
    router.push("/log/task");
};
const crontabFill = (value: string) => {
    taskForm.value.cronExpression = value;
};
const handleShowCron = () => {
    expression.value = taskForm.value.cronExpression;
    openCron.value = true;
};
const openModel = (task?: Task) => {
    taskFormRef.value?.resetFields();
    if (task !== undefined) {
        taskForm.value = JSON.parse(JSON.stringify(task));
        title.value = "修改任务";
    } else {
        taskForm.value = {
            id: undefined,
            taskName: "",
            taskGroup: "",
            invokeTarget: "",
            cronExpression: "",
            misfirePolicy: 3,
            concurrent: 0,
            status: 1,
            remark: "",
            nextValidTime: new Date(),
            createTime: "",
        };
        title.value = "添加任务";
    }
    addOrUpdate.value = true;
};
const handleCommand = (command: string, row: Task) => {
    switch (command) {
        case "handleRun":
            handleRun(row);
            break;
        case "handleView":
            handleView(row);
            break;
        default:
            break;
    }
};
const handleRun = (task: Task) => {
    runTask({
        id: task.id,
        taskGroup: task.taskGroup,
    }).then(({ data }) => {
        if (data.flag) {
            notifySuccess(data.msg);
        }
    });
};
const handleView = (task: Task) => {
    taskForm.value = task;
    taskView.value = true;
};
const submitForm = () => {
    taskFormRef.value?.validate((valid) => {
        if (valid) {
            if (taskForm.value.id !== undefined) {
                updateTask(taskForm.value).then(({ data }) => {
                    if (data.flag) {
                        notifySuccess(data.msg);
                        getList();
                    }
                    addOrUpdate.value = false;
                })
            } else {
                addTask(taskForm.value).then(({ data }) => {
                    if (data.flag) {
                        notifySuccess(data.msg);
                        getList();
                    }
                    addOrUpdate.value = false;
                });
            }
        }
    })
};
const handleSelectionChange = (selection: Task[]) => {
    taskIdList.value = selection.map((item) => item.id);
};
const handleDelete = (id?: number) => {
    let ids: number[] = [];
    if (id == undefined) {
        ids = taskIdList.value;
    } else {
        ids = [id];
    }
    messageConfirm("确认删除已选中的数据项?").then(() => {
        deleteTask(ids).then(({ data }) => {
            if (data.flag) {
                notifySuccess(data.msg);
                getList();
            }
        });
    }).catch(() => { });

};
const getList = () => {
    loading.value = true;
    getTaskList(queryParams.value).then(({ data }) => {
        taskList.value = data.data.recordList;
        count.value = data.data.count;
        loading.value = false;
    });
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