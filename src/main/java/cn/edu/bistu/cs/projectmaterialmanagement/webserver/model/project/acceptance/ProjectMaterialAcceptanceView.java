package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandPrivateView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandPublicView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialView;
import org.springframework.stereotype.Component;

@Component
public class ProjectMaterialAcceptanceView {
    private ProjectMaterialAcceptance projectMaterialAcceptance;//t_project_material_acceptance
    private User user;//外键：t_user_id,关联表为：t_user表,
    private ProjectMaterialView projectMaterialView;//外键：t_project_material_id,关联表为：t_project_material表,
    private ProjectMaterialBrandPrivateView projectMaterialBrandPrivateView;//外键：t_project_material_brand_private_id,关联表为：t_project_material_brand_private表,
    private ProjectMaterialBrandPublicView projectMaterialBrandPublicView;//外键：t_project_material_brand_public_id,关联表为：t_project_material_brand_public表,


    public ProjectMaterialAcceptance getProjectMaterialAcceptance() {
        return projectMaterialAcceptance;
    }

    public void setProjectMaterialAcceptance(ProjectMaterialAcceptance projectMaterialAcceptance) {
        this.projectMaterialAcceptance = projectMaterialAcceptance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public ProjectMaterialBrandPrivateView getProjectMaterialBrandPrivateView() {
        return projectMaterialBrandPrivateView;
    }

    public void setProjectMaterialBrandPrivateView(ProjectMaterialBrandPrivateView projectMaterialBrandPrivateView) {
        this.projectMaterialBrandPrivateView = projectMaterialBrandPrivateView;
    }

    public ProjectMaterialBrandPublicView getProjectMaterialBrandPublicView() {
        return projectMaterialBrandPublicView;
    }

    public void setProjectMaterialBrandPublicView(ProjectMaterialBrandPublicView projectMaterialBrandPublicView) {
        this.projectMaterialBrandPublicView = projectMaterialBrandPublicView;
    }

    public ProjectMaterialView getProjectMaterialView() {
        return projectMaterialView;
    }

    public void setProjectMaterialView(ProjectMaterialView projectMaterialView) {
        this.projectMaterialView = projectMaterialView;
    }
}