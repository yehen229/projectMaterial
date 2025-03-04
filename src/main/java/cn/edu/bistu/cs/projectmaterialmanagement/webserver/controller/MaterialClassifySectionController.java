package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifySection;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifySectionView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifyTree;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialClassifySectionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("materialclassifysection/v1")
@EnableMethodSecurity
public class MaterialClassifySectionController {
    private final IMaterialClassifySectionService materialClassifySectionService;

    MaterialClassifySectionController(IMaterialClassifySectionService materialClassifySectionService) {
        this.materialClassifySectionService = materialClassifySectionService;
    }

    @GetMapping(value = "get-by-id")
    public MaterialClassifySection getById(@RequestParam(value = "id") String id) {
        return materialClassifySectionService.getById(id);
    }

    @GetMapping(value = "get-view-by-id")
    public MaterialClassifySectionView getViewById(@RequestParam(value = "id") String id) {
        return materialClassifySectionService.getViewById(id);
    }



    @GetMapping(value = "get-by-material-classify-group-id")
    public List<MaterialClassifySection> getByMaterialClassifyGroupId(@RequestParam(value = "materialClassifyGroupId") String materialClassifyGroupId) {
        return materialClassifySectionService.getByMaterialClassifyGroupId(materialClassifyGroupId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody MaterialClassifySection materialClassifySection) {
        return materialClassifySectionService.add(materialClassifySection);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody MaterialClassifySection materialClassifySection) {
        return materialClassifySectionService.delete(materialClassifySection);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody MaterialClassifySection materialClassifySection) {
        return materialClassifySectionService.update(materialClassifySection);
    }


    @GetMapping(value = "page")
    public Page<MaterialClassifySection> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                 @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return materialClassifySectionService.getPage(pageNo, pageSize);
    }

    @GetMapping(value = "get-tree")
    public MaterialClassifyTree getTree() {
        return materialClassifySectionService.getTree();
    }

}