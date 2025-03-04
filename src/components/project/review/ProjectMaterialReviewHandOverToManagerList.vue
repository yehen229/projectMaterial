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
  serverGetProjectReviewUserViewPageByProjectReviewId,
  serverDownloadProjectReviewUserFileById,
  serverGetProjectReviewUserViewListByProjectReviewId,
  serverGetProjectReviewUserViewListByProjectIdAndTaskId
} from "@/server/project/projectreview";

//服务器返回到前端的类型
import { IServerPage } from "@/server/types/System";

import { getUserPageSize, setUserPageSize } from "@/cookies/user";
import { formatDate } from "../../../utils/utils";
import { IServerProjectUserTask } from "@/server/types/project/flow";

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

const {projectUserTask,designCompanyIndex} = defineProps<{projectUserTask:IServerProjectUserTask,designCompanyIndex:number}>();

const projectReviewUserViewListData = ref<IServerProjectReviewUserView[]>([]);
const employeeProjectReviewUserFileIds = ref<string[]>([]);

const emit = defineEmits<{
  (
    e: "onEmployeeFilesAsManagerFilesChanged",
    projectReviewUserViewList: IServerProjectReviewUserView[]
  ): void;
}>();

const setAllFilesAsManagerFiles = async () => {
  console.log("111111111111111111")
  console.log(projectUserTask.projectView.project.id)
  console.log(designCompanyIndex)
  const ret = await serverGetProjectReviewUserViewListByProjectIdAndTaskId(
      projectUserTask.projectView.project.id,
      projectUserTask.taskId,
      designCompanyIndex
  );
  console.log(ret);
  if (ret && ret.code == 200) {
    console.log(ret.data);
    projectReviewUserViewListData.value = ret.data;
  }

  emit(
    "onEmployeeFilesAsManagerFilesChanged",
    projectReviewUserViewListData.value
  );
};

watchEffect(async () => {
  // 在 3.5 之前只运行一次
  // 在 3.5+ 中在 "foo" prop 变化时重新执行
  // console.log(projectReviewId);
  //await setAllFilesAsManagerFiles();

  emit(
    "onEmployeeFilesAsManagerFilesChanged",
    projectReviewUserViewListData.value
  );
});

onMounted(async () => {
  console.log("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk")
  await setAllFilesAsManagerFiles();
});

const tableData = computed(() => {
  return projectReviewUserViewListData.value ?? [];
});

const totalCount = computed(() => {
  return Number(projectReviewUserViewListData.value?.length ?? 0);
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
  //console.log(downloadFilename);
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
  console.log(downloadFilename);

  const ret = await serverDownloadProjectReviewUserFileById(
    projectId,
    projectUserFileId,
    downloadFilename
  );
  console.log(ret);
};

const deleteUserReviewFile = (id: string) => {
  projectReviewUserViewListData.value.forEach(
    (item: IServerProjectReviewUserView) => {
      if (item.projectReviewUserFileList.some((itemFile) => itemFile.id === id))
        item.projectReviewUserFileList = item.projectReviewUserFileList.filter(
          (itemFile) => itemFile.id !== id
        );
    }
  );

  const temp = projectReviewUserViewListData.value.filter(
    (item: IServerProjectReviewUserView) =>
      item.projectReviewUserFileList.length > 0
  );
  projectReviewUserViewListData.value.length = 0;
  temp.forEach((item: IServerProjectReviewUserView) =>
    projectReviewUserViewListData.value.push(item)
  );

  emit(
    "onEmployeeFilesAsManagerFilesChanged",
    projectReviewUserViewListData.value
  );
};
</script>

<template>
  <div class="project-material-list-container" style="width: 100%">
    <el-button @click="setAllFilesAsManagerFiles"
      >将全部附件作为项目经理审核附件</el-button
    >
    <!--显示内容-->
    <div class="project-container" v-if="totalCount > 0">
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

        <el-table-column label="审核附件" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex">
              <div
                v-for="(item, index) in scope.row.projectReviewUserFileList"
                :key="index"
                style="
                  display: flex;
                  align-content: space-around;
                  padding-right: 50px;
                "
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
                <el-button
                  style="margin-left: 10px"
                  type="danger"
                  size="small"
                  @click="deleteUserReviewFile(item.id)"
                  >删除</el-button
                >
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="审核时间" width="160" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="margin-left: 10px">{{
                formatDate(scope.row.projectReviewUser.reviewDatetime)
              }}</span>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div v-else>
      <div style="color: gray">
        <p>没有选择项目员工的审核文件作为自己审核结果的附件。</p>
      </div>
    </div>
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
