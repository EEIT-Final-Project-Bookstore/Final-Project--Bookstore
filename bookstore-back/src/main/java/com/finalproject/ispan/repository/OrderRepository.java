package com.finalproject.ispan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalproject.ispan.domain.OrderBean;

public interface OrderRepository extends JpaRepository<OrderBean, Integer> {

}
