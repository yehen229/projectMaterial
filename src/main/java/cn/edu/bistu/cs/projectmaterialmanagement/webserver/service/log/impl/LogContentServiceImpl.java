package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.LogContent;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.LogContentView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.ILogContentService;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log.ILogContentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;

@Service
public class LogContentServiceImpl implements ILogContentService {

	private static final Logger log =
			LoggerFactory.getLogger(LogContentServiceImpl.class);

	private final ILogContentRepository logContentRepository;

	public LogContentServiceImpl(ILogContentRepository logContentRepository){
		this.logContentRepository=logContentRepository;
	}

	/**
	 * 增加
	 */
	@Override
	public String add(LogContent logContent){
		return logContentRepository.add(logContent);
	}

	/**
	 * 删除
	 */
	@Override
	public int delete(LogContent logContent){
		return logContentRepository.delete(logContent);
	}

	/**
	 * 根据id删除记录
	 * @param id
	 */
	@Override
	public int deleteById(String id){
		return logContentRepository.deleteById(id);
	}

	/**
	 * 根据logId删除记录
	 * @param logId
	 */
	@Override
	public int deleteByLogId(String logId){
		return logContentRepository.deleteByLogId(logId);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(LogContent logContent){
		return logContentRepository.update(logContent);
	}

	/**
	 * 得到数量
	 */
	@Override
	public int getCount(){
		return logContentRepository.getCount();
	}

	/**
	 * 根据logId得到数量
	 * @param logId
	 */
	@Override
	public int getCountByLogId(String logId){
		return logContentRepository.getCountByLogId(logId);
	}

	/**
	 * 根据id得到LogContent
	 * @param id
	 */
	@Override
	public LogContent getById(String id){
		return logContentRepository.getById(id);
	}

	/**
	 * 根据logId得到LogContent
	 * @param logId
	 */
	@Override
	public List<LogContent> getByLogId(String logId){
		return logContentRepository.getByLogId(logId);
	}

	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<LogContent> getPage(int pageNo, int pageSize){
		return logContentRepository.getPage(pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param logId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<LogContent> getPageByLogId(String logId,int pageNo, int pageSize){
		return logContentRepository.getPageByLogId(logId,pageNo,pageSize);
	}

	/**
	 * 根据主键获得视图对象
	 * @param id	主键 
	 */
	private LogContentView getLogContentViewByLogContentId(String id){
		LogContent logContent = getById(id);
		if(logContent ==null)return null;
		LogContentView logContentView=new LogContentView();
		return null;
	}

	/**
	 * 将页面转换为视图页面
	 * @param logContentPage	页面对象 
	 */
	private Page<LogContentView> convertLogContentPage2PageView(Page<LogContent> logContentPage,int pageNo, int pageSize){
		if(logContentPage == null)return null;
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<LogContentView> list =new ArrayList<>();
		for(LogContent logContent:logContentPage.getResult()){
			LogContentView logContentView=getLogContentViewByLogContentId(logContent.getId());
			if(logContentView!=null)list.add(logContentView);
		}
		return new Page<>(startIndex, logContentPage.getTotalCount(), pageSize, list);
	}

	/**
	 * 获得指定页面视图数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<LogContentView> getPageView(int pageNo, int pageSize){
		Page<LogContent> logContentPage = getPage(pageNo,pageSize);
		return convertLogContentPage2PageView(logContentPage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param logId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<LogContentView> getPageViewByLogId(String logId,int pageNo, int pageSize){
		Page<LogContent> logContentPage = getPageByLogId(logId,pageNo,pageSize);
		return convertLogContentPage2PageView(logContentPage,pageNo, pageSize);
	}

}