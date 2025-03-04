package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LogBussinessOp{
	private String id;//id,主键
	private String logId;//t_log_id,外键,	t_log_id<-表t_log.id
	private String logBusinessId;//t_log_business_id,外键,	t_log_business_id<-表t_log_business.id
	private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getLogId(){
		return logId;
	}
	public void setLogId(String logId){
		this.logId=logId;
	}
	public String getLogBusinessId(){
		return logBusinessId;
	}
	public void setLogBusinessId(String logBusinessId){
		this.logBusinessId=logBusinessId;
	}
	public Date getDeletedAt(){
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt){
		this.deletedAt=deletedAt;
	}
}