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
} from "@/server/types/project/review";

import {
  IServerCompany,
  IServerCompanyUser,
  IServerCompanyUserView,
  IServerCompanyUserForm,
} from "@/server/types/system/company";

import { serverGetAllCompanyListByCompanyType } from "@/server/system/company";

import ProjectUserTaskList from "@/components/project/flow/ProjectUserTaskList.vue";
import ProjectMaterialList from "@/components/project/material/ProjectMaterialList.vue";

import { genUUID } from "@/utils/utils";
import { getUserID, getUserPageSize } from "@/cookies/user";
import ProjectUserTaskInfo from "@/components/project/flow/ProjectUserTaskInfo.vue";

import ProjectReviewHistoryList from "@/components/project/flow/ProjectReviewHistoryList.vue";

import {
  getTaskName,
  getTaskProjectName,
  getTaskTitle,
} from "@/components/project/flow/index";

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

const form = reactive({
  supervisionCompanyId: "",
  constructionCompanyId: "",
});

const supervisionCompanyList: Ref<IServerCompany[]> = ref([]);
const constructionCompanyList: Ref<IServerCompany[]> = ref([]);

onBeforeRouteUpdate(async (to) => {
  if (typeof to.params.id === "string") {
    projectId.value = to.params.id;
    await getUserTaskFromServerByProjectId(to.params.id);
  } else {
    getUserTaskFromServerByProjectId(to.params.id[0]);
    projectId.value = to.params.id[0];
  }
  await getCompanyFromServer();
});

onMounted(async () => {
  if (typeof route.params.id === "string") {
    await getUserTaskFromServerByProjectId(route.params.id);
    projectId.value = route.params.id;
  }

  await getCompanyFromServer();
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

const getCompanyFromServer = async () => {
  const ret1 = await serverGetAllCompanyListByCompanyType("监理单位");
  if (ret1 && ret1.code == 200) {
    supervisionCompanyList.value = ret1.data;
  }
  const ret2 = await serverGetAllCompanyListByCompanyType("总包单位");
  if (ret2 && ret2.code == 200) {
    constructionCompanyList.value = ret2.data;
  }
};

/**
 * 总包单位品牌选择（从备选品牌选择或新品牌并提供说明）,物料使用申请
 */
const submitProcess = async () => {
  console.log(fileList.value);

  if (form.supervisionCompanyId === "") {
    ElMessageBox.alert("请选择监理单位", "提示", {
      confirmButtonText: "确定",
    });
    return;
  }

  if (form.constructionCompanyId === "") {
    ElMessageBox.alert("请选择总包单位", "提示", {
      confirmButtonText: "确定",
    });
    return;
  }

  const userId = getUserID();
  console.log(userId);
  if (!userId) {
    ElMessageBox.alert("用户信息异常，请重新登录", "提示", {
      confirmButtonText: "确定",
    });
    return;
  }

  let projectCompany: IServerProjectCompany = {
    id: "", //id,主键
    userId: userId, //t_user_id,外键,	t_user_id<-表t_user.id,用户，工程部员工用户，工程部员工
    projectId: projectId.value, //t_project_id
    generalContractorCompanyId: form.constructionCompanyId, //t_general_contractor_company_id,外键,	t_general_contractore_company_id<-表t_company.id,总包单位总包单位
    supervisionCompanyId: form.supervisionCompanyId, //t_supervision_company_id,外键,	t_supervision_company_id<-表t_company.id,监理单位监理单位
    createDatetime: new Date(), //create_datetime,分配时间分配时间
    deletedAt: new Date(), //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
  };
  // 调用API
  const response = await serverSubmitEngineeringDepartmentManagerDispatch(
    projectCompany
  );
  console.log(response);
  if (response && response.code === 200) {
    // Debug: 查看创建结果
    console.log(response.data);
    ElMessage.success("分配成功");
  } else {
    console.error("分配失败");
  }

  router.push("/project-user-task-list");
};

const cancelProcess = async () => {};
</script>

<template>
  <!--显示项目列表、当前能够执行的操作（例如审核等）-->
  <div class="tab-container">
    <ProjectUserTaskInfo
      :projectUserTask="projectUserTask"
    ></ProjectUserTaskInfo>
    <div v-if="projectUserTask">
      <div class="container">
        <div style="padding: 10px">
          <div
            style="font: 1.2em sans-serif; margin: 10px; margin-bottom: 20px"
          >
            分发监理单位与总包单位
          </div>
          <el-form :model="form" label-width="auto" style="width: 100%">
            <el-form-item label="监理单位">
              <el-select
                v-model="form.supervisionCompanyId"
                placeholder="Select"
                style="width: 100%"
              >
                <el-option
                  v-for="item in supervisionCompanyList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="总包单位">
              <el-select
                v-model="form.constructionCompanyId"
                placeholder="Select"
                style="width: 100%"
              >
                <el-option
                  v-for="item in constructionCompanyList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-form>
        </div>
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
</style>
