package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectMaterialRetest {
    private String id;//id,主键
    private String buyMaterialId;//t_buy_material_id,外键,	t_buy_material_id<-表t_buy_material.id
    private String userId;//t_user_id,外键,	t_user_id<-表t_user.id
    private int needRetest;//是否需要重测，0为否，1为是
    private int reviewResult;//review_result
    private String reviewContent;//review_content
    private Date reviewDatetime;//review_datetime
    private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuyMaterialId() {
        return buyMaterialId;
    }

    public void setBuyMaterialId(String buyMaterialId) {
        this.buyMaterialId = buyMaterialId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(int reviewResult) {
        this.reviewResult = reviewResult;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
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

    public int getNeedRetest() {
        return needRetest;
    }

    public void setNeedRetest(int needRetest) {
        this.needRetest = needRetest;
    }
}