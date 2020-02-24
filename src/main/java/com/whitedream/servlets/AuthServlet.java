package com.whitedream.servlets;

import com.whitedream.autheticate.PassiveCallbackHandler;
import com.whitedream.listeners.SessionLoginContext;
import com.whitedream.model.User;
import com.whitedream.utils.AuthRegexChecker;
import com.whitedream.utils.PasswordUtils;
import org.apache.log4j.Logger;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Используется FORM Authentication (j_security_check)
 */
@Deprecated
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
        List<String> errors;
        if (!checker.check()) {
            errors = checker.getErrors();
            sendErrorsToView(req, resp, errors);
        }
        PassiveCallbackHandler cbh = new PassiveCallbackHandler(username, password);
        SessionLoginContext loginContext = null;
        try {
            loginContext = new SessionLoginContext(SessionLoginContext.getName(), cbh);
            loginContext.login();
        } catch (LoginException e) {
            logger.debug(e.getMessage() + " | IP " + req.getRemoteAddr());
            errors = Collections.singletonList(e.getMessage());
            sendErrorsToView(req, resp, errors);
        }
        if (loginContext != null) {
            HttpSession session = req.getSession();
            session.setAttribute("loginContext", loginContext);
            resp.sendRedirect(req.getContextPath() + "/main");
        }
        //TODO послать редирект на страницу с общей ошибкой "что-то пошло не так"
    }


    private void sendErrorsToView(HttpServletRequest req, HttpServletResponse resp, List<String> errors) throws ServletException, IOException {
        req.setAttribute("errors", errors);
        String loginPage = "WEB-INF/jsp/login/login.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(loginPage);
        dispatcher.forward(req, resp);
    }
}
