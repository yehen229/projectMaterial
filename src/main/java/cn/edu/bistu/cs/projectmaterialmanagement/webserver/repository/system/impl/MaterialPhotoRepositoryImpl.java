package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialPhoto;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IMaterialPhotoRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class MaterialPhotoRepositoryImpl implements IMaterialPhotoRepository {
    private final JdbcTemplate jdbcTemplate;

    public MaterialPhotoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(MaterialPhoto materialPhoto) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_material_photo(id,
                                        t_material_id,
                                        file_path)
                                        VALUES(?,?,?)
                                        """,
                                newId,
                                materialPhoto.getMaterialId(),
                                materialPhoto.getFilePath()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(MaterialPhoto materialPhoto) {
        if (materialPhoto == null) return 0;

        return jdbcTemplate.update("""
                                           UPDATE t_material_photo
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   materialPhoto.getId());


    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_material_photo
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
                                           UPDATE t_material_photo
                                           SET deleted_at=? 
                                           WHERE t_material_id=?
                                           """,
                                   new Date(),
                                   materialId);
    }

    /**
     * update
     */
    @Override
    public int update(MaterialPhoto materialPhoto) {
        return jdbcTemplate.update("""
                                           UPDATE t_material_photo
                                           SET t_material_id=?,
                                           file_path=?
                                           WHERE id=?
                                           """,
                                   materialPhoto.getMaterialId(),
                                   materialPhoto.getFilePath(),
                                   materialPhoto.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material_photo
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
                                                        FROM t_material_photo
                                                        WHERE t_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, materialId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public MaterialPhoto getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material_photo 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_material_photo
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new MaterialPhotoMapper(), id);
    }

    /**
     * 根据materialId得到记录
     */
    @Override
    public List<MaterialPhoto> getByMaterialId(String materialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material_photo
                                                        WHERE t_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, materialId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material_photo 
                                          WHERE t_material_id=? AND deleted_at IS NULL
                                          """,
                                  new MaterialPhotoMapper(), materialId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<MaterialPhoto> getPage(int pageNo,
                                       int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<MaterialPhoto> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<MaterialPhoto> getPageByMaterialId(String materialId,
                                                   int pageNo,
                                                   int pageSize) {
        long totalCount = getCountByMaterialId(materialId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<MaterialPhoto> resultData = getPageQueryByMaterialId(materialId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<MaterialPhoto> getPageQuery(int pageNo,
                                             int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material_photo
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new MaterialPhotoMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_material_id）+获得指定页面数据
     *
     * @param materialId
     * @param pageNo     页号，从1开始
     * @param pageSize   每页的记录数
     */
    private List<MaterialPhoto> getPageQueryByMaterialId(String materialId,
                                                         int pageNo,
                                                         int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material_photo
                                          WHERE t_material_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new MaterialPhotoMapper(), materialId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class MaterialPhotoMapper implements RowMapper<MaterialPhoto> {
        @Override
        public MaterialPhoto mapRow(ResultSet rs,
                                    int rowNum) throws SQLException {
            MaterialPhoto materialPhoto = new MaterialPhoto();
            materialPhoto.setId(rs.getString("id"));
            materialPhoto.setMaterialId(rs.getString("t_material_id"));
            materialPhoto.setFilePath(rs.getString("file_path"));
            materialPhoto.setDeletedAt(rs.getTimestamp("deleted_at"));
            return materialPhoto;
        }
    }

}