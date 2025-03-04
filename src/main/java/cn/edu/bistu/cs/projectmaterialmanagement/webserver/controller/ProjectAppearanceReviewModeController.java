package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReviewMode;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.appearance.IProjectAppearanceReviewModeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectappearancereviewmode/v1")
@EnableMethodSecurity
public class ProjectAppearanceReviewModeController {
    private final IProjectAppearanceReviewModeService projectAppearanceReviewModeService;

    ProjectAppearanceReviewModeController(IProjectAppearanceReviewModeService projectAppearanceReviewModeService) {
        this.projectAppearanceReviewModeService = projectAppearanceReviewModeService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectAppearanceReviewMode getById(@RequestParam(value = "id") String id) {
        return projectAppearanceReviewModeService.getById(id);
    }


    @GetMapping(value = "get-by-user-id")
    public List<ProjectAppearanceReviewMode> getByUserId(@RequestParam(value = "userId") String userId) {
        return projectAppearanceReviewModeService.getByUserId(userId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectAppearanceReviewMode projectAppearanceReviewMode) {
        return projectAppearanceReviewModeService.add(projectAppearanceReviewMode);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectAppearanceReviewMode projectAppearanceReviewMode) {
        return projectAppearanceReviewModeService.delete(projectAppearanceReviewMode);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectAppearanceReviewMode projectAppearanceReviewMode) {
        return projectAppearanceReviewModeService.update(projectAppearanceReviewMode);
    }


    @GetMapping(value = "page")
    public Page<ProjectAppearanceReviewMode> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectAppearanceReviewModeService.getPage(pageNo, pageSize);
    }

}