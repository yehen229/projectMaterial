package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Log{
	private String id;//id,主键
	private String userId;//t_user_id,外键,	t_user_id<-表t_user.id
	private int opType;//op_type,操作类型：增删改查操作类型：增删改查
	private String tableName;//table_name,表名表名
	private Date opDatetime;//op_datetime
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
	public int getOpType(){
		return opType;
	}
	public void setOpType(int opType){
		this.opType=opType;
	}
	public String getTableName(){
		return tableName;
	}
	public void setTableName(String tableName){
		this.tableName=tableName;
	}
	public Date getOpDatetime(){
		return opDatetime;
	}
	public void setOpDatetime(Date opDatetime){
		this.opDatetime=opDatetime;
	}
	public Date getDeletedAt(){
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt){
		this.deletedAt=deletedAt;
	}
}