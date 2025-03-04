package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UseMaterialNewBrand {
    private String id;//id,主键
    private String useMaterialId;//t_use_material_id,外键,	t_use_material_id<-表t_use_material.id
    private String brandName;//brand_name,品牌品牌
    private String materialClassifyDivisionId;//t_material_classify_division_id,外键,	t_material_classify_division_id<-表t_material_classify_division.id,大类专业大类专业
    private String materialClassifyGroupId;//t_material_classify_group_id,外键,	t_material_classify_group_id<-表t_material_classify_group.id,中类材料分类中类材料分类
    private String materialClassifySectionId;//t_material_classify_section_id,外键,	t_material_classify_section_id<-表t_material_classify_section.id,小类材料名称小类材料名称
    private String projectMaterialBrandPrivateId;//t_project_material_brand_private_id
    private String materialPosition;//material_position,定位：合资、国产等定位：合资、国产等
    private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUseMaterialId() {
        return useMaterialId;
    }

    public void setUseMaterialId(String useMaterialId) {
        this.useMaterialId = useMaterialId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getMaterialClassifyDivisionId() {
        return materialClassifyDivisionId;
    }

    public void setMaterialClassifyDivisionId(String materialClassifyDivisionId) {
        this.materialClassifyDivisionId = materialClassifyDivisionId;
    }

    public String getMaterialClassifyGroupId() {
        return materialClassifyGroupId;
    }

    public void setMaterialClassifyGroupId(String materialClassifyGroupId) {
        this.materialClassifyGroupId = materialClassifyGroupId;
    }

    public String getMaterialClassifySectionId() {
        return materialClassifySectionId;
    }

    public void setMaterialClassifySectionId(String materialClassifySectionId) {
        this.materialClassifySectionId = materialClassifySectionId;
    }

    public String getMaterialPosition() {
        return materialPosition;
    }

    public void setMaterialPosition(String materialPosition) {
        this.materialPosition = materialPosition;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getProjectMaterialBrandPrivateId() {
        return projectMaterialBrandPrivateId;
    }

    public void setProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        this.projectMaterialBrandPrivateId = projectMaterialBrandPrivateId;
    }
}