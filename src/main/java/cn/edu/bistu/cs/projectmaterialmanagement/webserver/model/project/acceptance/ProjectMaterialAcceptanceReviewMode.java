package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectMaterialAcceptanceReviewMode {
    private String id;//id,主键
    private String userId;//t_user_id,外键,	t_user_id<-表t_user.id
    private String projectMaterialAcceptanceBatchId;//t_project_material_acceptance_batch_id,外键,	t_project_material_acceptance_batch_id<-表t_project_material_acceptance_batch.id
    private int mode;//mode,分发审核或直接审核分发审核或直接审核
    private Date createDatetime;//create_datetime
    private Date deletedAt;//deleted_at


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

    public String getProjectMaterialAcceptanceBatchId() {
        return projectMaterialAcceptanceBatchId;
    }

    public void setProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId) {
        this.projectMaterialAcceptanceBatchId = projectMaterialAcceptanceBatchId;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
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