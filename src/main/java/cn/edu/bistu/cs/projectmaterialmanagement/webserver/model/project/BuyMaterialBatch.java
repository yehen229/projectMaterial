package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BuyMaterialBatch{
	private String id;//id,主键
	private String userId;//t_user_id,外键,	t_user_id<-表t_user.id,用户用户
	private String projectId;//t_project,外键,	t_project<-表t_project.id,项目项目
	private Date createDatetime;//create_datetime
	private Date deletedAt;//deleted_at


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

	public Date getCreateDatetime(){
		return createDatetime;
	}
	public void setCreateDatetime(Date createDatetime){
		this.createDatetime=createDatetime;
	}
	public Date getDeletedAt(){
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt){
		this.deletedAt=deletedAt;
	}

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}