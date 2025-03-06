<script setup lang="ts">
/**
 * 对项目材料进行管理 *
 * 主要功能：增删改查
 */

import {
  computed,
  onMounted,
  reactive,
  ref,
  Ref,
  shallowRef,
  watchEffect,
} from "vue";

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
  serverGetTaskByCurrentLoginUser,
} from "@/server/project/projectmaterialflow";

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
} from "@/server/types/project/review";

import {
  serverAddProjectMaterialReviewTempFiles,
  serverDeleteProjectMaterialReviewTempFiles,
  serverGetProjectReviewUserViewPageByTaskId,
  serverDownloadProjectReviewUserFileById,
} from "@/server/project/projectreview";
import {
  getDesignCompanyIndex,
  getTaskName,
  getTaskProjectName,
  getTaskTitle,
} from "@/components/project/flow/index";
//服务器返回到前端的类型
import { IServerPage } from "@/server/types/System";

import { getUserPageSize, setUserPageSize } from "@/cookies/user";
import { formatDate } from "../../../utils/utils";

const router = useRouter();
const route = useRoute();

const dialogFormNewVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormUpdateVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormExcelVisible = ref(false); //控制“上传Excel对话框”是否显示
const dialogShowDetailsVisible = ref(false); //控制“显示对话框”是否显示

const loading = ref(false);

const searchText = ref("");
const searchSelect = ref("0");
const resultSelect = ref("0");

const pageNo = ref(1); //第几页
const pageSize = ref(getUserPageSize()); //每页多少数据

const projectReviewUserViewPageData =
  ref<IServerPage<IServerProjectReviewUserView> | null>(null);
const updateProjectMaterialView = ref<IServerProjectMaterialView>();
const projectViewData = ref<IServerProjectView>();
const radioUserType = ref(0);
const designCompanyIndex = ref(0);

const { projectUserTask } = defineProps(["projectUserTask"]);

const emit = defineEmits<{
  (
    e: "setAllEmployeeFilesAsManagerFiles",
    projectReviewUserViewList: IServerProjectReviewUserView[]
  ): void;
}>();

onMounted(async () => {
  if (projectUserTask) {
    await getProjectReviewUserViewPageFromServer();
     if (projectUserTask && projectUserTask.value)
      designCompanyIndex.value = getDesignCompanyIndex(projectUserTask.value);
  }
});

const getProjectReviewUserViewPageFromServer = async () => {
  console.log(projectUserTask);
  let search = searchText.value.trim();
  let reviewUser = "", reviewResult;
  if (search) {
    pageNo.value = 1;
  }
  if (
    projectUserTask &&
    projectUserTask.projectView &&
    projectUserTask.projectView.project &&
    projectUserTask.projectView.project.id &&
    projectUserTask.taskId
  ) {
    if (searchSelect.value == "0") {
      reviewUser = search;
      const ret = await serverGetProjectReviewUserViewPageByTaskId(
      projectUserTask.projectView.project.id,
      projectUserTask.taskId,
      designCompanyIndex.value,
      reviewUser,
      -1,
      pageNo.value,
      pageSize.value

      );
      console.log(ret);
      if (ret && ret.code == 200) {
        projectReviewUserViewPageData.value = ret.data;
      }
    } else if (searchSelect.value == "1") {
      const ret = await serverGetProjectReviewUserViewPageByTaskId(
      projectUserTask.projectView.project.id,
      projectUserTask.taskId,
      designCompanyIndex.value,
        reviewUser,
      Number(resultSelect.value),
      pageNo.value,
      pageSize.value
      );
      console.log(ret);
      if (ret && ret.code == 200) {
        projectReviewUserViewPageData.value = ret.data;
      }
    }
    
  }
};

const clearText = () => {
  searchText.value = "";
};

watchEffect(async () => {
  // 在 3.5 之前只运行一次
  // 在 3.5+ 中在 "foo" prop 变化时重新执行
  // console.log(projectReviewId);
  // if (projectReviewId) {
  //   await getProjectReviewUserViewPageFromServer();
  // }
});

const tableData = computed(() => {
  console.log(projectReviewUserViewPageData.value?.result);
  return projectReviewUserViewPageData.value?.result ?? [];
});

const totalCount = computed(() => {
  return Number(projectReviewUserViewPageData.value?.totalCount ?? 0);
});

/**
 * 用户点击“新增”按钮，显示新增对话框
 */
const onNewButtonClick = () => {
  dialogFormNewVisible.value = true;
};

/**
 * 单击显示按钮
 * @param index
 * @param row
 */
const onRowShowButtonClick = async (
  index: number,
  row: IServerProjectMaterialView
) => {
  console.log(row);
  updateProjectMaterialView.value = row;
  dialogShowDetailsVisible.value = true;
};

const onShowProjectMaterialDialogCancel = () => {
  dialogShowDetailsVisible.value = false;
};

const onShowProjectMaterialDialogOk = async (
  projectMaterialForm: IServerProjectMaterialForm
) => {
  dialogShowDetailsVisible.value = false;
};

const onPagePrevClick = (value: number) => {};
const onPageNextClick = (value: number) => {};
const onPageCurrentChange = async (value: number) => {
  pageNo.value = value;
  await getProjectReviewUserViewPageFromServer();
};

const onPageSizeChange = async (value: number) => {
  pageSize.value = value;
  setUserPageSize(value);
  await getProjectReviewUserViewPageFromServer();
};

const onSearchClick = async () => {
  let search = searchText.value.trim();
  if (search) {
    pageNo.value = 1;
  }
  await getProjectReviewUserViewPageFromServer();
};

const getReviewResult = (result: number) => {
  if (result == 0) return "未审核";
  if (result == 1) return "审核通过";
  if (result == 2) return "审核不通过";
};

const getFullFilename = (
  projectId: string,
  projectUserFileId: string,
  userName: string,
  index: number,
  originFileName: string
) => {
  //console.log(projectUserFileId);
  let file_ext = originFileName
    .substring(originFileName.lastIndexOf("."))
    .toLowerCase();

  const downloadFilename = userName + "_附件" + index + file_ext;
  // console.log(downloadFilename);
  return downloadFilename;
};

const downProjectReviewUserFileFromServer = async (
  projectId: string,
  projectUserFileId: string,
  userName: string,
  index: number,
  originFileName: string
) => {
  const downloadFilename = getFullFilename(
    projectId,
    projectUserFileId,
    userName,
    index,
    originFileName
  );
  //console.log(downloadFilename);

  const ret = await serverDownloadProjectReviewUserFileById(
    projectId,
    projectUserFileId,
    downloadFilename
  );
  console.log(ret);
};
</script>

<template>
  <div class="project-material-list-container">
    <div class="top-toolbar">
      <!--搜索框-->

      <div class="input-with-select">
        <el-input v-model="searchText" placeholder="输入搜索内容">
          <template #prepend>
            <el-select
              v-model="searchSelect"
              placeholder="Select"
              style="width: 115px"
              @change="clearText()"
            >
                <el-option label="审核人" value="0" />
                <el-option label="审核状态" value="1" />
            </el-select>
          </template>
          <template #prefix v-if="searchSelect == '1'">
            <el-select
              v-model="resultSelect"
              placeholder="Select"
              style="width: 100px"
              @change="clearText()"
            >
              <el-option label="尚未审核" value="0" />
              <el-option label="审核通过" value="1" />
              <el-option label="审核未通过" value="2" />
            </el-select>
          </template>
          <template #append>
            <el-button :icon="Search" @click="onSearchClick" />
          </template>
        </el-input>
      </div>
    </div>

    <!--显示内容-->
    <div class="project-container">
      <el-table
        :data="tableData"
        style="width: 100%"
        v-loading="loading"
        stripe
        show-overflow-tooltip
      >
        <el-table-column label="审核人" width="160" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              {{ scope.row.user.realName }}({{ scope.row.user.userName }})
            </div>
          </template>
        </el-table-column>

        <el-table-column label="审核结果" width="100" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <el-tag
                type="info"
                v-if="scope.row.projectReviewUser.reviewResult == 0"
                >尚未审核</el-tag
              >
              <el-tag
                type="success"
                v-else-if="scope.row.projectReviewUser.reviewResult == 1"
                >审核通过</el-tag
              >
              <el-tag
                type="danger"
                v-else-if="scope.row.projectReviewUser.reviewResult == 2"
                >审核不通过</el-tag
              >
            </div>
          </template>
        </el-table-column>

        <el-table-column label="审核意见" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span v-if="scope.row.projectReviewUser.reviewResult > 0">{{
                scope.row.projectReviewUser.reviewContent
              }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="审核附件" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: block">
              <div
                v-for="(item, index) in scope.row.projectReviewUserFileList"
                :key="index"
                style="padding-left: 10px; padding-right: 10px"
              >
                <div
                  class="download-file"
                  @click="
                    downProjectReviewUserFileFromServer(
                      scope.row.projectReview.projectId,
                      item.id,
                      scope.row.user.realName + '_' + scope.row.user.userName,
                      index + 1,
                      item.filePath
                    )
                  "
                >
                  {{
                    getFullFilename(
                      scope.row.projectReview.projectId,
                      item.id,
                      scope.row.user.realName + "_" + scope.row.user.userName,
                      index + 1,
                      item.filePath
                    )
                  }}
                </div>
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="审核时间" width="160" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span
                style="margin-left: 10px"
                v-if="scope.row.projectReviewUser.reviewResult > 0"
                >{{
                  formatDate(scope.row.projectReviewUser.reviewDatetime)
                }}</span
              >
            </div>
          </template>
        </el-table-column>
      </el-table>
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
</template>

<style scoped>
.project-material-list-container {
}
.page-class {
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

.download-file {
  cursor: pointer;
}
.download-file:hover {
  color: blue;
}
</style>
