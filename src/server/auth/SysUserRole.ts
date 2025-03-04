/**
 * 处理系统权限相关请求，包括增删改查等
 * @author hbs@bistu.edu.cn
 *
 */
import { BASEURL, axios } from '@/http'

import {
  IServerResponseData,
  IServerSysUserRole,
  IServerSysUserRoleView,
  IServerPage,
} from '@/server/types/System'

/**
 * 增加用户角色
 * @param name
 * @param note
 * @returns
 */
export async function serverUserRoleAdd(
  name: string,
  note: string,
): Promise<IServerResponseData<IServerSysUserRole>> {
  try {
    let res = await axios.post(BASEURL.sysuserrole + 'add', {
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
 * 删除用户角色
 * @param name
 * @param note
 * @returns
 */
export async function serverUserRoleDelete(
  id: string,
): Promise<IServerResponseData<IServerSysUserRole>> {
  try {
    let res = await axios.post(BASEURL.sysuserrole + 'delete', {
      id: id,
    })
    return res
  } catch (err) {
    console.log(err)
    throw err
  }
}

/**
 * 修改用户角色
 * @param name
 * @param note
 * @returns
 */
export async function serverUserRoleUpdate(
  sysUserRoleView: IServerSysUserRoleView,
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post(
      BASEURL.sysuserrole + 'update-user-roles',
      sysUserRoleView,
    )
    return res
  } catch (err) {
    console.log(err)
    throw err
  }
}

/**
 * 获得用户角色页面数据
 * @param name
 * @param note
 * @returns
 */
export async function serverGetUserRolePage(
  pageNo: number,
  pageSize: number,
): Promise<IServerResponseData<IServerPage<IServerSysUserRole>>> {
  try {
    let res = await axios.get(BASEURL.sysuserrole + 'page', {
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
 * 获得用户角色页面视图数据
 * @param name
 * @param note
 * @returns
 */
export async function serverGetUserRolePageView(
  pageNo: number,
  pageSize: number,
): Promise<IServerResponseData<IServerPage<IServerSysUserRoleView>>> {
  try {
    let res = await axios.get(BASEURL.sysuserrole + 'page-view', {
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
 * 获得用户角色页面视图数据
 * @param name
 * @param note
 * @returns
 */
export async function serverGetUserRolePageViewByUserName(
  searchText: string,
  pageNo: number,
  pageSize: number,
): Promise<IServerResponseData<IServerPage<IServerSysUserRoleView>>> {
  try {
    let res = await axios.get(BASEURL.sysuserrole + 'search-by-user-name', {
      params: {
        searchText: searchText,
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
 * 获得用户角色页面视图数据
 * @param name
 * @param note
 * @returns
 */
export async function serverGetUserRolePageViewByUserRealName(
  searchText: string,
  pageNo: number,
  pageSize: number,
): Promise<IServerResponseData<IServerPage<IServerSysUserRoleView>>> {
  try {
    let res = await axios.get(
      BASEURL.sysuserrole + 'search-by-user-real-name',
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
 * 获得用户角色页面视图数据
 * @param name
 * @param note
 * @returns
 */
export async function serverGetUserRolePageViewByRoleName(
  searchText: string,
  pageNo: number,
  pageSize: number,
): Promise<IServerResponseData<IServerPage<IServerSysUserRoleView>>> {
  try {
    let res = await axios.get(BASEURL.sysuserrole + 'search-by-role-name', {
      params: {
        searchText: searchText,
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
 * 获得用户角色页面视图数据
 * @param name
 * @param note
 * @returns
 */
export async function serverGetUserRolePageViewByUserDepartment(
  searchText: string,
  pageNo: number,
  pageSize: number,
): Promise<IServerResponseData<IServerPage<IServerSysUserRoleView>>> {
  try {
    let res = await axios.get(
      BASEURL.sysuserrole + 'search-by-user-department',
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
 * 获得用户角色页面视图数据
 * @param name
 * @param note
 * @returns
 */
export async function serverGetUserRolePageViewByUserType(
  searchText: string,
  pageNo: number,
  pageSize: number,
): Promise<IServerResponseData<IServerPage<IServerSysUserRoleView>>> {
  try {
    let res = await axios.get(BASEURL.sysuserrole + 'search-by-user-type', {
      params: {
        searchText: searchText,
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
