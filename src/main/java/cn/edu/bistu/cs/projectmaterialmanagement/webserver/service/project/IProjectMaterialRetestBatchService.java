package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;



import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetestBatch;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetestBatchView;

import java.util.List;

public interface IProjectMaterialRetestBatchService {
	String add(ProjectMaterialRetestBatch projectMaterialRetestBatch);
	int delete(ProjectMaterialRetestBatch projectMaterialRetestBatch);
	int update(ProjectMaterialRetestBatch projectMaterialRetestBatch);
	int deleteById(String id);
	int deleteByUserId(String userId);
	int deleteByProjectId(String projectId);
	int deleteByBuyMaterialBatchId(String buyMaterialBatchId);
	int getCount();
	int getCountByUserId(String userId);
	int getCountByProjectId(String projectId);
	int getCountByBuyMaterialBatchId(String buyMaterialBatchId);
	ProjectMaterialRetestBatch getById(String id);
	List<ProjectMaterialRetestBatch> getByUserId(String userId);
	List<ProjectMaterialRetestBatch> getByProjectId(String projectId);
	List<ProjectMaterialRetestBatch> getByBuyMaterialBatchId(String buyMaterialBatchId);
	Page<ProjectMaterialRetestBatch> getPage(int pageNo, int pageSize);
	Page<ProjectMaterialRetestBatch> getPageByUserId(String userId,int pageNo, int pageSize);
	Page<ProjectMaterialRetestBatch> getPageByProjectId(String projectId,int pageNo, int pageSize);
	Page<ProjectMaterialRetestBatch> getPageByBuyMaterialBatchId(String buyMaterialBatchId,int pageNo, int pageSize);
	Page<ProjectMaterialRetestBatchView> getPageView(int pageNo, int pageSize);
	Page<ProjectMaterialRetestBatchView> getPageViewByUserId(String userId,int pageNo, int pageSize);
	Page<ProjectMaterialRetestBatchView> getPageViewByProjectId(String projectId,int pageNo, int pageSize);
	Page<ProjectMaterialRetestBatchView> getPageViewByBuyMaterialBatchId(String buyMaterialBatchId,int pageNo, int pageSize);

}