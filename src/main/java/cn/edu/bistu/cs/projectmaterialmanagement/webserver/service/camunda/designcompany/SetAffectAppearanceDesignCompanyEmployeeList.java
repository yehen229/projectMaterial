package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.designcompany;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectDesignCompany;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.camunda.admin.SetProjectAdministratorList;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.system.ICompanyUserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SetAffectAppearanceDesignCompanyEmployeeList implements ExecutionListener {

    private final static Logger logger = LoggerFactory.getLogger(SetProjectAdministratorList.class);
    private final ICompanyUserService companyUserService;
    private final IProjectService projectService;
    private final IProjectUserService projectUserService;
    private final IUseMaterialBusinessService useMaterialBusinessService;
    private final IProjectDesignCompanyService projectDesignCompanyService;
    private final IProjectMaterialService projectMaterialService;

    public SetAffectAppearanceDesignCompanyEmployeeList(ICompanyUserService companyUserService,
                                                        IProjectService projectService,
                                                        IProjectDesignCompanyService projectDesignCompanyService,
                                                        IUseMaterialBusinessService useMaterialBusinessService,
                                                        IProjectUserService projectUserService,
                                                        IProjectMaterialService projectMaterialService) {

        this.companyUserService = companyUserService;
        this.projectService = projectService;
        this.projectDesignCompanyService = projectDesignCompanyService;
        this.useMaterialBusinessService = useMaterialBusinessService;
        this.projectMaterialService = projectMaterialService;
        this.projectUserService = projectUserService;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {


        String projectId = delegateExecution.getBusinessKey();
        Project project = projectService.getById(projectId);
        //   delegateExecution.setVariable(IProjectVariable.TASK_VARIABLE_PROJECT_ID, projectId);//设置项目号
        String useMaterialBrandSelectId = (String) delegateExecution.getVariable("useMaterialBrandSelectId");
        List<UseMaterialView> useMaterialViews = useMaterialBusinessService.getViewListByUseMaterialBrandSelectId(useMaterialBrandSelectId);
        if (useMaterialViews == null || useMaterialViews.isEmpty()) return;

// 提取所有设计公司ID（可能多个）
        Set<String> designCompanyIdSet = new HashSet<>();
        for (UseMaterialView view : useMaterialViews) {
            UseMaterial useMaterial = view.getUseMaterial();
            if (useMaterial == null) continue;

            ProjectMaterial projectMaterial = projectMaterialService.getById(useMaterial.getProjectMaterialId());
            if (projectMaterial == null) continue;

            String designCompanyId = projectMaterial.getCompanyId();
            if (designCompanyId != null && !designCompanyId.isEmpty()) {
                designCompanyIdSet.add(designCompanyId);
            }
        }

        if (designCompanyIdSet.isEmpty()) return;

        logger.info("工作流-监听器:ProjectStartExecutionListener:" + delegateExecution.getEventName());

        List<ProjectUser> projectUserList = projectUserService.getDesignCompanyEmployees(projectId);
        Set<String> projectUserIdSet = projectUserList.stream()
                .map(ProjectUser::getUserId)
                .collect(Collectors.toSet());
        List<String> designCompanyIdList = new ArrayList<>(designCompanyIdSet);
        int maxDesignCompanies = Math.min(designCompanyIdList.size(), 8);

        logger.info("工作流-监听器:ProjectStartExecutionListener 设置设计单位人员成功，共设置: " + maxDesignCompanies + " 个设计单位");
        for (int i = 0; i < 8; i++) {
            if (i < designCompanyIdList.size()) {
                // 正常设置人员列表
                String designCompanyId = designCompanyIdList.get(i);
                ProjectDesignCompany projectDesignCompany = projectDesignCompanyService.getByProjectIdAndDesignCompanyId(projectId, designCompanyId);
                if (projectDesignCompany == null) {
                    delegateExecution.setVariable("designCompanyEmployee" + i + "List", Collections.emptyList());
                    continue;
                }

                List<CompanyUser> companyUserList = companyUserService.getByCompanyId(projectDesignCompany.getDesignCompanyId());
                List<String> assigneeList = new ArrayList<>();
                if (companyUserList != null) {
                    for (CompanyUser user : companyUserList) {
                        if (projectUserIdSet.contains(user.getUserId())) {
                            assigneeList.add(user.getUserId());
                        }
                    }
                }

                delegateExecution.setVariable("designCompanyEmployee" + i + "List", assigneeList);
            } else {
                // 设置为空集合以避免流程报错
                delegateExecution.setVariable("designCompanyEmployee" + i + "List", Collections.emptyList());
            }
        }

    }
}