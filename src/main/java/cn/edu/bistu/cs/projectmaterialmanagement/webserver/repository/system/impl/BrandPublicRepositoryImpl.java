package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.BrandPublic;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IBrandPublicRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class BrandPublicRepositoryImpl implements IBrandPublicRepository {
    private final JdbcTemplate jdbcTemplate;

    public BrandPublicRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(BrandPublic brandPublic) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_brand_public(id,
                                        t_brand_id)
                                        VALUES(?,?)
                                        """,
                                newId,
                                brandPublic.getBrandId()) > 0)
            return newId;
        return null;
    }

    /**
     * update
     */
    @Override
    public int update(BrandPublic brandPublic) {
        return jdbcTemplate.update("""
                                           UPDATE t_brand_public
                                           SET t_brand_id=?
                                           WHERE id=?
                                           """,
                                   brandPublic.getBrandId(),
                                   brandPublic.getId());
    }

    /**
     * 根据id删除记录
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_brand_public
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);

    }

    /**
     * 根据brandId删除记录
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int deleteByBrandId(String brandId) {
        return jdbcTemplate.update("""
                                           UPDATE t_brand_public
                                           SET deleted_at=? 
                                           WHERE t_brand_id=?
                                           """,
                                   new Date(),
                                   brandId);


    }

    /**
     * delete
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int delete(BrandPublic brandPublic) {
        if (brandPublic == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_brand_public
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """, new Date(),
                                   brandPublic.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_brand_public
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键BrandId得到总数量
     */
    @Override
    public int getCountByBrandId(String brandId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_brand_public
                                                        WHERE t_brand_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, brandId);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByBrandName(String brandName) {
        brandName = "%" + brandName + "%";
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_brand_public
                                                        LEFT JOIN t_brand ON t_brand_public.t_brand_id = t_brand.id
                                                        WHERE name LIKE ? AND t_brand.deleted_at IS NULL AND t_brand_public.deleted_at IS NULL
                                                        """,
                                                Integer.class, brandName);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByBrandPosition(String brandPosition) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_brand_public
                                                        LEFT JOIN t_brand ON t_brand_public.t_brand_id = t_brand.id
                                                        WHERE position=? AND t_brand.deleted_at IS NULL AND t_brand_public.deleted_at IS NULL
                                                        """,
                                                Integer.class, brandPosition);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public BrandPublic getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_brand_public 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_brand_public
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new BrandPublicMapper(), id);
    }

    /**
     * 根据brandId得到记录
     */
    @Override
    public List<BrandPublic> getByBrandId(String brandId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_brand_public
                                                        WHERE t_brand_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, brandId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_brand_public 
                                          WHERE t_brand_id=? AND deleted_at IS NULL
                                          """,
                                  new BrandPublicMapper(), brandId);
    }

    @Override
    public List<BrandPublic> getByMaterialClassifySectionId(String materialClassifySectionId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_brand_public
                                                        LEFT JOIN t_brand on t_brand.id = t_brand_public.t_brand_id
                                                        WHERE t_material_classify_section_id=? 
                                                            AND t_brand_public.deleted_at IS NULL 
                                                            AND t_brand.deleted_at IS NULL
                                                        """,
                                                Integer.class, materialClassifySectionId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_brand_public 
                                          LEFT JOIN t_brand on t_brand.id = t_brand_public.t_brand_id
                                          WHERE t_material_classify_section_id=? 
                                              AND t_brand_public.deleted_at IS NULL 
                                              AND t_brand.deleted_at IS NULL
                                          """,
                                  new BrandPublicMapper(), materialClassifySectionId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<BrandPublic> getPage(int pageNo,
                                     int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<BrandPublic> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<BrandPublic> getPageByBrandId(String brandId,
                                              int pageNo,
                                              int pageSize) {
        long totalCount = getCountByBrandId(brandId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<BrandPublic> resultData = getPageQueryByBrandId(brandId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<BrandPublic> getPageByBrandName(String brandName,
                                                int pageNo,
                                                int pageSize) {
        long totalCount = getCountByBrandName(brandName);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<BrandPublic> resultData = getPageQueryByBrandName(brandName, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<BrandPublic> getPageByBrandPosition(String brandPosition,
                                                    int pageNo,
                                                    int pageSize) {
        long totalCount = getCountByBrandPosition(brandPosition);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<BrandPublic> resultData = getPageQueryByBrandPosition(brandPosition, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    private List<BrandPublic> getPageQueryByBrandName(String brandName,
                                                      int pageNo,
                                                      int pageSize) {
        brandName = "%" + brandName + "%";
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_brand_public
                                          LEFT JOIN t_brand ON t_brand_public.t_brand_id = t_brand.id
                                          WHERE name LIKE ? AND t_brand.deleted_at IS NULL AND t_brand_public.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new BrandPublicMapper(), brandName, pageNo * pageSize, pageSize);
    }

    private List<BrandPublic> getPageQueryByBrandPosition(String brandPosition,
                                                          int pageNo,
                                                          int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_brand_public
                                          LEFT JOIN t_brand ON t_brand_public.t_brand_id = t_brand.id
                                          WHERE position = ? AND t_brand.deleted_at IS NULL AND t_brand_public.deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new BrandPublicMapper(), brandPosition, pageNo * pageSize, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<BrandPublic> getPageQuery(int pageNo,
                                           int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_brand_public
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new BrandPublicMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_brand_id）+获得指定页面数据
     *
     * @param brandId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<BrandPublic> getPageQueryByBrandId(String brandId,
                                                    int pageNo,
                                                    int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_brand_public
                                          WHERE t_brand_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new BrandPublicMapper(), brandId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class BrandPublicMapper implements RowMapper<BrandPublic> {
        @Override
        public BrandPublic mapRow(ResultSet rs,
                                  int rowNum) throws SQLException {
            BrandPublic brandPublic = new BrandPublic();
            brandPublic.setId(rs.getString("id"));
            brandPublic.setBrandId(rs.getString("t_brand_id"));
            brandPublic.setDeletedAt(rs.getTimestamp("deleted_at"));
            return brandPublic;
        }
    }

}