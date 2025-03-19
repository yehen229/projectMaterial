package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.*;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.IStatisticalanalysisRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StatisticalanalysisRepositoryImpl implements IStatisticalanalysisRepository {
    private final JdbcTemplate jdbcTemplate;

    public StatisticalanalysisRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    获取设计部审核项目id
    public  List<Stastisprojectidandcount> getdesignunpassorpassList(int result){
        return jdbcTemplate.query("""
                SELECT DISTINCT\s
                t_project_material.t_material_id,
                t_project_material.t_project_id,
                t_project_material.t_company_id
                FROM t_project_material,t_project_review_mode,t_project_review,t_project_review_user
                    WHERE t_project_material.t_company_id=t_project_review_mode.t_company_id\s
                       	AND  t_project_material.t_project_id=t_project_review_mode.t_project_id
                       	AND  t_project_review.t_project_review_mode_id=t_project_review_mode.id
                   	AND t_project_review.review_result= ?
                       	AND t_project_review.id =t_project_review_user.t_project_review_id                       
                     """, new DesignreviewMapper(), result);

    }
    private static final class DesignreviewMapper implements RowMapper<Stastisprojectidandcount> {
        @Override
        public Stastisprojectidandcount mapRow(ResultSet rs,
                                         int rowNum) throws SQLException {
            Stastisprojectidandcount unpassmaterialmessage = new Stastisprojectidandcount();
            // 去掉多余的逗号
            unpassmaterialmessage.setDesigntMaterialId(rs.getString("t_material_id"));
            unpassmaterialmessage.setDesignProjectId(rs.getString("t_project_id"));
            unpassmaterialmessage.setDesigntCompanyId(rs.getString("t_company_id"));
            return unpassmaterialmessage;
        }
    }
    public Integer getdesignunpassorpassCount(String projectid, int result){
       Integer i = jdbcTemplate.queryForObject("""                
               select  count(*)
               from t_project_review_user ,t_project_review
               WHERE t_project_review_user.t_project_review_id = t_project_review.id
               and t_project_review_user.review_result = ?
               and t_project_review.t_project_id=   ?
               """,
               Integer.class,result, projectid);
       if (i == null || i == 0)
           return null;
       return i;
   }

//    获取总包审核
    public  List<Stastisprojectidandcount> getzongbaounpassorpassList(int result){
    return jdbcTemplate.query("""
                       SELECT
                       t_project_material.t_project_id,
                       t_project_material.t_material_id,
                       MAX(t_project_appearance_review_user.review_datetime)  AS latest_review_time
                   FROM
                       t_project_material
                   JOIN t_use_material ON t_project_material.id  = t_use_material.t_project_material_id
                   JOIN t_project_appearance_review_mode ON t_use_material.t_use_material_brand_select_id = t_project_appearance_review_mode.t_use_material_brand_select_id
                   JOIN t_project_appearance_review ON t_project_appearance_review_mode.id  = t_project_appearance_review.t_project_appearance_review_mode_id
                   JOIN t_project_appearance_review_user ON t_project_appearance_review.id  = t_project_appearance_review_user.t_project_appearance_review_id
                   WHERE
                       t_project_appearance_review.review_result  = ?
                   GROUP BY
                       t_project_material.t_project_id,
                       t_project_material.t_material_id
                   ORDER BY
                       latest_review_time DESC                  
                     """, new ZongbaoreviewMapper(), result);
}
    private static final class ZongbaoreviewMapper implements RowMapper<Stastisprojectidandcount> {
        @Override
        public Stastisprojectidandcount mapRow(ResultSet rs,
                                         int rowNum) throws SQLException {
            Stastisprojectidandcount unpassmaterialmessage = new Stastisprojectidandcount();
            // 去掉多余的逗号
            unpassmaterialmessage.setZongbaoMaterialId(rs.getString("t_material_id"));
            unpassmaterialmessage.setZongbaoProjectId(rs.getString("t_project_id"));
            return unpassmaterialmessage;
        }
    }
    public Integer getzongbaounpassorpassCount(String projectid, int result){
        Integer i = jdbcTemplate.queryForObject("""                
                        SELECT   COUNT( DISTINCT t_project_appearance_review_mode.t_use_material_brand_select_id)
                         FROM t_project_material ,t_project_appearance_review,t_project_appearance_review_mode,t_project_appearance_review_user,t_use_material
                         WHERE t_project_material.id =t_use_material.t_project_material_id
                         	AND t_use_material.t_use_material_brand_select_id = t_project_appearance_review_mode.t_use_material_brand_select_id
                         	AND t_project_appearance_review_mode.id =t_project_appearance_review.t_project_appearance_review_mode_id
                         	AND t_project_appearance_review.review_result=?
                         	AND t_project_appearance_review.id =t_project_appearance_review_user.t_project_appearance_review_id
                         	AND t_project_material.t_project_id=?
                         """,
                Integer.class, result, projectid);
        if (i == null || i == 0)
            return null;
        return i;
    }
//获取监理审核
    public  List<Stastisprojectidandcount> getjianliunpassorpassList(int result){
    return jdbcTemplate.query("""       
                SELECT t_buy_material.t_use_material_id,t_project_material_retest_batch.t_project_id,
                			 MAX(t_project_material_retest.review_datetime) AS latest_review_time
                FROM t_buy_material , t_project_material_retest,t_project_material_retest_batch
                WHERE t_buy_material.t_buy_material_batch_id = t_project_material_retest_batch.t_buy_material_batch_id
                			AND t_project_material_retest_batch.id  = t_project_material_retest.t_project_material_retest_batch_id
                			AND t_buy_material.id  = t_project_material_retest.t_buy_material_id
                			AND t_project_material_retest.review_result= ?
                GROUP BY t_buy_material.t_use_material_id,
                				 t_project_material_retest_batch.t_project_id
                ORDER BY latest_review_time DESC
                             """, new JianliStatisticalanalysisgetUnpassReviewJianliMapper(),  result);
}
    private static final class JianliStatisticalanalysisgetUnpassReviewJianliMapper implements RowMapper<Stastisprojectidandcount> {
        @Override
        public Stastisprojectidandcount mapRow(ResultSet rs,
                                         int rowNum) throws SQLException {
            Stastisprojectidandcount unpassmaterialmessage = new Stastisprojectidandcount();
            // 去掉多余的逗号
            unpassmaterialmessage.setJianliMaterialId(rs.getString("t_use_material_id"));
            unpassmaterialmessage.setJianliProjectId(rs.getString("t_project_id"));
            return unpassmaterialmessage;
        }
    }
    public  Integer getjianliunpassorpassCount(String projectid, int result){
        Integer i = jdbcTemplate.queryForObject("""                
                                SELECT COUNT(*)
                                 FROM t_buy_material , t_project_material_retest,t_project_material_retest_batch
                                 WHERE t_buy_material.t_buy_material_batch_id = t_project_material_retest_batch.t_buy_material_batch_id
                                    			AND t_project_material_retest_batch.id  = t_project_material_retest.t_project_material_retest_batch_id
                                     			AND t_buy_material.id  = t_project_material_retest.t_buy_material_id
                                 			AND t_project_material_retest.review_result= ?
                                 			AND t_project_material_retest_batch.t_project_id=?
                        """,
                Integer.class,result, projectid);
        if (i == null || i == 0)
            return null;
        return i;
    }

//监理与工程部
    public  List<Stastisprojectidandcount> getjianliandgongchengbuunpassorpassList(int result){
        return jdbcTemplate.query("""       
                SELECT t_project_material_acceptance_batch.t_project_id,t_project_material_acceptance.t_project_material_id,
                			 MAX(t_project_material_acceptance_review_user.review_datetime) AS latest_review_time
                FROM t_project_material_acceptance , t_project_material_acceptance_batch,t_project_material_acceptance_review_mode,
                	   t_project_material_acceptance_review,t_project_material_acceptance_review_user
                 WHERE t_project_material_acceptance_review_user.t_project_material_acceptance_review_id = t_project_material_acceptance_review.id
                   AND t_project_material_acceptance_review.t_project_material_acceptance_mode_id =    t_project_material_acceptance_review_mode.id
                	 AND t_project_material_acceptance_review_mode.t_project_material_acceptance_batch_id = t_project_material_acceptance_batch.id
                	 AND t_project_material_acceptance_batch.id = t_project_material_acceptance.t_project_material_acceptance_batch_id
                   AND t_project_material_acceptance_review_user.review_result = ?
                GROUP BY t_project_material_acceptance_batch.t_project_id,t_project_material_acceptance.t_project_material_id
                ORDER BY latest_review_time DESC
                             """, new JianliandgongchengbuStatisticalanalysisgetUnpassReviewJianliAndGongchengbuMapper(), result);
}


    private static final class JianliandgongchengbuStatisticalanalysisgetUnpassReviewJianliAndGongchengbuMapper implements RowMapper<Stastisprojectidandcount> {
        @Override
        public Stastisprojectidandcount mapRow(ResultSet rs,
                                         int rowNum) throws SQLException {
            Stastisprojectidandcount unpassmaterialmessage = new Stastisprojectidandcount();
            // 去掉多余的逗号
            unpassmaterialmessage.setJianliandgongchengbuMaterialId(rs.getString("t_project_material_id"));
            unpassmaterialmessage.setJianliandgongchengbuProjectId(rs.getString("t_project_id"));
            return unpassmaterialmessage;
        }
    }

    public Integer getjianliandgongchengbuunpassorpassCount(String projectid ,int result){
        Integer i = jdbcTemplate.queryForObject("""                
                                SELECT count(DISTINCT t_project_material_acceptance_review_mode.t_project_material_acceptance_batch_id)
                             FROM t_project_material_acceptance , t_project_material_acceptance_batch,t_project_material_acceptance_review_mode,
                             	   t_project_material_acceptance_review,t_project_material_acceptance_review_user
                              WHERE t_project_material_acceptance_review_user.t_project_material_acceptance_review_id = t_project_material_acceptance_review.id
                                AND t_project_material_acceptance_review.t_project_material_acceptance_mode_id =    t_project_material_acceptance_review_mode.id
                             	 AND t_project_material_acceptance_review_mode.t_project_material_acceptance_batch_id = t_project_material_acceptance_batch.id
                             	 AND t_project_material_acceptance_batch.id = t_project_material_acceptance.t_project_material_acceptance_batch_id
                                AND t_project_material_acceptance_review_user.review_result = ?
                             AND t_project_material_acceptance_batch.t_project_id= ?
                        """,
                Integer.class, result, projectid);
        if (i == null || i == 0)
            return null;
        return i;
    }


    @Override
    public List<Unpassmaterialmessage> getUnpassmaterialmessage() {
        Integer i = jdbcTemplate.queryForObject("""                                    
                        SELECT  count(*) FROM t_project_material,t_project_review_mode,t_project_review,t_project_review_user WHERE t_project_material.t_company_id=t_project_review_mode.t_company_id  AND  t_project_material.t_project_id=t_project_review_mode.t_project_id AND  t_project_review.t_project_review_mode_id=t_project_review_mode.id  AND t_project_review.review_result=2  AND t_project_review.id  =t_project_review_user.t_project_review_id""",
                Integer.class);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""     
                        SELECT 
                          t_project_material.t_material_id,
                          t_project_material.t_project_id,
                          t_project_material.t_company_id,
                          t_project_review_user.review_content, 
                          t_project_review_user.t_user_id
                         FROM
                                t_project_material,
                                 t_project_review_mode,
                                t_project_review,
                             t_project_review_user
                         WHERE
                             t_project_material.t_company_id = t_project_review_mode.t_company_id 
                             AND  t_project_material.t_project_id = t_project_review_mode.t_project_id
                             AND  t_project_review.t_project_review_mode_id = t_project_review_mode.id  
                             AND t_project_review.review_result   = 2
                             AND t_project_review.id   = t_project_review_user.t_project_review_id;
                                  """,
                new StatisticalanalysisMapper());

    }


    public List<Unpassonlymaterial> getUnpassOnlymaterial() {
        Integer i = jdbcTemplate.queryForObject("""                                                 
                             SELECT DISTINCT count(DISTINCT\s
                        				t_project_material.t_material_id,
                        				t_project_material.t_project_id,
                        				t_project_material.t_company_id)
                        FROM t_project_material,t_project_review_mode,t_project_review,t_project_review_user
                        WHERE t_project_material.t_company_id=t_project_review_mode.t_company_id\s
                        	AND  t_project_material.t_project_id=t_project_review_mode.t_project_id
                        	AND  t_project_review.t_project_review_mode_id=t_project_review_mode.id
                        	AND t_project_review.review_result=2
                        	AND t_project_review.id =t_project_review_user.t_project_review_id
                        """,
                Integer.class);
        if (i == null || i == 0)
            return null;
        return jdbcTemplate.query("""     
                        SELECT DISTINCT\s
                        				t_project_material.t_material_id,
                        				t_project_material.t_project_id,
                        				t_project_material.t_company_id
                        FROM t_project_material,t_project_review_mode,t_project_review,t_project_review_user
                        WHERE t_project_material.t_company_id=t_project_review_mode.t_company_id\s
                        	AND  t_project_material.t_project_id=t_project_review_mode.t_project_id
                        	AND  t_project_review.t_project_review_mode_id=t_project_review_mode.id
                        	AND t_project_review.review_result=2
                        	AND t_project_review.id =t_project_review_user.t_project_review_id
                                  """,
                new StatisticalanalysisMaterilaMapper());

    }

    //    设计部经理审核
    @Override
    public Page<Unpassonlymaterial> getPage(int pageNo, int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Unpassonlymaterial> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    @Override
    public List<OnematerialUnpass> getunpassreviewbyprojectidmaterialid_companyid(String projectid, String companyid) {

        Integer i = jdbcTemplate.queryForObject("""                
                         select count(*)
                        FROM  t_project_review_mode,t_project_review,t_project_review_user
                        WHERE t_project_review_mode.t_project_id= ?
                        	AND t_project_review_mode.t_company_id= ?
                        	AND t_project_review_mode.id =t_project_review.t_project_review_mode_id
                         	AND t_project_review.review_result=2
                        	AND t_project_review.id =t_project_review_user.t_project_review_id
                        	ORDER BY  t_project_review_user.review_datetime ASC
                                                         """,
                Integer.class, projectid, companyid);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                        select t_project_review_user.t_user_id,t_project_review_user.review_content,t_project_review_user.review_datetime,t_project_review_user.id
                              	FROM  t_project_review_mode,t_project_review,t_project_review_user
                              WHERE t_project_review_mode.t_project_id=?
                              	AND t_project_review_mode.t_company_id=?
                              	AND t_project_review_mode.id =t_project_review.t_project_review_mode_id
                              	AND t_project_review.review_result=2
                              	AND t_project_review.id =t_project_review_user.t_project_review_id
                              	ORDER BY  t_project_review_user.review_datetime ASC
                          """,
                new Getunpassreviewbyprojectidmaterialid_companyidMapper(), projectid, companyid);
    }

    private List<Unpassonlymaterial> getPageQuery(int pageNo,
                                                  int pageSize) {
        return jdbcTemplate.query("""
                SELECT DISTINCT\s
                t_project_material.t_material_id,
                t_project_material.t_project_id,
                t_project_material.t_company_id
                FROM t_project_material,t_project_review_mode,t_project_review,t_project_review_user
                    WHERE t_project_material.t_company_id=t_project_review_mode.t_company_id\s
                       	AND  t_project_material.t_project_id=t_project_review_mode.t_project_id
                       	AND  t_project_review.t_project_review_mode_id=t_project_review_mode.id
                   	AND t_project_review.review_result=2
                       	AND t_project_review.id =t_project_review_user.t_project_review_id                   
                     LIMIT ?,?
                     """, new StatisticalanalysisMaterilaMapper(), pageNo * pageSize, pageSize);
    }

    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                            SELECT DISTINCT count(DISTINCT\s
                        			t_project_material.t_material_id,
                        			t_project_material.t_project_id,
                        			t_project_material.t_company_id)
                                               FROM t_project_material,t_project_review_mode,t_project_review,t_project_review_user
                                               WHERE t_project_material.t_company_id=
                            t_project_review_mode.t_company_id\s
                        AND  t_project_material.
                            t_project_id=t_project_review_mode.t_project_id
                        AND
                            t_project_review.t_project_review_mode_id=
                            t_project_review_mode.id
                        AND t_project_review.review_result=2
                        AND t_project_review.id =t_project_review_user.t_project_review_id
                                                            """,
                Integer.class);
        return i == null ? 0 : i;
    }


    //    总包之前审核不通过的材料
    @Override
    public Page<Unpassonlymaterial> getunpassbeforezongbaoPage(int pageNo, int pageSize) {
        long totalCount = getunpassbeforezongbaoCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Unpassonlymaterial> resultData = getunpassbeforezongbaoPageQuery(pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    //    总包之前审核不通过的材料数量
    public int getunpassbeforezongbaoCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        
                        SELECT   COUNT( DISTINCT t_project_material.t_project_id,t_project_material.t_material_id)
                        FROM t_project_material ,t_project_appearance_review,t_project_appearance_review_mode,t_project_appearance_review_user,t_use_material
                        WHERE t_project_material.id =t_use_material.t_project_material_id
                        	AND t_use_material.t_use_material_brand_select_id = t_project_appearance_review_mode.t_use_material_brand_select_id
                        	AND t_project_appearance_review_mode.id =t_project_appearance_review.t_project_appearance_review_mode_id
                        	AND t_project_appearance_review.review_result=2
                        	AND t_project_appearance_review.id =t_project_appearance_review_user.t_project_appearance_review_id
                                                        """,
                Integer.class);
        return i == null ? 0 : i;
    }

    //    获取总包之前审核不通过的材料列表
    private List<Unpassonlymaterial> getunpassbeforezongbaoPageQuery(int pageNo,
                                                                     int pageSize) {
        return jdbcTemplate.query("""       
                SELECT\s
                            t_project_material.t_project_id,
                            t_project_material.t_material_id,
                            MAX(t_project_appearance_review_user.review_datetime)  AS latest_review_time\s
                        FROM\s
                            t_project_material\s
                        JOIN t_use_material ON t_project_material.id  = t_use_material.t_project_material_id\s
                        JOIN t_project_appearance_review_mode ON t_use_material.t_use_material_brand_select_id = t_project_appearance_review_mode.t_use_material_brand_select_id\s
                        JOIN t_project_appearance_review ON t_project_appearance_review_mode.id  = t_project_appearance_review.t_project_appearance_review_mode_id\s
                        JOIN t_project_appearance_review_user ON t_project_appearance_review.id  = t_project_appearance_review_user.t_project_appearance_review_id\s
                        WHERE\s
                            t_project_appearance_review.review_result  = 2\s
                        GROUP BY\s
                            t_project_material.t_project_id,
                            t_project_material.t_material_id\s
                        ORDER BY\s
                            latest_review_time DESC
                        LIMIT ?,?;          
                             """, new StatisticalanalysisgetunpassbeforezongbaoMapper(), pageNo * pageSize, pageSize);
    }

    @Override
    public List<OnematerialUnpassbeforeZongbao> getunpassreviewbeforezongbao(String projectid, String materialid) {
        Integer i = jdbcTemplate.queryForObject("""                
                        SELECT   COUNT( *)
                         FROM t_project_material ,t_project_appearance_review,t_project_appearance_review_mode,t_project_appearance_review_user,t_use_material
                         WHERE t_project_material.id =t_use_material.t_project_material_id
                         	AND t_use_material.t_use_material_brand_select_id = t_project_appearance_review_mode.t_use_material_brand_select_id
                         	AND t_project_appearance_review_mode.id =t_project_appearance_review.t_project_appearance_review_mode_id
                         	AND t_project_appearance_review.review_result=2
                         	AND t_project_appearance_review.id =t_project_appearance_review_user.t_project_appearance_review_id
                         	AND t_project_material.t_material_id = ?
                         	AND t_project_material.t_project_id=?
                         ORDER BY t_project_appearance_review_user.review_datetime ASC
                         """,
                Integer.class, materialid, projectid);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                        SELECT  t_project_appearance_review_user.t_user_id,t_project_appearance_review_user.review_content,t_project_appearance_review_user.review_datetime,t_project_appearance_review_user.id
                        FROM t_project_material ,t_project_appearance_review,t_project_appearance_review_mode,t_project_appearance_review_user,t_use_material
                        WHERE t_project_material.id =t_use_material.t_project_material_id
                        	AND t_use_material.t_use_material_brand_select_id = t_project_appearance_review_mode.t_use_material_brand_select_id
                        	AND t_project_appearance_review_mode.id =t_project_appearance_review.t_project_appearance_review_mode_id
                        	AND t_project_appearance_review.review_result=2
                        	AND t_project_appearance_review.id =t_project_appearance_review_user.t_project_appearance_review_id
                        	AND t_project_material.t_material_id = ?
                        	AND t_project_material.t_project_id=?
                        ORDER BY t_project_appearance_review_user.review_datetime ASC
                             """,
                new GetunpassreviewbeforezongbaoMapper(), materialid, projectid);
    }


    //监理审核
    @Override
    public Page<Unpassonlymaterial> getUnpassReviewJianli(int pageNo, int pageSize) {
        long totalCount = getUnpassReviewJianliCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Unpassonlymaterial> resultData = getUnpassReviewJianliPageQuery(pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);
    }

    private List<Unpassonlymaterial> getUnpassReviewJianliPageQuery(int pageNo,
                                                                    int pageSize) {
        return jdbcTemplate.query("""       
                SELECT t_buy_material.t_use_material_id,t_project_material_retest_batch.t_project_id,
                			 MAX(t_project_material_retest.review_datetime) AS latest_review_time
                FROM t_buy_material , t_project_material_retest,t_project_material_retest_batch
                WHERE t_buy_material.t_buy_material_batch_id = t_project_material_retest_batch.t_buy_material_batch_id
                			AND t_project_material_retest_batch.id  = t_project_material_retest.t_project_material_retest_batch_id
                			AND t_buy_material.id  = t_project_material_retest.t_buy_material_id
                			AND t_project_material_retest.review_result=2
                GROUP BY t_buy_material.t_use_material_id,
                				 t_project_material_retest_batch.t_project_id
                 ORDER BY latest_review_time DESC
                 limit ?,?
                             """, new StatisticalanalysisgetUnpassReviewJianliMapper(), pageNo * pageSize, pageSize);
    }

    public List<OnematerialUnpassjianli> getunpassreviewcontentjianli(String projectid, String materialid) {
        Integer i = jdbcTemplate.queryForObject("""                
                                SELECT COUNT(*)
                                 FROM t_buy_material , t_project_material_retest,t_project_material_retest_batch
                                 WHERE t_buy_material.t_buy_material_batch_id = t_project_material_retest_batch.t_buy_material_batch_id
                                    			AND t_project_material_retest_batch.id  = t_project_material_retest.t_project_material_retest_batch_id
                                     			AND t_buy_material.id  = t_project_material_retest.t_buy_material_id
                                 			AND t_project_material_retest.review_result=2
                                    			AND t_buy_material.t_use_material_id= ?
                                 			AND t_project_material_retest_batch.t_project_id=?
                        """,
                Integer.class, materialid, projectid);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                        SELECT t_project_material_retest.id,t_buy_material.t_use_material_id,t_project_material_retest.review_content,t_buy_material.t_buy_material_batch_id,t_project_material_retest.t_user_id,t_project_material_retest_batch.t_project_id,t_project_material_retest.review_datetime
                        FROM t_buy_material , t_project_material_retest,t_project_material_retest_batch
                        WHERE t_buy_material.t_buy_material_batch_id = t_project_material_retest_batch.t_buy_material_batch_id
                        			AND t_project_material_retest_batch.id  = t_project_material_retest.t_project_material_retest_batch_id
                        			AND t_buy_material.id  = t_project_material_retest.t_buy_material_id
                        			AND t_project_material_retest.review_result=2
                        			AND t_buy_material.t_use_material_id= ?
                        			AND t_project_material_retest_batch.t_project_id= ?
                        	ORDER BY t_project_material_retest.review_datetime ASC
                             """,
                new GetunpassreviewjianliMapper(), materialid, projectid);
    }

    public int getUnpassReviewJianliCount() {
        Integer i = jdbcTemplate.queryForObject("""
                        SELECT COUNT(DISTINCT t_buy_material.t_use_material_id,t_project_material_retest_batch.t_project_id)
                        FROM t_buy_material , t_project_material_retest,t_project_material_retest_batch
                        WHERE t_buy_material.t_buy_material_batch_id = t_project_material_retest_batch.t_buy_material_batch_id
                        			AND t_project_material_retest_batch.id  = t_project_material_retest.t_project_material_retest_batch_id
                        			AND t_buy_material.id  = t_project_material_retest.t_buy_material_id
                        			AND t_project_material_retest.review_result=2
                                                        """,
                Integer.class);
        return i == null ? 0 : i;
    }

    //    监理审核与项目经理审核
    public Page<Unpassonlymaterial> getUnpassReviewJianliAndGongchengbu(int pageNo, int pageSize) {
        long totalCount = getUnpassReviewJianliAndGongchengbuliCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<Unpassonlymaterial> resultData = getUnpassReviewJianliAndGongchengbuPageQuery(pageNo - 1, pageSize);
        return new Page<>(startIndex, totalCount, (int) totalCount, resultData);

    }

    public int getUnpassReviewJianliAndGongchengbuliCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                               
                        SELECT COUNT(DISTINCT t_project_material_acceptance_batch.t_project_id,t_project_material_acceptance.t_project_material_id)
                        FROM t_project_material_acceptance , t_project_material_acceptance_batch,t_project_material_acceptance_review_mode,
                        	   t_project_material_acceptance_review,t_project_material_acceptance_review_user
                         WHERE t_project_material_acceptance_review_user.t_project_material_acceptance_review_id = t_project_material_acceptance_review.id
                           AND t_project_material_acceptance_review.t_project_material_acceptance_mode_id =    t_project_material_acceptance_review_mode.id
                        	 AND t_project_material_acceptance_review_mode.t_project_material_acceptance_batch_id = t_project_material_acceptance_batch.id
                        	 AND t_project_material_acceptance_batch.id = t_project_material_acceptance.t_project_material_acceptance_batch_id
                           AND t_project_material_acceptance_review_user.review_result =2
                                                        """,
                Integer.class);
        return i == null ? 0 : i;
    }

    private List<Unpassonlymaterial> getUnpassReviewJianliAndGongchengbuPageQuery(int pageNo,
                                                                                  int pageSize) {
        return jdbcTemplate.query("""       
                SELECT t_project_material_acceptance_batch.t_project_id,t_project_material_acceptance.t_project_material_id,
                			 MAX(t_project_material_acceptance_review_user.review_datetime) AS latest_review_time
                FROM t_project_material_acceptance , t_project_material_acceptance_batch,t_project_material_acceptance_review_mode,
                	   t_project_material_acceptance_review,t_project_material_acceptance_review_user
                 WHERE t_project_material_acceptance_review_user.t_project_material_acceptance_review_id = t_project_material_acceptance_review.id
                   AND t_project_material_acceptance_review.t_project_material_acceptance_mode_id =    t_project_material_acceptance_review_mode.id
                	 AND t_project_material_acceptance_review_mode.t_project_material_acceptance_batch_id = t_project_material_acceptance_batch.id
                	 AND t_project_material_acceptance_batch.id = t_project_material_acceptance.t_project_material_acceptance_batch_id
                   AND t_project_material_acceptance_review_user.review_result =2
                GROUP BY t_project_material_acceptance_batch.t_project_id,t_project_material_acceptance.t_project_material_id
                ORDER BY latest_review_time DESC
                limit ?,?
                             """, new StatisticalanalysisgetUnpassReviewJianliAndGongchengbuMapper(), pageNo * pageSize, pageSize);
    }

    public List<OnematerialUnpassjianliandgongchengbu> getunpassreviewcontentjianliandgongchengbu(String projectid, String materialid) {
        Integer i = jdbcTemplate.queryForObject("""                
                                SELECT count(*)
                             FROM t_project_material_acceptance , t_project_material_acceptance_batch,t_project_material_acceptance_review_mode,
                             	   t_project_material_acceptance_review,t_project_material_acceptance_review_user
                              WHERE t_project_material_acceptance_review_user.t_project_material_acceptance_review_id = t_project_material_acceptance_review.id
                                AND t_project_material_acceptance_review.t_project_material_acceptance_mode_id =    t_project_material_acceptance_review_mode.id
                             	 AND t_project_material_acceptance_review_mode.t_project_material_acceptance_batch_id = t_project_material_acceptance_batch.id
                             	 AND t_project_material_acceptance_batch.id = t_project_material_acceptance.t_project_material_acceptance_batch_id
                                AND t_project_material_acceptance_review_user.review_result =2
                             	 AND t_project_material_acceptance.t_project_material_id=?
                             AND t_project_material_acceptance_batch.t_project_id= ?
                        """,
                Integer.class, materialid, projectid);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                SELECT t_project_material_acceptance_review_user.id,t_project_material_acceptance_review_user.t_user_id,t_project_material_acceptance_review_user.review_content
                        FROM t_project_material_acceptance , t_project_material_acceptance_batch,t_project_material_acceptance_review_mode,
                        	   t_project_material_acceptance_review,t_project_material_acceptance_review_user
                         WHERE t_project_material_acceptance_review_user.t_project_material_acceptance_review_id = t_project_material_acceptance_review.id
                           AND t_project_material_acceptance_review.t_project_material_acceptance_mode_id =    t_project_material_acceptance_review_mode.id
                        	 AND t_project_material_acceptance_review_mode.t_project_material_acceptance_batch_id = t_project_material_acceptance_batch.id
                        	 AND t_project_material_acceptance_batch.id = t_project_material_acceptance.t_project_material_acceptance_batch_id
                           AND t_project_material_acceptance_review_user.review_result =2
                        	 AND t_project_material_acceptance.t_project_material_id=?
                        	 AND t_project_material_acceptance_batch.t_project_id= ?
                        	 ORDER BY t_project_material_acceptance_review_user.review_datetime ASC
                                     """,
                new GetunpassreviewjianliandgongchengbuMapper(), materialid, projectid);
    }

    private static final class StatisticalanalysisMapper implements RowMapper<Unpassmaterialmessage> {
        @Override
        public Unpassmaterialmessage mapRow(ResultSet rs,
                                            int rowNum) throws SQLException {
            Unpassmaterialmessage unpassmaterialmessage = new Unpassmaterialmessage();
            // 去掉多余的逗号
            unpassmaterialmessage.setMaterialId(rs.getString("t_material_id"));
            unpassmaterialmessage.setProjectId(rs.getString("t_project_id"));
            unpassmaterialmessage.setCompanyId(rs.getString("t_company_id"));
            unpassmaterialmessage.setReviewConternt(rs.getString("review_content"));
            unpassmaterialmessage.setUserId(rs.getString("t_user_id"));
            return unpassmaterialmessage;
        }
    }

    private static final class StatisticalanalysisMaterilaMapper implements RowMapper<Unpassonlymaterial> {
        @Override
        public Unpassonlymaterial mapRow(ResultSet rs,
                                         int rowNum) throws SQLException {
            Unpassonlymaterial unpassmaterialmessage = new Unpassonlymaterial();
            // 去掉多余的逗号
            unpassmaterialmessage.setMaterialId(rs.getString("t_material_id"));
            unpassmaterialmessage.setProjectId(rs.getString("t_project_id"));
            unpassmaterialmessage.setCompanyId(rs.getString("t_company_id"));
            return unpassmaterialmessage;
        }
    }

    private static final class Getunpassreviewbyprojectidmaterialid_companyidMapper implements RowMapper<OnematerialUnpass> {
        @Override
        public OnematerialUnpass mapRow(ResultSet rs,
                                        int rowNum) throws SQLException {
            OnematerialUnpass onematerialUnpass = new OnematerialUnpass();

            onematerialUnpass.setUserid(rs.getString("t_user_id"));
            onematerialUnpass.setReviewcotent(rs.getString("review_content"));
            onematerialUnpass.setId(rs.getString("id"));

            return onematerialUnpass;
        }
    }

    //    在总包之前的审核
    private static final class StatisticalanalysisgetunpassbeforezongbaoMapper implements RowMapper<Unpassonlymaterial> {
        @Override
        public Unpassonlymaterial mapRow(ResultSet rs,
                                         int rowNum) throws SQLException {
            Unpassonlymaterial unpassmaterialmessage = new Unpassonlymaterial();
            // 去掉多余的逗号
            unpassmaterialmessage.setMaterialId(rs.getString("t_material_id"));
            unpassmaterialmessage.setProjectId(rs.getString("t_project_id"));
            return unpassmaterialmessage;
        }
    }

    private static final class GetunpassreviewbeforezongbaoMapper implements RowMapper<OnematerialUnpassbeforeZongbao> {
        @Override
        public OnematerialUnpassbeforeZongbao mapRow(ResultSet rs,
                                                     int rowNum) throws SQLException {
            OnematerialUnpassbeforeZongbao onematerialUnpassbeforeZongbao = new OnematerialUnpassbeforeZongbao();

            onematerialUnpassbeforeZongbao.setUserid(rs.getString("t_user_id"));
            onematerialUnpassbeforeZongbao.setReviewcotent(rs.getString("review_content"));
            onematerialUnpassbeforeZongbao.setId(rs.getString("id"));

            return onematerialUnpassbeforeZongbao;
        }
    }

//

    //    监理审核
    private static final class StatisticalanalysisgetUnpassReviewJianliMapper implements RowMapper<Unpassonlymaterial> {
        @Override
        public Unpassonlymaterial mapRow(ResultSet rs,
                                         int rowNum) throws SQLException {
            Unpassonlymaterial unpassmaterialmessage = new Unpassonlymaterial();
            // 去掉多余的逗号
            unpassmaterialmessage.setMaterialId(rs.getString("t_use_material_id"));
            unpassmaterialmessage.setProjectId(rs.getString("t_project_id"));
            return unpassmaterialmessage;
        }
    }

    private static final class GetunpassreviewjianliMapper implements RowMapper<OnematerialUnpassjianli> {
        @Override
        public OnematerialUnpassjianli mapRow(ResultSet rs,
                                              int rowNum) throws SQLException {
            OnematerialUnpassjianli onematerialUnpassjianli = new OnematerialUnpassjianli();

            onematerialUnpassjianli.setUserid(rs.getString("t_user_id"));
            onematerialUnpassjianli.setReviewcotent(rs.getString("review_content"));
            onematerialUnpassjianli.setId(rs.getString("id"));

            return onematerialUnpassjianli;
        }
    }

    //    监理审核与项目经理审核
    private static final class StatisticalanalysisgetUnpassReviewJianliAndGongchengbuMapper implements RowMapper<Unpassonlymaterial> {
        @Override
        public Unpassonlymaterial mapRow(ResultSet rs,
                                         int rowNum) throws SQLException {
            Unpassonlymaterial unpassmaterialmessage = new Unpassonlymaterial();
            // 去掉多余的逗号
            unpassmaterialmessage.setMaterialId(rs.getString("t_project_material_id"));
            unpassmaterialmessage.setProjectId(rs.getString("t_project_id"));
            return unpassmaterialmessage;
        }
    }

    private static final class GetunpassreviewjianliandgongchengbuMapper implements RowMapper<OnematerialUnpassjianliandgongchengbu> {
        @Override
        public OnematerialUnpassjianliandgongchengbu mapRow(ResultSet rs,
                                                            int rowNum) throws SQLException {
            OnematerialUnpassjianliandgongchengbu onematerialUnpassjianliandgongchengbu = new OnematerialUnpassjianliandgongchengbu();

            onematerialUnpassjianliandgongchengbu.setUserid(rs.getString("t_user_id"));
            onematerialUnpassjianliandgongchengbu.setReviewcotent(rs.getString("review_content"));
            onematerialUnpassjianliandgongchengbu.setId(rs.getString("id"));

            return onematerialUnpassjianliandgongchengbu;
        }
    }

}