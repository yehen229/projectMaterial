import { BASEURL, axios } from "@/http";

import { IServerResponseData, IServerPage, ICaptcha } from "../types/System";

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
  IServerBrand,
  IServerBrandPublic,
  IServerBrandPublicView,
} from "@/server/types/system/brand";

export async function serverMaterialAdd(
  material: IServerMaterial
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.material + "add",
      material
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverMaterialAddForm(
  materialForm: IServerMaterialForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.material + "add-form",
      materialForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverMaterialDelete(
  material: IServerMaterial
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.material + "delete",
      material
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverMaterialDeleteById(
  id: string
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.material + "delete",
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

export async function serverMaterialUpdate(
  material: IServerMaterial
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.material + "update",
      material
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverMaterialUpdateForm(
  materialForm: IServerMaterialForm
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.material + "update-form",
      materialForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetMaterialById(
  id: string
): Promise<IServerResponseData<IServerMaterial>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerMaterial>>(
      BASEURL.material + "get-by-id",
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

export async function serverGetMaterialViewById(
  id: string
): Promise<IServerResponseData<IServerMaterialView>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerMaterialView>>(
      BASEURL.material + "get-view-by-id",
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

export async function serverGetMaterialListByMaterialClassifySectionId(
  materialClassifySectionId: string
): Promise<IServerResponseData<IServerMaterialView[]>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerMaterialView[]>>(
      BASEURL.material + "list-view-by-material-classify-section-id",
      {
        params: {
          materialClassifySectionId: materialClassifySectionId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetMaterialPage(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerMaterial>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerMaterial>>
    >(BASEURL.material + "page", {
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

export async function serverGetMaterialPageView(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerMaterialView>>
    >(BASEURL.material + "page-view", {
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

export async function serverGetMaterialPageViewByName(
  name: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerMaterialView>>
    >(BASEURL.material + "page-view-by-name", {
      params: {
        name: name,
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

export async function serverGetMaterialPageViewByLocation(
  location: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerMaterialView>>
    >(BASEURL.material + "page-view-by-location", {
      params: {
        location: location,
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

export async function serverGetMaterialPageViewByItemMark(
  itemMark: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerMaterialView>>
    >(BASEURL.material + "page-view-by-item-mark", {
      params: {
        itemMark: itemMark,
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

export async function serverGetMaterialPageViewByClassifySectionId(
  classifySectionId: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerMaterialView>>
    >(BASEURL.material + "page-view-by-classify-section-id", {
      params: {
        classifySectionId: classifySectionId,
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
