<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref, watch } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";
import type { Action } from "element-plus";
import {
  IServerCompany,
  IServerCompanyUser,
  IServerCompanyUserView,
} from "@/server/types/system/company";

import {
  serverCompanyAdd,
  serverCompanyDelete,
  serverCompanyUpdate,
  serverGetCompanyPage,
} from "@/server/system/company";

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

const form = reactive<IServerCompany>({
  id: "", //id,主键
  name: "", //单位名称单位名称
  companyType: "设计单位", //company_type,单位类型：设计单位、设计部、工程部、监理单位、总包单位等单位类型：设计单位、设计部、工程部、监理单位、总包单位等
  note: "", //note,备注备注
  deletedAt: new Date(),
});

const rules = reactive<FormRules>({
  name: [
    { required: true, message: "请输入单位名称", trigger: "blur" },
    { min: 1, max: 64, message: "字数范围为：1-64", trigger: "blur" },
  ],
  companyType: [{ required: true, message: "请选择单位类型", trigger: "blur" }],
});

//event
const emit = defineEmits<{
  (e: "onDilalogOk", company: IServerCompany): void;
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

const onOpenDialog = async () => {};

const handleClose = () => {
  emit("onDilalogCancel");
};
const onOk = () => {
  if (!form.name || form.name.length == 0) {
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
      title="添加单位"
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
        <el-form-item label="单位名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入单位名称" />
        </el-form-item>

        <el-form-item label="单位类型" prop="companyType">
          <el-radio-group v-model="form.companyType">
            <el-radio label="设计单位" value="设计单位" />
            <el-radio label="建设单位" value="建设单位" />
            <el-radio label="监理单位" value="监理单位" />
            <el-radio label="总包单位" value="总包单位" />
            <el-radio label="厂家" value="厂家" />
          </el-radio-group>
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
