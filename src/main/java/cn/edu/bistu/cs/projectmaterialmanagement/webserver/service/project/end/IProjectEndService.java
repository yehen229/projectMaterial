package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.end;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEnd;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEndView;

import java.util.List;

public interface IProjectEndService {
    String add(ProjectEnd projectEnd);

    int delete(ProjectEnd projectEnd);

    int update(ProjectEnd projectEnd);

    int deleteById(String id);

    int deleteByProjectId(String projectId);

    int deleteByUserId(String userId);

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

    Page<ProjectEndView> getPageView(int pageNo,
                                     int pageSize);

    Page<ProjectEndView> getPageViewByProjectId(String projectId,
                                                int pageNo,
                                                int pageSize);

    Page<ProjectEndView> getPageViewByUserId(String userId,
                                             int pageNo,
                                             int pageSize);

}