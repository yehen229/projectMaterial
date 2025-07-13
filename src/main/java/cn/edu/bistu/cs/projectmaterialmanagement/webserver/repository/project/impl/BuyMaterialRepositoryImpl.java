package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.BuyMaterial;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.Factory;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IBuyMaterialRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class BuyMaterialRepositoryImpl implements IBuyMaterialRepository {
    private final JdbcTemplate jdbcTemplate;

    public BuyMaterialRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(BuyMaterial buyMaterial) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                        INSERT INTO t_buy_material(id,
                        t_user_id,
                        t_use_material_id,
                        t_project_material_brand_private_id,
                        t_project_material_brand_public_id,
                        t_buy_material_batch_id,
                        material_count,
                        material_unit,
                        batch,
                        qrcode,
                        create_datetime)
                        VALUES(?,?,?,?,?,?,?,?,?,?,?)
                        """,
                newId,
                buyMaterial.getUserId(),
                buyMaterial.getUseMaterialId(),
                buyMaterial.getProjectMaterialBrandPrivateId(),
                buyMaterial.getProjectMaterialBrandPublicId(),
                buyMaterial.getBuyMaterialBatchId(),
                buyMaterial.getMaterialCount(),
                buyMaterial.getMaterialUnit(),
                buyMaterial.getBatch(),
                newId,
                new Date()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(BuyMaterial buyMaterial) {
        if (buyMaterial == null) return 0;
        return jdbcTemplate.update("""
                        UPDATE t_buy_material
                        SET deleted_at=? 
                        WHERE id=?
                        """, new Date(),
                buyMaterial.getId());
    }

    /**
     * update
     */
    @Override
    public int update(BuyMaterial buyMaterial) {
        return jdbcTemplate.update("""
                        UPDATE t_buy_material
                        SET t_user_id=?,
                        t_use_material_id=?,
                        t_project_material_brand_private_id=?,
                        t_project_material_brand_public_id=?,
                        t_buy_material_batch_id=?,
                        material_count=?,
                        material_unit=?,
                        batch=?,
                        qrcode=?,
                        create_datetime=?,
                        deleted_at=? 
                        WHERE id=?
                        """,
                buyMaterial.getUserId(),
                buyMaterial.getUseMaterialId(),
                buyMaterial.getProjectMaterialBrandPrivateId(),
                buyMaterial.getProjectMaterialBrandPublicId(),
                buyMaterial.getBuyMaterialBatchId(),
                buyMaterial.getMaterialCount(),
                buyMaterial.getMaterialUnit(),
                buyMaterial.getBatch(),
                buyMaterial.getQrcode(),
                buyMaterial.getCreateDatetime(),
                buyMaterial.getDeletedAt(),
                buyMaterial.getId());
    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("""
                         UPDATE t_buy_material
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
                         UPDATE t_buy_material
                        SET deleted_at=?  
                        WHERE t_user_id=?
                        """, new Date(),
                userId);
    }

    /**
     * 根据useMaterialId删除记录
     */
    @Override
    public int deleteByUseMaterialId(String useMaterialId) {
        return jdbcTemplate.update("""
                         UPDATE t_buy_material
                        SET deleted_at=? 
                        WHERE t_use_material_id=?
                        """, new Date(),
                useMaterialId);
    }

    /**
     * 根据projectMaterialBrandPrivateId删除记录
     */
    @Override
    public int deleteByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        return jdbcTemplate.update("""
                         UPDATE t_buy_material
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
                         UPDATE t_buy_material
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
                        FROM t_buy_material
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
                        FROM t_buy_material
                        WHERE t_user_id=?
                        """,
                Integer.class, userId);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_buy_material
                        LEFT JOIN t_use_material ON t_buy_material.t_use_material_id = t_use_material.id
                        LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id
                        WHERE t_project_material.t_project_id=?
                        AND  t_buy_material.deleted_at IS  null 
                        AND  t_use_material.deleted_at IS  null  
                        AND  t_project_material.deleted_at IS  null  
                        """,
                Integer.class, projectId);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountReCheckIsRequiredByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_buy_material
                        LEFT JOIN t_use_material ON t_buy_material.t_use_material_id = t_use_material.id
                        LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id
                        WHERE t_project_material.t_project_id=?
                        AND  t_buy_material.deleted_at IS  null 
                        AND  t_use_material.deleted_at IS  null  
                        AND  t_project_material.deleted_at IS  null  AND t_buy_material.id NOT IN(
                        SELECT t_buy_material_id
                        FROM t_project_material_retest                                                        
                        )
                        """,
                Integer.class, projectId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键UseMaterialId得到总数量
     */
    @Override
    public int getCountByUseMaterialId(String useMaterialId) {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_buy_material
                        WHERE t_use_material_id=?
                        """,
                Integer.class, useMaterialId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectMaterialBrandPrivateId得到总数量
     */
    @Override
    public int getCountByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_buy_material
                        WHERE t_project_material_brand_private_id=?
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
                        FROM t_buy_material
                        WHERE t_project_material_brand_public_id=?
                        """,
                Integer.class, projectMaterialBrandPublicId);
        return i;
    }
    @Override
    public int findMaxBatchByBatchId(String projectId){
        Integer maxBatch = jdbcTemplate.queryForObject(
                """
                SELECT COALESCE(MAX(bm.batch), 0)
                FROM t_buy_material bm
                JOIN t_buy_material_batch bmb ON bm.t_buy_material_batch_id = bmb.id
                WHERE bmb.t_project_id = ?
                """,
                Integer.class,
                projectId
        );
        return (maxBatch == null ? 0 : maxBatch) + 1;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public BuyMaterial getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                SELECT count(*) 
                FROM t_buy_material 
                WHERE id=?
                """, Integer.class, id);
        if (i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                        SELECT * 
                        FROM t_buy_material
                        WHERE id=?
                        """,
                new BuyMaterialMapper(), id);
    }


    public BuyMaterial getByqrcode(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                SELECT count(*) 
                FROM t_buy_material 
                WHERE qrcode=?
                """, Integer.class, id);
        if (i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                        SELECT * 
                        FROM t_buy_material
                        WHERE qrcode=?
                        """,
                new BuyMaterialMapper(), id);
    }

    @Override
    public List<BuyMaterial> getallinfo() {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_buy_material
                        """,
                Integer.class);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                         SELECT * 
                         FROM t_buy_material 
                        """,
                new BuyMaterialMapper());
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<BuyMaterial> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_buy_material
                        WHERE t_user_id=?
                        """,
                Integer.class, userId);
        if (i == 0)
            return null;

        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_buy_material 
                        WHERE t_user_id=?
                        """,
                new BuyMaterialMapper(), userId);
    }

    /**
     * 根据useMaterialId得到记录
     */
    @Override
    public List<BuyMaterial> getByUseMaterialId(String useMaterialId) {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_buy_material
                        WHERE t_use_material_id=?
                        """,
                Integer.class, useMaterialId);
        if (i == 0)
            return null;

        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_buy_material 
                        WHERE t_use_material_id=?
                        """,
                new BuyMaterialMapper(), useMaterialId);
    }

    /**
     * 根据projectMaterialBrandPrivateId得到记录
     */
    @Override
    public List<BuyMaterial> getByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId) {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_buy_material
                        WHERE t_project_material_brand_private_id=?
                        """,
                Integer.class, projectMaterialBrandPrivateId);
        if (i == 0)
            return null;

        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_buy_material 
                        WHERE t_project_material_brand_private_id=?
                        """,
                new BuyMaterialMapper(), projectMaterialBrandPrivateId);
    }

    /**
     * 根据projectMaterialBrandPublicId得到记录
     */
    @Override
    public List<BuyMaterial> getByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId) {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_buy_material
                        WHERE t_project_material_brand_public_id=?
                        """,
                Integer.class, projectMaterialBrandPublicId);
        if (i == 0)
            return null;

        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_buy_material 
                        WHERE t_project_material_brand_public_id=?
                        """,
                new BuyMaterialMapper(), projectMaterialBrandPublicId);
    }


    /*
     * 通过二维码找到记录
     * */
    public List<BuyMaterial> getByqrcodeId(String qrcode) {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_buy_material
                        WHERE qrcode=?
                        """,
                Integer.class, qrcode);
        if (i == 0)
            return null;

        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_buy_material 
                        WHERE qrcode=?
                        """,
                new BuyMaterialMapper(), qrcode);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<BuyMaterial> getPage(int pageNo,
                                     int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<BuyMaterial> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<BuyMaterial> getPageByUserId(String userId,
                                             int pageNo,
                                             int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<BuyMaterial> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param useMaterialId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    @Override
    public Page<BuyMaterial> getPageByUseMaterialId(String useMaterialId,
                                                    int pageNo,
                                                    int pageSize) {
        long totalCount = getCountByUseMaterialId(useMaterialId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<BuyMaterial> resultData = getPageQueryByUseMaterialId(useMaterialId, pageNo - 1, pageSize);
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
    public Page<BuyMaterial> getPageByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,
                                                                    int pageNo,
                                                                    int pageSize) {
        long totalCount = getCountByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<BuyMaterial> resultData = getPageQueryByProjectMaterialBrandPrivateId(projectMaterialBrandPrivateId,
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
    public Page<BuyMaterial> getPageByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,
                                                                   int pageNo,
                                                                   int pageSize) {
        long totalCount = getCountByProjectMaterialBrandPublicId(projectMaterialBrandPublicId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<BuyMaterial> resultData = getPageQueryByProjectMaterialBrandPublicId(projectMaterialBrandPublicId,
                pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<BuyMaterial> getPageByProjectId(String projectId,
                                                Integer pageNo,
                                                Integer pageSize) {
        long totalCount = getCountByProjectId(projectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<BuyMaterial> resultData = getPageQueryByProjectId(projectId,
                pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<BuyMaterial> getPageReCheckIsRequiredByProjectId(String projectId,
                                                                 Integer pageNo,
                                                                 Integer pageSize) {
        long totalCount = getCountReCheckIsRequiredByProjectId(projectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<BuyMaterial> resultData = getPageReCheckIsRequiredQueryByProjectId(projectId,
                pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    /**
     * 从服务器获得已经购买的、并且没有被禁止使用的物料
     *
     * @param projectId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<String> getBoughtMaterialIdPageByProjectId(String projectId,
                                                           Integer pageNo,
                                                           Integer pageSize) {
        long totalCount = getCountBoughtMaterialIdPageByProjectId(projectId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<String> resultData = getPageBoughtMaterialIdPageByProjectId(projectId,
                pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }
    /**
     * 从服务器获得已经购买的、并且没有被禁止使用的物料
     *
     * @param projectId
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<String> getBoughtMaterialIdPageByProjectIdAndName(String projectId,
                                                           String name,
                                                           Integer pageNo,
                                                           Integer pageSize) {
        long totalCount = getCountBoughtMaterialIdPageByProjectIdAndName(projectId, name);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<String> resultData = getPageBoughtMaterialIdPageByProjectIdAndName(projectId, name,
                pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }
    /**
     * 从服务器获得已经购买的、并且没有被禁止使用的物料
     *
     * @param projectId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<String> getBoughtMaterialIdPageByProjectIdAndLocation(String projectId,
                                                           String location,
                                                           Integer pageNo,
                                                           Integer pageSize) {
        long totalCount = getCountBoughtMaterialIdPageByProjectIdAndLocation(projectId, location);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<String> resultData = getPageBoughtMaterialIdPageByProjectIdAndLocation(projectId, location,
                pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }
    /**
     * 从服务器获得已经购买的、并且没有被禁止使用的物料
     *
     * @param projectId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<String> getBoughtMaterialIdPageByProjectIdAndItemMark(String projectId,
                                                           String itemMark,
                                                           Integer pageNo,
                                                           Integer pageSize) {
        long totalCount = getCountBoughtMaterialIdPageByProjectIdAndItemMark(projectId, itemMark);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<String> resultData = getPageBoughtMaterialIdPageByProjectIdAndItemMark(projectId, itemMark,
                pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }
    /**
     * 从服务器获得已经购买的、并且没有被禁止使用的物料
     *
     * @param projectId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<String> getBoughtMaterialIdPageByProjectIdAndTechnology(String projectId,
                                                           String technology,
                                                           Integer pageNo,
                                                           Integer pageSize) {
        long totalCount = getCountBoughtMaterialIdPageByProjectIdAndTechnology(projectId, technology);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<String> resultData = getPageBoughtMaterialIdPageByProjectIdAndTechnology(projectId, technology,
                pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }
    /**
     * 从服务器获得已经购买的、并且没有被禁止使用的物料
     *
     * @param projectId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<String> getBoughtMaterialIdPageByProjectIdAndInstallation(String projectId,
                                                           String installation,
                                                           Integer pageNo,
                                                           Integer pageSize) {
        long totalCount = getCountBoughtMaterialIdPageByProjectIdAndInstallation(projectId, installation);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<String> resultData = getPageBoughtMaterialIdPageByProjectIdAndInstallation(projectId, installation,
                pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }
    /**
     * 从服务器获得已经购买的、并且没有被禁止使用的物料
     *
     * @param projectId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<String> getBoughtMaterialIdPageByProjectIdAndBrand(String projectId,
                                                           String brand,
                                                           Integer pageNo,
                                                           Integer pageSize) {
        long totalCount = getCountBoughtMaterialIdPageByProjectIdAndBrand(projectId, brand);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<String> resultData = getPageBoughtMaterialIdPageByProjectIdAndBrand(projectId, brand,
                pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    @Override
    public List<String> getBoughtProjectMaterialBrandPrivateIdListPageByProjectIdAndMaterialId(String projectId,
                                                                                               String materialId) {

        return jdbcTemplate.query("""
                        SELECT distinct (t_buy_material.t_project_material_brand_private_id) 
                        FROM t_buy_material
                        LEFT JOIN t_use_material ON t_buy_material.t_use_material_id = t_use_material.id
                        LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id                                                        
                        WHERE t_project_material.t_project_id=?
                        AND  t_project_material.t_material_id=?
                        AND  t_buy_material.deleted_at IS  null 
                        AND  t_use_material.deleted_at IS  null  
                        AND  t_project_material.deleted_at IS  null  AND t_buy_material.id IN(
                        SELECT t_buy_material_id
                        FROM t_project_material_retest       
                        WHERE need_retest=0 OR review_result=1
                        )                                         
                        """, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs,
                                         int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                },
                projectId, materialId);

    }

    @Override
    public List<String> getBoughtProjectMaterialBrandPublicIdListPageByProjectIdAndMaterialId(String projectId,
                                                                                              String materialId) {
        return jdbcTemplate.query("""
                        SELECT distinct (t_buy_material.t_project_material_brand_public_id) 
                        FROM t_buy_material
                        LEFT JOIN t_use_material ON t_buy_material.t_use_material_id = t_use_material.id
                        LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id                                                        
                        WHERE t_project_material.t_project_id=?
                        AND  t_project_material.t_material_id=?
                        AND  t_buy_material.deleted_at IS  null 
                        AND  t_use_material.deleted_at IS  null  
                        AND  t_project_material.deleted_at IS  null  AND t_buy_material.id IN(
                        SELECT t_buy_material_id
                        FROM t_project_material_retest       
                        WHERE need_retest=0 OR review_result=1
                        )                                          
                        """, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs,
                                         int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                },
                projectId, materialId);
    }

    @Override
    public List<BuyMaterial> getByBuyMaterialBatchId(String buyMaterialBatchId) {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(*) 
                        FROM t_buy_material
                        WHERE t_buy_material_batch_id=?
                        AND  t_buy_material.deleted_at IS  null                           
                        """,
                                                Integer.class, buyMaterialBatchId);
        if (i == 0)
            return null;

        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_buy_material 
                        WHERE t_buy_material_batch_id=?
                        AND  t_buy_material.deleted_at IS  null
                        """,
                                  new BuyMaterialMapper(), buyMaterialBatchId);
    }

    @Override
    public List<Factory> getBrandAllFactory() {
        return jdbcTemplate.query("""
select use_material_id,t_brand.id as brand_id,factory_id from t_brand RIGHT JOIN (select use_material_id,t_material_brand.id as material_brand_id,t_material_brand.t_material_id,t_material_brand.t_brand_id  from t_material_brand RIGHT JOIN (select t_use_material.id as use_material_id,t_project_material.id as project_material_id,t_project_material.t_material_id  from t_use_material left JOIN t_project_material on t_use_material.t_project_material_id=t_project_material.id) as A on A.t_material_id=t_material_brand.t_material_id) as B on t_brand.id = B.t_brand_id
""",
                new FactoryMapper());
    }

    private int getCountBoughtMaterialIdPageByProjectId(String projectId) {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(distinct (t_project_material_id)) 
                        FROM t_use_material
                        LEFT JOIN t_buy_material ON t_buy_material.t_use_material_id = t_use_material.id
                        LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id                                                        
                        WHERE t_project_material.t_project_id=?
                        AND  t_buy_material.deleted_at IS  null 
                        AND  t_use_material.deleted_at IS  null  
                        AND  t_project_material.deleted_at IS  null  AND t_buy_material.id IN(
                        SELECT t_buy_material_id
                        FROM t_project_material_retest       
                        WHERE need_retest=0 OR (review_result=1 AND need_retest = 1 )
                        )
                        """,
                Integer.class, projectId);
        return i;
    }

    private int getCountBoughtMaterialIdPageByProjectIdAndName(String projectId, String name) {
        name = "%" + name + "%";
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(distinct (t_project_material_id)) 
                        FROM t_use_material
                        LEFT JOIN t_buy_material ON t_buy_material.t_use_material_id = t_use_material.id
                        LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id
                        LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
                        WHERE t_project_material.t_project_id=?
                        AND  t_buy_material.deleted_at IS  null 
                        AND  t_use_material.deleted_at IS  null  
                        AND  t_material.deleted_at IS  null  
                        AND  t_project_material.deleted_at IS  null  AND t_buy_material.id IN(
                        SELECT t_buy_material_id
                        FROM t_project_material_retest       
                        WHERE need_retest=0 OR (review_result=1 AND need_retest = 1 )
                        ) AND t_material.name LIKE ?
                        """,
                Integer.class, projectId, name);
        return i;
    }

    private int getCountBoughtMaterialIdPageByProjectIdAndLocation(String projectId, String location) {
        location = "%" + location + "%";
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(distinct (t_project_material_id)) 
                        FROM t_use_material
                        LEFT JOIN t_buy_material ON t_buy_material.t_use_material_id = t_use_material.id
                        LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id
                        LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
                        WHERE t_project_material.t_project_id=?
                        AND  t_buy_material.deleted_at IS  null 
                        AND  t_use_material.deleted_at IS  null
                        AND  t_material.deleted_at IS  null  
                        AND  t_project_material.deleted_at IS  null  AND t_buy_material.id IN(
                        SELECT t_buy_material_id
                        FROM t_project_material_retest       
                        WHERE need_retest=0 OR (review_result=1 AND need_retest = 1 )
                        ) AND t_material.location LIKE ?
                        """,
                Integer.class, projectId, location);
        return i;
    }

    private int getCountBoughtMaterialIdPageByProjectIdAndItemMark(String projectId, String itemMark) {
        itemMark = "%" + itemMark + "%";
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(distinct (t_project_material_id)) 
                        FROM t_use_material
                        LEFT JOIN t_buy_material ON t_buy_material.t_use_material_id = t_use_material.id
                        LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id
                        LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
                        WHERE t_project_material.t_project_id=?
                        AND  t_buy_material.deleted_at IS  null 
                        AND  t_use_material.deleted_at IS  null  
                        AND  t_material.deleted_at IS  null
                        AND  t_project_material.deleted_at IS  null  AND t_buy_material.id IN(
                        SELECT t_buy_material_id
                        FROM t_project_material_retest       
                        WHERE need_retest=0 OR (review_result=1 AND need_retest = 1 )
                        ) AND t_material.item_mark LIKE ?
                        """,
                Integer.class, projectId, itemMark);
        return i;
    }

    private int getCountBoughtMaterialIdPageByProjectIdAndTechnology(String projectId, String technology) {
        technology = "%" + technology + "%";
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(distinct (t_project_material_id)) 
                        FROM t_use_material
                        LEFT JOIN t_buy_material ON t_buy_material.t_use_material_id = t_use_material.id
                        LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id
                        LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
                        WHERE t_project_material.t_project_id=?
                        AND  t_buy_material.deleted_at IS  null 
                        AND  t_use_material.deleted_at IS  null
                        AND   t_material.deleted_at IS  null
                        AND  t_project_material.deleted_at IS  null  AND t_buy_material.id IN(
                        SELECT t_buy_material_id
                        FROM t_project_material_retest       
                        WHERE need_retest=0 OR (review_result=1 AND need_retest = 1 )
                        ) AND t_material.technology LIKE ?
                        """,
                Integer.class, projectId, technology);
        return i;
    }

    private int getCountBoughtMaterialIdPageByProjectIdAndInstallation(String projectId, String installation) {
        installation = "%" + installation + "%";
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(distinct (t_project_material_id)) 
                        FROM t_use_material
                        LEFT JOIN t_buy_material ON t_buy_material.t_use_material_id = t_use_material.id
                        LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id
                        LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
                        WHERE t_project_material.t_project_id=?
                        AND  t_buy_material.deleted_at IS  null 
                        AND  t_use_material.deleted_at IS  null
                        AND   t_material.deleted_at IS  null
                        AND  t_project_material.deleted_at IS  null  AND t_buy_material.id IN(
                        SELECT t_buy_material_id
                        FROM t_project_material_retest       
                        WHERE need_retest=0 OR (review_result=1 AND need_retest = 1 )
                        ) AND t_material.installation LIKE ?
                        """,
                Integer.class, projectId, installation);
        return i;
    }

    private int getCountBoughtMaterialIdPageByProjectIdAndBrand(String projectId, String brand) {
        brand = "%" + brand + "%";
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT count(distinct (t_use_material.t_project_material_id)) 
                        FROM t_use_material
                        LEFT JOIN t_buy_material ON t_buy_material.t_use_material_id = t_use_material.id
                        LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id
                        LEFT JOIN t_project_material_brand_private ON t_use_material.t_project_material_brand_private_id = t_project_material_brand_private.id
                        LEFT JOIN t_project_material_brand_public ON t_use_material.t_project_material_brand_public_id = t_project_material_brand_public.id
                        LEFT JOIN t_project_brand ON t_project_material_brand_private.t_project_brand_id = t_project_brand.id
                        LEFT JOIN t_brand_public ON t_project_material_brand_public.t_brand_public_id = t_brand_public.id
                        LEFT JOIN t_brand ON t_project_brand.t_brand_id = t_brand.id OR t_brand_public.t_brand_id = t_brand.id
                        WHERE t_project_material.t_project_id=?
                        AND  t_buy_material.deleted_at IS  null 
                        AND  t_use_material.deleted_at IS  null
                        AND t_project_material_brand_private.deleted_at IS  null
                        AND t_project_material_brand_public.deleted_at IS  null
                        AND t_project_brand.deleted_at IS  null
                        AND t_brand_public.deleted_at IS  null
                        AND t_brand.deleted_at IS  null
                        AND  t_project_material.deleted_at IS  null  AND t_buy_material.id IN(
                        SELECT t_buy_material_id
                        FROM t_project_material_retest       
                        WHERE need_retest=0 OR (review_result=1 AND need_retest = 1 )
                        ) AND t_brand.name LIKE ?
                        """,
                Integer.class, projectId, brand);
        return i;
    }

    private List<String> getPageBoughtMaterialIdPageByProjectId(String projectId,
                                                                int pageNo,
                                                                int pageSize) {
        return jdbcTemplate.query("""
                        SELECT distinct (t_project_material_id) 
                                      FROM t_use_material
                                      LEFT JOIN t_buy_material ON t_buy_material.t_use_material_id = t_use_material.id
                                      LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id                                                        
                                      WHERE t_project_material.t_project_id=?
                                      AND  t_buy_material.deleted_at IS  null 
                                      AND  t_use_material.deleted_at IS  null  
                                      AND  t_project_material.deleted_at IS  null  AND t_buy_material.id IN(
                                      SELECT t_buy_material_id
                                      FROM t_project_material_retest       
                                      WHERE need_retest=0 OR (review_result=1 AND need_retest = 1)
                                      )
                        LIMIT ?,?
                        """, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs,
                                         int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                },
                projectId, pageNo * pageSize, pageSize);
    }

    private List<String> getPageBoughtMaterialIdPageByProjectIdAndName(String projectId,
                                                                String name,
                                                                int pageNo,
                                                                int pageSize) {
        name = "%" + name + "%";
        return jdbcTemplate.query("""
                        SELECT distinct (t_project_material_id) 
                                      FROM t_use_material
                                      LEFT JOIN t_buy_material ON t_buy_material.t_use_material_id = t_use_material.id
                                      LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id
                                      LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
                                      WHERE t_project_material.t_project_id=?
                                      AND  t_buy_material.deleted_at IS  null 
                                      AND  t_use_material.deleted_at IS  null
                                        AND t_material.deleted_at IS  null
                                      AND  t_project_material.deleted_at IS  null  AND t_buy_material.id IN(
                                      SELECT t_buy_material_id
                                      FROM t_project_material_retest       
                                      WHERE need_retest=0 OR (review_result=1 AND need_retest = 1)
                                      ) AND t_material.name LIKE ?
                        LIMIT ?,?
                        """, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs,
                                         int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                },
                projectId, name, pageNo * pageSize, pageSize);
    }

    private List<String> getPageBoughtMaterialIdPageByProjectIdAndLocation(String projectId,
                                                                String location,
                                                                int pageNo,
                                                                int pageSize) {
        location = "%" + location + "%";
        return jdbcTemplate.query("""
                        SELECT distinct (t_project_material_id) 
                                      FROM t_use_material
                                      LEFT JOIN t_buy_material ON t_buy_material.t_use_material_id = t_use_material.id
                                      LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id
                                      LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
                                      WHERE t_project_material.t_project_id=?
                                      AND  t_buy_material.deleted_at IS  null 
                                      AND  t_use_material.deleted_at IS  null  
                                      AND  t_material.deleted_at IS  null  
                                      AND  t_project_material.deleted_at IS  null  AND t_buy_material.id IN(
                                      SELECT t_buy_material_id
                                      FROM t_project_material_retest       
                                      WHERE need_retest=0 OR (review_result=1 AND need_retest = 1)
                                      ) AND t_material.location LIKE ?
                        LIMIT ?,?
                        """, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs,
                                         int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                },
                projectId, location, pageNo * pageSize, pageSize);
    }

    private List<String> getPageBoughtMaterialIdPageByProjectIdAndItemMark(String projectId,
                                                                String itemMark,
                                                                int pageNo,
                                                                int pageSize) {
        itemMark = "%" + itemMark + "%";
        return jdbcTemplate.query("""
                        SELECT distinct (t_project_material_id) 
                                      FROM t_use_material
                                      LEFT JOIN t_buy_material ON t_buy_material.t_use_material_id = t_use_material.id
                                      LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id
                                      LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
                                      WHERE t_project_material.t_project_id=?
                                      AND  t_buy_material.deleted_at IS  null 
                                      AND  t_use_material.deleted_at IS  null  
                                      AND  t_material.deleted_at IS  null  
                                      AND  t_project_material.deleted_at IS  null  AND t_buy_material.id IN(
                                      SELECT t_buy_material_id
                                      FROM t_project_material_retest       
                                      WHERE need_retest=0 OR (review_result=1 AND need_retest = 1)
                                      ) AND t_material.item_mark LIKE ?
                        LIMIT ?,?
                        """, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs,
                                         int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                },
                projectId, itemMark, pageNo * pageSize, pageSize);
    }

    private List<String> getPageBoughtMaterialIdPageByProjectIdAndTechnology(String projectId,
                                                                String technology,
                                                                int pageNo,
                                                                int pageSize) {
        technology = "%" + technology + "%";
        return jdbcTemplate.query("""
                        SELECT distinct (t_project_material_id) 
                                      FROM t_use_material
                                      LEFT JOIN t_buy_material ON t_buy_material.t_use_material_id = t_use_material.id
                                      LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id
                                      LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
                                      WHERE t_project_material.t_project_id=?
                                      AND  t_buy_material.deleted_at IS  null 
                                      AND  t_use_material.deleted_at IS  null  
                                      AND  t_material.deleted_at IS  null  
                                      AND  t_project_material.deleted_at IS  null  AND t_buy_material.id IN(
                                      SELECT t_buy_material_id
                                      FROM t_project_material_retest       
                                      WHERE need_retest=0 OR (review_result=1 AND need_retest = 1)
                                      ) AND t_material.technology LIKE ?
                        LIMIT ?,?
                        """, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs,
                                         int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                },
                projectId, technology, pageNo * pageSize, pageSize);
    }

    private List<String> getPageBoughtMaterialIdPageByProjectIdAndInstallation(String projectId,
                                                                String installation,
                                                                int pageNo,
                                                                int pageSize) {
        installation = "%" + installation + "%";
        return jdbcTemplate.query("""
                        SELECT distinct (t_project_material_id) 
                                      FROM t_use_material
                                      LEFT JOIN t_buy_material ON t_buy_material.t_use_material_id = t_use_material.id
                                      LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id
                                      LEFT JOIN t_material ON t_project_material.t_material_id = t_material.id
                                      WHERE t_project_material.t_project_id=?
                                      AND  t_buy_material.deleted_at IS  null 
                                      AND  t_use_material.deleted_at IS  null  
                                      AND  t_material.deleted_at IS  null  
                                      AND  t_project_material.deleted_at IS  null  AND t_buy_material.id IN(
                                      SELECT t_buy_material_id
                                      FROM t_project_material_retest       
                                      WHERE need_retest=0 OR (review_result=1 AND need_retest = 1)
                                      ) AND t_material.installation LIKE ?
                        LIMIT ?,?
                        """, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs,
                                         int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                },
                projectId, installation, pageNo * pageSize, pageSize);
    }

    private List<String> getPageBoughtMaterialIdPageByProjectIdAndBrand(String projectId,
                                                                String brand,
                                                                int pageNo,
                                                                int pageSize) {
        brand = "%" + brand + "%";
        return jdbcTemplate.query("""
                        SELECT distinct (t_use_material.t_project_material_id) 
                                      FROM t_use_material
                                      LEFT JOIN t_buy_material ON t_buy_material.t_use_material_id = t_use_material.id
                                      LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id
                                        LEFT JOIN t_project_material_brand_private ON t_use_material.t_project_material_brand_private_id = t_project_material_brand_private.id
                                        LEFT JOIN t_project_material_brand_public ON t_use_material.t_project_material_brand_public_id = t_project_material_brand_public.id
                                        LEFT JOIN t_project_brand ON t_project_material_brand_private.t_project_brand_id = t_project_brand.id
                                        LEFT JOIN t_brand_public ON t_project_material_brand_public.t_brand_public_id = t_brand_public.id
                                        LEFT JOIN t_brand ON t_project_brand.t_brand_id = t_brand.id OR t_brand_public.t_brand_id = t_brand.id
                                      WHERE t_project_material.t_project_id=?
                                      AND  t_buy_material.deleted_at IS  null 
                                      AND  t_use_material.deleted_at IS  null  
                                      AND  t_project_material_brand_private.deleted_at IS  null  
                                      AND  t_project_material_brand_public.deleted_at IS  null  
                                      AND  t_project_brand.deleted_at IS  null  
                                      AND  t_brand_public.deleted_at IS  null  
                                      AND  t_brand.deleted_at IS  null  
                                      AND  t_project_material.deleted_at IS  null  AND t_buy_material.id IN(
                                      SELECT t_buy_material_id
                                      FROM t_project_material_retest       
                                      WHERE need_retest=0 OR (review_result=1 AND need_retest = 1)
                                      ) AND t_brand.name Like ?
                        LIMIT ?,?
                        """, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs,
                                         int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                },
                projectId, brand, pageNo * pageSize, pageSize);
    }

    private List<BuyMaterial> getPageReCheckIsRequiredQueryByProjectId(String projectId,
                                                                       int pageNo,
                                                                       int pageSize) {
        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_buy_material
                        LEFT JOIN t_use_material ON t_buy_material.t_use_material_id = t_use_material.id
                        LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id
                        WHERE t_project_material.t_project_id=?
                        AND  t_buy_material.deleted_at IS  null 
                        AND  t_use_material.deleted_at IS  null  
                        AND  t_project_material.deleted_at IS  null  AND t_buy_material.id NOT IN(
                        SELECT t_buy_material_id
                        FROM t_project_material_retest                                                        
                        )  
                        LIMIT ?,?
                        """,
                new BuyMaterialMapper(), projectId, pageNo * pageSize, pageSize);
    }

    private List<BuyMaterial> getPageQueryByProjectId(String projectId,
                                                      int pageNo,
                                                      int pageSize) {
        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_buy_material
                        LEFT JOIN t_use_material ON t_buy_material.t_use_material_id = t_use_material.id
                        LEFT JOIN t_project_material ON t_use_material.t_project_material_id = t_project_material.id
                        WHERE t_project_material.t_project_id=?
                        AND  t_buy_material.deleted_at IS  null 
                        AND  t_use_material.deleted_at IS  null  
                        AND  t_project_material.deleted_at IS  null   
                        LIMIT ?,?
                        """,
                new BuyMaterialMapper(), projectId, pageNo * pageSize, pageSize);
    }


    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<BuyMaterial> getPageQuery(int pageNo,
                                           int pageSize) {
        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_buy_material
                        LIMIT ?,?
                        """,
                new BuyMaterialMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<BuyMaterial> getPageQueryByUserId(String userId,
                                                   int pageNo,
                                                   int pageSize) {
        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_buy_material
                        WHERE t_user_id=? 
                        LIMIT ?,?
                        """,
                new BuyMaterialMapper(), userId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_use_material_id）+获得指定页面数据
     *
     * @param useMaterialId
     * @param pageNo        页号，从1开始
     * @param pageSize      每页的记录数
     */
    private List<BuyMaterial> getPageQueryByUseMaterialId(String useMaterialId,
                                                          int pageNo,
                                                          int pageSize) {
        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_buy_material
                        WHERE t_use_material_id=? 
                        LIMIT ?,?
                        """,
                new BuyMaterialMapper(), useMaterialId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_material_brand_private_id）+获得指定页面数据
     *
     * @param projectMaterialBrandPrivateId
     * @param pageNo                        页号，从1开始
     * @param pageSize                      每页的记录数
     */
    private List<BuyMaterial> getPageQueryByProjectMaterialBrandPrivateId(String projectMaterialBrandPrivateId,
                                                                          int pageNo,
                                                                          int pageSize) {
        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_buy_material
                        WHERE t_project_material_brand_private_id=? 
                        LIMIT ?,?
                        """,
                new BuyMaterialMapper(), projectMaterialBrandPrivateId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_material_brand_public_id）+获得指定页面数据
     *
     * @param projectMaterialBrandPublicId
     * @param pageNo                       页号，从1开始
     * @param pageSize                     每页的记录数
     */
    private List<BuyMaterial> getPageQueryByProjectMaterialBrandPublicId(String projectMaterialBrandPublicId,
                                                                         int pageNo,
                                                                         int pageSize) {
        return jdbcTemplate.query("""
                        SELECT * 
                        FROM t_buy_material
                        WHERE t_project_material_brand_public_id=? 
                        LIMIT ?,?
                        """,
                new BuyMaterialMapper(), projectMaterialBrandPublicId, pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class BuyMaterialMapper implements RowMapper<BuyMaterial> {
        @Override
        public BuyMaterial mapRow(ResultSet rs,
                                  int rowNum) throws SQLException {
            BuyMaterial buyMaterial = new BuyMaterial();
            buyMaterial.setId(rs.getString("id"));
            buyMaterial.setUserId(rs.getString("t_user_id"));
            buyMaterial.setUseMaterialId(rs.getString("t_use_material_id"));
            buyMaterial.setProjectMaterialBrandPrivateId(rs.getString("t_project_material_brand_private_id"));
            buyMaterial.setProjectMaterialBrandPublicId(rs.getString("t_project_material_brand_public_id"));
            buyMaterial.setBuyMaterialBatchId(rs.getString("t_buy_material_batch_id"));
            buyMaterial.setMaterialCount(rs.getBigDecimal("material_count"));
            buyMaterial.setMaterialUnit(rs.getString("material_unit"));
            buyMaterial.setQrcode(rs.getString("qrcode"));
            buyMaterial.setBatch(rs.getInt("batch"));
            buyMaterial.setCreateDatetime(rs.getTimestamp("create_datetime"));
            buyMaterial.setDeletedAt(rs.getTimestamp("deleted_at"));
            return buyMaterial;
        }
    }

    private static final class FactoryMapper implements RowMapper<Factory> {
        @Override
        public Factory mapRow(ResultSet rs,
                              int rowNum) throws SQLException {
            Factory factory = new Factory();
            factory.setUse_material_id(rs.getString("use_material_id"));
            factory.setFactory_id(rs.getString("factory_id"));
            factory.setBrand_id(rs.getString("brand_id"));
            return factory;
        }
    }

}