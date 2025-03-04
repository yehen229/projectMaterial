package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.log.LogBusiness;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.impl.UserServiceImpl;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.log.ILogBusinessService;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.log.ILogBusinessRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogBusinessServiceImpl implements ILogBusinessService {

	private static final Logger log =
			LoggerFactory.getLogger(LogBusinessServiceImpl.class);

	private final ILogBusinessRepository logBusinessRepository;

	public LogBusinessServiceImpl(ILogBusinessRepository logBusinessRepository){
		this.logBusinessRepository=logBusinessRepository;
	}

	/**
	 * 增加
	 */
	@Override
	public String add(LogBusiness logBusiness){
		return logBusinessRepository.add(logBusiness);
	}

	/**
	 * 删除
	 */
	@Override
	public int delete(LogBusiness logBusiness){
		return logBusinessRepository.delete(logBusiness);
	}

	/**
	 * 根据id删除记录
	 * @param id
	 */
	@Override
	public int deleteById(String id){
		return logBusinessRepository.deleteById(id);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(LogBusiness logBusiness){
		return logBusinessRepository.update(logBusiness);
	}

	/**
	 * 得到数量
	 */
	@Override
	public int getCount(){
		return logBusinessRepository.getCount();
	}

	/**
	 * 根据id得到LogBusiness
	 * @param id
	 */
	@Override
	public LogBusiness getById(String id){
		return logBusinessRepository.getById(id);
	}

	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<LogBusiness> getPage(int pageNo, int pageSize){
		return logBusinessRepository.getPage(pageNo,pageSize);
	}

}