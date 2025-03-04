/**
 * 处理系统用户相关请求，包括登录、修改个人信息等
 * @author hbs@bistu.edu.cn
 *
 */
import { BASEURL, axios } from "@/http";

import { IServerResponseData, IServerPage, ICaptcha } from "../types/System";

import { IServerUser, IServerUserLoginResult } from "../types/account/user";

import { clearCookies } from "@/cookies/user";

/**
 * 用户登录
 * @param username
 * @param password
 * @param captchaCode
 * @param captchaKey
 * @returns
 */
export async function serverLogin(
  username: string, //用户名
  password: string, //用户密码
  captchaCode: string, //验证码
  captchaKey: string //验证码对应的键
): Promise<IServerResponseData<IServerUserLoginResult>> {
  try {
    let res = await axios.post<
      any,
      IServerResponseData<IServerUserLoginResult>
    >(BASEURL.user + "login", {
      userName: username,
      password: password,
      captchaCode: captchaCode,
      captchaKey: captchaKey,
    });
    return res;
  } catch (err) {
    console.log(err);

    throw err;
  }
}

/**
 * 用户登出
 * @param username
 * @param password
 * @returns
 */
export async function serverLoginOut(): Promise<IServerResponseData<number>> {
  try {
    clearCookies(); //删除Cookies相关数据
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.user + "logout"
    );

    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 返回公钥
 * @returns
 */
export async function serverGetPublicKey(): Promise<IServerResponseData<string> | null> {
  try {
    let res = await axios.get<any, IServerResponseData<string>>(
      BASEURL.user + "publicKey"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 返回验证
 * @returns
 */
export async function serverGetCaptchaJpg(): Promise<IServerResponseData<ICaptcha> | null> {
  try {
    let res = await axios.get<any, IServerResponseData<ICaptcha>>(
      BASEURL.user + "captcha.jpg"
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 增加用户
 * @param name
 * @param note
 * @returns
 */
export async function serverUserAdd(
  user: IServerUser
): Promise<IServerResponseData<string>> {
  try {
    let res = await axios.post<any, IServerResponseData<string>>(
      BASEURL.user + "add",
      user
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 删除用户
 * @param name
 * @param note
 * @returns
 */
export async function serverUserDelete(
  user: IServerUser
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.user + "delete",
      user
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 修改用户
 * @param name
 * @param note
 * @returns
 */
export async function serverUserUpdate(
  user: IServerUser
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.user + "update",
      user
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 修改用户
 * @param name
 * @param note
 * @returns
 */
export async function serverUserUpdateOwnInfo(
  user: IServerUser
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.user + "update-own-info",
      user
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 用户自己修改密码
 * @param name
 * @param note
 * @returns
 */
export async function serverUserUpdateOwnPwd(
  user: IServerUser
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.user + "update-own-pwd",
      user
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 将用户密码改为默认密码'123456'
 * @param name
 * @param note
 * @returns
 */
export async function serverUserResetPwd(
  user: IServerUser
): Promise<IServerResponseData<number>> {
  try {
    let res = await axios.post<any, IServerResponseData<number>>(
      BASEURL.user + "reset-pwd",
      user
    );
    return res;
  } catch (err) {
    console.log(err);
    throw err;
  }
}

/**
 * 获得用户
 * @param name
 * @param note
 * @returns
 */
export async function serverGetUserByUserName(
  userName: string
): Promise<IServerResponseData<IServerUser>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerUser>>(
      BASEURL.user + "get-by-user-name",
      {
        params: {
          userName: userName,
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
 * 获得用户
 * @param name
 * @param note
 * @returns
 */
export async function serverGetUserByUserId(
  id: string
): Promise<IServerResponseData<IServerUser>> {
  try {
    let res = await axios.get<any, IServerResponseData<IServerUser>>(
      BASEURL.user + "get-by-id",
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
 * 获得用户
 * @param name
 * @param note
 * @returns
 */
export async function serverGetUserPage(
  pageNo: number,
  pageSize: number
): Promise<IServerResponseData<IServerPage<IServerUser>>> {
  try {
    let res = await axios.get<
      any,
      IServerResponseData<IServerPage<IServerUser>>
    >(BASEURL.user + "page", {
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
