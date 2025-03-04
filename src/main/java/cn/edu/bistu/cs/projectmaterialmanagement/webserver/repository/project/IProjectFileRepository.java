package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectFile;

import java.util.List;

/**
 * ProjectFile Service Interface
 */
public interface IProjectFileRepository {

    String add(ProjectFile projectFile);

    int delete(ProjectFile projectFile);

    int deleteById(String id);

    int deleteByProjectId(String projectId);

    int update(ProjectFile projectFile);

    int getCount();

    int getCountByProjectId(String projectId);

    ProjectFile getById(String id);

    List<ProjectFile> getByProjectId(String projectId);

    Page<ProjectFile> getPage(int pageNo, int pageSize);

    Page<ProjectFile> getPageByProjectId(String projectId, int pageNo, int pageSize);

}