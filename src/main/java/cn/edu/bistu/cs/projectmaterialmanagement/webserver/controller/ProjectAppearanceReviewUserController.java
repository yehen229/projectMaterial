package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectAppearanceReviewUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectAppearanceReviewUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectappearancereviewuser/v1")
@EnableMethodSecurity
public class ProjectAppearanceReviewUserController {
    private final IProjectAppearanceReviewUserService projectAppearanceReviewUserService;

    ProjectAppearanceReviewUserController(IProjectAppearanceReviewUserService projectAppearanceReviewUserService) {
        this.projectAppearanceReviewUserService = projectAppearanceReviewUserService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectAppearanceReviewUser getById(@RequestParam(value = "id") String id) {
        return projectAppearanceReviewUserService.getById(id);
    }


    @GetMapping(value = "get-by-user-id")
    public List<ProjectAppearanceReviewUser> getByUserId(@RequestParam(value = "userId") String userId) {
        return projectAppearanceReviewUserService.getByUserId(userId);
    }


    @GetMapping(value = "get-by-project-appearance-review-id")
    public List<ProjectAppearanceReviewUser> getByProjectAppearanceReviewId(@RequestParam(value = "projectAppearanceReviewId") String projectAppearanceReviewId) {
        return projectAppearanceReviewUserService.getByProjectAppearanceReviewId(projectAppearanceReviewId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectAppearanceReviewUser projectAppearanceReviewUser) {
        return projectAppearanceReviewUserService.add(projectAppearanceReviewUser);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectAppearanceReviewUser projectAppearanceReviewUser) {
        return projectAppearanceReviewUserService.delete(projectAppearanceReviewUser);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectAppearanceReviewUser projectAppearanceReviewUser) {
        return projectAppearanceReviewUserService.update(projectAppearanceReviewUser);
    }


    @GetMapping(value = "page")
    public Page<ProjectAppearanceReviewUser> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectAppearanceReviewUserService.getPage(pageNo, pageSize);
    }

}