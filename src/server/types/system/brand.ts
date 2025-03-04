import { IServerMaterialClassifySectionView } from "./material";
export interface IServerBrand {
  id: string; //id,主键
  name: string; //name
  materialClassifySectionId: string; //t_material_classify_section_id
  position: string; //position,定位：合资、国产等定位：合资、国产等
  deletedAt: Date; //deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间
}

export interface IServerBrandView {
  brand: IServerBrand;
  materialClassifySectionView: IServerMaterialClassifySectionView;
}

export interface IServerBrandPublic {
  id: string; //id,主键
  brandId: string; //t_brand_id,外键,	t_brand_id<-表t_brand.id
  deletedAt: Date;
}

export interface IServerBrandPublicView {
  brandPublic: IServerBrandPublic; //t_brand_public
  brandView: IServerBrandView;
}
