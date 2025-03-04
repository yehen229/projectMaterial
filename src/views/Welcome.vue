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
  serverSubmitProcess,
  serverGetTaskByCurrentLoginUser,
} from "@/server/project/projectmaterialflow";

import { IServerProjectUserTask } from "@/server/types/project/flow";
import ProjectUserTaskList from "@/components/project/flow/ProjectUserTaskList.vue";

const router = useRouter();

const projectUserTaskList: Ref<IServerProjectUserTask[]> = ref([]);

onMounted(async () => {
  const ret = await serverGetTaskByCurrentLoginUser();
  if (ret && ret.code == 200) {
    projectUserTaskList.value = ret.data;
  }
});

const goBack = () => {
  history.back();
};

const onProjectUserTaskClick = async (
  projectUserTask: IServerProjectUserTask
) => {
  router.push({
    path: `/project-user-task/${projectUserTask.projectView.project.id}`,
  });
};
</script>

<template>
  <!--显示项目列表、当前能够执行的操作（例如审核等）-->
  <div class="tab-container">
    <ProjectUserTaskList></ProjectUserTaskList>
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

.project-container {
  margin: 10px;

  display: flex;
  flex-wrap: wrap;
  justify-content: baseline;
  align-items: center;
}
</style>
