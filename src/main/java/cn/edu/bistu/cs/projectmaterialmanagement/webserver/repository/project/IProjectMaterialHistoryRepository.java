package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;



import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialHistory;

import java.util.List;

/** ProjectMaterialHistory Service Interface 
*/
public interface IProjectMaterialHistoryRepository{

	String add(ProjectMaterialHistory projectMaterialHistory);
	int delete(ProjectMaterialHistory projectMaterialHistory);
	int update(ProjectMaterialHistory projectMaterialHistory);
	int deleteById(String id);
	int deleteByMaterialId(String materialId);
	int deleteByCompanyId(String companyId);
	int deleteByProjectId(String projectId);
	int deleteByProjectMaterialId(String projectMaterialId);

	int getCount();
	int getCountByMaterialId(String materialId);
	int getCountByCompanyId(String companyId);
	int getCountByProjectId(String projectId);
	int getCountByProjectMaterialId(String projectMaterialId);
	ProjectMaterialHistory getById(String id);
	List<ProjectMaterialHistory> getByMaterialId(String materialId);
	List<ProjectMaterialHistory> getByCompanyId(String companyId);
	List<ProjectMaterialHistory> getByProjectId(String projectId);
	List<ProjectMaterialHistory> getByProjectMaterialId(String projectMaterialId);
	Page<ProjectMaterialHistory> getPage(int pageNo, int pageSize);
	Page<ProjectMaterialHistory> getPageByMaterialId(String materialId,int pageNo, int pageSize);
	Page<ProjectMaterialHistory> getPageByCompanyId(String companyId,int pageNo, int pageSize);
	Page<ProjectMaterialHistory> getPageByProjectId(String projectId,int pageNo, int pageSize);
	Page<ProjectMaterialHistory> getPageByProjectMaterialId(String projectMaterialId,int pageNo, int pageSize);

}