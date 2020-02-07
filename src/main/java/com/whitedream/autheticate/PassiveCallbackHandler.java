package com.whitedream.autheticate;

import org.apache.log4j.Logger;

import javax.security.auth.callback.*;
import java.io.IOException;

public class PassiveCallbackHandler implements CallbackHandler {
    private final String username;
    private final String password;

    public PassiveCallbackHandler(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof NameCallback) {
                NameCallback nameCb = (NameCallback) callback;
                nameCb.setName(username);
            } else if (callback instanceof PasswordCallback) {
                PasswordCallback passCb = (PasswordCallback) callback;
                passCb.setPassword(password.toCharArray());
            } else {
                throw new UnsupportedCallbackException(callback, "The submitted Callback is unsupported");
            }
        }
    }
}
