<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref, watch } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { Download } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";
import type {
  UploadProps,
  UploadUserFile,
  UploadInstance,
  UploadRawFile,
  UploadRequestOptions,
} from "element-plus";

import type { Action } from "element-plus";
import {
  IServerCompany,
  IServerCompanyUser,
  IServerCompanyUserView,
  IServerCompanyUserForm,
} from "@/server/types/system/company";

import {
  serverCompanyAdd,
  serverCompanyDelete,
  serverCompanyUpdate,
  serverGetCompanyPage,
  serverGetAllCompanyList,
  serverGetAllCompanyListByCompanyType,
} from "@/server/system/company";

import {
  serverCompanyUserAdd,
  serverCompanyUserAddByForm,
  serverCompanyUserAddFromExcel,
  serverCompanyUserDelete,
  serverCompanyUserDeleteById,
  serverCompanyUserUpdate,
  serverGetCompanyUserById,
  serverGetCompanyUserListByCompanyId,
  serverGetCompanyUserListByUserId,
  serverGetCompanyUserPage,
  serverGetCompanyUserPageView,
  serverGetCompanyUserPageViewByUserName,
  serverGetCompanyUserPageViewByProjectName,
  serverGetCompanyUserPageViewByCompanyName,
} from "@/server/system/companyuser";

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

const router = useRouter();
const loading = ref(false);
interface Props {
  dialogVisible: boolean; //对话框是否可见
}

const props = withDefaults(defineProps<Props>(), {
  dialogVisible: false,
});

//event
const emit = defineEmits<{
  (e: "onDilalogOk"): void;
  (e: "onDilalogCancel"): void;
}>();

const dialogFormVisible = computed({
  get() {
    return props.dialogVisible;
  },
  set(val) {
    return val;
  },
});

const onOpenDialog = async () => {};

const handleClose = () => {
  emit("onDilalogCancel");
};
const onOk = () => {
  //上传文件
  submitUpload();

  emit("onDilalogOk");
};

const onCancel = () => {
  emit("onDilalogCancel");
};

/**
 * 点击“下载Excel模板”按钮，下载Excel文件
 */
const onExcelDownloadButtonClick = async () => {
  let a = document.createElement("a");
  a.href = "/static/员工名单模板.xlsx";
  a.download = "员工名单模板.xlsx";
  a.style.display = "none";
  document.body.appendChild(a);
  a.click();
  a.remove();
};

const fileList = ref<UploadUserFile[]>([]);
const upload = ref<UploadInstance>();

const handleRemove: UploadProps["onRemove"] = (file, uploadFiles) => {
  console.log(file, uploadFiles);
};

const handlePreview: UploadProps["onPreview"] = (uploadFile) => {
  console.log(uploadFile);
};

/**
 * 超过文件上传最大个数
 * @param files
 * @param uploadFiles
 */
const handleExceed: UploadProps["onExceed"] = (files, uploadFiles) => {
  ElMessage.warning(
    `The limit is 1, you selected ${files.length} files this time, add up to ${
      files.length + uploadFiles.length
    } totally`
  );
};

/**
 * 文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用
 * @param uploadFile
 * @param uploadFiles
 */
const handleChange: UploadProps["onChange"] = (uploadFile, uploadFiles) => {};

/**
 * 上传文件之前的钩子，参数为上传的文件， 若返回false或者返回 Promise 且被 reject，则停止上传。
 * @param rawFile
 */
const beforeUpload = (rawFile: UploadRawFile) => {
  const extension = rawFile.name.substring(rawFile.name.lastIndexOf(".") + 1);
  if (extension !== "xlsx" && extension !== "xls") {
    ElMessage.warning(
      `文件类型必须为Excel格式，文件后缀为xlsx或xls，现在的文件后缀是：${extension} `
    );
    return false;
  }
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

/**
 * 从Excel文件中导入学生名单
 * @param options
 */
const httpRequest = async (options: UploadRequestOptions) => {
  loading.value = true;
  const fileObj = options.file;
  const formData = new FormData();
  formData.append("file", fileObj);

  const ret = await serverCompanyUserAddFromExcel(formData);
  if (ret && ret.code == 200 && ret.data) {
    ElMessage.success(`成功上传`);
  } else ElMessage.error(`上传失败`);

  loading.value = false;
};

const submitUpload = () => {
  upload.value!.submit();
};
</script>

<template>
  <div>
    <el-dialog
      title="从Excel文件导入用户名单"
      v-model="dialogFormVisible"
      :before-close="handleClose"
      width="600px"
      @open="onOpenDialog"
      draggable
    >
      <el-upload
        ref="upload"
        class="upload-demo"
        action=""
        :limit="1"
        :auto-upload="false"
        accept=".xlsx, .xls"
        :file-list="fileList"
        :on-exceed="handleExceed"
        :on-preview="handlePreview"
        :on-change="handleChange"
        :on-remove="handleRemove"
        :before-upload="beforeUpload"
        :http-request="httpRequest"
        v-loading="loading"
      >
        <template #trigger>
          <el-button type="primary">选择文件</el-button>
        </template>

        <template #tip>
          <div class="el-upload__tip text-red">
            请选择员工名单Excel文件，该文件需要符合员工名单模板要求。
          </div>

          <el-button
            :icon="Download"
            @click="onExcelDownloadButtonClick"
            size="small"
          >
            下载Excel模板
          </el-button>
        </template>
      </el-upload>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="onCancel">取消</el-button>
          <el-button type="primary" @click="onOk()"> 确定 </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
