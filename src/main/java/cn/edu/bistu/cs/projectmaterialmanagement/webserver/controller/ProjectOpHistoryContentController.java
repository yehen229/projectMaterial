package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectOpHistoryContent;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectOpHistoryContentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectophistorycontent/v1")
public class ProjectOpHistoryContentController {
    private final IProjectOpHistoryContentService projectOpHistoryContentService;

    ProjectOpHistoryContentController(IProjectOpHistoryContentService projectOpHistoryContentService) {
        this.projectOpHistoryContentService = projectOpHistoryContentService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectOpHistoryContent getById(@RequestParam(value = "id") String id) {
        return projectOpHistoryContentService.getById(id);
    }


    @GetMapping(value = "get-by-project-op-history-id")
    public List<ProjectOpHistoryContent> getByProjectOpHistoryId(@RequestParam(value = "projectOpHistoryId") String projectOpHistoryId) {
        return projectOpHistoryContentService.getByProjectOpHistoryId(projectOpHistoryId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectOpHistoryContent projectOpHistoryContent) {
        return projectOpHistoryContentService.add(projectOpHistoryContent);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectOpHistoryContent projectOpHistoryContent) {
        return projectOpHistoryContentService.delete(projectOpHistoryContent);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectOpHistoryContent projectOpHistoryContent) {
        return projectOpHistoryContentService.update(projectOpHistoryContent);
    }


    @GetMapping(value = "page")
    public Page<ProjectOpHistoryContent> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectOpHistoryContentService.getPage(pageNo, pageSize);
    }

}