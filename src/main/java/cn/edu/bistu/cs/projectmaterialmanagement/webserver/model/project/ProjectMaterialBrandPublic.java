package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectMaterialBrandPublic {
    private String id;//id,主键
    private String projectMaterialId;//t_project_material_id,外键,	t_project_material_id<-表t_project_material.id
    private String brandPublicId;//t_brand_public_id,外键,	t_brand_public_id<-表t_brand_public.id
    private Date deletedAt;//deleted_at


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectMaterialId() {
        return projectMaterialId;
    }

    public void setProjectMaterialId(String projectMaterialId) {
        this.projectMaterialId = projectMaterialId;
    }

    public String getBrandPublicId() {
        return brandPublicId;
    }

    public void setBrandPublicId(String brandPublicId) {
        this.brandPublicId = brandPublicId;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}