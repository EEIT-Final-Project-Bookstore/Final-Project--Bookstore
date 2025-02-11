package bookstore.repository;

<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> a54bf433a72d9698fcf5758036056789b27b8af3
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import bookstore.domain.CouponBean;
import bookstore.domain.CouponOwnershipBean;
import bookstore.domain.CustomerBean;

<<<<<<< HEAD
public interface CouponOwnershipRepository extends JpaRepository<CouponOwnershipBean, Integer>{
	 // 根據用戶 ID 和優惠券代碼查詢記錄
	 @Query("SELECT co FROM CouponOwnershipBean co " +
	         "WHERE co.customer.customerID = :customerID " +
	         "AND co.coupon.couponId = :couponId")
	 CouponOwnershipBean findByCustomerIdAndCouponId(@Param("customerID") Long customerID, 
	                                              @Param("couponId") Integer couponId);
	 
	 // 根據優惠券 ID 刪除所有擁有該優惠券的 CouponOwnership 記錄
	    @Transactional
	    void deleteByCoupon_CouponId(Integer couponId);
	    
	    // 查詢指定 customer 和 coupon 的關聯
	    Optional<CouponOwnershipBean> findByCustomerAndCoupon(CustomerBean customer, CouponBean coupon);
	    
	    // 顧客查詢所有優惠券
	    List<CouponOwnershipBean> findByCustomerCustomerID(Long customerID);
	}
=======
public interface CouponOwnershipRepository extends JpaRepository<CouponOwnershipBean, Integer> {
	// 根據用戶 ID 和優惠券代碼查詢記錄
	@Query("SELECT co FROM CouponOwnershipBean co " +
			"WHERE co.customer.customerID = :customerID " +
			"AND co.coupon.couponId = :couponId")
	CouponOwnershipBean findByCustomerIdAndCouponId(@Param("customerID") Long customerID,
			@Param("couponId") Integer couponId);

	// 根據優惠券 ID 刪除所有擁有該優惠券的 CouponOwnership 記錄
	@Transactional
	void deleteByCoupon_CouponId(Integer couponId);

	// 查詢指定 customer 和 coupon 的關聯
	Optional<CouponOwnershipBean> findByCustomerAndCoupon(CustomerBean customer, CouponBean coupon);

	// List<CouponOwnershipBean> findByCustomer_CustomerId(Long customerId);
}
>>>>>>> a54bf433a72d9698fcf5758036056789b27b8af3
