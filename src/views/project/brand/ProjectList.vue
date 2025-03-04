<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref } from "vue";
import { ElMessage } from "element-plus";

import {
  IServerProject,
  IServerProjectView,
  IServerProjectUser,
  IServerProjectUserView,
} from "@/server/types/project/project";
import {
  serverProjectAdd,
  serverProjectUpdate,
  serverProjectDelete,
  serverGetProjectViewById,
  serverGetProjectPageView,
  serverGetProjectPageViewByKeyword,
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

import NewProjectDialog from "@/components/project/project/NewProjectDialog.vue";
import UpdateProjectDialog from "@/components/project/project/UpdateProjectDialog.vue";

const router = useRouter();
const loading = ref(false);

// 页码和页大小
const pageNo = ref(1);
const pageSize = ref(getUserPageSize());

const projectViewPage = ref<IServerPage<IServerProjectView>>();
const updateProjectView = ref<IServerProjectView>();

const dialogFormNewVisible = ref(false); //控制“修改对话框”是否显示
const dialogFormUpdateVisible = ref(false); //控制“修改对话框”是否显示

// 搜索框里的文本
const searchText = ref("");
const searchSelect = ref("1");

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
const onSearchClick = async () => {
  let search = searchText.value.trim();

  if (search) {
    pageNo.value = 1;
  }

  try {
    // 调用 API 搜索包含特定关键字的项目
    const ret = await serverGetProjectPageViewByKeyword(
      search,
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
  await fetchTableData();
};

const onPageSizeChange = async (value: number) => {
  pageSize.value = value;
  setUserPageSize(value);
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

const onNewProjectDialogOk = async (project: IServerProject) => {
  dialogFormNewVisible.value = false;

  const response = await serverProjectAdd(project);

  if (response && response.code === 200) {
    console.log(response.data);
    ElMessage.success("项目创建成功");

    await fetchTableData();
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

const onProjectBrandButtonClick = (
  index: number,
  projectItem: IServerProjectView
) => {
  router.push({ path: `/project-brand/${projectItem.project.id}` });
};

const goBack = () => {
  history.back();
};
</script>

<template>
  <el-page-header @back="goBack" style="margin-bottom: 20px">
    <template #content>
      <span class="text-large font-600 mr-3">私有品牌管理-项目列表</span>
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
              <el-option label="项目地点" value="1" />
            </el-select>
          </template>
          <template #append>
            <el-button :icon="Search" @click="onSearchClick" />
          </template>
        </el-input>
      </div>
      <!--
      <div>
        <el-button :icon="Search" @click="onSearchClick">高级搜索...</el-button>
      </div>
      -->
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
            <div style="display: flex; align-items: center">
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
              {{ scope.row.project.createDatetime }}-{{
                scope.row.project.endDatetime == null
                  ? "至今"
                  : scope.row.project.endDatetime
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

        <el-table-column label=" 建设单位" width="150">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              {{ scope.row.companyConstruction.name }}
            </div>
          </template>
        </el-table-column>

        <el-table-column label=" 设计单位" width="150">
          <template #default="scope">
            <div
              style="display: flex; align-items: center"
              v-if="scope.row.companyDesignList != null"
            >
              <div v-for="(companyItem, index) in scope.row.companyDesignList">
                {{ companyItem.name }}
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="240">
          <template #default="scope">
            <el-button
              size="small"
              type="primary"
              @click="onProjectBrandButtonClick(scope.$index, scope.row)"
            >
              私有品牌管理...
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
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
</style>
