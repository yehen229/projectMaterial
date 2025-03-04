package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.engineeringdepartment;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectBusinessService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectUserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetEngineeringDepartmentRemainingEmployeeListProjectMaterialAcceptance implements ExecutionListener {
    private final static Logger logger = LoggerFactory.getLogger(
            SetEngineeringDepartmentRemainingEmployeeListProjectMaterialAcceptance.class);

    private final IProjectUserService projectUserService;
    private final IProjectBusinessService projectBusinessService;

    public SetEngineeringDepartmentRemainingEmployeeListProjectMaterialAcceptance(IProjectUserService projectUserService,
                                                                                  IProjectBusinessService projectBusinessService) {
        this.projectUserService = projectUserService;
        this.projectBusinessService = projectBusinessService;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String projectMaterialAcceptanceReviewModeId = (String) delegateExecution.getVariable("projectMaterialAcceptanceReviewModeId");

        //得到设计部其它的项目员工（含项目经理，不含前面已经填写过意见的项目员工）
        List<ProjectMaterialAcceptanceReviewUser> projectMaterialAcceptanceReviewUserList = projectBusinessService.getRemainingNotMaterialAcceptanceReviewedEmployees(projectMaterialAcceptanceReviewModeId);


        List<String> assigneeList = new ArrayList<String>(); //分配任务的人员

        //设置可以对任务进行审核的人员
        if (projectMaterialAcceptanceReviewUserList != null && !projectMaterialAcceptanceReviewUserList.isEmpty()) {
            String users = "";
            for (ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser : projectMaterialAcceptanceReviewUserList) {
                //delegateExecution.addCandidateUser(projectUser.getUserId());
                assigneeList.add(projectMaterialAcceptanceReviewUser.getUserId());
            }

        }

        //对应流程图Collection
        delegateExecution.setVariable("engineeringDepartmentRemainingEmployeeList", assigneeList);

        //设置项目经理还未汇总审核
        delegateExecution.setVariable("engineeringDepartmentManagerProjectMaterialAcceptanceSummaryReview", 0);


    }
}