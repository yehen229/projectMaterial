/**
 * 处理系统角色相关请求，包括增删改查等
 * @author hbs@bistu.edu.cn
 *
 */
import { BASEURL, axios } from "@/http";

import { IServerResponseData, IServerPage } from "../types/System";

import {
  IServerUser,
  IServerRole,
  IServerUserRole,
  IServerUserRoleView,
} from "../types/account/user";

import { clearCookies } from "@/cookies/user";

/**
 * 增加角色
 * @param name
 * @param note
 * @returns
 */
export async function serverRoleAdd(
  role: IServerRole
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.role + "add",
      role
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 删除角色
 * @param name
 * @param note
 * @returns
 */
export async function serverRoleDelete(
  role: IServerRole
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.role + "delete",
      role
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 修改角色
 * @param name
 * @param note
 * @returns
 */
export async function serverRoleUpdate(
  role: IServerRole
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.role + "update",
      role
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetRoleById(
  id: string
): Promise<IServerResponseData<IServerRole>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerRole>>(
      BASEURL.role + "get-by-id",
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
 * 获得角色
 * @param name
 * @param note
 * @returns
 */
export async function serverGetRolePage(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerRole>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerRole>>
    >(BASEURL.role + "page", {
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
