package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;


import org.springframework.stereotype.Component;

@Component
public class ProjectMaterialBrandHistoryView{
	private ProjectMaterialBrandHistory projectMaterialBrandHistory;//t_project_material_brand_history
	private ProjectMaterialHistory projectMaterialHistory;//外键：t_project_material_history_id,关联表为：t_project_material_history表,
	private ProjectMaterialBrandPrivate projectMaterialBrandPrivate;//外键：t_project_material_brand_private_id,关联表为：t_project_material_brand_private表,私有品牌
	private ProjectMaterialBrandPublic projectMaterialBrandPublic;//外键：t_project_material_brand_public_id,关联表为：t_project_material_brand_public表,公共品牌


	public ProjectMaterialBrandHistory getProjectMaterialBrandHistory(){
		return projectMaterialBrandHistory;
	}
	public void setProjectMaterialBrandHistory(ProjectMaterialBrandHistory projectMaterialBrandHistory){
		this.projectMaterialBrandHistory=projectMaterialBrandHistory;
	}
	public ProjectMaterialHistory getProjectMaterialHistory(){
		return projectMaterialHistory;
	}
	public void setProjectMaterialHistory(ProjectMaterialHistory projectMaterialHistory){
		this.projectMaterialHistory=projectMaterialHistory;
	}
	public ProjectMaterialBrandPrivate getProjectMaterialBrandPrivate(){
		return projectMaterialBrandPrivate;
	}
	public void setProjectMaterialBrandPrivate(ProjectMaterialBrandPrivate projectMaterialBrandPrivate){
		this.projectMaterialBrandPrivate=projectMaterialBrandPrivate;
	}
	public ProjectMaterialBrandPublic getProjectMaterialBrandPublic(){
		return projectMaterialBrandPublic;
	}
	public void setProjectMaterialBrandPublic(ProjectMaterialBrandPublic projectMaterialBrandPublic){
		this.projectMaterialBrandPublic=projectMaterialBrandPublic;
	}
}