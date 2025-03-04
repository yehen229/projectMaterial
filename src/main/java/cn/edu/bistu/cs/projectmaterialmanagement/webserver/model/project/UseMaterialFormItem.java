package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Material;
import org.springframework.stereotype.Component;

@Component
public class UseMaterialFormItem {
  private  String projectMaterialId;//项目物料号
    private int isAppearance;//是否影响外观
    private String projectMaterialBrandPublicId;//项目物料品牌号(公开)
    private String projectMaterialBrandPrivateId;//项目物料品牌号(私有)
    private String userId;//用户号
    private String brandName;//品牌名称，如果是新建品牌，则此字段不能为空
    private String materialClassifyDivisionId;//物料大类号
    private String materialClassifyGroupId;//物料中类号
    private String materialClassifySectionId;//物料小类号
    private String materialPosition; //品牌定位：合资、国产等
    private String tempFileDir;//新建品牌附件目录，如果是新建品牌，则此字段不能为空

    //材料变更相关
    private boolean materialChange;//是否需要物料变更
    private Material material;//材料
    private ProjectMaterial projectMaterial;//项目物料
    private String photoFileDir;//材料样本图片目录
    private String[] photoIds;//材料样本图片id


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

    public boolean isMaterialChange() {
        return materialChange;
    }

    public void setMaterialChange(boolean materialChange) {
        this.materialChange = materialChange;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public ProjectMaterial getProjectMaterial() {
        return projectMaterial;
    }

    public void setProjectMaterial(ProjectMaterial projectMaterial) {
        this.projectMaterial = projectMaterial;
    }

    public String getPhotoFileDir() {
        return photoFileDir;
    }

    public void setPhotoFileDir(String photoFileDir) {
        this.photoFileDir = photoFileDir;
    }

    public String[] getPhotoIds() {
        return photoIds;
    }

    public void setPhotoIds(String[] photoIds) {
        this.photoIds = photoIds;
    }
}
