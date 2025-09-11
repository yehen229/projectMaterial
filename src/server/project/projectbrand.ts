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

export async function serverGetProjectBrandPageViewByProjectId(
  projectId: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectBrandView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectBrandView>>
    >(BASEURL.projectbrand + "page-view-by-project-id", {
      params: {
        projectId: projectId,
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

export async function serverGetProjectBrandPageViewByProjectIdAndPosition(
  projectId: string,
  position: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectBrandView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectBrandView>>
    >(BASEURL.projectbrand + "page-view-by-project-id-position", {
      params: {
        projectId: projectId,
        position: position,
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

export async function serverGetProjectBrandPageViewByProjectIdAndBrandName(
  projectId: string,
  brandName: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectBrandView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectBrandView>>
    >(BASEURL.projectbrand + "page-view-by-project-id-brand-name", {
      params: {
        projectId: projectId,
        brandName: brandName,
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

export async function serverPrivateBrandExcelAdd(
  projectId: string,
  formData: FormData
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.projectbrand + "PrivateExceladd?projectId=" + projectId,
      formData,
      {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      }
    );
    return res;
  } catch (err) {
    console.error(err);
    throw err;
  }
}

export async function serverPrivateBrandExcelDown(
  projectId: string
)
  : Promise<Blob> {
  console.log("请求axios了--------------");
  try {
    console.log("请求axiostry了--------------");
    // 发起请求，设置 responseType 为 'blob'
    let res = await axios.post(BASEURL.projectbrand + "PrivateExceldown?projectId=" + projectId,
      null,
      {
        responseType: 'blob' // 这是关键设置
      });
    const blob = res instanceof Blob ? res : res.data;
    console.log("res.data 类型:", typeof res.data);
    console.log("接收到的响应res---============:", res);
    // 验证 Blob 对象

    // 直接返回 res.data，它已经是 Blob
    return blob;
  } catch (err) {
    console.error('下载Excel文件出错:', err);
    throw err; // 重新抛出错误
  }
}