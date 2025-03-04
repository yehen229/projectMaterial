<script setup lang="ts">
/**
 * 对项目材料进行管理 *
 * 主要功能：增删改查
 */

import { computed, onMounted, reactive, ref, Ref, shallowRef } from "vue";

import { useRouter, useRoute, onBeforeRouteUpdate } from "vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import {
  View,
  Hide,
  Search,
  Plus,
  Download,
  Upload,
} from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";

import { IServerMaterial } from "@/server/types/system/material";

import {
  IServerProject,
  IServerProjectView,
  IServerProjectUser,
  IServerProjectUserView,
  IServerProjectMaterial,
  IServerProjectMaterialView,
  IServerProjectMaterialForm,
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
  serverProjectMaterialAdd,
  serverProjectMaterialAddForm,
  serverProjectMaterialDelete,
  serverProjectMaterialDeleteById,
  serverProjectMaterialUpdate,
  serverProjectMaterialUpdateForm,
  serverGetProjectMaterialById,
  serverGetProjectMaterialPage,
  serverGetProjectMaterialPageView,
  serverGetProjectMaterialPageViewByProject,
} from "@/server/project/projectmaterial";

import {
  serverStartProcess,
  serverDesignCompanySubmitProjectMaterial,
  serverGetTaskByCurrentLoginUser,
  serverGetTaskByCurrentLoginUserAndProjectId,
  serverSubmitProjectMaterialReviewOfManagerDirect,
  serverSubmitProjectMaterialReviewManagerDistributeToEmployees,
  serverSubmitProjectMaterialReviewOfEmployee,
  serverSubmitProjectMaterialReviewOfManagerSummary,
  serverSubmitEngineeringDepartmentManagerDispatch,
  serverGetTaskHistoryByProjectId,
} from "@/server/project/projectmaterialflow";

import {
  IServerProjectUserTask,
  IServerProjectHistory,
} from "@/server/types/project/flow";

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

//服务器返回到前端的类型
import { IServerPage } from "@/server/types/System";

import { getUserPageSize, setUserPageSize } from "@/cookies/user";

import NewProjectMaterialDialog from "@/components/project/material/NewProjectMaterialDialog.vue";
import UpdateProjectMaterialDialog from "@/components/project/material/UpdateProjectMaterialDialog.vue";
import UploadExcelProjectMaterialDialog from "@/components/project/material/UploadExcelProjectMaterialDialog.vue";
import ProjectMaterialList from "@/components/project/material/ProjectMaterialList.vue";
import { formatDate } from "../../../utils/utils";
import ProjectReviewHistoryList from "@/components/project/flow/ProjectReviewHistoryList.vue";

const router = useRouter();
const route = useRoute();

const dialogFormNewVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormUpdateVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormExcelVisible = ref(false); //控制“上传Excel对话框”是否显示

const loading = ref(false);

const searchText = ref("");
const searchSelect = ref("1");

const pageNo = ref(1); //第几页
const pageSize = ref(getUserPageSize()); //每页多少数据

const projectMaterialViewPageData =
  ref<IServerPage<IServerProjectMaterialView> | null>(null);
const updateProjectMaterialView = ref<IServerProjectMaterialView>();
const projectViewData = ref<IServerProjectView>();
const radioUserType = ref(0);
const projectUserTask: Ref<IServerProjectUserTask | undefined> = ref();

const projectHistoryList = ref<IServerProjectHistory[]>();

const projectId = ref("");
onBeforeRouteUpdate(async (to) => {
  if (typeof to.params.id === "string") {
    projectId.value = to.params.id;
    await getProjectFromServer(to.params.id);
  } else {
    getProjectFromServer(to.params.id[0]);
    projectId.value = to.params.id[0];
  }

  await getProjectMaterialViewFromServer();
});

onMounted(async () => {
  if (typeof route.params.id === "string") {
    await getProjectFromServer(route.params.id);
    projectId.value = route.params.id;
  }

  await getProjectMaterialViewFromServer();
});

const getProjectFromServer = async (projectId: string) => {
  const ret = await serverGetProjectViewById(projectId);
  console.log(ret);
  if (ret && ret.code == 200) {
    projectViewData.value = ret.data;
  }
};

const getProjectMaterialViewFromServer = async () => {
  let search = searchText.value.trim();

  if (search) {
    console.log(searchSelect.value);

    if (searchSelect.value == "0") {
      //单位类型
      console.log(search);
      const ret = await serverGetCompanyPageByCompanyName(
        projectId.value,
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        projectMaterialViewPageData.value = ret.data;
      }
    } else if (searchSelect.value == "1") {
      //单位名称
      const ret = await serverGetCompanyPageByCompanyType(
        projectId.value,
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        projectMaterialViewPageData.value = ret.data;
      }
    }
  } else {
    const ret = await serverGetProjectMaterialPageViewByProject(
      projectId.value,
      pageNo.value,
      pageSize.value
    );
    console.log(ret);
    if (ret && ret.code == 200) {
      projectMaterialViewPageData.value = ret.data;
    }
  }
};

const tableData = computed(() => {
  return projectMaterialViewPageData.value?.result ?? [];
});

const totalCount = computed(() => {
  return Number(projectMaterialViewPageData.value?.totalCount ?? 0);
});

const onPagePrevClick = (value: number) => {};
const onPageNextClick = (value: number) => {};
const onPageCurrentChange = async (value: number) => {
  pageNo.value = value;
  await getProjectMaterialViewFromSever();
};

const onPageSizeChange = async (value: number) => {
  pageSize.value = value;
  setUserPageSize(value);
  await getProjectMaterialViewFromSever();
};

const onSearchClick = async () => {
  let search = searchText.value.trim();

  if (search) {
    pageNo.value = 1;
  }
  await getProjectMaterialViewFromSever();
};

const goBack = () => {
  history.back();
};

/**
 * 上传Excel文件，导入材料
 */
const onExcelUploadButtonClick = () => {
  dialogFormExcelVisible.value = true;
};

/**
 * 下载用户名单
 * @param index
 * @param row
 */
const onDownloadExcelButtonClick = async () => {
  const downloadFilename = "材料名单";

  loading.value = true;
  let search = searchText.value.trim();

  if (search) {
    if (searchSelect.value == "用户名称") {
      //用户名称
      const ret = await serverDownloadCompanyUserByUserNamer(
        searchText.value,
        downloadFilename
      );
    } else if (searchSelect.value == "项目名称") {
      //项目名称
      const ret = await serverDownloadCompanyUserByProjectName(
        searchText.value,
        downloadFilename
      );
    } else if (searchSelect.value == "单位名称") {
      //单位名称
      const ret = await serverDownloadCompanyUserByCompanyName(
        searchText.value,
        downloadFilename
      );
    }
  } else {
    await serverDownloadAllCompanyUser(downloadFilename);
  }

  loading.value = false;
};

const projectLocation = computed(() => {
  if (
    projectViewData.value &&
    projectViewData.value.project &&
    projectViewData.value.project.location
  )
    return (
      JSON.parse(projectViewData.value.project.location)[0] +
      "-" +
      JSON.parse(projectViewData.value.project.location)[1]
    );

  return "";
});
</script>

<template>
  <el-page-header @back="goBack" style="margin-bottom: 20px">
    <template #content>
      <span class="text-large font-600 mr-3">项目详细信息</span>
    </template>
    <div class="mt-4 text-sm font-bold"></div>
  </el-page-header>

  <h1>《{{ projectViewData?.project.name }}》项目详细信息</h1>

  <!--项目信息-->
  <el-card class="box-card" style="margin-bottom: 10px">
    <div slot="header" class="clearfix">
      <span>项目信息</span>
    </div>
    <div>
      <el-row>
        <el-col :span="6">项目名称</el-col>
        <el-col :span="18">{{ projectViewData?.project.name }}</el-col>
      </el-row>
      <el-row>
        <el-col :span="6">项目地点</el-col>
        <el-col :span="18">{{ projectLocation }}</el-col>
      </el-row>
      <el-row>
        <el-col :span="6">建设单位</el-col>
        <el-col :span="18">{{
          projectViewData?.companyConstruction.name
        }}</el-col>
      </el-row>
      <el-row>
        <el-col :span="6">设计单位：</el-col>
        <el-col :span="18">
          <div
            v-for="(
              company, companyIndex
            ) in projectViewData?.companyDesignList"
          >
            {{ company.name }}
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">监理单位：</el-col>
        <el-col :span="18">{{
          projectViewData?.companySupervision?.name
        }}</el-col>
      </el-row>
      <el-row>
        <el-col :span="6">总包单位：</el-col>
        <el-col :span="18">{{
          projectViewData?.companyGeneralContract?.name
        }}</el-col>
      </el-row>

      <el-row>
        <el-col :span="6">项目起止时间：</el-col>
        <el-col :span="18"
          >{{ formatDate(projectViewData?.project.createDatetime) }}-{{
            projectViewData?.project.endDatetime == null
              ? "至今"
              : formatDate(projectViewData?.project.endDatetime)
          }}</el-col
        >
      </el-row>
    </div>
  </el-card>

  <!--项目物料列表-->
  <ProjectMaterialList :projectId="projectId" />

  <!-- 审核记录-->
  <ProjectReviewHistoryList :projectId="projectId" />
</template>

<style scoped>
@import url("@/assets/css/basic.css");
.page-class {
  padding: 10px;
}

.top-toolbar {
  display: flex;

  margin: 10px 0px;
}

.user-type-radio {
  margin-left: 50px;
}

.input-with-select {
  right: 20px;
  margin-left: 10px;
  flex: 1;
}
</style>
