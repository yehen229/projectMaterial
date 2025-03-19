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
  IServerProjectMaterialAcceptanceView,
} from "@/server/types/project/review";

import {
  serverStartProcess,
  serverGetTaskByCurrentLoginUser,
  serverGetTaskUseMaterialBrandSelectViewByProjectIdAndTaskId,
  serverGetProjecMaterialAcceptancePageViewByTaskId,
  serverGetProjecMaterialAcceptancePageViewByTaskIdAndName,
  serverGetProjecMaterialAcceptancePageViewByTaskIdAndLocation,
  serverGetProjecMaterialAcceptancePageViewByTaskIdAndItemMark,
  serverGetProjecMaterialAcceptancePageViewByTaskIdAndTechnology,
  serverGetProjecMaterialAcceptancePageViewByTaskIdAndInstallation,
  serverGetProjecMaterialAcceptancePageViewByTaskIdAndBrand,
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

const projectViewPage =
  ref<IServerPage<IServerProjectMaterialAcceptanceView>>();

const updateProjectMaterialView = ref<IServerProjectMaterialView>();
const projectViewData = ref<IServerProjectView>();

const { projectId, taskId } = defineProps<{
  projectId: string;
  taskId: string;
}>();

onMounted(async () => {
  if (projectId) {
    await getProjectFromServer(projectId);

    await getProjectMaterialAcceptanceViewFromSever();
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

const getProjectMaterialAcceptanceViewFromSever = async () => {
  let search = searchText.value.trim();

  if (search) {
    console.log(searchSelect.value);

    if (searchSelect.value == "0") {
      //材料名称
      console.log(search);
      const ret = await serverGetProjecMaterialAcceptancePageViewByTaskIdAndName(
        projectId,
        taskId,
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        projectViewPage.value = ret.data;
      }
    } else if (searchSelect.value == "1") {
      //材料位置
      const ret = await serverGetProjecMaterialAcceptancePageViewByTaskIdAndLocation(
        projectId,
        taskId,
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        projectViewPage.value = ret.data;
      }
    } else if (searchSelect.value == "2") {
      //编号
      const ret = await serverGetProjecMaterialAcceptancePageViewByTaskIdAndItemMark(
        projectId,
        taskId,
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        projectViewPage.value = ret.data;
      }
    } else if (searchSelect.value == "3") {
      //技术要求
      const ret = await serverGetProjecMaterialAcceptancePageViewByTaskIdAndTechnology(
        projectId,
        taskId,
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        projectViewPage.value = ret.data;
      }
    } else if (searchSelect.value == "4") {
      //施工要求
      const ret = await serverGetProjecMaterialAcceptancePageViewByTaskIdAndInstallation(
        projectId,
        taskId,
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        projectViewPage.value = ret.data;
      }
    } else if (searchSelect.value == "5") {
      //品牌
      const ret = await serverGetProjecMaterialAcceptancePageViewByTaskIdAndBrand(
        projectId,
        taskId,
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        projectViewPage.value = ret.data;
      }
    }
  } else {
    const ret = await serverGetProjecMaterialAcceptancePageViewByTaskId(
      projectId,
      taskId,
      pageNo.value,
      pageSize.value
    );
    console.log(ret);
    if (ret && ret.code == 200) {
      projectViewPage.value = ret.data;
    }
  }
};

const tableData = computed(() => {
  return projectViewPage.value?.result ?? [];
});

const totalCount = computed(() => {
  return Number(projectViewPage.value?.totalCount ?? 0);
});

/**
 * 向前翻页
 * @param value
 */
const onPagePrevClick = (value: number) => {};

/**
 * 向后翻页
 * @param value
 */
const onPageNextClick = (value: number) => {};
const onPageCurrentChange = async (value: number) => {
  pageNo.value = value;
  await getProjectMaterialAcceptanceViewFromSever();
};

const onPageSizeChange = async (value: number) => {
  pageSize.value = value;
  setUserPageSize(value);
  await getProjectMaterialAcceptanceViewFromSever();
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

watchEffect(async () => {
  // 在 3.5 之前只运行一次
  // 在 3.5+ 中在 "foo" prop 变化时重新执行
  console.log(projectId);
  if (projectId) {
    await getProjectMaterialAcceptanceViewFromSever();
    await getProjectFromServer(projectId);
  }
});

const onSearchClick = async () => {
  let search = searchText.value.trim();

  if (search) {
    pageNo.value = 1;
  }
  await getProjectMaterialAcceptanceViewFromSever();
};

const collapsed = ref(false);
const textElipsisValue = ref(false);
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
      <div class="top-toolbar">
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
                <el-option label="品牌" value="5" />
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
        </div>
      </div>
      <el-table
        :data="tableData"
        style="width: 100%"
        v-loading="loading"
        stripe
        show-overflow-tooltip
      >
        <el-table-column label="材料名称" show-overflow-tooltip>
          <template #default="scope">
            <div :class="{ textEllipsis: textElipsisValue }">
              {{ scope.row.projectMaterialView.material.name }}
            </div>
          </template>
        </el-table-column>

        <el-table-column label="材料位置" show-overflow-tooltip>
          <template #default="scope">
            <div :class="{ textEllipsis: textElipsisValue }">
              {{ scope.row.projectMaterialView.material.location }}
            </div>
          </template>
        </el-table-column>

        <el-table-column label="编号" show-overflow-tooltip>
          <template #default="scope">
            <div :class="{ textEllipsis: textElipsisValue }">
              {{ scope.row.projectMaterialView.material.itemMark }}
            </div>
          </template>
        </el-table-column>

        <el-table-column label="技术要求"  width="180" show-overflow-tooltip>
          <template #default="scope">
            <div :class="{ textEllipsis: textElipsisValue }">
              {{ scope.row.projectMaterialView.material.technology }}
            </div>
          </template>
        </el-table-column>

        <el-table-column label="施工要求"  width="180" show-overflow-tooltip>
          <template #default="scope">
            <div :class="{ textEllipsis: textElipsisValue }">
              {{ scope.row.projectMaterialView.material.installation }}
            </div>
          </template>
        </el-table-column>

        <el-table-column label="品牌" width="100">
          <template #default="scope">
            <div :class="{ textEllipsis: textElipsisValue }">
              <div v-if="scope.row.projectMaterialBrandPrivateView != null">
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

        <el-table-column label="材料数量" show-overflow-tooltip>
          <template #default="scope">
            <div :class="{ textEllipsis: textElipsisValue }">
              {{ scope.row.projectMaterialAcceptance.materialCount }}
            </div>
          </template>
        </el-table-column>
        <el-table-column label="数量单位" show-overflow-tooltip>
          <template #default="scope">
            <div :class="{ textEllipsis: textElipsisValue }">
              {{ scope.row.projectMaterialAcceptance.materialUnit }}
            </div>
          </template>
        </el-table-column>
        <el-table-column label="验收位置" show-overflow-tooltip>
          <template #default="scope">
            <div :class="{ textEllipsis: textElipsisValue }">
              {{ scope.row.projectMaterialAcceptance.position }}
            </div>
          </template>
        </el-table-column>
        <el-table-column label="备注" show-overflow-tooltip>
          <template #default="scope">
            <div :class="{ textEllipsis: textElipsisValue }">
              {{ scope.row.projectMaterialAcceptance.note }}
            </div>
          </template>
        </el-table-column>
      </el-table>

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

::v-deep .el-table .cell {
  white-space: pre-line;
}
</style>
