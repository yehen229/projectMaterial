package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.UserRole;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.UserRoleView;

import java.util.List;

public interface IUserRoleService {
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
	Page<UserRoleView> getPageView(int pageNo, int pageSize);
	Page<UserRoleView> getPageViewByUserId(String userId,int pageNo, int pageSize);
	Page<UserRoleView> getPageViewByRoleId(String roleId,int pageNo, int pageSize);

}