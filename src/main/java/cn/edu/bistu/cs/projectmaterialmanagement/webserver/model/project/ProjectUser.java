package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectUser{
	private String id;//id,主键
	private String userId;//t_user_id,外键,	t_user_id<-表t_user.id,用户用户
	private String projectId;//t_project_id,外键,	t_project_id<-表t_project.id
	private String roleId;//t_role_id,外键,	t_role_id<-表t_role.id
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
	public String getProjectId(){
		return projectId;
	}
	public void setProjectId(String projectId){
		this.projectId=projectId;
	}
	public String getRoleId(){
		return roleId;
	}
	public void setRoleId(String roleId){
		this.roleId=roleId;
	}
	public Date getDeletedAt(){
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt){
		this.deletedAt=deletedAt;
	}
}