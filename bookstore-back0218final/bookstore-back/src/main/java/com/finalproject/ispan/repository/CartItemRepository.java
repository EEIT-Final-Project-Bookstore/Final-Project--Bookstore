package com.finalproject.ispan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalproject.ispan.domain.CartItemBean;

public interface CartItemRepository extends JpaRepository<CartItemBean, Integer>{

}
