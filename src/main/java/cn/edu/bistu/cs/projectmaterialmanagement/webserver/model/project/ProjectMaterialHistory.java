package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectMaterialHistory{
	private String id;//id,主键
	private String materialId;//t_material_id,外键,	t_material_id<-表t_material.id,材料材料
	private String companyId;//t_company_id,外键,	t_company_id<-表t_company.id,提交的公司提交的公司
	private String projectId;//t_project_id,外键,	t_project_id<-表t_project.id,项目项目
	private int reviewed;//reviewed,审查结果审查结果
	private String projectMaterialId;//t_project_material_id,外键,	t_project_material_id<-表t_project_material.id
	private Date deletedAt;//deleted_at


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
	public String getCompanyId(){
		return companyId;
	}
	public void setCompanyId(String companyId){
		this.companyId=companyId;
	}
	public String getProjectId(){
		return projectId;
	}
	public void setProjectId(String projectId){
		this.projectId=projectId;
	}
	public int getReviewed(){
		return reviewed;
	}
	public void setReviewed(int reviewed){
		this.reviewed=reviewed;
	}
	public String getProjectMaterialId(){
		return projectMaterialId;
	}
	public void setProjectMaterialId(String projectMaterialId){
		this.projectMaterialId=projectMaterialId;
	}
	public Date getDeletedAt(){
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt){
		this.deletedAt=deletedAt;
	}
}