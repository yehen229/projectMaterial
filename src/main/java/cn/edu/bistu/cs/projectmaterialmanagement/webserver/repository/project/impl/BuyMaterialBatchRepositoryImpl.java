package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;



import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.BuyMaterialBatch;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IBuyMaterialBatchRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.Types;
import java.sql.SQLException;
@Repository
public class BuyMaterialBatchRepositoryImpl implements IBuyMaterialBatchRepository {
	private final JdbcTemplate jdbcTemplate;

	public BuyMaterialBatchRepositoryImpl(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	/**
	 * RowMapper
	 */
	private static final class BuyMaterialBatchMapper implements RowMapper<BuyMaterialBatch>{
		@Override
		public BuyMaterialBatch mapRow(ResultSet rs, int rowNum) throws SQLException {
			BuyMaterialBatch buyMaterialBatch = new BuyMaterialBatch();
			buyMaterialBatch.setId(rs.getString("id"));
			buyMaterialBatch.setUserId(rs.getString("t_user_id"));
			buyMaterialBatch.setProjectId(rs.getString("t_project_id"));
			buyMaterialBatch.setCreateDatetime(rs.getTimestamp("create_datetime"));
			buyMaterialBatch.setDeletedAt(rs.getTimestamp("deleted_at"));
			return buyMaterialBatch;
		}
	}

	/**
	 * insert
	 */
	@Override
	public String add(BuyMaterialBatch buyMaterialBatch){

		String newId=GUID.getGUID();
		if( jdbcTemplate.update("""
			INSERT INTO t_buy_material_batch(id,
			t_user_id,
			t_project_id,
			create_datetime,
			deleted_at)
			VALUES(?,?,?,?,?)
			""",
			newId,
			buyMaterialBatch.getUserId(),
			buyMaterialBatch.getProjectId(),
			buyMaterialBatch.getCreateDatetime(),
			buyMaterialBatch.getDeletedAt())>0)
			return newId;
		return null;
	}

	/**
	 * delete
	 */
	@Override
	public int delete(BuyMaterialBatch buyMaterialBatch){
		if(buyMaterialBatch==null)return 0;



		return jdbcTemplate.update("""
                        UPDATE t_buy_material_batch
                        SET deleted_at=? 
                        WHERE id=?
                        """, new Date(),
								   buyMaterialBatch.getId());
	}

	/**
	 * 根据id删除记录
	 */
	@Override
	public int deleteById(String id){


		return jdbcTemplate.update("""
                        UPDATE t_buy_material_batch
                        SET deleted_at=? 
                        WHERE id=?
                        """, new Date(),
								   id);
}
	/**
	 * 根据userId删除记录
	 */
	@Override
	public int deleteByUserId(String userId){




		return jdbcTemplate.update("""
                        UPDATE t_buy_material_batch
                        SET deleted_at=? 
                        WHERE t_user_id=?
                        """, new Date(),
								   userId);
}
	/**
	 * 根据project删除记录
	 */
	@Override
	public int deleteByProjectId(String projectId){


		return jdbcTemplate.update("""
                        UPDATE t_buy_material_batch
                        SET deleted_at=? 
                        WHERE t_project_id=?
                        """, new Date(),
								   projectId);
}
	/**
	 * update
	 */
	@Override
	public int update(BuyMaterialBatch buyMaterialBatch){
		return jdbcTemplate.update("""
			UPDATE t_buy_material_batch
			SET t_user_id=?,
			t_project_id=?,
			create_datetime=?,
			deleted_at=? 
			WHERE id=?
			""",
			buyMaterialBatch.getUserId(),
			buyMaterialBatch.getProjectId(),
			buyMaterialBatch.getCreateDatetime(),
			buyMaterialBatch.getDeletedAt(),
			buyMaterialBatch.getId());
	}

	/**
	 * 根据id得到记录
	 */
	@Override
	public BuyMaterialBatch getById(String id){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_buy_material_batch 
			WHERE id=?
			""",Integer.class,id);
			if(i != 1)
				return null;

		return jdbcTemplate.queryForObject("""
			SELECT * 
			FROM t_buy_material_batch
			WHERE id=?
			""",
			new BuyMaterialBatchMapper(),id);
	}
	/**
	 * 根据userId得到记录
	 */
	@Override
	public List<BuyMaterialBatch> getByUserId(String userId){
		Integer i=jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_buy_material_batch
			WHERE t_user_id=?
			""",
			Integer.class,userId);
			if(i == 0)
				return null;

		return jdbcTemplate.query("""
			SELECT * 
			FROM t_buy_material_batch 
			WHERE t_user_id=?
			""",
			new BuyMaterialBatchMapper(),userId);
	}
	/**
	 * 根据project得到记录
	 */
	@Override
	public List<BuyMaterialBatch> getByProjectId(String projectId){
		Integer i=jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_buy_material_batch
			WHERE t_project_id=?
			""",
			Integer.class,projectId);
			if(i == 0)
				return null;

		return jdbcTemplate.query("""
			SELECT * 
			FROM t_buy_material_batch 
			WHERE t_project_id=?
			""",
			new BuyMaterialBatchMapper(),projectId);
	}
	/**
	 * getCount
	 */
	@Override
	public int getCount(){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_buy_material_batch
			""", 
			Integer.class);
		return i==null?0:i;
	}

	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	
	private List<BuyMaterialBatch> getPageQuery(int pageNo, int pageSize){
		return jdbcTemplate.query("""
			SELECT * 
			FROM t_buy_material_batch
			LIMIT ?,?
			""",
			new BuyMaterialBatchMapper(),pageNo * pageSize, pageSize);
	}
	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<BuyMaterialBatch> getPage(int pageNo, int pageSize){
		long totalCount = getCount();
		if (totalCount < 1) return new Page<>();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<BuyMaterialBatch> resultData=getPageQuery(pageNo - 1, pageSize);
		return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
	}

	/**
	 * 根据外键UserId得到总数量
	 */
	@Override
	public int getCountByUserId(String userId){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_buy_material_batch
			WHERE t_user_id=?
			""", 
			Integer.class,userId);
		return i==null?0:i;
	}
	/**
	 * 根据外键（t_user_id）+获得指定页面数据
	 * @param userId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	private List<BuyMaterialBatch> getPageQueryByUserId(String userId,int pageNo, int pageSize){
		return jdbcTemplate.query("""
			SELECT * 
			FROM t_buy_material_batch
			WHERE t_user_id=? 
			LIMIT ?,?
			""",
			new BuyMaterialBatchMapper(),userId,pageNo * pageSize, pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param userId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<BuyMaterialBatch> getPageByUserId(String userId,int pageNo, int pageSize){
		long totalCount = getCountByUserId(userId);
		if (totalCount < 1) return new Page<>();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<BuyMaterialBatch> resultData=getPageQueryByUserId(userId,pageNo - 1, pageSize);
		return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
	}

	/**
	 * 根据外键Project得到总数量
	 */
	@Override
	public int getCountByProjectId(String projectId){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_buy_material_batch
			WHERE t_project_id=?
			""", 
			Integer.class,projectId);
		return i;
	}
	/**
	 * 根据外键（t_project）+获得指定页面数据
	 * @param projectId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	private List<BuyMaterialBatch> getPageQueryByProjectId(String projectId,int pageNo, int pageSize){
		return jdbcTemplate.query("""
			SELECT * 
			FROM t_buy_material_batch
			WHERE t_project_id=? 
			LIMIT ?,?
			""",
			new BuyMaterialBatchMapper(),projectId,pageNo * pageSize, pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param projectId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<BuyMaterialBatch> getPageByProjectId(String projectId,int pageNo, int pageSize){
		long totalCount = getCountByProjectId(projectId);
		if (totalCount < 1) return new Page<>();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<BuyMaterialBatch> resultData=getPageQueryByProjectId(projectId,pageNo - 1, pageSize);
		return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
	}

}