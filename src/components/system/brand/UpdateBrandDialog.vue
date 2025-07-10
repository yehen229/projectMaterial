<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref, watch } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";
import {
  IServerBrand,
  IServerBrandView,
  IServerBrandPublic,
  IServerBrandPublicView,
} from "@/server/types/system/brand";

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
import {
  IMaterialClassifyOption,
  IMaterialFactoryOption,
  generateMaterialClassifyOption,
} from "@/utils/MaterialClassifyOptions";
import { serverGetFactoryList } from "@/server/system/company";
const router = useRouter();

interface Props {
  dialogVisible: boolean; //对话框是否可见
  brandPublicView: IServerBrandPublicView;
}

const props = withDefaults(defineProps<Props>(), {
  dialogVisible: false,
});

const form = reactive<IServerBrand>({
  id: "", //id,主键
  name: "", //名称
  materialClassifySectionId: "", //
  factory_id: "",
  position: "0", //
  deletedAt: new Date(),
});
const rules = reactive<FormRules>({
  name: [
    { required: true, message: "请输入品牌名称", trigger: "blur" },
    { min: 1, max: 64, message: "字数范围为：1-64", trigger: "blur" },
  ],
  materialClassifySectionId: [
    { required: true, message: "请选择材料类别", trigger: "blur" },
  ],
});

//event
const emit = defineEmits<{
  (e: "onDilalogOk", brand: IServerBrand): void;
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
const materialClassifyOption = ref<IMaterialClassifyOption[]>([]);
const factoryOption = ref<IMaterialFactoryOption[]>([]);

const onOpenDialog = async () => {
  materialClassifyOption.value = await generateMaterialClassifyOption();

  if (props.brandPublicView) {
    form.id = props.brandPublicView.brandView.brand.id;
    form.name = props.brandPublicView.brandView.brand.name;
    form.materialClassifySectionId =
      props.brandPublicView.brandView.brand.materialClassifySectionId;
    form.position = props.brandPublicView.brandView.brand.position;
    form.deletedAt = props.brandPublicView.brandView.brand.deletedAt;
    const factoryResponse = await serverGetFactoryList();
    factoryOption.value.splice(0, factoryOption.value.length)
    factoryOption.value.push({
        id: null,
        value: null,
        label: "无",
      });
    for (let i = 0; i < factoryResponse.data.length; i++) {
      factoryOption.value.push({
        id: factoryResponse.data[i].id,
        value: factoryResponse.data[i].id,
        label: factoryResponse.data[i].name,
      });
    }
    form.factory_id = props.brandPublicView.company.id;
  }
};

const handleClose = () => {
  form.factory_id = "";
  emit("onDilalogCancel");
};
const onOk = () => {
  if (!form.name || form.name.length == 0) {
    ElMessageBox.alert("品牌名称不能为空", "提示", {
      confirmButtonText: "确定",
    });

    return;
  }

  if (
    !form.materialClassifySectionId ||
    form.materialClassifySectionId.length == 0
  ) {
    ElMessageBox.alert("材料种类不能为空", "提示", {
      confirmButtonText: "确定",
    });

    return;
  }

  emit("onDilalogOk", form);
  form.factory_id = "";
};

const onCancel = () => {
  emit("onDilalogCancel");
  form.factory_id = "";
};

const cascaderProps = {
  expandTrigger: "hover" as const,
  emitPath: false,
};
</script>

<template>
  <div>
    <el-dialog
      title="编辑单位"
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
        <el-form-item label="材料类别" prop="materialClassifySectionId">
          <el-cascader
            v-model="form.materialClassifySectionId"
            :options="materialClassifyOption"
            :props="cascaderProps"
            style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="品牌名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入品牌名称" />
        </el-form-item>
        
        <el-form-item label="厂家" prop="factory">
          <el-cascader
            placeholder="无"
            v-model="form.factory_id"
            :options="factoryOption"
            :props="cascaderProps"
            style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="定位" prop="position">
          <el-radio-group v-model="form.position">
            <el-radio value="国产">国产</el-radio>
            <el-radio value="合资">合资</el-radio>
            <el-radio value="外资">外资</el-radio>
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
