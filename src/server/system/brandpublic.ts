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

export async function serverBrandPublicAdd(
  brand: IServerBrand
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.brandpublic + "add",
      brand
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverBrandPublicDelete(
  brandpublic: IServerBrandPublic
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.brandpublic + "delete",
      brandpublic
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverBrandPublicDeleteById(
  id: string
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.brandpublic + "delete",
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

export async function serverBrandPublicUpdate(
  brand: IServerBrand
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.brandpublic + "update",
      brand
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}


export async function serverBrandExcelAdd(
  formData: FormData
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any,IServerResponseData<number>>(
      BASEURL.brandpublic + "Exceladd",
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
export async function serverBrandExcelDown()
: Promise<Blob>
{
  console.log("请求axios了--------------");
  try {
    console.log("请求axiostry了--------------");
    // 发起请求，设置 responseType 为 'blob'
    const res = await axios.post(BASEURL.brandpublic + "Exceldown", 
      null, 
      {
      responseType: 'blob' // 这是关键设置
    });
     const blob = res instanceof Blob ? res : res.data;
    console.log("Blob 大小:直接提", blob.size);
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

export async function serverGetBrandPublicById(
  id: string
): Promise<IServerResponseData<IServerBrandPublic>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerBrandPublic>>(
      BASEURL.brandpublic + "get-by-id",
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

export async function serverGetBrandPublicByBrandId(
  brandId: string
): Promise<IServerResponseData<IServerBrand[]>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerBrand[]>>(
      BASEURL.brandpublic + "get-by-brand-id",
      {
        params: {
          brandId: brandId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetBrandPublicByMaterialClassifySectionId(
  materialClassifySectionId: string
): Promise<IServerResponseData<IServerBrandPublicView[]>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerBrandPublicView[]>
    >(BASEURL.brandpublic + "get-by-material-classify-section-id", {
      params: {
        materialClassifySectionId: materialClassifySectionId,
      },
    });
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetBrandPublicPage(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerBrandPublic>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerBrandPublic>>
    >(BASEURL.brandpublic + "page", {
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

export async function serverGetBrandPublicPageView(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerBrandPublicView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerBrandPublicView>>
    >(BASEURL.brandpublic + "page-view", {
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

export async function serverGetBrandPublicPageViewByBrandName(
  brandName: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerBrandPublicView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerBrandPublicView>>
    >(BASEURL.brandpublic + "page-view-by-name", {
      params: {
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

export async function serverGetBrandPublicPageViewByBrandPosition(
  brandPosition: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerBrandPublicView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerBrandPublicView>>
    >(BASEURL.brandpublic + "page-view-by-position", {
      params: {
        brandPosition: brandPosition,
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
