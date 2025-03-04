package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialRetest;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialRetestRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectMaterialRetestRepositoryImpl implements IProjectMaterialRetestRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectMaterialRetestRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectMaterialRetest projectMaterialRetest) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_material_retest(id,
                                        t_buy_material_id,
                                        t_user_id,
                                        need_retest,
                                        review_result,
                                        review_content,
                                        review_datetime)
                                        VALUES(?,?,?,?,?,?,?)
                                        """,
                                newId,
                                projectMaterialRetest.getBuyMaterialId(),
                                projectMaterialRetest.getUserId(),
                                projectMaterialRetest.getNeedRetest(),
                                projectMaterialRetest.getReviewResult(),
                                projectMaterialRetest.getReviewContent(),
                                projectMaterialRetest.getReviewDatetime()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectMaterialRetest projectMaterialRetest) {
        if (projectMaterialRetest == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_retest
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectMaterialRetest.getId());

    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_retest
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);

    }

    /**
     * 根据buyMaterialId删除记录
     */
    @Override
    public int deleteByBuyMaterialId(String buyMaterialId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_retest
                                           SET deleted_at=? 
                                           WHERE t_buy_material_id=?
                                           """,
                                   new Date(),
                                   buyMaterialId);

    }

    /**
     * 根据userId删除记录
     */
    @Override
    public int deleteByUserId(String userId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_retest
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
    public int update(ProjectMaterialRetest projectMaterialRetest) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_retest
                                           SET t_buy_material_id=?,
                                           t_user_id=?,
                                           need_retest=?,
                                           review_result=?,
                                           review_content=?,
                                           review_datetime=?
                                           WHERE id=?
                                           """,
                                   projectMaterialRetest.getBuyMaterialId(),
                                   projectMaterialRetest.getUserId(),
                                   projectMaterialRetest.getNeedRetest(),
                                   projectMaterialRetest.getReviewResult(),
                                   projectMaterialRetest.getReviewContent(),
                                   projectMaterialRetest.getReviewDatetime(),
                                   projectMaterialRetest.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_retest
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键BuyMaterialId得到总数量
     */
    @Override
    public int getCountByBuyMaterialId(String buyMaterialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_retest
                                                        WHERE t_buy_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, buyMaterialId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键UserId得到总数量
     */
    @Override
    public int getCountByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_retest
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectMaterialRetest getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_retest 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_material_retest
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectMaterialRetestMapper(), id);
    }

    /**
     * 根据buyMaterialId得到记录
     */
    @Override
    public List<ProjectMaterialRetest> getByBuyMaterialId(String buyMaterialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_retest
                                                        WHERE t_buy_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, buyMaterialId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_retest 
                                          WHERE t_buy_material_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialRetestMapper(), buyMaterialId);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<ProjectMaterialRetest> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_retest
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_retest 
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialRetestMapper(), userId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialRetest> getPage(int pageNo,
                                               int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialRetest> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param buyMaterialId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    @Override
    public Page<ProjectMaterialRetest> getPageByBuyMaterialId(String buyMaterialId,
                                                              int pageNo,
                                                              int pageSize) {
        long totalCount = getCountByBuyMaterialId(buyMaterialId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialRetest> resultData = getPageQueryByBuyMaterialId(buyMaterialId, pageNo - 1, pageSize);
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
    public Page<ProjectMaterialRetest> getPageByUserId(String userId,
                                                       int pageNo,
                                                       int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialRetest> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectMaterialRetest> getPageQuery(int pageNo,
                                                     int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_retest
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialRetestMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_buy_material_id）+获得指定页面数据
     *
     * @param buyMaterialId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    private List<ProjectMaterialRetest> getPageQueryByBuyMaterialId(String buyMaterialId,
                                                                    int pageNo,
                                                                    int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_retest
                                          WHERE t_buy_material_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialRetestMapper(), buyMaterialId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<ProjectMaterialRetest> getPageQueryByUserId(String userId,
                                                             int pageNo,
                                                             int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_retest
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialRetestMapper(), userId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectMaterialRetestMapper implements RowMapper<ProjectMaterialRetest> {
        @Override
        public ProjectMaterialRetest mapRow(ResultSet rs,
                                            int rowNum) throws SQLException {
            ProjectMaterialRetest projectMaterialRetest = new ProjectMaterialRetest();
            projectMaterialRetest.setId(rs.getString("id"));
            projectMaterialRetest.setBuyMaterialId(rs.getString("t_buy_material_id"));
            projectMaterialRetest.setUserId(rs.getString("t_user_id"));
            projectMaterialRetest.setNeedRetest(rs.getInt("need_retest"));
            projectMaterialRetest.setReviewResult(rs.getInt("review_result"));
            projectMaterialRetest.setReviewDatetime(rs.getTimestamp("review_datetime"));
            projectMaterialRetest.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectMaterialRetest;
        }
    }

}