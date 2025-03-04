package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

@Component
public class ProjectMaterialForm {
    private ProjectMaterial projectMaterial;
    private String[] publicBrandIds;//参考品牌(公共)，json格式
    private String[] projectBrandIds;//参考品牌（项目私有），json格式

    public ProjectMaterial getProjectMaterial() {
        return projectMaterial;
    }

    public void setProjectMaterial(ProjectMaterial projectMaterial) {
        this.projectMaterial = projectMaterial;
    }

    public String[] getPublicBrandIds() {
        return publicBrandIds;
    }

    public void setPublicBrandIds(String[] publicBrandIds) {
        this.publicBrandIds = publicBrandIds;
    }

    public String[] getProjectBrandIds() {
        return projectBrandIds;
    }

    public void setProjectBrandIds(String[] projectBrandIds) {
        this.projectBrandIds = projectBrandIds;
    }
}
