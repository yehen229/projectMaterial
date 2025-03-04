package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialBrand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IMaterialBrandRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class MaterialBrandRepositoryImpl implements IMaterialBrandRepository {
    private final JdbcTemplate jdbcTemplate;

    public MaterialBrandRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(MaterialBrand materialBrand) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_material_brand(id,
                                        t_material_id,
                                        t_brand_id)
                                        VALUES(?,?,?)
                                        """,
                                newId,
                                materialBrand.getMaterialId(),
                                materialBrand.getBrandId()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(MaterialBrand materialBrand) {
        if (materialBrand == null) return 0;

        return jdbcTemplate.update("""
                                           UPDATE t_material_brand
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   materialBrand.getId());

    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_material_brand
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);

    }

    /**
     * 根据materialId删除记录
     */
    @Override
    public int deleteByMaterialId(String materialId) {

        return jdbcTemplate.update("""
                                           UPDATE t_material_brand
                                           SET deleted_at=? 
                                           WHERE t_material_id=?
                                           """,
                                   new Date(),
                                   materialId);

    }

    /**
     * 根据brandId删除记录
     */
    @Override
    public int deleteByBrandId(String brandId) {

        return jdbcTemplate.update("""
                                           UPDATE t_material_brand
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
    public int update(MaterialBrand materialBrand) {
        return jdbcTemplate.update("""
                                           UPDATE t_material_brand
                                           SET t_material_id=?,
                                           t_brand_id=?
                                           WHERE id=?
                                           """,
                                   materialBrand.getMaterialId(),
                                   materialBrand.getBrandId(),
                                   materialBrand.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material_brand
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键MaterialId得到总数量
     */
    @Override
    public int getCountByMaterialId(String materialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material_brand
                                                        WHERE t_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, materialId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键BrandId得到总数量
     */
    @Override
    public int getCountByBrandId(String brandId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material_brand
                                                        WHERE t_brand_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, brandId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public MaterialBrand getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material_brand 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_material_brand
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new MaterialBrandMapper(), id);
    }

    /**
     * 根据materialId得到记录
     */
    @Override
    public List<MaterialBrand> getByMaterialId(String materialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material_brand
                                                        WHERE t_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, materialId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material_brand 
                                          WHERE t_material_id=? AND deleted_at IS NULL
                                          """,
                                  new MaterialBrandMapper(), materialId);
    }

    /**
     * 根据brandId得到记录
     */
    @Override
    public List<MaterialBrand> getByBrandId(String brandId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material_brand
                                                        WHERE t_brand_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, brandId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material_brand 
                                          WHERE t_brand_id=? AND deleted_at IS NULL
                                          """,
                                  new MaterialBrandMapper(), brandId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<MaterialBrand> getPage(int pageNo,
                                       int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<MaterialBrand> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param materialId
     * @param pageNo     页号，从1开始
     * @param pageSize   每页的记录数
     */
    @Override
    public Page<MaterialBrand> getPageByMaterialId(String materialId,
                                                   int pageNo,
                                                   int pageSize) {
        long totalCount = getCountByMaterialId(materialId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<MaterialBrand> resultData = getPageQueryByMaterialId(materialId, pageNo - 1, pageSize);
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
    public Page<MaterialBrand> getPageByBrandId(String brandId,
                                                int pageNo,
                                                int pageSize) {
        long totalCount = getCountByBrandId(brandId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<MaterialBrand> resultData = getPageQueryByBrandId(brandId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<MaterialBrand> getPageQuery(int pageNo,
                                             int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material_brand
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new MaterialBrandMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_material_id）+获得指定页面数据
     *
     * @param materialId
     * @param pageNo     页号，从1开始
     * @param pageSize   每页的记录数
     */
    private List<MaterialBrand> getPageQueryByMaterialId(String materialId,
                                                         int pageNo,
                                                         int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material_brand
                                          WHERE t_material_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new MaterialBrandMapper(), materialId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_brand_id）+获得指定页面数据
     *
     * @param brandId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<MaterialBrand> getPageQueryByBrandId(String brandId,
                                                      int pageNo,
                                                      int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material_brand
                                          WHERE t_brand_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new MaterialBrandMapper(), brandId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class MaterialBrandMapper implements RowMapper<MaterialBrand> {
        @Override
        public MaterialBrand mapRow(ResultSet rs,
                                    int rowNum) throws SQLException {
            MaterialBrand materialBrand = new MaterialBrand();
            materialBrand.setId(rs.getString("id"));
            materialBrand.setMaterialId(rs.getString("t_material_id"));
            materialBrand.setBrandId(rs.getString("t_brand_id"));
            materialBrand.setDeletedAt(rs.getTimestamp("deleted_at"));
            return materialBrand;
        }
    }

}