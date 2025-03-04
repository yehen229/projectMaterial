<script setup lang="ts">
import { computed, onMounted, reactive, ref, Ref, watch } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";
import type { Action } from "element-plus";
import type { CascaderValue } from "element-plus";

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
} from "@/server/project/projectbrand";

import {
  serverGetMaterialListByMaterialClassifySectionId,
  serverGetMaterialViewById,
} from "@/server/system/material";
import MaterialPhoto from "@/components/system/meterial/MaterialPhoto.vue";

const router = useRouter();
const materialViewList = ref<IServerMaterialView[]>();
const brandPublicViewList = ref<IServerBrandPublicView[]>();
const projectBrandList = ref<IServerProjectBrandView[]>();

interface Props {
  dialogVisible: boolean; //对话框是否可见
  material: IServerMaterial | undefined;
  project: IServerProject | undefined;
  projectMaterial: IServerProjectMaterial | undefined;
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
  publicBrandId: [] as string[],
  projectBrandId: [] as string[],
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
  materialCount: [
    { required: true, message: "请输入材料数量", trigger: "blur" },
  ],
  materialUnit: [
    { required: true, message: "请输入数量单位", trigger: "blur" },
  ],
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
  if (!props.material || !props.project || !props.projectMaterial) return;
  if (props.material.id !== props.projectMaterial.materialId) return;

  console.log(props.material);

  materialClassifyOption.value = await generateMaterialClassifyOption();
  await getProjectBrandListFromSever();
  console.log(materialClassifyOption.value);

  form.materialId = props.material.id;
  form.materialClassifySectionId = props.material.materialClassifySectionId;
  form.name = props.material.name;
  form.itemMark = props.material.itemMark;
  form.location = props.material.location;
  form.technology = props.material.technology;
  form.material = props.material.material;
  form.color = props.material.color;
  form.dimension = props.material.dimension;
  form.fireRating = props.material.fireRating;
  form.installation = props.material.installation;
  form.materialCount = props.projectMaterial.materialCount;
  form.materialUnit = props.projectMaterial.materialUnit;

  await getMaterialViewListFromSever();
  await getbrandPublicViewListFromSever();

  //样本照片
  await getMaterialViewListFromSever();
  const retView = await serverGetMaterialViewById(form.materialId);
  if (retView && retView.code == 200) {
    form.materialPhotoViewList = retView.data.materialPhotoViewList;
  }

  console.log(form);
};

const handleClose = () => {
  emit("onDilalogCancel");
};
const onOk = () => {
  if (!props.material || !props.project || !props.projectMaterial) {
    ElMessageBox.alert("参数为空，请关闭对话框重新打开", "提示", {
      confirmButtonText: "确定",
    });

    return;
  }

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

  /*
  let brandCount = form.publicBrandId.length + form.projectBrandId.length;
  if (brandCount == 0) {
    ElMessageBox.alert("必须选择一个品牌", "提示", {
      confirmButtonText: "确定",
    });

    return;
  }*/

  console.log("form", form);
  console.log("props", props);

  let projectMaterialForm: IServerProjectMaterialForm = {
    projectMaterial: {
      id: props.projectMaterial.id,
      materialCount: form.materialCount,
      materialId: form.materialId,
      materialOriginId: props.projectMaterial.materialId,
      materialUnit: form.materialUnit,
      projectId: props.project.id,
      companyId: props.projectMaterial.companyId,
      deletedAt: new Date(),
    },
    material: {
      //材料{
      id: props.material.id, //id,主键
      materialClassifySectionId: props.material.materialClassifySectionId, //t_material_classify_section_id
      name: props.material.name, //name,名称名称
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
    publicBrandIds: form.publicBrandId, //参考品牌(公共)，json格式
    projectBrandIds: form.projectBrandId, //参考品牌（项目私有），json格式
    projectBrandViewList: [],
    photoTempDir: "", //照片目录
    photoIds: [], //照片ID
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
  if (!props.material || !props.project || !props.projectMaterial) return;
  const projectId = props.project.id;
  const ret1 = await serverGetProjectBrandViewListByProjectId(projectId);

  if (ret1 && ret1.code == 200) {
    projectBrandList.value = ret1.data;
  }
};

const onMaterialChanged = (materialView: IServerMaterialView) => {
  console.log("onMaterialChanged", materialView);
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
</script>

<template>
  <div>
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
            :value="props.project?.name"
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
          <el-input v-model="form.itemMark" placeholder="请输入材料编号" />
        </el-form-item>

        <el-form-item label="位置" prop="location">
          <el-input
            v-model="form.location"
            type="textarea"
            placeholder="请输入材料位置"
          />
        </el-form-item>

        <el-form-item label="技术要求" prop="technology">
          <el-input
            v-model="form.technology"
            type="textarea"
            placeholder="请输入技术要求"
          />
        </el-form-item>

        <el-form-item label="材料材质" prop="material">
          <el-input v-model="form.material" placeholder="请输入材料属性" />
        </el-form-item>

        <el-form-item label="颜色" prop="color">
          <el-input v-model="form.color" placeholder="请输入颜色" />
        </el-form-item>

        <el-form-item label="规格" prop="dimension">
          <el-input v-model="form.dimension" placeholder="请输入规格" />
        </el-form-item>

        <el-form-item label="防火等级" prop="fire_rating">
          <el-input v-model="form.fireRating" placeholder="请输入防火等级" />
        </el-form-item>

        <el-form-item label="施工要求" prop="installation">
          <el-input
            v-model="form.installation"
            type="textarea"
            placeholder="请输入施工要求"
          />
        </el-form-item>

        <el-form-item label="样本图片">
          <div
            v-for="(photoItem, photoIndex) in form.materialPhotoViewList"
            :key="photoIndex"
          >
            <MaterialPhoto :photoItem="photoItem"></MaterialPhoto>
          </div>
        </el-form-item>

        <el-form-item label="材料数量" prop="materialCount">
          <el-input v-model="form.materialCount" placeholder="请输入材料数量" />
        </el-form-item>

        <el-form-item label="数量单位" prop="materialUnit">
          <el-input v-model="form.materialUnit" placeholder="请输入数量单位" />
        </el-form-item>

        <!--显示该材料类别的品牌-->
        <!--     <el-form-item label="参考品牌(公共库)" prop="brandId">
          <el-checkbox-group v-model="form.publicBrandId">
            <el-checkbox
              v-for="(brandItem, index) in brandPublicViewList"
              :key="index"
              :label="brandItem.brandView.brand.name"
              :value="brandItem.brandPublic.id"
            />
          </el-checkbox-group>
        </el-form-item>

        <el-form-item label="参考品牌(私有库)" prop="brandId">
          <el-checkbox-group v-model="form.projectBrandId">
            <el-checkbox
              v-for="(brandItem, index) in projectBrandList"
              :key="index"
              :label="brandItem.brandView.brand.name"
              :value="brandItem.projectBrand.id"
            />
          </el-checkbox-group>
        </el-form-item>-->
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
