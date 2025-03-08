package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;



import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandHistory;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialBrandHistoryRepository;
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
public class ProjectMaterialBrandHistoryRepositoryImpl implements IProjectMaterialBrandHistoryRepository {
	private final JdbcTemplate jdbcTemplate;

	public ProjectMaterialBrandHistoryRepositoryImpl(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	/**
	 * RowMapper
	 */
	private static final class ProjectMaterialBrandHistoryMapper implements RowMapper<ProjectMaterialBrandHistory>{
		@Override
		public ProjectMaterialBrandHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectMaterialBrandHistory projectMaterialBrandHistory = new ProjectMaterialBrandHistory();
			projectMaterialBrandHistory.setId(rs.getString("id"));
			projectMaterialBrandHistory.setProjectMaterialHistoryId(rs.getString("t_project_material_history_id"));
			projectMaterialBrandHistory.setProjectMaterialBrandPrivateId(rs.getString("t_project_material_brand_private_id"));
			projectMaterialBrandHistory.setProjectMaterialBrandPublicId(rs.getString("t_project_material_brand_public_id"));
			projectMaterialBrandHistory.setDeletedAt(rs.getTimestamp("deleted_at"));
			return projectMaterialBrandHistory;
		}
	}

	/**
	 * insert
	 */
	@Override
	public String add(ProjectMaterialBrandHistory projectMaterialBrandHistory){

		String newId=GUID.getGUID();
		if( jdbcTemplate.update("""
			INSERT INTO t_project_material_brand_history(id,
			t_project_material_history_id,
			t_project_material_brand_private_id,
			t_project_material_brand_public_id,
			deleted_at)
			VALUES(?,?,?,?,?)
			""",
			newId,
			projectMaterialBrandHistory.getProjectMaterialHistoryId(),
			projectMaterialBrandHistory.getProjectMaterialBrandPrivateId(),
			projectMaterialBrandHistory.getProjectMaterialBrandPublicId(),
			projectMaterialBrandHistory.getDeletedAt())>0)
			return newId;
		return null;
	}

	/**
	 * delete
	 */
	@Override
	public int delete(ProjectMaterialBrandHistory projectMaterialBrandHistory){
		if(projectMaterialBrandHistory==null)return 0;


		return jdbcTemplate.update("""
                                           UPDATE t_project_material_brand_history
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
								   new Date(),
								   projectMaterialBrandHistory.getId());

	}

	/**
	 * 根据id删除记录
	 */
	@Override
	public int deleteById(String id){


		return jdbcTemplate.update("""
                                           UPDATE t_project_material_brand_history
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
								   new Date(),
								   id);

}
	/**
	 * 根据projectMaterialHistoryId删除记录
	 */
	@Override
	public int deleteByProjectMaterialHistoryId(String projectMaterialHistoryId){


		return jdbcTemplate.update("""
                                           UPDATE t_project_material_brand_history
                                           SET deleted_at=? 
                                           WHERE t_project_material_history_id=?
                                           """,
								   new Date(),
								   projectMaterialHistoryId);

}
	/**
	 * 根据projectMaterialBrandPrivateId删除记录
	 */
	@Override
	public int deleteByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId){


		return jdbcTemplate.update("""
                                           UPDATE t_project_material_brand_history
                                           SET deleted_at=? 
                                           WHERE t_project_material_brand_private_id=?
                                           """,
								   new Date(),
								   projectMaterialBrandPrivateId);

}
	/**
	 * 根据projectMaterialBrandPublicId删除记录
	 */
	@Override
	public int deleteByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId){


		return jdbcTemplate.update("""
                                           UPDATE t_project_material_brand_history
                                           SET deleted_at=? 
                                           WHERE t_project_material_brand_public_id=?
                                           """,
								   new Date(),
								   projectMaterialBrandPublicId);
}
	/**
	 * update
	 */
	@Override
	public int update(ProjectMaterialBrandHistory projectMaterialBrandHistory){
		return jdbcTemplate.update("""
			UPDATE t_project_material_brand_history
			SET t_project_material_history_id=?,
			t_project_material_brand_private_id=?,
			t_project_material_brand_public_id=?,
			deleted_at=? 
			WHERE id=?
			""",
			projectMaterialBrandHistory.getProjectMaterialHistoryId(),
			projectMaterialBrandHistory.getProjectMaterialBrandPrivateId(),
			projectMaterialBrandHistory.getProjectMaterialBrandPublicId(),
			projectMaterialBrandHistory.getDeletedAt(),
			projectMaterialBrandHistory.getId());
	}

	/**
	 * 根据id得到记录
	 */
	@Override
	public ProjectMaterialBrandHistory getById(String id){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_brand_history 
			WHERE id=?
			""",Integer.class,id);
			if(i==null||i!=1)
				return null;

		return jdbcTemplate.queryForObject("""
			SELECT * 
			FROM t_project_material_brand_history
			WHERE id=?
			""",
			new ProjectMaterialBrandHistoryMapper(),id);
	}
	/**
	 * 根据projectMaterialHistoryId得到记录
	 */
	@Override
	public List<ProjectMaterialBrandHistory> getByProjectMaterialHistoryId(String projectMaterialHistoryId){
		Integer i=jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_brand_history
			WHERE t_project_material_history_id=?
			""",
			Integer.class,projectMaterialHistoryId);
			if(i==null||i==0)
				return null;

		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_brand_history 
			WHERE t_project_material_history_id=?
			""",
			new ProjectMaterialBrandHistoryMapper(),projectMaterialHistoryId);
	}
	/**
	 * 根据projectMaterialBrandPrivateId得到记录
	 */
	@Override
	public List<ProjectMaterialBrandHistory> getByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId){
		Integer i=jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_brand_history
			WHERE t_project_material_brand_private_id=?
			""",
			Integer.class,projectMaterialBrandPrivateId);
			if(i==null||i==0)
				return null;

		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_brand_history 
			WHERE t_project_material_brand_private_id=?
			""",
			new ProjectMaterialBrandHistoryMapper(),projectMaterialBrandPrivateId);
	}
	/**
	 * 根据projectMaterialBrandPublicId得到记录
	 */
	@Override
	public List<ProjectMaterialBrandHistory> getByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId){
		Integer i=jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_brand_history
			WHERE t_project_material_brand_public_id=?
			""",
			Integer.class,projectMaterialBrandPublicId);
			if(i==null||i==0)
				return null;

		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_brand_history 
			WHERE t_project_material_brand_public_id=?
			""",
			new ProjectMaterialBrandHistoryMapper(),projectMaterialBrandPublicId);
	}
	/**
	 * getCount
	 */
	@Override
	public int getCount(){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_brand_history
			""", 
			Integer.class);
		return i==null?0:i;
	}

	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	
	private List<ProjectMaterialBrandHistory> getPageQuery(int pageNo, int pageSize){
		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_brand_history
			LIMIT ?,?
			""",
			new ProjectMaterialBrandHistoryMapper(),pageNo * pageSize, pageSize);
	}
	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialBrandHistory> getPage(int pageNo, int pageSize){
		long totalCount = getCount();
		if (totalCount < 1) return new Page<>();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectMaterialBrandHistory> resultData=getPageQuery(pageNo - 1, pageSize);
		return new Page<>(0, totalCount, (int) totalCount, resultData);
	}

	/**
	 * 根据外键ProjectMaterialHistoryId得到总数量
	 */
	@Override
	public int getCountByProjectMaterialHistoryId(String projectMaterialHistoryId){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_brand_history
			WHERE t_project_material_history_id=?
			""", 
			Integer.class,projectMaterialHistoryId);
		return i==null?0:i;
	}
	/**
	 * 根据外键（t_project_material_history_id）+获得指定页面数据
	 * @param projectMaterialHistoryId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	private List<ProjectMaterialBrandHistory> getPageQueryByProjectMaterialHistoryId(String projectMaterialHistoryId,int pageNo, int pageSize){
		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_brand_history
			WHERE t_project_material_history_id=? 
			LIMIT ?,?
			""",
			new ProjectMaterialBrandHistoryMapper(),projectMaterialHistoryId,pageNo * pageSize, pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param projectMaterialHistoryId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialBrandHistory> getPageByProjectMaterialHistoryId(String projectMaterialHistoryId,int pageNo, int pageSize){
		long totalCount = getCountByProjectMaterialHistoryId(projectMaterialHistoryId);
		if (totalCount < 1) return new Page<>();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectMaterialBrandHistory> resultData=getPageQueryByProjectMaterialHistoryId(projectMaterialHistoryId,pageNo - 1, pageSize);
		return new Page<>(0, totalCount, (int) totalCount, resultData);
	}

	/**
	 * 根据外键ProjectMaterialBrandPrivateId得到总数量
	 */
	@Override
	public int getCountByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_brand_history
			WHERE t_project_material_brand_private_id=?
			""", 
			Integer.class,projectMaterialBrandPrivateId);
		return i==null?0:i;
	}
	/**
	 * 根据外键（t_project_material_brand_private_id）+获得指定页面数据
	 * @param projectMaterialBrandPrivateId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	private List<ProjectMaterialBrandHistory> getPageQueryByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,int pageNo, int pageSize){
		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_brand_history
			WHERE t_project_material_brand_private_id=? 
			LIMIT ?,?
			""",
			new ProjectMaterialBrandHistoryMapper(),projectMaterialBrandPrivateId,pageNo * pageSize, pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param projectMaterialBrandPrivateId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialBrandHistory> getPageByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,int pageNo, int pageSize){
		long totalCount = getCountByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
		if (totalCount < 1) return new Page<>();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectMaterialBrandHistory> resultData=getPageQueryByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId,pageNo - 1, pageSize);
		return new Page<>(0, totalCount, (int) totalCount, resultData);
	}

	/**
	 * 根据外键ProjectMaterialBrandPublicId得到总数量
	 */
	@Override
	public int getCountByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_brand_history
			WHERE t_project_material_brand_public_id=?
			""", 
			Integer.class,projectMaterialBrandPublicId);
		return i==null?0:i;
	}
	/**
	 * 根据外键（t_project_material_brand_public_id）+获得指定页面数据
	 * @param projectMaterialBrandPublicId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	private List<ProjectMaterialBrandHistory> getPageQueryByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,int pageNo, int pageSize){
		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_brand_history
			WHERE t_project_material_brand_public_id=? 
			LIMIT ?,?
			""",
			new ProjectMaterialBrandHistoryMapper(),projectMaterialBrandPublicId,pageNo * pageSize, pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param projectMaterialBrandPublicId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialBrandHistory> getPageByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,int pageNo, int pageSize){
		long totalCount = getCountByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
		if (totalCount < 1) return new Page<>();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectMaterialBrandHistory> resultData=getPageQueryByProjectMaterialBrandPublicId(projectMaterialBrandPublicId,pageNo - 1, pageSize);
		return new Page<>(0, totalCount, (int) totalCount, resultData);
	}

}