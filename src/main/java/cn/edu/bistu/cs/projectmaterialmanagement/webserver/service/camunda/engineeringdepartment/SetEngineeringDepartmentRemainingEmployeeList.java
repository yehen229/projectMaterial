package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.engineeringdepartment;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectReviewUser;
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
public class SetEngineeringDepartmentRemainingEmployeeList implements ExecutionListener {
    private final static Logger logger = LoggerFactory.getLogger(SetEngineeringDepartmentRemainingEmployeeList.class);

    private final IProjectUserService projectUserService;
    private final IProjectBusinessService projectBusinessService;

    public SetEngineeringDepartmentRemainingEmployeeList(IProjectUserService projectUserService, IProjectBusinessService projectBusinessService) {
        this.projectUserService = projectUserService;
        this.projectBusinessService = projectBusinessService;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String projectReviewId = (String) delegateExecution.getVariable("projectReviewId");
        //得到设计部其它的项目员工（含项目经理，不含前面已经填写过意见的项目员工）
        List<ProjectReviewUser> projectUserList = projectBusinessService.getRemainingNotReviewedEmployees(projectReviewId);


        List<String> assigneeList = new ArrayList<String>(); //分配任务的人员

        //设置可以对任务进行审核的人员
        if (projectUserList != null && !projectUserList.isEmpty()) {
            String users = "";
            for (ProjectReviewUser projectReviewUser : projectUserList) {
                //delegateExecution.addCandidateUser(projectUser.getUserId());
                assigneeList.add(projectReviewUser.getUserId());
            }

        }

        //对应流程图Collection
        delegateExecution.setVariable("engineeringDepartmentRemainingEmployeeList", assigneeList);

        //设置项目经理还未汇总审核
        delegateExecution.setVariable("engineeringDepartmentManagerAppearanceSummaryReview", 0);


    }
}