package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.supervision;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectUserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetSupervisionCompanyEmployeeList implements ExecutionListener {
    private final static Logger logger = LoggerFactory.getLogger(SetSupervisionCompanyEmployeeList.class);

    private final IProjectUserService projectUserService;

    public SetSupervisionCompanyEmployeeList(IProjectUserService projectUserService) {
        this.projectUserService = projectUserService;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        List<ProjectUser> projectUserList = projectUserService.getSupervisionCompanyEmployees(delegateExecution.getBusinessKey());

        //设置监理人员，即监理公司的每个人（包括项目经理和项目员工均可以审批）
        List<String> assigneeList = new ArrayList<String>(); //分配任务的人员


        if (projectUserList != null && !projectUserList.isEmpty()) {
            String users = "";
            for (ProjectUser projectUser : projectUserList) {
                //delegateExecution.addCandidateUser(projectUser.getUserId());
                assigneeList.add(projectUser.getUserId());
            }
        }

        //对应流程图Collection
        delegateExecution.setVariable("supervisionCompanyEmployeeList", assigneeList);
    }
}
