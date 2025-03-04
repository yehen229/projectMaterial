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
import MaterialPhoto from "@/components/system/meterial/MaterialPhoto.vue";

const router = useRouter();
const materialViewList = ref<IServerMaterialView[]>();
const brandPublicViewList = ref<IServerBrandPublicView[]>();
const projectBrandList = ref<IServerProjectMaterialBrandPrivateView[]>();

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
  await getProjectBrandListFromSever(
    props.projectMaterialView.project.id,
    form.materialId
  );

  //公共品牌
  form.publicBrandId.length = 0;
  if (props.projectMaterialView.projectMaterialBrandPublicViewList)
    for (
      let i = 0;
      i < props.projectMaterialView.projectMaterialBrandPublicViewList.length;
      i++
    ) {
      form.publicBrandId.push(
        props.projectMaterialView.projectMaterialBrandPublicViewList[i]
          .brandPublicView.brandPublic.id
      );
    }

  //私有品牌
  form.projectBrandId.length = 0;
  if (props.projectMaterialView.projectMaterialBrandPrivateViewList)
    for (
      let i = 0;
      i < props.projectMaterialView.projectMaterialBrandPrivateViewList.length;
      i++
    ) {
      form.projectBrandId.push(
        props.projectMaterialView.projectMaterialBrandPrivateViewList[i]
          .projectMaterialBrandPrivate.projectBrandId
      );
    }

  console.log(form.projectBrandId);

  //样本照片
  await getMaterialViewListFromSever();
  const retView = await serverGetMaterialViewById(form.materialId);
  if (retView && retView.code == 200) {
    form.materialPhotoViewList = retView.data.materialPhotoViewList;
  }
};

const handleClose = () => {
  emit("onDilalogCancel");
};
const onOk = () => {
  emit("onDilalogCancel");
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

const getProjectBrandListFromSever = async (
  projectId: string,
  materialId: string
) => {
  //const ret1 = await serverGetProjectBrandViewListByProjectId(projectId);

  const ret1 =
    await serverGetProjectMaterialBrandPrivateViewListByProjectIdAndMaterialId(
      projectId,
      materialId
    );

  if (ret1 && ret1.code == 200) {
    projectBrandList.value = ret1.data;
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
</script>

<template>
  <div>
    <el-dialog
      title="项目材料详情"
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
            v-model="form.materialClassifySectionId"
            :options="materialClassifyOption"
            :props="cascaderProps"
            style="width: 100%"
            @change="onMaterialClassifySectionChange"
            disabled
          />
        </el-form-item>

        <el-form-item label="名称" prop="name">
          <el-select
            v-model="form.name"
            placeholder="Activity zone"
            clearable
            @change="onMaterialChanged"
            disabled
          >
            <el-option
              v-for="(materialItem, materialIndex) in materialViewList"
              :label="materialItem.material.name"
              :value="materialItem"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="编号" prop="item_mark">
          <el-input
            v-model="form.itemMark"
            placeholder="请输入材料编号"
            disabled
          />
        </el-form-item>

        <el-form-item label="位置" prop="location">
          <el-input
            v-model="form.location"
            type="textarea"
            placeholder="请输入材料位置"
            disabled
          />
        </el-form-item>

        <el-form-item label="技术要求" prop="technology">
          <el-input
            v-model="form.technology"
            type="textarea"
            placeholder="请输入技术要求"
            disabled
          />
        </el-form-item>

        <el-form-item label="材料材质" prop="material">
          <el-input
            v-model="form.material"
            placeholder="请输入材料属性"
            disabled
          />
        </el-form-item>

        <el-form-item label="颜色" prop="color">
          <el-input v-model="form.color" placeholder="请输入颜色" disabled />
        </el-form-item>

        <el-form-item label="规格" prop="dimension">
          <el-input
            v-model="form.dimension"
            placeholder="请输入规格"
            disabled
          />
        </el-form-item>

        <el-form-item label="防火等级" prop="fire_rating">
          <el-input
            v-model="form.fireRating"
            placeholder="请输入防火等级"
            disabled
          />
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
          <div
            v-for="(photoItem, photoIndex) in form.materialPhotoViewList"
            :key="photoIndex"
          >
            <MaterialPhoto :photoItem="photoItem"></MaterialPhoto>
          </div>
        </el-form-item>

        <el-form-item label="材料数量" prop="quantity">
          <el-input
            v-model="form.materialCount"
            placeholder="请输入材料数量"
            disabled
          />
        </el-form-item>

        <el-form-item label="数量单位" prop="unit">
          <el-input
            v-model="form.materialUnit"
            placeholder="请输入数量单位"
            disabled
          />
        </el-form-item>

        <!--显示该材料类别的品牌-->
        <el-form-item label="参考品牌(公共库)" prop="brandId">
          <el-checkbox-group v-model="form.publicBrandId" disabled>
            <el-checkbox
              v-for="(brandItem, index) in brandPublicViewList"
              :key="index"
              :label="brandItem.brandView.brand.name"
              :value="brandItem.brandPublic.id"
            />
          </el-checkbox-group>
        </el-form-item>

        <el-form-item label="参考品牌(私有库)" prop="brandId">
          <el-checkbox-group v-model="form.projectBrandId" disabled>
            <el-checkbox
              v-for="(brandItem, index) in projectBrandList"
              :key="index"
              :label="brandItem.projectBrandView.brandView.brand.name"
              :value="brandItem.projectBrandView.projectBrand.id"
            />
          </el-checkbox-group>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="onOk()"> 确定 </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
