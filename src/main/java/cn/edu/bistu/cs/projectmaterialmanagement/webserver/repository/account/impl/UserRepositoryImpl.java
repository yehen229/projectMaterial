package cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.account.impl;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account.User;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.general.Page;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.system.CompanyUser;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.repository.account.IUserRepository;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility.GUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean ExistUser(User user){
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*)
                                                        FROM t_user
                                                        WHERE user_name=? AND real_name=?
                                                        """,
                Integer.class,user.getUserName(),user.getRealName());
        if( i > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean isAdmin(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        select count(*) from t_user_role LEFT JOIN
                                                        t_role on t_user_role.t_role_id = t_role.id
                                                        where t_user_role.t_user_id = ?
                                                        """,
                Integer.class,id);
        if( i > 0){
            return true;
        }
        return false;
    }

    /**
     * insert
     */
    @Override
    public String add(User user) {
        String newId = GUID.getGUID();
        if (jdbcTemplate.update("""
                                        INSERT INTO t_user(
                                        id,
                                        user_name,
                                        real_name,
                                        password,
                                        tel,
                                        email
                                        )
                                        VALUES(?,?,?,?,?,?)
                                        """,
                                newId,
                                user.getUserName(),
                                user.getRealName(),
                                user.getPassword(),
                                user.getTel(),
                                user.getEmail()
        ) > 0)
            return newId;
        return null;
    }

    /**
     * delete
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int delete(User user) {
        if (user == null) return 0;
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_user
                                                        WHERE id=? AND user_name = ? AND real_name = ?
                                                        """,
                Integer.class,user.getId(),user.getUserName(),user.getRealName());
        if( i == 0){
            return 0;
        }
        return jdbcTemplate.update("""
                                           UPDATE t_user
                                           SET deleted_at=? 
                                           WHERE id=? AND user_name = ? AND real_name = ?
                                           """,
                                   new Date(),
                                   user.getId(),user.getUserName(),user.getRealName());
    }

    /**
     * update
     */
    @Override
    public int update(User user) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_user
                                                        WHERE user_name=? AND real_name=?
                                                        """,
                Integer.class,user.getUserName(),user.getRealName());
        if( i == 0){
            return 0;
        }
        return jdbcTemplate.update("""
                                           UPDATE t_user
                                           SET user_name=?,
                                           real_name=?,
                                           password=?,
                                           tel=?,
                                           email=?
                                           WHERE id=?
                                           """,
                                   user.getUserName(),
                                   user.getRealName(),
                                   user.getPassword(),
                                   user.getTel(),
                                   user.getEmail(),
                                   user.getId());
    }

    /**
     * 根据id删除记录
     * deleted_at(null表示未删，否则 表示删除时间)，查询时需要加入条件判断(deleted_at is null)
     */
    @Override
    public int deleteById(String id) {
        if (id == null) return 0;
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_user
                                                        WHERE id=?
                                                        """,
                Integer.class,id);
        if( i == 0){
            return 0;
        }
        return jdbcTemplate.update("""
                                           UPDATE t_user
                                           SET deleted_at=? 
                                           WHERE id=? 
                                           """,
                                   new Date(),
                                   id);
    }

    @Override
    public int updateOwnInfo(User user) {
        return jdbcTemplate.update("""
                                           UPDATE t_user
                                           SET tel=?,
                                           WHERE id=?
                                           """,

                                   user.getTel(),
                                   user.getId());
    }

    @Override
    public int isCorrectPwd(String id,
                            String pwd) {
        return 0;
    }

    @Override
    public int resetPwd(String id,
                        String pwd) {
        return jdbcTemplate.update("""
                                           UPDATE t_user
                                           SET password=?
                                           WHERE id=?
                                           """,
                                   pwd, id);
    }

    /**
     * getCount
     */
    @Override
    public int getCount() {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_user
                                                        WHERE deleted_at IS NULL
                                                        """,
                                                Integer.class);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountLikeRealName(String realName) {
        realName = "%" + realName.trim() + "%";
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_user
                                                        WHERE real_name LIKE ? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, realName);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountLikeTel(String tel) {
        tel = "%" + tel.trim() + "%";
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_user
                                                        WHERE tel LIKE ? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, tel);
        return i == null ? 0 : i;
    }

    @Override
    public int getCountLikeEmail(String email) {
        email = "%" + email.trim() + "%";
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_user                       
                                                        WHERE email LIKE ? AND deleted_at IS NULL
                                                        """,
                                                Integer.class, email);
        return i == null ? 0 : i;
    }

    /**
     * 根据id得到记录
     */
    @Override
    public User getById(String id) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_user 
                                                        WHERE id=? AND deleted_at IS NULL
                                                        """, Integer.class, id);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_user
                                                   WHERE id=? AND deleted_at IS NULL
                                                   """,
                                           new UserMapper(), id);
    }

    @Override
    public User getByUserName(String userName) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_user 
                                                        WHERE user_name=? AND deleted_at IS NULL
                                                        """, Integer.class, userName);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_user
                                                   WHERE user_name=? AND deleted_at IS NULL
                                                   """,
                                           new UserMapper(), userName);
    }

    @Override
    public User getByRealName(String realName) {
        realName = "%" + realName.trim() + "%";
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_user 
                                                        WHERE real_name LIKE ? AND deleted_at IS NULL
                                                        """, Integer.class, realName);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_user
                                                   WHERE real_name LIKE ? AND deleted_at IS NULL
                                                   """,
                                           new UserMapper(), realName);
    }



    @Override
    public User getByTel(String tel) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_user 
                                                        WHERE tel=? AND deleted_at IS NULL
                                                        """, Integer.class, tel);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_user
                                                   WHERE tel=? AND deleted_at IS NULL
                                                   """,
                                           new UserMapper(), tel);
    }

    @Override
    public User getByEmail(String email) {
        Integer i = jdbcTemplate.queryForObject("""
                                                        SELECT count(*) 
                                                        FROM t_user 
                                                        WHERE email=? AND deleted_at IS NULL
                                                        """, Integer.class, email);
        if (i == null || i != 1)
            return null;

        return jdbcTemplate.queryForObject("""
                                                   SELECT * 
                                                   FROM t_user
                                                   WHERE email=? AND deleted_at IS NULL
                                                   """,
                                           new UserMapper(), email);
    }

    @Override
    public Page<User> getPageLikeRealName(String realName,
                                          int pageNo,
                                          int pageSize) {
        long totalCount = getCountLikeRealName(realName);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<User> resultData = getPageQueryLikeRealName(realName, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<User> getPageLikeTel(String tel,
                                     int pageNo,
                                     int pageSize) {
        long totalCount = getCountLikeTel(tel);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<User> resultData = getPageQueryLikeTel(tel, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    @Override
    public Page<User> getPageLikeEmail(String email,
                                       int pageNo,
                                       int pageSize) {
        long totalCount = getCountLikeRealName(email);
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<User> resultData = getPageQueryLikeEmail(email, pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */
    @Override
    public Page<User> getPage(int pageNo,
                              int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        List<User> resultData = getPageQuery(pageNo - 1, pageSize);
        return new Page<>(0, totalCount, (int) totalCount, resultData);
    }

    private List<User> getPageQueryLikeRealName(String realName,
                                                int pageNo,
                                                int pageSize) {
        realName = "%" + realName.trim() + "%";
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_user
                                           		WHERE real_name LIKE ?
                                          LIMIT ?,?
                                          """,
                                  new UserMapper(), realName, pageNo * pageSize, pageSize);
    }

    private List<User> getPageQueryLikeTel(String tel,
                                           int pageNo,
                                           int pageSize) {
        tel = "%" + tel.trim() + "%";
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_user
                                           WHERE tel LIKE ?
                                          LIMIT ?,?
                                          """,
                                  new UserMapper(), tel, pageNo * pageSize, pageSize);
    }

    private List<User> getPageQueryLikeEmail(String email,
                                             int pageNo,
                                             int pageSize) {
        email = "%" + email.trim() + "%";
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_user
                                          WHERE email LIKE ?
                                          LIMIT ?,?
                                          """,
                                  new UserMapper(), email, pageNo * pageSize, pageSize);
    }

    /**
     * 获得指定页面数据
     *
     * @param pageNo   页号，从1开始
     * @param pageSize 每页的记录数
     */

    private List<User> getPageQuery(int pageNo,
                                    int pageSize) {
        return jdbcTemplate.query("""
                                          SELECT * 
                                          FROM t_user
                                          LIMIT ?,?
                                          """,
                                  new UserMapper(), pageNo * pageSize, pageSize);
    }

    /**
     * RowMapper
     */
    private static final class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs,
                           int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setUserName(rs.getString("user_name"));
            user.setRealName(rs.getString("real_name"));
            user.setPassword(rs.getString("password"));
            user.setTel(rs.getString("tel"));
            user.setEmail(rs.getString("email"));
            user.setDeletedAt(rs.getTimestamp("deleted_at"));
            return user;
        }
    }

}