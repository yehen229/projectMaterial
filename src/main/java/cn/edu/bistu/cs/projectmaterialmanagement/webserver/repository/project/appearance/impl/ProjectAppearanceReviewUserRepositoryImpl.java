package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.appearance.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReviewUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.appearance.IProjectAppearanceReviewUserRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectAppearanceReviewUserRepositoryImpl implements IProjectAppearanceReviewUserRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectAppearanceReviewUserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectAppearanceReviewUser projectAppearanceReviewUser) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_appearance_review_user(id,
                                        t_user_id,
                                        t_project_appearance_review_id,
                                        review_result,
                                        review_content,
                                        review_datetime)
                                        VALUES(?,?,?,?,?,?)
                                        """,
                                newId,
                                projectAppearanceReviewUser.getUserId(),
                                projectAppearanceReviewUser.getProjectAppearanceReviewId(),
                                projectAppearanceReviewUser.getReviewResult(),
                                projectAppearanceReviewUser.getReviewContent(),
                                projectAppearanceReviewUser.getReviewDatetime()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectAppearanceReviewUser projectAppearanceReviewUser) {
        if (projectAppearanceReviewUser == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review_user
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectAppearanceReviewUser.getId());

    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review_user
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);

    }

    /**
     * 根据userId删除记录
     */
    @Override
    public int deleteByUserId(String userId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review_user
                                           SET deleted_at=? 
                                           WHERE t_user_id=?
                                           """,
                                   new Date(),
                                   userId);

    }

    /**
     * 根据projectAppearanceReviewId删除记录
     */
    @Override
    public int deleteByProjectAppearanceReviewId(String projectAppearanceReviewId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review_user
                                           SET deleted_at=? 
                                           WHERE t_project_appearance_review_id=?
                                           """,
                                   new Date(),
                                   projectAppearanceReviewId);

    }

    /**
     * update
     */
    @Override
    public int update(ProjectAppearanceReviewUser projectAppearanceReviewUser) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review_user
                                           SET t_user_id=?,
                                           t_project_appearance_review_id=?,
                                           review_result=?,
                                           review_content=?,
                                           review_datetime=?
                                           WHERE id=?
                                           """,
                                   projectAppearanceReviewUser.getUserId(),
                                   projectAppearanceReviewUser.getProjectAppearanceReviewId(),
                                   projectAppearanceReviewUser.getReviewResult(),
                                   projectAppearanceReviewUser.getReviewContent(),
                                   projectAppearanceReviewUser.getReviewDatetime(),
                                   projectAppearanceReviewUser.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_user
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
                                                        FROM t_project_appearance_review_user
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectAppearanceReviewId得到总数量
     */
    @Override
    public int getCountByProjectAppearanceReviewId(String projectAppearanceReviewId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_user
                                                        WHERE t_project_appearance_review_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectAppearanceReviewId);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_user
                                                        LEFT JOIN t_project_appearance_review ON t_project_appearance_review_user.t_project_appearance_review_id = t_project_appearance_review.id
                                                        LEFT JOIN t_project_appearance_review_mode ON t_project_appearance_review_mode.id=t_project_appearance_review.t_project_appearance_review_mode_id
                                                        WHERE   t_project_appearance_review_mode.id=?
                                                                AND t_project_appearance_review_user.deleted_at IS NULL 
                                                                AND t_project_appearance_review.deleted_at IS NULL
                                                                AND t_project_appearance_review_mode.deleted_at IS NULL
                                                        """,
                                                Integer.class, projectAppearanceReviewModeId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectAppearanceReviewUser getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_user 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_appearance_review_user
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectAppearanceReviewUserMapper(), id);
    }
    @Override
    public int getCountByProjectId(String projectId){
        Integer totalReviews = jdbcTemplate.queryForObject("""
                                                     SELECT count(*)
                                                     FROM t_project_appearance_review
                                                     JOIN t_project_appearance_review_mode
                                                          ON t_project_appearance_review.t_project_appearance_review_mode_id = t_project_appearance_review_mode.id
                                                     JOIN t_use_material_brand_select
                                                          ON t_project_appearance_review_mode.t_use_material_brand_select_id = t_use_material_brand_select.id
                                                     WHERE t_use_material_brand_select.t_project_id = ? 
                                                     AND t_project_appearance_review.review_status=2
                                                     AND t_project_appearance_review.deleted_at IS NULL 
                                                     AND t_project_appearance_review_mode.deleted_at IS NULL 
                                                     AND t_use_material_brand_select.deleted_at IS NULL
                                                     """,
                Integer.class, projectId);

        return totalReviews == null ? 0 : totalReviews;

    }
    @Override
    public int getCountByProjectIdAndResult(String projectId, int nReviewResult){
        Integer i = jdbcTemplate.queryForObject("""
                                                     SELECT count(*)
                                                     FROM t_project_appearance_review
                                                     JOIN t_project_appearance_review_mode
                                                          ON t_project_appearance_review.t_project_appearance_review_mode_id = t_project_appearance_review_mode.id
                                                     JOIN t_use_material_brand_select
                                                          ON t_project_appearance_review_mode.t_use_material_brand_select_id = t_use_material_brand_select.id
                                                     WHERE t_use_material_brand_select.t_project_id = ? AND t_project_appearance_review.review_result = ? AND t_project_appearance_review.deleted_at IS NULL AND t_project_appearance_review_mode.deleted_at IS NULL AND t_use_material_brand_select.deleted_at IS NULL
                                                     """,
                Integer.class, projectId, nReviewResult);

        return i == null ? 0 : i;


    }

    @Override
    public ProjectAppearanceReviewUser getByUserIdAndUseMaterialBrandSelectId(String userId,
                                                                              String useMaterialBrandSelectId,
                                                                              String projectAppearanceReviewModeId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_user
                                                        LEFT JOIN t_project_appearance_review ON t_project_appearance_review_user.t_project_appearance_review_id = t_project_appearance_review.id
                                                        LEFT JOIN t_project_appearance_review_mode ON t_project_appearance_review_mode.id=t_project_appearance_review.t_project_appearance_review_mode_id
                                                        WHERE t_project_appearance_review_user.t_user_id=? 
                                                                AND t_project_appearance_review_mode.t_use_material_brand_select_id=? 
                                                                AND t_project_appearance_review_mode.id=?
                                                                AND t_project_appearance_review_user.deleted_at IS NULL 
                                                                AND t_project_appearance_review.deleted_at IS NULL
                                                                AND t_project_appearance_review_mode.deleted_at IS NULL
                                                        
                                                        """, Integer.class, userId, useMaterialBrandSelectId,
                                                projectAppearanceReviewModeId);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_appearance_review_user
                                                   LEFT JOIN t_project_appearance_review ON t_project_appearance_review_user.t_project_appearance_review_id = t_project_appearance_review.id
                                                   LEFT JOIN t_project_appearance_review_mode ON t_project_appearance_review_mode.id=t_project_appearance_review.t_project_appearance_review_mode_id
                                                   WHERE t_project_appearance_review_user.t_user_id=? 
                                                      AND t_project_appearance_review_mode.t_use_material_brand_select_id=?
                                                      AND t_project_appearance_review_mode.id=?
                                                   AND t_project_appearance_review_user.deleted_at IS NULL 
                                                   AND t_project_appearance_review.deleted_at IS NULL
                                                   AND t_project_appearance_review_mode.deleted_at IS NULL
                                                   """,
                                           new ProjectAppearanceReviewUserMapper(), userId, useMaterialBrandSelectId,
                                           projectAppearanceReviewModeId);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<ProjectAppearanceReviewUser> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_user
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review_user 
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectAppearanceReviewUserMapper(), userId);
    }

    /**
     * 根据projectAppearanceReviewId得到记录
     */
    @Override
    public List<ProjectAppearanceReviewUser> getByProjectAppearanceReviewId(String projectAppearanceReviewId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_user
                                                        WHERE t_project_appearance_review_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectAppearanceReviewId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review_user 
                                          WHERE t_project_appearance_review_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectAppearanceReviewUserMapper(), projectAppearanceReviewId);
    }

    @Override
    public List<ProjectAppearanceReviewUser> getByAppearanceModeIdAndNotReviewed(String projectAppearanceReviewModeId,
                                                                                 int reviewResult) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_user
                                                        LEFT JOIN t_project_appearance_review ON t_project_appearance_review.id=t_project_appearance_review_user.t_project_appearance_review_id
                                                        LEFT JOIN t_project_appearance_review_mode ON t_project_appearance_review.t_project_appearance_review_mode_id=t_project_appearance_review_mode.id
                                                        WHERE t_project_appearance_review_mode.id=? 
                                                        AND t_project_appearance_review_user.review_result=? 
                                                        AND t_project_appearance_review_user.deleted_at IS NULL
                                                        AND t_project_appearance_review.deleted_at IS NULL
                                                        AND t_project_appearance_review_mode.deleted_at IS NULL
                                                        """,
                                                Integer.class, projectAppearanceReviewModeId, reviewResult);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT t_project_appearance_review_user.* 
                                          FROM t_project_appearance_review_user
                                          LEFT JOIN t_project_appearance_review ON t_project_appearance_review.id=t_project_appearance_review_user.t_project_appearance_review_id
                                          LEFT JOIN t_project_appearance_review_mode ON t_project_appearance_review.t_project_appearance_review_mode_id=t_project_appearance_review_mode.id
                                          WHERE t_project_appearance_review_mode.id=? 
                                          AND t_project_appearance_review_user.review_result=? 
                                          AND t_project_appearance_review_user.deleted_at IS NULL
                                          AND t_project_appearance_review.deleted_at IS NULL
                                          AND t_project_appearance_review_mode.deleted_at IS NULL
                                          """,
                                  new ProjectAppearanceReviewUserMapper(), projectAppearanceReviewModeId, reviewResult);
    }

    @Override
    public List<ProjectAppearanceReviewUser> getByAppearanceModeId(String projectAppearanceReviewModeId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_user
                                                        LEFT JOIN t_project_appearance_review ON t_project_appearance_review.id=t_project_appearance_review_user.t_project_appearance_review_id
                                                        LEFT JOIN t_project_appearance_review_mode ON t_project_appearance_review.t_project_appearance_review_mode_id=t_project_appearance_review_mode.id
                                                        WHERE t_project_appearance_review_mode.id=? 
                                                        AND t_project_appearance_review_user.deleted_at IS NULL
                                                        AND t_project_appearance_review.deleted_at IS NULL
                                                        AND t_project_appearance_review_mode.deleted_at IS NULL
                                                        """,
                                                Integer.class, projectAppearanceReviewModeId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT t_project_appearance_review_user.* 
                                          FROM t_project_appearance_review_user
                                          LEFT JOIN t_project_appearance_review ON t_project_appearance_review.id=t_project_appearance_review_user.t_project_appearance_review_id
                                          LEFT JOIN t_project_appearance_review_mode ON t_project_appearance_review.t_project_appearance_review_mode_id=t_project_appearance_review_mode.id
                                          WHERE t_project_appearance_review_mode.id=? 
                                          AND t_project_appearance_review_user.deleted_at IS NULL
                                          AND t_project_appearance_review.deleted_at IS NULL
                                          AND t_project_appearance_review_mode.deleted_at IS NULL
                                          """,
                                  new ProjectAppearanceReviewUserMapper(), projectAppearanceReviewModeId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewUser> getPage(int pageNo,
                                                     int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectAppearanceReviewUser> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<ProjectAppearanceReviewUser> getPageByUserId(String userId,
                                                             int pageNo,
                                                             int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectAppearanceReviewUser> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectAppearanceReviewId
     * @param pageNo                    页号，从1开始
     * @param pageSize                  每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewUser> getPageByProjectAppearanceReviewId(String projectAppearanceReviewId,
                                                                                int pageNo,
                                                                                int pageSize) {
        long totalCount = getCountByProjectAppearanceReviewId(projectAppearanceReviewId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectAppearanceReviewUser> resultData = getPageQueryByProjectAppearanceReviewId(
                projectAppearanceReviewId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<ProjectAppearanceReviewUser> getPageByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId,
                                                                                    Integer pageNo,
                                                                                    Integer pageSize) {
        long totalCount = getCountByProjectAppearanceReviewModeId(projectAppearanceReviewModeId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectAppearanceReviewUser> resultData = getPageQueryByProjectAppearanceReviewModeId(
                projectAppearanceReviewModeId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectAppearanceReviewUser> getPageQuery(int pageNo,
                                                           int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review_user
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectAppearanceReviewUserMapper(), pageNo * pageSize, pageSize);
    }


    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<ProjectAppearanceReviewUser> getPageQueryByUserId(String userId,
                                                                   int pageNo,
                                                                   int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review_user
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectAppearanceReviewUserMapper(), userId, pageNo * pageSize, pageSize);
    }

    private List<ProjectAppearanceReviewUser> getPageQueryByProjectAppearanceReviewModeId(String projectAppearanceReviewModeId,
                                                                                          int pageNo,
                                                                                          int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review_user
                                          LEFT JOIN t_project_appearance_review ON t_project_appearance_review_user.t_project_appearance_review_id = t_project_appearance_review.id
                                          LEFT JOIN t_project_appearance_review_mode ON t_project_appearance_review_mode.id=t_project_appearance_review.t_project_appearance_review_mode_id
                                          WHERE   t_project_appearance_review_mode.id=?
                                          AND t_project_appearance_review_user.deleted_at IS NULL 
                                          AND t_project_appearance_review.deleted_at IS NULL
                                          AND t_project_appearance_review_mode.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectAppearanceReviewUserMapper(), projectAppearanceReviewModeId,
                                  pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_appearance_review_id）+获得指定页面数据
     *
     * @param projectAppearanceReviewId
     * @param pageNo                    页号，从1开始
     * @param pageSize                  每页的记录数
     */
    private List<ProjectAppearanceReviewUser> getPageQueryByProjectAppearanceReviewId(String projectAppearanceReviewId,
                                                                                      int pageNo,
                                                                                      int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review_user
                                          WHERE t_project_appearance_review_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectAppearanceReviewUserMapper(), projectAppearanceReviewId, pageNo * pageSize,
                                  pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectAppearanceReviewUserMapper implements RowMapper<ProjectAppearanceReviewUser> {
        @Override
        public ProjectAppearanceReviewUser mapRow(ResultSet rs,
                                                  int rowNum) throws SQLException {
            ProjectAppearanceReviewUser projectAppearanceReviewUser = new ProjectAppearanceReviewUser();
            projectAppearanceReviewUser.setId(rs.getString("id"));
            projectAppearanceReviewUser.setUserId(rs.getString("t_user_id"));
            projectAppearanceReviewUser.setProjectAppearanceReviewId(rs.getString("t_project_appearance_review_id"));
            projectAppearanceReviewUser.setReviewContent(rs.getString("review_content"));
            projectAppearanceReviewUser.setReviewResult(rs.getInt("review_result"));
            projectAppearanceReviewUser.setReviewDatetime(rs.getTimestamp("review_datetime"));
            projectAppearanceReviewUser.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectAppearanceReviewUser;
        }
    }

}