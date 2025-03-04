package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

@Component
public class MaterialForm {
    private Material material;
    private String photoTempDir;//photo_temp_dir,临时图片目录，材料样本图片放在此目录中
    private String[] brandIds;//参考品牌，json格式
    private String[] photoIds;

    public String[] getPhotoIds() {
        return photoIds;
    }

    public void setPhotoIds(String[] photoIds) {
        this.photoIds = photoIds;
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

    public String[] getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String[] brandIds) {
        this.brandIds = brandIds;
    }
}