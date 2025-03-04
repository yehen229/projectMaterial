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
  serverStartProcessByProjectId,
  serverDesignCompanySubmitProjectMaterial,
  serverSubmitAdminSetDesignAndEngineeringDepartmentUsers,
  serverSubmitProjectMaterialReviewOfManagerDirect,
  serverSubmitProjectMaterialReviewManagerDistributeToEmployees,
  serverSubmitProjectMaterialReviewOfEmployee,
  serverSubmitProjectMaterialReviewOfManagerSummary,
  serverSubmitEngineeringDepartmentManagerDispatch,
  serverGetTaskByCurrentLoginUser,
  serverGetTaskByCurrentLoginUserAndProjectId,
} from "@/server/project/projectmaterialflow";

import {
  serverProjectUserAdd,
  serverProjectUserAddManager,
  serverProjectUserAddEmployee,
  serverProjectUserDelete,
  serverProjectUserDeleteById,
  serverProjectUserUpdate,
  serverGetCurrentLoginUserIsDesignCompanyEmployee,
  serverGetCurrentLoginUserIsInDesignOrEngineeringDepartment,
  serverGetCurrentLoginUserIsInDesignDepartment,
  serverGetCurrentLoginUserIsDesignDepartmentProjectEmployee,
  serverGetCurrentLoginUserIsDesignDepartmentManager,
  serverGetCurrentLoginUserIsDesignDepartmentManagerOrEmployee,
  serverGetCurrentLoginUserIsInEngineeringDepartment,
  serverGetCurrentLoginUserIsEngineeringDepartmentEmployee,
  serverGetCurrentLoginUserIsEngineeringDepartmentManager,
  serverGetCurrentLoginUserIsEngineeringDepartmentManagerOrEmployee,
  serverGetCurrentLoginUserIsGeneralContractorCompanyEmployee,
  serverGetCurrentLoginUserIsSupervisionCompanyEmployee,
  serverGetProjectUserById,
  serverGetProjectUserByUerId,
  serverGetProjectUserByProjectId,
  serverGetCompanyByProjectId,
  serverGetProjectUserByRoleId,
  serverGetRoleByUserIdAndProjectId,
  serverGetEmployeeUserOfDesignDepartment,
  serverGetProjectAllUserViewByProjectId,
  serverGetProjectUserPage,
  serverGetProjectAllUsersPageView,
  serverGetProjectAllUsersPageViewByProjectName,
  serverGetProjectAllUsersPageViewByUserName,
} from "@/server/project/projectuser";
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

const projectId = ref("");
onBeforeRouteUpdate(async (to) => {
  if (typeof to.params.id === "string") {
    projectId.value = to.params.id;
    await getProjectFromServer(to.params.id);
  } else {
    getProjectFromServer(to.params.id[0]);
    projectId.value = to.params.id[0];
  }
});

onMounted(async () => {
  if (typeof route.params.id === "string") {
    await getProjectFromServer(route.params.id);
    projectId.value = route.params.id;
  }

  await getProjectMaterialViewFromSever();
});

const getProjectFromServer = async (projectId: string) => {
  const ret = await serverGetProjectViewById(projectId);
  console.log(ret);
  if (ret && ret.code == 200) {
    projectViewData.value = ret.data;
  }
};

const getProjectMaterialViewFromSever = async () => {
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
const onRowDeleteButtonClick = async (index: number, row: IServerCompany) => {
  console.log(index, row);

  ElMessageBox.confirm("是否真的删除数据？", "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      await serverCompanyDelete(row);
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

const onExcelUploadDialogCancel = () => {
  dialogFormExcelVisible.value = false;
};

const onExcelUploadDialogOk = () => {
  dialogFormExcelVisible.value = false;
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

  <el-page-header @back="goBack" style="margin-bottom: 20px">
    <template #content>
      <span class="text-large font-600 mr-3">项目材料</span>
    </template>
    <div class="mt-4 text-sm font-bold"></div>
  </el-page-header>

  <div class="tab-container">
    <div class="top-toolbar">
      <!--新增按钮-->
      <div>
        <!--   <el-button :icon="Plus" type="primary" @click="onNewButtonClick">
          新增材料
        </el-button>

        <el-button :icon="Upload" @click="onExcelUploadButtonClick">
          导入材料（Excel）
        </el-button>-->
        <el-button :icon="Download" @click="onDownloadExcelButtonClick">
          导出材料（Excel）
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
              <el-option label="品牌" value="5" />
              <el-option label="审核状态" value="6" />
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
          style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
          active-text="自动调整高度"
          inactive-text="显示全部内容"
        />
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
        <el-table-column
          label="材料名称"
          width="100"
          v-if="radioUserType == 0"
          show-overflow-tooltip
        >
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <div :class="{ textEllipsis: textElipsisValue }">
                {{ scope.row.material.name }}
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="材料位置" width="170" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <div :class="{ textEllipsis: textElipsisValue }">
                {{ scope.row.material.location }}
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="编号" width="100" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <div :class="{ textEllipsis: textElipsisValue }">
                {{ scope.row.material.itemMark }}
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="技术要求" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <div :class="{ textEllipsis: textElipsisValue }">
                {{ scope.row.material.technology }}
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="施工要求" width="200" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <div :class="{ textEllipsis: textElipsisValue }">
                {{ scope.row.material.installation }}
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="品牌" width="100" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <!--公共品牌-->
              <span
                v-for="(brandPublicViewItem, brandPublicIndex) in scope.row
                  .projectMaterialBrandPublicViewList"
                :key="brandPublicIndex"
              >
                <el-tag type="success">{{
                  brandPublicViewItem.brandPublicView.brandView.brand.name
                }}</el-tag>
              </span>
              <!--项目私有品牌-->
              <span
                v-for="(brandPrivateViewItem, brandPrivateIndex) in scope.row
                  .projectMaterialBrandPrivateViewList"
                :key="brandPrivateIndex"
              >
                <el-tag type="info">{{
                  brandPrivateViewItem.projectBrandView.brandView.brand.name
                }}</el-tag>
              </span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="审核状态" width="100" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="margin-left: 10px">{{ scope.row.checkstate }}</span>
            </div>
          </template>
        </el-table-column>

        <!--   <el-table-column label="操作" width="240">
          <template #default="scope">
            <el-button
              size="small"
              @click="onRowEditButtonClick(scope.$index, scope.row)"
            >
              编辑
            </el-button>

            <el-button
              size="small"
              type="danger"
              @click="onRowDeleteButtonClick(scope.$index, scope.row)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>-->
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
@import url("@/assets/css/basic.css");
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
