package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UseMaterial {
    private String id;//id,主键
    private String useMaterialBrandSelectId;//t_use_material_brand_select_id
    private String projectMaterialId;//t_project_material_id,外键,	t_project_material_id<-表t_project_material.id,项目物料项目物料
    private String userId;//t_user_id,外键,	t_user_id<-表t_user.id,用户（总包单位人员）用户（总包单位人员）
    private String projectMaterialBrandPrivateId;//t_project_material_brand_private_id,外键,	t_project_material_brand_private_id<-表t_project_material_brand_private.id,品牌品牌
    private String projectMaterialBrandPublicId;//t_project_material_brand_public_id,外键,	t_project_material_brand_public_id<-表t_project_material_brand_public.id
    private int isAppearance;//is_appearance,是否影响外观是否影响外观
    private Date createDatetime;//create_datetime,创建时间创建时间
    private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间

    public String getUseMaterialBrandSelectId() {
        return useMaterialBrandSelectId;
    }

    public void setUseMaterialBrandSelectId(String useMaterialBrandSelectId) {
        this.useMaterialBrandSelectId = useMaterialBrandSelectId;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProjectMaterialBrandPrivateId() {
        return projectMaterialBrandPrivateId;
    }

    public void setProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        this.projectMaterialBrandPrivateId = projectMaterialBrandPrivateId;
    }

    public String getProjectMaterialBrandPublicId() {
        return projectMaterialBrandPublicId;
    }

    public void setProjectMaterialBrandPublicId(String projectMaterialBrandPublicId) {
        this.projectMaterialBrandPublicId = projectMaterialBrandPublicId;
    }

    public int getIsAppearance() {
        return isAppearance;
    }

    public void setIsAppearance(int isAppearance) {
        this.isAppearance = isAppearance;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}