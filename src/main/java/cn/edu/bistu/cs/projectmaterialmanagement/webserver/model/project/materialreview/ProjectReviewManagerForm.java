package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview;

import org.springframework.stereotype.Component;

@Component
public class ProjectReviewManagerForm {
    private String projectId;
    private String taskId;
    private int designCompanyIndex;
    private ProjectReviewUser projectReviewUser;
    private String reviewTempDir;//review_temp_dir,临时审核附件目录，项目审核附件放在此目录中
    private String[] employeeReviewFileIds;


    public ProjectReviewUser getProjectReviewUser() {
        return projectReviewUser;
    }

    public void setProjectReviewUser(ProjectReviewUser projectReviewUser) {
        this.projectReviewUser = projectReviewUser;
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

    public int getDesignCompanyIndex() {
        return designCompanyIndex;
    }

    public void setDesignCompanyIndex(int designCompanyIndex) {
        this.designCompanyIndex = designCompanyIndex;
    }
}
