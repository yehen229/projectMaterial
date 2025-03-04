package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectOpHistoryContent;

import java.util.List;

/**
 * ProjectOpHistoryContent Service Interface
 */
public interface IProjectOpHistoryContentRepository {

    String add(ProjectOpHistoryContent projectOpHistoryContent);

    int delete(ProjectOpHistoryContent projectOpHistoryContent);

    int update(ProjectOpHistoryContent projectOpHistoryContent);

    int deleteById(String id);

    int deleteByProjectOpHistoryId(String projectOpHistoryId);

    int getCount();

    int getCountByProjectOpHistoryId(String projectOpHistoryId);

    ProjectOpHistoryContent getById(String id);

    List<ProjectOpHistoryContent> getByProjectOpHistoryId(String projectOpHistoryId);

    Page<ProjectOpHistoryContent> getPage(int pageNo,
                                          int pageSize);

    Page<ProjectOpHistoryContent> getPageByProjectOpHistoryId(String projectOpHistoryId,
                                                              int pageNo,
                                                              int pageSize);

}