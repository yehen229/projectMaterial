package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewMode;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialAcceptanceReviewModeRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectMaterialAcceptanceReviewModeRepositoryImpl implements IProjectMaterialAcceptanceReviewModeRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectMaterialAcceptanceReviewModeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_material_acceptance_review_mode(id,
                                        t_user_id,
                                        t_project_material_acceptance_batch_id,
                                        mode,
                                        create_datetime)
                                        VALUES(?,?,?,?,?)
                                        """,
                                newId,
                                projectMaterialAcceptanceReviewMode.getUserId(),
                                projectMaterialAcceptanceReviewMode.getProjectMaterialAcceptanceBatchId(),
                                projectMaterialAcceptanceReviewMode.getMode(),
                                new Date()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode) {
        if (projectMaterialAcceptanceReviewMode == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_review_mode
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   projectMaterialAcceptanceReviewMode.getId());
    }

    /**
     * update
     */
    @Override
    public int update(ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_review_mode
                                           SET t_user_id=?,
                                           t_project_material_acceptance_batch_id=?,
                                           mode=?,
                                           create_datetime=?,
                                           deleted_at=? 
                                           WHERE id=?
                                           """,
                                   projectMaterialAcceptanceReviewMode.getUserId(),
                                   projectMaterialAcceptanceReviewMode.getProjectMaterialAcceptanceBatchId(),
                                   projectMaterialAcceptanceReviewMode.getMode(),
                                   projectMaterialAcceptanceReviewMode.getCreateDatetime(),
                                   projectMaterialAcceptanceReviewMode.getDeletedAt(),
                                   projectMaterialAcceptanceReviewMode.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_review_mode
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
                                           UPDATE t_project_material_acceptance_review_mode
                                           SET deleted_at=? 
                                           WHERE t_user_id=?
                                           """, new Date(),
                                   userId);
    }

    /**
     * 根据projectMaterialAcceptanceBatchId删除记录
     */
    @Override
    public int deleteByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_review_mode
                                           SET deleted_at=? 
                                           WHERE t_project_material_acceptance_batch_id=?
                                           """, new Date(),
                                   projectMaterialAcceptanceBatchId);
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review_mode
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
                                                        FROM t_project_material_acceptance_review_mode
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectMaterialAcceptanceBatchId得到总数量
     */
    @Override
    public int getCountByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review_mode
                                                        WHERE t_project_material_acceptance_batch_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialAcceptanceBatchId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectMaterialAcceptanceReviewMode getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review_mode 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_material_acceptance_review_mode
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectMaterialAcceptanceReviewModeMapper(), id);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<ProjectMaterialAcceptanceReviewMode> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review_mode
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_review_mode 
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialAcceptanceReviewModeMapper(), userId);
    }

    /**
     * 根据projectMaterialAcceptanceBatchId得到记录
     */
    @Override
    public List<ProjectMaterialAcceptanceReviewMode> getByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review_mode
                                                        WHERE t_project_material_acceptance_batch_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialAcceptanceBatchId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_review_mode 
                                          WHERE t_project_material_acceptance_batch_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialAcceptanceReviewModeMapper(), projectMaterialAcceptanceBatchId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewMode> getPage(int pageNo,
                                                             int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceReviewMode> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<ProjectMaterialAcceptanceReviewMode> getPageByUserId(String userId,
                                                                     int pageNo,
                                                                     int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceReviewMode> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialAcceptanceBatchId
     * @param pageNo                           页号，从1开始
     * @param pageSize                         每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewMode> getPageByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId,
                                                                                               int pageNo,
                                                                                               int pageSize) {
        long totalCount = getCountByProjectMaterialAcceptanceBatchId(projectMaterialAcceptanceBatchId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceReviewMode> resultData = getPageQueryByProjectMaterialAcceptanceBatchId(
                projectMaterialAcceptanceBatchId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectMaterialAcceptanceReviewMode> getPageQuery(int pageNo,
                                                                   int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_review_mode
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceReviewModeMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<ProjectMaterialAcceptanceReviewMode> getPageQueryByUserId(String userId,
                                                                           int pageNo,
                                                                           int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_review_mode
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceReviewModeMapper(), userId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_material_acceptance_batch_id）+获得指定页面数据
     *
     * @param projectMaterialAcceptanceBatchId
     * @param pageNo                           页号，从1开始
     * @param pageSize                         每页的记录数
     */
    private List<ProjectMaterialAcceptanceReviewMode> getPageQueryByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId,
                                                                                                     int pageNo,
                                                                                                     int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_review_mode
                                          WHERE t_project_material_acceptance_batch_id=?   AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceReviewModeMapper(), projectMaterialAcceptanceBatchId,
                                  pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectMaterialAcceptanceReviewModeMapper implements RowMapper<ProjectMaterialAcceptanceReviewMode> {
        @Override
        public ProjectMaterialAcceptanceReviewMode mapRow(ResultSet rs,
                                                          int rowNum) throws SQLException {
            ProjectMaterialAcceptanceReviewMode projectMaterialAcceptanceReviewMode = new ProjectMaterialAcceptanceReviewMode();
            projectMaterialAcceptanceReviewMode.setId(rs.getString("id"));
            projectMaterialAcceptanceReviewMode.setUserId(rs.getString("t_user_id"));
            projectMaterialAcceptanceReviewMode.setProjectMaterialAcceptanceBatchId(
                    rs.getString("t_project_material_acceptance_batch_id"));
            projectMaterialAcceptanceReviewMode.setMode(rs.getInt("mode"));
            projectMaterialAcceptanceReviewMode.setCreateDatetime(rs.getDate("create_datetime"));
            projectMaterialAcceptanceReviewMode.setDeletedAt(rs.getDate("deleted_at"));
            return projectMaterialAcceptanceReviewMode;
        }
    }

}