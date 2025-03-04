package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

@Component
public class ProjectAppearanceReviewManagerDirectForm {
    private ProjectAppearanceReviewMode projectAppearanceReviewMode;
    private ProjectAppearanceReviewUser projectAppearanceReviewUser;
    private String projectId;
    private String taskId;

    private String reviewTempDir;//review_temp_dir,临时审核附件目录，项目审核附件放在此目录中


    public ProjectAppearanceReviewUser getProjectAppearanceReviewUser() {
        return projectAppearanceReviewUser;
    }

    public void setProjectAppearanceReviewUser(ProjectAppearanceReviewUser projectAppearanceReviewUser) {
        this.projectAppearanceReviewUser = projectAppearanceReviewUser;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getReviewTempDir() {
        return reviewTempDir;
    }

    public void setReviewTempDir(String reviewTempDir) {
        this.reviewTempDir = reviewTempDir;
    }

    public ProjectAppearanceReviewMode getProjectAppearanceReviewMode() {
        return projectAppearanceReviewMode;
    }

    public void setProjectAppearanceReviewMode(ProjectAppearanceReviewMode projectAppearanceReviewMode) {
        this.projectAppearanceReviewMode = projectAppearanceReviewMode;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


}
