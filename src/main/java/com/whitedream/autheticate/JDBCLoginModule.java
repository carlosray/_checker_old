package com.whitedream.autheticate;

import com.whitedream.autheticate.principal.PasswordPrincipal;
import com.whitedream.autheticate.principal.UserPrincipal;
import org.apache.log4j.Logger;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class JDBCLoginModule implements LoginModule {
    private static final Logger logger = Logger.getLogger(JDBCLoginModule.class);
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

    public JDBCLoginModule() {
        super();
    }

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        debug = "true".equalsIgnoreCase((String) options.get("debug"));
        logger.debug("JDBCLoginModule initialized");
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

            if (isValidUser()) { //validate user.
                succeeded = true;
                return true;
            }

        } catch (UnsupportedCallbackException | IOException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    private boolean isValidUser() {

        return false;
    }

    @Override
    public boolean commit() throws LoginException {
        if (!succeeded) {
            return false;
        }

    }

    @Override
    public boolean abort() throws LoginException {
        if (!succeeded) {
            return false;
        } else if (!commitSucceeded) {
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
    public boolean logout() throws LoginException {
        subject.getPrincipals().remove(userPrincipal);
        succeeded = false;
        succeeded = commitSucceeded;
        username = null;
        if (password != null) {
            Arrays.fill(password, ' ');
            password = null;
        }
        userPrincipal = null;
        return true;
    }
}
