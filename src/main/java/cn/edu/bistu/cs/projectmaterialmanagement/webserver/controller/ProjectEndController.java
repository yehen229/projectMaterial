package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEnd;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectEndService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projectend/v1")
@EnableMethodSecurity
public class ProjectEndController {
    private final IProjectEndService projectEndService;

    ProjectEndController(IProjectEndService projectEndService) {
        this.projectEndService = projectEndService;
    }

    @GetMapping(value = "get-by-id")
    public ProjectEnd getById(@RequestParam(value = "id") String id) {
        return projectEndService.getById(id);
    }


    @GetMapping(value = "get-by-project-id")
    public List<ProjectEnd> getByProjecctId(@RequestParam(value = "projeccId") String projecctId) {
        return projectEndService.getByProjectId(projecctId);
    }


    @GetMapping(value = "get-by-user-id")
    public List<ProjectEnd> getByUserId(@RequestParam(value = "userId") String userId) {
        return projectEndService.getByUserId(userId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody ProjectEnd projectEnd) {
        return projectEndService.add(projectEnd);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody ProjectEnd projectEnd) {
        return projectEndService.delete(projectEnd);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody ProjectEnd projectEnd) {
        return projectEndService.update(projectEnd);
    }


    @GetMapping(value = "page")
    public Page<ProjectEnd> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return projectEndService.getPage(pageNo, pageSize);
    }

}