package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log;

import org.springframework.stereotype.Component;

@Component
public class LogBussinessOpView{
	private LogBussinessOp logBussinessOp;//t_log_bussiness_op
	private Log log;//外键：t_log_id,关联表为：t_log表,
	private LogBusiness logBusiness;//外键：t_log_business_id,关联表为：t_log_business表,


	public LogBussinessOp getLogBussinessOp(){
		return logBussinessOp;
	}
	public void setLogBussinessOp(LogBussinessOp logBussinessOp){
		this.logBussinessOp=logBussinessOp;
	}
	public Log getLog(){
		return log;
	}
	public void setLog(Log log){
		this.log=log;
	}
	public LogBusiness getLogBusiness(){
		return logBusiness;
	}
	public void setLogBusiness(LogBusiness logBusiness){
		this.logBusiness=logBusiness;
	}
}