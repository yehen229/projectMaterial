<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref } from "vue";

import { RouterView, useRouter } from "vue-router/dist/vue-router";

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
  serverGetTaskByCurrentLoginUser,
} from "@/server/project/projectmaterialflow";

import { IServerProjectUserTask } from "@/server/types/project/flow";
import ProjectUserTaskList from "@/components/project/flow/ProjectUserTaskList.vue";

const router = useRouter();

onMounted(async () => {});
</script>

<template>
  <router-view></router-view>
</template>

<style scoped>
@import url("@/assets/css/basic.css");
</style>
