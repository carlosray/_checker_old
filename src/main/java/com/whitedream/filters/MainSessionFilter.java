package com.whitedream.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MainSessionFilter implements Filter {
    private static final Logger logger = Logger.getLogger(MainSessionFilter.class);
    private FilterConfig fc;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.fc = filterConfig;
        logger.debug("Main filter initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String uri = httpServletRequest.getRequestURI();
        HttpSession session = httpServletRequest.getSession(false);
        //если не активна сессия, то перекинуть на авторизацию
        if (session == null) {
            logger.debug("Unknown user requested to /main/* so redirected to /login");
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
