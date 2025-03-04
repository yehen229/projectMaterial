package cn.edu.bistu.cs.projectmaterialmanagement.webserver.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationExpression {
    public boolean hasTrue(String permission) {

        //从 securityContextHolder中获取用户权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object o = authentication.getPrincipal();
        return true;

        //权限信息
        //List<String> permissions = userDetails.getPermissions();


        //判断是否有权限
        //return permissions.contains(permission);
    }
}
