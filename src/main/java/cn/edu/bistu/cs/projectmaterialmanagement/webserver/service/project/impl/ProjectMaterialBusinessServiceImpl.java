package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.config.ConfigConstant;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialBrandPrivateRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IBrandPublicRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IMaterialBrandRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialClassifyDivisionService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialClassifyGroupService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IMaterialClassifySectionRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.materialreview.IProjectReviewService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.FileUtils;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.office.ExcelUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProjectMaterialBusinessServiceImpl implements IProjectMaterialBusinessService {
    private final IProjectService projectService;
    private final IProjectMaterialService projectMaterialService;
    private final IProjectBrandService projectBrandService;
    private final IProjectMaterialBrandPrivateService projectMaterialBrandPrivateService;
    private final IProjectMaterialBrandPublicService projectMaterialBrandPublicService;
    private final IMaterialService materialService;
    private final IUserService userService;
    private final ICompanyUserService companyUserService;
    private final ICompanyService companyService;
    private final IProjectMaterialHistoryService projectMaterialHistoryService;

    private final IMaterialPhotoService materialPhotoService;

    private final IMaterialBrandRepository materialBrandRepository;
    private final IBrandService brandService;
    private final IMaterialClassifySectionRepository materialClassifySectionRepository;
    private final IMaterialClassifyDivisionService materialClassifyDivisionService;
    private final IMaterialClassifyGroupService materialClassifyGroupService;
    private final MaterialClassifyDivision materialClassifyDivision;
    private final ProjectMaterialBrandPublic projectMaterialBrandPublic;
    private final IBrandPublicRepository brandPublicRepository;


    public ProjectMaterialBusinessServiceImpl(IProjectService projectService,
                                              IProjectMaterialService projectMaterialService,
                                              IProjectBrandService projectBrandService,
                                              IProjectMaterialBrandPrivateService projectMaterialBrandPrivateService,
                                              IProjectMaterialBrandPublicService projectMaterialBrandPublicService,
                                              IMaterialService materialService,
                                              IUserService userService,
                                              ICompanyUserService companyUserService,
                                              ICompanyService companyService,
                                              IProjectMaterialHistoryService projectMaterialHistoryService,
                                              IMaterialPhotoService materialPhotoService,
                                              IMaterialBrandRepository materialBrandRepository,
                                              IBrandService brandService,
                                              IMaterialClassifySectionRepository materialClassifySectionRepository,
                                              IMaterialClassifyDivisionService materialClassifyDivisionService,
                                              IMaterialClassifyGroupService materialClassifyGroupService,
                                              MaterialClassifyDivision materialClassifyDivision,
                                              ProjectMaterialBrandPublic projectMaterialBrandPublic,
                                              IBrandPublicRepository brandPublicRepository) {
        this.projectService = projectService;
        this.projectMaterialService = projectMaterialService;
        this.projectBrandService = projectBrandService;
        this.projectMaterialBrandPrivateService = projectMaterialBrandPrivateService;
        this.projectMaterialBrandPublicService = projectMaterialBrandPublicService;
        this.materialService = materialService;
        this.userService = userService;
        this.companyUserService = companyUserService;
        this.companyService = companyService;
        this.projectMaterialHistoryService = projectMaterialHistoryService;
        this.materialPhotoService = materialPhotoService;
        this.materialBrandRepository = materialBrandRepository;
        this.brandService = brandService;
        this.materialClassifySectionRepository = materialClassifySectionRepository;
        this.materialClassifyDivisionService = materialClassifyDivisionService;
        this.materialClassifyGroupService = materialClassifyGroupService;
        this.materialClassifyDivision = materialClassifyDivision;
        this.projectMaterialBrandPublic = projectMaterialBrandPublic;
        this.brandPublicRepository = brandPublicRepository;
    }


    /**
     * 新增项目材料
     * @param projectMaterialForm
     * @return
     */
    @Override
    public String addForm(ProjectMaterialForm projectMaterialForm) {
        if (projectMaterialForm == null)
            throw new BusinessException("项目物料表单不能为空");

        ProjectMaterial projectMaterial = projectMaterialForm.getProjectMaterial();
        if (projectMaterial.getProjectId() == null)
            throw new BusinessException("参数错误：项目号不对");

        String materialOriginalId = projectMaterial.getMaterialOriginId();
        if (materialOriginalId == null || materialOriginalId.isEmpty())
            throw new BusinessException("参数错误：原始材料号不对");



        String companyId = projectMaterial.getCompanyId();
        if (companyId == null || companyId.isEmpty()) {

            User user = userService.getCurrentLoginUser();
            if (user == null)
                throw new BusinessException("用户未登录，添加失败");
            CompanyUser companyUser = companyUserService.getByUserId(user.getId());
            if (companyUser == null)
                throw new BusinessException("用户未加入任何公司，添加失败");
            if (!companyUserService.isDesignCompanyByUserId(user.getId()))
                throw new BusinessException("用户不是设计公司成员，添加失败");
            companyId = companyUser.getCompanyId();
        } else {
            Company company = companyService.getById(companyId);
            if (company == null)
                throw new BusinessException("公司不存在，添加失败");
            if (!company.getCompanyType().equals(ICompanyService.COMPANY_TYPE_DESIGN))
                throw new BusinessException("公司不是设计公司，添加失败");
        }



        Material material= projectMaterialForm.getMaterial();


        if(material == null)
            throw new BusinessException("材料信息不能为空");



        String materialId;

        //材料发生了变化，需要更新材料信息
        if(projectMaterialForm.isMaterialChange()){
            materialId = materialService.add(material);
            if(materialId == null)
                throw new BusinessException("材料添加失败");


            //修改图片，注意图片包括新增图片和原来图片
            //获得原始物料的图片，如果新图片有原始物料的图片，则直接将其复制到新图片中
            List<MaterialPhoto> materialPhotoList = materialPhotoService.getByMaterialId(materialOriginalId);
            List<String> photoIdList = Arrays.asList(projectMaterialForm.getPhotoIds());
            if (materialPhotoList != null)
                materialPhotoList.forEach(materialPhoto ->
                                          {
                                              if (!photoIdList.contains(materialPhoto.getId()))
                                                  materialPhotoService.deleteById(materialPhoto.getId());
                                              MaterialPhoto newMaterialPhoto = new MaterialPhoto();
                                              newMaterialPhoto.setMaterialId(materialId);
                                              newMaterialPhoto.setFilePath(materialPhoto.getFilePath());
                                              newMaterialPhoto.setDeletedAt(null);
                                              materialPhotoService.add(newMaterialPhoto);
                                          });

            //添加新图片
            String photoTempDir = projectMaterialForm.getPhotoTempDir();
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

        } else {
            materialId = material.getId();
        }

        if(materialId == null)
            throw new BusinessException("材料信息不能为空");

        projectMaterial.setMaterialId(materialId);
        projectMaterial.setCompanyId(companyId);
        projectMaterial.setDeletedAt(null);

        String projectMaterialId =null;
        ProjectMaterial projectMaterialOrigin= projectMaterialService.getByProjectIdAndMaterialOriginId(projectMaterial.getProjectId(),materialOriginalId);
        if(projectMaterialOrigin!=null) {
            projectMaterialId = projectMaterialOrigin.getId();
            projectMaterial.setId(projectMaterialId);
            projectMaterialService.update(projectMaterial);
        }
        else
            projectMaterialId =projectMaterialService.add(projectMaterial);
        if (projectMaterialId == null)
            throw new BusinessException("项目物料添加失败");


        if(projectMaterialOrigin!=null && !materialOriginalId.equalsIgnoreCase(materialId)) {
            ProjectMaterialHistory projectMaterialHistory = new ProjectMaterialHistory();
            projectMaterialHistory.setProjectMaterialId(projectMaterialId);
            projectMaterialHistory.setProjectId(projectMaterial.getProjectId());
            projectMaterialHistory.setMaterialId(materialId);
            projectMaterialHistory.setCompanyId(companyId);
            projectMaterialHistory.setReviewed(IProjectReviewService.PROJECT_REVIEW_RESULT_UNKNOWN);
            projectMaterialHistoryService.add(projectMaterialHistory);
        }

        //处理品牌，包括公有品牌和私有品牌等
        projectMaterialBrandPrivateService.deleteByProjectMaterialId(projectMaterialId);

        //项目私有品牌
        for (String projectBrandId : projectMaterialForm.getProjectBrandIds()) {
            ProjectMaterialBrandPrivate projectMaterialBrandPrivate = new ProjectMaterialBrandPrivate();
            projectMaterialBrandPrivate.setProjectMaterialId(projectMaterialId);
            projectMaterialBrandPrivate.setProjectBrandId(projectBrandId);
            if (projectMaterialBrandPrivateService.add(projectMaterialBrandPrivate) == null)
                throw new BusinessException("项目物料品牌添加失败");
        }

        //项目的私有品牌，里面可能有新建品牌
        for(ProjectBrandView projectBrandView : projectMaterialForm.getProjectBrandViewList()){
            BrandView brandView = projectBrandView.getBrandView();
            if(brandView == null)
                continue;



            Brand brand = brandView.getBrand();
            ProjectBrand projectBrand = projectBrandView.getProjectBrand();

            if(brand == null)
                continue;

            if(brand.getId() == null|| brand.getId().isEmpty()){
               projectMaterialBrandPrivateService.add(brand.getName(),brand.getPosition(),brand.getMaterialClassifySectionId(),projectMaterialId,projectMaterial.getProjectId());
            }else if(projectBrand!=null && projectBrand.getId()!=null && !projectBrand.getId().isEmpty() && projectBrand.getProjectId()!=null && projectBrand.getBrandId()!=null){
                projectMaterialBrandPrivateService.add(projectBrand,projectMaterialId);
            }
            else {
                projectMaterialBrandPrivateService.add(brand.getId(),projectMaterialId,projectMaterial.getProjectId());

            }

        }

        //项目公有品牌

        for (String projectBrandId : projectMaterialForm.getPublicBrandIds()) {
            int nCount=projectMaterialBrandPublicService.getCountByProjectMaterialIdAndBrandPublicId(projectMaterialId,projectBrandId);
            if(nCount>0)
                continue;
            ProjectMaterialBrandPublic projectMaterialBrandPublic = new ProjectMaterialBrandPublic();
            projectMaterialBrandPublic.setProjectMaterialId(projectMaterialId);
            projectMaterialBrandPublic.setBrandPublicId(projectBrandId);
            if (projectMaterialBrandPublicService.add(projectMaterialBrandPublic) == null)
                throw new BusinessException("项目物料品牌添加失败");
        }

        //设置材料样本照片
        if(projectMaterialForm.isMaterialChange()) {

        }

        return projectMaterialId;
    }

    @Override
    public int updateForm(ProjectMaterialForm projectMaterialForm) {
        if (projectMaterialForm == null)
            throw new BusinessException("项目物料表单不能为空");
        String projectMaterialId = projectMaterialForm.getProjectMaterial().getId();
        if (projectMaterialId == null)
            throw new BusinessException("参数为空，修改失败");
        ProjectMaterial projectMaterial = projectMaterialService.getById(projectMaterialId);
        if (projectMaterial.getProjectId() == null)
            throw new BusinessException("参数错误，修改失败");

        int ret = projectMaterialService.update(projectMaterialForm.getProjectMaterial());
        if (ret == 0)
            throw new BusinessException("发生错误，修改失败");

        //修改品牌
        projectMaterialBrandPrivateService.deleteByProjectMaterialId(projectMaterialId);
        for (String projectBrandId : projectMaterialForm.getProjectBrandIds()) {
            ProjectMaterialBrandPrivate projectMaterialBrandPrivate = new ProjectMaterialBrandPrivate();
            projectMaterialBrandPrivate.setProjectMaterialId(projectMaterialId);
            projectMaterialBrandPrivate.setProjectBrandId(projectBrandId);
            if (projectMaterialBrandPrivateService.add(projectMaterialBrandPrivate) == null)
                throw new BusinessException("项目物料品牌添加失败");
        }

        projectMaterialBrandPublicService.deleteByProjectMaterialId(projectMaterialId);
        for (String projectBrandId : projectMaterialForm.getPublicBrandIds()) {
            ProjectMaterialBrandPublic projectMaterialBrandPublic = new ProjectMaterialBrandPublic();
            projectMaterialBrandPublic.setProjectMaterialId(projectMaterialId);
            projectMaterialBrandPublic.setBrandPublicId(projectBrandId);
            if (projectMaterialBrandPublicService.add(projectMaterialBrandPublic) == null)
                throw new BusinessException("项目物料品牌添加失败");
        }
        return ret;
    }

    /**
     * Todo:删除项目
     *
     * @param projectId
     */
    @Override
    public void deleteByProjectId(String projectId) {
        projectBrandService.deleteByProjectId(projectId);
        projectMaterialService.deleteByProjectId(projectId);

    }

    @Override
    public ProjectMaterialView getViewById(String projectMaterialId) {
        ProjectMaterial projectMaterial = projectMaterialService.getById(projectMaterialId);
        if (projectMaterial == null) return null;
        ProjectMaterialView projectMaterialView = new ProjectMaterialView();
        projectMaterialView.setProjectMaterial(projectMaterial);
        projectMaterialView.setMaterial(materialService.getById(projectMaterial.getMaterialId()));
        projectMaterialView.setMaterialOrigin(materialService.getById(projectMaterial.getMaterialOriginId()));
        projectMaterialView.setProject(projectService.getById(projectMaterial.getProjectId()));
        projectMaterialView.setProjectMaterialBrandPrivateViewList(
                projectMaterialBrandPrivateService.getViewListByProjectMaterialId(projectMaterialId));
        projectMaterialView.setProjectMaterialBrandPublicViewList(
                projectMaterialBrandPublicService.getViewListByProjectMaterialId(projectMaterialId));
        return projectMaterialView;
    }

    @Override
    public Page<ProjectMaterialView> getPageView(Integer pageNo,
                                                 Integer pageSize) {
        Page<ProjectMaterial> projectMaterialPage = projectMaterialService.getPage(pageNo, pageSize);
        return convertProjectMaterialPage2PageView(projectMaterialPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectMaterialView> getPageViewByProjectId(String projectId,
                                                            Integer pageNo,
                                                            Integer pageSize) {
        Page<ProjectMaterial> projectMaterialPage = projectMaterialService.getPageByProjectId(projectId, pageNo,
                                                                                              pageSize);
        return convertProjectMaterialPage2PageView(projectMaterialPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectMaterialView> getPageViewByCurrentUserAndProjectId(String projectId,
                                                                          Integer pageNo,
                                                                          Integer pageSize) {


        User user = userService.getCurrentLoginUser();
        if (user == null) return null;
        CompanyUser companyUser = companyUserService.getByUserId(user.getId());
        if (companyUser == null) return null;

        Page<ProjectMaterial> projectMaterialPage = projectMaterialService.getPageByProjectIdAndCompanyId(projectId,
                                                                                                          companyUser.getCompanyId(),
                                                                                                          pageNo,
                                                                                                          pageSize);
        return convertProjectMaterialPage2PageView(projectMaterialPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectMaterialView> getSearchViewByCurrentUserAndProjectId(String projectId,
                                                                            String name,
                                                                            String location,
                                                                            String itemMark,
                                                                            String technology,
                                                                            String installation,
                                                                            String brand,
                                                                            String brandPrivate,
                                                                            Integer pageNo,
                                                                            Integer pageSize) {


        User user = userService.getCurrentLoginUser();
        if (user == null) return null;
        CompanyUser companyUser = companyUserService.getByUserId(user.getId());
        if (companyUser == null) return null;
        Page<ProjectMaterial> projectMaterialPage = projectMaterialService.getSearchPageByProjectIdAndCompanyId(projectId,
                companyUser.getCompanyId(),
                name,
                location,
                itemMark,
                technology,
                installation,
                brand,
                brandPrivate,
                pageNo,
                pageSize);
        return convertProjectMaterialPage2PageView(projectMaterialPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectMaterialView> getProjectMaterialPageViewByProjectIdAndCompanyId(String projectId,
                                                                                       String companyId,
                                                                                       Integer pageNo,
                                                                                       Integer pageSize) {
        Page<ProjectMaterial> projectMaterialPage = projectMaterialService.getPageByProjectIdAndCompanyId(projectId,
                                                                                                          companyId,
                                                                                                          pageNo,
                                                                                                          pageSize);
        return convertProjectMaterialPage2PageView(projectMaterialPage, pageNo, pageSize);
    }

    /**
     * 将页面转换为视图页面
     *
     * @param projectMaterialPage 页面对象
     */
    private Page<ProjectMaterialView> convertProjectMaterialPage2PageView(Page<ProjectMaterial> projectMaterialPage,
                                                                          int pageNo,
                                                                          int pageSize) {
        if (projectMaterialPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialView> list = new ArrayList<>();
        for (ProjectMaterial projectMaterial : projectMaterialPage.getResult()) {
            ProjectMaterialView projectMaterialView = getViewById(projectMaterial.getId());
            if (projectMaterialView != null) list.add(projectMaterialView);
        }
        return new Page<>(startIndex, projectMaterialPage.getTotalCount(), pageSize, list);
    }



    /**
     * 以Excel文件格式下载材料
     *
     * @param request
     * @param response
     */
    @Override
    public void getProjectMaterialExcel(String projectId, HttpServletRequest request, HttpServletResponse response) {

        //获得当前用户
        User user = userService.getCurrentLoginUser();
        if (user == null)
            throw new BusinessException("当前用户为空");
        CompanyUser companyUser = companyUserService.getByUserId(user.getId());
        List<ProjectMaterial> projectMaterialList = projectMaterialService.getListByProjectId(projectId, companyUser.getCompanyId());
        generateProjectMaterialExcel(response, projectMaterialList);

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
     * 根据id得到MaterialClassifySection
     *
     * @param id
     */
    @Override
    public MaterialClassifySection getById(String id) {
        return materialClassifySectionRepository.getById(id);
    }

    private void generateProjectMaterialExcel(HttpServletResponse response,
                                          List<ProjectMaterial> projectMaterialList) {
        try {
            ExcelUtils excelUtils = new ExcelUtils(response);
            excelUtils.begin();
            XSSFSheet sheetProjectMaterial = excelUtils.createSheet("材料名单");
            XSSFWorkbook workbook = excelUtils.getWorkbook();

            //表头
            List<String> heads = new ArrayList<>();
            heads.add("序号");
            heads.add("材料类别");
            heads.add("材料名称");
            heads.add("编号");
            heads.add("材料位置");
            heads.add("技术要求");
            heads.add("材料材质");
            heads.add("颜色");
            heads.add("规格");
            heads.add("防火等级");
            heads.add("施工要求");
            heads.add("材料数量");
            heads.add("数量单位");
            heads.add("参考品牌（共有库）");
            heads.add("参考品牌（私有库）");
//            heads.add("样本图片");

            int rowIndex = 0;
            int span = heads.size() - 1;//合并的单元格
            rowIndex = excelUtils.writeTitle(sheetProjectMaterial, rowIndex, "材料名单", span);

            rowIndex = excelUtils.writeHeadsToExcel(sheetProjectMaterial, heads, rowIndex);


            int i = 1;
            int photoLocation = 15;
            for (ProjectMaterial projectMaterial : projectMaterialList) {
                String[] row = new String[heads.size()];
                ProjectMaterialView projectMaterialView = getViewById(projectMaterial.getId());
                Material material = projectMaterialView.getMaterial();
                List<MaterialPhoto> materialPhotoList = materialPhotoService.getByMaterialId(material.getId());
                //从数据库中查找，说明照片是材料的样本照片
//                for (MaterialPhoto materialPhoto : materialPhotoList) {
//                    if (materialPhoto != null && materialPhoto.getMaterialId().equals(material.getId())) {
//                        String fileName = ConfigConstant.FilePath + File.separator + materialPhoto.getFilePath();
//                        boolean fileExist = FileUtils.isFileExist(fileName);
//                        if (!fileExist) {
//
//                        }
//                        else {
//                            File file = new File(fileName);
//                            setPhoto(workbook, sheetProjectMaterial, photoLocation, i, file);
//                            photoLocation++;
//                        }
//                    }
//                }

//                List<MaterialPhotoView> materialPhotoViewList = new ArrayList<>();
//                for (MaterialPhoto materialPhoto : materialPhotoList) {
//                    MaterialPhotoView materialPhotoView = new MaterialPhotoView();
//                    materialPhotoView.setMaterialPhoto(materialPhoto);
//                    materialPhotoView.setMaterial(materialService.getById(materialPhoto.getMaterialId()));
//                    materialPhotoViewList.add(materialPhotoView);
//                }

                List<ProjectMaterialBrandPublicView> projectMaterialBrandPublicViewList =  projectMaterialView.getProjectMaterialBrandPublicViewList();
                List<ProjectMaterialBrandPrivateView> projectMaterialBrandPrivateViewList =  projectMaterialView.getProjectMaterialBrandPrivateViewList();
                MaterialClassifySection materialClassifySection = materialClassifySectionRepository.getById(material.getMaterialClassifySectionId());
                MaterialClassifyGroup materialClassifyGroup = materialClassifyGroupService.getById(materialClassifySection.getMaterialClassifyGroupId());
                MaterialClassifyDivision materialClassifyDivision = materialClassifyDivisionService.getById(materialClassifyGroup.getMaterialClassifyDivisionId());

                row[0] = i + "";//序号
                row[1] = materialClassifyDivision.getName() + "\\" + materialClassifyGroup.getName() + "\\" + materialClassifySection.getName();//材料类别
                row[2] = material.getName();//名称
                row[3] = material.getItemMark();//编号
                row[4] = material.getLocation();//材料位置
                row[5] = material.getTechnology();//技术要求
                row[6] = material.getMaterial();//材料材质
                row[7] = material.getColor();//颜色
                row[8] = material.getDimension();//规格
                row[9] = material.getFireRating();//防火等级
                row[10] = material.getInstallation();//施工要求
                row[11] = projectMaterialView.getProjectMaterial().getMaterialCount().toString();   //数量
                row[12] = projectMaterialView.getProjectMaterial().getMaterialUnit();   //数量单位
                if(projectMaterialBrandPublicViewList != null && projectMaterialBrandPublicViewList.size() > 0) {
                    for (ProjectMaterialBrandPublicView projectMaterialBrandPublicView : projectMaterialBrandPublicViewList) {
                        String brandName = projectMaterialBrandPublicView.getBrandPublicView().getBrandView().getBrand().getName();
                        if(row[13] != null) {
                            row[13] = row[13] + "、" + brandName;
                        } else {
                            row[13] = brandName;
                        }
                    }
                }   //共有品牌
                if(projectMaterialBrandPrivateViewList != null && projectMaterialBrandPrivateViewList.size() > 0) {
                    for (ProjectMaterialBrandPrivateView projectMaterialBrandPrivateView : projectMaterialBrandPrivateViewList) {
                        String brandName = projectMaterialBrandPrivateView.getProjectBrandView().getBrandView().getBrand().getName();
                        if(row[14] != null) {
                            row[14] = row[14] + "、" + brandName;
                        } else {
                            row[14] = brandName;
                        }
                    }
                }   //私有品牌

                rowIndex = excelUtils.writeRow(sheetProjectMaterial, new ArrayList<>(Arrays.asList(row)), rowIndex);


                i++;
            }

            //自动调整列宽
            excelUtils.autoSizeColumns(sheetProjectMaterial, 10);


            excelUtils.end();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addProjectMaterialExcel(MultipartFile multipartFile) throws Exception {
        if (!multipartFile.isEmpty()) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(multipartFile.getInputStream());
            Workbook workbook = WorkbookFactory.create(bufferedInputStream);
            return ProcessExcel(workbook);
        }

        return 0;
    }

    private int ProcessExcel(Workbook workbook) {
        int nCount = workbook.getNumberOfSheets();
        int nInsertCount = 0;//增加的记录数

        for (int sheetindex = 0; sheetindex < nCount; sheetindex++) {
            Sheet sheet = workbook.getSheetAt(sheetindex);

            int rowstart = sheet.getFirstRowNum();
            int rowEnd = sheet.getLastRowNum();

            int nameIndex = -1;//‘材料名’在Excel文件中的索引
            int itemMartIndex = -1;//‘编号’在Excel文件中的索引
            int locationIndex = -1;//‘材料位置’在Excel文件中的索引
            int technologyIndex = -1;//‘技术要求’在Excel文件中的索引
            int materialIndex = -1;//‘材料材质’在Excel文件中的索引
            int colorIndex = -1;//‘颜色’在Excel文件中的索引
            int dimensionIndex = -1;//’规格‘在Excel文件中的索引
            int fireRatingIndex = -1;//防火等级在Excel文件中的索引
            int installationIndex = -1;//施工要求在Excel文件中的索引
            int materialCountIndex = -1;//数量 在Excel文件中的索引
            int materialUnitIndex = -1;// 单位 在Excel中的索引



//            for (int i = rowstart; i <= rowEnd; i++) {
//
//                Row row = sheet.getRow(i);
//                if (null == row) continue;
//
//                int cellStart = row.getFirstCellNum();
//                int cellEnd = row.getLastCellNum();
//
//                String nameValue = null;
//                String telValue = null;
//                String emailValue = null;
//                String companyValue = null;
//
//                for (int k = cellStart; k <= cellEnd; k++) {
//                    Cell cell = row.getCell(k);
//                    if (null == cell) continue;
//
//
//                    String strIndexName = new DataFormatter().formatCellValue(cell).trim();
//
//                    if (i == rowstart) {
//                        //如果是第一行，则该行是表头，格式为：姓名|电话|邮箱|单位名称|...，注意，次序可能不固定
//                        if (strIndexName.equals("姓名") || strIndexName.equals("名称") || strIndexName.equals("名字"))
//                            nameIndex = k;
//                        else if (strIndexName.equals("电话") || strIndexName.equalsIgnoreCase("tel") || strIndexName.equalsIgnoreCase("telephone"))
//                            telIndex = k;
//                        else if (strIndexName.equals("邮箱") || strIndexName.equalsIgnoreCase("email") || strIndexName.equalsIgnoreCase("email") || strIndexName.equalsIgnoreCase("e-mail"))
//                            emailIndex = k;
//                        else if (strIndexName.equals("单位") || strIndexName.equals("单位名称") || strIndexName.equals("公司") || strIndexName.equals("公司名称"))
//                            companyIndex = k;
//
//
//                    } else if (k == nameIndex) nameValue = strIndexName;//姓名
//                    else if (k == telIndex) telValue = strIndexName;//名称
//                    else if (k == emailIndex) emailValue = strIndexName;//邮箱
//                    else if (k == companyIndex) companyValue = strIndexName;//单位
//
//
//                }
//
//                if (nameValue != null && !nameValue.trim().isEmpty() && telValue != null && !telValue.trim().isEmpty()) {
//
//
//                    try {
//                        //1.查询单位有没有。没有的话，不能增加新用户（不能简单增加单位，因为不知道单位性质）
//                        Company company = companyService.getByName(companyValue);
//                        if (company == null || company.getId() == null) {
//                            continue;
//                        }
//
//                        //2.添加公司用户
//                        CompanyUserForm companyUserForm = new CompanyUserForm();
//                        companyUserForm.setCompanyId(company.getId());
//                        companyUserForm.setTel(telValue);
//                        companyUserForm.setUserRealName(nameValue);
//                        companyUserForm.setEmail(emailValue);
//
//                        if (addCompanyUserForm(companyUserForm) != null) nInsertCount++;
//
//                    } catch (Exception e) {
//                        log.info("添加人员错误，发生异常");
//                    }
//                }
//            }
        }

        return nInsertCount;
    }

    /**
     * @Title: @Description: excel生成图片工具 @param @return @throws
     */
    public void setPhoto(XSSFWorkbook workbook, XSSFSheet sheet, int x, int y, File file) {
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        BufferedImage bufferImg = null;
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();

        try {
            bufferImg = ImageIO.read(file);
            ImageIO.write(bufferImg, "jpg", byteArrayOut);

            // 创建锚点并设置图片位置
            XSSFClientAnchor anchor = new XSSFClientAnchor(
                    0, 0,     // dx1, dy1
                    1023, 255, // dx2, dy2
                    x,  y,  // 起始列，起始行（基于0的索引）
                    x, y   // 结束列，结束行
            );
            anchor.setAnchorType(ClientAnchor.AnchorType.byId(3));

            // 插入图片
            drawing.createPicture(
                    anchor,
                    workbook.addPicture(byteArrayOut.toByteArray(), Workbook.PICTURE_TYPE_JPEG)
            );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
