<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref, watch } from "vue";

import { useRouter, useRoute, onBeforeRouteUpdate } from "vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";

import type {
  UploadInstance,
  UploadProps,
  UploadRawFile,
  UploadUserFile,
  UploadRequestOptions,
} from "element-plus";

import type { CascaderValue } from "element-plus";
import {
  IServerMaterial,
  IServerMaterialForm,
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
} from "@/server/types/system/material";

import {
  serverGetMaterialPage,
  serverMaterialAdd,
  serverMaterialAddForm,
  serverMaterialDelete,
  serverMaterialDeleteById,
  serverMaterialUpdate,
  serverGetMaterialById,
} from "@/server/system/material";
import { IServerPage } from "@/server/types/System";

import {
  IServerBrand,
  IServerBrandPublic,
  IServerBrandPublicView,
} from "@/server/types/system/brand";

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
  serverMaterialPhotoAdd,
  serverMaterialPhotoDelete,
  serverMaterialPhotoDeleteById,
  serverMaterialPhotoUpdate,
  serverMaterialPhotoAddUploadTempFiles,
  serverMaterialPhotoDeleteUploadTempFiles,
  serverGetMaterialPhotorById,
  serverGetMaterialPhotoPage,
} from "@/server/system/materialphoto";

import {
  setUserCookies,
  getUserName,
  getUserID,
  getUserRealName,
  hasRole,
  isTeacher,
  isAdmin,
  isStudent,
  getUserPageSize,
  setUserPageSize,
} from "@/cookies/user";

import {
  IMaterialClassifyOption,
  generateMaterialClassifyOption,
} from "@/utils/MaterialClassifyOptions";

import { genUUID } from "@/utils/utils";

interface Props {
  dialogVisible: boolean; //对话框是否可见
}

const props = withDefaults(defineProps<Props>(), {
  dialogVisible: false,
});

//event
const emit = defineEmits<{
  (e: "onDilalogOk", material: IServerMaterial): void;
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

const onOpenDialog = async () => {
  materialClassifyOption.value = await generateMaterialClassifyOption();
  console.log(materialClassifyOption.value);
};

const handleClose = () => {
  emit("onDilalogCancel");
};
const onOk = async () => {
  const isValid = await materialFormRef.value?.validate();
  if (isValid) {
    try {
      const material: IServerMaterial = {
        id: "",
        materialClassifySectionId: materialForm.materialClassifyId,
        name: materialForm.name,
        itemMark: materialForm.item_mark,
        location: materialForm.location,
        technology: materialForm.technology,
        material: materialForm.material,
        color: materialForm.color,
        dimension: materialForm.dimension,
        fireRating: materialForm.fire_rating,
        installation: materialForm.installation,
        deletedAt: new Date(),
      };

      let materialNewForm: IServerMaterialForm = {
        material: material,
        photoTempDir: uploadPhotoFilesDir.value,
        brandIds: materialForm.brandId,
        photoIds: [],
      };

      console.log(materialNewForm);

      // 调用API
      const response = await serverMaterialAddForm(materialNewForm);
      if (response && response.code === 200) {
        // Debug: 查看创建结果
        console.log(response.data);
        ElMessage.success("材料创建成功");
      } else {
        console.error("材料创建失败");
      }
      emit("onDilalogOk", material);
      window.location.reload()
    } catch (error) {
      console.error("提交表单时发生错误:", error);
      ElMessage.error("材料信息无效，请重新检查数据是否正确");
    }
  } else {
    ElMessage.error("表单验证失败");
  }
};

const onCancel = () => {
  emit("onDilalogCancel");
};

const cascaderProps = {
  expandTrigger: "hover" as const,
  emitPath: false,
};

const getbrandPublicViewListFromSever = async () => {
  const materialClassifySectionId = materialForm.materialClassifyId;
  const ret1 = await serverGetBrandPublicByMaterialClassifySectionId(
    materialClassifySectionId
  );

  if (ret1 && ret1.code == 200) {
    brandPublicViewList.value = ret1.data;
  }
};

// 定义表单数据
const materialForm = reactive({
  id: "",
  materialClassifyId: "",
  name: "",
  item_mark: "",
  location: "",
  technology: "",
  material: "",
  color: "",
  dimension: "",
  fire_rating: "",
  installation: "",
  quantity: "",
  unit: "",
  photoDir: genUUID(),
  brandId: [] as string[],
});

// 表单引用
const materialList = ref<IServerMaterialBrand[]>();
const brandPublicViewList = ref<IServerBrandPublicView[]>();
const materialFormRef = ref<FormInstance>();

const materialClassifyOption = ref<IMaterialClassifyOption[]>([]);

// 表单验证规则
const rules = reactive<FormRules>({
  materialClassifyId: [
    { required: true, message: "请选择材料分类", trigger: "change" },
  ],
  name: [{ required: true, message: "请输入材料名称", trigger: "blur" }],
  item_mark: [{ required: true, message: "请输入材料编号", trigger: "blur" }],
  location: [{ required: true, message: "请输入项目地点", trigger: "blur" }],

  brandId: [{ required: true, message: "请选择品牌", trigger: "change" }],
});

// 创建页面跳转用Router
const router = useRouter(); // 正确地调用 useRouter()

const handleUploadError = (err, file) => {
  console.error("上传发生错误:", err);
};

// 重置表单
const resetForm = () => {
  materialFormRef.value?.resetFields();
};

const handleChange = async (value: CascaderValue) => {
  await getbrandPublicViewListFromSever();
};

const fileList = ref<UploadUserFile[]>([]);

const dialogImageUrl = ref("");
const dialogVisible = ref(false);
const upload = ref<UploadInstance>();

const handleRemove: UploadProps["onRemove"] = async (
  uploadFile,
  uploadFiles
) => {
  console.log(uploadFile, uploadFiles);

  const formData = new FormData();

  formData.append("fileName", uploadFile.name);
  formData.append("uploadPhotoFilesDir", uploadPhotoFilesDir.value);

  const ret = await serverMaterialPhotoDeleteUploadTempFiles(formData);
  if (ret && ret.code == 200 && ret.data) {
    ElMessage({
      type: "success",
      message: "删除成功",
    });
  } else ElMessage.success(`删除失败`);
};

const handlePictureCardPreview: UploadProps["onPreview"] = (uploadFile) => {
  dialogImageUrl.value = uploadFile.url!;
  dialogVisible.value = true;
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
) => {};

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

const uploadPhotoFilesDir = ref(genUUID());

/**
 * 向服务器上传数据
 * @param options
 */
const httpRequest = async (options: UploadRequestOptions) => {
  const fileObj = options.file;

  const formData = new FormData();

  formData.append("file", fileObj);
  formData.append("uploadPhotoFilesDir", uploadPhotoFilesDir.value);

  const ret = await serverMaterialPhotoAddUploadTempFiles(formData);
  if (ret && ret.code == 200 && ret.data) {
    ElMessage({
      type: "success",
      message: "增加成功",
    });
  } else ElMessage.error(`上传失败`);
};
</script>

<template>
  <el-dialog v-model="dialogVisible" :append-to-body="true">
    <img w-full :src="dialogImageUrl" alt="Preview Image" />
  </el-dialog>

  <el-dialog
    title="增加材料"
    v-model="dialogFormVisible"
    :before-close="handleClose"
    width="1200px"
    @open="onOpenDialog"
    draggable
  >
    <el-form
      label-width="100px"
      :model="materialForm"
      :rules="rules"
      ref="materialFormRef"
    >
      <el-form-item label="材料类别" prop="materialClassifyId">
        <el-cascader
          v-model="materialForm.materialClassifyId"
          :options="materialClassifyOption"
          :props="cascaderProps"
          style="width: 100%"
          @change="handleChange"
        />
      </el-form-item>

      <el-form-item label="名称" prop="name">
        <el-input v-model="materialForm.name" placeholder="请输入材料名称" />
      </el-form-item>

      <el-form-item label="编号" prop="item_mark">
        <el-input
          v-model="materialForm.item_mark"
          placeholder="请输入材料编号"
        />
      </el-form-item>

      <el-form-item label="位置" prop="location">
        <el-input
          v-model="materialForm.location"
          type="textarea"
          placeholder="请输入材料位置"
        />
      </el-form-item>

      <el-form-item label="技术要求" prop="technology">
        <el-input
          v-model="materialForm.technology"
          type="textarea"
          placeholder="请输入技术要求"
        />
      </el-form-item>

      <el-form-item label="材料材质" prop="material">
        <el-input
          v-model="materialForm.material"
          placeholder="请输入材料属性"
        />
      </el-form-item>

      <el-form-item label="颜色" prop="color">
        <el-input v-model="materialForm.color" placeholder="请输入颜色" />
      </el-form-item>

      <el-form-item label="规格" prop="dimension">
        <el-input v-model="materialForm.dimension" placeholder="请输入规格" />
      </el-form-item>

      <el-form-item label="防火等级" prop="fire_rating">
        <el-input
          v-model="materialForm.fire_rating"
          placeholder="请输入防火等级"
        />
      </el-form-item>

      <el-form-item label="施工要求" prop="installation">
        <el-input
          v-model="materialForm.installation"
          type="textarea"
          placeholder="请输入施工要求"
        />
      </el-form-item>

      <!--显示该材料类别的品牌-->
      <el-form-item label="参考品牌" prop="brandId">
        <el-checkbox-group v-model="materialForm.brandId">
          <el-checkbox
            v-for="(brandItem, index) in brandPublicViewList"
            :key="index"
            :label="brandItem.brandView.brand.name"
            :value="brandItem.brandPublic.brandId"
          />
        </el-checkbox-group>
      </el-form-item>

      <el-form-item label="样本照片" prop="photos">
        <el-upload
          ref="upload"
          v-model:file-list="fileList"
          action=""
          :limit="10"
          multiple
          :auto-upload="true"
          accept=".png, .jpg, .jpeg, .gif"
          list-type="picture-card"
          :on-exceed="handleExceed"
          :on-change="handleUploadImageChange"
          :before-upload="beforeUpload"
          :http-request="httpRequest"
          :on-preview="handlePictureCardPreview"
          :on-remove="handleRemove"
        >
          <el-icon><Plus /></el-icon>
        </el-upload>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="onCancel">取消</el-button>
        <el-button type="primary" @click="onOk()"> 确定 </el-button>
      </span>
    </template>
  </el-dialog>
</template>
