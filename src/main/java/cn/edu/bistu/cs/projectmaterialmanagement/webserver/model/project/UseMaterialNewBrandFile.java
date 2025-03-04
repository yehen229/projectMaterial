package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UseMaterialNewBrandFile{
	private String id;//id,主键
	private String useMaterialNewBrandId;//t_use_material_new_brand_id,外键,	t_use_material_new_brand_id<-表t_use_material_new_brand.id
	private String filePath;//file_path
	private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getUseMaterialNewBrandId(){
		return useMaterialNewBrandId;
	}
	public void setUseMaterialNewBrandId(String useMaterialNewBrandId){
		this.useMaterialNewBrandId=useMaterialNewBrandId;
	}
	public String getFilePath(){
		return filePath;
	}
	public void setFilePath(String filePath){
		this.filePath=filePath;
	}
	public Date getDeletedAt(){
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt){
		this.deletedAt=deletedAt;
	}
}