package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectCompany;

import java.util.List;

/**
 * ProjectCompany Service Interface
 */
public interface IProjectCompanyRepository {

    String add(ProjectCompany projectCompany);

    int delete(ProjectCompany projectCompany);

    int deleteById(String id);

    int deleteByUserId(String userId);

    int deleteByGeneralContractorCompanyId(String generalContractorCompanyId);

    int deleteBySupervisionCompanyId(String supervisionCompanyId);

    int deleteByProjectId(String projectId);

    int update(ProjectCompany projectCompany);

    int getCount();

    int getCountByUserId(String userId);

    int getCountByGeneralContractorCompanyId(String totalPackageCompanyId);

    int getCountBySupervisionCompanyId(String supervisionCompanyId);

    int getCountByProjectId(String projectId);

    ProjectCompany getById(String id);

    List<ProjectCompany> getByUserId(String userId);

    List<ProjectCompany> getByGeneralContractorCompanyId(String totalPackageCompanyId);

    List<ProjectCompany> getBySupervisionCompanyId(String supervisionCompanyId);

    ProjectCompany getByProjectId(String projectId);

    Page<ProjectCompany> getPage(int pageNo, int pageSize);

    Page<ProjectCompany> getPageByUserId(String userId, int pageNo, int pageSize);

    Page<ProjectCompany> getPageByGeneralContractorCompanyId(String totalPackageCompanyId, int pageNo, int pageSize);

    Page<ProjectCompany> getPageBySupervisionCompanyId(String supervisionCompanyId, int pageNo, int pageSize);

    Page<ProjectCompany> getPageByProjectId(String projectId, int pageNo, int pageSize);

}