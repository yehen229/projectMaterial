package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectFileView;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectFileRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IProjectFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;

@Service
public class ProjectFileServiceImpl implements IProjectFileService {

	private static final Logger log =
			LoggerFactory.getLogger(ProjectFileServiceImpl.class);

	private final IProjectFileRepository projectFileRepository;

	public ProjectFileServiceImpl(IProjectFileRepository projectFileRepository){
		this.projectFileRepository=projectFileRepository;
	}

	/**
	 * 增加
	 */
	@Override
	public String add(ProjectFile projectFile){
		return projectFileRepository.add(projectFile);
	}

	/**
	 * 删除
	 */
	@Override
	public int delete(ProjectFile projectFile){
		return projectFileRepository.delete(projectFile);
	}

	/**
	 * 根据id删除记录
	 * @param id
	 */
	@Override
	public int deleteById(String id){
		return projectFileRepository.deleteById(id);
	}

	/**
	 * 根据projectId删除记录
	 * @param projectId
	 */
	@Override
	public int deleteByProjectId(String projectId){
		return projectFileRepository.deleteByProjectId(projectId);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(ProjectFile projectFile){
		return projectFileRepository.update(projectFile);
	}

	/**
	 * 得到数量
	 */
	@Override
	public int getCount(){
		return projectFileRepository.getCount();
	}

	/**
	 * 根据projectId得到数量
	 * @param projectId
	 */
	@Override
	public int getCountByProjectId(String projectId){
		return projectFileRepository.getCountByProjectId(projectId);
	}

	/**
	 * 根据id得到ProjectFile
	 * @param id
	 */
	@Override
	public ProjectFile getById(String id){
		return projectFileRepository.getById(id);
	}

	/**
	 * 根据projectId得到ProjectFile
	 * @param projectId
	 */
	@Override
	public List<ProjectFile> getByProjectId(String projectId){
		return projectFileRepository.getByProjectId(projectId);
	}

	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectFile> getPage(int pageNo, int pageSize){
		return projectFileRepository.getPage(pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param projectId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectFile> getPageByProjectId(String projectId,int pageNo, int pageSize){
		return projectFileRepository.getPageByProjectId(projectId,pageNo,pageSize);
	}

	/**
	 * 根据主键获得视图对象
	 * @param id	主键 
	 */
	private ProjectFileView getProjectFileViewByProjectFileId(String id){
		ProjectFile projectFile = getById(id);
		if(projectFile ==null)return null;
		ProjectFileView projectFileView=new ProjectFileView();
		return null;
	}

	/**
	 * 将页面转换为视图页面
	 * @param projectFilePage	页面对象 
	 */
	private Page<ProjectFileView> convertProjectFilePage2PageView(Page<ProjectFile> projectFilePage,int pageNo, int pageSize){
		if(projectFilePage == null)return null;
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectFileView> list =new ArrayList<>();
		for(ProjectFile projectFile:projectFilePage.getResult()){
			ProjectFileView projectFileView=getProjectFileViewByProjectFileId(projectFile.getId());
			if(projectFileView!=null)list.add(projectFileView);
		}
		return new Page<>(startIndex, projectFilePage.getTotalCount(), pageSize, list);
	}

	/**
	 * 获得指定页面视图数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectFileView> getPageView(int pageNo, int pageSize){
		Page<ProjectFile> projectFilePage = getPage(pageNo,pageSize);
		return convertProjectFilePage2PageView(projectFilePage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param projectId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectFileView> getPageViewByProjectId(String projectId,int pageNo, int pageSize){
		Page<ProjectFile> projectFilePage = getPageByProjectId(projectId,pageNo,pageSize);
		return convertProjectFilePage2PageView(projectFilePage,pageNo, pageSize);
	}

}