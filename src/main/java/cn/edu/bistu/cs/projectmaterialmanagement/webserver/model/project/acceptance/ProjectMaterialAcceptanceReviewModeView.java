package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import org.springframework.stereotype.Component;

@Component
public class ProjectMaterialAcceptanceReviewModeView {
    private ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode;//t_project_material_acceptance_review_mode
    private User user;//外键：t_user_id,关联表为：t_user表,
    private ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch;//外键：t_project_material_acceptance_batch_id,关联表为：t_project_material_acceptance_batch表,


    public ProjectMaterialAcceptanceReviewMode getProjectMaterialAcceptanceReviewMode() {
        return projectMaterialAcceptanceReviewMode;
    }

    public void setProjectMaterialAcceptanceReviewMode(ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode) {
        this.projectMaterialAcceptanceReviewMode = projectMaterialAcceptanceReviewMode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProjectMaterialAcceptanceBatch getProjectMaterialAcceptanceBatch() {
        return projectMaterialAcceptanceBatch;
    }

    public void setProjectMaterialAcceptanceBatch(ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch) {
        this.projectMaterialAcceptanceBatch = projectMaterialAcceptanceBatch;
    }
}