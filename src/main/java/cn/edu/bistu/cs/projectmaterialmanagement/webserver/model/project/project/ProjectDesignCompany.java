package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectDesignCompany {
    private String id;//id,主键
    private String projectId;
    private String designCompanyId;
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

    public String getDesignCompanyId() {
        return designCompanyId;
    }

    public void setDesignCompanyId(String designCompanyId) {
        this.designCompanyId = designCompanyId;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}