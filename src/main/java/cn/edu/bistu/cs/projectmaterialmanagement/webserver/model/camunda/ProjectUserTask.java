package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.camunda;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.ProjectView;
import org.springframework.stereotype.Component;

@Component
public class ProjectUserTask {
    private ProjectView projectView;
    private String taskId;
    private String taskName;
    private User assignee;
    private String processInstanceId;
    private String taskDefinitionKey;


    private String projectReviewId;//项目材料审批id
    private String useMaterialBrandSelectId;//总包单位选择品牌id

    private boolean designCompanyEmployee;
    private boolean engineeringDepartmentEmployee;
    private boolean designDepartmentManager;
    private boolean engineeringDepartmentManager;
    private boolean designDepartmentEmployee;
    private boolean generalContractorEmployee;
    private boolean supervisionCompanyEmployee;

    public boolean isDesignCompanyEmployee() {
        return designCompanyEmployee;
    }

    public void setDesignCompanyEmployee(boolean designCompanyEmployee) {
        this.designCompanyEmployee = designCompanyEmployee;
    }

    public boolean isEngineeringDepartmentEmployee() {
        return engineeringDepartmentEmployee;
    }

    public void setEngineeringDepartmentEmployee(boolean engineeringDepartmentEmployee) {
        this.engineeringDepartmentEmployee = engineeringDepartmentEmployee;
    }

    public boolean isDesignDepartmentManager() {
        return designDepartmentManager;
    }

    public void setDesignDepartmentManager(boolean designDepartmentManager) {
        this.designDepartmentManager = designDepartmentManager;
    }

    public boolean isEngineeringDepartmentManager() {
        return engineeringDepartmentManager;
    }

    public void setEngineeringDepartmentManager(boolean engineeringDepartmentManager) {
        this.engineeringDepartmentManager = engineeringDepartmentManager;
    }

    public boolean isDesignDepartmentEmployee() {
        return designDepartmentEmployee;
    }

    public void setDesignDepartmentEmployee(boolean designDepartmentEmployee) {
        this.designDepartmentEmployee = designDepartmentEmployee;
    }

    public boolean isGeneralContractorEmployee() {
        return generalContractorEmployee;
    }

    public void setGeneralContractorEmployee(boolean generalContractorEmployee) {
        this.generalContractorEmployee = generalContractorEmployee;
    }

    public boolean isSupervisionCompanyEmployee() {
        return supervisionCompanyEmployee;
    }

    public void setSupervisionCompanyEmployee(boolean supervisionCompanyEmployee) {
        this.supervisionCompanyEmployee = supervisionCompanyEmployee;
    }

    public ProjectView getProjectView() {
        return projectView;
    }

    public void setProjectView(ProjectView projectView) {
        this.projectView = projectView;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }


    public String getProjectReviewId() {
        return projectReviewId;
    }

    public void setProjectReviewId(String projectReviewId) {
        this.projectReviewId = projectReviewId;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getUseMaterialBrandSelectId() {
        return useMaterialBrandSelectId;
    }

    public void setUseMaterialBrandSelectId(String useMaterialBrandSelectId) {
        this.useMaterialBrandSelectId = useMaterialBrandSelectId;
    }
}
