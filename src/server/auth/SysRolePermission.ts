/**
 * 处理系统角色权限相关请求
 * @author hbs@bistu.edu.cn
 *
 */
import { BASEURL, axios } from '@/http'

import {
  IServerResponseData,
  IServerSysRolePermission,
  IServerSysRolePermissionView,
  IServerPage,
} from '../types/System'

/**
 * 修改角色权限
 * @param name
 * @param note
 * @returns
 */
export async function serverRolePermissionUpdate(
  sysUserRoleView: IServerSysRolePermissionView,
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post(
      BASEURL.sysrolepermission + 'update-role-permissions',
      sysUserRoleView,
    )
    return res
  } catch (err) {
    console.log(err)
    throw err
  }
}

/**
 * 获得用户角色页面视图数据
 * @param name
 * @param note
 * @returns
 */
export async function serverGetRolePermissionPageView(
  pageNo: number,
  pageSize: number,
): Promise<IServerResponseData<IServerPage<IServerSysRolePermissionView>>> {
  try {
    let res = await axios.get(BASEURL.sysrolepermission + 'page-view', {
      params: {
        pageNo: pageNo,
        pageSize: pageSize,
      },
    })
    console.log(res)
    return res
  } catch (err) {
    console.log(err)
    throw err
  }
}

/**
 * 获得角色权限页面视图数据
 * @param name
 * @param note
 * @returns
 */
export async function serverGetRolePermissionPageViewByRoleName(
  searchText: string,
  pageNo: number,
  pageSize: number,
): Promise<IServerResponseData<IServerPage<IServerSysRolePermissionView>>> {
  try {
    let res = await axios.get(
      BASEURL.sysrolepermission + 'search-by-role-name',
      {
        params: {
          searchText: searchText,
          pageNo: pageNo,
          pageSize: pageSize,
        },
      },
    )
    console.log(res)
    return res
  } catch (err) {
    console.log(err)
    throw err
  }
}

/**
 * 获得角色权限页面视图数据
 * @param name
 * @param note
 * @returns
 */
export async function serverGetRolePermissionPageViewByPermissionName(
  searchText: string,
  pageNo: number,
  pageSize: number,
): Promise<IServerResponseData<IServerPage<IServerSysRolePermissionView>>> {
  try {
    let res = await axios.get(
      BASEURL.sysrolepermission + 'search-by-permission-name',
      {
        params: {
          searchText: searchText,
          pageNo: pageNo,
          pageSize: pageSize,
        },
      },
    )
    console.log(res)
    return res
  } catch (err) {
    console.log(err)
    throw err
  }
}
