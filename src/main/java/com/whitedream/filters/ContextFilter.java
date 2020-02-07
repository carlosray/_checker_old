package com.whitedream.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContextFilter implements Filter {
    private static final Logger logger = Logger.getLogger(ContextFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("Context filter initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        //Redirect to login
        logger.debug("user requested contextPath so redirected to /login");
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");
    }

    @Override
    public void destroy() {

    }
}
