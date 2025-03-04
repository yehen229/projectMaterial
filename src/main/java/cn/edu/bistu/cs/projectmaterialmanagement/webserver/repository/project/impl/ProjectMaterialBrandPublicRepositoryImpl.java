package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialBrandPublic;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialBrandPublicRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectMaterialBrandPublicRepositoryImpl implements IProjectMaterialBrandPublicRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectMaterialBrandPublicRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectMaterialBrandPublic projectMaterialBrandPublic) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_material_brand_public(id,
                                        t_project_material_id,
                                        t_brand_public_id,
                                        deleted_at)
                                        VALUES(?,?,?,?)
                                        """,
                                newId,
                                projectMaterialBrandPublic.getProjectMaterialId(),
                                projectMaterialBrandPublic.getBrandPublicId(),
                                projectMaterialBrandPublic.getDeletedAt()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectMaterialBrandPublic projectMaterialBrandPublic) {
        if (projectMaterialBrandPublic == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_brand_public
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectMaterialBrandPublic.getId());
    }

    /**
     * update
     */
    @Override
    public int update(ProjectMaterialBrandPublic projectMaterialBrandPublic) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_brand_public
                                           SET t_project_material_id=?,
                                           t_brand_public_id=?,
                                           deleted_at=? 
                                           WHERE id=?
                                           """,
                                   projectMaterialBrandPublic.getProjectMaterialId(),
                                   projectMaterialBrandPublic.getBrandPublicId(),
                                   projectMaterialBrandPublic.getDeletedAt(),
                                   projectMaterialBrandPublic.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_brand_public
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
                                           UPDATE t_project_material_brand_public
                                           SET deleted_at=? 
                                           WHERE t_project_material_id=?
                                           """,
                                   new Date(),
                                   projectMaterialId);


    }

    /**
     * 根据brandPublicId删除记录
     */
    @Override
    public int deleteByBrandPublicId(String brandPublicId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_brand_public
                                           SET deleted_at=? 
                                           WHERE t_brand_public_id=?
                                           """,
                                   new Date(),
                                   brandPublicId);


    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_brand_public
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
                                                        FROM t_project_material_brand_public
                                                        WHERE t_project_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键BrandPublicId得到总数量
     */
    @Override
    public int getCountByBrandPublicId(String brandPublicId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_brand_public
                                                        WHERE t_brand_public_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, brandPublicId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectMaterialBrandPublic getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_brand_public 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_material_brand_public
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectMaterialBrandPublicMapper(), id);
    }

    /**
     * 根据projectMaterialId得到记录
     */
    @Override
    public List<ProjectMaterialBrandPublic> getByProjectMaterialId(String projectMaterialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_brand_public
                                                        WHERE t_project_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_brand_public 
                                          WHERE t_project_material_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialBrandPublicMapper(), projectMaterialId);
    }

    /**
     * 根据brandPublicId得到记录
     */
    @Override
    public List<ProjectMaterialBrandPublic> getByBrandPublicId(String brandPublicId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_brand_public
                                                        WHERE t_brand_public_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, brandPublicId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_brand_public 
                                          WHERE t_brand_public_id=?  AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialBrandPublicMapper(), brandPublicId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialBrandPublic> getPage(int pageNo,
                                                    int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialBrandPublic> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<ProjectMaterialBrandPublic> getPageByProjectMaterialId(String projectMaterialId,
                                                                       int pageNo,
                                                                       int pageSize) {
        long totalCount = getCountByProjectMaterialId(projectMaterialId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialBrandPublic> resultData = getPageQueryByProjectMaterialId(projectMaterialId, pageNo - 1,
                                                                                      pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param brandPublicId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    @Override
    public Page<ProjectMaterialBrandPublic> getPageByBrandPublicId(String brandPublicId,
                                                                   int pageNo,
                                                                   int pageSize) {
        long totalCount = getCountByBrandPublicId(brandPublicId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialBrandPublic> resultData = getPageQueryByBrandPublicId(brandPublicId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectMaterialBrandPublic> getPageQuery(int pageNo,
                                                          int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_brand_public AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialBrandPublicMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_material_id）+获得指定页面数据
     *
     * @param projectMaterialId
     * @param pageNo            页号，从1开始
     * @param pageSize          每页的记录数
     */
    private List<ProjectMaterialBrandPublic> getPageQueryByProjectMaterialId(String projectMaterialId,
                                                                             int pageNo,
                                                                             int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_brand_public
                                          WHERE t_project_material_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialBrandPublicMapper(), projectMaterialId, pageNo * pageSize,
                                  pageSize);
    }

    /**
     * 根据外键（t_brand_public_id）+获得指定页面数据
     *
     * @param brandPublicId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    private List<ProjectMaterialBrandPublic> getPageQueryByBrandPublicId(String brandPublicId,
                                                                         int pageNo,
                                                                         int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_brand_public
                                          WHERE t_brand_public_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialBrandPublicMapper(), brandPublicId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectMaterialBrandPublicMapper implements RowMapper<ProjectMaterialBrandPublic> {
        @Override
        public ProjectMaterialBrandPublic mapRow(ResultSet rs,
                                                 int rowNum) throws SQLException {
            ProjectMaterialBrandPublic projectMaterialBrandPublic = new ProjectMaterialBrandPublic();
            projectMaterialBrandPublic.setId(rs.getString("id"));
            projectMaterialBrandPublic.setProjectMaterialId(rs.getString("t_project_material_id"));
            projectMaterialBrandPublic.setBrandPublicId(rs.getString("t_brand_public_id"));
            projectMaterialBrandPublic.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectMaterialBrandPublic;
        }
    }

}