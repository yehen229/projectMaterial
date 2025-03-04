package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;



import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialHistory;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialHistoryRepository;
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
public class ProjectMaterialHistoryRepositoryImpl implements IProjectMaterialHistoryRepository {
	private final JdbcTemplate jdbcTemplate;

	public ProjectMaterialHistoryRepositoryImpl(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	/**
	 * RowMapper
	 */
	private static final class ProjectMaterialHistoryMapper implements RowMapper<ProjectMaterialHistory>{
		@Override
		public ProjectMaterialHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectMaterialHistory projectMaterialHistory = new ProjectMaterialHistory();
			projectMaterialHistory.setId(rs.getString("id"));
			projectMaterialHistory.setMaterialId(rs.getString("t_material_id"));
			projectMaterialHistory.setCompanyId(rs.getString("t_company_id"));
			projectMaterialHistory.setProjectId(rs.getString("t_project_id"));
			projectMaterialHistory.setReviewed(rs.getInt("reviewed"));
			projectMaterialHistory.setProjectMaterialId(rs.getString("t_project_material_id"));
			projectMaterialHistory.setDeletedAt(rs.getDate("deleted_at"));
			return projectMaterialHistory;
		}
	}

	/**
	 * insert
	 */
	@Override
	public String add(ProjectMaterialHistory projectMaterialHistory){

		String newId=GUID.getGUID();
		if( jdbcTemplate.update("""
			INSERT INTO t_project_material_history(id,
			t_material_id,
			t_company_id,
			t_project_id,
			reviewed,
			t_project_material_id,
			deleted_at)
			VALUES(?,?,?,?,?,?,?)
			""",
			newId,
			projectMaterialHistory.getMaterialId(),
			projectMaterialHistory.getCompanyId(),
			projectMaterialHistory.getProjectId(),
			projectMaterialHistory.getReviewed(),
			projectMaterialHistory.getProjectMaterialId(),
			projectMaterialHistory.getDeletedAt())>0)
			return newId;
		return null;
	}

	/**
	 * delete
	 */
	@Override
	public int delete(ProjectMaterialHistory projectMaterialHistory){
		if(projectMaterialHistory==null)return 0;

		return jdbcTemplate.update("""
                                           UPDATE t_project_material_history
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
								   new Date(),
								   projectMaterialHistory.getId());


	}

	/**
	 * 根据id删除记录
	 */
	@Override
	public int deleteById(String id){


		return jdbcTemplate.update("""
                                           UPDATE t_project_material_history
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
								   new Date(),
								   id);

}
	/**
	 * 根据materialId删除记录
	 */
	@Override
	public int deleteByMaterialId(String materialId){


		return jdbcTemplate.update("""
                                           UPDATE t_project_material_history
                                           SET deleted_at=? 
                                           WHERE t_material_id=?
                                           """,
								   new Date(),
								   materialId);

}
	/**
	 * 根据companyId删除记录
	 */
	@Override
	public int deleteByCompanyId(String companyId){


		return jdbcTemplate.update("""
                                           UPDATE t_project_material_history
                                           SET deleted_at=? 
                                           WHERE t_company_id=?
                                           """,
								   new Date(),
								   companyId);

}
	/**
	 * 根据projectId删除记录
	 */
	@Override
	public int deleteByProjectId(String projectId){


		return jdbcTemplate.update("""
                                           UPDATE t_project_material_history
                                           SET deleted_at=? 
                                           WHERE t_project_id=?
                                           """,
								   new Date(),
								   projectId);
}
	/**
	 * 根据projectMaterialId删除记录
	 */
	@Override
	public int deleteByProjectMaterialId(String projectMaterialId){


		return jdbcTemplate.update("""
                                           UPDATE t_project_material_history
                                           SET deleted_at=? 
                                           WHERE t_project_material_id=?
                                           """,
								   new Date(),
								   projectMaterialId);

}
	/**
	 * update
	 */
	@Override
	public int update(ProjectMaterialHistory projectMaterialHistory){
		return jdbcTemplate.update("""
			UPDATE t_project_material_history
			SET t_material_id=?,
			t_company_id=?,
			t_project_id=?,
			reviewed=?,
			t_project_material_id=?,
			deleted_at=? 
			WHERE id=?
			""",
			projectMaterialHistory.getMaterialId(),
			projectMaterialHistory.getCompanyId(),
			projectMaterialHistory.getProjectId(),
			projectMaterialHistory.getReviewed(),
			projectMaterialHistory.getProjectMaterialId(),
			projectMaterialHistory.getDeletedAt(),
			projectMaterialHistory.getId());
	}

	/**
	 * 根据id得到记录
	 */
	@Override
	public ProjectMaterialHistory getById(String id){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_history 
			WHERE id=?
			""",Integer.class,id);
			if(i==null||i!=1)
				return null;

		return jdbcTemplate.queryForObject("""
			SELECT * 
			FROM t_project_material_history
			WHERE id=?
			""",
			new ProjectMaterialHistoryMapper(),id);
	}
	/**
	 * 根据materialId得到记录
	 */
	@Override
	public List<ProjectMaterialHistory> getByMaterialId(String materialId){
		Integer i=jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_history
			WHERE t_material_id=?
			""",
			Integer.class,materialId);
			if(i==null||i==0)
				return null;

		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_history 
			WHERE t_material_id=?
			""",
			new ProjectMaterialHistoryMapper(),materialId);
	}
	/**
	 * 根据companyId得到记录
	 */
	@Override
	public List<ProjectMaterialHistory> getByCompanyId(String companyId){
		Integer i=jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_history
			WHERE t_company_id=?
			""",
			Integer.class,companyId);
			if(i==null||i==0)
				return null;

		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_history 
			WHERE t_company_id=?
			""",
			new ProjectMaterialHistoryMapper(),companyId);
	}
	/**
	 * 根据projectId得到记录
	 */
	@Override
	public List<ProjectMaterialHistory> getByProjectId(String projectId){
		Integer i=jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_history
			WHERE t_project_id=?
			""",
			Integer.class,projectId);
			if(i==null||i==0)
				return null;

		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_history 
			WHERE t_project_id=?
			""",
			new ProjectMaterialHistoryMapper(),projectId);
	}
	/**
	 * 根据projectMaterialId得到记录
	 */
	@Override
	public List<ProjectMaterialHistory> getByProjectMaterialId(String projectMaterialId){
		Integer i=jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_history
			WHERE t_project_material_id=?
			""",
			Integer.class,projectMaterialId);
			if(i==null||i==0)
				return null;

		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_history 
			WHERE t_project_material_id=?
			""",
			new ProjectMaterialHistoryMapper(),projectMaterialId);
	}
	/**
	 * getCount
	 */
	@Override
	public int getCount(){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_history
			""", 
			Integer.class);
		return i==null?0:i;
	}

	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	
	private List<ProjectMaterialHistory> getPageQuery(int pageNo, int pageSize){
		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_history
			LIMIT ?,?
			""",
			new ProjectMaterialHistoryMapper(),pageNo * pageSize, pageSize);
	}
	/**
	 * 获得指定页面数据
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialHistory> getPage(int pageNo, int pageSize){
		long totalCount = getCount();
		if (totalCount < 1) return new Page<>();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectMaterialHistory> resultData=getPageQuery(pageNo - 1, pageSize);
		return new Page<>(0, totalCount, (int) totalCount, resultData);
	}

	/**
	 * 根据外键MaterialId得到总数量
	 */
	@Override
	public int getCountByMaterialId(String materialId){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_history
			WHERE t_material_id=?
			""", 
			Integer.class,materialId);
		return i==null?0:i;
	}
	/**
	 * 根据外键（t_material_id）+获得指定页面数据
	 * @param materialId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	private List<ProjectMaterialHistory> getPageQueryByMaterialId(String materialId,int pageNo, int pageSize){
		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_history
			WHERE t_material_id=? 
			LIMIT ?,?
			""",
			new ProjectMaterialHistoryMapper(),materialId,pageNo * pageSize, pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param materialId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialHistory> getPageByMaterialId(String materialId,int pageNo, int pageSize){
		long totalCount = getCountByMaterialId(materialId);
		if (totalCount < 1) return new Page<>();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectMaterialHistory> resultData=getPageQueryByMaterialId(materialId,pageNo - 1, pageSize);
		return new Page<>(0, totalCount, (int) totalCount, resultData);
	}

	/**
	 * 根据外键CompanyId得到总数量
	 */
	@Override
	public int getCountByCompanyId(String companyId){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_history
			WHERE t_company_id=?
			""", 
			Integer.class,companyId);
		return i==null?0:i;
	}
	/**
	 * 根据外键（t_company_id）+获得指定页面数据
	 * @param companyId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	private List<ProjectMaterialHistory> getPageQueryByCompanyId(String companyId,int pageNo, int pageSize){
		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_history
			WHERE t_company_id=? 
			LIMIT ?,?
			""",
			new ProjectMaterialHistoryMapper(),companyId,pageNo * pageSize, pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param companyId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialHistory> getPageByCompanyId(String companyId,int pageNo, int pageSize){
		long totalCount = getCountByCompanyId(companyId);
		if (totalCount < 1) return new Page<>();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectMaterialHistory> resultData=getPageQueryByCompanyId(companyId,pageNo - 1, pageSize);
		return new Page<>(0, totalCount, (int) totalCount, resultData);
	}

	/**
	 * 根据外键ProjectId得到总数量
	 */
	@Override
	public int getCountByProjectId(String projectId){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_history
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
	private List<ProjectMaterialHistory> getPageQueryByProjectId(String projectId,int pageNo, int pageSize){
		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_history
			WHERE t_project_id=? 
			LIMIT ?,?
			""",
			new ProjectMaterialHistoryMapper(),projectId,pageNo * pageSize, pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param projectId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialHistory> getPageByProjectId(String projectId,int pageNo, int pageSize){
		long totalCount = getCountByProjectId(projectId);
		if (totalCount < 1) return new Page<>();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectMaterialHistory> resultData=getPageQueryByProjectId(projectId,pageNo - 1, pageSize);
		return new Page<>(0, totalCount, (int) totalCount, resultData);
	}

	/**
	 * 根据外键ProjectMaterialId得到总数量
	 */
	@Override
	public int getCountByProjectMaterialId(String projectMaterialId){
		Integer i= jdbcTemplate.queryForObject("""
			SELECT count(*) 
			FROM t_project_material_history
			WHERE t_project_material_id=?
			""", 
			Integer.class,projectMaterialId);
		return i==null?0:i;
	}
	/**
	 * 根据外键（t_project_material_id）+获得指定页面数据
	 * @param projectMaterialId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	private List<ProjectMaterialHistory> getPageQueryByProjectMaterialId(String projectMaterialId,int pageNo, int pageSize){
		return jdbcTemplate.query("""
			SELECT * 
			FROM t_project_material_history
			WHERE t_project_material_id=? 
			LIMIT ?,?
			""",
			new ProjectMaterialHistoryMapper(),projectMaterialId,pageNo * pageSize, pageSize);
	}

	/**
	 * 获得指定页面数据
	 * @param projectMaterialId
	 * @param pageNo   页号，从1开始
	 * @param pageSize 每页的记录数 
	 */
	@Override
	public Page<ProjectMaterialHistory> getPageByProjectMaterialId(String projectMaterialId,int pageNo, int pageSize){
		long totalCount = getCountByProjectMaterialId(projectMaterialId);
		if (totalCount < 1) return new Page<>();
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List<ProjectMaterialHistory> resultData=getPageQueryByProjectMaterialId(projectMaterialId,pageNo - 1, pageSize);
		return new Page<>(0, totalCount, (int) totalCount, resultData);
	}

}