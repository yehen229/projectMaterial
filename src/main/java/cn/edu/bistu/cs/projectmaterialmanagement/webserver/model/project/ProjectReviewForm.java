package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

@Component
public class ProjectReviewForm {
    private String projectId;
    private String taskId;
    private int designCompanyIndex;
    private ProjectReviewMode projectReviewMode;
    private ProjectReview projectReview;
    private ProjectReviewUser projectReivewUser;
    private String reviewTempDir;//review_temp_dir,临时审核附件目录，项目审核附件放在此目录中

    public ProjectReviewMode getProjectReviewMode() {
        return projectReviewMode;
    }

    public void setProjectReviewMode(ProjectReviewMode projectReviewMode) {
        this.projectReviewMode = projectReviewMode;
    }

    public ProjectReview getProjectReview() {
        return projectReview;
    }

    public void setProjectReview(ProjectReview projectReview) {
        this.projectReview = projectReview;
    }

    public ProjectReviewUser getProjectReivewUser() {
        return projectReivewUser;
    }

    public void setProjectReivewUser(ProjectReviewUser projectReivewUser) {
        this.projectReivewUser = projectReivewUser;
    }

    public String getReviewTempDir() {
        return reviewTempDir;
    }

    public void setReviewTempDir(String reviewTempDir) {
        this.reviewTempDir = reviewTempDir;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getDesignCompanyIndex() {
        return designCompanyIndex;
    }

    public void setDesignCompanyIndex(int designCompanyIndex) {
        this.designCompanyIndex = designCompanyIndex;
    }
}
