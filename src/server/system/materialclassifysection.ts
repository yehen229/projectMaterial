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
  IServerMaterialClassifyTreeByName,
  IServerMaterialPhoto,
  IServerMaterialPhotoView,
} from "@/server/types/system/material";

import {
  IServerBrand,
  IServerBrandPublic,
  IServerBrandPublicView,
} from "@/server/types/system/brand";

export async function serverMaterialClassifySectionAdd(
  materialClassifySection: IServerMaterialClassifySection
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.materialclassifysection + "add",
      materialClassifySection
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverMaterialClassifySectionDelete(
  materialClassifySection: IServerMaterialClassifySection
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.materialclassifysection + "delete",
      materialClassifySection
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverMaterialClassifySectionDeleteById(
  id: string
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.materialclassifysection + "delete",
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

export async function serverMaterialClassifySectionUpdate(
  materialClassifySection: IServerMaterialClassifySection
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.materialclassifysection + "update",
      materialClassifySection
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetMaterialClassifySectionById(
  id: string
): Promise<IServerResponseData<IServerMaterialClassifySection>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerMaterialClassifySection>
    >(BASEURL.materialclassifysection + "get-by-id", {
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

export async function serverGetMaterialClassifySectionViewById(
  id: string
): Promise<IServerResponseData<IServerMaterialClassifySectionView>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerMaterialClassifySectionView>
    >(BASEURL.materialclassifysection + "get-view-by-id", {
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

export async function serverGetMaterialClassifyTree(): Promise<
  IServerResponseData<IServerMaterialClassifyTree>
> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerMaterialClassifyTree>
    >(BASEURL.materialclassifysection + "get-tree");
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetMaterialClassifyTreeByName(
  name: string
): Promise<
  IServerResponseData<IServerMaterialClassifyTree>
> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerMaterialClassifyTree>
    >(BASEURL.materialclassifysection + "get-tree-by-name", {
      params: { name: name }
    });
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetMaterialClassifySectionPage(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerMaterialClassifySection>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerMaterialClassifySection>>
    >(BASEURL.materialclassifysection + "page", {
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
