package bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bookstore.domain.NotificationsBean;
import bookstore.domain.StatusBean;
import bookstore.repository.StatusesRepository;
import bookstore.service.NotificationsService;




@RestController
@RequestMapping("/admin/notifications")
public class NotificationsController {

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private StatusesRepository statusesRepository;

    /**
     * 获取某个顾客的所有通知
     */
    @GetMapping("/customer/{customerID}")
    public List<NotificationsBean> getNotificationsForCustomer(@PathVariable int customerID) {
        return notificationsService.getNotificationsByCustomerID(customerID);
    }

    /**
     * 新增通知（默认未读状态）
     */
    @PostMapping("/create")
    public NotificationsBean createNotification(@RequestBody NotificationsBean notification) {
        // 设置默认状态为未读
        StatusBean unreadStatus = statusesRepository.findById(4)
                .orElseThrow(() -> new IllegalArgumentException("StatusID 4 (未读状态) 不存在！"));
        notification.setStatus(unreadStatus);
        return notificationsService.saveNotification(notification);
    }

    /**
     * 更新通知
     */
    @PutMapping("/update/{id}")
    public NotificationsBean updateNotification(@PathVariable int id, @RequestBody NotificationsBean notification) {
        notification.setNotificationID(id); // 设置更新的通知ID
        return notificationsService.saveNotification(notification);
    }

    /**
     * 删除指定ID的通知
     */
    @DeleteMapping("/delete/{id}")
    public String deleteNotification(@PathVariable int id) {
        notificationsService.deleteNotification(id);
        return "通知删除成功！";
    }

    /**
     * 一键标记顾客的所有通知为已读
     */
    @PutMapping("/mark-all-as-read/{customerID}")
    public String markAllNotificationsAsRead(@PathVariable int customerID) {
        notificationsService.markAllAsRead(customerID);
        return "所有通知已标记为已读！";
    }
}
