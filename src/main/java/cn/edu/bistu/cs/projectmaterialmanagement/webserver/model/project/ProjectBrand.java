package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectBrand{
	private String id;//id,主键
	private String projectId;//t_project_id,外键,	t_project_id<-表t_project.id
	private String brandId;//t_brand_id,外键,	t_brand_id<-表t_brand.id
	private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getProjectId(){
		return projectId;
	}
	public void setProjectId(String projectId){
		this.projectId=projectId;
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