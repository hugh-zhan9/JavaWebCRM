package com.hugh.crm.filter;

import com.hugh.crm.pojo.Users;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class HandlerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getServletPath();
        if("/login.jsp".equals(path) || "/user/login.do".equals(path)){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            HttpSession session = request.getSession();
            Users user  = (Users) session.getAttribute("user");
            if (user != null ) {
                filterChain.doFilter(servletRequest,servletResponse);
            }else {
            /*
                实际项目中一律使用绝对路径
                关于转发与重定向路径的问题：
                转发：使用的是一种特殊的绝对路径的使用方式，这种绝对路径前面不加/项目名，也成为内部路径
                /login.jsp
                重定向：使用传统的绝对路径写法，加项目名称
                /CRM/login.jsp
                使用request.getContextPath() 来获取 /项目名
             */
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }
        }
    }

    @Override
    public void destroy() {

    }
}
