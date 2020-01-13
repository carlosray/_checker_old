package com.whitedream.servlets;

import com.whitedream.utils.AuthRegexChecker;
import org.apache.log4j.Logger;

import javax.security.auth.login.LoginContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AuthServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AuthServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Запрос на авторизацию IP " + req.getRemoteAddr());
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        AuthRegexChecker checker = new AuthRegexChecker(username, password, req.getRemoteAddr());
        if (!checker.check()){
            List<String> errors = checker.getErrors();
            req.setAttribute("errors", errors);
            RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/login/login.jsp");
            dispatcher.forward(req, resp);
        }
        /*LoginContext loginContext;
        try {
            loginContext = new LoginContext()
        }*/
    }
}
