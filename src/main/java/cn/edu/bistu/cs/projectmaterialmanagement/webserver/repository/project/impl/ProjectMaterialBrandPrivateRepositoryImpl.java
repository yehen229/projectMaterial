package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandPrivate;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialBrandPrivateRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectMaterialBrandPrivateRepositoryImpl implements IProjectMaterialBrandPrivateRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectMaterialBrandPrivateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectMaterialBrandPrivate projectMaterialBrandPrivate) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_material_brand_private(id,
                                        t_project_material_id,
                                        t_project_brand_id,
                                        deleted_at)
                                        VALUES(?,?,?,?)
                                        """,
                                newId,
                                projectMaterialBrandPrivate.getProjectMaterialId(),
                                projectMaterialBrandPrivate.getProjectBrandId(),
                                projectMaterialBrandPrivate.getDeletedAt()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectMaterialBrandPrivate projectMaterialBrandPrivate) {
        if (projectMaterialBrandPrivate == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_brand_private
                                           SET deleted_at=? 
                                           WHERE t_brand_id=?
                                           """,
                                   new Date(),
                                   projectMaterialBrandPrivate.getId());
    }

    /**
     * update
     */
    @Override
    public int update(ProjectMaterialBrandPrivate projectMaterialBrandPrivate) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_brand_private
                                           SET t_project_material_id=?,
                                           t_project_brand_id=?,
                                           deleted_at=? 
                                           WHERE id=?
                                           """,
                                   projectMaterialBrandPrivate.getProjectMaterialId(),
                                   projectMaterialBrandPrivate.getProjectBrandId(),
                                   projectMaterialBrandPrivate.getDeletedAt(),
                                   projectMaterialBrandPrivate.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_brand_private
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);
    }

    /**
     * 根据projectMaterialId删除记录
     */
    @Override
    public int deleteByProjectMaterialId(String projectMaterialId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_brand_private
                                           SET deleted_at=? 
                                           WHERE t_project_material_id=?
                                           """,
                                   new Date(),
                                   projectMaterialId);


    }

    /**
     * 根据projectBrandId删除记录
     */
    @Override
    public int deleteByProjectBrandId(String projectBrandId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_brand_private
                                           SET deleted_at=? 
                                           WHERE t_project_brand_id=?
                                           """,
                                   new Date(),
                                   projectBrandId);


    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_brand_private
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectMaterialId得到总数量
     */
    @Override
    public int getCountByProjectMaterialId(String projectMaterialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_brand_private
                                                        WHERE t_project_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectBrandId得到总数量
     */
    @Override
    public int getCountByProjectBrandId(String projectBrandId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_brand_private
                                                        WHERE t_project_brand_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectBrandId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectMaterialBrandPrivate getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_brand_private 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_material_brand_private
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectMaterialBrandPrivateMapper(), id);
    }

    @Override
    public ProjectMaterialBrandPrivate getByProjectMaterialIdAndProjectBrandId(String projectMaterialId,
                                                                               String projectBrandId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_brand_private 
                                                        WHERE t_project_material_id=? AND t_project_brand_id=? AND deleted_at IS NULL
                                                        """, Integer.class, projectMaterialId, projectBrandId);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_material_brand_private
                                                   WHERE t_project_material_id=? AND t_project_brand_id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectMaterialBrandPrivateMapper(), projectMaterialId, projectBrandId);
    }

    /**
     * 根据projectMaterialId得到记录
     */
    @Override
    public List<ProjectMaterialBrandPrivate> getByProjectMaterialId(String projectMaterialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_brand_private
                                                        WHERE t_project_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_brand_private 
                                          WHERE t_project_material_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialBrandPrivateMapper(), projectMaterialId);
    }

    /**
     * 根据projectBrandId得到记录
     */
    @Override
    public List<ProjectMaterialBrandPrivate> getByProjectBrandId(String projectBrandId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_brand_private
                                                        WHERE t_project_brand_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectBrandId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_brand_private 
                                          WHERE t_project_brand_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialBrandPrivateMapper(), projectBrandId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialBrandPrivate> getPage(int pageNo,
                                                     int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialBrandPrivate> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialId
     * @param pageNo            页号，从1开始
     * @param pageSize          每页的记录数
     */
    @Override
    public Page<ProjectMaterialBrandPrivate> getPageByProjectMaterialId(String projectMaterialId,
                                                                        int pageNo,
                                                                        int pageSize) {
        long totalCount = getCountByProjectMaterialId(projectMaterialId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialBrandPrivate> resultData = getPageQueryByProjectMaterialId(projectMaterialId, pageNo - 1,
                                                                                       pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectBrandId
     * @param pageNo         页号，从1开始
     * @param pageSize       每页的记录数
     */
    @Override
    public Page<ProjectMaterialBrandPrivate> getPageByProjectBrandId(String projectBrandId,
                                                                     int pageNo,
                                                                     int pageSize) {
        long totalCount = getCountByProjectBrandId(projectBrandId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialBrandPrivate> resultData = getPageQueryByProjectBrandId(projectBrandId, pageNo - 1,
                                                                                    pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectMaterialBrandPrivate> getPageQuery(int pageNo,
                                                           int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_brand_private
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialBrandPrivateMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_material_id）+获得指定页面数据
     *
     * @param projectMaterialId
     * @param pageNo            页号，从1开始
     * @param pageSize          每页的记录数
     */
    private List<ProjectMaterialBrandPrivate> getPageQueryByProjectMaterialId(String projectMaterialId,
                                                                              int pageNo,
                                                                              int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_brand_private
                                          WHERE t_project_material_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialBrandPrivateMapper(), projectMaterialId, pageNo * pageSize,
                                  pageSize);
    }

    /**
     * 根据外键（t_project_brand_id）+获得指定页面数据
     *
     * @param projectBrandId
     * @param pageNo         页号，从1开始
     * @param pageSize       每页的记录数
     */
    private List<ProjectMaterialBrandPrivate> getPageQueryByProjectBrandId(String projectBrandId,
                                                                           int pageNo,
                                                                           int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_brand_private
                                          WHERE t_project_brand_id=?  AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialBrandPrivateMapper(), projectBrandId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectMaterialBrandPrivateMapper implements RowMapper<ProjectMaterialBrandPrivate> {
        @Override
        public ProjectMaterialBrandPrivate mapRow(ResultSet rs,
                                                  int rowNum) throws SQLException {
            ProjectMaterialBrandPrivate projectMaterialBrandPrivate = new ProjectMaterialBrandPrivate();
            projectMaterialBrandPrivate.setId(rs.getString("id"));
            projectMaterialBrandPrivate.setProjectMaterialId(rs.getString("t_project_material_id"));
            projectMaterialBrandPrivate.setProjectBrandId(rs.getString("t_project_brand_id"));
            projectMaterialBrandPrivate.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectMaterialBrandPrivate;
        }
    }

}