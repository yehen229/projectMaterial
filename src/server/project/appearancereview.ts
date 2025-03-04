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

export async function serverAddAppearanceReviewTempFiles(
  files: FormData
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectappearancereview +
        "add-use-material-appearance-review-temp-file",
      files
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverDeleteAppearanceReviewTempFiles(
  files: FormData
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectappearancereview +
        "delete-use-material-appearance-review-temp-file",
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
  projectAppearanceReviewUserFileId: string,
  downloadFilename: string
) {
  try {
    let res = await axios.get(
      BASEURL.usematerialnewbrandfile +
        "download-use-material-appearance-review-file-by-id",
      {
        params: {
          projectId: projectId,
          projectAppearanceReviewUserFileId: projectAppearanceReviewUserFileId,
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

export async function serverDownloadAppearanceUserReviewFileById(
  projectId: string,
  projectAppearanceReviewUserFileId: string,
  downloadFilename: string
) {
  try {
    let res = await axios.get(
      BASEURL.projectappearancereviewuserfile +
        "download-use-material-appearance-review-file-by-id",
      {
        params: {
          projectId: projectId,
          projectAppearanceReviewUserFileId: projectAppearanceReviewUserFileId,
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

export async function serverDownloadProjectMaterialAcceptanceFileById(
  projectId: string,
  projectMaterialAppearanceReviewUserFileId: string,
  downloadFilename: string
) {
  try {
    let res = await axios.get(
      BASEURL.projectmaterialacceptancereviewuserfile +
        "download-project-material-appearance-review-file-by-id",
      {
        params: {
          projectId: projectId,
          projectMaterialAppearanceReviewUserFileId: projectMaterialAppearanceReviewUserFileId,
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

