<script setup lang="ts">
/**
 * 对单位进行管理 *
 * 主要功能：增删改查
 */

import { computed, onMounted, reactive, ref, Ref, watch, watchEffect } from "vue";

import { useRouter, useRoute, onBeforeRouteUpdate } from "vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";

import {
  IServerCompany,
  IServerCompanyUser,
  IServerCompanyUserView,
} from "@/server/types/system/company";

import {
  IServerProject,
  IServerProjectView,
  IServerProjectUser,
  IServerProjectUserView,
  IServerProjectAllUserView,
} from "@/server/types/project/project";
import {
  serverProjectUserAdd,
  serverProjectUserAddManager,
  serverProjectUserAddEmployee,
  serverProjectUserDelete,
  serverProjectUserDeleteById,
  serverProjectUserUpdate,
  serverGetProjectUserById,
  serverGetProjectUserByUerId,
  serverGetProjectUserByProjectId,
  serverGetProjectUserByRoleId,
  serverGetProjectAllUserViewByProjectId,
  serverGetProjectUserPage,
  serverGetProjectAllUsersPageView,
  serverGetProjectAllUsersPageViewByProjectName,
  serverGetProjectAllUsersPageViewByUserName,
} from "@/server/project/projectuser";

import {
  serverStartProcess,
  serverSubmitAdminSetDesignAndEngineeringDepartmentUsers,
  serverGetTaskByCurrentLoginUser,
  serverGetTaskByCurrentLoginUserAndProjectId,
  serverSubmitProjectMaterialReviewOfManagerDirect,
  serverSubmitProjectMaterialReviewManagerDistributeToEmployees,
  serverSubmitProjectMaterialReviewOfEmployee,
  serverSubmitProjectMaterialReviewOfManagerSummary,
} from "@/server/project/projectmaterialflow";

import { IServerProjectUserTask } from "@/server/types/project/flow";

//服务器返回到前端的类型
import { IServerResponseData, IServerPage } from "@/server/types/System";

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

import NewProjectUserDialog from "@/components/project/NewProjectUserDialog.vue";
import ProjectUserItem from "@/components/project/ProjectUserItem.vue";
import ProjectDesignCompanyUserItem from "@/components/project/ProjectDesignCompanyUserItem.vue";
import ProjectUserTaskInfo from "@/components/project/flow/ProjectUserTaskInfo.vue";

import ProjectReviewHistoryList from "@/components/project/flow/ProjectReviewHistoryList.vue";

import {
  getTaskName,
  getTaskProjectName,
  getTaskTitle,
} from "@/components/project/flow/index";

const router = useRouter();
const route = useRoute();

const dialogFormNewVisible = ref(false); //控制“新增对话框”是否显示
const dialogCompanyUserType = ref(0); //对话框用户类型
const dialogProjectView = ref<IServerProjectView>();
const formLabelWidth = "140px";
const ruleFormRef = ref<FormInstance>();
const form = reactive({
  id: "",
  name: "",
  note: "",
});
const loading = ref(false);

const updateCompany = ref<IServerCompany>({
  id: "", //id,主键
  name: "", //单位名称单位名称
  companyType: "", //company_type,单位类型：设计单位、设计部、工程部、监理单位、总包单位等单位类型：设计单位、设计部、工程部、监理单位、总包单位等
  note: "", //note,备注备注
  deletedAt: new Date(),
});
const companyId = ref("");

const searchText = ref("");
const searchSelect = ref("1");

const rules = reactive<FormRules>({
  name: [{ required: true, message: "请输入公司名称", trigger: "blur" }],
});

const pageNo = ref(1); //第几页
const pageSize = ref(getUserPageSize()); //每页多少数据

const projectAllUserPageViewData = ref<IServerProjectAllUserView>();

const radioUserType = ref(0);
const projectUserTask: Ref<IServerProjectUserTask | undefined> = ref();

const projectId = ref("");

watchEffect(async () => {
  if (dialogFormNewVisible.value == false) {
    await getProjectAllUsersPageViewFromSever();
  }
}
  
);

onBeforeRouteUpdate(async (to) => {
  if (typeof to.params.id === "string") {
    projectId.value = to.params.id;
    await getUserTaskFromServerByProjectId(to.params.id);
  } else {
    getUserTaskFromServerByProjectId(to.params.id[0]);
    projectId.value = to.params.id[0];
  }

  await getProjectAllUsersPageViewFromSever();
});

onMounted(async () => {
  if (typeof route.params.id === "string") {
    await getUserTaskFromServerByProjectId(route.params.id);
    projectId.value = route.params.id;
  }
  await getProjectAllUsersPageViewFromSever();
});

const getUserTaskFromServerByProjectId = async (projectId: string) => {
  const ret = await serverGetTaskByCurrentLoginUserAndProjectId(projectId);
  if (ret && ret.code == 200) {
    projectUserTask.value = ret.data;
  }
};

const getProjectAllUsersPageViewFromSever = async () => {
  const ret = await serverGetProjectAllUserViewByProjectId(projectId.value);
  console.log(ret);
  if (ret && ret.code == 200) {
    projectAllUserPageViewData.value = ret.data;
  }
};

const onPagePrevClick = (value: number) => {};
const onPageNextClick = (value: number) => {};
const onPageCurrentChange = async (value: number) => {
  pageNo.value = value;
  await getProjectAllUsersPageViewFromSever();
};

const onPageSizeChange = async (value: number) => {
  pageSize.value = value;
  setUserPageSize(value);
  await getProjectAllUsersPageViewFromSever();
};

const onSearchClick = async () => {
  let search = searchText.value.trim();

  if (search) {
    pageNo.value = 1;
  }
  await getProjectAllUsersPageViewFromSever();
};

const goBack = () => {
  history.back();
};
/**
 * 增加设计单位项目员工
 */
const onNewDesignCompanyProjectUserButtonClick = (
  projectView: IServerProjectView,
  companyIdTemp: string
) => {
  companyId.value = companyIdTemp;
  dialogProjectView.value = projectView;
  dialogFormNewVisible.value = true;
  dialogCompanyUserType.value = 0;
};

/**
 * 增加设计部项目员工
 */
const onNewDesignDepartmentProjectUserButtonClick = (
  projectView: IServerProjectView
) => {
  dialogProjectView.value = projectView;
  dialogFormNewVisible.value = true;
  dialogCompanyUserType.value = 1;
};

/**
 * 增加工程部项目员工
 */
const onNewEngineeringDepartmentProjectUserButtonClick = (
  projectView: IServerProjectView
) => {
  dialogProjectView.value = projectView;
  dialogFormNewVisible.value = true;
  dialogCompanyUserType.value = 2;
};

/**
 * 增加监理单位项目员工
 */
const onNewSupervisionCompanyProjectUserButtonClick = (
  projectView: IServerProjectView
) => {
  dialogProjectView.value = projectView;
  dialogFormNewVisible.value = true;
  dialogCompanyUserType.value = 3;
};

/**
 * 增加总包单位项目员工
 */
const onNewConstructionCompanyProjectUserButtonClick = (
  projectView: IServerProjectView
) => {
  dialogProjectView.value = projectView;
  dialogFormNewVisible.value = true;
  dialogCompanyUserType.value = 4;
};
/**
 * 删除项目员工
 * @param item
 */
const onDeleteProjectUserButtonClick = (item: IServerProjectUserView) => {
  ElMessageBox.confirm("是否真的删除数据？", "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      await serverProjectUserDelete(item.projectUser);
      await getProjectAllUsersPageViewFromSever();
      ElMessage({
        type: "success",
        message: "完成删除",
      });
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "删除失败",
      });
    });
};

/**
 * 新增项目员工
 */
const onNewProjectUserDialogOk = async (
  projectView: IServerProjectView,
  val: IServerCompanyUserView[],
  type: number,
  role: string
) => {
  if (val.length > 0) {
    if (role == "项目经理") {
      let projectUser: IServerProjectUser = {
        id: "",
        userId: val[0].user.id,
        projectId: projectView.project.id,
        roleId: "",
        deletedAt: new Date(),
      };
      await serverProjectUserAddManager(projectUser);
    } else {
      val.forEach(async (item) => {
        let projectUser: IServerProjectUser = {
          id: "",
          userId: item.user.id,
          projectId: projectView.project.id,
          roleId: "",
          deletedAt: new Date(),
        };
        await serverProjectUserAddEmployee(projectUser);
      });
    }
    await getProjectAllUsersPageViewFromSever();
  }
  dialogFormNewVisible.value = false;
};

const onNewProjectUserDialogCancel = () => {
  dialogFormNewVisible.value = false;
};

const onSubmitProjectUserTask = async () => {
  if (projectUserTask && projectUserTask.value?.projectView) {
    const ret = await serverSubmitAdminSetDesignAndEngineeringDepartmentUsers(
      projectUserTask.value.projectView.project
    );
    if (ret && ret.code == 200) {
      ElMessage({
        type: "success",
        message: "完成设置",
      });
    } else {
      ElMessage({
        type: "error",
        message: "设置失败",
      });
    }

    router.push({
      path: `/project-user-task-list`,
    });
  }
};
</script>

<template>
  <!--新增对话框-->
  <NewProjectUserDialog
    :dialogVisible="dialogFormNewVisible"
    :projectView="dialogProjectView"
    :companyId="companyId"
    :type="dialogCompanyUserType"
    @onDilalogCancel="onNewProjectUserDialogCancel"
    @onDilalogOk="onNewProjectUserDialogOk"
  ></NewProjectUserDialog>

  <el-page-header @back="goBack" style="margin-bottom: 20px">
    <template #content>
      <span class="text-large font-600 mr-3">{{
        getTaskName(projectUserTask)
      }}</span>
    </template>
    <div class="mt-4 text-sm font-bold"></div>
  </el-page-header>

  <ProjectUserTaskInfo :projectUserTask="projectUserTask"></ProjectUserTaskInfo>

  <div v-if="projectUserTask">
    <!--显示内容-->

    <div class="admin-set-employee-container container">
      <!--设计单位员工列表，包括项目经理和项目员工-->
      <ProjectDesignCompanyUserItem
        :projectAllUserView="projectAllUserPageViewData"
        :type="0"
        @onNew="onNewDesignCompanyProjectUserButtonClick"
        @onDelete="onDeleteProjectUserButtonClick"
      >
      </ProjectDesignCompanyUserItem>

      <!--设计部员工列表，包括项目经理和项目员工-->
      <ProjectUserItem
        :projectAllUserView="projectAllUserPageViewData"
        :type="1"
        @onNew="onNewDesignDepartmentProjectUserButtonClick"
        @onDelete="onDeleteProjectUserButtonClick"
      >
      </ProjectUserItem>

      <!--工程部员工列表，包括项目经理和项目员工-->
      <ProjectUserItem
        :projectAllUserView="projectAllUserPageViewData"
        :refreashPage="getProjectAllUsersPageViewFromSever"
        :type="2"
        @onNew="onNewEngineeringDepartmentProjectUserButtonClick"
        @onDelete="onDeleteProjectUserButtonClick"
      >
      </ProjectUserItem>

      <!--监理单位员工列表，包括项目经理和项目员工-->
      <!-- <ProjectUserItem
        :projectAllUserView="projectAllUserPageViewData"
        :type="3"
        @onNew="onNewSupervisionCompanyProjectUserButtonClick"
        @onDelete="onDeleteProjectUserButtonClick"
      >
      </ProjectUserItem>-->

      <!--总包单位员工列表，包括项目经理和项目员工-->
      <!-- <ProjectUserItem
        :projectAllUserView="projectAllUserPageViewData"
        :type="4"
        @onNew="onNewConstructionCompanyProjectUserButtonClick"
        @onDelete="onDeleteProjectUserButtonClick"
      >
      </ProjectUserItem>-->

      <div style="margin: 10px; display: flex; justify-content: center">
        <el-button type="primary" @click="onSubmitProjectUserTask"
          >确定</el-button
        ><el-button @click="onSubmitProjectUserTask">退回</el-button>
      </div>
    </div>
    <!-- 审核记录-->
    <ProjectReviewHistoryList :projectId="projectId" />
  </div>
  <div v-else>当前项目没有任务</div>
</template>

<style scoped>
@import url("@/assets/css/basic.css");

.admin-set-employee-container {
  padding: 10px;
}
</style>
