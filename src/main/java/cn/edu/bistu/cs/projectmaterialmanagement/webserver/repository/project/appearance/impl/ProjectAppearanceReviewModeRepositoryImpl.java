package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.appearance.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.appearance.ProjectAppearanceReviewMode;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.appearance.IProjectAppearanceReviewModeRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectAppearanceReviewModeRepositoryImpl implements IProjectAppearanceReviewModeRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectAppearanceReviewModeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectAppearanceReviewMode projectAppearanceReviewMode) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_appearance_review_mode(id,
                                        t_use_material_brand_select_id,
                                        t_user_id,
                                        mode,
                                        affect_appearance,
                                        create_datetime
                                        )
                                        VALUES(?,?,?,?,?,?)
                                        """,
                                newId,
                                projectAppearanceReviewMode.getUseMaterialBrandSelectId(),
                                projectAppearanceReviewMode.getUserId(),
                                projectAppearanceReviewMode.getMode(),
                                projectAppearanceReviewMode.getAffectAppearance(),
                                new Date()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectAppearanceReviewMode projectAppearanceReviewMode) {
        if (projectAppearanceReviewMode == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review_mode
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectAppearanceReviewMode.getId());

    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review_mode
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);

    }

    @Override
    public int deleteByUseMaterialBrandSelectId(String useMaterialBrandSelectId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review_mode
                                           SET deleted_at=? 
                                           WHERE t_use_material_brand_select_id=?
                                           """,
                                   new Date(),
                                   useMaterialBrandSelectId);
    }

    /**
     * 根据userId删除记录
     */
    @Override
    public int deleteByUserId(String userId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review_mode
                                           SET deleted_at=? 
                                           WHERE t_user_id=?
                                           """,
                                   new Date(),
                                   userId);

    }

    /**
     * update
     */
    @Override
    public int update(ProjectAppearanceReviewMode projectAppearanceReviewMode) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_appearance_review_mode
                                           SET t_use_material_brand_select_id=?,
                                           t_user_id=?,
                                           mode=?,
                                           affect_appearance=?
                                           WHERE id=?
                                           """,
                                   projectAppearanceReviewMode.getUseMaterialBrandSelectId(),
                                   projectAppearanceReviewMode.getUserId(),
                                   projectAppearanceReviewMode.getMode(),
                                   projectAppearanceReviewMode.getAffectAppearance(),
                                   projectAppearanceReviewMode.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_mode
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByUseMaterialBrandSelectId(String useMaterialBrandSelectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_mode
                                                        WHERE t_use_material_brand_select_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, useMaterialBrandSelectId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键UserId得到总数量
     */
    @Override
    public int getCountByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_mode
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectAppearanceReviewMode getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_mode 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_appearance_review_mode
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectAppearanceReviewModeMapper(), id);
    }

    @Override
    public List<ProjectAppearanceReviewMode> getByUseMaterialBrandSelectId(String useMaterialBrandSelectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_mode
                                                        WHERE t_use_material_brand_select_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, useMaterialBrandSelectId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review_mode 
                                          WHERE t_use_material_brand_select_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectAppearanceReviewModeMapper(), useMaterialBrandSelectId);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<ProjectAppearanceReviewMode> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_mode
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review_mode 
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectAppearanceReviewModeMapper(), userId);
    }

    @Override
    public List<ProjectAppearanceReviewMode> getByUseMaterialBrandSelectIdAndUserId(String useMaterialBrandSelectId,
                                                                                    String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_mode
                                                        WHERE t_use_material_brand_select_id=? AND t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review_mode 
                                          WHERE t_use_material_brand_select_id=? AND t_user_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectAppearanceReviewModeMapper(), useMaterialBrandSelectId, userId);
    }

    @Override
    public List<ProjectAppearanceReviewMode> getByUseMaterialBrandSelectIdAndUserIdAndAffectAppearance(String useMaterialBrandSelectId,
                                                                                                       String userId,
                                                                                                       int affectAppearance) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_appearance_review_mode
                                                        WHERE t_use_material_brand_select_id=? AND t_user_id=? AND affect_appearance=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review_mode 
                                          WHERE t_use_material_brand_select_id=? AND t_user_id=? AND affect_appearance=? AND deleted_at IS NULL
                                          """,
                                  new ProjectAppearanceReviewModeMapper(), useMaterialBrandSelectId, userId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectAppearanceReviewMode> getPage(int pageNo,
                                                     int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectAppearanceReviewMode> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<ProjectAppearanceReviewMode> getPageByUseMaterialBrandSelectId(String useMaterialBrandSelectId,
                                                                               int pageNo,
                                                                               int pageSize) {
        long totalCount = getCountByUseMaterialBrandSelectId(useMaterialBrandSelectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectAppearanceReviewMode> resultData = getPageQueryByUseMaterialBrandSelectId(useMaterialBrandSelectId,
                                                                                              pageNo - 1,
                                                                                              pageSize);
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
    public Page<ProjectAppearanceReviewMode> getPageByUserId(String userId,
                                                             int pageNo,
                                                             int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectAppearanceReviewMode> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }


    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectAppearanceReviewMode> getPageQuery(int pageNo,
                                                           int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review_mode
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectAppearanceReviewModeMapper(), pageNo * pageSize, pageSize);
    }

    private List<ProjectAppearanceReviewMode> getPageQueryByUseMaterialBrandSelectId(String useMaterialBrandSelectId,
                                                                                     int pageNo,
                                                                                     int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review_mode
                                          WHERE t_use_material_brand_select_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectAppearanceReviewModeMapper(), useMaterialBrandSelectId, pageNo * pageSize,
                                  pageSize);
    }


    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<ProjectAppearanceReviewMode> getPageQueryByUserId(String userId,
                                                                   int pageNo,
                                                                   int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_appearance_review_mode
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectAppearanceReviewModeMapper(), userId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectAppearanceReviewModeMapper implements RowMapper<ProjectAppearanceReviewMode> {
        @Override
        public ProjectAppearanceReviewMode mapRow(ResultSet rs,
                                                  int rowNum) throws SQLException {
            ProjectAppearanceReviewMode projectAppearanceReviewMode = new ProjectAppearanceReviewMode();
            projectAppearanceReviewMode.setId(rs.getString("id"));
            projectAppearanceReviewMode.setUseMaterialBrandSelectId(rs.getString("t_use_material_brand_select_id"));
            projectAppearanceReviewMode.setUserId(rs.getString("t_user_id"));
            projectAppearanceReviewMode.setMode(rs.getInt("mode"));
            projectAppearanceReviewMode.setDeletedAt(rs.getTimestamp("deleted_at"));
            projectAppearanceReviewMode.setCreateDatetime(rs.getTimestamp("create_datetime"));
            projectAppearanceReviewMode.setAffectAppearance(rs.getInt("affect_appearance"));
            return projectAppearanceReviewMode;
        }
    }

}