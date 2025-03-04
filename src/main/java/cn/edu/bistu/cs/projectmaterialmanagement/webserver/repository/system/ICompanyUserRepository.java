package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUser;

import java.util.List;

/**
 * CompanyUser Service Interface
 */
public interface ICompanyUserRepository {

    String add(CompanyUser companyUser);

    int delete(CompanyUser companyUser);

    int deleteById(String id);

    int deleteByCompanyId(String companyId);

    int deleteByUserId(String userId);

    int update(CompanyUser companyUser);

    int getCount();

    int getCountByCompanyId(String companyId);

    int getCountByUserId(String userId);

    int getCountByUserName(String userName);

    int getCountByProjectName(String projectName);

    int getCountByCompanyName(String companyName);

    int getCountByCompanyType(String companyType);

    int getCountByCompanyNameAndType(String companyName, String companyType);

    int getCountExcludingByCompanyId(String companyId,
                                      List<String> userIdList);

    CompanyUser getById(String id);

    List<CompanyUser> getByCompanyId(String companyId);

    CompanyUser getByUserId(String userId);

    List<CompanyUser> getAllList();

    List<CompanyUser> getListByUserName(String userName);

    List<CompanyUser> getListByProjectName(String projectName);

    List<CompanyUser> getListByCompanyName(String companyName);

    List<CompanyUser> getByCompanyType(String companyType);

    Page<CompanyUser> getPage(int pageNo, int pageSize);

    Page<CompanyUser> getPageByCompanyId(String companyId, int pageNo, int pageSize);

    Page<CompanyUser> getPageByUserId(String userId, int pageNo, int pageSize);

    Page<CompanyUser> getPageByUserName(String userName, int pageNo, int pageSize);

    Page<CompanyUser> getPageByProjectName(String projectName, int pageNo, int pageSize);

    Page<CompanyUser> getPageByCompanyName(String companyName, int pageNo, int pageSize);

    Page<CompanyUser> getPageByCompanyType(String companyType, Integer pageNo, Integer pageSize);

    Page<CompanyUser> getPageByNameAndType(String companyName, String companyType, Integer pageNo, Integer pageSize);


    Page<CompanyUser> getPageExcludingByCompanyId(String companyId,
                                                  List<String> userIdList,
                                                  Integer pageNo,
                                                  Integer pageSize);
}