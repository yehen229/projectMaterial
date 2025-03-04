package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.end;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEndFile;

import java.util.List;

/**
 * ProjectEndFile Service Interface
 */
public interface IProjectEndFileRepository {

    String add(ProjectEndFile projectEndFile);

    int delete(ProjectEndFile projectEndFile);

    int deleteById(String id);

    int deleteByProjectEndId(String projectEndId);

    int update(ProjectEndFile projectEndFile);

    int getCount();

    int getCountByProjectEndId(String projectEndId);

    ProjectEndFile getById(String id);

    List<ProjectEndFile> getByProjectEndId(String projectEndId);

    Page<ProjectEndFile> getPage(int pageNo,
                                 int pageSize);

    Page<ProjectEndFile> getPageByProjectEndId(String projectEndId,
                                               int pageNo,
                                               int pageSize);

}