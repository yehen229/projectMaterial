package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Material;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IStatisticalanalysisRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.appearance.IProjectAppearanceReviewRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.flow.ProjectFlow;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.appearance.IProjectAppearanceReviewUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.materialreview.IProjectReviewUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.IMaterialService;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller.ProjectMaterialFlowController;

import java.util.*;

@RestController
@RequestMapping("statisticalanalysis/v1")
@EnableMethodSecurity
public class StatisticalanalysisController {
    private final IProjectService projectService;
    private final ProjectFlow projectMaterialFlow;
    private final IMaterialService materialService;
    private final ICompanyService companyService;
    private final IUserService userService;
    private final IProjectReviewUserService projectReviewUserService;
    private  final IProjectAppearanceReviewUserService projectAppearanceReviewUserService;
    private  final ICompanyUserService companyUserService;


    private IStatisticalanalysisRepository statisticalanalysisRepository;


    public StatisticalanalysisController(IProjectService projectService, ProjectFlow projectMaterialFlow, IStatisticalanalysisRepository statisticalanalysisRepository,
                                         IMaterialService materialService, ICompanyService companyService, IUserService userService,
                                         IProjectReviewUserService projectReviewUserService,
                                         IProjectAppearanceReviewUserService projectAppearanceReviewUserService,
                                         ICompanyUserService companyUserService
    ) {
        this.companyService = companyService;
        this.materialService = materialService;
        this.projectService = projectService;
        this.projectMaterialFlow = projectMaterialFlow;
        this.statisticalanalysisRepository = statisticalanalysisRepository;
        this.userService = userService;
        this.projectReviewUserService = projectReviewUserService;
        this.projectAppearanceReviewUserService = projectAppearanceReviewUserService;
        this.companyUserService = companyUserService;
    }


    @GetMapping("getList")
    public Page<ProjectStatisticalAnalysis> test3(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 4 ? pageSize : Page.DEFAULT_PAGE_SIZE);

        /*1.获取项目列表*/
        Page<Project> page = projectService.getPage(pageNo, pageSize);
        List<ProjectStatisticalAnalysis> projectStatisticalAnalysisList = new ArrayList<>();
        for (Project project : page.getResult()) {

            ProjectStatisticalAnalysis statisticsOfProjectViewByTaskId = projectMaterialFlow.getStatisticsOfProjectViewByTaskId("123", project.getId());
            statisticsOfProjectViewByTaskId.setProjectId(project.getId());
            projectStatisticalAnalysisList.add(statisticsOfProjectViewByTaskId);
        }

//        return projectService.getPage(pageNo, pageSize);
        Page<ProjectStatisticalAnalysis> projectStatisticalAnalysisPage = new Page<>(page.getStart(), page.getTotalCount(), page.getPageSize(), projectStatisticalAnalysisList);
        return projectStatisticalAnalysisPage;
    }

    @GetMapping("byprojectname-getList")
    public Page<ProjectStatisticalAnalysis> test4(@RequestParam(value = "projectName", required = true) String projectName,
                                                  @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
//        1.通过项目名字获取项目列表
        Page<Project> pageByKeyword = projectService.getPageByKeyword(projectName, pageNo, pageSize);
        List<ProjectStatisticalAnalysis> projectStatisticalAnalysisList = new ArrayList<>();
        for (Project project : pageByKeyword.getResult()) {
            ProjectStatisticalAnalysis statisticsOfProjectViewByTaskId = projectMaterialFlow.getStatisticsOfProjectViewByTaskId("123", project.getId());
            statisticsOfProjectViewByTaskId.setProjectId(project.getId());
            projectStatisticalAnalysisList.add(statisticsOfProjectViewByTaskId);
        }

//        return projectService.getPage(pageNo, pageSize);
        Page<ProjectStatisticalAnalysis> projectStatisticalAnalysisPage = new Page<>(pageByKeyword.getStart(), pageByKeyword.getTotalCount(), pageByKeyword.getPageSize(), projectStatisticalAnalysisList);
        return projectStatisticalAnalysisPage;
    }

    @GetMapping("getAllList_agree")
    public Integer test4() {

        List<Project> allList = projectService.getAllList();
        List<ProjectStatisticalAnalysis> projectStatisticalAnalysisList = new ArrayList<>();
        Integer agreeCount = 0;
        Integer disagreeCount = 0;
        for (Project project : allList) {
            ProjectStatisticalAnalysis statisticsOfProjectViewByTaskId = projectMaterialFlow.getStatisticsOfProjectViewByTaskId("123", project.getId());
            boolean checkProjectWhetherEnd = statisticsOfProjectViewByTaskId.getCheckProjectWhetherEnd();

            if (checkProjectWhetherEnd == true) {
                agreeCount++;
            }
            if (checkProjectWhetherEnd == false) {
                disagreeCount++;
            }

        }

        return agreeCount;
    }

    @GetMapping("getAllList_disagree")
    public Integer test6() {

        List<Project> allList = projectService.getAllList();
        List<ProjectStatisticalAnalysis> projectStatisticalAnalysisList = new ArrayList<>();
        Integer agreeCount = 0;
        Integer disagreeCount = 0;
        for (Project project : allList) {
            ProjectStatisticalAnalysis statisticsOfProjectViewByTaskId = projectMaterialFlow.getStatisticsOfProjectViewByTaskId("123", project.getId());
            boolean checkProjectWhetherEnd = statisticsOfProjectViewByTaskId.getCheckProjectWhetherEnd();

            if (checkProjectWhetherEnd == true) {
                agreeCount++;
            }
            if (checkProjectWhetherEnd == false) {
                disagreeCount++;
            }

        }

        return disagreeCount;
    }

    @GetMapping("getchart_projectname_totalReviewResulDisagree")
    public List<ProjectStatisticalAnalysis> test5() {


        List<Project> allList = projectService.getAllList();
        List<ProjectStatisticalAnalysis> projectStatisticalAnalysisList = new ArrayList<>();
        for (Project project : allList) {
            ProjectStatisticalAnalysis statisticsOfProjectViewByTaskId = projectMaterialFlow.getStatisticsOfProjectViewByTaskId("123", project.getId());
            statisticsOfProjectViewByTaskId.setProjectId(project.getId());
            projectStatisticalAnalysisList.add(statisticsOfProjectViewByTaskId);
        }
        projectStatisticalAnalysisList.sort((o1, o2) -> Integer.compare(o2.getTotalReviewResulDisagree(), o1.getTotalReviewResulDisagree()));
        List<ProjectStatisticalAnalysis> topTen = projectStatisticalAnalysisList.subList(0, Math.min(10, projectStatisticalAnalysisList.size()));
        return topTen;
    }

    //    设计单位审核不通过
    @GetMapping("get_solomaterial_unpass_message")
    public List<List<UnpassmaterialmessageList>> getSolomaterialUnpassMessage() {
        /*根据projectid origin_id company_id 可以确定一个该项目的材料信息
         * */
//       1.从t_project_material表中获取每一条数据中的projectid  company_id

//        2.根据projectid  company_id 到t_project_review_model表获取相应的数据 获取t_project_review_model表的id

//        3.根据id到t_project_review表中获取相应的数据(满足review_result参数为2) t_project_review的id

//        4.根据id到t_project_review_user表中获取相应的 评论数据

        List<Unpassmaterialmessage> unpassmaterialmessage = statisticalanalysisRepository.getUnpassmaterialmessage();

        Set<String> uniqueSet = new LinkedHashSet<>(); // 保持插入顺序

        for (Unpassmaterialmessage message : unpassmaterialmessage) {
            uniqueSet.add(message.getMaterialId());
        }
        List<String> uniqueMaterialIds = new ArrayList<>(uniqueSet);

        Map<String, List<Unpassmaterialmessage>> groupedMessages = new HashMap<>();
        for (Unpassmaterialmessage message : unpassmaterialmessage) {
            String materialId = message.getMaterialId();
            groupedMessages.computeIfAbsent(materialId, k -> new ArrayList<>()).add(message);
        }

//        1.根据uniqueMaterialIds中的id找到groupedMessages中的数据
        List<List<UnpassmaterialmessageList>> finallist = new ArrayList<>();
        for (String uniqueMaterialId : uniqueMaterialIds) {
            List<Unpassmaterialmessage> unpassmaterialmessages = groupedMessages.get(uniqueMaterialId);
            List<UnpassmaterialmessageList> unpassmaterialmessageLists = new ArrayList<>();

            for (Unpassmaterialmessage unpassmaterialmessage1 : unpassmaterialmessages) {
                UnpassmaterialmessageList unpassmaterialmessageList = new UnpassmaterialmessageList();
//                获取材料信息
                Material materialServiceById = materialService.getById(unpassmaterialmessage1.getMaterialId());
                unpassmaterialmessageList.setMaterial(materialServiceById);
//                获取项目信息
                Company companyServiceById = companyService.getById(unpassmaterialmessage1.getCompanyId());
                unpassmaterialmessageList.setCompany(companyServiceById);
//                获取项目信息
                Project projectServiceById = projectService.getById(unpassmaterialmessage1.getProjectId());
                unpassmaterialmessageList.setProject(projectServiceById);
//                获取评论信息
                unpassmaterialmessageList.setReviewContent(unpassmaterialmessage1.getReviewConternt());
//                获取用户信息
                User userServiceById = userService.getById(unpassmaterialmessage1.getUserId());
                unpassmaterialmessageList.setUser(userServiceById);
//
                unpassmaterialmessageLists.add(unpassmaterialmessageList);
            }
            finallist.add(unpassmaterialmessageLists);
        }

        return finallist;

    }


    @GetMapping("get_unpass_material_message")   //    获取分页
    public Page<Unpassonlymaterial> getUnpassMaterialMessage(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
//        pageNo = pageNo == null ? 1 : pageNo;
//        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);
        Page<Unpassonlymaterial> page = statisticalanalysisRepository.getPage(pageNo, pageSize);
//        List<Unpassonlymaterial> unpassOnlymaterial = page.getResult();

//        List<Unpassonlymaterial> unpassOnlymaterial = statisticalanalysisRepository.getUnpassOnlymaterial();


        for (Unpassonlymaterial unpassonlymaterial : page.getResult()) {
            Material materialServiceById = materialService.getById(unpassonlymaterial.getMaterialId());
            unpassonlymaterial.setMaterial(materialServiceById);
            Project projectServiceById = projectService.getById(unpassonlymaterial.getProjectId());
            unpassonlymaterial.setProject(projectServiceById);
            Company companyServiceById = companyService.getById(unpassonlymaterial.getCompanyId());
            unpassonlymaterial.setCompany(companyServiceById);
        }
        return page;
    }


    @GetMapping("get_unpass_review_by_projectid_materialid_companyid")    //    获取评论
    public List<OnematerialUnpass> getunpassreviewbyprojectidmaterialid_companyid(@RequestParam(value = "projectid") String projectid,
                                                                                  @RequestParam(value = "companyid") String companyid) {
        List<OnematerialUnpass> unpassmaterialmessage = statisticalanalysisRepository.getunpassreviewbyprojectidmaterialid_companyid(projectid, companyid);

        for (OnematerialUnpass onematerialUnpass : unpassmaterialmessage) {
            onematerialUnpass.setUser(userService.getById(onematerialUnpass.getUserid()));
            onematerialUnpass.setProjectReviewUser(projectReviewUserService.getById(onematerialUnpass.getId()));
        }

        return unpassmaterialmessage;
    }

    //    总包公司订购之前审核
    @GetMapping("get_unpass_revie_before_zongbao")
    public Page<Unpassonlymaterial> getunpassbeforezongbao(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {
//        pageNo = pageNo == null ? 1 : pageNo;
//        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 10 ? pageSize : Page.DEFAULT_PAGE_SIZE);

        //        1.从t_project_material表中获取每一条数据中的projectid materialid
//        2.根据t_project_material表中的id 到t_use_material表中获取相应t_use_material_brand_select_id
//        3.根据t_use_material表中的t_use_material_brand_select_id到t_project_appearance_review_model表中获取 id
//        4.根据t_project_appearance_review_model表中的id到t_project_review表中获取相应的id 且 review_result为2
//        5.根据t_project_review表中的id到t_project_review_user表中获取review_content t_user_id


        Page<Unpassonlymaterial> page = statisticalanalysisRepository.getunpassbeforezongbaoPage(pageNo, pageSize);
//        List<Unpassonlymaterial> unpassOnlymaterial = page.getResult();

//        List<Unpassonlymaterial> unpassOnlymaterial = statisticalanalysisRepository.getUnpassOnlymaterial();


        for (Unpassonlymaterial unpassonlymaterial : page.getResult()) {
            Material materialServiceById = materialService.getById(unpassonlymaterial.getMaterialId());
            unpassonlymaterial.setMaterial(materialServiceById);
            Project projectServiceById = projectService.getById(unpassonlymaterial.getProjectId());
            unpassonlymaterial.setProject(projectServiceById);
        }

        return  page;
    }

    @GetMapping("get_unpass_review_before_zongbao")    //    获取评论
    public List<OnematerialUnpassbeforeZongbao> getunpassreviewbeforezongbao(@RequestParam(value = "projectid") String projectid,
                                                                                  @RequestParam(value = "materialid") String materialid) {
        List<OnematerialUnpassbeforeZongbao> unpassmaterialmessage = statisticalanalysisRepository.getunpassreviewbeforezongbao(projectid, materialid);

        for (OnematerialUnpassbeforeZongbao onematerialUnpass : unpassmaterialmessage) {
            onematerialUnpass.setUser(userService.getById(onematerialUnpass.getUserid()));
            onematerialUnpass.setProjectAppearanceReviewUser(projectAppearanceReviewUserService.getById(onematerialUnpass.getId()));
//          通过user获取company表信息
            CompanyUser byUserId = companyUserService.getByUserId(onematerialUnpass.getUser().getId());
            Company company = companyService.getById(byUserId.getCompanyId());
            onematerialUnpass.setCompany(company);
        }

        return unpassmaterialmessage;
    }
}
