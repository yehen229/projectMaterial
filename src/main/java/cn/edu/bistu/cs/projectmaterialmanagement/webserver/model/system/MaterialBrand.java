package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MaterialBrand{
	private String id;//id,主键
	private String materialId;//t_material_id,外键,	t_material_id<-表t_material.id
	private String brandId;//t_brand_id,外键,	t_brand_id<-表t_brand.id
	private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getMaterialId(){
		return materialId;
	}
	public void setMaterialId(String materialId){
		this.materialId=materialId;
	}
	public String getBrandId(){
		return brandId;
	}
	public void setBrandId(String brandId){
		this.brandId=brandId;
	}
	public Date getDeletedAt(){
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt){
		this.deletedAt=deletedAt;
	}
}