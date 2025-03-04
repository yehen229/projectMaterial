package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance;

public class ProjectAppearanceReviewDispatchForm {
    private String projectId;
    private String taskId;
    private ProjectAppearanceReviewMode projectAppearanceReviewMode;
    private String[] employeeIds;

    public ProjectAppearanceReviewMode getProjectAppearanceReviewMode() {
        return projectAppearanceReviewMode;
    }

    public void setProjectAppearanceReviewMode(ProjectAppearanceReviewMode projectAppearanceReviewMode) {
        this.projectAppearanceReviewMode = projectAppearanceReviewMode;
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


}
