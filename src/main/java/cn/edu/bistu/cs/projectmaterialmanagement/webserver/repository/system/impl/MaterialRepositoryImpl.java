package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Material;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IMaterialRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class MaterialRepositoryImpl implements IMaterialRepository {
    private final JdbcTemplate jdbcTemplate;

    public MaterialRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(Material material) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_material(id,
                                        t_material_classify_section_id,
                                        name,
                                        item_mark,
                                        location,
                                        technology,
                                        material,
                                        color,
                                        dimension,
                                        fire_rating,
                                        installation)
                                        VALUES(?,?,?,?,?,?,?,?,?,?,?)
                                        """,
                                newId,
                                material.getMaterialClassifySectionId(),
                                material.getName(),
                                material.getItemMark(),
                                material.getLocation(),
                                material.getTechnology(),
                                material.getMaterial(),
                                material.getColor(),
                                material.getDimension(),
                                material.getFireRating(),
                                material.getInstallation()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(Material material) {
        if (material == null) return 0;

        return jdbcTemplate.update("""
                                           UPDATE t_material
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   material.getId());


    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_material
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);
    }

    /**
     * update
     */
    @Override
    public int update(Material material) {
        return jdbcTemplate.update("""
                                           UPDATE t_material
                                           SET
                                           t_material_classify_section_id=?,
                                           name=?,
                                           item_mark=?,
                                           location=?,
                                           technology=?,
                                           material=?,
                                           color=?,
                                           dimension=?,
                                           fire_rating=?,
                                           installation=?
                                           WHERE id=?
                                           """,
                                   material.getMaterialClassifySectionId(),
                                   material.getName(),
                                   material.getItemMark(),
                                   material.getLocation(),
                                   material.getTechnology(),
                                   material.getMaterial(),
                                   material.getColor(),
                                   material.getDimension(),
                                   material.getFireRating(),
                                   material.getInstallation(),
                                   material.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByMaterialClassifySectionId(String materialClassifySectionId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material
                                                        WHERE t_material_classify_section_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, materialClassifySectionId);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByLikeItemMark(String itemMark) {
        itemMark = "%" + itemMark + "%";
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material
                                                        WHERE item_mark LIKE ? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, itemMark);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByLikeLocation(String location) {
        location = "%" + location + "%";
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material
                                                        WHERE location LIKE ? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, location);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByLikeName(String name) {
        name = "%" + name + "%";
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material
                                                        WHERE name LIKE ? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, name);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public Material getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_material
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new MaterialMapper(), id);
    }

    @Override
    public List<Material> getByMaterialClassifySectionId(String materialClassifySectionId) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material
                                          WHERE t_material_classify_section_id=? AND deleted_at IS NULL
                                          
                                          """,
                                  new MaterialMapper(), materialClassifySectionId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<Material> getPage(int pageNo,
                                  int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Material> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<Material> getPageByMaterialClassifySectionId(String materialClassifySectionId,
                                                             int pageNo,
                                                             int pageSize) {
        long totalCount = getCountByMaterialClassifySectionId(materialClassifySectionId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Material> resultData = getPageQueryByMaterialClassifySectionId(materialClassifySectionId, pageNo - 1,
                                                                            pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }


    @Override
    public Page<Material> getPageByItemMark(String itemMark,
                                            Integer pageNo,
                                            Integer pageSize) {
        long totalCount = getCountByLikeItemMark(itemMark);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Material> resultData = getPageQueryByItemMark(itemMark, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<Material> getPageByLocation(String location,
                                            Integer pageNo,
                                            Integer pageSize) {
        long totalCount = getCountByLikeLocation(location);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Material> resultData = getPageQueryByLocation(location, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<Material> getPageByName(String name,
                                        Integer pageNo,
                                        Integer pageSize) {
        long totalCount = getCountByLikeName(name);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Material> resultData = getPageQueryByName(name, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<Material> getPageQuery(int pageNo,
                                        int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new MaterialMapper(), pageNo * pageSize, pageSize);
    }

    private List<Material> getPageQueryByMaterialClassifySectionId(String materialClassifySectionId,
                                                                   int pageNo,
                                                                   int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material
                                          WHERE t_material_classify_section_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new MaterialMapper(), materialClassifySectionId, pageNo * pageSize, pageSize);
    }

    private List<Material> getPageQueryByItemMark(String itemMark,
                                                  int pageNo,
                                                  int pageSize) {
        itemMark = "%" + itemMark + "%";
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material
                                          WHERE item_mark LIKE ? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new MaterialMapper(), itemMark, pageNo * pageSize, pageSize);
    }

    private List<Material> getPageQueryByLocation(String location,
                                                  int pageNo,
                                                  int pageSize) {
        location = "%" + location + "%";
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material
                                          WHERE location LIKE ? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new MaterialMapper(), location, pageNo * pageSize, pageSize);
    }

    private List<Material> getPageQueryByName(String name,
                                              int pageNo,
                                              int pageSize) {
        name = "%" + name + "%";
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material
                                          WHERE name LIKE ? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new MaterialMapper(), name, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class MaterialMapper implements RowMapper<Material> {
        @Override
        public Material mapRow(ResultSet rs,
                               int rowNum) throws SQLException {
            Material material = new Material();
            material.setId(rs.getString("id"));
            material.setMaterialClassifySectionId(rs.getString("t_material_classify_section_id"));
            material.setName(rs.getString("name"));
            material.setItemMark(rs.getString("item_mark"));
            material.setLocation(rs.getString("location"));
            material.setMaterial(rs.getString("material"));
            material.setColor(rs.getString("color"));
            material.setDimension(rs.getString("dimension"));
            material.setFireRating(rs.getString("fire_rating"));
            material.setInstallation(rs.getString("installation"));
            material.setTechnology(rs.getString("technology"));
            material.setDeletedAt(rs.getTimestamp("deleted_at"));

            return material;
        }
    }

}