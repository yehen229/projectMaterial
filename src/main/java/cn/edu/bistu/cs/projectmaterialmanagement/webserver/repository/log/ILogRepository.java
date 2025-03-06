package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.Log;

import java.util.List;

/** Log Service Interface 
 */
public interface ILogRepository{

	String add(Log log);
	int getCount();
	int getCountByUserId(String userId);
	int getCountByProjectId(String projectId);
	Log getById(String id);
	List<Log> getByUserId(String userId);
	List<Log> getByProjectId(String projectId);
	Page<Log> getPage(int pageNo, int pageSize);
	List<Log> getAllLog();
	Page<Log> getPageByUserId(String userId,int pageNo, int pageSize);
	Page<Log> getPageByProjectId(String projectId, int pageNo, int pageSize);

}