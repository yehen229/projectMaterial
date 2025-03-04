package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Material;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialBusinessService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("material/v1")
@EnableMethodSecurity
public class MaterialController {
    private final IMaterialService materialService;
    private final IMaterialBusinessService materialBusinessService;

    MaterialController(IMaterialService materialService, IMaterialBusinessService materialBusinessService) {
        this.materialService = materialService;
        this.materialBusinessService = materialBusinessService;
    }

    @GetMapping(value = "get-by-id")
    public Material getById(@RequestParam(value = "id") String id) {
        return materialService.getById(id);
    }

    @GetMapping(value = "get-view-by-id")
    public MaterialView getViewByMaterialId(@RequestParam(value = "id") String id) {
        return materialBusinessService.getViewByMaterialId(id);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody Material material) {
        return materialService.add(material);
    }

    @PostMapping(value = "add-form")
    @PreAuthorize("hasRole('Admin')")
    public String addForm(@RequestBody MaterialForm materialForm) {
        return materialBusinessService.addForm(materialForm);
    }


    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody Material material) {
        return materialBusinessService.delete(material);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody Material material) {
        return materialService.update(material);
    }

    @PostMapping(value = "update-form")
    @PreAuthorize("hasRole('Admin')")
    public int updateForm(@RequestBody MaterialForm materialForm) {
        return materialBusinessService.updateForm(materialForm);
    }

    @GetMapping(value = "list-view-by-material-classify-section-id")
    public List<MaterialView> getListViewByMaterialClassifySectionId(@RequestParam(value = "materialClassifySectionId") String materialClassifySectionId) {

        return materialBusinessService.getListViewByMaterialClassifySectionId(materialClassifySectionId);
    }

    @GetMapping(value = "page")
    public Page<Material> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return materialService.getPage(pageNo, pageSize);
    }

    @GetMapping(value = "page-by-default-bind-type")
    public Page<Material> getPageByProjectBindType(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return materialService.getPageByProjectBindType(0,pageNo, pageSize);
    }

    @GetMapping(value = "page-view")
    public Page<MaterialView> getPageView(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return materialBusinessService.getPageViewByProjectBindType(0,pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-default-bind-type")
    public Page<MaterialView> getPageViewByProjectBindType(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return materialBusinessService.getPageViewByProjectBindType(0,pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-name")
    public Page<MaterialView> getPageViewByName(@RequestParam(value = "name") String name,
                                                @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return materialBusinessService.getPageViewByName(name, pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-location")
    public Page<MaterialView> getPageViewByLocation(@RequestParam(value = "location") String location,
                                                    @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return materialBusinessService.getPageViewByLocation(location, pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-item-mark")
    public Page<MaterialView> getPageViewByItemMark(@RequestParam(value = "itemMark") String itemMark,
                                                    @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return materialBusinessService.getPageViewByItemMark(itemMark, pageNo, pageSize);
    }

    @GetMapping(value = "page-view-by-classify-section-id")
    public Page<MaterialView> getPageViewByClassifySectionId(@RequestParam(value = "classifySectionId") String classifySectionId,
                                                             @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return materialBusinessService.getPageByMaterialClassifySectionId(classifySectionId, pageNo, pageSize);
    }

}