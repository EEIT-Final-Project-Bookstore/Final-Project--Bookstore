package com.finalproject.ispan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalproject.ispan.domain.OrderBean;
import com.finalproject.ispan.domain.OrderDetailBean;

public interface OrderDetailRepository extends JpaRepository<OrderDetailBean, Integer> {
	List<OrderDetailBean> findByOrder(OrderBean order);
}
