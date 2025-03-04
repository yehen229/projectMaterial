package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.admin;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.flow.IProjectVariable;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyUserService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SetProjectAdministratorList implements ExecutionListener {

    private final static Logger logger = LoggerFactory.getLogger(SetProjectAdministratorList.class);
    private final ICompanyUserService companyUserService;
    private final IProjectService projectService;
    private final IUserService userService;

    public SetProjectAdministratorList(ICompanyUserService companyUserService,
                                       IProjectService projectService,
                                       IUserService userService) {
        this.companyUserService = companyUserService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {


        String projectId = delegateExecution.getBusinessKey();
        Project project = projectService.getById(projectId);
        delegateExecution.setVariable(IProjectVariable.TASK_VARIABLE_PROJECT_ID, projectId);//设置项目号


        logger.info("工作流-监听器:ProjectStartExecutionListener:" + delegateExecution.getEventName());

        //设置下个节点授权人员为admin
        List<User> userList = userService.getAllAdminUsers();

        List<String> assigneeList = new ArrayList<String>(); //分配任务的人员

        //设置总包人员，即总包公司的每个人（包括项目经理和项目员工均可以审批）
        if (userList != null && !userList.isEmpty()) {
            String users = "";
            for (User user : userList) {
                assigneeList.add(user.getId());
            }

        }

        //对应流程图Collection
        delegateExecution.setVariable("projectAdministratorList", assigneeList);


    }
}
