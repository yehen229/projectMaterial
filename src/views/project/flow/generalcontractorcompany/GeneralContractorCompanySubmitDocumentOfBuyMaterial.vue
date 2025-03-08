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
  serverGetTaskByCurrentLoginUserAndProjectIdAndTaskId,
  serverSubmitProjectMaterialReviewOfManagerDirect,
  serverSubmitProjectMaterialReviewManagerDistributeToEmployees,
  serverSubmitProjectMaterialReviewOfEmployee,
  serverSubmitProjectMaterialReviewOfManagerSummary,
  serverSubmitEngineeringDepartmentManagerDispatch,
  serverSubmitGeneralContractorSelectBrand,
  serverSubmitGeneralContractorBuyProjectMaterial,
  serverSubmitGeneralContractorSubProjectMaterialVerificationDocument,
  serverGetBuyMaterialVerificationDocumentViewByProjectIdAndTaskId,
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
  IServerProjectMaterialVerificationDocumentView,
  IServerProjectMaterialVerificationDocumentFile,
  IServerProjectMaterialVerificationDocument,
  IServerGeneralContractorSubProjectMaterialVerificationDocumentForm,
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
import { IServerResponseData, IServerPage } from "@/server/types/System";

import {
  serverGetBuyMaterialPageViewByProjectId,
  serverGetBuyMaterialVerificationDocumentPageViewByProjectId,
} from "@/server/project/usematerial";

import ProjectUserTaskList from "@/components/project/flow/ProjectUserTaskList.vue";
import ProjectMaterialList from "@/components/project/material/ProjectMaterialList.vue";
import SelectProjectMaterialDialog from "@/components/project/material/SelectProjectMaterialDialog.vue";
import SelectProjectMaterialBrand from "@/components/project/material/SelectProjectMaterialBrand.vue";
import SelectReviewedUseMaterialDialog from "@/components/project/material/SelectReviewedUseMaterialDialog.vue";
const dialogSelectProjectMaterialDialogVisible = ref(false); //控制“选择项目物料对话框”是否显示

const dialogSelectProjectMaterialBrandDialogVisible = ref(false); //控制“选择项目物料品牌对话框”是否显示

import { genUUID } from "@/utils/utils";
import {
  setUserCookies,
  getUserName,
  getUserID,
  getUserRealName,
  hasRole,
  isTeacher,
  isAdmin,
  isStudent,
  getUserPageSize,
  setUserPageSize,
} from "@/cookies/user";
import ProjectUserTaskInfo from "@/components/project/flow/ProjectUserTaskInfo.vue";

import ProjectReviewHistoryList from "@/components/project/flow/ProjectReviewHistoryList.vue";
import BuyMaterialDocument from "@/components/project/material/BuyMaterialDocument.vue";
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

interface IBuyMaterialDocument {
  documentView: IServerProjectMaterialVerificationDocumentView;
  tempDir0: string; //上传文件临时目录：工程材料
  uploadFileCount0: number; //上传文件数量：工程材料
  tempDir1: string; //上传文件临时目录：设备报验材料
  uploadFileCount1: number; //上传文件数量：设备报验材料
}

const buyMaterialViewList =
  ref<IServerProjectMaterialVerificationDocumentView[]>();

const buyMaterialDocumentViewList = ref<IBuyMaterialDocument[]>([]);

// 页码和页大小
const pageNo = ref(1);
const pageSize = ref(getUserPageSize());

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

  await getBuyMaterialVerificationDocumentPageViewFromServer(
    projectId.value,
    taskId.value
  );
});

onMounted(async () => {
  if (typeof route.params.id === "string") {
    projectId.value = route.params.id;
  }
  if (typeof route.params.taskId === "string") {
    taskId.value = route.params.taskId;
  }

  await getUserTaskFromServerByProjectId(projectId.value, taskId.value);

  await getBuyMaterialVerificationDocumentPageViewFromServer(
    projectId.value,
    taskId.value
  );
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

const getBuyMaterialVerificationDocumentPageViewFromServer = async (
  projectId: string,
  taskId: string
) => {
  const ret =
    await serverGetBuyMaterialVerificationDocumentViewByProjectIdAndTaskId(
      projectId,
      taskId
    );
  if (ret && ret.code == 200) {
    buyMaterialViewList.value = ret.data;
  }
};

const goBack = () => {
  history.back();
};

/**
 *
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

  if (
    !projectUserTask.value ||
    !projectUserTask.value.projectView ||
    !projectUserTask.value.projectView.project
  ) {
    ElMessageBox.alert("工程信息异常，请重新登录", "提示", {
      confirmButtonText: "确定",
    });
    return;
  }

  //确保工程材料和 设备报验材料 文件数量必须大于0
  console.log(buyMaterialViewList);
  if (!buyMaterialViewList.value) return;

  for (var i = 0; i < buyMaterialViewList.value?.length; i++) {
    const item = buyMaterialViewList.value[i];
    if (!item.projectMaterialVerificationDocument) {
      ElMessageBox.alert(
        item.buyMaterialView.useMaterialView.projectMaterialView.material.name +
          ":材料为空，请填报材料",
        "提示",
        {
          confirmButtonText: "确定",
        }
      );
      return;
    }
    if (
      !item.projectMaterialVerificationDocumentFileList ||
      !item.projectMaterialVerificationDocumentFileList.length
    ) {
      ElMessageBox.alert(
        item.buyMaterialView.useMaterialView.projectMaterialView.material.name +
          ":文件为空，请填报材料",
        "提示",
        {
          confirmButtonText: "确定",
        }
      );
      return;
    }
  }

  const form: IServerGeneralContractorSubProjectMaterialVerificationDocumentForm =
    {
      project: projectUserTask.value?.projectView.project,
      taskId: projectUserTask.value.taskId,
    };
  // 调用API
  const response =
    await serverSubmitGeneralContractorSubProjectMaterialVerificationDocument(
      form
    );
  console.log(response);
  if (response && response.code === 200) {
    // Debug: 查看创建结果
    console.log(response.data);
    ElMessage.success("填报成功");
  } else {
    console.error("填报失败");
  }

  router.push("/project-user-task-list");
};

const cancelProcess = async () => {};

const onUploadDocument0Success = async (
  documentView: IServerProjectMaterialVerificationDocumentView,
  tempDir: string,
  uploadFileCount: number
) => {
  buyMaterialDocumentViewList.value.forEach((item) => {
    if (
      item.documentView.buyMaterialView.buyMaterial.id ===
      documentView.buyMaterialView.buyMaterial.id
    ) {
      item.tempDir0 = tempDir;
      item.uploadFileCount0 = uploadFileCount;
    }
  });
  await getBuyMaterialVerificationDocumentPageViewFromServer(
    projectId.value,
    taskId.value
  );
};

const onUploadDocument1Success = async (
  documentView: IServerProjectMaterialVerificationDocumentView,
  tempDir: string,
  uploadFileCount: number
) => {
  await getBuyMaterialVerificationDocumentPageViewFromServer(
    projectId.value,
    taskId.value
  );
};
</script>

<template>
  <!--显示项目列表、当前能够执行的操作（例如审核等）-->
  <div class="tab-container">
    <ProjectUserTaskInfo
      :projectUserTask="projectUserTask"
    ></ProjectUserTaskInfo>

    <div v-if="projectUserTask">
      <div class="project-container container">
        <!--显示内容-->

        <el-table
          :data="buyMaterialViewList"
          style="width: 100%"
          stripe
          show-overflow-tooltip
        >
          <el-table-column label="材料名称" show-overflow-tooltip>
            <template #default="scope">
              <div style="display: flex; align-items: center">
                <span>{{
                  scope.row.buyMaterialView.useMaterialView.projectMaterialView
                    .material.name
                }}</span>
              </div>
            </template>
          </el-table-column>

          <el-table-column label="材料位置" show-overflow-tooltip>
            <template #default="scope">
              <div style="display: flex; align-items: center">
                <span>{{
                  scope.row.buyMaterialView.useMaterialView.projectMaterialView
                    .material.location
                }}</span>
              </div>
            </template>
          </el-table-column>

          <el-table-column label="编号" show-overflow-tooltip>
            <template #default="scope">
              <div style="display: flex; align-items: center">
                <span>{{
                  scope.row.buyMaterialView.useMaterialView.projectMaterialView
                    .material.itemMark
                }}</span>
              </div>
            </template>
          </el-table-column>

          <el-table-column label="材料数量" show-overflow-tooltip>
            <template #default="scope">
              <span>{{
                scope.row.buyMaterialView.buyMaterial.materialCount
              }}</span>
            </template>
          </el-table-column>

          <el-table-column label="数量单位" show-overflow-tooltip>
            <template #default="scope">
              <span>{{
                scope.row.buyMaterialView.buyMaterial.materialUnit
              }}</span>
            </template>
          </el-table-column>

          <el-table-column label="品牌" show-overflow-tooltip>
            <template #default="scope">
              <!--项目物料品牌-->
              <span
                v-if="
                  scope.row.buyMaterialView.projectMaterialBrandPublicView !=
                  null
                "
                >{{
                  scope.row.buyMaterialView.projectMaterialBrandPublicView
                    .brandPublicView.brandView.brand.name
                }}</span
              >

              <!--项目私有品牌-->
              <span
                v-else-if="
                  scope.row.buyMaterialView.projectMaterialBrandPrivateView !=
                  null
                "
                >{{
                  scope.row.buyMaterialView.projectMaterialBrandPrivateView
                    .projectBrandView.brandView.brand.name
                }}</span
              >
            </template>
          </el-table-column>

          <el-table-column label="批次" show-overflow-tooltip>
            <template #default="scope"> </template>
          </el-table-column>

          <el-table-column label="工程材料" show-overflow-tooltip width="250px">
            <template #default="scope">
              <BuyMaterialDocument
                :documentView="scope.row"
                :fileType="0"
                :fileReadOnly="false"
                @onUploadSuccess="onUploadDocument0Success"
              ></BuyMaterialDocument>
            </template>
          </el-table-column>

          <el-table-column
            label="设备报验材料"
            show-overflow-tooltip
            width="250px"
          >
            <template #default="scope">
              <BuyMaterialDocument
                :documentView="scope.row"
                :fileType="1"
                :fileReadOnly="false"
                @onUploadSuccess="onUploadDocument1Success"
              ></BuyMaterialDocument>
            </template>
          </el-table-column>
        </el-table>

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
</style>
