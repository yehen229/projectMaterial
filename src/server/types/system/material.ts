import { IServerBrand } from "./brand";
export interface IServerMaterial {
  id: string; //id,主键
  materialClassifySectionId: string; //t_material_classify_section_id
  name: string; //name,名称名称
  itemMark: string; //item_mark,编号编号
  location: string; //location,位置位置
  technology: string; //technology,技术要求技术要求
  material: string; //material,材料材质材料材质
  color: string; //color,颜色颜色
  dimension: string; //dimension,规格规格
  fireRating: string; //fire_rating,防火等级防火等级
  installation: string; //installation,施工要求施工要求
  materialProjectBindType: number; //类型：0：原始材料；1：和项目绑定的、基于原始材料修改的
  deletedAt: Date;
}

export interface IServerMaterialBrand {
  id: string; //id,主键
  materialId: string; //t_material_id,外键,	t_material_id<-表t_material.id
  brandId: string; //t_brand_id,外键,	t_brand_id<-表t_brand.id
  deletedAt: Date;
}

export interface IServerMaterialBrandView {
  materialBrand: IServerMaterialBrand; //t_material_brand
  material: IServerMaterial; //外键：t_material_id,关联表为：t_material表,
  brand: IServerBrand; //外键：t_brand_id,关联表为：t_brand表,
}

export interface IServerMaterialClassifyDivision {
  id: string; //id,主键
  name: string; //name
  note: string; //note
  deletedAt: Date; //deleted_at
}

export interface IServerMaterialClassifyDivisionTreeItem {
  materialClassifyDivision: IServerMaterialClassifyDivision;
  children: IServerMaterialClassifyGroupTreeItem[];
}

export interface IServerMaterialClassifyGroup {
  id: string; //id,主键
  name: string; //name
  note: string; //note
  materialClassifyDivisionId: string; //t_material_classify_division_id,外键,	t_material_classify_division_id<-表t_material_classify_division.id
  deletedAt: Date; //deleted_at
}

export interface IServerMaterialClassifyGroupTreeItem {
  materialClassifyGroupView: IServerMaterialClassifyGroupView;
  children: IServerMaterialClassifySectionView[];
}

export interface IServerMaterialClassifyGroupView {
  materialClassifyGroup: IServerMaterialClassifyGroup; //t_material_classify_group
  materialClassifyDivision: IServerMaterialClassifyDivision; //外键：t_material_classify_division_id,关联表为：t_material_classify_division表,
}

export interface IServerMaterialClassifySection {
  id: string; //id,主键
  name: string; //name
  note: string; //note
  materialClassifyGroupId: string; //t_material_classify_group_id,外键,	t_material_classify_group_id<-表t_material_classify_group.id
  deletedAt: Date; //deleted_at
}

export interface IServerMaterialClassifySectionView {
  materialClassifySection: IServerMaterialClassifySection; //t_material_classify_section
  materialClassifyGroup: IServerMaterialClassifyGroup; //外键：t_material_classify_group_id,关联表为：t_material_classify_group表,
  materialClassifyDivision: IServerMaterialClassifyDivision; //外键：t_material_classify_division_id,关联表为：t_material_classify_division表,
}

export interface IServerMaterialClassifyTree {
  children: IServerMaterialClassifyDivisionTreeItem[];
}

// t_material_photo
export interface IServerMaterialPhoto {
  id: string; //id,主键
  materialId: string; //t_material_id,外键,	t_material_id<-表t_material.id
  filePath: string; //file_path,文件路径文件路径
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerMaterialPhotoView {
  materialPhoto: IServerMaterialPhoto; //t_material_photo
  material: IServerMaterial; //外键：t_material_id,关联表为：t_material表,
}

export interface IServerMaterialForm {
  material: IServerMaterial; //
  photoTempDir: string; //
  brandIds: string[]; //
  photoIds: string[];
}

export interface IServerMaterialView {
  material: IServerMaterial;
  materialClassifySectionView: IServerMaterialClassifySectionView;
  materialBrandViewList: IServerMaterialBrandView[];
  materialPhotoViewList: IServerMaterialPhotoView[];
}
