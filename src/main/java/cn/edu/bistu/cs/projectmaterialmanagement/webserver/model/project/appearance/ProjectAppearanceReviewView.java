package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterial;
import org.springframework.stereotype.Component;

@Component
public class ProjectAppearanceReviewView {
    private ProjectAppearanceReview projectAppearanceReview;//t_project_appearance_review
    private ProjectAppearanceReviewMode projectAppearanceReviewMode;
    private UseMaterial useMaterial;//外键：t_use_material_id,关联表为：t_use_material表,


    public ProjectAppearanceReview getProjectAppearanceReview() {
        return projectAppearanceReview;
    }

    public void setProjectAppearanceReview(ProjectAppearanceReview projectAppearanceReview) {
        this.projectAppearanceReview = projectAppearanceReview;
    }

    public UseMaterial getUseMaterial() {
        return useMaterial;
    }

    public void setUseMaterial(UseMaterial useMaterial) {
        this.useMaterial = useMaterial;
    }

    public ProjectAppearanceReviewMode getProjectAppearanceReviewMode() {
        return projectAppearanceReviewMode;
    }

    public void setProjectAppearanceReviewMode(ProjectAppearanceReviewMode projectAppearanceReviewMode) {
        this.projectAppearanceReviewMode = projectAppearanceReviewMode;
    }
}