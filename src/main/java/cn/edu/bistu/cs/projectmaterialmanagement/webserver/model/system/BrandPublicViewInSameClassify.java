package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BrandPublicViewInSameClassify {
    private BrandPublic brandPublic;
    private MaterialClassifySectionView materialClassifySectionView;
    private List<Brand> brandList;

    public BrandPublic getBrandPublic() {
        return brandPublic;
    }

    public void setBrandPublic(BrandPublic brandPublic) {
        this.brandPublic = brandPublic;
    }

    public MaterialClassifySectionView getMaterialClassifySectionView() {
        return materialClassifySectionView;
    }

    public void setMaterialClassifySectionView(MaterialClassifySectionView materialClassifySectionView) {
        this.materialClassifySectionView = materialClassifySectionView;
    }

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }
}
