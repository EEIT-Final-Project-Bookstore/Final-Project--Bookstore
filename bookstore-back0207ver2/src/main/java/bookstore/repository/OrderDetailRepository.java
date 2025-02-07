package bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.domain.OrderBean;
import bookstore.domain.OrderDetailBean;

public interface OrderDetailRepository extends JpaRepository<OrderDetailBean, Integer> {
	List<OrderDetailBean> findByOrder(OrderBean order);
}
