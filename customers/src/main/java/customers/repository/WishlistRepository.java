package customers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import customers.domain.WishlistBean;

public interface WishlistRepository extends JpaRepository<WishlistBean, Integer> {

}