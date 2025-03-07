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
  Delete,
  Edit,
  EditPen,
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
  serverGetProjectMaterialPageViewByCurrentUserProject,
  serverProjectMaterialExcelByDesignCompanyOfCurrentUserAndProjectId
} from "@/server/project/projectmaterial";

import {
  serverStartProcess,
  serverDesignCompanySubmitProjectMaterial,
  serverGetTaskByCurrentLoginUser,
  serverGetTaskByCurrentLoginUserAndProjectId,
  serverGetTaskByCurrentLoginUserAndProjectIdAndTaskId,
  serverSubmitProjectMaterialReviewOfManagerDirect,
  serverSubmitProjectMaterialReviewManagerDistributeToEmployees,
  serverSubmitProjectMaterialReviewOfEmployee,
  serverSubmitProjectMaterialReviewOfManagerSummary,
  serverSubmitEngineeringDepartmentManagerDispatch,
} from "@/server/project/projectmaterialflow";

import { IServerProjectUserTask } from "@/server/types/project/flow";

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
} from "@/server/types/system/company";

import {
  serverCompanyAdd,
  serverCompanyDelete,
  serverCompanyDeleteById,
  serverCompanyUpdate,
  serverGetCompanyUserById,
  serverGetAllCompanyList,
  serverGetAllCompanyListByCompanyType,
  serverGetCompanyPage,
  serverGetCompanyPageByCompanyName,
  serverGetCompanyPageByCompanyType,
} from "@/server/system/company";

//服务器返回到前端的类型
import { IServerPage } from "@/server/types/System";

import { getUserPageSize, setUserPageSize } from "@/cookies/user";

import NewProjectMaterialDialog from "@/components/project/material/NewProjectMaterialDialog.vue";
import UpdateProjectMaterialDialog from "@/components/project/material/UpdateProjectMaterialDialog.vue";
import UploadExcelProjectMaterialDialog from "@/components/project/material/UploadExcelProjectMaterialDialog.vue";
import ProjectUserTaskInfo from "@/components/project/flow/ProjectUserTaskInfo.vue";

import ProjectReviewHistoryList from "@/components/project/flow/ProjectReviewHistoryList.vue";
import ProjectMaterialFeekback from "@/components/project/review/ProjectMaterialFeekback.vue";
import {
  getDesignCompanyIndex,
  getTaskName,
  getTaskProjectName,
  getTaskTitle,
} from "@/components/project/flow/index";

import ShowProjectMaterialDiffDialog from "@/components/project/material/ShowProjectMaterialDiffDialog.vue";

const router = useRouter();
const route = useRoute();

const dialogFormNewVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormUpdateVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormExcelVisible = ref(false); //控制“上传Excel对话框”是否显示
const dialogShowProjectMaterialDiffDialogVisible = ref(false); //控制“显示材料不同对话框”是否显示
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
const designCompanyIndex = ref(0);
const projectId = ref("");
const taskId = ref("");
const showProjectMaterialDiff = ref(true); //是否显示不同

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
  await getProjectFromServer(projectId.value);
  await getUserTaskFromServerByProjectId(projectId.value, taskId.value);
  await getProjectMaterialViewFromSever();
});

onMounted(async () => {
  if (typeof route.params.id === "string") {
    projectId.value = route.params.id;
  }
  if (typeof route.params.taskId === "string") {
    taskId.value = route.params.taskId;
  }
  await getProjectFromServer(projectId.value);
  await getUserTaskFromServerByProjectId(projectId.value, taskId.value);
  await getProjectMaterialViewFromSever();
});

// designCompanyIndex.value = getDesignCompanyIndex(projectUserTask.value);

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

const getProjectFromServer = async (projectId: string) => {
  const ret = await serverGetProjectViewById(projectId);
  console.log(ret);
  if (ret && ret.code == 200) {
    projectViewData.value = ret.data;
  }
};

const getProjectMaterialViewFromSever = async () => {
  let search = searchText.value.trim();
  let name = "", location = "", itemMark = "", technology = "", installation = "", brand = "", brandPrivate = "";
  if (search) {
    console.log(searchSelect.value);
    switch (searchSelect.value) {
      case "0":
        name = search;
        break;
      case "1":
        location = search;
        break;
      case "2":
        itemMark = search;
        break;
      case "3":
        technology = search;
        break;
      case "4":
        installation = search;
        break;
      case "5":
        brand = search;
        break;
      case "6":
        brandPrivate = search;
        break;
      default:
        break;
    }
    const ret = await serverGetProjectMaterialPageViewByCurrentUserProject(
        projectId.value,
        name,
        location,
        itemMark,
        technology,
        installation,
        brand,
        brandPrivate,
        pageNo.value,
        pageSize.value
    );
    if (ret && ret.code == 200) {
        projectMaterialViewPageData.value = ret.data;
      }
  } else {
    const ret = await serverGetProjectMaterialPageViewByCurrentUserProject(
      projectId.value,
      name,
      location,
      itemMark,
      technology,
      installation,
      brand,
      brandPrivate,
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

/**
 * 用户点击“新增”按钮，显示新增对话框
 */
const onNewButtonClick = () => {
  dialogFormNewVisible.value = true;
};

/**
 * 用户在“新增”对话框中点击了“取消”按钮
 */
const onNewProjectMaterialDialogCancel = async () => {
  dialogFormNewVisible.value = false;
};

/**
 * 用户在“新增”对话框中点击了“确认”按钮
 */
const onNewProjectMaterialDialogOk = async (
  projectMaterialForm: IServerProjectMaterialForm
) => {
  console.log(projectMaterialForm);
  await serverProjectMaterialAddForm(projectMaterialForm);
  await getProjectMaterialViewFromSever();
  dialogFormNewVisible.value = false;
};

/**
 * 单击编辑按钮，编辑内容
 * @param index
 * @param row
 */
const onRowEditButtonClick = async (
  index: number,
  row: IServerProjectMaterialView
) => {
  updateProjectMaterialView.value = row;
  dialogFormUpdateVisible.value = true;
};

const onUpdateProjectMaterialDialogCancel = () => {
  dialogFormUpdateVisible.value = false;
};

const onUpdateProjectMaterialDialogOk = async (
  projectMaterialForm: IServerProjectMaterialForm
) => {
  console.log(projectMaterialForm);
  await serverProjectMaterialUpdateForm(projectMaterialForm);
  await getProjectMaterialViewFromSever();
  dialogFormUpdateVisible.value = false;
};

/**
 * 点击删除按钮，删除内容
 * @param index
 * @param row
 */
const onRowDeleteButtonClick = async (
  index: number,
  row: IServerProjectMaterialView
) => {
  console.log(index, row);

  ElMessageBox.confirm("是否真的删除数据？", "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      await serverProjectMaterialDelete(row.projectMaterial);
      await getProjectMaterialViewFromSever();
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

const onRowShowDiffButtonClick = async (
  index: number,
  row: IServerProjectMaterialView
) => {
  showProjectMaterialDiff.value = !showProjectMaterialDiff.value;
};

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
  await serverProjectMaterialExcelByDesignCompanyOfCurrentUserAndProjectId(projectId.value, downloadFilename);
  // let search = searchText.value.trim();
  
  // if (search) {
  //   if (searchSelect.value == "用户名称") {
  //     //用户名称
  //     const ret = await serverDownloadCompanyUserByUserNamer(
  //       searchText.value,
  //       downloadFilename
  //     );
  //   } else if (searchSelect.value == "项目名称") {
  //     //项目名称
  //     const ret = await serverDownloadCompanyUserByProjectName(
  //       searchText.value,
  //       downloadFilename
  //     );
  //   } else if (searchSelect.value == "单位名称") {
  //     //单位名称
  //     const ret = await serverDownloadCompanyUserByCompanyName(
  //       searchText.value,
  //       downloadFilename
  //     );
  //   }
  // } else {
  //   await serverDownloadAllCompanyUser(downloadFilename);
  // }


  loading.value = false;
};

const onExcelUploadDialogCancel = () => {
  dialogFormExcelVisible.value = false;
};

const onExcelUploadDialogOk = () => {
  dialogFormExcelVisible.value = false;
};

/**
 * 提交审核
 */
const onReviewSubmitButtonClick = async () => {
  if (projectViewData.value) {
    const ret = await serverDesignCompanySubmitProjectMaterial(
      projectViewData.value.project
    );

    if (ret && ret.code == 200) {
      ElMessage({
        type: "success",
        message: "提交审核成功",
      });
    }

    router.push("/project-user-task-list");
  }
};
const showDiffProjectMaterialView = ref<IServerProjectMaterialView>();
const onMaterialDiffClick = async (row: IServerProjectMaterialView) => {
  showDiffProjectMaterialView.value = row;
  dialogShowProjectMaterialDiffDialogVisible.value = true;
};
const onShowProjectMaterialDiffDialogOk = () => {
  dialogShowProjectMaterialDiffDialogVisible.value = false;
};
const onShowProjectMaterialDiffDialogCancel = () => {
  dialogShowProjectMaterialDiffDialogVisible.value = false;
};

/**
 * 材料是否不同
 * @param projectMaterialViewItem
 */
const isMaterialDifferent = (
  projectMaterialViewItem: IServerProjectMaterialView
) => {
  return (
    projectMaterialViewItem.material.id !==
    projectMaterialViewItem.projectMaterial.materialOriginId
  );
};
const textElipsisValue = ref(false);
</script>

<template>
  <!--新增对话框-->
  <NewProjectMaterialDialog
    :dialogVisible="dialogFormNewVisible"
    :projectView="projectViewData"
    @onDilalogCancel="onNewProjectMaterialDialogCancel"
    @onDilalogOk="onNewProjectMaterialDialogOk"
  ></NewProjectMaterialDialog>

  <!--修改对话框-->
  <UpdateProjectMaterialDialog
    :dialogVisible="dialogFormUpdateVisible"
    :projectView="projectViewData"
    :projectMaterialView="updateProjectMaterialView"
    @onDilalogCancel="onUpdateProjectMaterialDialogCancel"
    @onDilalogOk="onUpdateProjectMaterialDialogOk"
  ></UpdateProjectMaterialDialog>

  <!--上传Excel文件对话框-->
  <UploadExcelProjectMaterialDialog
    :dialogVisible="dialogFormExcelVisible"
    @onDilalogCancel="onExcelUploadDialogCancel"
    @onDilalogOk="onExcelUploadDialogOk"
  ></UploadExcelProjectMaterialDialog>

  <ShowProjectMaterialDiffDialog
    :dialogVisible="dialogShowProjectMaterialDiffDialogVisible"
    :showDiffProjectMaterialView="showDiffProjectMaterialView"
    @onDilalogCancel="onShowProjectMaterialDiffDialogCancel"
    @onDilalogOk="onShowProjectMaterialDiffDialogOk"
  ></ShowProjectMaterialDiffDialog>

  <el-page-header @back="goBack" style="margin-bottom: 20px">
    <template #content>
      <span class="text-large font-600 mr-3">项目材料</span>
    </template>
    <div class="mt-4 text-sm font-bold"></div>
  </el-page-header>

  <ProjectUserTaskInfo :projectUserTask="projectUserTask"></ProjectUserTaskInfo>

  <div v-if="projectUserTask">
    <div class="tab-container container">
      <div class="top-toolbar">
        <!--新增按钮-->
        <div>
          <el-button :icon="Plus" type="primary" @click="onNewButtonClick">
            新增材料
          </el-button>

          <!-- <el-button :icon="Upload" @click="onExcelUploadButtonClick">
            导入材料（Excel）
          </el-button> -->
          <el-button :icon="Download" @click="onDownloadExcelButtonClick">
            导出材料（Excel）
          </el-button>

          <el-button type="primary" @click="onReviewSubmitButtonClick">
            提交审核
          </el-button>
        </div>

        <!--搜索框-->
        <div class="input-with-select">
          <el-input v-model="searchText" placeholder="输入搜索内容">
            <template #prepend>
              <el-select
                v-model="searchSelect"
                placeholder="Select"
                style="width: 115px"
              >
                <el-option label="材料名称" value="0" />
                <el-option label="材料位置" value="1" />
                <el-option label="编号" value="2" />
                <el-option label="技术要求" value="3" />
                <el-option label="施工要求" value="4" />
                <el-option label="品牌（公有）" value="5" />
                <el-option label="品牌（私有）" value="6" />
                <!-- <el-option label="审核状态" value="7" /> -->
              </el-select>
            </template>
            <template #append>
              <el-button :icon="Search" @click="onSearchClick" />
            </template>
          </el-input>
        </div>

        <div>
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

          <el-switch
            v-model="showProjectMaterialDiff"
            class="ml-2"
            inline-prompt
            style="
              --el-switch-on-color: #13ce66;
              --el-switch-off-color: #ff4949;
            "
            active-text="显示不同"
            inactive-text="隐藏不同"
          />
        </div>
      </div>

      <!--显示内容-->
      <div class="project-container">
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
          <el-col :span="3">材料名称 </el-col>
          <el-col :span="3"> 材料位置</el-col>
          <el-col :span="2">编号 </el-col>
          <el-col :span="6">技术要求 </el-col>
          <el-col :span="4"> 施工要求</el-col>
          <el-col :span="4"> 品牌</el-col>
          <!-- <el-col :span="2"> 审核状态 </el-col> -->
          <el-col :span="2"> 操作 </el-col>
        </el-row>

        <el-row
          v-for="(
            projectMaterialViewItem, projectMaterialViewIndex
          ) in tableData"
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
          <el-col :span="3">
            <div v-if="isMaterialDifferent(projectMaterialViewItem)">
              <el-badge value="修改" class="item">
                <el-button
                  @click="onMaterialDiffClick(projectMaterialViewItem)"
                  >{{ projectMaterialViewItem.material.name }}</el-button
                >
                <template #content="{ value }">
                  <div class="custom-content">
                    <el-icon>
                      <EditPen />
                    </el-icon>
                    <span>{{ value }}</span>
                  </div>
                </template>
              </el-badge>
            </div>
            <div v-else style="display: flex; align-items: center">
              <span>{{ projectMaterialViewItem.material.name }}</span>
            </div>
          </el-col>

          <!--材料位置-->
          <el-col :span="3">
            <div style="display: flex; align-items: center">
              <div
                v-if="
                  showProjectMaterialDiff &&
                  isMaterialDifferent(projectMaterialViewItem) &&
                  projectMaterialViewItem.material.location !==
                    projectMaterialViewItem.materialOrigin.location
                "
                style="color: yellow; background-color: #606266"
                :class="{ textEllipsis: textElipsisValue }"
              >
                {{ projectMaterialViewItem.material.location }}
              </div>
              <div v-else :class="{ textEllipsis: textElipsisValue }">
                {{ projectMaterialViewItem.material.location }}
              </div>
            </div>
          </el-col>

          <!--编号-->
          <el-col :span="2">
            <div style="display: flex; align-items: center">
              <div
                v-if="
                  showProjectMaterialDiff &&
                  isMaterialDifferent(projectMaterialViewItem) &&
                  projectMaterialViewItem.material.itemMark !==
                    projectMaterialViewItem.materialOrigin.itemMark
                "
                style="color: yellow; background-color: #606266"
                :class="{ textEllipsis: textElipsisValue }"
              >
                {{ projectMaterialViewItem.material.itemMark }}
              </div>
              <div v-else :class="{ textEllipsis: textElipsisValue }">
                {{ projectMaterialViewItem.material.itemMark }}
              </div>
            </div>
          </el-col>

          <!--技术要求-->
          <el-col :span="6">
            <div style="display: flex; align-items: center">
              <div
                v-if="
                  showProjectMaterialDiff &&
                  isMaterialDifferent(projectMaterialViewItem) &&
                  projectMaterialViewItem.material.technology !==
                    projectMaterialViewItem.materialOrigin.technology
                "
                style="color: yellow; background-color: #606266"
                :class="{ textEllipsis: textElipsisValue }"
              >
                {{ projectMaterialViewItem.material.technology }}
              </div>
              <div v-else :class="{ textEllipsis: textElipsisValue }">
                {{ projectMaterialViewItem.material.technology }}
              </div>
            </div>
          </el-col>

          <!--施工要求-->
          <el-col :span="4">
            <div style="display: flex; align-items: center">
              <div
                v-if="
                  showProjectMaterialDiff &&
                  isMaterialDifferent(projectMaterialViewItem) &&
                  projectMaterialViewItem.material.installation !==
                    projectMaterialViewItem.materialOrigin.installation
                "
                style="color: yellow; background-color: #606266"
                :class="{ textEllipsis: textElipsisValue }"
              >
                {{ projectMaterialViewItem.material.installation }}
              </div>
              <div v-else :class="{ textEllipsis: textElipsisValue }">
                {{ projectMaterialViewItem.material.installation }}
              </div>
            </div>
          </el-col>

          <!--品牌-->
          <el-col :span="2">
            <div style="display: flex; flex-wrap: wrap; align-items: center">
              <!--公共品牌-->
              <span
                v-for="(
                  brandPublicViewItem, brandPublicIndex
                ) in projectMaterialViewItem.projectMaterialBrandPublicViewList"
                :key="brandPublicIndex"
              >
                <el-tag type="success" style="margin-left: 3px">{{
                  brandPublicViewItem.brandPublicView.brandView.brand.name
                }}</el-tag>
              </span>
              <!--项目私有品牌-->

              <span
                v-for="(
                  brandPrivateViewItem, brandPrivateIndex
                ) in projectMaterialViewItem.projectMaterialBrandPrivateViewList"
                :key="brandPrivateIndex"
              >
                <el-tag type="info">{{
                  brandPrivateViewItem.projectBrandView.brandView.brand.name
                }}</el-tag>
              </span>
            </div>
          </el-col>

          <!--审核状态-->
          <el-col :span="2">
            <div style="display: flex; align-items: center">
              <span style="margin-left: 10px">{{
                projectMaterialViewItem.checkstate
              }}</span>
            </div>
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
                :icon="Edit"
                style="margin-left: 10px"
                @click="
                  onRowEditButtonClick(
                    projectMaterialViewIndex,
                    projectMaterialViewItem
                  )
                "
              >
                编辑
              </el-button>

              <el-button
                size="small"
                type="danger"
                :icon="Delete"
                style="margin-top: 5px"
                @click="
                  onRowDeleteButtonClick(
                    projectMaterialViewIndex,
                    projectMaterialViewItem
                  )
                "
              >
                删除
              </el-button>

              <!--<el-button
                size="small"
                type="info"
                style="margin-top: 5px"
                @click="
                  onRowShowDiffButtonClick(
                    projectMaterialViewIndex,
                    projectMaterialViewItem
                  )
                "
              >
                标识不同
              </el-button>
              -->
            </div>
          </el-col>
        </el-row>
      </div>

      <el-pagination
        :hide-on-single-page="true"
        class="page-class"
        background
        v-model:current-page="pageNo"
        v-model:page-size="pageSize"
        :page-sizes="[10, 50, 100, 200, 300, 400]"
        layout="total, sizes, prev, pager, next"
        :total="totalCount"
        @prev-click="onPagePrevClick"
        @next-click="onPageNextClick"
        @current-change="onPageCurrentChange"
        @size-change="onPageSizeChange"
      />
    </div>

    <!--反馈意见-->
    <ProjectMaterialFeekback
      :projectUserTask="projectUserTask"
    ></ProjectMaterialFeekback>
    <!-- :designCompanyIndex="designCompanyIndex" -->
    <!-- 审核记录-->
    <ProjectReviewHistoryList :projectId="projectId" />
  </div>
</template>

<style scoped>
@import url("@/assets/css/basic.css");
.page-class {
  padding: 10px;
}

.tab-container {
  padding: 10px;
}

.top-toolbar {
  display: flex;

  margin: 0 10px;
}

.user-type-radio {
  margin-left: 50px;
}

.input-with-select {
  right: 20px;
  margin-left: 10px;
  flex: 1;
}

.item {
  margin-top: 10px;
  margin-right: 40px;
}

.custom-content {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
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
