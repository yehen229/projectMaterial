package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialNewBrand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IUseMaterialNewBrandService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usematerialnewbrand/v1")
public class UseMaterialNewBrandController {
    private final IUseMaterialNewBrandService useMaterialNewBrandService;

    UseMaterialNewBrandController(IUseMaterialNewBrandService useMaterialNewBrandService) {
        this.useMaterialNewBrandService = useMaterialNewBrandService;
    }

    @GetMapping(value = "get-by-id")
    public UseMaterialNewBrand getById(@RequestParam(value = "id") String id) {
        return useMaterialNewBrandService.getById(id);
    }


    @GetMapping(value = "get-by-use-material-id")
    public List<UseMaterialNewBrand> getByUseMaterialId(@RequestParam(value = "useMaterialId") String useMaterialId) {
        return useMaterialNewBrandService.getByUseMaterialId(useMaterialId);
    }


    @GetMapping(value = "get-by-material-classify-division-id")
    public List<UseMaterialNewBrand> getByMaterialClassifyDivisionId(@RequestParam(value = "materialClassifyDivisionId") String materialClassifyDivisionId) {
        return useMaterialNewBrandService.getByMaterialClassifyDivisionId(materialClassifyDivisionId);
    }


    @GetMapping(value = "get-by-material-classify-group-id")
    public List<UseMaterialNewBrand> getByMaterialClassifyGroupId(@RequestParam(value = "materialClassifyGroupId") String materialClassifyGroupId) {
        return useMaterialNewBrandService.getByMaterialClassifyGroupId(materialClassifyGroupId);
    }


    @GetMapping(value = "get-by-material-classify-section-id")
    public List<UseMaterialNewBrand> getByMaterialClassifySectionId(@RequestParam(value = "materialClassifySectionId") String materialClassifySectionId) {
        return useMaterialNewBrandService.getByMaterialClassifySectionId(materialClassifySectionId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody UseMaterialNewBrand useMaterialNewBrand) {
        return useMaterialNewBrandService.add(useMaterialNewBrand);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody UseMaterialNewBrand useMaterialNewBrand) {
        return useMaterialNewBrandService.delete(useMaterialNewBrand);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody UseMaterialNewBrand useMaterialNewBrand) {
        return useMaterialNewBrandService.update(useMaterialNewBrand);
    }


    @GetMapping(value = "page")
    public Page<UseMaterialNewBrand> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return useMaterialNewBrandService.getPage(pageNo, pageSize);
    }

}