package finatest.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import finatest.domain.NotificationsBean;

public interface NotificationRepository extends JpaRepository<NotificationsBean, Integer> {

    // 使用 Spring Data JPA 的查询命名规则，根据 Customer 的 ID 获取通知列表
    List<NotificationsBean> findByCustomer_CustomerID(Integer customerID);
}

