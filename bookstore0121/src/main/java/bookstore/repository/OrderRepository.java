package bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.domain.OrderBean;



public interface OrderRepository extends JpaRepository<OrderBean, Integer> {

}
