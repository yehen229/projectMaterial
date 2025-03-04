package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialVerificationDocument;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialVerificationDocumentView;

import java.util.List;

public interface IProjectMaterialVerificationDocumentService {
    String add(ProjectMaterialVerificationDocument projectMaterialVerificationDocument);

    int delete(ProjectMaterialVerificationDocument projectMaterialVerificationDocument);

    int update(ProjectMaterialVerificationDocument projectMaterialVerificationDocument);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByBuyMaterialId(String buyMaterialId);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByBuyMaterialId(String projectMaterialId);

    ProjectMaterialVerificationDocument getById(String id);

    List<ProjectMaterialVerificationDocument> getByUserId(String userId);

    List<ProjectMaterialVerificationDocument> getByBuyMaterialId(String buyMaterialId);

    Page<ProjectMaterialVerificationDocument> getPage(int pageNo,
                                                      int pageSize);

    Page<ProjectMaterialVerificationDocument> getPageByUserId(String userId,
                                                              int pageNo,
                                                              int pageSize);

    Page<ProjectMaterialVerificationDocument> getPageByBuyMaterialId(String buyMaterialId,
                                                                     int pageNo,
                                                                     int pageSize);

    Page<ProjectMaterialVerificationDocumentView> getPageView(int pageNo,
                                                              int pageSize);

    Page<ProjectMaterialVerificationDocumentView> getPageViewByUserId(String userId,
                                                                      int pageNo,
                                                                      int pageSize);

    Page<ProjectMaterialVerificationDocumentView> getPageViewByBuyMaterialId(String buyMaterialId,
                                                                             int pageNo,
                                                                             int pageSize);

}