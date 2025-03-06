package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.Log;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.ProjectLogView;

import java.util.List;

public interface ILogService {

	int SYS_LOG_OP_TYPE_LOGIN = 1;//登录
	int SYS_LOG_OP_TYPE_LOGOUT = 2;//登出

	String add(Log log);
	//	int delete(Log log);
//	int deleteById(String id);
//	int deleteByUserId(String userId);
	int getCount();
	int getCountByUserId(String userId);
	int getCountByProjectId(String projectId);

	Log getById(String id);
	List<Log> getByUserId(String userId);
	List<Log> getByProjectId(String projectId);
	Page<ProjectLogView> getPage(int pageNo, int pageSize);
	Page<Log> getPageByUserId(String userId, int pageNo, int pageSize);
	Page<Log> getPageByProjectId(String projectId, int pageNo, int pageSize);
}