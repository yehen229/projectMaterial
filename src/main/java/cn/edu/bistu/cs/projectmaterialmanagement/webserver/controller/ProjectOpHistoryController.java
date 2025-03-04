package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectOpHistory;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectOpHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("projectophistory/v1")
public class ProjectOpHistoryController {
    private final IProjectOpHistoryService projectOpHistoryService;


    ProjectOpHistoryController(IProjectOpHistoryService projectOpHistoryService) {
        this.projectOpHistoryService = projectOpHistoryService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectOpHistory getById(@RequestParam(value = "id") String id) {
        return projectOpHistoryService.getById(id);
    }


    @GetMapping(value = "get-by-user-id")
    public List<ProjectOpHistory> getByUserId(@RequestParam(value = "userId") String userId) {
        return projectOpHistoryService.getByUserId(userId);
    }


    @GetMapping(value = "get-by-project-id")
    public List<ProjectOpHistory> getByProjectId(@RequestParam(value = "projectId") String projectId) {
        return projectOpHistoryService.getByProjectId(projectId);
    }


}