package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.materialreview.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.project.materialreview.ProjectReviewUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.project.materialreview.IProjectReviewUserRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectReviewUserRepositoryImpl implements IProjectReviewUserRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectReviewUserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert
     */
    @Override
    public String add(ProjectReviewUser projectReviewUser) {

        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_project_review_user(id,
                                        t_user_id,
                                        t_project_review_id,
                                        review_result,
                                        review_content,
                                        review_datetime)
                                        VALUES(?,?,?,?,?,?)
                                        """,
                                newId,
                                projectReviewUser.getUserId(),
                                projectReviewUser.getProjectReviewId(),
                                projectReviewUser.getReviewResult(),
                                projectReviewUser.getReviewContent(),
                                projectReviewUser.getReviewDatetime()) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     */
    @Override
    public int delete(ProjectReviewUser projectReviewUser) {
        if (projectReviewUser == null) return 0;
        return jdbcTemplate.update("""
                                           UPDATE t_project_review_user
                                           SET deleted_at=? 
                                           WHERE id=?
                                           """,
                                   new Date(),
                                   projectReviewUser.getId());


    }

    /**
     * 根据id删除记录
     */
    @Override
    public int deleteById(String id) {

        return jdbcTemplate.update("""
                                           UPDATE t_project_review_user
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
                                           UPDATE t_project_review_user
                                           SET deleted_at=? 
                                           WHERE t_user_id=?
                                           """,
                                   new Date(),
                                   userId);

    }

    /**
     * 根据projectReviewId删除记录
     */
    @Override
    public int deleteByProjectReviewId(String projectReviewId) {

        return jdbcTemplate.update("""
                                           UPDATE t_project_review_user
                                           SET deleted_at=? 
                                           WHERE t_project_review_id=?
                                           """,
                                   new Date(),
                                   projectReviewId);

    }

    /**
     * update
     */
    @Override
    public int update(ProjectReviewUser projectReviewUser) {
        return jdbcTemplate.update("""
                                           UPDATE t_project_review_user
                                           SET t_user_id=?,
                                           t_project_review_id=?,
                                           review_result=?,
                                           review_content=?,
                                           review_datetime=?
                                           WHERE id=?
                                           """,
                                   projectReviewUser.getUserId(),
                                   projectReviewUser.getProjectReviewId(),
                                   projectReviewUser.getReviewResult(),
                                   projectReviewUser.getReviewContent(),
                                   projectReviewUser.getReviewDatetime(),
                                   projectReviewUser.getId());
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_user
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
                                                        FROM t_project_review_user
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectReviewId得到总数量
     */
    @Override
    public int getCountByProjectReviewId(String projectReviewId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_user
                                                        WHERE t_project_review_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectReviewId);
        return i == null ? 0 : i;
    }

    /**
     * 根据外键ProjectReviewId得到总数量
     */
    @Override
    public int getCountByProjectReviewIdAndUserAndResult(String projectReviewId, String reviewUser, int reviewResult) {
        Integer i = 0;
        if(reviewUser != "") {
            reviewUser = "%" + reviewUser + "%";
            i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_user
                                                        INNER JOIN t_user ON t_project_review_user.t_user_id=t_user.id
                                                        WHERE t_project_review_user.t_project_review_id=? AND t_project_review_user.deleted_at IS NULL AND t_user.real_name LIKE ?
                                                        """,
                    Integer.class, projectReviewId, reviewUser);
        } else if(reviewResult != -1) {
            i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_user
                                                        WHERE t_project_review_id=? AND deleted_at IS NULL AND review_result=?
                                                        """,
                    Integer.class, projectReviewId, reviewResult);
        }

        return i == null ? 0 : i;
    }

    @Override
    public int getCountByProjectReviewIdAndResult(String projectReviewId,
                                                  int nReviewResult) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_user
                                                        WHERE t_project_review_id=? AND review_result=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectReviewId, nReviewResult);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountByProjecId(String projectId
                                                  ) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*)
                                                        FROM t_project_review
                                                        WHERE t_project_id=? 
                                                        AND t_project_review.review_status=2
                                                        AND deleted_at IS NULL
                                                        """,
                Integer.class, projectId);
        return i == null ? 0 : i;
    }
    @Override
    public int getCountByProjectIdAndResult(String projectId, int nReviewResult){
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*)
                                                        FROM t_project_review
                                                        WHERE t_project_id=? AND review_result=? AND deleted_at IS NULL
                                                        """,
                Integer.class, projectId, nReviewResult);
        return i == null ? 0 : i;
    }
    /**
     * 根据id得到记录
     */
    @Override
    public ProjectReviewUser getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_user 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_review_user
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new ProjectReviewUserMapper(), id);
    }

    @Override
    public ProjectReviewUser getByUserIdAndProjectReviewId(String userId,
                                                           String projectReviewId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_user 
                                                        WHERE t_user_id=? AND  t_project_review_id =? AND deleted_at IS NULL
                                                        """, Integer.class, userId, projectReviewId);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_project_review_user
                                                   WHERE t_user_id=? AND  t_project_review_id =? AND deleted_at IS NULL
                                                   """,
                                           new ProjectReviewUserMapper(), userId, projectReviewId);
    }

    /**
     * 根据userId得到记录
     */
    @Override
    public List<ProjectReviewUser> getByUserId(String userId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_user
                                                        WHERE t_user_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, userId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review_user 
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectReviewUserMapper(), userId);
    }

    /**
     * 根据projectReviewId得到记录
     */
    @Override
    public List<ProjectReviewUser> getByProjectReviewId(String projectReviewId) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_user
                                                        WHERE t_project_review_id=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectReviewId);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review_user 
                                          WHERE t_project_review_id=? AND deleted_at IS NULL
                                          """,
                                  new ProjectReviewUserMapper(), projectReviewId);
    }

    @Override
    public List<ProjectReviewUser> getByProjectReviewIdAndNotReviewed(String projectReviewId,
                                                                      int reviewResult) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_project_review_user
                                                        WHERE t_project_review_id=? AND review_result=? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, projectReviewId, reviewResult);
        if (i == null || i == 0)
            return null;

        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review_user 
                                          WHERE t_project_review_id=? AND review_result=? AND deleted_at IS NULL
                                          """,
                                  new ProjectReviewUserMapper(), projectReviewId, reviewResult);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<ProjectReviewUser> getPage(int pageNo,
                                           int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectReviewUser> resultData = getPageQuery(pageNo - 1, pageSize);
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
    public Page<ProjectReviewUser> getPageByUserId(String userId,
                                                   int pageNo,
                                                   int pageSize) {
        long totalCount = getCountByUserId(userId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectReviewUser> resultData = getPageQueryByUserId(userId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectReviewId
     * @param pageNo          页号，从1开始
     * @param pageSize        每页的记录数
     */
    @Override
    public Page<ProjectReviewUser> getPageByProjectReviewId(String projectReviewId,
                                                            int pageNo,
                                                            int pageSize) {
        long totalCount = getCountByProjectReviewId(projectReviewId);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectReviewUser> resultData = getPageQueryByProjectReviewId(projectReviewId, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param projectReviewId
     * @param pageNo          页号，从1开始
     * @param pageSize        每页的记录数
     */
    @Override
    public Page<ProjectReviewUser> getPageByProjectReviewIdAndUserAndResult(String projectReviewId,
                                                            String reviewUser,
                                                            int reviewResult,
                                                            int pageNo,
                                                            int pageSize) {
        long totalCount = getCountByProjectReviewIdAndUserAndResult(projectReviewId, reviewUser, reviewResult);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<ProjectReviewUser> resultData = getPageQueryByProjectReviewIdAndUserAndResult(projectReviewId, reviewUser, reviewResult, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<ProjectReviewUser> getPageQuery(int pageNo,
                                                 int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review_user
                                          WHERE deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectReviewUserMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_user_id）+获得指定页面数据
     *
     * @param userId
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    private List<ProjectReviewUser> getPageQueryByUserId(String userId,
                                                         int pageNo,
                                                         int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review_user
                                          WHERE t_user_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectReviewUserMapper(), userId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_review_id）+获得指定页面数据
     *
     * @param projectReviewId
     * @param pageNo          页号，从1开始
     * @param pageSize        每页的记录数
     */
    private List<ProjectReviewUser> getPageQueryByProjectReviewId(String projectReviewId,
                                                                  int pageNo,
                                                                  int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review_user
                                          WHERE t_project_review_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                                  new ProjectReviewUserMapper(), projectReviewId, pageNo * pageSize, pageSize);
    }

    /**
     * 根据外键（t_project_review_id）+获得指定页面数据
     *
     * @param projectReviewId
     * @param pageNo          页号，从1开始
     * @param pageSize        每页的记录数
     */
    private List<ProjectReviewUser> getPageQueryByProjectReviewIdAndUserAndResult(String projectReviewId,
                                                                  String reviewUser,
                                                                  int reviewResult,
                                                                  int pageNo,
                                                                  int pageSize) {
        if(reviewUser != "") {
            reviewUser = "%" + reviewUser + "%";
            return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review_user
                                          INNER JOIN t_user ON t_project_review_user.t_user_id=t_user.id
                                          WHERE t_project_review_user.t_project_review_id=? AND t_project_review_user.deleted_at IS NULL AND t_user.real_name LIKE ?
                                          LIMIT ?,?
                                          """,
                    new ProjectReviewUserMapper(), projectReviewId, reviewUser, pageNo * pageSize, pageSize);
        } else if(reviewResult != -1) {
            return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review_user
                                          WHERE t_project_review_id=? AND deleted_at IS NULL AND review_result=?
                                          LIMIT ?,?
                                          """,
                    new ProjectReviewUserMapper(), projectReviewId, reviewResult, pageNo * pageSize, pageSize);
        } else {
            return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_project_review_user
                                          WHERE t_project_review_id=? AND deleted_at IS NULL
                                          LIMIT ?,?
                                          """,
                    new ProjectReviewUserMapper(), projectReviewId, pageNo * pageSize, pageSize);
        }
    }

    /**
     * RowMapper
     */
    private static final class ProjectReviewUserMapper implements RowMapper<ProjectReviewUser> {
        @Override
        public ProjectReviewUser mapRow(ResultSet rs,
                                        int rowNum) throws SQLException {
            ProjectReviewUser projectReviewUser = new ProjectReviewUser();
            projectReviewUser.setId(rs.getString("id"));
            projectReviewUser.setUserId(rs.getString("t_user_id"));
            projectReviewUser.setProjectReviewId(rs.getString("t_project_review_id"));
            projectReviewUser.setReviewResult(rs.getInt("review_result"));
            projectReviewUser.setReviewContent(rs.getString("review_content"));
            projectReviewUser.setReviewDatetime(rs.getTimestamp("review_datetime"));
            projectReviewUser.setDeletedAt(rs.getTimestamp("deleted_at"));
            return projectReviewUser;
        }
    }

}