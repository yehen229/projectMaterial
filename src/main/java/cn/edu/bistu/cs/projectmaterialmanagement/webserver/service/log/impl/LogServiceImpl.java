package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.Log;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.LogView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.ILogService;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log.ILogRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;

@Service
public class LogServiceImpl implements ILogService {

	private static final Logger log =
			LoggerFactory.getLogger(LogServiceImpl.class);

	private final ILogRepository logRepository;

	public LogServiceImpl(ILogRepository logRepository){
		this.logRepository=logRepository;
	}

	/**
	 * 增加
	 */
	@Override
	public String add(Log log){
		return logRepository.add(log);
	}

	/**
	 * 删除
	 */
//	@Override
//	public int delete(Log log){
//		return logRepository.delete(log);
//	}

	/**
	 * 根据id删除记录
	 * @param id
	 */
//	@Override
//	public int deleteById(String id){
//		return logRepository.deleteById(id);
//	}

	/**
	 * 根据userId删除记录
	 * @param userId
	 */
//	@Override
//	public int deleteByUserId(String userId){
//		return logRepository.deleteByUserId(userId);
//	}


	/**
	 * 得到数量
	 */
	@Override
	public int getCount(){
		return logRepository.getCount();
	}

	/**
	 * 根据userId得到数量
	 * @param userId
	 */
	@Override
	public int getCountByUserId(String userId){
		return logRepository.getCountByUserId(userId);
	}

	/**
	 * 根据ProjectId得到数量
	 * @param projectId
	 * @return
	 */
	@Override
	public int getCountByProjectId(String projectId) {
		return logRepository.getCountByProjectId(projectId);
	}

	/**
	 * 根据id得到Log
	 * @param id
	 */
	@Override
	public Log getById(String id){
		return logRepository.getById(id);
	}

	/**
	 * 根据userId得到Log
	 * @param userId
	 */
	@Override
	public List<Log> getByUserId(String userId){
		return logRepository.getByUserId(userId);
	}

	@Override
	public List<Log> getByProjectId(String projectId) {
		return logRepository.getByProjectId(projectId);
	}

	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数
	 */
	@Override
	public Page<Log> getPage(int pageNo, int pageSize){
		return logRepository.getPage(pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param userId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数
	 */
	@Override
	public Page<Log> getPageByUserId(String userId,int pageNo, int pageSize){
		return logRepository.getPageByUserId(userId,pageNo,pageSize);
	}

	@Override
	public Page<Log> getPageByProjectId(String projectId, int pageNo, int pageSize) {
		return null;
	}

	/**
	 * 根据主键获得视图对象
	 * @param id	主键
	 */
	private LogView getLogViewByLogId(String id){
		Log log = getById(id);
		if(log ==null)return null;
		LogView logView=new LogView();
		return null;
	}

	/**
	 * 将页面转换为视图页面
	 * @param logPage	页面对象
	 */
	private Page<LogView> convertLogPage2PageView(Page<Log> logPage,int pageNo, int pageSize){
		if(logPage == null)return null;
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<LogView> list =new ArrayList<>();
		for(Log log:logPage.getResult()){
			LogView logView=getLogViewByLogId(log.getId());
			if(logView!=null)list.add(logView);
		}
		return new Page<>(startIndex, logPage.getTotalCount(), pageSize, list);
	}

}