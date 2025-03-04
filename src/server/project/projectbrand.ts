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

import {
  IServerProject,
  IServerProjectBrand,
  IServerProjectBrandView,
  IServerProjectBrandForm,
  IServerProjectMaterialBrandPrivateView,
} from "@/server/types/project/project";

export async function serverProjectBrandAdd(
  projectBrand: IServerProjectBrand
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectbrand + "add",
      projectBrand
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverProjectBrandAddForm(
  projectBrandForm: IServerProjectBrandForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectbrand + "add-form",
      projectBrandForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverProjectBrandDelete(
  projectBrand: IServerProjectBrand
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.projectbrand + "delete",
      projectBrand
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverProjectBrandDeleteById(
  id: string
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.projectbrand + "delete",
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

export async function serverProjectBrandUpdate(
  projectBrand: IServerProjectBrand
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.projectbrand + "update",
      projectBrand
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetProjectBrandById(
  id: string
): Promise<IServerResponseData<IServerProjectBrand>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerProjectBrand>>(
      BASEURL.projectbrand + "get-by-id",
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

export async function serverGetProjectBrandListByProjectId(
  projectId: string
): Promise<IServerResponseData<IServerProjectBrand[]>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerProjectBrand[]>>(
      BASEURL.projectbrand + "get-list-by-project-id",
      {
        params: {
          projectId: projectId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetProjectBrandViewListByProjectId(
  projectId: string
): Promise<IServerResponseData<IServerProjectBrandView[]>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectBrandView[]>
    >(BASEURL.projectbrand + "get-view-list-by-project-id", {
      params: {
        projectId: projectId,
      },
    });
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetProjectMaterialBrandPrivateViewListByProjectIdAndMaterialId(
  projectId: string,
  materialId: string
): Promise<IServerResponseData<IServerProjectMaterialBrandPrivateView[]>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectMaterialBrandPrivateView[]>
    >(
      BASEURL.projectmaterialbrandprivate +
        "get-view-by-project-id-and-material-id",
      {
        params: {
          projectId: projectId,
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

export async function serverGetProjectBrandPage(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectBrand>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectBrand>>
    >(BASEURL.projectbrand + "page", {
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

export async function serverGetProjectBrandPageView(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectBrandView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectBrandView>>
    >(BASEURL.projectbrand + "page-view", {
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
