package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;

import java.util.List;

/**
 * Company Service Interface
 */
public interface ICompanyRepository {

    String add(Company company);

    int delete(Company company);

    int deleteById(String id);

    int update(Company company);

    int getCount();

    int getCountByLikeName(String name);

    int getCountByLikeCompanyType(String companyType);

    Company getById(String id);

    Company getByName(String name);

    Page<Company> getPage(int pageNo, int pageSize);

    Page<Company> getPageByCompanyName(String companyName, int pageNo, int pageSize);

    Page<Company> getPageByCompanyType(String companyType, int pageNo, int pageSize);

    List<Company> getAllCompanyList();
    List<Company> getFactoryList();

    List<Company> getCompanyListByType(String companyType);
    boolean ExistCompany(Company company);
}