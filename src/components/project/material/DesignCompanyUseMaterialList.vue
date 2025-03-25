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
  IServerUseMaterialView,
} from "@/server/types/project/review";

import {
  serverStartProcess,
  serverGetTaskByCurrentLoginUser,
  serverGetTaskUseMaterialBrandSelectViewByProjectIdAndTaskId,
  serverGetTaskUseMaterialBrandSelectViewByProjectIdAndTaskIdAndCompanyId,
} from "@/server/project/projectmaterialflow";

import { serverDownloadUseMaterialNewBrandFileById } from "@/server/project/usematerial";

//服务器返回到前端的类型
import { IServerPage } from "@/server/types/System";

import { getUserPageSize, setUserPageSize } from "@/cookies/user";

import NewProjectMaterialDialog from "@/components/project/material/NewProjectMaterialDialog.vue";
import UpdateProjectMaterialDialog from "@/components/project/material/UpdateProjectMaterialDialog.vue";
import UploadExcelProjectMaterialDialog from "@/components/project/material/UploadExcelProjectMaterialDialog.vue";

import ShowProjectMaterialDialog from "./ShowProjectMaterialDialog.vue";
import ShowProjectMaterialDiffDialog from "@/components/project/material/ShowProjectMaterialDiffDialog.vue";
import ShowUseProjectMaterialDialog from "@/components/project/material/ShowUseProjectMaterialDialog.vue";
import ShowUseProjectMaterialDiffDialog from "@/components/project/material/ShowUseProjectMaterialDiffDialog.vue";
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
const updateUseProjectMaterialView = ref<IServerUseMaterialView>();
const projectViewData = ref<IServerProjectView>();

const { projectId, taskId } = defineProps<{
  projectId: string;
  taskId: string;
}>();

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
  const ret = await serverGetTaskUseMaterialBrandSelectViewByProjectIdAndTaskIdAndCompanyId(
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
  useMaterialView: IServerUseMaterialView
) => {
  console.log(useMaterialView);
  updateProjectMaterialView.value = useMaterialView.projectMaterialView;
  dialogShowDetailsVisible.value = true;
  updateUseProjectMaterialView.value = useMaterialView;
  console.log(updateUseProjectMaterialView.value, "updatetetetetet");
  
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
const textElipsisValue = ref(false); //文本省略显示
const showProjectMaterialDiff = ref(true); //是否显示不同
const dialogShowProjectMaterialDiffDialogVisible = ref(false); //控制“显示材料不同对话框”是否显示

/**
 * 材料是否不同
 * @param projectMaterialViewItem
 */
const isMaterialDifferent = (useMaterialView: IServerUseMaterialView) => {
  if (!useMaterialView.material) return false;
  return (
    useMaterialView.material.id !==
    useMaterialView.projectMaterialView.material.id
  );
};

const showDiffProjectMaterialView = ref<IServerUseMaterialView>();
const onMaterialDiffClick = async (useMaterialView: IServerUseMaterialView) => {
  showDiffProjectMaterialView.value = useMaterialView;
  dialogShowProjectMaterialDiffDialogVisible.value = true;
};
const onShowProjectMaterialDiffDialogOk = () => {
  dialogShowProjectMaterialDiffDialogVisible.value = false;
};
const onShowProjectMaterialDiffDialogCancel = () => {
  dialogShowProjectMaterialDiffDialogVisible.value = false;
};

watchEffect(async () => {
  // 在 3.5 之前只运行一次
  // 在 3.5+ 中在 "foo" prop 变化时重新执行
  console.log(projectId);
  if (projectId) {
    await getUseMaterialViewFromSever();
    await getProjectFromServer(projectId);
  }
});
</script>

<template>
  <ShowUseProjectMaterialDialog
    :dialogVisible="dialogShowDetailsVisible"
    :projectView="projectViewData"
    :projectMaterialView="updateProjectMaterialView"
    :useMaterialView="updateUseProjectMaterialView"
    @onDilalogCancel="onShowProjectMaterialDialogCancel"
    @onDilalogOk="onShowProjectMaterialDialogOk"
  >
  </ShowUseProjectMaterialDialog>

  <ShowUseProjectMaterialDiffDialog
    :dialogVisible="dialogShowProjectMaterialDiffDialogVisible"
    :showDiffProjectMaterialView="showDiffProjectMaterialView"
    @onDilalogCancel="onShowProjectMaterialDiffDialogCancel"
    @onDilalogOk="onShowProjectMaterialDiffDialogOk"
  ></ShowUseProjectMaterialDiffDialog>

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
      <div class="top-toolbar" style="display: flex">
        <div style="margin-left: auto">
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
        <el-col :span="2"> 影响外观 </el-col>
        <el-col :span="2"> 品牌</el-col>
        <el-col :span="2"> 操作</el-col>
      </el-row>

      <el-row
        v-for="(
          useMaterialViewItem, useMaterialViewIndex
        ) in useMaterialBrandSelectView?.useMaterialViewList"
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
          <div v-if="isMaterialDifferent(useMaterialViewItem)">
            <el-badge value="修改" class="item">
              <el-button @click="onMaterialDiffClick(useMaterialViewItem)">{{
                useMaterialViewItem.material.name
              }}</el-button>
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
            <span>{{
              useMaterialViewItem.projectMaterialView.material.name
            }}</span>
          </div>
        </el-col>

        <!--材料位置-->
        <el-col :span="3">
          <div style="display: flex; align-items: center">
            <div
              v-if="
                showProjectMaterialDiff &&
                isMaterialDifferent(useMaterialViewItem) &&
                useMaterialViewItem.material.location !==
                  useMaterialViewItem.projectMaterialView.material.location
              "
              style="color: yellow; background-color: #606266"
              :class="{ textEllipsis: textElipsisValue }"
            >
              {{ useMaterialViewItem.material.location }}
            </div>
            <div v-else :class="{ textEllipsis: textElipsisValue }">
              {{ useMaterialViewItem.projectMaterialView.material.location }}
            </div>
          </div>
        </el-col>

        <!--编号-->
        <el-col :span="2">
          <div style="display: flex; align-items: center">
            <div
              v-if="
                showProjectMaterialDiff &&
                isMaterialDifferent(useMaterialViewItem) &&
                useMaterialViewItem.material.itemMark !==
                  useMaterialViewItem.projectMaterialView.material.itemMark
              "
              style="color: yellow; background-color: #606266"
              :class="{ textEllipsis: textElipsisValue }"
            >
              {{ useMaterialViewItem.material.itemMark }}
            </div>
            <div v-else :class="{ textEllipsis: textElipsisValue }">
              {{ useMaterialViewItem.projectMaterialView.material.itemMark }}
            </div>
          </div>
        </el-col>

        <!--技术要求-->
        <el-col :span="6">
          <div style="display: flex; align-items: center">
            <div
              v-if="
                showProjectMaterialDiff &&
                isMaterialDifferent(useMaterialViewItem) &&
                useMaterialViewItem.material.technology !==
                  useMaterialViewItem.projectMaterialView.material.technology
              "
              style="color: yellow; background-color: #606266"
              :class="{ textEllipsis: textElipsisValue }"
            >
              {{ useMaterialViewItem.material.technology }}
            </div>
            <div v-else :class="{ textEllipsis: textElipsisValue }">
              {{ useMaterialViewItem.projectMaterialView.material.technology }}
            </div>
          </div>
        </el-col>

        <!--施工要求-->
        <el-col :span="4">
          <div style="display: flex; align-items: center">
            <div
              v-if="
                showProjectMaterialDiff &&
                isMaterialDifferent(useMaterialViewItem) &&
                useMaterialViewItem.material.installation !==
                  useMaterialViewItem.projectMaterialView.material.installation
              "
              style="color: yellow; background-color: #606266"
              :class="{ textEllipsis: textElipsisValue }"
            >
              {{ useMaterialViewItem.material.installation }}
            </div>
            <div v-else :class="{ textEllipsis: textElipsisValue }">
              {{
                useMaterialViewItem.projectMaterialView.material.installation
              }}
            </div>
          </div>
        </el-col>
        <!--影响外观-->
        <el-col :span="2">
          <div style="display: flex; align-items: center">
            {{
              useMaterialViewItem.useMaterial.isAppearance == 0
                ? "不影响外观"
                : "影响外观"
            }}
          </div>
        </el-col>

        <!--品牌-->
        <el-col :span="2">
          <div style="display: flex; align-items: center">
            <div
              v-if="
                useMaterialViewItem.projectMaterialBrandPrivateView == null &&
                useMaterialViewItem.projectMaterialBrandPublicView == null &&
                useMaterialViewItem.useMaterialNewBrandView != null
              "
            >
              <div class="container" style="padding: 10px">
                <div>
                  新品牌：
                  {{
                    useMaterialViewItem.useMaterialNewBrandView
                      .useMaterialNewBrand.brandName
                  }}
                </div>
                <div>
                  {{
                    useMaterialViewItem.useMaterialNewBrandView
                      .materialClassifyDivision.name
                  }}-{{
                    useMaterialViewItem.useMaterialNewBrandView
                      .materialClassifyGroup.name
                  }}-{{
                    useMaterialViewItem.useMaterialNewBrandView
                      .materialClassifySection.name
                  }}-{{
                    useMaterialViewItem.useMaterialNewBrandView
                      .useMaterialNewBrand.materialPosition
                  }}
                </div>
                <div>
                  附件：
                  <div
                    v-for="(item, index) in useMaterialViewItem
                      .useMaterialNewBrandView.useMaterialNewBrandFileViewList"
                    :key="index"
                    style="padding-left: 10px; padding-right: 10px"
                    class="download-file"
                    @click="
                      downFileFromServer(
                        useMaterialViewItem.projectMaterialView.project.id,
                        item.useMaterialNewBrandFile.id,
                        useMaterialViewItem.user.realName +
                          '_' +
                          useMaterialViewItem.user.userName,
                        index + 1,
                        item.useMaterialNewBrandFile.filePath
                      )
                    "
                  >
                    {{
                      getFullFilename(
                        useMaterialViewItem.projectMaterialView.project.id,
                        item.useMaterialNewBrandFile.id,
                        useMaterialViewItem.user.realName +
                          "_" +
                          useMaterialViewItem.user.userName,
                        index + 1,
                        item.useMaterialNewBrandFile.filePath
                      )
                    }}
                  </div>
                </div>
              </div>
            </div>
            <div
              v-else-if="
                useMaterialViewItem.projectMaterialBrandPrivateView != null
              "
            >
              {{
                useMaterialViewItem.projectMaterialBrandPrivateView
                  .projectBrandView.brandView.brand.name
              }}（项目私有品牌）
            </div>
            <div
              v-else-if="
                useMaterialViewItem.projectMaterialBrandPublicView != null
              "
            >
              {{
                useMaterialViewItem.projectMaterialBrandPublicView
                  .brandPublicView.brandView.brand.name
              }}（公共品牌）
            </div>
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
              @click="
                onRowShowButtonClick(useMaterialViewIndex, useMaterialViewItem)
              "
            >
              详细信息
            </el-button>
          </div>
        </el-col>
      </el-row>
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
