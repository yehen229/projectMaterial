package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;

import java.util.List;

public interface ICompanyService {

    String COMPANY_TYPE_CONSTRUCTION = "建设单位";
    String COMPANY_TYPE_DESIGN = "设计单位";
    String COMPANY_TYPE_SUPERVISION = "监理单位";
    String COMPANY_TYPE_GENERAL_CONSTRUCTION = "总包单位";
    String COMPANY_TYPE_FACTORY = "厂家";
    String COMPANY_NAME_DESIGN_DEPARTMENT = "设计部";
    String COMPANY_NAME_ENGINEERING_DEPARTMENT = "工程部";


    String add(Company company);

    int delete(Company company);

    int update(Company company);

    int deleteById(String id);

    int getCount();

    Company getById(String id);

    Company getByName(String name);

    Company getDesignDepartment();

    Company getEngineeringDepartment();

    Page<Company> getPage(int pageNo, int pageSize);

    List<Company> getAllCompanyList();

    List<Company> getFactoryList();

    List<Company> getCompanyListByType(String companyType);

    Page<Company> getPageByCompanyName(String companyName, Integer pageNo, Integer pageSize);

    Page<Company> getPageByCompanyType(String companyType, Integer pageNo, Integer pageSize);
}