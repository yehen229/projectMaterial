package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview;

import org.springframework.stereotype.Component;

@Component
public class ProjectReviewUserFileView{
	private ProjectReviewUserFile projectReviewUserFile;//t_project_review_user_file
	private ProjectReviewUser projectReviewUser;//外键：t_project_review_user_id,关联表为：t_project_review_user表,


	public ProjectReviewUserFile getProjectReviewUserFile(){
		return projectReviewUserFile;
	}
	public void setProjectReviewUserFile(ProjectReviewUserFile projectReviewUserFile){
		this.projectReviewUserFile=projectReviewUserFile;
	}
	public ProjectReviewUser getProjectReviewUser(){
		return projectReviewUser;
	}
	public void setProjectReviewUser(ProjectReviewUser projectReviewUser){
		this.projectReviewUser=projectReviewUser;
	}
}