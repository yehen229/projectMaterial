<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref } from "vue";
import { ElMessage } from "element-plus";

import {
  IServerProject,
  IServerProjectView,
  IServerProjectUser,
  IServerProjectUserView,
  IServerProjectForm,
  IServerSearchProject
} from "@/server/types/project/project";
import {
  serverProjectAdd,
  serverProjectUpdate,
  serverProjectDelete,
  serverGetProjectViewById,
  serverGetProjectPageView,
  serverGetProjectPageViewByKeyword,
  serverGetProjectPageViewByProjectName,
  serverGetProjectPageViewByProjectLocation,
  serverGetProjectPageViewByParams
} from "@/server/project/project";

//服务器返回到前端的类型
import { IServerResponseData, IServerPage } from "@/server/types/System";

import { Search, Plus } from "@element-plus/icons-vue";

// 项目编辑
import { useRouter } from "vue-router";

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
  serverStartProcess,
  serverStartProcessByProjectId,
  serverGetTaskByCurrentLoginUser,
  serverGetTaskByCurrentLoginUserAndProjectId,
  serverSubmitProjectMaterialReviewOfManagerDirect,
  serverSubmitProjectMaterialReviewManagerDistributeToEmployees,
  serverSubmitProjectMaterialReviewOfEmployee,
  serverSubmitProjectMaterialReviewOfManagerSummary,
} from "@/server/project/projectmaterialflow";

import NewProjectDialog from "@/components/project/project/NewProjectDialog.vue";
import UpdateProjectDialog from "@/components/project/project/UpdateProjectDialog.vue";
import AdvancedSearchProjectDialog from "@/components/project/project/AdvancedSearchProjectDialog.vue";
import { formatDate } from "../../../utils/utils";

const router = useRouter();
const loading = ref(false);

// 页码和页大小
const pageNo = ref(1);
const pageSize = ref(getUserPageSize());

const projectViewPage = ref<IServerPage<IServerProjectView>>();
const updateProjectView = ref<IServerProjectView>();

const dialogFormNewVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormUpdateVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormAdvancedSearchVisible = ref(false); //控制“修改对话框”是否显示
// 搜索框里的文本
const searchText = ref("");
const searchSelect = ref("1");
const currentUserIsDesignOrEngineeringDepartment = ref(false);

const tableData = computed(() => {
  return projectViewPage.value?.result;
});

const totalCount = computed(() => {
  return Number(projectViewPage.value?.totalCount ?? 0);
});

/**
 * 编辑
 * @param projectItem 需要编辑的项目
 */
const onEditButtonClick = (index: number, projectItem: IServerProjectView) => {
  updateProjectView.value = projectItem;
  dialogFormUpdateVisible.value = true;
};

const onProjectMaterialButtonClick = (
  index: number,
  projectItem: IServerProjectView
) => {
  router.push({ path: `/project-material/${projectItem.project.id}` });
};

// 点击搜索按钮
// const onSearchClick = async () => {
//   let search = searchText.value.trim();

//   if (search) {
//     pageNo.value = 1;
//   }

//   try {
//     // 调用 API 搜索包含特定关键字的项目
//     const ret = await serverGetProjectPageViewByKeyword(
//       search,
//       pageNo.value,
//       pageSize.value
//     );

//     if (ret && ret.code == 200) {
//       projectViewPage.value = ret.data;
//     }
//   } catch (error) {
//     ElMessage.error("搜索失败");
//     console.error("获取项目列表失败", error);
//   }
// };

// 点击搜索按钮
const onSearchClick = async () => {
  let search = searchText.value.trim();
  let projectName = "";
  let projectLocation = "";
  if (search) {
    pageNo.value = 1;
  }
  if (searchText.value == "") {
    await fetchTableData();
  } else {
      if (searchSelect.value == "1") {
      projectLocation = search;
      try {
        const ret = await serverGetProjectPageViewByProjectLocation(
          projectLocation,
          pageNo.value,
          pageSize.value
        );

        if (ret && ret.code == 200) {
          projectViewPage.value = ret.data;
        }
      } catch (error) {
        ElMessage.error("搜索失败");
        console.error("获取项目列表失败", error);
      }
      
    } else if(searchSelect.value == "0") { 
      projectName = search;
      try {
        const ret = await serverGetProjectPageViewByProjectName(
          projectName,
          pageNo.value,
          pageSize.value
        );

        if (ret && ret.code == 200) {
          projectViewPage.value = ret.data;
        }
      } catch (error) {
        ElMessage.error("搜索失败");
        console.error("获取项目列表失败", error);
      }
    }
  }
};

// 获取项目列表
const fetchTableData = async () => {
  try {
    // 调用 API 获取项目列表
    const ret = await serverGetProjectPageView(pageNo.value, pageSize.value);

    if (ret && ret.code == 200) {
      projectViewPage.value = ret.data;
    }
  } catch (error) {
    ElMessage.error("获取项目列表失败");
    console.error("获取项目列表失败", error);
  }
};

// 在组件挂载时获取数据
onMounted(async () => {
  await fetchTableData();
  const ret =
    await serverGetCurrentLoginUserIsInDesignOrEngineeringDepartment();
  if (ret && ret.code == 200)
    currentUserIsDesignOrEngineeringDepartment.value = ret.data;
});

/**
 * 向前翻页
 * @param value
 */
const onPagePrevClick = (value: number) => {
    pageNo.value = pageNo.value - 1;
    onSearchClick();
};

/**
 * 向后翻页
 * @param value
 */
const onPageNextClick = (value: number) => {
    pageNo.value = pageNo.value + 1;
    onSearchClick();
};

const onPageCurrentChange = async (value: number) => {
  pageNo.value = value;
  await fetchTableData();
};

/**
 * 用户点击“新增”按钮，显示新增对话框
 */
const onNewButtonClick = () => {
  dialogFormNewVisible.value = true;
};

const onNewProjectDialogCancel = () => {
  dialogFormNewVisible.value = false;
};

/**
 * 新建项目
 * 1.新建项目
 * 2.启动项目审批流程
 * @param project 项目
 */
const onNewProjectDialogOk = async (projectForm: IServerProjectForm) => {
  dialogFormNewVisible.value = false;

  const response = await serverStartProcess(projectForm);

  if (response && response.code === 200) {
    console.log(response.data);
    ElMessage.success("项目创建成功");

    await fetchTableData();

    //  await serverStartProcessByProjectId(response.data);
  } else {
    console.error("项目创建失败");
  }
};

const onUpdateProjectDialogCancel = () => {
  dialogFormUpdateVisible.value = false;
};

const onUpdateProjectDialogOk = async (project: IServerProject) => {
  const ret = await serverProjectUpdate(project);

  if (ret && ret.code === 200) {
    ElMessage.success("项目更新成功");
    await fetchTableData();
  } else {
    ElMessage.error("项目更新失败");
  }

  dialogFormUpdateVisible.value = false;
};

const onAdvancedSearchClick = () => {
  dialogFormAdvancedSearchVisible.value = true;
};

const onAdvancedSearchDialogCancel = () => {
  dialogFormAdvancedSearchVisible.value = false;
};

const onAdvancedSearchDialogOk = async (project: IServerSearchProject) => {
  try {
    // 调用 API 搜索包含特定关键字的项目
    const ret = await serverGetProjectPageViewByParams(
      project.name,
      project.location,
      project.totalTaxIncluded,
      project.totalTaxNotIncluded,
      project.buildingAreaAboveGround,
      project.buildingAreaUnderGround,
      project.companyConstructionId,
      project.companyDesignId,
      project.note,
      project.createDatetime,
      project.endDatetime,
      pageNo.value,
      pageSize.value
    );

    if (ret && ret.code == 200) {
      projectViewPage.value = ret.data;
    }
  } catch (error) {
    ElMessage.error("搜索失败");
  }
  dialogFormAdvancedSearchVisible.value = false;
};

const onProjectDetailsButtonClick = (
  index: number,
  projectItem: IServerProjectView
) => {
  console.log("/project-details/${projectItem.project.id}",projectItem.project.id)
  router.push({ path: `/project-details/${projectItem.project.id}` });
};

const goBack = () => {
  history.back();
};
</script>

<template>
  <el-page-header @back="goBack" style="margin-bottom: 20px">
    <template #content>
      <span class="text-large font-600 mr-3">项目列表</span>
    </template>
    <div class="mt-4 text-sm font-bold"></div>
  </el-page-header>

  <!--新增对话框-->
  <NewProjectDialog
    :dialogVisible="dialogFormNewVisible"
    @onDilalogCancel="onNewProjectDialogCancel"
    @onDilalogOk="onNewProjectDialogOk"
  ></NewProjectDialog>

  <!--修改对话框-->
  <UpdateProjectDialog
    :dialogVisible="dialogFormUpdateVisible"
    :projectView="updateProjectView"
    @onDilalogCancel="onUpdateProjectDialogCancel"
    @onDilalogOk="onUpdateProjectDialogOk"
  ></UpdateProjectDialog>

  <!--高级搜索对话框-->
  <!-- <AdvancedSearchProjectDialog
    :dialogVisible="dialogFormAdvancedSearchVisible"
    @onDilalogCancel="onAdvancedSearchDialogCancel"
    @onDilalogOk="onAdvancedSearchDialogOk"
  ></AdvancedSearchProjectDialog> -->

  <div class="tab-container">
    <div class="top-toolbar">
      <!--新增按钮-->
      <div>
        <el-button
          :icon="Plus"
          type="primary"
          @click="onNewButtonClick"
          v-show="currentUserIsDesignOrEngineeringDepartment"
        >
          新增项目
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
              <el-option label="项目名称" value="0" />
              <el-option label="项目地点" value="1" />
            </el-select>
          </template>
          <template #append>
            <el-button :icon="Search" @click="onSearchClick" />
          </template>
        </el-input>
      </div>

      <!-- <div style="margin-left: 10px">
        <el-button :icon="Search" @click="onAdvancedSearchClick"
          >高级搜索...</el-button
        >
      </div> -->
    </div>

    <!--显示内容-->
    <div class="project-container">
      <el-table
        :data="tableData"
        style="width: 100%"
        v-loading="loading"
        stripe
      >
        <el-table-column label="项目名称">
          <template #default="scope">
            <div
              style="display: flex; align-items: center"
              class="project-title"
              @click="onProjectDetailsButtonClick(scope.$index, scope.row)"
            >
              {{ scope.row.project.name }}
            </div>
          </template>
        </el-table-column>

        <el-table-column label="项目地点">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              {{ JSON.parse(scope.row.project.location)[0] }}-{{
                JSON.parse(scope.row.project.location)[1]
              }}
            </div>
          </template>
        </el-table-column>

        <el-table-column label=" 项目起止时间">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              {{ formatDate(scope.row.project.createDatetime) }}-{{
                scope.row.project.endDatetime == null
                  ? "至今"
                  : formatDate(scope.row.project.endDatetime)
              }}
            </div>
          </template>
        </el-table-column>

        <el-table-column label="总投资(含税)">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              {{ scope.row.project.totalTaxIncluded }}万元
            </div>
          </template>
        </el-table-column>

        <el-table-column label="总投资(不含税)">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              {{ scope.row.project.totalTaxNotIncluded }}万元
            </div>
          </template>
        </el-table-column>

        <el-table-column label="建筑面积地上(㎡)">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              {{ scope.row.project.buildingAreaAboveGround }}㎡
            </div>
          </template>
        </el-table-column>

        <el-table-column label="建筑面积地下(㎡)">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              {{ scope.row.project.buildingAreaUnderGround }}㎡
            </div>
          </template>
        </el-table-column>

        <el-table-column label=" 建设单位">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              {{ scope.row.companyConstruction.name }}
            </div>
          </template>
        </el-table-column>

        <el-table-column label=" 设计单位">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <div
                v-for="(company, companyIndex) in scope.row.companyDesignList"
              >
                {{ company.name }}
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="操作">
          <template #default="scope">
            <!--    <el-button
              size="small"
              @click="onEditButtonClick(scope.$index, scope.row)"
            >
              编辑
            </el-button>

            <el-button
              size="small"
              @click="onProjectMaterialButtonClick(scope.$index, scope.row)"
            >
              项目材料...
            </el-button>
            -->
            <el-button
              size="small"
              @click="onProjectDetailsButtonClick(scope.$index, scope.row)"
            >
              项目详细信息
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-pagination
      :hide-on-single-page="false"
      class="page-class"
      background
      v-model:current-page="pageNo"
      v-model:page-size="pageSize"
      layout="total, prev, pager, next"
      :total="totalCount"
      @prev-click="onPagePrevClick"
      @next-click="onPageNextClick"
      @current-change="onPageCurrentChange"
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
}

.user-type-radio {
  margin-left: 50px;
}

.input-with-select {
  right: 20px;
  margin-left: 10px;
  flex: 1;
}

.project-card {
  border: 1.335px;
  border-radius: 4px;
  border-style: solid;
  border-color: #dcdfe6;
  margin-top: 20px;
}

.project-name {
  margin: 15px;
}

.project-details {
  display: flex;
  margin-left: 15px;
  margin-bottom: 10px;
  font-size: 11px;
}

.start-end-time {
  margin-right: 15%;
}

.editor-btn {
  margin-left: 15px;
  margin-bottom: 5px;
}

.project-title {
  cursor: pointer;
}
.user-type-radio:hover {
  color: #409eff;
}
</style>
