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
import { IServerMaterial } from "../system/material";

import {
  IServerMaterialClassifyDivision,
  IServerMaterialClassifyGroup,
  IServerMaterialClassifySection,
} from "@/server/types/system/material";

export interface IServerProjectReviewMode {
  id: string; //id,主键
  projectId: string; //t_project_id,外键,	t_project_id<-表t_project.id
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id,项目经理ID项目经理ID
  mode: number; //mode,分发审核或直接审核分发审核或直接审核
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
  createDatetime: Date;
  companyId:string
}

export interface IServerProjectReviewModeView {
  projectReviewMode: IServerProjectReviewMode; //t_project_review_mode
  project: IServerProject; //外键：t_project_id,关联表为：t_project表,
  user: IServerUser; //外键：t_user_id,关联表为：t_user表,项目经理ID
}

export interface IServerProjectReview {
  id: string; //id,主键
  projectId: string; //t_project_id,外键,	t_project_id<-表t_project.id
  projectReviewModeId: string; //t_project_review_mode_id,外键,
  reviewResult: number; //review_result,最终审核结果最终审核结果
  reviewStatus: number; //review_status,审核状态：审核状态：审核状态：0：未分配审核方式。1：已经分配审核方式，正处于审核状态（t_project_review_mode增加一条记录）如果是直接审核，则t_project_review_user增加项目经理审核意见，提交后修改t_project_review中审核状态；如果是分发审核，则项目员工分别填写意见，项目经理汇总后提交。2.审核结束审核状态：审核状态：审核状态：0：未分配审核方式。1：已经分配审核方式，正处于审核状态（t_project_review_mode增加一条记录）如果是直接审核，则t_project_review_user增加项目经理审核意见，提交后修改t_project_review中审核状态；如果是分发审核，则项目员工分别填写意见，项目经理汇总后提交。2.审核结束
  reviewDatetime: Date; //review_datetime,审核时间审核时间
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectReviewView {
  projectReview: IServerProjectReview; //t_project_review
  project: IServerProject; //外键：t_project_id,关联表为：t_project表,
  projectReviewMode: IServerProjectReviewMode;
}

//对项目材料的某次审核统计信息
export interface IServerProjectReviewStatistics {
  projectReviewView: IServerProjectReviewView;
  reviewResultAccept: number; //同意人数
  reviewResultReject: number; //不同意人数
  reviewResultUnreviewed: number; //未审核人数
}

export interface IServerProjectReviewUser {
  id: string; //id,主键
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id
  projectReviewId: string; //t_project_review_id,外键,	t_project_review_id<-表t_project_review.id
  reviewResult: number; //review_result,审核结果：通过，不通过审核结果：通过，不通过
  reviewContent: string; //review_content,审核意见审核意见
  reviewDatetime: Date; //review_datetime,审核时间审核时间
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectReviewUserFile {
  id: string; //id,主键
  projectReviewUserId: string; //t_project_review_user_id,外键,	t_project_review_user_id<-表t_project_review_user.id
  filePath: string; //file_path
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectReviewUserFileView {
  projectReviewUserFile: IServerProjectReviewUserFile; //t_project_review_user_file
  projectReviewUser: IServerProjectReviewUser; //外键：t_project_review_user_id,关联表为：t_project_review_user表,
}

export interface IServerProjectReviewUserView {
  //评阅记录用户
  projectReviewUser: IServerProjectReviewUser; //t_project_review_user

  //评阅用户
  user: IServerUser; //外键：t_user_id,关联表为：t_user表,

  //评阅记录
  projectReview: IServerProjectReview; //外键：t_project_review_id,关联表为：t_project_review表,

  //评阅的附件
  projectReviewUserFileList: IServerProjectReviewUserFile[];
}

export interface IServerProjectReviewForm {
  companyId:string,
  projectId: string;
  taskId: string;
  designCompanyIndex: number;
  projectReviewMode: IServerProjectReviewMode;
  projectReview: IServerProjectReview;
  projectReivewUser: IServerProjectReviewUser;
  reviewTempDir: string; //review_temp_dir,临时审核附件目录，项目审核附件放在此目录中
}

export interface IServerProjectReviewDispatchForm {
  companyId:string,
  projectId: string;
  taskId: string;
  designCompanyIndex: number;
  projectReviewMode: IServerProjectReviewMode;
  employeeIds: string[];
}

export interface IServerProjectReviewEmployeeForm {
  projectReviewUser: IServerProjectReviewUser;
  reviewTempDir: string; //review_temp_dir,临时审核附件目录，项目审核附件放在此目录中
  projectId: string;
  taskId: string;
  designCompanyIndex: number;
}
export interface IServerProjectReviewManagerForm {
  projectId: string;
  taskId: string;
  designCompanyIndex: number;
  projectReviewUser: IServerProjectReviewUser;
  reviewTempDir: string; //review_temp_dir,临时审核附件目录，项目审核附件放在此目录中
  employeeReviewFileIds: string[];
}

export interface IServerProjectCompany {
  id: string; //id,主键
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id,用户，工程部员工用户，工程部员工
  projectId: string; //t_project_id
  generalContractorCompanyId: string; //t_general_contractor_company_id,外键,	t_general_contractor_company_id<-表t_company.id,总包单位总包单位
  supervisionCompanyId: string; //t_supervision_company_id,外键,	t_supervision_company_id<-表t_company.id,监理单位监理单位
  createDatetime: Date; //create_datetime,分配时间分配时间
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerUseMaterial {
  id: string; //id,主键
  useMaterialBrandSelectId: string;
  projectMaterialId: string; //t_project_material_id,外键,	t_project_material_id<-表t_project_material.id,项目物料项目物料
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id,用户（总包单位人员）用户（总包单位人员）
  projectMaterialBrandPrivateId: string; //t_project_material_brand_private_id,外键,	t_project_material_brand_private_id<-表t_project_material_brand_private.id,品牌品牌
  projectMaterialBrandPublicId: string; //t_project_material_brand_public_id,外键,	t_project_material_brand_public_id<-表t_project_material_brand_public.id
  materialId: string;
  materialCount: number; //material_count,数量数量
  materialUnit: string; //material_unit,数量单位数量单位
  isAppearance: number; //is_appearance,是否影响外观是否影响外观
  createDatetime: Date; //create_datetime,创建时间创建时间
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerUseMaterialNewBrand {
  id: string; //id,主键
  useMaterialId: string; //t_use_material_id,外键,	t_use_material_id<-表t_use_material.id
  brandName: string; //brand_name,品牌品牌
  materialClassifyDivisionId: string; //t_material_classify_division_id,外键,	t_material_classify_division_id<-表t_material_classify_division.id,大类专业大类专业
  materialClassifyGroupId: string; //t_material_classify_group_id,外键,	t_material_classify_group_id<-表t_material_classify_group.id,中类材料分类中类材料分类
  materialClassifySectionId: string; //t_material_classify_section_id,外键,	t_material_classify_section_id<-表t_material_classify_section.id,小类材料名称小类材料名称
  projectMaterialBrandPrivateId: string; //t_project_material_brand_private_id
  materialPosition: string; //material_position,定位：合资、国产等定位：合资、国产等
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerUseMaterialNewBrandFile {
  id: string; //id,主键
  useMaterialNewBrandId: string; //t_use_material_new_brand_id,外键,	t_use_material_new_brand_id<-表t_use_material_new_brand.id
  filePath: string; //file_path
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerUseMaterialNewBrandFileView {
  useMaterialNewBrandFile: IServerUseMaterialNewBrandFile; //t_use_material_new_brand_file
  useMaterialNewBrand: IServerUseMaterialNewBrand; //外键：t_use_material_new_brand_id,关联表为：t_use_material_new_brand表,
}

export interface IServerUseMaterialNewBrandView {
  useMaterialNewBrand: IServerUseMaterialNewBrand; //t_use_material_new_brand
  useMaterial: IServerUseMaterial; //外键：t_use_material_id,关联表为：t_use_material表,
  materialClassifyDivision: IServerMaterialClassifyDivision; //外键：t_material_classify_division_id,关联表为：t_material_classify_division表,大类专业
  materialClassifyGroup: IServerMaterialClassifyGroup; //外键：t_material_classify_group_id,关联表为：t_material_classify_group表,中类材料分类
  materialClassifySection: IServerMaterialClassifySection; //外键：t_material_classify_section_id,关联表为：t_material_classify_section表,小类材料名称
  projectMaterialBrandPrivateView: IServerProjectMaterialBrandPrivateView;
  useMaterialNewBrandFileViewList: IServerUseMaterialNewBrandFileView[];
}

export interface IServerUseMaterialView {
  useMaterial: IServerUseMaterial; //t_use_material
  projectMaterialView: IServerProjectMaterialView; //外键：t_project_material_id,关联表为：t_project_material表,项目物料
  user: IServerUser; //外键：t_user_id,关联表为：t_user表,用户（总包单位人员）
  projectMaterialBrandPrivateView: IServerProjectMaterialBrandPrivateView; //外键：t_project_material_brand_private_id,关联表为：t_project_material_brand_private表,品牌
  projectMaterialBrandPublicView: IServerProjectMaterialBrandPublicView; //外键：t_project_material_brand_public_id,关联表为：t_project_material_brand_public表,
  useMaterialNewBrandView: IServerUseMaterialNewBrandView;
  material: IServerMaterial; //外键：t_material_id,关联表为：t_material表,
}

export interface IServerUseMaterialFormItem {
  projectMaterialId: string;
  isAppearance: number;
  projectMaterialBrandPublicId: string;
  projectMaterialBrandPrivateId: string;
  userId: string;
  brandName: string; //品牌名称，如果是新建品牌，则此字段不能为空
  materialClassifyDivisionId: string;
  materialClassifyGroupId: string;
  materialClassifySectionId: string;
  materialPosition: string; //品牌定位：合资、国产等
  tempFileDir: string; //新建品牌附件目录，如果是新建品牌，则此字段不能为空

  //材料变更相关
  materialChange: boolean; //是否需要物料变更
  material: IServerMaterial; //材料
  projectMaterial: IServerProjectMaterial; //项目物料
  photoFileDir: string; //材料样本图片目录
  photoIds: string[]; //材料样本图片id
}

export interface IServerUseMaterialForm {
  projectId: string; //项目号
  taskId: string;
  userId: string;
  useMaterialFormItems: IServerUseMaterialFormItem[];
}

export interface IServerUseMaterialBrandSelect {
  id: string; //id,主键
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id
  projectId: string; //t_project_id,外键,	t_project_id<-表t_project.id
  createDatetime: Date; //create_datetime
  deletedAt: Date; //deleted_at
}

export interface IServerUseMaterialBrandSelectView {
  useMaterialBrandSelect: IServerUseMaterialBrandSelect; //t_use_material_brand_select
  user: IServerUser; //外键：t_user_id,关联表为：t_user表,
  project: IServerProject; //外键：t_project_id,关联表为：t_project表,
  useMaterialViewList: IServerUseMaterialView[];
}

export interface IServerProjectAppearanceReviewMode {
  id: string; //id,主键
  useMaterialBrandSelectId: string; //t_use_material_brand_select_id
  projectMaterialId: string; //t_project_material_id,外键,	t_project_material_id<-表t_project_material.id
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id
  mode: number; //mode
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
  createDatetime: Date;
  affectAppearance: number; //影响外观
}
export interface IServerProjectAppearanceReview {
  id: string; //id,主键
  projectAppearanceReviewModeId: string; //t_project_appearance_review_mode_id,外键,	t_project_material_id<-表t_project_material.id
  useMaterialId: string;
  reviewStatus: number; //review_status,审核状态审核状态
  reviewResult: number; //review_result,最终审核结果最终审核结果
  reviewDatetime: Date; //review_datetime,最终审核时间最终审核时间
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectAppearanceReviewUser {
  id: string; //id,主键
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id
  projectAppearanceReviewId: string; //t_project_appearance_review_id,外键,	t_project_appearance_review_id<-表t_project_appearance_review.id
  reviewResult: number; //review_result
  reviewContent: string; //review_content
  reviewDatetime: Date; //review_datetime
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectAppearanceReviewUserFile {
  id: string; //id,主键
  projectAppearanceReviewUserId: string; //t_project_appearance_review_user_id,外键,	t_project_appearance_review_user_id<-表t_project_appearance_review_user.id
  filePath: string; //file_path
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectAppearanceReviewUserView {
  projectAppearanceReviewUser: IServerProjectAppearanceReviewUser; //t_project_appearance_review_user
  user: IServerUser; //外键：t_user_id,关联表为：t_user表,
  projectAppearanceReview: IServerProjectAppearanceReview; //外键：t_project_appearance_review_id,关联表为：t_project_appearance_review表,
  projectAppearanceReviewUserFileList: IServerProjectAppearanceReviewUserFile[];
}

export interface IServerAppearanceReviewForm {
  projectAppearanceReviewUser: IServerProjectAppearanceReviewUser;
  projectId: string;
  useMaterialBrandSelectId: string; //总包单位品牌选择、物料选择等ID
  reviewTempDir: string; //review_temp_dir,临时审核附件目录，项目审核附件放在此目录中
}

export interface IServerProjectAppearanceReviewManagerDirectForm {
  projectAppearanceReviewMode: IServerProjectAppearanceReviewMode;
  projectAppearanceReviewUser: IServerProjectAppearanceReviewUser;
  projectId: string;
  taskId: string;
  reviewTempDir: string; //review_temp_dir,临时审核附件目录，项目审核附件放在此目录中
}

export interface IServerProjectAppearanceReviewEmployeeForm {
  projectAppearanceReviewUser: IServerProjectAppearanceReviewUser;
  reviewTempDir: string; //review_temp_dir,临时审核附件目录，项目审核附件放在此目录中
  projectId: string;
  taskId: string;
  designCompanyIndex: number;
}

export interface IServerProjectAppearanceReviewDispatchForm {
  projectId: string;
  taskId: string;
  projectAppearanceReviewMode: IServerProjectAppearanceReviewMode;
  employeeIds: string[];
}

export interface IServerProjectAppearanceReviewManagerSummaryForm {
  projectAppearanceReviewUser: IServerProjectAppearanceReviewUser;
  projectId: string;
  taskId: string;
  reviewTempDir: string; //review_temp_dir,临时审核附件目录，项目审核附件放在此目录中
  employeeReviewFileIds: string[];
}

export interface IServerBuyMaterial {
  id: string; //id,主键
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id,购买用户（总包单位人员）购买用户（总包单位人员）
  useMaterialId: string; //t_use_material_id,外键,	t_use_material_id<-表t_use_material.id,物料使用申请物料使用申请
  projectMaterialBrandPrivateId: string; //t_project_material_brand_private_id,外键,	t_project_material_brand_private_id<-表t_project_material_brand_private.id,私有品牌私有品牌
  projectMaterialBrandPublicId: string; //t_project_material_brand_public_id,外键,	t_project_material_brand_public_id<-表t_project_material_brand_public.id,公有品牌公有品牌
  materialCount: number; //material_count,材料数量材料数量
  materialUnit: string; //material_unit,数量单位数量单位
  batch: number; //batch,批次批次
  qrcode: string; //qrcode,二维码二维码
  createDatetime: Date; //create_datetime,创建时间创建时间
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerBuyMaterialBatch {
  id: string; //id,主键
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id,用户用户
  projectId: string; //t_project,外键,	t_project<-表t_project.id,项目项目
  createDatetime: Date; //create_datetime
  deletedAt: Date; //deleted_at
}

export interface IServerBuyMaterialView {
  buyMaterial: IServerBuyMaterial; //t_buy_material
  user: IServerUser; //外键：t_user_id,关联表为：t_user表,
  useMaterialView: IServerUseMaterialView; //外键：t_use_material_id,关联表为：t_use_material表,物料使用申请
  projectMaterialBrandPrivate: IServerProjectMaterialBrandPrivateView; //外键：t_project_material_brand_private_id,关联表为：t_project_material_brand_private表,私有品牌
  projectMaterialBrandPublic: IServerProjectMaterialBrandPublicView; //外键：t_project_material_brand_public_id,关联表为：t_project_material_brand_public表,公有品牌
}

export interface IServerBuyMaterialForm {
  projectId: string;
  taskId: string;
  buyMaterials: IServerBuyMaterial[];
}
export interface IServerProjectMaterialVerificationDocument {
  id: string; //id,主键
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id
  buyMaterialId: string; //t_buy_material_id,外键,	t_project_material_id<-表t_project_material.id
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectMaterialVerificationDocumentFile {
  id: string; //id,主键
  projectMaterialVerificationDocumentId: string; //t_project_material_verification_document_id,外键,	t_project_material_verification_document_id<-表t_project_material_verification_document.id
  filePath: string; //file_path,文件路径文件路径
  fileType: number; //file_type,文件类型：工程材料或设备报验材料文件类型：工程材料或设备报验材料
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectMaterialVerificationDocumentView {
  projectMaterialVerificationDocumentFileList: IServerProjectMaterialVerificationDocumentFile[];
  projectMaterialVerificationDocument: IServerProjectMaterialVerificationDocument; //t_project_material_verification_document
  user: IServerUser; //外键：t_user_id,关联表为：t_user表,
  buyMaterialView: IServerBuyMaterialView; //外键：t_project_material_id,关联表为：t_project_material表,
}
export interface IServerGeneralContractorSubProjectMaterialVerificationDocumentForm {
  project: IServerProject;
  taskId: string;
}

export interface IServerProjectMaterialRetestBatch {
  id: string; //id,主键
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id
  projectId: string; //t_project_id,外键,	t_project_id<-表t_project.id
  buyMaterialBatchId: string; //t_buy_material_batch_id,外键,	t_buy_material_batch_id<-表t_buy_material_batch.id
  createDatetime: Date; //create_datetime
  deletedAt: Date;
} //deleted_at

export interface IServerProjectMaterialRetest {
  id: string; //id,主键
  buyMaterialId: string; //t_buy_material_id,外键,	t_buy_material_id<-表t_buy_material.id
  projectMaterialRetestBatchId: string; //t_project_material_retest_batch_id
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id
  needRetest: number; //need_retest,是否需要复检。0：不需要，1需要
  reviewResult: number; //review_result,审核结果，0未审核；1审核通过；2.审核不通过
  reviewContent: string; //review_content
  reviewDatetime: Date; //review_datetime
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectMaterialRetestForm {
  projectMaterialRetestList: IServerProjectMaterialRetest[];
  projectId: string;
  taskId: string;
  reviewTempDir: string;
}
export interface IServerProjectMaterialRetestView {
  projectMaterialRetest: IServerProjectMaterialRetest;
  buyMaterial: IServerBuyMaterial;
  user: IServerUser;
  projectMaterialRetestBatch: IServerProjectMaterialRetestBatch;
}
export interface IServerProjectMaterialRetestBatchFile {
  id: string; //id,主键
  projectMaterialRetestBatchId: string; //t_project_material_retest_batch_id,外键,	t_project_material_retest_id<-表t_project_material_retest.id
  filePath: string; //file_path,文件路径文件路径
  fileType: number; //file_type,文件类型：工程材料或设备报验材料文件类型：工程材料或设备报验材料
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectMaterialRetestBatchView {
  projectMaterialRetestBatch: IServerProjectMaterialRetestBatch; //t_project_material_retest_batch
  user: IServerUser; //外键：t_user_id,关联表为：t_user表,
  project: IServerProject; //外键：t_project_id,关联表为：t_project表,
  buyMaterialBatch: IServerBuyMaterialBatch; //外键：t_buy_material_batch_id,关联表为：t_buy_material_batch表,
  projectMaterialRetestFileList: IServerProjectMaterialRetestBatchFile[];
  projectMaterialRetestViewList: IServerProjectMaterialRetestView[];
}

export interface IServerProjectMaterialAcceptance {
  id: string; //id,主键
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id
  projectMaterialAcceptanceBatchId: string; //t_project_material_acceptance_batch_id
  projectMaterialId: string; //t_project_material_id,外键,	t_project_material_id<-表t_project_material.id
  projectMaterialBrandPrivateId: string; //t_project_material_brand_private_id,外键,	t_project_material_brand_private_id<-表t_project_material_brand_private.id
  projectMaterialBrandPublicId: string; //t_project_material_brand_public_id,外键,	t_project_material_brand_public_id<-表t_project_material_brand_public.id
  materialCount: number; //material_count,材料数量材料数量
  materialUnit: string; //material_unit,数量单位数量单位
  position: string;
  note: string;
  createDatetime: Date; //create_datetime
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectMaterialAcceptanceBatch {
  id: string; //id,主键
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id
  projectId: string; //t_project_id,外键,	t_project_id<-表t_project.id
  createDatetime: Date; //create_datetime
  deletedAt: Date; //deleted_at
}

export interface IServerProjectMaterialAcceptanceBatchView {
  projectMaterialAcceptanceBatch: IServerProjectMaterialAcceptanceBatch; //t_project_material_acceptance_batch
  user: IServerUser; //外键：t_user_id,关联表为：t_user表,
  project: IServerProject; //外键：t_project_id,关联表为：t_project表,
}

export interface IServerProjectMaterialAcceptanceView {
  projectMaterialAcceptance: IServerProjectMaterialAcceptance; //t_project_material_acceptance
  user: IServerUser; //外键：t_user_id,关联表为：t_user表,
  projectMaterialView: IServerProjectMaterialView; //外键：t_project_material_id,关联表为：t_project_material表,
  projectMaterialBrandPrivate: IServerProjectMaterialBrandPrivateView; //外键：t_project_material_brand_private_id,关联表为：t_project_material_brand_private表,
  projectMaterialBrandPublic: IServerProjectMaterialBrandPublicView; //外键：t_project_material_brand_public_id,关联表为：t_project_material_brand_public表,
}

export interface IServerProjectMaterialAcceptanceReviewMode {
  id: string; //id,主键
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id
  projectMaterialAcceptanceBatchId: string; //t_project_material_acceptance_batch_id,外键,	t_project_material_acceptance_batch_id<-表t_project_material_acceptance_batch.id
  mode: number; //mode,分发审核或直接审核分发审核或直接审核
  createDatetime: Date; //create_datetime
  deletedAt: Date; //deleted_at
}

export interface IServerProjectMaterialAcceptanceReviewModeView {
  projectMaterialAcceptanceReviewMode: IServerProjectMaterialAcceptanceReviewMode; //t_project_material_acceptance_review_mode
  user: IServerUser; //外键：t_user_id,关联表为：t_user表,
  projectMaterialAcceptanceBatch: IServerProjectMaterialAcceptanceBatch; //外键：t_project_material_acceptance_batch_id,关联表为：t_project_material_acceptance_batch表,
}

export interface IServerProjectMaterialAcceptanceReview {
  id: string; //id,主键
  projectMaterialAcceptanceModeId: string; //t_project_material_acceptance_mode_id,外键,	t_project_material_acceptance_mode_id<-表t_project_material_acceptance.id
  reviewStatus: number; //review_status,审核状态审核状态
  reviewResult: number; //review_result,最终审核结果最终审核结果
  reviewDatetime: number; //review_datetime,最终审核时间最终审核时间
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectMaterialAcceptanceReviewUser {
  id: string; //id,主键
  userId: string; //t_user_id,外键,	t_user_id<-表t_user.id
  projectMaterialAcceptanceReviewId: string; //t_project_material_acceptance_review_id,外键,	t_project_material_acceptance_review_id<-表t_project_material_acceptance_review.id
  reviewResult: number; //review_result,审核结果审核结果
  reviewContent: string; //review_content,审核意见审核意见
  reviewDatetime: Date; //review_datetime,审核时间审核时间
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectMaterialAcceptanceReviewUserFile {
  id: string; //id,主键
  filePath: string; //file_path
  projectMaterialAcceptanceReviewUserId: string; //t_project_material_acceptance_review_user_id,外键,	t_project_material_acceptance_review_user_id<-表t_project_material_acceptance_review_user.id
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerProjectMaterialAcceptanceReviewUserFileView {
  projectMaterialAcceptanceReviewUserFile: IServerProjectMaterialAcceptanceReviewUserFile; //t_project_material_acceptance_review_user_file
  projectMaterialAcceptanceReviewUser: IServerProjectMaterialAcceptanceReviewUser; //外键：t_project_material_acceptance_review_user_id,关联表为：t_project_material_acceptance_review_user表,
}

export interface IServerProjectMaterialAcceptanceReviewUserView {
  projectMaterialAcceptanceReviewUser: IServerProjectMaterialAcceptanceReviewUser; //t_project_material_acceptance_review_user
  user: IServerUser; //外键：t_user_id,关联表为：t_user表,
  projectMaterialAcceptanceReview: IServerProjectMaterialAcceptanceReview; //外键：t_project_material_acceptance_review_id,关联表为：t_project_material_acceptance_review表,

  projectMaterialAcceptanceReviewUserFileList: IServerProjectMaterialAcceptanceReviewUserFile[];
}

export interface IServerProjectMaterialAcceptanceReviewView {
  projectMaterialAcceptanceReview: IServerProjectMaterialAcceptanceReview; //t_project_material_acceptance_review
  projectMaterialAcceptance: IServerProjectMaterialAcceptance; //外键：t_project_material_report_id,关联表为：t_project_material_acceptance表,
}

export interface IServerProjectMaterialAcceptanceBatchForm {
  projectId: string;
  taskId: string;
  projectMaterialAcceptanceBatch: IServerProjectMaterialAcceptanceBatch;
  projectMaterialAcceptanceList: IServerProjectMaterialAcceptance[];
}

export interface IServerProjectMaterialAcceptanceReviewDispatchForm {
  projectId: string;
  taskId: string;
  projectMaterialAcceptanceReviewMode: IServerProjectMaterialAcceptanceReviewMode;
  employeeIds: string[];
}

export interface IServerProjectMaterialAcceptanceReviewEmployeeForm {
  projectMaterialAcceptanceReviewUser: IServerProjectMaterialAcceptanceReviewUser;
  reviewTempDir: string; //review_temp_dir,临时审核附件目录，项目审核附件放在此目录中
  projectId: string;
  taskId: string;
}

export interface IServerProjectMaterialAcceptanceReviewManagerDirectForm {
  projectMaterialAcceptanceReviewMode: IServerProjectMaterialAcceptanceReviewMode;
  projectMaterialAcceptanceReviewUser: IServerProjectMaterialAcceptanceReviewUser;
  projectId: string;
  taskId: string;
  reviewTempDir: string; //review_temp_dir,临时审核附件目录，项目审核附件放在此目录中
}

export interface IServerProjectMaterialAcceptanceReviewManagerSummaryForm {
  projectMaterialAcceptanceReviewUser: IServerProjectMaterialAcceptanceReviewUser;
  projectId: string;
  taskId: string;
  reviewTempDir: string; //review_temp_dir,临时审核附件目录，项目审核附件放在此目录中
  employeeReviewFileIds: string[];
}
