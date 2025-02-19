package com.finalproject.ispan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalproject.ispan.domain.CustomerBean;

public interface CustomerRepository extends JpaRepository<CustomerBean, Long> {
	Optional<CustomerBean> findByUsername(String username);
	Optional<CustomerBean> findByCustomerID(Long customerID);
	boolean existsByUsername(String username);
	void deleteByUsername(String username);
	boolean existsByEmail(String email);
}
