package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.LogBussinessOp;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.LogBussinessOpView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.ILogBussinessOpService;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log.ILogBussinessOpRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;

@Service
public class LogBussinessOpServiceImpl implements ILogBussinessOpService {

	private static final Logger log =
			LoggerFactory.getLogger(LogBussinessOpServiceImpl.class);

	private final ILogBussinessOpRepository logBussinessOpRepository;

	public LogBussinessOpServiceImpl(ILogBussinessOpRepository logBussinessOpRepository){
		this.logBussinessOpRepository=logBussinessOpRepository;
	}

	/**
	 * 增加
	 */
	@Override
	public String add(LogBussinessOp logBussinessOp){
		return logBussinessOpRepository.add(logBussinessOp);
	}

	/**
	 * 删除
	 */
	@Override
	public int delete(LogBussinessOp logBussinessOp){
		return logBussinessOpRepository.delete(logBussinessOp);
	}

	/**
	 * 根据id删除记录
	 * @param id
	 */
	@Override
	public int deleteById(String id){
		return logBussinessOpRepository.deleteById(id);
	}

	/**
	 * 根据logId删除记录
	 * @param logId
	 */
	@Override
	public int deleteByLogId(String logId){
		return logBussinessOpRepository.deleteByLogId(logId);
	}

	/**
	 * 根据logBusinessId删除记录
	 * @param logBusinessId
	 */
	@Override
	public int deleteByLogBusinessId(String logBusinessId){
		return logBussinessOpRepository.deleteByLogBusinessId(logBusinessId);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(LogBussinessOp logBussinessOp){
		return logBussinessOpRepository.update(logBussinessOp);
	}

	/**
	 * 得到数量
	 */
	@Override
	public int getCount(){
		return logBussinessOpRepository.getCount();
	}

	/**
	 * 根据logId得到数量
	 * @param logId
	 */
	@Override
	public int getCountByLogId(String logId){
		return logBussinessOpRepository.getCountByLogId(logId);
	}

	/**
	 * 根据logBusinessId得到数量
	 * @param logBusinessId
	 */
	@Override
	public int getCountByLogBusinessId(String logBusinessId){
		return logBussinessOpRepository.getCountByLogBusinessId(logBusinessId);
	}

	/**
	 * 根据id得到LogBussinessOp
	 * @param id
	 */
	@Override
	public LogBussinessOp getById(String id){
		return logBussinessOpRepository.getById(id);
	}

	/**
	 * 根据logId得到LogBussinessOp
	 * @param logId
	 */
	@Override
	public List<LogBussinessOp> getByLogId(String logId){
		return logBussinessOpRepository.getByLogId(logId);
	}

	/**
	 * 根据logBusinessId得到LogBussinessOp
	 * @param logBusinessId
	 */
	@Override
	public List<LogBussinessOp> getByLogBusinessId(String logBusinessId){
		return logBussinessOpRepository.getByLogBusinessId(logBusinessId);
	}

	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<LogBussinessOp> getPage(int pageNo, int pageSize){
		return logBussinessOpRepository.getPage(pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param logId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<LogBussinessOp> getPageByLogId(String logId,int pageNo, int pageSize){
		return logBussinessOpRepository.getPageByLogId(logId,pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param logBusinessId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<LogBussinessOp> getPageByLogBusinessId(String logBusinessId,int pageNo, int pageSize){
		return logBussinessOpRepository.getPageByLogBusinessId(logBusinessId,pageNo,pageSize);
	}

	/**
	 * 根据主键获得视图对象
	 * @param id	主键 
	 */
	private LogBussinessOpView getLogBussinessOpViewByLogBussinessOpId(String id){
		LogBussinessOp logBussinessOp = getById(id);
		if(logBussinessOp ==null)return null;
		LogBussinessOpView logBussinessOpView=new LogBussinessOpView();
		return null;
	}

	/**
	 * 将页面转换为视图页面
	 * @param logBussinessOpPage	页面对象 
	 */
	private Page<LogBussinessOpView> convertLogBussinessOpPage2PageView(Page<LogBussinessOp> logBussinessOpPage,int pageNo, int pageSize){
		if(logBussinessOpPage == null)return null;
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<LogBussinessOpView> list =new ArrayList<>();
		for(LogBussinessOp logBussinessOp:logBussinessOpPage.getResult()){
			LogBussinessOpView logBussinessOpView=getLogBussinessOpViewByLogBussinessOpId(logBussinessOp.getId());
			if(logBussinessOpView!=null)list.add(logBussinessOpView);
		}
		return new Page<>(startIndex, logBussinessOpPage.getTotalCount(), pageSize, list);
	}

	/**
	 * 获得指定页面视图数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<LogBussinessOpView> getPageView(int pageNo, int pageSize){
		Page<LogBussinessOp> logBussinessOpPage = getPage(pageNo,pageSize);
		return convertLogBussinessOpPage2PageView(logBussinessOpPage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param logId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<LogBussinessOpView> getPageViewByLogId(String logId,int pageNo, int pageSize){
		Page<LogBussinessOp> logBussinessOpPage = getPageByLogId(logId,pageNo,pageSize);
		return convertLogBussinessOpPage2PageView(logBussinessOpPage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param logBusinessId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<LogBussinessOpView> getPageViewByLogBusinessId(String logBusinessId,int pageNo, int pageSize){
		Page<LogBussinessOp> logBussinessOpPage = getPageByLogBusinessId(logBusinessId,pageNo,pageSize);
		return convertLogBussinessOpPage2PageView(logBussinessOpPage,pageNo, pageSize);
	}

}