package cn.edu.bistu.cs.projectmaterialmanagement.webserver.config;


import jakarta.servlet.*;

import java.io.IOException;

public class CustomExceptionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        System.out.println("测试1");
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            request.setAttribute("exception", e);
            request.getRequestDispatcher(("/error")).forward(request, response);
        }
    }
}
