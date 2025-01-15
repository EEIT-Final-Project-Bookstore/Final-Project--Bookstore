package finatest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import finatest.domain.CustomerBean;
import finatest.domain.NotificationTypeBean;
import finatest.domain.NotificationsBean;
import finatest.domain.StatusBean;
import finatest.respository.NotificationRepository;
import finatest.service.NotificationsService;

@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    public void testInsertNotification() {
        // 创建一个 NotificationsBean 实例
        NotificationsBean notification = new NotificationsBean();

        // 设置通知类型
        NotificationTypeBean notificationType = new NotificationTypeBean();
        notificationType.setNotificationTypeID(1); // 假设数据库中存在 ID 为 1 的通知类型
        notification.setNotificationType(notificationType);

        // 设置顾客
        CustomerBean customer = new CustomerBean();
        customer.setCustomerID(2); // 假设数据库中存在 ID 为 2 的顾客
        notification.setCustomer(customer);

        // 设置状态
        StatusBean status = new StatusBean();
        status.setStatusID(4); // 假设数据库中存在 ID 为 4 的状态（未读）
        notification.setStatus(status);

        // 设置其他字段
        notification.setContent("25457575試試看"); // 设置通知内容
        notification.setCreatedTime(LocalDateTime.now());
        notification.setUpdatedTime(LocalDateTime.now());

        // 调用 NotificationsService 保存数据
        NotificationsBean savedNotification = notificationsService.saveNotification(notification);

        // 验证保存是否成功
        assertNotNull(savedNotification, "保存的通知对象不能为空");
        assertNotNull(savedNotification.getNotificationID(), "保存的通知ID不能为空");
        System.out.println("Inserted Notification ID: " + savedNotification.getNotificationID());
    }
}
