package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.UseMaterial;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IUseMaterialRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class UseMaterialRepositoryImpl implements IUseMaterialRepository {
    private final JdbcTemplate jdbcTemplate;

    public UseMaterialRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(UseMaterial useMaterial) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_use_material(id,
                                        t_use_material_brand_select_id,
                                        t_project_material_id,
                                        t_user_id,
                                        t_project_material_brand_private_id,
                                        t_project_material_brand_public_id,
                                        t_material_id,
                                        material_count,
                                        material_unit,
                                        is_appearance,
                                        create_datetime
                                        )
                                        VALUES(?,?,?,?,?,?,?,?,?,?,?)
                                        """,
                                newId,
                                useMaterial.getUseMaterialBrandSelectId(),
                                useMaterial.getProjectMaterialId(),
                                useMaterial.getUserId(),
                                useMaterial.getProjectMaterialBrandPrivateId(),
                                useMaterial.getProjectMaterialBrandPublicId(),
                                useMaterial.getMaterialId(),
                                useMaterial.getMaterialCount(),
                                useMaterial.getMaterialUnit(),
                                useMaterial.getIsAppearance(),
                                new Date()
        ) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(UseMaterial useMaterial) {
        if (useMaterial == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_use_material
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   useMaterial.getId());


    }

    /**
     * update
     */
    @Override
    public int update(UseMaterial useMaterial) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material
                                           SET
                                           t_use_material_brand_select_id=?,
                                           t_project_material_id=?,
                                           t_user_id=?,
                                           t_project_material_brand_private_id=?,
                                           t_project_material_brand_public_id=?,
                                           t_material_id=?,
                                           material_count=?,
                                           material_unit=?,
                                           is_appearance=?
                                           WHERE id=?
                                           """,
                                   useMaterial.getUseMaterialBrandSelectId(),
                                   useMaterial.getProjectMaterialId(),
                                   useMaterial.getUserId(),
                                   useMaterial.getProjectMaterialBrandPrivateId(),
                                   useMaterial.getProjectMaterialBrandPublicId(),
                                   useMaterial.getMaterialId(),
                                   useMaterial.getMaterialCount(),
                                   useMaterial.getMaterialUnit(),
                                   useMaterial.getIsAppearance(),
                                   useMaterial.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   id);
    }

    @Override
    public int deleteByUseMaterialBrandSelectId(String useMaterialBrandSelectId) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material
                                           SET deleted_at=? 
                                           WHERE t_use_material_brand_select_id=?
                                           """,
                                   new Date(),
                                   useMaterialBrandSelectId);
    }

    /**
     * 根据projectMaterialId删除记录
     */
    @Override
    public int deleteByProjectMaterialId(String projectMaterialId) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material
                                           SET deleted_at=? 
                                           WHERE t_project_material_id=?
                                           """,
                                   new Date(), projectMaterialId);
    }

    /**
     * 根据userId删除记录
     */
    @Override
    public int deleteByUserId(String userId) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material
                                           SET deleted_at=? 
                                           WHERE t_user_id=?
                                           """,
                                   new Date(), userId);
    }

    /**
     * 根据projectMaterialBrandPrivateId删除记录
     */
    @Override
    public int deleteByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material
                                           SET deleted_at=? 
                                           WHERE t_project_material_brand_private_id=?
                                           """,
                                   new Date(), projectMaterialBrandPrivateId);
    }

    /**
     * 根据projectMaterialBrandPublicId删除记录
     */
    @Override
    public int deleteByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId) {
        return jdbcTemplate.update("""
                                           UPDATE t_use_material
                                           SET deleted_at=? 
                                           WHERE t_project_material_brand_public_id=?
                                           """,
                                   new Date(), projectMaterialBrandPublicId);
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material
                                                        WHERE deleted_at IS NULL   
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByUseMaterialBrandSelectId(String useMaterialBrandSelectId) {
        return jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material
                                                        WHERE t_use_material_brand_select_id=? AND deleted_at IS NULL
                                                        """,
                                           Integer.class, useMaterialBrandSelectId);
    }

    /**
     * 根据外键ProjectMaterialId得到总数量
     */
    @Override
    public int getCountByProjectMaterialId(String projectMaterialId) {
        return jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material
                                                        WHERE t_project_material_id=? AND deleted_at IS NULL
                                                        """,
                                           Integer.class, projectMaterialId);
    }

    /**
     * 根据外键UserId得到总数量
     */
    @Override
    public int getCountByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        return i;
    }

    @Override
    public int getCountOfReviewedAndApprovedPageByProjectId(String projectId) {

        return jdbcTemplate.queryForObject("""
    SELECT COUNT(DISTINCT u.id)
    FROM t_use_material u
    WHERE u.t_use_material_brand_select_id IN (
        SELECT DISTINCT s.id
        FROM t_use_material_brand_select s
        LEFT JOIN t_project_appearance_review_mode m ON m.t_use_material_brand_select_id = s.id
        LEFT JOIN t_project_appearance_review r ON r.t_project_appearance_review_mode_id = m.id
        WHERE s.t_project_id =?
        AND s.id NOT IN (
            SELECT st.id
            FROM t_use_material_brand_select st
            LEFT JOIN t_project_appearance_review_mode mt ON mt.t_use_material_brand_select_id = st.id
            LEFT JOIN t_project_appearance_review rt ON rt.t_project_appearance_review_mode_id = mt.id
            WHERE rt.review_result != 1
            AND rt.t_project_appearance_review_mode_id = mt.id
            AND mt.t_use_material_brand_select_id = st.id
            AND st.t_project_id =?
        )
    )
""", Integer.class, projectId, projectId);
    }

    @Override
    public int getCountOfReviewedAndApprovedPageByProjectIdAndName(String projectId, String name) {
        name = "%" + name + "%";
        return jdbcTemplate.queryForObject("""
    SELECT COUNT(DISTINCT u.id)
    FROM t_use_material u
    LEFT JOIN t_project_material ON u.t_project_material_id = t_project_material.id
    LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
    WHERE u.t_use_material_brand_select_id IN (
        SELECT DISTINCT s.id
        FROM t_use_material_brand_select s
        LEFT JOIN t_project_appearance_review_mode m ON m.t_use_material_brand_select_id = s.id
        LEFT JOIN t_project_appearance_review r ON r.t_project_appearance_review_mode_id = m.id
        WHERE s.t_project_id =?
        AND s.id NOT IN (
            SELECT st.id
            FROM t_use_material_brand_select st
            LEFT JOIN t_project_appearance_review_mode mt ON mt.t_use_material_brand_select_id = st.id
            LEFT JOIN t_project_appearance_review rt ON rt.t_project_appearance_review_mode_id = mt.id
            WHERE rt.review_result != 1
            AND rt.t_project_appearance_review_mode_id = mt.id
            AND mt.t_use_material_brand_select_id = st.id
            AND st.t_project_id =?
        )
    ) AND t_material.name LIKE ?
""", Integer.class, projectId, projectId, name);
    }

    @Override
    public int getCountOfReviewedAndApprovedPageByProjectIdAndLocation(String projectId, String location) {
        location = "%" + location + "%";
        return jdbcTemplate.queryForObject("""
    SELECT COUNT(DISTINCT u.id)
    FROM t_use_material u
    LEFT JOIN t_project_material ON u.t_project_material_id = t_project_material.id
    LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
    WHERE u.t_use_material_brand_select_id IN (
        SELECT DISTINCT s.id
        FROM t_use_material_brand_select s
        LEFT JOIN t_project_appearance_review_mode m ON m.t_use_material_brand_select_id = s.id
        LEFT JOIN t_project_appearance_review r ON r.t_project_appearance_review_mode_id = m.id
        WHERE s.t_project_id =?
        AND s.id NOT IN (
            SELECT st.id
            FROM t_use_material_brand_select st
            LEFT JOIN t_project_appearance_review_mode mt ON mt.t_use_material_brand_select_id = st.id
            LEFT JOIN t_project_appearance_review rt ON rt.t_project_appearance_review_mode_id = mt.id
            WHERE rt.review_result != 1
            AND rt.t_project_appearance_review_mode_id = mt.id
            AND mt.t_use_material_brand_select_id = st.id
            AND st.t_project_id =?
        )
    ) AND t_material.location LIKE ?
""", Integer.class, projectId, projectId, location);
    }

    @Override
    public int getCountOfReviewedAndApprovedPageByProjectIdAndItemMark(String projectId, String itemMark) {
        itemMark = "%" + itemMark + "%";
        return jdbcTemplate.queryForObject("""
    SELECT COUNT(DISTINCT u.id)
    FROM t_use_material u
    LEFT JOIN t_project_material ON u.t_project_material_id = t_project_material.id
    LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
    WHERE u.t_use_material_brand_select_id IN (
        SELECT DISTINCT s.id
        FROM t_use_material_brand_select s
        LEFT JOIN t_project_appearance_review_mode m ON m.t_use_material_brand_select_id = s.id
        LEFT JOIN t_project_appearance_review r ON r.t_project_appearance_review_mode_id = m.id
        WHERE s.t_project_id =?
        AND s.id NOT IN (
            SELECT st.id
            FROM t_use_material_brand_select st
            LEFT JOIN t_project_appearance_review_mode mt ON mt.t_use_material_brand_select_id = st.id
            LEFT JOIN t_project_appearance_review rt ON rt.t_project_appearance_review_mode_id = mt.id
            WHERE rt.review_result != 1
            AND rt.t_project_appearance_review_mode_id = mt.id
            AND mt.t_use_material_brand_select_id = st.id
            AND st.t_project_id =?
        )
    )  AND t_material.item_mark LIKE ?
""", Integer.class, projectId, projectId, itemMark);
    }

    @Override
    public int getCountOfReviewedAndApprovedPageByProjectIdAndTechnology(String projectId, String technology) {
        technology = "%" + technology + "%";
        return jdbcTemplate.queryForObject("""
    SELECT COUNT(DISTINCT u.id)
    FROM t_use_material u
    LEFT JOIN t_project_material ON u.t_project_material_id = t_project_material.id
    LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
    WHERE u.t_use_material_brand_select_id IN (
        SELECT DISTINCT s.id
        FROM t_use_material_brand_select s
        LEFT JOIN t_project_appearance_review_mode m ON m.t_use_material_brand_select_id = s.id
        LEFT JOIN t_project_appearance_review r ON r.t_project_appearance_review_mode_id = m.id
        WHERE s.t_project_id =?
        AND s.id NOT IN (
            SELECT st.id
            FROM t_use_material_brand_select st
            LEFT JOIN t_project_appearance_review_mode mt ON mt.t_use_material_brand_select_id = st.id
            LEFT JOIN t_project_appearance_review rt ON rt.t_project_appearance_review_mode_id = mt.id
            WHERE rt.review_result != 1
            AND rt.t_project_appearance_review_mode_id = mt.id
            AND mt.t_use_material_brand_select_id = st.id
            AND st.t_project_id =?
        )
    ) AND t_material.technology LIKE ?
""", Integer.class, projectId, projectId, technology);
    }

    @Override
    public int getCountOfReviewedAndApprovedPageByProjectIdAndInstallation(String projectId, String installation) {
        installation = "%" + installation + "%";
        return jdbcTemplate.queryForObject("""
    SELECT COUNT(DISTINCT u.id)
    FROM t_use_material u
    LEFT JOIN t_project_material ON u.t_project_material_id = t_project_material.id
    LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
    WHERE u.t_use_material_brand_select_id IN (
        SELECT DISTINCT s.id
        FROM t_use_material_brand_select s
        LEFT JOIN t_project_appearance_review_mode m ON m.t_use_material_brand_select_id = s.id
        LEFT JOIN t_project_appearance_review r ON r.t_project_appearance_review_mode_id = m.id
        WHERE s.t_project_id =?
        AND s.id NOT IN (
            SELECT st.id
            FROM t_use_material_brand_select st
            LEFT JOIN t_project_appearance_review_mode mt ON mt.t_use_material_brand_select_id = st.id
            LEFT JOIN t_project_appearance_review rt ON rt.t_project_appearance_review_mode_id = mt.id
            WHERE rt.review_result != 1
            AND rt.t_project_appearance_review_mode_id = mt.id
            AND mt.t_use_material_brand_select_id = st.id
            AND st.t_project_id =?
        )
    ) AND t_material.installation LIKE ?
""", Integer.class, projectId, projectId, installation);
    }

    @Override
    public int getCountOfReviewedAndApprovedPageByProjectIdAndBrand(String projectId, String brand) {
        brand = "%" + brand + "%";
        return jdbcTemplate.queryForObject("""
    SELECT COUNT(DISTINCT u.id)
    FROM t_use_material u
    LEFT JOIN t_project_material_brand_public ON u.t_project_material_brand_public_id = t_project_material_brand_public.id
    LEFT JOIN t_project_material_brand_private ON u.t_project_material_brand_private_id = t_project_material_brand_private.id
    LEFT JOIN t_brand_public ON t_project_material_brand_public.t_brand_public_id = t_brand_public.id
    LEFT JOIN t_project_brand ON t_project_material_brand_private.t_project_brand_id = t_project_brand.id
    LEFT JOIN t_brand ON t_project_brand.t_brand_id = t_brand.id OR t_brand_public.t_brand_id = t_brand.id
    WHERE u.t_use_material_brand_select_id IN (
        SELECT DISTINCT s.id
        FROM t_use_material_brand_select s
        LEFT JOIN t_project_appearance_review_mode m ON m.t_use_material_brand_select_id = s.id
        LEFT JOIN t_project_appearance_review r ON r.t_project_appearance_review_mode_id = m.id
        WHERE s.t_project_id =?
        AND s.id NOT IN (
            SELECT st.id
            FROM t_use_material_brand_select st
            LEFT JOIN t_project_appearance_review_mode mt ON mt.t_use_material_brand_select_id = st.id
            LEFT JOIN t_project_appearance_review rt ON rt.t_project_appearance_review_mode_id = mt.id
            WHERE rt.review_result != 1
            AND rt.t_project_appearance_review_mode_id = mt.id
            AND mt.t_use_material_brand_select_id = st.id
            AND st.t_project_id =?
        )
    ) AND t_brand.name LIKE ?
""", Integer.class, projectId, projectId, brand);
    }

    /**
     * 根据外键ProjectMaterialBrandPrivateId得到总数量
     */
    @Override
    public int getCountByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        return jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material
                                                        WHERE t_project_material_brand_private_id=? AND deleted_at IS NULL
                                                        """,
                                           Integer.class, projectMaterialBrandPrivateId);
    }

    /**
     * 根据外键ProjectMaterialBrandPublicId得到总数量
     */
    @Override
    public int getCountByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId) {
        return jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material
                                                        WHERE t_project_material_brand_public_id=? AND deleted_at IS NULL
                                                        """,
                                           Integer.class, projectMaterialBrandPublicId);
    }

    @Override
    public boolean isExistByProjectMaterialIdAndBrandPublicIdAndAppearance(String projectMaterialId,
                                                                           String projectMaterialBrandPublicId,
                                                                           int isAppearance) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material
                                                        WHERE t_project_material_id=?
                                                          AND t_project_material_brand_public_id=?
                                                          AND is_appearance=? 
                                                          AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialId,projectMaterialBrandPublicId,isAppearance);
        return i >0;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public UseMaterial getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_use_material
                                                   WHERE id=?
                                                   """,
                                           new UseMaterialMapper(), id);
    }

    @Override
    public List<UseMaterial> getByUseMaterialBrandSelectId(String useMaterialBrandSelectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material
                                                        WHERE t_use_material_brand_select_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, useMaterialBrandSelectId);
        if (i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material 
                                          WHERE t_use_material_brand_select_id=? AND deleted_at IS NULL
                                          """,
                                  new UseMaterialMapper(), useMaterialBrandSelectId);
    }

    /**
     * 根据projectMaterialId得到记录
     */
    @Override
    public List<UseMaterial> getByProjectMaterialId(String projectMaterialId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material
                                                        WHERE t_project_material_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialId);
        if (i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material 
                                          WHERE t_project_material_id=? AND deleted_at IS NULL
                                          """,
                                  new UseMaterialMapper(), projectMaterialId);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<UseMaterial> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material 
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          """,
                                  new UseMaterialMapper(), userId);
    }

    /**
     * 根据projectMaterialBrandPrivateId得到记录
     */
    @Override
    public List<UseMaterial> getByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material
                                                        WHERE t_project_material_brand_private_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialBrandPrivateId);
        if (i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material 
                                          WHERE t_project_material_brand_private_id=? AND deleted_at IS NULL
                                          """,
                                  new UseMaterialMapper(), projectMaterialBrandPrivateId);
    }

    /**
     * 根据projectMaterialBrandPublicId得到记录
     */
    @Override
    public List<UseMaterial> getByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_use_material
                                                        WHERE t_project_material_brand_public_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectMaterialBrandPublicId);
        if (i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material 
                                          WHERE t_project_material_brand_public_id=? AND deleted_at IS NULL
                                          """,
                                  new UseMaterialMapper(), projectMaterialBrandPublicId);
    }

    @Override
    public List<UseMaterial> getReviewedAndApprovedListByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                                      SELECT count(*) 
                                                                      FROM t_use_material
                                                        LEFT JOIN t_use_material_brand_select ON t_use_material_brand_select.id=t_use_material.t_use_material_brand_select_id
                                                        WHERE t_use_material_brand_select.t_project_id=? 
                                                        AND t_use_material_brand_select.deleted_at IS NULL
                                                        AND t_use_material.deleted_at IS NULL
                                                        """,
                                                Integer.class, projectId);
        if (i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          LEFT JOIN t_use_material_brand_select ON t_use_material_brand_select.id=t_use_material.t_use_material_brand_select_id
                                          WHERE t_use_material_brand_select.t_project_id=? 
                                          AND t_use_material_brand_select.deleted_at IS NULL
                                          AND t_use_material.deleted_at IS NULL
                                          """,
                                  new UseMaterialMapper(), projectId);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UseMaterial> getPage(int pageNo,
                                     int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterial> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<UseMaterial> getPageByUseMaterialBrandSelectId(String useMaterialBrandSelectId,
                                                               int pageNo,
                                                               int pageSize) {
        long totalCount = getCountByUseMaterialBrandSelectId(useMaterialBrandSelectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterial> resultData = getPageQueryByUseMaterialBrandSelectId(useMaterialBrandSelectId, pageNo - 1,
                                                                              pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialId
     * @param pageNo            页号，从1开始
     * @param pageSize          每页的记录数
     */
    @Override
    public Page<UseMaterial> getPageByProjectMaterialId(String projectMaterialId,
                                                        int pageNo,
                                                        int pageSize) {
        long totalCount = getCountByProjectMaterialId(projectMaterialId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterial> resultData = getPageQueryByProjectMaterialId(projectMaterialId, pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<UseMaterial> getPageByUserId(String userId,
                                             int pageNo,
                                             int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterial> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialBrandPrivateId
     * @param pageNo                        页号，从1开始
     * @param pageSize                      每页的记录数
     */
    @Override
    public Page<UseMaterial> getPageByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,
                                                                    int pageNo,
                                                                    int pageSize) {
        long totalCount = getCountByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterial> resultData = getPageQueryByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId,
                                                                                   pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectMaterialBrandPublicId
     * @param pageNo                       页号，从1开始
     * @param pageSize                     每页的记录数
     */
    @Override
    public Page<UseMaterial> getPageByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,
                                                                   int pageNo,
                                                                   int pageSize) {
        long totalCount = getCountByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterial> resultData = getPageQueryByProjectMaterialBrandPublicId(projectMaterialBrandPublicId,
                                                                                  pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<UseMaterial> getReviewedAndApprovedPageByProjectId(String projectId,
                                                                   Integer pageNo,
                                                                   Integer pageSize) {
        long totalCount = getCountOfReviewedAndApprovedPageByProjectId(projectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterial> resultData = getPageQueryOfReviewedAndApprovedPageByProjectId(projectId, pageNo - 1,
                                                                                        pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<UseMaterial> getReviewedAndApprovedPageByProjectIdAndName(String projectId,
                                                                   String name,
                                                                   Integer pageNo,
                                                                   Integer pageSize) {
        long totalCount = getCountOfReviewedAndApprovedPageByProjectIdAndName(projectId, name);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterial> resultData = getPageQueryOfReviewedAndApprovedPageByProjectIdAndName(projectId, name, pageNo - 1,
                                                                                        pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<UseMaterial> getReviewedAndApprovedPageByProjectIdAndLocation(String projectId,
                                                                   String location,
                                                                   Integer pageNo,
                                                                   Integer pageSize) {
        long totalCount = getCountOfReviewedAndApprovedPageByProjectIdAndLocation(projectId, location);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterial> resultData = getPageQueryOfReviewedAndApprovedPageByProjectIdAndLocation(projectId, location, pageNo - 1,
                                                                                        pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<UseMaterial> getReviewedAndApprovedPageByProjectIdAndItemMark(String projectId,
                                                                   String itemMark,
                                                                   Integer pageNo,
                                                                   Integer pageSize) {
        long totalCount = getCountOfReviewedAndApprovedPageByProjectIdAndItemMark(projectId, itemMark);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterial> resultData = getPageQueryOfReviewedAndApprovedPageByProjectIdAndItemMark(projectId, itemMark, pageNo - 1,
                                                                                        pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<UseMaterial> getReviewedAndApprovedPageByProjectIdAndTechnology(String projectId,
                                                                   String technology,
                                                                   Integer pageNo,
                                                                   Integer pageSize) {
        long totalCount = getCountOfReviewedAndApprovedPageByProjectIdAndTechnology(projectId, technology);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterial> resultData = getPageQueryOfReviewedAndApprovedPageByProjectIdAndTechnology(projectId, technology, pageNo - 1,
                                                                                        pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<UseMaterial> getReviewedAndApprovedPageByProjectIdAndInstallation(String projectId,
                                                                   String installation,
                                                                   Integer pageNo,
                                                                   Integer pageSize) {
        long totalCount = getCountOfReviewedAndApprovedPageByProjectIdAndInstallation(projectId, installation);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterial> resultData = getPageQueryOfReviewedAndApprovedPageByProjectIdAndInstallation(projectId, installation, pageNo - 1,
                                                                                        pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<UseMaterial> getReviewedAndApprovedPageByProjectIdAndBrand(String projectId,
                                                                   String brand,
                                                                   Integer pageNo,
                                                                   Integer pageSize) {
        long totalCount = getCountOfReviewedAndApprovedPageByProjectIdAndBrand(projectId, brand);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<UseMaterial> resultData = getPageQueryOfReviewedAndApprovedPageByProjectIdAndBrand(projectId, brand, pageNo - 1,
                                                                                        pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    private List<String> getPageQueryOfReviewedAndApprovedPageByProjectIdHelper(String projectId,
                                                                               int pageNo,
                                                                               int pageSize) {
        return jdbcTemplate.query("""
            
                SELECT DISTINCT (u.id)
                       FROM t_use_material u
                       WHERE u.t_use_material_brand_select_id IN (
                           SELECT DISTINCT (s.id)
                           FROM t_use_material_brand_select  s
                           LEFT JOIN t_project_appearance_review_mode  m ON m.t_use_material_brand_select_id=s.id
                           LEFT JOIN t_project_appearance_review  r ON r.t_project_appearance_review_mode_id=m.id
                           WHERE s.t_project_id=?
                               AND s.id  in (
               	                SELECT DISTINCT (st.id)
               	                FROM t_use_material_brand_select  st
               	                LEFT JOIN t_project_appearance_review_mode  mt ON mt.t_use_material_brand_select_id=st.id
               	                LEFT JOIN t_project_appearance_review  rt ON rt.t_project_appearance_review_mode_id=mt.id
           											LEFT JOIN t_company_user tu ON tu.t_user_id = mt.t_user_id
           											LEFT JOIN t_company tc ON tu.t_company_id = tc.id
               	                WHERE rt.review_result =1
           											  AND ((mt.affect_appearance = 0 AND tc.name = '工程部') OR (mt.affect_appearance = 1 AND tc.name = '设计部')) \s
               		                AND st.t_project_id=?
                                       AND rt.deleted_at IS NULL
                                       AND st.deleted_at IS NULL
                                       AND mt.deleted_at IS NULL
                               )
                               AND s.deleted_at IS NULL
                       )
            limit ?,?
            """, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs,
                                 int rowNum) throws SQLException {
                return rs.getString(1);
            }
        }, projectId, projectId , pageNo * pageSize, pageSize);
    }

    private List<String> getPageQueryOfReviewedAndApprovedPageByProjectIdAndNameHelper(String projectId,
                                                                               String name,
                                                                               int pageNo,
                                                                               int pageSize) {
        name = "%" + name + "%";
        return jdbcTemplate.query("""
            
                SELECT DISTINCT (u.id)
                       FROM t_use_material u
                        LEFT JOIN t_project_material ON u.t_project_material_id = t_project_material.id
                        LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
                       WHERE u.t_use_material_brand_select_id IN (
                           SELECT DISTINCT (s.id)
                           FROM t_use_material_brand_select  s
                           LEFT JOIN t_project_appearance_review_mode  m ON m.t_use_material_brand_select_id=s.id
                           LEFT JOIN t_project_appearance_review  r ON r.t_project_appearance_review_mode_id=m.id
                           WHERE s.t_project_id=?
                               AND s.id  in (
               	                SELECT DISTINCT (st.id)
               	                FROM t_use_material_brand_select  st
               	                LEFT JOIN t_project_appearance_review_mode  mt ON mt.t_use_material_brand_select_id=st.id
               	                LEFT JOIN t_project_appearance_review  rt ON rt.t_project_appearance_review_mode_id=mt.id
           											LEFT JOIN t_company_user tu ON tu.t_user_id = mt.t_user_id
           											LEFT JOIN t_company tc ON tu.t_company_id = tc.id
               	                WHERE rt.review_result =1
           											  AND ((mt.affect_appearance = 0 AND tc.name = '工程部') OR (mt.affect_appearance = 1 AND tc.name = '设计部')) \s
               		                AND st.t_project_id=?
                                       AND rt.deleted_at IS NULL
                                       AND st.deleted_at IS NULL
                                       AND mt.deleted_at IS NULL
                               )
                               AND s.deleted_at IS NULL
                       ) AND t_material.name LIKE ?
            limit ?,?
            """, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs,
                                 int rowNum) throws SQLException {
                return rs.getString(1);
            }
        }, projectId, projectId, name, pageNo * pageSize, pageSize);
    }

    private List<String> getPageQueryOfReviewedAndApprovedPageByProjectIdAndLocationHelper(String projectId,
                                                                               String location,
                                                                               int pageNo,
                                                                               int pageSize) {
        location = "%" + location + "%";
        return jdbcTemplate.query("""
            
                SELECT DISTINCT (u.id)
                       FROM t_use_material u
                        LEFT JOIN t_project_material ON u.t_project_material_id = t_project_material.id
                        LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
                       WHERE u.t_use_material_brand_select_id IN (
                           SELECT DISTINCT (s.id)
                           FROM t_use_material_brand_select  s
                           LEFT JOIN t_project_appearance_review_mode  m ON m.t_use_material_brand_select_id=s.id
                           LEFT JOIN t_project_appearance_review  r ON r.t_project_appearance_review_mode_id=m.id
                           WHERE s.t_project_id=?
                               AND s.id  in (
               	                SELECT DISTINCT (st.id)
               	                FROM t_use_material_brand_select  st
               	                LEFT JOIN t_project_appearance_review_mode  mt ON mt.t_use_material_brand_select_id=st.id
               	                LEFT JOIN t_project_appearance_review  rt ON rt.t_project_appearance_review_mode_id=mt.id
           											LEFT JOIN t_company_user tu ON tu.t_user_id = mt.t_user_id
           											LEFT JOIN t_company tc ON tu.t_company_id = tc.id
               	                WHERE rt.review_result =1
           											  AND ((mt.affect_appearance = 0 AND tc.name = '工程部') OR (mt.affect_appearance = 1 AND tc.name = '设计部')) \s
               		                AND st.t_project_id=?
                                       AND rt.deleted_at IS NULL
                                       AND st.deleted_at IS NULL
                                       AND mt.deleted_at IS NULL
                               )
                               AND s.deleted_at IS NULL
                       ) AND t_material.location LIKE ?
            limit ?,?
            """, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs,
                                 int rowNum) throws SQLException {
                return rs.getString(1);
            }
        }, projectId, projectId, location, pageNo * pageSize, pageSize);
    }

    private List<String> getPageQueryOfReviewedAndApprovedPageByProjectIdAndItemMarkHelper(String projectId,
                                                                               String itemMark,
                                                                               int pageNo,
                                                                               int pageSize) {
        itemMark = "%" + itemMark + "%";
        return jdbcTemplate.query("""
            
                SELECT DISTINCT (u.id)
                       FROM t_use_material u
                        LEFT JOIN t_project_material ON u.t_project_material_id = t_project_material.id
                        LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
                       WHERE u.t_use_material_brand_select_id IN (
                           SELECT DISTINCT (s.id)
                           FROM t_use_material_brand_select  s
                           LEFT JOIN t_project_appearance_review_mode  m ON m.t_use_material_brand_select_id=s.id
                           LEFT JOIN t_project_appearance_review  r ON r.t_project_appearance_review_mode_id=m.id
                           WHERE s.t_project_id=?
                               AND s.id  in (
               	                SELECT DISTINCT (st.id)
               	                FROM t_use_material_brand_select  st
               	                LEFT JOIN t_project_appearance_review_mode  mt ON mt.t_use_material_brand_select_id=st.id
               	                LEFT JOIN t_project_appearance_review  rt ON rt.t_project_appearance_review_mode_id=mt.id
           											LEFT JOIN t_company_user tu ON tu.t_user_id = mt.t_user_id
           											LEFT JOIN t_company tc ON tu.t_company_id = tc.id
               	                WHERE rt.review_result =1
           											  AND ((mt.affect_appearance = 0 AND tc.name = '工程部') OR (mt.affect_appearance = 1 AND tc.name = '设计部')) \s
               		                AND st.t_project_id=?
                                       AND rt.deleted_at IS NULL
                                       AND st.deleted_at IS NULL
                                       AND mt.deleted_at IS NULL
                               )
                               AND s.deleted_at IS NULL
                       ) AND t_material.item_mark LIKE ?
            limit ?,?
            """, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs,
                                 int rowNum) throws SQLException {
                return rs.getString(1);
            }
        }, projectId, projectId, itemMark, pageNo * pageSize, pageSize);
    }

    private List<String> getPageQueryOfReviewedAndApprovedPageByProjectIdAndTechnologyHelper(String projectId,
                                                                               String technology,
                                                                               int pageNo,
                                                                               int pageSize) {
        technology = "%" + technology + "%";
        return jdbcTemplate.query("""
            
                SELECT DISTINCT (u.id)
                       FROM t_use_material u
                        LEFT JOIN t_project_material ON u.t_project_material_id = t_project_material.id
                        LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
                       WHERE u.t_use_material_brand_select_id IN (
                           SELECT DISTINCT (s.id)
                           FROM t_use_material_brand_select  s
                           LEFT JOIN t_project_appearance_review_mode  m ON m.t_use_material_brand_select_id=s.id
                           LEFT JOIN t_project_appearance_review  r ON r.t_project_appearance_review_mode_id=m.id
                           WHERE s.t_project_id=?
                               AND s.id  in (
               	                SELECT DISTINCT (st.id)
               	                FROM t_use_material_brand_select  st
               	                LEFT JOIN t_project_appearance_review_mode  mt ON mt.t_use_material_brand_select_id=st.id
               	                LEFT JOIN t_project_appearance_review  rt ON rt.t_project_appearance_review_mode_id=mt.id
           											LEFT JOIN t_company_user tu ON tu.t_user_id = mt.t_user_id
           											LEFT JOIN t_company tc ON tu.t_company_id = tc.id
               	                WHERE rt.review_result =1
           											  AND ((mt.affect_appearance = 0 AND tc.name = '工程部') OR (mt.affect_appearance = 1 AND tc.name = '设计部')) \s
               		                AND st.t_project_id=?
                                       AND rt.deleted_at IS NULL
                                       AND st.deleted_at IS NULL
                                       AND mt.deleted_at IS NULL
                               )
                               AND s.deleted_at IS NULL
                       ) AND t_material.technology LIKE ?
            limit ?,?
            """, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs,
                                 int rowNum) throws SQLException {
                return rs.getString(1);
            }
        }, projectId, projectId, technology, pageNo * pageSize, pageSize);
    }

    private List<String> getPageQueryOfReviewedAndApprovedPageByProjectIdAndInstallationHelper(String projectId,
                                                                               String installation,
                                                                               int pageNo,
                                                                               int pageSize) {
        installation = "%" + installation + "%";
        return jdbcTemplate.query("""
            
                SELECT DISTINCT (u.id)
                       FROM t_use_material u
                        LEFT JOIN t_project_material ON u.t_project_material_id = t_project_material.id
                        LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
                       WHERE u.t_use_material_brand_select_id IN (
                           SELECT DISTINCT (s.id)
                           FROM t_use_material_brand_select  s
                           LEFT JOIN t_project_appearance_review_mode  m ON m.t_use_material_brand_select_id=s.id
                           LEFT JOIN t_project_appearance_review  r ON r.t_project_appearance_review_mode_id=m.id
                           WHERE s.t_project_id=?
                               AND s.id  in (
               	                SELECT DISTINCT (st.id)
               	                FROM t_use_material_brand_select  st
               	                LEFT JOIN t_project_appearance_review_mode  mt ON mt.t_use_material_brand_select_id=st.id
               	                LEFT JOIN t_project_appearance_review  rt ON rt.t_project_appearance_review_mode_id=mt.id
           											LEFT JOIN t_company_user tu ON tu.t_user_id = mt.t_user_id
           											LEFT JOIN t_company tc ON tu.t_company_id = tc.id
               	                WHERE rt.review_result =1
           											  AND ((mt.affect_appearance = 0 AND tc.name = '工程部') OR (mt.affect_appearance = 1 AND tc.name = '设计部')) \s
               		                AND st.t_project_id=?
                                       AND rt.deleted_at IS NULL
                                       AND st.deleted_at IS NULL
                                       AND mt.deleted_at IS NULL
                               )
                               AND s.deleted_at IS NULL
                       ) AND t_material.installation LIKE ?
            limit ?,?
            """, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs,
                                 int rowNum) throws SQLException {
                return rs.getString(1);
            }
        }, projectId, projectId, installation, pageNo * pageSize, pageSize);
    }

    private List<String> getPageQueryOfReviewedAndApprovedPageByProjectIdAndBrandHelper(String projectId,
                                                                               String brand,
                                                                               int pageNo,
                                                                               int pageSize) {
        brand = "%" + brand + "%";
        return jdbcTemplate.query("""
            
                SELECT DISTINCT (u.id)
                       FROM t_use_material u
                        LEFT JOIN t_project_material_brand_public ON u.t_project_material_brand_public_id = t_project_material_brand_public.id
                        LEFT JOIN t_project_material_brand_private ON u.t_project_material_brand_private_id = t_project_material_brand_private.id
                        LEFT JOIN t_brand_public ON t_project_material_brand_public.t_brand_public_id = t_brand_public.id
                        LEFT JOIN t_project_brand ON t_project_material_brand_private.t_project_brand_id = t_project_brand.id
                        LEFT JOIN t_brand ON t_project_brand.t_brand_id = t_brand.id OR t_brand_public.t_brand_id = t_brand.id
                       WHERE u.t_use_material_brand_select_id IN (
                           SELECT DISTINCT (s.id)
                           FROM t_use_material_brand_select  s
                           LEFT JOIN t_project_appearance_review_mode  m ON m.t_use_material_brand_select_id=s.id
                           LEFT JOIN t_project_appearance_review  r ON r.t_project_appearance_review_mode_id=m.id
                           WHERE s.t_project_id=?
                               AND s.id  in (
               	                SELECT DISTINCT (st.id)
               	                FROM t_use_material_brand_select  st
               	                LEFT JOIN t_project_appearance_review_mode  mt ON mt.t_use_material_brand_select_id=st.id
               	                LEFT JOIN t_project_appearance_review  rt ON rt.t_project_appearance_review_mode_id=mt.id
           											LEFT JOIN t_company_user tu ON tu.t_user_id = mt.t_user_id
           											LEFT JOIN t_company tc ON tu.t_company_id = tc.id
               	                WHERE rt.review_result =1
           											  AND ((mt.affect_appearance = 0 AND tc.name = '工程部') OR (mt.affect_appearance = 1 AND tc.name = '设计部')) \s
               		                AND st.t_project_id=?
                                       AND rt.deleted_at IS NULL
                                       AND st.deleted_at IS NULL
                                       AND mt.deleted_at IS NULL
                               )
                               AND s.deleted_at IS NULL
                       ) AND t_brand.name Like ?
            limit ?,?
            """, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs,
                                 int rowNum) throws SQLException {
                return rs.getString(1);
            }
        }, projectId, projectId, brand, pageNo * pageSize, pageSize);
    }

    private List<UseMaterial> getPageQueryOfReviewedAndApprovedPageByProjectId(String projectId,
                                                                               int pageNo,
                                                                               int pageSize) {
        List<String> ids = getPageQueryOfReviewedAndApprovedPageByProjectIdHelper(projectId, pageNo, pageSize);

        if(ids.isEmpty()){
            return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE deleted_at IS NULL                                       
                                          """,
                                      new UseMaterialMapper());
        }


        StringBuilder idsStr = new StringBuilder();


            for (String id : ids) {
                if(idsStr.isEmpty())
                    idsStr.append("'").append(id).append("'");
                else
                    idsStr.append(",").append("'").append(id).append("'");
            }


        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE deleted_at IS NULL   
                                          AND id IN (
                                          """+idsStr+")",
                                  new UseMaterialMapper());


    }

    private List<UseMaterial> getPageQueryOfReviewedAndApprovedPageByProjectIdAndName(String projectId,
                                                                               String name,
                                                                               int pageNo,
                                                                               int pageSize) {
        List<String> ids = getPageQueryOfReviewedAndApprovedPageByProjectIdAndNameHelper(projectId, name, pageNo, pageSize);

        if(ids.isEmpty()){
            return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE deleted_at IS NULL                                       
                                          """,
                                      new UseMaterialMapper());
        }


        StringBuilder idsStr = new StringBuilder();


            for (String id : ids) {
                if(idsStr.isEmpty())
                    idsStr.append("'").append(id).append("'");
                else
                    idsStr.append(",").append("'").append(id).append("'");
            }


        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE deleted_at IS NULL   
                                          AND id IN (
                                          """+idsStr+")",
                                  new UseMaterialMapper());


    }

    private List<UseMaterial> getPageQueryOfReviewedAndApprovedPageByProjectIdAndLocation(String projectId,
                                                                               String location,
                                                                               int pageNo,
                                                                               int pageSize) {
        List<String> ids = getPageQueryOfReviewedAndApprovedPageByProjectIdAndLocationHelper(projectId, location, pageNo, pageSize);

        if(ids.isEmpty()){
            return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE deleted_at IS NULL                                       
                                          """,
                                      new UseMaterialMapper());
        }


        StringBuilder idsStr = new StringBuilder();


            for (String id : ids) {
                if(idsStr.isEmpty())
                    idsStr.append("'").append(id).append("'");
                else
                    idsStr.append(",").append("'").append(id).append("'");
            }


        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE deleted_at IS NULL   
                                          AND id IN (
                                          """+idsStr+")",
                                  new UseMaterialMapper());


    }

    private List<UseMaterial> getPageQueryOfReviewedAndApprovedPageByProjectIdAndItemMark(String projectId,
                                                                               String itemMark,
                                                                               int pageNo,
                                                                               int pageSize) {
        List<String> ids = getPageQueryOfReviewedAndApprovedPageByProjectIdAndItemMarkHelper(projectId, itemMark, pageNo, pageSize);

        if(ids.isEmpty()){
            return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE deleted_at IS NULL                                       
                                          """,
                                      new UseMaterialMapper());
        }


        StringBuilder idsStr = new StringBuilder();


            for (String id : ids) {
                if(idsStr.isEmpty())
                    idsStr.append("'").append(id).append("'");
                else
                    idsStr.append(",").append("'").append(id).append("'");
            }


        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE deleted_at IS NULL   
                                          AND id IN (
                                          """+idsStr+")",
                                  new UseMaterialMapper());


    }

    private List<UseMaterial> getPageQueryOfReviewedAndApprovedPageByProjectIdAndTechnology(String projectId,
                                                                               String technology,
                                                                               int pageNo,
                                                                               int pageSize) {
        List<String> ids = getPageQueryOfReviewedAndApprovedPageByProjectIdAndTechnologyHelper(projectId, technology, pageNo, pageSize);

        if(ids.isEmpty()){
            return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE deleted_at IS NULL                                       
                                          """,
                                      new UseMaterialMapper());
        }


        StringBuilder idsStr = new StringBuilder();


            for (String id : ids) {
                if(idsStr.isEmpty())
                    idsStr.append("'").append(id).append("'");
                else
                    idsStr.append(",").append("'").append(id).append("'");
            }


        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE deleted_at IS NULL   
                                          AND id IN (
                                          """+idsStr+")",
                                  new UseMaterialMapper());


    }

    private List<UseMaterial> getPageQueryOfReviewedAndApprovedPageByProjectIdAndInstallation(String projectId,
                                                                               String installation,
                                                                               int pageNo,
                                                                               int pageSize) {
        List<String> ids = getPageQueryOfReviewedAndApprovedPageByProjectIdAndInstallationHelper(projectId, installation, pageNo, pageSize);

        if(ids.isEmpty()){
            return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE deleted_at IS NULL                                       
                                          """,
                                      new UseMaterialMapper());
        }


        StringBuilder idsStr = new StringBuilder();


            for (String id : ids) {
                if(idsStr.isEmpty())
                    idsStr.append("'").append(id).append("'");
                else
                    idsStr.append(",").append("'").append(id).append("'");
            }


        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE deleted_at IS NULL   
                                          AND id IN (
                                          """+idsStr+")",
                                  new UseMaterialMapper());


    }

    private List<UseMaterial> getPageQueryOfReviewedAndApprovedPageByProjectIdAndBrand(String projectId,
                                                                               String brand,
                                                                               int pageNo,
                                                                               int pageSize) {
        List<String> ids = getPageQueryOfReviewedAndApprovedPageByProjectIdAndBrandHelper(projectId, brand, pageNo, pageSize);

        if(ids.isEmpty()){
            return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE deleted_at IS NULL                                       
                                          """,
                                      new UseMaterialMapper());
        }


        StringBuilder idsStr = new StringBuilder();


            for (String id : ids) {
                if(idsStr.isEmpty())
                    idsStr.append("'").append(id).append("'");
                else
                    idsStr.append(",").append("'").append(id).append("'");
            }


        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE deleted_at IS NULL   
                                          AND id IN (
                                          """+idsStr+")",
                                  new UseMaterialMapper());


    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<UseMaterial> getPageQuery(int pageNo,
                                           int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE deleted_at IS NULL   
                                          LIMIT ?,?
                                          """,
                                  new UseMaterialMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_material_id）+获得指定页面数据
     *
     * @param projectMaterialId 项目物料ID
     * @param pageNo            页号，从1开始
     * @param pageSize          每页的记录数
     */
    private List<UseMaterial> getPageQueryByProjectMaterialId(String projectMaterialId,
                                                              int pageNo,
                                                              int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE t_project_material_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new UseMaterialMapper(), projectMaterialId, pageNo * pageSize, pageSize);
    }

    private List<UseMaterial> getPageQueryByUseMaterialBrandSelectId(String useMaterialBrandSelectId,
                                                                     int pageNo,
                                                                     int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE t_use_material_brand_select_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new UseMaterialMapper(), useMaterialBrandSelectId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId   用户ID
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<UseMaterial> getPageQueryByUserId(String userId,
                                                   int pageNo,
                                                   int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new UseMaterialMapper(), userId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_material_brand_private_id）+获得指定页面数据
     *
     * @param projectMaterialBrandPrivateId 项目物料品牌私有ID
     * @param pageNo                        页号，从1开始
     * @param pageSize                      每页的记录数
     */
    private List<UseMaterial> getPageQueryByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,
                                                                          int pageNo,
                                                                          int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE t_project_material_brand_private_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new UseMaterialMapper(), projectMaterialBrandPrivateId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_material_brand_public_id）+获得指定页面数据
     *
     * @param projectMaterialBrandPublicId 项目物料品牌公共ID
     * @param pageNo                       页号，从1开始
     * @param pageSize                     每页的记录数
     */
    private List<UseMaterial> getPageQueryByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,
                                                                         int pageNo,
                                                                         int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_use_material
                                          WHERE t_project_material_brand_public_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new UseMaterialMapper(), projectMaterialBrandPublicId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class UseMaterialMapper implements RowMapper<UseMaterial> {
        @Override
        public UseMaterial mapRow(ResultSet rs,
                                  int rowNum) throws SQLException {
            UseMaterial useMaterial = new UseMaterial();
            useMaterial.setId(rs.getString("id"));
            useMaterial.setProjectMaterialId(rs.getString("t_project_material_id"));
            useMaterial.setUserId(rs.getString("t_user_id"));
            useMaterial.setProjectMaterialBrandPrivateId(rs.getString("t_project_material_brand_private_id"));
            useMaterial.setProjectMaterialBrandPublicId(rs.getString("t_project_material_brand_public_id"));
            useMaterial.setUseMaterialBrandSelectId(rs.getString("t_use_material_brand_select_id"));
            useMaterial.setMaterialId(rs.getString("t_material_id"));
            useMaterial.setMaterialCount(rs.getBigDecimal("material_count"));
            useMaterial.setMaterialUnit(rs.getString("material_unit"));
            useMaterial.setIsAppearance(rs.getInt("is_appearance"));
            useMaterial.setCreateDatetime(rs.getTimestamp("create_datetime"));
            useMaterial.setDeletedAt(rs.getTimestamp("deleted_at"));
            return useMaterial;
        }
    }

}