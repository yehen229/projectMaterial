package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialPhoto;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialPhotoView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IMaterialPhotoService {
    String add(MaterialPhoto materialPhoto);

    int delete(MaterialPhoto materialPhoto);

    int update(MaterialPhoto materialPhoto);

    int deleteById(String id);

    int deleteByMaterialId(String materialId);

    int getCount();

    int getCountByMaterialId(String materialId);

    MaterialPhoto getById(String id);

    List<MaterialPhoto> getByMaterialId(String materialId);

    List<MaterialPhotoView> getViewByMaterialId(String materialId);

    Page<MaterialPhoto> getPage(int pageNo, int pageSize);

    Page<MaterialPhoto> getPageByMaterialId(String materialId, int pageNo, int pageSize);

    Page<MaterialPhotoView> getPageView(int pageNo, int pageSize);

    Page<MaterialPhotoView> getPageViewByMaterialId(String materialId, int pageNo, int pageSize);

    String addMaterialPhotoTempFile(String materialId, MultipartFile multipartFile);

    boolean deleteMaterialPhotoTempFile(String uploadPhotoFilesDir, String fileName);

    String downloadPhotoFile(String materialPhotoId, HttpServletRequest request, HttpServletResponse response) throws IOException;


}