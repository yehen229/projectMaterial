package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log;

import org.springframework.stereotype.Component;

@Component
public class LogContentView{
	private LogContent logContent;//t_log_content
	private Log log;//外键：t_log_id,关联表为：t_log表,


	public LogContent getLogContent(){
		return logContent;
	}
	public void setLogContent(LogContent logContent){
		this.logContent=logContent;
	}
	public Log getLog(){
		return log;
	}
	public void setLog(Log log){
		this.log=log;
	}
}