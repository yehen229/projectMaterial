import {
  IServerProject,
  IServerProjectMaterial,
  IServerProjectMaterialBrandPrivate,
  IServerProjectMaterialBrandPublic,
  IServerProjectMaterialBrandPrivateView,
  IServerProjectView,
  IServerProjectMaterialBrandPublicView,
  IServerProjectMaterialView,
} from "./project";

import { IServerUser } from "../account/user";
import { IServerBrand } from "../system/brand";

import {
  IServerMaterialClassifyDivision,
  IServerMaterialClassifyGroup,
  IServerMaterialClassifySection,
} from "@/server/types/system/material";

export interface IServerProjectEnd {
  id: string; //id,主键
  projectId: string; //projectId,外键,	t_projecct_id<-表t_project.id
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id
  createDatetime: Date; //create_datetime
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectEndFile {
  id: string; //id,主键
  filePath: string; //file_path
  projectEndId: string; //t_project_end_id,外键,	t_project_end_id<-表t_project_end.id
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectEndFileView {
  projectEndFile: IServerProjectEndFile; //t_project_end_file
  projectEnd: IServerProjectEnd; //外键：t_project_end_id,关联表为：t_project_end表,
}
export interface IServerProjectEndForm {
  projectEnd: IServerProjectEnd;
  reviewResult: number; //项目是否结项，0：不结束项目；1.结束项目
  reviewTempDir: string; //review_temp_dir,临时审核附件目录，项目审核附件放在此目录中
  projectId: string;
  taskId: string;
}

export interface IServerProjectEndView {
  projectEnd: IServerProjectEnd; //t_project_end
  projecct: IServerProject; //外键：t_projecct_id,关联表为：t_project表,
  user: IServerUser; //外键：t_user_id,关联表为：t_user表,
  projectEndFileList: IServerProjectEndFile[];
}
