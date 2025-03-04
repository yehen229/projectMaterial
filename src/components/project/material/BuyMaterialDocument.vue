<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref, watch } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { Delete } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";
import type { Action } from "element-plus";
import type { CascaderValue } from "element-plus";
import { ElTable } from "element-plus";
import type {
  UploadInstance,
  UploadProps,
  UploadRawFile,
  UploadUserFile,
  UploadRequestOptions,
} from "element-plus";

import {
  clearCookies,
  setUserCookies,
  getToken,
  getUserID,
  getUserName,
  getUserRealName,
  hasRole,
  isTeacher,
  isAdmin,
  isStudent,
  getUserPageSize,
  setUserPageSize,
} from "@/cookies/user";

import {
  IServerBrand,
  IServerBrandPublic,
  IServerBrandPublicView,
} from "@/server/types/system/brand";

import {
  IMaterialClassifyOption,
  generateMaterialClassifyOption,
} from "@/utils/MaterialClassifyOptions";

import {
  IServerProject,
  IServerProjectView,
  IServerProjectUser,
  IServerProjectUserView,
  IServerProjectAllUserView,
  IServerProjectBrand,
  IServerProjectBrandView,
  IServerProjectBrandForm,
  IServerProjectMaterial,
  IServerProjectMaterialView,
  IServerProjectMaterialForm,
  IServerProjectMaterialBrandPublicView,
  IServerProjectMaterialBrandPrivateView,
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
  IServerMaterial,
  IServerMaterialBrand,
  IServerMaterialBrandView,
  IServerMaterialClassifyDivision,
  IServerMaterialClassifyDivisionTreeItem,
  IServerMaterialClassifyGroup,
  IServerMaterialClassifyGroupTreeItem,
  IServerMaterialClassifyGroupView,
  IServerMaterialClassifySection,
  IServerMaterialClassifySectionView,
  IServerMaterialClassifyTree,
  IServerMaterialPhoto,
  IServerMaterialPhotoView,
  IServerMaterialForm,
  IServerMaterialView,
} from "@/server/types/system/material";

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
  serverBrandPublicAdd,
  serverBrandPublicDelete,
  serverBrandPublicDeleteById,
  serverBrandPublicUpdate,
  serverGetBrandPublicById,
  serverGetBrandPublicByBrandId,
  serverGetBrandPublicByMaterialClassifySectionId,
  serverGetBrandPublicPage,
  serverGetBrandPublicPageView,
  serverGetBrandPublicPageViewByBrandName,
  serverGetBrandPublicPageViewByBrandPosition,
} from "@/server/system/brandpublic";

import {
  serverGetProjectBrandListByProjectId,
  serverGetProjectBrandViewListByProjectId,
} from "@/server/project/projectbrand";

//服务器返回到前端的类型
import { IServerPage } from "@/server/types/System";

import {
  serverAddUseProjectMaterialBrandTempFiles,
  serverDeleteUseProjectMaterialBrandTempFiles,
  serverAddBuyMaterialVerificationDocumentTempFiles,
  serverDeleteBuyMaterialVerificationDocumentTempFiles,
  serverDownloadBuyMaterialVerificationDocumentFilesById,
  serverAddBuyMaterialVerificationDocumentFile,
  serverDeleteBuyMaterialVerificationDocumentFile,
} from "@/server/project/usematerial";

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
} from "@/server/types/project/review";

import { serverGetMaterialListByMaterialClassifySectionId } from "@/server/system/material";

import { areaOptions } from "@/utils/area";

const loading = ref(false);

const searchText = ref("");
const searchSelect = ref("1");

const pageNo = ref(1); //第几页
const pageSize = ref(getUserPageSize()); //每页多少数据

const projectMaterialViewPageData =
  ref<IServerPage<IServerProjectMaterialVerificationDocumentView> | null>(null);
const updateProjectMaterialView = ref<IServerProjectMaterialView>();
const projectViewData = ref<IServerProjectView>();
const radioUserType = ref(0);

const materialBrandId = ref("");
const materialNewBrandName = ref("");

const fileList = ref<UploadUserFile[]>([]);
const upload = ref<UploadInstance>();
const fileListUploadNum = ref(0);

import { genUUID } from "@/utils/utils";

interface Props {
  documentView: IServerProjectMaterialVerificationDocumentView;
  fileType: number; //文件类型：0工程材料，1设备报验材料
  fileReadOnly: boolean;
}

const props = withDefaults(defineProps<Props>(), {});

//event
const emit = defineEmits<{
  //上传或删除文件成功，参数为上传文件路径和上传文件数量
  (
    e: "onUploadSuccess",
    documentView: IServerProjectMaterialVerificationDocumentView,
    tempDir: string,
    uploadFileCount: number
  ): void;
}>();

onMounted(async () => {});

const cascaderProps = {
  expandTrigger: "hover" as const,
  emitPath: true,
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

  formData.append(
    "projectId",
    props.documentView.buyMaterialView.useMaterialView.projectMaterialView
      .project.id
  );
  formData.append("fileName", uploadFile.name);
  formData.append("uploadDocumentFilesDir", uploadBrandFilesDir.value);

  const ret = await serverDeleteBuyMaterialVerificationDocumentTempFiles(
    formData
  );
  if (ret && ret.code == 200 && ret.data) {
    ElMessage({
      type: "success",
      message: "删除成功",
    });
  } else ElMessage.success(`删除失败`);

  console.log(uploadFiles.length);
  fileListUploadNum.value = uploadFiles.length;
  emit(
    "onUploadSuccess",
    props.documentView,
    uploadBrandFilesDir.value,
    fileListUploadNum.value
  );
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
  emit(
    "onUploadSuccess",
    props.documentView,
    uploadBrandFilesDir.value,
    fileListUploadNum.value
  );
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

const uploadBrandFilesDir = ref(genUUID());
const userNewBrandName = ref("");

/**
 * 向服务器上传数据
 * @param options
 */
const httpRequest = async (options: UploadRequestOptions) => {
  console.log("options");
  const fileObj = options.file;

  const formData = new FormData();

  formData.append(
    "projectId",
    props.documentView.buyMaterialView.useMaterialView.projectMaterialView
      .project.id
  );
  formData.append(
    "buyMaterialId",
    props.documentView.buyMaterialView.buyMaterial.id
  );
  formData.append("file", fileObj);
  formData.append("uploadDocumentFilesDir", uploadBrandFilesDir.value);
  formData.append("fileType", props.fileType + "");

  console.log(formData);

  const ret = await serverAddBuyMaterialVerificationDocumentFile(formData);
  if (ret && ret.code == 200 && ret.data) {
    ElMessage({
      type: "success",
      message: "增加成功",
    });

    emit(
      "onUploadSuccess",
      props.documentView,
      uploadBrandFilesDir.value,
      fileListUploadNum.value
    );
  } else ElMessage.error(`上传失败`);
};

const selectedProjectMaterialBrandPublicView =
  ref<IServerProjectMaterialBrandPublicView>();
const selectedProjectMaterialBrandPrivateView =
  ref<IServerProjectMaterialBrandPrivateView>();

const getFullFilename = (
  projectId: string,
  projectUserFileId: string,
  userName: string,
  index: number,
  originFileName: string
) => {
  //console.log(projectUserFileId);
  let file_ext = originFileName
    .substring(originFileName.lastIndexOf("."))
    .toLowerCase();

  const downloadFilename = userName + "_附件" + index + file_ext;
  // console.log(downloadFilename);
  return downloadFilename;
};

const downProjectReviewUserFileFromServer = async (
  projectId: string,
  projectUserFileId: string,
  userName: string,
  index: number,
  originFileName: string
) => {
  const downloadFilename = getFullFilename(
    projectId,
    projectUserFileId,
    userName,
    index,
    originFileName
  );
  //console.log(downloadFilename);

  const ret = await serverDownloadBuyMaterialVerificationDocumentFilesById(
    projectId,
    projectUserFileId,
    downloadFilename
  );
  console.log(ret);
};

const deleteFile = async (id: string) => {
  console.log("options");

  const formData = new FormData();

  formData.append(
    "projectId",
    props.documentView.buyMaterialView.useMaterialView.projectMaterialView
      .project.id
  );
  formData.append("projectMaterialVerificationDocumentFileId", id);

  console.log(formData);

  const ret = await serverDeleteBuyMaterialVerificationDocumentFile(formData);
  if (ret && ret.code == 200 && ret.data) {
    ElMessage({
      type: "success",
      message: "删除成功",
    });

    emit(
      "onUploadSuccess",
      props.documentView,
      uploadBrandFilesDir.value,
      fileListUploadNum.value
    );
  } else ElMessage.error(`删除失败`);

  emit(
    "onUploadSuccess",
    props.documentView,
    uploadBrandFilesDir.value,
    fileListUploadNum.value
  );
};
</script>

<template>
  <div>
    <div
      v-for="(
        item, index
      ) in documentView.projectMaterialVerificationDocumentFileList"
      :key="index"
      style="padding-left: 10px; padding-right: 10px; display: flex"
    >
      <div
        class="download-file"
        v-if="item.fileType == fileType"
        @click="
          downProjectReviewUserFileFromServer(
            documentView.buyMaterialView.useMaterialView.projectMaterialView
              .project.id,
            item.id,
            documentView.user.realName + '_' + documentView.user.userName,
            index + 1,
            item.filePath
          )
        "
      >
        {{
          getFullFilename(
            documentView.buyMaterialView.useMaterialView.projectMaterialView
              .project.id,
            item.id,
            documentView.user.realName + "_" + documentView.user.userName,
            index + 1,
            item.filePath
          )
        }}
      </div>
      <el-button
        v-if="item.fileType == fileType && !fileReadOnly"
        type="danger"
        size="small"
        :icon="Delete"
        circle
        @click="deleteFile(item.id)"
      />
    </div>

    <el-upload
      v-if="!fileReadOnly"
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
      :show-file-list="false"
    >
      <template #trigger>
        <el-button size="small">新增文件...</el-button>
      </template>
    </el-upload>
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

.input-with-select {
  right: 20px;
  margin-left: 10px;
  flex: 1;
}

.download-file {
  cursor: pointer;
}
.download-file:hover {
  color: blue;
}
</style>
