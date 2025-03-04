import { BASEURL, axios } from "@/http";

import { IServerResponseData, IServerPage, ICaptcha } from "../types/System";
import {
  IServerProject,
  IServerProjectView,
  IServerProjectUser,
  IServerProjectUserView,
  IServerProjectAllUserView,
} from "../types/project/project";

import {
  IServerCompany,
  IServerCompanyUser,
  IServerCompanyUserView,
  IServerCompanyUserForm,
} from "@/server/types/system/company";

import { IServerUser } from "../types/account/user";

/**
 * 增加项目用户
 * @param projectUser
 * @returns 返回新增项目用户ID
 */
export async function serverProjectUserAdd(
  projectUser: IServerProjectUser
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectuser + "add",
      projectUser
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 新增项目用户，该用户为项目经理
 * @param projectUser 项目用户
 * @returns 返回新增项目用户ID
 */
export async function serverProjectUserAddManager(
  projectUser: IServerProjectUser
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectuser + "add-manager",
      projectUser
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 新增项目用户，该用户为项目员工
 * @param projectUser 项目用户
 * @returns 返回新增项目用户ID
 */
export async function serverProjectUserAddEmployee(
  projectUser: IServerProjectUser
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.projectuser + "add-employee",
      projectUser
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 删除项目用户
 * @param projectUser 项目用户
 * @returns 删除数量，如果删除成功，则值大于0，否则 等于0
 */
export async function serverProjectUserDelete(
  projectUser: IServerProjectUser
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.projectuser + "delete",
      projectUser
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 根据ID删除项目用户
 * @param id 项目用户ID
 * @returns 删除数量，如果删除成功，则值大于0，否则 等于0
 */
export async function serverProjectUserDeleteById(
  id: string
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.projectuser + "delete",
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
 * 修改项目用户
 * @param projectUser 项目用户
 * @returns 修改数量，如果修改成功，则值大于0，否则 等于0
 */
export async function serverProjectUserUpdate(
  projectUser: IServerProjectUser
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.projectuser + "update",
      projectUser
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
/**
 * 当前登录是否项目设计公司员工
 * @returns
 */
export async function serverGetCurrentLoginUserIsDesignCompanyEmployee(): Promise<
  IServerResponseData<boolean>
> {
  try {
    let res = await axios.get<any, IServerResponseData<boolean>>(
      BASEURL.projectuser + "get-current-login-user-is-design-company-employee"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 当前登录用户是否为设计部或工程部员工
 * @returns
 */
export async function serverGetCurrentLoginUserIsInDesignOrEngineeringDepartment(): Promise<
  IServerResponseData<boolean>
> {
  try {
    let res = await axios.get<any, IServerResponseData<boolean>>(
      BASEURL.projectuser +
        "get-current-login-user-is-in-design-or-engineering-department"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 当前登录用户单位是否为设计部
 * @returns
 */
export async function serverGetCurrentLoginUserIsInDesignDepartment(): Promise<
  IServerResponseData<boolean>
> {
  try {
    let res = await axios.get<any, IServerResponseData<boolean>>(
      BASEURL.projectuser + "get-current-login-user-is-in-design-department"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
/**
 * 当前登录是否设计部项目员工
 * @returns
 */
export async function serverGetCurrentLoginUserIsDesignDepartmentProjectEmployee(): Promise<
  IServerResponseData<boolean>
> {
  try {
    let res = await axios.get<any, IServerResponseData<boolean>>(
      BASEURL.projectuser +
        "get-current-login-user-is-design-department-employee"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
/**
 * 当前登录是否设计部项目经理
 * @returns
 */
export async function serverGetCurrentLoginUserIsDesignDepartmentManager(): Promise<
  IServerResponseData<boolean>
> {
  try {
    let res = await axios.get<any, IServerResponseData<boolean>>(
      BASEURL.projectuser +
        "get-current-login-user-is-design-department-manager"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
/**
 * 当前登录是否设计部员工
 * @returns
 */
export async function serverGetCurrentLoginUserIsDesignDepartmentManagerOrEmployee(): Promise<
  IServerResponseData<boolean>
> {
  try {
    let res = await axios.get<any, IServerResponseData<boolean>>(
      BASEURL.projectuser +
        "get-current-login-user-is-design-department-manager-or-employee"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
/**
 * 当前登录用户是否为工程部员工
 * @returns
 */
export async function serverGetCurrentLoginUserIsInEngineeringDepartment(): Promise<
  IServerResponseData<boolean>
> {
  try {
    let res = await axios.get<any, IServerResponseData<boolean>>(
      BASEURL.projectuser +
        "get-current-login-user-is-in-engineering-department"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
/**
 * 当前登录是否工程部员工
 * @returns
 */
export async function serverGetCurrentLoginUserIsEngineeringDepartmentEmployee(): Promise<
  IServerResponseData<boolean>
> {
  try {
    let res = await axios.get<any, IServerResponseData<boolean>>(
      BASEURL.projectuser +
        "get-current-login-user-is-engineering-department-employee"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
/**
 * 当前登录是否工程部经理
 * @returns
 */
export async function serverGetCurrentLoginUserIsEngineeringDepartmentManager(): Promise<
  IServerResponseData<boolean>
> {
  try {
    let res = await axios.get<any, IServerResponseData<boolean>>(
      BASEURL.projectuser +
        "get-current-login-user-is-engineering-department-manager"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
/**
 * 当前登录是否工程部经理或员工
 * @returns
 */
export async function serverGetCurrentLoginUserIsEngineeringDepartmentManagerOrEmployee(): Promise<
  IServerResponseData<boolean>
> {
  try {
    let res = await axios.get<any, IServerResponseData<boolean>>(
      BASEURL.projectuser +
        "get-current-login-user-is-engineering-department-manager-or-employee"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
/**
 * 当前登录是否为总包公司员工
 * @returns
 */
export async function serverGetCurrentLoginUserIsGeneralContractorCompanyEmployee(): Promise<
  IServerResponseData<boolean>
> {
  try {
    let res = await axios.get<any, IServerResponseData<boolean>>(
      BASEURL.projectuser +
        "get-current-login-user-is-general-contractor-company-employee"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}
/**
 * 当前登录是否为监理公司员工
 * @returns
 */
export async function serverGetCurrentLoginUserIsSupervisionCompanyEmployee(): Promise<
  IServerResponseData<boolean>
> {
  try {
    let res = await axios.get<any, IServerResponseData<boolean>>(
      BASEURL.projectuser +
        "get-current-login-user-is-supervision-company-employee"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 根据ID得到项目用户
 * @param id 项目用户ID
 * @returns
 */
export async function serverGetProjectUserById(): Promise<
  IServerResponseData<boolean>
> {
  try {
    let res = await axios.get<any, IServerResponseData<boolean>>(
      BASEURL.projectuser + "get-by-id"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 根据用户ID得到项目用户列表，即该用户所在的所有项目中的项目用户列表，据此可以得到该用户在所有项目中的身份
 * @param userId 用户ID
 * @returns
 */
export async function serverGetProjectUserByUerId(
  userId: string
): Promise<IServerResponseData<IServerProjectUser[]>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerProjectUser[]>>(
      BASEURL.projectuser + "get-by-user-id",
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
 * 根据项目ID得到该项目下所有的项目用户列表，据此可以知道该项目有哪些用户
 * @param projectId 项目ID
 * @returns
 */
export async function serverGetProjectUserByProjectId(
  projectId: string
): Promise<IServerResponseData<IServerProjectUser[]>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerProjectUser[]>>(
      BASEURL.projectuser + "get-by-project-id",
      {
        params: {
          projectId: projectId,
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
 * 得到有人员参与该项目的所有公司
 * @param projectId 项目ID
 * @returns
 */
export async function serverGetCompanyByProjectId(
  projectId: string
): Promise<IServerResponseData<IServerCompany[]>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerCompany[]>>(
      BASEURL.projectuser + "get-company-by-project-id",
      {
        params: {
          projectId: projectId,
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
 * 根据角色id得到项目用户列表，例如得到所有的项目经理
 * @param roleId 角色ID
 * @returns
 */
export async function serverGetProjectUserByRoleId(
  roleId: string
): Promise<IServerResponseData<IServerProjectUser[]>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerProjectUser[]>>(
      BASEURL.projectuser + "get-by-role-id",
      {
        params: {
          roleId: roleId,
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
 * 根据用户ID、项目ID得到用户在项目中的角色
 * @param userId
 * @param projectId
 * @returns
 */
export async function serverGetRoleByUserIdAndProjectId(
  userId: string,
  projectId: string
): Promise<IServerResponseData<IServerProjectUser[]>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerProjectUser[]>>(
      BASEURL.projectuser + "get-role-by-user-id-and-project-id",
      {
        params: {
          userId: userId,
          projectId: projectId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetEmployeeUserOfDesignDepartment(
  projectId: string
): Promise<IServerResponseData<IServerUser[]>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerUser[]>>(
      BASEURL.projectuser + "get-design-department-employee-user-by-project-id",
      {
        params: {
          projectId: projectId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetEmployeeUserOfEngineeringDepartment(
  projectId: string
): Promise<IServerResponseData<IServerUser[]>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerUser[]>>(
      BASEURL.projectuser +
        "get-engineering-department-employee-user-by-project-id",
      {
        params: {
          projectId: projectId,
        },
      }
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

export async function serverGetProjectAllUserViewByProjectId(
  projectId: string
): Promise<IServerResponseData<IServerProjectAllUserView>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerProjectAllUserView>
    >(BASEURL.projectuser + "get-project-all-user-view-by-project-id", {
      params: {
        projectId: projectId,
      },
    });
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 判断是否存在设计部经理
 * @param projectId
 * @returns
 */
export async function serverGetExistsDesignDepartmentManagerByProjectId(
  projectId: string
): Promise<IServerResponseData<boolean>> {
  try {
    let res = await axios.get<any, IServerResponseData<boolean>>(
      BASEURL.projectuser + "get-exists-design-department-manager",
      {
        params: {
          projectId: projectId,
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
 * 判断是否存在工程部经理
 * @param projectId
 * @returns
 */
export async function serverGetExistsEngineeringDepartmentManagerByProjectId(
  projectId: string
): Promise<IServerResponseData<boolean>> {
  try {
    let res = await axios.get<any, IServerResponseData<boolean>>(
      BASEURL.projectuser + "get-exists-engineering-department-manager",
      {
        params: {
          projectId: projectId,
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
 * 得到设计部经理
 * @param projectId
 * @returns
 */
export async function serverGetDesignDepartmentManagerByProjectId(
  projectId: string
): Promise<IServerResponseData<IServerProjectUserView>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerProjectUserView>>(
      BASEURL.projectuser + "get-design-department-manager",
      {
        params: {
          projectId: projectId,
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
 * 得到工程部经理
 * @param projectId
 * @returns
 */
export async function serverGetEngineeringDepartmentManagerByProjectId(
  projectId: string
): Promise<IServerResponseData<IServerProjectUserView>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerProjectUserView>>(
      BASEURL.projectuser + "get-engineering-department-manager",
      {
        params: {
          projectId: projectId,
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
export async function serverGetProjectUserPage(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectUser>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectUser>>
    >(BASEURL.projectuser + "page", {
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

export async function serverGetProjectAllUsersPageView(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectAllUserView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectAllUserView>>
    >(BASEURL.projectuser + "page-all-user-view", {
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

export async function serverGetProjectAllUsersPageViewByProjectName(
  projectName: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectAllUserView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectAllUserView>>
    >(BASEURL.projectuser + "page-all-user-view-by-project-name", {
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

export async function serverGetProjectAllUsersPageViewByUserName(
  projectName: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerProjectAllUserView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerProjectAllUserView>>
    >(BASEURL.projectuser + "page-all-user-view-by-user-name", {
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
 * 得到设计部项目经理人选(即去除掉项目员工后的人员)
 * @param projectId
 * @param pageNo
 * @return
 */
export async function serverGetDesignDepartmentManagerCandidatesViewPageByProjectId(
  projectId: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerCompanyUserView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerCompanyUserView>>
    >(
      BASEURL.projectuser +
        "page-design-department-manager-candidates-view-by-project-id",
      {
        params: {
          projectId: projectId,
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

/**
 * 得到设计部项目员工人选(即去除掉项目经理后的人员)
 * @param projectId
 * @param pageNo
 * @return
 */
export async function serverGetDesignDepartmentEmployeeCandidatesViewPageByProjectId(
  projectId: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerCompanyUserView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerCompanyUserView>>
    >(
      BASEURL.projectuser +
        "page-design-department-employee-candidates-view-by-project-id",
      {
        params: {
          projectId: projectId,
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
/**
 * 得到工程部项目经理人选(即去除掉项目员工后的人员)
 * @param projectId
 * @param pageNo
 * @return
 */
export async function serverGetEngineeringDepartmentManagerCandidatesViewPageByProjectId(
  projectId: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerCompanyUserView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerCompanyUserView>>
    >(
      BASEURL.projectuser +
        "page-engineering-department-manager-candidates-view-by-project-id",
      {
        params: {
          projectId: projectId,
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
/**
 * 得到工程部项目员工人选(即去除掉项目经理后的人员)
 * @param projectId
 * @param pageNo
 * @return
 */
export async function serverGetEngineeringDepartmentEmployeeCandidatesViewPageByProjectId(
  projectId: string,
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerCompanyUserView>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerCompanyUserView>>
    >(
      BASEURL.projectuser +
        "page-engineering-department-employee-candidates-view-by-project-id",
      {
        params: {
          projectId: projectId,
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
