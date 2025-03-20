<script setup lang="ts">
/**
 * 文件说明：
 *
 * 项目物料流程-设计部经理对设计单位提交的物料进行指派、分发审核。
 * 即设计部经理确定审核是由自己直接给定审核结果，还是先交由手下的项目员工审核后汇总
 * 详见流程图
 *
 *
 */

import {computed, onMounted, reactive, ref, Ref} from "vue";

import {useRouter, useRoute, onBeforeRouteUpdate} from "vue-router";

import type {FormInstance, FormRules} from "element-plus";
import {View, Hide, Search, Plus} from "@element-plus/icons-vue";

import {ElMessage, ElMessageBox} from "element-plus";
import type {Action} from "element-plus";

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
  IServerProjectUserView, IServerProjectMaterialView,
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
} from "@/server/project/projectmaterialflow";

import {IServerProjectUserTask} from "@/server/types/project/flow";

import {serverGetEmployeeUserOfDesignDepartment} from "@/server/project/projectuser";
import {IServerUser} from "@/server/types/account/user";

import {
  serverAddProjectMaterialReviewTempFiles,
  serverDeleteProjectMaterialReviewTempFiles,
} from "@/server/project/projectreview";

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
} from "@/server/types/project/review";

import ProjectUserTaskList from "@/components/project/flow/ProjectUserTaskList.vue";
import ProjectMaterialList from "@/components/project/material/ProjectMaterialList.vue";
import ProjectMaterialWaitingForReviewList from "@/components/project/material/ProjectMaterialWaitingForReviewList.vue";
import {genUUID} from "@/utils/utils";
import {getUserID, getUserPageSize} from "@/cookies/user";
import ProjectUserTaskInfo from "@/components/project/flow/ProjectUserTaskInfo.vue";

import ProjectReviewHistoryList from "@/components/project/flow/ProjectReviewHistoryList.vue";

import {
  getDesignCompanyIndex,
  getTaskName,
  getTaskProjectName,
  getTaskTitle,
} from "@/components/project/flow/index";
import {serverGetProjectMaterialPageViewByTaskIdAndProjectId} from "@/server/project/projectmaterial";
import {IServerPage} from "@/server/types/System";

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

const designCompanyIndex = ref(0);

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
  await handleRadioChange();

  if (
      !projectUserTask.value ||
      projectUserTask.value?.taskDefinitionKey?.indexOf(
          "Activity_Project_Material_Design_Department_Distribution_Review"
      ) < 0
  ) {
    router.push("/project-user-task-list");
  }
});

onMounted(async () => {
  if (typeof route.params.id === "string") {
    projectId.value = route.params.id;
  }
  if (typeof route.params.taskId === "string") {
    taskId.value = route.params.taskId;
  }

  await getUserTaskFromServerByProjectId(projectId.value, taskId.value);

  await handleRadioChange();

  if (
      !projectUserTask.value ||
      projectUserTask.value?.taskDefinitionKey?.indexOf(
          "Activity_Project_Material_Design_Department_Distribution_Review"
      ) < 0
  ) {
    router.push("/project-user-task-list");
  }
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

    if (projectUserTask && projectUserTask.value)
      designCompanyIndex.value = getDesignCompanyIndex(projectUserTask.value);
    console.log(projectUserTask.value);
  }
};

const goBack = () => {
  history.back();
};

/**
 * 监听radio的change事件
 */
const handleRadioChange = async () => {

  if (!projectId.value) {
    ElMessage({
      message: "当前项目为空",
      type: "error",
    });
  }

  if (radio.value == 0 && employeeList.value.length == 0) {
    //分发审核,显示项目员工名单
    const ret = await serverGetEmployeeUserOfDesignDepartment(projectId.value);
    if (ret && ret.code == 200) {
      employeeList.value = ret.data;
    }
  } else {
    //直接审核
  }
};

const handleRadioReviewChange = async () => {
};

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
  // companyid
//   const ret = await serverGetProjectMaterialPageViewByTaskIdAndProjectId(
//       projectId.value,
//       taskId.value,
//       designCompanyIndex.value,
//       pageNo.value,
//       pageSize.value
//   );
// //   console.log(ret);
//   if (ret && ret.code == 200) {
//     projectMaterialViewPageData.value = ret.data;
//   }
  await getcompanyId()

  if (radio.value === 0) {
    //分发审核，项目经理将项目材料交给项目员工进行初审，当项目员工审核完毕后，由项目经理汇总审核结果
    if (radio.value === 0 && checkList.value.length === 0) {
      ElMessageBox.alert("请选择审核人员", "提示", {
        confirmButtonText: "确定",
      });
      return;
    }
    await submitToServerManagerDistributeToEmployees(userId);
  } else {
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
  }
};

const submitToServerManagerDistributeToEmployees = async (userId: string) => {
  //分发审核
  const projectReviewDispatchForm: IServerProjectReviewDispatchForm = {
    companyId:companyId.value,
    projectId: projectId.value,
    taskId: taskId.value,
    designCompanyIndex: designCompanyIndex.value,
    projectReviewMode: {
      id: "", //id,主键
      projectId: projectId.value, //t_project_id,外键,	t_project_id<-表t_project.id
      userId: userId, //t_user_id,外键,	t_user_id<-表t_user.id,项目经理ID项目经理ID
      mode: 1, //mode,分发审核或直接审核分发审核或直接审核
      deletedAt: new Date(),
      createDatetime: new Date(),
      companyId:companyId.value
    },
    employeeIds: checkList.value,
  };

  // 调用API
  const response =
      await serverSubmitProjectMaterialReviewManagerDistributeToEmployees(
          projectReviewDispatchForm
      );
  console.log(response);
  if (response && response.code === 200) {
    // Debug: 查看创建结果
    console.log(response.data);
    ElMessage.success("分发成功");
  } else {
    console.error("分发失败");
  }

  router.push("/project-user-task-list");
};
const pageNo = ref(1); //第几页
const pageSize = ref(getUserPageSize()); //每页多少数据
const companyId = ref(null);
const projectMaterialViewPageData =
    ref<IServerPage<IServerProjectMaterialView> | null>(null);// 获取公司id

const getcompanyId = async () => {
  const ret = await serverGetProjectMaterialPageViewByTaskIdAndProjectId(
      projectId.value,
      taskId.value,
      designCompanyIndex.value,
     "",
      "",
      "",
      "",
     "",
      "",
     "",
      pageNo.value,
      pageSize.value
  );
//   console.log(ret);
  if (ret && ret.code == 200) {
    projectMaterialViewPageData.value = ret.data;
    // 从接口返回获取copanyid
    if (projectMaterialViewPageData.value.result.length  > 0) {
      companyId.value  = projectMaterialViewPageData.value.result[0].projectMaterial.companyId;
      console.log(' 获取到的companyId:', companyId.value);
    }
  }
}

const submitToServerManagerDirect = async (userId: string) => {
  //直接审核
  const projectReviewForm: IServerProjectReviewForm = {
    companyId:companyId.value,
    projectId: projectId.value,
    taskId: taskId.value,
    designCompanyIndex: designCompanyIndex.value,
    projectReviewMode: {
      id: "", //id,主键
      projectId: projectId.value, //t_project_id,外键,	t_project_id<-表t_project.id
      userId: userId, //t_user_id,外键,	t_user_id<-表t_user.id,项目经理ID项目经理ID
      mode: 1, //mode,分发审核或直接审核分发审核或直接审核
      deletedAt: new Date(),
      createDatetime: new Date(),
    },
    projectReview: {
      id: "", //id,主键
      projectId: projectId.value, //t_project_id,外键,	t_project_id<-表t_project.id
      projectReviewModeId: "", //t_project_review_mode_id,外键,
      reviewResult: form.radioReviewResult, //review_result,最终审核结果最终审核结果
      reviewStatus: 2, //review_status,审核状态：审核状态：审核状态：0：未分配审核方式。1：已经分配审核方式，正处于审核状态（t_project_review_mode增加一条记录）如果是直接审核，则t_project_review_user增加项目经理审核意见，提交后修改t_project_review中审核状态；如果是分发审核，则项目员工分别填写意见，项目经理汇总后提交。2.审核结束审核状态：审核状态：审核状态：0：未分配审核方式。1：已经分配审核方式，正处于审核状态（t_project_review_mode增加一条记录）如果是直接审核，则t_project_review_user增加项目经理审核意见，提交后修改t_project_review中审核状态；如果是分发审核，则项目员工分别填写意见，项目经理汇总后提交。2.审核结束
      reviewDatetime: new Date(), //review_datetime,审核时间审核时间
      deletedAt: new Date(), //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
    },
    projectReivewUser: {
      id: "", //id,主键
      userId: userId, //t_user_id,外键,	t_user_id<-表t_user.id
      projectReviewId: "", //t_project_review_id,外键,	t_project_review_id<-表t_project_review.id
      reviewResult: form.radioReviewResult, //review_result,审核结果：通过，不通过审核结果：通过，不通过
      reviewContent: form.textareaReviewResult, //review_content,审核意见审核意见
      reviewDatetime: new Date(), //review_datetime,审核时间审核时间
      deletedAt: new Date(), //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
    },
    reviewTempDir: uploadReviewFilesDir.value,
  };
console.log("projectReviewForm",projectReviewForm)
console.log("projectReviewForm.companyId",projectReviewForm.companyId)
  // 调用API
  const response = await serverSubmitProjectMaterialReviewOfManagerDirect(
      projectReviewForm
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
};


const cancelProcess = async () => {
};

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
  formData.append("uploadPhotoFilesDir", uploadReviewFilesDir.value);

  const ret = await serverDeleteProjectMaterialReviewTempFiles(formData);
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

  const ret = await serverAddProjectMaterialReviewTempFiles(formData);
  if (ret && ret.code == 200 && ret.data) {
    ElMessage({
      type: "success",
      message: "增加成功",
    });
  } else ElMessage.error(`上传失败`);
};

/**
 * 全部选择
 */
const selectAll = () => {
  checkList.value = [];
  employeeList.value.forEach((item) => {
    checkList.value.push(item.id);
  });
};

/**
 * 全部不选择
 */
const selectNone = () => {
  checkList.value = [];
};

/**
 * 反向选择
 */
const selectReverse = () => {
  var temp = checkList.value;
  checkList.value = [];
  employeeList.value.forEach((item) => {
    if (temp.indexOf(item.id) == -1) {
      checkList.value.push(item.id);
    }
  });
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
        <div style="margin: 10px">
          <el-radio-group v-model="radio" @change="handleRadioChange">
            <el-radio :value="0">分发审核</el-radio>
            <el-radio :value="1">直接审核</el-radio>
          </el-radio-group>
        </div>
        <div v-if="radio == 0">
          <el-button size="small" @click="selectAll">全部选择</el-button>
          <el-button size="small" @click="selectNone">全部不选择</el-button>

          <div v-for="(item, index) in employeeList">
            <el-checkbox-group v-model="checkList">
              <el-checkbox
                  :label="item.realName + '  (' + item.userName + ')'"
                  :value="item.id"
              />
            </el-checkbox-group>
          </div>
        </div>
        <div v-else class="review-container">
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
        </div>
        <el-alert title="通过后请联系工程部项目经理分发施工，监理。不通过请联系设计单位重新提交项目材料参数" type="info" show-icon style="margin-top: 5px"/>
        <div style="margin: 10px; display: flex; justify-content: center">
          <el-button type="primary" @click="submitProcess">确定</el-button>
          <el-button type="primary" @click="cancelProcess">取消</el-button>
        </div>
      </div>

      <!--等待审核的项目物料列表-->
      <ProjectMaterialWaitingForReviewList
          :projectId="projectId"
          :taskId="taskId"
          :designCompanyIndex="designCompanyIndex"
      />

      <!--项目物料列表-->
      <ProjectMaterialList :projectId="projectId"/>

      <!-- 审核记录-->
      <ProjectReviewHistoryList :projectId="projectId"/>
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
