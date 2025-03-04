package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import org.springframework.stereotype.Component;

@Component
public class BuyMaterialView {
    private BuyMaterial buyMaterial;//t_buy_material
    private User user;//外键：t_user_id,关联表为：t_user表,购买用户（总包单位人员）
    private UseMaterialView useMaterialView;//外键：t_use_material_id,关联表为：t_use_material表,物料使用申请
    private ProjectMaterialBrandPrivateView projectMaterialBrandPrivateView;//外键：t_project_material_brand_private_id,关联表为：t_project_material_brand_private表,私有品牌
    private ProjectMaterialBrandPublicView projectMaterialBrandPublicView;//外键：t_project_material_brand_public_id,关联表为：t_project_material_brand_public表,公有品牌


    public BuyMaterial getBuyMaterial() {
        return buyMaterial;
    }

    public void setBuyMaterial(BuyMaterial buyMaterial) {
        this.buyMaterial = buyMaterial;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UseMaterialView getUseMaterialView() {
        return useMaterialView;
    }

    public void setUseMaterialView(UseMaterialView useMaterialView) {
        this.useMaterialView = useMaterialView;
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
}