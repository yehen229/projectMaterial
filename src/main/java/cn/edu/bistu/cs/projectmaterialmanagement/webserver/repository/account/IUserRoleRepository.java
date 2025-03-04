package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.account;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.UserRole;

import java.util.List;

/** UserRole Service Interface 
*/
public interface IUserRoleRepository{

	String add(UserRole userRole);
	int delete(UserRole userRole);
	int update(UserRole userRole);
	int deleteById(String id);
	int deleteByUserId(String userId);
	int deleteByRoleId(String roleId);

	int getCount();
	int getCountByUserId(String userId);
	int getCountByRoleId(String roleId);
	UserRole getById(String id);
	List<UserRole> getByUserId(String userId);
	List<UserRole> getByRoleId(String roleId);
	Page<UserRole> getPage(int pageNo, int pageSize);
	Page<UserRole> getPageByUserId(String userId,int pageNo, int pageSize);
	Page<UserRole> getPageByRoleId(String roleId,int pageNo, int pageSize);

}