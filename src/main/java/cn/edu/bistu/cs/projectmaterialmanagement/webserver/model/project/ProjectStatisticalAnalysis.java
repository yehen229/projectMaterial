package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReviewView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.ProjectReviewView;
import org.springframework.stereotype.Component;

@Component
public class ProjectStatisticalAnalysis {
    private ProjectReviewView projectReviewView;
    private ProjectAppearanceReviewView projectAppearanceReviewView;
    private ProjectMaterialAcceptanceReviewView projectMaterialAcceptanceReviewView;
    private int totalReviewResult;
    private int totalReviewResultAgree;//同意人数
    private int totalReviewResulDisagree;//不同意人数
    private String projectName;
    private boolean checkProjectWhetherEnd;
    private String projectId;
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }



    public boolean isCheckProjectWhetherEnd() {
        return checkProjectWhetherEnd;
    }

    public void setCheckProjectWhetherEnd(boolean checkProjectWhetherEnd) {
        this.checkProjectWhetherEnd = checkProjectWhetherEnd;
    }
    public boolean getCheckProjectWhetherEnd() {
        return checkProjectWhetherEnd;
    }
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }



    public ProjectReviewView getProjectReviewView() {
        return projectReviewView;
    }

    public void setProjectReviewView(ProjectReviewView projectReviewView) {
        this.projectReviewView = projectReviewView;
    }

    public ProjectAppearanceReviewView getProjectAppearanceReviewView() {
        return projectAppearanceReviewView;
    }

    public void setProjectAppearanceReviewView(ProjectAppearanceReviewView projectAppearanceReviewView) {
        this.projectAppearanceReviewView = projectAppearanceReviewView;
    }

    public ProjectMaterialAcceptanceReviewView getProjectMaterialAcceptanceReviewView() {
        return projectMaterialAcceptanceReviewView;
    }

    public void setProjectMaterialAcceptanceReviewView(ProjectMaterialAcceptanceReviewView projectMaterialAcceptanceReviewView) {
        this.projectMaterialAcceptanceReviewView = projectMaterialAcceptanceReviewView;
    }

    public int getTotalReviewResult() {
        return totalReviewResult;
    }

    public void setTotalReviewResult(int totalReviewResult) {
        this.totalReviewResult = totalReviewResult;
    }

    public int getTotalReviewResultAgree() {
        return totalReviewResultAgree;
    }

    public void setTotalReviewResultAgree(int totalReviewResultAgree) {
        this.totalReviewResultAgree = totalReviewResultAgree;
    }

    public int getTotalReviewResulDisagree() {
        return totalReviewResulDisagree;
    }

    public void setTotalReviewResulDisagree(int totalReviewResulDisagree) {
        this.totalReviewResulDisagree = totalReviewResulDisagree;
    }
}

