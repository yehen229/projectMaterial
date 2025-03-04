package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LogBusiness{
	private String id;//id,主键
	private String businessName;//business_name
	private String note;//note
	private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getBusinessName(){
		return businessName;
	}
	public void setBusinessName(String businessName){
		this.businessName=businessName;
	}
	public String getNote(){
		return note;
	}
	public void setNote(String note){
		this.note=note;
	}
	public Date getDeletedAt(){
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt){
		this.deletedAt=deletedAt;
	}
}