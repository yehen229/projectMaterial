package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectMaterialVerificationDocumentFile{
	private String id;//id,主键
	private String projectMaterialVerificationDocumentId;//t_project_material_verification_document_id,外键,	t_project_material_verification_document_id<-表t_project_material_verification_document.id
	private String filePath;//file_path,文件路径文件路径
	private int fileType;//file_type,文件类型：工程材料或设备报验材料文件类型：工程材料或设备报验材料
	private Date deletedAt;//deleted_at,null表示未删，否则 表示删除时间null表示未删，否则 表示删除时间


	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getProjectMaterialVerificationDocumentId(){
		return projectMaterialVerificationDocumentId;
	}
	public void setProjectMaterialVerificationDocumentId(String projectMaterialVerificationDocumentId){
		this.projectMaterialVerificationDocumentId=projectMaterialVerificationDocumentId;
	}
	public String getFilePath(){
		return filePath;
	}
	public void setFilePath(String filePath){
		this.filePath=filePath;
	}
	public int getFileType(){
		return fileType;
	}
	public void setFileType(int fileType){
		this.fileType=fileType;
	}
	public Date getDeletedAt(){
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt){
		this.deletedAt=deletedAt;
	}
}