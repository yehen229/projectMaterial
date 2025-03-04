package cn.edu.bistu.cs.projectmaterialmanagement.webserver.model.account;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserLoginResult {
    private String token;
    private List<String> roleList;
    private String userName;
    private String userRealName;
    private String userId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
