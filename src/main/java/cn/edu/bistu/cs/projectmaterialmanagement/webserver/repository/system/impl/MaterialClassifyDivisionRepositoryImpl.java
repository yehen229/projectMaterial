package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.MaterialClassifyDivision;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.system.IMaterialClassifyDivisionRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class MaterialClassifyDivisionRepositoryImpl implements IMaterialClassifyDivisionRepository {
    private final JdbcTemplate jdbcTemplate;

    public MaterialClassifyDivisionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(MaterialClassifyDivision materialClassifyDivision) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_material_classify_division(id,
                                        name,
                                        note
                                        )
                                        VALUES(?,?,?)
                                        """,
                                newId,
                                materialClassifyDivision.getName(),
                                materialClassifyDivision.getNote()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(MaterialClassifyDivision materialClassifyDivision) {
        if (materialClassifyDivision == null) return 0;

        return jdbcTemplate.update("""
                                           UPDATE t_material_classify_division
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   materialClassifyDivision.getId());


    }

    /**
     * update
     */
    @Override
    public int update(MaterialClassifyDivision materialClassifyDivision) {
        return jdbcTemplate.update("""
                                           UPDATE t_material_classify_division
                                           SET name=?,
                                           note=?
                                           WHERE id=?
                                           """,
                                   materialClassifyDivision.getName(),
                                   materialClassifyDivision.getNote(),
                                   materialClassifyDivision.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_material_classify_division
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);

    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material_classify_division
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public MaterialClassifyDivision getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_material_classify_division 
                                                        WHERE id=? AND  deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_material_classify_division
                                                   WHERE id=? AND  deleted_at IS NULL
                                                   """,
                                           new MaterialClassifyDivisionMapper(), id);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<MaterialClassifyDivision> getPage(int pageNo,
                                                  int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<MaterialClassifyDivision> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public List<MaterialClassifyDivision> getAllList() {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material_classify_division
                                          WHERE deleted_at IS NULL
                                          """,
                                  new MaterialClassifyDivisionMapper());
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<MaterialClassifyDivision> getPageQuery(int pageNo,
                                                        int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_material_classify_division
                                          WHERE  deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new MaterialClassifyDivisionMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class MaterialClassifyDivisionMapper implements RowMapper<MaterialClassifyDivision> {
        @Override
        public MaterialClassifyDivision mapRow(ResultSet rs,
                                               int rowNum) throws SQLException {
            MaterialClassifyDivision materialClassifyDivision = new MaterialClassifyDivision();
            materialClassifyDivision.setId(rs.getString("id"));
            materialClassifyDivision.setName(rs.getString("name"));
            materialClassifyDivision.setNote(rs.getString("note"));
            materialClassifyDivision.setDeletedAt(rs.getTimestamp("deleted_at"));
            return materialClassifyDivision;
        }
    }

}