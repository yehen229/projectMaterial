package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Log{
	//属性
	private String id;  // 日志id
	private String t_user_id;   // 用户id
	private String t_project_id;    //项目id
	private String step_description;    //操作的内容
	private int op_type;    //操作的类型（增删查改）

	private Date op_datetime; //操作的时间


	public Log() {
	}

	public Log(String t_user_id, String t_project_id, String step_description, int op_type, Date op_datetime) {
		this.t_user_id = t_user_id;
		this.t_project_id = t_project_id;
		this.step_description = step_description;
		this.op_type = op_type;
		this.op_datetime = op_datetime;
	}

	/**
	 * 获取
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取
	 * @return t_user_id
	 */
	public String getT_user_id() {
		return t_user_id;
	}

	/**
	 * 设置
	 * @param t_user_id
	 */
	public void setT_user_id(String t_user_id) {
		this.t_user_id = t_user_id;
	}

	/**
	 * 获取
	 * @return t_project_id
	 */
	public String getT_project_id() {
		return t_project_id;
	}

	/**
	 * 设置
	 * @param t_project_id
	 */
	public void setT_project_id(String t_project_id) {
		this.t_project_id = t_project_id;
	}

	/**
	 * 获取
	 * @return step_description
	 */
	public String getStep_description() {
		return step_description;
	}

	/**
	 * 设置
	 * @param step_description
	 */
	public void setStep_description(String step_description) {
		this.step_description = step_description;
	}

	/**
	 * 获取
	 * @return op_type
	 */
	public int getOp_type() {
		return op_type;
	}

	/**
	 * 设置
	 * @param op_type
	 */
	public void setOp_type(int op_type) {
		this.op_type = op_type;
	}

	/**
	 * 获取
	 * @return op_datetime
	 */
	public Date getOp_datetime() {
		return op_datetime;
	}

	/**
	 * 设置
	 * @param op_datetime
	 */
	public void setOp_datetime(Date op_datetime) {
		this.op_datetime = op_datetime;
	}

	public String toString() {
		return "Log{id = " + id + ", t_user_id = " + t_user_id + ", t_project_id = " + t_project_id + ", step_description = " + step_description + ", op_type = " + op_type + ", op_datetime = " + op_datetime + "}";
	}
}