package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.engineeringdepartment;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.flow.IProjectVariable;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectUserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 设置下一个节点处理人员为管理人员
 */
@Service
public class SetEngineeringDepartmentManagerExecutionListener implements ExecutionListener {
    private final static Logger logger = LoggerFactory.getLogger(SetEngineeringDepartmentManagerExecutionListener.class);

    private final IProjectUserService projectUserService;
    private final IProjectService projectService;
    private final IUserService userService;

    public SetEngineeringDepartmentManagerExecutionListener(IProjectUserService projectUserService, IProjectService projectService, IUserService userService) {
        this.projectUserService = projectUserService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String projectId = delegateExecution.getBusinessKey();
        ProjectUser projectUser = projectUserService.getManagerOfEngineeringDepartment(projectId);
        if (projectUser == null) {
            throw new BusinessException("未找到工程部经理");
        }

        //设置下一个节点的处理人为工程部经理
        delegateExecution.setVariable(IProjectVariable.engineering_department_manager, projectUser.getUserId());

    }
}
