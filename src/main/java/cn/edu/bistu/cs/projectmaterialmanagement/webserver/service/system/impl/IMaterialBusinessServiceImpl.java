package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.FileUtils;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class IMaterialBusinessServiceImpl implements IMaterialBusinessService {

    private final IMaterialService materialService;
    private final IMaterialPhotoService materialPhotoService;
    private final IMaterialBrandService materialBrandService;
    private final IMaterialClassifySectionService materialClassifySectionService;

    public IMaterialBusinessServiceImpl(IMaterialService materialService,
                                        IMaterialPhotoService materialPhotoService,
                                        IMaterialBrandService materialBrandService,
                                        IMaterialClassifySectionService materialClassifySectionService) {
        this.materialService = materialService;
        this.materialPhotoService = materialPhotoService;
        this.materialBrandService = materialBrandService;
        this.materialClassifySectionService = materialClassifySectionService;
    }

    @Override
    public String addForm(MaterialForm materialForm) {
        if (materialForm == null)
            throw new BusinessException("参数为空");

        Material material = materialForm.getMaterial();
        if (material == null)
            throw new BusinessException("参数为空");
        String[] brandIds = materialForm.getBrandIds();
        if (brandIds == null)
            throw new BusinessException("参数为空");

        String materialId = materialService.add(material);
        if (materialId == null)
            throw new BusinessException("添加失败");

        //添加品牌
        for (String brandId : brandIds) {
            MaterialBrand materialBrand = new MaterialBrand();
            materialBrand.setBrandId(brandId);
            materialBrand.setMaterialId(materialId);
            materialBrandService.add(materialBrand);
        }

        //添加图片
        String photoTempDir = materialForm.getPhotoTempDir();
        if (photoTempDir != null) {
            String pathTemp = FileUtils.getFilePath(photoTempDir, true);
            String pathDest = FileUtils.getFilePath("", false);
            List<String> listPath = FileUtils.listFiles(pathTemp);
            if (listPath != null && !listPath.isEmpty()) {
                for (String fileName : listPath) {
                    String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));
                    String newFileNamePath = pathDest + newFileName;
                    if (FileUtils.MoveFile(pathTemp + fileName, newFileNamePath)) {
                        MaterialPhoto materialPhoto = new MaterialPhoto();
                        materialPhoto.setMaterialId(materialId);
                        materialPhoto.setFilePath(newFileName);
                        materialPhotoService.add(materialPhoto);
                    }
                }
            }
        }

        return materialId;


    }

    @Override
    public int updateForm(MaterialForm materialForm) {
        if (materialForm == null)
            throw new BusinessException("参数为空");

        Material material = materialForm.getMaterial();
        if (material == null)
            throw new BusinessException("参数为空");
        String[] brandIds = materialForm.getBrandIds();
        if (brandIds == null)
            throw new BusinessException("参数为空");

        String materialId = materialForm.getMaterial().getId();
        if (materialId == null)
            throw new BusinessException("参数为空");

        materialService.update(material);

        //修改品牌
        materialBrandService.deleteByMaterialId(materialId);
        for (String brandId : brandIds) {
            MaterialBrand materialBrand = new MaterialBrand();
            materialBrand.setBrandId(brandId);
            materialBrand.setMaterialId(materialId);
            materialBrandService.add(materialBrand);
        }

        //删除图片
        List<MaterialPhoto> materialPhotoList = materialPhotoService.getByMaterialId(materialId);
        List<String> photoIdList = Arrays.asList(materialForm.getPhotoIds());
        if (materialPhotoList != null)
            materialPhotoList.forEach(materialPhoto ->
            {
                if (!photoIdList.contains(materialPhoto.getId()))
                    materialPhotoService.deleteById(materialPhoto.getId());
            });

        //添加图片
        String photoTempDir = materialForm.getPhotoTempDir();
        if (photoTempDir != null) {
            String pathTemp = FileUtils.getFilePath(photoTempDir, true);
            String pathDest = FileUtils.getFilePath("", false);
            List<String> listPath = FileUtils.listFiles(pathTemp);
            if (listPath != null && !listPath.isEmpty()) {
                for (String fileName : listPath) {
                    String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));
                    String newFileNamePath = pathDest + newFileName;
                    if (FileUtils.MoveFile(pathTemp + fileName, newFileNamePath)) {
                        MaterialPhoto materialPhoto = new MaterialPhoto();
                        materialPhoto.setMaterialId(materialId);
                        materialPhoto.setFilePath(newFileName);
                        materialPhotoService.add(materialPhoto);
                    }
                }
            }
        }

        return 1;

    }

    @Override
    public MaterialView getViewByMaterialId(String id) {
        Material material = materialService.getById(id);
        return convert(material);
    }

    @Override
    public List<MaterialView> getListViewByMaterialClassifySectionId(String materialClassifySectionId) {
        List<Material> materialList = materialService.getByMaterialClassifySectionId(materialClassifySectionId);
        return convert(materialList);
    }

    @Override
    public Page<MaterialView> getPageView(Integer pageNo, Integer pageSize) {
        Page<Material> page = materialService.getPage(pageNo, pageSize);
        return convert(page, pageNo, pageSize);
    }

    @Override
    public Page<MaterialView> getPageViewByName(String name, Integer pageNo, Integer pageSize) {
        Page<Material> page = materialService.getPageByName(name, pageNo, pageSize);
        return convert(page, pageNo, pageSize);
    }

    @Override
    public Page<MaterialView> getPageViewByLocation(String location, Integer pageNo, Integer pageSize) {
        Page<Material> page = materialService.getPageByLocation(location, pageNo, pageSize);
        return convert(page, pageNo, pageSize);
    }

    @Override
    public Page<MaterialView> getPageViewByItemMark(String itemMark, Integer pageNo, Integer pageSize) {
        Page<Material> page = materialService.getPageByItemMark(itemMark, pageNo, pageSize);
        return convert(page, pageNo, pageSize);
    }

    @Override
    public Page<MaterialView> getPageByMaterialClassifySectionId(String classifySectionId, Integer pageNo, Integer pageSize) {
        Page<Material> page = materialService.getPageByMaterialClassifySectionId(classifySectionId, pageNo, pageSize);
        return convert(page, pageNo, pageSize);
    }

    @Override
    public Page<MaterialView> getPageViewByProjectBindType(int projectBindType,
                                                           Integer pageNo,
                                                           Integer pageSize) {
        Page<Material> page = materialService.getPageByProjectBindType(projectBindType, pageNo, pageSize);
        return convert(page, pageNo, pageSize);
    }

    @Override
    public Page<MaterialView> getPageViewByNameAndProjectBindType(String name,
                                                                  int projectBindType,
                                                                  Integer pageNo,
                                                                  Integer pageSize) {
        Page<Material> page = materialService.getPageByNameAndProjectBindType(name, projectBindType,pageNo, pageSize);
        return convert(page, pageNo, pageSize);
    }

    @Override
    public Page<MaterialView> getPageViewByLocationAndProjectBindType(String location,
                                                                      int projectBindType,
                                                                      Integer pageNo,
                                                                      Integer pageSize) {
        Page<Material> page = materialService.getPageByLocationAndProjectBindType(location, projectBindType,pageNo, pageSize);
        return convert(page, pageNo, pageSize);
    }

    @Override
    public Page<MaterialView> getPageViewByItemMarkAndProjectBindType(String itemMark,
                                                                      int projectBindType,
                                                                      Integer pageNo,
                                                                      Integer pageSize) {
        Page<Material> page = materialService.getPageByItemMarkAndProjectBindType(itemMark, projectBindType,pageNo, pageSize);
        return convert(page, pageNo, pageSize);
    }

    @Override
    public Page<MaterialView> getPageByMaterialClassifySectionIdAndProjectBindType(String classifySectionId,
                                                                                   int projectBindType,
                                                                                   Integer pageNo,
                                                                                   Integer pageSize) {
        return null;
    }

    @Override
    public int delete(Material material) {
        if (material == null || material.getId() == null) return 0;
        materialPhotoService.deleteByMaterialId(material.getId());
        materialBrandService.deleteByMaterialId(material.getId());
        return materialService.delete(material);
    }

    private MaterialView convert(Material material) {
        if (material == null) return null;
        MaterialView materialView = new MaterialView();
        materialView.setMaterial(material);
        materialView.setMaterialClassifySectionView(materialClassifySectionService.getViewById(material.getMaterialClassifySectionId()));
        materialView.setMaterialBrandViewList(materialBrandService.getViewByMaterialId(material.getId()));
        materialView.setMaterialPhotoViewList(materialPhotoService.getViewByMaterialId(material.getId()));
        return materialView;
    }

    private Page<MaterialView> convert(Page<Material> materialPage, Integer pageNo, Integer pageSize) {
        if (materialPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<MaterialView> list = new ArrayList<>();
        for (Material material : materialPage.getResult()) {
            MaterialView materialView = convert(material);
            if (materialView != null)
                list.add(materialView);

        }

        return new Page<>(startIndex, materialPage.getTotalCount(), pageSize, list);
    }

    private List<MaterialView> convert(List<Material> materialList) {
        if (materialList == null) return null;

        List<MaterialView> list = new ArrayList<>();
        for (Material material : materialList) {
            MaterialView materialView = convert(material);
            if (materialView != null)
                list.add(materialView);

        }

        return list;
    }
}
