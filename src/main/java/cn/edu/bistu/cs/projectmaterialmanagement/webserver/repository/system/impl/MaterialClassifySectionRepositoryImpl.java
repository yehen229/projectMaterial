package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifySection;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IMaterialClassifySectionRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class MaterialClassifySectionRepositoryImpl implements IMaterialClassifySectionRepository {
    private final JdbcTemplate jdbcTemplate;

    public MaterialClassifySectionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(MaterialClassifySection materialClassifySection) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_material_classify_section(id,
                                        name,
                                        note,
                                        t_material_classify_group_id
                                        )
                                        VALUES(?,?,?,?)
                                        """,
                                newId,
                                materialClassifySection.getName(),
                                materialClassifySection.getNote(),
                                materialClassifySection.getMaterialClassifyGroupId()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(MaterialClassifySection materialClassifySection) {
        if (materialClassifySection == null) return 0;

        return jdbcTemplate.update("""
                                           UPDATE t_material_classify_section
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   materialClassifySection.getId());

    }

    /**
     * update
     */
    @Override
    public int update(MaterialClassifySection materialClassifySection) {
        return jdbcTemplate.update("""
                                           UPDATE t_material_classify_section
                                           SET name=?,
                                           note=?,
                                           t_material_classify_group_id=?
                                           WHERE id=?
                                           """,
                                   materialClassifySection.getName(),
                                   materialClassifySection.getNote(),
                                   materialClassifySection.getMaterialClassifyGroupId(),
                                   materialClassifySection.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_material_classify_section
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);


    }

    /**
     * 根据materialClassifyGroupId删除记录
     */
    @Override
    public int deleteByMaterialClassifyGroupId(String materialClassifyGroupId) {
        return jdbcTemplate.update("""
                                           DELETE FROM t_material_classify_section 
                                           WHERE t_material_classify_group_id=?
                                           """,
                                   materialClassifyGroupId);
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                                          SELECT count(*) 
                                                                          FROM t_material_classify_section
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键MaterialClassifyGroupId得到总数量
     */
    @Override
    public int getCountByMaterialClassifyGroupId(String materialClassifyGroupId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material_classify_section
                                                        WHERE t_material_classify_group_id=? AND  deleted_at IS NULL
                                                        """,
                                                Integer.class, materialClassifyGroupId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public MaterialClassifySection getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material_classify_section 
                                                        WHERE id=? AND  deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_material_classify_section
                                                   WHERE id=? AND  deleted_at IS NULL
                                                   """,
                                           new MaterialClassifySectionMapper(), id);
    }

    /**
     * 根据materialClassifyGroupId得到记录
     */
    @Override
    public List<MaterialClassifySection> getByMaterialClassifyGroupId(String materialClassifyGroupId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material_classify_section
                                                        WHERE t_material_classify_group_id=? AND  deleted_at IS NULL
                                                        """,
                                                Integer.class, materialClassifyGroupId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material_classify_section 
                                          WHERE t_material_classify_group_id=? AND  deleted_at IS NULL
                                          """,
                                  new MaterialClassifySectionMapper(), materialClassifyGroupId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<MaterialClassifySection> getPage(int pageNo,
                                                 int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<MaterialClassifySection> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<MaterialClassifySection> getPageByMaterialClassifyGroupId(String materialClassifyGroupId,
                                                                          int pageNo,
                                                                          int pageSize) {
        long totalCount = getCountByMaterialClassifyGroupId(materialClassifyGroupId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<MaterialClassifySection> resultData = getPageQueryByMaterialClassifyGroupId(materialClassifyGroupId,
                                                                                         pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<MaterialClassifySection> getPageQuery(int pageNo,
                                                       int pageSize) {
        return jdbcTemplate.query("""
                                                            SELECT * 
                                                            FROM t_material_classify_section
                                          WHERE deleted_at IS NULL
                                                            LIMIT ?,?
                                          """,
                                  new MaterialClassifySectionMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_material_classify_group_id）+获得指定页面数据
     *
     * @param materialClassifyGroupId
     * @param pageNo                  页号，从1开始
     * @param pageSize                每页的记录数
     */
    private List<MaterialClassifySection> getPageQueryByMaterialClassifyGroupId(String materialClassifyGroupId,
                                                                                int pageNo,
                                                                                int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material_classify_section
                                          WHERE t_material_classify_group_id=? AND  deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new MaterialClassifySectionMapper(), materialClassifyGroupId, pageNo * pageSize,
                                  pageSize);
    }

    /**
     * RowMapper
     */
    private static final class MaterialClassifySectionMapper implements RowMapper<MaterialClassifySection> {
        @Override
        public MaterialClassifySection mapRow(ResultSet rs,
                                              int rowNum) throws SQLException {
            MaterialClassifySection materialClassifySection = new MaterialClassifySection();
            materialClassifySection.setId(rs.getString("id"));
            materialClassifySection.setName(rs.getString("name"));
            materialClassifySection.setNote(rs.getString("note"));
            materialClassifySection.setMaterialClassifyGroupId(rs.getString("t_material_classify_group_id"));
            materialClassifySection.setDeletedAt(rs.getTimestamp("deleted_at"));
            return materialClassifySection;
        }
    }

}