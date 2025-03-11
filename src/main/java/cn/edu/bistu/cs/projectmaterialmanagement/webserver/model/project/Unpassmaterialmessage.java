package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

public class Unpassmaterialmessage {
    private String materialId;
    private String projectId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String companyId;
    private String reviewConternt;
    private String userId;
    public Unpassmaterialmessage() {
    }
    public Unpassmaterialmessage(String materialId, String projectId, String companyId, String reviewConternt) {
        this.materialId = materialId;
        this.projectId = projectId;
        this.companyId = companyId;
        this.reviewConternt = reviewConternt;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getReviewConternt() {
        return reviewConternt;
    }

    public void setReviewConternt(String reviewConternt) {
        this.reviewConternt = reviewConternt;
    }



}
