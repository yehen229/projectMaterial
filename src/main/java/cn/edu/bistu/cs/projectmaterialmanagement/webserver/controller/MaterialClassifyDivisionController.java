package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifyDivision;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialClassifyDivisionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("materialclassifydivision/v1")
@EnableMethodSecurity
public class MaterialClassifyDivisionController {
    private final IMaterialClassifyDivisionService materialClassifyDivisionService;

    MaterialClassifyDivisionController(IMaterialClassifyDivisionService materialClassifyDivisionService) {
        this.materialClassifyDivisionService = materialClassifyDivisionService;
    }

    @GetMapping(value = "get-by-id")
    public MaterialClassifyDivision getById(@RequestParam(value = "id") String id) {
        return materialClassifyDivisionService.getById(id);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody MaterialClassifyDivision materialClassifyDivision) {
        return materialClassifyDivisionService.add(materialClassifyDivision);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody MaterialClassifyDivision materialClassifyDivision) {
        return materialClassifyDivisionService.delete(materialClassifyDivision);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody MaterialClassifyDivision materialClassifyDivision) {
        return materialClassifyDivisionService.update(materialClassifyDivision);
    }

    @GetMapping(value = "list")
    public List<MaterialClassifyDivision> getAllList() {
        return materialClassifyDivisionService.getAllList();
    }


    @GetMapping(value = "page")
    public Page<MaterialClassifyDivision> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return materialClassifyDivisionService.getPage(pageNo, pageSize);
    }


}