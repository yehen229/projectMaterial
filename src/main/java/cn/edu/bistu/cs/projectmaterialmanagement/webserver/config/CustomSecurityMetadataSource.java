package cn.edu.bistu.cs.projectmaterialmanagement.webserver.config;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static final Logger log =
            LoggerFactory.getLogger(CustomSecurityMetadataSource.class);
   /* private final ISysPermissionService sysPermissionService;
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    public CustomSecurityMetadataSource(ISysPermissionService sysPermissionService) {
        this.sysPermissionService = sysPermissionService;
    }*/

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {

        /*

        String requestURI = ((FilterInvocation) object).getRequest().getRequestURI();
        //System.out.println("CustomSecurityMetadataSource：访问方法：" + ((FilterInvocation) object).getRequest().getMethod());
        List<SysPermission> sysPermissionList = sysPermissionService.getAllPermissions();

        //System.out.println("CustomSecurityMetadataSource：requestURI: " + requestURI);
        for (SysPermission sysPermission : sysPermissionList) {
            if (antPathMatcher.match(sysPermission.getName(), requestURI)) {
                String[] permissions = new String[1];
                permissions[0] = sysPermission.getName();
                //System.out.println("permissions: " + Arrays.toString(permissions));
                return SecurityConfig.createList(permissions);
            }
        }*/
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
