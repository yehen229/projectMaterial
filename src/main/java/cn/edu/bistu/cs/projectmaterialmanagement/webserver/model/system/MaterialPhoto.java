package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MaterialPhoto{
	private String id;//id,主键
	private String materialId;//t_material_id,外键,	t_material_id<-表t_material.id
	private String filePath;//file_path,文件路径文件路径
	private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getMaterialId(){
		return materialId;
	}
	public void setMaterialId(String materialId){
		this.materialId=materialId;
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