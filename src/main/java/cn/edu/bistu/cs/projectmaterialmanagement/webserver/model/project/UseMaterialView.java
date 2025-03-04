package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import org.springframework.stereotype.Component;

@Component
public class UseMaterialView {
    private UseMaterial useMaterial;//t_use_material
    private ProjectMaterialView projectMaterialView;//外键：t_project_material_id,关联表为：t_project_material表,项目物料
    private User user;//外键：t_user_id,关联表为：t_user表,用户（总包单位人员）
    private UseMaterialNewBrandView useMaterialNewBrandView;
    private ProjectMaterialBrandPrivateView projectMaterialBrandPrivateView;
    private ProjectMaterialBrandPublicView projectMaterialBrandPublicView;


    public UseMaterial getUseMaterial() {
        return useMaterial;
    }

    public void setUseMaterial(UseMaterial useMaterial) {
        this.useMaterial = useMaterial;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public UseMaterialNewBrandView getUseMaterialNewBrandView() {
        return useMaterialNewBrandView;
    }

    public void setUseMaterialNewBrandView(UseMaterialNewBrandView useMaterialNewBrandView) {
        this.useMaterialNewBrandView = useMaterialNewBrandView;
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