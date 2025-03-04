package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectFileView;

import java.util.List;

public interface IProjectFileService {
	String add(ProjectFile projectFile);
	int delete(ProjectFile projectFile);
	int update(ProjectFile projectFile);
	int deleteById(String id);
	int deleteByProjectId(String projectId);
	int getCount();
	int getCountByProjectId(String projectId);
	ProjectFile getById(String id);
	List<ProjectFile> getByProjectId(String projectId);
	Page<ProjectFile> getPage(int pageNo, int pageSize);
	Page<ProjectFile> getPageByProjectId(String projectId,int pageNo, int pageSize);
	Page<ProjectFileView> getPageView(int pageNo, int pageSize);
	Page<ProjectFileView> getPageViewByProjectId(String projectId,int pageNo, int pageSize);

}