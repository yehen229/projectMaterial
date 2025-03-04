package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialVerificationDocument;

import java.util.List;

/**
 * ProjectMaterialVerificationDocument Service Interface
 */
public interface IProjectMaterialVerificationDocumentRepository {

    String add(ProjectMaterialVerificationDocument projectMaterialVerificationDocument);

    int delete(ProjectMaterialVerificationDocument projectMaterialVerificationDocument);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByBuyMaterialId(String buyMaterialId);

    int update(ProjectMaterialVerificationDocument projectMaterialVerificationDocument);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByBuyMaterialId(String buyMaterialId);

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

}