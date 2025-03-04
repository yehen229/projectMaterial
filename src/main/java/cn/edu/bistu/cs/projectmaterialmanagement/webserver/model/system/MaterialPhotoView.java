package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

@Component
public class MaterialPhotoView{
	private MaterialPhoto materialPhoto;//t_material_photo
	private Material material;//外键：t_material_id,关联表为：t_material表,


	public MaterialPhoto getMaterialPhoto(){
		return materialPhoto;
	}
	public void setMaterialPhoto(MaterialPhoto materialPhoto){
		this.materialPhoto=materialPhoto;
	}
	public Material getMaterial(){
		return material;
	}
	public void setMaterial(Material material){
		this.material=material;
	}
}