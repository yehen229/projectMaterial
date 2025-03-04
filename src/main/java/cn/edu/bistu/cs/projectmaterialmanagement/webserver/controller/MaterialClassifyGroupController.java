package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifyGroup;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifyTree;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialClassifyGroupService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("materialclassifygroup/v1")
@EnableMethodSecurity
public class MaterialClassifyGroupController {
    private final IMaterialClassifyGroupService materialClassifyGroupService;

    MaterialClassifyGroupController(IMaterialClassifyGroupService materialClassifyGroupService) {
        this.materialClassifyGroupService = materialClassifyGroupService;
    }

    @GetMapping(value = "get-by-id")
    public MaterialClassifyGroup getById(@RequestParam(value = "id") String id) {
        return materialClassifyGroupService.getById(id);
    }


    @GetMapping(value = "get-by-material-classify-division-id")
    public List<MaterialClassifyGroup> getByMaterialClassifyDivisionId(@RequestParam(value = "materialClassifyDivisionId") String materialClassifyDivisionId) {
        return materialClassifyGroupService.getByMaterialClassifyDivisionId(materialClassifyDivisionId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody MaterialClassifyGroup materialClassifyGroup) {
        return materialClassifyGroupService.add(materialClassifyGroup);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody MaterialClassifyGroup materialClassifyGroup) {
        return materialClassifyGroupService.delete(materialClassifyGroup);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody MaterialClassifyGroup materialClassifyGroup) {
        return materialClassifyGroupService.update(materialClassifyGroup);
    }


    @GetMapping(value = "page")
    public Page<MaterialClassifyGroup> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                               @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return materialClassifyGroupService.getPage(pageNo, pageSize);
    }

    @GetMapping(value = "get-tree")
    public MaterialClassifyTree getTree() {
        return materialClassifyGroupService.getTree();
    }

}