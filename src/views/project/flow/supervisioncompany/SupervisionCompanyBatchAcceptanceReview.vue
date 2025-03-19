<script setup lang="ts">
/**
 * 文件说明：
 *监理单位对于总包单位提交的项目验收材料进行审核
 * 详见流程图
 *
 *
 */

import { computed, onMounted, reactive, ref, Ref } from "vue";

import { useRouter, useRoute, onBeforeRouteUpdate } from "vue-router";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";
import type { Action } from "element-plus";

import type {
  UploadInstance,
  UploadProps,
  UploadRawFile,
  UploadUserFile,
  UploadRequestOptions,
} from "element-plus";

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
  serverStartProcessByProjectId,
  serverGetTaskByCurrentLoginUser,
  serverDesignCompanySubmitProjectMaterial,
  serverGetTaskByCurrentLoginUserAndProjectId,
  serverGetTaskByCurrentLoginUserAndProjectIdAndTaskId,
  serverSubmitProjectMaterialReviewOfManagerDirect,
  serverSubmitProjectMaterialReviewManagerDistributeToEmployees,
  serverSubmitProjectMaterialReviewOfEmployee,
  serverSubmitProjectMaterialReviewOfManagerSummary,
  serverSubmitSupervisionCompanyReviewSelectBrand,
  serverSubmitSupervisionCompanyProjectMaterialBatchAcceptanceReview,
} from "@/server/project/projectmaterialflow";

import { IServerProjectUserTask } from "@/server/types/project/flow";

import { serverGetEmployeeUserOfDesignDepartment } from "@/server/project/projectuser";
import { IServerUser } from "@/server/types/account/user";

import {
  serverAddAppearanceReviewTempFiles,
  serverDeleteAppearanceReviewTempFiles,
} from "@/server/project/appearancereview";

import {
  IServerProjectReviewMode,
  IServerProjectReviewModeView,
  IServerProjectReview,
  IServerProjectReviewView,
  IServerProjectReviewUser,
  IServerProjectReviewUserView,
  IServerProjectReviewUserFile,
  IServerProjectReviewUserFileView,
  IServerProjectReviewForm,
  IServerProjectReviewDispatchForm,
  IServerProjectReviewEmployeeForm,
  IServerProjectAppearanceReviewUser,
  IServerAppearanceReviewForm,
  IServerProjectAppearanceReviewEmployeeForm,
  IServerProjectMaterialAcceptanceReviewEmployeeForm,
} from "@/server/types/project/review";

import ProjectUserTaskList from "@/components/project/flow/ProjectUserTaskList.vue";
import ProjectMaterialList from "@/components/project/material/ProjectMaterialList.vue";
import ProjectUserTaskInfo from "@/components/project/flow/ProjectUserTaskInfo.vue";
import ProjectMaterialAcceptanceList from "@/components/project/material/ProjectMaterialAcceptanceList.vue";
import EngineeringdepartmentProjectMaterialAcceptanceFeedback from "@/components/project/review/EngineeringdepartmentProjectMaterialAcceptanceFeedback.vue";

import ProjectReviewHistoryList from "@/components/project/flow/ProjectReviewHistoryList.vue";
import UseMaterialList from "@/components/project/material/UseMaterialList.vue";


import { genUUID } from "@/utils/utils";
import { getUserID } from "@/cookies/user";
import { getUserPageSize } from "../../../../cookies/user";

const router = useRouter();
const route = useRoute();

const projectUserTask: Ref<IServerProjectUserTask | undefined> = ref();
const projectId = ref("");
const taskId = ref("");
const radio = ref(0);

const checkList = ref([]);
const employeeList: Ref<IServerUser[]> = ref([]);

const fileList = ref<UploadUserFile[]>([]);
const upload = ref<UploadInstance>();
const fileListUploadNum = ref(0);

const form = reactive({
  radioReviewResult: 1,
  textareaReviewResult: "",
});

onBeforeRouteUpdate(async (to) => {
  if (typeof to.params.id === "string") {
    projectId.value = to.params.id;
  } else {
    projectId.value = to.params.id[0];
  }
  if (typeof to.params.taskId === "string") {
    taskId.value = to.params.taskId;
  } else {
    taskId.value = to.params.taskId[0];
  }

  await getUserTaskFromServerByProjectId(projectId.value, taskId.value);
});

onMounted(async () => {
  if (typeof route.params.id === "string") {
    projectId.value = route.params.id;
  }
  if (typeof route.params.taskId === "string") {
    taskId.value = route.params.taskId;
  }

  await getUserTaskFromServerByProjectId(projectId.value, taskId.value);
});

const getUserTaskFromServerByProjectId = async (
  projectId: string,
  taskId: string
) => {
  const ret = await serverGetTaskByCurrentLoginUserAndProjectIdAndTaskId(
    projectId,
    taskId
  );

  if (ret && ret.code == 200) {
    projectUserTask.value = ret.data;
  }
};

const goBack = () => {
  history.back();
};

const handleRadioReviewChange = async () => {};

/**
 * 项目经理分发审核，确定是分发给项目员工审核还是直接审核
 */
const submitProcess = async () => {
  console.log(fileList.value);

  const userId = getUserID();
  console.log(userId);
  if (!userId) {
    ElMessageBox.alert("用户信息异常，请重新登录", "提示", {
      confirmButtonText: "确定",
    });
    return;
  }

  //项目经理直接审核
  if (!form.textareaReviewResult) {
    ElMessageBox.alert("审核意见为空，请填写审核意见", "提示", {
      confirmButtonText: "确定",
    });
    return;
  }

  if (fileListUploadNum.value == 0) {
    ElMessageBox.confirm("附件为空，是否继续提交？", "提示", {
      distinguishCancelAndClose: true,
      confirmButtonText: "确定",
      cancelButtonText: "取消",
    })
      .then(async () => {
        //继续提交
        await submitToServerManagerDirect(userId);
      })
      .catch((action: Action) => {
        return;
      });
  } else await submitToServerManagerDirect(userId);
};

const submitToServerManagerDirect = async (userId: string) => {
  console.log(userId);

  //直接审核
  const acceptanceReviewForm: IServerProjectMaterialAcceptanceReviewEmployeeForm =
    {
      projectMaterialAcceptanceReviewUser: {
        id: "", //id,主键
        userId: userId, //t_user_id,外键,	t_user_id<-表t_user.id
        projectMaterialAcceptanceReviewId: "", //t_project_review_id,外键,	t_project_review_id<-表t_project_review.id
        reviewResult: form.radioReviewResult, //review_result,审核结果：通过，不通过审核结果：通过，不通过
        reviewContent: form.textareaReviewResult, //review_content,审核意见审核意见
        reviewDatetime: new Date(), //review_datetime,审核时间审核时间
        deletedAt: new Date(), //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
      },
      projectId: projectId.value,
      taskId: projectUserTask.value?.taskId ?? "",
      reviewTempDir: uploadReviewFilesDir.value,
    };

  console.log(acceptanceReviewForm);

  // 调用API
  const response =
    await serverSubmitSupervisionCompanyProjectMaterialBatchAcceptanceReview(
      acceptanceReviewForm
    );
  console.log(response);
  if (response && response.code === 200) {
    // Debug: 查看创建结果
    console.log(response.data);
    ElMessage.success("审核成功");
  } else {
    console.error("审核失败");
  }

  router.push("/project-user-task-list");
  //}
};
const cancelProcess = async () => {};

const handleUploadError = (err, file) => {
  console.error("上传发生错误:", err);
};

const handleRemove: UploadProps["onRemove"] = async (
  uploadFile,
  uploadFiles
) => {
  console.log(uploadFile, uploadFiles);

  const formData = new FormData();
  formData.append("projectId", projectId.value);
  formData.append("fileName", uploadFile.name);
  formData.append("uploadReviewFilesDir", uploadReviewFilesDir.value);

  const ret = await serverDeleteAppearanceReviewTempFiles(formData);
  if (ret && ret.code == 200 && ret.data) {
    ElMessage({
      type: "success",
      message: "删除成功",
    });
  } else ElMessage.success(`删除失败`);

  console.log(uploadFiles.length);
  fileListUploadNum.value = uploadFiles.length;
};

/**
 * 超过文件上传最大个数
 * @param files
 * @param uploadFiles
 */
const handleExceed: UploadProps["onExceed"] = (files, uploadFiles) => {
  ElMessage.warning(`最大上传文件个数为10个，已经超过最大值。`);
};

/**
 * 文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用
 * @param uploadFile
 * @param uploadFiles
 */
const handleUploadImageChange: UploadProps["onChange"] = (
  uploadFile,
  uploadFiles
) => {
  console.log(uploadFile, uploadFiles);

  console.log(uploadFiles.length);
  fileListUploadNum.value = uploadFiles.length;
};

/**
 * 上传文件之前的钩子，参数为上传的文件， 若返回false或者返回 Promise 且被 reject，则停止上传。
 * @param rawFile
 */
const beforeUpload = (rawFile: UploadRawFile) => {
  const extension = rawFile.name.substring(rawFile.name.lastIndexOf(".") + 1);
};

/**
 * 删除文件之前的钩子，参数为上传的文件和文件列表， 若返回 false 或者返回 Promise 且被 reject，则停止删除。
 * @param uploadFile
 * @param uploadFiles
 */
const beforeRemove: UploadProps["beforeRemove"] = (uploadFile, uploadFiles) => {
  return ElMessageBox.confirm(
    `Cancel the transfert of ${uploadFile.name} ?`
  ).then(
    () => true,
    () => false
  );
};

const uploadReviewFilesDir = ref(genUUID());

/**
 * 向服务器上传数据
 * @param options
 */
const httpRequest = async (options: UploadRequestOptions) => {
  console.log("options");
  const fileObj = options.file;

  const formData = new FormData();

  formData.append("projectId", projectId.value);
  formData.append("file", fileObj);
  formData.append("uploadReviewFilesDir", uploadReviewFilesDir.value);

  console.log(formData);

  const ret = await serverAddAppearanceReviewTempFiles(formData);
  if (ret && ret.code == 200 && ret.data) {
    ElMessage({
      type: "success",
      message: "增加成功",
    });
  } else ElMessage.error(`上传失败`);
};
</script>

<template>
  <!--显示项目列表、当前能够执行的操作（例如审核等）-->
  <div class="tab-container">
    <ProjectUserTaskInfo
      :projectUserTask="projectUserTask"
    ></ProjectUserTaskInfo>

    <div v-if="projectUserTask">
      <div class="review-container container">
        <el-form :model="form" label-width="auto" style="width: 100%">
          <el-form-item label="审核结果">
            <el-radio-group
              v-model="form.radioReviewResult"
              @change="handleRadioReviewChange"
            >
              <el-radio :value="1">审核通过</el-radio>
              <el-radio :value="2">审核不通过</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="审核意见">
            <el-input
              v-model="form.textareaReviewResult"
              style="width: 100%"
              :rows="2"
              type="textarea"
              placeholder="请填写审核意见"
            />
          </el-form-item>

          <el-form-item label="审核附件">
            <el-upload
              ref="upload"
              class="upload-demo"
              action=""
              :limit="10"
              accept=".doc,.docx,.pdf,.txt,.zip,.rar,.7z,.xls,.xlsx,.ppt,.pptx"
              :file-list="fileList"
              :on-exceed="handleExceed"
              :on-change="handleUploadImageChange"
              :on-remove="handleRemove"
              :before-upload="beforeUpload"
              :http-request="httpRequest"
            >
              <template #trigger>
                <el-button type="primary">选择文件</el-button>
              </template>

              <template #tip>
                <div class="el-upload__tip text-red">
                  请选择审核附件，文件格式要求为.doc、.docx、.pdf、.txt、.zip、.rar、.7z、.xls、.xlsx、.ppt、.pptx等。
                </div>
              </template>
            </el-upload>
          </el-form-item>
        </el-form>
        <el-alert title="通过后：需要工程部经理审核，不通过：总包单位重新提交" type="info" show-icon style="margin-top: 5px"/>
        <div style="margin: 10px; display: flex; justify-content: center">
          <el-button type="primary" @click="submitProcess">确定</el-button>
          <el-button type="primary" @click="cancelProcess">取消</el-button>
        </div>
      </div>
     <ProjectMaterialAcceptanceList
        :projectId="projectId"
        :taskId="taskId"
      ></ProjectMaterialAcceptanceList>
  
      <!--项目物料列表-->
      <ProjectMaterialList :projectId="projectId" />

      <!-- 审核记录-->
      <ProjectReviewHistoryList :projectId="projectId" />
    </div>
    <div v-else>当前项目没有任务</div>
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

.review-container {
  border: 1px solid #ccc;
  padding: 10px;
}
</style>
