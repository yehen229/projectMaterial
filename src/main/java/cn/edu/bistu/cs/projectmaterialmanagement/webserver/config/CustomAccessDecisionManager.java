package cn.edu.bistu.cs.projectmaterialmanagement.webserver.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

//自定义决策器,决定访问是否允许
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {

    private static final Logger log =
            LoggerFactory.getLogger(CustomAccessDecisionManager.class);

    @Override
    public void decide(Authentication authentication,
                       Object object,
                       Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {


        log.info("函数调用：CustomAccessDecisionManager");

        if (configAttributes.isEmpty()) {
            log.info("函数调用：CustomAccessDecisionManager-empty");
            return;
        }


        for (ConfigAttribute configAttribute : configAttributes) {
            String attribute = configAttribute.getAttribute();
            System.out.println(attribute);
            System.out.println(authentication.getAuthorities());
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (attribute.trim().equals(authority.getAuthority())) {
                    log.info("函数调用：CustomAccessDecisionManager-for");
                    return;
                }
            }
            log.info("函数调用：CustomAccessDecisionManager-throw");
            throw new AccessDeniedException("对不起，你没有权限");
        }

        log.info("函数调用：CustomAccessDecisionManager-end");

    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
