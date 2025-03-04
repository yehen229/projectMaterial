package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.LogBussinessOp;

import java.util.List;

/** LogBussinessOp Service Interface 
*/
public interface ILogBussinessOpRepository{

	String add(LogBussinessOp logBussinessOp);
	int delete(LogBussinessOp logBussinessOp);
	int update(LogBussinessOp logBussinessOp);
	int deleteById(String id);
	int deleteByLogId(String logId);
	int deleteByLogBusinessId(String logBusinessId);

	int getCount();
	int getCountByLogId(String logId);
	int getCountByLogBusinessId(String logBusinessId);
	LogBussinessOp getById(String id);
	List<LogBussinessOp> getByLogId(String logId);
	List<LogBussinessOp> getByLogBusinessId(String logBusinessId);
	Page<LogBussinessOp> getPage(int pageNo, int pageSize);
	Page<LogBussinessOp> getPageByLogId(String logId,int pageNo, int pageSize);
	Page<LogBussinessOp> getPageByLogBusinessId(String logBusinessId,int pageNo, int pageSize);

}