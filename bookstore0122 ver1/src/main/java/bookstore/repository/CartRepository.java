package bookstore.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.domain.BookBean;
import bookstore.domain.CartBean;
import bookstore.domain.CustomerBean;



public interface CartRepository extends JpaRepository<CartBean, Integer> {
	CartBean findByCustomerId(Integer customerId); // 根據顧客ID查詢購物車

	Optional<CartBean> findByCustomerAndBooks(CustomerBean customerBean, BookBean books);
}
