package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.materialreview.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.ProjectReviewMode;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.ProjectReviewModeView;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.materialreview.IProjectReviewModeRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.materialreview.IProjectReviewModeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;

@Service
public class ProjectReviewModeServiceImpl implements IProjectReviewModeService {
	private static final Logger log =
			LoggerFactory.getLogger(ProjectReviewModeServiceImpl.class);


	private final IProjectReviewModeRepository projectReviewModeRepository;

	public ProjectReviewModeServiceImpl(IProjectReviewModeRepository projectReviewModeRepository){
		this.projectReviewModeRepository=projectReviewModeRepository;
	}

	/**
	 * 增加
	 */
	@Override
	public String add(ProjectReviewMode projectReviewMode){
		return projectReviewModeRepository.add(projectReviewMode);
	}

	/**
	 * 删除
	 */
	@Override
	public int delete(ProjectReviewMode projectReviewMode){
		return projectReviewModeRepository.delete(projectReviewMode);
	}

	/**
	 * 根据id删除记录
	 * @param id
	 */
	@Override
	public int deleteById(String id){
		return projectReviewModeRepository.deleteById(id);
	}

	/**
	 * 根据projectId删除记录
	 * @param projectId
	 */
	@Override
	public int deleteByProjectId(String projectId){
		return projectReviewModeRepository.deleteByProjectId(projectId);
	}

	/**
	 * 根据userId删除记录
	 * @param userId
	 */
	@Override
	public int deleteByUserId(String userId){
		return projectReviewModeRepository.deleteByUserId(userId);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(ProjectReviewMode projectReviewMode){
		return projectReviewModeRepository.update(projectReviewMode);
	}

	/**
	 * 得到数量
	 */
	@Override
	public int getCount(){
		return projectReviewModeRepository.getCount();
	}

	/**
	 * 根据projectId得到数量
	 * @param projectId
	 */
	@Override
	public int getCountByProjectId(String projectId){
		return projectReviewModeRepository.getCountByProjectId(projectId);
	}

	/**
	 * 根据userId得到数量
	 * @param userId
	 */
	@Override
	public int getCountByUserId(String userId){
		return projectReviewModeRepository.getCountByUserId(userId);
	}

	/**
	 * 根据id得到ProjectReviewMode
	 * @param id
	 */
	@Override
	public ProjectReviewMode getById(String id){
		return projectReviewModeRepository.getById(id);
	}

	/**
	 * 根据projectId得到ProjectReviewMode
	 * @param projectId
	 */
	@Override
	public List<ProjectReviewMode> getByProjectId(String projectId){
		return projectReviewModeRepository.getByProjectId(projectId);
	}

	/**
	 * 根据userId得到ProjectReviewMode
	 * @param userId
	 */
	@Override
	public List<ProjectReviewMode> getByUserId(String userId){
		return projectReviewModeRepository.getByUserId(userId);
	}

	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectReviewMode> getPage(int pageNo, int pageSize){
		return projectReviewModeRepository.getPage(pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param projectId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectReviewMode> getPageByProjectId(String projectId,int pageNo, int pageSize){
		return projectReviewModeRepository.getPageByProjectId(projectId,pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param userId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectReviewMode> getPageByUserId(String userId,int pageNo, int pageSize){
		return projectReviewModeRepository.getPageByUserId(userId,pageNo,pageSize);
	}

	/**
	 * 根据主键获得视图对象
	 * @param id	主键 
	 */
	private ProjectReviewModeView getProjectReviewModeViewByProjectReviewModeId(String id){
		ProjectReviewMode projectReviewMode = getById(id);
		if(projectReviewMode ==null)return null;
		ProjectReviewModeView projectReviewModeView=new ProjectReviewModeView();
		return null;
	}

	/**
	 * 将页面转换为视图页面
	 * @param projectReviewModePage	页面对象 
	 */
	private Page<ProjectReviewModeView> convertProjectReviewModePage2PageView(Page<ProjectReviewMode> projectReviewModePage,int pageNo, int pageSize){
		if(projectReviewModePage == null)return null;
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectReviewModeView> list =new ArrayList<>();
		for(ProjectReviewMode projectReviewMode:projectReviewModePage.getResult()){
			ProjectReviewModeView projectReviewModeView=getProjectReviewModeViewByProjectReviewModeId(projectReviewMode.getId());
			if(projectReviewModeView!=null)list.add(projectReviewModeView);
		}
		return new Page<>(startIndex, projectReviewModePage.getTotalCount(), pageSize, list);
	}

	/**
	 * 获得指定页面视图数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectReviewModeView> getPageView(int pageNo, int pageSize){
		Page<ProjectReviewMode> projectReviewModePage = getPage(pageNo,pageSize);
		return convertProjectReviewModePage2PageView(projectReviewModePage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param projectId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectReviewModeView> getPageViewByProjectId(String projectId,int pageNo, int pageSize){
		Page<ProjectReviewMode> projectReviewModePage = getPageByProjectId(projectId,pageNo,pageSize);
		return convertProjectReviewModePage2PageView(projectReviewModePage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param userId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectReviewModeView> getPageViewByUserId(String userId,int pageNo, int pageSize){
		Page<ProjectReviewMode> projectReviewModePage = getPageByUserId(userId,pageNo,pageSize);
		return convertProjectReviewModePage2PageView(projectReviewModePage,pageNo, pageSize);
	}

}