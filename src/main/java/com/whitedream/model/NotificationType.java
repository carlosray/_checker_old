package com.whitedream.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "notification_types")
public class NotificationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_type_id")
    private int id;

    @Column(name = "notification_type_name")
    private String name;

    @OneToMany(mappedBy = "notification_type_id", fetch = FetchType.EAGER)
    private Collection<Notification> notificationsByType;

    public NotificationType() {
    }

    public NotificationType(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Notification> getNotificationsByType() {
        return notificationsByType;
    }

    public void setNotificationsByType(Collection<Notification> notificationsByType) {
        this.notificationsByType = notificationsByType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationType that = (NotificationType) o;
        return id == that.id &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "NotificationType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", notificationsByType=" + notificationsByType +
                '}';
    }
}
