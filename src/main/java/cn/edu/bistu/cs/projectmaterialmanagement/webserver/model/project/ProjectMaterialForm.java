package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Material;
import org.springframework.stereotype.Component;

@Component
public class ProjectMaterialForm {
    private ProjectMaterial projectMaterial;
    private Material material;//物料信息
    private boolean materialChange;//材料变更,用户是否修改了材料参数（不包括品牌、数量和数量单位）
    private String[] publicBrandIds;//参考品牌(公共)，json格式
    private String[] projectBrandIds;//参考品牌（项目私有），json格式
    private ProjectBrandView[]  projectBrandViewList;
    private String photoTempDir;//照片目录
    private String[] photoIds;//照片ID

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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getPhotoTempDir() {
        return photoTempDir;
    }

    public void setPhotoTempDir(String photoTempDir) {
        this.photoTempDir = photoTempDir;
    }

    public String[] getPhotoIds() {
        return photoIds;
    }

    public void setPhotoIds(String[] photoIds) {
        this.photoIds = photoIds;
    }

    public ProjectBrandView[] getProjectBrandViewList() {
        return projectBrandViewList;
    }

    public void setProjectBrandViewList(ProjectBrandView[] projectBrandViewList) {
        this.projectBrandViewList = projectBrandViewList;
    }

    public boolean isMaterialChange() {
        return materialChange;
    }

    public void setMaterialChange(boolean materialChange) {
        this.materialChange = materialChange;
    }
}
