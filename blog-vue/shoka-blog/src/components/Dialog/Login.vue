<template>
  <n-modal class="bg" v-model:show="dialogVisible" preset="dialog" :show-icon="false" transform-origin="center"
    :block-scroll="false">
    <n-input class="mt-11" v-model:value="loginForm.username" placeholder="邮箱号" @keyup.enter="handlelogin"></n-input>
    <n-input class="mt-11" v-model:value="loginForm.password" type="password" show-password-on="click" placeholder="密码"
      @keyup.enter="handlelogin"></n-input>
    <n-button class="mt-11" color="#ed6ea0" style="width: 100%" :loading="loading" @click="handlelogin">
      登 录
    </n-button>
    <div class="mt-10 login-tip">
      <span class="colorFlag" @click="handleRegister">立即注册</span>
      <span class="colorFlag" @click="handleForget">忘记密码?</span>
    </div>
    <div>
      <div class="social-login-title">社交账号登录</div>
      <div class="social-login-wrapper">
        <svg-icon class="icon" icon-class="qq" size="2rem" color="#00aaee" v-if="showLogin('qq')"
          @click="qqLogin"></svg-icon>
        <svg-icon class="icon" icon-class="gitee" size="2rem" v-if="showLogin('gitee')" @click="giteeLogin"></svg-icon>
        <svg-icon class="icon" icon-class="github" size="2rem" v-if="showLogin('github')" @click="githubLogin"></svg-icon>
      </div>
    </div>
  </n-modal>
</template>

<script setup lang="ts">
import { login } from "@/api/login";
import { LoginForm } from "@/api/login/types";
import config from "@/assets/js/config";
import { useAppStore, useBlogStore, useUserStore } from "@/store";
import { setToken } from "@/utils/token";
const app = useAppStore();
const user = useUserStore();
const blog = useBlogStore();
const route = useRoute();
const loading = ref(false);
const loginForm = ref<LoginForm>({
  username: "",
  password: "",
});
const dialogVisible = computed({
  get: () => app.loginFlag,
  set: (value) => (app.loginFlag = value),
});
const showLogin = computed(
  () => (type: string) => blog.blogInfo.siteConfig.loginList.includes(type)
);
const handleRegister = () => {
  app.setLoginFlag(false);
  app.setRegisterFlag(true);
};
const handleForget = () => {
  app.setLoginFlag(false);
  app.setForgetFlag(true);
};
const qqLogin = () => {
  //保留当前路径
  user.savePath(route.path);
  app.setLoginFlag(false);
  window.open(
    "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=" +
    config.QQ_APP_ID +
    "&redirect_uri=" +
    config.QQ_REDIRECT_URL +
    "&scope=scope&display=display",
    "_self"
  );
};
const giteeLogin = () => {
  //保留当前路径
  user.savePath(route.path);
  app.setLoginFlag(false);
  window.open(
    "https://gitee.com/oauth/authorize?client_id=" +
    config.GITEE_APP_ID +
    "&response_type=code&redirect_uri=" +
    config.GITEE_REDIRECT_URL,
    "_self"
  );
};
const githubLogin = () => {
  //保留当前路径
  user.savePath(route.path);
  app.setLoginFlag(false);
  window.open(
    "https://github.com/login/oauth/authorize?client_id=" +
    config.GITHUB_APP_ID +
    "&redirect_uri=" +
    config.GITHUB_REDIRECT_URL +
    "&scope=user",
    "_self"
  );
};
const handlelogin = () => {
  let reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
  if (!reg.test(loginForm.value.username)) {
    window.$message?.warning("邮箱格式不正确");
    return;
  }
  if (loginForm.value.password.trim().length == 0) {
    window.$message?.warning("密码不能为空");
    return;
  }
  loading.value = true;
  login(loginForm.value).then(({ data }) => {
    if (data.flag) {
      setToken(data.data);
      user.GetUserInfo();
      window.$message?.success("登录成功");
      loginForm.value = {
        username: "",
        password: "",
      };
      app.setLoginFlag(false);
    }
    loading.value = false;
  });
};
</script>

<style scoped>
.login-tip {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.social-login-title {
  margin-top: 1rem;
  color: #b5b5b5;
  font-size: 0.75rem;
  text-align: center;
}

.social-login-title::before {
  content: "";
  display: inline-block;
  background-color: #d8d8d8;
  width: 60px;
  height: 1px;
  margin: 0 12px;
  vertical-align: middle;
}

.social-login-title::after {
  content: "";
  display: inline-block;
  background-color: #d8d8d8;
  width: 60px;
  height: 1px;
  margin: 0 12px;
  vertical-align: middle;
}

.social-login-wrapper {
  text-align: center;
  margin-top: 1.4rem;
}

.icon {
  margin: 0 0.3rem;
}
</style>
