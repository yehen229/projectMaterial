package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.ProjectReviewUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.materialreview.IProjectReviewUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectreviewuser/v1")
@EnableMethodSecurity
public class ProjectReviewUserController {
    private final IProjectReviewUserService projectReviewUserService;


    ProjectReviewUserController(IProjectReviewUserService projectReviewUserService) {
        this.projectReviewUserService = projectReviewUserService;

    }

    @GetMapping(value = "get-by-id")
    public ProjectReviewUser getById(@RequestParam(value = "id") String id) {
        return projectReviewUserService.getById(id);
    }


    @GetMapping(value = "get-by-user-id")
    public List<ProjectReviewUser> getByUserId(@RequestParam(value = "userId") String userId) {
        return projectReviewUserService.getByUserId(userId);
    }


    @GetMapping(value = "get-by-project-review-id")
    public List<ProjectReviewUser> getByProjectReviewId(@RequestParam(value = "projectReviewId") String projectReviewId) {
        return projectReviewUserService.getByProjectReviewId(projectReviewId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectReviewUser projectReviewUser) {
        return projectReviewUserService.add(projectReviewUser);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectReviewUser projectReviewUser) {
        return projectReviewUserService.delete(projectReviewUser);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectReviewUser projectReviewUser) {
        return projectReviewUserService.update(projectReviewUser);
    }


    @GetMapping(value = "page")
    public Page<ProjectReviewUser> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                           @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectReviewUserService.getPage(pageNo, pageSize);
    }


}