package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.ICompanyRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements ICompanyService {

    private static final Logger log =
            LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final ICompanyRepository companyRepository;

    public CompanyServiceImpl(ICompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * 增加
     */
    @Override
    public String add(Company company) {
        return companyRepository.add(company);
    }

    /**
     * 删除
     */
    @Override
    public int delete(Company company) {
        return companyRepository.delete(company);
    }

    /**
     * 更新
     */
    @Override
    public int update(Company company) {
        return companyRepository.update(company);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return companyRepository.deleteById(id);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return companyRepository.getCount();
    }

    /**
     * 根据id得到Company
     *
     * @param id
     */
    @Override
    public Company getById(String id) {
        return companyRepository.getById(id);
    }

    @Override
    public Company getByName(String name) {
        return companyRepository.getByName(name);
    }


    /**
     * 获取设计部
     * @return
     */
    @Override
    public Company getDesignDepartment() {
        return getByName(COMPANY_NAME_DESIGN_DEPARTMENT);
    }


    /**
     * 获取工程部
     * @return
     */
    @Override
    public Company getEngineeringDepartment() {
        return getByName(COMPANY_NAME_ENGINEERING_DEPARTMENT);
    }

    @Override
    public Page<Company> getPage(int pageNo, int pageSize) {
        return companyRepository.getPage(pageNo, pageSize);
    }

    @Override
    public List<Company> getAllCompanyList() {
        return companyRepository.getAllCompanyList();
    }

    @Override
    public List<Company> getFactoryList() {
        return companyRepository.getFactoryList();
    }

    @Override
    public List<Company> getCompanyListByType(String companyType) {
        return companyRepository.getCompanyListByType(companyType);
    }

    @Override
    public Page<Company> getPageByCompanyName(String companyName, Integer pageNo, Integer pageSize) {
        return companyRepository.getPageByCompanyName(companyName, pageNo, pageSize);
    }

    @Override
    public Page<Company> getPageByCompanyType(String companyType, Integer pageNo, Integer pageSize) {
        return companyRepository.getPageByCompanyType(companyType, pageNo, pageSize);
    }
}



