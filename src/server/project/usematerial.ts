import { BASEURL, axios } from "@/http";

import { IServerResponseData, IServerPage, ICaptcha } from "../types/System";
import {
  IServerProject,
  IServerProjectMaterial,
  IServerProjectView,
  IServerProjectMaterialView,
} from "../types/project/project";
import {
  IServerProjectReviewUserView,
  IServerProjectReviewStatistics,
  IServerBuyMaterialView,
} from "../types/project/review";
import {
  exportFile,
  exportFileWithFullFilenameWithExt,
} from "@/server/exportfile";

import {
  IServerUseMaterialView,
  IServerProjectMaterialVerificationDocumentView,
} from "@/server/types/project/review";

export async function serverAddUseProjectMaterialBrandTempFiles(
  files: FormData
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.usematerial + "add-use-material-new-brand-temp-file",
      files
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverDeleteUseProjectMaterialBrandTempFiles(
  files: FormData
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.usematerial + "delete-use-material-new-brand-temp-file",
      files
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverDownloadUseMaterialNewBrandFileById(
  projectId: string,
  useMaterialNewBrandFileId: string,
  downloadFilename: string
) {
  try {
    let res = await axios.get(
      BASEURL.usematerialnewbrandfile +
      "download-use-material-new-brand-file-by-id",
      {
        params: {
          projectId: projectId,
          useMaterialNewBrandFileId: useMaterialNewBrandFileId,
        },
        responseType: "arraybuffer",
      }
    );
    exportFileWithFullFilenameWithExt(
      res,

      downloadFilename
    );
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetUseMaterialPageViewByProject(
  projectId: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerUseMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerUseMaterialView>>
    >(BASEURL.usematerial + "page-use-material-view-by-project-id", {
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
export async function serverGetUseMaterialPageViewByProjectAndName(
  projectId: string,
  name: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerUseMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerUseMaterialView>>
    >(BASEURL.usematerial + "page-use-material-view-by-project-id-and-name", {
      params: {
        projectId: projectId,
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

export async function serverGetUseMaterialPageViewByProjectAndLocation(
  projectId: string,
  location: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerUseMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerUseMaterialView>>
    >(BASEURL.usematerial + "page-use-material-view-by-project-id-and-location", {
      params: {
        projectId: projectId,
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

export async function serverGetUseMaterialPageViewByProjectAndItemMark(
  projectId: string,
  itemMark: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerUseMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerUseMaterialView>>
    >(BASEURL.usematerial + "page-use-material-view-by-project-id-and-item-mark", {
      params: {
        projectId: projectId,
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

export async function serverGetUseMaterialPageViewByProjectAndTechnology(
  projectId: string,
  technology: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerUseMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerUseMaterialView>>
    >(BASEURL.usematerial + "page-use-material-view-by-project-id-and-technology", {
      params: {
        projectId: projectId,
        technology: technology,
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

export async function serverGetUseMaterialPageViewByProjectAndInstallation(
  projectId: string,
  installation: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerUseMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerUseMaterialView>>
    >(BASEURL.usematerial + "page-use-material-view-by-project-id-and-installation", {
      params: {
        projectId: projectId,
        installation: installation,
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

export async function serverGetUseMaterialPageViewByProjectAndBrand(
  projectId: string,
  brand: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerUseMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerUseMaterialView>>
    >(BASEURL.usematerial + "page-use-material-view-by-project-id-and-brand", {
      params: {
        projectId: projectId,
        brand: brand,
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

/**
 *从服务器获得已经购买的、并且没有被禁止使用的物料
 */
export async function serverGetBoughtUseMaterialPageViewByProject(
  projectId: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectMaterialView>>
    >(BASEURL.buymaterial + "page-bought-material-view-by-project-id", {
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

/**
 *从服务器获得已经购买的、并且没有被禁止使用的物料
 */
export async function serverGetBoughtUseMaterialPageViewByProjectAndName(
  projectId: string,
  name: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectMaterialView>>
    >(BASEURL.buymaterial + "page-bought-material-view-by-project-id-and-name", {
      params: {
        projectId: projectId,
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

/**
 *从服务器获得已经购买的、并且没有被禁止使用的物料
 */
export async function serverGetBoughtUseMaterialPageViewByProjectAndLocation(
  projectId: string,
  location: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectMaterialView>>
    >(BASEURL.buymaterial + "page-bought-material-view-by-project-id-and-location", {
      params: {
        projectId: projectId,
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

/**
 *从服务器获得已经购买的、并且没有被禁止使用的物料
 */
export async function serverGetBoughtUseMaterialPageViewByProjectAndItemMark(
  projectId: string,
  itemMark: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectMaterialView>>
    >(BASEURL.buymaterial + "page-bought-material-view-by-project-id-and-item-mark", {
      params: {
        projectId: projectId,
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

/**
 *从服务器获得已经购买的、并且没有被禁止使用的物料
 */
export async function serverGetBoughtUseMaterialPageViewByProjectAndTechnology(
  projectId: string,
  technology: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectMaterialView>>
    >(BASEURL.buymaterial + "page-bought-material-view-by-project-id-and-technology", {
      params: {
        projectId: projectId,
        technology: technology,
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

/**
 *从服务器获得已经购买的、并且没有被禁止使用的物料
 */
export async function serverGetBoughtUseMaterialPageViewByProjectAndInstallation(
  projectId: string,
  installation: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectMaterialView>>
    >(BASEURL.buymaterial + "page-bought-material-view-by-project-id-and-installation", {
      params: {
        projectId: projectId,
        installation: installation,
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

/**
 *从服务器获得已经购买的、并且没有被禁止使用的物料
 */
export async function serverGetBoughtUseMaterialPageViewByProjectAndBrand(
  projectId: string,
  brand: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectMaterialView>>
    >(BASEURL.buymaterial + "page-bought-material-view-by-project-id-and-brand", {
      params: {
        projectId: projectId,
        brand: brand,
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

export async function serverGetBuyMaterialPageViewByProjectId(
  projectId: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerBuyMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerBuyMaterialView>>
    >(BASEURL.buymaterial + "page-view-by-project-id", {
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

export async function serverGetBuyMaterialVerificationDocumentPageViewByProjectId(
  projectId: string,
  pageNo: number,
  pageSize: number
): Promise<
  IServerResponseData<
    IServerPage<IServerProjectMaterialVerificationDocumentView>
  >
> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<
        IServerPage<IServerProjectMaterialVerificationDocumentView>
      >
    >(BASEURL.buymaterial + "page-verification-document-view-by-project-id", {
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

export async function serverGetBuyMaterialRecheckIsRequiredByProjectId(
  projectId: string,
  pageNo: number,
  pageSize: number
): Promise<
  IServerResponseData<
    IServerPage<IServerProjectMaterialVerificationDocumentView>
  >
> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<
        IServerPage<IServerProjectMaterialVerificationDocumentView>
      >
    >(BASEURL.buymaterial + "page-recheck-is-required-by-project-id", {
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

export async function serverAddBuyMaterialVerificationDocumentTempFiles(
  files: FormData
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.buymaterial + "add-buy-material-verification-document-temp-file",
      files
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverDeleteBuyMaterialVerificationDocumentTempFiles(
  files: FormData
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.buymaterial +
      "delete-buy-material-verification-document-temp-file",
      files
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverAddBuyMaterialVerificationDocumentFile(
  files: FormData
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.buymaterial + "add-buy-material-verification-document-file",
      files
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverDeleteBuyMaterialVerificationDocumentFile(
  files: FormData
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.buymaterial + "delete-buy-material-verification-document-file",
      files
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverDownloadBuyMaterialVerificationDocumentFilesById(
  projectId: string,
  projectMaterialVerificationDocumentFileId: string,
  downloadFilename: string
) {
  try {
    let res = await axios.get(
      BASEURL.projectmaterialverificationdocumentfile +
      "download-buy-material-verification-document-file-by-id",
      {
        params: {
          projectId: projectId,
          projectMaterialVerificationDocumentFileId:
            projectMaterialVerificationDocumentFileId,
        },
        responseType: "arraybuffer",
      }
    );
    exportFileWithFullFilenameWithExt(
      res,

      downloadFilename
    );
  } catch (err) {
    console.log(err);
    throw err;
  }
}
