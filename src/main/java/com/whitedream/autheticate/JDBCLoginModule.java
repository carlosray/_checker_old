package com.whitedream.autheticate;

import com.whitedream.autheticate.principal.PasswordPrincipal;
import com.whitedream.autheticate.principal.RolePrincipal;
import com.whitedream.autheticate.principal.UserPrincipal;
import com.whitedream.dao.DaoFactory;
import com.whitedream.dao.UserDao;
import com.whitedream.dao.exception.NoEntityExistsException;
import com.whitedream.dao.util.ConnectionBuilder;
import com.whitedream.dao.util.DBConnectionBuilder;
import com.whitedream.model.User;
import com.whitedream.utils.PasswordUtils;
import org.apache.log4j.Logger;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Map;

public class JDBCLoginModule implements LoginModule {
    private static final Logger logger = Logger.getLogger(JDBCLoginModule.class);
    private ConnectionBuilder connectionBuilder;
    private Subject subject;
    private CallbackHandler callbackHandler;
    private boolean debug;
    private boolean succeeded = false;
    private boolean commitSucceeded = false;
    //user credentials
    private String username = null;
    private char[] password = null;

    //user principle
    private UserPrincipal userPrincipal = null;
    private PasswordPrincipal passwordPrincipal = null;
    private RolePrincipal rolePrincipal = null;
    private User user;

    public JDBCLoginModule() {
        super();
    }

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        debug = "true".equalsIgnoreCase((String) options.get("debug"));
        logger.debug("JDBCLoginModule initialized");
        connectionBuilder = new DBConnectionBuilder();
    }

    @Override
    public boolean login() throws LoginException {
        if (callbackHandler == null) {
            throw new LoginException("Error: no CallbackHandler available " +
                    "to garner authentication information from the user");
        }
        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("username");
        callbacks[1] = new PasswordCallback("password", false);
        try {
            callbackHandler.handle(callbacks);
            username = ((NameCallback) callbacks[0]).getName();
            password = ((PasswordCallback) callbacks[1]).getPassword();

            if (debug) {
                logger.debug("Username :" + username);
                logger.debug("Password : " + Arrays.toString(password));
            }

            if (username == null || password == null) {
                throw new LoginException("Callback handler does not return login data properly");
            }
            //validate user.
            if (isValidUser()) {
                succeeded = true;
                return true;
            }
            else {
                throw new LoginException("Неправильное имя пользователя или пароль");
            }
        } catch (Exception e) {
            throw new LoginException("Ошибка аутентификации: " + e.getMessage());
        }
    }

    private boolean isValidUser() throws Exception {
        try (Connection connection = connectionBuilder.getConnection()) {
            UserDao userDao = DaoFactory.getUserDao(connection);
            user = userDao.getUser(username);
            return PasswordUtils.check(Arrays.toString(password), user.getPassword());
        } catch (NoEntityExistsException e) {
            return false;
        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        }
    }

    @Override
    public boolean commit() {
        if (!succeeded) {
            return false;
        } else {
            String logMessage = String.format("Пользователь '%s' : ", user.getUserName());
            userPrincipal = new UserPrincipal(user.getUserName());
            if (!subject.getPrincipals().contains(userPrincipal)) {
                subject.getPrincipals().add(userPrincipal);
                logMessage += " | добавлен principal: " + userPrincipal;
            }
            passwordPrincipal = new PasswordPrincipal(user.getPassword());
            if (!subject.getPrincipals().contains(passwordPrincipal)) {
                subject.getPrincipals().add(passwordPrincipal);
                logMessage += " | добавлен principal: " + passwordPrincipal;
            }
            rolePrincipal = new RolePrincipal(user.getRole().getRoleName());
            if (!subject.getPrincipals().contains(rolePrincipal)) {
                subject.getPrincipals().add(rolePrincipal);
                logMessage += " | добавлен principal: " + rolePrincipal;
            }
            if (debug) {
                logger.debug(logMessage);
            }
            subject.getPrivateCredentials().add(user);
        }
        commitSucceeded = true;
        logger.debug(String.format("Пользователь '%s' успешно наполнен principal", user.getUserName()));
        return true;
    }

    @Override
    public boolean abort() {
        if (!succeeded) {
            return false;
        } else if (!commitSucceeded) {
            user = null;
            succeeded = false;
            username = null;
            if (password != null) {
                password = null;
            }
            userPrincipal = null;
        } else {
            logout();
        }
        return true;
    }

    @Override
    public boolean logout() {
        subject.getPrincipals().remove(userPrincipal);
        subject.getPrincipals().remove(passwordPrincipal);
        subject.getPrincipals().remove(rolePrincipal);
        succeeded = false;
        succeeded = commitSucceeded;
        username = null;
        if (password != null) {
            Arrays.fill(password, ' ');
            password = null;
        }
        userPrincipal = null;
        passwordPrincipal = null;
        rolePrincipal = null;
        return true;
    }
}
