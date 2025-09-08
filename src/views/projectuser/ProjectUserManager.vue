<script setup lang="ts">
/**
 * 对单位进行管理 *
 * 主要功能：增删改查
 */

import { computed, onMounted, reactive, ref, Ref } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";

import {
  IServerCompany,
  IServerCompanyUser,
  IServerCompanyUserView,
} from "@/server/types/system/company";

import {
  IServerProject,
  IServerProjectView,
  IServerProjectUser,
  IServerProjectUserView,
  IServerProjectAllUserView,
} from "@/server/types/project/project";
import {
  serverProjectUserAdd,
  serverProjectUserAddManager,
  serverProjectUserAddEmployee,
  serverProjectUserDelete,
  serverProjectUserDeleteById,
  serverProjectUserUpdate,
  serverGetProjectUserById,
  serverGetProjectUserByUerId,
  serverGetProjectUserByProjectId,
  serverGetProjectUserByRoleId,
  serverGetProjectUserPage,
  serverGetProjectAllUsersPageView,
  serverGetProjectAllUsersPageViewByProjectName,
  serverGetProjectAllUsersPageViewByUserName,
} from "@/server/project/projectuser";

//服务器返回到前端的类型
import { IServerResponseData, IServerPage } from "@/server/types/System";

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

import NewProjectUserDialog from "@/components/project/NewProjectUserDialog.vue";
import ProjectUserItem from "@/components/project/ProjectUserItem.vue";

const router = useRouter();

const dialogFormNewVisible = ref(false); //控制“新增对话框”是否显示
const dialogCompanyUserType = ref(0); //对话框用户类型
const dialogProjectView = ref<IServerProjectView>();
const formLabelWidth = "140px";
const ruleFormRef = ref<FormInstance>();
const form = reactive({
  id: "",
  name: "",
  note: "",
});
const loading = ref(false);

const updateCompany = ref<IServerCompany>({
  id: "", //id,主键
  name: "", //单位名称单位名称
  companyType: "", //company_type,单位类型：设计单位、设计部、工程部、监理单位、总包单位等单位类型：设计单位、设计部、工程部、监理单位、总包单位等
  note: "", //note,备注备注
  deletedAt: new Date(),
});

const searchText = ref("");
const searchSelect = ref("1");

const rules = reactive<FormRules>({
  name: [{ required: true, message: "请输入公司名称", trigger: "blur" }],
});

const pageNo = ref(1); //第几页
const pageSize = ref(getUserPageSize()); //每页多少数据

const projectAllUserPageViewData =
  ref<IServerPage<IServerProjectAllUserView>>();

const radioUserType = ref(0);

onMounted(async () => {
  await getProjectAllUsersPageViewFromSever();
});

const getProjectAllUsersPageViewFromSever = async () => {
  let search = searchText.value.trim();

  if (search) {
    console.log(searchSelect.value);

    if (searchSelect.value == "0") {
      //项目名称
      console.log(search);
      const ret = await serverGetProjectAllUsersPageViewByProjectName(
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        projectAllUserPageViewData.value = ret.data;
      }
    } else if (searchSelect.value == "1") {
      //用户名称
      const ret = await serverGetProjectAllUsersPageViewByUserName(
        searchText.value,
        pageNo.value,
        pageSize.value
      );
      if (ret && ret.code == 200) {
        projectAllUserPageViewData.value = ret.data;
      }
    }
  } else {
    const ret = await serverGetProjectAllUsersPageView(
      pageNo.value,
      pageSize.value
    );
    console.log(ret);
    if (ret && ret.code == 200) {
      projectAllUserPageViewData.value = ret.data;
    }
  }
};

const tableData = computed(() => {
  return projectAllUserPageViewData.value?.result;
});

const totalCount = computed(() => {
  return Number(projectAllUserPageViewData.value?.totalCount ?? 0);
});

const onPagePrevClick = (value: number) => {};
const onPageNextClick = (value: number) => {};
const onPageCurrentChange = async (value: number) => {
  pageNo.value = value;
  await getProjectAllUsersPageViewFromSever();
};

const onPageSizeChange = async (value: number) => {
  pageSize.value = value;
  setUserPageSize(value);
  await getProjectAllUsersPageViewFromSever();
};

const onSearchClick = async () => {
  let search = searchText.value.trim();

  if (search) {
    pageNo.value = 1;
  }
  await getProjectAllUsersPageViewFromSever();
};

const goBack = () => {
  history.back();
};
/**
 * 增加设计单位项目员工
 */
const onNewDesignCompanyProjectUserButtonClick = (
  projectView: IServerProjectView
) => {
  dialogProjectView.value = projectView;
  dialogFormNewVisible.value = true;
  dialogCompanyUserType.value = 0;
};

/**
 * 增加设计部项目员工
 */
const onNewDesignDepartmentProjectUserButtonClick = (
  projectView: IServerProjectView
) => {
  dialogProjectView.value = projectView;
  dialogFormNewVisible.value = true;
  dialogCompanyUserType.value = 1;
};

/**
 * 增加工程部项目员工
 */
const onNewEngineeringDepartmentProjectUserButtonClick = (
  projectView: IServerProjectView
) => {
  dialogProjectView.value = projectView;
  dialogFormNewVisible.value = true;
  dialogCompanyUserType.value = 2;
};

/**
 * 增加监理单位项目员工
 */
const onNewSupervisionCompanyProjectUserButtonClick = (
  projectView: IServerProjectView
) => {
  dialogProjectView.value = projectView;
  dialogFormNewVisible.value = true;
  dialogCompanyUserType.value = 3;
};

/**
 * 增加总包单位项目员工
 */
const onNewConstructionCompanyProjectUserButtonClick = (
  projectView: IServerProjectView
) => {
  dialogProjectView.value = projectView;
  dialogFormNewVisible.value = true;
  dialogCompanyUserType.value = 4;
};
/**
 * 删除项目员工
 * @param item
 */
const onDeleteProjectUserButtonClick = (item: IServerProjectUserView) => {
  ElMessageBox.confirm("是否真的删除数据？", "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      await serverProjectUserDelete(item.projectUser);
      await getProjectAllUsersPageViewFromSever();
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

/**
 * 新增项目员工
 */
const onNewProjectUserDialogOk = async (
  projectView: IServerProjectView,
  val: IServerCompanyUserView[],
  type: number,
  role: string
) => {
  if (val.length > 0) {
    if (role == "项目经理") {
      let projectUser: IServerProjectUser = {
        id: "",
        userId: val[0].user.id,
        projectId: projectView.project.id,
        roleId: "",
        deletedAt: new Date(),
      };
      await serverProjectUserAddManager(projectUser);
    } else {
      val.forEach(async (item) => {
        let projectUser: IServerProjectUser = {
          id: "",
          userId: item.user.id,
          projectId: projectView.project.id,
          roleId: "",
          deletedAt: new Date(),
        };
        await serverProjectUserAddEmployee(projectUser);
      });
    }
    await getProjectAllUsersPageViewFromSever();
  }
  dialogFormNewVisible.value = false;
};

const onNewProjectUserDialogCancel = () => {
  dialogFormNewVisible.value = false;
};
</script>

<template>
  <!--新增对话框-->
  <NewProjectUserDialog
    :dialogVisible="dialogFormNewVisible"
    :projectView="dialogProjectView"
    :type="dialogCompanyUserType"
    @onDilalogCancel="onNewProjectUserDialogCancel"
    @onDilalogOk="onNewProjectUserDialogOk"
  ></NewProjectUserDialog>

  <el-page-header @back="goBack" style="margin-bottom: 20px">
    <template #content>
      <span class="text-large font-600 mr-3">项目用户管理</span>
    </template>
    <div class="mt-4 text-sm font-bold"></div>
  </el-page-header>

  <div class="tab-container">
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
              <el-option label="项目名称" value="0" />
              <el-option label="用户名称" value="1" />
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
      <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column label="项目信息" width="400">
          <template #default="scope">
            <div>
              <div style="font: 1.5em sans-serif; padding: 10px">
                {{ scope.row.projectView.project.name }}
              </div>
              <div>
                项目地点：{{
                  JSON.parse(scope.row.projectView.project.location)[0]
                }}-{{ JSON.parse(scope.row.projectView.project.location)[1] }}
              </div>
              <div class="project-details">
                建设单位：{{ scope.row.projectView.companyConstruction.name }}
              </div>
              <div class="project-details">
                设计单位：
                <div
                  v-for="(company, companyIndex) in scope.row
                    ?.companyDesignList"
                >
                  {{ company.name }}
                </div>
              </div>
              <div class="start-end-time">
                项目起止时间：{{
                  scope.row.projectView.project.createDatetime
                }}-{{
                  scope.row.projectView.project.endDatetime == null
                    ? "至今"
                    : scope.row.projectView.project.endDatetime
                }}
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="项目员工">
          <template #default="scope">
            <div class="project-user-item-container">
              <!--设计单位员工列表，包括项目经理和项目员工-->
              <ProjectUserItem
                :projectAllUserView="scope.row"
                :type="0"
                @onNew="onNewDesignCompanyProjectUserButtonClick"
                @onDelete="onDeleteProjectUserButtonClick"
              >
              </ProjectUserItem>

              <!--设计部员工列表，包括项目经理和项目员工-->
              <ProjectUserItem
                :projectAllUserView="scope.row"
                :type="1"
                @onNew="onNewDesignDepartmentProjectUserButtonClick"
                @onDelete="onDeleteProjectUserButtonClick"
              >
              </ProjectUserItem>

              <!--工程部员工列表，包括项目经理和项目员工-->
              <ProjectUserItem
                :projectAllUserView="scope.row"
                :type="2"
                @onNew="onNewEngineeringDepartmentProjectUserButtonClick"
                @onDelete="onDeleteProjectUserButtonClick"
              >
              </ProjectUserItem>

              <!--监理单位员工列表，包括项目经理和项目员工-->
              <ProjectUserItem
                :projectAllUserView="scope.row"
                :type="3"
                @onNew="onNewSupervisionCompanyProjectUserButtonClick"
                @onDelete="onDeleteProjectUserButtonClick"
              >
              </ProjectUserItem>

              <!--总包单位员工列表，包括项目经理和项目员工-->
              <ProjectUserItem
                :projectAllUserView="scope.row"
                :type="4"
                @onNew="onNewConstructionCompanyProjectUserButtonClick"
                @onDelete="onDeleteProjectUserButtonClick"
              >
              </ProjectUserItem>
            </div>
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

.input-with-select {
  right: 20px;
  margin-left: 10px;
  flex: 1;
}

.project-user-item-container {
}

.project-user-item {
  border: 1px solid #dcdfe6;
  display: flex;
  margin: 5px;
  padding: 5px;
}
.project-user-item-company {
  width: 100px;
}
.project-user-item-users {
}
.project-user-item-user {
  display: flex;
  align-items: center;
}
.project-user-item-user-name {
  width: 200px;
  margin: 5px;
}
.project-user-item-user-role {
  width: 100px;
  margin: 5px;
}

.project-details {
}
</style>
