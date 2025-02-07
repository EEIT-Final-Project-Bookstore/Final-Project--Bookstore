package com.finalproject.ispan.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.finalproject.ispan.domain.OrderBean;

public interface OrderRepository extends JpaRepository<OrderBean, String> {
	Optional<OrderBean> findByOrderId(String orderId);
//	取得顧客所有訂單，並按 orderCreationTime 降序排列(最新訂單在最前面)
	List<OrderBean> findByCustomer_CustomerIDOrderByOrderCreationTimeDesc(Long customerID);
//	按 orderCreationTime 降序排列，回傳該顧客最新一筆訂單
	OrderBean findTopByCustomer_CustomerIDOrderByOrderCreationTimeDesc(Long customerID);
//	計算使用該優惠券的訂單數量
	@Query("SELECT COUNT(o) FROM OrderBean o WHERE o.coupon.couponId = :couponId")
	long countOrdersByCouponId(@Param("couponId") Integer couponId);

}
