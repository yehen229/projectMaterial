package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectBrand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectBrandForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectBrandView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Brand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Brandexcel;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectBrandRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectBrandService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IBrandService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectBrandServiceImpl implements IProjectBrandService {

    private static final Logger log =
            LoggerFactory.getLogger(ProjectBrandServiceImpl.class);

    private final IProjectBrandRepository projectBrandRepository;
    private final IProjectService projectService;
    private final IBrandService brandService;

    public ProjectBrandServiceImpl(IProjectBrandRepository projectBrandRepository,
                                   IProjectService projectService,
                                   IBrandService brandService) {
        this.projectBrandRepository = projectBrandRepository;
        this.projectService = projectService;
        this.brandService = brandService;
    }

    /**
     * 增加
     */
    @Override
    public String add(ProjectBrand projectBrand) {
        return projectBrandRepository.add(projectBrand);
    }

    @Override
    public String addForm(ProjectBrandForm projectBrandForm) {
        if (projectBrandForm == null)
            throw new BusinessException("参数不能为空");
        Project project = projectBrandForm.getProject();
        if (project == null)
            throw new BusinessException("参数不能为空");
        Brand brand = projectBrandForm.getBrand();
        if (brand == null)
            throw new BusinessException("参数不能为空");
        String brandId = brandService.add(brand);
        if (brandId == null)
            throw new BusinessException("添加失败");
        ProjectBrand projectBrand = new ProjectBrand();
        projectBrand.setBrandId(brandId);
        projectBrand.setProjectId(project.getId());
        return add(projectBrand);
    }

    /**
     * 删除
     */
    @Override
    public int delete(ProjectBrand projectBrand) {
        return projectBrandRepository.delete(projectBrand);
    }

    /**
     * 更新
     */
    @Override
    public int update(ProjectBrand projectBrand) {
        return projectBrandRepository.update(projectBrand);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     */
    @Override
    public int deleteById(String id) {
        return projectBrandRepository.deleteById(id);
    }

    /**
     * 根据projectId删除记录
     *
     * @param projectId
     */
    @Override
    public int deleteByProjectId(String projectId) {
        return projectBrandRepository.deleteByProjectId(projectId);
    }

    /**
     * 根据brandId删除记录
     *
     * @param brandId
     */
    @Override
    public int deleteByBrandId(String brandId) {
        return projectBrandRepository.deleteByBrandId(brandId);
    }

    /**
     * 得到数量
     */
    @Override
    public int getCount() {
        return projectBrandRepository.getCount();
    }

    /**
     * 根据projectId得到数量
     *
     * @param projectId
     */
    @Override
    public int getCountByProjectId(String projectId) {
        return projectBrandRepository.getCountByProjectId(projectId);
    }

    /**
     * 根据brandId得到数量
     *
     * @param brandId
     */
    @Override
    public int getCountByBrandId(String brandId) {
        return projectBrandRepository.getCountByBrandId(brandId);
    }

    /**
     * 根据id得到ProjectBrand
     *
     * @param id
     */
    @Override
    public ProjectBrand getById(String id) {
        return projectBrandRepository.getById(id);
    }

    @Override
    public ProjectBrand getByProjectIdAndBrandId(String projectId,
                                                 String brandId) {
        return projectBrandRepository.getByProjectIdAndBrandId(projectId, brandId);
    }

    @Override
    public ProjectBrandView getViewById(String id) {
        return getProjectBrandViewByProjectBrandId(id);
    }

    /**
     * 根据projectId得到ProjectBrand
     *
     * @param projectId
     */
    @Override
    public List<ProjectBrand> getByProjectId(String projectId) {
        return projectBrandRepository.getByProjectId(projectId);
    }

    /**
     * 根据brandId得到ProjectBrand
     *
     * @param brandId
     */
    @Override
    public List<ProjectBrand> getByBrandId(String brandId) {
        return projectBrandRepository.getByBrandId(brandId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectBrand> getPage(int pageNo,
                                      int pageSize) {
        return projectBrandRepository.getPage(pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectBrand> getPageByProjectId(String projectId,
                                                 int pageNo,
                                                 int pageSize) {
        return projectBrandRepository.getPageByProjectId(projectId, pageNo, pageSize);
    }
    /**
     * 获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectBrand> getPageByProjectIdAndPosition(String projectId,
                                                            String position,
                                                            int pageNo,
                                                            int pageSize) {
        return projectBrandRepository.getPageByProjectIdAndPosition(projectId, position, pageNo, pageSize);
    }
    /**
     * 获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectBrand> getPageByProjectIdAndBrandName(String projectId,
                                                             String brandName,
                                                             int pageNo,
                                                             int pageSize) {
        return projectBrandRepository.getPageByProjectIdAndBrandName(projectId, brandName, pageNo, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param brandId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectBrand> getPageByBrandId(String brandId,
                                               int pageNo,
                                               int pageSize) {
        return projectBrandRepository.getPageByBrandId(brandId, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectBrandView> getPageView(int pageNo,
                                              int pageSize) {
        Page<ProjectBrand> projectBrandPage = getPage(pageNo, pageSize);
        return convertProjectBrandPage2PageView(projectBrandPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectBrandView> getPageViewByProjectId(String projectId,
                                                         int pageNo,
                                                         int pageSize) {
        Page<ProjectBrand> projectBrandPage = getPageByProjectId(projectId, pageNo, pageSize);
        return convertProjectBrandPage2PageView(projectBrandPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectBrandView> getPageViewByProjectIdAndPosition(String projectId,
                                                         String position,
                                                         int pageNo,
                                                         int pageSize) {
        Page<ProjectBrand> projectBrandPage = getPageByProjectIdAndPosition(projectId, position, pageNo, pageSize);
        return convertProjectBrandPage2PageView(projectBrandPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectBrandView> getPageViewByProjectIdAndBrandName(String projectId,
                                                         String brandName,
                                                         int pageNo,
                                                         int pageSize) {
        Page<ProjectBrand> projectBrandPage = getPageByProjectIdAndBrandName(projectId, brandName, pageNo, pageSize);
        return convertProjectBrandPage2PageView(projectBrandPage, pageNo, pageSize);
    }

    /**
     * 获得指定页面视图数据
     *
     * @param brandId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectBrandView> getPageViewByBrandId(String brandId,
                                                       int pageNo,
                                                       int pageSize) {
        Page<ProjectBrand> projectBrandPage = getPageByBrandId(brandId, pageNo, pageSize);
        return convertProjectBrandPage2PageView(projectBrandPage, pageNo, pageSize);
    }

    @Override
    public List<ProjectBrandView> getViewListByProjectId(String projectId) {
        List<ProjectBrand> projectBrandList = projectBrandRepository.getByProjectId(projectId);
        return convertProjectBrandList2ListView(projectBrandList);
    }

    /**
     * 根据主键获得视图对象
     *
     * @param id 主键
     */
    private ProjectBrandView getProjectBrandViewByProjectBrandId(String id) {
        ProjectBrand projectBrand = getById(id);
        if (projectBrand == null) return null;
        ProjectBrandView projectBrandView = new ProjectBrandView();
        projectBrandView.setProjectBrand(projectBrand);
        projectBrandView.setProject(projectService.getById(projectBrand.getProjectId()));
        projectBrandView.setBrandView(brandService.getViewById(projectBrand.getBrandId()));
        return projectBrandView;
    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectBrandPage 页面对象
     */
    private Page<ProjectBrandView> convertProjectBrandPage2PageView(Page<ProjectBrand> projectBrandPage,
                                                                    int pageNo,
                                                                    int pageSize) {
        if (projectBrandPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectBrandView> list = new ArrayList<>();
        for (ProjectBrand projectBrand : projectBrandPage.getResult()) {
            ProjectBrandView projectBrandView = getProjectBrandViewByProjectBrandId(projectBrand.getId());
            if (projectBrandView != null) list.add(projectBrandView);
        }
        return new Page<>(startIndex, projectBrandPage.getTotalCount(), pageSize, list);
    }

    private List<ProjectBrandView> convertProjectBrandList2ListView(List<ProjectBrand> projectBrandList) {
        if (projectBrandList == null) return null;

        List<ProjectBrandView> list = new ArrayList<>();
        for (ProjectBrand projectBrand : projectBrandList) {
            ProjectBrandView projectBrandView = getProjectBrandViewByProjectBrandId(projectBrand.getId());
            if (projectBrandView != null) list.add(projectBrandView);
        }
        return list;
    }

    @Override
    public List<Brandexcel> getExcelListByProjectId(String projectId) {

        return projectBrandRepository.getExcelListByProjectId(projectId);
    }

    @Override
    public void PrivateExceldown(String projectId,HttpServletResponse response) throws IOException {
        System.out.println("进服务了------------------");
        response.reset();
        List<Brandexcel> list=getExcelListByProjectId(projectId);
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("私有品牌");
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
        String fileName = URLEncoder.encode("私有品牌列表.xlsx", StandardCharsets.UTF_8);
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

}