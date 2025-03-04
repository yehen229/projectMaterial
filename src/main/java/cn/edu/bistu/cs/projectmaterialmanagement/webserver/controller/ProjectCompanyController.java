package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectCompany;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectCompanyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectcompany/v1")
@EnableMethodSecurity
public class ProjectCompanyController {
    private final IProjectCompanyService projectCompanyService;

    ProjectCompanyController(IProjectCompanyService projectCompanyService) {
        this.projectCompanyService = projectCompanyService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectCompany getById(@RequestParam(value = "id") String id) {
        return projectCompanyService.getById(id);
    }


    @GetMapping(value = "get-by-user-id")
    public List<ProjectCompany> getByUserId(@RequestParam(value = "userId") String userId) {
        return projectCompanyService.getByUserId(userId);
    }


    @GetMapping(value = "get-by-total-package-company-id")
    public List<ProjectCompany> getByTotalPackageCompanyId(@RequestParam(value = "totalPackageCompanyId") String totalPackageCompanyId) {
        return projectCompanyService.getByTotalPackageCompanyId(totalPackageCompanyId);
    }


    @GetMapping(value = "get-by-supervision-company-id")
    public List<ProjectCompany> getBySupervisionCompanyId(@RequestParam(value = "supervisionCompanyId") String supervisionCompanyId) {
        return projectCompanyService.getBySupervisionCompanyId(supervisionCompanyId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectCompany projectCompany) {
        return projectCompanyService.add(projectCompany);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectCompany projectCompany) {
        return projectCompanyService.delete(projectCompany);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectCompany projectCompany) {
        return projectCompanyService.update(projectCompany);
    }


    @GetMapping(value = "page")
    public Page<ProjectCompany> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectCompanyService.getPage(pageNo, pageSize);
    }

}