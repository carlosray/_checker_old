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

public class SessionLoginContext extends LoginContext implements HttpSessionBindingListener {
    private static final Logger logger = Logger.getLogger(LoginContext.class);

    /**
     * Default constructor. See javax.security.auth.login.LoginContext
     * for details.
     */
    public SessionLoginContext(String name) throws LoginException {
        super(name);
    }

    /**
     * Default constructor. See javax.security.auth.login.LoginContext
     * for details.
     */
    public SessionLoginContext(String name, CallbackHandler callbackHandler) throws LoginException {
        super(name, callbackHandler);
    }

    /**
     * Default constructor. See javax.security.auth.login.LoginContext
     * for details.
     */
    public SessionLoginContext(String name, Subject subject) throws LoginException {
        super(name, subject);
    }

    /**
     * Default constructor. See javax.security.auth.login.LoginContext
     * for details.
     */
    public SessionLoginContext(String name, Subject subject, CallbackHandler callbackHandler) throws LoginException {
        super(name, subject, callbackHandler);
    }


    public void valueBound(HttpSessionBindingEvent event) {
        String sessionName = (String) event.getSession().getAttribute("sessionName");
        String sessionID = event.getSession().getId();
        String formatMessage = String.format("Session created [username = %s; sessionName = %s; sessionID = %s]", getUsername(), sessionName, sessionID);
        logger.debug(formatMessage);
    }

    public void valueUnbound(HttpSessionBindingEvent event) {
        try {
            logout();
            String sessionName = (String) event.getSession().getAttribute("sessionName");
            String sessionID = event.getSession().getId();
            String formatMessage = String.format("Session destroyed [username = %s; sessionName = %s; sessionID = %s]", getUsername(), sessionName, sessionID);
            logger.debug(formatMessage);
        } catch (LoginException ex) {
            logger.error(ex.getMessage());
        }
    }

    private String getUsername(){
        String username = "";
        Set<Principal> principals = this.getSubject().getPrincipals();
        for (Principal principal : principals) {
            if (principal instanceof UserPrincipal){
                username = principal.getName();
            }
        }
        return username;
    }
}
