package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.acceptance.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.acceptance.ProjectMaterialAcceptance;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.acceptance.IProjectMaterialAcceptanceRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectMaterialAcceptanceRepositoryImpl implements IProjectMaterialAcceptanceRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectMaterialAcceptanceRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectMaterialAcceptance projectMaterialAcceptance) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_material_acceptance(id,
                                        t_user_id,
                                        t_project_material_acceptance_batch_id,
                                        t_project_material_id,
                                        t_project_material_brand_private_id,
                                        t_project_material_brand_public_id,
                                        material_count,
                                        material_unit,
                                        position,
                                        note,
                                        create_datetime)
                                        VALUES(?,?,?,?,?,?,?,?,?,?,?)
                                        """,
                                newId,
                                projectMaterialAcceptance.getUserId(),
                                projectMaterialAcceptance.getProjectMaterialAcceptanceBatchId(),
                                projectMaterialAcceptance.getProjectMaterialId(),
                                projectMaterialAcceptance.getProjectMaterialBrandPrivateId(),
                                projectMaterialAcceptance.getProjectMaterialBrandPublicId(),
                                projectMaterialAcceptance.getMaterialCount(),
                                projectMaterialAcceptance.getMaterialUnit(),
                                projectMaterialAcceptance.getPosition(),
                                projectMaterialAcceptance.getNote(),
                                new Date()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectMaterialAcceptance projectMaterialAcceptance) {
        if (projectMaterialAcceptance == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """, new Date(),
                                   projectMaterialAcceptance.getId());
    }

    /**
     * update
     */
    @Override
    public int update(ProjectMaterialAcceptance projectMaterialAcceptance) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance
                                           SET t_user_id=?,
                                           t_project_material_acceptance_batch_id=?,
                                           t_project_material_id=?,
                                           t_project_material_brand_private_id=?,
                                           t_project_material_brand_public_id=?,
                                           material_count=?,
                                           material_unit=?,
                                           position=?,
                                           note=?,
                                           create_datetime=?
                                           WHERE id=?
                                           """,
                                   projectMaterialAcceptance.getUserId(),
                                   projectMaterialAcceptance.getProjectMaterialAcceptanceBatchId(),
                                   projectMaterialAcceptance.getProjectMaterialId(),
                                   projectMaterialAcceptance.getProjectMaterialBrandPrivateId(),
                                   projectMaterialAcceptance.getProjectMaterialBrandPublicId(),
                                   projectMaterialAcceptance.getMaterialCount(),
                                   projectMaterialAcceptance.getMaterialUnit(),
                                   projectMaterialAcceptance.getPosition(),
                                   projectMaterialAcceptance.getNote(),
                                   projectMaterialAcceptance.getCreateDatetime(),
                                   projectMaterialAcceptance.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """, new Date(),
                                   id);
    }

    /**
     * 根据userId删除记录
     */
    @Override
    public int deleteByUserId(String userId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance
                                           SET deleted_at=? 
                                           WHERE t_user_id=?
                                           """, new Date(),
                                   userId);
    }

    @Override
    public int deleteByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance
                                           SET deleted_at=? 
                                           WHERE t_project_material_acceptance_batch_id=?
                                           """, new Date(),
                                   projectMaterialAcceptanceBatchId);
    }

    /**
     * 根据projectMaterialId删除记录
     */
    @Override
    public int deleteByProjectMaterialId(String projectMaterialId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance
                                           SET deleted_at=? 
                                           WHERE t_project_material_id=?
                                           """, new Date(),
                                   projectMaterialId);
    }

    /**
     * 根据projectMaterialBrandPrivateId删除记录
     */
    @Override
    public int deleteByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance
                                           SET deleted_at=? 
                                           WHERE t_project_material_brand_private_id=?
                                           """, new Date(),
                                   projectMaterialBrandPrivateId);
    }

    /**
     * 根据projectMaterialBrandPublicId删除记录
     */
    @Override
    public int deleteByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_material_acceptance
                                           SET deleted_at=? 
                                           WHERE t_project_material_brand_public_id=?
                                           """, new Date(),
                                   projectMaterialBrandPublicId);
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance
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
                                                        FROM t_project_material_acceptance
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance
                                                        WHERE t_project_material_acceptance_batch_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialAcceptanceBatchId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectMaterialId得到总数量
     */
    @Override
    public int getCountByProjectMaterialId(String projectMaterialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance
                                                        WHERE t_project_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectMaterialBrandPrivateId得到总数量
     */
    @Override
    public int getCountByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance
                                                        WHERE t_project_material_brand_private_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialBrandPrivateId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectMaterialBrandPublicId得到总数量
     */
    @Override
    public int getCountByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance
                                                        WHERE t_project_material_brand_public_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialBrandPublicId);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public ProjectMaterialAcceptance getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_material_acceptance
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectMaterialAcceptanceMapper(), id);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<ProjectMaterialAcceptance> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance 
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialAcceptanceMapper(), userId);
    }

    @Override
    public List<ProjectMaterialAcceptance> getByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance
                                                        WHERE t_project_material_acceptance_batch_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialAcceptanceBatchId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance 
                                          WHERE t_project_material_acceptance_batch_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialAcceptanceMapper(), projectMaterialAcceptanceBatchId);
    }

    /**
     * 根据projectMaterialId得到记录
     */
    @Override
    public List<ProjectMaterialAcceptance> getByProjectMaterialId(String projectMaterialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance
                                                        WHERE t_project_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance 
                                          WHERE t_project_material_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialAcceptanceMapper(), projectMaterialId);
    }

    /**
     * 根据projectMaterialBrandPrivateId得到记录
     */
    @Override
    public List<ProjectMaterialAcceptance> getByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance
                                                        WHERE t_project_material_brand_private_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialBrandPrivateId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance 
                                          WHERE t_project_material_brand_private_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialAcceptanceMapper(), projectMaterialBrandPrivateId);
    }

    /**
     * 根据projectMaterialBrandPublicId得到记录
     */
    @Override
    public List<ProjectMaterialAcceptance> getByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_material_acceptance
                                                        WHERE t_project_material_brand_public_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialBrandPublicId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance 
                                          WHERE t_project_material_brand_public_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectMaterialAcceptanceMapper(), projectMaterialBrandPublicId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptance> getPage(int pageNo,
                                                   int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptance> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<ProjectMaterialAcceptance> getPageByUserId(String userId,
                                                           int pageNo,
                                                           int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptance> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<ProjectMaterialAcceptance> getPageByProjectMaterialAcceptanceBatchId(String projectMaterialAcceptanceBatchId,
                                                                                     int pageNo,
                                                                                     int pageSize) {
        long totalCount = getCountByProjectMaterialAcceptanceBatchId(projectMaterialAcceptanceBatchId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptance> resultData = getPageQueryByProjectMaterialAcceptanceBatchId(
                projectMaterialAcceptanceBatchId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialId
     * @param pageNo            页号，从1开始
     * @param pageSize          每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptance> getPageByProjectMaterialId(String projectMaterialId,
                                                                      int pageNo,
                                                                      int pageSize) {
        long totalCount = getCountByProjectMaterialId(projectMaterialId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptance> resultData = getPageQueryByProjectMaterialId(projectMaterialId, pageNo - 1,
                                                                                     pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialBrandPrivateId
     * @param pageNo                        页号，从1开始
     * @param pageSize                      每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptance> getPageByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,
                                                                                  int pageNo,
                                                                                  int pageSize) {
        long totalCount = getCountByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptance> resultData = getPageQueryByProjectMaterialBrandPrivateId(
                projectMaterialBrandPrivateId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialBrandPublicId
     * @param pageNo                       页号，从1开始
     * @param pageSize                     每页的记录数
     */
    @Override
    public Page<ProjectMaterialAcceptance> getPageByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,
                                                                                 int pageNo,
                                                                                 int pageSize) {
        long totalCount = getCountByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectMaterialAcceptance> resultData = getPageQueryByProjectMaterialBrandPublicId(
                projectMaterialBrandPublicId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectMaterialAcceptance> getPageQuery(int pageNo,
                                                         int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<ProjectMaterialAcceptance> getPageQueryByUserId(String userId,
                                                                 int pageNo,
                                                                 int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceMapper(), userId, pageNo * pageSize, pageSize);
    }

    private List<ProjectMaterialAcceptance> getPageQueryByProjectMaterialAcceptanceBatchId(String projectMaterialBrandPrivateId,
                                                                                           int pageNo,
                                                                                           int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance
                                          WHERE t_project_material_acceptance_batch_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceMapper(), projectMaterialBrandPrivateId,
                                  pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_material_id）+获得指定页面数据
     *
     * @param projectMaterialId
     * @param pageNo            页号，从1开始
     * @param pageSize          每页的记录数
     */
    private List<ProjectMaterialAcceptance> getPageQueryByProjectMaterialId(String projectMaterialId,
                                                                            int pageNo,
                                                                            int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance
                                          WHERE t_project_material_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceMapper(), projectMaterialId, pageNo * pageSize,
                                  pageSize);
    }

    /**
     * 根据外键（t_project_material_brand_private_id）+获得指定页面数据
     *
     * @param projectMaterialBrandPrivateId
     * @param pageNo                        页号，从1开始
     * @param pageSize                      每页的记录数
     */
    private List<ProjectMaterialAcceptance> getPageQueryByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,
                                                                                        int pageNo,
                                                                                        int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance
                                          WHERE t_project_material_brand_private_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceMapper(), projectMaterialBrandPrivateId,
                                  pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_material_brand_public_id）+获得指定页面数据
     *
     * @param projectMaterialBrandPublicId
     * @param pageNo                       页号，从1开始
     * @param pageSize                     每页的记录数
     */
    private List<ProjectMaterialAcceptance> getPageQueryByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,
                                                                                       int pageNo,
                                                                                       int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_material_acceptance
                                          WHERE t_project_material_brand_public_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectMaterialAcceptanceMapper(), projectMaterialBrandPublicId,
                                  pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class ProjectMaterialAcceptanceMapper implements RowMapper<ProjectMaterialAcceptance> {
        @Override
        public ProjectMaterialAcceptance mapRow(ResultSet rs,
                                                int rowNum) throws SQLException {
            ProjectMaterialAcceptance projectMaterialAcceptance = new ProjectMaterialAcceptance();
            projectMaterialAcceptance.setId(rs.getString("id"));
            projectMaterialAcceptance.setUserId(rs.getString("t_user_id"));
            projectMaterialAcceptance.setProjectMaterialAcceptanceBatchId(
                    rs.getString("t_project_material_acceptance_batch_id"));
            projectMaterialAcceptance.setProjectMaterialId(rs.getString("t_project_material_id"));
            projectMaterialAcceptance.setProjectMaterialBrandPrivateId(
                    rs.getString("t_project_material_brand_private_id"));
            projectMaterialAcceptance.setProjectMaterialBrandPublicId(
                    rs.getString("t_project_material_brand_public_id"));
            projectMaterialAcceptance.setMaterialCount(rs.getBigDecimal("material_count"));
            projectMaterialAcceptance.setMaterialUnit(rs.getString("material_unit"));
            projectMaterialAcceptance.setPosition(rs.getString("position"));
            projectMaterialAcceptance.setNote(rs.getString("note"));
            projectMaterialAcceptance.setCreateDatetime(rs.getDate("create_datetime"));
            projectMaterialAcceptance.setDeletedAt(rs.getDate("deleted_at"));
            return projectMaterialAcceptance;
        }
    }

}