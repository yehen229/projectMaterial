package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEnd;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEndFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEndForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectDesignCompany;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectForm;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.appearance.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.end.IProjectEndFileService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.end.IProjectEndService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.materialreview.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.FileUtils;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectBusinessServiceImpl implements IProjectBusinessService {

    private final IMaterialService materialService;
    private final IProjectService projectService;
    private final IProjectReviewModeService projectReviewModeService;
    private final IProjectReviewService projectReviewService;
    private final IProjectReviewUserService projectReviewUserService;
    private final IProjectReviewUserFileService projectReviewUserFileService;
    private final IUserService userService;
    private final IProjectUserService projectUserService;
    private final IProjectMaterialBusinessService projectMaterialBusinessService;
    private final ICompanyService companyService;
    private final ICompanyUserService companyUserService;
    private final IProjectCompanyService projectCompanyService;
    private final IUseMaterialService useMaterialService;
    private final IUseMaterialNewBrandService useMaterialNewBrandService;
    private final IUseMaterialNewBrandFileService useMaterialNewBrandFileService;
    private final IMaterialClassifyDivisionService materialClassifyDivisionService;
    private final IMaterialClassifyGroupService materialClassifyGroupService;
    private final IMaterialClassifySectionService materialClassifySectionService;
    private final IUseMaterialBrandSelectService useMaterialBrandSelectService;
    private final IProjectMaterialService projectMaterialService;
    private final IProjectMaterialBrandPrivateService projectMaterialBrandPrivateService;
    private final IProjectMaterialBrandPublicService projectMaterialBrandPublicService;
    private final IUseMaterialBusinessService useMaterialBusinessService;
    private final IBrandService brandService;
    private final IProjectBrandService projectBrandService;
    private final IProjectReviewBusinessService projectReviewBusinessService;



    //外观（非外观）审核
    private final IProjectAppearanceReviewModeService projectAppearanceReviewModeService;
    private final IProjectAppearanceReviewService projectAppearanceReviewService;
    private final IProjectAppearanceReviewUserService projectAppearanceReviewUserService;
    private final IProjectAppearanceReviewUserFileService projectAppearanceReviewUserFileService;
    private final IProjectAppearanceBusinessService projectAppearanceBusinessService;


    private final IBuyMaterialService buyMaterialService;
    private final IBuyMaterialBatchService buyMaterialBatchService;
    private final IProjectMaterialVerificationDocumentService projectMaterialVerificationDocumentService;
    private final IProjectMaterialVerificationDocumentFileService projectMaterialVerificationDocumentFileService;

    private final IProjectMaterialRetestService projectMaterialRetestService;
    private final IProjectMaterialRetestFileService projectMaterialRetestFileService;

    private final IProjectMaterialAcceptanceBatchService projectMaterialAcceptanceBatchService;
    private final IProjectMaterialAcceptanceService projectMaterialAcceptanceService;
    private final IProjectMaterialAcceptanceReviewService projectMaterialAcceptanceReviewService;
    private final IProjectMaterialAcceptanceReviewModeService projectMaterialAcceptanceReviewModeService;
    private final IProjectMaterialAcceptanceReviewUserService projectMaterialAcceptanceReviewUserService;
    private final IProjectMaterialAcceptanceReviewUserFileService projectMaterialAcceptanceReviewUserFileService;
    private final IProjectMaterialAcceptanceBusinessService projectMaterialAcceptanceBusinessService;

    private final IProjectEndService projectEndService;
    private final IProjectEndFileService projectEndFileService;

    private final IProjectDesignCompanyService projectDesignCompanyService;

    private final IProjectMaterialRetestBusinessService projectMaterialRetestBusinessService;

    private final IProjectMaterialHistoryService projectMaterialHistoryService;
    private final IProjectMaterialRetestBatchService projectMaterialRetestBatchService;

    public ProjectBusinessServiceImpl(
            IMaterialService materialService,
            IProjectService projectService,
            IProjectReviewModeService projectReviewModeService,
            IProjectReviewService projectReviewService,
            IProjectReviewUserService projectReviewUserService,
            IProjectReviewUserFileService projectReviewUserFileService,
            IUserService userService,
            IProjectUserService projectUserService,
            IProjectMaterialBusinessService projectMaterialBusinessService,
            ICompanyService companyService,
            ICompanyUserService companyUserService,
            IProjectCompanyService projectCompanyService,
            IUseMaterialService useMaterialService,
            IUseMaterialNewBrandService useMaterialNewBrandService,
            IUseMaterialNewBrandFileService useMaterialNewBrandFileService,
            IMaterialClassifyDivisionService materialClassifyDivisionService,
            IMaterialClassifyGroupService materialClassifyGroupService,
            IMaterialClassifySectionService materialClassifySectionService,
            IUseMaterialBrandSelectService useMaterialBrandSelectService,
            IProjectMaterialService projectMaterialService,
            IProjectMaterialBrandPrivateService projectMaterialBrandPrivateService,
            IProjectMaterialBrandPublicService projectMaterialBrandPublicService,
            IUseMaterialBusinessService useMaterialBusinessService,
            IBrandService brandService,
            IProjectBrandService projectBrandService,
            IProjectReviewBusinessService projectReviewBusinessService,
            IProjectAppearanceReviewModeService projectAppearanceReviewModeService,
            IProjectAppearanceReviewService projectAppearanceReviewService,
            IProjectAppearanceReviewUserService projectAppearanceReviewUserService,
            IProjectAppearanceReviewUserFileService projectAppearanceReviewUserFileService,
            IProjectAppearanceBusinessService projectAppearanceBusinessService,
            IBuyMaterialService buyMaterialService,
            IBuyMaterialBatchService buyMaterialBatchService,
            IProjectMaterialVerificationDocumentService projectMaterialVerificationDocumentService,
            IProjectMaterialVerificationDocumentFileService projectMaterialVerificationDocumentFileService,
            IProjectMaterialRetestService projectMaterialRetestService,
            IProjectMaterialRetestFileService projectMaterialRetestFileService,
            IProjectMaterialAcceptanceBatchService projectMaterialAcceptanceBatchService,
            IProjectMaterialAcceptanceService projectMaterialAcceptanceService,
            IProjectMaterialAcceptanceReviewService projectMaterialAcceptanceReviewService,
            IProjectMaterialAcceptanceReviewModeService projectMaterialAcceptanceReviewModeService,
            IProjectMaterialAcceptanceReviewUserService projectMaterialAcceptanceReviewUserService,
            IProjectMaterialAcceptanceReviewUserFileService projectMaterialAcceptanceReviewUserFileService,

            IProjectMaterialAcceptanceBusinessService projectMaterialAcceptanceBusinessService,
            IProjectEndService projectEndService,
            IProjectEndFileService projectEndFileService,
            IProjectDesignCompanyService projectDesignCompanyService,
            IProjectMaterialRetestBusinessService projectMaterialRetestBusinessService,
            IProjectMaterialHistoryService projectMaterialHistoryService,
            IProjectMaterialRetestBatchService projectMaterialRetestBatchService) {
        this.materialService = materialService;
        this.projectService = projectService;
        this.projectReviewModeService = projectReviewModeService;
        this.projectReviewService = projectReviewService;
        this.projectReviewUserService = projectReviewUserService;
        this.projectReviewUserFileService = projectReviewUserFileService;
        this.userService = userService;
        this.projectUserService = projectUserService;
        this.projectMaterialBusinessService = projectMaterialBusinessService;
        this.companyService = companyService;
        this.companyUserService = companyUserService;
        this.projectCompanyService = projectCompanyService;
        this.useMaterialService = useMaterialService;
        this.useMaterialNewBrandService = useMaterialNewBrandService;
        this.useMaterialNewBrandFileService = useMaterialNewBrandFileService;
        this.materialClassifyDivisionService = materialClassifyDivisionService;
        this.materialClassifyGroupService = materialClassifyGroupService;
        this.materialClassifySectionService = materialClassifySectionService;
        this.useMaterialBrandSelectService = useMaterialBrandSelectService;
        this.projectMaterialService = projectMaterialService;
        this.projectMaterialBrandPrivateService = projectMaterialBrandPrivateService;
        this.projectMaterialBrandPublicService = projectMaterialBrandPublicService;

        this.useMaterialBusinessService = useMaterialBusinessService;
        this.brandService = brandService;
        this.projectBrandService = projectBrandService;
        this.projectReviewBusinessService = projectReviewBusinessService;


        this.projectAppearanceReviewModeService = projectAppearanceReviewModeService;
        this.projectAppearanceReviewService = projectAppearanceReviewService;
        this.projectAppearanceReviewUserService = projectAppearanceReviewUserService;
        this.projectAppearanceReviewUserFileService = projectAppearanceReviewUserFileService;
        this.projectAppearanceBusinessService = projectAppearanceBusinessService;
        this.buyMaterialService = buyMaterialService;
        this.buyMaterialBatchService = buyMaterialBatchService;
        this.projectMaterialVerificationDocumentService = projectMaterialVerificationDocumentService;
        this.projectMaterialVerificationDocumentFileService = projectMaterialVerificationDocumentFileService;
        this.projectMaterialRetestService = projectMaterialRetestService;
        this.projectMaterialRetestFileService = projectMaterialRetestFileService;
        this.projectMaterialAcceptanceBatchService = projectMaterialAcceptanceBatchService;
        this.projectMaterialAcceptanceService = projectMaterialAcceptanceService;
        this.projectMaterialAcceptanceReviewService = projectMaterialAcceptanceReviewService;
        this.projectMaterialAcceptanceReviewModeService = projectMaterialAcceptanceReviewModeService;
        this.projectMaterialAcceptanceReviewUserService = projectMaterialAcceptanceReviewUserService;
        this.projectMaterialAcceptanceReviewUserFileService = projectMaterialAcceptanceReviewUserFileService;

        this.projectMaterialAcceptanceBusinessService = projectMaterialAcceptanceBusinessService;
        this.projectEndService = projectEndService;
        this.projectEndFileService = projectEndFileService;
        this.projectDesignCompanyService = projectDesignCompanyService;
        this.projectMaterialRetestBusinessService = projectMaterialRetestBusinessService;

        this.projectMaterialHistoryService = projectMaterialHistoryService;
        this.projectMaterialRetestBatchService = projectMaterialRetestBatchService;
    }


    /**
     * 将文件添加到临时文件夹
     *
     * @param uploadFilesDir
     * @param multipartFile
     * @return
     */
    private String addTempFile(String uploadFilesDir,
                               MultipartFile multipartFile) {
        if (uploadFilesDir == null || uploadFilesDir.isEmpty()) return null;
        if (multipartFile == null || multipartFile.isEmpty()) return null;
        if (multipartFile.getOriginalFilename() == null || multipartFile.getOriginalFilename().isEmpty()) return null;

        String suffix = multipartFile.getOriginalFilename().substring(
                multipartFile.getOriginalFilename().lastIndexOf(".") + 1
        );

        if (!"doc,docx,pdf,txt,ppt,pptx,xls,xlsx,zip,rar,7z".toUpperCase().contains(suffix.toUpperCase()))
            throw new BusinessException("请选择doc,docx,pdf,txt,ppt,pptx,xls,xlsx,zip,rar,7z等格式文件");

        return FileUtils.addAdditionalFile(uploadFilesDir, multipartFile, true);
    }

    /**
     * 从临时文件夹中删除文件
     *
     * @param uploadFilesDir
     * @param fileName
     * @return
     */
    private boolean deleteTempFile(String uploadFilesDir,
                                   String fileName) {
        if (uploadFilesDir == null || uploadFilesDir.isEmpty()) return false;
        if (fileName == null || fileName.isEmpty()) return false;
        return FileUtils.deleteAdditionalFile(uploadFilesDir, fileName, true);
    }

    /**
     * 新建项目
     *
     * @param projectForm
     * @return
     */
    @Override
    public String add(ProjectForm projectForm) {
        if (projectForm == null)
            throw new BusinessException("参数错误");
        Project project = projectForm.getProject();
        if (project == null)
            throw new BusinessException("参数错误");
        List<Company> companyDesignList = projectForm.getCompanyDesignList();
        if (companyDesignList == null || companyDesignList.isEmpty())
            throw new BusinessException("参数错误");

        if (companyDesignList.size() > 8)
            throw new BusinessException("设计单位不能超过8个");

        String projectId = projectService.add(project);
        if (projectId == null)
            throw new BusinessException("添加失败");

        for (Company company : companyDesignList) {
            ProjectDesignCompany projectDesignCompany = new ProjectDesignCompany();
            projectDesignCompany.setDesignCompanyId(company.getId());
            projectDesignCompany.setProjectId(projectId);
            if (projectDesignCompanyService.add(projectDesignCompany) == null)
                throw new BusinessException("添加失败");
        }

        return projectId;
    }

    @Override
    public String addProjectMaterialReviewTempFile(String uploadReviewFilesDir,
                                                   MultipartFile multipartFile) {
        return addTempFile(uploadReviewFilesDir, multipartFile);
    }

    @Override
    public boolean deleteProjectMaterialReviewTempFile(String uploadReviewFilesDir,
                                                       String fileName) {
        return deleteTempFile(uploadReviewFilesDir, fileName);
    }

    @Override
    public String addUseMaterialNewBrandTempFile(String uploadReviewFilesDir,
                                                 MultipartFile multipartFile) {
        return addTempFile(uploadReviewFilesDir, multipartFile);
    }

    @Override
    public boolean deleteUseMaterialNewBrandTempFile(String uploadReviewFilesDir,
                                                     String fileName) {
        return deleteTempFile(uploadReviewFilesDir, fileName);
    }

    @Override
    public String addUseMaterialAppearanceReviewTempFile(String uploadReviewFilesDir,
                                                         MultipartFile multipartFile) {
        return addTempFile(uploadReviewFilesDir, multipartFile);

    }

    @Override
    public boolean deleteUseMaterialAppearanceReviewTempFile(String uploadReviewFilesDir,
                                                             String fileName) {
        return deleteTempFile(uploadReviewFilesDir, fileName);

    }

    /**
     * @param buyMaterialId 采购单id
     * @param multipartFile 文件
     * @param fileType      文件类型：0工程材料，1设备报验材料
     * @return
     */
    @Override
    public String addBuyMaterialVerificationDocumentFile(String buyMaterialId,
                                                         MultipartFile multipartFile,
                                                         int fileType) {

        BuyMaterial buyMaterial = buyMaterialService.getById(buyMaterialId);
        if (buyMaterial == null)
            return null;

        User user = userService.getCurrentLoginUser();


        if (multipartFile == null || multipartFile.isEmpty()) return null;
        if (multipartFile.getOriginalFilename() == null || multipartFile.getOriginalFilename().isEmpty()) return null;

        String suffix = multipartFile.getOriginalFilename().substring(
                multipartFile.getOriginalFilename().lastIndexOf(".") + 1
        );

        if (!"doc,docx,pdf,txt,ppt,pptx,xls,xlsx,zip,rar,7z".toUpperCase().contains(suffix.toUpperCase()))
            throw new BusinessException("请选择doc,docx,pdf,txt,ppt,pptx,xls,xlsx,zip,rar,7z等格式文件");


        String fileName = FileUtils.addFile(GUID.getGUID(), multipartFile);


        List<ProjectMaterialVerificationDocument> projectMaterialVerificationDocumentList = projectMaterialVerificationDocumentService.getByBuyMaterialId(
                buyMaterialId);
        String projectMaterialVerificationDocumentId;
        if (projectMaterialVerificationDocumentList == null) {
            ProjectMaterialVerificationDocument projectMaterialVerificationDocument = new ProjectMaterialVerificationDocument();
            projectMaterialVerificationDocument.setBuyMaterialId(buyMaterialId);
            projectMaterialVerificationDocument.setUserId(user.getId());
            projectMaterialVerificationDocumentId = projectMaterialVerificationDocumentService.add(
                    projectMaterialVerificationDocument);

        } else {
            projectMaterialVerificationDocumentId = projectMaterialVerificationDocumentList.getFirst().getId();
        }

        if (projectMaterialVerificationDocumentId != null) {
            ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile = new ProjectMaterialVerificationDocumentFile();
            projectMaterialVerificationDocumentFile.setProjectMaterialVerificationDocumentId(
                    projectMaterialVerificationDocumentId);
            projectMaterialVerificationDocumentFile.setFilePath(fileName);
            projectMaterialVerificationDocumentFile.setFileType(fileType);

            return projectMaterialVerificationDocumentFileService.add(projectMaterialVerificationDocumentFile);
        }
        return null;

    }

    @Override
    public int deleteBuyMaterialVerificationDocumentFileById(String projectMaterialVerificationDocumentFileId) {
        ProjectMaterialVerificationDocumentFile projectMaterialVerificationDocumentFile = projectMaterialVerificationDocumentFileService.getById(
                projectMaterialVerificationDocumentFileId);
        if (projectMaterialVerificationDocumentFile == null)
            return 0;
        return projectMaterialVerificationDocumentFileService.deleteById(projectMaterialVerificationDocumentFileId);
    }

    @Override
    public String addForm(ProjectReviewForm projectReviewForm) {
        if (projectReviewForm == null)
            throw new BusinessException("参数为空");

        ProjectReviewMode projectReviewMode = projectReviewForm.getProjectReviewMode();
        projectReviewMode.setCompanyId(projectReviewForm.getCompanyId());
        if (projectReviewMode == null)
            throw new BusinessException("参数为空");

        User user = userService.getCurrentLoginUser();
        if (!user.getId().equals(projectReviewMode.getUserId()))
            throw new BusinessException("参数错误，用户Id不对");

        ProjectReview projectReview = projectReviewForm.getProjectReview();
        if (projectReview == null)
            throw new BusinessException("参数为空");

        if (projectReview.getReviewResult() != IProjectReviewService.PROJECT_REVIEW_RESULT_ACCEPTED &&
                projectReview.getReviewResult() != IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED)
            throw new BusinessException("参数错误，评审结果为非法值");


        Project project = projectService.getById(projectReview.getProjectId());
        if (project == null)
            throw new BusinessException("参数错误，项目不存在");

        ProjectReviewUser projectReivewUser = projectReviewForm.getProjectReivewUser();
        if (projectReivewUser == null)
            throw new BusinessException("参数为空");

        if (projectReviewMode.getMode() != IProjectReviewModeService.PROJECT_REVIEW_MODE_DISPATCH &&
                projectReviewMode.getMode() != IProjectReviewModeService.PROJECT_REVIEW_MODE_MANAGER_REVIEW)
            throw new BusinessException("参数错误");

        String projectReviewModeId = projectReviewModeService.add(projectReviewMode);
        if (projectReviewModeId == null)
            throw new BusinessException("将数据添加到数据库时发生错误");

        projectReview.setProjectReviewModeId(projectReviewModeId);
        projectReview.setReviewDatetime(new Date());
        projectReview.setReviewStatus(IProjectReviewService.PROJECT_REVIEW_STATUS_REVIEWED);

        String projectReviewId = projectReviewService.add(projectReview);
        if (projectReviewId == null)
            throw new BusinessException("将数据添加到数据库时发生错误");

        projectReivewUser.setProjectReviewId(projectReviewId);
        projectReivewUser.setReviewDatetime(new Date());
        projectReivewUser.setUserId(user.getId());
        String projectReviewUserId = projectReviewUserService.add(projectReivewUser);


        //添加文件
        String reviewTempDir = projectReviewForm.getReviewTempDir();
        if (reviewTempDir != null) {
            String pathTemp = FileUtils.getFilePath(reviewTempDir, true);
            String pathDest = FileUtils.getFilePath("", false);
            List<String> listPath = FileUtils.listFiles(pathTemp);
            if (listPath != null && !listPath.isEmpty()) {
                for (String fileName : listPath) {
                    String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));
                    String newFileNamePath = pathDest + newFileName;
                    if (FileUtils.MoveFile(pathTemp + fileName, newFileNamePath)) {
                        ProjectReviewUserFile projectReviewUserFile = new ProjectReviewUserFile();
                        projectReviewUserFile.setProjectReviewUserId(projectReviewUserId);

                        projectReviewUserFile.setFilePath(newFileName);
                        projectReviewUserFileService.add(projectReviewUserFile);
                    }
                }
            }
        }

        return projectReviewId;
    }

    /**
     * 项目经理分派给项目员工进行审核
     *
     * @param projectReviewDispatchForm
     * @return
     */
    @Override
    public String addForm(ProjectReviewDispatchForm projectReviewDispatchForm) {

        if (projectReviewDispatchForm == null)
            throw new BusinessException("参数为空");

        if (projectReviewDispatchForm.getEmployeeIds() == null || projectReviewDispatchForm.getEmployeeIds().length == 0)
            throw new BusinessException("审核员工参数为空");

        ProjectReviewMode projectReviewMode = projectReviewDispatchForm.getProjectReviewMode();
        if (projectReviewMode == null)
            throw new BusinessException("参数为空");

        User user = userService.getCurrentLoginUser();
        if (!user.getId().equals(projectReviewMode.getUserId()))
            throw new BusinessException("参数错误，用户Id不对");

        Project project = projectService.getById(projectReviewMode.getProjectId());
        if (project == null)
            throw new BusinessException("参数错误，项目不存在");

        String projectReviewModeId = projectReviewModeService.add(projectReviewMode);
        if (projectReviewModeId == null)
            throw new BusinessException("将数据添加到数据库时发生错误");

        ProjectReview projectReview = new ProjectReview();
        projectReview.setProjectId(project.getId());
        projectReview.setReviewDatetime(new Date());
        projectReview.setReviewStatus(IProjectReviewService.PROJECT_REVIEW_STATUS_UNREVIEWED);
        projectReview.setReviewResult(IProjectReviewService.PROJECT_REVIEW_RESULT_UNKNOWN);
        projectReview.setProjectReviewModeId(projectReviewModeId);

        String projectReviewId = projectReviewService.add(projectReview);
        if (projectReviewId == null)
            throw new BusinessException("将数据添加到数据库时发生错误");


        //将项目所有员工（即项目经理和项目员工）添加到项目用户审核表中，但是现在这些项目员工还没有开始审核，后续如果审核的话，会update审核结果和审核内容
        List<String> employeeIdList = new ArrayList<>(List.of(projectReviewDispatchForm.getEmployeeIds()));
        employeeIdList.add(user.getId());//添加项目经理

        for (String employeeId : employeeIdList) {
            User userTemp = userService.getById(employeeId);
            if (userTemp != null) {
                ProjectReviewUser projectReviewUser = new ProjectReviewUser();
                projectReviewUser.setProjectReviewId(projectReviewId);
                projectReviewUser.setReviewDatetime(new Date());
                projectReviewUser.setUserId(employeeId);
                projectReviewUser.setReviewResult(IProjectReviewService.PROJECT_REVIEW_RESULT_UNKNOWN);
                projectReviewUserService.add(projectReviewUser);
            }
        }
        return projectReviewId;
    }

    /**
     * 影响外观：项目经理分发审核任务
     *
     * @param projectAppearanceReviewDispatchForm
     * @param useMaterialBrandSelectId
     * @return
     */
    @Override
    public String addForm(ProjectAppearanceReviewDispatchForm projectAppearanceReviewDispatchForm,
                          String useMaterialBrandSelectId) {
        if (projectAppearanceReviewDispatchForm == null)
            throw new BusinessException("参数为空");

        if (projectAppearanceReviewDispatchForm.getEmployeeIds() == null || projectAppearanceReviewDispatchForm.getEmployeeIds().length == 0)
            throw new BusinessException("审核员工参数为空");


        ProjectAppearanceReviewMode projectAppearanceReviewMode = projectAppearanceReviewDispatchForm.getProjectAppearanceReviewMode();
        if (projectAppearanceReviewMode == null)
            throw new BusinessException("参数为空");

        User user = userService.getCurrentLoginUser();
        if (!user.getId().equals(projectAppearanceReviewMode.getUserId()))
            throw new BusinessException("参数错误，用户Id不对");


        //获得用户的部门
        ProjectUser projectUser = projectUserService.getByUserIdAndProjectId(user.getId(),
                                                                             projectAppearanceReviewDispatchForm.getProjectId());

        if (projectUser == null)
            throw new BusinessException("参数错误，用户不是该项目的成员");

        CompanyUser companyUser = companyUserService.getByUserId(user.getId());
        if (companyUser == null)
            throw new BusinessException("参数错误，用户不是合法单位成员");

        //查看员工是否存在，是否是指定部门的员工
        for (String employeeId : projectAppearanceReviewDispatchForm.getEmployeeIds()) {
            ProjectUser projectUserTemp = projectUserService.getByUserIdAndProjectId(employeeId,
                                                                                     projectAppearanceReviewDispatchForm.getProjectId());
            if (projectUserTemp == null)
                throw new BusinessException("参数错误，员工不存在或员工不是该项目的成员");

            CompanyUser companyUserTemp = companyUserService.getByUserId(employeeId);
            if (companyUserTemp == null)
                throw new BusinessException("参数错误，用户不是合法单位成员");

            //判断指定的用户是否是部门成员
            if (!companyUserTemp.getCompanyId().equalsIgnoreCase(companyUser.getCompanyId()))
                throw new BusinessException("参数错误，用户不是部门成员");


        }


        UseMaterialBrandSelect useMaterialBrandSelect = useMaterialBrandSelectService.getById(useMaterialBrandSelectId);
        if (useMaterialBrandSelect == null)
            throw new BusinessException("参数错误，项目不存在");

        projectAppearanceReviewMode.setUseMaterialBrandSelectId(useMaterialBrandSelectId);
        projectAppearanceReviewMode.setMode(
                projectAppearanceReviewMode.getMode() == 0 ? IProjectReviewModeService.PROJECT_REVIEW_MODE_DISPATCH :
                        IProjectReviewModeService.PROJECT_REVIEW_MODE_MANAGER_REVIEW);
        String projectAppearanceReviewModeId = projectAppearanceReviewModeService.add(projectAppearanceReviewMode);
        if (projectAppearanceReviewModeId == null)
            throw new BusinessException("将数据添加到数据库时发生错误");

        ProjectAppearanceReview projectAppearanceReview = new ProjectAppearanceReview();
        projectAppearanceReview.setProjectAppearanceReviewModeId(projectAppearanceReviewModeId);
        projectAppearanceReview.setReviewDatetime(new Date());
        projectAppearanceReview.setReviewStatus(IProjectReviewService.PROJECT_REVIEW_STATUS_UNREVIEWED);
        projectAppearanceReview.setReviewResult(IProjectReviewService.PROJECT_REVIEW_RESULT_UNKNOWN);
        projectAppearanceReview.setUseMaterialId(null);//暂时把所有的物料当作一个批次，而不是针对每一个物料进行审核
        String projectAppearanceReviewId = projectAppearanceReviewService.add(projectAppearanceReview);
        if (projectAppearanceReviewId == null)
            throw new BusinessException("将数据添加到数据库时发生错误");


        //将项目所有员工（即项目经理和项目员工）添加到项目用户审核表中，但是现在这些项目员工还没有开始审核，后续如果审核的话，会update审核结果和审核内容
        List<String> employeeIdList = new ArrayList<>(List.of(projectAppearanceReviewDispatchForm.getEmployeeIds()));
        employeeIdList.add(user.getId());//添加项目经理

        for (String employeeId : employeeIdList) {
            User userTemp = userService.getById(employeeId);
            if (userTemp != null) {
                ProjectAppearanceReviewUser projectAppearanceReviewUser = new ProjectAppearanceReviewUser();
                projectAppearanceReviewUser.setProjectAppearanceReviewId(projectAppearanceReviewId);
                projectAppearanceReviewUser.setReviewDatetime(new Date());
                projectAppearanceReviewUser.setUserId(employeeId);
                projectAppearanceReviewUser.setReviewResult(IProjectReviewService.PROJECT_REVIEW_RESULT_UNKNOWN);
                projectAppearanceReviewUserService.add(projectAppearanceReviewUser);
            }
        }
        return projectAppearanceReviewModeId;
    }

    /**
     * 员工审核项目
     *
     * @param projectReviewEmployeeForm
     * @return
     */
    @Override
    public String addFormOfEmployee(ProjectReviewEmployeeForm projectReviewEmployeeForm) {
        if (projectReviewEmployeeForm == null)
            throw new BusinessException("参数为空");

        ProjectReviewUser projectReviewUser = projectReviewEmployeeForm.getProjectReviewUser();
        if (projectReviewUser == null)
            throw new BusinessException("参数为空");
        String projectReviewId = projectReviewUser.getProjectReviewId();
        if (projectReviewId == null)
            throw new BusinessException("参数为空");
        String userId = projectReviewUser.getUserId();
        if (userId == null)
            throw new BusinessException("用户参数为空");

        ProjectReviewUser projectReviewUserTemp = projectReviewUserService.getByUserIdAndProjectReviewId(userId,
                                                                                                         projectReviewId);
        if (projectReviewUserTemp == null)
            throw new BusinessException("参数错误，不存在该条记录");

        //将审核结果添加到数据库
        projectReviewUserTemp.setReviewResult(projectReviewUser.getReviewResult());
        projectReviewUserTemp.setReviewContent(projectReviewUser.getReviewContent());
        projectReviewUserService.update(projectReviewUserTemp);

        //添加文件
        String reviewTempDir = projectReviewEmployeeForm.getReviewTempDir();
        if (reviewTempDir != null) {
            String pathTemp = FileUtils.getFilePath(reviewTempDir, true);
            String pathDest = FileUtils.getFilePath("", false);
            List<String> listPath = FileUtils.listFiles(pathTemp);
            if (listPath != null && !listPath.isEmpty()) {
                for (String fileName : listPath) {
                    String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));
                    String newFileNamePath = pathDest + newFileName;
                    if (FileUtils.MoveFile(pathTemp + fileName, newFileNamePath)) {
                        ProjectReviewUserFile projectReviewUserFile = new ProjectReviewUserFile();
                        projectReviewUserFile.setProjectReviewUserId(projectReviewUserTemp.getId());

                        projectReviewUserFile.setFilePath(newFileName);
                        projectReviewUserFileService.add(projectReviewUserFile);
                    }
                }
            }
        }

        return projectReviewUserTemp.getId();
    }

    /**
     * 项目经理汇总审核
     *
     * @param projectReviewManagerForm
     * @return
     */
    @Override
    public String addFormOfManagerSummary(ProjectReviewManagerForm projectReviewManagerForm) {
        if (projectReviewManagerForm == null)
            throw new BusinessException("参数为空");

        ProjectReviewUser projectReviewUser = projectReviewManagerForm.getProjectReviewUser();
        if (projectReviewUser == null)
            throw new BusinessException("参数为空");
        String projectReviewId = projectReviewUser.getProjectReviewId();
        if (projectReviewId == null)
            throw new BusinessException("参数为空");
        String userId = projectReviewUser.getUserId();
        if (userId == null)
            throw new BusinessException("用户参数为空");

        ProjectReviewUser projectReviewUserTemp = projectReviewUserService.getByUserIdAndProjectReviewId(userId,
                                                                                                         projectReviewId);
        if (projectReviewUserTemp == null)
            throw new BusinessException("参数错误，不存在该条记录");

        //将审核结果添加到数据库
        projectReviewUserTemp.setReviewResult(projectReviewUser.getReviewResult());
        projectReviewUserTemp.setReviewContent(projectReviewUser.getReviewContent());
        projectReviewUserService.update(projectReviewUserTemp);

        //更新最终的结果
        ProjectReview projectReview = projectReviewService.getById(
                projectReviewUserTemp.getProjectReviewId());
        projectReview.setReviewDatetime(new Date());
        projectReview.setReviewStatus(IProjectReviewService.PROJECT_REVIEW_STATUS_REVIEWED);
        projectReview.setReviewResult(projectReviewUserTemp.getReviewResult());
        projectReviewService.update(projectReview);

        //添加自己的文件
        String reviewTempDir = projectReviewManagerForm.getReviewTempDir();
        if (reviewTempDir != null) {
            String pathTemp = FileUtils.getFilePath(reviewTempDir, true);
            String pathDest = FileUtils.getFilePath("", false);
            List<String> listPath = FileUtils.listFiles(pathTemp);
            if (listPath != null && !listPath.isEmpty()) {
                for (String fileName : listPath) {
                    String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));
                    String newFileNamePath = pathDest + newFileName;
                    if (FileUtils.MoveFile(pathTemp + fileName, newFileNamePath)) {
                        ProjectReviewUserFile projectReviewUserFile = new ProjectReviewUserFile();
                        projectReviewUserFile.setProjectReviewUserId(projectReviewUserTemp.getId());
                        projectReviewUserFile.setFilePath(newFileName);
                        projectReviewUserFileService.add(projectReviewUserFile);
                    }
                }
            }
        }

        //员工的文件作为自己文件，这里不是简单复制id，而是复制文件
        String[] employeeReviewFileIds = projectReviewManagerForm.getEmployeeReviewFileIds();
        if (employeeReviewFileIds != null) {
            for (String employeeReviewFileId : employeeReviewFileIds) {
                projectReviewUserFileService.copyProjectReviewUserFile(employeeReviewFileId,
                                                                       projectReviewUserTemp.getId());
            }
        }

        return projectReviewUserTemp.getId();
    }

    /**
     * 项目经理直接审核,而不是分配给员工审核后汇总
     *
     * @param projectAppearanceReviewManagerDirectForm
     * @param useMaterialBrandSelectId
     */
    @Override
    public String addFormOfManagerReviewDirectly(ProjectAppearanceReviewManagerDirectForm projectAppearanceReviewManagerDirectForm,
                                                 String useMaterialBrandSelectId) {
        if (projectAppearanceReviewManagerDirectForm == null)
            throw new BusinessException("参数为空");

        ProjectAppearanceReviewUser projectAppearanceReviewUser = projectAppearanceReviewManagerDirectForm.getProjectAppearanceReviewUser();
        if (projectAppearanceReviewUser == null)
            throw new BusinessException("参数为空");


        String userId = projectAppearanceReviewUser.getUserId();
        if (userId == null)
            throw new BusinessException("用户参数为空");

        User user = userService.getCurrentLoginUser();
        if (!user.getId().equalsIgnoreCase(userId))
            throw new BusinessException("用户参数错误");


        ProjectAppearanceReviewMode projectAppearanceReviewMode = new ProjectAppearanceReviewMode();
        projectAppearanceReviewMode.setCreateDatetime(new Date());
        projectAppearanceReviewMode.setAffectAppearance(
                getAppearanceByUseMaterialBrandSelectId(useMaterialBrandSelectId));
        projectAppearanceReviewMode.setUserId(userId);
        projectAppearanceReviewMode.setMode(IProjectReviewModeService.PROJECT_REVIEW_MODE_MANAGER_REVIEW);//项目经理审核
        projectAppearanceReviewMode.setUseMaterialBrandSelectId(useMaterialBrandSelectId);
        String projectAppearanceReviewModeId = projectAppearanceReviewModeService.add(projectAppearanceReviewMode);
        if (projectAppearanceReviewModeId == null)
            throw new BusinessException("添加项目 appearance review mode 失败");


        ProjectAppearanceReview projectAppearanceReview = new ProjectAppearanceReview();
        projectAppearanceReview.setProjectAppearanceReviewModeId(projectAppearanceReviewModeId);
        projectAppearanceReview.setReviewDatetime(new Date());
        projectAppearanceReview.setReviewResult(projectAppearanceReviewUser.getReviewResult() == 2 ?
                                                        IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED :
                                                        IProjectReviewService.PROJECT_REVIEW_RESULT_ACCEPTED);
        projectAppearanceReview.setReviewStatus(IProjectReviewService.PROJECT_REVIEW_STATUS_REVIEWED);
        projectAppearanceReview.setUseMaterialId(null);//暂时把所有的物料当作一个批次，而不是针对每一个物料进行审核
        String projectAppearanceReviewId = projectAppearanceReviewService.add(projectAppearanceReview);
        if (projectAppearanceReviewId == null)
            throw new BusinessException("添加 appearanceReview 失败");

        ProjectAppearanceReviewUser appearanceReviewUser1 = new ProjectAppearanceReviewUser();
        appearanceReviewUser1.setProjectAppearanceReviewId(projectAppearanceReviewId);
        appearanceReviewUser1.setReviewContent(projectAppearanceReviewUser.getReviewContent());
        appearanceReviewUser1.setReviewDatetime(new Date());
        appearanceReviewUser1.setUserId(projectAppearanceReviewUser.getUserId());
        appearanceReviewUser1.setReviewResult(projectAppearanceReviewUser.getReviewResult() == 2 ?
                                                      IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED :
                                                      IProjectReviewService.PROJECT_REVIEW_RESULT_ACCEPTED);
        String appearanceReviewUserId = projectAppearanceReviewUserService.add(appearanceReviewUser1);
        if (appearanceReviewUserId == null)
            throw new BusinessException("添加 appearanceReviewUser 失败");

        //添加附件
        String tempFileDir = projectAppearanceReviewManagerDirectForm.getReviewTempDir();
        String pathTemp = FileUtils.getFilePath(tempFileDir, true);
        String pathDest = FileUtils.getFilePath("", false);
        List<String> listPath = FileUtils.listFiles(pathTemp);
        if (listPath != null && !listPath.isEmpty()) {
            for (String fileName : listPath) {
                String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));
                String newFileNamePath = pathDest + newFileName;
                if (FileUtils.MoveFile(pathTemp + fileName, newFileNamePath)) {
                    ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile = new ProjectAppearanceReviewUserFile();
                    projectAppearanceReviewUserFile.setProjectAppearanceReviewUserId(appearanceReviewUserId);
                    projectAppearanceReviewUserFile.setFilePath(newFileName);
                    projectAppearanceReviewUserFileService.add(projectAppearanceReviewUserFile);
                }
            }
        }

        return projectAppearanceReviewModeId;
    }

    @Override
    public String addFormOfEmployee(ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm,
                                    String useMaterialBrandSelectId,
                                    String projectAppearanceReviewModeId) {
        if (projectAppearanceReviewEmployeeForm == null)
            throw new BusinessException("参数为空");

        ProjectAppearanceReviewUser projectAppearanceReviewUser = projectAppearanceReviewEmployeeForm.getProjectAppearanceReviewUser();
        if (projectAppearanceReviewUser == null)
            throw new BusinessException("参数为空");


        String userId = projectAppearanceReviewUser.getUserId();
        if (userId == null)
            throw new BusinessException("用户参数为空");

        User user = userService.getCurrentLoginUser();
        if (!user.getId().equalsIgnoreCase(userId))
            throw new BusinessException("用户参数错误");

        ProjectAppearanceReviewUser projectAppearanceReviewUserTemp = projectAppearanceReviewUserService.getByUserIdAndUseMaterialBrandSelectId(
                userId, useMaterialBrandSelectId, projectAppearanceReviewModeId);


        if (projectAppearanceReviewUserTemp == null)
            throw new BusinessException("参数错误，不存在该条记录");

        //将审核结果添加到数据库
        projectAppearanceReviewUserTemp.setReviewResult(projectAppearanceReviewUser.getReviewResult());
        projectAppearanceReviewUserTemp.setReviewContent(projectAppearanceReviewUser.getReviewContent());
        projectAppearanceReviewUserService.update(projectAppearanceReviewUserTemp);

        //添加文件
        String reviewTempDir = projectAppearanceReviewEmployeeForm.getReviewTempDir();
        if (reviewTempDir != null) {
            String pathTemp = FileUtils.getFilePath(reviewTempDir, true);
            String pathDest = FileUtils.getFilePath("", false);
            List<String> listPath = FileUtils.listFiles(pathTemp);
            if (listPath != null && !listPath.isEmpty()) {
                for (String fileName : listPath) {
                    String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));
                    String newFileNamePath = pathDest + newFileName;
                    if (FileUtils.MoveFile(pathTemp + fileName, newFileNamePath)) {
                        ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile = new ProjectAppearanceReviewUserFile();
                        projectAppearanceReviewUserFile.setProjectAppearanceReviewUserId(
                                projectAppearanceReviewUserTemp.getId());
                        projectAppearanceReviewUserFile.setFilePath(newFileName);
                        projectAppearanceReviewUserFileService.add(projectAppearanceReviewUserFile);
                    }
                }
            }
        }

        return projectAppearanceReviewUserTemp.getId();
    }

    @Override
    public String addFormOfManagerSummary(ProjectAppearanceReviewManagerSummaryForm projectAppearanceReviewManagerSummaryForm,
                                          String useMaterialBrandSelectId,
                                          String projectAppearanceReviewModeId) {
        if (projectAppearanceReviewManagerSummaryForm == null)
            throw new BusinessException("参数为空");

        ProjectAppearanceReviewUser projectAppearanceReviewUser = projectAppearanceReviewManagerSummaryForm.getProjectAppearanceReviewUser();
        if (projectAppearanceReviewUser == null)
            throw new BusinessException("参数为空");


        String userId = projectAppearanceReviewUser.getUserId();
        if (userId == null)
            throw new BusinessException("用户参数为空");

        User user = userService.getCurrentLoginUser();
        if (!user.getId().equalsIgnoreCase(userId))
            throw new BusinessException("用户参数错误");

        ProjectAppearanceReviewUser projectAppearanceReviewUserTemp = projectAppearanceReviewUserService.getByUserIdAndUseMaterialBrandSelectId(
                userId, useMaterialBrandSelectId, projectAppearanceReviewModeId);


        if (projectAppearanceReviewUserTemp == null)
            throw new BusinessException("参数错误，不存在该条记录");

        //将审核结果添加到数据库
        projectAppearanceReviewUserTemp.setReviewResult(projectAppearanceReviewUser.getReviewResult());
        projectAppearanceReviewUserTemp.setReviewContent(projectAppearanceReviewUser.getReviewContent());
        projectAppearanceReviewUserService.update(projectAppearanceReviewUserTemp);

        //更新最终的结果
        ProjectAppearanceReview projectAppearanceReview = projectAppearanceReviewService.getById(
                projectAppearanceReviewUserTemp.getProjectAppearanceReviewId());
        projectAppearanceReview.setReviewDatetime(new Date());
        projectAppearanceReview.setReviewStatus(IProjectReviewService.PROJECT_REVIEW_STATUS_REVIEWED);
        projectAppearanceReview.setReviewResult(projectAppearanceReviewUserTemp.getReviewResult());
        projectAppearanceReviewService.update(projectAppearanceReview);

        //添加自己的文件
        String reviewTempDir = projectAppearanceReviewManagerSummaryForm.getReviewTempDir();
        if (reviewTempDir != null) {
            String pathTemp = FileUtils.getFilePath(reviewTempDir, true);
            String pathDest = FileUtils.getFilePath("", false);
            List<String> listPath = FileUtils.listFiles(pathTemp);
            if (listPath != null && !listPath.isEmpty()) {
                for (String fileName : listPath) {
                    String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));
                    String newFileNamePath = pathDest + newFileName;
                    if (FileUtils.MoveFile(pathTemp + fileName, newFileNamePath)) {
                        ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile = new ProjectAppearanceReviewUserFile();
                        projectAppearanceReviewUserFile.setProjectAppearanceReviewUserId(
                                projectAppearanceReviewUserTemp.getId());
                        projectAppearanceReviewUserFile.setFilePath(newFileName);
                        projectAppearanceReviewUserFileService.add(projectAppearanceReviewUserFile);
                    }
                }
            }
        }

        //员工的文件作为自己文件，这里不是简单复制id，而是复制文件
        String[] employeeReviewFileIds = projectAppearanceReviewManagerSummaryForm.getEmployeeReviewFileIds();
        if (employeeReviewFileIds != null) {
            for (String employeeReviewFileId : employeeReviewFileIds) {
                projectAppearanceReviewUserFileService.copyFile(employeeReviewFileId,
                                                                projectAppearanceReviewUserTemp.getId());
            }
        }

        return projectAppearanceReviewUserTemp.getId();

    }

    @Override
    public String addFormOfDesignCompanyEmployee(ProjectAppearanceReviewEmployeeForm projectAppearanceReviewEmployeeForm,
                                                 String useMaterialBrandSelectId) {

        if (projectAppearanceReviewEmployeeForm == null)
            throw new BusinessException("参数为空");

        ProjectAppearanceReviewUser projectAppearanceReviewUser = projectAppearanceReviewEmployeeForm.getProjectAppearanceReviewUser();
        if (projectAppearanceReviewUser == null)
            throw new BusinessException("参数为空");


        String userId = projectAppearanceReviewUser.getUserId();
        if (userId == null)
            throw new BusinessException("用户参数为空");

        User user = userService.getCurrentLoginUser();
        if (!user.getId().equalsIgnoreCase(userId))
            throw new BusinessException("用户参数错误");


        ProjectAppearanceReviewMode projectAppearanceReviewMode = new ProjectAppearanceReviewMode();
        projectAppearanceReviewMode.setCreateDatetime(new Date());
        projectAppearanceReviewMode.setAffectAppearance(
                getAppearanceByUseMaterialBrandSelectId(useMaterialBrandSelectId));
        projectAppearanceReviewMode.setUserId(userId);
        projectAppearanceReviewMode.setMode(IProjectReviewModeService.PROJECT_REVIEW_MODE_MANAGER_REVIEW);//项目经理审核
        projectAppearanceReviewMode.setUseMaterialBrandSelectId(useMaterialBrandSelectId);
        String projectAppearanceReviewModeId = projectAppearanceReviewModeService.add(projectAppearanceReviewMode);
        if (projectAppearanceReviewModeId == null)
            throw new BusinessException("添加项目 appearance review mode 失败");


        ProjectAppearanceReview projectAppearanceReview = new ProjectAppearanceReview();
        projectAppearanceReview.setProjectAppearanceReviewModeId(projectAppearanceReviewModeId);
        projectAppearanceReview.setReviewDatetime(new Date());
        projectAppearanceReview.setReviewResult(projectAppearanceReviewUser.getReviewResult() == 2 ?
                                                        IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED :
                                                        IProjectReviewService.PROJECT_REVIEW_RESULT_ACCEPTED);
        projectAppearanceReview.setReviewStatus(IProjectReviewService.PROJECT_REVIEW_STATUS_REVIEWED);
        projectAppearanceReview.setUseMaterialId(null);//暂时把所有的物料当作一个批次，而不是针对每一个物料进行审核
        String projectAppearanceReviewId = projectAppearanceReviewService.add(projectAppearanceReview);
        if (projectAppearanceReviewId == null)
            throw new BusinessException("添加 appearanceReview 失败");

        ProjectAppearanceReviewUser appearanceReviewUser1 = new ProjectAppearanceReviewUser();
        appearanceReviewUser1.setProjectAppearanceReviewId(projectAppearanceReviewId);
        appearanceReviewUser1.setReviewContent(projectAppearanceReviewUser.getReviewContent());
        appearanceReviewUser1.setReviewDatetime(new Date());
        appearanceReviewUser1.setUserId(projectAppearanceReviewUser.getUserId());
        appearanceReviewUser1.setReviewResult(projectAppearanceReviewUser.getReviewResult() == 0 ?
                                                      IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED :
                                                      IProjectReviewService.PROJECT_REVIEW_RESULT_ACCEPTED);
        String appearanceReviewUserId = projectAppearanceReviewUserService.add(appearanceReviewUser1);
        if (appearanceReviewUserId == null)
            throw new BusinessException("添加 appearanceReviewUser 失败");

        //添加附件
        String tempFileDir = projectAppearanceReviewEmployeeForm.getReviewTempDir();
        String pathTemp = FileUtils.getFilePath(tempFileDir, true);
        String pathDest = FileUtils.getFilePath("", false);
        List<String> listPath = FileUtils.listFiles(pathTemp);
        if (listPath != null && !listPath.isEmpty()) {
            for (String fileName : listPath) {
                String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));
                String newFileNamePath = pathDest + newFileName;
                if (FileUtils.MoveFile(pathTemp + fileName, newFileNamePath)) {
                    ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile = new ProjectAppearanceReviewUserFile();
                    projectAppearanceReviewUserFile.setProjectAppearanceReviewUserId(appearanceReviewUserId);
                    projectAppearanceReviewUserFile.setFilePath(newFileName);
                    projectAppearanceReviewUserFileService.add(projectAppearanceReviewUserFile);
                }
            }
        }

        return projectAppearanceReviewModeId;

    }

    /**
     * 监理公司决定是否需要复检
     *
     * @param projectMaterialRetestList
     * @return
     */
    @Override
    public String submitSupervisionCompanyDecideWhetherToRecheck(List<ProjectMaterialRetest> projectMaterialRetestList,String tempFileDir) {

        if (projectMaterialRetestList == null || projectMaterialRetestList.isEmpty())
            throw new BusinessException("参数为空");

        ProjectMaterialRetest projectMaterialRetestFirst = projectMaterialRetestList.getFirst();
        if (projectMaterialRetestFirst==null || projectMaterialRetestFirst.getBuyMaterialId()==null)
            throw new BusinessException("参数错误，没有找到需要复检的物料");
        String buyMaterialId=projectMaterialRetestFirst.getBuyMaterialId();
        BuyMaterial buyMaterial = buyMaterialService.getById(buyMaterialId);
        if (buyMaterial==null)
            throw new BusinessException("参数错误，没有找到需要复检的物料");
        BuyMaterialBatch buyMaterialBatch = buyMaterialBatchService.getById(buyMaterial.getBuyMaterialBatchId());
        if (buyMaterialBatch==null)
            throw new BusinessException("参数错误，没有找到需要复检的物料");




        ProjectMaterialRetestBatch projectMaterialRetestBatch = new ProjectMaterialRetestBatch();
        projectMaterialRetestBatch.setCreateDatetime(new Date());
        projectMaterialRetestBatch.setUserId(userService.getCurrentLoginUser().getId());
        projectMaterialRetestBatch.setDeletedAt(null);
        projectMaterialRetestBatch.setProjectId(buyMaterialBatch.getProjectId());
        projectMaterialRetestBatch.setBuyMaterialBatchId(buyMaterialBatch.getId());
        String projectMaterialRetestBatchId = projectMaterialRetestBatchService.add(projectMaterialRetestBatch);

        if(projectMaterialRetestBatchId==null)
            throw new BusinessException("添加项目材料复检批次失败");




        String userId = projectMaterialRetestFirst.getUserId();
        if (userId == null)
            throw new BusinessException("用户参数为空");


        User user = userService.getCurrentLoginUser();
        if (!user.getId().equalsIgnoreCase(userId))
            throw new BusinessException("用户参数错误");

         for (int i = 0; i < projectMaterialRetestList.size(); i++) {
            ProjectMaterialRetest projectMaterialRetest = projectMaterialRetestList.get(i);

            projectMaterialRetest.setReviewDatetime(new Date());
            projectMaterialRetest.setReviewContent(projectMaterialRetest.getReviewContent());

            // 这里确保每个 projectMaterialRetest 使用自己的 buyMaterialId
            if (projectMaterialRetest.getBuyMaterialId() == null)
                throw new BusinessException("项目材料复检的物料ID为空");
            projectMaterialRetest.setBuyMaterialId(projectMaterialRetest.getBuyMaterialId());

            projectMaterialRetest.setDeletedAt(null);
            projectMaterialRetest.setProjectMaterialRetestBatchId(projectMaterialRetestBatchId);

            // 是否需要复检。0：不需要，1：需要
            if (projectMaterialRetest.getNeedRetest() == 0) {
                projectMaterialRetest.setNeedRetest(0);
            } else {
                projectMaterialRetest.setNeedRetest(1);
            }

            // 审核结果，0未审核；1审核通过；2审核不通过
            if (projectMaterialRetest.getReviewResult() == 1) {
                projectMaterialRetest.setReviewResult(IProjectReviewService.PROJECT_REVIEW_RESULT_ACCEPTED);
            } else if (projectMaterialRetest.getReviewResult() == 2) {
                projectMaterialRetest.setReviewResult(IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED);
            } else {
                projectMaterialRetest.setReviewResult(IProjectReviewService.PROJECT_REVIEW_RESULT_UNKNOWN);
            }

            String projectMaterialRetestId = projectMaterialRetestService.add(projectMaterialRetest);
            if (projectMaterialRetestId == null) {
                throw new BusinessException("添加项目材料复检失败");
            }
        }





            //添加附件
           // String tempFileDir = projectMaterialRetestForm.getReviewTempDir();
            String pathTemp = FileUtils.getFilePath(tempFileDir, true);
            String pathDest = FileUtils.getFilePath("", false);
            List<String> listPath = FileUtils.listFiles(pathTemp);
            if (listPath != null && !listPath.isEmpty()) {
                for (String fileName : listPath) {
                    String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));
                    String newFileNamePath = pathDest + newFileName;
                    if (FileUtils.MoveFile(pathTemp + fileName, newFileNamePath)) {
                        ProjectMaterialRetestBatchFile projectMaterialRetestFile = new ProjectMaterialRetestBatchFile();
                        projectMaterialRetestFile.setProjectMaterialRetestBatchId(projectMaterialRetestBatchId);
                        projectMaterialRetestFile.setFilePath(newFileName);
                        projectMaterialRetestFileService.add(projectMaterialRetestFile);
                    }
                }
            }



        return projectMaterialRetestBatchId;

    }

    @Override
    public String addAcceptanceBatchForm(ProjectMaterialAcceptanceBatchForm projectMaterialAcceptanceBatchForm) {
        if (projectMaterialAcceptanceBatchForm == null)
            throw new BusinessException("参数为空");

        ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch = projectMaterialAcceptanceBatchForm.getProjectMaterialAcceptanceBatch();
        if (projectMaterialAcceptanceBatch == null)
            throw new BusinessException("参数为空");


        String userId = projectMaterialAcceptanceBatch.getUserId();
        if (userId == null)
            throw new BusinessException("用户参数为空");

        User user = userService.getCurrentLoginUser();
        if (!user.getId().equalsIgnoreCase(userId))
            throw new BusinessException("用户参数错误");

        List<ProjectMaterialAcceptance> projectMaterialAcceptanceList =
                projectMaterialAcceptanceBatchForm.getProjectMaterialAcceptanceList();
        if (projectMaterialAcceptanceList == null || projectMaterialAcceptanceList.isEmpty())
            throw new BusinessException("参数为空");

        //检查项目验收材料数据是否正确
        for (ProjectMaterialAcceptance projectMaterialAcceptance : projectMaterialAcceptanceList) {
            if (projectMaterialAcceptance.getProjectMaterialId() == null || projectMaterialAcceptance.getProjectMaterialId().isEmpty())
                throw new BusinessException("项目验收材料数据错误");
            if (projectMaterialAcceptance.getMaterialCount() == null || projectMaterialAcceptance.getMaterialCount().compareTo(
                    BigDecimal.ZERO) <= 0 || projectMaterialAcceptance.getMaterialUnit() == null ||
                    projectMaterialAcceptance.getMaterialUnit().trim().isEmpty())
                throw new BusinessException("项目验收材料数据错误");

            if ((projectMaterialAcceptance.getProjectMaterialBrandPublicId() == null || projectMaterialAcceptance.getProjectMaterialBrandPublicId().isEmpty()) &&
                    (projectMaterialAcceptance.getProjectMaterialBrandPrivateId() == null || projectMaterialAcceptance.getProjectMaterialBrandPrivateId().isEmpty()))
                throw new BusinessException("项目验收材料数据错误");
        }

        //生成一个验收批次
        String projectMaterialAcceptanceBatchId = projectMaterialAcceptanceBatchService.add(
                projectMaterialAcceptanceBatch);

        if (projectMaterialAcceptanceBatchId == null)
            throw new BusinessException("添加项目验收批次失败");

        for (ProjectMaterialAcceptance projectMaterialAcceptance : projectMaterialAcceptanceList) {
            projectMaterialAcceptance.setProjectMaterialAcceptanceBatchId(projectMaterialAcceptanceBatchId);
            if (projectMaterialAcceptance.getProjectMaterialBrandPrivateId() != null && projectMaterialAcceptance.getProjectMaterialBrandPrivateId().isEmpty())
                projectMaterialAcceptance.setProjectMaterialBrandPrivateId(null);

            if (projectMaterialAcceptance.getProjectMaterialBrandPublicId() != null && projectMaterialAcceptance.getProjectMaterialBrandPublicId().isEmpty())
                projectMaterialAcceptance.setProjectMaterialBrandPublicId(null);
            projectMaterialAcceptanceService.add(projectMaterialAcceptance);
        }

        return projectMaterialAcceptanceBatchId;


    }

    @Override
    public String addAcceptanceFormOfManagerReviewDirectly(ProjectMaterialAcceptanceReviewManagerDirectForm projectMaterialAcceptanceReviewManagerDirectForm,
                                                           String projectMaterialAcceptanceBatchId) {
        if (projectMaterialAcceptanceReviewManagerDirectForm == null)
            throw new BusinessException("参数为空");

        ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser = projectMaterialAcceptanceReviewManagerDirectForm.getProjectMaterialAcceptanceReviewUser();
        if (projectMaterialAcceptanceReviewUser == null)
            throw new BusinessException("参数为空");


        ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch = projectMaterialAcceptanceBatchService.getById(
                projectMaterialAcceptanceBatchId);
        if (projectMaterialAcceptanceBatch == null)
            throw new BusinessException("项目验收批次不存在");


        String userId = projectMaterialAcceptanceReviewUser.getUserId();
        if (userId == null)
            throw new BusinessException("用户参数为空");

        User user = userService.getCurrentLoginUser();
        if (!user.getId().equalsIgnoreCase(userId))
            throw new BusinessException("用户参数错误");

        ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode = new ProjectMaterialAcceptanceReviewMode();
        projectMaterialAcceptanceReviewMode.setCreateDatetime(new Date());
        projectMaterialAcceptanceReviewMode.setProjectMaterialAcceptanceBatchId(projectMaterialAcceptanceBatchId);
        projectMaterialAcceptanceReviewMode.setUserId(userId);
        projectMaterialAcceptanceReviewMode.setMode(
                IProjectReviewModeService.PROJECT_REVIEW_MODE_MANAGER_REVIEW);//项目经理审核
        String projectMaterialAcceptanceReviewModeId = projectMaterialAcceptanceReviewModeService.add(
                projectMaterialAcceptanceReviewMode);
        if (projectMaterialAcceptanceReviewModeId == null)
            throw new BusinessException("添加项目 appearance review mode 失败");


        ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview = new ProjectMaterialAcceptanceReview();
        projectMaterialAcceptanceReview.setProjectMaterialAcceptanceModeId(projectMaterialAcceptanceReviewModeId);
        projectMaterialAcceptanceReview.setReviewDatetime(new Date());
        projectMaterialAcceptanceReview.setReviewResult(projectMaterialAcceptanceReviewUser.getReviewResult() == 0 ?
                                                                IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED :
                                                                IProjectReviewService.PROJECT_REVIEW_RESULT_ACCEPTED);
        projectMaterialAcceptanceReview.setReviewStatus(IProjectReviewService.PROJECT_REVIEW_STATUS_REVIEWED);
        String projectMaterialAcceptanceReviewId = projectMaterialAcceptanceReviewService.add(
                projectMaterialAcceptanceReview);
        if (projectMaterialAcceptanceReviewId == null)
            throw new BusinessException("添加 appearanceReview 失败");

        ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUserTemp = new ProjectMaterialAcceptanceReviewUser();
        projectMaterialAcceptanceReviewUserTemp.setProjectMaterialAcceptanceReviewId(projectMaterialAcceptanceReviewId);
        projectMaterialAcceptanceReviewUserTemp.setReviewContent(
                projectMaterialAcceptanceReviewUser.getReviewContent());
        projectMaterialAcceptanceReviewUserTemp.setReviewDatetime(new Date());
        projectMaterialAcceptanceReviewUserTemp.setUserId(projectMaterialAcceptanceReviewUser.getUserId());
        projectMaterialAcceptanceReviewUserTemp.setReviewResult(
                projectMaterialAcceptanceReviewUser.getReviewResult() == 0 ?
                        IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED :
                        IProjectReviewService.PROJECT_REVIEW_RESULT_ACCEPTED);
        String projectMaterialAcceptanceReviewUserId = projectMaterialAcceptanceReviewUserService.add(
                projectMaterialAcceptanceReviewUserTemp);
        if (projectMaterialAcceptanceReviewUserId == null)
            throw new BusinessException("添加 appearanceReviewUser 失败");

        //添加附件
        String tempFileDir = projectMaterialAcceptanceReviewManagerDirectForm.getReviewTempDir();
        String pathTemp = FileUtils.getFilePath(tempFileDir, true);
        String pathDest = FileUtils.getFilePath("", false);
        List<String> listPath = FileUtils.listFiles(pathTemp);
        if (listPath != null && !listPath.isEmpty()) {
            for (String fileName : listPath) {
                String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));
                String newFileNamePath = pathDest + newFileName;
                if (FileUtils.MoveFile(pathTemp + fileName, newFileNamePath)) {
                    ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile = new ProjectMaterialAcceptanceReviewUserFile();
                    projectMaterialAcceptanceReviewUserFile.setProjectMaterialAcceptanceReviewUserId(
                            projectMaterialAcceptanceReviewUserId);
                    projectMaterialAcceptanceReviewUserFile.setFilePath(newFileName);
                    projectMaterialAcceptanceReviewUserFileService.add(projectMaterialAcceptanceReviewUserFile);
                }
            }
        }

        return projectMaterialAcceptanceReviewModeId;
    }

    /**
     * 项目验收：工程部项目经理分发任务
     *
     * @param projectMaterialAcceptanceReviewDispatchForm
     * @param projectMaterialAcceptanceBatchId
     * @return
     */
    @Override
    public String addForm(ProjectMaterialAcceptanceReviewDispatchForm projectMaterialAcceptanceReviewDispatchForm,
                          String projectMaterialAcceptanceBatchId) {

        if (projectMaterialAcceptanceReviewDispatchForm == null)
            throw new BusinessException("参数为空");


        ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch = projectMaterialAcceptanceBatchService.getById(
                projectMaterialAcceptanceBatchId);
        if (projectMaterialAcceptanceBatch == null)
            throw new BusinessException("项目验收批次不存在");

        ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode = projectMaterialAcceptanceReviewDispatchForm.getProjectMaterialAcceptanceReviewMode();
        if (projectMaterialAcceptanceReviewMode == null)
            throw new BusinessException("参数为空");


        String userId = projectMaterialAcceptanceReviewMode.getUserId();
        if (userId == null)
            throw new BusinessException("用户参数为空");

        User user = userService.getCurrentLoginUser();
        if (!user.getId().equalsIgnoreCase(userId))
            throw new BusinessException("用户参数错误");

        //获得用户的部门
        ProjectUser projectUser = projectUserService.getByUserIdAndProjectId(user.getId(),
                                                                             projectMaterialAcceptanceBatch.getProjectId());

        if (projectUser == null)
            throw new BusinessException("参数错误，用户不是该项目的成员");

        CompanyUser companyUser = companyUserService.getByUserId(user.getId());
        if (companyUser == null)
            throw new BusinessException("参数错误，用户不是合法单位成员");

        //查看员工是否存在，是否是指定部门的员工
        for (String employeeId : projectMaterialAcceptanceReviewDispatchForm.getEmployeeIds()) {
            ProjectUser projectUserTemp = projectUserService.getByUserIdAndProjectId(employeeId,
                                                                                     projectMaterialAcceptanceReviewDispatchForm.getProjectId());
            if (projectUserTemp == null)
                throw new BusinessException("参数错误，员工不存在或员工不是该项目的成员");

            CompanyUser companyUserTemp = companyUserService.getByUserId(employeeId);
            if (companyUserTemp == null)
                throw new BusinessException("参数错误，用户不是合法单位成员");

            //判断指定的用户是否是部门成员
            if (!companyUserTemp.getCompanyId().equalsIgnoreCase(companyUser.getCompanyId()))
                throw new BusinessException("参数错误，用户不是部门成员");


        }


        projectMaterialAcceptanceReviewMode.setProjectMaterialAcceptanceBatchId(projectMaterialAcceptanceBatchId);
        projectMaterialAcceptanceReviewMode.setUserId(user.getId());
        projectMaterialAcceptanceReviewMode.setCreateDatetime(new Date());

        projectMaterialAcceptanceReviewMode.setMode(
                projectMaterialAcceptanceReviewMode.getMode() == 0 ? IProjectReviewModeService.PROJECT_REVIEW_MODE_DISPATCH :
                        IProjectReviewModeService.PROJECT_REVIEW_MODE_MANAGER_REVIEW);
        String projectMaterialAcceptanceReviewModeId = projectMaterialAcceptanceReviewModeService.add(
                projectMaterialAcceptanceReviewMode);
        if (projectMaterialAcceptanceReviewModeId == null)
            throw new BusinessException("将数据添加到数据库时发生错误");

        ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview = new ProjectMaterialAcceptanceReview();
        projectMaterialAcceptanceReview.setProjectMaterialAcceptanceModeId(projectMaterialAcceptanceReviewModeId);
        projectMaterialAcceptanceReview.setReviewDatetime(new Date());
        projectMaterialAcceptanceReview.setReviewStatus(IProjectReviewService.PROJECT_REVIEW_STATUS_UNREVIEWED);
        projectMaterialAcceptanceReview.setReviewResult(IProjectReviewService.PROJECT_REVIEW_RESULT_UNKNOWN);
        String projectMaterialAcceptanceReviewId = projectMaterialAcceptanceReviewService.add(
                projectMaterialAcceptanceReview);
        if (projectMaterialAcceptanceReviewId == null)
            throw new BusinessException("将数据添加到数据库时发生错误");


        //将项目所有员工（即项目经理和项目员工）添加到项目用户审核表中，但是现在这些项目员工还没有开始审核，后续如果审核的话，会update审核结果和审核内容
        List<String> employeeIdList = new ArrayList<>(
                List.of(projectMaterialAcceptanceReviewDispatchForm.getEmployeeIds()));
        employeeIdList.add(user.getId());//添加项目经理

        for (String employeeId : employeeIdList) {
            User userTemp = userService.getById(employeeId);
            if (userTemp != null) {
                ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser = new ProjectMaterialAcceptanceReviewUser();
                projectMaterialAcceptanceReviewUser.setProjectMaterialAcceptanceReviewId(
                        projectMaterialAcceptanceReviewId);
                projectMaterialAcceptanceReviewUser.setReviewDatetime(new Date());
                projectMaterialAcceptanceReviewUser.setUserId(employeeId);
                projectMaterialAcceptanceReviewUser.setReviewResult(
                        IProjectReviewService.PROJECT_REVIEW_RESULT_UNKNOWN);
                projectMaterialAcceptanceReviewUserService.add(projectMaterialAcceptanceReviewUser);
            }
        }
        return projectMaterialAcceptanceReviewModeId;

    }


    /**
     * 项目验收：增加员工审核
     *
     * @param projectMaterialAcceptanceReviewEmployeeForm
     * @param projectMaterialAcceptanceBatchId
     * @param projectMaterialAcceptanceReviewModeId
     * @return
     */
    @Override
    public String addFormOfEmployee(ProjectMaterialAcceptanceReviewEmployeeForm projectMaterialAcceptanceReviewEmployeeForm,
                                    String projectMaterialAcceptanceBatchId,
                                    String projectMaterialAcceptanceReviewModeId) {

        if (projectMaterialAcceptanceReviewEmployeeForm == null)
            throw new BusinessException("参数为空");

        ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser = projectMaterialAcceptanceReviewEmployeeForm.getProjectMaterialAcceptanceReviewUser();
        if (projectMaterialAcceptanceReviewUser == null)
            throw new BusinessException("参数为空");

        ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch = projectMaterialAcceptanceBatchService.getById(
                projectMaterialAcceptanceBatchId);
        if (projectMaterialAcceptanceBatch == null)
            throw new BusinessException("项目验收批次不存在");


        String userId = projectMaterialAcceptanceReviewUser.getUserId();
        if (userId == null)
            throw new BusinessException("用户参数为空");

        User user = userService.getCurrentLoginUser();
        if (!user.getId().equalsIgnoreCase(userId))
            throw new BusinessException("用户参数错误");

        ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUserTemp = projectMaterialAcceptanceReviewUserService.getByUserIdAndModeId(
                userId, projectMaterialAcceptanceBatchId, projectMaterialAcceptanceReviewModeId);

        if (projectMaterialAcceptanceReviewUserTemp == null)
            throw new BusinessException("不存在记录");

        //将审核结果添加到数据库
        if (projectMaterialAcceptanceReviewUser.getReviewResult() != 1)
            projectMaterialAcceptanceReviewUserTemp.setReviewResult(
                    IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED);
        else
            projectMaterialAcceptanceReviewUserTemp.setReviewResult(
                    IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED);
        projectMaterialAcceptanceReviewUserTemp.setReviewContent(
                projectMaterialAcceptanceReviewUser.getReviewContent());
        projectMaterialAcceptanceReviewUserService.update(projectMaterialAcceptanceReviewUserTemp);

        //添加文件
        String reviewTempDir = projectMaterialAcceptanceReviewEmployeeForm.getReviewTempDir();
        if (reviewTempDir != null) {
            String pathTemp = FileUtils.getFilePath(reviewTempDir, true);
            String pathDest = FileUtils.getFilePath("", false);
            List<String> listPath = FileUtils.listFiles(pathTemp);
            if (listPath != null && !listPath.isEmpty()) {
                for (String fileName : listPath) {
                    String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));
                    String newFileNamePath = pathDest + newFileName;
                    if (FileUtils.MoveFile(pathTemp + fileName, newFileNamePath)) {
                        ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile = new ProjectMaterialAcceptanceReviewUserFile();
                        projectMaterialAcceptanceReviewUserFile.setProjectMaterialAcceptanceReviewUserId(
                                projectMaterialAcceptanceReviewUserTemp.getId());
                        projectMaterialAcceptanceReviewUserFile.setFilePath(newFileName);
                        projectMaterialAcceptanceReviewUserFileService.add(projectMaterialAcceptanceReviewUserFile);
                    }
                }
            }
        }

        return projectMaterialAcceptanceReviewUserTemp.getId();

    }

    /**
     * 项目验收：工程部项目经理汇总
     *
     * @param projectMaterialAcceptanceReviewManagerSummaryForm
     * @param projectMaterialAcceptanceBatchId
     * @param projectMaterialAcceptanceReviewModeId
     * @return
     */
    @Override
    public String addFormOfManagerSummary(ProjectMaterialAcceptanceReviewManagerSummaryForm projectMaterialAcceptanceReviewManagerSummaryForm,
                                          String projectMaterialAcceptanceBatchId,
                                          String projectMaterialAcceptanceReviewModeId) {

        if (projectMaterialAcceptanceReviewManagerSummaryForm == null)
            throw new BusinessException("参数为空");

        ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser = projectMaterialAcceptanceReviewManagerSummaryForm.getProjectMaterialAcceptanceReviewUser();
        if (projectMaterialAcceptanceReviewUser == null)
            throw new BusinessException("参数为空");

        ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch = projectMaterialAcceptanceBatchService.getById(
                projectMaterialAcceptanceBatchId);
        if (projectMaterialAcceptanceBatch == null)
            throw new BusinessException("项目验收批次不存在");

        ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode = projectMaterialAcceptanceReviewModeService.getById(
                projectMaterialAcceptanceReviewModeId);
        if (projectMaterialAcceptanceReviewMode == null)
            throw new BusinessException("参数为空");


        String userId = projectMaterialAcceptanceReviewMode.getUserId();
        if (userId == null)
            throw new BusinessException("用户参数为空");

        User user = userService.getCurrentLoginUser();
        if (!user.getId().equalsIgnoreCase(userId))
            throw new BusinessException("用户参数错误");

        ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUserTemp = projectMaterialAcceptanceReviewUserService.getByUserIdAndModeId(
                userId, projectMaterialAcceptanceBatchId, projectMaterialAcceptanceReviewModeId);

        if (projectMaterialAcceptanceReviewUserTemp == null)
            throw new BusinessException("不存在记录");

        //将审核结果添加到数据库
        if (projectMaterialAcceptanceReviewUser.getReviewResult() != 2)
            projectMaterialAcceptanceReviewUserTemp.setReviewResult(
                    IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED);
        else
            projectMaterialAcceptanceReviewUserTemp.setReviewResult(
                    IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED);
        projectMaterialAcceptanceReviewUserTemp.setReviewContent(
                projectMaterialAcceptanceReviewUser.getReviewContent());
        projectMaterialAcceptanceReviewUserService.update(projectMaterialAcceptanceReviewUserTemp);


        //更新最终的结果
        ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview = projectMaterialAcceptanceReviewService.getById(
                projectMaterialAcceptanceReviewUserTemp.getProjectMaterialAcceptanceReviewId());
        projectMaterialAcceptanceReview.setReviewDatetime(new Date());
        projectMaterialAcceptanceReview.setReviewStatus(IProjectReviewService.PROJECT_REVIEW_STATUS_REVIEWED);
        projectMaterialAcceptanceReview.setReviewResult(projectMaterialAcceptanceReviewUserTemp.getReviewResult());
        projectMaterialAcceptanceReviewService.update(projectMaterialAcceptanceReview);

        //添加自己的文件
        String reviewTempDir = projectMaterialAcceptanceReviewManagerSummaryForm.getReviewTempDir();
        if (reviewTempDir != null) {
            String pathTemp = FileUtils.getFilePath(reviewTempDir, true);
            String pathDest = FileUtils.getFilePath("", false);
            List<String> listPath = FileUtils.listFiles(pathTemp);
            if (listPath != null && !listPath.isEmpty()) {
                for (String fileName : listPath) {
                    String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));
                    String newFileNamePath = pathDest + newFileName;
                    if (FileUtils.MoveFile(pathTemp + fileName, newFileNamePath)) {
                        ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile = new ProjectMaterialAcceptanceReviewUserFile();
                        projectMaterialAcceptanceReviewUserFile.setProjectMaterialAcceptanceReviewUserId(
                                projectMaterialAcceptanceReviewUserTemp.getId());
                        projectMaterialAcceptanceReviewUserFile.setFilePath(newFileName);
                        projectMaterialAcceptanceReviewUserFileService.add(projectMaterialAcceptanceReviewUserFile);
                    }
                }
            }
        }

        //员工的文件作为自己文件，这里不是简单复制id，而是复制文件
        String[] employeeReviewFileIds = projectMaterialAcceptanceReviewManagerSummaryForm.getEmployeeReviewFileIds();
        if (employeeReviewFileIds != null) {
            for (String employeeReviewFileId : employeeReviewFileIds) {
                projectMaterialAcceptanceReviewUserFileService.copyFile(employeeReviewFileId,
                                                                projectMaterialAcceptanceReviewUserTemp.getId());
            }
        }

        return projectMaterialAcceptanceReviewUserTemp.getId();

    }

    /**
     * 项目结项
     *
     * @param projectEndForm
     * @return
     */
    @Override
    public String addFormOfEndProject(ProjectEndForm projectEndForm) {

        if (projectEndForm == null)
            throw new BusinessException("参数为空");

        ProjectEnd projectEnd = projectEndForm.getProjectEnd();
        if (projectEnd == null)
            throw new BusinessException("参数为空");


        String userId = projectEnd.getUserId();
        if (userId == null)
            throw new BusinessException("用户参数为空");

        User user = userService.getCurrentLoginUser();
        if (!user.getId().equalsIgnoreCase(userId))
            throw new BusinessException("用户参数错误");

        Project project = projectService.getById(projectEnd.getProjectId());
        if (project == null)
            throw new BusinessException("项目不存在");

        //判断是否结项。0未结项，1已结项
//        if (projectEndForm.getReviewResult() == 0)
//            return null;

        projectEnd.setCreateDatetime(new Date());


        String projectEndId = projectEndService.add(projectEnd);
        if (projectEndId == null)
            throw new BusinessException("添加项目结项失败");


        //复检，才需要添加附件
        //添加附件
        String tempFileDir = projectEndForm.getReviewTempDir();
        String pathTemp = FileUtils.getFilePath(tempFileDir, true);
        String pathDest = FileUtils.getFilePath("", false);
        List<String> listPath = FileUtils.listFiles(pathTemp);
        if (listPath != null && !listPath.isEmpty()) {
            for (String fileName : listPath) {
                String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));
                String newFileNamePath = pathDest + newFileName;
                if (FileUtils.MoveFile(pathTemp + fileName, newFileNamePath)) {
                    ProjectEndFile projectEndFile = new ProjectEndFile();
                    projectEndFile.setProjectEndId(projectEndId);
                    projectEndFile.setFilePath(newFileName);
                    projectEndFileService.add(projectEndFile);
                }
            }
        }

        return projectEndId;


    }

    @Override
    public List<ProjectReviewUser> getRemainingNotReviewedEmployees(String projectReviewId) {
        return projectReviewUserService.getByProjectReviewIdAndNotReviewed(projectReviewId);


    }

    @Override
    public List<ProjectAppearanceReviewUser> getRemainingAppearanceNotReviewedEmployees(String projectAppearanceReviewModeId) {
        List<ProjectAppearanceReview> projectAppearanceReviewList = projectAppearanceReviewService.getByProjectAppearanceReviewModeId(
                projectAppearanceReviewModeId);
        if (projectAppearanceReviewList == null || projectAppearanceReviewList.isEmpty()) return null;

        List<ProjectAppearanceReviewUser> projectAppearanceReviewUserList = new ArrayList<>();
        projectAppearanceReviewList.forEach(projectAppearanceReview -> {
            List<ProjectAppearanceReviewUser> temp = projectAppearanceReviewUserService.getByAppearanceModeIdAndNotReviewed(
                    projectAppearanceReviewModeId);
            if (temp != null)
                projectAppearanceReviewUserList.addAll(temp);
        });
        return projectAppearanceReviewUserList;
    }
    @Override
    public List<ProjectMaterialAcceptanceReviewUser> getRemainingNotMaterialAcceptanceReviewedEmployees(String projectMaterialAcceptanceReviewModeId){
        List<ProjectMaterialAcceptanceReview> projectMaterialAcceptanceReviewList = projectMaterialAcceptanceReviewService.getByMaterialAcceptanceReviewModeId(
                projectMaterialAcceptanceReviewModeId);
        if (projectMaterialAcceptanceReviewList == null || projectMaterialAcceptanceReviewList.isEmpty()) return null;
        List<ProjectMaterialAcceptanceReviewUser> projectMaterialAcceptanceReviewUserList = new ArrayList<>();
        projectMaterialAcceptanceReviewList.forEach(projectMaterialAcceptanceReview -> {
            List<ProjectMaterialAcceptanceReviewUser> temp = projectMaterialAcceptanceReviewUserService.getByMaterialAcceptanceModeIdAndNotReviewed(
                    projectMaterialAcceptanceReviewModeId);
            if (temp != null)
                projectMaterialAcceptanceReviewUserList.addAll(temp);

        });
        return projectMaterialAcceptanceReviewUserList;
    }

    /**
     * Todo:删除项目
     *
     * @param project
     */
    @Override
    public void delete(Project project) {
        projectMaterialBusinessService.deleteByProjectId(project.getId());
        projectReviewService.deleteByProjectId(project.getId());
        projectReviewService.deleteByProjectId(project.getId());
        projectReviewModeService.deleteByProjectId(project.getId());
        projectUserService.deleteByProjectId(project.getId());

        projectService.delete(project);
    }

    @Override
    public ProjectView getProjectViewById(String projectId) {
        Project project = projectService.getById(projectId);
        if (project == null) return null;
        ProjectView projectView = new ProjectView();
        projectView.setProject(project);
        projectView.setUser(userService.getById(project.getUserId()));
        projectView.setCompanyConstruction(companyService.getById(project.getCompanyConstructionId()));
        projectView.setCompanyDesignList(projectDesignCompanyService.getCompanyByProjectId(projectId));
        ProjectCompany projectCompany = projectCompanyService.getByProjectId(project.getId());

        //设置监理单位、总包单位
        if (projectCompany != null) {
            projectView.setCompanyGeneralContract(
                    companyService.getById(projectCompany.getGeneralContractorCompanyId()));
            projectView.setCompanySupervision(companyService.getById(projectCompany.getSupervisionCompanyId()));

        }
        return projectView;
    }

    @Override
    public Page<ProjectView> getPageView(int pageNo,
                                         int pageSize) {
        Page<Project> projectPage = projectService.getPage(pageNo, pageSize);
        return convertProjectPage2PageView(projectPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectView> getPageViewByUserId(String userId,
                                                 int pageNo,
                                                 int pageSize) {
        Page<Project> projectPage = projectService.getPageByUserId(userId, pageNo, pageSize);
        return convertProjectPage2PageView(projectPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectView> getPageViewByCompanyConstructionId(String companyConstructionId,
                                                                int pageNo,
                                                                int pageSize) {
        Page<Project> projectPage = projectService.getPageByCompanyConstructionId(companyConstructionId, pageNo,
                                                                                  pageSize);
        return convertProjectPage2PageView(projectPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectView> getCurrentLoginUserInGeneralContractorCompanyNotEndedProjectPageView(
            Integer pageNo,
            Integer pageSize) {
        User user = userService.getCurrentLoginUser();
        if (user == null)
            throw new RuntimeException("用户未登录");

        CompanyUser companyUser = companyUserService.getByUserId(user.getId());
        if (companyUser == null || !companyUserService.isGeneralContractorCompanyByUserId(user.getId()))
            throw new RuntimeException("用户不是总包单位成员");

        Page<Project> projectPage = projectService.getNotEndedProjectPageOfGeneralContractorCompany(
                companyUser.getCompanyId(), pageNo,
                pageSize);
        return convertProjectPage2PageView(projectPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectView> getPageViewByCompanyDesignId(String companyDesignId,
                                                          int pageNo,
                                                          int pageSize) {
        Page<Project> projectPage = projectService.getPageByCompanyDesignId(companyDesignId, pageNo, pageSize);
        return convertProjectPage2PageView(projectPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectView> getPageViewByProjectName(String projectName,
                                                      int pageNo,
                                                      int pageSize) {
        Page<Project> projectPage = projectService.getPageByProjectName(projectName, pageNo, pageSize);
        return convertProjectPage2PageView(projectPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectView> getPageViewByProjectLocation(String projectLocation,
                                                      int pageNo,
                                                      int pageSize) {
        Page<Project> projectPage = projectService.getPageByProjectLocation(projectLocation, pageNo, pageSize);
        return convertProjectPage2PageView(projectPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectView> getPageViewByParams(String name,
                                                 String location,
                                                 BigDecimal totalTaxIncluded,
                                                 BigDecimal totalTaxNotIncluded,
                                                 BigDecimal buildingAreaAboveGround,
                                                 BigDecimal buildingAreaUnderGround,
                                                 String companyConstructionId,
                                                 String companyDesignId,
                                                 String note,
                                                 Date createDatetime,
                                                 Date endDatetime,
                                                      int pageNo,
                                                      int pageSize) {
        Page<Project> projectPage = projectService.getPageByParams(name, location, totalTaxIncluded, totalTaxNotIncluded, buildingAreaAboveGround, buildingAreaUnderGround, companyConstructionId, companyDesignId, note, createDatetime, endDatetime , pageNo, pageSize);
        return convertProjectPage2PageView(projectPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectView> getPageViewByKeyword(String keyword,
                                                  int pageNo,
                                                  int pageSize) {
        Page<Project> projectPage = projectService.getPageByKeyword(keyword, pageNo, pageSize);
        return convertProjectPage2PageView(projectPage, pageNo, pageSize);
    }


    @Override
    public String addFormOfGeneralContractorBrandSelectAndUseMaterial(UseMaterialForm useMaterialForm) {
        if (useMaterialForm == null)
            throw new BusinessException("参数为空");

        User user = userService.getCurrentLoginUser();
        if (user == null)
            throw new BusinessException("用户未登录");

        //检查参数是否正确
        if (!checkUseMaterialForm(useMaterialForm.getUseMaterialFormItems()))
            return null;

        String projectId = useMaterialForm.getProjectId();
        String userId = useMaterialForm.getUserId();

        if (!user.getId().equals(userId))
            throw new BusinessException("用户id与当前用户不一致");

        UseMaterialBrandSelect useMaterialBrandSelect = new UseMaterialBrandSelect();
        useMaterialBrandSelect.setCreateDatetime(new Date());
        useMaterialBrandSelect.setProjectId(projectId);
        useMaterialBrandSelect.setUserId(userId);
        String useMaterialBrandSelectId = useMaterialBrandSelectService.add(useMaterialBrandSelect);
        if (useMaterialBrandSelectId == null)
            throw new BusinessException("添加总包单位人员品牌选择失败");


        //参数正确，可以添加，如果是新建品牌的话，还需要添加附件
        useMaterialForm.getUseMaterialFormItems().forEach(useMaterialFormItem -> {



            //材料
            String materialId = null;
            if(useMaterialFormItem.isMaterialChange()){
                Material material = useMaterialFormItem.getMaterial();
                material.setMaterialProjectBindType(1);//material_project_bind_type,类型：0：原始材料；1：和项目绑定的、基于原始材料修改的
                //需要修改材料
                materialId = materialService.add(material);
                if(materialId == null)
                    throw new BusinessException("材料添加失败");
            }


            String projectMaterialId = useMaterialFormItem.getProjectMaterialId();
            String projectMaterialBrandPublicId = useMaterialFormItem.getProjectMaterialBrandPublicId();
            String projectMaterialBrandPrivateId = useMaterialFormItem.getProjectMaterialBrandPrivateId();
            String brandName = useMaterialFormItem.getBrandName();
            String tempFileDir = useMaterialFormItem.getTempFileDir();

            if(projectMaterialBrandPublicId.isEmpty())
                projectMaterialBrandPublicId = null;
            if(projectMaterialBrandPrivateId.isEmpty())
                projectMaterialBrandPrivateId = null;
            if(brandName.isEmpty())
                brandName = null;

            UseMaterial useMaterial = new UseMaterial();
            useMaterial.setUseMaterialBrandSelectId(useMaterialBrandSelectId);
            useMaterial.setCreateDatetime(new Date());
            useMaterial.setProjectMaterialId(projectMaterialId);
            useMaterial.setUserId(useMaterialForm.getUserId());
            useMaterial.setMaterialId(materialId);
            useMaterial.setMaterialCount(useMaterialFormItem.getProjectMaterial().getMaterialCount());
            useMaterial.setMaterialUnit(useMaterialFormItem.getProjectMaterial().getMaterialUnit());
            useMaterial.setIsAppearance(useMaterialFormItem.getIsAppearance());



            if (projectMaterialBrandPublicId != null || projectMaterialBrandPrivateId != null) {
                //选择一个已有的品牌（公共的或者项目私有的，但是只能有一个）
                if (projectMaterialBrandPublicId != null) {
                    useMaterial.setProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
                    useMaterial.setProjectMaterialBrandPrivateId(null);
                } else {
                    useMaterial.setProjectMaterialBrandPublicId(null);
                    useMaterial.setProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
                }
            } else {
                //新品牌
                useMaterial.setProjectMaterialBrandPublicId(null);
                useMaterial.setProjectMaterialBrandPrivateId(null);
            }



            String useMaterialId = useMaterialService.add(useMaterial);
            if (useMaterialId == null)
                throw new BusinessException("添加使用物料品牌失败");

            //新建品牌
            if (brandName != null) {
                UseMaterialNewBrand useMaterialNewBrand = new UseMaterialNewBrand();


                String materialPosition = useMaterialFormItem.getMaterialPosition();
                String materialClassifyDivisionId = useMaterialFormItem.getMaterialClassifyDivisionId();
                String materialClassifyGroupId = useMaterialFormItem.getMaterialClassifyGroupId();
                String materialClassifySectionId = useMaterialFormItem.getMaterialClassifySectionId();

                useMaterialNewBrand.setMaterialPosition(materialPosition);
                useMaterialNewBrand.setMaterialClassifyDivisionId(materialClassifyDivisionId);
                useMaterialNewBrand.setMaterialClassifyGroupId(materialClassifyGroupId);
                useMaterialNewBrand.setMaterialClassifySectionId(materialClassifySectionId);
                useMaterialNewBrand.setBrandName(brandName);
                useMaterialNewBrand.setUseMaterialId(useMaterialId);

                String useMaterialNewBrandId = useMaterialNewBrandService.add(useMaterialNewBrand);
                if (useMaterialNewBrandId == null)
                    throw new BusinessException("添加使用物料新建品牌失败");

                //添加附件


                String pathTemp = FileUtils.getFilePath(tempFileDir, true);
                String pathDest = FileUtils.getFilePath("", false);
                List<String> listPath = FileUtils.listFiles(pathTemp);
                if (listPath != null && !listPath.isEmpty()) {
                    for (String fileName : listPath) {
                        String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));
                        String newFileNamePath = pathDest + newFileName;
                        if (FileUtils.MoveFile(pathTemp + fileName, newFileNamePath)) {
                            UseMaterialNewBrandFile useMaterialNewBrandFile = new UseMaterialNewBrandFile();
                            useMaterialNewBrandFile.setUseMaterialNewBrandId(useMaterialNewBrandId);
                            useMaterialNewBrandFile.setFilePath(newFileName);
                            useMaterialNewBrandFileService.add(useMaterialNewBrandFile);
                        }
                    }
                }
            }

        });
        return useMaterialBrandSelectId;
    }

    @Override
    public UseMaterialBrandSelectView getUseMaterialBrandSelectViewByCurrentLoginUser(String projectId,
                                                                                      String useMaterialBrandSelectId) {
        return useMaterialBusinessService.getViewByUseMaterialBrandSelectId(useMaterialBrandSelectId);
    }

    @Override
    public List<UseMaterialView> getUseMaterialViewListByUseMaterialBrandSelectId(String useMaterialBrandSelectId) {
        return useMaterialBusinessService.getViewListByUseMaterialBrandSelectId(useMaterialBrandSelectId);
    }

    @Override
    public String addSupervisionCompanyReview(ProjectAppearanceReviewEmployeeForm appearanceReviewForm,
                                              String useMaterialBrandSelectId) {
        User user = userService.getCurrentLoginUser();
        if (user == null)
            throw new BusinessException("当前用户不存在");


        if (appearanceReviewForm == null)
            throw new BusinessException("参数为空");
        ProjectAppearanceReviewUser appearanceReviewUser = appearanceReviewForm.getProjectAppearanceReviewUser();
        if (appearanceReviewUser == null)
            throw new BusinessException("参数为空");

        if (!user.getId().equalsIgnoreCase(appearanceReviewUser.getUserId()))
            throw new BusinessException("当前用户与提交用户不一致");

        String projectId = appearanceReviewForm.getProjectId();
        if (projectId == null)
            throw new BusinessException("参数为空");
        Project project = projectService.getById(projectId);
        if (project == null)
            throw new BusinessException("项目不存在");

        if (useMaterialBrandSelectId == null)
            throw new BusinessException("参数为空");

        UseMaterialBrandSelect useMaterialBrandSelect = useMaterialBrandSelectService.getById(useMaterialBrandSelectId);
        if (useMaterialBrandSelect == null)
            throw new BusinessException("参数为空");


        List<UseMaterial> useMaterialList = useMaterialService.getByUseMaterialBrandSelectId(useMaterialBrandSelectId);
        if (useMaterialList == null || useMaterialList.isEmpty())
            throw new BusinessException("不存在需要审核的品牌、物料等");


        //分派方式为监理单位直接审核
        ProjectAppearanceReviewMode projectAppearanceReviewMode = new ProjectAppearanceReviewMode();
        projectAppearanceReviewMode.setUserId(appearanceReviewUser.getUserId());
        projectAppearanceReviewMode.setMode(
                IProjectReviewModeService.PROJECT_REVIEW_MODE_MANAGER_REVIEW);//监理单位直接审核，不需要分发
        projectAppearanceReviewMode.setUseMaterialBrandSelectId(useMaterialBrandSelectId);
        projectAppearanceReviewMode.setAffectAppearance(
                getAppearanceByUseMaterialBrandSelectId(useMaterialBrandSelectId));//是否影响外观
        String projectAppearanceReviewModeId = projectAppearanceReviewModeService.add(projectAppearanceReviewMode);
        if (projectAppearanceReviewModeId == null)
            throw new BusinessException("添加项目 appearanceReviewMode 失败");

        ProjectAppearanceReview projectAppearanceReview = new ProjectAppearanceReview();
        projectAppearanceReview.setProjectAppearanceReviewModeId(projectAppearanceReviewModeId);
        projectAppearanceReview.setReviewDatetime(new Date());
        projectAppearanceReview.setReviewResult(appearanceReviewUser.getReviewResult() == 2 ?
                                                        IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED :
                                                        IProjectReviewService.PROJECT_REVIEW_RESULT_ACCEPTED);
        projectAppearanceReview.setReviewStatus(IProjectReviewService.PROJECT_REVIEW_STATUS_REVIEWED);
        projectAppearanceReview.setUseMaterialId(null);//暂时把所有的物料当作一个批次，而不是针对每一个物料进行审核
        String projectAppearanceReviewId = projectAppearanceReviewService.add(projectAppearanceReview);
        if (projectAppearanceReviewId == null)
            throw new BusinessException("添加 appearanceReview 失败");

        ProjectAppearanceReviewUser appearanceReviewUser1 = new ProjectAppearanceReviewUser();
        appearanceReviewUser1.setProjectAppearanceReviewId(projectAppearanceReviewId);
        appearanceReviewUser1.setReviewContent(appearanceReviewUser.getReviewContent());
        appearanceReviewUser1.setReviewDatetime(new Date());
        appearanceReviewUser1.setUserId(appearanceReviewUser.getUserId());
        appearanceReviewUser1.setReviewResult(appearanceReviewUser.getReviewResult() == 2 ?
                                                      IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED :
                                                      IProjectReviewService.PROJECT_REVIEW_RESULT_ACCEPTED);
        String appearanceReviewUserId = projectAppearanceReviewUserService.add(appearanceReviewUser1);
        if (appearanceReviewUserId == null)
            throw new BusinessException("添加 appearanceReviewUser 失败");

        //添加附件
        String tempFileDir = appearanceReviewForm.getReviewTempDir();
        String pathTemp = FileUtils.getFilePath(tempFileDir, true);
        String pathDest = FileUtils.getFilePath("", false);
        List<String> listPath = FileUtils.listFiles(pathTemp);
        if (listPath != null && !listPath.isEmpty()) {
            for (String fileName : listPath) {
                String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));
                String newFileNamePath = pathDest + newFileName;
                if (FileUtils.MoveFile(pathTemp + fileName, newFileNamePath)) {
                    ProjectAppearanceReviewUserFile projectAppearanceReviewUserFile = new ProjectAppearanceReviewUserFile();
                    projectAppearanceReviewUserFile.setProjectAppearanceReviewUserId(appearanceReviewUserId);
                    projectAppearanceReviewUserFile.setFilePath(newFileName);
                    projectAppearanceReviewUserFileService.add(projectAppearanceReviewUserFile);
                }
            }
        }

        return projectAppearanceReviewModeId;
    }

    /**
     * 得到审核的品牌、材料是外观审核，还是非外观审核
     *
     * @param projectAppearanceReviewModeId
     * @return 0: 不影响外观审核，1：影响外观审核
     */
    @Override
    public int getAppearanceTypeByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId) {
        ProjectAppearanceReviewMode projectAppearanceReviewMode = projectAppearanceReviewModeService.getById(
                projectAppearanceReviewModeId);
        if (projectAppearanceReviewMode == null)
            throw new BusinessException("参数为空");

        UseMaterialBrandSelect useMaterialBrandSelect = useMaterialBrandSelectService.getById(
                projectAppearanceReviewMode.getUseMaterialBrandSelectId());
        if (useMaterialBrandSelect == null)
            throw new BusinessException("参数为空");

        List<UseMaterial> useMaterialList = useMaterialService.getByUseMaterialBrandSelectId(
                useMaterialBrandSelect.getId());
        if (useMaterialList == null || useMaterialList.isEmpty())
            throw new BusinessException("不存在需要审核的品牌、物料等");
        return useMaterialList.getFirst().getIsAppearance();

    }

    @Override
    public String submitSupervisionCompanySubmitProjectMaterialBatchAcceptanceReview(ProjectMaterialAcceptanceReviewEmployeeForm projectMaterialAcceptanceReviewEmployeeForm,
                                                                                     String projectMaterialAcceptanceBatchId) {

        User user = userService.getCurrentLoginUser();
        if (user == null)
            throw new BusinessException("当前用户不存在");


        if (projectMaterialAcceptanceReviewEmployeeForm == null)
            throw new BusinessException("参数为空");
        ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser = projectMaterialAcceptanceReviewEmployeeForm.getProjectMaterialAcceptanceReviewUser();
        if (projectMaterialAcceptanceReviewUser == null)
            throw new BusinessException("参数为空");

        if (!user.getId().equalsIgnoreCase(projectMaterialAcceptanceReviewUser.getUserId()))
            throw new BusinessException("当前用户与提交用户不一致");

        ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch = projectMaterialAcceptanceBatchService.getById(
                projectMaterialAcceptanceBatchId);
        if (projectMaterialAcceptanceBatch == null)
            throw new BusinessException("参数非法");


        //分派方式为监理单位直接审核
        ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode = new ProjectMaterialAcceptanceReviewMode();
        projectMaterialAcceptanceReviewMode.setUserId(user.getId());
        projectMaterialAcceptanceReviewMode.setMode(
                IProjectReviewModeService.PROJECT_REVIEW_MODE_MANAGER_REVIEW);//监理单位直接审核，不需要分发
        projectMaterialAcceptanceReviewMode.setCreateDatetime(new Date());
        projectMaterialAcceptanceReviewMode.setProjectMaterialAcceptanceBatchId(projectMaterialAcceptanceBatchId);


        String projectMaterialAcceptanceReviewModeId = projectMaterialAcceptanceReviewModeService.add(
                projectMaterialAcceptanceReviewMode);
        if (projectMaterialAcceptanceReviewModeId == null)
            throw new BusinessException("添加失败");

        ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview = new ProjectMaterialAcceptanceReview();
        projectMaterialAcceptanceReview.setProjectMaterialAcceptanceModeId(projectMaterialAcceptanceReviewModeId);
        projectMaterialAcceptanceReview.setReviewDatetime(new Date());
        projectMaterialAcceptanceReview.setReviewResult(projectMaterialAcceptanceReviewUser.getReviewResult() == 0 ?
                                                                IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED :
                                                                IProjectReviewService.PROJECT_REVIEW_RESULT_ACCEPTED);

        projectMaterialAcceptanceReview.setReviewStatus(IProjectReviewService.PROJECT_REVIEW_STATUS_REVIEWED);
        String projectMaterialAcceptanceReviewId = projectMaterialAcceptanceReviewService.add(
                projectMaterialAcceptanceReview);
        if (projectMaterialAcceptanceReviewId == null)
            throw new BusinessException("添加  失败");

        ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser1 = new ProjectMaterialAcceptanceReviewUser();
        projectMaterialAcceptanceReviewUser1.setProjectMaterialAcceptanceReviewId(projectMaterialAcceptanceReviewId);
        projectMaterialAcceptanceReviewUser1.setReviewContent(projectMaterialAcceptanceReviewUser.getReviewContent());
        projectMaterialAcceptanceReviewUser1.setReviewDatetime(new Date());
        projectMaterialAcceptanceReviewUser1.setUserId(projectMaterialAcceptanceReviewUser.getUserId());
        projectMaterialAcceptanceReviewUser1.setReviewResult(
                projectMaterialAcceptanceReviewUser.getReviewResult() == 2 ?
                        IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED :
                        IProjectReviewService.PROJECT_REVIEW_RESULT_ACCEPTED);
        String projectMaterialAcceptanceReviewUserId = projectMaterialAcceptanceReviewUserService.add(
                projectMaterialAcceptanceReviewUser1);
        if (projectMaterialAcceptanceReviewUserId == null)
            throw new BusinessException("添加  失败");

        //添加附件
        String tempFileDir = projectMaterialAcceptanceReviewEmployeeForm.getReviewTempDir();
        String pathTemp = FileUtils.getFilePath(tempFileDir, true);
        String pathDest = FileUtils.getFilePath("", false);
        List<String> listPath = FileUtils.listFiles(pathTemp);
        if (listPath != null && !listPath.isEmpty()) {
            for (String fileName : listPath) {
                String newFileName = GUID.getGUID() + fileName.substring(fileName.lastIndexOf("."));
                String newFileNamePath = pathDest + newFileName;
                if (FileUtils.MoveFile(pathTemp + fileName, newFileNamePath)) {
                    ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile = new ProjectMaterialAcceptanceReviewUserFile();
                    projectMaterialAcceptanceReviewUserFile.setProjectMaterialAcceptanceReviewUserId(
                            projectMaterialAcceptanceReviewUserId);
                    projectMaterialAcceptanceReviewUserFile.setFilePath(newFileName);
                    projectMaterialAcceptanceReviewUserFileService.add(projectMaterialAcceptanceReviewUserFile);
                }
            }
        }

        return projectMaterialAcceptanceReviewModeId;


    }

    @Override
    public ProjectReviewStatistics getStatisticsOfAppearanceReviewUserViewByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId) {
        List<ProjectAppearanceReview> projectAppearanceReviewList = projectAppearanceReviewService.getByProjectAppearanceReviewModeId(
                projectAppearanceReviewModeId);
        if (projectAppearanceReviewList == null || projectAppearanceReviewList.isEmpty())
            return null;

        List<ProjectAppearanceReviewUser> projectAppearanceReviewUserList = new ArrayList<>();
        for (ProjectAppearanceReview projectAppearanceReview : projectAppearanceReviewList) {
            List<ProjectAppearanceReviewUser> list = projectAppearanceReviewUserService.getByProjectAppearanceReviewId(
                    projectAppearanceReview.getId());
            if (list != null && !list.isEmpty())
                projectAppearanceReviewUserList.addAll(list);
        }

        if (projectAppearanceReviewUserList.isEmpty())
            return null;


        ProjectReviewStatistics projectReviewStatistics = new ProjectReviewStatistics();
        projectAppearanceReviewUserList.forEach(projectAppearanceReviewUser -> {
            if (projectAppearanceReviewUser.getReviewResult() == IProjectReviewService.PROJECT_REVIEW_RESULT_ACCEPTED)
                projectReviewStatistics.setReviewResultAccept(projectReviewStatistics.getReviewResultAccept() + 1);
            else if (projectAppearanceReviewUser.getReviewResult() == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED)
                projectReviewStatistics.setReviewResultReject(projectReviewStatistics.getReviewResultReject() + 1);
            else if (projectAppearanceReviewUser.getReviewResult() == IProjectReviewService.PROJECT_REVIEW_RESULT_UNKNOWN)
                projectReviewStatistics.setReviewResultUnreviewed(
                        projectReviewStatistics.getReviewResultUnreviewed() + 1);
        });


        return projectReviewStatistics;
    }
       @Override
    public ProjectReviewStatistics getStatisticsOfEngineeringDepartmentMaterialAcceptanceReviewUserViewByReviewModeId(String projectMaterialAcceptanceReviewModeId) {
        List<ProjectMaterialAcceptanceReview> projectMaterialAcceptanceReviewList = projectMaterialAcceptanceReviewService.getByMaterialAcceptanceReviewModeId(
                projectMaterialAcceptanceReviewModeId);
        if (projectMaterialAcceptanceReviewList == null || projectMaterialAcceptanceReviewList.isEmpty())
            return null;
        List<ProjectMaterialAcceptanceReviewUser> projectMaterialAcceptanceReviewUserList = new ArrayList<>();
        for (ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview : projectMaterialAcceptanceReviewList) {
            List<ProjectMaterialAcceptanceReviewUser> list = projectMaterialAcceptanceReviewUserService.getByProjectMaterialAcceptanceReviewId(
                    projectMaterialAcceptanceReview.getId());
            if (list != null && !list.isEmpty())
                projectMaterialAcceptanceReviewUserList.addAll(list);
        }
        if (projectMaterialAcceptanceReviewUserList.isEmpty())
            return null;
        ProjectReviewStatistics projectReviewStatistics = new ProjectReviewStatistics();

        projectMaterialAcceptanceReviewUserList.forEach(projectAppearanceReviewUser -> {
            if (projectAppearanceReviewUser.getReviewResult() == IProjectReviewService.PROJECT_REVIEW_RESULT_ACCEPTED)
                projectReviewStatistics.setReviewResultAccept(projectReviewStatistics.getReviewResultAccept() + 1);
            else if (projectAppearanceReviewUser.getReviewResult() == IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED)
                projectReviewStatistics.setReviewResultReject(projectReviewStatistics.getReviewResultReject() + 1);
            else if (projectAppearanceReviewUser.getReviewResult() == IProjectReviewService.PROJECT_REVIEW_RESULT_UNKNOWN)
                projectReviewStatistics.setReviewResultUnreviewed(
                        projectReviewStatistics.getReviewResultUnreviewed() + 1);
        });


        return projectReviewStatistics;
    }

    @Override
    public ProjectReviewStatistics getStatisticsOfProjectMaterialReviewUserViewByProjectReviewId(String projectReviewId) {
        return projectReviewBusinessService.getStatisticsOfProjectReviewUserViewByProjectReviewId(projectReviewId);
    }
    public ProjectStatisticalAnalysis getStatisticsOfProjectViewByProjectId(String projectId){
        int sum = 0;
        int acceptReviewSum = 0;
        int rejectReviewSum = 0;
      Project project = projectService.getById(projectId);
      String projectName = project.getName();
      List<ProjectEnd> projectEnd =  projectEndService.getByProjectId(projectId);


            int projectReviewSum =  projectReviewUserService.getCountByProjectId(projectId);
      int projectReviewAccept = projectReviewUserService.getCountByProjectIdAndResult(projectId,IProjectReviewService.PROJECT_REVIEW_RESULT_ACCEPTED);
      int projectReviewReject = projectReviewUserService.getCountByProjectIdAndResult(projectId,IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED);
      sum += projectReviewSum;
      acceptReviewSum += projectReviewAccept;
      rejectReviewSum += projectReviewReject;
      int projectAppearanceReviewSum = projectAppearanceReviewUserService.getCountByProjectId(projectId);
      int projectAppearanceReviewAccept = projectAppearanceReviewUserService.getCountByProjectIdAndResult(projectId,
              IProjectReviewService.PROJECT_REVIEW_RESULT_ACCEPTED);
      int projectAppearanceReviewReject = projectAppearanceReviewUserService.getCountByProjectIdAndResult(projectId,
              IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED);
      sum += projectAppearanceReviewSum;
      acceptReviewSum += projectAppearanceReviewAccept;
      rejectReviewSum += projectAppearanceReviewReject;
      int projectMaterialAcceptanceReviewSum = projectMaterialAcceptanceReviewUserService.getCountByProjectId(projectId);
      int projectMaterialAcceptanceReviewAccept = projectMaterialAcceptanceReviewUserService.getCountByProjectIdAndResult(projectId,
              IProjectReviewService.PROJECT_REVIEW_RESULT_ACCEPTED);
      int projectMaterialAcceptanceReviewReject = projectMaterialAcceptanceReviewUserService.getCountByProjectIdAndResult(projectId,
              IProjectReviewService.PROJECT_REVIEW_RESULT_REJECTED);
      sum += projectMaterialAcceptanceReviewSum;
      acceptReviewSum += projectMaterialAcceptanceReviewAccept;
      rejectReviewSum += projectMaterialAcceptanceReviewReject;

      ProjectStatisticalAnalysis projectStatisticalAnalysis = new ProjectStatisticalAnalysis();
      projectStatisticalAnalysis.setTotalReviewResult(sum);
      projectStatisticalAnalysis.setProjectName(projectName);
        if (projectEnd == null){
            projectStatisticalAnalysis.setCheckProjectWhetherEnd(false);}
        else projectStatisticalAnalysis.setCheckProjectWhetherEnd(true);
      projectStatisticalAnalysis.setTotalReviewResultAgree(acceptReviewSum);
      projectStatisticalAnalysis.setTotalReviewResulDisagree(rejectReviewSum);
      return projectStatisticalAnalysis;
    }

    @Override
    public Page<ProjectAppearanceReviewUserView> getPageProjectAppearanceReviewUserViewByTaskId(String projectAppearanceReviewModeId,
                                                                                                Integer pageNo,
                                                                                                Integer pageSize) {

        return projectAppearanceBusinessService.getViewPageByProjectAppearanceReviewModeId(
                projectAppearanceReviewModeId, pageNo, pageSize);

    }
    @Override
    public Page<ProjectMaterialAcceptanceReviewUserView> getPageProjectMaterialAcceptanceReviewUserViewByTaskId(String projectMaterialAcceptanceReviewModeId,
                                                                                                                Integer pageNo,
                                                                                                                 Integer pageSize){
        return projectMaterialAcceptanceBusinessService.getViewPageByProjectMaterialAcceptanceReviewModeId(
                projectMaterialAcceptanceReviewModeId, pageNo, pageSize);
    }

    @Override
    public List<ProjectAppearanceReviewUserView> getListOfProjectAppearanceReviewUserViewByTaskId(String projectAppearanceReviewModeId) {
        return projectAppearanceBusinessService.getViewListByProjectAppearanceReviewModeId(
                projectAppearanceReviewModeId);
    }
    @Override
    public List<ProjectReviewUserView> getProjectReviewUserViewListByProjectIdAndTaskId(String projectReviewId) {
        return projectReviewBusinessService.getProjectReviewUserViewListByProjectIdAndTaskId(projectReviewId);
    }

    @Override
    public boolean checkDesignDepartmentManagerExists(String projectId) {
        return projectUserService.getManagerOfDesignDepartment(projectId) != null;
    }

    @Override
    public boolean checkEngineeringDepartmentManagerExists(String projectId) {
        return projectUserService.getManagerOfEngineeringDepartment(projectId) != null;
    }

    @Override
    public boolean checkDesignCompanyEmployeeExists(String projectId) {
        List<ProjectUser> projectUserDesignCompanyList = projectUserService.getDesignCompanyEmployees(projectId);
        return projectUserDesignCompanyList != null && !projectUserDesignCompanyList.isEmpty();
    }

    @Override
    public boolean checkSupervisionCompanyEmployeeExists(String projectId) {
        List<ProjectUser> projectUserDesignCompanyList = projectUserService.getSupervisionCompanyEmployees(projectId);
        return projectUserDesignCompanyList != null && !projectUserDesignCompanyList.isEmpty();
    }

    @Override
    public boolean checkGeneralContractorCompanyEmployeeExists(String projectId) {
        List<ProjectUser> projectUserDesignCompanyList = projectUserService.getGeneralContractorCompanyEmployees(
                projectId);
        return projectUserDesignCompanyList != null && !projectUserDesignCompanyList.isEmpty();
    }

    @Override
    public boolean isCurrentLoginUserInEngineeringDepartment() {
        User currentUser = userService.getCurrentLoginUser();
        if (currentUser == null) return false;
        return companyUserService.isEngineeringDepartmentByUserId(currentUser.getId());
    }

    @Override
    public boolean isCurrentLoginUserInDesignDepartment() {
        User currentUser = userService.getCurrentLoginUser();
        if (currentUser == null) return false;
        return companyUserService.isDesignDepartmentByUserId(currentUser.getId());
    }

    @Override
    public Page<UseMaterialView> getReviewedAndApprovedUseMaterialViewPageByProjectId(String projectId,
                                                                                      Integer pageNo,
                                                                                      Integer pageSize) {


        return useMaterialBusinessService.getReviewedAndApprovedPageViewByProjectId(projectId, pageNo, pageSize);


    }

    /**
     * 总包单位提交了品牌选择、物料使用申请后，经过层层审批，已经获得通过，将这些通过的新品牌增加到项目私有品牌中
     *
     * @param projectId
     */
    @Override
    public void setProjectReviewedNewBrandByProjectId(String projectId) {
        List<UseMaterial> useMaterialList = useMaterialService.getReviewedAndApprovedListByProjectId(projectId);
        if (useMaterialList == null || useMaterialList.isEmpty()) return;
        for (UseMaterial useMaterial : useMaterialList) {


            /**
             * 处理更改的材料
             * 总包公司提交修改后的材料，经过层层审批后，材料获得同意。此时需要将此材料更新到项目中，后续使用该材料进行采购
             */
            String materialId = useMaterial.getMaterialId();
            if(materialId!=null){
                Material material= materialService.getById(materialId);
                String projectMaterialId =useMaterial.getProjectMaterialId();
                ProjectMaterial projectMaterial = projectMaterialService.getById(projectMaterialId);
                ProjectMaterial projectMaterialOrigin= projectMaterialService.getByProjectIdAndMaterialOriginId(projectId,projectMaterial.getMaterialOriginId());
                if(projectMaterialOrigin!=null) {
                    projectMaterialId = projectMaterialOrigin.getId();
                    projectMaterial.setId(projectMaterialId);
                    projectMaterialService.update(projectMaterialId,materialId,useMaterial.getMaterialCount(),projectMaterial.getMaterialUnit());
                }
                else
                    projectMaterialId =projectMaterialService.add(projectMaterial);
                if (projectMaterialId == null)
                    throw new BusinessException("项目物料添加失败");


                String companyId = projectMaterial.getCompanyId();


                    User user = userService.getById(useMaterial.getUserId());
                    if (user == null)
                        throw new BusinessException("用户未登录，添加失败");
                    CompanyUser companyUser = companyUserService.getByUserId(user.getId());
                    if (companyUser == null)
                        throw new BusinessException("用户未加入任何公司，添加失败");

                    companyId = companyUser.getCompanyId();




                ProjectMaterialHistory projectMaterialHistory = new ProjectMaterialHistory();
                projectMaterialHistory.setProjectMaterialId(projectMaterialId);
                projectMaterialHistory.setProjectId(projectMaterial.getProjectId());
                projectMaterialHistory.setMaterialId(materialId);
                projectMaterialHistory.setCompanyId(companyId);
                projectMaterialHistory.setReviewed(IProjectReviewService.PROJECT_REVIEW_RESULT_UNKNOWN);
                projectMaterialHistoryService.add(projectMaterialHistory);
            }


            //不是新品牌，则不需要处理
            if (useMaterial.getProjectMaterialBrandPrivateId() != null || useMaterial.getProjectMaterialBrandPublicId() != null)
                continue;

            List<UseMaterialNewBrand> useMaterialNewBrandList = useMaterialNewBrandService.getByUseMaterialId(
                    useMaterial.getId());

            if (useMaterialNewBrandList == null || useMaterialNewBrandList.isEmpty())
                continue;


            for (UseMaterialNewBrand useMaterialNewBrand : useMaterialNewBrandList) {
                Brand brand =null;
            /*    Brand brand = brandService.getByNameAndMaterialClassifySectionIdAndPosition(
                        useMaterialNewBrand.getBrandName(),
                        useMaterialNewBrand.getMaterialClassifySectionId(),
                        useMaterialNewBrand.getMaterialPosition());*/


                Brand brandTemp = new Brand();
                brandTemp.setName(useMaterialNewBrand.getBrandName());
                brandTemp.setPosition(useMaterialNewBrand.getMaterialPosition());
                brandTemp.setMaterialClassifySectionId(useMaterialNewBrand.getMaterialClassifySectionId());
                String brandId = brandService.add(brandTemp);


                if (brandId == null)
                    continue;


                ProjectBrand projectBrand = projectBrandService.getByProjectIdAndBrandId(projectId, brandId);
                String projectBrandId;
                if (projectBrand == null) {
                    projectBrand = new ProjectBrand();
                    projectBrand.setBrandId(brandId);
                    projectBrand.setProjectId(projectId);
                    projectBrandId = projectBrandService.add(projectBrand);
                } else projectBrandId = projectBrand.getId();

                if (projectBrandId == null)
                    continue;


                ProjectMaterialBrandPrivate projectMaterialBrandPrivate =
                        projectMaterialBrandPrivateService.getByProjectMaterialIdAndProjectBrandId(
                                useMaterial.getProjectMaterialId(), projectBrandId);

                String projectMaterialBrandPrivateId;

                //新建项目私有品牌
                if (projectMaterialBrandPrivate == null) {
                    projectMaterialBrandPrivate = new ProjectMaterialBrandPrivate();
                    projectMaterialBrandPrivate.setProjectBrandId(projectBrandId);
                    projectMaterialBrandPrivate.setProjectMaterialId(
                            useMaterial.getProjectMaterialId());
                    projectMaterialBrandPrivateId = projectMaterialBrandPrivateService.add(
                            projectMaterialBrandPrivate);


                } else projectMaterialBrandPrivateId = projectMaterialBrandPrivate.getId();

                //更新物料使用申请中新建品牌的项目私有品牌id
                if (projectMaterialBrandPrivateId != null) {
                    useMaterialNewBrandService.updateProjectMaterialBrandPrivateId(useMaterialNewBrand.getId(),
                                                                                   projectMaterialBrandPrivateId);
                }


            }
        }
    }

    @Override
    public Page<ProjectReviewUserView> getProjectMaterialUserUserReViewPageByTaskId(String projectReviewId,
                                                                                    String reviewUser,
                                                                                    int reviewResult,
                                                                                    Integer pageNo,
                                                                                    Integer pageSize) {
        return projectReviewBusinessService.getPageViewByProjectReviewId(projectReviewId, reviewUser, reviewResult, pageNo, pageSize);
    }

    /**
     * 添加购买的物料
     * @param buyMaterialForm
     * @return
     */
    @Override
    public String addFormOfGeneralContractorBuyMaterialSelect(BuyMaterialForm buyMaterialForm){
        if (buyMaterialForm == null || buyMaterialForm.getBuyMaterials().length == 0)
            throw new BusinessException("参数为空");

        User user = userService.getCurrentLoginUser();
        if (user == null)
            throw new BusinessException("用户未登录，添加失败");

        //增加订购批次（一个批次可以包含多个品种的物料）
        BuyMaterialBatch buyMaterialBatch = new BuyMaterialBatch();
        buyMaterialBatch.setUserId(user.getId());
        buyMaterialBatch.setProjectId(buyMaterialForm.getProjectId());
        buyMaterialBatch.setCreateDatetime(new Date());
        String buyMaterialBatchId = buyMaterialBatchService.add(buyMaterialBatch);

        if (buyMaterialBatchId == null)
            throw new BusinessException("添加失败");

        List<String> buyMaterialIds = new ArrayList<>();
        for (BuyMaterial buyMaterial : buyMaterialForm.getBuyMaterials()) {
            UseMaterial useMaterial = useMaterialService.getById(buyMaterial.getUseMaterialId());
            if (useMaterial == null) {
                throw new BusinessException("不存在该物料使用申请");
            }

            if ((buyMaterial.getProjectMaterialBrandPrivateId() == null || buyMaterial.getProjectMaterialBrandPrivateId().isEmpty())
                    && (buyMaterial.getProjectMaterialBrandPublicId() == null || buyMaterial.getProjectMaterialBrandPublicId().isEmpty())) {
                throw new BusinessException("请选择品牌");
            }

            if (buyMaterial.getProjectMaterialBrandPrivateId() == null || buyMaterial.getProjectMaterialBrandPrivateId().isEmpty()) {
                buyMaterial.setProjectMaterialBrandPrivateId(null);
            } else {
                buyMaterial.setProjectMaterialBrandPublicId(null);
            }

            if (!buyMaterial.getUserId().equalsIgnoreCase(user.getId())) {
                throw new BusinessException("用户信息错误");
            }

            if (buyMaterial.getMaterialCount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new BusinessException("数量必须大于0");
            }

            if (buyMaterial.getMaterialUnit() == null || buyMaterial.getMaterialUnit().isEmpty()) {
                throw new BusinessException("数量单位为空");
            }

            buyMaterial.setBuyMaterialBatchId(buyMaterialBatchId); // 关联批次 ID

            String buyMaterialId = buyMaterialService.add(buyMaterial);
            buyMaterialIds.add(buyMaterialId);
        }
        return buyMaterialBatchId;
    }

    @Override
    public List<String> addFormOfGeneralContractorBuyProjectMaterial(BuyMaterial[] buyMaterials) {
        if (buyMaterials == null || buyMaterials.length == 0) return null;
        User user = userService.getCurrentLoginUser();
        List<String> result = new ArrayList<>();
        for (BuyMaterial buyMaterial : buyMaterials) {
            UseMaterial useMaterial = useMaterialService.getById(buyMaterial.getUseMaterialId());
            if (useMaterial == null)
                throw new BusinessException("不存在该物料使用申请");
            if ((buyMaterial.getProjectMaterialBrandPrivateId() == null || buyMaterial.getProjectMaterialBrandPrivateId().isEmpty())
                    && (buyMaterial.getProjectMaterialBrandPublicId() == null || buyMaterial.getProjectMaterialBrandPublicId().isEmpty()))
                throw new BusinessException("请选择品牌");

            if (buyMaterial.getProjectMaterialBrandPrivateId() == null || buyMaterial.getProjectMaterialBrandPrivateId().isEmpty())
                buyMaterial.setProjectMaterialBrandPrivateId(null);
            else
                buyMaterial.setProjectMaterialBrandPublicId(null);
            if (!buyMaterial.getUserId().equalsIgnoreCase(user.getId()))
                throw new BusinessException("用户信息错误");
            if (buyMaterial.getMaterialCount().compareTo(BigDecimal.ZERO) <= 0)
                throw new BusinessException("数量必须大于0");
            if (buyMaterial.getMaterialUnit() == null || buyMaterial.getMaterialUnit().isEmpty())
                throw new BusinessException("数量单位为空");

            String buyMaterialId = buyMaterialService.add(buyMaterial);
            result.add(buyMaterialId);
        }
        return result;
    }

    @Override
    public Page<BuyMaterialView> getBuyMaterialViewPageByProjectId(String projectId,
                                                                   Integer pageNo,
                                                                   Integer pageSize) {
        Page<BuyMaterial> page = buyMaterialService.getPageByProjectId(projectId, pageNo, pageSize);
        return convertBuyMaterialPage2PageView(page, pageNo, pageSize);
    }

    @Override
    public Page<ProjectMaterialVerificationDocumentView> getPageDocumentViewByProjectId(String projectId,
                                                                                        Integer pageNo,
                                                                                        Integer pageSize) {
        Page<BuyMaterial> page = buyMaterialService.getPageByProjectId(projectId, pageNo, pageSize);
        return convertBuyMaterialPage2ProjectMaterialVerificationDocumentViewPageView(page, pageNo, pageSize);
    }

    @Override
    public Page<ProjectMaterialVerificationDocumentView> getPageReCheckIsRequiredByProjectId(String projectId,
                                                                                             Integer pageNo,
                                                                                             Integer pageSize) {
        Page<BuyMaterial> page = buyMaterialService.getPageReCheckIsRequiredByProjectId(projectId, pageNo, pageSize);
        return convertBuyMaterialPage2ProjectMaterialVerificationDocumentViewPageView(page, pageNo, pageSize);
    }

    @Override
    public UseMaterialForm splitUseMaterialFormByIsAppearance(UseMaterialForm useMaterialForm,
                                                              int isAppearance) {
        if (useMaterialForm == null) return null;
        UseMaterialForm newUseMaterialForm = new UseMaterialForm();
        newUseMaterialForm.setProjectId(useMaterialForm.getProjectId());
        newUseMaterialForm.setTaskId(useMaterialForm.getTaskId());
        newUseMaterialForm.setUserId(useMaterialForm.getUserId());

        //根据isAppearance进行分组
        newUseMaterialForm.setUseMaterialFormItems(useMaterialForm.getUseMaterialFormItems().stream().filter(
                useMaterialFormItem -> useMaterialFormItem.getIsAppearance() == isAppearance).collect(
                Collectors.toList()));
        return newUseMaterialForm;

    }

    @Override
    public List<ProjectMaterialVerificationDocumentView> getListMaterialVerificationDocumentViewByBuyMaterialId(String buyMaterialId) {
        return getProjectMaterialVerificationDocumentViewByBuyMaterialId(buyMaterialId);
    }

    @Override
    public List<ProjectMaterialVerificationDocumentView> getListProjectMaterialVerificationDocumentViewOfReCheckIsRequiredByBuyMaterialId(String buyMaterialId) {
        return getProjectMaterialVerificationDocumentViewByBuyMaterialId(buyMaterialId);

    }

    @Override
    public List<ProjectMaterialVerificationDocumentView> getListMaterialVerificationDocumentViewByBuyMaterialBatchId(String buyMaterialBatchId) {
        List<BuyMaterial> buyMaterialList = buyMaterialService.getByBuyMaterialBatchId(buyMaterialBatchId);

        if(buyMaterialList == null || buyMaterialList.isEmpty())
            return null;

        List<ProjectMaterialVerificationDocumentView> result = new ArrayList<>();
        for (BuyMaterial buyMaterial : buyMaterialList) {
            List<ProjectMaterialVerificationDocument> projectMaterialVerificationDocumentList = projectMaterialVerificationDocumentService.getByBuyMaterialId(
                    buyMaterial.getId());

            if (projectMaterialVerificationDocumentList == null || projectMaterialVerificationDocumentList.isEmpty()) {
                ProjectMaterialVerificationDocumentView projectMaterialVerificationDocumentView = new ProjectMaterialVerificationDocumentView();
                BuyMaterialView buyMaterialView = getBuyMaterialViewByBuyMaterialId(buyMaterial.getId());
                if (buyMaterialView == null) return null;

                projectMaterialVerificationDocumentView.setBuyMaterialView(buyMaterialView);
                projectMaterialVerificationDocumentView.setUser(userService.getCurrentLoginUser());
                projectMaterialVerificationDocumentView.setProjectMaterialVerificationDocument(null);
                projectMaterialVerificationDocumentView.setProjectMaterialVerificationDocumentFileList(null);
                result.add( projectMaterialVerificationDocumentView);
            } else {

                projectMaterialVerificationDocumentList.forEach(projectMaterialVerificationDocument -> {
                    ProjectMaterialVerificationDocumentView projectMaterialVerificationDocumentView = new ProjectMaterialVerificationDocumentView();
                    projectMaterialVerificationDocumentView.setUser(
                            userService.getById(projectMaterialVerificationDocument.getUserId()));
                    projectMaterialVerificationDocumentView.setProjectMaterialVerificationDocument(
                            projectMaterialVerificationDocument);
                    projectMaterialVerificationDocumentView.setProjectMaterialVerificationDocumentFileList(
                            projectMaterialVerificationDocumentFileService.getByProjectMaterialVerificationDocumentId(
                                    projectMaterialVerificationDocument.getId()));
                    projectMaterialVerificationDocumentView.setBuyMaterialView(
                            getBuyMaterialViewByBuyMaterialId(buyMaterial.getId()));
                    result.add(projectMaterialVerificationDocumentView);

                });

            }
        }

        return result;



    }

    @Override
    public List<ProjectMaterialVerificationDocumentView> getListProjectMaterialVerificationDocumentViewOfReCheckIsRequiredByBuyMaterialBatchId(String buyMaterialBatchId) {
        return getListMaterialVerificationDocumentViewByBuyMaterialBatchId(buyMaterialBatchId);
    }

    @Override
    public Page<ProjectMaterialView> getBoughtMaterialViewPageByProjectId(String projectId,
                                                                          Integer pageNo,
                                                                          Integer pageSize) {

        Page<String> projectMaterialIdPage = buyMaterialService.getBoughtMaterialIdPageByProjectId(projectId, pageNo,
                                                                                                   pageSize);
        return convertProjectMaterialIdPage2ProjectMaterialViewPage(projectMaterialIdPage, pageNo, pageSize);
    }

    @Override
    public Page<ProjectMaterialAcceptanceView> getProjectMaterialAcceptanceViewListByCurrentLoginUser(String projectMaterialAcceptanceBatchId,
                                                                                                      Integer pageNo,
                                                                                                      Integer pageSize) {

        return projectMaterialAcceptanceBusinessService.getPageViewByProjectMaterialAcceptanceBatchId(
                projectMaterialAcceptanceBatchId, pageNo, pageSize);

    }

    @Override
    public ProjectReviewUserView getFeedbackOfProjectMaterialReviewedByReviewId(String projectReviewId) {
        ProjectReview projectReview = projectReviewService.getById(projectReviewId);
        if (projectReview == null)
            return null;


        //获得当前项目的设计部项目经理
        String projectId = projectReview.getProjectId();
        ProjectUser designDepartmentManager = projectUserService.getManagerOfDesignDepartment(projectId);

        return projectReviewBusinessService.getViewByProjectReviewIdAndUserId(projectReviewId,
                                                                              designDepartmentManager.getUserId());


    }

    @Override
    public Page<ProjectMaterialView> getProjectMaterialPageViewByProjectIdAndCompanyId(String projectId,
                                                                                       String companyId,
                                                                                       String name,
                                                                                       String location,
                                                                                       String itemMark,
                                                                                       String technology,
                                                                                       String installation,
                                                                                       String brandPublic,
                                                                                       String brandPrivate,
                                                                                       Integer pageNo,
                                                                                       Integer pageSize) {

        return projectMaterialBusinessService.getProjectMaterialPageViewByProjectIdAndCompanyId(projectId, companyId,
                                                                                                name, location, itemMark, technology, installation, brandPublic, brandPrivate,
                                                                                                pageNo, pageSize);

    }

    @Override
    public ProjectAppearanceReviewUserView getFeedbackOfSelectBrandReviewedByReviewId(String projectAppearanceReviewModeId) {
        ProjectAppearanceReviewMode projectAppearanceReviewMode = projectAppearanceReviewModeService.getById(
                projectAppearanceReviewModeId);
        if (projectAppearanceReviewMode == null) return null;

        //根据ModeId找到reviewId，两者之间1：1
        List<ProjectAppearanceReview> projectAppearanceReviewList = projectAppearanceReviewService.getByProjectAppearanceReviewModeId(
                projectAppearanceReviewModeId);
        if (projectAppearanceReviewList == null || projectAppearanceReviewList.isEmpty()) return null;

        ProjectAppearanceReview projectAppearanceReview = projectAppearanceReviewList.getFirst();
        String projectAppearanceReviewId = projectAppearanceReview.getId();

        //根据reviewId找到对应的用户review
        List<ProjectAppearanceReviewUser> projectAppearanceReviewUserList = projectAppearanceReviewUserService.getByProjectAppearanceReviewId(
                projectAppearanceReviewId);

        //如果只有一条反馈意见，则只返回一个；
        // 如果有多个 ，如果存在项目经理，则返回项目经理的意见，否则返回第一个意见
        if (projectAppearanceReviewUserList == null || projectAppearanceReviewUserList.isEmpty()) return null;
        if (projectAppearanceReviewUserList.size() == 1)
            return projectAppearanceBusinessService.getViewByProjectAppearanceReviewUserId(
                    projectAppearanceReviewUserList.get(0).getId());
        else {
            UseMaterialBrandSelect useMaterialBrandSelect = useMaterialBrandSelectService.getById(
                    projectAppearanceReviewMode.getUseMaterialBrandSelectId());
            //有项目经理的话，返回项目经理的反馈
            for (ProjectAppearanceReviewUser projectAppearanceReviewUser : projectAppearanceReviewUserList) {
                if (projectUserService.isUserDesignDepartmentManager(useMaterialBrandSelect.getProjectId(),
                                                                     projectAppearanceReviewUser.getUserId()) ||
                        projectUserService.isUserEngineeringDepartmentManager(useMaterialBrandSelect.getProjectId(),
                                                                              projectAppearanceReviewUser.getUserId()))
                    return projectAppearanceBusinessService.getViewByProjectAppearanceReviewUserId(
                            projectAppearanceReviewUser.getId());
            }
            return projectAppearanceBusinessService.getViewByProjectAppearanceReviewUserId(
                    projectAppearanceReviewUserList.get(0).getId());
        }


    }
    @Override
    public ProjectAppearanceReviewUserView getFeedbackOfAffectAppearanceReviewedByReviewId(String projectAppearanceReviewModeId){
       ProjectAppearanceReviewMode projectAppearanceReviewMode = projectAppearanceReviewModeService.getById(
                projectAppearanceReviewModeId);
        if (projectAppearanceReviewMode == null) return null;

        List<ProjectAppearanceReview> projectAppearanceReviewList = projectAppearanceReviewService.getByProjectAppearanceReviewModeId(
                projectAppearanceReviewModeId);
        if (projectAppearanceReviewList == null || projectAppearanceReviewList.isEmpty()) return null;

        ProjectAppearanceReview projectAppearanceReview = projectAppearanceReviewList.getFirst();
        String projectAppearanceReviewId = projectAppearanceReview.getId();

        List<ProjectAppearanceReviewUser> projectAppearanceReviewUserList = projectAppearanceReviewUserService.getByProjectAppearanceReviewId(
                projectAppearanceReviewId);

        if (projectAppearanceReviewUserList == null || projectAppearanceReviewUserList.isEmpty()) return null;
        if (projectAppearanceReviewUserList.size() == 1)
            return projectAppearanceBusinessService.getViewByProjectAppearanceReviewUserId(
                    projectAppearanceReviewUserList.get(0).getId());
        else {
            UseMaterialBrandSelect useMaterialBrandSelect = useMaterialBrandSelectService.getById(
                    projectAppearanceReviewMode.getUseMaterialBrandSelectId());
            for (ProjectAppearanceReviewUser projectAppearanceReviewUser : projectAppearanceReviewUserList) {
                if (projectUserService.isUserDesignDepartmentManager(useMaterialBrandSelect.getProjectId(),
                                                                     projectAppearanceReviewUser.getUserId()) ||
                        projectUserService.isUserEngineeringDepartmentManager(useMaterialBrandSelect.getProjectId(),
                                                                              projectAppearanceReviewUser.getUserId()))
                    return projectAppearanceBusinessService.getViewByProjectAppearanceReviewUserId(
                            projectAppearanceReviewUser.getId());
            }
            return projectAppearanceBusinessService.getViewByProjectAppearanceReviewUserId(
                    projectAppearanceReviewUserList.get(0).getId());
        }
    }
    @Override
    public List<ProjectMaterialRetestView> getFeedbackOfProjectMaterialAcceptanceReviewedByRetestId(String projectMaterialRetestBatchId) {
        List<ProjectMaterialRetest> projectMaterialRetestList = projectMaterialRetestService.getbyProjectMaterialRetestBatchId(projectMaterialRetestBatchId);

        if (projectMaterialRetestList == null || projectMaterialRetestList.isEmpty()) {
            return Collections.emptyList(); // 返回空列表而不是 null
        }

        List<ProjectMaterialRetestView> resultList = new ArrayList<>();

        for (ProjectMaterialRetest projectMaterialRetest : projectMaterialRetestList) {
            BuyMaterial buyMaterial = buyMaterialService.getById(projectMaterialRetest.getBuyMaterialId());
            UseMaterial useMaterial = useMaterialService.getById(buyMaterial.getUseMaterialId());
            UseMaterialBrandSelect useMaterialBrandSelect = useMaterialBrandSelectService.getById(useMaterial.getUseMaterialBrandSelectId());

            // 如果是监理单位的员工，则添加到列表
            if (projectUserService.isUserSupervisionCompanyEmployee(useMaterialBrandSelect.getProjectId(), projectMaterialRetest.getUserId())) {
                resultList.add(projectMaterialRetestBusinessService.getViewBypProjectMaterialRetestUserId(
                        projectMaterialRetest.getUserId(), projectMaterialRetest.getId()));
            }
        }

        return resultList; // 返回所有符合条件的 ProjectMaterialRetestView
    }

    @Override
    public ProjectMaterialAcceptanceReviewUserView getFeedbackOfProjectMaterialAcceptanceReviewedByReviewId(String projectMaterialAcceptanceReviewModeId) {
              ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode =projectMaterialAcceptanceReviewModeService.getById(
                      projectMaterialAcceptanceReviewModeId);
        if (projectMaterialAcceptanceReviewMode == null) return null;

        List<ProjectMaterialAcceptanceReview> projectMaterialAcceptanceReviewList = projectMaterialAcceptanceReviewService.getByProjectMaterialAcceptanceModeId(
                      projectMaterialAcceptanceReviewModeId);
        if (projectMaterialAcceptanceReviewList == null || projectMaterialAcceptanceReviewList.isEmpty()) return null;
        ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview = projectMaterialAcceptanceReviewList.getFirst();
        String projectMaterialAcceptanceReviewId = projectMaterialAcceptanceReview.getId();
         List<ProjectMaterialAcceptanceReviewUser> projectMaterialAcceptanceReviewUserList = projectMaterialAcceptanceReviewUserService.getByProjectMaterialAcceptanceReviewId(
                 projectMaterialAcceptanceReviewId);
         if (projectMaterialAcceptanceReviewUserList == null || projectMaterialAcceptanceReviewUserList.isEmpty()) return null;
         if (projectMaterialAcceptanceReviewUserList.size() == 1){
             return projectMaterialAcceptanceBusinessService.getViewByprojectMaterialAcceptanceReviewUserId(
                     projectMaterialAcceptanceReviewUserList.get(0).getId());
         }
         else {
             ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch = projectMaterialAcceptanceBatchService.getById(
                     projectMaterialAcceptanceReviewMode.getProjectMaterialAcceptanceBatchId());
             for (ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser : projectMaterialAcceptanceReviewUserList){
                 if (projectUserService.isUserDesignDepartmentManager(projectMaterialAcceptanceBatch.getProjectId(),
                         projectMaterialAcceptanceReviewUser.getUserId()) ||
                         projectUserService.isUserEngineeringDepartmentManager(projectMaterialAcceptanceBatch.getProjectId(),
                                 projectMaterialAcceptanceReviewUser.getUserId()));
                 return projectMaterialAcceptanceBusinessService.getViewByprojectMaterialAcceptanceReviewUserId(
                         projectMaterialAcceptanceReviewUser.getId());
             }
             return projectMaterialAcceptanceBusinessService.getViewByprojectMaterialAcceptanceReviewUserId(
                     projectMaterialAcceptanceReviewUserList.get(0).getId());


         }





    }

    @Override
    public UseMaterialBrandSelectView getNotPassedBrandOfSelectBrandReviewedOfSupervisionCompanyByProjectIdAndTaskId(String projectAppearanceReviewModeId) {
        ProjectAppearanceReviewMode projectAppearanceReviewMode = projectAppearanceReviewModeService.getById(
                projectAppearanceReviewModeId);
        if (projectAppearanceReviewMode == null) return null;
        UseMaterialBrandSelect useMaterialBrandSelect = useMaterialBrandSelectService.getById(
                projectAppearanceReviewMode.getUseMaterialBrandSelectId());
        if (useMaterialBrandSelect == null) return null;
        return useMaterialBusinessService.getViewByUseMaterialBrandSelectId(useMaterialBrandSelect.getId());
    }

    private Page<ProjectMaterialView> convertProjectMaterialIdPage2ProjectMaterialViewPage(Page<String> projectMaterialIdPage,
                                                                                           Integer pageNo,
                                                                                           Integer pageSize) {
        if (projectMaterialIdPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialView> list = new ArrayList<>();
        for (String projectMaterialId : projectMaterialIdPage.getResult()) {
            ProjectMaterialView buyMaterialView = new ProjectMaterialView();
            ProjectMaterial projectMaterial = projectMaterialService.getById(projectMaterialId);
            buyMaterialView.setProjectMaterial(projectMaterial);
            buyMaterialView.setProject(projectService.getById(projectMaterial.getProjectId()));
            buyMaterialView.setMaterial(materialService.getById(projectMaterial.getMaterialId()));


            //获得私有品牌列表,注意，一定是从购买的物料中或缺，并且没有被禁止使用的
            List<String> projectMaterialBrandPrivateIdList = buyMaterialService.getBoughtProjectMaterialBrandPrivateIdListPageByProjectIdAndMaterialId(
                    projectMaterial.getProjectId(), projectMaterial.getMaterialId());

            if (projectMaterialBrandPrivateIdList != null && !projectMaterialBrandPrivateIdList.isEmpty()) {
                List<ProjectMaterialBrandPrivateView> projectMaterialBrandPrivateViewList = new ArrayList<>();
                for (String projectMaterialBrandPrivateId : projectMaterialBrandPrivateIdList) {
                    if (projectMaterialBrandPrivateId == null || projectMaterialBrandPrivateId.isEmpty())
                        continue;
                    ProjectMaterialBrandPrivateView temp = projectMaterialBrandPrivateService.getViewById(
                            projectMaterialBrandPrivateId);
                    if (temp != null)
                        projectMaterialBrandPrivateViewList.add(temp);
                }

                if (!projectMaterialBrandPrivateViewList.isEmpty())
                    buyMaterialView.setProjectMaterialBrandPrivateViewList(projectMaterialBrandPrivateViewList);
                else buyMaterialView.setProjectMaterialBrandPrivateViewList(null
                );

            } else
                buyMaterialView.setProjectMaterialBrandPrivateViewList(null);

            //获得公共品牌列表，注意，一定是从购买的物料中或缺，并且没有被禁止使用的
            List<String> projectMaterialBrandPublicIdList = buyMaterialService.getBoughtProjectMaterialBrandPublicIdListPageByProjectIdAndMaterialId(
                    projectMaterial.getProjectId(), projectMaterial.getMaterialId());

            if (projectMaterialBrandPublicIdList != null && !projectMaterialBrandPublicIdList.isEmpty()) {
                List<ProjectMaterialBrandPublicView> projectMaterialBrandPublicViewList = new ArrayList<>();
                for (String projectMaterialBrandPublicId : projectMaterialBrandPublicIdList) {
                    if (projectMaterialBrandPublicId == null || projectMaterialBrandPublicId.isEmpty()) continue;
                    ProjectMaterialBrandPublicView temp = projectMaterialBrandPublicService.getViewById(
                            projectMaterialBrandPublicId);
                    if (temp != null)
                        projectMaterialBrandPublicViewList.add(temp);
                }

                if (!projectMaterialBrandPublicViewList.isEmpty())
                    buyMaterialView.setProjectMaterialBrandPublicViewList(projectMaterialBrandPublicViewList);
                else
                    buyMaterialView.setProjectMaterialBrandPublicViewList(null);
            } else
                buyMaterialView.setProjectMaterialBrandPublicViewList(null);

            list.add(buyMaterialView);
        }
        return new Page<>(startIndex, projectMaterialIdPage.getTotalCount(), pageSize, list);
    }


    private List<ProjectMaterialVerificationDocumentView> getProjectMaterialVerificationDocumentViewByBuyMaterialId(String buyMaterialId) {
        BuyMaterial buyMaterial = buyMaterialService.getById(buyMaterialId);
        if (buyMaterial == null) return null;


        List<ProjectMaterialVerificationDocument> projectMaterialVerificationDocumentList = projectMaterialVerificationDocumentService.getByBuyMaterialId(
                buyMaterialId);

        if (projectMaterialVerificationDocumentList == null || projectMaterialVerificationDocumentList.isEmpty()) {
            ProjectMaterialVerificationDocumentView projectMaterialVerificationDocumentView = new ProjectMaterialVerificationDocumentView();
            BuyMaterialView buyMaterialView = getBuyMaterialViewByBuyMaterialId(buyMaterialId);
            if (buyMaterialView == null) return null;

            projectMaterialVerificationDocumentView.setBuyMaterialView(buyMaterialView);
            projectMaterialVerificationDocumentView.setUser(userService.getCurrentLoginUser());
            projectMaterialVerificationDocumentView.setProjectMaterialVerificationDocument(null);
            projectMaterialVerificationDocumentView.setProjectMaterialVerificationDocumentFileList(null);
            return Collections.singletonList(projectMaterialVerificationDocumentView);
        } else {
            List<ProjectMaterialVerificationDocumentView> list = new ArrayList<>();
            projectMaterialVerificationDocumentList.forEach(projectMaterialVerificationDocument -> {
                ProjectMaterialVerificationDocumentView projectMaterialVerificationDocumentView = new ProjectMaterialVerificationDocumentView();
                projectMaterialVerificationDocumentView.setUser(
                        userService.getById(projectMaterialVerificationDocument.getUserId()));
                projectMaterialVerificationDocumentView.setProjectMaterialVerificationDocument(
                        projectMaterialVerificationDocument);
                projectMaterialVerificationDocumentView.setProjectMaterialVerificationDocumentFileList(
                        projectMaterialVerificationDocumentFileService.getByProjectMaterialVerificationDocumentId(
                                projectMaterialVerificationDocument.getId()));
                projectMaterialVerificationDocumentView.setBuyMaterialView(
                        getBuyMaterialViewByBuyMaterialId(buyMaterialId));
                list.add(projectMaterialVerificationDocumentView);

            });
            return list;
        }
    }

    private BuyMaterialView getBuyMaterialViewByBuyMaterialId(String id) {
        BuyMaterial buyMaterial = buyMaterialService.getById(id);
        if (buyMaterial == null) return null;
        BuyMaterialView buyMaterialView = new BuyMaterialView();
        buyMaterialView.setBuyMaterial(buyMaterial);
        buyMaterialView.setProjectMaterialBrandPrivateView(
                projectMaterialBrandPrivateService.getViewById(buyMaterial.getProjectMaterialBrandPrivateId()));
        buyMaterialView.setProjectMaterialBrandPublicView(
                projectMaterialBrandPublicService.getViewById(buyMaterial.getProjectMaterialBrandPublicId()));
        buyMaterialView.setUser(userService.getById(buyMaterial.getUserId()));
        buyMaterialView.setUseMaterialView(
                useMaterialBusinessService.getViewByUseMaterialId(buyMaterial.getUseMaterialId()));
        return buyMaterialView;
    }

    private Page<BuyMaterialView> convertBuyMaterialPage2PageView(Page<BuyMaterial> buyMaterialPage,
                                                                  int pageNo,
                                                                  int pageSize) {
        if (buyMaterialPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<BuyMaterialView> list = new ArrayList<>();
        for (BuyMaterial buyMaterial : buyMaterialPage.getResult()) {
            BuyMaterialView buyMaterialView = getBuyMaterialViewByBuyMaterialId(buyMaterial.getId());
            if (buyMaterialView != null) list.add(buyMaterialView);
        }
        return new Page<>(startIndex, buyMaterialPage.getTotalCount(), pageSize, list);
    }

    private Page<ProjectMaterialVerificationDocumentView> convertBuyMaterialPage2ProjectMaterialVerificationDocumentViewPageView(Page<BuyMaterial> buyMaterialPage,
                                                                                                                                 int pageNo,
                                                                                                                                 int pageSize) {
        if (buyMaterialPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialVerificationDocumentView> list = new ArrayList<>();
        for (BuyMaterial buyMaterial : buyMaterialPage.getResult()) {
            List<ProjectMaterialVerificationDocumentView> projectMaterialVerificationDocumentViewList = getProjectMaterialVerificationDocumentViewByBuyMaterialId(
                    buyMaterial.getId());
            if (projectMaterialVerificationDocumentViewList != null)
                list.addAll(projectMaterialVerificationDocumentViewList);
        }
        return new Page<>(startIndex, buyMaterialPage.getTotalCount(), pageSize, list);
    }

    private int getAppearanceByUseMaterialBrandSelectId(String useMaterialBrandSelectId) {
        UseMaterialBrandSelect useMaterialBrandSelect = useMaterialBrandSelectService.getById(useMaterialBrandSelectId);
        if (useMaterialBrandSelect == null)
            return 0;
        List<UseMaterial> useMaterialList = useMaterialService.getByUseMaterialBrandSelectId(
                useMaterialBrandSelect.getId());
        if (useMaterialList == null || useMaterialList.isEmpty())
            return 0;
        return useMaterialList.getFirst().getIsAppearance();
    }

    /**
     * 检查用户提交的参数是否正确
     * @param useMaterialFormItems
     * @return
     */
    private boolean checkUseMaterialForm(List<UseMaterialFormItem> useMaterialFormItems) {

        if (useMaterialFormItems == null || useMaterialFormItems.isEmpty())
            throw new BusinessException("参数为空");

        User user = userService.getCurrentLoginUser();
        if (user == null)
            throw new BusinessException("用户未登录");

        //检测各项参数是否正确
        useMaterialFormItems.forEach(useMaterialFormItem -> {


            String projectMaterialId = useMaterialFormItem.getProjectMaterialId();
            if (projectMaterialId == null || projectMaterialId.isEmpty())
                throw new BusinessException("项目物料id为空");

            String userId = useMaterialFormItem.getUserId();


            if (userId == null || userId.isEmpty() || userService.getById(userId) == null)
                throw new BusinessException("用户id为空或不合法");

            if (!user.getId().equals(userId))
                throw new BusinessException("用户id与当前用户不一致");

            String projectMaterialBrandPublicId = useMaterialFormItem.getProjectMaterialBrandPublicId();
            String projectMaterialBrandPrivateId = useMaterialFormItem.getProjectMaterialBrandPrivateId();
            String brandName = useMaterialFormItem.getBrandName();

            if(projectMaterialBrandPublicId.isEmpty())
                projectMaterialBrandPublicId = null;
            if(projectMaterialBrandPrivateId.isEmpty())
                projectMaterialBrandPrivateId = null;
            if(brandName.isEmpty())
                brandName = null;



            //要么是公共品牌或项目私有品牌之一，要么是新建品牌
            if (projectMaterialBrandPublicId == null && projectMaterialBrandPrivateId == null && brandName == null)
                throw new BusinessException("未选择品牌或未新建品牌");

            if (brandName != null) {
                String tempFileDir = useMaterialFormItem.getTempFileDir();
                if (tempFileDir == null || tempFileDir.isEmpty())
                    throw new BusinessException("未上传文件");

                String materialPosition = useMaterialFormItem.getMaterialPosition();
                String materialClassifyDivisionId = useMaterialFormItem.getMaterialClassifyDivisionId();
                String materialClassifyGroupId = useMaterialFormItem.getMaterialClassifyGroupId();
                String materialClassifySectionId = useMaterialFormItem.getMaterialClassifySectionId();
                if (materialPosition == null || materialPosition.isEmpty()
                        || materialClassifyDivisionId == null || materialClassifyDivisionId.isEmpty()
                        || materialClassifyGroupId == null || materialClassifyGroupId.isEmpty()
                        || materialClassifySectionId == null || materialClassifySectionId.isEmpty())
                    throw new BusinessException("未选择物料分类或物料定位");

                MaterialClassifyDivision materialClassifyDivision = materialClassifyDivisionService.getById(
                        materialClassifyDivisionId);
                if (materialClassifyDivision == null)
                    throw new BusinessException("未选择物料分类");
                MaterialClassifyGroup materialClassifyGroup = materialClassifyGroupService.getById(
                        materialClassifyGroupId);
                if (materialClassifyGroup == null)
                    throw new BusinessException("未选择物料分类");
                MaterialClassifySection materialClassifySection = materialClassifySectionService.getById(
                        materialClassifySectionId);
                if (materialClassifySection == null)
                    throw new BusinessException("未选择物料分类");

                //检查是否存在附件
                String pathTemp = FileUtils.getFilePath(tempFileDir, true);
                List<String> stringList = FileUtils.listFiles(pathTemp);
                if (stringList != null && stringList.isEmpty()) throw new BusinessException("未上传文件");
            }

            //如果用户没有修改物料，也没有新建品牌，则需要查看用户是否以前申请过同样物料、同样品牌、同样外观。如果申请过，则不需要再次申请。
            if(!useMaterialFormItem.isMaterialChange() && brandName == null) {
                boolean found= useMaterialService.isExistByProjectMaterialIdAndBrandPublicIdAndAppearance(
                        projectMaterialId, projectMaterialBrandPublicId,useMaterialFormItem.getIsAppearance());
                if(found)
                    throw new BusinessException("您已经申请过同样物料、同样品牌、同样外观的物料:"+useMaterialFormItem.getMaterial().getName());
            }
        });
        return true;
    }

    private Page<ProjectView> convertProjectPage2PageView(Page<Project> projectPage,
                                                          int pageNo,
                                                          int pageSize) {
        if (projectPage == null) return null;
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectView> list = new ArrayList<>();
        for (Project project : projectPage.getResult()) {
            ProjectView projectView = getProjectViewById(project.getId());
            if (projectView != null) list.add(projectView);
        }
        return new Page<>(startIndex, projectPage.getTotalCount(), pageSize, list);
    }


}
