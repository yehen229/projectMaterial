package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class Project {
    private String id;//id,主键
    private String userId;//t_user_id,外键,	t_user_id<-表t_user.id,创建者创建者
    private String name;//name,项目名称项目名称
    private String location;//location,项目地点项目地点
    private BigDecimal totalTaxIncluded;//total_tax_included,总投资_含税，万元总投资_含税，万元
    private BigDecimal totalTaxNotIncluded;//total_tax_not_included,总投资_不含税，万元总投资_不含税，万元
    private BigDecimal buildingAreaAboveGround;//building_area_above_ground,建筑面积：地上，平米建筑面积：地上，平米
    private BigDecimal buildingAreaUnderGround;//building_area_under_ground,建筑面积：地下，平米建筑面积：地下，平米
    private String companyConstructionId;//t_company_construction_id,外键,	t_company_construction_id<-表t_company.id,建设单位：建设单位类型、内部（设计部、工程部）建设单位：建设单位类型、内部（设计部、工程部）
    private String note;//note,其它其它
    private Date createDatetime;//create_datetime,项目创建时间项目创建时间
    private Date endDatetime;//end_datetime,项目结束时间项目结束时间
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getTotalTaxIncluded() {
        return totalTaxIncluded;
    }

    public void setTotalTaxIncluded(BigDecimal totalTaxIncluded) {
        this.totalTaxIncluded = totalTaxIncluded;
    }

    public BigDecimal getTotalTaxNotIncluded() {
        return totalTaxNotIncluded;
    }

    public void setTotalTaxNotIncluded(BigDecimal totalTaxNotIncluded) {
        this.totalTaxNotIncluded = totalTaxNotIncluded;
    }

    public BigDecimal getBuildingAreaAboveGround() {
        return buildingAreaAboveGround;
    }

    public void setBuildingAreaAboveGround(BigDecimal buildingAreaAboveGround) {
        this.buildingAreaAboveGround = buildingAreaAboveGround;
    }

    public BigDecimal getBuildingAreaUnderGround() {
        return buildingAreaUnderGround;
    }

    public void setBuildingAreaUnderGround(BigDecimal buildingAreaUnderGround) {
        this.buildingAreaUnderGround = buildingAreaUnderGround;
    }

    public String getCompanyConstructionId() {
        return companyConstructionId;
    }

    public void setCompanyConstructionId(String companyConstructionId) {
        this.companyConstructionId = companyConstructionId;
    }


    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Date getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Date endDatetime) {
        this.endDatetime = endDatetime;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}