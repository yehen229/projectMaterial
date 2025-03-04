package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.acceptance.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceBatch;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.acceptance.IProjectMaterialAcceptanceBatchRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectMaterialAcceptanceBatchRepositoryImpl implements IProjectMaterialAcceptanceBatchRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectMaterialAcceptanceBatchRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_material_acceptance_batch(id,
                                        t_user_id,
                                        t_project_id,
                                        create_datetime)
                                        VALUES(?,?,?,?)
                                        """,
                                newId,
                                projectMaterialAcceptanceBatch.getUserId(),
                                projectMaterialAcceptanceBatch.getProjectId(),
                                new Date()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch) {
        if (projectMaterialAcceptanceBatch == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_batch
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """, new Date(),
                                   projectMaterialAcceptanceBatch.getId());
    }

    /**
     * update
     */
    @Override
    public int update(ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_batch
                                           SET t_user_id=?,
                                           t_project_id=?,
                                           create_datetime=? 
                                           WHERE id=?
                                           """,
                                   projectMaterialAcceptanceBatch.getUserId(),
                                   projectMaterialAcceptanceBatch.getProjectId(),
                                   projectMaterialAcceptanceBatch.getCreateDatetime(),
                                   projectMaterialAcceptanceBatch.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_batch
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """, new Date(),
                                   id);
    }

    /**
     * 根据userId删除记录
     */
    @Override
    public int deleteByUserId(String userId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_batch
                                           SET deleted_at=? 
                                           WHERE t_user_id=?
                                           """, new Date(),
                                   userId);
    }

    /**
     * 根据projectId删除记录
     */
    @Override
    public int deleteByProjectId(String projectId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_batch
                                           SET deleted_at=? 
                                           WHERE t_project_id=?
                                           """, new Date(),
                                   projectId);
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_batch
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键UserId得到总数量
     */
    @Override
    public int getCountByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_batch
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectId得到总数量
     */
    @Override
    public int getCountByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_batch
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectMaterialAcceptanceBatch getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_batch 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_material_acceptance_batch
                                                   WHERE id=?
                                                   """,
                                           new ProjectMaterialAcceptanceBatchMapper(), id);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<ProjectMaterialAcceptanceBatch> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_batch
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_batch 
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialAcceptanceBatchMapper(), userId);
    }

    /**
     * 根据projectId得到记录
     */
    @Override
    public List<ProjectMaterialAcceptanceBatch> getByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_batch
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_batch 
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialAcceptanceBatchMapper(), projectId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceBatch> getPage(int pageNo,
                                                        int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceBatch> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceBatch> getPageByUserId(String userId,
                                                                int pageNo,
                                                                int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceBatch> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceBatch> getPageByProjectId(String projectId,
                                                                   int pageNo,
                                                                   int pageSize) {
        long totalCount = getCountByProjectId(projectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceBatch> resultData = getPageQueryByProjectId(projectId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectMaterialAcceptanceBatch> getPageQuery(int pageNo,
                                                              int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_batch
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceBatchMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<ProjectMaterialAcceptanceBatch> getPageQueryByUserId(String userId,
                                                                      int pageNo,
                                                                      int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_batch
                                          WHERE t_user_id=? 
                                          AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceBatchMapper(), userId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_id）+获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    private List<ProjectMaterialAcceptanceBatch> getPageQueryByProjectId(String projectId,
                                                                         int pageNo,
                                                                         int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_batch
                                          WHERE t_project_id=? 
                                          AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceBatchMapper(), projectId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectMaterialAcceptanceBatchMapper implements RowMapper<ProjectMaterialAcceptanceBatch> {
        @Override
        public ProjectMaterialAcceptanceBatch mapRow(ResultSet rs,
                                                     int rowNum) throws SQLException {
            ProjectMaterialAcceptanceBatch projectMaterialAcceptanceBatch = new ProjectMaterialAcceptanceBatch();
            projectMaterialAcceptanceBatch.setId(rs.getString("id"));
            projectMaterialAcceptanceBatch.setUserId(rs.getString("t_user_id"));
            projectMaterialAcceptanceBatch.setProjectId(rs.getString("t_project_id"));
            projectMaterialAcceptanceBatch.setCreateDatetime(rs.getDate("create_datetime"));
            projectMaterialAcceptanceBatch.setDeletedAt(rs.getDate("deleted_at"));
            return projectMaterialAcceptanceBatch;
        }
    }

}