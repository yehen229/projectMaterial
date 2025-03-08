<script setup lang="ts">
/**
 * 文件说明：
 *
 * 监理和总包单位判断是否需要复试
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
import { IServerResponseData, IServerPage } from "@/server/types/System";
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
  serverSubmitAffectAppearanceReviewOfEngineeringDepartmentManagerDispatchToEmployee,
  serverSubmitAffectAppearanceReviewOfEngineeringDepartmentManagerDirect,
  serverSubmitSupervisionCompanyDecideWhetherToRecheck,
  serverGetBuyMaterialRecheckIsRequiredByProjectIdAndTaskId,
} from "@/server/project/projectmaterialflow";

import { IServerProjectUserTask } from "@/server/types/project/flow";

import { serverGetEmployeeUserOfEngineeringDepartment } from "@/server/project/projectuser";
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
  IServerProjectCompany,
  IServerUseMaterialForm,
  IServerUseMaterialFormItem,
  IServerBuyMaterialForm,
  IServerProjectMaterialVerificationDocumentView,
  IServerProjectMaterialVerificationDocumentFile,
  IServerProjectMaterialVerificationDocument,
  IServerProjectMaterialRetest,
  IServerProjectMaterialRetestForm,
} from "@/server/types/project/review";

import { serverGetBuyMaterialRecheckIsRequiredByProjectId } from "@/server/project/usematerial";

import ProjectUserTaskList from "@/components/project/flow/ProjectUserTaskList.vue";
import ProjectMaterialList from "@/components/project/material/ProjectMaterialList.vue";
import UseMaterialList from "@/components/project/material/UseMaterialList.vue";

import { genUUID } from "@/utils/utils";
import { getUserID, getUserPageSize } from "@/cookies/user";
import ProjectUserTaskInfo from "@/components/project/flow/ProjectUserTaskInfo.vue";

import ProjectReviewHistoryList from "@/components/project/flow/ProjectReviewHistoryList.vue";
import BuyMaterialDocument from "@/components/project/material/BuyMaterialDocument.vue";

import {
  getTaskName,
  getTaskProjectName,
  getTaskTitle,
} from "@/components/project/flow/index";
const router = useRouter();
const route = useRoute();

const projectUserTask: Ref<IServerProjectUserTask | undefined> = ref();
const projectId = ref("");
const taskId = ref("");
const radio = ref(0);

const checkList = ref<string[]>([]);
const employeeList: Ref<IServerUser[]> = ref([]);

const fileList = ref<UploadUserFile[]>([]);
const upload = ref<UploadInstance>();
const fileListUploadNum = ref(0);

// 页码和页大小
const pageNo = ref(1);
const pageSize = ref(getUserPageSize());

const buyMaterialViewList =
  ref<IServerProjectMaterialVerificationDocumentView[]>();

const buyMaterialViewListRetest = ref<number[]>([]);

const form = reactive({
  radioReviewResult: 1,
  textareaReviewResult: "",
});

const loading = ref(false);

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
    projectUserTask.value?.taskDefinitionKey !==
    "Activity_Supervision_Company_Decide_Whether_ReCheck_Is_Required"
  )
    router.push("/project-user-task-list");

  await getBuyMaterialVerificationDocumentPageViewFromServer(
    projectId.value,
    taskId.value
  );
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
    projectUserTask.value?.taskDefinitionKey !==
    "Activity_Supervision_Company_Decide_Whether_ReCheck_Is_Required"
  )
    router.push("/project-user-task-list");

  await getBuyMaterialVerificationDocumentPageViewFromServer(
    projectId.value,
    taskId.value
  );
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

const getBuyMaterialVerificationDocumentPageViewFromServer = async (
  projectId: string,
  taskId: string
) => {
  const ret = await serverGetBuyMaterialRecheckIsRequiredByProjectIdAndTaskId(
    projectId,
    taskId
  );
  if (ret && ret.code == 200) {
    buyMaterialViewList.value = ret.data;
    console.log(ret.data);

    buyMaterialViewListRetest.value = [];
    for (let i = 0; i < buyMaterialViewList.value.length; i++) {
      buyMaterialViewListRetest.value.push(1);
    }
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
};

/**
 * 监听radio的change事件,用户点击了‘审核结果’
 */
const handleRadioReviewChange = async () => {
  if (form.radioReviewResult == 1) {
    //审核不通过，即部分通古、部分不通过
    if (buyMaterialViewList.value) {
      var count = 0;
      for (let i = 0; i < buyMaterialViewList.value.length; i++) {
        //0:不需要复检，1:需要复检
        buyMaterialViewListRetest.value[i] = 1;
      }
    }
  }
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

  if (radio.value === 0) {
    //不需要复检
    await submitToServerNotNeedRecheck(userId);
  } else {
    //需要复检
    //需要复检，一种是全体通过，一种是部分通过
    if (form.radioReviewResult == 2) {
      //审核不通过，即部分通古、部分不通过
      if (buyMaterialViewList.value) {
        var count = 0;
        for (let i = 0; i < buyMaterialViewList.value.length; i++) {
          //0:不需要复检，1:需要复检
          count += buyMaterialViewListRetest.value[i];
        }

        if (count == 0) {
          ElMessageBox.alert(
            "选择了“需要复检、审核不通过”，但未选择复检项目",
            "提示",
            {
              confirmButtonText: "确定",
            }
          );
          return;
        }
      }
    } else {
      if (buyMaterialViewList.value) {
        var count = 0;
        for (let i = 0; i < buyMaterialViewList.value.length; i++) {
          //0:不需要复检，1:需要复检
          buyMaterialViewListRetest.value[i] = 0;
        }
      }
    }

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

const submitToServerNotNeedRecheck = async (userId: string) => {
  if (!buyMaterialViewList.value || buyMaterialViewList.value.length === 0) {
    ElMessageBox.alert("附件材料为空", "提示", {
      confirmButtonText: "确定",
    });

    return;
  }

  var projectMaterialRetestList: IServerProjectMaterialRetest[] = [];
  for (let i = 0; i < buyMaterialViewList.value.length; i++) {
    const element = buyMaterialViewList.value[i];
    var projectMaterialRetest: IServerProjectMaterialRetest = {
      id: "", //id,主键
      buyMaterialId:
        buyMaterialViewList.value[0].buyMaterialView.buyMaterial.id, //t_project_id,外键,	t_project_id<-表t_project.id
      projectMaterialRetestBatchId: "",
      userId: userId, //t_user_id,外键,	t_user_id<-表t_user.id,项目经理ID项目经理ID
      needRetest: 0, //是否需要复检，0不需要复检，1需要复检
      reviewResult: 1, //审核结果，0未审核；1审核通过；2.审核不通过
      reviewContent: form.textareaReviewResult,
      reviewDatetime: new Date(),
      deletedAt: new Date(),
    };
    projectMaterialRetestList.push(projectMaterialRetest);
  }

  //不需要复检
  const projectMaterialRetestForm: IServerProjectMaterialRetestForm = {
    projectId: projectId.value,
    projectMaterialRetestList: projectMaterialRetestList,
    taskId: taskId.value,
    reviewTempDir: "",
  };

  // 调用API
  const response = await serverSubmitSupervisionCompanyDecideWhetherToRecheck(
    projectMaterialRetestForm
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

/**
 * 项目经理直接填写审核意见，不经过分发审核
 * @param userId 当前登录用户ID
 */
const submitToServerManagerDirect = async (userId: string) => {
  if (!buyMaterialViewList.value || buyMaterialViewList.value.length === 0) {
    ElMessageBox.alert("附件材料为空", "提示", {
      confirmButtonText: "确定",
    });

    return;
  }

  var projectMaterialRetestList: IServerProjectMaterialRetest[] = [];
  for (let i = 0; i < buyMaterialViewList.value.length; i++) {
    const element = buyMaterialViewList.value[i];
    var projectMaterialRetest: IServerProjectMaterialRetest = {
      id: "", //id,主键
      buyMaterialId:
        buyMaterialViewList.value[0].buyMaterialView.buyMaterial.id, //t_project_id,外键,	t_project_id<-表t_project.id
      projectMaterialRetestBatchId: "",
      userId: userId, //t_user_id,外键,	t_user_id<-表t_user.id,项目经理ID项目经理ID
      needRetest: 1, //是否需要复检，0不需要复检，1需要复检
      reviewResult: buyMaterialViewListRetest.value[i], //review_result,审核结果，0未审核；1审核通过；2.审核不通过
      reviewContent: form.textareaReviewResult,
      reviewDatetime: new Date(),
      deletedAt: new Date(),
    };
    projectMaterialRetestList.push(projectMaterialRetest);
  }

  //直接审核
  const projectMaterialRetestForm: IServerProjectMaterialRetestForm = {
    projectId: projectId.value,
    projectMaterialRetestList: projectMaterialRetestList,
    taskId: taskId.value,
    reviewTempDir: uploadReviewFilesDir.value,
  };

  console.log(projectMaterialRetestForm);

  // 调用API
  const response = await serverSubmitSupervisionCompanyDecideWhetherToRecheck(
    projectMaterialRetestForm
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
const textElipsisValue = ref(false);
</script>

<template>
  <!--显示项目列表、当前能够执行的操作（例如审核等）-->
  <div class="tab-container">
    <ProjectUserTaskInfo
      :projectUserTask="projectUserTask"
    ></ProjectUserTaskInfo>

    <div v-if="projectUserTask">
      <div class="review-container container">
        <div class="top-toolbar">
          <div style="margin-left: auto">
            <el-switch
              v-model="textElipsisValue"
              inline-prompt
              style="
                --el-switch-on-color: #13ce66;
                --el-switch-off-color: #ff4949;
              "
              active-text="自动调整高度"
              inactive-text="显示全部内容"
            />
          </div>
        </div>

        <el-row
          style="
            width: 100%;
            font: 1em sans-serif;
            border: 1px solid #eee;
            border-bottom-style: none;
            padding: 5px;
            margin: 5px;
            margin-left: 0px;
            white-space: pre-wrap;
            line-height: 1.5;
            margin-top: 10px;
            margin-bottom: -5px;
          "
        >
          <el-col :span="2">材料名称 </el-col>
          <el-col :span="2"> 材料位置</el-col>
          <el-col :span="2">编号 </el-col>
          <el-col :span="2">材料数量 </el-col>
          <el-col :span="2"> 数量单位</el-col>
          <el-col :span="2"> 品牌</el-col>
          <el-col :span="2"> 批次 </el-col>
          <el-col :span="4"> 工程材料 </el-col>
          <el-col :span="4"> 设备报验材料 </el-col>
          <el-col :span="2" v-if="form.radioReviewResult == 2">
            复检是否通过
          </el-col>
        </el-row>

        <el-row
          v-for="(
            projectMaterialVerificationDocumentViewItem,
            projectMaterialVerificationDocumentViewIndex
          ) in buyMaterialViewList"
          style="
            width: 100%;
            font: 0.8em sans-serif;
            border: 1px solid #eee;
            padding: 5px;
            margin: 5px;
            margin-left: 0px;
            white-space: pre-wrap;
            line-height: 1.5;
            color: #606266;
          "
          v-loading="loading"
          :gutter="20"
        >
          <!--材料名称-->
          <el-col :span="2">
            <div style="display: flex; align-items: center">
              <div :class="{ textEllipsis: textElipsisValue }">
                {{
                  projectMaterialVerificationDocumentViewItem.buyMaterialView
                    .useMaterialView.projectMaterialView.material.name
                }}
              </div>
            </div>
          </el-col>

          <!--材料位置-->
          <el-col :span="2">
            <div style="display: flex; align-items: center">
              <div :class="{ textEllipsis: textElipsisValue }">
                {{
                  projectMaterialVerificationDocumentViewItem.buyMaterialView
                    .useMaterialView.projectMaterialView.material.location
                }}
              </div>
            </div>
          </el-col>

          <!--编号-->
          <el-col :span="2">
            <div style="display: flex; align-items: center">
              <div :class="{ textEllipsis: textElipsisValue }">
                {{
                  projectMaterialVerificationDocumentViewItem.buyMaterialView
                    .useMaterialView.projectMaterialView.material.itemMark
                }}
              </div>
            </div>
          </el-col>

          <!--材料数量-->
          <el-col :span="2">
            <div style="display: flex; align-items: center">
              <div :class="{ textEllipsis: textElipsisValue }">
                {{
                  projectMaterialVerificationDocumentViewItem.buyMaterialView
                    .buyMaterial.materialCount
                }}
              </div>
            </div>
          </el-col>

          <!--数量单位-->
          <el-col :span="2">
            <div style="display: flex; align-items: center">
              <div :class="{ textEllipsis: textElipsisValue }">
                {{
                  projectMaterialVerificationDocumentViewItem.buyMaterialView
                    .buyMaterial.materialUnit
                }}
              </div>
            </div>
          </el-col>

          <!--品牌-->
          <el-col :span="2">
            <div style="display: flex; flex-wrap: wrap; align-items: center">
              <!--项目物料品牌-->
              <span
                v-if="
                  projectMaterialVerificationDocumentViewItem.buyMaterialView
                    .projectMaterialBrandPublic != null
                "
                >{{
                  projectMaterialVerificationDocumentViewItem.buyMaterialView
                    .projectMaterialBrandPublic.brandPublicView.brandView.brand
                    .name
                }}</span
              >

              <!--项目私有品牌-->
              <span
                v-else-if="
                  projectMaterialVerificationDocumentViewItem.buyMaterialView
                    .projectMaterialBrandPrivate != null
                "
                >{{
                  projectMaterialVerificationDocumentViewItem.buyMaterialView
                    .projectMaterialBrandPrivate.projectBrandView.brandView
                    .brand.name
                }}</span
              >
            </div>
          </el-col>

          <!--批次-->
          <el-col :span="2">
            <div style="display: flex; align-items: center">
              <div :class="{ textEllipsis: textElipsisValue }">
                {{
                  projectMaterialVerificationDocumentViewItem.buyMaterialView
                    .buyMaterial.batch
                }}
              </div>
            </div>
          </el-col>

          <!--工程材料-->
          <el-col :span="4">
            <div style="display: flex; align-items: center">
              <BuyMaterialDocument
                :documentView="projectMaterialVerificationDocumentViewItem"
                :fileType="0"
                :fileReadOnly="true"
              ></BuyMaterialDocument>
            </div>
          </el-col>

          <!--设备报验材料-->
          <el-col :span="4">
            <div style="display: flex; align-items: center">
              <BuyMaterialDocument
                :documentView="projectMaterialVerificationDocumentViewItem"
                :fileType="1"
                :fileReadOnly="true"
              ></BuyMaterialDocument>
            </div>
          </el-col>

          <!--是否复检-->
          <el-col :span="2" v-if="form.radioReviewResult == 2">
            <div style="display: flex; align-items: center">
              <el-radio-group
                v-model="
                  buyMaterialViewListRetest[
                    projectMaterialVerificationDocumentViewIndex
                  ]
                "
              >
                <el-radio :value="1">审核通过</el-radio>
                <el-radio :value="2">审核不通过</el-radio>
              </el-radio-group>
            </div>
          </el-col>
        </el-row>

        <div style="margin: 10px">
          <el-radio-group v-model="radio" @change="handleRadioChange">
            <el-radio :value="0">不需要复检</el-radio>
            <el-radio :value="1">需要复检</el-radio>
          </el-radio-group>
        </div>
        <div v-if="radio == 0"></div>
        <div v-else class="review-container">
          <el-form :model="form" label-width="auto" style="width: 100%">
            <el-form-item label="审核结果">
              <el-radio-group
                v-model="form.radioReviewResult"
                @change="handleRadioReviewChange"
              >
                <el-radio :value="1">全体审核通过</el-radio>
                <el-radio :value="2"
                  >审核不通过，通知禁止使用并退出现场</el-radio
                >
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

        <div style="margin: 10px; display: flex; justify-content: center">
          <el-button type="primary" @click="submitProcess">确定</el-button>
          <el-button type="primary" @click="cancelProcess">取消</el-button>
        </div>
      </div>

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

.textEllipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  overflow: hidden;
  /* autoprefixer: ignore next */
  -webkit-box-orient: vertical;
}

::v-deep .el-table .cell {
  white-space: pre-line;
}
</style>
