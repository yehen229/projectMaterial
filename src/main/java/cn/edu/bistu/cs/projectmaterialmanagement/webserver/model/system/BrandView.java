package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

@Component
public class BrandView {
    private Brand brand;
    private MaterialClassifySectionView materialClassifySectionView;

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public MaterialClassifySectionView getMaterialClassifySectionView() {
        return materialClassifySectionView;
    }

    public void setMaterialClassifySectionView(MaterialClassifySectionView materialClassifySectionView) {
        this.materialClassifySectionView = materialClassifySectionView;
    }
}
