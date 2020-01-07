package com.whitedream.servlets;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
        List<String> errors = new ArrayList<>();
        if (!isCorrectUsername(username)){
            String loginMessage = "username не прошел первичную проверку (не соответсвует требованиям)";
            logger.error(loginMessage);
            errors.add(loginMessage);
        }
        if (!isCorrectPassword(password)){
            String passwordMessage = "password не прошел первичную проверку (не соответсвует требованиям)";
            logger.error(passwordMessage);
            errors.add(passwordMessage);
        }
        if (!errors.isEmpty()){
            req.setAttribute("errorsList", errors);
            resp.sendRedirect(req.getContextPath() + "/login");
        }
        else {

        }

    }

    /**
     * @param username логин
     * @return возвращает true, если username прошел проверку
     */
    private boolean isCorrectUsername(String username){
        if (username == null) return false;
        //TODO Верификация логина
        return true;
    }

    /**
     * @param password пароль
     * @return возвращает true, если password прошел проверку
     */
    private boolean isCorrectPassword(String password){
        if (password == null) return false;
        //TODO Верификация пароля
        return true;
    }
}
