<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref, watch } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";
import type { Action } from "element-plus";
import { async } from "../../../server/system/companyuser";
import {
  IServerCompany,
  IServerCompanyUser,
  IServerCompanyUserView,
  IServerCompanyUserForm,
} from "@/server/types/system/company";

import {
  serverCompanyAdd,
  serverCompanyDelete,
  serverCompanyUpdate,
  serverGetCompanyPage,
  serverGetAllCompanyList,
  serverGetAllCompanyListByCompanyType,
} from "@/server/system/company";

import {
  serverCompanyUserAdd,
  serverCompanyUserAddByForm,
  serverCompanyUserDelete,
  serverCompanyUserDeleteById,
  serverCompanyUserUpdate,
  serverGetCompanyUserById,
  serverGetCompanyUserListByCompanyId,
  serverGetCompanyUserListByUserId,
  serverGetCompanyUserPage,
  serverGetCompanyUserPageView,
  serverGetCompanyUserPageViewByUserName,
  serverGetCompanyUserPageViewByProjectName,
  serverGetCompanyUserPageViewByCompanyName,
} from "@/server/system/companyuser";

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

const form = reactive<IServerCompanyUserForm>({
  companyUserId: "",
  companyId: "",
  userRealName: "", //单位名称单位名称
  tel: "",
  email: "", //
});

const companyList = ref<Array<IServerCompany>>();

const rules = reactive<FormRules>({
  userRealName: [
    { required: true, message: "请输入用户名称", trigger: "blur" },
    { min: 1, max: 64, message: "字数范围为：1-64", trigger: "blur" },
  ],
  tel: [
    { required: true, message: "请输入用户电话", trigger: "blur" },
    { min: 1, max: 64, message: "字数范围为：1-64", trigger: "blur" },
  ],
  companyId: [{ required: true, message: "请选择单位类型", trigger: "blur" }],
});

//event
const emit = defineEmits<{
  (e: "onDilalogOk", form: IServerCompanyUserForm): void;
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

const getAllCompanyList = async () => {
  const ret = await serverGetAllCompanyList();

  if (ret && ret.code == 200) {
    companyList.value = ret.data;
  }
};

const onOpenDialog = async () => {
  await getAllCompanyList();
};

const handleClose = () => {
  emit("onDilalogCancel");
};
const onOk = async () => {
  if (!form.userRealName || form.userRealName.length == 0) {
    ElMessageBox.alert("用户名称不能为空", "提示", {
      confirmButtonText: "确定",
    });
    return;
  }

  if (!form.tel || form.tel.length == 0) {
    ElMessageBox.alert("用户电话不能为空", "提示", {
      confirmButtonText: "确定",
    });

    return;
  }

  if (!form.companyId || form.companyId.length == 0) {
    ElMessageBox.alert("单位名称不能为空", "提示", {
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
        <el-form-item label="用户名称" prop="userRealName">
          <el-input v-model="form.userRealName" placeholder="请输入用户名称" />
        </el-form-item>
        <el-form-item label="电话" prop="tel">
          <el-input v-model="form.tel" placeholder="请输入用户电话" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入用户邮箱" />
        </el-form-item>

        <el-form-item label="单位名称" prop="companyId">
          <el-select v-model="form.companyId" placeholder="选择单位名称">
            <el-option
              v-for="(item, index) in companyList"
              :key="index"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
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
