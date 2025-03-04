<script setup lang="ts">
import { reactive, ref, onMounted } from "vue";
import axios from "axios";

import { useRouter } from "vue-router/dist/vue-router";

import PythonLogo from "@/assets/python-logo-only.png";

import { View, Hide, Refresh, Help } from "@element-plus/icons-vue";

import {
  serverLogin,
  serverGetPublicKey,
  serverGetCaptchaJpg,
} from "@/server/account/User";

import { IServerSysUserLoginResult } from "@/server/ServerType";

import { JSEncrypt } from "jsencrypt";

import { waitUntil } from "@/utils/waitUntil";
import {
  setUserCookies,
  getUserName,
  getUserRealName,
  hasRole,
  isTeacher,
  isAdmin,
  isStudent,
  getUserPageSize,
  setUserPageSize,
} from "@/cookies/user";

import { userStore } from "@/store/user";

import MobileLogo from "@/assets/MobileLogo.png";
import WorkLogo from "@/assets/WorkLogo.png";

const store = userStore();

const show = ref(false);
const Authorization = ref("");
const captchaLabel = ref("验证码");
const captchaLabelCountDown = ref(120);
const showPwd = ref(false); //是否显示密码明文
const formLogin = reactive({
  username: "",
  password: "",
  captchaCode: "",
  captchaKey: "",
  code: "",
});

const router = useRouter();

onMounted(async () => {
  await onRefreshCode();
});

/**
 * 单击登录按钮
 */
const onLoginClick = async () => {
  let publicKeyResponse = await serverGetPublicKey();

  if (publicKeyResponse) {
    if ((publicKeyResponse.code = 200)) {
      //获得了公钥，使用公钥加密，服务器端使用私钥解密
      let encrypt = new JSEncrypt();
      let publicKey = publicKeyResponse.data;
      encrypt.setPublicKey(publicKey);
      let encodePassword = encrypt.encrypt(formLogin.password);
      if (typeof encodePassword == "string") {
        let ret = await serverLogin(
            formLogin.username,
            encodePassword,
            formLogin.code,
            formLogin.captchaKey
        );
        if (ret && ret.code == 200) {
          let data: IServerSysUserLoginResult = ret.data;
          console.log(data);
          setUserCookies(data);
          console.log(data);

          router.push({ path: `/` });
        } else {
          console.log(ret);
        }
      }
    }
  }
};

const countDownTimerId = ref(0);

const countDownTimer = () => {
  window.clearInterval(countDownTimerId.value);

  captchaLabelCountDown.value = 120;

  countDownTimerId.value = window.setInterval(countDownTimerHelper, 1000);
};

const countDownTimerHelper = async () => {
  if (captchaLabelCountDown.value <= 0) {
    window.clearInterval(countDownTimerId.value);
    captchaLabel.value = "验证码";
    return;
  }

  captchaLabel.value =
      "验证码(有效时间剩余" + captchaLabelCountDown.value + "秒)";

  captchaLabelCountDown.value -= 1;
};

/**
 * 刷新按钮，从服务器端得到新的验证码
 */
const onRefreshCode = async () => {
  let ret = await serverGetCaptchaJpg();
  console.log(ret);
  if (ret) {
    if ((ret.code = 200)) {
      formLogin.captchaCode = ret.data.code; //验证图像
      formLogin.captchaKey = ret.data.key; //验证图像对应的key
      formLogin.code = "";
    }

    countDownTimer();
  }
};
function onClickBack() {
  show.value = false;
}
</script>

<template>
  <div class="login-page">
    <div class="login-logo">
      <img :src="MobileLogo" style=" width: 2% ; background-color: aliceblue; border-radius: 50%; margin-right:10px" />
      全周期工程建设物料智能管理系统
    </div>
    <div class="login-body">
      <div class="work-logo-box">
        <img :src="WorkLogo" style="width:100%;"/>
      </div>
      <div class="login-container" style="width:45%">
        <el-form
            class="login-form"
            label-position="top"
            label-width="80%"
            :model="formLogin"
            style="max-width: 46%"
        >
          <h2 style="margin-bottom: 5px;font-weight: bolder;">登录</h2>
          <div style="padding-bottom: 30px;">Hi !  欢迎回来</div>
          <el-form-item>
            <label for="username">账号</label>
            <el-input
                id="username"
                v-model="formLogin.username"
                placeholder="请输入电话号码"
                @keydown.enter="onLoginClick"
            />
          </el-form-item>
          <el-form-item>
            <label for="password">密码</label>
            <el-input
                id="password"
                v-model="formLogin.password"
                :type="showPwd ? 'text' : 'password'"
                placeholder="请输入密码"
                @keydown.enter="onLoginClick"
            >
              <template #append>
                <el-button
                    :icon="showPwd ? Hide : View"
                    @click="showPwd = !showPwd"
                />
              </template>
            </el-input>
          </el-form-item>

          <el-form-item>
            <div>
              <label for="captcha">{{ captchaLabel }}</label>
            </div>
            <el-col :span="16">
              <el-input
                  id="captcha"
                  v-model="formLogin.code"
                  placeholder="请输入验证码"
                  @keydown.enter="onLoginClick"
              >
                <template #append>
                  <el-button :icon="Refresh" @click="onRefreshCode" />
                </template>
              </el-input>
            </el-col>

            <el-col :span="8">
              <img
                  class="login-code"
                  alt="验证码"
                  id="codeImg"
                  :src="formLogin.captchaCode"
                  @click="onRefreshCode"
              />
            </el-col>
          </el-form-item>

          <el-button class="login-button" type="primary" @click="onLoginClick" v-if="show === false">
            登录
          </el-button>

          <el-button type="primary" @click="onClickBack" v-if="show === true">
            返回
          </el-button>
          <!--
                  <div style="margin-top: 2em">
                    <el-link
                      href="https://docs.pythonkaoshi.com"
                      :icon="Help"
                      target="_blank"
                      :underline="false"
                      >帮助中心</el-link
                    >
                  </div> -->
        </el-form>
      </div>
    </div>

  </div>
</template>

<style scoped>

.el-input {
  border-color: #0278fc;
}

.login-page{
  background-image: linear-gradient(to bottom, #3403b4, #1c7cec);
  height: 100vh;
  font-family: Microsoft JhengHei;
}

.login-body {
  display: flex;
  justify-content: space-between;

}

.login-form {
  background-color: white;
  padding: 25px 35px 50px 35px;
  border-radius: 5%;
  text-align: left;
}

.work-logo-box {
  flex: 1;
  padding-left: 10%;
  padding-top: 5%;
}

.login-button {
  width: 100%;
  height: 40px;
  background-color: #0278fc;
  margin-top: 20px;
}

.login-container {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  flex-direction: column;
  margin-top: 50px;
  text-align: center;
}

.login-code {
  width: 100px;
  height: 30px;
  vertical-align: top;
  text-align: center;
}

.login-logo {
  padding-left: 20px;
  padding-top: 20px;
  font-size: 21px;
  color: #fff;
  display: flex;
  align-items: center;
}
</style>