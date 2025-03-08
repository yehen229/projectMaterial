package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;



import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetestBatch;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialRetestBatchRepository;
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
public class ProjectMaterialRetestBatchRepositoryImpl implements IProjectMaterialRetestBatchRepository {
	private final JdbcTemplate jdbcTemplate;

	public ProjectMaterialRetestBatchRepositoryImpl(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	/**
	 * RowMapper
	 */
	private static final class ProjectMaterialRetestBatchMapper implements RowMapper<ProjectMaterialRetestBatch>{
		@Override
		public ProjectMaterialRetestBatch mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectMaterialRetestBatch projectMaterialRetestBatch = new ProjectMaterialRetestBatch();
			projectMaterialRetestBatch.setId(rs.getString("id"));
			projectMaterialRetestBatch.setUserId(rs.getString("t_user_id"));
			projectMaterialRetestBatch.setProjectId(rs.getString("t_project_id"));
			projectMaterialRetestBatch.setBuyMaterialBatchId(rs.getString("t_buy_material_batch_id"));
			projectMaterialRetestBatch.setCreateDatetime(rs.getTimestamp("create_datetime"));
			projectMaterialRetestBatch.setDeletedAt(rs.getTimestamp("deleted_at"));
			return projectMaterialRetestBatch;
		}
	}

	/**
	 * insert
	 */
	@Override
	public String add(ProjectMaterialRetestBatch projectMaterialRetestBatch){

		String newId=GUID.getGUID();
		if( jdbcTemplate.update("""
			INSERT INTO t_project_material_retest_batch(id,
			t_user_id,
			t_project_id,
			t_buy_material_batch_id,
			create_datetime,
			deleted_at)
			VALUES(?,?,?,?,?,?)
			""",
			newId,
			projectMaterialRetestBatch.getUserId(),
			projectMaterialRetestBatch.getProjectId(),
			projectMaterialRetestBatch.getBuyMaterialBatchId(),
			projectMaterialRetestBatch.getCreateDatetime(),
			projectMaterialRetestBatch.getDeletedAt())>0)
			return newId;
		return null;
	}

	/**
	 * delete
	 */
	@Override
	public int delete(ProjectMaterialRetestBatch projectMaterialRetestBatch){
		if(projectMaterialRetestBatch==null)return 0;


		return jdbcTemplate.update("""
                        UPDATE t_project_material_retest_batch
                        SET deleted_at=? 
                        WHERE id=?
                        """, new Date(),
								   projectMaterialRetestBatch.getId());
	}

	/**
	 * 根据id删除记录
	 */
	@Override
	public int deleteById(String id){

		return jdbcTemplate.update("""
                        UPDATE t_project_material_retest_batch
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
                        UPDATE t_project_material_retest_batch
                        SET deleted_at=? 
                        WHERE t_user_id=?
                        """, new Date(),
								   userId);
}
	/**
	 * 根据projectId删除记录
	 */
	@Override
	public int deleteByProjectId(String projectId){


		return jdbcTemplate.update("""
                        UPDATE t_project_material_retest_batch
                        SET deleted_at=? 
                        WHERE t_project_id=?
                        """, new Date(),
								   projectId);
}
	/**
	 * 根据buyMaterialBatchId删除记录
	 */
	@Override
	public int deleteByBuyMaterialBatchId(String buyMaterialBatchId){


		return jdbcTemplate.update("""
                        UPDATE t_project_material_retest_batch
                        SET deleted_at=? 
                        WHERE t_buy_material_batch_id=?
                        """, new Date(),
								   buyMaterialBatchId);
}
	/**
	 * update
	 */
	@Override
	public int update(ProjectMaterialRetestBatch projectMaterialRetestBatch){
		return jdbcTemplate.update("""
			UPDATE t_project_material_retest_batch
			SET t_user_id=?,
			t_project_id=?,
			t_buy_material_batch_id=?,
			create_datetime=?,
			deleted_at=? 
			WHERE id=?
			""",
			projectMaterialRetestBatch.getUserId(),
			projectMaterialRetestBatch.getProjectId(),
			projectMaterialRetestBatch.getBuyMaterialBatchId(),
			projectMaterialRetestBatch.getCreateDatetime(),
			projectMaterialRetestBatch.getDeletedAt(),
			projectMaterialRetestBatch.getId());
	}

	/**
	 * 根据id得到记录
	 */
	@Override
	public ProjectMaterialRetestBatch getById(String id){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_retest_batch 
			WHERE id=?
			""",Integer.class,id);
			if(i==null||i!=1)
				return null;

		return jdbcTemplate.queryForObject("""
			SELECT * 
			FROM t_project_material_retest_batch
			WHERE id=?
			""",
			new ProjectMaterialRetestBatchMapper(),id);
	}
	/**
	 * 根据userId得到记录
	 */
	@Override
	public List<ProjectMaterialRetestBatch> getByUserId(String userId){
		Integer i=jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_retest_batch
			WHERE t_user_id=?
			""",
			Integer.class,userId);
			if(i==null||i==0)
				return null;

		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_retest_batch 
			WHERE t_user_id=?
			""",
			new ProjectMaterialRetestBatchMapper(),userId);
	}
	/**
	 * 根据projectId得到记录
	 */
	@Override
	public List<ProjectMaterialRetestBatch> getByProjectId(String projectId){
		Integer i=jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_retest_batch
			WHERE t_project_id=?
			""",
			Integer.class,projectId);
			if(i==null||i==0)
				return null;

		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_retest_batch 
			WHERE t_project_id=?
			""",
			new ProjectMaterialRetestBatchMapper(),projectId);
	}
	/**
	 * 根据buyMaterialBatchId得到记录
	 */
	@Override
	public List<ProjectMaterialRetestBatch> getByBuyMaterialBatchId(String buyMaterialBatchId){
		Integer i=jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_retest_batch
			WHERE t_buy_material_batch_id=?
			""",
			Integer.class,buyMaterialBatchId);
			if(i==null||i==0)
				return null;

		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_retest_batch 
			WHERE t_buy_material_batch_id=?
			""",
			new ProjectMaterialRetestBatchMapper(),buyMaterialBatchId);
	}
	/**
	 * getCount
	 */
	@Override
	public int getCount(){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_retest_batch
			""", 
			Integer.class);
		return i==null?0:i;
	}

	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	
	private List<ProjectMaterialRetestBatch> getPageQuery(int pageNo, int pageSize){
		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_retest_batch
			LIMIT ?,?
			""",
			new ProjectMaterialRetestBatchMapper(),pageNo * pageSize, pageSize);
	}
	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialRetestBatch> getPage(int pageNo, int pageSize){
		long totalCount = getCount();
		if (totalCount < 1) return new Page<>();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectMaterialRetestBatch> resultData=getPageQuery(pageNo - 1, pageSize);
		return new Page<>(0, totalCount, (int) totalCount, resultData);
	}

	/**
	 * 根据外键UserId得到总数量
	 */
	@Override
	public int getCountByUserId(String userId){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_retest_batch
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
	private List<ProjectMaterialRetestBatch> getPageQueryByUserId(String userId,int pageNo, int pageSize){
		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_retest_batch
			WHERE t_user_id=? 
			LIMIT ?,?
			""",
			new ProjectMaterialRetestBatchMapper(),userId,pageNo * pageSize, pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param userId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialRetestBatch> getPageByUserId(String userId,int pageNo, int pageSize){
		long totalCount = getCountByUserId(userId);
		if (totalCount < 1) return new Page<>();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectMaterialRetestBatch> resultData=getPageQueryByUserId(userId,pageNo - 1, pageSize);
		return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
	}

	/**
	 * 根据外键ProjectId得到总数量
	 */
	@Override
	public int getCountByProjectId(String projectId){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_retest_batch
			WHERE t_project_id=?
			""", 
			Integer.class,projectId);
		return i==null?0:i;
	}
	/**
	 * 根据外键（t_project_id）+获得指定页面数据
	 * @param projectId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	private List<ProjectMaterialRetestBatch> getPageQueryByProjectId(String projectId,int pageNo, int pageSize){
		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_retest_batch
			WHERE t_project_id=? 
			LIMIT ?,?
			""",
			new ProjectMaterialRetestBatchMapper(),projectId,pageNo * pageSize, pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param projectId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialRetestBatch> getPageByProjectId(String projectId,int pageNo, int pageSize){
		long totalCount = getCountByProjectId(projectId);
		if (totalCount < 1) return new Page<>();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectMaterialRetestBatch> resultData=getPageQueryByProjectId(projectId,pageNo - 1, pageSize);
		return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
	}

	/**
	 * 根据外键BuyMaterialBatchId得到总数量
	 */
	@Override
	public int getCountByBuyMaterialBatchId(String buyMaterialBatchId){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_retest_batch
			WHERE t_buy_material_batch_id=?
			""", 
			Integer.class,buyMaterialBatchId);
		return i==null?0:i;
	}
	/**
	 * 根据外键（t_buy_material_batch_id）+获得指定页面数据
	 * @param buyMaterialBatchId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	private List<ProjectMaterialRetestBatch> getPageQueryByBuyMaterialBatchId(String buyMaterialBatchId,int pageNo, int pageSize){
		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_retest_batch
			WHERE t_buy_material_batch_id=? 
			LIMIT ?,?
			""",
			new ProjectMaterialRetestBatchMapper(),buyMaterialBatchId,pageNo * pageSize, pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param buyMaterialBatchId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialRetestBatch> getPageByBuyMaterialBatchId(String buyMaterialBatchId,int pageNo, int pageSize){
		long totalCount = getCountByBuyMaterialBatchId(buyMaterialBatchId);
		if (totalCount < 1) return new Page<>();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectMaterialRetestBatch> resultData=getPageQueryByBuyMaterialBatchId(buyMaterialBatchId,pageNo - 1, pageSize);
		return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
	}

}