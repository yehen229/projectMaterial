/**
 * 服务器返回统一的结果数据格式
 */
export interface IServerResponseData<P = {}> {
  code: number; //代码
  message: string; //信息
  data: P; //数据
}

/**
 * 验证码
 */
export interface ICaptcha {
  code: string;
  key: string;
}

/**
 * 页面对象
 */
export interface IServerPage<T> {
  start: number;
  totalCount: number;
  pageSize: number;
  result: Array<T>;
  hasPreviousPage: boolean;
  hasNextPage: boolean;
}
