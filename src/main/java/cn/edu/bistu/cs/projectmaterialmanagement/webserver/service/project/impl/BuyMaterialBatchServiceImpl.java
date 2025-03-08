package cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.impl;



import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.BuyMaterialBatch;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.BuyMaterialBatchView;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IBuyMaterialBatchRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.project.IBuyMaterialBatchService;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;

@Service
public class BuyMaterialBatchServiceImpl implements IBuyMaterialBatchService {
	private final IBuyMaterialBatchRepository buyMaterialBatchRepository;

	public BuyMaterialBatchServiceImpl(IBuyMaterialBatchRepository buyMaterialBatchRepository){
		this.buyMaterialBatchRepository=buyMaterialBatchRepository;
	}

	/**
	 * 增加
	 */
	@Override
	public String add(BuyMaterialBatch buyMaterialBatch){
		return buyMaterialBatchRepository.add(buyMaterialBatch);
	}

	/**
	 * 删除
	 */
	@Override
	public int delete(BuyMaterialBatch buyMaterialBatch){
		return buyMaterialBatchRepository.delete(buyMaterialBatch);
	}

	/**
	 * 根据id删除记录
	 * @param id
	 */
	@Override
	public int deleteById(String id){
		return buyMaterialBatchRepository.deleteById(id);
	}

	/**
	 * 根据userId删除记录
	 * @param userId
	 */
	@Override
	public int deleteByUserId(String userId){
		return buyMaterialBatchRepository.deleteByUserId(userId);
	}

	/**
	 * 根据project删除记录
	 * @param projectId
	 */
	@Override
	public int deleteByProjectId(String projectId){
		return buyMaterialBatchRepository.deleteByProjectId(projectId);
	}

	/**
	 * 更新
	 */
	@Override
	public int update(BuyMaterialBatch buyMaterialBatch){
		return buyMaterialBatchRepository.update(buyMaterialBatch);
	}

	/**
	 * 得到数量
	 */
	@Override
	public int getCount(){
		return buyMaterialBatchRepository.getCount();
	}

	/**
	 * 根据userId得到数量
	 * @param userId
	 */
	@Override
	public int getCountByUserId(String userId){
		return buyMaterialBatchRepository.getCountByUserId(userId);
	}

	/**
	 * 根据project得到数量
	 * @param projectId
	 */
	@Override
	public int getCountByProjectId(String projectId){
		return buyMaterialBatchRepository.getCountByProjectId(projectId);
	}

	/**
	 * 根据id得到BuyMaterialBatch
	 * @param id
	 */
	@Override
	public BuyMaterialBatch getById(String id){
		return buyMaterialBatchRepository.getById(id);
	}

	/**
	 * 根据userId得到BuyMaterialBatch
	 * @param userId
	 */
	@Override
	public List<BuyMaterialBatch> getByUserId(String userId){
		return buyMaterialBatchRepository.getByUserId(userId);
	}

	/**
	 * 根据project得到BuyMaterialBatch
	 * @param projectId
	 */
	@Override
	public List<BuyMaterialBatch> getByProjectId(String projectId){
		return buyMaterialBatchRepository.getByProjectId(projectId);
	}

	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<BuyMaterialBatch> getPage(int pageNo, int pageSize){
		return buyMaterialBatchRepository.getPage(pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param userId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<BuyMaterialBatch> getPageByUserId(String userId,int pageNo, int pageSize){
		return buyMaterialBatchRepository.getPageByUserId(userId,pageNo,pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param projectId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<BuyMaterialBatch> getPageByProjectId(String projectId,int pageNo, int pageSize){
		return buyMaterialBatchRepository.getPageByProjectId(projectId,pageNo,pageSize);
	}

	/**
	 * 根据主键获得视图对象
	 * @param id	主键 
	 */
	private BuyMaterialBatchView getBuyMaterialBatchViewByBuyMaterialBatchId(String id){
		BuyMaterialBatch buyMaterialBatch = getById(id);
		if(buyMaterialBatch ==null)return null;
		BuyMaterialBatchView buyMaterialBatchView=new BuyMaterialBatchView();
		return null;
	}

	/**
	 * 将页面转换为视图页面
	 * @param buyMaterialBatchPage	页面对象 
	 */
	private Page<BuyMaterialBatchView> convertBuyMaterialBatchPage2PageView(Page<BuyMaterialBatch> buyMaterialBatchPage,int pageNo, int pageSize){
		if(buyMaterialBatchPage == null)return null;
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<BuyMaterialBatchView> list =new ArrayList<>();
		for(BuyMaterialBatch buyMaterialBatch:buyMaterialBatchPage.getResult()){
			BuyMaterialBatchView buyMaterialBatchView=getBuyMaterialBatchViewByBuyMaterialBatchId(buyMaterialBatch.getId());
			if(buyMaterialBatchView!=null)list.add(buyMaterialBatchView);
		}
		return new Page<>(startIndex, buyMaterialBatchPage.getTotalCount(), pageSize, list);
	}

	/**
	 * 获得指定页面视图数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<BuyMaterialBatchView> getPageView(int pageNo, int pageSize){
		Page<BuyMaterialBatch> buyMaterialBatchPage = getPage(pageNo,pageSize);
		return convertBuyMaterialBatchPage2PageView(buyMaterialBatchPage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param userId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<BuyMaterialBatchView> getPageViewByUserId(String userId,int pageNo, int pageSize){
		Page<BuyMaterialBatch> buyMaterialBatchPage = getPageByUserId(userId,pageNo,pageSize);
		return convertBuyMaterialBatchPage2PageView(buyMaterialBatchPage,pageNo, pageSize);
	}

	/**
	 * 获得指定页面视图数据
	 * @param projectId 项目号
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<BuyMaterialBatchView> getPageViewByProjectId(String projectId,int pageNo, int pageSize){
		Page<BuyMaterialBatch> buyMaterialBatchPage = getPageByProjectId(projectId,pageNo,pageSize);
		return convertBuyMaterialBatchPage2PageView(buyMaterialBatchPage,pageNo, pageSize);
	}

}