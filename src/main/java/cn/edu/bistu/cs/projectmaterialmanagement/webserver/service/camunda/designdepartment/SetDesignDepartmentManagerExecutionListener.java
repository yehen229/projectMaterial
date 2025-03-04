package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.designdepartment;

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
public class SetDesignDepartmentManagerExecutionListener implements ExecutionListener {
    private final static Logger logger = LoggerFactory.getLogger(SetDesignDepartmentManagerExecutionListener.class);

    private final IProjectUserService projectUserService;

    public SetDesignDepartmentManagerExecutionListener(IProjectUserService projectUserService) {
        this.projectUserService = projectUserService;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        //设置下一步的操作人员为本项目的设计部经理
        ProjectUser projectUser = projectUserService.getManagerOfDesignDepartment(delegateExecution.getBusinessKey());

        List<String> assigneeList = new ArrayList<String>(); //分配任务的人员

        //设置项目经理
        if (projectUser != null) {


            //delegateExecution.addCandidateUser(projectUser.getUserId());
            assigneeList.add(projectUser.getUserId());

            //对应流程图Collection
            delegateExecution.setVariable("design_department_manager", projectUser.getUserId());


        }


    }
}

