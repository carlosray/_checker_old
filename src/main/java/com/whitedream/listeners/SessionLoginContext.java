package com.whitedream.listeners;

import com.whitedream.autheticate.principal.UserPrincipal;
import org.apache.log4j.Logger;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.security.Principal;
import java.util.Set;

/**
 * Не используется, т.к. FORM Authentication (j_security_check)
 */
@Deprecated
public class SessionLoginContext extends LoginContext implements HttpSessionBindingListener {
    private static final Logger logger = Logger.getLogger(LoginContext.class);
    private static String name = "Checker";

    public SessionLoginContext() throws LoginException {
        super(SessionLoginContext.name);
    }

    /**
     * Default constructor. See javax.security.auth.login.LoginContext
     * for details.
     */
    public SessionLoginContext(String name) throws LoginException {
        super(name);
        SessionLoginContext.name = name;
    }

    /**
     * Default constructor. See javax.security.auth.login.LoginContext
     * for details.
     */
    public SessionLoginContext(String name, CallbackHandler callbackHandler) throws LoginException {
        super(name, callbackHandler);
        SessionLoginContext.name = name;
    }

    /**
     * Default constructor. See javax.security.auth.login.LoginContext
     * for details.
     */
    public SessionLoginContext(String name, Subject subject) throws LoginException {
        super(name, subject);
        SessionLoginContext.name = name;
    }

    /**
     * Default constructor. See javax.security.auth.login.LoginContext
     * for details.
     */
    public SessionLoginContext(String name, Subject subject, CallbackHandler callbackHandler) throws LoginException {
        super(name, subject, callbackHandler);
        SessionLoginContext.name = name;
    }


    public void valueBound(HttpSessionBindingEvent event) {
        String sessionID = event.getSession().getId();
        String formatMessage = String.format("Session created for [username = %s; sessionID = %s]", getUsername(), sessionID);
        logger.debug(formatMessage);
    }

    public void valueUnbound(HttpSessionBindingEvent event) {
        try {
            logout();
            String sessionID = event.getSession().getId();
            String formatMessage = String.format("Session destroyed for [username = %s; sessionID = %s]", getUsername(), sessionID);
            logger.debug(formatMessage);
        } catch (LoginException ex) {
            logger.error(ex.getMessage());
        }
    }

    private String getUsername(){
        String username = "";
        Subject subject = this.getSubject();
        if (subject != null) {
            Set<Principal> principals = subject.getPrincipals();
            for (Principal principal : principals) {
                if (principal instanceof UserPrincipal) {
                    username = principal.getName();
                }
            }
        }
        return username;
    }

    public static String getName() {
        return name;
    }
}
