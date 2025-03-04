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

export async function serverMaterialClassifyGroupAdd(
  materialClassifyGroup: IServerMaterialClassifyGroup
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.materialclassifygroup + "add",
      materialClassifyGroup
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverMaterialClassifyGroupDelete(
  materialClassifyGroup: IServerMaterialClassifyGroup
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.materialclassifygroup + "delete",
      materialClassifyGroup
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverMaterialClassifyGroupDeleteById(
  id: string
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.materialclassifygroup + "delete",
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

export async function serverMaterialClassifyGroupUpdate(
  materialClassifyGroup: IServerMaterialClassifyGroup
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.materialclassifygroup + "update",
      materialClassifyGroup
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetMaterialClassifyGroupById(
  id: string
): Promise<IServerResponseData<IServerMaterialClassifyGroup>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerMaterialClassifyGroup>
    >(BASEURL.materialclassifygroup + "get-by-id", {
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

export async function serverGetMaterialClassifyGroupPage(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerMaterialClassifyGroup>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerMaterialClassifyGroup>>
    >(BASEURL.materialclassifygroup + "page", {
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

export async function serverGetMaterialClassifyGroupTree(): Promise<
  IServerResponseData<IServerMaterialClassifyTree>
> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerMaterialClassifyTree>
    >(BASEURL.materialclassifygroup + "get-tree");
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
