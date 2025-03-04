package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;



import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandHistory;

import java.util.List;

/** ProjectMaterialBrandHistory Service Interface 
*/
public interface IProjectMaterialBrandHistoryRepository{

	String add(ProjectMaterialBrandHistory projectMaterialBrandHistory);
	int delete(ProjectMaterialBrandHistory projectMaterialBrandHistory);
	int update(ProjectMaterialBrandHistory projectMaterialBrandHistory);
	int deleteById(String id);
	int deleteByProjectMaterialHistoryId(String projectMaterialHistoryId);
	int deleteByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId);
	int deleteByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId);

	int getCount();
	int getCountByProjectMaterialHistoryId(String projectMaterialHistoryId);
	int getCountByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId);
	int getCountByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId);
	ProjectMaterialBrandHistory getById(String id);
	List<ProjectMaterialBrandHistory> getByProjectMaterialHistoryId(String projectMaterialHistoryId);
	List<ProjectMaterialBrandHistory> getByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId);
	List<ProjectMaterialBrandHistory> getByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId);
	Page<ProjectMaterialBrandHistory> getPage(int pageNo, int pageSize);
	Page<ProjectMaterialBrandHistory> getPageByProjectMaterialHistoryId(String projectMaterialHistoryId,int pageNo, int pageSize);
	Page<ProjectMaterialBrandHistory> getPageByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,int pageNo, int pageSize);
	Page<ProjectMaterialBrandHistory> getPageByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,int pageNo, int pageSize);

}