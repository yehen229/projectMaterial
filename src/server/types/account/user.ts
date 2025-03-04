/**
 * 登录返回结果
 */
export interface IServerUserLoginResult {
  token: string; //Token
  roleList: Array<string>; //角色
  userName: string; //用户ID
  userRealName: string; //用户真实姓名
  userId: string;
}

export interface IServerUser {
  id: string; //id,主键
  userName: string; //login_id,登录id登录id
  realName: string; //name,姓名姓名
  password: string; //password,密码密码
  tel: string; //tel,电话电话
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

/**
 * 角色
 */
export interface IServerRole {
  id: string; //id,主键
  name: string; //name,名称
  note: string; //note,备注
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

/**
 * 用户角色
 */
export interface IServerUserRole {
  id: string; //id,主键
  userId: string; //用户
  roleId: string; //角色
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

/**
 * 用户角色视图
 */
export interface IServerUserRoleView {
  sysUser: IServerUser; //用户
  sysRoleList: Array<IServerRole>; //该用户的角色列表
}
