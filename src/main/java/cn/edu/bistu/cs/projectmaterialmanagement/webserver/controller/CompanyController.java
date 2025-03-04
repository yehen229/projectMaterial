package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("company/v1")
@EnableMethodSecurity
public class CompanyController {
    private final ICompanyService companyService;

    CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(value = "get-by-id")
    public Company getById(@RequestParam(value = "id") String id) {
        return companyService.getById(id);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody Company company) {
        return companyService.add(company);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody Company company) {
        return companyService.delete(company);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody Company company) {
        return companyService.update(company);
    }


    @GetMapping(value = "get-all-list")
    // @PreAuthorize("hasRole('Admin')")
    public List<Company> getAllCompanyList() {
        return companyService.getAllCompanyList();
    }

    @GetMapping(value = "get-list-by-company-type")
    // @PreAuthorize("hasRole('Admin')")
    public List<Company> getCompanyListByType(@RequestParam(value = "companyType") String companyType) {
        return companyService.getCompanyListByType(companyType);
    }


    @GetMapping(value = "page")
    public Page<Company> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return companyService.getPage(pageNo, pageSize);
    }

    @GetMapping(value = "page-by-company-name")
    public Page<Company> getPageByCompanyName(@RequestParam(value = "companyName", required = true) String companyName,
                                              @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return companyService.getPageByCompanyName(companyName, pageNo, pageSize);
    }

    @GetMapping(value = "page-by-company-type")
    public Page<Company> getPageByCompanyType(@RequestParam(value = "companyType", required = true) String companyType,
                                              @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return companyService.getPageByCompanyType(companyType, pageNo, pageSize);
    }

}