package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance;

import org.springframework.stereotype.Component;

@Component
public class ProjectMaterialAcceptanceReviewDispatchForm {
    private String projectId;
    private String taskId;
    private ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode;
    private String[] employeeIds;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public ProjectMaterialAcceptanceReviewMode getProjectMaterialAcceptanceReviewMode() {
        return projectMaterialAcceptanceReviewMode;
    }

    public void setProjectMaterialAcceptanceReviewMode(ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode) {
        this.projectMaterialAcceptanceReviewMode = projectMaterialAcceptanceReviewMode;
    }

    public String[] getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(String[] employeeIds) {
        this.employeeIds = employeeIds;
    }
}
