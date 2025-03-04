package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectReview {
    private String id;//id,主键
    private String projectId;//t_project_id,外键,	t_project_id<-表t_project.id
    private String projectReviewModeId;//t_project_review_mode_id,外键,
    private int reviewResult;//review_result,最终审核结果最终审核结果
    private int reviewStatus;//review_status,审核状态：审核状态：审核状态：0：未分配审核方式。1：已经分配审核方式，正处于审核状态（t_project_review_mode增加一条记录）如果是直接审核，则t_project_review_user增加项目经理审核意见，提交后修改t_project_review中审核状态；如果是分发审核，则项目员工分别填写意见，项目经理汇总后提交。2.审核结束审核状态：审核状态：审核状态：0：未分配审核方式。1：已经分配审核方式，正处于审核状态（t_project_review_mode增加一条记录）如果是直接审核，则t_project_review_user增加项目经理审核意见，提交后修改t_project_review中审核状态；如果是分发审核，则项目员工分别填写意见，项目经理汇总后提交。2.审核结束
    private Date reviewDatetime;//review_datetime,审核时间审核时间
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

    public int getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(int reviewResult) {
        this.reviewResult = reviewResult;
    }

    public int getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(int reviewStatus) {
        this.reviewStatus = reviewStatus;
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

    public String getProjectReviewModeId() {
        return projectReviewModeId;
    }

    public void setProjectReviewModeId(String projectReviewModeId) {
        this.projectReviewModeId = projectReviewModeId;
    }
}