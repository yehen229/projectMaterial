<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref, watch } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";
import type { Action } from "element-plus";
import {
  serverUserRoleAdd, //增加
  serverUserRoleDelete, //删除
  serverUserRoleUpdate, //修改
  serverGetUserRolePage, //得到指定页面数据
  serverGetUserRolePageView, //根据指定页面数据
} from "@/server/auth/SysUserRole";

import { serverGetRolePage } from "@/server/auth/SysRole";

//服务器返回到前端的类型
import {
  IServerResponseData,
  IServerPage,
  IServerSysUser,
} from "@/server/ServerType";

import {
  clearCookies,
  setUserCookies,
  getToken,
  getUserID,
  getUserName,
  getUserRealName,
  hasRole,
  isTeacher,
  isAdmin,
  isStudent,
  getUserPageSize,
  setUserPageSize,
} from "@/cookies/user";

const router = useRouter();

interface Props {
  dialogVisible: boolean; //对话框是否可见
}

const props = withDefaults(defineProps<Props>(), {
  dialogVisible: false,
});

const form = reactive<IServerSysUser>({
  id: "", //id,主键
  userName: "", //user_name,学号或工号等，用户登录ID
  realName: "", //real_name,用户名称
  password: "123456", //pwd,密码
  userType: "学生", //user_type,用户类型：学生、教师等
  tel: "", //tel,电话
  email: "", //email,电子邮件
  department: "", //department,系部
  school: "", //school,学院
  title: "", //title,职称
  post: "", //post,职务
  resume: "", //resume,个人简历
  naturalClass: "", //natural_class,班级
});

const rules = reactive<FormRules>({
  userName: [
    { required: true, message: "请输入学号或工号", trigger: "blur" },
    { min: 1, max: 64, message: "字数范围为：1-64", trigger: "blur" },
  ],
  realName: [
    { required: true, message: "请输入用户名称", trigger: "blur" },
    { min: 1, max: 64, message: "字数范围为：1-64", trigger: "blur" },
  ],
});

//event
const emit = defineEmits<{
  (e: "onDilalogOk", sysUser: IServerSysUser): void;
  (e: "onDilalogCancel"): void;
}>();

const dialogFormVisible = computed({
  get() {
    return props.dialogVisible;
  },
  set(val) {
    return val;
  },
});

const userTypeStudent = computed(() => {
  return form.userType == "学生";
});

const onOpenDialog = async () => {};

const handleClose = () => {
  emit("onDilalogCancel");
};
const onOk = () => {
  if (!form.userName || form.userName.length == 0) {
    ElMessageBox.alert("学号或工号不能为空", "提示", {
      confirmButtonText: "确定",
    });

    return;
  }
  if (!form.realName || form.realName.length == 0) {
    ElMessageBox.alert("用户名称不能为空", "提示", {
      confirmButtonText: "确定",
    });

    return;
  }

  emit("onDilalogOk", form);
};

const onCancel = () => {
  emit("onDilalogCancel");
};
</script>

<template>
  <div>
    <el-dialog
      title="添加用户"
      v-model="dialogFormVisible"
      :before-close="handleClose"
      width="600px"
      @open="onOpenDialog"
      draggable
    >
      <el-form
        label-width="100px"
        :model="form"
        :rules="rules"
        ref="ruleFormRef"
      >
        <el-form-item v-if="userTypeStudent" label="学号" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item v-else label="工号" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入工号" />
        </el-form-item>

        <el-form-item label="用户名称" prop="realName">
          <el-input v-model="form.realName" placeholder="请输入用户真实名称" />
        </el-form-item>
        <el-form-item label="用户类型" prop="userType">
          <el-radio-group v-model="form.userType">
            <el-radio label="学生" value="学生" />
            <el-radio label="教师" value="教师" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="电话" prop="tel">
          <el-input v-model="form.tel" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="电子邮件" prop="email">
          <el-input v-model="form.email" placeholder="请输入电子邮件" />
        </el-form-item>
        <el-form-item label="学院" prop="school">
          <el-input v-model="form.school" placeholder="请输入学院" />
        </el-form-item>
        <el-form-item label="系部" prop="department">
          <el-input v-model="form.department" placeholder="请输入系部" />
        </el-form-item>
        <el-form-item v-if="userTypeStudent" label="班级" prop="naturalClass">
          <el-input v-model="form.naturalClass" placeholder="请输入班级" />
        </el-form-item>

        <el-form-item v-if="!userTypeStudent" label="职称" prop="title">
          <el-input v-model="form.title" placeholder="请输入职称" />
        </el-form-item>
        <el-form-item v-if="!userTypeStudent" label="职务" prop="post">
          <el-input v-model="form.post" placeholder="请输入职务" />
        </el-form-item>
        <el-form-item v-if="!userTypeStudent" label="个人简历" prop="resume">
          <el-input
            v-model="form.resume"
            type="textarea"
            :rows="2"
            placeholder="请输入个人简"
          />
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="onCancel">取消</el-button>
          <el-button type="primary" @click="onOk()"> 确定 </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
