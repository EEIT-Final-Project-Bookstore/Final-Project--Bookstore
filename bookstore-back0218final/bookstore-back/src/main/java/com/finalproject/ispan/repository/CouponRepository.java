package com.finalproject.ispan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalproject.ispan.domain.CouponBean;

public interface CouponRepository extends JpaRepository<CouponBean, Integer> {
	CouponBean findByCouponCode(String couponCode);
}
