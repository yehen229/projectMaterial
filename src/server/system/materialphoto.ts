import { BASEURL, axios } from "@/http";

import { IServerResponseData, IServerPage, ICaptcha } from "../types/System";

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
} from "@/server/types/system/material";

import {
  IServerBrand,
  IServerBrandPublic,
  IServerBrandPublicView,
} from "@/server/types/system/brand";

import { exportFile } from "@/server/exportfile";

export async function serverMaterialPhotoAdd(
  materialPhoto: IServerMaterialPhoto
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.materialphoto + "add",
      materialPhoto
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverMaterialPhotoDelete(
  materialPhoto: IServerMaterialPhoto
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.materialphoto + "delete",
      materialPhoto
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverMaterialPhotoDeleteById(
  id: string
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.materialphoto + "delete",
      {
        id: id,
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverMaterialPhotoUpdate(
  materialPhoto: IServerMaterialPhoto
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.materialphoto + "update",
      materialPhoto
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverMaterialPhotoAddUploadTempFiles(
  files: FormData
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.materialphoto + "add-material-photo-temp-file",
      files
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverMaterialPhotoDeleteUploadTempFiles(
  files: FormData
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.materialphoto + "delete-material-photo-temp-file",
      files
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverProjectMaterialPhotoAddUploadTempFiles(
  files: FormData
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.materialphoto + "add-project-material-photo-temp-file",
      files
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverProjectMaterialPhotoDeleteUploadTempFiles(
  files: FormData
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.materialphoto + "delete-project-material-photo-temp-file",
      files
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetMaterialPhotoById(
  id: string
): Promise<IServerResponseData<IServerMaterialPhoto>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerMaterialPhoto>>(
      BASEURL.materialphoto + "get-by-id",
      {
        params: {
          id: id,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetMaterialPhotoListByMaterialId(
  materialId: string
): Promise<IServerResponseData<IServerMaterialPhoto[]>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerMaterialPhoto[]>>(
      BASEURL.materialphoto + "get-by-material-id",
      {
        params: {
          materialId: materialId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetMaterialPhotoFileById(
  materialPhotoId: string
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.get<any, IServerResponseData<string>>(
      BASEURL.materialphoto + "download-photo-file-in-base64",
      {
        params: {
          materialPhotoId: materialPhotoId,
        },
      }
    );

    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export function serverGetMaterialPhotoFileUrl(materialPhotoId: string) {
  return (
    BASEURL.apiUrl +
    BASEURL.materialphoto +
    "download-photo-file?materialPhotoId=" +
    materialPhotoId
  );
}

export async function serverGetMaterialPhotoPage(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerMaterialPhoto>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerMaterialPhoto>>
    >(BASEURL.materialphoto + "page", {
      params: {
        pageNo: pageNo,
        pageSize: pageSize,
      },
    });
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
