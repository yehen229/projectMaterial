package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifyGroup;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IMaterialClassifyGroupRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class MaterialClassifyGroupRepositoryImpl implements IMaterialClassifyGroupRepository {
    private final JdbcTemplate jdbcTemplate;

    public MaterialClassifyGroupRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(MaterialClassifyGroup materialClassifyGroup) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_material_classify_group(id,
                                        name,
                                        note,
                                        t_material_classify_division_id                        )
                                        VALUES(?,?,?,?)
                                        """,
                                newId,
                                materialClassifyGroup.getName(),
                                materialClassifyGroup.getNote(),
                                materialClassifyGroup.getMaterialClassifyDivisionId()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(MaterialClassifyGroup materialClassifyGroup) {
        if (materialClassifyGroup == null) return 0;

        return jdbcTemplate.update("""
                                           UPDATE t_material_classify_group
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   materialClassifyGroup.getId());


    }

    /**
     * update
     */
    @Override
    public int update(MaterialClassifyGroup materialClassifyGroup) {
        return jdbcTemplate.update("""
                                           UPDATE t_material_classify_group
                                           SET name=?,
                                           note=?,
                                           t_material_classify_division_id=?
                                           WHERE id=?
                                           """,
                                   materialClassifyGroup.getName(),
                                   materialClassifyGroup.getNote(),
                                   materialClassifyGroup.getMaterialClassifyDivisionId(),
                                   materialClassifyGroup.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_material_classify_group
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);


    }

    /**
     * 根据materialClassifyDivisionId删除记录
     */
    @Override
    public int deleteByMaterialClassifyDivisionId(String materialClassifyDivisionId) {
        return jdbcTemplate.update("""
                                           DELETE FROM t_material_classify_group 
                                           WHERE t_material_classify_division_id=?
                                           """,
                                   materialClassifyDivisionId);
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material_classify_group
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键MaterialClassifyDivisionId得到总数量
     */
    @Override
    public int getCountByMaterialClassifyDivisionId(String materialClassifyDivisionId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material_classify_group
                                                        WHERE t_material_classify_division_id=? AND  deleted_at IS NULL
                                                        """,
                                                Integer.class, materialClassifyDivisionId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public MaterialClassifyGroup getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material_classify_group 
                                                        WHERE id=? AND  deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_material_classify_group
                                                   WHERE id=?
                                                   """,
                                           new MaterialClassifyGroupMapper(), id);
    }

    /**
     * 根据materialClassifyDivisionId得到记录
     */
    @Override
    public List<MaterialClassifyGroup> getByMaterialClassifyDivisionId(String materialClassifyDivisionId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material_classify_group
                                                        WHERE t_material_classify_division_id=? AND  deleted_at IS NULL
                                                        """,
                                                Integer.class, materialClassifyDivisionId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material_classify_group 
                                          WHERE t_material_classify_division_id=? AND  deleted_at IS NULL
                                          """,
                                  new MaterialClassifyGroupMapper(), materialClassifyDivisionId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<MaterialClassifyGroup> getPage(int pageNo,
                                               int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<MaterialClassifyGroup> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<MaterialClassifyGroup> getPageByMaterialClassifyDivisionId(String materialClassifyDivisionId,
                                                                           int pageNo,
                                                                           int pageSize) {
        long totalCount = getCountByMaterialClassifyDivisionId(materialClassifyDivisionId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<MaterialClassifyGroup> resultData = getPageQueryByMaterialClassifyDivisionId(materialClassifyDivisionId,
                                                                                          pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<MaterialClassifyGroup> getPageQuery(int pageNo,
                                                     int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material_classify_group
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new MaterialClassifyGroupMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_material_classify_division_id）+获得指定页面数据
     *
     * @param materialClassifyDivisionId
     * @param pageNo                     页号，从1开始
     * @param pageSize                   每页的记录数
     */
    private List<MaterialClassifyGroup> getPageQueryByMaterialClassifyDivisionId(String materialClassifyDivisionId,
                                                                                 int pageNo,
                                                                                 int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material_classify_group
                                          WHERE t_material_classify_division_id=? AND  deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new MaterialClassifyGroupMapper(), materialClassifyDivisionId, pageNo * pageSize,
                                  pageSize);
    }

    /**
     * RowMapper
     */
    private static final class MaterialClassifyGroupMapper implements RowMapper<MaterialClassifyGroup> {
        @Override
        public MaterialClassifyGroup mapRow(ResultSet rs,
                                            int rowNum) throws SQLException {
            MaterialClassifyGroup materialClassifyGroup = new MaterialClassifyGroup();
            materialClassifyGroup.setId(rs.getString("id"));
            materialClassifyGroup.setName(rs.getString("name"));
            materialClassifyGroup.setNote(rs.getString("note"));
            materialClassifyGroup.setMaterialClassifyDivisionId(rs.getString("t_material_classify_division_id"));
            materialClassifyGroup.setDeletedAt(rs.getTimestamp("deleted_at"));
            return materialClassifyGroup;
        }
    }

}