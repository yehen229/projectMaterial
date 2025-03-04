package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview;

import org.springframework.stereotype.Component;

@Component
public class ProjectReviewStatistics {
    private ProjectReviewView projectReviewView;
    private int reviewResultAccept;//同意人数
    private int reviewResultReject;//不同意人数
    private int reviewResultUnreviewed;//未审核人数

    public ProjectReviewView getProjectReviewView() {
        return projectReviewView;
    }

    public void setProjectReviewView(ProjectReviewView projectReviewView) {
        this.projectReviewView = projectReviewView;
    }

    public int getReviewResultAccept() {
        return reviewResultAccept;
    }

    public void setReviewResultAccept(int reviewResultAccept) {
        this.reviewResultAccept = reviewResultAccept;
    }

    public int getReviewResultReject() {
        return reviewResultReject;
    }

    public void setReviewResultReject(int reviewResultReject) {
        this.reviewResultReject = reviewResultReject;
    }

    public int getReviewResultUnreviewed() {
        return reviewResultUnreviewed;
    }

    public void setReviewResultUnreviewed(int reviewResultUnreviewed) {
        this.reviewResultUnreviewed = reviewResultUnreviewed;
    }
}
