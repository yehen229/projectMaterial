package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectBrand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Brandexcel;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectBrandRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectBrandRepositoryImpl implements IProjectBrandRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectBrandRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectBrand projectBrand) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_brand(id,
                                        t_project_id,
                                        t_brand_id)
                                        VALUES(?,?,?)
                                        """,
                                newId,
                                projectBrand.getProjectId(),
                                projectBrand.getBrandId()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectBrand projectBrand) {
        if (projectBrand == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_brand
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectBrand.getId());

    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_brand
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);

    }

    /**
     * 根据projectId删除记录
     */
    @Override
    public int deleteByProjectId(String projectId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_brand
                                           SET deleted_at=? 
                                           WHERE t_project_id=?
                                           """,
                                   new Date(),
                                   projectId);

    }

    /**
     * 根据brandId删除记录
     */
    @Override
    public int deleteByBrandId(String brandId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_brand
                                           SET deleted_at=? 
                                           WHERE t_brand_id=?
                                           """,
                                   new Date(),
                                   brandId);

    }

    /**
     * update
     */
    @Override
    public int update(ProjectBrand projectBrand) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_brand
                                           SET t_project_id=?,
                                           t_brand_id=?
                                           WHERE id=?
                                           """,
                                   projectBrand.getProjectId(),
                                   projectBrand.getBrandId(),
                                   projectBrand.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_brand
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectId得到总数量
     */
    @Override
    public int getCountByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_brand
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        return i == null ? 0 : i;
    }
    /**
     * 根据外键ProjectId和位置得到总数量
     */
    @Override
    public int getCountByProjectIdAndPosition(String projectId, String position) {
        position = "%" + position.trim() + "%";
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_brand
                                                        INNER JOIN t_brand ON t_project_brand.t_brand_id = t_brand.id
                                                        WHERE t_project_brand.t_project_id=? AND t_project_brand.deleted_at IS NULL
                                                        AND t_brand.deleted_at IS NULL
                                                        AND t_brand.position LIKE ?
                                                        """,
                                                Integer.class, projectId, position);
        return i == null ? 0 : i;
    }
    /**
     * 根据外键ProjectId和品牌名得到总数量
     */
    @Override
    public int getCountByProjectIdAndBrandName(String projectId, String brandName) {
        brandName = "%" + brandName.trim() + "%";
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_brand
                                                        INNER JOIN t_brand ON t_project_brand.t_brand_id = t_brand.id
                                                        WHERE t_project_brand.t_project_id=? AND t_project_brand.deleted_at IS NULL
                                                        AND t_brand.deleted_at IS NULL
                                                        AND t_brand.name LIKE ?
                                                        """,
                                                Integer.class, projectId, brandName);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键BrandId得到总数量
     */
    @Override
    public int getCountByBrandId(String brandId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_brand
                                                        WHERE t_brand_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, brandId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectBrand getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_brand 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_brand
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectBrandMapper(), id);
    }

    @Override
    public ProjectBrand getByProjectIdAndBrandId(String projectId,
                                                 String brandId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_brand 
                                                        WHERE t_project_id=? AND t_brand_id=? AND deleted_at IS NULL
                                                        """, Integer.class, projectId, brandId);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_brand
                                                    WHERE t_project_id=? AND t_brand_id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectBrandMapper(), projectId, brandId);
    }

    /**
     * 根据projectId得到记录
     */
    @Override
    public List<ProjectBrand> getByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_brand
                                                        WHERE t_project_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_brand 
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectBrandMapper(), projectId);
    }

    /**
     * 根据brandId得到记录
     */
    @Override
    public List<ProjectBrand> getByBrandId(String brandId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_brand
                                                        WHERE t_brand_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, brandId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_brand 
                                          WHERE t_brand_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectBrandMapper(), brandId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectBrand> getPage(int pageNo,
                                      int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectBrand> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<ProjectBrand> getPageByProjectId(String projectId,
                                                 int pageNo,
                                                 int pageSize) {
        long totalCount = getCountByProjectId(projectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectBrand> resultData = getPageQueryByProjectId(projectId, pageNo - 1, pageSize);
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
    public Page<ProjectBrand> getPageByProjectIdAndPosition(String projectId,
                                                 String position,
                                                 int pageNo,
                                                 int pageSize) {
        long totalCount = getCountByProjectIdAndPosition(projectId, position);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectBrand> resultData = getPageQueryByProjectIdAndPosition(projectId, position, pageNo - 1, pageSize);
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
    public Page<ProjectBrand> getPageByProjectIdAndBrandName(String projectId,
                                                 String brandName,
                                                 int pageNo,
                                                 int pageSize) {
        long totalCount = getCountByProjectIdAndBrandName(projectId, brandName);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectBrand> resultData = getPageQueryByProjectIdAndBrandName(projectId, brandName, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param brandId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectBrand> getPageByBrandId(String brandId,
                                               int pageNo,
                                               int pageSize) {
        long totalCount = getCountByBrandId(brandId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectBrand> resultData = getPageQueryByBrandId(brandId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectBrand> getPageQuery(int pageNo,
                                            int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_brand
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectBrandMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_id）+获得指定页面数据
     *
     * @param projectId
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    private List<ProjectBrand> getPageQueryByProjectId(String projectId,
                                                       int pageNo,
                                                       int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_brand
                                          WHERE t_project_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectBrandMapper(), projectId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_id和位置）+获得指定页面数据
     *
     * @param projectId
     * @param position
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    private List<ProjectBrand> getPageQueryByProjectIdAndPosition(String projectId,
                                                       String position,
                                                       int pageNo,
                                                       int pageSize) {
        position = "%" + position + "%";
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_brand
                                          INNER JOIN t_brand ON t_project_brand.t_brand_id = t_brand.id
                                          WHERE t_project_brand.t_project_id=? 
                                          AND t_brand.position LIKE ?
                                          AND t_project_brand.deleted_at IS NULL
                                          AND t_brand.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectBrandMapper(), projectId, position, pageNo * pageSize, pageSize);
    }


    /**
     * 根据外键（t_project_id和名字）+获得指定页面数据
     *
     * @param projectId
     * @param brandName
     * @param pageNo    页号，从1开始
     * @param pageSize  每页的记录数
     */
    private List<ProjectBrand> getPageQueryByProjectIdAndBrandName(String projectId,
                                                       String brandName,
                                                       int pageNo,
                                                       int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_brand
                                          INNER JOIN t_brand ON t_project_brand.t_brand_id = t_brand.id
                                          WHERE t_project_brand.t_project_id=? 
                                          AND t_brand.name LIKE ?
                                          AND t_project_brand.deleted_at IS NULL
                                          AND t_brand.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectBrandMapper(), projectId,brandName, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_brand_id）+获得指定页面数据
     *
     * @param brandId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<ProjectBrand> getPageQueryByBrandId(String brandId,
                                                     int pageNo,
                                                     int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_brand
                                          WHERE t_brand_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectBrandMapper(), brandId, pageNo * pageSize, pageSize);
    }

    @Override
    public List<Brandexcel> getExcelListByProjectId(String projectId) {

        return jdbcTemplate.query("""
                                SELECT
                mcd.name                                  AS division_name,
                mcg.name                                  AS group_name,
                mcs.name                                  AS section_name,
                b.position,
                b.name                                    AS brand_name,
                c.name                                    AS company_name
                FROM t_project_brand AS tpb
                LEFT JOIN t_brand AS b ON tpb.t_brand_id = b.id
                LEFT JOIN t_company               AS c   ON b.factory_id = c.id
                LEFT JOIN t_material_classify_section AS mcs ON b.t_material_classify_section_id = mcs.id
                LEFT JOIN t_material_classify_group   AS mcg ON mcs.t_material_classify_group_id = mcg.id
                LEFT JOIN t_material_classify_division AS mcd ON mcg.t_material_classify_division_id = mcd.id
                WHERE tpb.deleted_at IS NULL AND b.deleted_at IS NULL AND tpb.t_project_id = ?
          """,
                new ProjectBrandRepositoryImpl.PrivateBrandExcelMapper(), projectId);
    }

    /**
     * RowMapper
     */
    private static final class ProjectBrandMapper implements RowMapper<ProjectBrand> {
        @Override
        public ProjectBrand mapRow(ResultSet rs,
                                   int rowNum) throws SQLException {
            ProjectBrand projectBrand = new ProjectBrand();
            projectBrand.setId(rs.getString("id"));
            projectBrand.setProjectId(rs.getString("t_project_id"));
            projectBrand.setBrandId(rs.getString("t_brand_id"));
            projectBrand.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectBrand;
        }
    }

    private static final class PrivateBrandExcelMapper implements RowMapper<Brandexcel> {
        @Override
        public Brandexcel mapRow(ResultSet rs,
                                 int rowNum) throws SQLException {
            Brandexcel brandexcel = new Brandexcel();
            brandexcel.setMaterialsdiv(rs.getString("division_name"));
            brandexcel.setMaterialsgroup(rs.getString("group_name"));
            brandexcel.setMaterialssection(rs.getString("section_name"));
            brandexcel.setPosition(rs.getString("position"));
            brandexcel.setName(rs.getString("brand_name"));
            brandexcel.setFactory_id(rs.getString("company_name"));
            return brandexcel;
        }
    }

}