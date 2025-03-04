package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

public class BuyMaterialForm {
    private String projectId;
    private String taskId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    private BuyMaterial[] buyMaterials;


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public BuyMaterial[] getBuyMaterials() {
        return buyMaterials;
    }

    public void setBuyMaterials(BuyMaterial[] buyMaterials) {
        this.buyMaterials = buyMaterials;
    }
}
