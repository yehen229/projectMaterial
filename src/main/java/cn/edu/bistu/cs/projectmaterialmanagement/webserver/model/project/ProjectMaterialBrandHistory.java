package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectMaterialBrandHistory{
	private String id;//id,主键
	private String projectMaterialHistoryId;//t_project_material_history_id,外键,	t_project_material_history_id<-表t_project_material_history.id
	private String projectMaterialBrandPrivateId;//t_project_material_brand_private_id,外键,	t_project_material_brand_private_id<-表t_project_material_brand_private.id,私有品牌私有品牌
	private String projectMaterialBrandPublicId;//t_project_material_brand_public_id,外键,	t_project_material_brand_public_id<-表t_project_material_brand_public.id,公共品牌公共品牌
	private Date deletedAt;//deleted_at


	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getProjectMaterialHistoryId(){
		return projectMaterialHistoryId;
	}
	public void setProjectMaterialHistoryId(String projectMaterialHistoryId){
		this.projectMaterialHistoryId=projectMaterialHistoryId;
	}
	public String getProjectMaterialBrandPrivateId(){
		return projectMaterialBrandPrivateId;
	}
	public void setProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId){
		this.projectMaterialBrandPrivateId=projectMaterialBrandPrivateId;
	}
	public String getProjectMaterialBrandPublicId(){
		return projectMaterialBrandPublicId;
	}
	public void setProjectMaterialBrandPublicId(String projectMaterialBrandPublicId){
		this.projectMaterialBrandPublicId=projectMaterialBrandPublicId;
	}
	public Date getDeletedAt(){
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt){
		this.deletedAt=deletedAt;
	}
}