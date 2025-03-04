<script setup lang="ts">
import { reactive, ref, onMounted } from "vue";
import axios from "axios";

import { useRouter } from "vue-router/dist/vue-router";

import PythonLogo from "@/assets/python-logo-only.png";

import { View, Hide, Refresh } from "@element-plus/icons-vue";
import type { FormInstance, FormRules } from "element-plus";
import {
  serverLogin,
  serverLoginOut,
  serverGetPublicKey,
  serverGetCaptchaJpg,
  serverUserAdd,
  serverUserDelete,
  serverUserUpdate,
  serverUserUpdateOwnInfo,
  serverUserUpdateOwnPwd,
  serverUserResetPwd,
  serverGetUserByUserName,
  serverGetUserByUserId,
  serverGetUserPage,
} from "@/server/account/User";

import { JSEncrypt } from "jsencrypt";

import { waitUntil } from "@/utils/waitUntil";
import {
  setUserCookies,
  getUserName,
  getUserID,
  getUserRealName,
  hasRole,
  isTeacher,
  isAdmin,
  isStudent,
  getUserPageSize,
  setUserPageSize,
} from "@/cookies/user";

import { userStore } from "@/store/user";

//服务器返回到前端的类型
import { IServerUser } from "@/server/types/User";

const store = userStore();

const show = ref(false);
const Authorization = ref("");
const captchaLabel = ref("验证码");
const captchaLabelCountDown = ref(120);
const showPwd = ref(false); //是否显示密码明文

const router = useRouter();

const userView = ref<IServerSysUser>();

const ruleFormRef = ref<FormInstance>();

const ruleForm = reactive({
  userName: "",
  realName: "",
  password: "",
  tel: "",
});

const rules = reactive<FormRules>({});

onMounted(async () => {
  await getUserFromServer();
});

const getUserFromServer = async () => {
  const id = getUserID();
  if (id) {
    const ret = await serverGetUserByUserId(id);
    if (ret && ret.code == 200) {
      userView.value = ret.data;

      ruleForm.userName = ret.data.userName;
      ruleForm.realName = ret.data.realName;

      ruleForm.tel = ret.data.tel;
    }
  }
};

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid, fields) => {
    if (valid && userView.value) {
      const user: IServerSysUser = {
        id: userView.value.id, //id,主键
        userName: userView.value.userName, //user_name,学号或工号等，用户登录ID
        realName: userView.value.realName, //real_name,用户名称
        password: "", //pwd,密码

        tel: ruleForm.tel, //tel,电话
      };

      await serverUserUpdateOwnInfo(user);
      await getUserFromServer();
    } else {
      console.log("error submit!", fields);
    }
  });
};

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
};
</script>

<template>
  <div>
    <h1>个人设置</h1>
    <el-form
      ref="ruleFormRef"
      :rules="rules"
      label-position="top"
      label-width="100px"
      :model="ruleForm"
      style="max-width: 600px"
    >
      <el-form-item label="用户名(用户登录ID)">
        <el-input
          v-model="ruleForm.userName"
          placeholder="请输入登录ID"
          disabled
        />
      </el-form-item>

      <el-form-item label="姓名" prop="realName">
        <el-input
          v-model="ruleForm.realName"
          placeholder="请输入姓名"
          disabled
        />
      </el-form-item>

      <el-form-item label="电话" prop="tel">
        <el-input v-model="ruleForm.tel" placeholder="请输入电话" />
      </el-form-item>

      <el-button type="primary" @click="submitForm(ruleFormRef)">
        修改
      </el-button>
      <el-button @click="resetForm(ruleFormRef)">Reset</el-button>
    </el-form>
  </div>
</template>

<style scoped></style>
