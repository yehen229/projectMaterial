package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import org.springframework.stereotype.Component;

@Component
public class LogView{
	private Log log;//t_log
	private User user;//外键：t_user_id,关联表为：t_user表,


	public Log getLog(){
		return log;
	}
	public void setLog(Log log){
		this.log=log;
	}
	public User getUser(){
		return user;
	}
	public void setUser(User user){
		this.user=user;
	}
}