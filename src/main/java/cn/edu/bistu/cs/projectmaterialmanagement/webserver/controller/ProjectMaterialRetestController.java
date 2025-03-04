package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetest;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialRetestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectmaterialretest/v1")
@EnableMethodSecurity
public class ProjectMaterialRetestController {
    private final IProjectMaterialRetestService projectMaterialRetestService;

    ProjectMaterialRetestController(IProjectMaterialRetestService projectMaterialRetestService) {
        this.projectMaterialRetestService = projectMaterialRetestService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectMaterialRetest getById(@RequestParam(value = "id") String id) {
        return projectMaterialRetestService.getById(id);
    }


    @GetMapping(value = "get-by-buy-material-id")
    public List<ProjectMaterialRetest> getByBuyMaterialId(@RequestParam(value = "buyMaterialId") String buyMaterialId) {
        return projectMaterialRetestService.getByBuyMaterialId(buyMaterialId);
    }


    @GetMapping(value = "get-by-user-id")
    public List<ProjectMaterialRetest> getByUserId(@RequestParam(value = "userId") String userId) {
        return projectMaterialRetestService.getByUserId(userId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectMaterialRetest projectMaterialRetest) {
        return projectMaterialRetestService.add(projectMaterialRetest);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectMaterialRetest projectMaterialRetest) {
        return projectMaterialRetestService.delete(projectMaterialRetest);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectMaterialRetest projectMaterialRetest) {
        return projectMaterialRetestService.update(projectMaterialRetest);
    }


    @GetMapping(value = "page")
    public Page<ProjectMaterialRetest> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                               @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialRetestService.getPage(pageNo, pageSize);
    }

}