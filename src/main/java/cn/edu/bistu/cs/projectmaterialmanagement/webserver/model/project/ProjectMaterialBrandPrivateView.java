package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

@Component
public class ProjectMaterialBrandPrivateView {
    private ProjectMaterialBrandPrivate projectMaterialBrandPrivate;//t_project_material_brand_private
    private ProjectMaterial projectMaterial;//外键：t_project_material_id,关联表为：t_project_material表,
    private ProjectBrandView projectBrandView;//外键：t_project_brand_id,关联表为：t_project_brand表,

    public ProjectMaterialBrandPrivate getProjectMaterialBrandPrivate() {
        return projectMaterialBrandPrivate;
    }

    public void setProjectMaterialBrandPrivate(ProjectMaterialBrandPrivate projectMaterialBrandPrivate) {
        this.projectMaterialBrandPrivate = projectMaterialBrandPrivate;
    }

    public ProjectMaterial getProjectMaterial() {
        return projectMaterial;
    }

    public void setProjectMaterial(ProjectMaterial projectMaterial) {
        this.projectMaterial = projectMaterial;
    }

    public ProjectBrandView getProjectBrandView() {
        return projectBrandView;
    }

    public void setProjectBrandView(ProjectBrandView projectBrandView) {
        this.projectBrandView = projectBrandView;
    }
}