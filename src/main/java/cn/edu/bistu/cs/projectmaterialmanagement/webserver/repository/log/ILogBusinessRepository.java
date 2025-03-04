package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.LogBusiness;

/** LogBusiness Service Interface 
*/
public interface ILogBusinessRepository{

	String add(LogBusiness logBusiness);
	int delete(LogBusiness logBusiness);
	int update(LogBusiness logBusiness);
	int deleteById(String id);

	int getCount();
	LogBusiness getById(String id);
	Page<LogBusiness> getPage(int pageNo, int pageSize);

}