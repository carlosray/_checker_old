package com.whitedream.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class User implements Serializable {
    private int id;
    private String userName;
    private String password;
    private Role role;
    private Date creationDate;

    public User(String userName, String password, Role role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public User(int id, String userName, String password, Role role, Date creationDate) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.creationDate = creationDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userName.equals(user.userName) &&
                password.equals(user.password) &&
                role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", creationDate=" + creationDate +
                '}';
    }
}
