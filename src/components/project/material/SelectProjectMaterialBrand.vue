<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref, watch } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

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
} from "@/server/project/usematerial";

import { serverGetMaterialListByMaterialClassifySectionId } from "@/server/system/material";

import { areaOptions } from "@/utils/area";

const loading = ref(false);

const searchText = ref("");
const searchSelect = ref("1");

const pageNo = ref(1); //第几页
const pageSize = ref(getUserPageSize()); //每页多少数据

const projectMaterialViewPageData =
  ref<IServerPage<IServerProjectMaterialView> | null>(null);
const updateProjectMaterialView = ref<IServerProjectMaterialView>();
const projectViewData = ref<IServerProjectView>();
const radioUserType = ref(0);

const materialClassifyDivision = ref(""); //大类
const materialClassifyDivisionId = ref(""); //大类
const materialClassifyGroup = ref(""); //中类
const materialClassifyGroupId = ref(""); //中类
const materialClassifySection = ref(""); //小类
const materialClassifySectionId = ref(""); //小类
const materialBrandPosition = ref("国产"); //品牌定位
const materialBrandPositionId = ref(""); //品牌定位

const materialBrandId = ref("");
const materialNewBrandName = ref("");

const fileList = ref<UploadUserFile[]>([]);
const upload = ref<UploadInstance>();
const fileListUploadNum = ref(0);

import { genUUID } from "@/utils/utils";

interface Props {
  projectMaterialView: IServerProjectMaterialView;
}

const props = withDefaults(defineProps<Props>(), {});

//event
const emit = defineEmits<{
  //上传或删除文件成功，参数为上传文件路径和上传文件数量
  (
    e: "onUploadSuccess",
    projectMaterialView: IServerProjectMaterialView,
    tempDir: string,
    uploadFileCount: number
  ): void;

  //新建品牌名称
  (
    e: "onNewBrandNameChanged",
    projectMaterialView: IServerProjectMaterialView,
    newBrandName: string,
    materialClassifyDivisionId: string,
    materialClassifyGroupId: string,
    materialClassifySectionId: string,
    materialBrandPosition: string
  ): void;

  //选择公共品牌
  (
    e: "onSelectBrandPublic",
    projectMaterialView: IServerProjectMaterialView,
    projectMaterialBrandPublicView: IServerProjectMaterialBrandPublicView
  ): void;

  //选择私有品牌
  (
    e: "onSelectBrandPrivate",
    projectMaterialView: IServerProjectMaterialView,
    projectMaterialBrandPrivateView: IServerProjectMaterialBrandPrivateView
  ): void;

  //选择自建品牌
  (
    e: "onSelectNewBrand",
    projectMaterialView: IServerProjectMaterialView
  ): void;

  //选择自建品牌
  (
    e: "onSelectNewBrand",
    projectMaterialView: IServerProjectMaterialView
  ): void;
}>();

onMounted(async () => {
  if (props.projectMaterialView.projectMaterialBrandPublicViewList.length > 0) {
    const item =
      props.projectMaterialView.projectMaterialBrandPublicViewList[0];

    materialClassifyDivision.value =
      item.brandPublicView.brandView.materialClassifySectionView.materialClassifyDivision.name;
    materialClassifyDivisionId.value =
      item.brandPublicView.brandView.materialClassifySectionView.materialClassifyDivision.id;

    materialClassifyGroup.value =
      item.brandPublicView.brandView.materialClassifySectionView.materialClassifyGroup.name;
    materialClassifyGroupId.value =
      item.brandPublicView.brandView.materialClassifySectionView.materialClassifyGroup.id;

    materialClassifySection.value =
      item.brandPublicView.brandView.materialClassifySectionView.materialClassifySection.name;
    materialClassifySectionId.value =
      item.brandPublicView.brandView.materialClassifySectionView.materialClassifySection.id;

    materialBrandPosition.value = item.brandPublicView.brandView.brand.position;
    materialBrandPositionId.value = item.brandPublicView.brandView.brand.id;
  } else if (
    props.projectMaterialView.projectMaterialBrandPrivateViewList.length > 0
  ) {
    const item =
      props.projectMaterialView.projectMaterialBrandPrivateViewList[0];
    materialClassifyDivision.value =
      item.projectBrandView.brandView.materialClassifySectionView.materialClassifyDivision.name;
    materialClassifyDivisionId.value =
      item.projectBrandView.brandView.materialClassifySectionView.materialClassifyDivision.id;

    materialClassifyGroup.value =
      item.projectBrandView.brandView.materialClassifySectionView.materialClassifyGroup.name;
    materialClassifyGroupId.value =
      item.projectBrandView.brandView.materialClassifySectionView.materialClassifyGroup.id;

    materialClassifySection.value =
      item.projectBrandView.brandView.materialClassifySectionView.materialClassifySection.name;
    materialClassifySectionId.value =
      item.projectBrandView.brandView.materialClassifySectionView.materialClassifySection.id;

    materialBrandPosition.value =
      item.projectBrandView.brandView.brand.position;
    materialBrandPositionId.value = item.projectBrandView.brandView.brand.id;
  }
});

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

  formData.append("projectId", props.projectMaterialView.project.id);
  formData.append("file", uploadFile.name);
  formData.append("uploadBrandFilesDir", uploadBrandFilesDir.value);

  const ret = await serverDeleteUseProjectMaterialBrandTempFiles(formData);
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
    props.projectMaterialView,
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
    props.projectMaterialView,
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

  formData.append("projectId", props.projectMaterialView.project.id);
  formData.append("file", fileObj);
  formData.append("uploadBrandFilesDir", uploadBrandFilesDir.value);

  console.log(formData);

  const ret = await serverAddUseProjectMaterialBrandTempFiles(formData);
  if (ret && ret.code == 200 && ret.data) {
    ElMessage({
      type: "success",
      message: "增加成功",
    });

    emit(
      "onUploadSuccess",
      props.projectMaterialView,
      uploadBrandFilesDir.value,
      fileListUploadNum.value
    );
  } else ElMessage.error(`上传失败`);
};

const selectedProjectMaterialBrandPublicView =
  ref<IServerProjectMaterialBrandPublicView>();
const selectedProjectMaterialBrandPrivateView =
  ref<IServerProjectMaterialBrandPrivateView>();
/**
 * 用户选择了一个公共品牌
 * @param item
 */
const onPublicBrandChange = (item: IServerProjectMaterialBrandPublicView) => {
  emit("onSelectBrandPublic", props.projectMaterialView, item);
};

/**
 * 用户选择了一个私有品牌
 * @param item
 */
const onPrivateBrandChange = (item: IServerProjectMaterialBrandPrivateView) => {
  emit("onSelectBrandPrivate", props.projectMaterialView, item);
};

const onNewBrandNameChange = (value: string) => {
  emit(
    "onNewBrandNameChanged",
    props.projectMaterialView,
    materialNewBrandName.value,
    materialClassifyDivisionId.value,
    materialClassifyGroupId.value,
    materialClassifySectionId.value,
    materialBrandPositionId.value
  );
};

const onNewBrandChange = () => {
  emit("onSelectNewBrand", props.projectMaterialView);
};
</script>

<template>
  <div>
    <el-form :model="form" label-width="auto">
      <!--      <el-form-item label="品牌">-->
      <el-form-item>
        <el-radio-group v-model="materialBrandId">
          <el-radio
            v-for="(
              item, index
            ) in projectMaterialView.projectMaterialBrandPublicViewList"
            :key="index"
            :value="item.projectMaterialBrandPublic.id"
            @change="onPublicBrandChange(item)"
          >
            <div style="display: flex">
              <div>{{ item.brandPublicView.brandView.brand.name }}</div>
              <div
                style="
                  margin-left: auto;
                  color: var(--el-text-color-secondary);
                  font-size: 13px;
                "
              >
                公共品牌
              </div>
            </div>
          </el-radio>
          <el-radio
            v-for="(
              item, index
            ) in projectMaterialView.projectMaterialBrandPrivateViewList"
            :key="index"
            :value="item.projectMaterialBrandPrivate.id"
            @change="onPrivateBrandChange(item)"
          >
            <div style="display: flex">
              <div>{{ item.projectBrandView.brandView.brand.name }}</div>
              <div
                style="
                  margin-left: auto;
                  color: var(--el-text-color-secondary);
                  font-size: 13px;
                "
              >
                项目私有品牌
              </div>
            </div></el-radio
          >

          <el-radio value="0" @change="onNewBrandChange()"
            ><span style="color: red"
              >以上品牌均不对，选择新品牌</span
            ></el-radio
          >
        </el-radio-group>
      </el-form-item>
      <el-form-item label="大类-专业" v-if="materialBrandId === '0'">
        <el-input v-model="materialClassifyDivision" disabled />
      </el-form-item>
      <el-form-item label="中类-材料分类" v-if="materialBrandId === '0'">
        <el-input v-model="materialClassifyGroup" disabled />
      </el-form-item>
      <el-form-item label="小类-材料名称" v-if="materialBrandId === '0'">
        <el-input v-model="materialClassifySection" disabled />
      </el-form-item>
      <el-form-item label="品牌定位" v-if="materialBrandId === '0'">
        <el-radio-group
          v-model="materialBrandPosition"
          @change="onNewBrandChange()"
        >
          <el-radio value="国产">国产</el-radio>
          <el-radio value="合资">合资</el-radio>
          <el-radio value="外资">外资</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="新品牌名称" v-if="materialBrandId === '0'">
        <el-input
          v-model="materialNewBrandName"
          placeholder="输入新品牌名称,注意大类-专业、中类-材料分类、小类-材料名称和品牌定位正确"
          @change="onNewBrandNameChange"
        />
      </el-form-item>
      <el-form-item label="新品牌附件" v-if="materialBrandId === '0'">
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
            <div class="el-upload__tip text-red">请选择新建品牌附件。</div>
          </template>
        </el-upload>
      </el-form-item>
    </el-form>
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
</style>
