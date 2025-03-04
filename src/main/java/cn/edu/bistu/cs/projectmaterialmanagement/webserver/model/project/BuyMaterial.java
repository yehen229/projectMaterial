package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class BuyMaterial {
    private String id;//id,主键
    private String userId;//t_user_id,外键,	t_user_id<-表t_user.id,购买用户（总包单位人员）购买用户（总包单位人员）
    private String useMaterialId;//t_use_material_id,外键,	t_use_material_id<-表t_use_material.id,物料使用申请物料使用申请
    private String projectMaterialBrandPrivateId;//t_project_material_brand_private_id,外键,	t_project_material_brand_private_id<-表t_project_material_brand_private.id,私有品牌私有品牌
    private String projectMaterialBrandPublicId;//t_project_material_brand_public_id,外键,	t_project_material_brand_public_id<-表t_project_material_brand_public.id,公有品牌公有品牌
    private BigDecimal materialCount;//material_count,材料数量材料数量
    private String materialUnit;//material_unit,数量单位数量单位
    private int batch;//batch,批次批次
    private String qrcode;//qrcode,二维码二维码
    private Date createDatetime;//create_datetime,创建时间创建时间
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

    public String getUseMaterialId() {
        return useMaterialId;
    }

    public void setUseMaterialId(String useMaterialId) {
        this.useMaterialId = useMaterialId;
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

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
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