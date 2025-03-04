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
import { IServerAppearanceReviewForm } from "../../../server/types/project/review";

const router = useRouter();
const materialViewList = ref<IServerMaterialView[]>();
const brandPublicViewList = ref<IServerBrandPublicView[]>();
const projectBrandList = ref<IServerProjectBrandView[]>();

interface Props {
  dialogVisible: boolean; //对话框是否可见
  showDiffProjectMaterialView: IServerProjectMaterialView;
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
  materialPhotoViewList: [] as IServerMaterialPhotoView[], //修改后的图片
  materialOriginPhotoViewList: [] as IServerMaterialPhotoView[], //基础材料的图片
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

  form.materialId =
    props.showDiffProjectMaterialView.projectMaterial.materialId;
  form.materialClassifySectionId =
    props.showDiffProjectMaterialView.material.materialClassifySectionId;
  form.name = props.showDiffProjectMaterialView.material.name;
  form.itemMark = props.showDiffProjectMaterialView.material.itemMark;
  form.location = props.showDiffProjectMaterialView.material.location;
  form.technology = props.showDiffProjectMaterialView.material.technology;
  form.material = props.showDiffProjectMaterialView.material.material;
  form.color = props.showDiffProjectMaterialView.material.color;
  form.dimension = props.showDiffProjectMaterialView.material.dimension;
  form.fireRating = props.showDiffProjectMaterialView.material.fireRating;
  form.installation = props.showDiffProjectMaterialView.material.installation;
  form.materialCount =
    props.showDiffProjectMaterialView.projectMaterial.materialCount;
  form.materialUnit =
    props.showDiffProjectMaterialView.projectMaterial.materialUnit;

  await getMaterialViewListFromSever();
  await getbrandPublicViewListFromSever();

  //公共品牌
  form.publicBrandId.length = 0;
  if (props.showDiffProjectMaterialView.projectMaterialBrandPublicViewList)
    for (
      let i = 0;
      i <
      props.showDiffProjectMaterialView.projectMaterialBrandPublicViewList
        .length;
      i++
    ) {
      form.publicBrandId.push(
        props.showDiffProjectMaterialView.projectMaterialBrandPublicViewList[i]
          .brandPublicView.brandPublic.id
      );
    }

  //私有品牌
  form.projectBrandId.length = 0;
  if (props.showDiffProjectMaterialView.projectMaterialBrandPrivateViewList)
    for (
      let i = 0;
      i <
      props.showDiffProjectMaterialView.projectMaterialBrandPrivateViewList
        .length;
      i++
    ) {
      form.projectBrandId.push(
        props.showDiffProjectMaterialView.projectMaterialBrandPrivateViewList[i]
          .projectMaterialBrandPrivate.projectBrandId
      );
    }

  console.log(form.projectBrandId);

  //修改后的样本照片
  await getMaterialViewListFromSever();
  const retView = await serverGetMaterialViewById(form.materialId);
  if (retView && retView.code == 200) {
    form.materialPhotoViewList = retView.data.materialPhotoViewList;
  }

  //基础材料的样本照片
  const retViewOrigin = await serverGetMaterialViewById(
    props.showDiffProjectMaterialView.materialOrigin.id
  );

  console.log(retViewOrigin);
  if (retViewOrigin && retViewOrigin.code == 200) {
    form.materialOriginPhotoViewList = retViewOrigin.data.materialPhotoViewList;
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

const getProjectBrandListFromSever = async () => {
  const projectId = props.showDiffProjectMaterialView.project.id;
  const ret1 = await serverGetProjectBrandViewListByProjectId(projectId);

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
      <div class="content">
        <el-row :gutter="20">
          <el-col :span="4"> </el-col>
          <el-col :span="10">
            <span style="font-weight: bold">修改前</span>
          </el-col>
          <el-col :span="10">
            <span style="font-weight: bold">修改后</span>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="4">
            <div class="title">工项目名称</div>
          </el-col>
          <el-col :span="20">
            {{ props.showDiffProjectMaterialView.project.name }}
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="4"> <div class="title">材料类别</div></el-col>
          <el-col :span="20">
            <el-cascader
              v-model="form.materialClassifySectionId"
              :options="materialClassifyOption"
              :props="cascaderProps"
              style="width: 100%"
              @change="onMaterialClassifySectionChange"
              disabled
            />
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="4"> <div class="title">名称</div> </el-col>
          <el-col :span="20">
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
          </el-col>
        </el-row>

        <!--编号-->
        <el-row :gutter="20">
          <el-col :span="4">
            <div
              v-if="
                props.showDiffProjectMaterialView.material.itemMark !==
                props.showDiffProjectMaterialView.materialOrigin.itemMark
              "
              class="title-diff"
            >
              编号
            </div>
            <div v-else>
              <div class="title">编号</div>
            </div>
          </el-col>
          <el-col :span="10"
            >{{ props.showDiffProjectMaterialView.materialOrigin.itemMark }}
          </el-col>

          <el-col :span="10">
            <div
              v-if="
                props.showDiffProjectMaterialView.material.itemMark !==
                props.showDiffProjectMaterialView.materialOrigin.itemMark
              "
              style="color: yellow; background-color: #606266"
            >
              {{ props.showDiffProjectMaterialView.material.itemMark }}
            </div>
            <div v-else>
              {{ props.showDiffProjectMaterialView.material.itemMark }}
            </div>
          </el-col>
        </el-row>

        <!--位置-->
        <el-row :gutter="20">
          <el-col :span="4">
            <div
              v-if="
                props.showDiffProjectMaterialView.material.location !==
                props.showDiffProjectMaterialView.materialOrigin.location
              "
              class="title-diff"
            >
              位置
            </div>
            <div v-else>
              <div class="title">位置</div>
            </div>
          </el-col>
          <el-col :span="10"
            >{{ props.showDiffProjectMaterialView.materialOrigin.location }}
          </el-col>

          <el-col :span="10">
            <div
              v-if="
                props.showDiffProjectMaterialView.material.location !==
                props.showDiffProjectMaterialView.materialOrigin.location
              "
              style="color: yellow; background-color: #606266"
            >
              {{ props.showDiffProjectMaterialView.material.location }}
            </div>
            <div v-else>
              {{ props.showDiffProjectMaterialView.material.location }}
            </div>
          </el-col>
        </el-row>

        <!--技术要求-->
        <el-row :gutter="20">
          <el-col :span="4">
            <div
              v-if="
                props.showDiffProjectMaterialView.material.technology !==
                props.showDiffProjectMaterialView.materialOrigin.technology
              "
              class="title-diff"
            >
              技术要求
            </div>
            <div v-else>
              <div class="title">技术要求</div>
            </div>
          </el-col>
          <el-col :span="10">
            {{ props.showDiffProjectMaterialView.materialOrigin.technology }}
          </el-col>

          <el-col :span="10">
            <div
              v-if="
                props.showDiffProjectMaterialView.material.technology !==
                props.showDiffProjectMaterialView.materialOrigin.technology
              "
              style="color: yellow; background-color: #606266"
            >
              {{ props.showDiffProjectMaterialView.material.technology }}
            </div>
            <div v-else>
              {{ props.showDiffProjectMaterialView.material.technology }}
            </div>
          </el-col>
        </el-row>

        <!--材料材质-->
        <el-row :gutter="20">
          <el-col :span="4">
            <div
              v-if="
                props.showDiffProjectMaterialView.material.material !==
                props.showDiffProjectMaterialView.materialOrigin.material
              "
              class="title-diff"
            >
              材料材质
            </div>
            <div v-else>
              <div class="title">材料材质</div>
            </div>
          </el-col>
          <el-col :span="10">
            {{ props.showDiffProjectMaterialView.materialOrigin.material }}
          </el-col>

          <el-col :span="10">
            <div
              v-if="
                props.showDiffProjectMaterialView.material.material !==
                props.showDiffProjectMaterialView.materialOrigin.material
              "
              style="color: yellow; background-color: #606266"
            >
              {{ props.showDiffProjectMaterialView.material.material }}
            </div>
            <div v-else>
              {{ props.showDiffProjectMaterialView.material.material }}
            </div>
          </el-col>
        </el-row>

        <!--颜色-->
        <el-row :gutter="20">
          <el-col :span="4">
            <div
              v-if="
                props.showDiffProjectMaterialView.material.color !==
                props.showDiffProjectMaterialView.materialOrigin.color
              "
              class="title-diff"
            >
              颜色
            </div>
            <div v-else>
              <div class="title">颜色</div>
            </div>
          </el-col>
          <el-col :span="10">
            {{ props.showDiffProjectMaterialView.materialOrigin.color }}
          </el-col>

          <el-col :span="10">
            <div
              v-if="
                props.showDiffProjectMaterialView.material.color !==
                props.showDiffProjectMaterialView.materialOrigin.color
              "
              style="color: yellow; background-color: #606266"
            >
              {{ props.showDiffProjectMaterialView.material.color }}
            </div>
            <div v-else>
              {{ props.showDiffProjectMaterialView.material.color }}
            </div>
          </el-col>
        </el-row>

        <!--规格-->
        <el-row :gutter="20">
          <el-col :span="4">
            <div
              v-if="
                props.showDiffProjectMaterialView.material.dimension !==
                props.showDiffProjectMaterialView.materialOrigin.dimension
              "
              class="title-diff"
            >
              规格
            </div>
            <div v-else>
              <div class="title">规格</div>
            </div>
          </el-col>
          <el-col :span="10">
            {{ props.showDiffProjectMaterialView.materialOrigin.dimension }}
          </el-col>

          <el-col :span="10">
            <div
              v-if="
                props.showDiffProjectMaterialView.material.dimension !==
                props.showDiffProjectMaterialView.materialOrigin.dimension
              "
              style="color: yellow; background-color: #606266"
            >
              {{ props.showDiffProjectMaterialView.material.dimension }}
            </div>
            <div v-else>
              {{ props.showDiffProjectMaterialView.material.dimension }}
            </div>
          </el-col>
        </el-row>

        <!--防火等级-->
        <el-row :gutter="20">
          <el-col :span="4">
            <div
              v-if="
                props.showDiffProjectMaterialView.material.fireRating !==
                props.showDiffProjectMaterialView.materialOrigin.fireRating
              "
              class="title-diff"
            >
              防火等级
            </div>
            <div v-else>
              <div class="title">防火等级</div>
            </div>
          </el-col>
          <el-col :span="10">
            {{ props.showDiffProjectMaterialView.materialOrigin.fireRating }}
          </el-col>

          <el-col :span="10">
            <div
              v-if="
                props.showDiffProjectMaterialView.material.fireRating !==
                props.showDiffProjectMaterialView.materialOrigin.fireRating
              "
              style="color: yellow; background-color: #606266"
            >
              {{ props.showDiffProjectMaterialView.material.fireRating }}
            </div>
            <div v-else>
              {{ props.showDiffProjectMaterialView.material.fireRating }}
            </div>
          </el-col>
        </el-row>

        <!--施工要求-->
        <el-row :gutter="20">
          <el-col :span="4">
            <div
              v-if="
                props.showDiffProjectMaterialView.material.installation !==
                props.showDiffProjectMaterialView.materialOrigin.installation
              "
              class="title-diff"
            >
              施工要求
            </div>
            <div v-else>
              <div class="title">施工要求</div>
            </div>
          </el-col>
          <el-col :span="10">
            {{ props.showDiffProjectMaterialView.materialOrigin.installation }}
          </el-col>

          <el-col :span="10">
            <div
              v-if="
                props.showDiffProjectMaterialView.material.installation !==
                props.showDiffProjectMaterialView.materialOrigin.installation
              "
              style="color: yellow; background-color: #606266"
            >
              {{ props.showDiffProjectMaterialView.material.installation }}
            </div>
            <div v-else>
              {{ props.showDiffProjectMaterialView.material.installation }}
            </div>
          </el-col>
        </el-row>

        <!--样本图片-->
        <el-row :gutter="20">
          <el-col :span="4">
            <div
              v-if="
                props.showDiffProjectMaterialView.material.installation !==
                props.showDiffProjectMaterialView.materialOrigin.installation
              "
              class="title-diff"
            >
              样本图片
            </div>
            <div v-else>
              <div class="title">样本图片</div>
            </div>
          </el-col>
          <el-col :span="10">
            <div
              v-for="(
                photoItem, photoIndex
              ) in form.materialOriginPhotoViewList"
              :key="photoIndex"
            >
              <MaterialPhoto :photoItem="photoItem"></MaterialPhoto>
            </div>
          </el-col>

          <el-col :span="10">
            <div
              v-for="(photoItem, photoIndex) in form.materialPhotoViewList"
              :key="photoIndex"
            >
              <MaterialPhoto :photoItem="photoItem"></MaterialPhoto>
            </div>
          </el-col>
        </el-row>

        <!--材料数量-->
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="title">材料数量</div>
          </el-col>
          <el-col :span="20">
            {{
              props.showDiffProjectMaterialView.projectMaterial.materialCount
            }}
          </el-col>
        </el-row>

        <!--数量单位-->
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="title">数量单位</div>
          </el-col>
          <el-col :span="20">
            {{ props.showDiffProjectMaterialView.projectMaterial.materialUnit }}
          </el-col>
        </el-row>

        <!--参考品牌(公共库)-->
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="title">参考品牌(公共库)</div>
          </el-col>
          <el-col :span="20">
            <el-checkbox-group v-model="form.publicBrandId" disabled>
              <el-checkbox
                v-for="(brandItem, index) in brandPublicViewList"
                :key="index"
                :label="brandItem.brandView.brand.name"
                :value="brandItem.brandPublic.id"
              />
            </el-checkbox-group>
          </el-col>
        </el-row>

        <!--参考品牌(私有库)-->
        <el-row :gutter="20">
          <el-col :span="4">
            <div class="title">参考品牌(私有库)</div>
          </el-col>
          <el-col :span="10"></el-col>
          <el-col :span="10">
            <el-checkbox-group v-model="form.projectBrandId" disabled>
              <el-checkbox
                v-for="(brandItem, index) in projectBrandList"
                :key="index"
                :label="brandItem.brandView.brand.name"
                :value="brandItem.projectBrand.id"
              />
            </el-checkbox-group>
          </el-col>
        </el-row>
      </div>

      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="onOk()"> 确定 </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.el-row {
  margin-bottom: 20px;
}

.el-row:last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.title {
  text-align: right;
  margin-right: 20px;
}

.title-diff {
  text-align: right;
  margin-right: 20px;
  color: red;
}

.content {
  white-space: pre-wrap;
  line-height: 1.5;
}
</style>
