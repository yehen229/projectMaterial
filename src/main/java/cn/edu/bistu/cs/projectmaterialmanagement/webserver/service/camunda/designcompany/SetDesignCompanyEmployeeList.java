package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.designcompany;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectDesignCompany;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.admin.SetProjectAdministratorList;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectDesignCompanyService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyUserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetDesignCompanyEmployeeList implements ExecutionListener {

    private final static Logger logger = LoggerFactory.getLogger(SetProjectAdministratorList.class);
    private final ICompanyUserService companyUserService;
    private final IProjectService projectService;
    private final IProjectDesignCompanyService projectDesignCompanyService;

    public SetDesignCompanyEmployeeList(ICompanyUserService companyUserService,
                                        IProjectService projectService,
                                        IProjectDesignCompanyService projectDesignCompanyService) {
        this.companyUserService = companyUserService;
        this.projectService = projectService;
        this.projectDesignCompanyService = projectDesignCompanyService;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {


        String projectId = delegateExecution.getBusinessKey();
        Project project = projectService.getById(projectId);
        //   delegateExecution.setVariable(IProjectVariable.TASK_VARIABLE_PROJECT_ID, projectId);//设置项目号


        logger.info("工作流-监听器:ProjectStartExecutionListener:" + delegateExecution.getEventName());

        //目前只能支持最多8个设计单位
        delegateExecution.setVariable("nHaveDesignCompany0", 0);
        delegateExecution.setVariable("nHaveDesignCompany1", 0);
        delegateExecution.setVariable("nHaveDesignCompany2", 0);
        delegateExecution.setVariable("nHaveDesignCompany3", 0);
        delegateExecution.setVariable("nHaveDesignCompany4", 0);
        delegateExecution.setVariable("nHaveDesignCompany5", 0);
        delegateExecution.setVariable("nHaveDesignCompany6", 0);
        delegateExecution.setVariable("nHaveDesignCompany7", 0);


        List<ProjectDesignCompany> projectDesignCompanyList = projectDesignCompanyService.getByProjectId(projectId);
        if (projectDesignCompanyList != null && !projectDesignCompanyList.isEmpty()) {


            int index = 0;

            for (ProjectDesignCompany projectDesignCompany : projectDesignCompanyList) {

                List<String> assigneeList = new ArrayList<String>(); //分配任务的人员

                //设置下个节点授权人员为设计单位人员
                List<CompanyUser> companyUserList = companyUserService.getByCompanyId(
                        projectDesignCompany.getDesignCompanyId());
                if (companyUserList == null || companyUserList.isEmpty())
                    continue;

                //设置总包人员，即总包公司的每个人（包括项目经理和项目员工均可以审批）

                for (CompanyUser companyUser : companyUserList) {
                    assigneeList.add(companyUser.getUserId());
                }

                //对应流程图Collection
                if (index == 0) {
                    //设置第0个设计单位，供后面节点使用
                    delegateExecution.setVariable("nDesignCompanyId0", projectDesignCompany.getDesignCompanyId());

                    //设置第0个设计单位存在
                    delegateExecution.setVariable("nHaveDesignCompany0", 1);

                    //设置第0个设计单位人员列表
                    delegateExecution.setVariable("designCompanyEmployee0List", assigneeList);
                } else if (index == 1) {
                    //设置第1个设计单位，供后面节点使用
                    delegateExecution.setVariable("nDesignCompanyId1", projectDesignCompany.getDesignCompanyId());

                    //设置第1个设计单位存在
                    delegateExecution.setVariable("nHaveDesignCompany1", 1);

                    //设置第1个设计单位人员列表
                    delegateExecution.setVariable("designCompanyEmployee1List", assigneeList);
                } else if (index == 2) {
                    //设置第2个设计单位，供后面节点使用
                    delegateExecution.setVariable("nDesignCompanyId2", projectDesignCompany.getDesignCompanyId());

                    //设置第2个设计单位存在
                    delegateExecution.setVariable("nHaveDesignCompany2", 1);

                    //设置第2个设计单位人员列表
                    delegateExecution.setVariable("designCompanyEmployee2List", assigneeList);
                } else if (index == 3) {
                    //设置第3个设计单位，供后面节点使用
                    delegateExecution.setVariable("nDesignCompanyId3", projectDesignCompany.getDesignCompanyId());

                    //设置第3个设计单位存在
                    delegateExecution.setVariable("nHaveDesignCompany3", 1);

                    //设置第3个设计单位人员列表
                    delegateExecution.setVariable("designCompanyEmployee3List", assigneeList);
                } else if (index == 4) {
                    //设置第4个设计单位，供后面节点使用
                    delegateExecution.setVariable("nDesignCompanyId4", projectDesignCompany.getDesignCompanyId());

                    //设置第4个设计单位存在
                    delegateExecution.setVariable("nHaveDesignCompany4", 1);

                    //设置第4个设计单位人员列表
                    delegateExecution.setVariable("designCompanyEmployee4List", assigneeList);
                } else if (index == 5) {
                    //设置第5个设计单位，供后面节点使用
                    delegateExecution.setVariable("nDesignCompanyId5", projectDesignCompany.getDesignCompanyId());

                    //设置第5个设计单位存在
                    delegateExecution.setVariable("nHaveDesignCompany5", 1);

                    //设置第5个设计单位人员列表
                    delegateExecution.setVariable("designCompanyEmployee5List", assigneeList);
                } else if (index == 6) {
                    //设置第6个设计单位，供后面节点使用
                    delegateExecution.setVariable("nDesignCompanyId6", projectDesignCompany.getDesignCompanyId());

                    //设置第6个设计单位存在
                    delegateExecution.setVariable("nHaveDesignCompany6", 1);

                    //设置第6个设计单位人员列表
                    delegateExecution.setVariable("designCompanyEmployee6List", assigneeList);
                } else if (index == 7) {
                    //设置第0个设计单位，供后面节点使用
                    delegateExecution.setVariable("nDesignCompanyId7", projectDesignCompany.getDesignCompanyId());

                    //设置第7个设计单位存在
                    delegateExecution.setVariable("nHaveDesignCompany7", 1);

                    //设置第7个设计单位人员列表
                    delegateExecution.setVariable("designCompanyEmployee7List", assigneeList);
                }


                index++;


            }
        }


    }
}