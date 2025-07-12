package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialBrand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialBrandView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IMaterialBrandRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IBrandService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialBrandService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialBrandServiceImpl implements IMaterialBrandService {

    private static final Logger log =
            LoggerFactory.getLogger(MaterialBrandServiceImpl.class);

    private final IMaterialBrandRepository materialBrandRepository;
    private final IBrandService brandService;
    private final IMaterialService materialService;

    public MaterialBrandServiceImpl(IMaterialBrandRepository materialBrandRepository, IBrandService brandService, IMaterialService materialService) {
        this.materialBrandRepository = materialBrandRepository;
        this.brandService = brandService;
        this.materialService = materialService;
    }

    /**
     * 增加
     */
    @Override
    public String add(MaterialBrand materialBrand) {
        return materialBrandRepository.add(materialBrand);
    }

    /**
     * 删除
     */
    @Override
    public int delete(MaterialBrand materialBrand) {
        return materialBrandRepository.delete(materialBrand);
    }

    /**
     * 更新
     */
    @Override
    public int update(MaterialBrand materialBrand) {
        return materialBrandRepository.update(materialBrand);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return materialBrandRepository.deleteById(id);
    }

    /**
     * 根据materialId删除记录
     *
     * @param materialId
     */
    @Override
    public int deleteByMaterialId(String materialId) {
        return materialBrandRepository.deleteByMaterialId(materialId);
    }

    /**
     * 根据brandId删除记录
     *
     * @param brandId
     */
    @Override
    public int deleteByBrandId(String brandId) {
        return materialBrandRepository.deleteByBrandId(brandId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return materialBrandRepository.getCount();
    }

    /**
     * 根据materialId得到数量
     *
     * @param materialId
     */
    @Override
    public int getCountByMaterialId(String materialId) {
        return materialBrandRepository.getCountByMaterialId(materialId);
    }

    /**
     * 根据brandId得到数量
     *
     * @param brandId
     */
    @Override
    public int getCountByBrandId(String brandId) {
        return materialBrandRepository.getCountByBrandId(brandId);
    }

    /**
     * 根据id得到MaterialBrand
     *
     * @param id
     */
    @Override
    public MaterialBrand getById(String id) {
        return materialBrandRepository.getById(id);
    }

    /**
     * 根据materialId得到MaterialBrand
     *
     * @param materialId
     */
    @Override
    public List<MaterialBrand> getByMaterialId(String materialId) {
        return materialBrandRepository.getByMaterialId(materialId);
    }

    /**
     * 根据brandId得到MaterialBrand
     *
     * @param brandId
     */
    @Override
    public List<MaterialBrand> getByBrandId(String brandId) {
        return materialBrandRepository.getByBrandId(brandId);
    }

    @Override
    public MaterialBrandView getViewById(String Id) {
        MaterialBrand materialBrand = materialBrandRepository.getById(Id);
        if (materialBrand == null) return null;
        MaterialBrandView materialBrandView = new MaterialBrandView();
        materialBrandView.setMaterialBrand(materialBrand);
        materialBrandView.setBrand(brandService.getById(materialBrand.getBrandId()));
        materialBrandView.setMaterial(materialService.getById(materialBrand.getMaterialId()));
        return materialBrandView;
    }

    @Override
    public List<MaterialBrandView> getViewByMaterialId(String materialId) {
        List<MaterialBrand> materialBrandList = materialBrandRepository.getByMaterialId(materialId);
        if (materialBrandList == null || materialBrandList.isEmpty()) return null;
        List<MaterialBrandView> materialBrandViewList = new ArrayList<>();
        for (MaterialBrand materialBrand : materialBrandList) {
            MaterialBrandView materialBrandView = new MaterialBrandView();
            materialBrandView.setMaterialBrand(materialBrand);
            materialBrandView.setBrand(brandService.getById(materialBrand.getBrandId()));
            materialBrandView.setMaterial(materialService.getById(materialBrand.getMaterialId()));
            materialBrandViewList.add(materialBrandView);
        }
        return materialBrandViewList;
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<MaterialBrand> getPage(int pageNo, int pageSize) {
        return materialBrandRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param materialId
     * @param pageNo     页号，从1开始
     * @param pageSize   每页的记录数
     */
    @Override
    public Page<MaterialBrand> getPageByMaterialId(String materialId, int pageNo, int pageSize) {
        return materialBrandRepository.getPageByMaterialId(materialId, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param brandId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<MaterialBrand> getPageByBrandId(String brandId, int pageNo, int pageSize) {
        return materialBrandRepository.getPageByBrandId(brandId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<MaterialBrandView> getPageView(int pageNo, int pageSize) {
        Page<MaterialBrand> materialBrandPage = getPage(pageNo, pageSize);
        return convertMaterialBrandPage2PageView(materialBrandPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param materialId
     * @param pageNo     页号，从1开始
     * @param pageSize   每页的记录数
     */
    @Override
    public Page<MaterialBrandView> getPageViewByMaterialId(String materialId, int pageNo, int pageSize) {
        Page<MaterialBrand> materialBrandPage = getPageByMaterialId(materialId, pageNo, pageSize);
        return convertMaterialBrandPage2PageView(materialBrandPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param brandId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<MaterialBrandView> getPageViewByBrandId(String brandId, int pageNo, int pageSize) {
        Page<MaterialBrand> materialBrandPage = getPageByBrandId(brandId, pageNo, pageSize);
        return convertMaterialBrandPage2PageView(materialBrandPage, pageNo, pageSize);
    }

    @Override
    public void importExcel(MultipartFile file) {}
//        // 1. 打开 Excel
//        Workbook wb = WorkbookFactory.create(file.getInputStream());
//        Sheet sheet = wb.getSheetAt(0);          // 读第一个 sheet
//        int lastRowNum = sheet.getLastRowNum();
//
//        // 2. 逐行解析
//        List<MaterialBrand> list = new ArrayList<>();
//        for (int i = 1; i <= lastRowNum; i++) {  // 跳过表头
//            Row row = sheet.getRow(i);
//            if (row == null) continue;           // 空行跳过
//
//            MaterialBrand brand = new MaterialBrand();
//            brand.setName(getCellStr(row, 0));   // 第 1 列：品牌名
//            brand.setCode(getCellStr(row, 1));   // 第 2 列：品牌编码
//            brand.setPosition(getCellStr(row, 2));// 第 3 列：定位
//            list.add(brand);
//        }
//        wb.close();
//
//        // 3. 批量入库
//        if (!list.isEmpty()) {
//            materialBrandMapper.insertBatch(list);
//        }
//    }
//    private String getCellStr(Row row, int col) {
//        Cell cell = row.getCell(col);
//        if (cell == null) return "";
//        cell.setCellType(CellType.STRING);
//        return cell.getStringCellValue().trim();
//    }
    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private MaterialBrandView getMaterialBrandViewByMaterialBrandId(String id) {
        MaterialBrand materialBrand = getById(id);
        if (materialBrand == null) return null;
        MaterialBrandView materialBrandView = new MaterialBrandView();
        return null;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param materialBrandPage 页面对象
     */
    private Page<MaterialBrandView> convertMaterialBrandPage2PageView(Page<MaterialBrand> materialBrandPage, int pageNo, int pageSize) {
        if (materialBrandPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<MaterialBrandView> list = new ArrayList<>();
        for (MaterialBrand materialBrand : materialBrandPage.getResult()) {
            MaterialBrandView materialBrandView = getMaterialBrandViewByMaterialBrandId(materialBrand.getId());
            if (materialBrandView != null) list.add(materialBrandView);
        }
        return new Page<>(startIndex, materialBrandPage.getTotalCount(), pageSize, list);
    }

}