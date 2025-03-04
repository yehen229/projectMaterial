package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance;

import org.springframework.stereotype.Component;

@Component
public class ProjectMaterialAcceptanceReviewView {
    private ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview;//t_project_material_acceptance_review
    private ProjectMaterialAcceptance projectMaterialAcceptance;//外键：t_project_material_report_id,关联表为：t_project_material_acceptance表,


    public ProjectMaterialAcceptanceReview getProjectMaterialAcceptanceReview() {
        return projectMaterialAcceptanceReview;
    }

    public void setProjectMaterialAcceptanceReview(ProjectMaterialAcceptanceReview projectMaterialAcceptanceReview) {
        this.projectMaterialAcceptanceReview = projectMaterialAcceptanceReview;
    }

    public ProjectMaterialAcceptance getProjectMaterialAcceptance() {
        return projectMaterialAcceptance;
    }

    public void setProjectMaterialAcceptance(ProjectMaterialAcceptance projectMaterialAcceptance) {
        this.projectMaterialAcceptance = projectMaterialAcceptance;
    }
}