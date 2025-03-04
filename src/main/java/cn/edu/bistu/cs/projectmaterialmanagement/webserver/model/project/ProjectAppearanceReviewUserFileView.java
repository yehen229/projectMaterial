package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

@Component
public class ProjectAppearanceReviewUserFileView{
	private ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile;//t_project_appearance_review_user_file
	private ProjectAppearanceReviewUser projectAppearanceReviewUser;//外键：t_project_appearance_review_user_id,关联表为：t_project_appearance_review_user表,


	public ProjectAppearanceReviewUserFile getProjectAppearanceReviewUserFile(){
		return projectAppearanceReviewUserFile;
	}
	public void setProjectAppearanceReviewUserFile(ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile){
		this.projectAppearanceReviewUserFile=projectAppearanceReviewUserFile;
	}
	public ProjectAppearanceReviewUser getProjectAppearanceReviewUser(){
		return projectAppearanceReviewUser;
	}
	public void setProjectAppearanceReviewUser(ProjectAppearanceReviewUser projectAppearanceReviewUser){
		this.projectAppearanceReviewUser=projectAppearanceReviewUser;
	}
}