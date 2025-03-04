package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUserForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUserView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICompanyUserService {
    String add(CompanyUser companyUser);

    String addCompanyUserForm(CompanyUserForm companyUserForm);

    int addUsersFromExcel(MultipartFile multipartFile) throws Exception;

    int delete(CompanyUser companyUser);

    int update(CompanyUser companyUser);

    int updateCompanyUserForm(CompanyUserForm companyUserForm);

    int deleteById(String id);

    int deleteByCompanyId(String companyId);

    int deleteByUserId(String userId);

    int getCount();

    int getCountByCompanyId(String companyId);

    int getCountByUserId(String userId);

    CompanyUser getById(String id);

    Company getCompanyById(String id);

    Company getCompanyByUserId(String userId);

    // 判断是否是设计单位员工
    boolean isDesignCompanyByUserId(String userId);

    //是否是设计部员工
    boolean isDesignDepartmentByUserId(String userId);

    //是否是工程部员工
    boolean isEngineeringDepartmentByUserId(String userId);

    //是否是监理单位员工
    boolean isSupervisionCompanyByUserId(String userId);

    //是否是总包单位员工
    boolean isGeneralContractorCompanyByUserId(String userId);

    List<CompanyUser> getByCompanyId(String companyId);

    List<CompanyUser> getByCompanyType(String companyType);

    CompanyUser getByUserId(String userId);

    Page<CompanyUser> getPage(int pageNo, int pageSize);

    Page<CompanyUser> getPageByCompanyId(String companyId, int pageNo, int pageSize);

    Page<CompanyUser> getPageByUserId(String userId, int pageNo, int pageSize);

    Page<CompanyUser> getPageByUserName(String userName, int pageNo, int pageSize);

    Page<CompanyUser> getPageByProjectName(String projectName, int pageNo, int pageSize);

    Page<CompanyUser> getPageByCompanyName(String companyName, int pageNo, int pageSize);

    Page<CompanyUserView> getPageView(int pageNo, int pageSize);

    Page<CompanyUserView> getPageViewByCompanyId(String companyId, int pageNo, int pageSize);

    Page<CompanyUserView> getPageViewByUserId(String userId, int pageNo, int pageSize);


    Page<CompanyUserView> getPageViewByUserName(String userName, Integer pageNo, Integer pageSize);

    Page<CompanyUserView> getPageViewByProjectName(String projectName, Integer pageNo, Integer pageSize);

    Page<CompanyUserView> getPageViewByCompanyName(String companyName, Integer pageNo, Integer pageSize);

    Page<CompanyUserView> getPageViewByCompanyType(String companyType, Integer pageNo, Integer pageSize);

    Page<CompanyUserView> getPageViewByCompanyNameAndType(String companyName, String companyType, Integer pageNo, Integer pageSize);


    void downloadAllCompanyUser(HttpServletRequest request, HttpServletResponse response);

    void downloadCompanyUserByUserName(String userName, HttpServletRequest request, HttpServletResponse response);

    void downloadCompanyUserByProjectName(String projectName, HttpServletRequest request, HttpServletResponse response);

    void downloadCompanyUserByCompanyName(String companyName, HttpServletRequest request, HttpServletResponse response);

}