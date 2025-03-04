package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.LogContent;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.LogContentView;

import java.util.List;

public interface ILogContentService {
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
	Page<LogContentView> getPageView(int pageNo, int pageSize);
	Page<LogContentView> getPageViewByLogId(String logId,int pageNo, int pageSize);

}