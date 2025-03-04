package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialVerificationDocumentFile;

import java.util.List;

/**
 * ProjectMaterialVerificationDocumentFile Service Interface
 */
public interface IProjectMaterialVerificationDocumentFileRepository {

    String add(ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile);

    int delete(ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile);

    int deleteById(String id);

    int deleteByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId);

    int update(ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile);

    int getCount();

    int getCountByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId);

    ProjectMaterialVerificationDocumentFile getById(String id);

    List<ProjectMaterialVerificationDocumentFile> getByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId);

    Page<ProjectMaterialVerificationDocumentFile> getPage(int pageNo, int pageSize);

    Page<ProjectMaterialVerificationDocumentFile> getPageByProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId, int pageNo, int pageSize);

}