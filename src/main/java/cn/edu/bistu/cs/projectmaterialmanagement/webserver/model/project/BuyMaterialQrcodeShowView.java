package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.project.Project;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Brand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Material;

public class BuyMaterialQrcodeShowView {

    private BuyMaterial buyMaterial;//t_buy_material
    private Project project;//project

    private Material material;

    private Brand brand;//获取对应的品牌数据
    public BuyMaterial getBuyMaterial() {
        return buyMaterial;
    }

    public void setBuyMaterial(BuyMaterial buyMaterial) {
        this.buyMaterial = buyMaterial;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Brand getBrand(){ return brand;}
    public void setBrand(Brand brand){ this.brand = brand;}
}
