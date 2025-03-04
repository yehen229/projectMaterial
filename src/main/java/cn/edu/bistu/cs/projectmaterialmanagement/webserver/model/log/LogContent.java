package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LogContent{
	private String id;//id,主键
	private String logId;//t_log_id,外键,	t_log_id<-表t_log.id
	private String tableColumn;//table_column
	private String oldValue;//old_value
	private String newValue;//new_value
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
	public String getTableColumn(){
		return tableColumn;
	}
	public void setTableColumn(String tableColumn){
		this.tableColumn=tableColumn;
	}
	public String getOldValue(){
		return oldValue;
	}
	public void setOldValue(String oldValue){
		this.oldValue=oldValue;
	}
	public String getNewValue(){
		return newValue;
	}
	public void setNewValue(String newValue){
		this.newValue=newValue;
	}
	public Date getDeletedAt(){
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt){
		this.deletedAt=deletedAt;
	}
}