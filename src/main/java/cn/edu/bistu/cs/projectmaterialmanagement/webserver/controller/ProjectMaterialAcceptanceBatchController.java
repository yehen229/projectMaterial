package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceBatch;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialAcceptanceBatchService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectmaterialacceptancebatch/v1")
public class ProjectMaterialAcceptanceBatchController {
    private final IProjectMaterialAcceptanceBatchService projectMaterialAcceptanceBatchService;

    ProjectMaterialAcceptanceBatchController(IProjectMaterialAcceptanceBatchService projectMaterialAcceptanceBatchService) {
        this.projectMaterialAcceptanceBatchService = projectMaterialAcceptanceBatchService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectMaterialAcceptanceBatch getById(@RequestParam(value = "id") String id) {
        return projectMaterialAcceptanceBatchService.getById(id);
    }


    @GetMapping(value = "get-by-user-id")
    public List<ProjectMaterialAcceptanceBatch> getByUserId(@RequestParam(value = "userId") String userId) {
        return projectMaterialAcceptanceBatchService.getByUserId(userId);
    }


    @GetMapping(value = "get-by-project-id")
    public List<ProjectMaterialAcceptanceBatch> getByProjectId(@RequestParam(value = "projectId") String projectId) {
        return projectMaterialAcceptanceBatchService.getByProjectId(projectId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch) {
        return projectMaterialAcceptanceBatchService.add(projectMaterialAcceptanceBatch);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch) {
        return projectMaterialAcceptanceBatchService.delete(projectMaterialAcceptanceBatch);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch) {
        return projectMaterialAcceptanceBatchService.update(projectMaterialAcceptanceBatch);
    }


    @GetMapping(value = "page")
    public Page<ProjectMaterialAcceptanceBatch> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectMaterialAcceptanceBatchService.getPage(pageNo, pageSize);
    }

}