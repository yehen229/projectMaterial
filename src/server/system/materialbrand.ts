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

export async function serverMaterialBrandAdd(
  materialBrand: IServerMaterialBrand
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.materialbrand + "add",
      materialBrand
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverMaterialBrandDelete(
  materialBrand: IServerMaterialBrand
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.materialbrand + "delete",
      materialBrand
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverMaterialBrandDeleteById(
  id: string
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.materialbrand + "delete",
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

export async function serverMaterialBrandUpdate(
  materialBrand: IServerMaterialBrand
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.materialbrand + "update",
      materialBrand
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetMaterialBrandById(
  id: string
): Promise<IServerResponseData<IServerMaterialBrand>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerMaterialBrand>>(
      BASEURL.materialbrand + "get-by-id",
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

export async function serverGetMaterialBrandPage(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerMaterialBrand>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerMaterialBrand>>
    >(BASEURL.materialbrand + "page", {
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
