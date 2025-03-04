package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance;

import org.springframework.stereotype.Component;

@Component
public class ProjectMaterialAcceptanceReviewUserFileView {
    private ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile;//t_project_material_acceptance_review_user_file
    private ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser;//外键：t_project_material_acceptance_review_user_id,关联表为：t_project_material_acceptance_review_user表,


    public ProjectMaterialAcceptanceReviewUserFile getProjectMaterialAcceptanceReviewUserFile() {
        return projectMaterialAcceptanceReviewUserFile;
    }

    public void setProjectMaterialAcceptanceReviewUserFile(ProjectMaterialAcceptanceReviewUserFile projectMaterialAcceptanceReviewUserFile) {
        this.projectMaterialAcceptanceReviewUserFile = projectMaterialAcceptanceReviewUserFile;
    }

    public ProjectMaterialAcceptanceReviewUser getProjectMaterialAcceptanceReviewUser() {
        return projectMaterialAcceptanceReviewUser;
    }

    public void setProjectMaterialAcceptanceReviewUser(ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser) {
        this.projectMaterialAcceptanceReviewUser = projectMaterialAcceptanceReviewUser;
    }
}