package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.end;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEnd;

import java.util.List;

/**
 * ProjectEnd Service Interface
 */
public interface IProjectEndRepository {

    String add(ProjectEnd projectEnd);

    int delete(ProjectEnd projectEnd);

    int deleteById(String id);

    int deleteByProjectId(String projectId);

    int deleteByUserId(String userId);

    int update(ProjectEnd projectEnd);

    int getCount();

    int getCountByProjectId(String projectId);

    int getCountByUserId(String userId);

    ProjectEnd getById(String id);

    List<ProjectEnd> getByProjectId(String projectId);

    List<ProjectEnd> getByUserId(String userId);

    Page<ProjectEnd> getPage(int pageNo,
                             int pageSize);

    Page<ProjectEnd> getPageByProjectId(String projectId,
                                        int pageNo,
                                        int pageSize);

    Page<ProjectEnd> getPageByUserId(String userId,
                                     int pageNo,
                                     int pageSize);

}