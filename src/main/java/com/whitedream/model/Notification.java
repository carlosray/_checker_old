package com.whitedream.model;

import java.io.Serializable;
import java.util.Objects;

public class Notification implements Serializable {
    private String type;
    private String destinationAddress;
    private User user;

    public Notification(String type, String destinationAddress, User user) {
        this.type = type;
        this.destinationAddress = destinationAddress;
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(destinationAddress, that.destinationAddress) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, destinationAddress, user);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "type='" + type + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", user=" + user +
                '}';
    }
}
