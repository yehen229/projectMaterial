package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialNewBrand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IUseMaterialNewBrandRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class UseMaterialNewBrandRepositoryImpl implements IUseMaterialNewBrandRepository {
    private final JdbcTemplate jdbcTemplate;

    public UseMaterialNewBrandRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(UseMaterialNewBrand useMaterialNewBrand) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_use_material_new_brand(id,
                                        t_use_material_id,
                                        brand_name,
                                        t_material_classify_division_id,
                                        t_material_classify_group_id,
                                        t_material_classify_section_id,
                                        t_project_material_brand_private_id,
                                        material_position)
                                        VALUES(?,?,?,?,?,?,?,?)
                                        """,
                                newId,
                                useMaterialNewBrand.getUseMaterialId(),
                                useMaterialNewBrand.getBrandName(),
                                useMaterialNewBrand.getMaterialClassifyDivisionId(),
                                useMaterialNewBrand.getMaterialClassifyGroupId(),
                                useMaterialNewBrand.getMaterialClassifySectionId(),
                                useMaterialNewBrand.getProjectMaterialBrandPrivateId(),
                                useMaterialNewBrand.getMaterialPosition()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(UseMaterialNewBrand useMaterialNewBrand) {
        if (useMaterialNewBrand == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_use_material_new_brand
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   useMaterialNewBrand.getId());


    }

    /**
     * update
     */
    @Override
    public int update(UseMaterialNewBrand useMaterialNewBrand) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material_new_brand
                                           SET t_use_material_id=?,
                                           brand_name=?,
                                           t_material_classify_division_id=?,
                                           t_material_classify_group_id=?,
                                           t_material_classify_section_id=?,
                                           t_project_material_brand_private_id=?,
                                           material_position=?
                                           WHERE id=?
                                           """,
                                   useMaterialNewBrand.getUseMaterialId(),
                                   useMaterialNewBrand.getBrandName(),
                                   useMaterialNewBrand.getMaterialClassifyDivisionId(),
                                   useMaterialNewBrand.getMaterialClassifyGroupId(),
                                   useMaterialNewBrand.getMaterialClassifySectionId(),
                                   useMaterialNewBrand.getProjectMaterialBrandPrivateId(),
                                   useMaterialNewBrand.getMaterialPosition(),
                                   useMaterialNewBrand.getId());
    }

    @Override
    public int updateProjectMaterialBrandPrivateId(String id,
                                                   String projectMaterialBrandPrivateId) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material_new_brand
                                           SET  t_project_material_brand_private_id=?
                                           WHERE id=?
                                           """,
                                   projectMaterialBrandPrivateId,
                                   id);
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material_new_brand
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);
    }

    /**
     * 根据useMaterialId删除记录
     */
    @Override
    public int deleteByUseMaterialId(String useMaterialId) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material_new_brand
                                           SET deleted_at=?  
                                           WHERE t_use_material_id=?
                                           """,
                                   new Date(), useMaterialId);
    }

    /**
     * 根据materialClassifyDivisionId删除记录
     */
    @Override
    public int deleteByMaterialClassifyDivisionId(String materialClassifyDivisionId) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material_new_brand
                                           SET deleted_at=?  
                                           WHERE t_material_classify_division_id=?
                                           """,
                                   new Date(), materialClassifyDivisionId);
    }

    /**
     * 根据materialClassifyGroupId删除记录
     */
    @Override
    public int deleteByMaterialClassifyGroupId(String materialClassifyGroupId) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material_new_brand
                                           SET deleted_at=?  
                                           WHERE t_material_classify_group_id=?
                                           """,
                                   new Date(), materialClassifyGroupId);
    }

    /**
     * 根据materialClassifySectionId删除记录
     */
    @Override
    public int deleteByMaterialClassifySectionId(String materialClassifySectionId) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material_new_brand
                                            SET deleted_at=?  
                                            WHERE t_material_classify_section_id=?
                                           """,
                                   new Date(), materialClassifySectionId);
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                                          SELECT count(*) 
                                                                          FROM t_use_material_new_brand
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键UseMaterialId得到总数量
     */
    @Override
    public int getCountByUseMaterialId(String useMaterialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material_new_brand
                                                        WHERE t_use_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, useMaterialId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键MaterialClassifyDivisionId得到总数量
     */
    @Override
    public int getCountByMaterialClassifyDivisionId(String materialClassifyDivisionId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material_new_brand
                                                        WHERE t_material_classify_division_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, materialClassifyDivisionId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键MaterialClassifyGroupId得到总数量
     */
    @Override
    public int getCountByMaterialClassifyGroupId(String materialClassifyGroupId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material_new_brand
                                                        WHERE t_material_classify_group_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, materialClassifyGroupId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键MaterialClassifySectionId得到总数量
     */
    @Override
    public int getCountByMaterialClassifySectionId(String materialClassifySectionId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material_new_brand
                                                        WHERE t_material_classify_section_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, materialClassifySectionId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public UseMaterialNewBrand getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material_new_brand 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_use_material_new_brand
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new UseMaterialNewBrandMapper(), id);
    }

    /**
     * 根据useMaterialId得到记录
     */
    @Override
    public List<UseMaterialNewBrand> getByUseMaterialId(String useMaterialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material_new_brand
                                                        WHERE t_use_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, useMaterialId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material_new_brand 
                                          WHERE t_use_material_id=? AND deleted_at IS NULL
                                          """,
                                  new UseMaterialNewBrandMapper(), useMaterialId);
    }

    /**
     * 根据materialClassifyDivisionId得到记录
     */
    @Override
    public List<UseMaterialNewBrand> getByMaterialClassifyDivisionId(String materialClassifyDivisionId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material_new_brand
                                                        WHERE t_material_classify_division_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, materialClassifyDivisionId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material_new_brand 
                                          WHERE t_material_classify_division_id=? AND deleted_at IS NULL
                                          """,
                                  new UseMaterialNewBrandMapper(), materialClassifyDivisionId);
    }

    /**
     * 根据materialClassifyGroupId得到记录
     */
    @Override
    public List<UseMaterialNewBrand> getByMaterialClassifyGroupId(String materialClassifyGroupId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material_new_brand
                                                        WHERE t_material_classify_group_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, materialClassifyGroupId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material_new_brand 
                                          WHERE t_material_classify_group_id=? AND deleted_at IS NULL
                                          """,
                                  new UseMaterialNewBrandMapper(), materialClassifyGroupId);
    }

    /**
     * 根据materialClassifySectionId得到记录
     */
    @Override
    public List<UseMaterialNewBrand> getByMaterialClassifySectionId(String materialClassifySectionId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material_new_brand
                                                        WHERE t_material_classify_section_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, materialClassifySectionId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material_new_brand 
                                          WHERE t_material_classify_section_id=? AND deleted_at IS NULL
                                          """,
                                  new UseMaterialNewBrandMapper(), materialClassifySectionId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrand> getPage(int pageNo,
                                             int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterialNewBrand> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param useMaterialId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrand> getPageByUseMaterialId(String useMaterialId,
                                                            int pageNo,
                                                            int pageSize) {
        long totalCount = getCountByUseMaterialId(useMaterialId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterialNewBrand> resultData = getPageQueryByUseMaterialId(useMaterialId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param materialClassifyDivisionId
     * @param pageNo                     页号，从1开始
     * @param pageSize                   每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrand> getPageByMaterialClassifyDivisionId(String materialClassifyDivisionId,
                                                                         int pageNo,
                                                                         int pageSize) {
        long totalCount = getCountByMaterialClassifyDivisionId(materialClassifyDivisionId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterialNewBrand> resultData = getPageQueryByMaterialClassifyDivisionId(materialClassifyDivisionId,
                                                                                        pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param materialClassifyGroupId
     * @param pageNo                  页号，从1开始
     * @param pageSize                每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrand> getPageByMaterialClassifyGroupId(String materialClassifyGroupId,
                                                                      int pageNo,
                                                                      int pageSize) {
        long totalCount = getCountByMaterialClassifyGroupId(materialClassifyGroupId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterialNewBrand> resultData = getPageQueryByMaterialClassifyGroupId(materialClassifyGroupId,
                                                                                     pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param materialClassifySectionId
     * @param pageNo                    页号，从1开始
     * @param pageSize                  每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrand> getPageByMaterialClassifySectionId(String materialClassifySectionId,
                                                                        int pageNo,
                                                                        int pageSize) {
        long totalCount = getCountByMaterialClassifySectionId(materialClassifySectionId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterialNewBrand> resultData = getPageQueryByMaterialClassifySectionId(materialClassifySectionId,
                                                                                       pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<UseMaterialNewBrand> getPageQuery(int pageNo,
                                                   int pageSize) {
        return jdbcTemplate.query("""
                                                            SELECT * 
                                                            FROM t_use_material_new_brand
                                          WHERE deleted_at IS NULL
                                                            LIMIT ?,?
                                          """,
                                  new UseMaterialNewBrandMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_use_material_id）+获得指定页面数据
     *
     * @param useMaterialId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    private List<UseMaterialNewBrand> getPageQueryByUseMaterialId(String useMaterialId,
                                                                  int pageNo,
                                                                  int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material_new_brand
                                          WHERE t_use_material_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new UseMaterialNewBrandMapper(), useMaterialId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_material_classify_division_id）+获得指定页面数据
     *
     * @param materialClassifyDivisionId
     * @param pageNo                     页号，从1开始
     * @param pageSize                   每页的记录数
     */
    private List<UseMaterialNewBrand> getPageQueryByMaterialClassifyDivisionId(String materialClassifyDivisionId,
                                                                               int pageNo,
                                                                               int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material_new_brand
                                          WHERE t_material_classify_division_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new UseMaterialNewBrandMapper(), materialClassifyDivisionId, pageNo * pageSize,
                                  pageSize);
    }

    /**
     * 根据外键（t_material_classify_group_id）+获得指定页面数据
     *
     * @param materialClassifyGroupId
     * @param pageNo                  页号，从1开始
     * @param pageSize                每页的记录数
     */
    private List<UseMaterialNewBrand> getPageQueryByMaterialClassifyGroupId(String materialClassifyGroupId,
                                                                            int pageNo,
                                                                            int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material_new_brand
                                          WHERE t_material_classify_group_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new UseMaterialNewBrandMapper(), materialClassifyGroupId, pageNo * pageSize,
                                  pageSize);
    }

    /**
     * 根据外键（t_material_classify_section_id）+获得指定页面数据
     *
     * @param materialClassifySectionId
     * @param pageNo                    页号，从1开始
     * @param pageSize                  每页的记录数
     */
    private List<UseMaterialNewBrand> getPageQueryByMaterialClassifySectionId(String materialClassifySectionId,
                                                                              int pageNo,
                                                                              int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material_new_brand
                                          WHERE t_material_classify_section_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new UseMaterialNewBrandMapper(), materialClassifySectionId, pageNo * pageSize,
                                  pageSize);
    }

    /**
     * RowMapper
     */
    private static final class UseMaterialNewBrandMapper implements RowMapper<UseMaterialNewBrand> {
        @Override
        public UseMaterialNewBrand mapRow(ResultSet rs,
                                          int rowNum) throws SQLException {
            UseMaterialNewBrand useMaterialNewBrand = new UseMaterialNewBrand();
            useMaterialNewBrand.setId(rs.getString("id"));
            useMaterialNewBrand.setUseMaterialId(rs.getString("t_use_material_id"));
            useMaterialNewBrand.setBrandName(rs.getString("brand_name"));
            useMaterialNewBrand.setMaterialClassifyDivisionId(rs.getString("t_material_classify_division_id"));
            useMaterialNewBrand.setMaterialClassifyGroupId(rs.getString("t_material_classify_group_id"));
            useMaterialNewBrand.setMaterialClassifySectionId(rs.getString("t_material_classify_section_id"));
            useMaterialNewBrand.setProjectMaterialBrandPrivateId(rs.getString("t_project_material_brand_private_id"));
            useMaterialNewBrand.setMaterialPosition(rs.getString("material_position"));
            useMaterialNewBrand.setDeletedAt(rs.getTimestamp("deleted_at"));
            return useMaterialNewBrand;
        }
    }

}