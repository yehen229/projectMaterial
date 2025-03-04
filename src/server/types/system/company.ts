import { IServerUser, IServerRole } from "@/server/types/account/user";
import {
  IServerProject,
  IServerProjectUser,
  IServerProjectUserView,
} from "@/server/types/project/project";

export interface IServerCompany {
  id: string; //id,主键
  name: string; //name,单位名称单位名称
  companyType: string; //company_type,单位类型：设计单位、设计部、工程部、监理单位、总包单位等单位类型：设计单位、设计部、工程部、监理单位、总包单位等
  note: string; //note,备注备注
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerCompanyUser {
  id: string; //id,主键
  companyId: string; //t_company_id,外键,	t_company_id<-表t_company.id
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerCompanyUserForm {
  companyUserId: string; //companyuserid
  companyId: string; //公司id
  userRealName: string; //用户名称
  tel: string; //电话
  email: string; //邮箱
}

export interface IServerCompanyUserView {
  companyUser: IServerCompanyUser; //t_company_user
  company: IServerCompany; //外键：t_company_id,关联表为：t_company表,
  user: IServerUser; //外键：t_user_id,关联表为：t_user表,
  projectUserViewList: IServerProjectUserView[];
}
