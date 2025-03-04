package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaterialView {
    private Material material;
    private MaterialClassifySectionView materialClassifySectionView;
    private List<MaterialBrandView> materialBrandViewList;
    private List<MaterialPhotoView> materialPhotoViewList;

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public MaterialClassifySectionView getMaterialClassifySectionView() {
        return materialClassifySectionView;
    }

    public void setMaterialClassifySectionView(MaterialClassifySectionView materialClassifySectionView) {
        this.materialClassifySectionView = materialClassifySectionView;
    }

    public List<MaterialBrandView> getMaterialBrandViewList() {
        return materialBrandViewList;
    }

    public void setMaterialBrandViewList(List<MaterialBrandView> materialBrandViewList) {
        this.materialBrandViewList = materialBrandViewList;
    }

    public List<MaterialPhotoView> getMaterialPhotoViewList() {
        return materialPhotoViewList;
    }

    public void setMaterialPhotoViewList(List<MaterialPhotoView> materialPhotoViewList) {
        this.materialPhotoViewList = materialPhotoViewList;
    }
}
