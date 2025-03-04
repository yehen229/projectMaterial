package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

@Component
public class MaterialBrandView{
	private MaterialBrand materialBrand;//t_material_brand
	private Material material;//外键：t_material_id,关联表为：t_material表,
	private Brand brand;//外键：t_brand_id,关联表为：t_brand表,


	public MaterialBrand getMaterialBrand(){
		return materialBrand;
	}
	public void setMaterialBrand(MaterialBrand materialBrand){
		this.materialBrand=materialBrand;
	}
	public Material getMaterial(){
		return material;
	}
	public void setMaterial(Material material){
		this.material=material;
	}
	public Brand getBrand(){
		return brand;
	}
	public void setBrand(Brand brand){
		this.brand=brand;
	}
}