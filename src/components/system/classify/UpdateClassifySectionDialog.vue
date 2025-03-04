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
  serverMaterialClassifyGroupAdd,
  serverMaterialClassifyGroupDelete,
  serverMaterialClassifyGroupDeleteById,
  serverMaterialClassifyGroupUpdate,
  serverGetMaterialClassifyGroupById,
  serverGetMaterialClassifyGroupPage,
  serverGetMaterialClassifyGroupTree,
} from "@/server/system/materialclassifygroup";

import {
  serverMaterialClassifyDivisionAdd,
  serverMaterialClassifyDivisionDelete,
  serverMaterialClassifyDivisionDeleteById,
  serverMaterialClassifyDivisionUpdate,
  serverGetMaterialClassifyDivisionById,
  serverGetMaterialClassifyDivisionPage,
  serverGetMaterialClassifyDivisionAllList,
} from "@/server/system/materialclassifydivision";

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
  materialClassifyDivision?: IServerMaterialClassifyDivision;
  materialClassifyGroup?: IServerMaterialClassifyGroup;
  materialClassifySection?: IServerMaterialClassifySection;
}

const props = withDefaults(defineProps<Props>(), {
  dialogVisible: false,
});

const form = reactive<IServerMaterialClassifySection>({
  id: "", //id,主键
  name: "", //
  note: "", //note,备注备注
  materialClassifyGroupId: "",
  deletedAt: new Date(),
});

const rules = reactive<FormRules>({
  name: [
    { required: true, message: "请输入大类名称", trigger: "blur" },
    { min: 1, max: 64, message: "字数范围为：1-64", trigger: "blur" },
  ],
});

//event
const emit = defineEmits<{
  (
    e: "onDilalogOk",
    materialClassifySection: IServerMaterialClassifySection
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

interface IClassifyOption {
  id: string;
  value: string;
  label: string;
  children: IClassifyOption[];
}

const generateGroupOption = (
  divisionTreeChildren: IServerMaterialClassifyGroupTreeItem[]
) => {
  if (!divisionTreeChildren) return [];
  let temp: IClassifyOption[] = [];
  divisionTreeChildren.forEach((groupTreeItem) => {
    const newChild: IClassifyOption = {
      id: groupTreeItem.materialClassifyGroupView.materialClassifyGroup.id,
      label: groupTreeItem.materialClassifyGroupView.materialClassifyGroup.name,
      value: groupTreeItem.materialClassifyGroupView.materialClassifyGroup.id,
      children: [],
    };
    temp.push(newChild);
  });
  return temp;
};

const generateOption = (tree: IServerMaterialClassifyTree) => {
  classifyOptions.value = [];
  tree.children.forEach((divisionTreeItem) => {
    const children: IClassifyOption[] = generateGroupOption(
      divisionTreeItem.children
    );
    const newChild: IClassifyOption = {
      id: divisionTreeItem.materialClassifyDivision.id,
      label: divisionTreeItem.materialClassifyDivision.name,
      value: divisionTreeItem.materialClassifyDivision.id,
      children: children,
    };

    classifyOptions.value.push(newChild);
  });
};

const classifyOptions = ref<IClassifyOption[]>([]);
const getMaterialClassifyGroupTree = async () => {
  const res = await serverGetMaterialClassifyGroupTree();

  if (res.code == 200) {
    generateOption(res.data);
  }
};

onMounted(async () => {
  await getMaterialClassifyGroupTree();
});

const onOpenDialog = async () => {
  await getMaterialClassifyGroupTree();

  if (props.materialClassifySection) {
    form.id = props.materialClassifySection.id;
    form.name = props.materialClassifySection.name;
    form.note = props.materialClassifySection.note;
  }
  if (props.materialClassifyGroup)
    form.materialClassifyGroupId = props.materialClassifyGroup.id;
};

const handleClose = () => {
  emit("onDilalogCancel");
};
const onOk = () => {
  if (
    !form.materialClassifyGroupId ||
    form.materialClassifyGroupId.length == 0
  ) {
    ElMessageBox.alert("中类不能为空", "提示", {
      confirmButtonText: "确定",
    });

    return;
  }
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
      title="修改小类"
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
        <el-form-item label="分类" prop="materialClassifyGroupId">
          <el-cascader
            v-model="form.materialClassifyGroupId"
            :options="classifyOptions"
            style="width: 300px"
          />
        </el-form-item>

        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>

        <el-form-item label="备注" prop="note">
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
