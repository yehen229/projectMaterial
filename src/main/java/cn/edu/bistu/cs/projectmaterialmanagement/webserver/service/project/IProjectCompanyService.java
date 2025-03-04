package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectCompany;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectCompanyView;

import java.util.List;

public interface IProjectCompanyService {
    String add(ProjectCompany projectCompany);

    int delete(ProjectCompany projectCompany);

    int update(ProjectCompany projectCompany);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByTotalPackageCompanyId(String totalPackageCompanyId);

    int deleteBySupervisionCompanyId(String supervisionCompanyId);

    int deleteByProjectId(String projectId);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByTotalPackageCompanyId(String totalPackageCompanyId);

    int getCountBySupervisionCompanyId(String supervisionCompanyId);

    int getCountByProjectId(String projectId);

    ProjectCompany getById(String id);

    List<ProjectCompany> getByUserId(String userId);

    List<ProjectCompany> getByTotalPackageCompanyId(String totalPackageCompanyId);

    List<ProjectCompany> getBySupervisionCompanyId(String supervisionCompanyId);

    ProjectCompany getByProjectId(String projectId);

    Page<ProjectCompany> getPage(int pageNo, int pageSize);

    Page<ProjectCompany> getPageByUserId(String userId, int pageNo, int pageSize);

    Page<ProjectCompany> getPageByTotalPackageCompanyId(String totalPackageCompanyId, int pageNo, int pageSize);

    Page<ProjectCompany> getPageBySupervisionCompanyId(String supervisionCompanyId, int pageNo, int pageSize);

    Page<ProjectCompany> getPageByProjectId(String projectId, int pageNo, int pageSize);

    Page<ProjectCompanyView> getPageView(int pageNo, int pageSize);

    Page<ProjectCompanyView> getPageViewByUserId(String userId, int pageNo, int pageSize);

    Page<ProjectCompanyView> getPageViewByTotalPackageCompanyId(String totalPackageCompanyId, int pageNo, int pageSize);

    Page<ProjectCompanyView> getPageViewBySupervisionCompanyId(String supervisionCompanyId, int pageNo, int pageSize);

    Page<ProjectCompanyView> getPageViewByProjectId(String projectId, int pageNo, int pageSize);

}