package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

@Component
public class UseMaterialFormItem {
    String projectMaterialId;//项目物料号
    int isAppearance;//是否影响外观
    String projectMaterialBrandPublicId;//项目物料品牌号(公开)
    String projectMaterialBrandPrivateId;//项目物料品牌号(私有)
    String userId;//用户号
    String brandName;//品牌名称，如果是新建品牌，则此字段不能为空
    String materialClassifyDivisionId;//物料大类号
    String materialClassifyGroupId;//物料中类号
    String materialClassifySectionId;//物料小类号
    String materialPosition; //品牌定位：合资、国产等
    String tempFileDir;//新建品牌附件目录，如果是新建品牌，则此字段不能为空


    public String getProjectMaterialId() {
        return projectMaterialId;
    }

    public void setProjectMaterialId(String projectMaterialId) {
        this.projectMaterialId = projectMaterialId;
    }

    public int getIsAppearance() {
        return isAppearance;
    }

    public void setIsAppearance(int isAppearance) {
        this.isAppearance = isAppearance;
    }

    public String getProjectMaterialBrandPublicId() {
        return projectMaterialBrandPublicId;
    }

    public void setProjectMaterialBrandPublicId(String projectMaterialBrandPublicId) {
        this.projectMaterialBrandPublicId = projectMaterialBrandPublicId;
    }

    public String getProjectMaterialBrandPrivateId() {
        return projectMaterialBrandPrivateId;
    }

    public void setProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        this.projectMaterialBrandPrivateId = projectMaterialBrandPrivateId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getMaterialClassifyDivisionId() {
        return materialClassifyDivisionId;
    }

    public void setMaterialClassifyDivisionId(String materialClassifyDivisionId) {
        this.materialClassifyDivisionId = materialClassifyDivisionId;
    }

    public String getMaterialClassifyGroupId() {
        return materialClassifyGroupId;
    }

    public void setMaterialClassifyGroupId(String materialClassifyGroupId) {
        this.materialClassifyGroupId = materialClassifyGroupId;
    }

    public String getMaterialClassifySectionId() {
        return materialClassifySectionId;
    }

    public void setMaterialClassifySectionId(String materialClassifySectionId) {
        this.materialClassifySectionId = materialClassifySectionId;
    }

    public String getMaterialPosition() {
        return materialPosition;
    }

    public void setMaterialPosition(String materialPosition) {
        this.materialPosition = materialPosition;
    }

    public String getTempFileDir() {
        return tempFileDir;
    }

    public void setTempFileDir(String tempFileDir) {
        this.tempFileDir = tempFileDir;
    }
}
