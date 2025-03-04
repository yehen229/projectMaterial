package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;



import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandHistory;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandHistoryView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialBrandHistoryRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialBrandHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;

@Service
public class ProjectMaterialBrandHistoryServiceImpl implements IProjectMaterialBrandHistoryService {
	private final IProjectMaterialBrandHistoryRepository projectMaterialBrandHistoryRepository;

	public ProjectMaterialBrandHistoryServiceImpl(IProjectMaterialBrandHistoryRepository projectMaterialBrandHistoryRepository){
		this.projectMaterialBrandHistoryRepository=projectMaterialBrandHistoryRepository;
	}

	/**
	 * 增加
	 */
	@Override
	public String add(ProjectMaterialBrandHistory projectMaterialBrandHistory){
		return projectMaterialBrandHistoryRepository.add(projectMaterialBrandHistory);
	}

	/**
	 * 删除
	 */
	@Override
	public int delete(ProjectMaterialBrandHistory projectMaterialBrandHistory){
		return projectMaterialBrandHistoryRepository.delete(projectMaterialBrandHistory);
	}

	/**
	 * 根据id删除记录
	 * @param id
	 */
	@Override
	public int deleteById(String id){
		return projectMaterialBrandHistoryRepository.deleteById(id);
	}

	/**
	 * 根据projectMaterialHistoryId删除记录
	 * @param projectMaterialHistoryId
	 */
	@Override
	public int deleteByProjectMaterialHistoryId(String projectMaterialHistoryId){
		return projectMaterialBrandHistoryRepository.deleteByProjectMaterialHistoryId(projectMaterialHistoryId);
	}

	/**
	 * 根据projectMaterialBrandPrivateId删除记录
	 * @param projectMaterialBrandPrivateId
	 */
	@Override
	public int deleteByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId){
		return projectMaterialBrandHistoryRepository.deleteByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
	}

	/**
	 * 根据projectMaterialBrandPublicId删除记录
	 * @param projectMaterialBrandPublicId
	 */
	@Override
	public int deleteByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId){
		return projectMaterialBrandHistoryRepository.deleteByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(ProjectMaterialBrandHistory projectMaterialBrandHistory){
		return projectMaterialBrandHistoryRepository.update(projectMaterialBrandHistory);
	}

	/**
	 * 得到数量
	 */
	@Override
	public int getCount(){
		return projectMaterialBrandHistoryRepository.getCount();
	}

	/**
	 * 根据projectMaterialHistoryId得到数量
	 * @param projectMaterialHistoryId
	 */
	@Override
	public int getCountByProjectMaterialHistoryId(String projectMaterialHistoryId){
		return projectMaterialBrandHistoryRepository.getCountByProjectMaterialHistoryId(projectMaterialHistoryId);
	}

	/**
	 * 根据projectMaterialBrandPrivateId得到数量
	 * @param projectMaterialBrandPrivateId
	 */
	@Override
	public int getCountByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId){
		return projectMaterialBrandHistoryRepository.getCountByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
	}

	/**
	 * 根据projectMaterialBrandPublicId得到数量
	 * @param projectMaterialBrandPublicId
	 */
	@Override
	public int getCountByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId){
		return projectMaterialBrandHistoryRepository.getCountByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
	}

	/**
	 * 根据id得到ProjectMaterialBrandHistory
	 * @param id
	 */
	@Override
	public ProjectMaterialBrandHistory getById(String id){
		return projectMaterialBrandHistoryRepository.getById(id);
	}

	/**
	 * 根据projectMaterialHistoryId得到ProjectMaterialBrandHistory
	 * @param projectMaterialHistoryId
	 */
	@Override
	public List<ProjectMaterialBrandHistory> getByProjectMaterialHistoryId(String projectMaterialHistoryId){
		return projectMaterialBrandHistoryRepository.getByProjectMaterialHistoryId(projectMaterialHistoryId);
	}

	/**
	 * 根据projectMaterialBrandPrivateId得到ProjectMaterialBrandHistory
	 * @param projectMaterialBrandPrivateId
	 */
	@Override
	public List<ProjectMaterialBrandHistory> getByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId){
		return projectMaterialBrandHistoryRepository.getByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
	}

	/**
	 * 根据projectMaterialBrandPublicId得到ProjectMaterialBrandHistory
	 * @param projectMaterialBrandPublicId
	 */
	@Override
	public List<ProjectMaterialBrandHistory> getByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId){
		return projectMaterialBrandHistoryRepository.getByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
	}

	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialBrandHistory> getPage(int pageNo, int pageSize){
		return projectMaterialBrandHistoryRepository.getPage(pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param projectMaterialHistoryId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialBrandHistory> getPageByProjectMaterialHistoryId(String projectMaterialHistoryId,int pageNo, int pageSize){
		return projectMaterialBrandHistoryRepository.getPageByProjectMaterialHistoryId(projectMaterialHistoryId,pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param projectMaterialBrandPrivateId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialBrandHistory> getPageByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,int pageNo, int pageSize){
		return projectMaterialBrandHistoryRepository.getPageByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId,pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param projectMaterialBrandPublicId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialBrandHistory> getPageByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,int pageNo, int pageSize){
		return projectMaterialBrandHistoryRepository.getPageByProjectMaterialBrandPublicId(projectMaterialBrandPublicId,pageNo,pageSize);
	}

	/**
	 * 根据主键获得视图对象
	 * @param id	主键 
	 */
	private ProjectMaterialBrandHistoryView getProjectMaterialBrandHistoryViewByProjectMaterialBrandHistoryId(String id){
		ProjectMaterialBrandHistory projectMaterialBrandHistory = getById(id);
		if(projectMaterialBrandHistory ==null)return null;
		ProjectMaterialBrandHistoryView projectMaterialBrandHistoryView=new ProjectMaterialBrandHistoryView();
		return null;
	}

	/**
	 * 将页面转换为视图页面
	 * @param projectMaterialBrandHistoryPage	页面对象 
	 */
	private Page<ProjectMaterialBrandHistoryView> convertProjectMaterialBrandHistoryPage2PageView(Page<ProjectMaterialBrandHistory> projectMaterialBrandHistoryPage,int pageNo, int pageSize){
		if(projectMaterialBrandHistoryPage == null)return null;
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectMaterialBrandHistoryView> list =new ArrayList<>();
		for(ProjectMaterialBrandHistory projectMaterialBrandHistory:projectMaterialBrandHistoryPage.getResult()){
			ProjectMaterialBrandHistoryView projectMaterialBrandHistoryView=getProjectMaterialBrandHistoryViewByProjectMaterialBrandHistoryId(projectMaterialBrandHistory.getId());
			if(projectMaterialBrandHistoryView!=null)list.add(projectMaterialBrandHistoryView);
		}
		return new Page<>(startIndex, projectMaterialBrandHistoryPage.getTotalCount(), pageSize, list);
	}

	/**
	 * 获得指定页面视图数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialBrandHistoryView> getPageView(int pageNo, int pageSize){
		Page<ProjectMaterialBrandHistory> projectMaterialBrandHistoryPage = getPage(pageNo,pageSize);
		return convertProjectMaterialBrandHistoryPage2PageView(projectMaterialBrandHistoryPage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param projectMaterialHistoryId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialBrandHistoryView> getPageViewByProjectMaterialHistoryId(String projectMaterialHistoryId,int pageNo, int pageSize){
		Page<ProjectMaterialBrandHistory> projectMaterialBrandHistoryPage = getPageByProjectMaterialHistoryId(projectMaterialHistoryId,pageNo,pageSize);
		return convertProjectMaterialBrandHistoryPage2PageView(projectMaterialBrandHistoryPage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param projectMaterialBrandPrivateId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialBrandHistoryView> getPageViewByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,int pageNo, int pageSize){
		Page<ProjectMaterialBrandHistory> projectMaterialBrandHistoryPage = getPageByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId,pageNo,pageSize);
		return convertProjectMaterialBrandHistoryPage2PageView(projectMaterialBrandHistoryPage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param projectMaterialBrandPublicId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialBrandHistoryView> getPageViewByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,int pageNo, int pageSize){
		Page<ProjectMaterialBrandHistory> projectMaterialBrandHistoryPage = getPageByProjectMaterialBrandPublicId(projectMaterialBrandPublicId,pageNo,pageSize);
		return convertProjectMaterialBrandHistoryPage2PageView(projectMaterialBrandHistoryPage,pageNo, pageSize);
	}

}