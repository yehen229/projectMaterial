import axios from "axios";
import type { AxiosInstance, AxiosRequestConfig, AxiosResponse } from "axios";
import { ElMessage } from "element-plus";

import { useRouter } from "vue-router/dist/vue-router";

import { getUserID, clearCookies } from "@/cookies/user";

const router = useRouter();

const BASEURL = {
  //apiUrl: "http://211.68.36.45:8000", //学校机房production
  //apiUrl: "https://api.pythonkaoshi.com", //阿里云
  qrcode: "/qrcode/v1/",
  // apiUrl: "https://www.bistuxinxigang.shop:9000",
  // apiUrl: "https://10.157.176.70:9000",
  // apiUrl: "https://10.146.58.74:9000",
  apiUrl: "http://127.0.0.1:9000", //pro
  //apiUrl: "https://74.48.81.138:9000", //dev

  logManage: "/log/v1/",
  statisticalanalysis: "/statisticalanalysis/v1/", //权限管理
  user: "/user/v1/", //用户管理
  role: "/sysrole/v1/", //角色管理
  userrole: "/sysuserrole/v1/", //权限管理

  project: "/project/v1/",
  projectuser: "/projectuser/v1/",
  company: "/company/v1/",
  companyuser: "/companyuser/v1/",

  material: "/material/v1/", //物料管理
  materialbrand: "/materialbrand/v1/", //物料品牌管理
  materialclassifydivision: "/materialclassifydivision/v1/", //物料分类管理-大类
  materialclassifygroup: "/materialclassifygroup/v1/", //物料分类管理-中类
  materialclassifysection: "/materialclassifysection/v1/", //物料分类管理-小类

  materialphoto: "/materialphoto/v1/", //物料图片管理

  brand: "/brand/v1/", //品牌管理
  brandpublic: "/brandpublic/v1/", //品牌公共管理
  projectbrand: "/projectbrand/v1/",
  projectmaterialbrandprivate: "/projectmaterialbrandprivate/v1/",
  projectmaterial: "/projectmaterial/v1/",
  projectreview: "/projectreview/v1/",
  projectreviewuserfile: "/projectreviewuserfile/v1/",
  usematerial: "/usematerial/v1/",
  usematerialnewbrandfile: "/usematerialnewbrandfile/v1/",
  projectappearancereview: "/projectappearancereview/v1/",
  projectappearancereviewuserfile: "/projectappearancereviewuserfile/v1/",
  buymaterial: "/buymaterial/v1/",
  projectmaterialverificationdocumentfile:
    "/projectmaterialverificationdocumentfile/v1/",
  projectmaterialacceptancereviewuserfile:
    "/projectmaterialacceptancereviewuserfile/v1/",

  projectmaterialflow: "/projectmaterialflow/v1/", //流程管理
};

axios.defaults.timeout = 60000;
axios.defaults.withCredentials = false;

//axios.defaults.headers.post['Content-Type'] =
//  'application/x-www-form-urlencoded;charset=UTF-8'

// 登录流程控制中，根据本地是否存在token判断用户的登录情况
// 但是即使token存在，也有可能token是过期的，所以在每次的请求头中携带token
// 后台根据携带的token判断用户的登录情况，并返回给我们对应的状态码
axios.interceptors.request.use((config) => {
  const token = localStorage.getItem("ACCESS_TOKEN");
  if (token) {
    config.headers.Authorization = "Bearer " + token;
  }
  return config;
});

// 添加响应拦截器
axios.interceptors.response.use(
  function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么

    return response.data;
  },
  function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么

    if (error.response.status === 401) {
      // 401 说明 token 验证失败
      // 可以直接跳转到登录页面，重新登录获取 token
      clearCookies();
      location.href = "/login";
    } else if (error.response.status === 500) {
      // 服务器错误
      // do something
      ElMessage({
        showClose: true,
        message: `${error.response.data.message}`,
        type: "error",
      });

      return Promise.reject(error.response.data);
    } else {
      ElMessage({
        showClose: true,
        message: `${error.response.data.message}`,
        type: "error",
      });
    }
    // 返回 response 里的错误信息
    return Promise.reject(error.response.data);
  }
);

axios.defaults.baseURL = BASEURL.apiUrl;

export { BASEURL, axios };

/*
class Request {
  // axios 实例
  public static instance: AxiosInstance

  constructor() {
    // 基础配置，url和超时时间
    let config: AxiosRequestConfig = { baseURL: BASEURL.apiUrl, timeout: 60000 }
    // 使用axios.create创建axios实例
    Request.instance = axios.create(config)

    Request.instance.defaults.withCredentials = true

    Request.instance.defaults.headers.post['Content-Type'] =
      'application/x-www-form-urlencoded;charset=UTF-8'

    Request.instance.interceptors.request.use(
      (config: AxiosRequestConfig) => {
        // 登录流程控制中，根据本地是否存在token判断用户的登录情况
        // 但是即使token存在，也有可能token是过期的，所以在每次的请求头中携带token
        // 后台根据携带的token判断用户的登录情况，并返回给我们对应的状态码
        // if (config.headers.isJwt) {
        const token = localStorage.getItem('ACCESS_TOKEN')
        if (token) {
          config.headers.Authorization = 'Bearer ' + token
        }
        // }

        return config
      },
      (err: any) => {
        return Promise.reject(err)
      },
    )

    // 响应拦截器
    Request.instance.interceptors.response.use(
      (res: AxiosResponse) => {
        // 正确，返回结果
        return res.data
      },
      (err: any) => {
        //错误
        if (err.response) {
          // 请求成功发出且服务器也响应了状态码，但状态代码超出了 2xx 的范围
          // 这里用来处理http常见错误，进行全局提示
          let message = ''
          switch (err.response.status) {
            case 400:
              message = '请求错误(400)'
              break
            case 401:
              message = '未授权，请重新登录(401)'
              // 这里可以做清空storage并跳转到登录页的操作
              break
            case 403:
              message = '拒绝访问(403)'
              break
            case 404:
              message = '请求出错(404)'
              break
            case 408:
              message = '请求超时(408)'
              break
            case 500:
              message = '服务器错误(500)'
              break
            case 501:
              message = '服务未实现(501)'
              break
            case 502:
              message = '网络错误(502)'
              break
            case 503:
              message = '服务不可用(503)'
              break
            case 504:
              message = '网络超时(504)'
              break
            case 505:
              message = 'HTTP版本不受支持(505)'
              break
            default:
              message = `连接出错(${err.response.status})!`
          }
          // 这里错误消息可以使用全局弹框展示出来
          // 比如element plus 可以使用 ElMessage
          ElMessage({
            showClose: true,
            message: `${message}，请检查网络或联系管理员！`,
            type: 'error',
          })
        } else if (err.request) {
          // 请求已经成功发起，但没有收到响应
          // `error.request` 在浏览器中是 XMLHttpRequest 的实例，
          // 而在node.js中是 http.ClientRequest 的实例
          console.log(err.request)
        } else {
          // 发送请求时出了点问题
          console.log('Error', err.message)
        }

        // 这里是AxiosError类型，所以一般我们只reject我们需要的响应即可
        return Promise.reject(err.response)
      },
    )
  }
}

export const request = new Request()
//export { Request, request }
*/
