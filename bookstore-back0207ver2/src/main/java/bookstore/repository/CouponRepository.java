package bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bookstore.domain.CouponBean;


public interface CouponRepository extends JpaRepository<CouponBean, Integer> {
	 CouponBean findByCouponCode(String couponCode);
}
