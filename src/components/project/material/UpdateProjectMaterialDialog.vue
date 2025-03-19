<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref, watch } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus, Edit } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";
import type { Action } from "element-plus";
import type { CascaderValue } from "element-plus";

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
  serverGetProjectMaterialBrandPrivateViewListByProjectIdAndMaterialId,
} from "@/server/project/projectbrand";

import {
  serverGetMaterialListByMaterialClassifySectionId,
  serverGetMaterialViewById,
} from "@/server/system/material";

import {
  serverMaterialPhotoAdd,
  serverMaterialPhotoDelete,
  serverMaterialPhotoDeleteById,
  serverMaterialPhotoUpdate,
  serverMaterialPhotoAddUploadTempFiles,
  serverMaterialPhotoDeleteUploadTempFiles,
  serverProjectMaterialPhotoAddUploadTempFiles,
  serverProjectMaterialPhotoDeleteUploadTempFiles,
  serverGetMaterialPhotoListByMaterialId,
  serverGetMaterialPhotoFileById,
  serverGetMaterialPhotoPage,
  serverGetMaterialPhotoFileUrl,
} from "@/server/system/materialphoto";
import { serverGetMaterialClassifySectionViewById } from "@/server/system/materialclassifysection";

import MaterialPhoto from "@/components/system/meterial/MaterialPhoto.vue";

import UpdateProjectMaterialPhotoDialog from "@/components/system/meterial/UpdateProjectMaterialPhotoDialog.vue";
import NewProjectBrandDialog from "@/components/project/brand/NewProjectBrandDialog.vue";
import UpdateProjectBrandDialog from "@/components/project/brand/UpdateProjectBrandDialog.vue";
import UpdateProjectPrivateBrandDialog from "@/components/project/brand/UpdateProjectPrivateBrandDialog.vue";

import { genUUID } from "@/utils/utils";
const router = useRouter();
const materialViewList = ref<IServerMaterialView[]>();
const brandPublicViewList = ref<IServerBrandPublicView[]>();
const projectBrandList = ref<IServerProjectMaterialBrandPrivateView[]>([]);
const dialogFormNewVisible = ref(false); //控制“新建对话框”是否显示
const dialogFormUpdateVisible = ref(false); //控制“修改对话框”是否显示
interface Props {
  dialogVisible: boolean; //对话框是否可见
  projectView: IServerProjectView;
  projectMaterialView: IServerProjectMaterialView;
}

const props = withDefaults(defineProps<Props>(), {
  dialogVisible: false,
});

const form = reactive({
  id: "", //id,主键
  name: "", //名称
  materialId: "",
  materialClassifySectionId: "", //
  itemMark: "",
  location: "",
  technology: "",
  material: "",
  color: "",
  dimension: "",
  fireRating: "",
  installation: "",
  materialCount: 0,
  materialUnit: "",
  publicBrandIds: [] as string[],
  projectBrandIds: [] as string[],
  deletedAt: new Date(),
  materialPhotoViewList: [] as IServerMaterialPhotoView[],
});

const rules = reactive<FormRules>({
  name: [
    { required: true, message: "请输入品牌名称", trigger: "blur" },
    { min: 1, max: 64, message: "字数范围为：1-64", trigger: "blur" },
  ],
  materialClassifySectionId: [
    { required: true, message: "请选择材料类别", trigger: "blur" },
  ],
  quantity: [{ required: true, message: "请输入材料数量", trigger: "blur" }],
  unit: [{ required: true, message: "请输入数量单位", trigger: "blur" }],
});

//event
const emit = defineEmits<{
  (e: "onDilalogOk", projectMaterialForm: IServerProjectMaterialForm): void;
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

const materialClassifyOption = ref<IMaterialClassifyOption[]>([]);
const onOpenDialog = async () => {
  materialClassifyOption.value = await generateMaterialClassifyOption();
  await getProjectBrandListFromSever();
  console.log(materialClassifyOption.value);

  form.materialId = props.projectMaterialView.projectMaterial.materialId;
  form.materialClassifySectionId =
    props.projectMaterialView.material.materialClassifySectionId;
  form.name = props.projectMaterialView.material.name;
  form.itemMark = props.projectMaterialView.material.itemMark;
  form.location = props.projectMaterialView.material.location;
  form.technology = props.projectMaterialView.material.technology;
  form.material = props.projectMaterialView.material.material;
  form.color = props.projectMaterialView.material.color;
  form.dimension = props.projectMaterialView.material.dimension;
  form.fireRating = props.projectMaterialView.material.fireRating;
  form.installation = props.projectMaterialView.material.installation;
  form.materialCount = props.projectMaterialView.projectMaterial.materialCount;
  form.materialUnit = props.projectMaterialView.projectMaterial.materialUnit;

  await getMaterialViewListFromSever();
  await getbrandPublicViewListFromSever();

  //公共品牌
  form.publicBrandIds.length = 0;
  if (props.projectMaterialView.projectMaterialBrandPublicViewList)
    for (
      let i = 0;
      i < props.projectMaterialView.projectMaterialBrandPublicViewList.length;
      i++
    ) {
      form.publicBrandIds.push(
        props.projectMaterialView.projectMaterialBrandPublicViewList[i]
          .brandPublicView.brandPublic.id
      );
    }

  //私有品牌
  form.projectBrandIds.length = 0;
  if (props.projectMaterialView.projectMaterialBrandPrivateViewList)
    for (
      let i = 0;
      i < props.projectMaterialView.projectMaterialBrandPrivateViewList.length;
      i++
    ) {
      form.projectBrandIds.push(
        props.projectMaterialView.projectMaterialBrandPrivateViewList[i]
          .projectMaterialBrandPrivate.projectBrandId
      );
    }

  //样本照片
  await getMaterialViewListFromSever();
  const retView = await serverGetMaterialViewById(form.materialId);
  if (retView && retView.code == 200) {
    form.materialPhotoViewList = retView.data.materialPhotoViewList;
  }

  await getMaterialPhotoViewListFromSever(
    props.projectMaterialView.material.id
  );
};

const handleClose = () => {
  emit("onDilalogCancel");
};
const onOk = () => {
  if (!form.name || form.name.length == 0) {
    ElMessageBox.alert("名称不能为空", "提示", {
      confirmButtonText: "确定",
    });

    return;
  }

  if (
    !form.materialClassifySectionId ||
    form.materialClassifySectionId.length == 0
  ) {
    ElMessageBox.alert("材料类别不能为空", "提示", {
      confirmButtonText: "确定",
    });

    return;
  }

  if (!form.materialCount || form.materialCount <= 0) {
    ElMessageBox.alert("材料数量不能小于等于0", "提示", {
      confirmButtonText: "确定",
    });

    return;
  }

  if (!form.materialUnit || form.materialUnit.length == 0) {
    ElMessageBox.alert("数量单位不能为空", "提示", {
      confirmButtonText: "确定",
    });

    return;
  }

  let brandCount = form.publicBrandIds.length + projectBrandList.value.length;
  if (brandCount == 0) {
    ElMessageBox.alert("必须选择一个品牌", "提示", {
      confirmButtonText: "确定",
    });

    return;
  }

  console.log("form", form);
  console.log("props", props);

  var projectBrandViewList: IServerProjectBrandView[] = [];

  projectBrandList.value.forEach((item) => {
    projectBrandViewList.push(item.projectBrandView);
  });

  let projectMaterialForm: IServerProjectMaterialForm = {
    projectMaterial: {
      id: props.projectMaterialView.projectMaterial.id,
      materialCount: form.materialCount,
      materialId: form.materialId,
      materialOriginId: props.projectMaterialView.projectMaterial.materialId,
      materialUnit: form.materialUnit,
      projectId: props.projectView.project.id,
      companyId: props.projectMaterialView.projectMaterial.companyId,
      deletedAt: new Date(),
    },
    material: {
      id: form.materialId, //id,主键
      materialClassifySectionId: form.materialClassifySectionId, //t_material_classify_section_id
      name: form.name, //name,名称名称
      itemMark: form.itemMark, //item_mark,编号编号
      location: form.location, //location,位置位置
      technology: form.technology, //technology,技术要求技术要求
      material: form.material, //material,材料材质材料材质
      color: form.color, //color,颜色颜色
      dimension: form.dimension, //dimension,规格规格
      fireRating: form.fireRating, //fire_rating,防火等级防火等级
      installation: form.installation, //installation,施工要求施工要求
      materialProjectBindType: 1, //类型：0：原始材料；1：和项目绑定的、基于原始材料修改的
      deletedAt: new Date(),
    },
    materialChange: true, //材料变更,用户是否修改了材料参数（不包括品牌、数量和数量单位）

    publicBrandIds: form.publicBrandIds,
    projectBrandIds: [],
    projectBrandViewList: projectBrandViewList,
    photoTempDir: uploadPhotoFilesDir.value,
    photoIds: [],
  };

  console.log("projectMaterialForm", projectMaterialForm);

  emit("onDilalogOk", projectMaterialForm);
};

const onCancel = () => {
  emit("onDilalogCancel");
};

const cascaderProps = {
  expandTrigger: "hover" as const,
  emitPath: false,
};

const onMaterialClassifySectionChange = async (value: CascaderValue) => {
  await getMaterialViewListFromSever();
  await getbrandPublicViewListFromSever();
};

const getMaterialViewListFromSever = async () => {
  const materialClassifySectionId = form.materialClassifySectionId;
  const ret1 = await serverGetMaterialListByMaterialClassifySectionId(
    materialClassifySectionId
  );

  if (ret1 && ret1.code == 200) {
    materialViewList.value = ret1.data;
  }
};

const getbrandPublicViewListFromSever = async () => {
  const materialClassifySectionId = form.materialClassifySectionId;
  const ret1 = await serverGetBrandPublicByMaterialClassifySectionId(
    materialClassifySectionId
  );

  if (ret1 && ret1.code == 200) {
    brandPublicViewList.value = ret1.data;
  }
};

const getProjectBrandListFromSever = async () => {
  const projectId = props.projectView.project.id;
  //const ret1 = await serverGetProjectBrandViewListByProjectId(projectId);

  const ret1 =
    await serverGetProjectMaterialBrandPrivateViewListByProjectIdAndMaterialId(
      projectId,
      props.projectMaterialView.material.id
    );

  if (ret1 && ret1.code == 200) {
    projectBrandList.value = ret1.data;
  }
};

const getMaterialPhotoViewListFromSever = async (materialId: string) => {
  if (!materialId) return;
  fileList.value.length = 0;

  //获取照片
  const retPhotoList = await serverGetMaterialPhotoListByMaterialId(materialId);

  imageData.value.length = 0;
  if (retPhotoList && retPhotoList.code == 200 && retPhotoList.data) {
    retPhotoList.data.forEach(async (item) => {
      let retfile = await serverGetMaterialPhotoFileById(item.id);
      // let bolb = new Blob(retfile.data);

      fileList.value.push({
        name: item.id,
        url: retfile.data,
      });
    });
  }
};

const onMaterialChanged = (materialView: IServerMaterialView) => {
  form.materialId = materialView.material.id;
  form.itemMark = materialView.material.itemMark;
  form.location = materialView.material.location;
  form.technology = materialView.material.technology;
  form.material = materialView.material.material;
  form.color = materialView.material.color;
  form.dimension = materialView.material.dimension;
  form.fireRating = materialView.material.fireRating;
  form.installation = materialView.material.installation;
  form.materialPhotoViewList = materialView.materialPhotoViewList;
};

const fileList = ref<UploadUserFile[]>([]);
const imageData = ref<string[]>([]);

const dialogImageUrl = ref("");
const dialogVisible = ref(false);
const upload = ref<UploadInstance>();

const handleRemove: UploadProps["onRemove"] = async (
  uploadFile,
  uploadFiles
) => {
  console.log(uploadFile, uploadFiles);

  /*

  const formData = new FormData();

  formData.append("projectId", props.projectView.project.id);
  formData.append("fileName", uploadFile.name);
  formData.append("uploadPhotoFilesDir", uploadPhotoFilesDir.value);

  const ret = await serverMaterialPhotoDeleteUploadTempFiles(formData);
  if (ret && ret.code == 200 && ret.data) {
    ElMessage({
      type: "success",
      message: "删除成功",
    });
  } else ElMessage.success(`删除失败`);*/
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
) => {
  console.log(uploadFile, uploadFiles);
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

const uploadPhotoFilesDir = ref(genUUID());

/**
 * 向服务器上传数据
 * @param options
 */
const httpRequest = async (options: UploadRequestOptions) => {
  const fileObj = options.file;

  const formData = new FormData();
  formData.append("projectId", props.projectView.project.id);
  formData.append("file", fileObj);
  formData.append("uploadPhotoFilesDir", uploadPhotoFilesDir.value);

  const ret = await serverProjectMaterialPhotoAddUploadTempFiles(formData);
  if (ret && ret.code == 200 && ret.data) {
    ElMessage({
      type: "success",
      message: "增加成功",
    });
  } else ElMessage.error(`上传失败`);
};

const onNewPrivateBrandButtonClick = () => {
  dialogFormNewVisible.value = true;
};
const onNewProjectBrandDialogOk = async (brand: IServerBrand) => {
  dialogFormNewVisible.value = false;

  const found = projectBrandList.value?.find((item) => {
    if (brand.id)
      return (
        item.projectBrandView.projectBrand.brandId == brand.id &&
        item.projectBrandView.brandView.brand.name == brand.name &&
        item.projectBrandView.brandView.brand.position == brand.position &&
        item.projectBrandView.brandView.brand.materialClassifySectionId ==
          brand.materialClassifySectionId
      );
    else
      return (
        item.projectBrandView.brandView.brand.name == brand.name &&
        item.projectBrandView.brandView.brand.position == brand.position &&
        item.projectBrandView.brandView.brand.materialClassifySectionId ==
          brand.materialClassifySectionId
      );
  });
  console.log(found);
  if (!found) {
    const ret = await serverGetMaterialClassifySectionViewById(
      brand.materialClassifySectionId
    );

    if (ret && ret.code == 200 && ret.data) {
      const materialClassifySectionView = ret.data;
      const brandTemp: IServerBrand = {
        id: brand.id, //id,主键
        name: brand.name, //name
        materialClassifySectionId: brand.materialClassifySectionId, //t_material_classify_section_id
        position: brand.position, //position,定位：合资、国产等定位：合资、国产等
        deletedAt: new Date(), //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
      };

      const projectBrandView: IServerProjectMaterialBrandPrivateView = {
        projectMaterialBrandPrivate: {
          id: "",
          projectMaterialId: "",
          projectBrandId: "",
          deletedAt: new Date(),
        },
        projectMaterial: {
          id: "", //id,主键
          projectId: "", //t_project_id,外键,	t_project_id<-表t_project.id
          companyId: "", //t_company_id,外键,	t_company_id<-表t_company.id
          materialId: "", //t_material_id,外键,	t_material_id<-表t_material.id
          materialOriginId: "", //t_material_origin_id,项目原始材料，t_material_id是此材料修改而来，t_material_id可以和t_material_origin_id相同
          materialCount: 0, //material_count,数量数量
          materialUnit: "", //material_unit,数量单位数量单位
          deletedAt: new Date(), //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
        },
        projectBrandView: {
          projectBrand: {
            id: "",
            brandId: brand.id,
            projectId: props.projectView.project.id,
            deletedAt: new Date(),
          },
          project: props.projectView.project,
          brandView: {
            brand: brandTemp,
            materialClassifySectionView: materialClassifySectionView,
          },
        },
      };
      console.log(projectBrandView);
      console.log(projectBrandList.value);
      projectBrandList.value?.push(projectBrandView);

      console.log(projectBrandList.value);
    }
  }
};
const onNewProjectBrandDialogCancel = () => {
  dialogFormNewVisible.value = false;
};

//删除index处的项目品牌
const onRowDeleteButtonClick = async (
  index: number,
  row: IServerProjectBrandView
) => {
  projectBrandList.value?.splice(index, 1);
};

const projectBrandUpdateIndex = ref(-1);
const projectBrandUpdateName = computed(() => {
  if (!projectBrandList.value) return "";
  if (
    projectBrandUpdateIndex.value >= 0 &&
    projectBrandUpdateIndex.value < projectBrandList.value?.length
  )
    return projectBrandList.value[projectBrandUpdateIndex.value]
      .projectBrandView.brandView.brand.name;
  return "";
});

const projectBrandUpdatePosition = computed(() => {
  if (!projectBrandList.value) return "";
  if (
    projectBrandUpdateIndex.value >= 0 &&
    projectBrandUpdateIndex.value < projectBrandList.value?.length
  )
    return projectBrandList.value[projectBrandUpdateIndex.value]
      .projectBrandView.brandView.brand.position;
  return "";
});
const onRowEditButtonClick = (
  index: number,
  row: IServerProjectMaterialBrandPrivateView
) => {
  dialogFormUpdateVisible.value = true;
  projectBrandUpdateIndex.value = index;
};

const onUpdateProjectBrandDialogCancel = () => {
  dialogFormUpdateVisible.value = false;
};

const onUpdateProjectBrandDialogOk = (brand: IServerBrand) => {
  dialogFormUpdateVisible.value = false;
  if (!projectBrandList.value) return;
  projectBrandList.value[
    projectBrandUpdateIndex.value
  ].projectBrandView.brandView.brand.materialClassifySectionId =
    brand.materialClassifySectionId;
  projectBrandList.value[
    projectBrandUpdateIndex.value
  ].projectBrandView.brandView.brand.name = brand.name;
  projectBrandList.value[
    projectBrandUpdateIndex.value
  ].projectBrandView.brandView.brand.position = brand.position;
};
</script>

<template>
  <div>
    <!--新增对话框-->
    <NewProjectBrandDialog
      :append-to-body="true"
      ref="dialogFormNew"
      :dialogVisible="dialogFormNewVisible"
      :projectView="projectView"
      :materialClassifySectionId="form.materialClassifySectionId"
      @onDilalogCancel="onNewProjectBrandDialogCancel"
      @onDilalogOk="onNewProjectBrandDialogOk"
    ></NewProjectBrandDialog>

    <!--修改对话框-->
    <UpdateProjectPrivateBrandDialog
      :append-to-body="true"
      :dialogVisible="dialogFormUpdateVisible"
      :projectView="projectView"
      :materialClassifySectionId="form.materialClassifySectionId"
      :brandName="projectBrandUpdateName"
      :position="projectBrandUpdatePosition"
      @onDilalogCancel="onUpdateProjectBrandDialogCancel"
      @onDilalogOk="onUpdateProjectBrandDialogOk"
    ></UpdateProjectPrivateBrandDialog>

    <el-dialog
      title="修改项目材料"
      v-model="dialogFormVisible"
      :before-close="handleClose"
      width="1200px"
      @open="onOpenDialog"
      draggable
    >
      <el-form
        label-width="150px"
        :model="form"
        :rules="rules"
        ref="ruleFormRef"
      >
        <el-form-item label="工项目名称">
          <el-input
            placeholder="请输入项目名称"
            :value="props.projectView.project.name"
            disabled
          />
        </el-form-item>
        <el-form-item label="材料类别" prop="materialClassifySectionId">
          <el-cascader
            disabled
            v-model="form.materialClassifySectionId"
            :options="materialClassifyOption"
            :props="cascaderProps"
            style="width: 100%"
            @change="onMaterialClassifySectionChange"
          />
        </el-form-item>

        <el-form-item label="名称" prop="name">
          <el-select
            disabled
            v-model="form.name"
            placeholder="Activity zone"
            clearable
            @change="onMaterialChanged"
          >
            <el-option
              v-for="(materialItem, materialIndex) in materialViewList"
              :label="materialItem.material.name"
              :value="materialItem"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="编号" prop="item_mark">
          <el-input disabled v-model="form.itemMark" placeholder="请输入材料编号" />
        </el-form-item>

        <el-form-item label="位置" prop="location">
          <el-input
            v-model="form.location"
            type="textarea"
            disabled
            placeholder="请输入材料位置"
          />
        </el-form-item>

        <el-form-item label="技术要求" prop="technology">
          <el-input
            v-model="form.technology"
            type="textarea"
            disabled
            placeholder="请输入技术要求"
          />
        </el-form-item>

        <el-form-item label="材料材质" prop="material">
          <el-input disabled  v-model="form.material" placeholder="请输入材料属性" />
        </el-form-item>

        <el-form-item label="颜色" prop="color">
          <el-input disabled v-model="form.color" placeholder="请输入颜色" />
        </el-form-item>

        <el-form-item label="规格" prop="dimension">
          <el-input disabled v-model="form.dimension" placeholder="请输入规格" />
        </el-form-item>

        <el-form-item label="防火等级" prop="fire_rating">
          <el-input disabled v-model="form.fireRating" placeholder="请输入防火等级" />
        </el-form-item>

        <el-form-item label="施工要求" prop="installation">
          <el-input
            v-model="form.installation"
            type="textarea"
            disabled
            placeholder="请输入施工要求"
          />
        </el-form-item>

        <el-form-item label="样本图片">
          <div>
            <el-upload
            
              ref="upload"
              v-model:file-list="fileList"
              action=""
              :limit="10"
              disabled
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
          </div>
        </el-form-item>

        <el-form-item label="材料数量" prop="quantity">
          <el-input v-model="form.materialCount" placeholder="请输入材料数量" />
        </el-form-item>

        <el-form-item label="数量单位" prop="unit">
          <el-input v-model="form.materialUnit" placeholder="请输入数量单位" />
        </el-form-item>

        <!--显示该材料类别的品牌-->
        <el-form-item label="参考品牌(公共库)" prop="brandId">
          <el-checkbox-group v-model="form.publicBrandIds">
            <el-checkbox
              v-for="(brandItem, index) in brandPublicViewList"
              :key="index"
              :label="brandItem.brandView.brand.name"
              :value="brandItem.brandPublic.id"
            />
          </el-checkbox-group>
        </el-form-item>

        <el-form-item label="参考品牌(私有库)" prop="brandId">
          <div>
            <!--
            <div style="display: flex">
              <el-checkbox-group v-model="form.projectBrandIds">
                <el-checkbox
                  v-for="(brandItem, index) in projectBrandList"
                  :key="index"
                  :label="brandItem.brandView.brand.name"
                  :value="brandItem.projectBrand.id"
                />
              </el-checkbox-group>
            </div>
            -->

            <!--显示内容-->
            <div
              class="project-container"
              v-if="form.materialClassifySectionId || form.name"
            >
              <el-button
                @click="onNewPrivateBrandButtonClick"
                :icon="Edit"
                size="small"
                :disabled="!form.materialClassifySectionId || !form.name"
                >增加</el-button
              >
              <el-table :data="projectBrandList" style="width: 100%" stripe>
                <el-table-column label="大类-专业" width="100px">
                  <template #default="scope">
                    <div style="display: flex; align-items: center">
                      <span>{{
                        scope.row.projectBrandView.brandView
                          .materialClassifySectionView.materialClassifyDivision
                          .name
                      }}</span>
                    </div>
                  </template>
                </el-table-column>

                <el-table-column label="中类-材料分类" width="200px">
                  <template #default="scope">
                    <div style="display: flex; align-items: center">
                      <span>{{
                        scope.row.projectBrandView.brandView
                          .materialClassifySectionView.materialClassifyGroup
                          .name
                      }}</span>
                    </div>
                  </template>
                </el-table-column>

                <el-table-column label="小类-材料名称" width="200px">
                  <template #default="scope">
                    <div style="display: flex; align-items: center">
                      <span>{{
                        scope.row.projectBrandView.brandView
                          .materialClassifySectionView.materialClassifySection
                          .name
                      }}</span>
                    </div>
                  </template>
                </el-table-column>

                <el-table-column label="定位" width="100px">
                  <template #default="scope">
                    <div style="display: flex; align-items: center">
                      <span>{{
                        scope.row.projectBrandView.brandView.brand.location
                      }}</span>
                    </div>
                  </template>
                </el-table-column>

                <el-table-column label="品牌" width="200px">
                  <template #default="scope">
                    <div style="display: flex; align-items: center">
                      <span>{{
                        scope.row.projectBrandView.brandView.brand.name
                      }}</span>
                    </div>
                  </template>
                </el-table-column>

                <el-table-column label="操作" width="140px">
                  <template #default="scope">
                    <el-button
                      size="small"
                      @click="onRowEditButtonClick(scope.$index, scope.row)"
                    >
                      编辑
                    </el-button>

                    <el-button
                      size="small"
                      type="danger"
                      @click="onRowDeleteButtonClick(scope.$index, scope.row)"
                    >
                      删除
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="onCancel">取消</el-button>
          <el-button type="primary" @click="onOk()"> 确定 </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
