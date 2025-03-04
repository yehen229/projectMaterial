package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance;

import org.springframework.stereotype.Component;

@Component
public class ProjectMaterialAcceptanceReviewManagerDirectForm {
    private ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode;
    private ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser;
    private String projectId;
    private String taskId;
    private String reviewTempDir;//review_temp_dir,临时审核附件目录，项目审核附件放在此目录中

    public ProjectMaterialAcceptanceReviewMode getProjectMaterialAcceptanceReviewMode() {
        return projectMaterialAcceptanceReviewMode;
    }

    public void setProjectMaterialAcceptanceReviewMode(ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode) {
        this.projectMaterialAcceptanceReviewMode = projectMaterialAcceptanceReviewMode;
    }

    public ProjectMaterialAcceptanceReviewUser getProjectMaterialAcceptanceReviewUser() {
        return projectMaterialAcceptanceReviewUser;
    }

    public void setProjectMaterialAcceptanceReviewUser(ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser) {
        this.projectMaterialAcceptanceReviewUser = projectMaterialAcceptanceReviewUser;
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

    public String getReviewTempDir() {
        return reviewTempDir;
    }

    public void setReviewTempDir(String reviewTempDir) {
        this.reviewTempDir = reviewTempDir;
    }
}
