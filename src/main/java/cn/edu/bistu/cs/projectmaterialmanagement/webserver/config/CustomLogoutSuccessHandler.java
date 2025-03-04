package cn.edu.bistu.cs.projectmaterialmanagement.webserver.config;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IJWTTokenService;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final IJWTTokenService jwtTokenService;

    @Resource
    private RedisTemplate<String, User> redisTemplate;

    public CustomLogoutSuccessHandler(IJWTTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication)
            throws IOException, ServletException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null)
            throw new BadCredentialsException("用户未登录");

        DecodedJWT decodedJWT = jwtTokenService.verify(authorization.substring(7));//7为去除前面的7个字符（Bearer ）
        if (decodedJWT == null)
            throw new BadCredentialsException("用户未登录");


        //验签失败返回false
        //成功返回true
        String payload = decodedJWT.getClaim("tokenId").toString();


        payload = payload.replace("\"", "");
        redisTemplate.delete(payload);//删除用户登录信息
        SecurityContextHolder.clearContext();   //将用户认证信息删除


        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().println(OBJECT_MAPPER.writeValueAsString("注销成功"));


    }
}
