package com.finalproject.ispan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalproject.ispan.domain.BookBean;
import com.finalproject.ispan.domain.CartBean;
import com.finalproject.ispan.domain.CustomerBean;

public interface CartRepository extends JpaRepository<CartBean, Integer> {
	CartBean findByCustomerId(Integer customerId); // 根據顧客ID查詢購物車

	Optional<CartBean> findByCustomerAndBooks(CustomerBean customerBean, BookBean books);
}
