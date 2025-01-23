package bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.domain.OrderBean;



public interface OrderRepository extends JpaRepository<OrderBean, Integer> {
	Optional<OrderBean> findById(Integer orderID);

}
