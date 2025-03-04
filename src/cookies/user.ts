import { IServerUserLoginResult } from "@/server/types/User";

export function clearCookies() {
  localStorage.removeItem("ACCESS_TOKEN");
  localStorage.removeItem("USER_ROLES");
  localStorage.removeItem("USER_NAME");
  localStorage.removeItem("USER_REAL_NAME");
  localStorage.removeItem("USER_ID");
  localStorage.removeItem("USER_PAGE_SIZE");
}

export function setUserCookies(user: IServerUserLoginResult) {
  localStorage.setItem("ACCESS_TOKEN", user.token);
  localStorage.setItem("USER_ROLES", user.roleList + "");
  localStorage.setItem("USER_NAME", user.userName);
  localStorage.setItem("USER_REAL_NAME", user.userRealName);
  localStorage.setItem("USER_ID", user.userId);
}

export function getToken() {
  return localStorage.getItem("ACCESS_TOKEN");
}

export function getUserID() {
  return localStorage.getItem("USER_ID");
}

export function getUserName() {
  return localStorage.getItem("USER_NAME");
}

export function getUserRealName() {
  return localStorage.getItem("USER_REAL_NAME");
}

/**
 * 用户是否拥有指定角色
 * @param role
 * @returns
 */
export function hasRole(role: string) {
  const roles = localStorage.getItem("USER_ROLES");

  if (roles) {
    let roleArray: string[] = roles?.split(",");
    return roleArray.indexOf(role) >= 0;
  }

  return false;
}

/**
 * 用户是否是教师角色
 * @returns
 */
export function isTeacher() {
  return hasRole("Teacher");
}

/**
 * 用户是否是管理员角色
 * @returns
 */
export function isAdmin() {
  return hasRole("Admin");
}

/**
 * 用户是否是学生角色
 * @returns
 */
export function isStudent() {
  return hasRole("Student");
}

/**
 * 返回用户浏览数据时的页面大小，即每页显示数据
 * @returns 页面大小
 */
export function getUserPageSize() {
  var r = localStorage.getItem("USER_PAGE_SIZE");
  if (r == null) return 10;
  return parseInt(r);
}

/**
 * 设置用户浏览数据时的页面大小，即每页显示数据
 * @param pageSize 页面大小
 */
export function setUserPageSize(pageSize: number) {
  localStorage.setItem("USER_PAGE_SIZE", pageSize + "");
}
