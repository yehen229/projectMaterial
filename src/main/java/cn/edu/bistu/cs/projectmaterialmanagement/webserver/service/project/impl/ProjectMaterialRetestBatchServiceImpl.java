package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;



import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetestBatch;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetestBatchView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialRetestBatchRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialRetestBatchService;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;

@Service
public class ProjectMaterialRetestBatchServiceImpl implements IProjectMaterialRetestBatchService {
	private final IProjectMaterialRetestBatchRepository projectMaterialRetestBatchRepository;

	public ProjectMaterialRetestBatchServiceImpl(IProjectMaterialRetestBatchRepository projectMaterialRetestBatchRepository){
		this.projectMaterialRetestBatchRepository=projectMaterialRetestBatchRepository;
	}

	/**
	 * 增加
	 */
	@Override
	public String add(ProjectMaterialRetestBatch projectMaterialRetestBatch){
		return projectMaterialRetestBatchRepository.add(projectMaterialRetestBatch);
	}

	/**
	 * 删除
	 */
	@Override
	public int delete(ProjectMaterialRetestBatch projectMaterialRetestBatch){
		return projectMaterialRetestBatchRepository.delete(projectMaterialRetestBatch);
	}

	/**
	 * 根据id删除记录
	 * @param id
	 */
	@Override
	public int deleteById(String id){
		return projectMaterialRetestBatchRepository.deleteById(id);
	}

	/**
	 * 根据userId删除记录
	 * @param userId
	 */
	@Override
	public int deleteByUserId(String userId){
		return projectMaterialRetestBatchRepository.deleteByUserId(userId);
	}

	/**
	 * 根据projectId删除记录
	 * @param projectId
	 */
	@Override
	public int deleteByProjectId(String projectId){
		return projectMaterialRetestBatchRepository.deleteByProjectId(projectId);
	}

	/**
	 * 根据buyMaterialBatchId删除记录
	 * @param buyMaterialBatchId
	 */
	@Override
	public int deleteByBuyMaterialBatchId(String buyMaterialBatchId){
		return projectMaterialRetestBatchRepository.deleteByBuyMaterialBatchId(buyMaterialBatchId);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(ProjectMaterialRetestBatch projectMaterialRetestBatch){
		return projectMaterialRetestBatchRepository.update(projectMaterialRetestBatch);
	}

	/**
	 * 得到数量
	 */
	@Override
	public int getCount(){
		return projectMaterialRetestBatchRepository.getCount();
	}

	/**
	 * 根据userId得到数量
	 * @param userId
	 */
	@Override
	public int getCountByUserId(String userId){
		return projectMaterialRetestBatchRepository.getCountByUserId(userId);
	}

	/**
	 * 根据projectId得到数量
	 * @param projectId
	 */
	@Override
	public int getCountByProjectId(String projectId){
		return projectMaterialRetestBatchRepository.getCountByProjectId(projectId);
	}

	/**
	 * 根据buyMaterialBatchId得到数量
	 * @param buyMaterialBatchId
	 */
	@Override
	public int getCountByBuyMaterialBatchId(String buyMaterialBatchId){
		return projectMaterialRetestBatchRepository.getCountByBuyMaterialBatchId(buyMaterialBatchId);
	}

	/**
	 * 根据id得到ProjectMaterialRetestBatch
	 * @param id
	 */
	@Override
	public ProjectMaterialRetestBatch getById(String id){
		return projectMaterialRetestBatchRepository.getById(id);
	}

	/**
	 * 根据userId得到ProjectMaterialRetestBatch
	 * @param userId
	 */
	@Override
	public List<ProjectMaterialRetestBatch> getByUserId(String userId){
		return projectMaterialRetestBatchRepository.getByUserId(userId);
	}

	/**
	 * 根据projectId得到ProjectMaterialRetestBatch
	 * @param projectId
	 */
	@Override
	public List<ProjectMaterialRetestBatch> getByProjectId(String projectId){
		return projectMaterialRetestBatchRepository.getByProjectId(projectId);
	}

	/**
	 * 根据buyMaterialBatchId得到ProjectMaterialRetestBatch
	 * @param buyMaterialBatchId
	 */
	@Override
	public List<ProjectMaterialRetestBatch> getByBuyMaterialBatchId(String buyMaterialBatchId){
		return projectMaterialRetestBatchRepository.getByBuyMaterialBatchId(buyMaterialBatchId);
	}

	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialRetestBatch> getPage(int pageNo, int pageSize){
		return projectMaterialRetestBatchRepository.getPage(pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param userId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialRetestBatch> getPageByUserId(String userId,int pageNo, int pageSize){
		return projectMaterialRetestBatchRepository.getPageByUserId(userId,pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param projectId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialRetestBatch> getPageByProjectId(String projectId,int pageNo, int pageSize){
		return projectMaterialRetestBatchRepository.getPageByProjectId(projectId,pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param buyMaterialBatchId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialRetestBatch> getPageByBuyMaterialBatchId(String buyMaterialBatchId,int pageNo, int pageSize){
		return projectMaterialRetestBatchRepository.getPageByBuyMaterialBatchId(buyMaterialBatchId,pageNo,pageSize);
	}

	/**
	 * 根据主键获得视图对象
	 * @param id	主键 
	 */
	private ProjectMaterialRetestBatchView getProjectMaterialRetestBatchViewByProjectMaterialRetestBatchId(String id){
		ProjectMaterialRetestBatch projectMaterialRetestBatch = getById(id);
		if(projectMaterialRetestBatch ==null)return null;
		ProjectMaterialRetestBatchView projectMaterialRetestBatchView=new ProjectMaterialRetestBatchView();
		return null;
	}

	/**
	 * 将页面转换为视图页面
	 * @param projectMaterialRetestBatchPage	页面对象 
	 */
	private Page<ProjectMaterialRetestBatchView> convertProjectMaterialRetestBatchPage2PageView(Page<ProjectMaterialRetestBatch> projectMaterialRetestBatchPage,int pageNo, int pageSize){
		if(projectMaterialRetestBatchPage == null)return null;
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectMaterialRetestBatchView> list =new ArrayList<>();
		for(ProjectMaterialRetestBatch projectMaterialRetestBatch:projectMaterialRetestBatchPage.getResult()){
			ProjectMaterialRetestBatchView projectMaterialRetestBatchView=getProjectMaterialRetestBatchViewByProjectMaterialRetestBatchId(projectMaterialRetestBatch.getId());
			if(projectMaterialRetestBatchView!=null)list.add(projectMaterialRetestBatchView);
		}
		return new Page<>(startIndex, projectMaterialRetestBatchPage.getTotalCount(), pageSize, list);
	}

	/**
	 * 获得指定页面视图数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialRetestBatchView> getPageView(int pageNo, int pageSize){
		Page<ProjectMaterialRetestBatch> projectMaterialRetestBatchPage = getPage(pageNo,pageSize);
		return convertProjectMaterialRetestBatchPage2PageView(projectMaterialRetestBatchPage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param userId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialRetestBatchView> getPageViewByUserId(String userId,int pageNo, int pageSize){
		Page<ProjectMaterialRetestBatch> projectMaterialRetestBatchPage = getPageByUserId(userId,pageNo,pageSize);
		return convertProjectMaterialRetestBatchPage2PageView(projectMaterialRetestBatchPage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param projectId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialRetestBatchView> getPageViewByProjectId(String projectId,int pageNo, int pageSize){
		Page<ProjectMaterialRetestBatch> projectMaterialRetestBatchPage = getPageByProjectId(projectId,pageNo,pageSize);
		return convertProjectMaterialRetestBatchPage2PageView(projectMaterialRetestBatchPage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param buyMaterialBatchId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialRetestBatchView> getPageViewByBuyMaterialBatchId(String buyMaterialBatchId,int pageNo, int pageSize){
		Page<ProjectMaterialRetestBatch> projectMaterialRetestBatchPage = getPageByBuyMaterialBatchId(buyMaterialBatchId,pageNo,pageSize);
		return convertProjectMaterialRetestBatchPage2PageView(projectMaterialRetestBatchPage,pageNo, pageSize);
	}

}