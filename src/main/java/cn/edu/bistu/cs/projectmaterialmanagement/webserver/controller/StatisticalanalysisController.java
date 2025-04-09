package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.end.ProjectEnd;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Company;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Material;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IStatisticalanalysisRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.appearance.IProjectAppearanceReviewRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.flow.ProjectFlow;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialRetestService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IUseMaterialService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.acceptance.IProjectMaterialAcceptanceReviewUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.appearance.IProjectAppearanceReviewUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.end.IProjectEndService;
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
    private final IUseMaterialService useMaterialService;
    private  final IProjectMaterialService projectMaterialService;
    private final IProjectMaterialRetestService projectMaterialRetestService;
    private final IProjectMaterialAcceptanceReviewUserService projectMaterialAcceptanceReviewUserService;
    private final IProjectEndService projectEndService;


    private IStatisticalanalysisRepository statisticalanalysisRepository;


    public StatisticalanalysisController(IProjectService projectService, ProjectFlow projectMaterialFlow, IStatisticalanalysisRepository statisticalanalysisRepository,
                                         IMaterialService materialService, ICompanyService companyService, IUserService userService,
                                         IProjectReviewUserService projectReviewUserService,
                                         IProjectAppearanceReviewUserService projectAppearanceReviewUserService,
                                         ICompanyUserService companyUserService,
                                            IUseMaterialService useMaterialService,
                                            IProjectMaterialService projectMaterialService,
                                            IProjectMaterialRetestService projectMaterialRetestService,
                                            IProjectMaterialAcceptanceReviewUserService projectMaterialAcceptanceReviewUserService,
                                            IProjectEndService projectEndService
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
        this.useMaterialService = useMaterialService;
        this.projectMaterialService = projectMaterialService;
        this.projectMaterialRetestService = projectMaterialRetestService;
        this.projectMaterialAcceptanceReviewUserService = projectMaterialAcceptanceReviewUserService;
        this.projectEndService = projectEndService;
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
    public List<ProjectStatis> test4() {
        int result = 1;
//        1.获取设计部审核不通过的项目列表 2为不通过
        List<Stastisprojectidandcount> designstastisprojectidandcounts = statisticalanalysisRepository.getdesignunpassorpassList(result);
        Set<String> designprojectSet = new HashSet<>();
//        对获取的project进行去重
        for (Stastisprojectidandcount stastisprojectidandcount : designstastisprojectidandcounts) {
            designprojectSet.add(stastisprojectidandcount.getDesignProjectId());
        }
        Map<String, Integer> designmap = new HashMap<>();
        for (String projectid : designprojectSet) {
//            获取每个projectid下的审核不通过次数
            Integer designunpasscount = statisticalanalysisRepository.getdesignunpassorpassCount(projectid, result);
//            建立一个map
            designmap.put(projectid, designunpasscount);
        }

//        2.总包公司订购之前审核
        List<Stastisprojectidandcount> zongbaostastisprojectidandcounts = statisticalanalysisRepository.getzongbaounpassorpassList(result);
        Set<String> zongbaoprojectSet = new HashSet<>();
        for ( Stastisprojectidandcount stastisprojectidandcount : zongbaostastisprojectidandcounts) {
            zongbaoprojectSet.add(stastisprojectidandcount.getZongbaoProjectId());
        }
        Map<String, Integer> zongbaomap = new HashMap<>();
        for (String projectid : zongbaoprojectSet) {
            Integer zongbaounpasscount = statisticalanalysisRepository.getzongbaounpassorpassCount(projectid, result);
            zongbaomap.put(projectid, zongbaounpasscount);
        }

//        3.监理审核不通过
        List<Stastisprojectidandcount> jianlistastisprojectidandcounts = statisticalanalysisRepository.getjianliunpassorpassList(result);
        Set<String> jianliSet = new HashSet<>();
        for ( Stastisprojectidandcount stastisprojectidandcount : jianlistastisprojectidandcounts) {
            jianliSet.add(stastisprojectidandcount.getJianliProjectId());
        }
        Map<String, Integer> jianlimap = new HashMap<>();
        for (String projectid : jianliSet) {
            Integer jianliunpasscount = statisticalanalysisRepository.getjianliunpassorpassCount(projectid,result);
            jianlimap.put(projectid, jianliunpasscount);
        }
//       4.监理与工程审核不通过
        List<Stastisprojectidandcount> jianliandgongchengbustastisprojectidandcounts = statisticalanalysisRepository.getjianliandgongchengbuunpassorpassList(result);
        Set<String> jianliandgongchengbuSet = new HashSet<>();
        for ( Stastisprojectidandcount stastisprojectidandcount : jianliandgongchengbustastisprojectidandcounts) {
            jianliandgongchengbuSet.add(stastisprojectidandcount.getJianliandgongchengbuProjectId());
        }
        Map<String, Integer> jianliandgongchengbumap = new HashMap<>();
        for (String projectid : jianliandgongchengbuSet) {
            Integer jianliandgongchengbuunpasscount = statisticalanalysisRepository.getjianliandgongchengbuunpassorpassCount(projectid, 2);
            jianliandgongchengbumap.put(projectid, jianliandgongchengbuunpasscount);
        }


//        对designmap zongbaomap jianlimap jianliandgongchengbumap 中string相同的进行合并
        Map<String, Integer> mergedMap = new HashMap<>();
        mergeMaps(mergedMap, designmap);
        mergeMaps(mergedMap, zongbaomap);
        mergeMaps(mergedMap, jianlimap);
        mergeMaps(mergedMap, jianliandgongchengbumap);
        List<ProjectStatis> projectStatisList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : mergedMap.entrySet()) {
            ProjectStatis projectStatis = new ProjectStatis();
            projectStatis.setProjectId(entry.getKey());
            projectStatis.setCount(entry.getValue());
            projectStatis.setProject(projectService.getById(entry.getKey()));
            System.out.println(entry.getKey() + ":" + entry.getValue());
            projectStatisList.add(projectStatis);
        }

        return projectStatisList;
    }

//    获取从大到小的前十个审核不通过的项目
    @GetMapping("getAllList_disagree")
    public  List<ProjectStatis> test6() {
        int result = 2;
//        1.获取设计部审核不通过的项目列表 2为不通过
        List<Stastisprojectidandcount> designstastisprojectidandcounts = statisticalanalysisRepository.getdesignunpassorpassList(result);
        Set<String> designprojectSet = new HashSet<>();
//        对获取的project进行去重
        for (Stastisprojectidandcount stastisprojectidandcount : designstastisprojectidandcounts) {
            designprojectSet.add(stastisprojectidandcount.getDesignProjectId());
        }
        Map<String, Integer> designmap = new HashMap<>();
        for (String projectid : designprojectSet) {
//            获取每个projectid下的审核不通过次数
            Integer designunpasscount = statisticalanalysisRepository.getdesignunpassorpassCount(projectid, result);
//            建立一个map
            designmap.put(projectid, designunpasscount);
        }

//        2.总包公司订购之前审核
        List<Stastisprojectidandcount> zongbaostastisprojectidandcounts = statisticalanalysisRepository.getzongbaounpassorpassList(result);
        Set<String> zongbaoprojectSet = new HashSet<>();
        for ( Stastisprojectidandcount stastisprojectidandcount : zongbaostastisprojectidandcounts) {
            zongbaoprojectSet.add(stastisprojectidandcount.getZongbaoProjectId());
        }
        Map<String, Integer> zongbaomap = new HashMap<>();
        for (String projectid : zongbaoprojectSet) {
            Integer zongbaounpasscount = statisticalanalysisRepository.getzongbaounpassorpassCount(projectid, result);
            zongbaomap.put(projectid, zongbaounpasscount);
        }

//        3.监理审核不通过
        List<Stastisprojectidandcount> jianlistastisprojectidandcounts = statisticalanalysisRepository.getjianliunpassorpassList(result);
        Set<String> jianliSet = new HashSet<>();
        for ( Stastisprojectidandcount stastisprojectidandcount : jianlistastisprojectidandcounts) {
            jianliSet.add(stastisprojectidandcount.getJianliProjectId());
        }
        Map<String, Integer> jianlimap = new HashMap<>();
        for (String projectid : jianliSet) {
            Integer jianliunpasscount = statisticalanalysisRepository.getjianliunpassorpassCount(projectid,result);
            jianlimap.put(projectid, jianliunpasscount);
        }
//       4.监理与工程审核不通过
        List<Stastisprojectidandcount> jianliandgongchengbustastisprojectidandcounts = statisticalanalysisRepository.getjianliandgongchengbuunpassorpassList(result);
        Set<String> jianliandgongchengbuSet = new HashSet<>();
        for ( Stastisprojectidandcount stastisprojectidandcount : jianliandgongchengbustastisprojectidandcounts) {
            jianliandgongchengbuSet.add(stastisprojectidandcount.getJianliandgongchengbuProjectId());
        }
        Map<String, Integer> jianliandgongchengbumap = new HashMap<>();
        for (String projectid : jianliandgongchengbuSet) {
            Integer jianliandgongchengbuunpasscount = statisticalanalysisRepository.getjianliandgongchengbuunpassorpassCount(projectid, 2);
            jianliandgongchengbumap.put(projectid, jianliandgongchengbuunpasscount);
        }


//        对designmap zongbaomap jianlimap jianliandgongchengbumap 中string相同的进行合并
        Map<String, Integer> mergedMap = new HashMap<>();
        mergeMaps(mergedMap, designmap);
        mergeMaps(mergedMap, zongbaomap);
        mergeMaps(mergedMap, jianlimap);
        mergeMaps(mergedMap, jianliandgongchengbumap);
        List<ProjectStatis> projectStatisList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : mergedMap.entrySet()) {
            ProjectStatis projectStatis = new ProjectStatis();
            projectStatis.setProjectId(entry.getKey());
            projectStatis.setCount(entry.getValue());
            projectStatis.setProject(projectService.getById(entry.getKey()));
            System.out.println(entry.getKey() + ":" + entry.getValue());
            projectStatisList.add(projectStatis);
        }


//  根据projectStatisList中的count进行排序
        projectStatisList.sort((o1, o2) -> Integer.compare(o2.getCount(), o1.getCount()));
//        返回projectStatisList的前十个
        List<ProjectStatis> topTen = projectStatisList.subList(0, Math.min(10, projectStatisList.size()));
        return topTen;
    }

    //获取所有审核不通过且未结束的项目
    @GetMapping("getAllList_disagree_all")
    public  List<ProjectStatis> test7() {
        int result = 2;
//        1.获取设计部审核不通过的项目列表 2为不通过
        List<Stastisprojectidandcount> designstastisprojectidandcounts = statisticalanalysisRepository.getdesignunpassorpassList(result);
        Set<String> designprojectSet = new HashSet<>();
//        对获取的project进行去重
        for (Stastisprojectidandcount stastisprojectidandcount : designstastisprojectidandcounts) {
            designprojectSet.add(stastisprojectidandcount.getDesignProjectId());
        }
        Map<String, Integer> designmap = new HashMap<>();
        for (String projectid : designprojectSet) {
//            获取每个projectid下的审核不通过次数
            Integer designunpasscount = statisticalanalysisRepository.getdesignunpassorpassCount(projectid, result);
//            建立一个map
            designmap.put(projectid, designunpasscount);
        }

//        2.总包公司订购之前审核
        List<Stastisprojectidandcount> zongbaostastisprojectidandcounts = statisticalanalysisRepository.getzongbaounpassorpassList(result);
        Set<String> zongbaoprojectSet = new HashSet<>();
        for ( Stastisprojectidandcount stastisprojectidandcount : zongbaostastisprojectidandcounts) {
            zongbaoprojectSet.add(stastisprojectidandcount.getZongbaoProjectId());
        }
        Map<String, Integer> zongbaomap = new HashMap<>();
        for (String projectid : zongbaoprojectSet) {
            Integer zongbaounpasscount = statisticalanalysisRepository.getzongbaounpassorpassCount(projectid, result);
            zongbaomap.put(projectid, zongbaounpasscount);
        }

//        3.监理审核不通过
        List<Stastisprojectidandcount> jianlistastisprojectidandcounts = statisticalanalysisRepository.getjianliunpassorpassList(result);
        Set<String> jianliSet = new HashSet<>();
        for ( Stastisprojectidandcount stastisprojectidandcount : jianlistastisprojectidandcounts) {
            jianliSet.add(stastisprojectidandcount.getJianliProjectId());
        }
        Map<String, Integer> jianlimap = new HashMap<>();
        for (String projectid : jianliSet) {
            Integer jianliunpasscount = statisticalanalysisRepository.getjianliunpassorpassCount(projectid,result);
            jianlimap.put(projectid, jianliunpasscount);
        }
//       4.监理与工程审核不通过
        List<Stastisprojectidandcount> jianliandgongchengbustastisprojectidandcounts = statisticalanalysisRepository.getjianliandgongchengbuunpassorpassList(result);
        Set<String> jianliandgongchengbuSet = new HashSet<>();
        for ( Stastisprojectidandcount stastisprojectidandcount : jianliandgongchengbustastisprojectidandcounts) {
            jianliandgongchengbuSet.add(stastisprojectidandcount.getJianliandgongchengbuProjectId());
        }
        Map<String, Integer> jianliandgongchengbumap = new HashMap<>();
        for (String projectid : jianliandgongchengbuSet) {
            Integer jianliandgongchengbuunpasscount = statisticalanalysisRepository.getjianliandgongchengbuunpassorpassCount(projectid, 2);
            jianliandgongchengbumap.put(projectid, jianliandgongchengbuunpasscount);
        }


//        对designmap zongbaomap jianlimap jianliandgongchengbumap 中string相同的进行合并
        Map<String, Integer> mergedMap = new HashMap<>();
        mergeMaps(mergedMap, designmap);
        mergeMaps(mergedMap, zongbaomap);
        mergeMaps(mergedMap, jianlimap);
        mergeMaps(mergedMap, jianliandgongchengbumap);
        List<ProjectStatis> projectStatisList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : mergedMap.entrySet()) {
            ProjectStatis projectStatis = new ProjectStatis();
            projectStatis.setProjectId(entry.getKey());
            projectStatis.setCount(entry.getValue());
            projectStatis.setProject(projectService.getById(entry.getKey()));
            System.out.println(entry.getKey() + ":" + entry.getValue());
            projectStatisList.add(projectStatis);
        }


//  根据projectStatisList中的count进行排序
        projectStatisList.sort((o1, o2) -> Integer.compare(o2.getCount(), o1.getCount()));
        return projectStatisList;
    }

    private static void mergeMaps(Map<String, Integer> mergedMap, Map<String, Integer> sourceMap) {
        sourceMap.forEach((key,  value) ->
                mergedMap.merge(key,  value, Integer::sum)
        );
    }

//    获取已经完成的项目的数量
    @GetMapping("get_end_project_count")
    public Integer getendprojectcount() {
       return projectEndService.getCount();

    }
    //    获取正在进行的项目的数量
    @GetMapping("get_processing_project_count")
    public Integer getprocessingprojectcount () {
        int allprojectcount = projectService.getCount();
        return (allprojectcount-projectEndService.getCount());

    }

    //    获取项目列表
    @GetMapping("get_page_project_list")
    public Page<ProjectStatisticalAnalysis> getprojectlist(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 4 ? pageSize : Page.DEFAULT_PAGE_SIZE);


        Page<Project> page = projectService.getPage(pageNo, pageSize);
        List<ProjectStatisticalAnalysis> projectStatisticalAnalysisList = new ArrayList<>();

        List<ProjectStatis> getallprojectunpasslist = getallprojectunpasslist(2);
        List<ProjectStatis> getallprojectpasslist = getallprojectunpasslist(1);
        for (Project project : page.getResult()) {
            ProjectStatisticalAnalysis projectStatisticalAnalysis = new ProjectStatisticalAnalysis();
            projectStatisticalAnalysis.setProjectName(project.getName());
            projectStatisticalAnalysis.setProjectId(project.getId());
//            判断该项目是否完成
            List<ProjectEnd> byId = projectEndService.getByProjectId(project.getId());
            if (byId != null) {
               projectStatisticalAnalysis.setCheckProjectWhetherEnd(true);
            } else {
                projectStatisticalAnalysis.setCheckProjectWhetherEnd(false);;
            }
//            获取该项目审核不通过次数 判断在getallprojectunpasslist中projectId是否存在
            int boolcount = 0;
            for (ProjectStatis projectStatis : getallprojectunpasslist) {
                if (projectStatis.getProjectId().equals(project.getId())) {
                    boolcount=1;
                    projectStatisticalAnalysis.setTotalReviewResulDisagree(projectStatis.getCount());
                }
            }
           if (boolcount == 0) {
               projectStatisticalAnalysis.setTotalReviewResulDisagree(0);
           }
          boolcount=0;
//            获取该项目审核通过次数
            for (ProjectStatis projectStatis : getallprojectpasslist) {
                if (projectStatis.getProjectId().equals(project.getId())) {
                    boolcount=1;
                    projectStatisticalAnalysis.setTotalReviewResultAgree(projectStatis.getCount());
                }
            }
            if (boolcount == 0) {
                projectStatisticalAnalysis.setTotalReviewResultAgree(0);
            }

            projectStatisticalAnalysisList.add(projectStatisticalAnalysis);
        }

        Page<ProjectStatisticalAnalysis> projectStatisticalAnalysisPage = new Page<>(page.getStart(), page.getTotalCount(), page.getPageSize(), projectStatisticalAnalysisList);
        return projectStatisticalAnalysisPage;
    }

    //    获取 搜索 项目通过的列表
    @GetMapping("get_search_page_project_list")
    public Page<ProjectStatisticalAnalysis> getSearchprojectlist(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                           @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                             @RequestParam(value = "projectName") String projectName) {
    pageNo = pageNo == null ? 1 : pageNo;
    pageSize = pageSize == null ? Page.DEFAULT_PAGE_SIZE : (pageSize > 4 ? pageSize : Page.DEFAULT_PAGE_SIZE);

    Page<Project> page =  projectService.getPageByKeyword(projectName, pageNo, pageSize);

    List<ProjectStatisticalAnalysis> projectStatisticalAnalysisList = new ArrayList<>();

    List<ProjectStatis> getallprojectunpasslist = getallprojectunpasslist(2);
    List<ProjectStatis> getallprojectpasslist = getallprojectunpasslist(1);
    for (Project project : page.getResult()) {
        ProjectStatisticalAnalysis projectStatisticalAnalysis = new ProjectStatisticalAnalysis();
        projectStatisticalAnalysis.setProjectName(project.getName());
        projectStatisticalAnalysis.setProjectId(project.getId());
//            判断该项目是否完成
        List<ProjectEnd> byId = projectEndService.getByProjectId(project.getId());
        if (byId != null) {
            projectStatisticalAnalysis.setCheckProjectWhetherEnd(true);
        } else {
            projectStatisticalAnalysis.setCheckProjectWhetherEnd(false);;
        }
//            获取该项目审核不通过次数 判断在getallprojectunpasslist中projectId是否存在
        int boolcount = 0;
        for (ProjectStatis projectStatis : getallprojectunpasslist) {
            if (projectStatis.getProjectId().equals(project.getId())) {
                boolcount=1;
                projectStatisticalAnalysis.setTotalReviewResulDisagree(projectStatis.getCount());
            }
        }
        if (boolcount == 0) {
            projectStatisticalAnalysis.setTotalReviewResulDisagree(0);
        }
        boolcount=0;
//            获取该项目审核通过次数
        for (ProjectStatis projectStatis : getallprojectpasslist) {
            if (projectStatis.getProjectId().equals(project.getId())) {
                boolcount=1;
                projectStatisticalAnalysis.setTotalReviewResultAgree(projectStatis.getCount());
            }
        }
        if (boolcount == 0) {
            projectStatisticalAnalysis.setTotalReviewResultAgree(0);
        }

        projectStatisticalAnalysisList.add(projectStatisticalAnalysis);
    }

    Page<ProjectStatisticalAnalysis> projectStatisticalAnalysisPage = new Page<>(page.getStart(), page.getTotalCount(), page.getPageSize(), projectStatisticalAnalysisList);
    return projectStatisticalAnalysisPage;
}



    //    获取所有项目不通过的列表
    public List<ProjectStatis> getallprojectunpasslist(int kk) {
        int result = kk;
//        1.获取设计部审核不通过的项目列表 2为不通过
        List<Stastisprojectidandcount> designstastisprojectidandcounts = statisticalanalysisRepository.getdesignunpassorpassList(result);
        Set<String> designprojectSet = new HashSet<>();
//        对获取的project进行去重
        for (Stastisprojectidandcount stastisprojectidandcount : designstastisprojectidandcounts) {
            designprojectSet.add(stastisprojectidandcount.getDesignProjectId());
        }
        Map<String, Integer> designmap = new HashMap<>();
        for (String projectid : designprojectSet) {
//            获取每个projectid下的审核不通过次数
            Integer designunpasscount = statisticalanalysisRepository.getdesignunpassorpassCount(projectid, result);
//            建立一个map
            designmap.put(projectid, designunpasscount);
        }

//        2.总包公司订购之前审核
        List<Stastisprojectidandcount> zongbaostastisprojectidandcounts = statisticalanalysisRepository.getzongbaounpassorpassList(result);
        Set<String> zongbaoprojectSet = new HashSet<>();
        for ( Stastisprojectidandcount stastisprojectidandcount : zongbaostastisprojectidandcounts) {
            zongbaoprojectSet.add(stastisprojectidandcount.getZongbaoProjectId());
        }
        Map<String, Integer> zongbaomap = new HashMap<>();
        for (String projectid : zongbaoprojectSet) {
            Integer zongbaounpasscount = statisticalanalysisRepository.getzongbaounpassorpassCount(projectid, result);
            zongbaomap.put(projectid, zongbaounpasscount);
        }

//        3.监理审核不通过
        List<Stastisprojectidandcount> jianlistastisprojectidandcounts = statisticalanalysisRepository.getjianliunpassorpassList(result);
        Set<String> jianliSet = new HashSet<>();
        for ( Stastisprojectidandcount stastisprojectidandcount : jianlistastisprojectidandcounts) {
            jianliSet.add(stastisprojectidandcount.getJianliProjectId());
        }
        Map<String, Integer> jianlimap = new HashMap<>();
        for (String projectid : jianliSet) {
            Integer jianliunpasscount = statisticalanalysisRepository.getjianliunpassorpassCount(projectid,result);
            jianlimap.put(projectid, jianliunpasscount);
        }
//       4.监理与工程审核不通过
        List<Stastisprojectidandcount> jianliandgongchengbustastisprojectidandcounts = statisticalanalysisRepository.getjianliandgongchengbuunpassorpassList(result);
        Set<String> jianliandgongchengbuSet = new HashSet<>();
        for ( Stastisprojectidandcount stastisprojectidandcount : jianliandgongchengbustastisprojectidandcounts) {
            jianliandgongchengbuSet.add(stastisprojectidandcount.getJianliandgongchengbuProjectId());
        }
        Map<String, Integer> jianliandgongchengbumap = new HashMap<>();
        for (String projectid : jianliandgongchengbuSet) {
            Integer jianliandgongchengbuunpasscount = statisticalanalysisRepository.getjianliandgongchengbuunpassorpassCount(projectid, 2);
            jianliandgongchengbumap.put(projectid, jianliandgongchengbuunpasscount);
        }


//        对designmap zongbaomap jianlimap jianliandgongchengbumap 中string相同的进行合并
        Map<String, Integer> mergedMap = new HashMap<>();
        mergeMaps(mergedMap, designmap);
        mergeMaps(mergedMap, zongbaomap);
        mergeMaps(mergedMap, jianlimap);
        mergeMaps(mergedMap, jianliandgongchengbumap);
        List<ProjectStatis> projectStatisList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : mergedMap.entrySet()) {
            ProjectStatis projectStatis = new ProjectStatis();
            projectStatis.setProjectId(entry.getKey());
            projectStatis.setCount(entry.getValue());
            projectStatis.setProject(projectService.getById(entry.getKey()));
            System.out.println(entry.getKey() + ":" + entry.getValue());
            projectStatisList.add(projectStatis);
        }

        return projectStatisList;
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

//    监理审核不通过
    @GetMapping("get_unpass_review_jianli")
    public Page<Unpassonlymaterial> getUnpassReviewJianli(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {

//        1.首先从 t_buy_material表中获取 id t_buy_material_batch_id 与t_use_material_id
//          根据t_buy_material_batch_id到t_project_material_retest_batch表 获取 id
//          根据t_project_material_retest_batch表的id 跟t_buy_material表中的id  到t_project_material_resest表中获取相应的数据 满足review_result为2

        Page<Unpassonlymaterial> page = statisticalanalysisRepository.getUnpassReviewJianli(pageNo, pageSize);

        for (Unpassonlymaterial unpassonlymaterial : page.getResult()) {
//           材料信息
            String usematerialId = unpassonlymaterial.getMaterialId();
            UseMaterial useMaterialServiceById = useMaterialService.getById(usematerialId);
            String projectMaterialId = useMaterialServiceById.getProjectMaterialId();
            ProjectMaterial projectMaterialServiceById = projectMaterialService.getById(projectMaterialId);
            String materialId = projectMaterialServiceById.getMaterialId();
            Material materialServiceById1 = materialService.getById(materialId);
            unpassonlymaterial.setMaterial(materialServiceById1);
//            项目信息;
            Project projectServiceById = projectService.getById(unpassonlymaterial.getProjectId());
            unpassonlymaterial.setProject(projectServiceById);
        }

        return  page;
    }

    @GetMapping("get_unpass_reviewcontent_jianli")    //    获取评论
    public List<OnematerialUnpassjianli> getunpassreviewcontentjianli(@RequestParam(value = "projectid") String projectid,
                                                                             @RequestParam(value = "materialid") String materialid) {
        List<OnematerialUnpassjianli> unpassmaterialmessage = statisticalanalysisRepository.getunpassreviewcontentjianli(projectid, materialid);

        for (OnematerialUnpassjianli onematerialUnpass : unpassmaterialmessage) {
            onematerialUnpass.setUser(userService.getById(onematerialUnpass.getUserid()));
            onematerialUnpass.setProjectMaterialRetest(projectMaterialRetestService.getById(onematerialUnpass.getId()));
//          通过user获取company表信息
            CompanyUser byUserId = companyUserService.getByUserId(onematerialUnpass.getUser().getId());
            Company company = companyService.getById(byUserId.getCompanyId());
            onematerialUnpass.setCompany(company);
        }

        return unpassmaterialmessage;
    }
//第18 20 步骤 监理与工程部进行审核
    @GetMapping("get_unpass_review_jianli_and_gongchengbu")
    public Page<Unpassonlymaterial> getUnpassReviewJianliAndGongchengbu(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {
//        1.首先从t_project_material_acceptance 表中获取每一条数据中的t_project_material_acceptance_batch_id  t_project_material_id
//        2.根据首先从t_project_material_acceptance表的表中获取每一条数据中的t_project_material_acceptance_batch_id到
//          t_project_material_acceptance_batch表获取 id t_project_id
//        3.根据 t_project_material_acceptance_batch表的id到  t_project_material_acceptance_review_model表中获取id
//        4.根据t_project_material_acceptance_review_model表的id到 t_project_material_acceptance_review 表中获取相应的数据
//        5.根据 t_project_material_acceptance_review 表中的id到 t_project_material_acceptance_review_user表中的数据 满足review_result为2

        Page<Unpassonlymaterial> page = statisticalanalysisRepository.getUnpassReviewJianliAndGongchengbu(pageNo, pageSize);
        for (Unpassonlymaterial unpassonlymaterial : page.getResult()) {
//           材料信息
            String projectmaterialId = unpassonlymaterial.getMaterialId();
            ProjectMaterial projectMaterialServiceById = projectMaterialService.getById(projectmaterialId);
            String materialId = projectMaterialServiceById.getMaterialId();
            Material materialServiceById1 = materialService.getById(materialId);
            unpassonlymaterial.setMaterial(materialServiceById1);
//            评论信息
            unpassonlymaterial.setProject(projectService.getById(unpassonlymaterial.getProjectId()));
        }

        return  page;
    }


    @GetMapping("get_unpass_reviewcontent_jianli_gongchengbu")    //    获取评论
    public List<OnematerialUnpassjianliandgongchengbu> getunpassreviewcontentJianliAndGongchengbu(@RequestParam(value = "projectid") String projectid,
                                                                      @RequestParam(value = "materialid") String materialid) {
        List<OnematerialUnpassjianliandgongchengbu> unpassmaterialmessage = statisticalanalysisRepository.getunpassreviewcontentjianliandgongchengbu(projectid, materialid);

        for (OnematerialUnpassjianliandgongchengbu onematerialUnpass : unpassmaterialmessage) {
            onematerialUnpass.setUser(userService.getById(onematerialUnpass.getUserid()));
            onematerialUnpass.setProjectMaterialAcceptanceReviewUser(projectMaterialAcceptanceReviewUserService.getById(onematerialUnpass.getId()));
//          通过user获取company表信息
            CompanyUser byUserId = companyUserService.getByUserId(onematerialUnpass.getUser().getId());
            Company company = companyService.getById(byUserId.getCompanyId());
            onematerialUnpass.setCompany(company);
        }

        return unpassmaterialmessage;
    }
}
