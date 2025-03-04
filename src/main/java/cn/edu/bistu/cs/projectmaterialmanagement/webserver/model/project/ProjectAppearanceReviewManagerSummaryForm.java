package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

public class ProjectAppearanceReviewManagerSummaryForm {
    private ProjectAppearanceReviewUser projectAppearanceReviewUser;
    private String projectId;
    private String taskId;

    private String reviewTempDir;//review_temp_dir,临时审核附件目录，项目审核附件放在此目录中
    private String[] employeeReviewFileIds;

    public ProjectAppearanceReviewUser getProjectAppearanceReviewUser() {
        return projectAppearanceReviewUser;
    }

    public void setProjectAppearanceReviewUser(ProjectAppearanceReviewUser projectAppearanceReviewUser) {
        this.projectAppearanceReviewUser = projectAppearanceReviewUser;
    }

    public String getReviewTempDir() {
        return reviewTempDir;
    }

    public void setReviewTempDir(String reviewTempDir) {
        this.reviewTempDir = reviewTempDir;
    }

    public String[] getEmployeeReviewFileIds() {
        return employeeReviewFileIds;
    }

    public void setEmployeeReviewFileIds(String[] employeeReviewFileIds) {
        this.employeeReviewFileIds = employeeReviewFileIds;
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
