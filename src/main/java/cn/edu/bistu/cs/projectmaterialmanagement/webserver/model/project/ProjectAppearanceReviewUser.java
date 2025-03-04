package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectAppearanceReviewUser{
	private String id;//id,主键
	private String userId;//t_user_id,外键,	t_user_id<-表t_user.id
	private String projectAppearanceReviewId;//t_project_appearance_review_id,外键,	t_project_appearance_review_id<-表t_project_appearance_review.id
	private int reviewResult;//review_result
	private String reviewContent;//review_content
	private Date reviewDatetime;//review_datetime
	private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId=userId;
	}
	public String getProjectAppearanceReviewId(){
		return projectAppearanceReviewId;
	}
	public void setProjectAppearanceReviewId(String projectAppearanceReviewId){
		this.projectAppearanceReviewId=projectAppearanceReviewId;
	}
	public int getReviewResult(){
		return reviewResult;
	}
	public void setReviewResult(int reviewResult){
		this.reviewResult=reviewResult;
	}
	public String getReviewContent(){
		return reviewContent;
	}
	public void setReviewContent(String reviewContent){
		this.reviewContent=reviewContent;
	}
	public Date getReviewDatetime(){
		return reviewDatetime;
	}
	public void setReviewDatetime(Date reviewDatetime){
		this.reviewDatetime=reviewDatetime;
	}
	public Date getDeletedAt(){
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt){
		this.deletedAt=deletedAt;
	}
}