package com.whitedream.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_name")
    private String password;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_role_id")
    private Role role;

    @Column(name = "created_at")
    private Date creationDate;

    @OneToMany(mappedBy = "user_id", fetch = FetchType.EAGER)
    private Collection<Notification> notificationsByUser;

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

    public Collection<Notification> getNotificationsByUser() {
        return notificationsByUser;
    }

    public void setNotificationsByUser(Collection<Notification> notificationsByUser) {
        this.notificationsByUser = notificationsByUser;
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
