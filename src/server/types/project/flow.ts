import { IServerProject, IServerProjectView } from "./project";
import { IServerUser } from "../account/user";
import { IServerCompany } from "@/server/types/system/company";

export interface IServerProjectUserTask {
  projectView: IServerProjectView;
  taskId: string;
  taskName: string;
  assignee: IServerUser;
  processInstanceId: string;
  taskDefinitionKey: string;

  // role
  designCompanyEmployee: boolean;
  engineeringDepartmentEmployee: boolean;
  designDepartmentManager: boolean;
  engineeringDepartmentManager: boolean;
  designDepartmentEmployee: boolean;
  generalContractorEmployee: boolean;
  supervisionCompanyEmployee: boolean;
}

export interface IServerProjectUserCompletedTask {
  projectView: IServerProjectView;
  taskId: string;
  taskName: string;
  assignee: IServerUser;
  processInstanceId: string;
  operateDate: Date;
}

export interface IServerProjectHistory {
  projectView: IServerProjectView;
  taskId: string;
  taskName: string;
  assignee: IServerUser;
  processInstanceId: string;
  operateDate: Date;
}

export interface IServerProjectOpHistory {
  id: string; //id,主键
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id,操作或审批人操作或审批人
  projectId: string; //t_project_id,外键,	t_project_id<-表t_project.id,审批的项目审批的项目
  opDatetime: Date; //op_datetime,操作或审批的时间操作或审批的时间
  stepDescription: string; //step_description,步骤详细描述步骤详细描述
  stepPhase: string; //step_phase,步骤的阶段步骤的阶段
  deletedAt: Date; //deleted_at
}

export interface IServerProjectOpHistoryView {
  projectOpHistory: IServerProjectOpHistory; //t_project_op_history
  user: IServerUser; //外键：t_user_id,关联表为：t_user表,操作或审批人
  project: IServerProject; //外键：t_project_id,关联表为：t_project表,审批的项目
  company: IServerCompany;
}
