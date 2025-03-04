package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectOpHistory {
    private String id;//id,主键
    private String userId;//t_user_id,外键,	t_user_id<-表t_user.id,操作或审批人操作或审批人
    private String projectId;//t_project_id,外键,	t_project_id<-表t_project.id,审批的项目审批的项目
    private Date opDatetime;//op_datetime,操作或审批的时间操作或审批的时间
    private String stepDescription;//step_description,步骤详细描述步骤详细描述
    private String stepPhase;//step_phase,步骤的阶段步骤的阶段
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Date getOpDatetime() {
        return opDatetime;
    }

    public void setOpDatetime(Date opDatetime) {
        this.opDatetime = opDatetime;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public String getStepPhase() {
        return stepPhase;
    }

    public void setStepPhase(String stepPhase) {
        this.stepPhase = stepPhase;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}