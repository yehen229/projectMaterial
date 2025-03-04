package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceBatch;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceBatchView;

import java.util.List;

public interface IProjectMaterialAcceptanceBatchService {
    String add(ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch);

    int delete(ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch);

    int update(ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByProjectId(String projectId);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByProjectId(String projectId);

    ProjectMaterialAcceptanceBatch getById(String id);

    List<ProjectMaterialAcceptanceBatch> getByUserId(String userId);

    List<ProjectMaterialAcceptanceBatch> getByProjectId(String projectId);

    Page<ProjectMaterialAcceptanceBatch> getPage(int pageNo,
                                                 int pageSize);

    Page<ProjectMaterialAcceptanceBatch> getPageByUserId(String userId,
                                                         int pageNo,
                                                         int pageSize);

    Page<ProjectMaterialAcceptanceBatch> getPageByProjectId(String projectId,
                                                            int pageNo,
                                                            int pageSize);

    Page<ProjectMaterialAcceptanceBatchView> getPageView(int pageNo,
                                                         int pageSize);

    Page<ProjectMaterialAcceptanceBatchView> getPageViewByUserId(String userId,
                                                                 int pageNo,
                                                                 int pageSize);

    Page<ProjectMaterialAcceptanceBatchView> getPageViewByProjectId(String projectId,
                                                                    int pageNo,
                                                                    int pageSize);

}