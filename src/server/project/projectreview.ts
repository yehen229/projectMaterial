import { BASEURL, axios } from "@/http";

import { IServerResponseData, IServerPage, ICaptcha } from "../types/System";
import { IServerProject, IServerProjectView } from "../types/project/project";
import {
  IServerProjectReviewUserView,
  IServerProjectReviewStatistics,
} from "../types/project/review";
import {
  exportFile,
  exportFileWithFullFilenameWithExt,
} from "@/server/exportfile";
export async function serverAddProjectMaterialReviewTempFiles(
  files: FormData
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectreview + "add-project-material-review-temp-file",
      files
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverDeleteProjectMaterialReviewTempFiles(
  files: FormData
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectreview + "delete-project-material-review-temp-file",
      files
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetProjectReviewUserViewListByProjectIdAndTaskId(
  projectId: string,
  taskId: string,
  designCompanyIndex: number
): Promise<IServerResponseData<IServerProjectReviewUserView[]>> {
  try {

    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectReviewUserView[]>
    >(BASEURL.projectreview + "list-user-view-by-project-review-id", {
      params: {
        projectId: projectId,
        taskId: taskId,
        designCompanyIndex: designCompanyIndex,
      },
    });
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetProjectReviewUserViewPageByTaskId(
  projectId: string,
  taskId: string,
  designCompanyIndex:number,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectReviewUserView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectReviewUserView>>
    >(
      BASEURL.projectmaterialflow +
        "page-project-material-user-review-by-task-id",
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

/**
 * 得到指定审核批次的项目材料的统计信息
 * @param projectReviewId
 * @returns
 */
export async function serverGetStatisticsOfProjectReviewUserViewByTaskId(
  projectId: string,
  taskId: string,
  designCompanyIndex: number
): Promise<IServerResponseData<IServerProjectReviewStatistics>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectReviewStatistics>
    >(
      BASEURL.projectmaterialflow +
        "statistics-project-material-review-by-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
          designCompanyIndex: designCompanyIndex,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetStatisticsOfProjectAppearanceReviewUserViewByTaskId(
  projectId: string,
  taskId: string
): Promise<IServerResponseData<IServerProjectReviewStatistics>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectReviewStatistics>
    >(BASEURL.projectmaterialflow + "statistics-appearance-review-by-task-id", {
      params: {
        projectId: projectId,
        taskId: taskId,
      },
    });
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetStatisticsOfDesignDepartmentProjectAppearanceReviewUserViewByTaskId(
  projectId: string,
  taskId: string
): Promise<IServerResponseData<IServerProjectReviewStatistics>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectReviewStatistics>
    >(
      BASEURL.projectmaterialflow +
        "statistics-design-department-appearance-review-by-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
export async function serverGetStatisticsOfEngineeringDepartmentMaterialAcceptanceReviewUserViewByTaskId(
  projectId: string,
  taskId: string
): Promise<IServerResponseData<IServerProjectReviewStatistics>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectReviewStatistics>
    >(
      BASEURL.projectmaterialflow +
        "statistics-engineering-department-material-acceptance-review-by-task-id",
      {
        params: {
          projectId: projectId,
          taskId: taskId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverDownloadProjectReviewUserFileById(
  projectId: string,
  projectReviewUserFileId: string,
  downloadFilename: string
) {
  try {
    let res = await axios.get(
      BASEURL.projectreviewuserfile + "download-project-review-file-by-id",
      {
        params: {
          projectId: projectId,
          projectReviewUserFileId: projectReviewUserFileId,
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
