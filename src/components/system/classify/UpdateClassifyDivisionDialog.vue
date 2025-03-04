<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref, watch } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";
import type { Action } from "element-plus";
import {
  IServerMaterial,
  IServerMaterialBrand,
  IServerMaterialBrandView,
  IServerMaterialClassifyDivision,
  IServerMaterialClassifyDivisionTreeItem,
  IServerMaterialClassifyGroup,
  IServerMaterialClassifyGroupTreeItem,
  IServerMaterialClassifyGroupView,
  IServerMaterialClassifySection,
  IServerMaterialClassifySectionView,
  IServerMaterialClassifyTree,
  IServerMaterialPhoto,
  IServerMaterialPhotoView,
} from "@/server/types/system/material";

import {
  IServerBrand,
  IServerBrandPublic,
  IServerBrandPublicView,
} from "@/server/types/system/brand";

import {
  serverMaterialClassifySectionAdd,
  serverMaterialClassifySectionDelete,
  serverMaterialClassifySectionDeleteById,
  serverMaterialClassifySectionUpdate,
  serverGetMaterialClassifySectionById,
  serverGetMaterialClassifyTree,
  serverGetMaterialClassifySectionPage,
} from "@/server/system/materialclassifysection";

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
  materialClassifyDivision: IServerMaterialClassifyDivision;
}

const props = withDefaults(defineProps<Props>(), {
  dialogVisible: false,
});

const form = reactive<IServerMaterialClassifyDivision>({
  id: "", //id,主键
  name: "", //名称
  note: "", //note,备注备注
  deletedAt: new Date(),
});

const rules = reactive<FormRules>({
  name: [
    { required: true, message: "请输入名称", trigger: "blur" },
    { min: 1, max: 64, message: "字数范围为：1-64", trigger: "blur" },
  ],
});

//event
const emit = defineEmits<{
  (
    e: "onDilalogOk",
    materialClassifyDivision: IServerMaterialClassifyDivision
  ): void;
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

const onOpenDialog = async () => {
  form.id = props.materialClassifyDivision.id; //id,主键
  form.name = props.materialClassifyDivision.name; //name,名称
  form.note = props.materialClassifyDivision.note; //note,备注
};

const handleClose = () => {
  emit("onDilalogCancel");
};
const onOk = () => {
  if (!form.name || form.name.length == 0) {
    ElMessageBox.alert("名称不能为空", "提示", {
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
      title="编辑大类"
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
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="名称" prop="note">
          <el-input v-model="form.note" placeholder="请输入备注" />
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
