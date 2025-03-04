package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.ProjectMaterialVerificationDocument;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IProjectMaterialVerificationDocumentRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectMaterialVerificationDocumentRepositoryImpl implements IProjectMaterialVerificationDocumentRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectMaterialVerificationDocumentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectMaterialVerificationDocument projectMaterialVerificationDocument) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_material_verification_document(id,
                                        t_user_id,
                                        t_buy_material_id)
                                        VALUES(?,?,?)
                                        """,
                                newId,
                                projectMaterialVerificationDocument.getUserId(),
                                projectMaterialVerificationDocument.getBuyMaterialId()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectMaterialVerificationDocument projectMaterialVerificationDocument) {
        if (projectMaterialVerificationDocument == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_verification_document
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectMaterialVerificationDocument.getId());

    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_verification_document
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);

    }

    /**
     * 根据userId删除记录
     */
    @Override
    public int deleteByUserId(String userId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_verification_document
                                           SET deleted_at=? 
                                           WHERE t_user_id=?
                                           """,
                                   new Date(),
                                   userId);

    }

    /**
     * 根据projectMaterialId删除记录
     */
    @Override
    public int deleteByBuyMaterialId(String buyMaterialId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_verification_document
                                           SET deleted_at=? 
                                           WHERE t_buy_material_id=?
                                           """,
                                   new Date(),
                                   buyMaterialId);

    }

    /**
     * update
     */
    @Override
    public int update(ProjectMaterialVerificationDocument projectMaterialVerificationDocument) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_verification_document
                                           SET t_user_id=?,
                                           t_buy_material_id=?
                                           WHERE id=?
                                           """,
                                   projectMaterialVerificationDocument.getUserId(),
                                   projectMaterialVerificationDocument.getBuyMaterialId(),
                                   projectMaterialVerificationDocument.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_verification_document
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键UserId得到总数量
     */
    @Override
    public int getCountByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_verification_document
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectMaterialId得到总数量
     */
    @Override
    public int getCountByBuyMaterialId(String buyMaterialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_verification_document
                                                        WHERE t_buy_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, buyMaterialId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectMaterialVerificationDocument getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_verification_document 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_material_verification_document
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectMaterialVerificationDocumentMapper(), id);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<ProjectMaterialVerificationDocument> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_verification_document
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_verification_document 
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialVerificationDocumentMapper(), userId);
    }

    /**
     * 根据projectMaterialId得到记录
     */
    @Override
    public List<ProjectMaterialVerificationDocument> getByBuyMaterialId(String buyMaterialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_verification_document
                                                        WHERE t_buy_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, buyMaterialId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_verification_document 
                                          WHERE t_buy_material_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialVerificationDocumentMapper(), buyMaterialId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialVerificationDocument> getPage(int pageNo,
                                                             int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialVerificationDocument> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialVerificationDocument> getPageByUserId(String userId,
                                                                     int pageNo,
                                                                     int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialVerificationDocument> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param buyMaterialId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    @Override
    public Page<ProjectMaterialVerificationDocument> getPageByBuyMaterialId(String buyMaterialId,
                                                                            int pageNo,
                                                                            int pageSize) {
        long totalCount = getCountByBuyMaterialId(buyMaterialId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialVerificationDocument> resultData = getPageQueryByBuyMaterialId(buyMaterialId,
                                                                                           pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectMaterialVerificationDocument> getPageQuery(int pageNo,
                                                                   int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_verification_document
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialVerificationDocumentMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<ProjectMaterialVerificationDocument> getPageQueryByUserId(String userId,
                                                                           int pageNo,
                                                                           int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_verification_document
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialVerificationDocumentMapper(), userId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_material_id）+获得指定页面数据
     *
     * @param buyMaterialId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    private List<ProjectMaterialVerificationDocument> getPageQueryByBuyMaterialId(String buyMaterialId,
                                                                                  int pageNo,
                                                                                  int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_verification_document
                                          WHERE t_buy_material_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialVerificationDocumentMapper(), buyMaterialId, pageNo * pageSize,
                                  pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectMaterialVerificationDocumentMapper implements RowMapper<ProjectMaterialVerificationDocument> {
        @Override
        public ProjectMaterialVerificationDocument mapRow(ResultSet rs,
                                                          int rowNum) throws SQLException {
            ProjectMaterialVerificationDocument projectMaterialVerificationDocument = new ProjectMaterialVerificationDocument();
            projectMaterialVerificationDocument.setId(rs.getString("id"));
            projectMaterialVerificationDocument.setUserId(rs.getString("t_user_id"));
            projectMaterialVerificationDocument.setBuyMaterialId(rs.getString("t_buy_material_id"));
            projectMaterialVerificationDocument.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectMaterialVerificationDocument;
        }
    }

}