package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.BrandPublicView;
import org.springframework.stereotype.Component;

@Component
public class ProjectMaterialBrandPublicView {
    private ProjectMaterialBrandPublic projectMaterialBrandPublic;//t_project_material_brand_public
    private ProjectMaterial projectMaterial;//外键：t_project_material_id,关联表为：t_project_material表,
    private BrandPublicView brandPublicView;//外键：t_brand_public_id,关联表为：t_brand_public表,


    public ProjectMaterialBrandPublic getProjectMaterialBrandPublic() {
        return projectMaterialBrandPublic;
    }

    public void setProjectMaterialBrandPublic(ProjectMaterialBrandPublic projectMaterialBrandPublic) {
        this.projectMaterialBrandPublic = projectMaterialBrandPublic;
    }

    public ProjectMaterial getProjectMaterial() {
        return projectMaterial;
    }

    public void setProjectMaterial(ProjectMaterial projectMaterial) {
        this.projectMaterial = projectMaterial;
    }

    public BrandPublicView getBrandPublicView() {
        return brandPublicView;
    }

    public void setBrandPublicView(BrandPublicView brandPublicView) {
        this.brandPublicView = brandPublicView;
    }
}