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
  serverGetTaskByCurrentLoginUser,
} from "@/server/project/projectmaterialflow";

import { serverGetCurrentLoginUserIsGeneralContractorCompanyEmployee } from "@/server/system/companyuser";

import { serverGetCurrentLoginUserIsInDesignOrEngineeringDepartment } from "@/server/project/projectuser";

import { IServerProjectUserTask } from "@/server/types/project/flow";
import { getTaskName, getTaskProjectName, getTaskTitle } from "./index";
const router = useRouter();

const projectUserTaskList: Ref<IServerProjectUserTask[]> = ref([]);
const canNewProject = ref(false);
const isGeneralContractorCompanyEmployee = ref(false);

interface IProjectUserTask {
  project: IServerProject;
  projectUserTaskList: IServerProjectUserTask[];
}

const projectUserTaskListSorted: Ref<IProjectUserTask[]> = ref([]);

onMounted(async () => {
  const ret = await serverGetTaskByCurrentLoginUser();
  if (ret && ret.code == 200) {
    projectUserTaskList.value = ret.data;
  }

  console.log(projectUserTaskList.value);

  if (projectUserTaskList.value && projectUserTaskList.value.length > 0) {
    for (let i = 0; i < projectUserTaskList.value.length; i++) {
      if (
        projectUserTaskList.value[i].designDepartmentEmployee ||
        projectUserTaskList.value[i].designDepartmentManager ||
        projectUserTaskList.value[i].engineeringDepartmentEmployee ||
        projectUserTaskList.value[i].engineeringDepartmentManager
      ) {
        canNewProject.value = true;
      }

      if (projectUserTaskList.value[i].generalContractorEmployee) {
        isGeneralContractorCompanyEmployee.value = true;
      }
    }

    projectUserTaskListSorted.value = [];
    projectUserTaskList.value.forEach((projectUserTask) => {
      let project = projectUserTask.projectView.project;

      var found = projectUserTaskListSorted.value.find(
        (item) => item.project.id === project.id
      );
      if (!found) {
        projectUserTaskListSorted.value.push({
          project: project,
          projectUserTaskList: [projectUserTask],
        });
      } else {
        found.projectUserTaskList.push(projectUserTask);
      }
    });
  } else {
    const ret =
      await serverGetCurrentLoginUserIsInDesignOrEngineeringDepartment();
    if (ret && ret.data) canNewProject.value = ret.data;

    if (!canNewProject.value) {
      const ret =
        await serverGetCurrentLoginUserIsGeneralContractorCompanyEmployee();
      if (ret && ret.data) isGeneralContractorCompanyEmployee.value = ret.data;
    }
  }
});

const goBack = () => {
  history.back();
};

const onProjectUserTaskClick = async (
  projectUserTask: IServerProjectUserTask
) => {
  switch (projectUserTask.taskDefinitionKey) {
    case "Activity_Construction_Company_Create_Project":
      //建设单位创建项目，启动流程
      console.log("建设单位创建项目，启动流程");
      break;
    case "Activity_Admin_Set_Design_Company_And_Design_Department_And_Engineering_Department_Employees":
      //管理人员设置设计、工程部员工阶段
      console.log("管理人员设置设计、工程部员工阶段");
      router.push({
        path: `/project-user-task/${projectUserTask.projectView.project.id}/admin-set-design-and-engineering-employee`,
      });
      break;
    case "Activity_Design_Company_Submit_Project_Material_0":
    case "Activity_Design_Company_Submit_Project_Material_1":
    case "Activity_Design_Company_Submit_Project_Material_2":
    case "Activity_Design_Company_Submit_Project_Material_3":
    case "Activity_Design_Company_Submit_Project_Material_4":
    case "Activity_Design_Company_Submit_Project_Material_5":
    case "Activity_Design_Company_Submit_Project_Material_6":
    case "Activity_Design_Company_Submit_Project_Material_7":
      //设计单位项目材料填报阶段
      console.log("设计单位项目材料填报阶段");
      router.push({
        path: `/project-user-task/${projectUserTask.projectView.project.id}/design-company-fill-out-project-material/${projectUserTask.taskId}`,
      });
      break;
    case "Activity_Project_Material_Design_Department_Distribution_Review_0":
    case "Activity_Project_Material_Design_Department_Distribution_Review_1":
    case "Activity_Project_Material_Design_Department_Distribution_Review_2":
    case "Activity_Project_Material_Design_Department_Distribution_Review_3":
    case "Activity_Project_Material_Design_Department_Distribution_Review_4":
    case "Activity_Project_Material_Design_Department_Distribution_Review_5":
    case "Activity_Project_Material_Design_Department_Distribution_Review_6":
    case "Activity_Project_Material_Design_Department_Distribution_Review_7":
      //设计部经理分派任务
      router.push({
        path: `/project-user-task/${projectUserTask.projectView.project.id}/design-department-manager-dispatch/${projectUserTask.taskId}`,
      });
      break;
    case "Activity_Project_Material_Design_Department_Manager_And_Employee_Reivew_0":
    case "Activity_Project_Material_Design_Department_Manager_And_Employee_Reivew_1":
    case "Activity_Project_Material_Design_Department_Manager_And_Employee_Reivew_2":
    case "Activity_Project_Material_Design_Department_Manager_And_Employee_Reivew_3":
    case "Activity_Project_Material_Design_Department_Manager_And_Employee_Reivew_4":
    case "Activity_Project_Material_Design_Department_Manager_And_Employee_Reivew_5":
    case "Activity_Project_Material_Design_Department_Manager_And_Employee_Reivew_6":
    case "Activity_Project_Material_Design_Department_Manager_And_Employee_Reivew_7":
      //设计部所有员工填写审核意见，根据员工类型确定下一步
      if (projectUserTask.designDepartmentManager)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/design-department-manager-review/${projectUserTask.taskId}`,
        });
      else if (projectUserTask.designDepartmentEmployee)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/design-department-employee-review/${projectUserTask.taskId}`,
        });
      break;
    case "Activity_Project_Material_Design_Department_Remain_Employee_Reivew_0":
    case "Activity_Project_Material_Design_Department_Remain_Employee_Reivew_1":
    case "Activity_Project_Material_Design_Department_Remain_Employee_Reivew_2":
    case "Activity_Project_Material_Design_Department_Remain_Employee_Reivew_3":
    case "Activity_Project_Material_Design_Department_Remain_Employee_Reivew_4":
    case "Activity_Project_Material_Design_Department_Remain_Employee_Reivew_5":
    case "Activity_Project_Material_Design_Department_Remain_Employee_Reivew_6":
    case "Activity_Project_Material_Design_Department_Remain_Employee_Reivew_7":
      //如果是设计部经理，总结员工审核意见；如果是项目员工，则填写审核意见
      if (projectUserTask.designDepartmentManager)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/design-department-manager-summary-review/${projectUserTask.taskId}`,
        });
      else if (projectUserTask.designDepartmentEmployee)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/design-department-employee-review/${projectUserTask.taskId}`,
        });
      break;
    case "Activity_Engineering_Department_Manager_Dispatch":
      //工程部项目经理分发施工、监理
      router.push({
        path: `/project-user-task/${projectUserTask.projectView.project.id}/engineering-department-manager-dispatch`,
      });
      break;
    case "Activity_Admin_Set_SuperVision_And_General_Contractor_Employees":
      //管理人员设置监理、总包单位员工阶段
      router.push({
        path: `/project-user-task/${projectUserTask.projectView.project.id}/admin-set-supervision-and-general-contractor-employee`,
      });
      break;
    case "Activity_General_Contractor_Company_Select_Brand":
      //总包单位选择品牌（在备选品牌中选择 或使用新品牌并提供说明），选择物料
      router.push({
        path: `/project-user-task/${projectUserTask.projectView.project.id}/general_contractor-company-brand-selection/${projectUserTask.taskId}`,
      });
      break;
    case "Activity_Supervision_Company_Review_Select_Brand":
      //监理对总包单位的品牌和物料等进行审核
      router.push({
        path: `/project-user-task/${projectUserTask.projectView.project.id}/supervision-company-review-brand-selection/${projectUserTask.taskId}`,
      });
      break;

    case "Activity_Affect_Appearance_Engineering_Department_Distribution_Review":
      //工程部对于影响外观（总包单位的）品牌和物料）进行分派任务
      router.push({
        path: `/project-user-task/${projectUserTask.projectView.project.id}/engineering-department-appearance-manager-dispatch/${projectUserTask.taskId}`,
      });
      break;
    case "Activity_Affect_Appearance_Engineering_Department_Manager_And_Employee_Reivew":
      //工程部所有员工填写审核意见，根据员工类型确定下一步
      if (projectUserTask.engineeringDepartmentManager)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/engineering-department-appearance-manager-review/${projectUserTask.taskId}`,
        });
      else if (projectUserTask.engineeringDepartmentEmployee)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/engineering-department-appearance-employee-review/${projectUserTask.taskId}`,
        });
      break;
    case "Activity_Affect_Appearance_Engineering_Department_Remain_Employee_Reivew":
      //如果是设计部经理，总结员工审核意见；如果是项目员工，则填写审核意见
      if (projectUserTask.engineeringDepartmentManager)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/engineering-department-appearance-manager-summary-review/${projectUserTask.taskId}`,
        });
      else if (projectUserTask.engineeringDepartmentEmployee)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/engineering-department-appearance-employee-review/${projectUserTask.taskId}`,
        });
      break;
    case "Activity_Affect_Appearance_Design_Company_Reivew_0":
    case "Activity_Affect_Appearance_Design_Company_Reivew_1":
    case "Activity_Affect_Appearance_Design_Company_Reivew_2":
    case "Activity_Affect_Appearance_Design_Company_Reivew_3":
    case "Activity_Affect_Appearance_Design_Company_Reivew_4":
    case "Activity_Affect_Appearance_Design_Company_Reivew_5":
    case "Activity_Affect_Appearance_Design_Company_Reivew_6":
    case "Activity_Affect_Appearance_Design_Company_Reivew_7":
      //设计单位对于影响外观（总包单位的）品牌和物料）进行审核
      router.push({
        path: `/project-user-task/${projectUserTask.projectView.project.id}/design-company-appearance-review/${projectUserTask.taskId}`,
      });
      break;

    case "Activity_Affect_Appearance_Design_Department_Distribution_Review":
      //设计部对于影响外观（总包单位的）品牌和物料）进行分派任务
      router.push({
        path: `/project-user-task/${projectUserTask.projectView.project.id}/design-department-appearance-manager-dispatch/${projectUserTask.taskId}`,
      });
      break;
    case "Activity_Affect_Appearance_Design_Department_Manager_And_Employee_Reivew":
      //设计部所有员工填写审核意见，根据员工类型确定下一步
      if (projectUserTask.designDepartmentManager)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/design-department-appearance-manager-review/${projectUserTask.taskId}`,
        });
      else if (projectUserTask.designDepartmentEmployee)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/design-department-appearance-employee-review/${projectUserTask.taskId}`,
        });
      break;

    case "Activity_Affect_Appearance_Design_Department_Remain_Employee_Reivew":
      //如果是设计部经理，总结员工审核意见；如果是项目员工，则填写审核意见
      if (projectUserTask.designDepartmentManager)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/design-department-appearance-manager-summary-review/${projectUserTask.taskId}`,
        });
      else if (projectUserTask.designDepartmentEmployee)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/design-department-appearance-employee-review/${projectUserTask.taskId}`,
        });
      break;

    case "Activity_Not_Affect_Appearance_Engineering_Department_Distribution_Review":
      //工程部对于影响外观（总包单位的）品牌和物料）进行分派任务
      router.push({
        path: `/project-user-task/${projectUserTask.projectView.project.id}/engineering-department-not-affect-appearance-manager-dispatch/${projectUserTask.taskId}`,
      });
      break;
    case "Activity_Not_Affect_Appearance_Engineering_Department_Manager_And_Employee_Reivew":
      //工程部所有员工填写审核意见，根据员工类型确定下一步
      if (projectUserTask.engineeringDepartmentManager)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/engineering-department-not-affect-appearance-manager-review/${projectUserTask.taskId}`,
        });
      else if (projectUserTask.engineeringDepartmentEmployee)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/engineering-department-not-affect-appearance-employee-review/${projectUserTask.taskId}`,
        });
      break;
    case "Activity_Not_Affect_Appearance_Engineering_Department_Remain_Employee_Reivew":
      //如果是设计部经理，总结员工审核意见；如果是项目员工，则填写审核意见
      if (projectUserTask.engineeringDepartmentManager)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/engineering-department-not-affect-appearance-manager-summary-review/${projectUserTask.taskId}`,
        });
      else if (projectUserTask.engineeringDepartmentEmployee)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/engineering-department-not-affect-appearance-employee-review/${projectUserTask.taskId}`,
        });
      break;

    case "Activity_General_Contractor_Order":
      //总包单位订购
      router.push({
        path: `/project-user-task/${projectUserTask.projectView.project.id}/general-contractor-buy-material/${projectUserTask.taskId}`,
      });
      break;
    case "Activity_General_Contractor_Submit_Documentation":
      //总包单位填写工程材料、设备报验材料
      router.push({
        path: `/project-user-task/${projectUserTask.projectView.project.id}/general-contractor-buy-material-document/${projectUserTask.taskId}`,
      });
      break;
    case "Activity_Supervision_Company_Decide_Whether_ReCheck_Is_Required":
      //监理判断是否需要复试
      router.push({
        path: `/project-user-task/${projectUserTask.projectView.project.id}/supervision-company-decide-whether-to-recheck/${projectUserTask.taskId}`,
      });
      break;
    case "Activity_General_Contractor_Batch_Acceptance":
      //总包单位新建项目材料批次验收
      router.push({
        path: `/project-user-task/${projectUserTask.projectView.project.id}/general-contractor-batch-acceptance/${projectUserTask.taskId}`,
      });
      break;
    case "Activity_Supervision_Company_Perform_The_Acceptance_Procedure_For_The_Batch_Of_Materials":
      //监理项目对项目材料批次验收进行审核
      router.push({
        path: `/project-user-task/${projectUserTask.projectView.project.id}/supervision-company-batch-acceptance/${projectUserTask.taskId}`,
      });
      break;
    case "Activity_Engineering_Department_Project_Material_Acceptance_Distribution_Review":
      //工程部项目经理对于项目材料批次验收审核进行分派任务
      router.push({
        path: `/project-user-task/${projectUserTask.projectView.project.id}/engineering-department-batch-acceptance-manager-dispatch/${projectUserTask.taskId}`,
      });
      break;
    case "Activity_Engineering_Department_Project_Material_Acceptance_Manager_And_Employee_Reivew":
      //工程部所有员工填写审核意见，根据员工类型确定下一步
      if (projectUserTask.engineeringDepartmentManager)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/engineering-department-batch-acceptance-manager-review/${projectUserTask.taskId}`,
        });
      else if (projectUserTask.engineeringDepartmentEmployee)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/engineering-department-batch-acceptance-employee-review/${projectUserTask.taskId}`,
        });
      break;
    case "Activity_Engineering_Department_Project_Material_Acceptance_Remain_Employee_Reivew":
      //如果是工程部经理，总结员工审核意见；如果是项目员工，则填写审核意见
      if (projectUserTask.engineeringDepartmentManager)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/engineering-department-batch-acceptance-manager-summary-review/${projectUserTask.taskId}`,
        });
      else if (projectUserTask.engineeringDepartmentEmployee)
        router.push({
          path: `/project-user-task/${projectUserTask.projectView.project.id}/engineering-department-batch-acceptance-employee-review/${projectUserTask.taskId}`,
        });
      break;
    case "Activity_Engineering_Department_Manager_Determine_Whether_The_Project_Is_Completion":
      //工程部项目经理决定是否完成项目
      router.push({
        path: `/project-user-task/${projectUserTask.projectView.project.id}/engineering-department-manager-close-project/${projectUserTask.taskId}`,
      });
      break;
  }
};
const browseProject = async () => {
  router.push({
    path: `/project-list`,
  });
};
const onProjectClick = (project: IServerProject) => {
  router.push({
    path: `/project-details/${project.id}`,
  });
};

/**
 * 监理单位未结项的项目列表
 */
const supervisionCompanyNotEndProjectPage = async () => {
  router.push({
    path: `/supervision-company-not-ended-project-list`,
  });
};

/**
 * 总包单位未结项的项目列表
 */
const generalContractorNotEndProjectPage = async () => {
  router.push({
    path: `/general-contractor-not-ended-project-list`,
  });
};
</script>

<template>
  <!--如果是建设单位，则可以创建项目-->
  <div class="top-toolbar">
    <el-button type="primary" plain @click="browseProject">浏览项目</el-button>

    <!--只有设计部和工程部人员才能够创建项目-->
    <el-button type="primary" plain @click="browseProject" v-if="canNewProject"
      >新建项目</el-button
    >
    <!--只有总包单位人员才能够（1）品牌选择与物料使用申请；（2）物料订购；（3）发起物料验收-->
    <el-button
      type="primary"
      plain
      @click="generalContractorNotEndProjectPage"
      v-if="isGeneralContractorCompanyEmployee"
      >品牌选择与物料申请</el-button
    >
    <el-button
      type="primary"
      plain
      @click="generalContractorNotEndProjectPage"
      v-if="isGeneralContractorCompanyEmployee"
      >物料订购</el-button
    >
    <el-button
      type="primary"
      plain
      @click="generalContractorNotEndProjectPage"
      v-if="isGeneralContractorCompanyEmployee"
      >物料验收</el-button
    >
  </div>
  <!--显示项目列表、当前能够执行的操作（例如审核等）-->
  <div
    class="tab-container"
    v-if="projectUserTaskList && projectUserTaskList.length > 0"
  >
    <div
      v-for="(
        projectUserTaskListSortedItem, index
      ) in projectUserTaskListSorted"
      :key="index"
    >
      <el-card class="box-card">
        <!--项目名称-->
        <template #header>
          <span
            class="card-title"
            @click="onProjectClick(projectUserTaskListSortedItem.project)"
            >{{
              projectUserTaskListSortedItem.project != null
                ? projectUserTaskListSortedItem.project.name
                : ""
            }}<span style="color: red">的任务</span></span
          >
        </template>
        <div class="card-content">
          <div
            class="card-content-item"
            v-for="(
              task, taskIndex
            ) in projectUserTaskListSortedItem.projectUserTaskList"
          >
            <div class="card-content-item-title">({{ taskIndex + 1 }})</div>
            <div
              class="card-content-item-content"
              @click="onProjectUserTaskClick(task)"
            >
              {{ getTaskName(task) }}
            </div>
          </div>
        </div>
      </el-card>
    </div>
  </div>
  <div v-else>当前用户没有任务</div>
</template>

<style scoped>
@import url("@/assets/css/basic.css");

.top-toolbar {
  display: flex;

  padding: 10px;
}

.box-card {
  margin-top: 10px;
}

.card-title {
  font: 1.2em sans-serif;
  cursor: pointer;
}

.card-content {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 10px;
  cursor: pointer;
}

.card-content-item {
  display: flex;
  font: 1em sans-serif;
  justify-content: flex-start;
  align-items: center;
  justify-items: baseline;
}

.card-content-item-title {
  width: 30px;
}

.card-content-item-content {
  cursor: pointer;
}
.card-content-item-content:hover {
  color: #409eff;
}
</style>
