package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.UserRole;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.UserRoleView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserRoleService;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.account.IUserRoleRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;

@Service
public class UserRoleServiceImpl implements IUserRoleService {
	private static final Logger log =
			LoggerFactory.getLogger(UserRoleServiceImpl.class);

	private final IUserRoleRepository userRoleRepository;

	public UserRoleServiceImpl(IUserRoleRepository userRoleRepository){
		this.userRoleRepository=userRoleRepository;
	}

	/**
	 * 增加
	 */
	@Override
	public String add(UserRole userRole){
		return userRoleRepository.add(userRole);
	}

	/**
	 * 删除
	 */
	@Override
	public int delete(UserRole userRole){
		return userRoleRepository.delete(userRole);
	}

	/**
	 * 根据id删除记录
	 * @param id
	 */
	@Override
	public int deleteById(String id){
		return userRoleRepository.deleteById(id);
	}

	/**
	 * 根据userId删除记录
	 * @param userId
	 */
	@Override
	public int deleteByUserId(String userId){
		return userRoleRepository.deleteByUserId(userId);
	}

	/**
	 * 根据roleId删除记录
	 * @param roleId
	 */
	@Override
	public int deleteByRoleId(String roleId){
		return userRoleRepository.deleteByRoleId(roleId);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(UserRole userRole){
		return userRoleRepository.update(userRole);
	}

	/**
	 * 得到数量
	 */
	@Override
	public int getCount(){
		return userRoleRepository.getCount();
	}

	/**
	 * 根据userId得到数量
	 * @param userId
	 */
	@Override
	public int getCountByUserId(String userId){
		return userRoleRepository.getCountByUserId(userId);
	}

	/**
	 * 根据roleId得到数量
	 * @param roleId
	 */
	@Override
	public int getCountByRoleId(String roleId){
		return userRoleRepository.getCountByRoleId(roleId);
	}

	/**
	 * 根据id得到UserRole
	 * @param id
	 */
	@Override
	public UserRole getById(String id){
		return userRoleRepository.getById(id);
	}

	/**
	 * 根据userId得到UserRole
	 * @param userId
	 */
	@Override
	public List<UserRole> getByUserId(String userId){
		return userRoleRepository.getByUserId(userId);
	}

	/**
	 * 根据roleId得到UserRole
	 * @param roleId
	 */
	@Override
	public List<UserRole> getByRoleId(String roleId){
		return userRoleRepository.getByRoleId(roleId);
	}

	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<UserRole> getPage(int pageNo, int pageSize){
		return userRoleRepository.getPage(pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param userId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<UserRole> getPageByUserId(String userId,int pageNo, int pageSize){
		return userRoleRepository.getPageByUserId(userId,pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param roleId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<UserRole> getPageByRoleId(String roleId,int pageNo, int pageSize){
		return userRoleRepository.getPageByRoleId(roleId,pageNo,pageSize);
	}

	/**
	 * 根据主键获得视图对象
	 * @param id	主键 
	 */
	private UserRoleView getUserRoleViewByUserRoleId(String id){
		UserRole userRole = getById(id);
		if(userRole ==null)return null;
		UserRoleView userRoleView=new UserRoleView();
		return null;
	}

	/**
	 * 将页面转换为视图页面
	 * @param userRolePage	页面对象 
	 */
	private Page<UserRoleView> convertUserRolePage2PageView(Page<UserRole> userRolePage,int pageNo, int pageSize){
		if(userRolePage == null)return null;
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<UserRoleView> list =new ArrayList<>();
		for(UserRole userRole:userRolePage.getResult()){
			UserRoleView userRoleView=getUserRoleViewByUserRoleId(userRole.getId());
			if(userRoleView!=null)list.add(userRoleView);
		}
		return new Page<>(startIndex, userRolePage.getTotalCount(), pageSize, list);
	}

	/**
	 * 获得指定页面视图数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<UserRoleView> getPageView(int pageNo, int pageSize){
		Page<UserRole> userRolePage = getPage(pageNo,pageSize);
		return convertUserRolePage2PageView(userRolePage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param userId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<UserRoleView> getPageViewByUserId(String userId,int pageNo, int pageSize){
		Page<UserRole> userRolePage = getPageByUserId(userId,pageNo,pageSize);
		return convertUserRolePage2PageView(userRolePage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param roleId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<UserRoleView> getPageViewByRoleId(String roleId,int pageNo, int pageSize){
		Page<UserRole> userRolePage = getPageByRoleId(roleId,pageNo,pageSize);
		return convertUserRolePage2PageView(userRolePage,pageNo, pageSize);
	}

}