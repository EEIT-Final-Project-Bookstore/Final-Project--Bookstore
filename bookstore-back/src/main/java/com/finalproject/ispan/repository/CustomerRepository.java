package com.finalproject.ispan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalproject.ispan.domain.CustomerBean;

public interface CustomerRepository extends JpaRepository<CustomerBean, Integer> {
	Optional<CustomerBean> findByUsername(String username);
	Optional<CustomerBean> findById(Integer customerId);
}
