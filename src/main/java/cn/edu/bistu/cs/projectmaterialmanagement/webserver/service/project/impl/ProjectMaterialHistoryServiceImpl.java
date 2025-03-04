package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;



import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialHistory;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialHistoryView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialHistoryRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;

@Service
public class ProjectMaterialHistoryServiceImpl implements IProjectMaterialHistoryService {
	private final IProjectMaterialHistoryRepository projectMaterialHistoryRepository;

	public ProjectMaterialHistoryServiceImpl(IProjectMaterialHistoryRepository projectMaterialHistoryRepository){
		this.projectMaterialHistoryRepository=projectMaterialHistoryRepository;
	}

	/**
	 * 增加
	 */
	@Override
	public String add(ProjectMaterialHistory projectMaterialHistory){
		return projectMaterialHistoryRepository.add(projectMaterialHistory);
	}

	/**
	 * 删除
	 */
	@Override
	public int delete(ProjectMaterialHistory projectMaterialHistory){
		return projectMaterialHistoryRepository.delete(projectMaterialHistory);
	}

	/**
	 * 根据id删除记录
	 * @param id
	 */
	@Override
	public int deleteById(String id){
		return projectMaterialHistoryRepository.deleteById(id);
	}

	/**
	 * 根据materialId删除记录
	 * @param materialId
	 */
	@Override
	public int deleteByMaterialId(String materialId){
		return projectMaterialHistoryRepository.deleteByMaterialId(materialId);
	}

	/**
	 * 根据companyId删除记录
	 * @param companyId
	 */
	@Override
	public int deleteByCompanyId(String companyId){
		return projectMaterialHistoryRepository.deleteByCompanyId(companyId);
	}

	/**
	 * 根据projectId删除记录
	 * @param projectId
	 */
	@Override
	public int deleteByProjectId(String projectId){
		return projectMaterialHistoryRepository.deleteByProjectId(projectId);
	}

	/**
	 * 根据projectMaterialId删除记录
	 * @param projectMaterialId
	 */
	@Override
	public int deleteByProjectMaterialId(String projectMaterialId){
		return projectMaterialHistoryRepository.deleteByProjectMaterialId(projectMaterialId);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(ProjectMaterialHistory projectMaterialHistory){
		return projectMaterialHistoryRepository.update(projectMaterialHistory);
	}

	/**
	 * 得到数量
	 */
	@Override
	public int getCount(){
		return projectMaterialHistoryRepository.getCount();
	}

	/**
	 * 根据materialId得到数量
	 * @param materialId
	 */
	@Override
	public int getCountByMaterialId(String materialId){
		return projectMaterialHistoryRepository.getCountByMaterialId(materialId);
	}

	/**
	 * 根据companyId得到数量
	 * @param companyId
	 */
	@Override
	public int getCountByCompanyId(String companyId){
		return projectMaterialHistoryRepository.getCountByCompanyId(companyId);
	}

	/**
	 * 根据projectId得到数量
	 * @param projectId
	 */
	@Override
	public int getCountByProjectId(String projectId){
		return projectMaterialHistoryRepository.getCountByProjectId(projectId);
	}

	/**
	 * 根据projectMaterialId得到数量
	 * @param projectMaterialId
	 */
	@Override
	public int getCountByProjectMaterialId(String projectMaterialId){
		return projectMaterialHistoryRepository.getCountByProjectMaterialId(projectMaterialId);
	}

	/**
	 * 根据id得到ProjectMaterialHistory
	 * @param id
	 */
	@Override
	public ProjectMaterialHistory getById(String id){
		return projectMaterialHistoryRepository.getById(id);
	}

	/**
	 * 根据materialId得到ProjectMaterialHistory
	 * @param materialId
	 */
	@Override
	public List<ProjectMaterialHistory> getByMaterialId(String materialId){
		return projectMaterialHistoryRepository.getByMaterialId(materialId);
	}

	/**
	 * 根据companyId得到ProjectMaterialHistory
	 * @param companyId
	 */
	@Override
	public List<ProjectMaterialHistory> getByCompanyId(String companyId){
		return projectMaterialHistoryRepository.getByCompanyId(companyId);
	}

	/**
	 * 根据projectId得到ProjectMaterialHistory
	 * @param projectId
	 */
	@Override
	public List<ProjectMaterialHistory> getByProjectId(String projectId){
		return projectMaterialHistoryRepository.getByProjectId(projectId);
	}

	/**
	 * 根据projectMaterialId得到ProjectMaterialHistory
	 * @param projectMaterialId
	 */
	@Override
	public List<ProjectMaterialHistory> getByProjectMaterialId(String projectMaterialId){
		return projectMaterialHistoryRepository.getByProjectMaterialId(projectMaterialId);
	}

	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialHistory> getPage(int pageNo, int pageSize){
		return projectMaterialHistoryRepository.getPage(pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param materialId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialHistory> getPageByMaterialId(String materialId,int pageNo, int pageSize){
		return projectMaterialHistoryRepository.getPageByMaterialId(materialId,pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param companyId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialHistory> getPageByCompanyId(String companyId,int pageNo, int pageSize){
		return projectMaterialHistoryRepository.getPageByCompanyId(companyId,pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param projectId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialHistory> getPageByProjectId(String projectId,int pageNo, int pageSize){
		return projectMaterialHistoryRepository.getPageByProjectId(projectId,pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param projectMaterialId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialHistory> getPageByProjectMaterialId(String projectMaterialId,int pageNo, int pageSize){
		return projectMaterialHistoryRepository.getPageByProjectMaterialId(projectMaterialId,pageNo,pageSize);
	}

	/**
	 * 根据主键获得视图对象
	 * @param id	主键 
	 */
	private ProjectMaterialHistoryView getProjectMaterialHistoryViewByProjectMaterialHistoryId(String id){
		ProjectMaterialHistory projectMaterialHistory = getById(id);
		if(projectMaterialHistory ==null)return null;
		ProjectMaterialHistoryView projectMaterialHistoryView=new ProjectMaterialHistoryView();
		return null;
	}

	/**
	 * 将页面转换为视图页面
	 * @param projectMaterialHistoryPage	页面对象 
	 */
	private Page<ProjectMaterialHistoryView> convertProjectMaterialHistoryPage2PageView(Page<ProjectMaterialHistory> projectMaterialHistoryPage,int pageNo, int pageSize){
		if(projectMaterialHistoryPage == null)return null;
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectMaterialHistoryView> list =new ArrayList<>();
		for(ProjectMaterialHistory projectMaterialHistory:projectMaterialHistoryPage.getResult()){
			ProjectMaterialHistoryView projectMaterialHistoryView=getProjectMaterialHistoryViewByProjectMaterialHistoryId(projectMaterialHistory.getId());
			if(projectMaterialHistoryView!=null)list.add(projectMaterialHistoryView);
		}
		return new Page<>(startIndex, projectMaterialHistoryPage.getTotalCount(), pageSize, list);
	}

	/**
	 * 获得指定页面视图数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialHistoryView> getPageView(int pageNo, int pageSize){
		Page<ProjectMaterialHistory> projectMaterialHistoryPage = getPage(pageNo,pageSize);
		return convertProjectMaterialHistoryPage2PageView(projectMaterialHistoryPage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param materialId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialHistoryView> getPageViewByMaterialId(String materialId,int pageNo, int pageSize){
		Page<ProjectMaterialHistory> projectMaterialHistoryPage = getPageByMaterialId(materialId,pageNo,pageSize);
		return convertProjectMaterialHistoryPage2PageView(projectMaterialHistoryPage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param companyId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialHistoryView> getPageViewByCompanyId(String companyId,int pageNo, int pageSize){
		Page<ProjectMaterialHistory> projectMaterialHistoryPage = getPageByCompanyId(companyId,pageNo,pageSize);
		return convertProjectMaterialHistoryPage2PageView(projectMaterialHistoryPage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param projectId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialHistoryView> getPageViewByProjectId(String projectId,int pageNo, int pageSize){
		Page<ProjectMaterialHistory> projectMaterialHistoryPage = getPageByProjectId(projectId,pageNo,pageSize);
		return convertProjectMaterialHistoryPage2PageView(projectMaterialHistoryPage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param projectMaterialId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialHistoryView> getPageViewByProjectMaterialId(String projectMaterialId,int pageNo, int pageSize){
		Page<ProjectMaterialHistory> projectMaterialHistoryPage = getPageByProjectMaterialId(projectMaterialId,pageNo,pageSize);
		return convertProjectMaterialHistoryPage2PageView(projectMaterialHistoryPage,pageNo, pageSize);
	}

}