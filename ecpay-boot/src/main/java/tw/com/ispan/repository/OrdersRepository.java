package tw.com.ispan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.com.ispan.domain.OrdersBean;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersBean, Integer> {
    // 如果需要其他查詢方法，這裡可以擴展
	Optional<OrdersBean> findById(Integer orderID);
}