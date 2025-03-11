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
  serverGetProjectAppearanceReviewUserViewPageByTaskId,
  serverGetProjectAppearanceReviewDesignDepartmentUserViewPageByTaskId,
  serverGetFeedbackOfProjectMaterialReviewedByProjectIdAndTaskId,
  serverGetFeedbackOfSelectBrandReviewedOfSupervisionCompanyByProjectIdAndTaskId,
  serverGetFeedbackOfProjectMaterialAcceptanceReviewedOfSupervisionCompanyByProjectIdAndTaskId,
  serverGetFeedbackOfProjectMaterialAcceptanceReviewedOfEngineeringDepartmentByProjectIdAndTaskId,
  serverGetFeedbackOfNeedReCheckNotPassedOfSupervisionCompanyByProjectIdAndTaskId,
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
  IServerProjectAppearanceReviewUserView,
  IServerProjectMaterialAcceptanceReviewUser,
  IServerProjectMaterialAcceptanceReviewUserView,
  IServerProjectMaterialRetestForm,
  IServerProjectMaterialRetestView,
} from "@/server/types/project/review";

import {
  serverAddProjectMaterialReviewTempFiles,
  serverDeleteProjectMaterialReviewTempFiles,
  serverGetProjectReviewUserViewPageByProjectReviewId,
  serverDownloadProjectReviewUserFileById,
} from "@/server/project/projectreview";

import {   serverDownloadProjectMaterialAcceptanceFileById } from "@/server/project/appearancereview";

//服务器返回到前端的类型
import { IServerPage } from "@/server/types/System";

import { getUserPageSize, setUserPageSize } from "@/cookies/user";
import { formatDate } from "@/utils/utils";

const router = useRouter();
const route = useRoute();

const dialogFormNewVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormUpdateVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormExcelVisible = ref(false); //控制“上传Excel对话框”是否显示
const dialogShowDetailsVisible = ref(false); //控制“显示对话框”是否显示

const loading = ref(false);

const searchText = ref("");
const searchSelect = ref("1");

const pageNo = ref(1); //第几页
const pageSize = ref(getUserPageSize()); //每页多少数据
const updateProjectMaterialView = ref<IServerProjectMaterialView>();
const projectViewData = ref<IServerProjectView>();
const radioUserType = ref(0);

const { projectUserTask } = defineProps(["projectUserTask"]);

const emit = defineEmits<{
  (
    e: "setAllEmployeeFilesAsManagerFiles",
    projectReviewUserViewList: IServerProjectMaterialRetestView[]
  ): void;
}>();

onMounted(async () => {
  await getProjectReviewUserViewPageFromServer();
});

const projectReviewUserViewData = ref<IServerProjectMaterialRetestView[]>([]);

const getProjectReviewUserViewPageFromServer = async () => {
  console.log(projectUserTask);
  if (
    projectUserTask &&
    projectUserTask.projectView &&
    projectUserTask.projectView.project &&
    projectUserTask.projectView.project.id &&
    projectUserTask.taskId
  ) {
    const ret =
      await serverGetFeedbackOfNeedReCheckNotPassedOfSupervisionCompanyByProjectIdAndTaskId(
        projectUserTask.projectView.project.id,
        projectUserTask.taskId
      );
    console.log(ret);
    if (ret && ret.code == 200) {
      projectReviewUserViewData.value = ret.data; // 赋值为数组
      console.log("projectReviewUserViewData.value", projectReviewUserViewData.value);
    }
  }
};

watchEffect(async () => {
  await getProjectReviewUserViewPageFromServer();
});


watchEffect(async () => {
  // 在 3.5 之前只运行一次
  // 在 3.5+ 中在 "foo" prop 变化时重新执行

  await getProjectReviewUserViewPageFromServer();
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

const downloadProjectMaterialAcceptanceFileById = async (
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

  const ret = await serverDownloadProjectMaterialAcceptanceFileById(
    projectId,
    projectUserFileId,
    downloadFilename
  );
  console.log(ret);
};
const reviewResult = computed(() => {
  if (
    projectReviewUserViewData &&
    projectReviewUserViewData.value &&
    projectReviewUserViewData.value.projectMaterialRetest
  ) {
    if (
      projectReviewUserViewData.value.projectMaterialRetest
        .reviewResult == 2
    )
      return "不通过";
    if (
      projectReviewUserViewData.value.pprojectMaterialRetest
        .reviewResult == 1
    )
      return "通过";
  }
});
const collapsed = ref(false);
</script>

<template>
  <div v-if="projectReviewUserViewData.length > 0">
    <div
      v-for="(review, index) in projectReviewUserViewData"
      :key="index"
      class="project-material-list-container container"
    >
      <div style="font: 1.2em sans-serif; margin: 10px">
        审核内容 {{ index + 1 }}
        <el-icon style="float: right; cursor: pointer" v-if="collapsed[index]" @click="collapsed[index] = !collapsed[index]">
          <ArrowDown />
        </el-icon>
        <el-icon style="float: right; cursor: pointer" v-else @click="collapsed[index] = !collapsed[index]">
          <ArrowUp />
        </el-icon>
      </div>
      <div class="project-container" v-show="!collapsed[index]">
        <div style="display: flex; margin: 10px; align-items: baseline">
          <div>审核结果：</div>
          <div v-if="review.projectMaterialRetest.reviewResult == 2" style="color: red">不通过</div>
          <div v-else-if="review.projectMaterialRetest.reviewResult == 1" style="color: green">通过</div>
        </div>

        <div style="display: flex; margin: 10px; align-items: baseline">
          <div>审核意见：</div>
          <div>{{ review.projectMaterialRetest.reviewContent }}</div>
        </div>

        <div style="display: flex; margin: 10px; align-items: baseline">
          <div>审核时间：</div>
          <div>{{ formatDate(review.projectMaterialRetest.reviewDatetime) }}</div>
        </div>

        <div style="display: flex; margin: 10px; align-items: baseline">
          <div>审核附件：</div>
          <div>
            <div
              v-for="(item, idx) in review.projectMaterialRetestFileList"
              :key="idx"
              style="padding-right: 10px"
            >
              <div
                class="download-file"
                @click="
                  downloadProjectMaterialAcceptanceFileById(
                    projectUserTask.projectView.project.id,
                    item.id,
                    review.user.realName + '_' + review.user.userName,
                    idx + 1,
                    item.filePath
                  )
                "
              >
                {{
                  getFullFilename(
                    projectUserTask.projectView.project.id,
                    item.id,
                    review.user.realName + '_' + review.user.userName,
                    idx + 1,
                    item.filePath
                  )
                }}
                <el-icon><Download /></el-icon>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>



<style scoped>
.project-material-list-container {
  padding: 10px;
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
