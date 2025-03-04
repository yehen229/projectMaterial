package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.LogContent;

import java.util.List;

/** LogContent Service Interface 
*/
public interface ILogContentRepository{

	String add(LogContent logContent);
	int delete(LogContent logContent);
	int update(LogContent logContent);
	int deleteById(String id);
	int deleteByLogId(String logId);

	int getCount();
	int getCountByLogId(String logId);
	LogContent getById(String id);
	List<LogContent> getByLogId(String logId);
	Page<LogContent> getPage(int pageNo, int pageSize);
	Page<LogContent> getPageByLogId(String logId,int pageNo, int pageSize);

}