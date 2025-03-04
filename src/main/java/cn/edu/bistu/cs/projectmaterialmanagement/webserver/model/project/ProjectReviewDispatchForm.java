package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

@Component
public class ProjectReviewDispatchForm {
    private String projectId;
    private String taskId;
    private int designCompanyIndex;
    private ProjectReviewMode projectReviewMode;
    private String[] employeeIds;

    public ProjectReviewMode getProjectReviewMode() {
        return projectReviewMode;
    }

    public void setProjectReviewMode(ProjectReviewMode projectReviewMode) {
        this.projectReviewMode = projectReviewMode;
    }

    public String[] getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(String[] employeeIds) {
        this.employeeIds = employeeIds;
    }

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

    public int getDesignCompanyIndex() {
        return designCompanyIndex;
    }

    public void setDesignCompanyIndex(int designCompanyIndex) {
        this.designCompanyIndex = designCompanyIndex;
    }
}
