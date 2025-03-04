package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialBrandSelect;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IUseMaterialBrandSelectRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class UseMaterialBrandSelectRepositoryImpl implements IUseMaterialBrandSelectRepository {
    private final JdbcTemplate jdbcTemplate;

    public UseMaterialBrandSelectRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(UseMaterialBrandSelect useMaterialBrandSelect) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_use_material_brand_select(id,
                                        t_user_id,
                                        t_project_id,
                                        create_datetime 
                                        )
                                        VALUES(?,?,?,?)
                                        """,
                                newId,
                                useMaterialBrandSelect.getUserId(),
                                useMaterialBrandSelect.getProjectId(),
                                new Date()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(UseMaterialBrandSelect useMaterialBrandSelect) {
        if (useMaterialBrandSelect == null) return 0;
        return jdbcTemplate.update("""
                                           DELETE FROM t_use_material_brand_select 
                                           WHERE id=?
                                           """,
                                   useMaterialBrandSelect.getId());
    }

    /**
     * update
     */
    @Override
    public int update(UseMaterialBrandSelect useMaterialBrandSelect) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material_brand_select
                                           SET t_user_id=?,
                                           t_project_id=?
                                           WHERE id=?
                                           """,
                                   useMaterialBrandSelect.getUserId(),
                                   useMaterialBrandSelect.getProjectId(),
                                   useMaterialBrandSelect.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {


        return jdbcTemplate.update("""
                                           UPDATE t_use_material_brand_select
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(), id);
    }

    /**
     * 根据userId删除记录
     */
    @Override
    public int deleteByUserId(String userId) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material_brand_select
                                           SET deleted_at=? 
                                           WHERE t_user_id=?
                                           """,
                                   new Date(), userId);
    }

    /**
     * 根据projectId删除记录
     */
    @Override
    public int deleteByProjectId(String projectId) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material_brand_select
                                           SET deleted_at=? 
                                           WHERE t_project_id=?
                                           """,
                                   new Date(), projectId);
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material_brand_select
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
                                                        FROM t_use_material_brand_select
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
                                                        FROM t_use_material_brand_select
                                                        WHERE t_project_id=?  AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public UseMaterialBrandSelect getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material_brand_select 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_use_material_brand_select
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new UseMaterialBrandSelectMapper(), id);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<UseMaterialBrandSelect> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material_brand_select
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material_brand_select 
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          """,
                                  new UseMaterialBrandSelectMapper(), userId);
    }

    /**
     * 根据projectId得到记录
     */
    @Override
    public List<UseMaterialBrandSelect> getByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material_brand_select
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material_brand_select 
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          """,
                                  new UseMaterialBrandSelectMapper(), projectId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UseMaterialBrandSelect> getPage(int pageNo,
                                                int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterialBrandSelect> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<UseMaterialBrandSelect> getPageByUserId(String userId,
                                                        int pageNo,
                                                        int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterialBrandSelect> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
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
    public Page<UseMaterialBrandSelect> getPageByProjectId(String projectId,
                                                           int pageNo,
                                                           int pageSize) {
        long totalCount = getCountByProjectId(projectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterialBrandSelect> resultData = getPageQueryByProjectId(projectId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<UseMaterialBrandSelect> getPageQuery(int pageNo,
                                                      int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material_brand_select
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new UseMaterialBrandSelectMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<UseMaterialBrandSelect> getPageQueryByUserId(String userId,
                                                              int pageNo,
                                                              int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material_brand_select
                                          WHERE t_user_id=?  AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new UseMaterialBrandSelectMapper(), userId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_id）+获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    private List<UseMaterialBrandSelect> getPageQueryByProjectId(String projectId,
                                                                 int pageNo,
                                                                 int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material_brand_select
                                          WHERE t_project_id=?  AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new UseMaterialBrandSelectMapper(), projectId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class UseMaterialBrandSelectMapper implements RowMapper<UseMaterialBrandSelect> {
        @Override
        public UseMaterialBrandSelect mapRow(ResultSet rs,
                                             int rowNum) throws SQLException {
            UseMaterialBrandSelect useMaterialBrandSelect = new UseMaterialBrandSelect();
            useMaterialBrandSelect.setId(rs.getString("id"));
            useMaterialBrandSelect.setUserId(rs.getString("t_user_id"));
            useMaterialBrandSelect.setProjectId(rs.getString("t_project_id"));
            useMaterialBrandSelect.setCreateDatetime(rs.getTimestamp("create_datetime"));
            useMaterialBrandSelect.setDeletedAt(rs.getTimestamp("deleted_at"));
            return useMaterialBrandSelect;
        }
    }

}