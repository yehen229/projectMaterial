package cn.edu.bistu.cs.projectmaterialmanagement.webserver.config;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        System.out.println("CustomAccessDeniedHandler");
        String detaimMessage = null;
        if (accessDeniedException instanceof MissingCsrfTokenException) {
            detaimMessage = "缺少CSRF TOKEN";
        } else if (accessDeniedException instanceof InvalidCsrfTokenException) {
            detaimMessage = "无效CSRF TOKEN";
        } else if (accessDeniedException instanceof CsrfException) {
            detaimMessage = accessDeniedException.getLocalizedMessage();
        } else if (accessDeniedException instanceof AuthorizationServiceException) {
            detaimMessage = AuthorizationServiceException.class.getSimpleName() + " " + accessDeniedException.getLocalizedMessage();
        }

        System.out.println(detaimMessage);

        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().println(OBJECT_MAPPER.writeValueAsString(new BusinessException("认证错误")));


    }
}
