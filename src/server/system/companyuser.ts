import { BASEURL, axios } from "@/http";

import { IServerResponseData, IServerPage, ICaptcha } from "../types/System";
import {
  IServerCompany,
  IServerCompanyUser,
  IServerCompanyUserView,
  IServerCompanyUserForm,
} from "@/server/types/system/company";

import { exportFile } from "@/server/exportfile";

/**
 * 增加单位用户
 * @param company
 * @returns 返回新增项目ID
 */
export async function serverCompanyUserAdd(
  companyUser: IServerCompanyUser
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.companyuser + "add",
      companyUser
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverCompanyUserAddByForm(
  companyuserForm: IServerCompanyUserForm
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.companyuser + "add-company-user-form",
      companyuserForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverCompanyUserAddFromExcel(
  excelFile: FormData
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.companyuser + "add-company-user-excel",
      excelFile
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 删除单位用户
 * @param project
 * @returns 删除数量，如果删除成功，则值大于0，否则 等于0
 */
export async function serverCompanyUserDelete(
  companyUser: IServerCompanyUser
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.companyuser + "delete",
      companyUser
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 根据ID删除单位
 * @param project
 * @returns 删除数量，如果删除成功，则值大于0，否则 等于0
 */
export async function serverCompanyUserDeleteById(
  id: string
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.companyuser + "delete",
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

/**
 * 修改单位用户
 * @param project
 * @returns 修改数量，如果修改成功，则值大于0，否则 等于0
 */
export async function serverCompanyUserUpdate(
  companyUser: IServerCompanyUser
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.companyuser + "update",
      companyUser
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverCompanyUserUpdateByForm(
  companyuserForm: IServerCompanyUserForm
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.companyuser + "update-company-user-form",
      companyuserForm
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 根据Id 删除单位用户
 * @param id
 * @returns
 */
export async function serverGetCompanyUserById(
  id: string
): Promise<IServerResponseData<IServerCompanyUser>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerCompanyUser>>(
      BASEURL.companyuser + "get-by-id",
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

/**
 * 通过公司id获取公司用户
 * @param companyId 公司ID
 * @returns 公司用户列表
 */
export async function serverGetCompanyUserListByCompanyId(
  companyId: string
): Promise<IServerResponseData<Array<IServerCompanyUser>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<Array<IServerCompanyUser>>
    >(BASEURL.companyuser + "get-by-company-id", {
      params: {
        companyId: companyId,
      },
    });
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 用户是否是总包单位员工
 * @param userId
 * @returns
 */
export async function serverGetCurrentLoginUserIsGeneralContractorCompanyEmployee(): Promise<
  IServerResponseData<boolean>
> {
  try {
    let res = await axios.get<any, IServerResponseData<boolean>>(
      BASEURL.companyuser +
        "get-current-login-user-is-general-contractor-company-employee-by-user-id"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 根据用户id得到所在公司用户。
 * 注意：
 * 1.一个用户只能存在与一个公司中。
 * 2.一个用户可以参与多个项目，在每个项目中只能有一个角色（要么是项目经理，要么是项目员工）。
 * 3.一个项目只能有一个项目经理，可以有多个项目员工
 * @param userId
 * @returns
 */
export async function serverGetCompanyUserListByUserId(
  userId: string
): Promise<IServerResponseData<IServerCompanyUser>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerCompanyUser>>(
      BASEURL.companyuser + "get-by-user-id",
      {
        params: {
          userId: userId,
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
 * 获得单位用户指定页面数据
 * @param pageNo 页码
 * @param pageSize 页面大小
 * @returns 单位用户页面
 */
export async function serverGetCompanyUserPage(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerCompanyUser>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerCompanyUser>>
    >(BASEURL.companyuser + "page", {
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

/**
 *
 * @param pageNo 页码
 * @param pageSize 页面大小
 * @returns
 */
export async function serverGetCompanyUserPageView(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerCompanyUserView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerCompanyUserView>>
    >(BASEURL.companyuser + "page-view", {
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
/**
 * 获得包含指定用户名称的指定页面的公司用户视图
 * @param userName
 * @param pageNo
 * @param pageSize
 * @returns
 */
export async function serverGetCompanyUserPageViewByUserName(
  userName: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerCompanyUserView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerCompanyUserView>>
    >(BASEURL.companyuser + "page-view-by-user-name", {
      params: {
        userName: userName,
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
 * 获得包含指定项目名称的指定页面的公司用户视图
 */
export async function serverGetCompanyUserPageViewByProjectName(
  projectName: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerCompanyUserView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerCompanyUserView>>
    >(BASEURL.companyuser + "page-view-by-project-name", {
      params: {
        projectName: projectName,
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
 * 获得包含指定公司名称的指定页面的公司用户视图
 * @param companyName
 * @param pageNo
 * @param pageSize
 * @returns
 */
export async function serverGetCompanyUserPageViewByCompanyName(
  companyName: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerCompanyUserView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerCompanyUserView>>
    >(BASEURL.companyuser + "page-view-by-company-name", {
      params: {
        companyName: companyName,
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

export async function serverGetCompanyUserPageViewByCompanyType(
  companyType: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerCompanyUserView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerCompanyUserView>>
    >(BASEURL.companyuser + "page-view-by-company-type", {
      params: {
        companyType: companyType,
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

export async function serverGetCompanyUserPageViewByCompanyId(
  companyId: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerCompanyUserView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerCompanyUserView>>
    >(BASEURL.companyuser + "page-view-by-company-id", {
      params: {
        companyId: companyId,
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

export async function serverGetCompanyUserPageViewByCompanyNameAndType(
  companyName: string,
  companyType: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerCompanyUserView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerCompanyUserView>>
    >(BASEURL.companyuser + "page-view-by-company-name-and-type", {
      params: {
        companyName: companyName,
        companyType: companyType,
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
 * 下载名单
 * @param examId
 * @param filetype
 * @returns
 */
export async function serverDownloadAllCompanyUser(downloadFilename: string) {
  try {
    let res: BlobPart = await axios.get(
      BASEURL.companyuser + "download-company-user-all",
      {
        params: {},
        responseType: "arraybuffer",
      }
    );

    exportFile(res, "excel", downloadFilename);
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverDownloadCompanyUserByUserNamer(
  userName: string,
  downloadFilename: string
) {
  try {
    let res: BlobPart = await axios.get(
      BASEURL.companyuser + "download-company-user-by-user-name",
      {
        params: { userName: userName },
        responseType: "arraybuffer",
      }
    );

    exportFile(res, "excel", downloadFilename);
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverDownloadCompanyUserByProjectName(
  projectName: string,
  downloadFilename: string
) {
  try {
    let res: BlobPart = await axios.get(
      BASEURL.companyuser + "download-company-user-by-project-name",
      {
        params: { projectName: projectName },
        responseType: "arraybuffer",
      }
    );

    exportFile(res, "excel", downloadFilename);
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverDownloadCompanyUserByCompanyName(
  companyName: string,
  downloadFilename: string
) {
  try {
    let res: BlobPart = await axios.get(
      BASEURL.companyuser + "download-company-user-by-company-name",
      {
        params: {
          companyName: companyName,
        },
        responseType: "arraybuffer",
      }
    );

    exportFile(res, "excel", downloadFilename);
  } catch (err) {
    console.log(err);
    throw err;
  }
}
