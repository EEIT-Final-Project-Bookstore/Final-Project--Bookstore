package com.finalproject.ispan.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.ispan.domain.NotificationsBean;
import com.finalproject.ispan.domain.StatusBean;
import com.finalproject.ispan.repository.NotificationRepository;
import com.finalproject.ispan.repository.StatusRepository;

@Service
public class NotificationsService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private StatusRepository statusRepository;

    /**
     * 根据顾客ID获取通知列表
     */
    public List<NotificationsBean> getNotificationsByCustomerID(int customerID) {
        return notificationRepository.findByCustomer_CustomerID(customerID);
    }

    /**
     * 保存或更新通知
     */
    /*
     * public NotificationsBean saveNotification(NotificationsBean notification) {
     * // 检查是否为更新操作
     * if (notification.getNotificationID() != 0) {
     * // 如果是更新操作，从数据库中加载现有记录
     * NotificationsBean existingNotification =
     * notificationRepository.findById(notification.getNotificationID())
     * .orElseThrow(() -> new IllegalArgumentException("通知不存在，ID: " +
     * notification.getNotificationID()));
     * 
     * // 保留现有的 createdTime 值
     * notification.setCreatedTime(existingNotification.getCreatedTime());
     * } else {
     * // 如果是新增操作，设置创建时间
     * notification.setCreatedTime(LocalDateTime.now());
     * }
     * 
     * // 无论是新增还是更新，都需要设置更新时间
     * notification.setUpdatedTime(LocalDateTime.now());
     * 
     * return notificationRepository.save(notification);
     * }
     */
    public NotificationsBean saveNotification(NotificationsBean notification) {
        // 检查是否为更新操作
        if (notification.getNotificationID() != 0) {
            // 如果是更新操作，从数据库中加载现有记录
            NotificationsBean existingNotification = notificationRepository.findById(notification.getNotificationID())
                    .orElseThrow(() -> new IllegalArgumentException("通知不存在，ID: " + notification.getNotificationID()));

            // 保留现有的 createdTime 值
            notification.setCreatedTime(existingNotification.getCreatedTime());
        } else {
            // 如果是新增操作，设置创建时间
            notification.setCreatedTime(LocalDateTime.now());
        }

        // 无论是新增还是更新，都需要设置更新时间
        notification.setUpdatedTime(LocalDateTime.now());

        // 强制设置 StatusID 为 12（未读状态）
        StatusBean unreadStatus = statusRepository.findById(12)
                .orElseThrow(() -> new IllegalArgumentException("StatusID 12 (未读状态) 不存在！"));
        notification.setStatus(unreadStatus);

        return notificationRepository.save(notification);
    }

    /**
     * 根据通知ID删除通知
     */
    public void deleteNotification(int notificationID) {
        notificationRepository.deleteById(notificationID);
    }

    /**
     * 一键标记顾客的所有通知为已读
     */
    public void markAllAsRead(int customerID) {
        // 获取状态 ID 为 11（已读）的状态
        StatusBean readStatus = statusRepository.findById(11)
                .orElseThrow(() -> new IllegalArgumentException("StatusID 11 (已读状态) 不存在！"));

        // 获取顾客的所有通知
        List<NotificationsBean> notifications = notificationRepository.findByCustomer_CustomerID(customerID);

        // 将所有通知状态标记为已读
        for (NotificationsBean notification : notifications) {
            notification.setStatus(readStatus);
        }

        // 批量保存
        notificationRepository.saveAll(notifications);
    }

    public List<NotificationsBean> getAllNotifications() {
        return notificationRepository.findAll(); // 使用 JPA 的內建方法
    }
}