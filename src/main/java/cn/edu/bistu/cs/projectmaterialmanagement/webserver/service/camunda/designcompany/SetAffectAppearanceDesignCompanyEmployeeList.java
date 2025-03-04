package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.designcompany;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectDesignCompany;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.admin.SetProjectAdministratorList;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectDesignCompanyService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectMaterialService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IUseMaterialBusinessService;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyUserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetAffectAppearanceDesignCompanyEmployeeList implements ExecutionListener {

    private final static Logger logger = LoggerFactory.getLogger(SetProjectAdministratorList.class);
    private final ICompanyUserService companyUserService;
    private final IProjectService projectService;
    private final IUseMaterialBusinessService useMaterialBusinessService;
    private final IProjectDesignCompanyService projectDesignCompanyService;
    private final IProjectMaterialService projectMaterialService;

    public SetAffectAppearanceDesignCompanyEmployeeList(ICompanyUserService companyUserService,
                                                        IProjectService projectService,
                                                        IProjectDesignCompanyService projectDesignCompanyService,
                                                        IUseMaterialBusinessService useMaterialBusinessService,
                                                        IProjectMaterialService projectMaterialService) {

        this.companyUserService = companyUserService;
        this.projectService = projectService;
        this.projectDesignCompanyService = projectDesignCompanyService;
        this.useMaterialBusinessService = useMaterialBusinessService;
        this.projectMaterialService = projectMaterialService;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {


        String projectId = delegateExecution.getBusinessKey();
        Project project = projectService.getById(projectId);
        //   delegateExecution.setVariable(IProjectVariable.TASK_VARIABLE_PROJECT_ID, projectId);//设置项目号
        String useMaterialBrandSelectId = (String) delegateExecution.getVariable("useMaterialBrandSelectId");
        List<UseMaterialView> useMaterialViews = useMaterialBusinessService.getViewListByUseMaterialBrandSelectId(useMaterialBrandSelectId);
        if (useMaterialViews == null || useMaterialViews.isEmpty()) return;



            UseMaterial useMaterial = useMaterialViews.get(0).getUseMaterial();
            if (useMaterial == null) return;
            ProjectMaterial projectMaterial = projectMaterialService.getById(useMaterial.getProjectMaterialId());
            String designCompanyId = projectMaterial.getCompanyId();
            if (designCompanyId == null || designCompanyId.isEmpty()) return;
            logger.info("工作流-监听器:ProjectStartExecutionListener:" + delegateExecution.getEventName());
            ProjectDesignCompany projectDesignCompany = projectDesignCompanyService.getByProjectIdAndDesignCompanyId(projectId, designCompanyId);
            if (projectDesignCompany == null) return;
            List<CompanyUser> companyUserList = companyUserService.getByCompanyId(
                    projectDesignCompany.getDesignCompanyId());
            List<String> assigneeList = new ArrayList<String>(); //分配任务的人员

            if (companyUserList != null || !companyUserList.isEmpty()) {
                for (CompanyUser companyUser : companyUserList) {
                    assigneeList.add(companyUser.getUserId());

                }
            }


            delegateExecution.setVariable("designCompanyEmployeeList", assigneeList);

    }
}