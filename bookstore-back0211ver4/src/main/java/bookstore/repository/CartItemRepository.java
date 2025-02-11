package bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.domain.CartItemBean;

public interface CartItemRepository extends JpaRepository<CartItemBean, Integer> {

}
