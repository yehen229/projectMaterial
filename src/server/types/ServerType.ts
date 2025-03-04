// ServerType.ts

/**
 * 定义服务器响应数据的通用结构
 */
export interface IServerResponseData<T> {
    code: number;
    message: string;
    data: T;
  }
  
  /**
   * 定义系统用户的数据结构
   */
  export interface IServerSysUser {
    id: number;
    username: string;
    email: string;
    role: string;
  }
  
  /**
   * 定义分页数据的通用结构
   */
  export interface IServerPage<T> {
    total: number;
    pageSize: number;
    currentPage: number;
    data: T[];
  }