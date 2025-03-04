<script setup lang="ts">
/**
 * 文档说明
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
  IServerProjectReviewManagerForm,
  IServerUseMaterialForm,
  IServerUseMaterialBrandSelectView,
} from "@/server/types/project/review";

import {
  serverStartProcess,
  serverGetTaskByCurrentLoginUser,
  serverGetTaskUseMaterialBrandSelectViewByProjectIdAndTaskId,
} from "@/server/project/projectmaterialflow";

import { serverDownloadUseMaterialNewBrandFileById } from "@/server/project/usematerial";

//服务器返回到前端的类型
import { IServerPage } from "@/server/types/System";

import { getUserPageSize, setUserPageSize } from "@/cookies/user";

import NewProjectMaterialDialog from "@/components/project/material/NewProjectMaterialDialog.vue";
import UpdateProjectMaterialDialog from "@/components/project/material/UpdateProjectMaterialDialog.vue";
import UploadExcelProjectMaterialDialog from "@/components/project/material/UploadExcelProjectMaterialDialog.vue";

import ShowProjectMaterialDialog from "./ShowProjectMaterialDialog.vue";

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

const useMaterialBrandSelectView = ref<IServerUseMaterialBrandSelectView>();
const updateProjectMaterialView = ref<IServerProjectMaterialView>();
const projectViewData = ref<IServerProjectView>();

const { projectId, taskId } = defineProps<{
  projectId: string;
  taskId: string;
}>();

watchEffect(async () => {
  // 在 3.5 之前只运行一次
  // 在 3.5+ 中在 "foo" prop 变化时重新执行
  console.log(projectId);
  if (projectId) {
    await getUseMaterialViewFromSever();
    await getProjectFromServer(projectId);
  }
});

onMounted(async () => {
  if (projectId) {
    await getProjectFromServer(projectId);

    await getUseMaterialViewFromSever();
  }
});

const getProjectFromServer = async (projectId: string) => {
  console.log(projectId);
  const ret = await serverGetProjectViewById(projectId);
  console.log(ret);
  if (ret && ret.code == 200) {
    projectViewData.value = ret.data;
  }
};

const getUseMaterialViewFromSever = async () => {
  console.log(projectId);
  console.log(taskId);
  const ret = await serverGetTaskUseMaterialBrandSelectViewByProjectIdAndTaskId(
    projectId,
    taskId
  );
  console.log(ret);
  if (ret && ret.code == 200) {
    useMaterialBrandSelectView.value = ret.data;
  }
  console.log(useMaterialBrandSelectView.value);
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

const downFileFromServer = async (
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

  const ret = await serverDownloadUseMaterialNewBrandFileById(
    projectId,
    projectUserFileId,
    downloadFilename
  );
  console.log(ret);
};

const collapsed = ref(false);
</script>

<template>
  <ShowProjectMaterialDialog
    :dialogVisible="dialogShowDetailsVisible"
    :projectView="projectViewData"
    :projectMaterialView="updateProjectMaterialView"
    @onDilalogCancel="onShowProjectMaterialDialogCancel"
    @onDilalogOk="onShowProjectMaterialDialogOk"
  >
  </ShowProjectMaterialDialog>
  <div class="project-material-list-container container">
    <!--显示内容-->
    <div style="font: 1.2em sans-serif; margin: 10px">
      审核内容
      <el-icon
        style="float: right; cursor: pointer"
        v-if="collapsed"
        @click="collapsed = !collapsed"
      >
        <ArrowDown />
      </el-icon>
      <el-icon
        style="float: right; cursor: pointer"
        v-else
        @click="collapsed = !collapsed"
      >
        <ArrowUp />
      </el-icon>
    </div>
    <div class="project-container" v-show="!collapsed">
      <el-table
        :data="useMaterialBrandSelectView?.useMaterialViewList"
        style="width: 100%"
        v-loading="loading"
        stripe
        show-overflow-tooltip
      >
        <el-table-column label="材料名称" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="margin-left: 10px">{{
                scope.row.projectMaterialView.material.name
              }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="材料位置" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="margin-left: 10px">{{
                scope.row.projectMaterialView.material.location
              }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="编号" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="margin-left: 10px">{{
                scope.row.projectMaterialView.material.itemMark
              }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="技术要求" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="margin-left: 10px">{{
                scope.row.projectMaterialView.material.technology
              }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="施工要求" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="margin-left: 10px">{{
                scope.row.projectMaterialView.material.installation
              }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="影响外观" show-overflow-tooltip>
          <template #default="scope">
            {{
              scope.row.useMaterial.isAppearance == 0
                ? "不影响外观"
                : "影响外观"
            }}
          </template>
        </el-table-column>

        <el-table-column label="品牌" width="300px">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <div
                v-if="
                  scope.row.projectMaterialBrandPrivateView == null &&
                  scope.row.projectMaterialBrandPublicView == null &&
                  scope.row.useMaterialNewBrandView != null
                "
              >
                <div class="container" style="padding: 10px">
                  <div>
                    新品牌：
                    {{
                      scope.row.useMaterialNewBrandView.useMaterialNewBrand
                        .brandName
                    }}
                  </div>
                  <div>
                    {{
                      scope.row.useMaterialNewBrandView.materialClassifyDivision
                        .name
                    }}-{{
                      scope.row.useMaterialNewBrandView.materialClassifyGroup
                        .name
                    }}-{{
                      scope.row.useMaterialNewBrandView.materialClassifySection
                        .name
                    }}-{{
                      scope.row.useMaterialNewBrandView.useMaterialNewBrand
                        .materialPosition
                    }}
                  </div>
                  <div>
                    附件：
                    <div
                      v-for="(item, index) in scope.row.useMaterialNewBrandView
                        .useMaterialNewBrandFileViewList"
                      :key="index"
                      style="padding-left: 10px; padding-right: 10px"
                      class="download-file"
                      @click="
                        downFileFromServer(
                          scope.row.projectMaterialView.project.id,
                          item.useMaterialNewBrandFile.id,
                          scope.row.user.realName +
                            '_' +
                            scope.row.user.userName,
                          index + 1,
                          item.useMaterialNewBrandFile.filePath
                        )
                      "
                    >
                      {{
                        getFullFilename(
                          scope.row.projectMaterialView.project.id,
                          item.useMaterialNewBrandFile.id,
                          scope.row.user.realName +
                            "_" +
                            scope.row.user.userName,
                          index + 1,
                          item.useMaterialNewBrandFile.filePath
                        )
                      }}
                    </div>
                  </div>
                </div>
              </div>
              <div
                v-else-if="scope.row.projectMaterialBrandPrivateView != null"
              >
                {{
                  scope.row.projectMaterialBrandPrivateView.projectBrandView
                    .brandView.brand.name
                }}（项目私有品牌）
              </div>
              <div v-else-if="scope.row.projectMaterialBrandPublicView != null">
                {{
                  scope.row.projectMaterialBrandPublicView.brandPublicView
                    .brandView.brand.name
                }}（公共品牌）
              </div>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<style scoped>
@import url("@/assets/css/basic.css");
.project-material-list-container {
  padding-top: 10px;
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
