<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref, watch } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";
import type { Action } from "element-plus";
import type { CascaderValue } from "element-plus";

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
  IServerBrand,
  IServerBrandPublic,
  IServerBrandPublicView,
} from "@/server/types/system/brand";

import {
  IMaterialClassifyOption,
  generateMaterialClassifyOption,
} from "@/utils/MaterialClassifyOptions";

import {
  IServerProject,
  IServerProjectView,
  IServerProjectUser,
  IServerProjectUserView,
  IServerProjectAllUserView,
  IServerProjectBrand,
  IServerProjectBrandView,
  IServerProjectBrandForm,
  IServerProjectMaterial,
  IServerProjectMaterialView,
  IServerProjectMaterialForm,
  IServerSearchProject
} from "@/server/types/project/project";

import {
  serverProjectAdd,
  serverProjectDelete,
  serverProjectUpdate,
  serverGetProjectById,
  serverGetProjectViewById,
  serverGetProjectPageView,
  serverGetProjectPageViewByKeyword,
} from "@/server/project/project";

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
  IServerMaterialForm,
  IServerMaterialView,
} from "@/server/types/system/material";

import {
  serverBrandPublicAdd,
  serverBrandPublicDelete,
  serverBrandPublicDeleteById,
  serverBrandPublicUpdate,
  serverGetBrandPublicById,
  serverGetBrandPublicByBrandId,
  serverGetBrandPublicByMaterialClassifySectionId,
  serverGetBrandPublicPage,
  serverGetBrandPublicPageView,
  serverGetBrandPublicPageViewByBrandName,
  serverGetBrandPublicPageViewByBrandPosition,
} from "@/server/system/brandpublic";

import {
  serverGetProjectBrandListByProjectId,
  serverGetProjectBrandViewListByProjectId,
} from "@/server/project/projectbrand";

import { IServerCompany } from "@/server/types/system/company";
import { serverGetAllCompanyListByCompanyType } from "@/server/system/company";

import { serverGetMaterialListByMaterialClassifySectionId } from "@/server/system/material";

import { areaOptions } from "@/utils/area";

const companyConstructionList = ref<IServerCompany[]>();
const companyDesignList = ref<IServerCompany[]>();

const router = useRouter();
const materialViewList = ref<IServerMaterialView[]>();
const brandPublicViewList = ref<IServerBrandPublicView[]>();
const projectBrandList = ref<IServerProjectBrandView[]>();

interface Props {
  dialogVisible: boolean; //对话框是否可见
}

const props = withDefaults(defineProps<Props>(), {
  dialogVisible: false,
});

const projectForm = reactive({
  name: "",
  location: "",
  totalInvestmentWithTax: 0,
  totalInvestmentWithoutTax: 0,
  buildingAreaAboveGround: 0,
  buildingAreaUnderGround: 0,
  companyConstructionId: "",
  companyDesignId: "",
  note: "",
  approvalDate: new Date(),
});
// 表单引用
const projectFormRef = ref<FormInstance>();

// 表单验证规则
const rules = reactive<FormRules>({
  name: [{ required: true, message: "项目名称", trigger: "blur" }],
  location: [{ required: true, message: "项目地点", trigger: "blur" }],
  totalInvestmentWithTax: [
    { required: true, message: "总投资（含税）/万", trigger: "change" },
  ],
  totalInvestmentWithoutTax: [
    { required: true, message: "总投资（不含税）/万", trigger: "change" },
  ],

  buildingAreaAboveGround: [
    { required: true, message: "建设面积（地下）/平米", trigger: "change" },
  ],

  buildingAreaUnderGround: [
    { required: true, message: "建设面积（地下）/平米", trigger: "change" },
  ],
  companyConstructionId: [
    { required: true, message: "建设单位", trigger: "change" },
  ],
  companyDesignId: [{ required: true, message: "设计单位", trigger: "change" }],
  approvalDate: [{ required: true, message: "立项时间", trigger: "change" }],
});

//event
const emit = defineEmits<{
  (e: "onDilalogOk", project: IServerSearchProject): void;
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
const onOpenDialog = async () => {
  await getCompanyFromSever();
  //  await getProjectBrandListFromSever();
  console.log(materialClassifyOption.value);
};

const getCompanyFromSever = async () => {
  const ret1 = await serverGetAllCompanyListByCompanyType("建设单位");

  if (ret1 && ret1.code == 200) {
    companyConstructionList.value = ret1.data;
  }

  const ret2 = await serverGetAllCompanyListByCompanyType("设计单位");

  if (ret2 && ret2.code == 200) {
    companyDesignList.value = ret2.data;
  }
};

const handleClose = () => {
  emit("onDilalogCancel");
};
const onOk = () => {
  let userId = getUserID();
  if (!userId) {
    ElMessageBox.alert("当前登录用户为空，请重新登录", "提示", {
      confirmButtonText: "确定",
    });
    router.push("/login");
    return;
  }

  projectFormRef.value?.validate(async (valid) => {
    console.log(projectForm);
    if (valid) {
      try {
        console.log(projectForm.approvalDate);

        const project: IServerSearchProject = {
          id: "", // 在后端生成
          userId: userId,
          name: projectForm.name,
          location: JSON.stringify(projectForm.location),
          totalTaxIncluded: projectForm.totalInvestmentWithTax,
          totalTaxNotIncluded: projectForm.totalInvestmentWithoutTax,
          buildingAreaAboveGround: projectForm.buildingAreaAboveGround,
          buildingAreaUnderGround: projectForm.buildingAreaUnderGround,
          companyConstructionId: projectForm.companyConstructionId,
          companyDesignId: projectForm.companyDesignId,
          note: "", // 根据需要填充
          createDatetime: projectForm.approvalDate
            ? projectForm.approvalDate
            : new Date(),
          endDatetime: new Date(), // 默认值为 null
          deletedAt: null, // 默认值为 null
        };

        emit("onDilalogOk", project);
      } catch (error) {
        ElMessage.error("项目创建失败");
        console.error("项目创建失败：", error);
      }
    } else {
      ElMessage.error("项目信息无效，请重新检查数据是否正确");
    }
  });
};

const onCancel = () => {
  emit("onDilalogCancel");
};

const cascaderProps = {
  expandTrigger: "hover" as const,
  emitPath: true,
};

// 重置表单
const resetForm = () => {
  projectFormRef.value?.resetFields();
};
</script>

<template>
  <div>
    <el-dialog
      title="高级搜索"
      v-model="dialogFormVisible"
      :before-close="handleClose"
      width="1200px"
      @open="onOpenDialog"
      draggable
    >
      <el-form
        ref="projectFormRef"
        :model="projectForm"
        :rules="rules"
        label-width="160px"
      >
        <el-form-item label="项目名称" prop="name">
          <el-input v-model="projectForm.name" placeholder="请输入项目名称" />
        </el-form-item>

        <el-form-item label="项目地点" prop="location">
          <el-cascader
            v-model="projectForm.location"
            :options="areaOptions"
            :props="cascaderProps"
            style="width: 300px"
          />
        </el-form-item>

        <el-form-item label="总投资（含税）" prop="totalInvestmentWithTax">
          <el-input-number
            v-model="projectForm.totalInvestmentWithTax"
            :precision="6"
            :step="1"
            :min="0.000001"
            placeholder="请输入总投资（含税）"
            style="width: 300px"
          />万元
        </el-form-item>

        <el-form-item label="总投资（不含税）" prop="totalInvestmentWithoutTax">
          <el-input-number
            v-model="projectForm.totalInvestmentWithoutTax"
            :precision="6"
            :step="1"
            :min="0.000001"
            placeholder="请输入总投资（不含税）"
            style="width: 300px"
          />万元
        </el-form-item>

        <el-form-item label="建设面积(地上)" prop="buildingAreaAboveGround">
          <el-input-number
            v-model="projectForm.buildingAreaAboveGround"
            :precision="4"
            :step="1"
            :min="0.0001"
            placeholder="请输入建设面积(地上)"
            style="width: 300px"
          />平米
        </el-form-item>

        <el-form-item label="建设面积(地下)/" prop="buildingAreaUnderGround">
          <el-input-number
            v-model="projectForm.buildingAreaUnderGround"
            :precision="4"
            :step="1"
            :min="0.0001"
            placeholder="请输入建设面积(地下)"
            style="width: 300px"
          />平米
        </el-form-item>

        <el-form-item label="建设单位" prop="companyConstructionId">
          <el-select
            v-model="projectForm.companyConstructionId"
            placeholder="请选择建设单位"
          >
            <el-option
              v-for="item in companyConstructionList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="设计单位" prop="companyDesignId">
          <el-select
            v-model="projectForm.companyDesignId"
            placeholder="请选择设计单位"
          >
            <el-option
              v-for="item in companyDesignList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="备注" prop="note">
          <el-input
            v-model="projectForm.note"
            type="textarea"
            placeholder="请输入备注"
          />
        </el-form-item>

        <el-form-item label="立项时间" prop="approvalDate">
          <el-date-picker
            v-model="projectForm.approvalDate"
            type="date"
            placeholder="选择立项时间"
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

<style scoped>
.page-class {
  padding: 10px;
}

.top-toolbar {
  display: flex;
  margin: 0 10px;
}

.input-with-select {
  right: 20px;
  margin-left: 10px;
  flex: 1;
}
</style>
