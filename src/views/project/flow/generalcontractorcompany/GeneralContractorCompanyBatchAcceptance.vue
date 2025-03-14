<script setup lang="ts">
/**
 * 文件说明：
 *
 * 总包单位新建批量验收
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
  serverSubmitGeneralContractorProjectMaterialBatchAcceptancet,
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
  IServerProjectMaterialAcceptance,
  IServerProjectMaterialAcceptanceBatch,
  IServerProjectMaterialAcceptanceBatchView,
  IServerProjectMaterialAcceptanceView,
  IServerProjectMaterialAcceptanceReviewMode,
  IServerProjectMaterialAcceptanceReviewModeView,
  IServerProjectMaterialAcceptanceReview,
  IServerProjectMaterialAcceptanceReviewUser,
  IServerProjectMaterialAcceptanceReviewUserFile,
  IServerProjectMaterialAcceptanceReviewUserFileView,
  IServerProjectMaterialAcceptanceReviewUserView,
  IServerProjectMaterialAcceptanceReviewView,
  IServerProjectMaterialAcceptanceBatchForm,
  IServerProjectMaterialAcceptanceReviewDispatchForm,
  IServerProjectMaterialAcceptanceReviewEmployeeForm,
  IServerProjectMaterialAcceptanceReviewManagerDirectForm,
  IServerProjectMaterialAcceptanceReviewManagerSummaryForm,
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
import SelectBoughtMaterialDialog from "@/components/project/material/SelectBoughtMaterialDialog.vue";
import ProjectMaterialAcceptanceFeedback from "@/components/project/review/ProjectMaterialAcceptanceFeedback.vue";
import EngineeringdepartmentProjectMaterialAcceptanceFeedback from "@/components/project/review/EngineeringdepartmentProjectMaterialAcceptanceFeedback.vue";
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

interface IBatchAcceptanceItem {
  materialCount: number; //数量
  materialUnit: string; //数量单位
  projectMaterialBrandPrivateView: IServerProjectMaterialBrandPrivateView | null; //私有品牌
  projectMaterialBrandPublicView: IServerProjectMaterialBrandPublicView | null; //公共品牌

  position: string; //验收位置
  note: string; //备注
}

interface IBuyMaterial {
  projectMaterialView: IServerProjectMaterialView;
  batchAcceptanceItemList: IBatchAcceptanceItem[];
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

  let useMaterialFormList: IServerUseMaterialFormItem[] = [];

  //每种材料可能有多个品牌，但要求该材料至少有一个品牌的数量和数量单位都不能为空
  for (let i = 0; i < useProjectMaterialViewList.value.length; i++) {
    let item = useProjectMaterialViewList.value[i];
    let tempcount = 0;
    for (let item2 of item.batchAcceptanceItemList) {
      if (item2.materialCount <= 0 || item2.materialUnit == "") {
        tempcount++;
      }
    }

    if (tempcount == item.batchAcceptanceItemList.length) {
      ElMessageBox.alert("数量和数量单位都不能为空", "提示", {
        confirmButtonText: "确定",
      });
      return;
    }
  }

  let projectMaterialAcceptanceList: IServerProjectMaterialAcceptance[] = [];
  useProjectMaterialViewList.value.forEach((item) => {
    //每个物料有多个品牌，每个品牌有数量和数量单位

    item.batchAcceptanceItemList.forEach((item2) => {
      let privateBrandId = "";
      if (
        item2.projectMaterialBrandPrivateView &&
        item2.projectMaterialBrandPrivateView.projectMaterialBrandPrivate
      )
        privateBrandId =
          item2.projectMaterialBrandPrivateView.projectMaterialBrandPrivate.id;

      let pbblicBrandId = "";
      if (
        item2.projectMaterialBrandPublicView &&
        item2.projectMaterialBrandPublicView.projectMaterialBrandPublic
      )
        pbblicBrandId =
          item2.projectMaterialBrandPublicView.projectMaterialBrandPublic.id;

      if (item2.materialCount > 0 && item2.materialUnit.length > 0) {
        let projectMaterialAcceptance: IServerProjectMaterialAcceptance = {
          id: "", //id,主键
          userId: userId, //t_user_id,外键,	t_user_id<-表t_user.id
          projectMaterialAcceptanceBatchId: "", //t_project_material_acceptance_batch_id,外键,	t_project_material_acceptance_batch_id<-表t_project_material_acceptance_batch.id
          projectMaterialId: item.projectMaterialView.projectMaterial.id, //t_project_material_id,外键,	t_project_material_id<-表t_project_material.id
          projectMaterialBrandPrivateId: privateBrandId, //t_project_material_brand_private_id,外键,	t_project_material_brand_private_id<-表t_project_material_brand_private.id
          projectMaterialBrandPublicId: pbblicBrandId, //t_project_material_brand_public_id,外键,	t_project_material_brand_public_id<-表t_project_material_brand_public.id
          materialCount: item2.materialCount, //material_count,材料数量材料数量
          materialUnit: item2.materialUnit, //material_unit,数量单位数量单位
          position: item2.position,
          note: item2.note,
          createDatetime: new Date(), //create_datetime
          deletedAt: new Date(), //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
        };
        projectMaterialAcceptanceList.push(projectMaterialAcceptance);
      }
    });
  });

  let batchForm: IServerProjectMaterialAcceptanceBatchForm = {
    projectId: projectId.value,
    taskId: taskId.value,
    projectMaterialAcceptanceBatch: {
      id: "", //id,主键
      userId: userId, //t_user_id,外键,	t_user_id<-表t_user.id
      projectId: projectId.value, //t_project_id,外键,	t_project_id<-表t_project.id
      createDatetime: new Date(), //create_datetime
      deletedAt: new Date(), //deleted_at\
    },
    projectMaterialAcceptanceList: projectMaterialAcceptanceList,
  };

  // 调用API
  const response =
    await serverSubmitGeneralContractorProjectMaterialBatchAcceptancet(
      batchForm
    );
  console.log(response);
  if (response && response.code === 200) {
    // Debug: 查看创建结果
    console.log(response.data);
    ElMessage.success("验收成功");
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
  _projectMaterialViewList: IServerProjectMaterialView[]
) => {
  console.log(_projectMaterialViewList);
  dialogSelectProjectMaterialDialogVisible.value = false;

  const userId = getUserID();

  if (!userId) {
    ElMessageBox.alert("用户信息异常，请重新登录", "提示", {
      confirmButtonText: "确定",
    });
    return;
  }

  _projectMaterialViewList.forEach(async (item: IServerProjectMaterialView) => {
    if (
      !useProjectMaterialViewList.value.some(
        (item2) =>
          item2.projectMaterialView.projectMaterial.id ===
          item.projectMaterial.id
      )
    ) {
      let batchAcceptanceItemList: IBatchAcceptanceItem[] = [];
      if (
        item.projectMaterialBrandPrivateViewList &&
        item.projectMaterialBrandPrivateViewList.length > 0
      ) {
        item.projectMaterialBrandPrivateViewList.forEach((itemBrand) => {
          let batchAcceptanceItem: IBatchAcceptanceItem = {
            materialCount: 0,
            materialUnit: "",
            projectMaterialBrandPrivateView: itemBrand,
            projectMaterialBrandPublicView: null,
            position: "",
            note: "",
          };
          batchAcceptanceItemList.push(batchAcceptanceItem);
        });
      }

      if (
        item.projectMaterialBrandPublicViewList &&
        item.projectMaterialBrandPublicViewList.length > 0
      ) {
        item.projectMaterialBrandPublicViewList.forEach((itemBrand) => {
          let batchAcceptanceItem: IBatchAcceptanceItem = {
            materialCount: 0,
            materialUnit: "",
            projectMaterialBrandPrivateView: null,
            projectMaterialBrandPublicView: itemBrand,
            position: "",
            note: "",
          };
          batchAcceptanceItemList.push(batchAcceptanceItem);
        });
      }

      const useMaterial: IBuyMaterial = {
        projectMaterialView: item,
        batchAcceptanceItemList: batchAcceptanceItemList,
      };
      useProjectMaterialViewList.value.push(useMaterial);
    }
  });
};
</script>

<template>
  <!--选择已经购买的项目物料-->
  <SelectBoughtMaterialDialog
    :dialogVisible="dialogSelectProjectMaterialDialogVisible"
    :projectId="projectId"
    @onDilalogCancel="onSelectProjectMaterialDialogCancel"
    @onDilalogOk="onSelectProjectMaterialDialogOk"
  >
  </SelectBoughtMaterialDialog>

  <!--显示项目列表、当前能够执行的操作（例如审核等）-->
  <div class="tab-container">
    <ProjectUserTaskInfo
      :projectUserTask="projectUserTask"
    ></ProjectUserTaskInfo>

    <div v-if="projectUserTask">
      <div class="project-container container">
        <div>
          <el-button type="primary" @click="selectReviewedUseMaterial"
            >选择物料</el-button
          >
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

          <el-table-column label="物料品牌、数量" show-overflow-tooltip>
            <template #default="scope">
              <div v-for="item in scope.row.batchAcceptanceItemList">
                <!--项目物料品牌-->
                <el-form-item label="物料品牌">
                  <!--项目公有品牌-->
                  <div v-if="item.projectMaterialBrandPublicView != null">
                    {{
                      item.projectMaterialBrandPublicView.brandPublicView
                        .brandView.brand.name
                    }}
                  </div>
                  <!--项目私有品牌-->
                  <div v-if="item.projectMaterialBrandPrivateView != null">
                    {{
                      item.projectMaterialBrandPrivateView.projectBrandView
                        .brandView.brand.name
                    }}
                  </div>
                </el-form-item>

                <!--材料数量-->
                <el-form-item
                  label="物料数量"
                  :rules="[
                    {
                      required: true,
                      message: '请输入物料数量',
                      trigger: 'blur',
                    },
                  ]"
                >
                  <el-input v-model="item.materialCount" />
                </el-form-item>

                <!--数量单位-->
                <el-form-item
                  label="数量单位"
                  :rules="[
                    {
                      required: true,
                      message: '请输入数量单位',
                      trigger: 'blur',
                    },
                  ]"
                >
                  <el-input v-model="item.materialUnit" />
                </el-form-item>

                <!--验收位置-->
                <el-form-item
                  label="验收位置"
                  :rules="[
                    {
                      required: true,
                      message: '请输入验收位置',
                      trigger: 'blur',
                    },
                  ]"
                >
                  <el-input v-model="item.position" />
                </el-form-item>

                <!--备注-->
                <el-form-item label="备注">
                  <el-input v-model="item.note" />
                </el-form-item>
              </div>
            </template>
          </el-table-column>

          <el-table-column label="批次" show-overflow-tooltip>
            <template #default="scope"> </template>
          </el-table-column>
        </el-table>

        <div style="margin: 10px; display: flex; justify-content: center">
          <el-button type="primary" @click="submitProcess">确定</el-button>
          <el-button type="primary" @click="cancelProcess">取消</el-button>
        </div>
      </div>
      <ProjectMaterialAcceptanceFeedback :projectUserTask="projectUserTask">
      </ProjectMaterialAcceptanceFeedback>
      <EngineeringdepartmentProjectMaterialAcceptanceFeedback
        :projectUserTask="projectUserTask"
      >
      </EngineeringdepartmentProjectMaterialAcceptanceFeedback>

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
