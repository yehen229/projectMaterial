package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectMaterialAcceptanceReview {
    private String id;//id,主键
    private String projectMaterialAcceptanceModeId;//t_project_material_report_id,外键,	t_project_material_report_id<-表t_project_material_acceptance.id
    private int reviewStatus;//review_status,审核状态审核状态
    private int reviewResult;//review_result,最终审核结果最终审核结果
    private Date reviewDatetime;//review_datetime,最终审核时间最终审核时间
    private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(int reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public int getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(int reviewResult) {
        this.reviewResult = reviewResult;
    }

    public Date getReviewDatetime() {
        return reviewDatetime;
    }

    public void setReviewDatetime(Date reviewDatetime) {
        this.reviewDatetime = reviewDatetime;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }


    public String getProjectMaterialAcceptanceModeId() {
        return projectMaterialAcceptanceModeId;
    }

    public void setProjectMaterialAcceptanceModeId(String projectMaterialAcceptanceModeId) {
        this.projectMaterialAcceptanceModeId = projectMaterialAcceptanceModeId;
    }
}