<script setup lang="ts">
/**
 * 对单位进行管理 *
 * 主要功能：增删改查
 */

import { computed, onMounted, reactive, ref, Ref } from "vue";

import { useRouter } from "vue-router/dist/vue-router";

import store from "@/store";

import type { FormInstance, FormRules } from "element-plus";
import { View, Hide, Search, Plus } from "@element-plus/icons-vue";

import { ElMessage, ElMessageBox } from "element-plus";

//服务器返回到前端的类型
import { IServerPage } from "@/server/types/System";

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
  IServerMaterialView,
} from "@/server/types/system/material";

import {
  serverMaterialAdd,
  serverMaterialAddForm,
  serverMaterialDelete,
  serverMaterialDeleteById,
  serverMaterialUpdate,
  serverGetMaterialById,
  serverGetMaterialPage,
  serverGetMaterialPageView,
  serverGetMaterialPageViewByName,
  serverGetMaterialPageViewByLocation,
  serverGetMaterialPageViewByItemMark,
  serverGetMaterialPageViewByClassifySectionId,
} from "@/server/system/material";

import { serverGetMaterialPhotoFileById } from "@/server/system/materialphoto";

import { getUserPageSize, setUserPageSize } from "@/cookies/user";

interface Props {
  photoItem: IServerMaterialPhotoView;
}

const props = withDefaults(defineProps<Props>(), {});

const imageData = ref("");

onMounted(async () => {
  await getImgUrl();
});

const getImgUrl = async () => {
  let res = await serverGetMaterialPhotoFileById(
    props.photoItem.materialPhoto.id
  );
  console.log(res);

  if (res && res.code == 200) imageData.value = res.data;
  return "";
};
const dialogImageUrl = ref("");
const dialogVisible = ref(false);
const imgRef = ref<HTMLImageElement>();

const onPreview = () => {
  dialogImageUrl.value = imageData.value;
  dialogVisible.value = true;
};

const dialogWidth = ref("1000px");
const onOpen = () => {
  if (imgRef.value?.naturalWidth)
    return (dialogWidth.value = imgRef.value?.naturalWidth + 30 + "px");
};
</script>

<template>
  <el-dialog
    v-model="dialogVisible"
    @open="onOpen"
    :z-index="9999"
    :append-to-body="true"
    :width="dialogWidth"
  >
    <img :src="dialogImageUrl" alt="Preview Image" />
  </el-dialog>

  <img
    ref="imgRef"
    :src="imageData"
    :alt="photoItem.materialPhoto.filePath"
    style="width: 40px"
    @click="onPreview"
  />
</template>

<style scoped></style>
