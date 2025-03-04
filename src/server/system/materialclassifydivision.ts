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

export async function serverMaterialClassifyDivisionAdd(
  materialClassifyDivision: IServerMaterialClassifyDivision
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.materialclassifydivision + "add",
      materialClassifyDivision
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverMaterialClassifyDivisionDelete(
  materialClassifyDivision: IServerMaterialClassifyDivision
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.materialclassifydivision + "delete",
      materialClassifyDivision
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverMaterialClassifyDivisionDeleteById(
  id: string
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.materialclassifydivision + "delete",
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

export async function serverMaterialClassifyDivisionUpdate(
  materialClassifyDivision: IServerMaterialClassifyDivision
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.materialclassifydivision + "update",
      materialClassifyDivision
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetMaterialClassifyDivisionById(
  id: string
): Promise<IServerResponseData<IServerMaterialClassifyDivision>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerMaterialClassifyDivision>
    >(BASEURL.materialclassifydivision + "get-by-id", {
      params: {
        id: id,
      },
    });
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetMaterialClassifyDivisionAllList(): Promise<
  IServerResponseData<IServerMaterialClassifyDivision[]>
> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerMaterialClassifyDivision[]>
    >(BASEURL.materialclassifydivision + "list");
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetMaterialClassifyDivisionPage(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerMaterialClassifyDivision>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerMaterialClassifyDivision>>
    >(BASEURL.materialclassifydivision + "page", {
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
