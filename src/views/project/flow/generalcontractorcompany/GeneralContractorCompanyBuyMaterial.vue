<script setup lang="ts">
/**
 * 文件说明：
 *
 * 订购流程-总包公司订购
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
  serverGetTaskByCurrentLoginUserAndProjectIdAndTaskId,
  serverSubmitProjectMaterialReviewOfManagerDirect,
  serverSubmitProjectMaterialReviewManagerDistributeToEmployees,
  serverSubmitProjectMaterialReviewOfEmployee,
  serverSubmitProjectMaterialReviewOfManagerSummary,
  serverSubmitEngineeringDepartmentManagerDispatch,
  serverSubmitGeneralContractorSelectBrand,
  serverSubmitGeneralContractorBuyProjectMaterial,
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
  IServerBuyMaterialForm,
} from "@/server/types/project/review";

import {
  IServerUseMaterial,
  IServerUseMaterialNewBrand,
  IServerUseMaterialNewBrandFile,
  IServerUseMaterialNewBrandFileView,
  IServerUseMaterialNewBrandView,
  IServerUseMaterialView,
  IServerBuyMaterial,
  IServerBuyMaterialView,
} from "@/server/types/project/review";

import { serverGetAllCompanyListByCompanyType } from "@/server/system/company";

import ProjectUserTaskList from "@/components/project/flow/ProjectUserTaskList.vue";
import ProjectMaterialList from "@/components/project/material/ProjectMaterialList.vue";
import SelectProjectMaterialDialog from "@/components/project/material/SelectProjectMaterialDialog.vue";
import SelectProjectMaterialBrand from "@/components/project/material/SelectProjectMaterialBrand.vue";
import SelectReviewedUseMaterialDialog from "@/components/project/material/SelectReviewedUseMaterialDialog.vue";
import SupervisionNeedReCheckNotPassedProjectMaterialAcceptanceFeedback from "@/components/project/review/SupervisionNeedReCheckNotPassedProjectMaterialAcceptanceFeedback.vue";

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
const taskId = ref("");
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

interface IBuyMaterial {
  projectMaterialView: IServerProjectMaterialView;
  useMaterial: IServerUseMaterial;
  useMaterialNewBrand?: IServerUseMaterialNewBrand;
  buyMaterial: IServerBuyMaterial;
  projectMaterialBrandPrivateView: IServerProjectMaterialBrandPrivateView; //外键：t_project_material_brand_private_id,关联表为：t_project_material_brand_private表,品牌
  projectMaterialBrandPublicView: IServerProjectMaterialBrandPublicView; //外键：t_project_material_brand_public_id,关联表为：t_project_material_brand_public表,
}

const useProjectMaterialViewList = ref<IBuyMaterial[]>([]);

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
});

onMounted(async () => {
  if (typeof route.params.id === "string") {
    projectId.value = route.params.id;
  }
  if (typeof route.params.taskId === "string") {
    taskId.value = route.params.taskId;
  }

  await getUserTaskFromServerByProjectId(projectId.value, taskId.value);
});

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

  if (!useProjectMaterialViewList.value.length) {
    ElMessageBox.alert("订购材料为空，请选择材料进行订购", "提示", {
      confirmButtonText: "确定",
    });
    return;
  }

  let useMaterialFormList: IServerUseMaterialFormItem[] = [];

  //材料数量和数量单位都不能为空
  for (let i = 0; i < useProjectMaterialViewList.value.length; i++) {
    let item = useProjectMaterialViewList.value[i];
    if (
      item.buyMaterial.materialCount <= 0 ||
      item.buyMaterial.materialUnit == ""
    ) {
      ElMessageBox.alert("数量和数量单位都不能为空", "提示", {
        confirmButtonText: "确定",
      });
      return;
    }
  }

  let buyMaterialForm: IServerBuyMaterialForm = {
    buyMaterials: [],
    taskId: taskId.value,
    projectId: projectId.value,
  };

  useProjectMaterialViewList.value.forEach((item) => {
    buyMaterialForm.buyMaterials.push(item.buyMaterial);
  });

  // 调用API
  const response = await serverSubmitGeneralContractorBuyProjectMaterial(
    buyMaterialForm
  );
  console.log(response);
  if (response && response.code === 200) {
    // Debug: 查看创建结果
    console.log(response.data);
    // ElMessage.success("订购成功");
    ElMessage({
      message: "订购成功,二维码 已生成,请前往二维码列表查看",
      type: "success",
    });
  } else {
    console.error("订购失败");
  }

  router.push("/project-user-task-list");
};

const cancelProcess = async () => {};
const selectReviewedUseMaterial = () => {
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
  _projectMaterialViewList: IServerUseMaterialView[]
) => {
  dialogSelectProjectMaterialDialogVisible.value = false;

  const userId = getUserID();

  if (!userId) {
    ElMessageBox.alert("用户信息异常，请重新登录", "提示", {
      confirmButtonText: "确定",
    });
    return;
  }

  _projectMaterialViewList.forEach(async (item: IServerUseMaterialView) => {
    console.log(item,"ietememtmet");
    
    if (
      !useProjectMaterialViewList.value.some(
        (item2) =>
          item2.projectMaterialView.projectMaterial.id ===
          item.projectMaterialView.projectMaterial.id
      )
    ) {
      let privateBrandId = "";
      if (
        item.projectMaterialBrandPrivateView &&
        item.projectMaterialBrandPrivateView.projectMaterialBrandPrivate
      )
        privateBrandId =
          item.projectMaterialBrandPrivateView.projectMaterialBrandPrivate.id;
      let publicBrandId = "";
      if (
        item.projectMaterialBrandPublicView &&
        item.projectMaterialBrandPublicView.projectMaterialBrandPublic
      )
        publicBrandId =
          item.projectMaterialBrandPublicView.projectMaterialBrandPublic.id;

      let useMaterialNewBrandTemp;
      if (item.useMaterialNewBrandView)
        useMaterialNewBrandTemp =
          item.useMaterialNewBrandView.useMaterialNewBrand;

      const useMaterial: IBuyMaterial = {
        projectMaterialView: item.projectMaterialView,
        useMaterial: item.useMaterial,
        useMaterialNewBrand: useMaterialNewBrandTemp,
        buyMaterial: {
          id: "", //id,主键
          userId: userId, //t_user_id,外键,	t_user_id<-表t_user.id,购买用户（总包单位人员）购买用户（总包单位人员）
          useMaterialId: item.useMaterial.id, //t_use_material_id,外键,	t_use_material_id<-表t_use_material.id,物料使用申请物料使用申请
          projectMaterialBrandPrivateId: privateBrandId, //t_project_material_brand_private_id,外键,	t_project_material_brand_private_id<-表t_project_material_brand_private.id,私有品牌私有品牌
          projectMaterialBrandPublicId: publicBrandId, //t_project_material_brand_public_id,外键,	t_project_material_brand_public_id<-表t_project_material_brand_public.id,公有品牌公有品牌
          materialCount: 0, //material_count,材料数量材料数量
          materialUnit: item.useMaterial.materialUnit, //material_unit,数量单位数量单位
          batch: 0, //batch,批次批次
          qrcode: "", //qrcode,二维码二维码
          createDatetime: new Date(), //create_datetime,创建时间创建时间
          deletedAt: new Date(), //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
        },
        projectMaterialBrandPrivateView: item.projectMaterialBrandPrivateView,
        projectMaterialBrandPublicView: item.projectMaterialBrandPublicView,
      };
      useProjectMaterialViewList.value.push(useMaterial);
    }
  });
};
const textElipsisValue = ref(false);
</script>

<template>
  <!--选择项目物料-->
  <SelectReviewedUseMaterialDialog
    :dialogVisible="dialogSelectProjectMaterialDialogVisible"
    :projectId="projectId"
    @onDilalogCancel="onSelectProjectMaterialDialogCancel"
    @onDilalogOk="onSelectProjectMaterialDialogOk"
  >
  </SelectReviewedUseMaterialDialog>

  <!--显示项目列表、当前能够执行的操作（例如审核等）-->
  <div class="tab-container">
    <ProjectUserTaskInfo
      :projectUserTask="projectUserTask"
    ></ProjectUserTaskInfo>

    <div v-if="projectUserTask">
      <div class="project-container container">
        <div style="display: flex">
          <div>
            <el-button type="primary" @click="selectReviewedUseMaterial"
              >选择物料</el-button
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

        <el-table
          :data="useProjectMaterialViewList"
          style="width: 100%"
          stripe
          show-overflow-tooltip
        >
          <el-table-column label="材料名称" show-overflow-tooltip>
            <template #default="scope">
              <div style="display: flex; align-items: center">
                <span>{{ scope.row.projectMaterialView.material.name }}</span>
              </div>
            </template>
          </el-table-column>

          <el-table-column label="材料位置" show-overflow-tooltip>
            <template #default="scope">
              <div style="display: flex; align-items: center">
                <span>{{
                  scope.row.projectMaterialView.material.location
                }}</span>
              </div>
            </template>
          </el-table-column>

          <el-table-column label="编号" show-overflow-tooltip>
            <template #default="scope">
              <div style="display: flex; align-items: center">
                <span>{{
                  scope.row.projectMaterialView.material.itemMark
                }}</span>
              </div>
            </template>
          </el-table-column>

          <el-table-column label="材料数量" show-overflow-tooltip>
            <template #default="scope">
              <el-input v-model="scope.row.buyMaterial.materialCount" />
            </template>
          </el-table-column>

          <el-table-column label="数量单位" show-overflow-tooltip>
            <template #default="scope">
              <el-tooltip
                  class="box-item"
                  effect="dark"
                  content="请尽量保持数量单位和初始一致"
                  placement="top-start"
                >
                <el-input v-model="scope.row.buyMaterial.materialUnit" />
              </el-tooltip>
            </template>
          </el-table-column>

          <el-table-column label="品牌" show-overflow-tooltip>
            <template #default="scope">
              <!--项目物料品牌-->
              <span v-if="scope.row.projectMaterialBrandPublicView != null">{{
                scope.row.projectMaterialBrandPublicView.brandPublicView
                  .brandView.brand.name
              }}</span>

              <!--项目私有品牌-->
              <span
                v-else-if="scope.row.projectMaterialBrandPrivateView != null"
                >{{
                  scope.row.projectMaterialBrandPrivateView.projectBrandView
                    .brandView.brand.name
                }}</span
              >
            </template>
          </el-table-column>

          <el-table-column label="批次" show-overflow-tooltip>
            <template #default="scope"> </template>
          </el-table-column>
        </el-table>
        <el-alert title="提交后可扫码确认详情，总包单位可填报材料" type="info" show-icon style="margin-top: 5px"/>
        <div style="margin: 10px; display: flex; justify-content: center">
          <el-button type="primary" @click="submitProcess">确定</el-button>
          <el-button type="primary" @click="cancelProcess">取消</el-button>
        </div>
      </div>

      <!--反馈-->
      <SupervisionNeedReCheckNotPassedProjectMaterialAcceptanceFeedback
        :projectUserTask="projectUserTask"
      >
      </SupervisionNeedReCheckNotPassedProjectMaterialAcceptanceFeedback>

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

::v-deep .el-table .cell {
  white-space: pre-line;
}
</style>
