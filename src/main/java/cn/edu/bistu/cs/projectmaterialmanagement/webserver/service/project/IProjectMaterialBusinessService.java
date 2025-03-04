package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialView;

public interface IProjectMaterialBusinessService {
    String addForm(ProjectMaterialForm projectMaterialForm);

    int updateForm(ProjectMaterialForm projectMaterialForm);

    void deleteByProjectId(String id);


    ProjectMaterialView getViewById(String projectMaterialId);

    Page<ProjectMaterialView> getPageView(Integer pageNo,
                                          Integer pageSize);

    Page<ProjectMaterialView> getPageViewByProjectId(String projectId,
                                                     Integer pageNo,
                                                     Integer pageSize);


    Page<ProjectMaterialView> getPageViewByCurrentUserAndProjectId(String projectId,
                                                                   Integer pageNo,
                                                                   Integer pageSize);

    Page<ProjectMaterialView> getProjectMaterialPageViewByProjectIdAndCompanyId(String projectId,
                                                                                String companyId,
                                                                                Integer pageNo,
                                                                                Integer pageSize);
}
