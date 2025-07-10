import { BASEURL, axios } from "@/http";

import { IServerResponseData, IServerPage, ICaptcha } from "../types/System";
import {
  IServerCompany,
  IServerCompanyUser,
  IServerCompanyUserView,
  IServerCompanyUserForm,
} from "@/server/types/system/company";

/**
 * 增加单位
 * @param company
 * @returns 返回新增项目ID
 */
export async function serverCompanyAdd(
  company: IServerCompany
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.company + "add",
      company
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 删除单位
 * @param project
 * @returns 删除数量，如果删除成功，则值大于0，否则 等于0
 */
export async function serverCompanyDelete(
  company: IServerCompany
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.company + "delete",
      company
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
export async function serverCompanyDeleteById(
  id: string
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.company + "delete",
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
 * 修改单位
 * @param project
 * @returns 修改数量，如果修改成功，则值大于0，否则 等于0
 */
export async function serverCompanyUpdate(
  company: IServerCompany
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.company + "update",
      company
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetCompanyUserById(
  id: string
): Promise<IServerResponseData<IServerCompany>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerCompany>>(
      BASEURL.company + "get-by-id",
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

export async function serverGetAllCompanyList(): Promise<
  IServerResponseData<Array<IServerCompany>>
> {
  try {
    let res = await axios.get<any, IServerResponseData<Array<IServerCompany>>>(
      BASEURL.company + "get-all-list"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetFactoryList(): Promise<
  IServerResponseData<Array<IServerCompany>>
> {
  try {
    let res = await axios.get<any, IServerResponseData<Array<IServerCompany>>>(
      BASEURL.company + "get-factory-list"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetAllCompanyListByCompanyType(
  companyType: string
): Promise<IServerResponseData<Array<IServerCompany>>> {
  try {
    let res = await axios.get<any, IServerResponseData<Array<IServerCompany>>>(
      BASEURL.company + "get-list-by-company-type",
      {
        params: {
          companyType: companyType,
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
 * 获得单位指定页面
 * @param pageNo 页码
 * @param pageSize 页面大小
 * @returns 单位列表
 */
export async function serverGetCompanyPage(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerCompany>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerCompany>>
    >(BASEURL.company + "page", {
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

export async function serverGetCompanyPageByCompanyName(
  companyName: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerCompany>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerCompany>>
    >(BASEURL.company + "page-by-company-name", {
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

export async function serverGetCompanyPageByCompanyType(
  companyType: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerCompany>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerCompany>>
    >(BASEURL.company + "page-by-company-type", {
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
