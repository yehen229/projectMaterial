package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetest;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetestView;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialRetestRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialRetestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;

@Service
public class ProjectMaterialRetestServiceImpl implements IProjectMaterialRetestService {

	private static final Logger log =
			LoggerFactory.getLogger(ProjectMaterialRetestServiceImpl.class);

	private final IProjectMaterialRetestRepository projectMaterialRetestRepository;

	public ProjectMaterialRetestServiceImpl(IProjectMaterialRetestRepository projectMaterialRetestRepository){
		this.projectMaterialRetestRepository=projectMaterialRetestRepository;
	}

	/**
	 * 增加
	 */
	@Override
	public String add(ProjectMaterialRetest projectMaterialRetest){
		return projectMaterialRetestRepository.add(projectMaterialRetest);
	}

	/**
	 * 删除
	 */
	@Override
	public int delete(ProjectMaterialRetest projectMaterialRetest){
		return projectMaterialRetestRepository.delete(projectMaterialRetest);
	}

	/**
	 * 根据id删除记录
	 * @param id
	 */
	@Override
	public int deleteById(String id){
		return projectMaterialRetestRepository.deleteById(id);
	}

	/**
	 * 根据buyMaterialId删除记录
	 * @param buyMaterialId
	 */
	@Override
	public int deleteByBuyMaterialId(String buyMaterialId){
		return projectMaterialRetestRepository.deleteByBuyMaterialId(buyMaterialId);
	}

	/**
	 * 根据userId删除记录
	 * @param userId
	 */
	@Override
	public int deleteByUserId(String userId){
		return projectMaterialRetestRepository.deleteByUserId(userId);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(ProjectMaterialRetest projectMaterialRetest){
		return projectMaterialRetestRepository.update(projectMaterialRetest);
	}

	/**
	 * 得到数量
	 */
	@Override
	public int getCount(){
		return projectMaterialRetestRepository.getCount();
	}

	/**
	 * 根据buyMaterialId得到数量
	 * @param buyMaterialId
	 */
	@Override
	public int getCountByBuyMaterialId(String buyMaterialId){
		return projectMaterialRetestRepository.getCountByBuyMaterialId(buyMaterialId);
	}

	/**
	 * 根据userId得到数量
	 * @param userId
	 */
	@Override
	public int getCountByUserId(String userId){
		return projectMaterialRetestRepository.getCountByUserId(userId);
	}

	/**
	 * 根据id得到ProjectMaterialRetest
	 * @param id
	 */
	@Override
	public ProjectMaterialRetest getById(String id){
		return projectMaterialRetestRepository.getById(id);
	}

	/**
	 * 根据buyMaterialId得到ProjectMaterialRetest
	 * @param buyMaterialId
	 */
	@Override
	public List<ProjectMaterialRetest> getByBuyMaterialId(String buyMaterialId){
		return projectMaterialRetestRepository.getByBuyMaterialId(buyMaterialId);
	}

	/**
	 * 根据userId得到ProjectMaterialRetest
	 * @param userId
	 */
	@Override
	public List<ProjectMaterialRetest> getByUserId(String userId){
		return projectMaterialRetestRepository.getByUserId(userId);
	}
	@Override
	public List<ProjectMaterialRetest> getbyProjectMaterialRetestBatchId(String projectMaterialRetestBatchId){
		return projectMaterialRetestRepository.getByRetestBatchId(projectMaterialRetestBatchId);
	}

	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialRetest> getPage(int pageNo, int pageSize){
		return projectMaterialRetestRepository.getPage(pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param buyMaterialId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialRetest> getPageByBuyMaterialId(String buyMaterialId,int pageNo, int pageSize){
		return projectMaterialRetestRepository.getPageByBuyMaterialId(buyMaterialId,pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param userId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialRetest> getPageByUserId(String userId,int pageNo, int pageSize){
		return projectMaterialRetestRepository.getPageByUserId(userId,pageNo,pageSize);
	}

	/**
	 * 根据主键获得视图对象
	 * @param id	主键 
	 */
	private ProjectMaterialRetestView getProjectMaterialRetestViewByProjectMaterialRetestId(String id){
		ProjectMaterialRetest projectMaterialRetest = getById(id);
		if(projectMaterialRetest ==null)return null;
		ProjectMaterialRetestView projectMaterialRetestView=new ProjectMaterialRetestView();
		return null;
	}

	/**
	 * 将页面转换为视图页面
	 * @param projectMaterialRetestPage	页面对象 
	 */
	private Page<ProjectMaterialRetestView> convertProjectMaterialRetestPage2PageView(Page<ProjectMaterialRetest> projectMaterialRetestPage,int pageNo, int pageSize){
		if(projectMaterialRetestPage == null)return null;
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectMaterialRetestView> list =new ArrayList<>();
		for(ProjectMaterialRetest projectMaterialRetest:projectMaterialRetestPage.getResult()){
			ProjectMaterialRetestView projectMaterialRetestView=getProjectMaterialRetestViewByProjectMaterialRetestId(projectMaterialRetest.getId());
			if(projectMaterialRetestView!=null)list.add(projectMaterialRetestView);
		}
		return new Page<>(startIndex, projectMaterialRetestPage.getTotalCount(), pageSize, list);
	}

	/**
	 * 获得指定页面视图数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialRetestView> getPageView(int pageNo, int pageSize){
		Page<ProjectMaterialRetest> projectMaterialRetestPage = getPage(pageNo,pageSize);
		return convertProjectMaterialRetestPage2PageView(projectMaterialRetestPage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param buyMaterialId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialRetestView> getPageViewByBuyMaterialId(String buyMaterialId,int pageNo, int pageSize){
		Page<ProjectMaterialRetest> projectMaterialRetestPage = getPageByBuyMaterialId(buyMaterialId,pageNo,pageSize);
		return convertProjectMaterialRetestPage2PageView(projectMaterialRetestPage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param userId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialRetestView> getPageViewByUserId(String userId,int pageNo, int pageSize){
		Page<ProjectMaterialRetest> projectMaterialRetestPage = getPageByUserId(userId,pageNo,pageSize);
		return convertProjectMaterialRetestPage2PageView(projectMaterialRetestPage,pageNo, pageSize);
	}

}