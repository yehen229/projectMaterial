import { BASEURL, axios } from "@/http";

import { IServerResponseData, IServerPage, ICaptcha } from "../types/System";

import { exportFile } from "@/server/exportfile";

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
  IServerProjectMaterial,
  IServerProjectMaterialView,
  IServerProjectMaterialForm,
} from "@/server/types/project/project";

import { IServerProjectMaterialVerificationDocumentView } from "@/server/types/project/review";

export async function serverProjectMaterialAdd(
  projectMaterial: IServerProjectMaterial
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterial + "add",
      projectMaterial
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 新增项目材料
 * @param projectBrandForm
 * @returns
 */
export async function serverProjectMaterialAddForm(
  projectBrandForm: IServerProjectMaterialForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterial + "add-form",
      projectBrandForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverProjectMaterialDelete(
  projectMaterial: IServerProjectMaterial
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.projectmaterial + "delete",
      projectMaterial
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverProjectMaterialDeleteById(
  id: string
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.projectmaterial + "delete",
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

export async function serverProjectMaterialUpdate(
  projectMaterial: IServerProjectMaterial
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.projectmaterial + "update",
      projectMaterial
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverProjectMaterialUpdateForm(
  projectMaterialForm: IServerProjectMaterialForm
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.projectmaterial + "update-form",
      projectMaterialForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetProjectMaterialById(
  id: string
): Promise<IServerResponseData<IServerProjectMaterial>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerProjectMaterial>>(
      BASEURL.projectmaterial + "get-by-id",
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

export async function serverGetProjectMaterialPage(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectMaterial>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectMaterial>>
    >(BASEURL.projectmaterial + "page", {
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

export async function serverGetProjectMaterialPageView(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectMaterialView>>
    >(BASEURL.projectmaterial + "page-view", {
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

export async function serverGetProjectMaterialPageViewByProject(
  projectId: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectMaterialView>>
    >(BASEURL.projectmaterial + "page-view-by-project-id", {
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

export async function serverGetProjectMaterialPageViewByCurrentUserProject(
  projectId: string,
  name: String,
  location: String,
  itemMark: String,
  technology: String,
  installation: String,
  brand: String,
  brandPrivate: String,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectMaterialView>>> {
  try {
    let res;
    if (name == "" && location == "" && itemMark == "" && technology == "" && installation == "" && brand == "" && brandPrivate == "") {
      res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectMaterialView>>
      >(
        BASEURL.projectmaterial +
        "page-view-by-design-company-of-current-user-and-project-id",
        {
          params: {
            projectId: projectId,
            pageNo: pageNo,
            pageSize: pageSize,
          },
        }
      );
    } else {
      res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectMaterialView>>
      >(
        BASEURL.projectmaterial +
        "page-view-by-search",
        {
          params: {
            projectId: projectId,
            name: name,
            location: location,
            itemMark: itemMark,
            technology: technology,
            installation: installation,
            brand: brand,
            brandPrivate: brandPrivate,
            pageNo: pageNo,
            pageSize: pageSize,
          },
        }
      );
    }
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetProjectMaterialPageViewByTaskIdAndProjectId(
  projectId: string,
  taskId: string,
  designCompanyIndex: number,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectMaterialView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectMaterialView>>
    >(
      BASEURL.projectmaterialflow +
      "page-view-project-material-by-task-id-and-project-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
          designCompanyIndex: designCompanyIndex,
          pageNo: pageNo,
          pageSize: pageSize,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverProjectMaterialExcelByDesignCompanyOfCurrentUserAndProjectId(
  projectId: string,
  downloadFilename: string
) {
  try {
    let res: BlobPart = await axios.get(
      BASEURL.projectmaterial + "download-project-material-excel-by-design-company-of-current-user-and-project-id",
      {
        params: { projectId: projectId },
        responseType: "arraybuffer",
      }
    );

    exportFile(res, "excel", downloadFilename);
  } catch (err) {
    console.log(err);
    throw err;
  }
}

// 上传项目材料接口
export async function serverProjectMaterialAddFromExcel(
  excelFile: FormData
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectmaterial + "add-project-material-excel",
      excelFile
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

