package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class ProjectMaterialAcceptance {
    private String id;//id,主键
    private String userId;//t_user_id,外键,	t_user_id<-表t_user.id
    private String projectMaterialAcceptanceBatchId;//t_project_material_acceptance_batch_id,外键,	t_project_material_acceptance_batch_id<-表t_project_material_acceptance_batch.id
    private String projectMaterialId;//t_project_material_id,外键,	t_project_material_id<-表t_project_material.id
    private String projectMaterialBrandPrivateId;//t_project_material_brand_private_id,外键,	t_project_material_brand_private_id<-表t_project_material_brand_private.id
    private String projectMaterialBrandPublicId;//t_project_material_brand_public_id,外键,	t_project_material_brand_public_id<-表t_project_material_brand_public.id
    private BigDecimal materialCount;//material_count,材料数量材料数量
    private String materialUnit;//material_unit,数量单位数量单位
    private String position;
    private String note;
    private Date createDatetime;//create_datetime
    private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProjectMaterialId() {
        return projectMaterialId;
    }

    public void setProjectMaterialId(String projectMaterialId) {
        this.projectMaterialId = projectMaterialId;
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

    public BigDecimal getMaterialCount() {
        return materialCount;
    }

    public void setMaterialCount(BigDecimal materialCount) {
        this.materialCount = materialCount;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
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


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getProjectMaterialAcceptanceBatchId() {
        return projectMaterialAcceptanceBatchId;
    }

    public void setProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId) {
        this.projectMaterialAcceptanceBatchId = projectMaterialAcceptanceBatchId;
    }
}