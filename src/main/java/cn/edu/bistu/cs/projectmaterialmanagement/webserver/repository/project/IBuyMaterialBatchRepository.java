package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;



import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.BuyMaterialBatch;

import java.util.List;

/** BuyMaterialBatch Service Interface 
*/
public interface IBuyMaterialBatchRepository{

	String add(BuyMaterialBatch buyMaterialBatch);
	int delete(BuyMaterialBatch buyMaterialBatch);
	int update(BuyMaterialBatch buyMaterialBatch);
	int deleteById(String id);
	int deleteByUserId(String userId);
	int deleteByProjectId(String projectId);

	int getCount();
	int getCountByUserId(String userId);
	int getCountByProjectId(String projectId);
	BuyMaterialBatch getById(String id);
	List<BuyMaterialBatch> getByUserId(String userId);
	List<BuyMaterialBatch> getByProjectId(String projectId);
	Page<BuyMaterialBatch> getPage(int pageNo, int pageSize);
	Page<BuyMaterialBatch> getPageByUserId(String userId,int pageNo, int pageSize);
	Page<BuyMaterialBatch> getPageByProjectId(String projectId,int pageNo, int pageSize);

}