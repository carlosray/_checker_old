package com.whitedream.autheticate.principal;

import java.io.Serializable;
import java.security.Principal;
import java.util.Objects;

public class PasswordPrincipal implements Principal, Serializable {
    private String name;

    public PasswordPrincipal(String name) {
        if (name == null)
            throw new NullPointerException("NULL password.");
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordPrincipal that = (PasswordPrincipal) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "PasswordPrincipal{" +
                "name='" + name + '\'' +
                '}';
    }
}
