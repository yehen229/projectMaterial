package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialPhoto;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialPhotoView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialPhotoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("materialphoto/v1")
@EnableMethodSecurity
public class MaterialPhotoController {
    private final IMaterialPhotoService materialPhotoService;

    MaterialPhotoController(IMaterialPhotoService materialPhotoService) {
        this.materialPhotoService = materialPhotoService;
    }

    @GetMapping(value = "get-by-id")
    public MaterialPhoto getById(@RequestParam(value = "id") String id) {
        return materialPhotoService.getById(id);
    }


    @GetMapping(value = "get-by-material-id")
    public List<MaterialPhoto> getByMaterialId(@RequestParam(value = "materialId") String materialId) {
        return materialPhotoService.getByMaterialId(materialId);
    }

    @GetMapping(value = "get-photo-view-list-by-material-id-and-photo-ids")
    public List<MaterialPhotoView> getMaterialPhotoViewListByMaterialIdAndPhotoIds(@RequestParam(value = "materialId") String materialId,
                                           @RequestParam(value = "photoIds") String[] photoIds) {
        return materialPhotoService.getMaterialPhotoViewListByMaterialIdAndPhotoIds(materialId, photoIds);
    }

    @PostMapping(value = "add")
    @PreAuthorize("hasRole('Admin')")
    public String add(@RequestBody MaterialPhoto materialPhoto) {
        return materialPhotoService.add(materialPhoto);
    }

    /**
     * 增加临时文件
     *
     * @param uploadPhotoFilesDir
     * @param multipartFile
     * @return
     */
    @PostMapping(value = "add-material-photo-temp-file")
    @PreAuthorize("hasAnyRole('Admin')")
    public String addMaterialPhotoTempFile(
            @RequestParam("uploadPhotoFilesDir") String uploadPhotoFilesDir,
            @RequestParam("file") MultipartFile multipartFile) {
        return materialPhotoService.addMaterialPhotoTempFile(uploadPhotoFilesDir, multipartFile);
    }

    /**
     * 删除临时文件
     *
     * @param uploadPhotoFilesDir
     * @param fileName
     * @return
     */
    @PostMapping(value = "delete-material-photo-temp-file")
    @PreAuthorize("hasAnyRole('Admin')")
    public boolean deleteMaterialPhotoTempFile(
            @RequestParam("uploadPhotoFilesDir") String uploadPhotoFilesDir,
            @RequestParam("fileName") String fileName) {
        return materialPhotoService.deleteMaterialPhotoTempFile(uploadPhotoFilesDir, fileName);
    }

    /**
     * 增加临时文件
     *
     * @param uploadPhotoFilesDir
     * @param multipartFile
     * @return
     */
    @PostMapping(value = "add-project-material-photo-temp-file")
    @PreAuthorize("hasAnyRole('Admin') or  @ProjectPermission.isInProject(#projectId)")
    public String addProjectMaterialPhotoTempFile(
            @RequestParam(value = "projectId") String projectId,
            @RequestParam("uploadPhotoFilesDir") String uploadPhotoFilesDir,
            @RequestParam("file") MultipartFile multipartFile) {
        return materialPhotoService.addMaterialPhotoTempFile(uploadPhotoFilesDir, multipartFile);
    }

    /**
     * 删除临时文件
     *
     * @param uploadPhotoFilesDir
     * @param fileName
     * @return
     */
    @PostMapping(value = "delete-project-material-photo-temp-file")
    @PreAuthorize("hasAnyRole('Admin') or  @ProjectPermission.isInProject(#projectId)")
    public boolean deleteProjectMaterialPhotoTempFile(
            @RequestParam(value = "projectId") String projectId,
            @RequestParam("uploadPhotoFilesDir") String uploadPhotoFilesDir,
            @RequestParam("fileName") String fileName) {
        return materialPhotoService.deleteMaterialPhotoTempFile(uploadPhotoFilesDir, fileName);
    }

    @PostMapping(value = "delete")
    @PreAuthorize("hasRole('Admin')")
    public int delete(@RequestBody MaterialPhoto materialPhoto) {
        return materialPhotoService.delete(materialPhoto);
    }

    @PostMapping(value = "update")
    @PreAuthorize("hasRole('Admin')")
    public int update(@RequestBody MaterialPhoto materialPhoto) {
        return materialPhotoService.update(materialPhoto);
    }

    @GetMapping(value = "download-photo-file-in-base64")
    public String downloadPhotoFile(
            @RequestParam(value = "materialPhotoId") String materialPhotoId,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {
        return materialPhotoService.downloadPhotoFile(materialPhotoId, request, response);
    }


    @GetMapping(value = "page")
    public Page<MaterialPhoto> getPage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        return materialPhotoService.getPage(pageNo, pageSize);
    }

}