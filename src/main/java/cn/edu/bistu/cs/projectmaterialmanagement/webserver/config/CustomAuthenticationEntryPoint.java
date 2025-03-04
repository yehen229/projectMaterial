package cn.edu.bistu.cs.projectmaterialmanagement.webserver.config;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.common.ResultData;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 认证失败处理
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        System.out.println("CustomAuthenticationEntryPoint");
        String detailMessage = authException.getClass().getSimpleName() + " " + authException.getLocalizedMessage();

        if (authException instanceof InsufficientAuthenticationException) {
            detailMessage = "请登录后再访问";
        }


        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().println(OBJECT_MAPPER.writeValueAsString(ResultData.failed(detailMessage)));
    }
}
