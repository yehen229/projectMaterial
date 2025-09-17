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
  serverGetTaskHistoryByProjectId,
  serverGetHistoryViewPageByProjectId,
} from "@/server/project/projectmaterialflow";

import { IServerProjectOpHistoryView } from "@/server/types/project/flow";

//服务器返回到前端的类型
import { IServerPage } from "@/server/types/System";

import { getUserPageSize, setUserPageSize } from "@/cookies/user";

import NewProjectMaterialDialog from "@/components/project/material/NewProjectMaterialDialog.vue";
import UpdateProjectMaterialDialog from "@/components/project/material/UpdateProjectMaterialDialog.vue";
import UploadExcelProjectMaterialDialog from "@/components/project/material/UploadExcelProjectMaterialDialog.vue";

import { formatDate } from "@/utils/utils";

const router = useRouter();
const route = useRoute();
const loading = ref(false);

const pageNo = ref(1); //第几页
const pageSize = ref(getUserPageSize()); //每页多少数据

const projectHistoryPageData =
  ref<IServerPage<IServerProjectOpHistoryView> | null>(null);
const projectViewData = ref<IServerProjectView>();

const { projectId } = defineProps(["projectId"]);

onMounted(async () => {
  if (projectId) {
    await getProjectFromServer(projectId);
    await getProjectHistoryFromServer(projectId);
  }
});

const getProjectFromServer = async (projectId: string) => {
  const ret = await serverGetProjectViewById(projectId);

  if (ret && ret.code == 200) {
    projectViewData.value = ret.data;
  }
};

const getProjectHistoryFromServer = async (projectId: string) => {
  const ret = await serverGetHistoryViewPageByProjectId(
    projectId,
    pageNo.value,
    pageSize.value
  );

  if (ret && ret.code == 200) {
    projectHistoryPageData.value = ret.data;
  }
};
const tableData = computed(() => {
  return projectHistoryPageData.value?.result ?? [];
});

const totalCount = computed(() => {
  return Number(projectHistoryPageData.value?.totalCount ?? 0);
});
const onPagePrevClick = (value: number) => {};
const onPageNextClick = (value: number) => {};
const onPageCurrentChange = async (value: number) => {
  pageNo.value = value;
  await getProjectHistoryFromServer(projectId);
};

const onPageSizeChange = async (value: number) => {
  pageSize.value = value;
  setUserPageSize(value);
  await getProjectHistoryFromServer(projectId);
};

const collapsed = ref(false);

watchEffect(async () => {
  // 在 3.5 之前只运行一次
  // 在 3.5+ 中在 "foo" prop 变化时重新执行

  if (projectId) {
    await getProjectFromServer(projectId);
    await getProjectHistoryFromServer(projectId);
  }
});
</script>

<template>
  <div class="project-material-list-container container">
    <div style="font: 1.2em sans-serif; margin: 10px">
      项目审核历史
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

    <!--显示内容-->
    <div class="project-container" v-show="!collapsed">
      <el-table
        :data="tableData"
        style="width: 100%"
        v-loading="loading"
        stripe
        show-overflow-tooltip
      >
        <el-table-column label="阶段" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="margin-left: 10px">{{
                scope.row.projectOpHistory.stepPhase
              }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="操作" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="margin-left: 10px">{{
                scope.row.projectOpHistory.stepDescription
              }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="操作者" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="margin-left: 10px">{{
                scope.row.user.realName
              }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="单位或部门" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="margin-left: 10px">{{
                scope.row.company?.name
              }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="时间" show-overflow-tooltip>
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span style="margin-left: 10px">{{
                formatDate(scope.row.projectOpHistory.opDatetime)
              }}</span>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-pagination
      style="margin: 10px"
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
.project-material-list-container {
  padding-top: 10px;
}
</style>
