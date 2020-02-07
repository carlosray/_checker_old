package com.whitedream.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    private static final Logger logger = Logger.getLogger(LoginFilter.class);
    private FilterConfig fc;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.fc = filterConfig;
        logger.debug("Login filter initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String uri = httpServletRequest.getRequestURI();
        HttpSession session = httpServletRequest.getSession(false);
        //если активна сессия, то вернуть в main
        if (session != null) {
            logger.debug("Session ID '" + session.getId() + "' requested to /login/* so redirected to /main");
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/main");
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
