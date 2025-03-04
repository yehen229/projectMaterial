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

export async function serverBrandAdd(
  brand: IServerBrand
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.brand + "add",
      brand
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverBrandDelete(
  brand: IServerBrand
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.brand + "delete",
      brand
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverBrandDeleteById(
  id: string
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.brand + "delete",
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

export async function serverBrandUpdate(
  brand: IServerBrand
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.brand + "update",
      brand
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetBrandrById(
  id: string
): Promise<IServerResponseData<IServerBrand>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerBrand>>(
      BASEURL.brand + "get-by-id",
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

export async function serverGetBrandPage(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerBrand>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerBrand>>
    >(BASEURL.brand + "page", {
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
