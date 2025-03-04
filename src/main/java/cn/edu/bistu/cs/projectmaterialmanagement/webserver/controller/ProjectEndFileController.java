package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEndFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.end.IProjectEndFileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectendfile/v1")
@EnableMethodSecurity
public class ProjectEndFileController {
    private final IProjectEndFileService projectEndFileService;

    ProjectEndFileController(IProjectEndFileService projectEndFileService) {
        this.projectEndFileService = projectEndFileService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectEndFile getById(@RequestParam(value = "id") String id) {
        return projectEndFileService.getById(id);
    }


    @GetMapping(value = "get-by-project-end-id")
    public List<ProjectEndFile> getByProjectEndId(@RequestParam(value = "projectEndId") String projectEndId) {
        return projectEndFileService.getByProjectEndId(projectEndId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectEndFile projectEndFile) {
        return projectEndFileService.add(projectEndFile);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectEndFile projectEndFile) {
        return projectEndFileService.delete(projectEndFile);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectEndFile projectEndFile) {
        return projectEndFileService.update(projectEndFile);
    }


    @GetMapping(value = "page")
    public Page<ProjectEndFile> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectEndFileService.getPage(pageNo, pageSize);
    }

}