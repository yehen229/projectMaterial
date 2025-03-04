package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

@Component
public class UseMaterialNewBrandFileView{
	private UseMaterialNewBrandFile useMaterialNewBrandFile;//t_use_material_new_brand_file
	private UseMaterialNewBrand useMaterialNewBrand;//外键：t_use_material_new_brand_id,关联表为：t_use_material_new_brand表,


	public UseMaterialNewBrandFile getUseMaterialNewBrandFile(){
		return useMaterialNewBrandFile;
	}
	public void setUseMaterialNewBrandFile(UseMaterialNewBrandFile useMaterialNewBrandFile){
		this.useMaterialNewBrandFile=useMaterialNewBrandFile;
	}
	public UseMaterialNewBrand getUseMaterialNewBrand(){
		return useMaterialNewBrand;
	}
	public void setUseMaterialNewBrand(UseMaterialNewBrand useMaterialNewBrand){
		this.useMaterialNewBrand=useMaterialNewBrand;
	}
}