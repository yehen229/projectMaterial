package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialBrand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialBrandService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("materialbrand/v1")
@EnableMethodSecurity
public class MaterialBrandController {
    private final IMaterialBrandService materialBrandService;

    MaterialBrandController(IMaterialBrandService materialBrandService) {
        this.materialBrandService = materialBrandService;
    }

    @GetMapping(value = "get-by-id")
    public MaterialBrand getById(@RequestParam(value = "id") String id) {
        return materialBrandService.getById(id);
    }


    @GetMapping(value = "get-by-material-id")
    public List<MaterialBrand> getByMaterialId(@RequestParam(value = "materialId") String materialId) {
        return materialBrandService.getByMaterialId(materialId);
    }


    @GetMapping(value = "get-by-brand-id")
    public List<MaterialBrand> getByBrandId(@RequestParam(value = "brandId") String brandId) {
        return materialBrandService.getByBrandId(brandId);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody MaterialBrand materialBrand) {
        return materialBrandService.add(materialBrand);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody MaterialBrand materialBrand) {
        return materialBrandService.delete(materialBrand);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody MaterialBrand materialBrand) {
        return materialBrandService.update(materialBrand);
    }


    @GetMapping(value = "page")
    public Page<MaterialBrand> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return materialBrandService.getPage(pageNo, pageSize);
    }
    @PostMapping("/import")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> importData(@RequestParam("file") MultipartFile file) {
        try {
            materialBrandService.importExcel(file);
            return ResponseEntity.ok("导入成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("导入失败：" + e.getMessage());
        }
    }

}