package com.whitedream.utils;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Утильный класс первичной проверки логина и пароля на сооветсвие регулярному выражению(после формы)
 */
public class AuthRegexChecker {
    private static final Logger logger = Logger.getLogger(AuthRegexChecker.class);
    private static final String loginRegex;
    private static final String passwordRegex;
    private final String login;
    private final String password;
    private String ipAddress = "Unknown";
    private List<String> errors = new ArrayList<>();

    static {
        Properties properties = ServiceConfig.getConfig();
        loginRegex = properties.getProperty("form.login.regexp", "[a-zA-Z]+");
        passwordRegex = properties.getProperty("form.password.regexp", "[0-9a-zA-Z!@#$%^&*]+");
    }

    /**
     * @param login проверяемый логин на соответствие regexp
     * @param password проверяемый пароль на соответствие regexp
     */
    public AuthRegexChecker(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public AuthRegexChecker(String login, String password, String ipAddress) {
        this.login = login;
        this.password = password;
        this.ipAddress = ipAddress;
    }

    /**
     * Проверка логина и пароля на соответствие regexp
     * @return возвращает 'true', если проверка прошла успешно, иначе false + заполняет список ошибок {@link #errors}
     */
    public boolean check(){
        if (!isCorrectUsername(login)){
            logger.debug("username не прошел первичную проверку (IP " + ipAddress + ")");
            errors.add("Некорректное имя пользователя");
        }
        if (!isCorrectPassword(password)){
            logger.debug("password не прошел первичную проверку (IP " + ipAddress + ")");
            errors.add("Некорректный пароль");
        }
        return errors.isEmpty();
    }

    /**
     *
     * @return возвращает список ошибок
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * @param username логин
     * @return возвращает true, если username прошел проверку
     */
    private boolean isCorrectUsername(String username){
        if (username == null) return false;
        return username.matches(loginRegex);
    }

    /**
     * @param password пароль
     * @return возвращает true, если password прошел проверку
     */
    private boolean isCorrectPassword(String password){
        if (password == null) return false;
        return password.matches(passwordRegex);
    }
}
