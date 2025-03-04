package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialBrandSelect;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IUseMaterialBrandSelectService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usematerialbrandselect/v1")
public class UseMaterialBrandSelectController {
    private final IUseMaterialBrandSelectService useMaterialBrandSelectService;

    UseMaterialBrandSelectController(IUseMaterialBrandSelectService useMaterialBrandSelectService) {
        this.useMaterialBrandSelectService = useMaterialBrandSelectService;
    }

    @GetMapping(value = "get-by-id")
    public UseMaterialBrandSelect getById(@RequestParam(value = "id") String id) {
        return useMaterialBrandSelectService.getById(id);
    }


    @GetMapping(value = "get-by-user-id")
    public List<UseMaterialBrandSelect> getByUserId(@RequestParam(value = "userId") String userId) {
        return useMaterialBrandSelectService.getByUserId(userId);
    }


    @GetMapping(value = "get-by-project-id")
    public List<UseMaterialBrandSelect> getByProjectId(@RequestParam(value = "projectId") String projectId) {
        return useMaterialBrandSelectService.getByProjectId(projectId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody UseMaterialBrandSelect useMaterialBrandSelect) {
        return useMaterialBrandSelectService.add(useMaterialBrandSelect);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody UseMaterialBrandSelect useMaterialBrandSelect) {
        return useMaterialBrandSelectService.delete(useMaterialBrandSelect);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody UseMaterialBrandSelect useMaterialBrandSelect) {
        return useMaterialBrandSelectService.update(useMaterialBrandSelect);
    }


    @GetMapping(value = "page")
    public Page<UseMaterialBrandSelect> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return useMaterialBrandSelectService.getPage(pageNo, pageSize);
    }

}