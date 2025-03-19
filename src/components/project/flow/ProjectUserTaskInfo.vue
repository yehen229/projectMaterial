<script setup lang="ts">
import { computed, onBeforeMount, onMounted, reactive, ref, Ref } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { ArrowUp, ArrowDown, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";

import {
  IServerProject,
  IServerProjectView,
  IServerProjectUser,
  IServerProjectUserView,
  IServerProjectAllUserView,
} from "@/server/types/project/project";
import {
  serverGetProjectPageView,
  serverGetProjectPageViewByKeyword,
} from "@/server/project/project";

import {
  serverGetProjectAllUserViewByProjectId
} from  "@/server/project/projectuser"

import {
  serverStartProcess,
  serverGetTaskByCurrentLoginUser,
} from "@/server/project/projectmaterialflow";

import { IServerProjectUserTask } from "@/server/types/project/flow";
import { getTaskName, getTaskProjectName, getTaskTitle } from "./index";
import { formatDate } from "@/utils/utils";
import { IServerPage } from "@/server/types/System";
const router = useRouter();
const { projectUserTask } = defineProps(["projectUserTask"]);
let projectAllUserPageViewData = ref();
const isShow = ref(false);
const onProjectClick = (projectUserTask: IServerProjectUserTask) => {
  router.push({
    path: `/project-details/${projectUserTask.projectView.project.id}`,
  });
};

onMounted(async () => {
  await getProjectUsersPageViewFromSever();
});

const showUsers = async () => {
  if (isShow.value) {
    isShow.value = false;
  } else {
    await getProjectUsersPageViewFromSever();
    isShow.value = true;
  }
}

const getProjectUsersPageViewFromSever = async () => {
  const ret = await serverGetProjectAllUserViewByProjectId(projectUserTask.projectView.project.id);
  if (ret && ret.code == 200) {
    projectAllUserPageViewData = JSON.parse(JSON.stringify(ret.data));    
  }
};

const collapsed = ref(false);
</script>

<template>
  <h1>{{ getTaskTitle(projectUserTask) }}</h1>
  <div class="task-info-container container" v-if="projectUserTask">
    <div style="font: 1.2em sans-serif; margin: 10px">
      任务名称：{{ getTaskName(projectUserTask) }}
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
    <div v-show="!collapsed">
      <div @click="onProjectClick(projectUserTask)" class="task-project-title">
        项目名称：{{ getTaskProjectName(projectUserTask) }}
      </div>

      <div style="display: flex">
        <div class="task-details">
          项目地点：{{
            JSON.parse(projectUserTask.projectView.project.location)[0]
          }}-{{ JSON.parse(projectUserTask.projectView.project.location)[1] }}
        </div>
        <div class="task-details">
          建设单位：{{ projectUserTask.projectView.companyConstruction.name }}
        </div>
        <div class="task-details">
          设计单位：
          <span
            v-for="(company, companyIndex) in projectUserTask.projectView
              ?.companyDesignList"
            style="padding-right: 10px"
          >
            {{ company.name }}
          </span>
        </div>
        <div class="task-details">
          监理单位：
          <span v-if="projectUserTask.projectView.companySupervision != null">
            {{ projectUserTask.projectView.companySupervision?.name }}
          </span>
          <span v-else style="color: red">未指定</span>
        </div>
        <div class="task-details">
          总包单位：
          <span
            v-if="projectUserTask.projectView.companyGeneralContract != null"
          >
            {{ projectUserTask.projectView.companyGeneralContract?.name }}
          </span>
          <span v-else style="color: red">未指定</span>
        </div>
        <div class="task-details">
          项目起止时间：{{
            formatDate(projectUserTask.projectView.project.createDatetime)
          }}-{{
            projectUserTask.projectView.project.endDatetime == null
              ? "至今"
              : formatDate(projectUserTask.projectView.project.endDatetime)
          }}
        </div>
      </div>
      <el-button @click="showUsers">显示项目成员 </el-button>
      <div style="display: flex" >
        <el-collapse width="100%" v-if="isShow">
          <el-collapse-item title="设计单位" width="100%">
            <div v-for="user in projectAllUserPageViewData.projectUserViewListDesignCompany[0].projectUserViewList">
              {{ user.user.realName }}({{ user.user.tel }})
            </div>
          </el-collapse-item>
          <el-collapse-item title="设计部人员" width="100%">
            <div v-for="user in projectAllUserPageViewData.projectUserViewListDesignDepartment">
              {{ user.user.realName }}({{ user.user.tel }})
            </div>
          </el-collapse-item>
          <el-collapse-item title="工程部人员" width="100%">
            <div v-for="user in projectAllUserPageViewData.projectUserViewListEngineeringDepartment">
              {{ user.user.realName }}({{ user.user.tel }})
            </div>
          </el-collapse-item>
          <el-collapse-item title="监理单位人员" width="100%">
            <div v-for="user in projectAllUserPageViewData.projectUserViewListSupervisionCompany">
              {{ user.user.realName }}({{ user.user.tel }})
            </div>
          </el-collapse-item>
          <el-collapse-item title="总包单位人员" width="100%">
            <div v-for="user in projectAllUserPageViewData.projectUserViewListConstructionCompany">
              {{ user.user.realName }}({{ user.user.tel }})
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
    </div>
  </div>
  <div v-else>当前用户没有任务</div>
</template>

<style scoped>
@import url("@/assets/css/basic.css");
.task-info-container {
  padding: 10px;
  margin-bottom: 10px;
}
.task-details {
  font: 0.9em sans-serif;
  margin: 2px;
}

.task-project-title {
  cursor: pointer;
}
.task-project-title:hover {
  color: #409eff;
}
</style>
