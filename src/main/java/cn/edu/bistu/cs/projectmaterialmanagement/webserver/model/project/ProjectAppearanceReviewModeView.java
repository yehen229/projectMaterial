package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import org.springframework.stereotype.Component;

@Component
public class ProjectAppearanceReviewModeView{
	private ProjectAppearanceReviewMode projectAppearanceReviewMode;//t_project_appearance_review_mode
	private ProjectMaterial projectMaterial;//外键：t_project_material_id,关联表为：t_project_material表,
	private User user;//外键：t_user_id,关联表为：t_user表,


	public ProjectAppearanceReviewMode getProjectAppearanceReviewMode(){
		return projectAppearanceReviewMode;
	}
	public void setProjectAppearanceReviewMode(ProjectAppearanceReviewMode projectAppearanceReviewMode){
		this.projectAppearanceReviewMode=projectAppearanceReviewMode;
	}
	public ProjectMaterial getProjectMaterial(){
		return projectMaterial;
	}
	public void setProjectMaterial(ProjectMaterial projectMaterial){
		this.projectMaterial=projectMaterial;
	}
	public User getUser(){
		return user;
	}
	public void setUser(User user){
		this.user=user;
	}
}