package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectMaterialAcceptanceReviewUserView {
    private ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser;//t_project_material_acceptance_review_user
    private User user;//外键：t_user_id,关联表为：t_user表,
    private ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview;//外键：t_project_material_acceptance_review_id,关联表为：t_project_material_acceptance_review表,

    private List<ProjectMaterialAcceptanceReviewUserFile> projectMaterialAcceptanceReviewUserFileList;


    public ProjectMaterialAcceptanceReviewUser getProjectMaterialAcceptanceReviewUser() {
        return projectMaterialAcceptanceReviewUser;
    }

    public void setProjectMaterialAcceptanceReviewUser(ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser) {
        this.projectMaterialAcceptanceReviewUser = projectMaterialAcceptanceReviewUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProjectMaterialAcceptanceReview getProjectMaterialAcceptanceReview() {
        return projectMaterialAcceptanceReview;
    }

    public void setProjectMaterialAcceptanceReview(ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview) {
        this.projectMaterialAcceptanceReview = projectMaterialAcceptanceReview;
    }

    public List<ProjectMaterialAcceptanceReviewUserFile> getProjectMaterialAcceptanceReviewUserFileList() {
        return projectMaterialAcceptanceReviewUserFileList;
    }

    public void setProjectMaterialAcceptanceReviewUserFileList(List<ProjectMaterialAcceptanceReviewUserFile> projectMaterialAcceptanceReviewUserFileList) {
        this.projectMaterialAcceptanceReviewUserFileList = projectMaterialAcceptanceReviewUserFileList;
    }
}