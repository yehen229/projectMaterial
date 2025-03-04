package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class ProjectMaterial {
    private String id;//id,主键
    private String projectId;//t_project_id,外键,	t_project_id<-表t_project.id
    private String companyId;//t_design_company_id
    private String materialId;//t_material_id,外键,	t_material_id<-表t_material.id
    private BigDecimal materialCount;//material_count,数量数量
    private String materialUnit;//material_unit,数量单位数量单位
    private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
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

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}