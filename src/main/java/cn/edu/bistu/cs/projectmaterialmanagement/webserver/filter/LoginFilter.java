package cn.edu.bistu.cs.projectmaterialmanagement.webserver.filter;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IJWTTokenService;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class LoginFilter extends OncePerRequestFilter {


    private static final Logger log =
            LoggerFactory.getLogger(LoginFilter.class);

    private final IJWTTokenService jwtTokenService;

    @Resource
    private RedisTemplate<String, User> redisTemplate;


    public LoginFilter(IJWTTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;

    }

    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // log.info("获取客户端ip: " + ip);
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        // authorization = null;
        // System.out.println(authorization);


        //   logger.info(request.getRequestURI());
        //  logger.warn(authorization);

        /**
         * 1.如果是登录页面或其它不需要登录就能够访问的资源，则放行
         * 2.如果访问的资源需要用户登录，则首先验证Token是否正确，如果正确，则放行；如果不正确，则返回错误
         */

        if (request.getRequestURI().equals("/user/v1/login")//用户登录
                || request.getRequestURI().equals("/user/v1/signUp")//注册用户
                || request.getRequestURI().equals("/user/v1/publicKey")//前端加密公钥
                || request.getRequestURI().equals("/user/v1/captcha.jpg") //验证图片

        ) {
            filterChain.doFilter(request, response);
        } else if (authorization == null) {
            throw new BadCredentialsException("用户未登录,authorization:null");
        } else {
            DecodedJWT decodedJWT = jwtTokenService.verify(authorization.substring(7));//7为去除前面的7个字符（Bearer ）
            if (decodedJWT == null)
                throw new BadCredentialsException("用户未登录,decodedJWT:null");


            //验签失败返回false
            //成功返回true
            String payload = decodedJWT.getClaim("tokenId").toString();


            payload = payload.replace("\"", "");


            if (payload.equals("")) {
                throw new BadCredentialsException("用户未登录");
            } else {
                //已登录，获取用户信息，进行授权
                User userInfo = redisTemplate.opsForValue().get(payload);  //取缓存
                //logger.info("userInfo：" + userInfo);
                if (userInfo == null) {
                    //用户凭证过期
                    redisTemplate.delete(payload);//删除用户登录信息
                    SecurityContextHolder.clearContext();   //将用户认证信息删除
                    throw new BadCredentialsException("用户登录过期, 需要重新登录");
                } else {
                    //logger.info("userInfo getAuthorities：" + userInfo.getAuthorities().toString());
                    UsernamePasswordAuthenticationToken token =
                            new UsernamePasswordAuthenticationToken(userInfo, null, userInfo.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(token);
                    //logger.info("SecurityContextHolder信息：" + SecurityContextHolder.getContext());

                }
                // logger.warn("attributes: " + customSecurityMetadataSource.getAllConfigAttributes());
                filterChain.doFilter(request, response);
            }
        }
    }
}
