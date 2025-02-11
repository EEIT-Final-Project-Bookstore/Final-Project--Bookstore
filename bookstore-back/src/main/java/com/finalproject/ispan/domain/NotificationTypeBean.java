package com.finalproject.ispan.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "NotificationTypes")
public class NotificationTypeBean {
    @Id
    @Column(name = "NotificationTypeID")
    private int notificationTypeID;

    @Column(name = "NotificationType", nullable = false)
    private String notificationType;

    // Getters and Setters
    public int getNotificationTypeID() {
        return notificationTypeID;
    }

    public void setNotificationTypeID(int notificationTypeID) {
        this.notificationTypeID = notificationTypeID;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
}