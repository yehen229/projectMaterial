/**
 * 处理系统角色相关请求，包括增删改查等
 * @author hbs@bistu.edu.cn
 *
 */
import { BASEURL, axios } from '@/http'

import { IServerResponseData, IServerSysRole, IServerPage } from '../types/System'

/**
 * 增加角色
 * @param name
 * @param note
 * @returns
 */
export async function serverRoleAdd(
  name: string,
  note: string,
): Promise<IServerResponseData<IServerSysRole>> {
  try {
    let res = await axios.post(BASEURL.sysrole + 'add', {
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
 * 删除角色
 * @param name
 * @param note
 * @returns
 */
export async function serverRoleDelete(
  id: string,
): Promise<IServerResponseData<IServerSysRole>> {
  try {
    let res = await axios.post(BASEURL.sysrole + 'delete', {
      id: id,
    })
    return res
  } catch (err) {
    console.log(err)
    throw err
  }
}

/**
 * 修改角色
 * @param name
 * @param note
 * @returns
 */
export async function serverRoleUpdate(
  id: string,
  name: string,
  note: string,
): Promise<IServerResponseData<IServerSysRole>> {
  try {
    let res = await axios.post(BASEURL.sysrole + 'update', {
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
 * 获得角色
 * @param name
 * @param note
 * @returns
 */
export async function serverGetRolePage(
  pageNo: number,
  pageSize: number,
): Promise<IServerResponseData<IServerPage<IServerSysRole>>> {
  try {
    let res = await axios.get(BASEURL.sysrole + 'page', {
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
 * 根据名字获得角色
 * @param name
 * @param note
 * @returns
 */
export async function serverGetRolePageByName(
  name: string,
  pageNo: number,
  pageSize: number,
): Promise<IServerResponseData<IServerPage<IServerSysRole>>> {
  try {
    let res = await axios.get(BASEURL.sysrole + 'page-by-name', {
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
