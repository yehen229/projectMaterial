package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Brand;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IBrandRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class BrandRepositoryImpl implements IBrandRepository {
    private final JdbcTemplate jdbcTemplate;

    public BrandRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(Brand brand) {
        System.out.println(brand.getFactory_id());
        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_brand(id,
                                        name,
                                        t_material_classify_section_id,
                                        position,
                                        factory_id)
                                        VALUES(?,?,?,?,?)
                                        """,
                                newId,
                                brand.getName(),
                                brand.getMaterialClassifySectionId(),
                                brand.getPosition(),
                                brand.getFactory_id()) > 0)
            return newId;
        return null;
    }

    /**
     * insert
     */
    @Override
    public String addAndGetId(Brand brand) {
        System.out.println(brand.getFactory_id());
        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_brand(id,
                                        name,
                                        t_material_classify_section_id,
                                        position,
                                        factory_id)
                                        VALUES(?,?,?,?,?)
                                        """,
                newId,
                brand.getName(),
                brand.getMaterialClassifySectionId(),
                brand.getPosition(),
                brand.getFactory_id()) > 0) {
            System.out.println(newId);
            return newId;
        }
            return null;
    }

    /**
     * update
     */
    @Override
    public int update(Brand brand) {
        return jdbcTemplate.update("""
                                           UPDATE t_brand
                                           SET name=?,
                                           t_material_classify_section_id=?,
                                           position=?,
                                           factory_id=?
                                           WHERE id=?
                                           """,
                                   brand.getName(),
                                   brand.getMaterialClassifySectionId(),
                                   brand.getPosition(),
                                   brand.getFactory_id(),
                                   brand.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_brand
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);
    }

    /**
     * delete
     */
    @Override
    public int delete(Brand brand) {
        if (brand == null) return 0;

        return jdbcTemplate.update("""
                                           UPDATE t_brand
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   brand.getId());

    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_brand
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public Brand getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_brand 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_brand
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new BrandMapper(), id);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<Brand> getPage(int pageNo,
                               int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Brand> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Brand getByNameAndMaterialClassifySectionIdAndPosition(String brandName,
                                                                  String materialClassifySectionId,
                                                                  String position) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_brand 
                                                        WHERE name=? AND t_material_classify_section_id=? AND position=? AND deleted_at IS NULL
                                                        """, Integer.class, brandName, materialClassifySectionId,
                                                position);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_brand
                                                   WHERE name=? AND t_material_classify_section_id=? AND position=? AND deleted_at IS NULL
                                                   """,
                                           new BrandMapper(), brandName, materialClassifySectionId, position);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<Brand> getPageQuery(int pageNo,
                                     int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_brand
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new BrandMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class BrandMapper implements RowMapper<Brand> {
        @Override
        public Brand mapRow(ResultSet rs,
                            int rowNum) throws SQLException {
            Brand brand = new Brand();
            brand.setId(rs.getString("id"));
            brand.setName(rs.getString("name"));
            brand.setMaterialClassifySectionId(rs.getString("t_material_classify_section_id"));
            brand.setPosition(rs.getString("position"));
            brand.setDeletedAt(rs.getTimestamp("deleted_at"));
            brand.setFactory_id(rs.getString("factory_id"));
            return brand;
        }
    }

}