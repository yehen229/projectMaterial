/**
 * 处理系统权限相关请求，包括增删改查等
 * @author hbs@bistu.edu.cn
 *
 */
import { BASEURL, axios } from '@/http'

import {
  IServerResponseData,
  IServerSysPermission,
  IServerPage,
} from '../types/System'

/**
 * 增加权限
 * @param name
 * @param note
 * @returns
 */
export async function serverPermissionAdd(
  name: string,
  note: string,
): Promise<IServerResponseData<IServerSysPermission>> {
  try {
    let res = await axios.post(BASEURL.syspermission + 'add', {
      name: name,
      note: note,
    })
    return res
  } catch (err) {
    console.log(err)
    throw err
  }
}

/**
 * 删除权限
 * @param name
 * @param note
 * @returns
 */
export async function serverPermissionDelete(
  id: string,
): Promise<IServerResponseData<IServerSysPermission>> {
  try {
    let res = await axios.post(BASEURL.syspermission + 'delete', {
      id: id,
    })
    return res
  } catch (err) {
    console.log(err)
    throw err
  }
}

/**
 * 修改权限
 * @param name
 * @param note
 * @returns
 */
export async function serverPermissionUpdate(
  id: string,
  name: string,
  note: string,
): Promise<IServerResponseData<IServerSysPermission>> {
  try {
    let res = await axios.post(BASEURL.syspermission + 'update', {
      id: id,
      name: name,
      note: note,
    })
    return res
  } catch (err) {
    console.log(err)
    throw err
  }
}

/**
 * 获得权限
 * @param name
 * @param note
 * @returns
 */
export async function serverGetPermissionPage(
  pageNo: number,
  pageSize: number,
): Promise<IServerResponseData<IServerPage<IServerSysPermission>>> {
  try {
    let res = await axios.get(BASEURL.syspermission + 'page', {
      params: {
        pageNo: pageNo,
        pageSize: pageSize,
      },
    })
    return res
  } catch (err) {
    console.log(err)
    throw err
  }
}

/**
 * 根据名字获得权限
 * @param name
 * @param note
 * @returns
 */
export async function serverGetPermissionPageByName(
  name: string,
  pageNo: number,
  pageSize: number,
): Promise<IServerResponseData<IServerPage<IServerSysPermission>>> {
  try {
    let res = await axios.get(BASEURL.syspermission + 'page-by-name', {
      params: {
        name: name,
        pageNo: pageNo,
        pageSize: pageSize,
      },
    })
    return res
  } catch (err) {
    console.log(err)
    throw err
  }
}
