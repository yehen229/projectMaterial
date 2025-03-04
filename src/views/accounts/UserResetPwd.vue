<script setup lang="ts">
import { reactive, ref, onMounted } from "vue";
import axios from "axios";

import { useRouter } from "vue-router/dist/vue-router";

import PythonLogo from "@/assets/python-logo-only.png";

import { View, Hide, Refresh } from "@element-plus/icons-vue";
import type { FormInstance, FormRules } from "element-plus";
import { ElMessage, ElMessageBox } from "element-plus";
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

import { userStore } from "@/store/user";

//服务器返回到前端的类型
import { IServerUser } from "@/server/types/User";

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

const store = userStore();

const show = ref(false);
const Authorization = ref("");
const captchaLabel = ref("验证码");
const captchaLabelCountDown = ref(120);
const showPwd = ref(false); //是否显示密码明文

const router = useRouter();

onMounted(async () => {
  await getUserFromServer();
});

const getUserFromServer = async () => {
  const id = getUserID();
  console.log(id);
  if (id) {
    const ret = await serverGetUserByUserId(id);
    if (ret && ret.code == 200) {
      userView.value = ret.data;
      ruleForm.userName = ret.data.userName;
      ruleForm.realName = ret.data.realName;
    }
  }
};

const userView = ref<IServerUser>();

const ruleFormRef = ref<FormInstance>();

const ruleForm = reactive({
  userName: "",
  realName: "",
  oldPwd: "",
  newPwd: "",
  confirmNewPwd: "",
});

const validatePass = (rule: any, value: any, callback: any) => {
  if (value === "") {
    callback(new Error("请输入新密码"));
  } else {
    if (value.length <= 3 || value.length >= 64)
      callback(new Error("新密码长度不够"));
    if (ruleForm.confirmNewPwd !== "") {
      if (!ruleFormRef.value) return;
      ruleFormRef.value.validateField("checkPass", () => null);
    }
    callback();
  }
};
const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === "") {
    callback(new Error("请再次输入新密码"));
  } else if (value !== ruleForm.newPwd) {
    callback(new Error("两次新密码不匹配！"));
  } else {
    callback();
  }
};

const rules = reactive<FormRules>({
  newPwd: [{ validator: validatePass, trigger: "blur" }],
  confirmNewPwd: [{ validator: validatePass2, trigger: "blur" }],
});

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid, fields) => {
    if (valid && userView.value) {
      const user: IServerUser = {
        id: userView.value.id, //id,主键
        userName: "", //user_name,学号或工号等，用户登录ID
        realName: ruleForm.newPwd, //real_name,用户名称
        password: ruleForm.oldPwd, //pwd,密码
        tel: "", //tel,电话
      };

      try {
        const ret = await serverUserUpdateOwnPwd(user);
        if (ret) {
          console.log(ret);
          if ((ret.code = 200))
            ElMessage({
              type: "success",
              message: "成功修改密码",
            });
        }
      } catch (error) {
        ElMessage({
          type: "error",
          message: "修改密码失败:" + error.message,
        });
      }
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
    <h1>重置密码</h1>
    <el-form
      ref="ruleFormRef"
      :rules="rules"
      label-position="top"
      label-width="100px"
      :model="ruleForm"
      style="max-width: 600px"
    >
      <el-form-item label="用户名(用户登录ID)">
        <el-input v-model="ruleForm.userName" disabled />
      </el-form-item>

      <el-form-item label="姓名" prop="realName">
        <el-input v-model="ruleForm.realName" disabled />
      </el-form-item>

      <el-form-item label="旧密码" prop="oldPwd">
        <el-input
          v-model="ruleForm.oldPwd"
          placeholder="旧密码"
          type="password"
          show-password
        />
      </el-form-item>

      <el-form-item label="新密码" prop="newPwd">
        <el-input
          v-model="ruleForm.newPwd"
          placeholder="新密码"
          type="password"
          show-password
        />
      </el-form-item>

      <el-form-item label="再次输入新密码" prop="confirmNewPwd">
        <el-input
          v-model="ruleForm.confirmNewPwd"
          show-password
          type="password"
          placeholder="再次输入新密码"
        />
      </el-form-item>

      <el-button type="primary" @click="submitForm(ruleFormRef)">
        修改
      </el-button>
      <el-button @click="resetForm(ruleFormRef)">清除</el-button>
    </el-form>
  </div>
</template>

<style scoped></style>
