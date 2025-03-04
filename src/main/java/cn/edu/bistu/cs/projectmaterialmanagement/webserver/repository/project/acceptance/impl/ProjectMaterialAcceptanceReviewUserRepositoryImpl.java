package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.acceptance.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptanceReviewUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.acceptance.IProjectMaterialAcceptanceReviewUserRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectMaterialAcceptanceReviewUserRepositoryImpl implements IProjectMaterialAcceptanceReviewUserRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectMaterialAcceptanceReviewUserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_material_acceptance_review_user(id,
                                        t_user_id,
                                        t_project_material_acceptance_review_id,
                                        review_result,
                                        review_content,
                                        review_datetime)
                                        VALUES(?,?,?,?,?,?)
                                        """,
                                newId,
                                projectMaterialAcceptanceReviewUser.getUserId(),
                                projectMaterialAcceptanceReviewUser.getProjectMaterialAcceptanceReviewId(),
                                projectMaterialAcceptanceReviewUser.getReviewResult(),
                                projectMaterialAcceptanceReviewUser.getReviewContent(),
                                new Date()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser) {
        if (projectMaterialAcceptanceReviewUser == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_review_user
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """, new Date(),
                                   projectMaterialAcceptanceReviewUser.getId());
    }

    /**
     * update
     */
    @Override
    public int update(ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_review_user
                                           SET t_user_id=?,
                                           t_project_material_acceptance_review_id=?,
                                           review_result=?,
                                           review_content=?,
                                           review_datetime=?
                                           WHERE id=?
                                           """,
                                   projectMaterialAcceptanceReviewUser.getUserId(),
                                   projectMaterialAcceptanceReviewUser.getProjectMaterialAcceptanceReviewId(),
                                   projectMaterialAcceptanceReviewUser.getReviewResult(),
                                   projectMaterialAcceptanceReviewUser.getReviewContent(),
                                   projectMaterialAcceptanceReviewUser.getReviewDatetime(),
                                   projectMaterialAcceptanceReviewUser.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_review_user
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
                                           UPDATE t_project_material_acceptance_review_user
                                           SET deleted_at=? 
                                           WHERE t_user_id=?
                                           """, new Date(),
                                   userId);
    }

    /**
     * 根据projectMaterialAcceptanceReviewId删除记录
     */
    @Override
    public int deleteByProjectMaterialAcceptanceReviewId(String projectMaterialAcceptanceReviewId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance_review_user
                                           SET deleted_at=? 
                                           WHERE t_project_material_acceptance_review_id=?
                                           """, new Date(),
                                   projectMaterialAcceptanceReviewId);
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review_user
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
                                                        FROM t_project_material_acceptance_review_user
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        return i == null ? 0 : i;
    }
    @Override
    public int getCountByProjectId(String projectId){
        Integer totalReviews = jdbcTemplate.queryForObject("""
                                                     SELECT count(*) AS total_reviews
                                                     FROM t_project_material_acceptance_review
                                                     JOIN t_project_material_acceptance_review_mode
                                                          ON t_project_material_acceptance_review.t_project_material_acceptance_mode_id = t_project_material_acceptance_review_mode.id
                                                     JOIN t_project_material_acceptance_batch
                                                          ON t_project_material_acceptance_review_mode.t_project_material_acceptance_batch_id = t_project_material_acceptance_batch.id
                                                     WHERE t_project_material_acceptance_batch.t_project_id = ?
                                                     AND t_project_material_acceptance_review.review_status=2
                                                     AND t_project_material_acceptance_review.deleted_at IS NULL
                                                     AND t_project_material_acceptance_review_mode.deleted_at IS NULL
                                                     AND t_project_material_acceptance_batch.deleted_at IS NULL
                                                     """,
                Integer.class, projectId);

        return totalReviews == null ? 0 : totalReviews;
    }
    @Override
    public int getCountByProjectIdAndResult(String projectId, int nReviewResult){
        Integer i = jdbcTemplate.queryForObject("""
                                                     SELECT count(*)
                                                     FROM t_project_material_acceptance_review
                                                     JOIN t_project_material_acceptance_review_mode
                                                          ON t_project_material_acceptance_review.t_project_material_acceptance_mode_id = t_project_material_acceptance_review_mode.id
                                                     JOIN t_project_material_acceptance_batch
                                                          ON t_project_material_acceptance_review_mode.t_project_material_acceptance_batch_id = t_project_material_acceptance_batch.id
                                                     WHERE t_project_material_acceptance_batch.t_project_id = ?
                                                     AND t_project_material_acceptance_review.review_result = ?
                                                     AND t_project_material_acceptance_review.deleted_at IS NULL
                                                     AND t_project_material_acceptance_review_mode.deleted_at IS NULL
                                                     AND t_project_material_acceptance_batch.deleted_at IS NULL
                                                     """,
                Integer.class, projectId, nReviewResult);

        return i == null ? 0 : i;

    }

    /**
     * 根据外键ProjectMaterialAcceptanceReviewId得到总数量
     */
    @Override
    public int getCountByProjectMaterialAcceptanceReviewId(String projectMaterialAcceptanceReviewId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review_user
                                                        WHERE t_project_material_acceptance_review_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialAcceptanceReviewId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectMaterialAcceptanceReviewUser getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review_user 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_material_acceptance_review_user
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectMaterialAcceptanceReviewUserMapper(), id);
    }

    @Override
    public ProjectMaterialAcceptanceReviewUser getByUserIdAndModeId(String userId,
                                                                    String projectMaterialAcceptanceBatchId,
                                                                    String projectMaterialAcceptanceReviewModeId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review_user
                                                        LEFT JOIN t_project_material_acceptance_review ON t_project_material_acceptance_review.id=t_project_material_acceptance_review_user.t_project_material_acceptance_review_id   
                                                        LEFT JOIN t_project_material_acceptance_review_mode ON t_project_material_acceptance_review_mode.id=t_project_material_acceptance_review.t_project_material_acceptance_mode_id   
                                                        WHERE t_project_material_acceptance_review_user.t_user_id=?
                                                        AND t_project_material_acceptance_review.t_project_material_acceptance_mode_id=?
                                                        AND t_project_material_acceptance_review_mode.t_project_material_acceptance_batch_id=?
                                                        AND t_project_material_acceptance_review_user.deleted_at IS NULL
                                                        AND t_project_material_acceptance_review.deleted_at IS NULL
                                                        AND t_project_material_acceptance_review_mode.deleted_at IS NULL
                                                        """, Integer.class, userId,
                                                projectMaterialAcceptanceReviewModeId,
                                                projectMaterialAcceptanceBatchId);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_material_acceptance_review_user
                                                   LEFT JOIN t_project_material_acceptance_review ON t_project_material_acceptance_review.id=t_project_material_acceptance_review_user.t_project_material_acceptance_review_id  \s
                                                   LEFT JOIN t_project_material_acceptance_review_mode ON t_project_material_acceptance_review_mode.id=t_project_material_acceptance_review.t_project_material_acceptance_mode_id  \s
                                                   WHERE t_project_material_acceptance_review_user.t_user_id=?
                                                   AND t_project_material_acceptance_review.t_project_material_acceptance_mode_id=?
                                                   AND t_project_material_acceptance_review_mode.t_project_material_acceptance_batch_id=?
                                                   AND t_project_material_acceptance_review_user.deleted_at IS NULL
                                                   AND t_project_material_acceptance_review.deleted_at IS NULL
                                                   AND t_project_material_acceptance_review_mode.deleted_at IS NULL                                                   
                                                   """,
                                           new ProjectMaterialAcceptanceReviewUserMapper(), userId,
                                           projectMaterialAcceptanceReviewModeId,
                                           projectMaterialAcceptanceBatchId);
    }

    @Override
    public List<ProjectMaterialAcceptanceReviewUser> getByMaterialAcceptanceModeIdAndNotReviewed(String projectMaterialAcceptanceReviewModeId, int reviewResult) {
         Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review_user
                                                        LEFT JOIN t_project_material_acceptance_review ON t_project_material_acceptance_review.id=t_project_material_acceptance_review_user.t_project_material_acceptance_review_id
                                                        LEFT JOIN t_project_material_acceptance_review_mode ON t_project_material_acceptance_review.t_project_material_acceptance_mode_id=t_project_material_acceptance_review_mode.id
                                                        WHERE t_project_material_acceptance_review_mode.id=? 
                                                        AND t_project_material_acceptance_review_user.review_result=? 
                                                        AND t_project_material_acceptance_review_user.deleted_at IS NULL
                                                        AND t_project_material_acceptance_review.deleted_at IS NULL
                                                        AND t_project_material_acceptance_review_mode.deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialAcceptanceReviewModeId, reviewResult);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT t_project_material_acceptance_review_user.* 
                                          FROM t_project_material_acceptance_review_user
                                          LEFT JOIN t_project_material_acceptance_review ON t_project_material_acceptance_review.id=t_project_material_acceptance_review_user.t_project_material_acceptance_review_id
                                          LEFT JOIN t_project_material_acceptance_review_mode ON t_project_material_acceptance_review.t_project_material_acceptance_mode_id=t_project_material_acceptance_review_mode.id
                                          WHERE t_project_material_acceptance_review_mode.id=? 
                                          AND t_project_material_acceptance_review_user.review_result=? 
                                          AND t_project_material_acceptance_review_user.deleted_at IS NULL
                                          AND t_project_material_acceptance_review.deleted_at IS NULL
                                          AND t_project_material_acceptance_review_mode.deleted_at IS NULL
                                          """,
                                  new ProjectMaterialAcceptanceReviewUserMapper(), projectMaterialAcceptanceReviewModeId, reviewResult);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<ProjectMaterialAcceptanceReviewUser> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review_user
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_review_user 
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialAcceptanceReviewUserMapper(), userId);
    }

    /**
     * 根据projectMaterialAcceptanceReviewId得到记录
     */
    @Override
    public List<ProjectMaterialAcceptanceReviewUser> getByProjectMaterialAcceptanceReviewId(String projectMaterialAcceptanceReviewId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review_user
                                                        WHERE t_project_material_acceptance_review_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialAcceptanceReviewId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_review_user 
                                          WHERE t_project_material_acceptance_review_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialAcceptanceReviewUserMapper(), projectMaterialAcceptanceReviewId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewUser> getPage(int pageNo,
                                                             int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceReviewUser> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<ProjectMaterialAcceptanceReviewUser> getPageByUserId(String userId,
                                                                     int pageNo,
                                                                     int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceReviewUser> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialAcceptanceReviewId
     * @param pageNo                            页号，从1开始
     * @param pageSize                          每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptanceReviewUser> getPageByProjectMaterialAcceptanceReviewId(String projectMaterialAcceptanceReviewId,
                                                                                                int pageNo,
                                                                                                int pageSize) {
        long totalCount = getCountByProjectMaterialAcceptanceReviewId(projectMaterialAcceptanceReviewId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceReviewUser> resultData = getPageQueryByProjectMaterialAcceptanceReviewId(
                projectMaterialAcceptanceReviewId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectMaterialAcceptanceReviewUser> getPageQuery(int pageNo,
                                                                   int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_review_user
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceReviewUserMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<ProjectMaterialAcceptanceReviewUser> getPageQueryByUserId(String userId,
                                                                           int pageNo,
                                                                           int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_review_user
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceReviewUserMapper(), userId, pageNo * pageSize, pageSize);
    }
    public int getCountByProjectMaterialAcceptanceReviewModeId(String projectMaterialAcceptanceReviewModeId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance_review_user
                                                        LEFT JOIN t_project_material_acceptance_review ON t_project_material_acceptance_review_user.t_project_material_acceptance_review_id = t_project_material_acceptance_review.id
                                                        LEFT JOIN t_project_material_acceptance_review_mode ON t_project_material_acceptance_review_mode.id=t_project_material_acceptance_review.t_project_material_acceptance_mode_id
                                                        WHERE   t_project_material_acceptance_review_mode.id=?
                                                                AND t_project_material_acceptance_review_user.deleted_at IS NULL 
                                                                AND t_project_material_acceptance_review.deleted_at IS NULL
                                                                AND t_project_material_acceptance_review_mode.deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialAcceptanceReviewModeId);
        return i == null ? 0 : i;
    }
    private List<ProjectMaterialAcceptanceReviewUser> getPageQueryByProjectMaterialAcceptanceReviewModeId(String projectMaterialAcceptanceReviewModeId,
                                                                                          int pageNo,
                                                                                          int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_review_user
                                          LEFT JOIN t_project_material_acceptance_review ON t_project_material_acceptance_review_user.t_project_material_acceptance_review_id = t_project_material_acceptance_review.id
                                          LEFT JOIN t_project_material_acceptance_review_mode ON t_project_material_acceptance_review_mode.id=t_project_material_acceptance_review.t_project_material_acceptance_mode_id
                                          WHERE   t_project_material_acceptance_review_mode.id=?
                                          AND t_project_material_acceptance_review_user.deleted_at IS NULL 
                                          AND t_project_material_acceptance_review.deleted_at IS NULL
                                          AND t_project_material_acceptance_review_mode.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceReviewUserMapper(), projectMaterialAcceptanceReviewModeId,
                                  pageNo * pageSize, pageSize);
    }

    @Override
    public Page<ProjectMaterialAcceptanceReviewUser> getViewPageByProjectMaterialAcceptanceReviewModeId(String projectMaterialAcceptanceReviewModeId,
                                                                                    Integer pageNo,
                                                                                    Integer pageSize) {
        long totalCount = getCountByProjectMaterialAcceptanceReviewModeId(projectMaterialAcceptanceReviewModeId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptanceReviewUser> resultData = getPageQueryByProjectMaterialAcceptanceReviewModeId(
                projectMaterialAcceptanceReviewModeId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 根据外键（t_project_material_acceptance_review_id）+获得指定页面数据
     *
     * @param projectMaterialAcceptanceReviewId
     * @param pageNo                            页号，从1开始
     * @param pageSize                          每页的记录数
     */
    private List<ProjectMaterialAcceptanceReviewUser> getPageQueryByProjectMaterialAcceptanceReviewId(String projectMaterialAcceptanceReviewId,
                                                                                                      int pageNo,
                                                                                                      int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance_review_user
                                          WHERE t_project_material_acceptance_review_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceReviewUserMapper(), projectMaterialAcceptanceReviewId,
                                  pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectMaterialAcceptanceReviewUserMapper implements RowMapper<ProjectMaterialAcceptanceReviewUser> {
        @Override
        public ProjectMaterialAcceptanceReviewUser mapRow(ResultSet rs,
                                                          int rowNum) throws SQLException {
            ProjectMaterialAcceptanceReviewUser projectMaterialAcceptanceReviewUser = new ProjectMaterialAcceptanceReviewUser();
            projectMaterialAcceptanceReviewUser.setId(rs.getString("id"));
            projectMaterialAcceptanceReviewUser.setUserId(rs.getString("t_user_id"));
            projectMaterialAcceptanceReviewUser.setProjectMaterialAcceptanceReviewId(
                    rs.getString("t_project_material_acceptance_review_id"));
            projectMaterialAcceptanceReviewUser.setReviewResult(rs.getInt("review_result"));
            projectMaterialAcceptanceReviewUser.setReviewContent(rs.getString("review_content"));
            projectMaterialAcceptanceReviewUser.setReviewDatetime(rs.getDate("review_datetime"));
            projectMaterialAcceptanceReviewUser.setDeletedAt(rs.getDate("deleted_at"));
            return projectMaterialAcceptanceReviewUser;
        }
    }

}