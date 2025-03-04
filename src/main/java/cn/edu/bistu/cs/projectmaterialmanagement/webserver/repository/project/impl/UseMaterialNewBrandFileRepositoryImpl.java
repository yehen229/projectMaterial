package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterialNewBrandFile;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IUseMaterialNewBrandFileRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class UseMaterialNewBrandFileRepositoryImpl implements IUseMaterialNewBrandFileRepository {
    private final JdbcTemplate jdbcTemplate;

    public UseMaterialNewBrandFileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(UseMaterialNewBrandFile useMaterialNewBrandFile) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_use_material_new_brand_file(id,
                                        t_use_material_new_brand_id,
                                        file_path)
                                        VALUES(?,?,?)
                                        """,
                                newId,
                                useMaterialNewBrandFile.getUseMaterialNewBrandId(),
                                useMaterialNewBrandFile.getFilePath()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(UseMaterialNewBrandFile useMaterialNewBrandFile) {
        if (useMaterialNewBrandFile == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_use_material_new_brand_file
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   useMaterialNewBrandFile.getId());

    }

    /**
     * update
     */
    @Override
    public int update(UseMaterialNewBrandFile useMaterialNewBrandFile) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material_new_brand_file
                                           SET t_use_material_new_brand_id=?,
                                           file_path=?
                                           WHERE id=?
                                           """,
                                   useMaterialNewBrandFile.getUseMaterialNewBrandId(),
                                   useMaterialNewBrandFile.getFilePath(),
                                   useMaterialNewBrandFile.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material_new_brand_file
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);
    }

    /**
     * 根据useMaterialNewBrandId删除记录
     */
    @Override
    public int deleteByUseMaterialNewBrandId(String useMaterialNewBrandId) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material_new_brand_file
                                           SET deleted_at=?
                                           WHERE t_use_material_new_brand_id=?
                                           """,
                                   new Date(), useMaterialNewBrandId);
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                                          SELECT count(*) 
                                                                          FROM t_use_material_new_brand_file
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键UseMaterialNewBrandId得到总数量
     */
    @Override
    public int getCountByUseMaterialNewBrandId(String useMaterialNewBrandId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material_new_brand_file
                                                        WHERE t_use_material_new_brand_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, useMaterialNewBrandId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public UseMaterialNewBrandFile getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material_new_brand_file 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_use_material_new_brand_file
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new UseMaterialNewBrandFileMapper(), id);
    }

    /**
     * 根据useMaterialNewBrandId得到记录
     */
    @Override
    public List<UseMaterialNewBrandFile> getByUseMaterialNewBrandId(String useMaterialNewBrandId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material_new_brand_file
                                                        WHERE t_use_material_new_brand_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, useMaterialNewBrandId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material_new_brand_file 
                                          WHERE t_use_material_new_brand_id=? AND deleted_at IS NULL
                                          """,
                                  new UseMaterialNewBrandFileMapper(), useMaterialNewBrandId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrandFile> getPage(int pageNo,
                                                 int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterialNewBrandFile> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param useMaterialNewBrandId
     * @param pageNo                页号，从1开始
     * @param pageSize              每页的记录数
     */
    @Override
    public Page<UseMaterialNewBrandFile> getPageByUseMaterialNewBrandId(String useMaterialNewBrandId,
                                                                        int pageNo,
                                                                        int pageSize) {
        long totalCount = getCountByUseMaterialNewBrandId(useMaterialNewBrandId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterialNewBrandFile> resultData = getPageQueryByUseMaterialNewBrandId(useMaterialNewBrandId,
                                                                                       pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<UseMaterialNewBrandFile> getPageQuery(int pageNo,
                                                       int pageSize) {
        return jdbcTemplate.query("""
                                                            SELECT * 
                                                            FROM t_use_material_new_brand_file
                                          WHERE deleted_at IS NULL
                                                            LIMIT ?,?
                                          """,
                                  new UseMaterialNewBrandFileMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_use_material_new_brand_id）+获得指定页面数据
     *
     * @param useMaterialNewBrandId
     * @param pageNo                页号，从1开始
     * @param pageSize              每页的记录数
     */
    private List<UseMaterialNewBrandFile> getPageQueryByUseMaterialNewBrandId(String useMaterialNewBrandId,
                                                                              int pageNo,
                                                                              int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material_new_brand_file
                                          WHERE t_use_material_new_brand_id=?  AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new UseMaterialNewBrandFileMapper(), useMaterialNewBrandId, pageNo * pageSize,
                                  pageSize);
    }

    /**
     * RowMapper
     */
    private static final class UseMaterialNewBrandFileMapper implements RowMapper<UseMaterialNewBrandFile> {
        @Override
        public UseMaterialNewBrandFile mapRow(ResultSet rs,
                                              int rowNum) throws SQLException {
            UseMaterialNewBrandFile useMaterialNewBrandFile = new UseMaterialNewBrandFile();
            useMaterialNewBrandFile.setId(rs.getString("id"));
            useMaterialNewBrandFile.setUseMaterialNewBrandId(rs.getString("t_use_material_new_brand_id"));
            useMaterialNewBrandFile.setFilePath(rs.getString("file_path"));
            useMaterialNewBrandFile.setDeletedAt(rs.getTimestamp("deleted_at"));
            return useMaterialNewBrandFile;
        }
    }

}