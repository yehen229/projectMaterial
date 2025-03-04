package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectFileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectfile/v1")
@EnableMethodSecurity
public class ProjectFileController {
    private final IProjectFileService projectFileService;

    ProjectFileController(IProjectFileService projectFileService) {
        this.projectFileService = projectFileService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectFile getById(@RequestParam(value = "id") String id) {
        return projectFileService.getById(id);
    }


    @GetMapping(value = "get-by-project-id")
    public List<ProjectFile> getByProjectId(@RequestParam(value = "projectId") String projectId) {
        return projectFileService.getByProjectId(projectId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectFile projectFile) {
        return projectFileService.add(projectFile);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectFile projectFile) {
        return projectFileService.delete(projectFile);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectFile projectFile) {
        return projectFileService.update(projectFile);
    }


    @GetMapping(value = "page")
    public Page<ProjectFile> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectFileService.getPage(pageNo, pageSize);
    }

}