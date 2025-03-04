package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectOpHistory;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectOpHistoryRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectOpHistoryRepositoryImpl implements IProjectOpHistoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectOpHistoryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectOpHistory projectOpHistory) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_op_history(id,
                                        t_user_id,
                                        t_project_id,
                                        op_datetime,
                                        step_description,
                                        step_phase)
                                        VALUES(?,?,?,?,?,?)
                                        """,
                                newId,
                                projectOpHistory.getUserId(),
                                projectOpHistory.getProjectId(),
                                new Date(),
                                projectOpHistory.getStepDescription(),
                                projectOpHistory.getStepPhase()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectOpHistory projectOpHistory) {
        if (projectOpHistory == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_op_history
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   projectOpHistory.getId());
    }

    /**
     * update
     */
    @Override
    public int update(ProjectOpHistory projectOpHistory) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_op_history
                                           SET t_user_id=?,
                                           t_project_id=?,
                                           op_datetime=?,
                                           step_description=?,
                                           step_phase=?
                                           WHERE id=?
                                           """,
                                   projectOpHistory.getUserId(),
                                   projectOpHistory.getProjectId(),
                                   projectOpHistory.getOpDatetime(),
                                   projectOpHistory.getStepDescription(),
                                   projectOpHistory.getStepPhase(),
                                   projectOpHistory.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_op_history
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
                                           UPDATE t_project_op_history
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
                                           UPDATE t_project_op_history
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
                                                        FROM t_project_op_history
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
                                                        FROM t_project_op_history
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
                                                        FROM t_project_op_history
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectOpHistory getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_op_history 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_op_history
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectOpHistoryMapper(), id);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<ProjectOpHistory> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_op_history
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_op_history 
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectOpHistoryMapper(), userId);
    }

    /**
     * 根据projectId得到记录
     */
    @Override
    public List<ProjectOpHistory> getByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_op_history
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_op_history 
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectOpHistoryMapper(), projectId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectOpHistory> getPage(int pageNo,
                                          int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectOpHistory> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<ProjectOpHistory> getPageByUserId(String userId,
                                                  int pageNo,
                                                  int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectOpHistory> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
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
    public Page<ProjectOpHistory> getPageByProjectId(String projectId,
                                                     int pageNo,
                                                     int pageSize) {
        long totalCount = getCountByProjectId(projectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectOpHistory> resultData = getPageQueryByProjectId(projectId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectOpHistory> getPageQuery(int pageNo,
                                                int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_op_history
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectOpHistoryMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<ProjectOpHistory> getPageQueryByUserId(String userId,
                                                        int pageNo,
                                                        int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_op_history
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectOpHistoryMapper(), userId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_id）+获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    private List<ProjectOpHistory> getPageQueryByProjectId(String projectId,
                                                           int pageNo,
                                                           int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_op_history
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          ORDER BY  op_datetime                                                                                      
                                          LIMIT ?,?
                                          """,
                                  new ProjectOpHistoryMapper(), projectId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectOpHistoryMapper implements RowMapper<ProjectOpHistory> {
        @Override
        public ProjectOpHistory mapRow(ResultSet rs,
                                       int rowNum) throws SQLException {
            ProjectOpHistory projectOpHistory = new ProjectOpHistory();
            projectOpHistory.setId(rs.getString("id"));
            projectOpHistory.setUserId(rs.getString("t_user_id"));
            projectOpHistory.setProjectId(rs.getString("t_project_id"));
            projectOpHistory.setOpDatetime(rs.getTimestamp("op_datetime"));
            projectOpHistory.setStepDescription(rs.getString("step_description"));
            projectOpHistory.setStepPhase(rs.getString("step_phase"));
            projectOpHistory.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectOpHistory;
        }
    }

}