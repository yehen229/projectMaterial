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
 * 获取项目信息（项目名查询）
 * @param projectName 项目名
 * @param pageNo 页码
 * @param pageSize 页面大小
 * @returns 项目列表
 */
export async function serverGetProjectPageViewByProjectName(
  projectName: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectView>>
    >(BASEURL.project + "page-view-by-project-name", {
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
 * 获取项目信息（项目位置查询）
 * @param projectLocation 项目位置
 * @param pageNo 页码
 * @param pageSize 页面大小
 * @returns 项目列表
 */
export async function serverGetProjectPageViewByProjectLocation(
  projectLocation: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectView>>
    >(BASEURL.project + "page-view-by-project-location", {
      params: {
        projectLocation: projectLocation,
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
 * 高级搜索（全查询）
 * 
 * @param pageNo 页码
 * @param pageSize 页面大小
 * @returns 项目列表
 */
export async function serverGetProjectPageViewByParams(
  name: string,
  location: string,
  totalTaxIncluded: number,
  totalTaxNotIncluded: number,
  buildingAreaAboveGround: number,
  buildingAreaUnderGround: number,
  companyConstructionId: string,
  companyDesignId: string,
  note: string,
  createDatetime: Date,
  endDatetime: Date,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectView>>
    >(BASEURL.project + "page-view-by-params", {
      params: {
        name: name,
        location: location,
        totalTaxIncluded: totalTaxIncluded,
        totalTaxNotIncluded: totalTaxNotIncluded,
        buildingAreaAboveGround: buildingAreaAboveGround,
        buildingAreaUnderGround: buildingAreaUnderGround,
        companyConstructionId: companyConstructionId,
        companyDesignId: companyDesignId,
        note: note,
        createDatetime: createDatetime,
        endDatetime: endDatetime,
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
