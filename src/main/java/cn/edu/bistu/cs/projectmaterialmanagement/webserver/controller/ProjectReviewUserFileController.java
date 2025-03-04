package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.DownloadFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReviewUserFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectReviewUserFileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectreviewuserfile/v1")
@EnableMethodSecurity
public class ProjectReviewUserFileController {
    private final IProjectReviewUserFileService projectReviewUserFileService;

    ProjectReviewUserFileController(IProjectReviewUserFileService projectReviewUserFileService) {
        this.projectReviewUserFileService = projectReviewUserFileService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectReviewUserFile getById(@RequestParam(value = "id") String id) {
        return projectReviewUserFileService.getById(id);
    }


    @GetMapping(value = "get-by-project-review-user-id")
    public List<ProjectReviewUserFile> getByProjectReviewUserId(@RequestParam(value = "projectReviewUserId") String projectReviewUserId) {
        return projectReviewUserFileService.getByProjectReviewUserId(projectReviewUserId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectReviewUserFile projectReviewUserFile) {
        return projectReviewUserFileService.add(projectReviewUserFile);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectReviewUserFile projectReviewUserFile) {
        return projectReviewUserFileService.delete(projectReviewUserFile);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectReviewUserFile projectReviewUserFile) {
        return projectReviewUserFileService.update(projectReviewUserFile);
    }


    @GetMapping(value = "page")
    public Page<ProjectReviewUserFile> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                               @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectReviewUserFileService.getPage(pageNo, pageSize);
    }

    @GetMapping(value = "download-project-review-file-by-id")
    @PreAuthorize("hasAnyRole('Admin') or @ProjectPermission.isInProject(#projectId) ")
    public DownloadFile downloadProjectReviewUserFileById(
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "projectReviewUserFileId") String projectReviewUserFileId,
            HttpServletRequest request,
            HttpServletResponse response) {
        projectReviewUserFileService.downloadFileById(projectReviewUserFileId, request, response);

        return new DownloadFile();
    }

}