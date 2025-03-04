package cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.BuyMaterialQrcodeShowView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectStatisticalAnalysis;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.flow.ProjectFlow;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.controller.ProjectMaterialFlowController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("statisticalanalysis/v1")
@EnableMethodSecurity
public class StatisticalanalysisController {
    private final IProjectService projectService;
    private final ProjectFlow projectMaterialFlow;

    public StatisticalanalysisController(IProjectService projectService, ProjectFlow projectMaterialFlow) {
        this.projectService = projectService;
        this.projectMaterialFlow = projectMaterialFlow;
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
}
