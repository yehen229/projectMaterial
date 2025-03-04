package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectOpHistory;

import java.util.List;

public interface IProjectOpHistoryService {
    String add(ProjectOpHistory projectOpHistory);

    int delete(ProjectOpHistory projectOpHistory);

    int update(ProjectOpHistory projectOpHistory);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByProjectId(String projectId);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByProjectId(String projectId);

    ProjectOpHistory getById(String id);

    List<ProjectOpHistory> getByUserId(String userId);

    List<ProjectOpHistory> getByProjectId(String projectId);

    Page<ProjectOpHistory> getPage(int pageNo,
                                   int pageSize);

    Page<ProjectOpHistory> getPageByUserId(String userId,
                                           int pageNo,
                                           int pageSize);

    Page<ProjectOpHistory> getPageByProjectId(String projectId,
                                              int pageNo,
                                              int pageSize);


}