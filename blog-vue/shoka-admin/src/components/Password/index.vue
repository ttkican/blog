<template>
    <div>
        <svg-icon @click="openModel" icon-class="password" size="1.2rem" />
        <div>
            <el-dialog width="500px" title="修改密码" v-model="open">
                <el-form ref="ruleFormRef" :model="ruleForm" status-icon label-width="120px" :rules="rules">
                    <el-form-item prop="oldPassword" label="旧密码">
                        <el-input style="width: 250px" v-model="ruleForm.oldPassword" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item prop="newPassword" label="新密码">
                        <el-input style="width: 250px" v-model="ruleForm.newPassword" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item prop="checkPassword" label="确认密码">
                        <el-input style="width: 250px" v-model="ruleForm.checkPassword" autocomplete="off"></el-input>
                    </el-form-item>
                </el-form>
                <template #footer>
                    <div class="dialog-footer">
                        <el-button type="primary" @click="submitForm">确 定</el-button>
                        <el-button @click="open = false">取 消</el-button>
                    </div>
                </template>
            </el-dialog>
        </div>
    </div>
</template>

<script setup lang="ts">
import { updateAdminPassword } from "@/api/user";
import { Password } from "@/api/user/types";
import { notifySuccess } from "@/utils/modal";
import { FormInstance, FormRules } from "element-plus";
import { reactive, ref } from "vue";


const ruleFormRef = ref<FormInstance>();
const open = ref(false);
const ruleForm = ref<Password>({
    oldPassword: '',
    newPassword: '',
    checkPassword: '',
});
const openModel = () => {
    open.value = true;
    ruleForm.value = {
        oldPassword: '',
        newPassword: '',
        checkPassword: '',
    };
    ruleFormRef.value?.resetFields();
};
const submitForm = () => {
    ruleFormRef.value?.validate((valid) => {
        if (valid) {
            updateAdminPassword(ruleForm.value).then(({ data }) => {
                if (data.flag) {
                    notifySuccess(data.msg);
                }
                open.value = false;
            })
        }
    })
};
const validatePass2 = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error("请再次输入密码"))
    } else if (value !== ruleForm.value.newPassword) {
        callback(new Error("两次输入的密码不一致"))
    } else {
        callback()
    }
};
const rules = reactive<FormRules>({
    oldPassword: [{
        required: true, message: "请输入密码", trigger: "blur"
    }, { min: 6, message: "密码不能少于6位", trigger: "blur" }],
    newPassword: [
        { required: true, message: "请输入密码", trigger: "blur" },
        { min: 6, message: "密码不能少于6位", trigger: "blur" }],
    checkPassword: [
        { required: true, validator: validatePass2, trigger: 'blur' },
        { min: 6, message: "密码不能少于6位", trigger: "blur" }],
});

</script>

<style scoped>

</style>