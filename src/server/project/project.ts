import { BASEURL, axios } from "@/http";

import { IServerResponseData, IServerPage, ICaptcha } from "../types/System";
import { IServerProject, IServerProjectView } from "../types/project/project";

/**
 * 增加项目
 * @param project
 * @returns 返回新增项目ID
 */
export async function serverProjectAdd(
  project: IServerProject
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.project + "add",
      project
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetAllProjectList(): Promise<
  IServerResponseData<Array<IServerProject>>
> {
  try {
    let res = await axios.get<any, IServerResponseData<Array<IServerProject>>>(
      BASEURL.project + "get-all-list"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 删除项目
 * @param project
 * @returns 删除数量，如果删除成功，则值大于0，否则 等于0
 */
export async function serverProjectDelete(
  project: IServerProject
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.project + "delete",
      project
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 修改项目
 * @param project
 * @returns 修改数量，如果修改成功，则值大于0，否则 等于0
 */
export async function serverProjectUpdate(
  project: IServerProject
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.project + "update",
      project
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetProjectById(
  id: string
): Promise<IServerResponseData<IServerProject>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerProject>>(
      BASEURL.project + "get-by-id",
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

export async function serverGetProjectViewById(
  id: string
): Promise<IServerResponseData<IServerProjectView>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerProjectView>>(
      BASEURL.project + "get-view-by-id",
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
 * 获取所有项目信息
 * @param pageNo 页码
 * @param pageSize 页面大小
 * @returns 项目列表
 */
export async function serverGetProjectPageView(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectView>>
    >(BASEURL.project + "page-view", {
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
 * 获取项目信息（关键词查询）
 * @param pageNo 页码
 * @param pageSize 页面大小
 * @returns 项目列表
 */
export async function serverGetProjectPageViewByKeyword(
  keyword: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectView>>
    >(BASEURL.project + "page-view-by-keyword", {
      params: {
        keyword: keyword,
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
 * 得到总包单位参与的没有结项的项目页面
 * @param generalContractorCompanyId
 * @param pageNo
 * @param pageSize
 * @returns
 */
export async function serverGetCurrentLoginUserOfGeneralContractorCompanyNotEndedProjectPageView(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectView>>
    >(
      BASEURL.project +
        "page-view-not-ended-of-current-login-user-in-general-contractor-company",
      {
        params: {
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
