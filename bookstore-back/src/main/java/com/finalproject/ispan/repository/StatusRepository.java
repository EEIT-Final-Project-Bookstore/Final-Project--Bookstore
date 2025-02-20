package com.finalproject.ispan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalproject.ispan.domain.StatusBean;

public interface StatusRepository extends JpaRepository<StatusBean, Integer> {
	StatusBean findByDetailedStatus(String detailedStatus);
//	StatusBean findFirstByDetailedStatus(String detailedStatus);
}
