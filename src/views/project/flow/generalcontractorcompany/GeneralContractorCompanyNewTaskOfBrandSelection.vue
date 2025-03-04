<script setup lang="ts">
/**
 * 文件说明：
 *
 * 项目物料流程-工程部项目经理分发施工、监理
 * 详见流程图
 *
 *
 */

import { computed, onMounted, reactive, ref, Ref } from "vue";

import { useRouter, useRoute, onBeforeRouteUpdate } from "vue-router";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";
import type { Action } from "element-plus";

import type {
  UploadInstance,
  UploadProps,
  UploadRawFile,
  UploadUserFile,
  UploadRequestOptions,
} from "element-plus";

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
  IServerProjectMaterialBrandPublicView,
  IServerProjectMaterialBrandPrivateView,
} from "@/server/types/project/project";
import {
  serverGetProjectPageView,
  serverGetProjectPageViewByKeyword,
} from "@/server/project/project";

import {
  serverStartProcess,
  serverGetTaskByCurrentLoginUser,
  serverGetTaskByCurrentLoginUserAndProjectId,
  serverSubmitProjectMaterialReviewOfManagerDirect,
  serverSubmitProjectMaterialReviewManagerDistributeToEmployees,
  serverSubmitProjectMaterialReviewOfEmployee,
  serverSubmitProjectMaterialReviewOfManagerSummary,
  serverSubmitEngineeringDepartmentManagerDispatch,
  serverSubmitGeneralContractorSelectBrand,
  serverSubmitGeneralContractorNewTaskOfSelectBrand,
} from "@/server/project/projectmaterialflow";

import { IServerProjectUserTask } from "@/server/types/project/flow";

import { serverGetEmployeeUserOfDesignDepartment } from "@/server/project/projectuser";
import { IServerUser } from "@/server/types/account/user";

import {
  serverAddProjectMaterialReviewTempFiles,
  serverDeleteProjectMaterialReviewTempFiles,
} from "@/server/project/projectreview";

import {
  IServerProjectReviewMode,
  IServerProjectReviewModeView,
  IServerProjectReview,
  IServerProjectReviewView,
  IServerProjectReviewUser,
  IServerProjectReviewUserView,
  IServerProjectReviewUserFile,
  IServerProjectReviewUserFileView,
  IServerProjectReviewForm,
  IServerProjectReviewDispatchForm,
  IServerProjectReviewEmployeeForm,
  IServerProjectCompany,
  IServerUseMaterialForm,
  IServerUseMaterialFormItem,
} from "@/server/types/project/review";

import {
  IServerUseMaterial,
  IServerUseMaterialNewBrand,
  IServerUseMaterialNewBrandFile,
  IServerUseMaterialNewBrandFileView,
  IServerUseMaterialNewBrandView,
  IServerUseMaterialView,
} from "@/server/types/project/review";

import { serverGetAllCompanyListByCompanyType } from "@/server/system/company";

import ProjectUserTaskList from "@/components/project/flow/ProjectUserTaskList.vue";
import ProjectMaterialList from "@/components/project/material/ProjectMaterialList.vue";
import SelectProjectMaterialDialog from "@/components/project/material/SelectProjectMaterialDialog.vue";
import SelectProjectMaterialBrand from "@/components/project/material/SelectProjectMaterialBrand.vue";

const dialogSelectProjectMaterialDialogVisible = ref(false); //控制“选择项目物料对话框”是否显示

const dialogSelectProjectMaterialBrandDialogVisible = ref(false); //控制“选择项目物料品牌对话框”是否显示

import { genUUID } from "@/utils/utils";
import { getUserID, getUserPageSize } from "@/cookies/user";
import ProjectUserTaskInfo from "@/components/project/flow/ProjectUserTaskInfo.vue";

import ProjectReviewHistoryList from "@/components/project/flow/ProjectReviewHistoryList.vue";
import {
  getTaskName,
  getTaskProjectName,
  getTaskTitle,
} from "@/components/project/flow/index";
import ElementPlus from "element-plus";

const router = useRouter();
const route = useRoute();

const projectUserTask: Ref<IServerProjectUserTask | undefined> = ref();
const projectId = ref("");
const radio = ref(0);

const checkList = ref([]);
const employeeList: Ref<IServerUser[]> = ref([]);

const fileList = ref<UploadUserFile[]>([]);
const upload = ref<UploadInstance>();
const fileListUploadNum = ref(0);
const selectedBrandId = ref("");
const selectedAffectAppearance = ref("");
const form = reactive({
  supervisionCompanyId: "",
  constructionCompanyId: "",
});

interface IUseMaterial {
  projectMaterialView: IServerProjectMaterialView;
  useMaterial: IServerUseMaterial;
  useMaterialNewBrand: IServerUseMaterialNewBrand;
  tempDir: string; //上传文件临时目录
  uploadFileCount: number; //上传文件数量
}

const useProjectMaterialViewList = ref<IUseMaterial[]>([]);

onBeforeRouteUpdate(async (to) => {
  if (typeof to.params.id === "string") {
    projectId.value = to.params.id;
    // await getUserTaskFromServerByProjectId(to.params.id);
  } else {
    //   getUserTaskFromServerByProjectId(to.params.id[0]);
    projectId.value = to.params.id[0];
  }
});

onMounted(async () => {
  if (typeof route.params.id === "string") {
    // await getUserTaskFromServerByProjectId(route.params.id);
    projectId.value = route.params.id;
  }
});

const getUserTaskFromServerByProjectId = async (projectId: string) => {
  const ret = await serverGetTaskByCurrentLoginUserAndProjectId(projectId);
  if (ret && ret.code == 200) {
    projectUserTask.value = ret.data;
  }
};

const goBack = () => {
  history.back();
};

/**
 * 总包单位品牌选择（从备选品牌选择或新品牌并提供说明）,物料使用申请
 */
const submitProcess = async () => {
  console.log(fileList.value);
  const userId = getUserID();
  console.log(userId);
  if (!userId) {
    ElMessageBox.alert("用户信息异常，请重新登录", "提示", {
      confirmButtonText: "确定",
    });
    return;
  }

  let useMaterialFormList: IServerUseMaterialFormItem[] = [];

  let useMaterialForm: IServerUseMaterialForm = {
    projectId: projectId.value,
    useMaterialFormItems: useMaterialFormList,
    userId: userId,
    taskId: "",
  };
  // 调用API
  const response = await serverSubmitGeneralContractorNewTaskOfSelectBrand(
    useMaterialForm
  );
  console.log(response);
  if (response && response.code === 200) {
    // Debug: 查看创建结果
    console.log(response.data);
    ElMessage.success("申请成功");
  } else {
    console.error("申请失败");
  }

  router.push("/project-user-task-list");
};

const cancelProcess = async () => {};
const selectBrand = () => {
  dialogSelectProjectMaterialDialogVisible.value = true;
};
const onSelectProjectMaterialDialogCancel = () => {
  dialogSelectProjectMaterialDialogVisible.value = false;
};

/**
 * 将用户选择的物料增加到列表中，注意，不能重复添加
 * @param _projectMaterialViewList
 */
const onSelectProjectMaterialDialogOk = async (
  _projectMaterialViewList: IServerProjectMaterialView[]
) => {
  dialogSelectProjectMaterialDialogVisible.value = false;

  _projectMaterialViewList.forEach(async (item) => {
    if (
      !useProjectMaterialViewList.value.some(
        (item2) =>
          item2.projectMaterialView.projectMaterial.id ===
          item.projectMaterial.id
      )
    ) {
      const useMaterial: IUseMaterial = {
        projectMaterialView: item,
        useMaterial: {
          id: "", //id,主键
          useMaterialBrandSelectId: "",
          projectMaterialId: item.projectMaterial.id, //t_project_material_id,外键,	t_project_material_id<-表t_project_material.id,项目物料项目物料
          userId: "", //t_user_id,外键,	t_user_id<-表t_user.id,用户（总包单位人员）用户（总包单位人员）
          projectMaterialBrandPrivateId: "", //t_project_material_brand_private_id,外键,	t_project_material_brand_private_id<-表t_project_material_brand_private.id,品牌品牌
          projectMaterialBrandPublicId: "", //t_project_material_brand_public_id,外键,	t_project_material_brand_public_id<-表t_project_material_brand_public.id
          isAppearance: 0, //is_appearance,是否影响外观是否影响外观
          createDatetime: new Date(), //create_datetime,创建时间创建时间
          deletedAt: new Date(), //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
        },
        useMaterialNewBrand: {
          id: "", //id,主键
          useMaterialId: "", //t_use_material_id,外键,	t_use_material_id<-表t_use_material.id
          brandName: "", //brand_name,品牌品牌
          materialClassifyDivisionId: "", //t_material_classify_division_id,外键,	t_material_classify_division_id<-表t_material_classify_division.id,大类专业大类专业
          materialClassifyGroupId: "", //t_material_classify_group_id,外键,	t_material_classify_group_id<-表t_material_classify_group.id,中类材料分类中类材料分类
          materialClassifySectionId: "", //t_material_classify_section_id,外键,	t_material_classify_section_id<-表t_material_classify_section.id,小类材料名称小类材料名称
          projectMaterialBrandPrivateId: "",
          materialPosition: "", //material_position,定位：合资、国产等定位：合资、国产等
          deletedAt: new Date(), //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
        },
        tempDir: "",
        uploadFileCount: 0,
      };
      useProjectMaterialViewList.value.push(useMaterial);
    }
  });
};

//上传或删除文件成功，参数为上传文件路径和上传文件数量
const onUploadSuccess = (
  projectMaterialView: IServerProjectMaterialView,
  tempDir: string,
  uploadFileCount: number
) => {
  useProjectMaterialViewList.value.forEach((item) => {
    if (
      item.projectMaterialView.projectMaterial.id ===
      projectMaterialView.projectMaterial.id
    ) {
      item.tempDir = tempDir;
      item.uploadFileCount = uploadFileCount;
    }
  });
};

//新建品牌名称
const onNewBrandNameChanged = (
  projectMaterialView: IServerProjectMaterialView,
  newBrandName: string,
  materialClassifyDivisionId: string,
  materialClassifyGroupId: string,
  materialClassifySectionId: string,
  materialBrandPosition: string
) => {
  useProjectMaterialViewList.value.forEach((item) => {
    if (
      item.projectMaterialView.projectMaterial.id ===
      projectMaterialView.projectMaterial.id
    ) {
      item.useMaterialNewBrand.brandName = newBrandName;
      item.useMaterialNewBrand.materialClassifyDivisionId =
        materialClassifyDivisionId;
      item.useMaterialNewBrand.materialClassifyGroupId =
        materialClassifyGroupId;
      item.useMaterialNewBrand.materialClassifySectionId =
        materialClassifySectionId;
      item.useMaterialNewBrand.materialPosition = materialBrandPosition;
    }
  });
};

//选择公共品牌
const onSelectBrandPublic = (
  projectMaterialView: IServerProjectMaterialView,
  projectMaterialBrandPublicView: IServerProjectMaterialBrandPublicView
) => {
  useProjectMaterialViewList.value.forEach((item) => {
    if (
      item.projectMaterialView.projectMaterial.id ===
      projectMaterialView.projectMaterial.id
    ) {
      item.useMaterial.projectMaterialBrandPrivateId = "";
      item.useMaterial.projectMaterialBrandPublicId =
        projectMaterialBrandPublicView.projectMaterialBrandPublic.id;
    }
  });
};

//选择私有品牌
const onSelectBrandPrivate = (
  projectMaterialView: IServerProjectMaterialView,
  projectMaterialBrandPrivateView: IServerProjectMaterialBrandPrivateView
) => {
  useProjectMaterialViewList.value.forEach((item) => {
    if (
      item.projectMaterialView.projectMaterial.id ===
      projectMaterialView.projectMaterial.id
    ) {
      item.useMaterial.projectMaterialBrandPrivateId =
        projectMaterialBrandPrivateView.projectMaterialBrandPrivate.id;
      item.useMaterial.projectMaterialBrandPublicId = "";
    }
  });
};

//选择自建品牌
const onSelectNewBrand = (projectMaterialView: IServerProjectMaterialView) => {
  useProjectMaterialViewList.value.forEach((item) => {
    if (
      item.projectMaterialView.projectMaterial.id ===
      projectMaterialView.projectMaterial.id
    ) {
      item.useMaterial.projectMaterialBrandPrivateId = "";
      item.useMaterial.projectMaterialBrandPublicId = "";
    }
  });
};
</script>

<template>
  <!--显示项目列表、当前能够执行的操作（例如审核等）-->
  <div class="tab-container">
    <div class="project-container container">
      <div style="font: 1.2em sans-serif; margin: 10px">新建品牌选择</div>

      <!--显示内容-->
      确定要为该该项目进行品牌选择吗？

      <div style="margin: 10px; display: flex; justify-content: center">
        <el-button type="primary" @click="submitProcess">确定</el-button>
        <el-button type="primary" @click="cancelProcess">取消</el-button>
      </div>
    </div>

    <!--项目物料列表-->
    <ProjectMaterialList :projectId="projectId" />

    <!-- 审核记录-->
    <ProjectReviewHistoryList :projectId="projectId" />
  </div>
</template>

<style scoped>
@import url("@/assets/css/basic.css");
.page-class {
  padding: 10px;
}

.top-toolbar {
  display: flex;
  margin: 0 10px;
}

.project-container {
  padding: 10px;
}
</style>
