package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CompanyUser{
	private String id;//id,主键
	private String companyId;//t_company_id,外键,	t_company_id<-表t_company.id
	private String userId;//t_user_id,外键,	t_user_id<-表t_user.id
	private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getCompanyId(){
		return companyId;
	}
	public void setCompanyId(String companyId){
		this.companyId=companyId;
	}
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId=userId;
	}
	public Date getDeletedAt(){
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt){
		this.deletedAt=deletedAt;
	}
}