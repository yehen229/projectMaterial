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
import { getCurrentInstance } from "vue";
import { useRouter, useRoute, onBeforeRouteUpdate } from "vue-router";

import type { FormInstance, FormRules } from "element-plus";
import {
  View,
  Hide,
  Search,
  Plus,
  Delete,
  Edit,
} from "@element-plus/icons-vue";

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
  serverGetTaskByCurrentLoginUserAndProjectIdAndTaskId,
  serverSubmitProjectMaterialReviewOfManagerDirect,
  serverSubmitProjectMaterialReviewManagerDistributeToEmployees,
  serverSubmitProjectMaterialReviewOfEmployee,
  serverSubmitProjectMaterialReviewOfManagerSummary,
  serverSubmitEngineeringDepartmentManagerDispatch,
  serverSubmitGeneralContractorSelectBrand,
  serverGetNotPassedBrandOfSelectBrandReviewedOfSupervisionCompanyByProjectIdAndTaskId,
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
  IServerUseMaterialBrandSelectView,
} from "@/server/types/project/review";

import {
  IServerUseMaterial,
  IServerUseMaterialNewBrand,
  IServerUseMaterialNewBrandFile,
  IServerUseMaterialNewBrandFileView,
  IServerUseMaterialNewBrandView,
  IServerUseMaterialView,
} from "@/server/types/project/review";

import { IServerMaterial } from "@/server/types/system/material";

import { serverGetAllCompanyListByCompanyType } from "@/server/system/company";

import ProjectUserTaskList from "@/components/project/flow/ProjectUserTaskList.vue";
import ProjectMaterialList from "@/components/project/material/ProjectMaterialList.vue";
import SelectProjectMaterialDialog from "@/components/project/material/SelectProjectMaterialDialog.vue";
import SelectProjectMaterialBrand from "@/components/project/material/SelectProjectMaterialBrand.vue";

import { genUUID } from "@/utils/utils";
import { getUserID, getUserPageSize } from "@/cookies/user";
import ProjectUserTaskInfo from "@/components/project/flow/ProjectUserTaskInfo.vue";

import SelectBrandFeekback from "@/components/project/review/SelectBrandFeekback.vue";
import AffectAppearanceFeedback from "@/components/project/review/AffectAppearanceFeedback.vue";
import NotAffectAppearanceFeedback from "@/components/project/review/NotAffectAppearanceFeedback.vue";
import DesigncompanyAffectAppearanceFeedback from "@/components/project/review/DesigncompanyAffectAppearanceFeedback.vue";
import DesigndepartmentAffectAppearanceFeedback from "@/components/project/review/DesigndepartmentAffectAppearanceFeedback.vue";
import ProjectReviewHistoryList from "@/components/project/flow/ProjectReviewHistoryList.vue";
import {
  getTaskName,
  getTaskProjectName,
  getTaskTitle,
} from "@/components/project/flow/index";
import ElementPlus from "element-plus";

import UpdateProjectMaterialByGeneralContractorCompanyDialog from "@/components/project/material/UpdateProjectMaterialByGeneralContractorCompanyDialog.vue";

const router = useRouter();
const route = useRoute();
const loading = ref(false);
const internalInstance = getCurrentInstance();
const projectUserTask: Ref<IServerProjectUserTask | undefined> = ref();
const projectId = ref("");
const taskId = ref("");
const radio = ref(0);

const dialogSelectProjectMaterialDialogVisible = ref(false); //控制“选择项目物料对话框”是否显示

const dialogSelectProjectMaterialBrandDialogVisible = ref(false); //控制“选择项目物料品牌对话框”是否显示
const dialogUpdateProjectMaterialByGeneralContractorCompanyDialogVisible =
  ref(false); //控制“修改材料”对话框是否显示

const checkList = ref([]);
const employeeList: Ref<IServerUser[]> = ref([]);

const notPassedUseMaterialBrandSelectView =
  ref<IServerUseMaterialBrandSelectView>();

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

  //材料变更相关
  materialChange: boolean; //是否需要物料变更
  material: IServerMaterial; //材料
  projectMaterial: IServerProjectMaterial; //项目物料
  photoFileDir: string; //材料样本图片目录
  photoIds: string[]; //材料样本图片id
}

const useProjectMaterialViewList = ref<IUseMaterial[]>([]);

onBeforeRouteUpdate(async (to) => {
  if (typeof to.params.id === "string") {
    projectId.value = to.params.id;
  } else {
    projectId.value = to.params.id[0];
  }
  if (typeof to.params.taskId === "string") {
    taskId.value = to.params.taskId;
  } else {
    taskId.value = to.params.taskId[0];
  }

  await getUserTaskFromServerByProjectId(projectId.value, taskId.value);
  await getNotPassedBrandFromServer();
});

onMounted(async () => {
  if (typeof route.params.id === "string") {
    projectId.value = route.params.id;
  }
  if (typeof route.params.taskId === "string") {
    taskId.value = route.params.taskId;
  }

  await getUserTaskFromServerByProjectId(projectId.value, taskId.value);
  await getNotPassedBrandFromServer();
});

const getNotPassedBrandFromServer = async () => {
  const ret =
    await serverGetNotPassedBrandOfSelectBrandReviewedOfSupervisionCompanyByProjectIdAndTaskId(
      projectId.value,
      taskId.value
    );
  if (ret && ret.code == 200) {
    notPassedUseMaterialBrandSelectView.value = ret.data;
    if (
      notPassedUseMaterialBrandSelectView &&
      notPassedUseMaterialBrandSelectView.value
    ) {
      notPassedUseMaterialBrandSelectView.value.useMaterialViewList.forEach(
        (item) => {
          const useMaterial: IUseMaterial = {
            projectMaterialView: item.projectMaterialView,
            useMaterial: {
              id: "", //id,主键
              useMaterialBrandSelectId: "",
              projectMaterialId: item.useMaterial.projectMaterialId, //t_project_material_id,外键,	t_project_material_id<-表t_project_material.id,项目物料项目物料
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

            //材料变更相关
            materialChange: false, //是否需要物料变更
            material: item.projectMaterialView.material, //材料
            projectMaterial: item.projectMaterialView.projectMaterial, //项目物料
            photoFileDir: "", //材料样本图片目录
            photoIds: [], //材料样本图片id
          };
          useProjectMaterialViewList.value.push(useMaterial);
        }
      );
    }
  }
};

const getUserTaskFromServerByProjectId = async (
  projectId: string,
  taskId: string
) => {
  const ret = await serverGetTaskByCurrentLoginUserAndProjectIdAndTaskId(
    projectId,
    taskId
  );
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

  if (!projectUserTask.value || !projectUserTask.value.taskId) {
    ElMessageBox.alert("任务信息异常", "提示", {
      confirmButtonText: "确定",
    });
    return;
  }

  let useMaterialFormList: IServerUseMaterialFormItem[] = [];
  let errFlag = false;

  //确保用户要选择一个已有品牌，或者新建品牌。如果是新建品牌，则要求文件数量必须大于0
  useProjectMaterialViewList.value.forEach((item) => {
    if (
      (!item.useMaterial.projectMaterialBrandPrivateId ||
        item.useMaterial.projectMaterialBrandPrivateId.length == 0) &&
      (!item.useMaterial.projectMaterialBrandPublicId ||
        item.useMaterial.projectMaterialBrandPublicId.length == 0) &&
      !item.useMaterialNewBrand.brandName.trim()
    ) {
      //用户没有选择品牌，也没有新建品牌
      ElMessageBox.alert("必须选择一个已有品牌，或者新建品牌", "提示", {
        confirmButtonText: "确定",
      });
      errFlag = true;
      return;
    }

    //判断用户是否选择了一个已有品牌
    if (
      item.useMaterial.projectMaterialBrandPrivateId ||
      item.useMaterial.projectMaterialBrandPublicId
    ) {
      //用户选择了一个已有品牌
    } else {
      if (!item.useMaterialNewBrand.brandName.trim() || !item.uploadFileCount) {
        ElMessageBox.alert("新建品牌名称不能为空，并且必须提供附件", "提示", {
          confirmButtonText: "确定",
        });
        errFlag = true;
        return;
      }
    }

    let form: IServerUseMaterialFormItem = {
      projectMaterialId: item.projectMaterialView.projectMaterial.id,
      isAppearance: item.useMaterial.isAppearance,
      projectMaterialBrandPublicId:
        item.useMaterial.projectMaterialBrandPublicId,
      projectMaterialBrandPrivateId:
        item.useMaterial.projectMaterialBrandPrivateId,
      userId: userId,
      brandName: item.useMaterialNewBrand.brandName,
      materialClassifyDivisionId:
        item.useMaterialNewBrand.materialClassifyDivisionId,
      materialClassifyGroupId: item.useMaterialNewBrand.materialClassifyGroupId,
      materialClassifySectionId:
        item.useMaterialNewBrand.materialClassifySectionId,
      materialPosition: item.useMaterialNewBrand.materialPosition,
      tempFileDir: item.tempDir,

      //材料变更相关
      materialChange: item.materialChange, //是否需要物料变更
      material: item.material, //材料
      projectMaterial: item.projectMaterial, //项目物料
      photoFileDir: item.photoFileDir, //材料样本图片目录
      photoIds: item.photoIds, //材料样本图片id
    };
    useMaterialFormList.push(form);
  });

  if (errFlag) return;

  let useMaterialForm: IServerUseMaterialForm = {
    projectId: projectId.value,
    taskId: projectUserTask.value?.taskId,

    useMaterialFormItems: useMaterialFormList,
    userId: userId,
  };
  // 调用API
  const response = await serverSubmitGeneralContractorSelectBrand(
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
          materialId: "",
          materialCount: 0, //material_count,数量数量
          materialUnit: "", //material_unit,数量单位数量单位
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

        //材料变更相关
        materialChange: false, //是否需要物料变更
        material: item.material, //材料
        projectMaterial: item.projectMaterial, //项目物料
        photoFileDir: "", //材料样本图片目录
        photoIds: [], //材料样本图片id
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

/**
 * 删掉用户选择的材料
 * @param row
 */
const onDeleteBrandSelected = (row: IUseMaterial) => {
  //删掉useProjectMaterialViewList中等于row.projectMaterialView.projectMaterial.id
  const index = useProjectMaterialViewList.value.findIndex(
    (item) =>
      item.projectMaterialView.projectMaterial.id ===
      row.projectMaterialView.projectMaterial.id
  );
  if (index >= 0) useProjectMaterialViewList.value.splice(index, 1);
};

const isEditMaterial = ref<boolean[]>([]);
/**
 * 修改项目物料（这里的修改其实是新建材料，但是是在当前的材料基础上进行新建的）
 * @param row
 */

const updateUseMaterial = ref<IUseMaterial>();
const updateUseMaterialIndex = ref(-1);
const onEditMaterialSelected = (index: number, row: IUseMaterial) => {
  console.log(row);
  updateUseMaterial.value = row;
  updateUseMaterialIndex.value = index;
  dialogUpdateProjectMaterialByGeneralContractorCompanyDialogVisible.value =
    true;
  console.log(updateUseMaterial.value);
};
const onUpdateProjectMaterialByGeneralContractorCompanyDialogCancel = () => {
  dialogUpdateProjectMaterialByGeneralContractorCompanyDialogVisible.value =
    false;
};

//修改材料
const onUpdateProjectMaterialByGeneralContractorCompanyDialogOk = async (
  projectMaterialForm: IServerProjectMaterialForm
) => {
  console.log(projectMaterialForm);
  console.log(updateUseMaterialIndex.value);
  if (updateUseMaterialIndex.value >= 0) {
    useProjectMaterialViewList.value[
      updateUseMaterialIndex.value
    ].projectMaterialView.material = projectMaterialForm.material; //材料

    //材料变更相关

    useProjectMaterialViewList.value[
      updateUseMaterialIndex.value
    ].materialChange = true; //是否需要物料变更
    useProjectMaterialViewList.value[updateUseMaterialIndex.value].material =
      projectMaterialForm.material; //材料
    useProjectMaterialViewList.value[
      updateUseMaterialIndex.value
    ].projectMaterial = projectMaterialForm.projectMaterial; //项目物料

    useProjectMaterialViewList.value[
      updateUseMaterialIndex.value
    ].photoFileDir = projectMaterialForm.photoTempDir;
    useProjectMaterialViewList.value[updateUseMaterialIndex.value].photoIds =
      projectMaterialForm.photoIds;
  }

  console.log(useProjectMaterialViewList.value);
  dialogUpdateProjectMaterialByGeneralContractorCompanyDialogVisible.value =
    false;
  // internalInstance?.proxy?.$forceUpdate();
};

const textElipsisValue = ref(false);
</script>

<template>
  <!--选择项目物料-->
  <SelectProjectMaterialDialog
    :dialogVisible="dialogSelectProjectMaterialDialogVisible"
    :projectId="projectId"
    @onDilalogCancel="onSelectProjectMaterialDialogCancel"
    @onDilalogOk="onSelectProjectMaterialDialogOk"
  >
  </SelectProjectMaterialDialog>

  <UpdateProjectMaterialByGeneralContractorCompanyDialog
    :dialogVisible="
      dialogUpdateProjectMaterialByGeneralContractorCompanyDialogVisible
    "
    :material="updateUseMaterial?.material"
    :project="updateUseMaterial?.projectMaterialView.project"
    :projectMaterial="updateUseMaterial?.projectMaterialView.projectMaterial"
    @onDilalogCancel="
      onUpdateProjectMaterialByGeneralContractorCompanyDialogCancel
    "
    @onDilalogOk="onUpdateProjectMaterialByGeneralContractorCompanyDialogOk"
  ></UpdateProjectMaterialByGeneralContractorCompanyDialog>

  <!--显示项目列表、当前能够执行的操作（例如审核等）-->
  <div class="tab-container">
    <ProjectUserTaskInfo
      :projectUserTask="projectUserTask"
    ></ProjectUserTaskInfo>

    <div v-if="projectUserTask">
      <div class="project-container container">
        <div class="top-toolbar" style="display: flex">
          <div>
            <el-button type="primary" @click="selectBrand"
              >新增品牌、物料使用申请</el-button
            >
          </div>

          <div style="margin-left: auto">
            <el-switch
              v-model="textElipsisValue"
              inline-prompt
              style="
                --el-switch-on-color: #13ce66;
                --el-switch-off-color: #ff4949;
              "
              active-text="自动调整高度"
              inactive-text="显示全部内容"
            />
          </div>
        </div>

        <!--显示内容-->
        <el-row
          style="
            width: 100%;
            font: 1em sans-serif;
            border: 1px solid #eee;
            border-bottom-style: none;
            padding: 5px;
            margin: 5px;
            margin-left: 0px;
            white-space: pre-wrap;
            line-height: 1.5;
            margin-top: 10px;
            margin-bottom: -5px;
          "
        >
          <el-col :span="2">材料名称 </el-col>
          <el-col :span="2"> 材料位置</el-col>
          <el-col :span="2">编号 </el-col>
          <el-col :span="5">技术要求 </el-col>
          <el-col :span="4"> 施工要求</el-col>
          <el-col :span="2"> 影响外观</el-col>
          <el-col :span="5"> 材料及品牌</el-col>
          <el-col :span="2"> 操作 </el-col>
        </el-row>

        <el-row
          v-for="(
            projectMaterialViewItem, projectMaterialViewIndex
          ) in useProjectMaterialViewList"
          style="
            width: 100%;
            font: 0.8em sans-serif;
            border: 1px solid #eee;
            padding: 5px;
            margin: 5px;
            margin-left: 0px;
            white-space: pre-wrap;
            line-height: 1.5;
            color: #606266;
          "
          v-loading="loading"
          :gutter="20"
        >
          <!--材料名称-->
          <el-col :span="2">
            <div style="display: flex; align-items: center">
              <div :class="{ textEllipsis: textElipsisValue }">
                {{ projectMaterialViewItem.material.name }}
              </div>
            </div>
          </el-col>

          <!--材料位置-->
          <el-col :span="2">
            <div style="display: flex; align-items: center">
              <div :class="{ textEllipsis: textElipsisValue }">
                {{ projectMaterialViewItem.material.location }}
              </div>
            </div>
          </el-col>

          <!--编号-->
          <el-col :span="2">
            <div style="display: flex; align-items: center">
              <div :class="{ textEllipsis: textElipsisValue }">
                {{ projectMaterialViewItem.material.itemMark }}
              </div>
            </div>
          </el-col>

          <!--技术要求-->
          <el-col :span="5">
            <div style="display: flex; align-items: center">
              <div :class="{ textEllipsis: textElipsisValue }">
                {{ projectMaterialViewItem.material.technology }}
              </div>
            </div>
          </el-col>

          <!--施工要求-->
          <el-col :span="4">
            <div style="display: flex; align-items: center">
              <div :class="{ textEllipsis: textElipsisValue }">
                {{ projectMaterialViewItem.material.installation }}
              </div>
            </div>
          </el-col>

          <!--影响外观-->
          <el-col :span="2">
            <el-radio-group
              v-model="projectMaterialViewItem.useMaterial.isAppearance"
            >
              <el-radio :value="0">不影响外观</el-radio>
              <el-radio :value="1">影响外观</el-radio>
            </el-radio-group>
          </el-col>

          <!--材料及品牌-->
          <el-col :span="5">
            <!--选择项目物料品牌-->
            <SelectProjectMaterialBrand
              :projectMaterialView="projectMaterialViewItem.projectMaterialView"
              @onUploadSuccess="onUploadSuccess"
              @onNewBrandNameChanged="onNewBrandNameChanged"
              @onSelectBrandPublic="onSelectBrandPublic"
              @onSelectBrandPrivate="onSelectBrandPrivate"
              @onSelectNewBrand="onSelectNewBrand"
            >
            </SelectProjectMaterialBrand>
          </el-col>

          <!--操作-->
          <el-col :span="2">
            <div
              style="
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
              "
            >
              <el-button
                size="small"
                :icon="Delete"
                @click="onDeleteBrandSelected(projectMaterialViewItem)"
                >删除</el-button
              ><el-button
                size="small"
                :icon="Edit"
                style="margin-top: 5px"
                @click="
                  onEditMaterialSelected(
                    projectMaterialViewIndex,
                    projectMaterialViewItem
                  )
                "
                >修改材料</el-button
              >
            </div>
          </el-col>
        </el-row>

        <div style="margin: 10px; display: flex; justify-content: center">
          <el-button type="primary" @click="submitProcess">确定</el-button>
          <el-button type="primary" @click="cancelProcess">取消</el-button>
        </div>
      </div>

      <!--反馈意见-->
      <SelectBrandFeekback
        :projectUserTask="projectUserTask"
      ></SelectBrandFeekback>

      <AffectAppearanceFeedback
        :projectUserTask="projectUserTask"
      ></AffectAppearanceFeedback>

      <DesigncompanyAffectAppearanceFeedback
        :projectUserTask="projectUserTask"
      ></DesigncompanyAffectAppearanceFeedback>

      <DesigndepartmentAffectAppearanceFeedback
        :projectUserTask="projectUserTask"
      ></DesigndepartmentAffectAppearanceFeedback>

      <NotAffectAppearanceFeedback
        :projectUserTask="projectUserTask"
      ></NotAffectAppearanceFeedback>

      <!--项目物料列表-->
      <ProjectMaterialList :projectId="projectId" />

      <!-- 审核记录-->
      <ProjectReviewHistoryList :projectId="projectId" />
    </div>
    <div v-else>当前项目没有任务</div>
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

.textEllipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  overflow: hidden;
  /* autoprefixer: ignore next */
  -webkit-box-orient: vertical;
}
</style>
