import { IServerUser, IServerRole } from "@/server/types/account/user";
import { IServerCompany } from "@/server/types/system/company";
import { IServerBrand, IServerBrandView } from "@/server/types/system/brand";
import { IServerMaterial } from "@/server/types/system/material";
import { IServerBrandPublicView } from "../system/brand";

export interface IServerProject {
  id: string; //id,主键
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id,创建者创建者
  name: string; //name,项目名称项目名称
  location: string; //location,项目地点项目地点
  totalTaxIncluded: number; //total_tax_included,总投资_含税，万元总投资_含税，万元
  totalTaxNotIncluded: number; //total_tax_not_included,总投资_不含税，万元总投资_不含税，万元
  buildingAreaAboveGround: number; //building_area_above_ground,建筑面积：地上，平米建筑面积：地上，平米
  buildingAreaUnderGround: number; //building_area_under_ground,建筑面积：地下，平米建筑面积：地下，平米
  companyConstructionId: string; //t_company_construction_id,外键,	t_company_construction_id<-表t_company.id,建设单位：建设单位类型、内部（设计部、工程部）建设单位：建设单位类型、内部（设计部、工程部）
  note: string; //note,其它其它
  createDatetime: Date; //create_datetime,项目创建时间项目创建时间
  endDatetime: Date; //end_datetime,项目结束时间
  deletedAt: Date | null; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerSearchProject {
  id: string; //id,主键
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id,创建者创建者
  name: string; //name,项目名称项目名称
  location: string; //location,项目地点项目地点
  totalTaxIncluded: number; //total_tax_included,总投资_含税，万元总投资_含税，万元
  totalTaxNotIncluded: number; //total_tax_not_included,总投资_不含税，万元总投资_不含税，万元
  buildingAreaAboveGround: number; //building_area_above_ground,建筑面积：地上，平米建筑面积：地上，平米
  buildingAreaUnderGround: number; //building_area_under_ground,建筑面积：地下，平米建筑面积：地下，平米
  companyConstructionId: string; //t_company_construction_id,外键,	t_company_construction_id<-表t_company.id,建设单位：建设单位类型、内部（设计部、工程部）建设单位：建设单位类型、内部（设计部、工程部）
  companyDesignId: string;
  note: string; //note,其它其它
  createDatetime: Date; //create_datetime,项目创建时间项目创建时间
  endDatetime: Date; //end_datetime,项目结束时间
  deletedAt: Date | null; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectDesignCompany {
  id: string; //id,主键
  projectId: string;
  designCompanyId: string;
  deletedAt: Date | null; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectForm {
  project: IServerProject;
  companyDesignList: IServerCompany[];
}

export interface IServerProjectView {
  project: IServerProject; //t_project
  user: IServerUser; //外键：t_user_id,关联表为：t_user表,创建者
  companyConstruction: IServerCompany; //外键：t_company_construction_id,关联表为：t_company表,建设单位：建设单位类型、内部（设计部、工程部）
  companyDesignList: IServerCompany[]; //外键：t_company_design_id,关联表为：t_company表,设计单位类型：设计单位
  companySupervision: IServerCompany; //监理单位
  companyGeneralContract: IServerCompany; //总包单位
}

export interface IServerProjectUser {
  id: string; //id,主键
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id,用户用户
  projectId: string; //t_project_id,外键,	t_project_id<-表t_project.id
  roleId: string; //t_role_id,外键,	t_role_id<-表t_role.id
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectUserView {
  projectUser: IServerProjectUser;
  user: IServerUser;
  project: IServerProject;
  role: IServerRole;
}

export interface IServerProjectCompanyAllUserView {
  projectUserViewList: Array<IServerProjectUserView>; //设计单位员工列表，包括项目经理和项目员工
  company: IServerCompany;
}

export interface IServerProjectAllUserView {
  projectUserViewListDesignCompany: Array<IServerProjectCompanyAllUserView>; //设计单位员工列表，包括项目经理和项目员工
  projectUserViewListDesignDepartment: Array<IServerProjectUserView>; //设计部员工列表，包括项目经理和项目员工
  projectUserViewListEngineeringDepartment: Array<IServerProjectUserView>; //工程部员工列表，包括项目经理和项目员工
  projectUserViewListSupervisionCompany: Array<IServerProjectUserView>; //监理单位员工列表，包括项目经理和项目员工
  projectUserViewListConstructionCompany: Array<IServerProjectUserView>; //总包单位员工列表，包括项目经理和项目员工
  projectView: IServerProjectView; //项目
}

export interface IServerProjectBrand {
  id: string; //id,主键
  projectId: string; //t_project_id,外键,	t_project_id<-表t_project.id
  brandId: string; //t_brand_id,外键,	t_brand_id<-表t_brand.id
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectBrandView {
  projectBrand: IServerProjectBrand; //t_project_brand
  project: IServerProject; //外键：t_project_id,关联表为：t_project表,
  brandView: IServerBrandView; //外键：t_brand_id,关联表为：t_brand表,
}

export interface IServerProjectBrandForm {
  project: IServerProject;
  brand: IServerBrand;
}

export interface IServerProjectMaterial {
  id: string; //id,主键
  projectId: string; //t_project_id,外键,	t_project_id<-表t_project.id
  companyId: string; //t_company_id,外键,	t_company_id<-表t_company.id
  materialId: string; //t_material_id,外键,	t_material_id<-表t_material.id
  materialOriginId: string; //t_material_origin_id,项目原始材料，t_material_id是此材料修改而来，t_material_id可以和t_material_origin_id相同
  materialCount: number; //material_count,数量数量
  materialUnit: string; //material_unit,数量单位数量单位
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectMaterialView {
  projectMaterial: IServerProjectMaterial; //t_project_material
  project: IServerProject; //外键：t_project_id,关联表为：t_project表,
  material: IServerMaterial; //外键：t_material_count_id,关联表为：t_material_count表,
  materialOrigin: IServerMaterial; //原始材料，如果发生改动，则基于原始材料进行改动
  projectMaterialBrandPrivateViewList: IServerProjectMaterialBrandPrivateView[];
  projectMaterialBrandPublicViewList: IServerProjectMaterialBrandPublicView[];
}

export interface IServerProjectMaterialForm {
  projectMaterial: IServerProjectMaterial;
  material: IServerMaterial; //材料
  materialChange: boolean; //材料变更,用户是否修改了材料参数（不包括品牌、数量和数量单位）
  publicBrandIds: string[]; //参考品牌(公共)，json格式
  projectBrandIds: string[]; //参考品牌（项目私有），json格式
  projectBrandViewList: IServerProjectBrandView[];
  photoTempDir: string; //照片目录
  photoIds: string[]; //照片ID
}

export interface IServerProjectMaterialBrandPrivate {
  id: string; //id,主键
  projectMaterialId: string; //t_project_material_id,外键,	t_project_material_id<-表t_project_material.id
  projectBrandId: string; //t_project_brand_id,外键,	t_project_brand_id<-表t_project_brand.id
  deletedAt: Date; //deleted_at
}

export interface IServerProjectMaterialBrandPrivateView {
  projectMaterialBrandPrivate: IServerProjectMaterialBrandPrivate; //t_project_material_brand_private
  projectMaterial: IServerProjectMaterial; //外键：t_project_material_id,关联表为：t_project_material表,
  projectBrandView: IServerProjectBrandView; //外键：t_project_brand_id,关联表为：t_project_brand表,
}

export interface IServerProjectMaterialBrandPublic {
  id: string; //id,主键
  projectMaterialId: string; //t_project_material_id,外键,	t_project_material_id<-表t_project_material.id
  brandPublicId: string; //t_brand_public_id,外键,	t_brand_public_id<-表t_brand_public.id
  deletedAt: Date; //deleted_at
}
export interface IServerProjectMaterialBrandPublicView {
  projectMaterialBrandPublic: IServerProjectMaterialBrandPublic; //t_project_material_brand_public
  projectMaterial: IServerProjectMaterial; //外键：t_project_material_id,关联表为：t_project_material表,
  brandPublicView: IServerBrandPublicView; //外键：t_brand_public_id,关联表为：t_brand_public表,
}
