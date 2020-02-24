package com.whitedream.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "notifications")
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private int id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "notification_type_id")
    private NotificationType type;

    @Column(name = "destination_address")
    private String destinationAddress;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at")
    private Date creationDate;

    public Notification() {
    }

    public Notification(NotificationType type, String destinationAddress, User user) {
        this.type = type;
        this.destinationAddress = destinationAddress;
        this.user = user;
    }

    public Notification(NotificationType type, String destinationAddress, User user, Date creationDate) {
        this.type = type;
        this.destinationAddress = destinationAddress;
        this.user = user;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return id == that.id &&
                type.equals(that.type) &&
                destinationAddress.equals(that.destinationAddress) &&
                user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, destinationAddress, user);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", type=" + type +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", user=" + user +
                ", creationDate=" + creationDate +
                '}';
    }
}
