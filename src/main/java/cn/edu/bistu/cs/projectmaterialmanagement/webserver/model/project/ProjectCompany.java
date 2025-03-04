package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectCompany {
    private String id;//id,主键
    private String userId;//t_user_id,外键,	t_user_id<-表t_user.id,用户，工程部员工用户，工程部员工
    private String projectId;//t_project_id
    private String generalContractorCompanyId;//t_general_contractor_company_id,外键,	t_total_package_company_id<-表t_company.id,总包单位总包单位
    private String supervisionCompanyId;//t_supervision_company_id,外键,	t_supervision_company_id<-表t_company.id,监理单位监理单位
    private Date createDatetime;//create_datetime,分配时间分配时间
    private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

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

    public String getGeneralContractorCompanyId() {
        return generalContractorCompanyId;
    }

    public void setGeneralContractorCompanyId(String generalContractorCompanyId) {
        this.generalContractorCompanyId = generalContractorCompanyId;
    }

    public String getSupervisionCompanyId() {
        return supervisionCompanyId;
    }

    public void setSupervisionCompanyId(String supervisionCompanyId) {
        this.supervisionCompanyId = supervisionCompanyId;
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