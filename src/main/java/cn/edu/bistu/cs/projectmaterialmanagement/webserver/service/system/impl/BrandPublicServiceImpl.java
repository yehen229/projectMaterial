package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IBrandPublicRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.ICompanyRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IBrandPublicService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IBrandService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class BrandPublicServiceImpl implements IBrandPublicService {

    private static final Logger log =
            LoggerFactory.getLogger(BrandPublicServiceImpl.class);
    private final IBrandService brandService;
    private final IBrandPublicRepository brandPublicRepository;
    private final ICompanyRepository companyRepository;

    public BrandPublicServiceImpl(IBrandService brandService, IBrandPublicRepository brandPublicRepository, ICompanyRepository companyRepository) {
        this.brandService = brandService;
        this.brandPublicRepository = brandPublicRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public String add(Brand brand) {
        if (brand == null)
            throw new BusinessException("参数不能为空");


        String brandId = brandService.add(brand);
        if (brandId == null)
            throw new BusinessException("添加失败");

        BrandPublic brandPublic = new BrandPublic();
        brandPublic.setBrandId(brandId);


        return add(brandPublic);
    }

    /**
     * 增加
     */
    @Override
    public String add(BrandPublic brandPublic) {
        return brandPublicRepository.add(brandPublic);
    }

    @Override
    public String MId(String classname) {
        return brandPublicRepository.findIdByClassName(classname);
    }

    /**
     * 删除
     */
    @Override
    public int delete(BrandPublic brandPublic) {
        return brandPublicRepository.delete(brandPublic);
    }

    /**
     * 更新
     */
    @Override
    public int update(BrandPublic brandPublic) {
        return brandPublicRepository.update(brandPublic);
    }

    @Override
    public int update(Brand brand) {
        return brandService.update(brand);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return brandPublicRepository.deleteById(id);
    }

    /**
     * 根据brandId删除记录
     *
     * @param brandId
     */
    @Override
    public int deleteByBrandId(String brandId) {
        return brandPublicRepository.deleteByBrandId(brandId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return brandPublicRepository.getCount();
    }

    /**
     * 根据brandId得到数量
     *
     * @param brandId
     */
    @Override
    public int getCountByBrandId(String brandId) {
        return brandPublicRepository.getCountByBrandId(brandId);
    }

    /**
     * 根据id得到BrandPublic
     *
     * @param id
     */
    @Override
    public BrandPublic getById(String id) {
        return brandPublicRepository.getById(id);
    }

    @Override
    public BrandPublicView getViewById(String id) {
        return getBrandPublicViewByBrandPublicId(id);
    }

    /**
     * 根据brandId得到BrandPublic
     *
     * @param brandId
     */
    @Override
    public List<BrandPublic> getByBrandId(String brandId) {
        return brandPublicRepository.getByBrandId(brandId);
    }

    @Override
    public List<BrandPublicView> getByMaterialClassifySectionId(String materialClassifySectionId) {
        List<BrandPublic> brandPublicList = brandPublicRepository.getByMaterialClassifySectionId(materialClassifySectionId);
        if (brandPublicList == null || brandPublicList.isEmpty()) return null;

        List<BrandPublicView> brandPublicViewList = new ArrayList<>();
        for (BrandPublic brandPublic : brandPublicList) {
            BrandPublicView brandPublicView = getBrandPublicViewByBrandPublicId(brandPublic.getId());
            if (brandPublicView != null) brandPublicViewList.add(brandPublicView);
        }
        return brandPublicViewList;
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<BrandPublic> getPage(int pageNo, int pageSize) {
        return brandPublicRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param brandId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<BrandPublic> getPageByBrandId(String brandId, int pageNo, int pageSize) {
        return brandPublicRepository.getPageByBrandId(brandId, pageNo, pageSize);
    }

    @Override
    public Page<BrandPublic> getPageByBrandName(String brandName, int pageNo, int pageSize) {
        return brandPublicRepository.getPageByBrandName(brandName, pageNo, pageSize);
    }

    @Override
    public Page<BrandPublic> getPageByBrandPosition(String brandPosition, int pageNo, int pageSize) {
        return brandPublicRepository.getPageByBrandPosition(brandPosition, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<BrandPublicView> getPageView(int pageNo, int pageSize) {
        Page<BrandPublic> brandPublicPage = getPage(pageNo, pageSize);
        return convertBrandPublicPage2PageView(brandPublicPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param brandId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<BrandPublicView> getPageViewByBrandId(String brandId, int pageNo, int pageSize) {
        Page<BrandPublic> brandPublicPage = getPageByBrandId(brandId, pageNo, pageSize);
        return convertBrandPublicPage2PageView(brandPublicPage, pageNo, pageSize);
    }

    @Override
    public Page<BrandPublicView> getPageViewByBrandName(String brandName, Integer pageNo, Integer pageSize) {
        Page<BrandPublic> brandPublicPage = getPageByBrandName(brandName, pageNo, pageSize);
        return convertBrandPublicPage2PageView(brandPublicPage, pageNo, pageSize);
    }

    @Override
    public Page<BrandPublicView> getPageViewByBrandPosition(String brandPosition, Integer pageNo, Integer pageSize) {
        Page<BrandPublic> brandPublicPage = getPageByBrandPosition(brandPosition, pageNo, pageSize);
        return convertBrandPublicPage2PageView(brandPublicPage, pageNo, pageSize);
    }

    @Override
    public String FId(String fname) {
        return brandPublicRepository.findIdByfname(fname);
    }

    @Override
    public List<Brandexcel> getExcelList() {

        return brandPublicRepository.getExcelList();
    }

    @Override
    public void Exceldown(HttpServletResponse response) throws IOException {
        System.out.println("进服务了------------------");
        response.reset();
        List<Brandexcel> list=getExcelList();
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("品牌");
        // 表头
        String[] headers = {
                "大类-专业",
                "中类-材料分类",
                "小类-材料名称",
                "定位",
                "品牌",
                "厂家"
        };
        Row headRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headRow.createCell(i).setCellValue(headers[i]);
        }
        System.out.println("开空间了------------------");
        int rowIdx = 1;
        for (Brandexcel be : list) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(be.getMaterialsdiv());
            row.createCell(1).setCellValue(be.getMaterialsgroup());
            row.createCell(2).setCellValue(be.getMaterialssection());
            row.createCell(3).setCellValue(be.getPosition());
            row.createCell(4).setCellValue(be.getName());
            row.createCell(5).setCellValue(
                    be.getFactory_id() == null ? "无" : be.getFactory_id());
        }
        System.out.println("添加完了了------------------");
        String fileName = URLEncoder.encode("品牌列表.xlsx", StandardCharsets.UTF_8);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename*=UTF-8''" + fileName);

        // 5) 写出 & 关闭
// Spring Boot 示例
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173"); // 允许的前端域名
        response.setHeader("Access-Control-Allow-Methods", "POST, GET"); // 允许的HTTP方法
       // 允许的请求头
        wb.write(response.getOutputStream());
        System.out.println("写完了------------------");

        wb.close();

    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private BrandPublicView getBrandPublicViewByBrandPublicId(String id) {
        BrandPublic brandPublic = getById(id);
        if (brandPublic == null) return null;
        BrandPublicView brandPublicView = new BrandPublicView();
        brandPublicView.setBrandPublic(brandPublic);
        brandPublicView.setBrandView(brandService.getViewById(brandPublic.getBrandId()));
        if(brandPublicView.getBrandView().getBrand().getFactory_id() != null) {
            Company company = companyRepository.getById(brandPublicView.getBrandView().getBrand().getFactory_id());
            brandPublicView.setCompany(company);
        } else {
            brandPublicView.setCompany(null);
        }
        return brandPublicView;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param brandPublicPage 页面对象
     */
    private Page<BrandPublicView> convertBrandPublicPage2PageView(Page<BrandPublic> brandPublicPage, int pageNo, int pageSize) {
        if (brandPublicPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<BrandPublicView> list = new ArrayList<>();
        for (BrandPublic brandPublic : brandPublicPage.getResult()) {
            BrandPublicView brandPublicView = getBrandPublicViewByBrandPublicId(brandPublic.getId());
            if (brandPublicView != null) list.add(brandPublicView);
        }
        return new Page<>(startIndex, brandPublicPage.getTotalCount(), pageSize, list);
    }

}