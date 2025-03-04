package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.Log;

import java.util.List;

/** Log Service Interface 
*/
public interface ILogRepository{

	String add(Log log);
	int delete(Log log);
	int update(Log log);
	int deleteById(String id);
	int deleteByUserId(String userId);

	int getCount();
	int getCountByUserId(String userId);
	Log getById(String id);
	List<Log> getByUserId(String userId);
	Page<Log> getPage(int pageNo, int pageSize);
	Page<Log> getPageByUserId(String userId,int pageNo, int pageSize);

}