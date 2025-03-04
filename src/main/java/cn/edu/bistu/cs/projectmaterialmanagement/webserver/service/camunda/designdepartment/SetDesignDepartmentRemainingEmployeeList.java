package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.designdepartment;

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
public class SetDesignDepartmentRemainingEmployeeList implements ExecutionListener {
    private final static Logger logger = LoggerFactory.getLogger(SetDesignDepartmentRemainingEmployeeList.class);

    private final IProjectUserService projectUserService;
    private final IProjectBusinessService projectBusinessService;

    public SetDesignDepartmentRemainingEmployeeList(IProjectUserService projectUserService, IProjectBusinessService projectBusinessService) {
        this.projectUserService = projectUserService;
        this.projectBusinessService = projectBusinessService;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        for (int i = 0; i < 7; i++) {
            Object projectReviewIdOjb = delegateExecution.getVariable("projectReviewId" + i);
            if (projectReviewIdOjb != null) {
                String projectReviewId = projectReviewIdOjb.toString();

//            String projectReviewId = (String) delegateExecution.getVariable("projectReviewId");
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
                delegateExecution.setVariable("designDepartmentRemainingEmployee" + i + "List", assigneeList);

                //设置项目经理还未汇总审核
                delegateExecution.setVariable("designDepartmentManagerProjectMaterialSummaryReview" + i, 0);
            }
        }
    }
}

