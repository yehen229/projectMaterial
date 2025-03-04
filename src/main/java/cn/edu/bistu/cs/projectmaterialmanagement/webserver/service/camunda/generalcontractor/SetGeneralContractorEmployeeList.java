package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.generalcontractor;

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
public class SetGeneralContractorEmployeeList implements ExecutionListener {
    private final static Logger logger = LoggerFactory.getLogger(SetGeneralContractorEmployeeList.class);

    private final IProjectUserService projectUserService;

    public SetGeneralContractorEmployeeList(IProjectUserService projectUserService) {
        this.projectUserService = projectUserService;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        //项目的总包单位员工
        List<ProjectUser> projectUserList = projectUserService.getGeneralContractorCompanyEmployees(delegateExecution.getBusinessKey());

        List<String> assigneeList = new ArrayList<String>(); //分配任务的人员

        //设置总包人员，即总包公司的每个人（包括项目经理和项目员工均可以审批）
        if (projectUserList != null && !projectUserList.isEmpty()) {
            String users = "";
            for (ProjectUser projectUser : projectUserList) {
                //delegateExecution.addCandidateUser(projectUser.getUserId());
                assigneeList.add(projectUser.getUserId());
            }

        }

        //对应流程图Collection
        delegateExecution.setVariable("generalContractorCompanyEmployeeList", assigneeList);

    }
}
