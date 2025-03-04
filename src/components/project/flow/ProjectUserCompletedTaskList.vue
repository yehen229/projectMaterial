<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";

import {
  IServerProject,
  IServerProjectView,
  IServerProjectUser,
  IServerProjectUserView,
} from "@/server/types/project/project";
import {
  serverGetProjectPageView,
  serverGetProjectPageViewByKeyword,
} from "@/server/project/project";

import {
  serverStartProcess,
  serverGetCompletedTaskByCurrentLoginUser,
  serverGetTaskByCurrentLoginUser,
} from "@/server/project/projectmaterialflow";

import { IServerProjectUserCompletedTask } from "@/server/types/project/flow";

import { formatDate } from "@/utils/utils";

const router = useRouter();

const projectUserCompletedTaskList: Ref<IServerProjectUserCompletedTask[]> =
  ref<IServerProjectUserCompletedTask[]>([]);

onMounted(async () => {
  const ret = await serverGetCompletedTaskByCurrentLoginUser();
  if (ret && ret.code == 200) {
    projectUserCompletedTaskList.value = ret.data;
  }

  console.log(projectUserCompletedTaskList.value);
});

const goBack = () => {
  history.back();
};

const onProjectUserTaskClick = async (
  projectUserTask: IServerProjectUserCompletedTask
) => {};

const onProjectClick = (projectUserTask: IServerProjectUserCompletedTask) => {
  router.push({
    path: `/project-details/${projectUserTask.projectView.project.id}`,
  });
};
</script>

<template>
  <!--显示项目列表、当前能够执行的操作（例如审核等）-->
  <div class="tab-container">
    <div
      v-for="(projectUserTask, index) in projectUserCompletedTaskList"
      :key="index"
    >
      <el-card class="box-card">
        <!--项目名称-->
        <template #header>
          <span class="card-title">{{
            projectUserTask.projectView.project.name
          }}</span>
        </template>
        <div
          class="card-content"
          @click="onProjectUserTaskClick(projectUserTask)"
        >
          <div class="card-content-item">
            <span class="card-content-item-title">任务名称：</span>
            <span class="card-content-item-content">{{
              projectUserTask.taskName
            }}</span>
          </div>
          <div class="card-content-item">
            <span class="card-content-item-title">项目：</span>
            <span
              class="card-content-item-project-title"
              @click="onProjectClick(projectUserTask)"
              >{{ projectUserTask.projectView.project.name }}</span
            >
          </div>
          <div class="card-content-item">
            <span class="card-content-item-title">完成时间：</span>
            <span class="card-content-item-content">{{
              formatDate(projectUserTask.operateDate)
            }}</span>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<style scoped>
.page-class {
  padding: 10px;
}

.top-toolbar {
  display: flex;
  margin: 0 10px;
}

.project-container {
  margin: 10px;

  display: flex;
  flex-wrap: wrap;
  justify-content: baseline;
  align-items: center;
}

.card-content {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 10px;
  cursor: pointer;
}

.card-content-item-project-title {
  cursor: pointer;
}
.card-content-item-project-title:hover {
  color: #409eff;
}
</style>
